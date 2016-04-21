package net.gvmtool.vendors.model

import org.hibernate.validator.constraints.URL

class SdkmanExtension {

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
        return "SdkmanExtension{" +
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
