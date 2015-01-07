package net.gvmtool.vendors

class GvmReleaseVersionTask extends GvmReleaseBaseTask implements ExceptionHandling, ApiAccess {

    final static RELEASE_ENDPOINT = "/release"

    GvmReleaseVersionTask() {
        description = "Release a new Candidate Version on GVM."
    }

    void execute(GvmConfig gvm) {
        withTry(logger) {
            logger.quiet("Releasing $gvm.candidate $gvm.version...")
            def values = [candidate: gvm.candidate, version: gvm.version, url: gvm.url]
            def response = post(restClient, RELEASE_ENDPOINT, accessToken, values)
            logger.quiet("Response: ${response.statusCode}: ${response.json.message}...")
        }
    }
}
