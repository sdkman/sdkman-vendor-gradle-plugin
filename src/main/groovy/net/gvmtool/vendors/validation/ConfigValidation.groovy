package net.gvmtool.vendors.validation

import net.gvmtool.vendors.model.GvmExtension
import org.gradle.api.GradleException

import javax.validation.Validation
import javax.validation.ValidatorFactory

trait ConfigValidation {

    final static GVM_CONFIG_BLOCK = "gvm"

    protected ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()

    def withValid(GvmExtension config, fun) {
        def validator = validatorFactory.validator
        detectViolationsIn(GVM_CONFIG_BLOCK, validator.validate(config))
        fun()
    }

    def detectViolationsIn(String block, Set constraints) {
        if (constraints.size()) {
            def message = constraints.collect { "${it.propertyPath} ${it.message}" }.join("; ")
            throw new GradleException("config block invalid: $block - " + message)
        }
    }
}