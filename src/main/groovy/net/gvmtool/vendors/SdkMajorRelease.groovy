package net.gvmtool.vendors

import net.gvmtool.vendors.model.GvmExtension

class SdkMajorRelease extends SdkMinorRelease {

    static final DEFAULT_ENDPOINT = "/default"

    SdkMajorRelease() {
        description = "Convenience task performs a Major Release consisting of Release, Default and Announce combined."
    }

    @Override
    void execute(GvmExtension config) {
        super.execute(config)

        logger.quiet("Defaulting $config.candidate $config.version...")
        def values = [candidate: config.candidate, version: config.version]
        def response = put(restClient, DEFAULT_ENDPOINT, config.consumerKey, config.consumerToken, values)
        logger.quiet("Response: ${response.statusCode}: ${response.contentAsString}...")
    }
}