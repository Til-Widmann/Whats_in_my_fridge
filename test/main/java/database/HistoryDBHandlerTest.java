package main.java.database;

import main.java.database.dataObjects.History;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HistoryDBHandlerTest {
    private static final int TEST_SALAD_ID = -5;
    LocalDateTime historyInsertTime;
    @BeforeEach
    void setUp() {

        historyInsertTime = LocalDateTime.of(2020, 12, 3, 12,50,20);
        HistoryDBHandler.insertHistory(new History(
                TEST_SALAD_ID,
                        historyInsertTime,
                        -200
                ));
        HistoryDBHandler.insertHistory(new History(
                TEST_SALAD_ID,
                LocalDateTime.now(),
                100
        ));
    }

    @AfterEach
    void tearDown() {
        HistoryDBHandler.removeHistory(TEST_SALAD_ID);
    }

    @Test
    void insertHistory() {
        LinkedList<History> histories = HistoryDBHandler.getHistoryOf(TEST_SALAD_ID);
        assertEquals(historyInsertTime, histories.getFirst().getUseDate());
        assertEquals(-200,histories.getFirst().getAmount());
        assertEquals(100, histories.getLast().getAmount());
    }

    @Test
    void getHistoryOf() {
        List<History> histories = HistoryDBHandler.getHistoryOf(TEST_SALAD_ID);
        assertEquals(2, histories.size());
    }

    @Test
    void removeHistory() {
        HistoryDBHandler.removeHistory(TEST_SALAD_ID);
        assertEquals(0, HistoryDBHandler.getHistoryOf(TEST_SALAD_ID).size());
    }
}