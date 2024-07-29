package com.ecom.ecommerceapp

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [ProductEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "ecommerce_database"
                )
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            // Insert initial data
                            populateInitialData(context)
                        }

                        override fun onOpen(db: SupportSQLiteDatabase) {
                            super.onOpen(db)
                            // Insert initial data if it doesn't exist
                            CoroutineScope(Dispatchers.IO).launch {
                                val productDao = getDatabase(context).productDao()
                                val existingProducts = productDao.getAllProducts()
                                if (existingProducts.isEmpty()) {
                                    populateInitialData(context)
                                }
                            }
                        }
                    })
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private fun populateInitialData(context: Context) {
            val productDao = getDatabase(context).productDao()
            CoroutineScope(Dispatchers.IO).launch {
                // Insert initial data
                productDao.insertProduct(ProductEntity(
                    name = "Samsung Galaxy S21",
                    description = "Experience the new generation of smartphones with the Samsung Galaxy S21.",
                    imageUrl = "https://via.placeholder.com/150",
                    price = 799.99,
                    category = "Electronics"
                ))
                productDao.insertProduct(ProductEntity(
                    name = "Nike Air Max 270",
                    description = "Step into comfort with the Nike Air Max 270 shoes.",
                    imageUrl = "https://via.placeholder.com/150",
                    price = 150.00,
                    category = "Footwear"
                ))
                productDao.insertProduct(ProductEntity(
                    name = "Sony WH-1000XM4",
                    description = "Experience the best noise-canceling headphones with the Sony WH-1000XM4.",
                    imageUrl = "https://via.placeholder.com/150",
                    price = 349.99,
                    category = "Electronics"
                ))
                productDao.insertProduct(ProductEntity(
                    name = "Apple MacBook Pro",
                    description = "The new Apple MacBook Pro with M1 chip delivers unprecedented performance.",
                    imageUrl = "https://via.placeholder.com/150",
                    price = 1299.99,
                    category = "Computers"
                ))
                productDao.insertProduct(ProductEntity(
                    name = "Canon EOS R5",
                    description = "Capture stunning photos and videos with the Canon EOS R5 mirrorless camera.",
                    imageUrl = "https://via.placeholder.com/150",
                    price = 3899.00,
                    category = "Cameras"
                ))
            }
        }
    }
}

