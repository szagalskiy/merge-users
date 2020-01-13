package com.development.merge

/**
 * Entity, which contains information about user
 */
data class User(
        val name : String,
        val mails : HashSet<String>,
        var processed : Boolean = false
)