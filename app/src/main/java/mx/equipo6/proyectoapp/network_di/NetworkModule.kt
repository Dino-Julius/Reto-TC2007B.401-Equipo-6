package mx.equipo6.proyectoapp.network_di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mx.equipo6.proyectoapp.api.ApiService
import mx.equipo6.proyectoapp.include.Constants.Companion.API_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * This module provides the necessary dependencies for the network layer of the app using Dagger Hilt.
 * The module provides the base URL for the API, an instance of [HttpLoggingInterceptor] for logging HTTP requests and responses,
 * an instance of [OkHttpClient] with custom configurations, a Converter.Factory for [Retrofit] to use Gson for JSON conversion,
 * an instance of [Retrofit] configured with the base URL, [OkHttpClient], and [Converter.Factory], and an instance of [ApiService] created by [Retrofit].
 * @author Dinesh Chavan [@Dinesh2510 on Github]
 */
@Module
// Specify that this module will be installed in the SingletonComponent
@InstallIn(SingletonComponent::class)
object NetworkModule {

    // Provide the base URL for the API
    @Provides
    fun provdesBaseUrl(): String {
        return API_URL
    }

    // Provide an instance of HttpLoggingInterceptor for logging HTTP requests and responses
    @Provides
    fun providesLogginInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    // Provide an instance of OkHttpClient with custom configurations
    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()

        // Set various timeouts for the OkHttpClient
        okHttpClient.callTimeout(4, TimeUnit.SECONDS)
        okHttpClient.connectTimeout(40, TimeUnit.SECONDS)
        okHttpClient.readTimeout(40, TimeUnit.SECONDS)
        okHttpClient.writeTimeout(40, TimeUnit.SECONDS)

        // Add the logging interceptor to the OkHttpClient
        okHttpClient.addInterceptor(loggingInterceptor)

        // Build and return the OkHttpClient instance
        okHttpClient.build()
        return okHttpClient.build()
    }

    // Provide a Converter.Factory for Retrofit to use Gson for JSON conversion
    @Provides
    fun provedeConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    // Provide an instance of Retrofit configured with the base URL, OkHttpClient, and Converter.Factory
    @Provides
    fun provideRetrofitClient(okHttpClient: OkHttpClient, baseUrl: String, converterFactory: Converter.Factory): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }

    // Provide an instance of ApiService created by Retrofit
    @Provides
    fun provideApiServide(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}