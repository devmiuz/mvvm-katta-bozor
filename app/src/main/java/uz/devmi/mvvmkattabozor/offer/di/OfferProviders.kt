package uz.devmi.mvvmkattabozor.offer.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import uz.devmi.mvvmkattabozor.base.db.AppDatabase
import uz.devmi.mvvmkattabozor.offer.data.dao.OfferDao
import uz.devmi.mvvmkattabozor.offer.data.rest_service.OfferRestService
import uz.devmi.mvvmkattabozor.offer.data.dao.OfferImageDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class OfferProviders {

    @Provides
    @Singleton
    fun provideOffersRestService(
        retrofit: Retrofit
    ): OfferRestService {
        return retrofit.create(OfferRestService::class.java)
    }

    @Provides
    @Singleton
    fun provideOfferDao(
        appDatabase: AppDatabase
    ): OfferDao =
        appDatabase.offerDao

    @Provides
    @Singleton
    fun provideOfferImageDao(
        appDatabase: AppDatabase
    ): OfferImageDao =
        appDatabase.offerImageDao
}