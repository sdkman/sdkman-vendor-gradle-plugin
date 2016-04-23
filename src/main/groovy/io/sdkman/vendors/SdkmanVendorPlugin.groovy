package io.sdkman.vendors

import io.sdkman.vendors.model.SdkmanExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task

class SdkmanVendorPlugin implements Plugin<Project> {
    @Override
    void apply(Project target) {
        target.extensions.create("sdkman", SdkmanExtension)

        configure(target.tasks.create('sdkReleaseVersion', SdkReleaseVersionTask))
        configure(target.tasks.create('sdkDefaultVersion', SdkDefaultVersionTask))
        configure(target.tasks.create('sdkAnnounceVersion', SdkAnnounceVersionTask))

        configure(target.tasks.create('sdkMinorRelease', SdkMinorRelease))
        configure(target.tasks.create('sdkMajorRelease', SdkMajorRelease))
    }

    private Task configureCommon(SdkmanVendorBaseTask task) {
        configureTask(task) {
            apiUrl = apiUrl ?: project.extensions.sdkman.api
            candidate = candidate ?: project.extensions.sdkman.candidate
            version = version ?: project.extensions.sdkman.version
            consumerKey = consumerKey ?: project.extensions.sdkman.consumerKey
            consumerToken = consumerToken ?: project.extensions.sdkman.consumerToken
        }
        return task
    }

    private Task configure(SdkReleaseVersionTask task) {
        configureCommon(task)
        return configureTask(task) {
            downloadUrl = downloadUrl ?: project.extensions.sdkman.url
        }
    }

    private Task configure(SdkDefaultVersionTask task) {
        return configureCommon(task)
    }

    private Task configure(SdkAnnounceVersionTask task) {
        configureCommon(task)
        return configureTask(task) {
            hashtag = hashtag ?: project.extensions.sdkman.hashtag
        }
    }

    private Task configure(SdkMinorRelease task) {
        configureCommon(task)
        return configureTask(task) {
            downloadUrl = downloadUrl ?: project.extensions.sdkman.url
            hashtag = hashtag ?: project.extensions.sdkman.hashtag
        }
    }

    private static configureTask(Task task, Closure initializer) {
        task.project.afterEvaluate {
            def cl = initializer.clone()
            cl.delegate = task
            cl.resolveStrategy = DELEGATE_FIRST
            cl.call()
        }
        return task
    }
}
