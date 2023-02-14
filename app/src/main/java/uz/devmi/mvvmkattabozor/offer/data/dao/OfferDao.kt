package uz.devmi.mvvmkattabozor.offer.data.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import uz.devmi.mvvmkattabozor.offer.data.model.OfferEntity
import uz.devmi.mvvmkattabozor.offer.data.model.OfferRelation

@Dao
interface OfferDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(offerEntity: OfferEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(offerEntities: List<OfferEntity>)

    @Transaction
    @Query("SELECT * FROM offers")
    fun getAll(): Flow<List<OfferRelation>>

    @Transaction
    @Query("SELECT * FROM offers WHERE id = :id")
    fun getById(id: Int): Flow<OfferRelation>

}