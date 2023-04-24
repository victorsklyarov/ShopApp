package com.zeroillusion.shopapp.di

import android.app.Application
import androidx.room.Room
import com.zeroillusion.shopapp.core.domain.utils.Constants.BASE_URL
import com.zeroillusion.shopapp.core.data.local.CurrentUserDatabase
import com.zeroillusion.shopapp.core.data.local.UserDatabase
import com.zeroillusion.shopapp.core.data.repository.UserRepositoryImpl
import com.zeroillusion.shopapp.core.domain.repository.UserRepository
import com.zeroillusion.shopapp.core.domain.use_case.*
import com.zeroillusion.shopapp.feature.home.data.local.ProductDatabase
import com.zeroillusion.shopapp.feature.home.data.remote.ProductsApi
import com.zeroillusion.shopapp.feature.home.data.repository.HomeRepositoryImpl
import com.zeroillusion.shopapp.feature.home.domain.repository.HomeRepository
import com.zeroillusion.shopapp.feature.home.domain.use_case.GetProducts
import com.zeroillusion.shopapp.feature.profile.data.repository.ProfileRepositoryImpl
import com.zeroillusion.shopapp.feature.profile.domain.repository.ProfileRepository
import com.zeroillusion.shopapp.feature.profile.domain.use_case.ResetCurrentUser
import com.zeroillusion.shopapp.feature.profile.domain.use_case.UpdateUser
import com.zeroillusion.shopapp.feature.signin.data.repository.SignInRepositoryImpl
import com.zeroillusion.shopapp.feature.signin.domain.repository.SignInRepository
import com.zeroillusion.shopapp.feature.signin.domain.use_case.AddUser
import com.zeroillusion.shopapp.feature.signin.domain.use_case.ValidateEmail
import com.zeroillusion.shopapp.feature.signin.domain.use_case.ValidateName
import com.zeroillusion.shopapp.feature.signin.domain.use_case.ValidatePassword
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ShopModule {

    //core
    @Provides
    @Singleton
    fun provideGetUser(repository: UserRepository): GetUser {
        return GetUser(repository)
    }

    @Provides
    @Singleton
    fun provideGetCurrentUser(repository: UserRepository): GetCurrentUser {
        return GetCurrentUser(repository)
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        db: UserDatabase,
        cudb: CurrentUserDatabase
    ): UserRepository {
        return UserRepositoryImpl(db.dao, cudb.dao)
    }

    @Provides
    @Singleton
    fun provideUserDatabase(app: Application): UserDatabase {
        return Room.databaseBuilder(
            app, UserDatabase::class.java, "user_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideCurrentUserDatabase(app: Application): CurrentUserDatabase {
        return Room.databaseBuilder(
            app, CurrentUserDatabase::class.java, "current_user_db"
        ).build()
    }

    //feature.signin
    @Provides
    @Singleton
    fun provideAddUser(repository: SignInRepository): AddUser {
        return AddUser(repository)
    }

    @Provides
    @Singleton
    fun provideValidateName(): ValidateName {
        return ValidateName()
    }

    @Provides
    @Singleton
    fun provideValidateEmail(repository: SignInRepository): ValidateEmail {
        return ValidateEmail(repository)
    }

    @Provides
    @Singleton
    fun provideValidatePassword(): ValidatePassword {
        return ValidatePassword()
    }

    @Provides
    @Singleton
    fun provideSignInRepository(
        db: UserDatabase,
        cudb: CurrentUserDatabase
    ): SignInRepository {
        return SignInRepositoryImpl(
            db.dao,
            cudb.dao
        )
    }

    //feature.home
    @Provides
    @Singleton
    fun provideGetProducts(repository: HomeRepository): GetProducts {
        return GetProducts(repository)
    }

    @Provides
    @Singleton
    fun provideProductRepository(
        db: ProductDatabase,
        api: ProductsApi
    ): HomeRepository {
        return HomeRepositoryImpl(api, db.dao)
    }

    @Provides
    @Singleton
    fun provideProductDatabase(app: Application): ProductDatabase {
        return Room.databaseBuilder(
            app, ProductDatabase::class.java, "product_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideProductApi(): ProductsApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductsApi::class.java)
    }

    //feature.profile
    @Provides
    @Singleton
    fun provideUpdateUser(repository: ProfileRepository): UpdateUser {
        return UpdateUser(repository)
    }

    @Provides
    @Singleton
    fun provideResetCurrentUser(repository: ProfileRepository): ResetCurrentUser {
        return ResetCurrentUser(repository)
    }

    @Provides
    @Singleton
    fun provideProfileRepository(
        db: UserDatabase,
        cudb: CurrentUserDatabase
    ): ProfileRepository {
        return ProfileRepositoryImpl(
            db.dao,
            cudb.dao
        )
    }
}