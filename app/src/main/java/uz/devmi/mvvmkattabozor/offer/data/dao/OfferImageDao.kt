package uz.devmi.mvvmkattabozor.offer.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import uz.devmi.mvvmkattabozor.offer.data.model.ImageEntity

@Dao
interface OfferImageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(imageEntity: ImageEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(imageEntities: List<ImageEntity>)
}