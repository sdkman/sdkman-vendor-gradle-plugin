package net.gvmtool.vendors

import groovy.json.JsonSlurper
import org.gradle.api.logging.Logger
import wslite.rest.RESTClientException

trait ExceptionHandling {

    def slurper = new JsonSlurper()

    def withTry(Logger logger, fun) {
        try {
            fun()

        } catch (RESTClientException e) {
            def response = e.response
            def message = slurper.parseText(response.contentAsString).message
            logger.error("Error: ${response.statusCode}: ${message}")
        }
    }
}
