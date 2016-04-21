package net.gvmtool.vendors

class SdkAnnounceVersionTask extends SdkmanVendorBaseTask {

    static final String ANNOUNCE_ENDPOINT = "/announce/struct"

    String hashtag

    SdkAnnounceVersionTask() {
        description = "Announce a Release on SDKMAN!"
    }

    @Override
    void executeTask() {
        withConnection(apiUrl) {
            logger.quiet("Announcing for $candidate $version...")

            def values = [candidate: candidate, version: version, hashtag: hashtag ?: candidate ]

            def response = post(restClient, ANNOUNCE_ENDPOINT, consumerKey, consumerToken, values)

            logger.quiet("Response: ${response.statusCode}: ${response.contentAsString}...")
        }
    }
}
