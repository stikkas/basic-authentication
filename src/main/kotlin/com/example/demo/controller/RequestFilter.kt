package com.example.demo.controller

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpFilter
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import java.util.Base64
import org.springframework.security.core.context.SecurityContextHolder


class RequestFilter : HttpFilter() {

    override fun doFilter(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        if (SecurityContextHolder.getContext().authentication == null) {
            println("Authentication is null")
            val req = MutableRequest(request)
            val auth = String(Base64.getEncoder().encode("user:password".toByteArray()))
            req.putHeader("Authorization", "Basic $auth")
            chain.doFilter(req, response)
        } else {
            println("Authenticated")
            chain.doFilter(request, response)
        }

    }
}
