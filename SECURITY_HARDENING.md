# Spotilol 1.1.3 security hardening

Implemented source changes:
- Native `AndBridge.nFetch` now accepts only HTTPS Spotify hosts, blocks unsupported methods, re-validates redirects, avoids returning `Set-Cookie`, and caps response bodies at 4 MiB.
- Main WebView top-level navigation is restricted to HTTPS Spotify and OAuth origins.
- The popup/child WebView uses the same origin policy and does not expose `AndBridge`.
- The local MITM proxy remains loopback-only and now rejects non-HTTPS CONNECT targets and hosts outside the Spotify/OAuth allowlist.
- Profile and proxy secret storage no longer falls back to plaintext SharedPreferences.
- Bouncy Castle dependencies were migrated from the obsolete `jdk15on` 1.70 artifacts to `jdk18on` 1.86.

Build note: the bundled Gradle wrapper could not download its distribution in the isolated environment, so a real Gradle build could not be completed here.
