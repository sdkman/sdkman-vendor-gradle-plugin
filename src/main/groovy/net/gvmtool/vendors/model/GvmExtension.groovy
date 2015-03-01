package net.gvmtool.vendors.model

import org.hibernate.validator.constraints.URL

import javax.validation.constraints.NotNull

class GvmExtension {

    @URL
    String api = "https://vendor.gvmtool.net"

    @NotNull
    String consumerKey

    @NotNull
    String consumerToken

    @NotNull
    String candidate

    @NotNull
    String version

    @NotNull
    @URL
    String url

    String hashtag

    @Override
    public String toString() {
        return "GvmExtension{" +
                "api='" + api + '\'' +
                ", consumerKey='" + consumerKey + '\'' +
                ", consumerToken='" + consumerToken + '\'' +
                ", candidate='" + candidate + '\'' +
                ", version='" + version + '\'' +
                ", url='" + url + '\'' +
                ", hashtag='" + hashtag + '\'' +
                '}';
    }
}
