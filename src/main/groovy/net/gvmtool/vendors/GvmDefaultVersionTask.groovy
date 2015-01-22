package net.gvmtool.vendors

import net.gvmtool.vendors.model.GvmExtension

class GvmDefaultVersionTask extends GvmVendorBaseTask {

    static final DEFAULT_ENDPOINT = "/default"
    static final RELEASE_CONFIG = "release"

    GvmDefaultVersionTask() {
        description = "Make an existing Candidate Version the new Default on GVM."
    }

    void execute(GvmExtension config) {
        def releaseConfig = config.apis.asMap.get(RELEASE_CONFIG)
        withConnection(releaseConfig) {
            withAuth(restClient, releaseConfig) {
                logger.quiet("Releasing $config.candidate $config.version...")
                def values = [candidate: config.candidate, version: config.version]
                def response = put(restClient, DEFAULT_ENDPOINT, accessToken, values)
                logger.quiet("Response: ${response.statusCode}: ${response.json.message}...")
            }
        }
    }
}
