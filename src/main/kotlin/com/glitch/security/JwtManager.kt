package com.glitch.security

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.DecodedJWT
import com.glitch.config.appConfig
import java.util.Date


internal class JwtConfig {
    companion object {
        val issuer = "glitch.paris"
        val algorithm: Algorithm = Algorithm.HMAC256(appConfig.jwtSecret)
        val verifier: JWTVerifier = JWT.require(algorithm).withIssuer(issuer).build()
    }
}


fun createJwt(subject: String, username: String, ttl: Int = 60): String {
    val ttlMs: Long = ttl.toLong() * 60 * 1000
    return JWT.create()
        .withSubject(subject)
        .withClaim("user", username)
        .withIssuedAt(Date())
        .withIssuer(JwtConfig.issuer)
        .withExpiresAt(Date(System.currentTimeMillis() + ttlMs))
        .sign(JwtConfig.algorithm)
}


fun verifyJwt(jwt: String): DecodedJWT? =
    try {
        JwtConfig.verifier.verify(jwt)
    } catch (e: JWTVerificationException) {
        null
    }