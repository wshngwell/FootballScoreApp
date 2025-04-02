package com.example.footballscoreapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.footballscoreapp.data.local.dbModels.MatchDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface MatchesDao {

    @Query("SELECT * FROM MATCHDBMODEL")
    fun getMatches(): Flow<List<MatchDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMatchToDb(listOfMatchDbModels: List<MatchDbModel>)

    @Query("DELETE FROM matchdbmodel WHERE matchId IN (:listOfMatchId)")
    fun deleteMatchFromDb(listOfMatchId: List<String>)

}