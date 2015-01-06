package net.gvmtool.vendors

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import wslite.http.auth.HTTPBasicAuthorization
import wslite.rest.RESTClient

abstract class GvmReleaseBaseTask extends DefaultTask implements ConfigValidation {

    protected RESTClient restClient
    protected String accessToken

    @TaskAction
    void start() {
        GvmConfig gvmConfig = project.gvm
        withValid(gvmConfig) {
            restClient = prepareClient(
                    gvmConfig.apiBaseUrl,
                    gvmConfig.releaseClientId,
                    gvmConfig.releaseClientSecret)

            logger.quiet("Getting access token...")
            accessToken = oauthAccessToken(
                    restClient,
                    gvmConfig.username,
                    gvmConfig.password,
                    gvmConfig.releaseClientId,
                    gvmConfig.releaseClientSecret)

            execute(gvmConfig)
        }
    }

    private static prepareClient(String apiBaseUrl, String clientId, String clientSecret) {
        def client = new RESTClient(apiBaseUrl)
        client.httpClient.sslTrustAllCerts = true
        client.authorization = new HTTPBasicAuthorization(clientId, clientSecret)
        client
    }

    private oauthAccessToken(RESTClient client, String username, String password, String clientId, String clientSecret) {
        client.post(path: "/oauth/token") {
            type "application/x-www-form-urlencoded"
            text "password=${password}&username=${username}&grant_type=password&scope=read%20write&client_secret=${clientSecret}&client_id=${clientId}"
        }.json.access_token
    }

    abstract void execute(GvmConfig config)

}
