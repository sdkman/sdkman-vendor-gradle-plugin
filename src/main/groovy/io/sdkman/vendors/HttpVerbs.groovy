package io.sdkman.vendors


import groovy.json.JsonOutput

trait HttpVerbs {

    HttpURLConnection post(HttpURLConnection connection, Map values) {
        call(connection, 'POST', values)
    }

    HttpURLConnection put(HttpURLConnection connection, Map values) {
        call(connection, 'PUT', values)
    }

    private call(HttpURLConnection connection, String method, Map values) {
        connection.requestMethod = method
        connection.outputStream.withWriter { writer ->
            writer << JsonOutput.toJson(values)
        }
        connection
    }
}