package io.sdkman.vendors

class SdkAnnounceVersionTask extends SdkmanVendorBaseTask {

    static final String ANNOUNCE_ENDPOINT = "/announce/struct"

    String hashtag

    SdkAnnounceVersionTask() {
        description = "Announce a Release on SDKMAN!"
    }

    @Override
    void executeTask() {
        withConnection(apiUrl, ANNOUNCE_ENDPOINT, consumerKey, consumerToken) { conn ->
            logger.quiet("Announcing for $candidate $version...")
            def values = [candidate: candidate, version: version, hashtag: hashtag ?: candidate]
            def response = post(conn, values)
            logger.quiet("Response: ${response.responseCode}: ${response.responseMessage}...")
        }
    }
}
