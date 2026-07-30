package com.glitch.security

import org.springframework.security.crypto.bcrypt.BCrypt

fun hashPassword(password: String, salt: String): String = BCrypt.hashpw(password, salt)

