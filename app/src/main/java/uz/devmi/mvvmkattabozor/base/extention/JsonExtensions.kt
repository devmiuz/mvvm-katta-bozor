package uz.devmi.mvvmkattabozor.base.extention

import kotlinx.serialization.json.Json

@Suppress("EXPERIMENTAL_API_USAGE")
val Json.Default.actual: Json
    get() = lazyJson

private val lazyJson: Json by lazy {
    Json {
        isLenient = true
        ignoreUnknownKeys = true
        prettyPrint = true
        allowSpecialFloatingPointValues = true
        useArrayPolymorphism = true
    }
}