package com.glitch.rest.web

import org.http4k.core.ContentType
import org.http4k.core.HttpHandler
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.lens.contentType

const val PUBLIC_KEY = """
-----BEGIN PGP PUBLIC KEY BLOCK-----
Comment: User ID:	kueller
Comment: Valid from:	12/3/24 2:26 AM
Comment: Type:	255-bit EdDSA (secret key available)
Comment: Usage:	Signing, Encryption, Certifying User IDs
Comment: Fingerprint:	6069 490A 80E2 591B 4523  A12B 996F 0543 7D4E EA59

mDMEZ05eKRYJKwYBBAHaRw8BAQdAe1nuoPoat7QhlSZ5ERi5OStBI8lgd3ajJQhH
t2qp1XW0B2t1ZWxsZXKIkwQTFgoAOxYhBGBpSQqA4lkbRSOhK5lvBUN9TupZBQJn
Tl4pAhsDBQsJCAcCAiICBhUKCQgLAgQWAgMBAh4HAheAAAoJEJlvBUN9TupZhN4A
/R11oZZaRhKGN5a5+7XOcQcmhjXgZj9GqlonHgvfEHheAP9QGrgvBND1XzMWrb8M
hAQLJcPtlOSqqGecfvnF4vU/A7g4BGdOXikSCisGAQQBl1UBBQEBB0A9+1JD5mR4
4xtBlZhNkxJ2CyHeMXj0TJZjmYwtvzV2GQMBCAeIeAQYFgoAIBYhBGBpSQqA4lkb
RSOhK5lvBUN9TupZBQJnTl4pAhsMAAoJEJlvBUN9TupZ+L4BALdabeGDSOVrLcju
+EfIHPWywG/3NeNrBHKHT9mNnlZ9AQCmSjMh/Wa95S3Yac5oFeYF4xzCLoYdvQzm
EZ7cEWhpAg==
=M9Sv
-----END PGP PUBLIC KEY BLOCK-----
"""

val pgp: HttpHandler = { _ ->
    Response(Status.OK).body(PUBLIC_KEY.trim()).contentType(ContentType.TEXT_PLAIN)
}