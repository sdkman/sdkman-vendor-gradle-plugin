package net.gvmtool.vendors.model

import org.hibernate.validator.constraints.URL

class GvmExtension {

    @URL
    String api = "https://vendor.gvmtool.net"

    String consumerKey

    String consumerToken

    String candidate

    String version

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
