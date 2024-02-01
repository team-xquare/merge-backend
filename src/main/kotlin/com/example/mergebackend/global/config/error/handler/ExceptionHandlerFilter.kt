package com.example.mergebackend.global.config.error.handler

import com.example.mergebackend.global.config.error.data.ErrorCode
import com.example.mergebackend.global.config.error.exception.BusinessException
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class ExceptionHandlerFilter(
        private val objectMapper: ObjectMapper
): OncePerRequestFilter() {

    private fun sendErrorResponse(
            errorCode: ErrorCode,
            response: HttpServletResponse
    ){
        response.let {
            it.status = errorCode.status.value()
            it.contentType = MediaType.APPLICATION_JSON_VALUE
            it.characterEncoding = "UTF-8"
        }
        objectMapper.writeValue(
                response.writer,
                errorCode.message
        )
    }

    override fun doFilterInternal(
            request: HttpServletRequest,
            response: HttpServletResponse,
            filterChain: FilterChain
    ) {
        try {
            filterChain.doFilter(request, response)
        }catch (e: BusinessException) {
            sendErrorResponse(e.errorCode, response)
        }catch (e: Exception){
            e.printStackTrace()
            sendErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR, response)
        }

    }
}