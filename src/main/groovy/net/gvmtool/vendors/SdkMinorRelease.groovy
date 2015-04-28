package net.gvmtool.vendors

import org.gradle.api.tasks.Input

class SdkMinorRelease extends GvmVendorBaseTask {

    static final ANNOUNCE_ENDPOINT = "/announce/struct"

    static final RELEASE_ENDPOINT = "/release"

    String downloadUrl
    String hashtag

    SdkMinorRelease() {
        description = "Convenience task performs a Minor Release consisting of Release and Announce combined."
    }

    @Override
    void executeTask() {
        withConnection(apiUrl) {
            logger.quiet("Releasing $candidate $version...")
            def releaseValues = [candidate: candidate, version: version, url: downloadUrl]
            def releaseResponse = post(restClient, RELEASE_ENDPOINT, consumerKey, consumerToken, releaseValues)
            logger.quiet("Response: ${releaseResponse.statusCode}: ${releaseResponse.contentAsString}...")

            logger.quiet("Announcing for $candidate $version...")
            def announceValues = [candidate: candidate, version: version, hashtag: hashtag ?: candidate]
            def announceResponse = post(restClient, ANNOUNCE_ENDPOINT, consumerKey, consumerToken, announceValues)
            logger.quiet("Response: ${announceResponse.statusCode}: ${announceResponse.contentAsString}...")
        }
    }
}
