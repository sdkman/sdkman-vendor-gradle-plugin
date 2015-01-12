package net.gvmtool.vendors

import wslite.http.auth.HTTPBasicAuthorization
import wslite.rest.RESTClient

trait ApiConnectivity {

    RESTClient restClient

    def withConnection(GvmExtension config, Closure call) {
        restClient = prepareClient(config.apiUrl, config.clientId, config.clientSecret)
        call()
    }

    def prepareClient(String apiBaseUrl, String clientId, String clientSecret) {
        def client = new RESTClient(apiBaseUrl)
        client.httpClient.sslTrustAllCerts = true
        client.authorization = new HTTPBasicAuthorization(clientId, clientSecret)
        client
    }

}
