package io.sdkman.vendors.model

class SdkmanExtension {

    String api = "https://vendors.sdkman.io"

    String consumerKey

    String consumerToken

    String candidate

    String version

    String platform = "UNIVERSAL"

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
                ", platform='" + platform + '\'' +
                ", url='" + url + '\'' +
                ", hashtag='" + hashtag + '\'' +
                '}';
    }
}
