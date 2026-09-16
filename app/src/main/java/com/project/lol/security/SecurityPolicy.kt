package com.project.lol.security

import android.net.Uri

/** Central allowlists used by the WebView, native bridge and local MITM proxy. */
object SecurityPolicy {
    private val spotifyHosts = setOf(
        "spotify.com",
        "scdn.co"
    )

    private val oauthHosts = setOf(
        "google.com",
        "youtube.com",
        "facebook.com",
        "apple.com"
    )

    fun isSpotifyHost(host: String?): Boolean = isHostIn(host, spotifyHosts)

    fun isOAuthHost(host: String?): Boolean = isHostIn(host, oauthHosts)

    fun isTrustedWebHost(host: String?): Boolean = isSpotifyHost(host) || isOAuthHost(host)

    fun isAllowedNativeFetchUrl(rawUrl: String): Boolean {
        val uri = runCatching { Uri.parse(rawUrl) }.getOrNull() ?: return false
        if (uri.scheme?.lowercase() != "https") return false
        return isSpotifyHost(uri.host)
    }

    fun isAllowedProxyHost(host: String?): Boolean = isTrustedWebHost(host)

    private fun isHostIn(host: String?, roots: Set<String>): Boolean {
        val normalized = host?.trim()?.lowercase()?.trimEnd('.') ?: return false
        return roots.any { normalized == it || normalized.endsWith(".$it") }
    }
}
