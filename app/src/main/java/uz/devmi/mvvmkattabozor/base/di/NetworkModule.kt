package uz.devmi.mvvmkattabozor.base.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import uz.devmi.mvvmkattabozor.BuildConfig
import uz.devmi.mvvmkattabozor.base.*
import uz.devmi.mvvmkattabozor.base.extention.actual
import uz.devmi.mvvmkattabozor.base.network.CustomConvertersFactory
import uz.devmi.mvvmkattabozor.base.network.FlowCallAdapterFactory
import uz.devmi.mvvmkattabozor.base.network.HttpRequestInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (!BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.NONE
            } else {
                HttpLoggingInterceptor.Level.BODY
            }
        }
    }

    @Provides
    @Singleton
    fun provideHttpRequestInterceptor(): HttpRequestInterceptor {
        return HttpRequestInterceptor()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        httpRequestInterceptor: HttpRequestInterceptor
    ): OkHttpClient {
        val builder = OkHttpClient.Builder().apply {
            addInterceptor(httpLoggingInterceptor)
            if (BuildConfig.DEBUG) {
                addInterceptor(httpRequestInterceptor)
            }
            connectTimeout(30L, TimeUnit.SECONDS)
            readTimeout(30L, TimeUnit.SECONDS)
            writeTimeout(30L, TimeUnit.SECONDS)
            followSslRedirects(true)
            followRedirects(true)
            retryOnConnectionFailure(true)
        }

        return builder.build()
    }

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.BASE_URL)
            .addCallAdapterFactory(FlowCallAdapterFactory)
            .addConverterFactory(CustomConvertersFactory)
            .addConverterFactory(Json.Default.actual.asConverterFactory("application/json; charset=utf-8".toMediaType()))
            .build()
    }

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @Singleton
    fun JsonConverterFactory(
    ): Converter.Factory {
        val lazyJson: Json by lazy {
            Json {
                isLenient = true
                ignoreUnknownKeys = true
                prettyPrint = true
                allowSpecialFloatingPointValues = true
                useArrayPolymorphism = true
            }
        }

        return lazyJson.asConverterFactory("application/json; charset=utf-8".toMediaType())
    }
}