package com.example.twidy.data.api

sealed interface Result<out R>

class Success<out T>(val data: T, val isRemote: Boolean): Result<T>

class Failure(val message: String): Result<Nothing>

object NetworkFailure : Result<Nothing>
