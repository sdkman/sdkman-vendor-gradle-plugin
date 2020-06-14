package io.sdkman.vendors

trait ApiConnectivity {
    def withConnection(String url, String path, String key, String token, fun) {
        HttpURLConnection connection = new URL(url + path).openConnection() as HttpURLConnection
        connection.setRequestProperty("Consumer-Key", key)
        connection.setRequestProperty("Consumer-Token", token)
        connection.setRequestProperty("Content-Type", "application/json")
        connection.setRequestProperty("Accepts", "application/json")
        connection.doOutput = true

        fun(connection)

        connection.disconnect()
    }
}
