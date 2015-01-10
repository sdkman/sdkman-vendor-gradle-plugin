package net.gvmtool.vendors

import wslite.http.auth.HTTPBasicAuthorization
import wslite.rest.RESTClient

trait OAuthConnectivity {

    RESTClient restClient
    String accessToken
    
    def withAuth(GvmConfig config, Closure call) {
        restClient = prepareClient(
                config.apiBaseUrl,
                config.releaseClientId,
                config.releaseClientSecret)

        //logger.quiet("Getting access token...")
        accessToken = oauthAccessToken(
                restClient,
                config.username,
                config.password,
                config.releaseClientId,
                config.releaseClientSecret)
        call()
    }

    def prepareClient(String apiBaseUrl, String clientId, String clientSecret) {
        def client = new RESTClient(apiBaseUrl)
        client.httpClient.sslTrustAllCerts = true
        client.authorization = new HTTPBasicAuthorization(clientId, clientSecret)
        client
    }

    def oauthAccessToken(RESTClient client, String username, String password, String clientId, String clientSecret) {
        client.post(path: "/oauth/token") {
            type "application/x-www-form-urlencoded"
            text "password=${password}&username=${username}&grant_type=password&scope=read%20write&client_secret=${clientSecret}&client_id=${clientId}"
        }.json.access_token
    }

}