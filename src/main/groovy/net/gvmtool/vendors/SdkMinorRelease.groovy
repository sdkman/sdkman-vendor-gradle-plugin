package net.gvmtool.vendors

import net.gvmtool.vendors.model.GvmExtension

class SdkMinorRelease extends GvmVendorBaseTask {

    static final ANNOUNCE_ENDPOINT = "/announce/struct"

    static final RELEASE_ENDPOINT = "/release"

    SdkMinorRelease() {
        description = "Convenience task performs a Minor Release consisting of Release and Announce combined."
    }

    @Override
    void execute(GvmExtension config) {
        withConnection(config.api) {
            logger.quiet("Releasing $config.candidate $config.version...")
            def releaseValues = [candidate: config.candidate, version: config.version, url: config.url]
            def releaseResponse = post(restClient, RELEASE_ENDPOINT, config.consumerKey, config.consumerToken, releaseValues)
            logger.quiet("Response: ${releaseResponse.statusCode}: ${releaseResponse.contentAsString}...")

            logger.quiet("Announcing for $config.candidate $config.version...")
            def announceValues = [candidate: config.candidate, version: config.version, hashtag: config.hashtag ?: config.candidate]
            def announceResponse = post(restClient, ANNOUNCE_ENDPOINT, config.consumerKey, config.consumerToken, announceValues)
            logger.quiet("Response: ${announceResponse.statusCode}: ${announceResponse.contentAsString}...")
        }
    }
}
