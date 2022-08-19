package dev.ygordoring.chat.thelog.di

import dev.ygordoring.chat.thelog.business.storage.LocalDataStorage
import dev.ygordoring.chat.thelog.business.repository.*
import dev.ygordoring.chat.thelog.business.storage.StorageContract
import dev.ygordoring.chat.thelog.presentation.viewmodel.*
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import kotlin.coroutines.CoroutineContext

val coroutineModule: Module = module {
    factory<CoroutineContext> { Dispatchers.IO }
}

val localDataStorageModule: Module = module {
    factory<StorageContract> {
        LocalDataStorage(androidContext())
    }
}

val userRepositoryModule: Module = module {
    factory<UserRepositoryContract> {
        UserApiRepository(prefs = get())
    }
}

val postRepositoryModule: Module = module {
    factory<PostRepositoryContract> {
        PostApiRepository(prefs = get())
    }
}

val newsRepositoryModule: Module = module {
    factory<NewsRepositoryContract> {
        NewsApiRepository(newsApiClient = get())
    }
}

val splashViewModelModule: Module = module {
    viewModel {
        SplashViewModel(
            coroutineContext = get(),
            userRepository = get()
        )
    }
}

val loginViewModelModule: Module = module {
    viewModel {
        LoginViewModel(
            coroutineContext = get(),
            userRepository = get()
        )
    }
}

val signUpViewModelModule: Module = module {
    viewModel {
        SignUpViewModel(
            coroutineContext = get(),
            userRepository = get()
        )
    }
}

val mainViewModelModule: Module = module {
    viewModel {
        MainViewModel(
            coroutineContext = get(),
            userRepository = get(),
            postRepository = get()
        )
    }
}

val postViewModelModule: Module = module {
    viewModel {
        PostViewModel(
            coroutineContext = get(),
            userRepository = get(),
            postRepository = get()
        )
    }
}

val newsViewModelModule: Module = module {
    viewModel {
        NewsViewModel(
            coroutineContext = get(),
            newsRepository = get()
        )
    }
}