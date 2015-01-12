package net.gvmtool.vendors

import net.gvmtool.vendors.validation.ConfigValidation
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

abstract class GvmVendorBaseTask extends DefaultTask implements ConfigValidation,
        ExceptionHandling, HttpVerbs, ApiConnectivity, OAuth {

    @TaskAction
    void start() {
        GvmExtension config = project.gvm
        withValid(config) {
            withTry(logger){
                withConnection(config){
                    withAuth(restClient, config){
                        execute(config)
                    }
                }
            }
        }
    }

    abstract void execute(GvmExtension config)

}
