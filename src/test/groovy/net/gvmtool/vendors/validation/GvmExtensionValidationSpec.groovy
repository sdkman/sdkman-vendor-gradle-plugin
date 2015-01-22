package net.gvmtool.vendors.validation

import net.gvmtool.vendors.model.GvmExtension
import spock.lang.Specification

import javax.validation.ConstraintViolation
import javax.validation.Validation
import javax.validation.Validator

class GvmExtensionValidationSpec extends Specification {

    static validatorFactory

    Validator validator

    GvmExtension config

    void setupSpec() {
        validatorFactory = Validation.buildDefaultValidatorFactory()
    }

    void setup() {
        validator = validatorFactory.validator
        config = new GvmExtension(null)
        config.candidate = "grails"
        config.version = "2.4.4"
        config.url = "http://dist.springframework.org.s3.amazonaws.com/release/GRAILS/grails-2.4.4.zip"
    }

    void "should validate candidate is not null"() {
        given:
        config.candidate = null

        when:
        def constraints = validator.validate(config)

        then:
        constraints.size() == 1
        extractMessage(constraints) == "may not be null"
    }

    void "should validate candidate version is not null"() {
        given:
        config.version = null

        when:
        def constraints = validator.validate(config)

        then:
        constraints.size() == 1
        extractMessage(constraints) == "may not be null"
    }

    void "should validate candidate version url"() {
        setup:
        config.url = url
        def constraints = validator.validate(config)

        expect:
        constraints.size() == size
        extractMessage(constraints) == message

        where:
        url                                                                               | size | message
        null                                                                              |    1 | "may not be null"
        "invalid_url"                                                                     |    1 | "must be a valid URL"
        "http://dist.springframework.org.s3.amazonaws.com/release/GRAILS/grails-2.4.4.zip"|    0 | "no message"
    }

    private static extractMessage(Set<ConstraintViolation> constraints) {
        (constraints.size() > 0) ? constraints.first().message : "no message"
    }
}
