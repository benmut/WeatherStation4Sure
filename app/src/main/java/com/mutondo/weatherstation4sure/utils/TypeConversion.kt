package com.mutondo.weatherstation4sure.utils

val Int?.orZero
    get() = this ?: 0

val Long?.orZero
    get() = this ?: 0L

val Float?.orZero: Float
    get() = this ?: 0f
