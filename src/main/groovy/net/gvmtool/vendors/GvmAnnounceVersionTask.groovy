package net.gvmtool.vendors

import net.gvmtool.vendors.model.GvmExtension

class GvmAnnounceVersionTask extends GvmVendorBaseTask {

    static final String ANNOUNCE_ENDPOINT = "/announce/struct"

    GvmAnnounceVersionTask() {
        description = "Announce a Release on GVM."
    }

    @Override
    void execute(GvmExtension config) {
        withConnection(config.apiUrl) {
            logger.quiet("Announcing for $config.candidate $config.version...")

            def values = [candidate: config.candidate, version: config.version,
                    hashtag: config.hashtag ?: config.candidate ]

            def response = post(restClient, ANNOUNCE_ENDPOINT,
                    config.consumerKey, config.consumerToken, values)

            logger.quiet("Response: ${response.statusCode}: ${response.contentAsString}...")
        }
    }
}
