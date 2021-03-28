package ru.school21.eleonard.security.helpers

import android.content.Context
import android.os.Build
import ru.school21.eleonard.security.helpers.EncryptionKeyGenerator.generateKeyPairPreM
import ru.school21.eleonard.security.helpers.EncryptionKeyGenerator.generateSecretKey
import java.io.IOException
import java.security.KeyStore
import java.security.KeyStoreException
import java.security.NoSuchAlgorithmException
import java.security.cert.CertificateException

object EncryptionUtils {
    fun encrypt(context: Context, token: String?): String? {
        val securityKey = getSecurityKey(context)
        return securityKey?.encrypt(token)
    }

    fun decrypt(context: Context, token: String?): String? {
        val securityKey = getSecurityKey(context)
        return securityKey?.decrypt(token)
    }

    private fun getSecurityKey(context: Context): SecurityKey? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            generateSecretKey(keyStore!!)
        } else {
            generateKeyPairPreM(context, keyStore!!)
        }
    }

    private val keyStore: KeyStore?
        get() {
            var keyStore: KeyStore? = null
            try {
                keyStore = KeyStore.getInstance(EncryptionKeyGenerator.ANDROID_KEY_STORE)
                keyStore.load(null)
            } catch (e: KeyStoreException) {
                e.printStackTrace()
            } catch (e: CertificateException) {
                e.printStackTrace()
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return keyStore
        }

    fun clear() {
        val keyStore = keyStore
        try {
            if (keyStore!!.containsAlias(EncryptionKeyGenerator.KEY_ALIAS))
                keyStore.deleteEntry(EncryptionKeyGenerator.KEY_ALIAS)
        } catch (e: KeyStoreException) {
            e.printStackTrace()
        }
    }
}