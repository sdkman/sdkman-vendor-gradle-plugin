package net.gvmtool.vendors

import net.gvmtool.vendors.model.ApiCredentials
import wslite.http.auth.HTTPBasicAuthorization
import wslite.rest.RESTClient

trait ApiConnectivity {

    RESTClient restClient

    def withConnection(ApiCredentials creds, fun) {
        restClient = prepareClient(creds.apiUrl, creds.clientId, creds.clientSecret)
        fun()
    }

    def prepareClient(String apiBaseUrl, String clientId, String clientSecret) {
        def client = new RESTClient(apiBaseUrl)
        client.httpClient.sslTrustAllCerts = true
        client.authorization = new HTTPBasicAuthorization(clientId, clientSecret)
        client
    }

}
