package io.sdkman.vendors.model

class SdkmanExtension {

    String api = "https://vendors.sdkman.io"

    String consumerKey

    String consumerToken

    String candidate

    String version

    String url

    Map<String, String> platforms

    String hashtag

    @Override
    public String toString() {
        return "SdkmanExtension{" +
                "api='" + api + '\'' +
                ", consumerKey='" + consumerKey + '\'' +
                ", consumerToken='" + consumerToken + '\'' +
                ", candidate='" + candidate + '\'' +
                ", version='" + version + '\'' +
                ", platforms='" + platforms + '\'' +
                ", url='" + url + '\'' +
                ", hashtag='" + hashtag + '\'' +
                '}';
    }
}
