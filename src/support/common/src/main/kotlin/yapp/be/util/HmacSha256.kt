package yapp.be.util

import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

object HmacSha256 {
    private const val ALGORITHM = "HmacSHA256"
    private val encoder = Base64.getEncoder()

    fun sign(str: String, secretKey: String): String {
        val rawHmac = hmac(secretKey)
            .doFinal(str.toByteArray(Charsets.UTF_8))
        return String(encoder.encode(rawHmac))
    }

    private fun hmac(secretKey: String): Mac {
        val signingKey = SecretKeySpec(secretKey.toByteArray(), ALGORITHM)
        return Mac.getInstance(ALGORITHM)
            .also { it.init(signingKey) }
    }
}
