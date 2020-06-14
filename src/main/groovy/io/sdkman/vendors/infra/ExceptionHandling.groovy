package io.sdkman.vendors.infra

import groovy.json.JsonSlurper
import org.gradle.api.logging.Logger

trait ExceptionHandling {

    def slurper = new JsonSlurper()

    def withTry(Logger logger, fun) {
        try {
            fun()

        } catch (Throwable e) {
            def response = e.response
            if(response) {
                def content = response.contentAsString
                try {
                    def message = slurper.parseText(content).message
                    logger.error("Error: ${response.statusCode}: ${message}")
                } catch (Exception ignored) {
                    logger.error("Error: ${response.statusCode}: ${content}")
                }
            } else {
                logger.error("Error: ${e.message}", e)
            }
        }
    }
}
