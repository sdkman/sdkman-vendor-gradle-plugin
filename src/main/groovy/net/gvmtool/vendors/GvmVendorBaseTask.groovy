package net.gvmtool.vendors

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

abstract class GvmVendorBaseTask extends DefaultTask implements ConfigValidation,
        ExceptionHandling, HttpVerbs, ApiConnectivity, OAuth {

    @TaskAction
    void start() {
        GvmConfig config = project.gvm
        withValid(config) {
            withTry(logger){
                withAuth(restClient, config){
                    execute(config)
                }
            }
        }
    }

    abstract void execute(GvmConfig config)

}
