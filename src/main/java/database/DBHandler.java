package main.java.database;

import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.Dao;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class DBHandler <T>{
    private final Logger logger = Logger.getGlobal();
    private final Dao<T, Integer> dao = getDao();

    void add(T Object) {
        try {
            dao.create(Object);
        }catch (Exception e){
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    void update(T Object) {
        try {
            dao.update(Object);
        }catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    ArrayList<T> getAll() {
        CloseableIterator<T> closeableIterator = dao.closeableIterator();
        ArrayList<T> objectArrayList = new ArrayList<>();
        try {
            closeableIterator.forEachRemaining(objectArrayList::add);
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        return objectArrayList;
    }

    void remove(T Object) {
        try {
            dao.delete(Object);
        }catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }
    abstract Dao<T, Integer> getDao();
}
