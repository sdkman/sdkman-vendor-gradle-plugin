package net.gvmtool.vendors

import org.gradle.api.GradleException

import javax.validation.Validation
import javax.validation.ValidatorFactory

trait ConfigValidation {

    protected ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()

    def withValid(GvmExtension config, Closure call) {
        def constraints = validatorFactory.validator.validate(config)
        if(constraints.size()) {
            def message = constraints.collect { "${it.propertyPath} ${it.message}" }.join("; ")
            throw new GradleException("Configuration invalid: " + message)
        }

        call()
    }
}