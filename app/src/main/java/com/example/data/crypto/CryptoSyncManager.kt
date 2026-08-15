package com.example.data.crypto

import android.util.Base64
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec

data class EncryptedSyncPayload(
    val cipherTextBase64: String,
    val ivBase64: String,
    val sha256Hash: String,
    val deviceOrigin: String,
    val timestamp: Long,
    val recordCount: Int
)

object CryptoSyncManager {

    private const val AES_KEY_SIZE = 256
    private const val GCM_IV_LENGTH = 12
    private const val GCM_TAG_LENGTH = 128
    private const val DEFAULT_SECRET_SEED = "ARCANA_SACRED_ALCHEMICAL_KEY_SEED_78_CARDS"

    private val secureRandom = SecureRandom()

    private val masterKey: SecretKey by lazy {
        deriveKeyFromPassphrase(DEFAULT_SECRET_SEED)
    }

    fun deriveKeyFromPassphrase(passphrase: String): SecretKey {
        val digest = MessageDigest.getInstance("SHA-256")
        val keyBytes = digest.digest(passphrase.toByteArray(StandardCharsets.UTF_8))
        return SecretKeySpec(keyBytes, "AES")
    }

    fun encryptPayload(plainText: String, deviceId: String = "Android_Pixel_Node_1", customPass: String? = null): EncryptedSyncPayload {
        val key = if (customPass.isNullOrBlank()) masterKey else deriveKeyFromPassphrase(customPass)
        val iv = ByteArray(GCM_IV_LENGTH)
        secureRandom.nextBytes(iv)

        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        val spec = GCMParameterSpec(GCM_TAG_LENGTH, iv)
        cipher.init(Cipher.ENCRYPT_MODE, key, spec)

        val cipherBytes = cipher.doFinal(plainText.toByteArray(StandardCharsets.UTF_8))
        val cipherBase64 = Base64.encodeToString(cipherBytes, Base64.NO_WRAP)
        val ivBase64 = Base64.encodeToString(iv, Base64.NO_WRAP)

        val hash = calculateSha256(cipherBase64)

        return EncryptedSyncPayload(
            cipherTextBase64 = cipherBase64,
            ivBase64 = ivBase64,
            sha256Hash = "0x" + hash.take(12),
            deviceOrigin = deviceId,
            timestamp = System.currentTimeMillis(),
            recordCount = 1
        )
    }

    fun decryptPayload(cipherTextBase64: String, ivBase64: String, customPass: String? = null): String {
        return try {
            val key = if (customPass.isNullOrBlank()) masterKey else deriveKeyFromPassphrase(customPass)
            val iv = Base64.decode(ivBase64, Base64.NO_WRAP)
            val cipherBytes = Base64.decode(cipherTextBase64, Base64.NO_WRAP)

            val cipher = Cipher.getInstance("AES/GCM/NoPadding")
            val spec = GCMParameterSpec(GCM_TAG_LENGTH, iv)
            cipher.init(Cipher.DECRYPT_MODE, key, spec)

            val plainBytes = cipher.doFinal(cipherBytes)
            String(plainBytes, StandardCharsets.UTF_8)
        } catch (e: Exception) {
            "Decryption Error: ${e.localizedMessage}"
        }
    }

    fun calculateSha256(input: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hash = digest.digest(input.toByteArray(StandardCharsets.UTF_8))
        return hash.joinToString("") { "%02x".format(it) }
    }
}
