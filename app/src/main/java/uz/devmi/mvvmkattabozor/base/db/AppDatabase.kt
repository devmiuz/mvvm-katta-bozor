package uz.devmi.mvvmkattabozor.base.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.devmi.mvvmkattabozor.offer.data.dao.OfferDao
import uz.devmi.mvvmkattabozor.offer.data.dao.OfferImageDao
import uz.devmi.mvvmkattabozor.offer.data.model.ImageEntity
import uz.devmi.mvvmkattabozor.offer.data.model.OfferEntity

@Database(
    entities = [
        OfferEntity::class,
        ImageEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract val offerDao: OfferDao
    abstract val offerImageDao : OfferImageDao

    companion object {
        private const val DATABASE_NAME: String = "kattabozor_database"

        fun create(appContext: Context): AppDatabase =
            Room.databaseBuilder(appContext, AppDatabase::class.java, DATABASE_NAME)
                .addMigrations(*MigrationHolder.migrations)
                .build()
    }

}