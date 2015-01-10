package net.gvmtool.vendors

class GvmDefaultVersionTask extends GvmVendorBaseTask {

    static final DEFAULT_ENDPOINT = "/default"

    GvmDefaultVersionTask() {
        description = "Make an existing Candidate Version the new Default on GVM."
    }

    void execute(GvmConfig config) {
        logger.quiet("Releasing $config.candidate $config.version...")
        def values = [candidate: config.candidate, version: config.version]
        def response = put(restClient, DEFAULT_ENDPOINT, accessToken, values)
        logger.quiet("Response: ${response.statusCode}: ${response.json.message}...")
    }
}
