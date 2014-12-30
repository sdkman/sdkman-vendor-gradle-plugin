package net.gvmtool.vendors

import wslite.rest.RESTClient
import wslite.rest.Response

trait ReleaseApiAccess {

    Response postRelease(RESTClient client, String token, String candidate, String version, String url) {
        client.post(path: "/release", headers: ["Authorization": "Bearer $token"]) {
            type "application/json"
            json candidate: candidate, version: version, url: url
        }
    }

    Response put(RESTClient client, String path, String token, Map values) {
        client.put(path: "/default", headers: ["Authorization": "Bearer $token"]) {
            type "application/json"
            json values
        }
    }
}