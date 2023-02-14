package uz.devmi.mvvmkattabozor.offer.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.devmi.mvvmkattabozor.offer.data.repository.OfferRepository
import uz.devmi.mvvmkattabozor.offer.data.repository.OfferRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class OfferBinders {

    @Binds
    @Singleton
    abstract fun offerRepository(
        impl: OfferRepositoryImpl
    ): OfferRepository

}