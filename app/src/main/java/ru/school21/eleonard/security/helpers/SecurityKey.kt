package ru.school21.eleonard.security.helpers

import android.os.Build
import android.util.Base64
import java.security.GeneralSecurityException
import java.security.KeyPair
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

internal class SecurityKey {
    private var secretKey: SecretKey? = null
    private var keyPair: KeyPair? = null

    constructor(secretKey: SecretKey?) {
        this.secretKey = secretKey
    }

    constructor(keyPair: KeyPair?) {
        this.keyPair = keyPair
    }

    fun encrypt(token: String?): String? {
        if (token == null) return null
        try {
            val cipher = getCipher(Cipher.ENCRYPT_MODE)
            val encrypted = cipher.doFinal(token.toByteArray())
            return Base64.encodeToString(encrypted, Base64.URL_SAFE)
        } catch (e: GeneralSecurityException) {
            e.printStackTrace()
        }
        return null
    }

    fun decrypt(encryptedToken: String?): String? {
        if (encryptedToken == null) return null
        try {
            val cipher = getCipher(Cipher.DECRYPT_MODE)
            val decoded = Base64.decode(encryptedToken, Base64.URL_SAFE)
            val original = cipher.doFinal(decoded)
            return String(original)
        } catch (e: GeneralSecurityException) {
            e.printStackTrace()
        }
        return null
    }

    @Throws(GeneralSecurityException::class)
    private fun getCipher(mode: Int): Cipher {
        val cipher: Cipher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cipher = Cipher.getInstance(AES_MODE_FOR_POST_API_23)
            cipher.init(mode, secretKey, GCMParameterSpec(128, AES_MODE_FOR_POST_API_23.toByteArray(), 0, 12))
        } else {
            cipher = Cipher.getInstance(RSA_MODE)
            cipher.init(mode, if (mode == Cipher.DECRYPT_MODE) keyPair!!.public else keyPair!!.private)
        }
        return cipher
    }

    companion object {
        private const val RSA_MODE = "RSA/ECB/PKCS1Padding"
        private const val AES_MODE_FOR_POST_API_23 = "AES/GCM/NoPadding"
    }
}