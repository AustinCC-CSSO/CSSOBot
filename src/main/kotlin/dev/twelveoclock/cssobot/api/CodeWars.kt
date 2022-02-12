package dev.twelveoclock.cssobot.api

import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class CodeWars(val apiKey: String) {

    fun getLeaderboard(page: Int = 1): String {
        return get("https://www.codewars.com/api/v1/users/leaderboard?page=$page&pageSize=50")
    }

    fun getUser(username: String): String {
        return get("https://www.codewars.com/api/v1/users/$username")
    }

    fun getUserCompleted(username: String): String {
        return get("https://www.codewars.com/api/v1/users/$username/code-challenges/completed")
    }

    fun getUserCompleted(username: String, page: Int = 1): String {
        return get("https://www.codewars.com/api/v1/users/$username/code-challenges/completed?page=$page&pageSize=50")
    }

    fun getUserCompleted(username: String, page: Int = 1, pageSize: Int = 50): String {
        return get("https://www.codewars.com/api/v1/users/$username/code-challenges/completed?page=$page&pageSize=$pageSize")
    }

    fun getUserCompleted(username: String, page: Int = 1, pageSize: Int = 50, language: String): String {
        return get("https://www.codewars.com/api/v1/users/$username/code-challenges/completed?page=$page&pageSize=$pageSize&language=$language")
    }

    fun getUserCompleted(username: String, page: Int = 1, pageSize: Int = 50, language: String, since: String): String {
        return get("https://www.codewars.com/api/v1/users/$username/code-challenges/completed?page=$page&pageSize=$pageSize&language=$language&since=$since")
    }

    fun get(url: String): String {
        return HttpClient.newBuilder().build().send(
            HttpRequest.newBuilder().uri(URI(url)).build(),
            HttpResponse.BodyHandlers.ofString()
        ).body()
    }
}