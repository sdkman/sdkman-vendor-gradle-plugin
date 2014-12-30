package net.gvmtool.vendors

class GvmDefaultVersionTask extends GvmReleaseBaseTask implements ExceptionHandling, ReleaseApiAccess {
    void execute(GvmConfig config) {
        withTry(logger) {
            logger.quiet("Releasing $config.candidate $config.version...")
            def values = [candidate: config.candidate, version: config.version]
            def response = put(restClient, '/default', accessToken, values)
            logger.quiet("Response: ${response.statusCode}: ${response.json.message}...")
        }
    }
}
