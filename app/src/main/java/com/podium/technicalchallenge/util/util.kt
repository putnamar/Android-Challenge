package com.podium.technicalchallenge.util

inline val<reified T> T.TAG: String
    get() = if(T::class.isCompanion) T::class.java.enclosingClass.simpleName
    else T::class.java.simpleName

inline fun<T: Any?> tryOrNull(d: () -> T): T? =
    try { d() }
    catch (e: Exception) { null }

