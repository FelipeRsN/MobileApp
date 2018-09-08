package com.arctouch.codechallenge.util

//SingleLiveEvent used to send one value and invalidate the field to avoid observe the same value
//class copied from medium and adapted for my use
//https://medium.com/androiddevelopers/livedata-with-snackbar-navigation-and-other-events-the-singleliveevent-case-ac2622673150

open class SingleLiveEvent<out T>(private val content: T) {

    var hasBeenHandled = false
        private set

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    fun peekContent(): T = content
}