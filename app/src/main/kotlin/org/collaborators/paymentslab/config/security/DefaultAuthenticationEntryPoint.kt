package org.collaborators.paymentslab.config.security

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.collaborator.paymentlab.common.error.ErrorCode
import org.collaborator.paymentlab.common.result.ApiResult
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint

class DefaultAuthenticationEntryPoint(
    private val objectMapper: ObjectMapper): AuthenticationEntryPoint {

    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException?
    ) {
        response.status = HttpStatus.UNAUTHORIZED.value()
        response.setHeader("content-type", "application/json;charset=utf8")
        val outputStream = response.outputStream
        objectMapper.writeValue(outputStream, ApiResult.error(ErrorCode.UN_AUTHENTICATED))
    }
}