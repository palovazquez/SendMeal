package com.practicaClases.sendmeal.DAO;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.practicaClases.sendmeal.model.Plato;

import java.util.concurrent.Executor;

@Database(entities = {Plato.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public static Executor databaseWriteExecutor;

    public abstract PlatoDao platoDao();

    private static final String DB_NAME = "database.db";
    private static AppDatabase INSTANCE;

    public static AppDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DB_NAME).build();
                }
            }
        }
        return INSTANCE;
    }
}
