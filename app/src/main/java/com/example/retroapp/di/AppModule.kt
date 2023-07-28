package com.example.retroapp.di

import android.content.Context
import com.example.retroapp.data.AuthRepository
import com.example.retroapp.data.AuthRepositoryImpl
import com.example.retroapp.data.StorageRepository
import com.example.retroapp.data.StorageRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository = impl

    @Provides
    fun provideFirebaseFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Singleton
    @Provides
    fun provideStorageRepository(
        firebaseFirestore: FirebaseFirestore,
        auth: FirebaseAuth,
        firebaseStorage: FirebaseStorage,
        @ApplicationContext context: Context
    ): StorageRepository = StorageRepositoryImpl(firebaseFirestore, auth, firebaseStorage, context)

    @Provides
    fun provideFirebaseStorage(): FirebaseStorage = FirebaseStorage.getInstance()


}