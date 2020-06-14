package io.sdkman.vendors

class SdkReleaseVersionTask extends SdkmanVendorBaseTask {

    static final RELEASE_ENDPOINT = "/release"

    String downloadUrl

    SdkReleaseVersionTask() {
        description = "Release a new Candidate Version on SDKMAN!"
    }

    @Override
    void executeTask() {
        withConnection(apiUrl, RELEASE_ENDPOINT, consumerKey, consumerToken) { conn ->
            logger.quiet("Releasing $candidate $version...")

            def values = [candidate: candidate, version: version, url: downloadUrl, platform: platform ?: "UNIVERSAL"]

            def response = post(conn, values)

            logger.quiet("Response: ${response.responseCode}: ${response.responseMessage}...")
        }
    }
}
