

//package com.ghamdandev.demo.data.di
//
//import com.google.firebase.firestore.FirebaseFirestore
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.components.SingletonComponent
//import javax.inject.Singleton
//@Module
//@InstallIn(SingletonComponent::class)
//object AppModule {
//
//    @Provides
//    @Singleton
//    fun provideFirestore(): FirebaseFirestore {
//        return FirebaseFirestore.getInstance()
//    }
//}
////@Module
////@InstallIn(SingletonComponent::class)
////object AppModule {
////
////    @Provides
////    @Singleton
////    fun provideFirestore(): FirebaseFirestore {
////        return FirebaseFirestore.getInstance()
////    }
////
////    @Provides
////    @Singleton
////    fun provideFirebaseService(firestore: FirebaseFirestore): FirebaseService {
////        return FirebaseService(firestore)
////    }
////}