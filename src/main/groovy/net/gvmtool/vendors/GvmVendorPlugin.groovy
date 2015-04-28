package net.gvmtool.vendors

import net.gvmtool.vendors.model.GvmExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task

class GvmVendorPlugin implements Plugin<Project> {
    @Override
    void apply(Project target) {
        target.extensions.create("gvm", GvmExtension)

        configure(target.tasks.create('gvmReleaseVersion', GvmReleaseVersionTask))

        configure(target.tasks.create('gvmDefaultVersion', GvmDefaultVersionTask))
        configure(target.tasks.create('gvmAnnounceVersion', GvmAnnounceVersionTask))

        configure(target.tasks.create('sdkMinorRelease', SdkMinorRelease))
        configure(target.tasks.create('sdkMajorRelease', SdkMajorRelease))
    }

    private Task configureCommon(GvmVendorBaseTask task) {
        configureTask(task) {
            apiUrl = apiUrl ?: project.extensions.gvm.api
            candidate = candidate ?: project.extensions.gvm.candidate
            version = version ?: project.extensions.gvm.version
            consumerKey = consumerKey ?: project.extensions.gvm.consumerKey
            consumerToken = consumerToken ?: project.extensions.gvm.consumerToken
        }
        return task
    }

    private Task configure(GvmReleaseVersionTask task) {
        configureCommon(task)
        return configureTask(task) {
            downloadUrl = downloadUrl ?: project.extensions.gvm.url
        }
    }

    private Task configure(GvmDefaultVersionTask task) {
        return configureCommon(task)
    }

    private Task configure(GvmAnnounceVersionTask task) {
        configureCommon(task)
        return configureTask(task) {
            hashtag = hashtag ?: project.extensions.gvm.hashtag
        }
    }

    private Task configure(SdkMinorRelease task) {
        configureCommon(task)
        return configureTask(task) {
            downloadUrl = downloadUrl ?: project.extensions.gvm.url
            hashtag = hashtag ?: project.extensions.gvm.hashtag
        }
    }

    private Task configureTask(Task task, Closure initializer) {
        task.project.afterEvaluate {
            Closure cl = initializer.clone()
            cl.delegate = task
            cl.resolveStrategy = Closure.DELEGATE_FIRST
            cl.call()
        }
        return task
    }
}
