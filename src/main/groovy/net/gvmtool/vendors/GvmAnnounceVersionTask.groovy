package net.gvmtool.vendors

import net.gvmtool.vendors.model.GvmExtension

class GvmAnnounceVersionTask extends GvmVendorBaseTask {

    static final String ANNOUNCE_ENDPOINT = "/announce/struct"

    String hashtag

    GvmAnnounceVersionTask() {
        description = "Announce a Release on GVM."
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
