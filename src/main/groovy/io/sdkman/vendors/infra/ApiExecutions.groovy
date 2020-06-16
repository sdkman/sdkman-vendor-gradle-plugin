package io.sdkman.vendors.infra

import static io.sdkman.vendors.infra.ApiEndpoints.*

trait ApiExecutions implements ApiConnectivity, HttpVerbs {

    ApiResponse execAnnounce(String host, String candidate, String version, String hashtag, String key, String token) {
        withConnection(host, ANNOUNCE_ENDPOINT, key, token) { conn ->
            logger.quiet("Announcing for $candidate $version...")
            def values = [candidate: candidate, version: version, hashtag: hashtag]
            def response = post(conn, values)
            logger.quiet("Response: ${response.responseCode}: ${response.responseMessage}...")
            response
        }
    }

    ApiResponse execDefault(String host, String candidate, String version, String key, String token) {
        withConnection(host, DEFAULT_ENDPOINT, key, token) { conn ->
            logger.quiet("Releasing $candidate $version as candidate default...")
            def values = [candidate: candidate, version: version]
            def response = put(conn, values)
            logger.quiet("Response: ${response.responseCode}: ${response.responseMessage}...")
            response
        }
    }

    ApiResponse execRelease(String host, String candidate, String version, String platform, String downloadUrl, String key, String token) {
        withConnection(host, RELEASE_ENDPOINT, key, token) { conn ->
            logger.quiet("Releasing $candidate $version for $platform...")
            def values = [candidate: candidate, version: version, platform: platform, url: downloadUrl]
            def response = post(conn, values)
            logger.quiet("Response: ${response.responseCode}: ${response.responseMessage}...")
            response
        }
    }
}