package net.gvmtool.vendors

import org.hibernate.validator.constraints.Length
import org.hibernate.validator.constraints.URL

import javax.validation.constraints.NotNull

class GvmExtension {

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

}
