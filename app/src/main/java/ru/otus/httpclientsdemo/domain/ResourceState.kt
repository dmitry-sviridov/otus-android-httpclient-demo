package ru.otus.httpclientsdemo.domain

data class ResourceState<out T>(
    val status: FetchingStatus,
    val data: T?,
    val message: String?
) {
    companion object {

        fun <T> success(data: T?): ResourceState<T> {
            return ResourceState(FetchingStatus.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): ResourceState<T> {
            return ResourceState(FetchingStatus.ERROR, data, msg)
        }

        fun <T> loading(data: T?): ResourceState<T> {
            return ResourceState(FetchingStatus.LOADING, data, null)
        }

    }

    enum class FetchingStatus {
        LOADING,
        ERROR,
        SUCCESS
    }

}