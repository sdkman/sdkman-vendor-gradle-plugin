package net.gvmtool.vendors

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

abstract class GvmReleaseBaseTask extends DefaultTask implements ConfigValidation, ExceptionHandling, HttpVerbs, OAuthConnectivity {

    @TaskAction
    void start() {
        GvmConfig gvmConfig = project.gvm
        withValid(gvmConfig) {
            withTry(logger){
                withAuth(gvmConfig){
                    execute(gvmConfig)
                }
            }
        }
    }

    abstract void execute(GvmConfig config)

}
