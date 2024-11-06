package com.example.lamdatec.di

import com.example.lamdatec.features.Graficos.MQ2.data.SensorMQ2Repository
import com.example.lamdatec.features.Graficos.MQ2.data.SensorMQ2RepositoryImp
import com.example.lamdatec.features.Graficos.MQ7.data.SensorMQ7Repository
import com.example.lamdatec.features.Graficos.MQ7.data.SensorMQ7RepositoryImp
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideFirebaseDatabase(): FirebaseDatabase {
        return FirebaseDatabase.getInstance()
    }

    @Singleton
    @Provides
    fun provideSensorMQ2Repository(database: FirebaseDatabase): SensorMQ2Repository {
        return SensorMQ2RepositoryImp(database)
    }

    @Singleton
    @Provides
    fun provideSensorMQ7Repository(database: FirebaseDatabase): SensorMQ7Repository {
        return SensorMQ7RepositoryImp(database)
    }
}