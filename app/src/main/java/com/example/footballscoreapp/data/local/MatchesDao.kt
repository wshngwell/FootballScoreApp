package com.example.footballscoreapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.footballscoreapp.data.local.dbModels.MatchDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface MatchesDao {

    @Query("SELECT * FROM MATCHDBMODEL")
    fun getMatches(): Flow<List<MatchDbModel>>

    @Insert
    fun addMatchToDb(matchDbModel: MatchDbModel)

    @Query("DELETE FROM matchdbmodel WHERE matchId =:matchId")
    fun deleteMatchFromDb(matchId: Int)

}