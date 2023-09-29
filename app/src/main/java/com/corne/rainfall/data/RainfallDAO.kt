package com.corne.rainfall.data

import android.location.Location
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.corne.rainfall.data.model.LocationModel
import com.corne.rainfall.data.model.RainfallEntryModel
import com.corne.rainfall.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import org.xml.sax.helpers.LocatorImpl

@Dao
interface RainfallDAO {

    @Query("SELECT * FROM LocationModel")
    fun getAllLocations(): Flow<List<LocationModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend  fun insert( vararg location: LocationModel)

    @Delete
    fun delete(location: LocationModel)

}