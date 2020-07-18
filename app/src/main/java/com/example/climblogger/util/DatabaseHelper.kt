package com.example.climblogger.util

import android.os.Environment
import com.example.climblogger.data.RouteRoomDatabase
import java.io.*

fun backupDB(database: RouteRoomDatabase, databaseFile: File) {
    database.close()

    val sdir = File(
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
        "DBsaves"
    )
    val sfpath =
        sdir.path + File.separator + "DBsave" + System.currentTimeMillis()
            .toString()
    if (!sdir.exists()) {
        sdir.mkdirs()
    }
    val savefile = File(sfpath)

    try {
        savefile.createNewFile()
        val buffersize = 8 * 1024
        val buffer = ByteArray(buffersize)
        var bytes_read = buffersize
        val savedb: OutputStream = FileOutputStream(sfpath)
        val indb: InputStream = FileInputStream(databaseFile)
        while (indb.read(buffer, 0, buffersize).also { bytes_read = it } > 0) {
            savedb.write(buffer, 0, bytes_read)
        }
        savedb.flush()
        indb.close()
        savedb.close()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
