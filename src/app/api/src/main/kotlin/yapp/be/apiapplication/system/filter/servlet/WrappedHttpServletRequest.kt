package yapp.be.apiapplication.system.filter.servlet

import jakarta.servlet.ReadListener
import jakarta.servlet.ServletInputStream
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletRequestWrapper
import org.springframework.util.FileCopyUtils
import yapp.be.exceptions.CustomException
import yapp.be.exceptions.SystemExceptionType
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException

class WrappedHttpServletRequest(request: HttpServletRequest) : HttpServletRequestWrapper(request) {

    private var cachedBuffer: ByteArrayOutputStream? = null

    @Throws(IOException::class)
    override fun getInputStream(): ServletInputStream {
        if (cachedBuffer == null) {
            cachedBuffer = ByteArrayOutputStream()
            FileCopyUtils.copy(super.getInputStream(), cachedBuffer!!)
        }
        return CachedServletInputStream()
    }

    inner class CachedServletInputStream : ServletInputStream() {
        private val buffer = ByteArrayInputStream(cachedBuffer?.toByteArray())
        @Throws(IOException::class)
        override fun read(): Int {
            return buffer.read()
        }

        override fun isFinished(): Boolean {
            return buffer.available() == 0
        }

        override fun isReady(): Boolean {
            return true
        }

        override fun setReadListener(listener: ReadListener?) {
            throw CustomException(SystemExceptionType.INTERNAL_SERVER_ERROR, "Not Implemented")
        }
    }
}
