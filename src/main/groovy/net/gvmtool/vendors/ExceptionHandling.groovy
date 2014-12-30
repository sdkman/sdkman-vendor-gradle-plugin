package net.gvmtool.vendors

import org.gradle.api.logging.Logger
import wslite.rest.RESTClientException

trait ExceptionHandling {

    def withTry(Logger logger, Closure closure) {
        try {
            closure()

        } catch (RESTClientException e) {
            def response = e.response
            logger.error("${response.statusCode}: ${e.message}")
        }
    }
}
