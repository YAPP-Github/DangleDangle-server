package yapp.be.apiapplication.system.utils

import org.slf4j.MDC
import yapp.be.apiapplication.system.filter.servlet.WrappedHttpServletRequest

fun injectAccessLog(request: WrappedHttpServletRequest) {
    MDC.put(
        "requestHeader",
        request.headerNames.asSequence()
            .joinToString(separator = ", ", prefix = "{", postfix = "}") {
                headerName ->
                "$headerName: ${request.getHeader(headerName)}"
            }
    )
    MDC.put(
        "requestParameter",
        request.parameterNames.asSequence().joinToString(separator = ",") {
            parameterName ->
            "$parameterName=${request.getParameter(parameterName)}"
        }
    )
    MDC.put("requestMethod", request.method)
    MDC.put("requestUrl", request.requestURI)
    MDC.put("requestBody", String(request.inputStream.readAllBytes(), Charsets.UTF_8))
}
