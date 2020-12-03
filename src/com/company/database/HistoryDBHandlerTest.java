package com.company.database;

import com.company.database.dataObjects.FoodItem;
import com.company.database.dataObjects.History;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HistoryDBHandlerTest {
    int testSaladId = -5;
    LocalDateTime historyInsertTime;
    @BeforeEach
    void setUp() {

        historyInsertTime = LocalDateTime.of(2020, 12, 3, 12,50,20);
        HistoryDBHandler.insertHistory(new History(
                        testSaladId,
                        historyInsertTime,
                        -200
                ));
        HistoryDBHandler.insertHistory(new History(
                testSaladId,
                LocalDateTime.now(),
                100
        ));
    }
    @AfterEach
    void tearDown() {
        HistoryDBHandler.removeHistory(testSaladId);
    }

    @Test
    void insertHistory() {
        LinkedList<History> histories = HistoryDBHandler.getHistoryOf(testSaladId);
        assertEquals(historyInsertTime, histories.getFirst().getUseDate());
        assertEquals(-200,histories.getFirst().getAmount());
        assertEquals(100, histories.getLast().getAmount());
    }

    @Test
    void getHistoryOf() {
        List<History> histories = HistoryDBHandler.getHistoryOf(testSaladId);
        assertEquals(2, histories.size());
    }

    @Test
    void removeHistory() {
        HistoryDBHandler.removeHistory(testSaladId);
        assertEquals(0, HistoryDBHandler.getHistoryOf(testSaladId).size());
    }
}