package net.gvmtool.vendors

import org.gradle.api.tasks.Input

class GvmReleaseVersionTask extends GvmVendorBaseTask {

    static final RELEASE_ENDPOINT = "/release"

    String downloadUrl

    GvmReleaseVersionTask() {
        description = "Release a new Candidate Version on GVM."
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
