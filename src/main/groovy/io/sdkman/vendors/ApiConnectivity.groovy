package io.sdkman.vendors

import wslite.rest.RESTClient

trait ApiConnectivity {

    RESTClient restClient

    def withConnection(String url, fun) {
        restClient = prepareClient(url)
        fun()
    }

    def prepareClient(String url) {
        def client = new RESTClient(url)
        client.httpClient.sslTrustAllCerts = true
        client
    }

}
