package com.example.footballscoreapp.di

import com.example.footballscoreapp.data.local.FavouriteMatchesRepositoryImpl
import com.example.footballscoreapp.data.local.FootballDataBase
import com.example.footballscoreapp.data.local.MatchesDao
import com.example.footballscoreapp.data.remote.ApiFactory
import com.example.footballscoreapp.data.remote.ApiService
import com.example.footballscoreapp.data.remote.repositories.LeaguesWithMatchesRepositoryImpl
import com.example.footballscoreapp.domain.repositories.IFavouriteMatchesRepository
import com.example.footballscoreapp.domain.repositories.ILeaguesWithMatchesRepository
import com.example.footballscoreapp.domain.usecases.allMatchesUseCases.GetMatchesUseCase
import com.example.footballscoreapp.domain.usecases.favouriteMatchesUseCases.AddMatchToFavouriteUseCase
import com.example.footballscoreapp.domain.usecases.favouriteMatchesUseCases.DeleteMatchFromFavouriteUseCase
import com.example.footballscoreapp.domain.usecases.favouriteMatchesUseCases.GetFavouriteMatchesUseCase
import com.example.footballscoreapp.domain.usecases.liveMatchesUseCases.GetLiveMatchesUseCase
import com.example.footballscoreapp.presentation.favouriteMatchesScreen.FavouriteMatchesViewModel
import com.example.footballscoreapp.presentation.leagueScreen.LeaguesViewModel
import com.example.footballscoreapp.presentation.liveMatchesScreen.LiveMatchesViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<ApiService> {
        ApiFactory.getApiService(androidApplication())
    }
    single<MatchesDao> {
        FootballDataBase.getInstance(application = androidApplication()).getMatchesDao()
    }
    single<IFavouriteMatchesRepository> {
        FavouriteMatchesRepositoryImpl(
            matchesDao = get<MatchesDao>()
        )
    }
    single<ILeaguesWithMatchesRepository> {
        LeaguesWithMatchesRepositoryImpl(
            apiService = get<ApiService>()
        )
    }

    factory<GetMatchesUseCase> {
        GetMatchesUseCase(
            iLeaguesWithMatchesRepository = get<ILeaguesWithMatchesRepository>(),
            iFavouriteMatchesRepository = get<IFavouriteMatchesRepository>()
        )
    }
    factory<GetLiveMatchesUseCase> {
        GetLiveMatchesUseCase(
            getMatchesUseCase = get<GetMatchesUseCase>()
        )
    }
    factory<GetFavouriteMatchesUseCase> {
        GetFavouriteMatchesUseCase(
            iFavouriteMatchesRepository = get<IFavouriteMatchesRepository>()
        )
    }
    factory<AddMatchToFavouriteUseCase> {
        AddMatchToFavouriteUseCase(
            iFavouriteMatchesRepository = get<IFavouriteMatchesRepository>()
        )
    }
    factory<DeleteMatchFromFavouriteUseCase> {
        DeleteMatchFromFavouriteUseCase(
            iFavouriteMatchesRepository = get<IFavouriteMatchesRepository>()
        )
    }
    viewModel<LeaguesViewModel> {
        LeaguesViewModel(
            getMatchesUseCase = get<GetMatchesUseCase>(),
            getFavouriteMatchesUseCase = get<GetFavouriteMatchesUseCase>(),
            addMatchToFavouriteUseCase = get<AddMatchToFavouriteUseCase>(),
            deleteMatchFromFavouriteUseCase = get<DeleteMatchFromFavouriteUseCase>()
        )
    }
    viewModel<LiveMatchesViewModel> {
        LiveMatchesViewModel(
            getLiveMatchesUseCase = get<GetLiveMatchesUseCase>(),
            getFavouriteMatchesUseCase = get<GetFavouriteMatchesUseCase>(),
            addMatchToFavouriteUseCase = get<AddMatchToFavouriteUseCase>(),
            deleteMatchFromFavouriteUseCase = get<DeleteMatchFromFavouriteUseCase>()
        )
    }
    viewModel<FavouriteMatchesViewModel> {
        FavouriteMatchesViewModel(
            getFavouriteMatchesUseCase = get<GetFavouriteMatchesUseCase>(),
            addMatchToFavouriteUseCase = get<AddMatchToFavouriteUseCase>(),
            deleteMatchFromFavouriteUseCase = get<DeleteMatchFromFavouriteUseCase>()
        )
    }

}