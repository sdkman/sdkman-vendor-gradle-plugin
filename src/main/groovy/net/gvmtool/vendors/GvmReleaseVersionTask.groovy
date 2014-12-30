package net.gvmtool.vendors

class GvmReleaseVersionTask extends GvmReleaseBaseTask implements ExceptionHandling, ReleaseApiAccess {

    void execute(GvmConfig gvm) {
        withTry(logger) {
            logger.quiet("Releasing $gvm.candidate $gvm.version...")
            def response = postRelease(restClient, accessToken, gvm.candidate, gvm.version, gvm.url)
            logger.info("${response.statusCode}: ${response.json.message}...")
        }
    }
}
