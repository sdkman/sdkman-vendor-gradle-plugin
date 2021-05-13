package io.sdkman.vendors.model

import groovy.transform.CompileStatic
import org.gradle.api.credentials.PasswordCredentials
import org.gradle.api.provider.MapProperty
import org.gradle.api.provider.Property

@CompileStatic
abstract class SdkmanExtension {
    abstract Property<String> getApiUrl()

    abstract Property<PasswordCredentials> getCredentials()

    abstract Property<String> getCandidate()

    abstract Property<String> getVersion()

    abstract Property<String> getUrl()

    abstract MapProperty<String, String> getPlatforms()

    abstract Property<String> getHashtag()

    @Override
    String toString() {
        return "SdkmanExtension{" +
                "api='" + apiUrl.getOrNull() + '\'' +
                ", credentials available?='" + credentials.isPresent() + '\'' +
                ", candidate='" + candidate.getOrNull() + '\'' +
                ", version='" + version.getOrNull() + '\'' +
                ", platforms='" + platforms.getOrNull() + '\'' +
                ", url='" + url.getOrNull() + '\'' +
                ", hashtag='" + hashtag.getOrNull() + '\'' +
                '}';
    }
}
