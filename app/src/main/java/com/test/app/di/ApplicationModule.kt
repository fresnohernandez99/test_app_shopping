package com.test.app.di

import android.content.Context
import com.test.app.utils.imageLoader.ImageLoader
import com.test.login.facebook.FacebookLoginData
import com.test.login.google.GoogleLoginData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    @Singleton
    fun getFacebookLoginData() = FacebookLoginData()

    @Provides
    @Singleton
    fun getGoogleLoginData(@ApplicationContext context: Context) = GoogleLoginData(context)

    @Provides
    @Singleton
    fun getImageLoader(@ApplicationContext context: Context) = ImageLoader(context)

}