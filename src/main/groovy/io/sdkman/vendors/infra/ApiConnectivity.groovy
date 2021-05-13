package io.sdkman.vendors.infra

trait ApiConnectivity {
    ApiResponse withConnection(String url, String path, String key, String token, fun) {
        HttpURLConnection connection = new URL(url + path).openConnection() as HttpURLConnection
        connection.setRequestProperty("Consumer-Key", key)
        connection.setRequestProperty("Consumer-Token", token)
        connection.setRequestProperty("Content-Type", "application/json")
        connection.setRequestProperty("Accepts", "application/json")
        connection.doOutput = true

        HttpURLConnection response = fun(connection)
        def apiResponse = new ApiResponse(
                code: response.responseCode,
                message: response.responseMessage
        )

        connection.disconnect()
        apiResponse
    }
}
