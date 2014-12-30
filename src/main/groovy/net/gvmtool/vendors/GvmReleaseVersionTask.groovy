package net.gvmtool.vendors

class GvmReleaseVersionTask extends GvmReleaseBaseTask implements ExceptionHandling, ReleaseApiAccess {

    final static RELEASE_ENDPOINT = "/release"

    void execute(GvmConfig gvm) {
        withTry(logger) {
            logger.quiet("Releasing $gvm.candidate $gvm.version...")
            def values = [candidate: gvm.candidate, version: gvm.version, url: gvm.url]
            def response = post(restClient, RELEASE_ENDPOINT, accessToken, values)
            logger.quiet("Response: ${response.statusCode}: ${response.json.message}...")
        }
    }
}
