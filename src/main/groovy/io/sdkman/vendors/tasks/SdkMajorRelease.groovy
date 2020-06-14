package io.sdkman.vendors.tasks

class SdkMajorRelease extends SdkMinorRelease {

    static final DEFAULT_ENDPOINT = "/default"

    SdkMajorRelease() {
        description = "Convenience task performs a Major Release consisting of Release, Default and Announce combined on SDKMAN!"
    }

    @Override
    void executeTask() {
        super.executeTask()

        withConnection(apiUrl, DEFAULT_ENDPOINT, consumerKey, consumerToken) { conn ->
            logger.quiet("Defaulting $candidate $version...")
            def values = [candidate: candidate, version: version]
            def response = put(conn, values)
            logger.quiet("Response: ${response.responseCode}: ${response.responseMessage}...")
        }
    }
}