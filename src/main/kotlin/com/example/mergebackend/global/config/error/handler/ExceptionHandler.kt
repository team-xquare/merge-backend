package com.example.mergebackend.global.config.error.handler

import com.example.mergebackend.global.config.error.data.BindErrorResponse
import com.example.mergebackend.global.config.error.data.ErrorResponse
import com.example.mergebackend.global.config.error.exception.BusinessException
import org.springframework.core.convert.ConversionFailedException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.BindException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException::class)
    protected fun handleBindException(e: BindException): BindErrorResponse = ErrorResponse.of(e)

    @ExceptionHandler(BusinessException::class)
    protected fun customExceptionHandle(e: BusinessException): ResponseEntity<ErrorResponse> =
            ResponseEntity(ErrorResponse.of(e.errorCode), e.errorCode.status)

    @ExceptionHandler(HttpMessageNotReadableException::class)
    protected fun handleHttpMessageNotReadable(e: HttpMessageNotReadableException): ResponseEntity<ErrorResponse> =
            ResponseEntity(
                    ErrorResponse(HttpStatus.BAD_REQUEST, e.message ?: e.localizedMessage),
                    HttpStatus.BAD_REQUEST
            )

    @ExceptionHandler(IllegalStateException::class)
    protected fun handleIllegalStateException(e: IllegalStateException): ResponseEntity<ErrorResponse> =
            ResponseEntity(
                    ErrorResponse(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
                    HttpStatus.BAD_REQUEST
            )

    @ExceptionHandler(ConversionFailedException::class)
    protected fun handleConversionFailedException(e: ConversionFailedException): ResponseEntity<ErrorResponse> =
            ResponseEntity(
                    ErrorResponse(HttpStatus.BAD_REQUEST, "잘못된 입력입니다."),
                    HttpStatus.BAD_REQUEST
            )
}