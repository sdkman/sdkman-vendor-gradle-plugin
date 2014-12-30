package net.gvmtool.vendors

import wslite.rest.RESTClient
import wslite.rest.Response

trait ReleaseApiAccess {

    Response post(RESTClient client, String path, String token, Map values) {
        client.post(path: path, headers: ["Authorization": "Bearer $token"]) {
            type "application/json"
            json values
        }
    }

    Response put(RESTClient client, String path, String token, Map values) {
        client.put(path: "/default", headers: ["Authorization": "Bearer $token"]) {
            type "application/json"
            json values
        }
    }
}