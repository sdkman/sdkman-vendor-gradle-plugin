package net.gvmtool.vendors.model

import org.hibernate.validator.constraints.Length
import org.hibernate.validator.constraints.URL

import javax.validation.constraints.NotNull

class ApiCredentials {

    @NotNull
    @URL
    String apiUrl

    @NotNull
    String clientId

    @NotNull
    @Length(min = 24, max = 24, message = "must be 24 characters long")
    String clientSecret

    @NotNull
    String username

    @NotNull
    String password

    String name

    ApiCredentials(){}

    ApiCredentials(String name) {
        this.name = name
    }
}
