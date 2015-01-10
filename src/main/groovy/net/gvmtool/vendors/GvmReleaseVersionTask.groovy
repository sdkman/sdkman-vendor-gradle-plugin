package net.gvmtool.vendors

class GvmReleaseVersionTask extends GvmReleaseBaseTask {

    final static RELEASE_ENDPOINT = "/release"

    GvmReleaseVersionTask() {
        description = "Release a new Candidate Version on GVM."
    }

    void execute(GvmConfig config) {
        withTry(logger) {
            logger.quiet("Releasing $config.candidate $config.version...")
            def values = [candidate: config.candidate, version: config.version, url: config.url]
            def response = post(restClient, RELEASE_ENDPOINT, accessToken, values)
            logger.quiet("Response: ${response.statusCode}: ${response.json.message}...")
        }
    }
}
