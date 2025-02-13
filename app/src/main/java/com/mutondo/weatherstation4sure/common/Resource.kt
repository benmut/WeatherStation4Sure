package com.mutondo.weatherstation4sure.common

sealed class Resource<T>(val data: T?, val message: String? = null) {
    class Success<T>(data: T?) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
}

enum class ResourceStatus {
    SUCCESS,
    ERROR,
    FAILED
}
