package com.sixlogs.pt.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sixlogs.pt.model.QazaPrayerModel
import com.sixlogs.pt.storage.entities.QazaPrayerEntities

@Dao
interface QazaPrayerDao{

   // @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Insert()
    fun insertPrayer(vararg data: QazaPrayerEntities)

    @Query("SELECT * FROM qazaPrayers")
    fun getAllQazaPrayer(): List<QazaPrayerModel>

//    @Query("INSERT INTO qazaPrayers VALUES(0,date_,0,0,0,0,0)")
   // @Query("INSER qazaPrayers SET fajar =:Done WHERE  date = :date_ ")

 /*   @Query("INSERT INTO qazaPrayers VALUES(0,0,0,0,0)")
    fun updateFajarOfDate( Done: Boolean?, step: String,  date_: String)*/
/*

    @Query("UPDATE qazaPrayers SET duhar =:Done WHERE  date = :date_ ")
    fun updateDuharOfDate( Done: Boolean?, step: String,  date_: String)

    @Query("UPDATE qazaPrayers SET asar =:Done WHERE  date = :date_ ")
    fun updateAsarOfDate( Done: Boolean?, step: String,  date_: String)

    @Query("UPDATE qazaPrayers SET maghrib =:Done WHERE  date = :date_ ")
    fun updateMaghribOfDate( Done: Boolean?, step: String,  date_: String)

    @Query("UPDATE qazaPrayers SET isha =:Done WHERE  date = :date_ ")
    fun updateIshaOfDate( Done: Boolean?, step: String,  date_: String)
*/

    @Query("DELETE FROM qazaPrayers")
    fun deleteAll()

}