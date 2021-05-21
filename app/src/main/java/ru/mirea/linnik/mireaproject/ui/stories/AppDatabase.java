package ru.mirea.linnik.mireaproject.ui.stories;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Cell.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract HistoryDao storyDao();
}

