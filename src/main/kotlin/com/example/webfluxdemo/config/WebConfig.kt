package com.example.webfluxdemo.config

import com.example.webfluxdemo.common.Role
import com.example.webfluxdemo.filter.AuthFilter
import com.example.webfluxdemo.filter.enableJwtAuth
import com.example.webfluxdemo.filter.hasRole
import com.example.webfluxdemo.handler.AuthHandler
import com.example.webfluxdemo.handler.HelloHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.config.WebFluxConfigurer
import org.springframework.web.reactive.function.server.*

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
            }
        }
    }

    @Bean
    fun helloRouter(): RouterFunction<*> {
        return coRouter {
            enableJwtAuth()
            accept(APPLICATION_JSON).nest {
                "/hello".nest {
                    GET("", helloHandler::hello)
                    hasRole(Role.Teacher, Role.User)
                }
            }
        }
    }
}