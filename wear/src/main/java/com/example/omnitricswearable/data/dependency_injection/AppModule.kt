package com.example.omnitricswearable.data.dependency_injection

import android.app.Application
import com.example.omnitricswearable.common.Constants
import com.example.omnitricswearable.data.remote.omnitrics.OmnitricsApi
import com.example.omnitricswearable.data.repository.RoutineRepositoryImpl
import com.example.omnitricswearable.data.sensor.MeasurableSensor
import com.example.omnitricswearable.data.sensor.implementations.HeartRateSensor
import com.example.omnitricswearable.domain.model.repository.RoutineRepository
import com.example.omnitricswearable.domain.notifications.CalendarNotifications
import com.example.omnitricswearable.presentation.screens.routine_details.exercise_session.utils.ExerciseProgressManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideOmnitricsApi(): OmnitricsApi {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OmnitricsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRoutineRepository(api: OmnitricsApi): RoutineRepository{
        return RoutineRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideHeartRateSensor(app: Application): MeasurableSensor {
        return HeartRateSensor(app)
    }

    @Provides
    @Singleton
    fun provideCalendarNotifications(app: Application): CalendarNotifications {
        return CalendarNotifications(app)
    }

    @Provides
    @Singleton
    fun provideExerciseProgressManager(): ExerciseProgressManager{
        return ExerciseProgressManager()
    }
}