package net.gvmtool.vendors

import net.gvmtool.vendors.validation.ConfigValidation
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
