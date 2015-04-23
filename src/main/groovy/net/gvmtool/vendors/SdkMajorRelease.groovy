package net.gvmtool.vendors

class SdkMajorRelease extends SdkMinorRelease {

    static final DEFAULT_ENDPOINT = "/default"

    SdkMajorRelease() {
        description = "Convenience task performs a Major Release consisting of Release, Default and Announce combined."
    }

    @Override
    void executeTask() {
        super.executeTask()

        logger.quiet("Defaulting $candidate $version...")
        def values = [candidate: candidate, version: version]
        def response = put(restClient, DEFAULT_ENDPOINT, consumerKey, consumerToken, values)
        logger.quiet("Response: ${response.statusCode}: ${response.contentAsString}...")
    }
}