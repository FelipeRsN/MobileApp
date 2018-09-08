package com.arctouch.codechallenge.util

//state and data Resource used on viewmodels
class Resource<T> private constructor(val status: Status, val data: T? = null, val throwable: Throwable? = null) {

    enum class Status {
        SUCCESS, ERROR, LOADING
    }

    companion object {
        fun <T> success(data: T? = null): Resource<T> {
            return Resource(Status.SUCCESS, data)
        }
        fun <T> error(throwable: Throwable? = null): Resource<T> {
            return Resource(Status.ERROR, throwable = throwable)
        }
        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(Status.LOADING, data)
        }
    }
}