package net.gvmtool.vendors.model

import org.gradle.api.NamedDomainObjectContainer
import org.hibernate.validator.constraints.URL

import javax.validation.constraints.NotNull

class GvmExtension {

    final NamedDomainObjectContainer<ApiCredentials> apis

    GvmExtension(apis) {
        this.apis = apis
    }

    def apis(Closure closure) {
        apis.configure closure
    }

    @NotNull
    String candidate

    @NotNull
    String version

    @NotNull
    @URL
    String url
}
