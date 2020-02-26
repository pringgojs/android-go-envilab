package com.envilabindonesia.android.di.module

import com.envilabindonesia.android.api.ApiServices
import com.envilabindonesia.android.base.interceptors.AuthInterceptor
import com.envilabindonesia.android.base.interceptors.PermissionInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import javax.inject.Singleton

/**
 * Created by galihadityo on 2019-03-02.
 */

@Module
class NetworkModule {

    @Singleton
    @Provides
    internal fun provideService(retrofit: Retrofit): ApiServices = retrofit.create(ApiServices::class.java)

    @Singleton
    @Provides
    internal fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://www.envilab-id.com/envilabapi/v1/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    internal fun okHttpClient(loggingInterceptor: HttpLoggingInterceptor,
                              authInterceptor: AuthInterceptor,
                              permissionInterceptor: PermissionInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .protocols(Arrays.asList(Protocol.HTTP_1_1))
            .addInterceptor(authInterceptor)
            .addInterceptor(permissionInterceptor)
            .addInterceptor(loggingInterceptor)
            .retryOnConnectionFailure(true)
            .build()
    }

    @Singleton
    @Provides
    internal fun loggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Singleton
    @Provides
    internal fun authInterceptor(): AuthInterceptor {
        return AuthInterceptor()
    }

    @Singleton
    @Provides
    internal fun permissionInterceptor(): PermissionInterceptor {
        return PermissionInterceptor()
    }

}