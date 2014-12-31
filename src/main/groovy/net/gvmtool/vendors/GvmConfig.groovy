package net.gvmtool.vendors

import org.hibernate.validator.constraints.Length
import org.hibernate.validator.constraints.URL

import javax.validation.constraints.NotNull
import org.gradle.api.GradleException

import javax.validation.constraints.Size

class GvmConfig {

    @NotNull
    @URL
    String apiBaseUrl = "https://release-dev.cfapps.io"

    @NotNull
    String releaseClientId

    @NotNull
    @Length(min = 24, max = 24, message = "must be 24 characters long")
    String releaseClientSecret

    @NotNull
    String username

    @NotNull
    String password

    @NotNull
    String candidate

    @NotNull
    String version

    @NotNull
    @URL
    String url

    def validate() {
        [
                "apiBaseUrl"         : apiBaseUrl,
                "releaseClientId"    : releaseClientId,
                "releaseClientSecret": releaseClientSecret,
                "username"           : username,
                "password"           : password,
                "candidate"          : candidate,
                "version"            : version,
                "url"                : url
        ].each { name, field ->
            if (!field) throw new GradleException("Missing field in config: $name")
        }
    }
}
