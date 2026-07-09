// SubjectManagerTest.java - исправленная версия
package ru.urfu;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тестирует хранилище вариантов
 */
class SubjectManagerTest {

    private SubjectManager subjectManager;

    @TempDir
    static File tempDir;

    @BeforeAll
    public static void setUpBeforeClass() throws IOException {
        File directory = new File(tempDir, "Test");
        directory.mkdirs();

        File subject = new File(directory, "Математика");
        subject.mkdirs();

        File variant = new File(subject, "1");
        variant.mkdirs();

        File answ = new File(variant, "answ");
        answ.mkdirs();

        File ques = new File(variant, "ques");
        ques.mkdirs();

        File firstQues = new File(ques, "1.txt");
        FileWriter writer = new FileWriter(firstQues);
        writer.write("Вопрос");
        writer.close();

        File firstAnsw = new File(answ, "1.txt");
        FileWriter writer2 = new FileWriter(firstAnsw);
        writer2.write("Ответ");
        writer2.close();
    }

    @BeforeEach
    public void setUp() {
        subjectManager = new SubjectManager();
    }

    /**
     * Тестирует заполнение предметами и вариантами из папок и получение вариантов
     * по указанному предмету и номеру варианта
     */
    @Test
    public void testPopulateDataAndGetVariant() {
        subjectManager.populateData(tempDir.getPath() + "\\Test");
        QuesAns quesAns = subjectManager.getVariant("Математика", 1);
        assertEquals("Вопрос", quesAns.getQuestion(0));
        assertEquals("Ответ", quesAns.getAnswer(0));
    }

    /**
     * Тестирует получение списка всех предметов
     */
    @Test
    public void testAllSubjects() {
        subjectManager.populateData(tempDir.getPath() + "\\Test");
        assertEquals("Математика", subjectManager.allSubjects().get(0));
    }

    /**
     * Тестирует метод hasSubject
     */
    @Test
    public void testHasSubject() {
        subjectManager.populateData(tempDir.getPath() + "\\Test");
        assertTrue(subjectManager.hasSubject("Математика"));
        assertFalse(subjectManager.hasSubject("Физика"));
    }

    /**
     * Тестирует метод quantityVariants
     */
    @Test
    public void testQuantityVariants() {
        subjectManager.populateData(tempDir.getPath() + "\\Test");
        assertEquals(1, subjectManager.quantityVariants("Математика"));
        assertEquals(0, subjectManager.quantityVariants("Физика"));
    }

    /**
     * Тестирует получение варианта с несуществующим номером
     */
    @Test
    public void testGetVariantInvalidNumber() {
        subjectManager.populateData(tempDir.getPath() + "\\Test");
        assertNull(subjectManager.getVariant("Математика", 0));
        assertNull(subjectManager.getVariant("Математика", 2));
        assertNull(subjectManager.getVariant("Физика", 1));
    }

    /**
     * Тестирует загрузку из несуществующей директории
     */
    @Test
    public void testPopulateDataNonExistentDirectory() {
        subjectManager.populateData(tempDir.getPath() + "\\NonExistent");
        assertTrue(subjectManager.allSubjects().isEmpty());
        assertEquals(0, subjectManager.quantityVariants("Математика"));
        assertFalse(subjectManager.hasSubject("Математика"));
    }
}