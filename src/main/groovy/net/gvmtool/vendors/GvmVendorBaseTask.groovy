package net.gvmtool.vendors

import net.gvmtool.vendors.model.GvmExtension
import net.gvmtool.vendors.validation.ConfigValidation
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

abstract class GvmVendorBaseTask extends DefaultTask
        implements ExceptionHandling, HttpVerbs, ApiConnectivity, ConfigValidation {

    @TaskAction
    void start() {
        GvmExtension config = project.gvm
        withTry(logger){
            withValid(config){
                execute(config)
            }
        }
    }

    abstract void execute(GvmExtension config)

}
