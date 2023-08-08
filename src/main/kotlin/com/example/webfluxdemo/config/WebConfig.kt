package com.example.webfluxdemo.config

import com.example.webfluxdemo.domain.ResponseResult
import com.example.webfluxdemo.filter.AuthFilter
import com.example.webfluxdemo.handler.AuthHandler
import com.example.webfluxdemo.handler.HelloHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.config.WebFluxConfigurer
import org.springframework.web.reactive.function.server.*
import reactor.core.publisher.Mono

@Configuration
@EnableWebFlux
class WebConfig(
    @Autowired val authHandler: AuthHandler,
    @Autowired val helloHandler: HelloHandler
) : WebFluxConfigurer {

    @Bean
    fun authRouter(): RouterFunction<*> {
        return coRouter {
            accept(APPLICATION_JSON).nest {
                "/auth".nest {
                    POST("/login", authHandler::login)
                    POST("/register", authHandler::register)
                }

                "/hello".nest {
                    GET("", helloHandler::hello)
                    filter(AuthFilter::doFilter)
                }

            }
        }
    }
}