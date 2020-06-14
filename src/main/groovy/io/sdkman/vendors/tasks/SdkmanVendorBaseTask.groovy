package io.sdkman.vendors.tasks

import io.sdkman.vendors.infra.ApiConnectivity
import io.sdkman.vendors.infra.ExceptionHandling
import io.sdkman.vendors.infra.HttpVerbs
import io.sdkman.vendors.validation.ConfigValidation
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.hibernate.validator.constraints.URL

import javax.validation.constraints.NotNull

abstract class SdkmanVendorBaseTask extends DefaultTask
        implements ExceptionHandling, HttpVerbs, ApiConnectivity, ConfigValidation {

    @Input @URL @NotNull String apiUrl
    @Input @NotNull String candidate
    @Input @NotNull String version
    @Input @NotNull String platform
    @NotNull String consumerKey
    @NotNull String consumerToken

    @TaskAction
    void start() {
        withTry(logger){
            withValid(this) {
                executeTask()
            }
        }
    }

    abstract void executeTask()

}
