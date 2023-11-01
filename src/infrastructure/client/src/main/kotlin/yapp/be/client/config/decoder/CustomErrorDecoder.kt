package yapp.be.client.config.decoder

import feign.Response
import feign.codec.ErrorDecoder
import io.github.oshai.kotlinlogging.KotlinLogging

class CustomErrorDecoder : ErrorDecoder {
    private val defaultErrorDecoder: ErrorDecoder = ErrorDecoder.Default()
    private val logger = KotlinLogging.logger { }
    override fun decode(methodKey: String?, response: Response): Exception {
        logger.info { response.body().toString() }
        return defaultErrorDecoder.decode(methodKey, response)
    }
}
