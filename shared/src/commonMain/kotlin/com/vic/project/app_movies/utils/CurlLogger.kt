package com.vic.project.app_movies.utils

import com.vic.project.app_movies.utils.StringUtils.maskToken
import io.ktor.client.plugins.api.createClientPlugin
import io.ktor.client.request.*
import io.ktor.http.content.OutgoingContent
import io.ktor.util.*

val CurlLogger = createClientPlugin("CurlLogger") {
    onRequest { request, _ ->
        val curl = buildCurlCommand(request).maskToken()
        println("CURL: $curl")
    }
}

private fun buildCurlCommand(request: HttpRequestBuilder): String {
    val method = request.method.value
    val url = request.url.buildString()
    val headers = request.headers.entries()
        .joinToString(" ") { (key, values) ->
            values.joinToString(" ") { value -> "-H \"$key: $value\"" }
        }

    val body = when (val content = request.body) {
        is OutgoingContent.ByteArrayContent -> {
            val raw = content.bytes().decodeToString()
            if (raw.isNotBlank()) "--data '${raw}'" else ""
        }
        is OutgoingContent.NoContent -> ""
        else -> "--data '[Body of type ${content::class.simpleName} not logged]'"
    }


    return "curl -X $method $headers $body \"$url\""
}