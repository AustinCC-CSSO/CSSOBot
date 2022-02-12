package dev.twelveoclock.cssobot.api

import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class CodeWars(val apiKey: String) {
    fun getLeaderboard(page: Int = 1): String {
        val url = "https://www.codewars.com/api/v1/users/leaderboard?page=$page&pageSize=50"
        return get(url)
    }

    fun getUser(username: String): String {
        val url = "https://www.codewars.com/api/v1/users/$username"
        return get(url)
    }

    fun getUserCompleted(username: String): String {
        val url = "https://www.codewars.com/api/v1/users/$username/code-challenges/completed"
        return get(url)
    }

    fun getUserCompleted(username: String, page: Int = 1): String {
        val url = "https://www.codewars.com/api/v1/users/$username/code-challenges/completed?page=$page&pageSize=50"
        return get(url)
    }

    fun getUserCompleted(username: String, page: Int = 1, pageSize: Int = 50): String {
        val url =
            "https://www.codewars.com/api/v1/users/$username/code-challenges/completed?page=$page&pageSize=$pageSize"
        return get(url)
    }

    fun getUserCompleted(username: String, page: Int = 1, pageSize: Int = 50, language: String): String {
        val url =
            "https://www.codewars.com/api/v1/users/$username/code-challenges/completed?page=$page&pageSize=$pageSize&language=$language"
        return get(url)
    }

    fun getUserCompleted(username: String, page: Int = 1, pageSize: Int = 50, language: String, since: String): String {
        val url =
            "https://www.codewars.com/api/v1/users/$username/code-challenges/completed?page=$page&pageSize=$pageSize&language=$language&since=$since"
        return get(url)
    }

    fun get(url: String): String {
        val client = HttpClient.newBuilder().build()
        val request = HttpRequest.newBuilder()
            .uri(URI(url))
            .build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        return response.body()
    }
}