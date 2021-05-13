package io.sdkman.vendors.tasks

import groovy.transform.CompileStatic
import org.gradle.api.DefaultTask
import org.gradle.api.credentials.PasswordCredentials
import org.gradle.api.provider.MapProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Nested

@CompileStatic
abstract class SdkmanVendorBaseTask extends DefaultTask {

    @Input
    abstract Property<String> getApiUrl()

    @Input
    abstract Property<String> getCandidate()

    @Input
    abstract Property<String> getVersion()

    @Input
    abstract MapProperty<String, String> getPlatforms()

    @Nested
    abstract Property<PasswordCredentials> getCredentials()
}
