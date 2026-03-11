package com.qbs.laafresh.data.dbhelper

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.qbs.laafresh.data.dbhelper.table.*

@Database(
    entities = [(MyCartEntity::class), (DeliveryAddressEntity::class), (LoginEntity::class), (ProductEntity::class), (ProductCategoryEntity::class), (ProductPriceEntity::class), (SubCategoriesItemEntity::class)],
    version = 3,
    exportSchema = false
)
abstract class LaaFreshDB : RoomDatabase() {

    abstract fun laaFreshDAO(): LaaFreshDAO

    companion object {
        private var INSTANCE: LaaFreshDB? = null
        fun getInstance(context: Context?): LaaFreshDB? {
            if (INSTANCE == null) {
                synchronized(LaaFreshDB::class) {
                    INSTANCE = Room.databaseBuilder(
                        context!!.applicationContext,
                        LaaFreshDB::class.java, "LaaFresh.db"
                    )
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }


}