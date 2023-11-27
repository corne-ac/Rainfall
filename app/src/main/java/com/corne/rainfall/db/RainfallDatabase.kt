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

/**
 * This abstract class represents the Rainfall database.
 * It extends RoomDatabase and is annotated with @Database to define the entities and version of the database.
 * It is also annotated with @TypeConverters to specify the converters used for the database.
 *
 * @property rainEntityDao The DAO for the RainfallEntity.
 * @property locationDao The DAO for the LocationEntity.
 * @property notificationDAO The DAO for the NotificationEntity.
 */

//The below code was derived from CopyProgramming
//https://copyprogramming.com/howto/importing-android-database-sqlite-android-studio-code-example
//Manuel Taylor

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

    /**
     * This method backs up the database.
     * It creates a copy of the database and its associated files (WAL and SHM) with a backup suffix.
     *
     * @param context The context used to get the database path.
     * @return An integer indicating the result of the backup operation. -1 indicates an error.
     */
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

    /**
     * This method restores the database from a backup.
     * It replaces the current database and its associated files (WAL and SHM) with the backup files.
     *
     * @param context The context used to get the database path.
     * @param restart A Boolean indicating whether to restart the application after restoring the database. Default is true.
     */
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


    /**
     * This method performs a checkpoint operation on the database.
     * It forces the database to write all changes in the WAL file to the main database file and then truncates the WAL file.
     */
    private fun checkpoint() {
        val db = this.openHelper.writableDatabase
        db.query("PRAGMA wal_checkpoint(FULL);")
        db.query("PRAGMA wal_checkpoint(TRUNCATE);")
    }

    // Singleton prevents multiple instances of database opening at the same time.
    // In order to keep ensure this thread synchronization is needed.
    // This is a slightly modified version of the code from this example:
    // https://github.com/JeremyLeyvraz/WorkersKotlinExample/blob/43fb582a3740e4bfa474cf72881ecde07cc51dfb/libraryExample/src/main/java/com/lj/libraryExample/AppDatabase.kt#L13C23-L29
    /**
     * This companion object provides a singleton instance of the RainfallDatabase.
     * It ensures that multiple instances of the database are not opened at the same time.
     */
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