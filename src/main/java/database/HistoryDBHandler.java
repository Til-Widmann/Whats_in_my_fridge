package main.java.database;

import com.j256.ormlite.dao.Dao;
import main.java.database.dataObjects.History;

public class HistoryDBHandler extends DBHandler<History>{

    @Override
    Dao getDao() {
        return DBConnect.getHistoryDao();
    }
}
