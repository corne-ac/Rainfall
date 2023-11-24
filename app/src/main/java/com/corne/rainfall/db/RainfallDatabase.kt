package com.corne.rainfall.db

import android.content.Context
import android.content.Intent
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.corne.rainfall.db.dao.LocationDAO
import com.corne.rainfall.db.dao.NotificationDAO
import com.corne.rainfall.db.dao.RainfallDAO
import com.corne.rainfall.db.entity.LocationEntity
import com.corne.rainfall.db.entity.NotificationEntity
import com.corne.rainfall.db.entity.RainfallEntity
import com.corne.rainfall.utils.Constants
import com.corne.rainfall.utils.DateConverter
import com.corne.rainfall.utils.UUIDConverter
import java.io.File
import java.io.IOException
import kotlin.system.exitProcess

@Database(
    entities = [RainfallEntity::class, LocationEntity::class, NotificationEntity::class],
    version = 5,
)
@TypeConverters(DateConverter::class, UUIDConverter::class)
abstract class RainfallDatabase : RoomDatabase() {
    abstract fun rainEntityDao(): RainfallDAO
    abstract fun locationDao(): LocationDAO
    abstract fun notificationDAO(): NotificationDAO

    //https://stackoverflow.com/questions/73197105/how-to-export-room-database-as-a-db-to-download-file-so-i-can-use-it-later
    //TODO: sort of works maybe want some message for success and maybe save to downloads folder??
    fun backupDatabase(context: Context): Int {
        var result = -1
        if (INSTANCE == null) return result

        val dbFile = context.getDatabasePath(Constants.DATABASE_NAME)
        val dbWalFile = File(dbFile.path + Constants.WAL_FILE_SUFFIX)
        val dbShmFile = File(dbFile.path + Constants.SHM_FILE_SUFFIX)
        val bkpFile = File(dbFile.path + Constants.BACKUP_SUFFIX)
        val bkpWalFile = File(bkpFile.path + Constants.WAL_FILE_SUFFIX)
        val bkpShmFile = File(bkpFile.path + Constants.SHM_FILE_SUFFIX)
        if (bkpFile.exists()) bkpFile.delete()
        if (bkpWalFile.exists()) bkpWalFile.delete()
        if (bkpShmFile.exists()) bkpShmFile.delete()
        checkpoint()
        try {
            dbFile.copyTo(bkpFile, true)
            if (dbWalFile.exists()) dbWalFile.copyTo(bkpWalFile, true)
            if (dbShmFile.exists()) dbShmFile.copyTo(bkpShmFile, true)
            result = 0
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return result
    }

    fun restoreDatabase(context: Context, restart: Boolean = true) {
        if (!File(context.getDatabasePath(Constants.DATABASE_NAME).path + Constants.BACKUP_SUFFIX).exists()) {
            return
        }
        if (INSTANCE == null) return
        val dbpath = INSTANCE!!.openHelper.readableDatabase.path
        val dbFile = File(dbpath)
        val dbWalFile = File(dbFile.path + Constants.WAL_FILE_SUFFIX)
        val dbShmFile = File(dbFile.path + Constants.SHM_FILE_SUFFIX)
        val bkpFile = File(dbFile.path + Constants.BACKUP_SUFFIX)
        val bkpWalFile = File(bkpFile.path + Constants.WAL_FILE_SUFFIX)
        val bkpShmFile = File(bkpFile.path + Constants.SHM_FILE_SUFFIX)
        try {
            bkpFile.copyTo(dbFile, true)
            if (bkpWalFile.exists()) bkpWalFile.copyTo(dbWalFile, true)
            if (bkpShmFile.exists()) bkpShmFile.copyTo(dbShmFile, true)
            checkpoint()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        if (restart) {
            val i = context.packageManager.getLaunchIntentForPackage(context.packageName)
            i!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            context.startActivity(i)
            exitProcess(0)
        }
    }


    private fun checkpoint() {
        val db = this.openHelper.writableDatabase
        db.query("PRAGMA wal_checkpoint(FULL);")
        db.query("PRAGMA wal_checkpoint(TRUNCATE);")
    }

    // Singleton prevents multiple instances of database opening at the same time.
    // In order to keep ensure this thread synchronization is needed.
    // This is a slightly modified version of the code from this example:
    // https://github.com/JeremyLeyvraz/WorkersKotlinExample/blob/43fb582a3740e4bfa474cf72881ecde07cc51dfb/libraryExample/src/main/java/com/lj/libraryExample/AppDatabase.kt#L13C23-L29
    companion object {
        var INSTANCE: RainfallDatabase? = null
        fun getInstance(context: Context): RainfallDatabase {
            return INSTANCE ?: synchronized(this) {
                val rainfallDatabase = Room.databaseBuilder(
                    context.applicationContext,
                    RainfallDatabase::class.java,
                    Constants.DATABASE_NAME

                ).fallbackToDestructiveMigration().build()
                INSTANCE = rainfallDatabase


                rainfallDatabase
            }
        }
    }
}