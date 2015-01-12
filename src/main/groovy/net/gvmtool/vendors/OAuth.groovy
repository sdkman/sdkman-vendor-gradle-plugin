package net.gvmtool.vendors

import wslite.rest.RESTClient

trait OAuth {

    String accessToken
    
    def withAuth(RESTClient restClient, GvmExtension config, Closure call) {
        accessToken = oauthAccessToken(
                restClient,
                config.username,
                config.password,
                config.clientId,
                config.clientSecret)
        call()
    }

    def oauthAccessToken(RESTClient client, String username, String password, String clientId, String clientSecret) {
        client.post(path: "/oauth/token") {
            type "application/x-www-form-urlencoded"
            text "password=${password}&username=${username}&grant_type=password&scope=read%20write&client_secret=${clientSecret}&client_id=${clientId}"
        }.json.access_token
    }

}