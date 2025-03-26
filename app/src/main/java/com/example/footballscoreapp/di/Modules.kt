package com.example.footballscoreapp.di

import com.example.footballscoreapp.data.remote.ApiFactory
import com.example.footballscoreapp.data.remote.ApiService
import com.example.footballscoreapp.data.remote.repositories.LeaguesWithMatchesRepositoryImpl
import com.example.footballscoreapp.domain.repositories.ILeaguesWithMatchesRepository
import com.example.footballscoreapp.domain.usecases.GetMatchesUseCase
import com.example.footballscoreapp.presentation.leagueScreen.LeaguesViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<ApiService> {
        ApiFactory.getApiService(androidApplication())
    }

    single<ILeaguesWithMatchesRepository> {
        LeaguesWithMatchesRepositoryImpl(
            apiService = get<ApiService>()
        )
    }

    factory<GetMatchesUseCase> {
        GetMatchesUseCase(
            iLeaguesWithMatchesRepository = get<ILeaguesWithMatchesRepository>()
        )
    }
    viewModel<LeaguesViewModel> {
        LeaguesViewModel(
            getMatchesUseCase = get<GetMatchesUseCase>(),
        )
    }
}