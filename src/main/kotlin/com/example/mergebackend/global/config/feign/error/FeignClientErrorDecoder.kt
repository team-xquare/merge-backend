package com.example.mergebackend.global.config.feign.error

import com.example.mergebackend.global.config.error.exception.ExpiredTokenException
import com.example.mergebackend.global.config.feign.exception.FeignBadRequestException
import com.example.mergebackend.global.config.feign.exception.FeignDefaultException
import com.example.mergebackend.global.config.feign.exception.FeignForbiddenException
import com.example.mergebackend.global.config.feign.exception.FeignUnAuthorizationException
import feign.FeignException
import feign.Response
import feign.codec.ErrorDecoder
import mu.KotlinLogging

class FeignClientErrorDecoder : ErrorDecoder {

    private companion object {
        val logger = KotlinLogging.logger {}
    }

    override fun decode(methodKey: String, response: Response): Exception {
        logger.error { "${response.status()} ${response.reason()} : $methodKey | ${response.body()}\n$response" }

        if (response.status() >= 400) {
            when (response.status()) {
                400 -> throw FeignBadRequestException
                401 -> throw FeignUnAuthorizationException
                403 -> throw FeignForbiddenException
                419 -> throw ExpiredTokenException
                else -> throw FeignDefaultException
            }
        }

        return FeignException.errorStatus(methodKey, response)
    }
}
