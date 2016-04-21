package net.gvmtool.vendors

class SdkReleaseVersionTask extends SdkmanVendorBaseTask {

    static final RELEASE_ENDPOINT = "/release"

    String downloadUrl

    SdkReleaseVersionTask() {
        description = "Release a new Candidate Version on SDKMAN!"
    }

    @Override
    void executeTask() {
        withConnection(apiUrl) {
            logger.quiet("Releasing $candidate $version...")

            def values = [candidate: candidate, version: version, url: downloadUrl]

            def response = post(restClient, RELEASE_ENDPOINT, consumerKey, consumerToken, values)

            logger.quiet("Response: ${response.statusCode}: ${response.contentAsString}...")
        }
    }
}
