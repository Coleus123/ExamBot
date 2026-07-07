package ru.urfu;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Тестирует правильно ли работают методы с данными пользователей
 */
class UserStatisticsTest {
    private UserStatistics userStatistics;

    @TempDir
    static File tempDir;

    @BeforeAll
    public static void setUpBeforeClass() throws IOException {
        File directory = new File(tempDir, "Test");
        directory.mkdirs();

        File subject = new File(directory, "Математика");
        subject.mkdirs();

        File user = new File(subject, "user123");
        try (FileWriter writer = new FileWriter(user)) {
            writer.write("1 5 30 40");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    void setUp() {
        userStatistics = new UserStatistics();
    }

    /**
     * Проверяет, правильно ли заполняется статистика пользователя
     * и правильно ли высчитывается средний балл и среднее время
     */
    @Test
    void populateStatisticsAndGetAverageScoreAndGetAverageTime() {
        userStatistics.populateStatistics(tempDir.getPath() + "\\Test");
        assertEquals(3L, userStatistics
                .getAverageScore("Математика", "user123"));
        assertEquals(35L, userStatistics
                .getAverageTime("Математика","user123"));
    }

    /**
     * Проверяет, правильно ли добавляются новые данные к статистике пользователя
     */
    @Test
    void addStat() {
        userStatistics.populateStatistics(tempDir.getPath() + "\\Test");
        userStatistics.addStat("Математика", "user1234",
                100L, 500L);
        assertEquals(100L, userStatistics
                .getAverageScore("Математика","user1234"));
        assertEquals(500L, userStatistics
                .getAverageTime("Математика","user1234"));
    }

    /**
     *Проверяет, правильно ли проверяется наличие статистики пользователя
     */
    @Test
    public void testCheck(){
        userStatistics.populateStatistics(tempDir.getPath() + "\\Test");
        assertTrue(userStatistics.check("Математика", "user123"));
        assertFalse(userStatistics.check("Информатика", "user123"));
    }
}