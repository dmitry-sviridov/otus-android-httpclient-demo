package ru.otus.httpclientsdemo.model

data class Post(
    val userId: Long,
    val id: Long,
    val title: String,
    val body: String
)