package io.sdkman.vendors

import groovy.transform.CompileStatic
import io.sdkman.vendors.model.SdkmanExtension
import io.sdkman.vendors.tasks.SdkAnnounceVersion
import io.sdkman.vendors.tasks.SdkDefaultVersion
import io.sdkman.vendors.tasks.SdkReleaseVersion
import io.sdkman.vendors.tasks.SdkmanVendorBaseTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task

@CompileStatic
class SdkmanVendorPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        SdkmanExtension extension = project.extensions.create("sdkman", SdkmanExtension)

        // Default values for the sdkman extension
        extension.apiUrl.convention("https://vendors.sdkman.io")
        extension.consumerKey.convention(project.providers.environmentVariable("SDKMAN_KEY"))
        extension.consumerToken.convention(project.providers.environmentVariable("SDKMAN_TOKEN"))
        extension.platforms.convention(extension.url.map({ url -> [UNIVERSAL: url] }))

        // Wire extension into tasks
        project.tasks.withType(SdkmanVendorBaseTask).configureEach { SdkmanVendorBaseTask task ->
            task.apiUrl.convention(extension.apiUrl)
            task.candidate.convention(extension.candidate)
            task.version.convention(extension.version)
            task.platforms.convention(extension.platforms)
            task.consumerKey.convention(extension.consumerKey)
            task.consumerToken.convention(extension.consumerToken)
        }

        // We should only announce or change the default after we've done a release
        project.tasks.withType(SdkDefaultVersion).configureEach { Task task ->
            task.shouldRunAfter(project.tasks.withType(SdkReleaseVersion))
        }
        project.tasks.withType(SdkAnnounceVersion).configureEach { Task task ->
            task.shouldRunAfter(project.tasks.withType(SdkReleaseVersion))
        }

        // Create the underlying tasks that release, announce and change the default version on SDKMAN!
        def sdkReleaseVersion = project.tasks.register('sdkReleaseVersion', SdkReleaseVersion) { task ->
            task.description = "Release a new Candidate Version on SDKMAN!"
        }
        def sdkDefaultVersion = project.tasks.register('sdkDefaultVersion', SdkDefaultVersion) { task ->
            task.description = "Make an existing Candidate Version the new Default on SDKMAN!"
        }
        def sdkAnnounceVersion = project.tasks.register('sdkAnnounceVersion', SdkAnnounceVersion) { task ->
            task.description = "Announce a Release on SDKMAN!"
            task.hashtag.convention(extension.hashtag.orElse(extension.candidate))
        }

        // Create lifecycle/convenience tasks that do minor or major releases
        project.tasks.register('sdkMinorRelease') { task ->
            task.group = "SDKMAN! Release"
            task.description = "Performs a Minor Release. Release and Announce on SDKMAN!"
            task.dependsOn(sdkReleaseVersion, sdkAnnounceVersion)
        }

        project.tasks.register('sdkMajorRelease') { task ->
            task.group = "SDKMAN! Release"
            task.description = "Performs a Major Release. Release, Announce and Default on SDKMAN!"
            task.dependsOn(sdkReleaseVersion, sdkDefaultVersion, sdkAnnounceVersion)
        }
    }
}
