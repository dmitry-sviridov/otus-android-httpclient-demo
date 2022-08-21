package ru.otus.httpclientsdemo.model

import kotlinx.serialization.Serializable

@Serializable
class Post(
    val userId: Long,
    val id: Long,
    val title: String,
    val body: String
)