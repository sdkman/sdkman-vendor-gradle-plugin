package net.gvmtool.vendors

import net.gvmtool.vendors.model.GvmExtension

class GvmReleaseVersionTask extends GvmVendorBaseTask {

    final static RELEASE_ENDPOINT = "/release"
    final static RELEASE_CONFIG = "release"

    GvmReleaseVersionTask() {
        description = "Release a new Candidate Version on GVM."
    }

    void execute(GvmExtension config) {
        def releaseConfig = config.apis.asMap.get(RELEASE_CONFIG)
        withConnection(releaseConfig) {
            withAuth(restClient, releaseConfig) {
                logger.quiet("Releasing $config.candidate $config.version...")
                def values = [candidate: config.candidate, version: config.version, url: config.url]
                def response = post(restClient, RELEASE_ENDPOINT, accessToken, values)
                logger.quiet("Response: ${response.statusCode}: ${response.json.message}...")
            }
        }
    }
}
