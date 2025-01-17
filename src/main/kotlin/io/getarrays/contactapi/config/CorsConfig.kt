package io.getarrays.contactapi.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders.ACCEPT
import org.springframework.http.HttpHeaders.ACCEPT_ENCODING
import org.springframework.http.HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS
import org.springframework.http.HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN
import org.springframework.http.HttpHeaders.ACCESS_CONTROL_REQUEST_HEADERS
import org.springframework.http.HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.http.HttpHeaders.CONTENT_LENGTH
import org.springframework.http.HttpHeaders.CONTENT_TYPE
import org.springframework.http.HttpHeaders.ORIGIN
import org.springframework.http.HttpMethod.DELETE
import org.springframework.http.HttpMethod.GET
import org.springframework.http.HttpMethod.OPTIONS
import org.springframework.http.HttpMethod.PATCH
import org.springframework.http.HttpMethod.POST
import org.springframework.http.HttpMethod.PUT
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
class CorsConfig {

    @Bean
    fun corsFilter(): CorsFilter {
        val urlBasedCorsConfigurationSource = UrlBasedCorsConfigurationSource()
        val corsConfiguration = CorsConfiguration()
        corsConfiguration.allowCredentials = false
        corsConfiguration.allowedOrigins = listOf(
            "http://localhost:5173",
            "http://localhost:3000",
            "http://localhost:4200"
        )
//        corsConfiguration.allowedHeaders = listOf("*")
        corsConfiguration.allowedHeaders = listOf(
            ORIGIN,
            ACCESS_CONTROL_ALLOW_ORIGIN,
            CONTENT_TYPE,
            CONTENT_LENGTH,
            ACCEPT_ENCODING,
            ACCEPT,
            AUTHORIZATION,
            ACCESS_CONTROL_REQUEST_METHOD,
            ACCESS_CONTROL_REQUEST_HEADERS,
            ACCESS_CONTROL_ALLOW_CREDENTIALS,
            "X-Requested-With",
        )
        corsConfiguration.exposedHeaders = listOf(
            ORIGIN,
            ACCESS_CONTROL_ALLOW_ORIGIN,
            CONTENT_TYPE,
            CONTENT_LENGTH,
            ACCEPT_ENCODING,
            ACCEPT,
            AUTHORIZATION,
            ACCESS_CONTROL_REQUEST_METHOD,
            ACCESS_CONTROL_REQUEST_HEADERS,
            ACCESS_CONTROL_ALLOW_CREDENTIALS,
            "X-Requested-With",
        )
        corsConfiguration.setAllowedMethods(
            listOf(
                GET.name(),
                POST.name(),
                PUT.name(),
                PATCH.name(),
                DELETE.name(),
                OPTIONS.name()
            )
        )
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration)
        return CorsFilter(urlBasedCorsConfigurationSource)
    }

}
