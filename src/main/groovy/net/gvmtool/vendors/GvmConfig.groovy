package net.gvmtool.vendors

import org.gradle.api.GradleException

class GvmConfig {
    String apiBaseUrl = "https://release-dev.cfapps.io"

    String releaseClientId
    String releaseClientSecret

    String username
    String password

    String candidate
    String version
    String url

    def validate() {
        [
                "apiBaseUrl": apiBaseUrl,
                "releaseClientId": releaseClientId,
                "releaseClientSecret": releaseClientSecret,
                "username": username,
                "password": password,
                "candidate": candidate,
                "version": version,
                "url": url
        ].each { name, field ->
            if(!field) throw new GradleException("Missing field in config: $name")
        }
    }
}
