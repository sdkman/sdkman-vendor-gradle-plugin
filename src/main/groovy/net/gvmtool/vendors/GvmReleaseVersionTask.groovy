package net.gvmtool.vendors

import net.gvmtool.vendors.model.GvmExtension

class GvmReleaseVersionTask extends GvmVendorBaseTask {

    static final RELEASE_ENDPOINT = "/release"

    GvmReleaseVersionTask() {
        description = "Release a new Candidate Version on GVM."
    }

    @Override
    void execute(GvmExtension config) {
        withConnection(config.api) {
            logger.quiet("Releasing $config.candidate $config.version...")

            def values = [candidate: config.candidate, version: config.version, url: config.url]

            def response = post(restClient, RELEASE_ENDPOINT,
                    config.consumerKey, config.consumerToken, values)

            logger.quiet("Response: ${response.statusCode}: ${response.contentAsString}...")
        }
    }
}
