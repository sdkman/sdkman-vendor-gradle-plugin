package io.sdkman.vendors.model

import groovy.transform.CompileStatic
import org.gradle.api.provider.MapProperty
import org.gradle.api.provider.Property

@CompileStatic
abstract class SdkmanExtension {
    abstract Property<String> getApiUrl()

    abstract Property<String> getConsumerKey()

    abstract Property<String> getConsumerToken()

    abstract Property<String> getCandidate()

    abstract Property<String> getVersion()

    abstract Property<String> getUrl()

    abstract MapProperty<String, String> getPlatforms()

    abstract Property<String> getHashtag()
}
