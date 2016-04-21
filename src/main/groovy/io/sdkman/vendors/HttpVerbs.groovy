package io.sdkman.vendors

import wslite.rest.RESTClient
import wslite.rest.Response

trait HttpVerbs {

    Response post(RESTClient client, String path, String consumerKey, String consumerToken, Map values) {
        client.post(path: path, headers: headers(consumerKey, consumerToken)) {
            type "application/json"
            json values
        }
    }

    Response put(RESTClient client, String path, String consumerKey, String consumerToken, Map values) {
        client.put(path: path, headers: headers(consumerKey, consumerToken)) {
            type "application/json"
            json values
        }
    }

    def headers(String key, String token) {
        ["consumer_key": key, "consumer_token": token]
    }
}