package io.sdkman.vendors.tasks

import groovy.transform.CompileStatic
import io.sdkman.vendors.infra.ApiErrorResponseHandler
import io.sdkman.vendors.infra.ApiExecutions
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

@CompileStatic
abstract class SdkAnnounceVersion extends SdkmanVendorBaseTask implements ApiErrorResponseHandler, ApiExecutions {
    @Input
    abstract Property<String> getHashtag()

    @TaskAction
    void start() {
        handleNon2xxApiResponse {
            execAnnounce(apiUrl.get(), candidate.get(), version.get(), hashtag.get(), consumerKey.get(), consumerToken.get())
        }
    }
}
