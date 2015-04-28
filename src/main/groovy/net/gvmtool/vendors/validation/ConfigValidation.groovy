package net.gvmtool.vendors.validation

import org.gradle.api.GradleException
import org.gradle.api.Task

import javax.validation.Validation
import javax.validation.ValidatorFactory

trait ConfigValidation {

    protected ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()

    def withValid(Task task, fun) {
        def validator = validatorFactory.validator
        detectViolationsIn(task.name, validator.validate(task))
        fun()
    }

    def detectViolationsIn(String name, Set constraints) {
        if (constraints.size()) {
            def message = constraints.collect { "${it.propertyPath} ${it.message}" }.join("; ")
            throw new GradleException("Invalid configuration for task $name: " + message)
        }
    }
}
