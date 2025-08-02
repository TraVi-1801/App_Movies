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
        .flatMap { (key, values) ->
            values.map { value -> "--header '$key: $value'" }
        }

    val body = when (val content = request.body) {
        is OutgoingContent.ByteArrayContent -> {
            val raw = content.bytes().decodeToString()
            if (raw.isNotBlank()) listOf("--data '$raw'") else emptyList()
        }
        is OutgoingContent.NoContent -> emptyList()
        else -> listOf("--data '[Body of type ${content::class.simpleName} not logged]'")
    }

    val parts = buildList {
        add("curl --request $method")
        add("--url '$url'")
        addAll(headers)
        addAll(body)
    }

    // Join with \ continuation for pretty multiline output
    return parts.joinToString(" \\\n     ")
}