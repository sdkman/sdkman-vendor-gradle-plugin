package io.sdkman.vendors.infra

trait ApiExecutions implements ApiConnectivity, HttpVerbs {

    def execAnnounce(String host, String path, String candidate, String version, String hashtag, String key, String token) {
        withConnection(host, path, key, token) { conn ->
            logger.quiet("Announcing for $candidate $version...")
            def values = [candidate: candidate, version: version, hashtag: hashtag ?: candidate]
            def response = post(conn, values)
            logger.quiet("Response: ${response.responseCode}: ${response.responseMessage}...")
        }
    }

    def execDefault(String host, String path, String candidate, String version, String key, String token) {
        withConnection(host, path, key, token) { conn ->
            logger.quiet("Releasing $candidate $version as candidate default...")
            def values = [candidate: candidate, version: version]
            def response = put(conn, values)
            logger.quiet("Response: ${response.responseCode}: ${response.responseMessage}...")
        }
    }

}