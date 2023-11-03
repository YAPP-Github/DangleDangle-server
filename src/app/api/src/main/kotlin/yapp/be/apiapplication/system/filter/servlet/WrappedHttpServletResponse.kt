package yapp.be.apiapplication.system.filter.servlet

import jakarta.servlet.ServletOutputStream
import jakarta.servlet.WriteListener
import jakarta.servlet.http.HttpServletResponse
import jakarta.servlet.http.HttpServletResponseWrapper
import yapp.be.exceptions.CustomException
import yapp.be.exceptions.SystemExceptionType
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.OutputStreamWriter
import java.io.PrintWriter

class WrappedHttpServletResponse(response: HttpServletResponse) : HttpServletResponseWrapper(response) {
    private var writer: PrintWriter? = null
    private var cachedBuffer = ByteArrayOutputStream()
    private var cachedServletOutputStream: ServletOutputStream? = null

    @Throws(IOException::class)
    override fun getOutputStream(): ServletOutputStream {
        if (cachedServletOutputStream == null) {
            cachedServletOutputStream = CachedServletOutputStream()
        }
        return cachedServletOutputStream!!
    }

    @Throws(IOException::class)
    override fun getWriter(): PrintWriter {
        if (writer == null) {
            writer = PrintWriter(OutputStreamWriter(outputStream, characterEncoding))
        }
        return writer!!
    }

    @Throws(IOException::class)
    override fun flushBuffer() {
        writer?.also {
            it.flush()
            return
        }

        cachedServletOutputStream?.also {
            it.flush()
            return
        }
    }

    @Throws(IOException::class)
    fun readAllBytes(): ByteArray {
        return cachedBuffer.toByteArray()
    }

    inner class CachedServletOutputStream() : ServletOutputStream() {
        private var buffer = response.outputStream

        @Throws(IOException::class)
        override fun write(b: Int) {
            cachedBuffer.write(b)
            buffer.write(b)
        }

        override fun isReady(): Boolean {
            return true
        }

        override fun setWriteListener(listener: WriteListener?) {
            throw CustomException(SystemExceptionType.INTERNAL_SERVER_ERROR, "Not yet implemented")
        }
    }
}
