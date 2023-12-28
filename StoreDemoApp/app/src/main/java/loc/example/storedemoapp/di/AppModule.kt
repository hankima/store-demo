package loc.example.storedemoapp.di

import android.util.Log
import com.google.gson.Gson
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import loc.example.storedemoapp.TAG
import loc.example.storedemoapp.repo.ProductRepository
import loc.example.storedemoapp.repo.ProductRepositoryImpl
import loc.example.storedemoapp.service.StoreService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class AppModule {

    @Singleton
    @Binds
    abstract fun bindProductRepository(impl: ProductRepositoryImpl): ProductRepository

    companion object {
        private const val STORE_API_URL = "https://fakestoreapi.com"

        @Singleton
        @Provides
        fun provideGson() = Gson()

        @Singleton
        @Provides
        fun provideStoreService(client: OkHttpClient) =
            Retrofit.Builder()
                .baseUrl(STORE_API_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(StoreService::class.java)

        @Singleton
        @Provides
        fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor) =
            OkHttpClient.Builder()
                .addInterceptor(interceptor = loggingInterceptor)
                .build()

        @Singleton
        @Provides
        fun provideInterceptor() =
            HttpLoggingInterceptor(logger = object : HttpLoggingInterceptor.Logger {
                override fun log(message: String) {
                    Log.d(TAG, "Http interceptor log: $message")
                }
            }).setLevel(level = HttpLoggingInterceptor.Level.BODY)
    }
}