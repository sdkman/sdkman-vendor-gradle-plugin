package net.gvmtool.vendors

class GvmDefaultVersionTask extends GvmReleaseBaseTask implements ExceptionHandling, ReleaseApiAccess {

    static final DEFAULT_ENDPOINT = "/default"

    void execute(GvmConfig config) {
        withTry(logger) {
            logger.quiet("Releasing $config.candidate $config.version...")
            def values = [candidate: config.candidate, version: config.version]
            def response = put(restClient, DEFAULT_ENDPOINT, accessToken, values)
            logger.quiet("Response: ${response.statusCode}: ${response.json.message}...")
        }
    }
}
