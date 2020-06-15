package io.sdkman.vendors

import io.sdkman.vendors.model.SdkmanExtension
import io.sdkman.vendors.tasks.SdkAnnounceVersionTask
import io.sdkman.vendors.tasks.SdkDefaultVersionTask
import io.sdkman.vendors.tasks.SdkMajorRelease
import io.sdkman.vendors.tasks.SdkMinorRelease
import io.sdkman.vendors.tasks.SdkReleaseVersionTask
import io.sdkman.vendors.tasks.SdkmanVendorBaseTask
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
            candidate = project.extensions.sdkman.candidate ?: "change_me"
            version = project.extensions.sdkman.version ?: "change_me"
            consumerKey = project.extensions.sdkman.consumerKey ?: "change_me"
            consumerToken = project.extensions.sdkman.consumerToken ?: "change_me"
            platforms = project.extensions.sdkman.platforms ?: [UNIVERSAL: project.extensions.sdkman.url ?: "change_me" ]
        }
        return task
    }

    private Task configure(SdkReleaseVersionTask task) {
        configureCommon(task)
    }

    private Task configure(SdkDefaultVersionTask task) {
        return configureCommon(task)
    }

    private Task configure(SdkAnnounceVersionTask task) {
        configureCommon(task)
        return configureTask(task) {
            hashtag = hashtag ? project.extensions.sdkman.candidate : project.extensions.sdkman.hashtag
        }
    }

    private Task configure(SdkMinorRelease task) {
        configureCommon(task)
        return configureTask(task) {
            hashtag = hashtag ? project.extensions.sdkman.candidate : project.extensions.sdkman.hashtag
        }
    }

    private Task configure(SdkMajorRelease task) {
        configureCommon(task)
        return configureTask(task) {
            hashtag = hashtag ? project.extensions.sdkman.candidate : project.extensions.sdkman.hashtag
        }
    }

    private static configureTask(Task task, Closure initializer) {
        task.project.afterEvaluate {
            def cl = initializer.clone()
            cl.delegate = task
            cl.resolveStrategy = Closure.DELEGATE_FIRST
            cl.call()
        }
        return task
    }
}
