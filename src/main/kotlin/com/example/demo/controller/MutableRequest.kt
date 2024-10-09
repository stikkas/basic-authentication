package com.example.demo.controller

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletRequestWrapper
import java.util.Collections
import java.util.Enumeration


class MutableRequest(private val request: HttpServletRequest) : HttpServletRequestWrapper(request) {
    private val customHeaders: MutableMap<String, String> = mutableMapOf()

    fun putHeader(name: String, value: String) {
        customHeaders[name] = value
    }

    override fun getHeader(name: String): String? {
        if (customHeaders.containsKey(name))
            return customHeaders[name]
        return request.getHeader(name)
    }

    override fun getHeaderNames(): Enumeration<String> {
        val set = customHeaders.keys.toMutableSet()
        val headers = request.headerNames
        while(headers.hasMoreElements()) {
            set.add(headers.nextElement())
        }
        return Collections.enumeration(set)
    }
}
