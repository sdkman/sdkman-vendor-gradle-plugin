package net.gvmtool.vendors.validation

import net.gvmtool.vendors.model.GvmExtension
import org.gradle.api.GradleException

import javax.validation.Validation
import javax.validation.ValidatorFactory

trait ConfigValidation {

    final static GVM_CONFIG_BLOCK = "gvm"
    final static APIS_CONFIG_BLOCK = "apis"

    protected ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()

    def withValid(GvmExtension config, fun) {
        def validator = validatorFactory.validator

        def constraints = validator.validate(config)

        detectViolationsIn(GVM_CONFIG_BLOCK, constraints)

        config.apis.asList().forEach {
            detectViolationsIn(APIS_CONFIG_BLOCK, validator.validate(it))
        }

        fun()
    }

    def detectViolationsIn(String block, Set constraints) {
        if (constraints.size()) {
            def message = constraints.collect { "${it.propertyPath} ${it.message}" }.join("; ")
            throw new GradleException("config block invalid: $block - " + message)
        }
    }
}