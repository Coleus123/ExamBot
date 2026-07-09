// FileLoaderTest.java - новый тест
package ru.urfu;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для класса FileLoader
 */
class FileLoaderTest {

    @TempDir
    static File tempDir;
    private FileLoader fileLoader;

    @BeforeAll
    public static void setUpBeforeClass() throws Exception {
        // Создаем структуру для теста
        File directory = new File(tempDir, "Test");
        directory.mkdirs();

        File subject = new File(directory, "Математика");
        subject.mkdirs();

        // Вариант 1
        File variant1 = new File(subject, "1");
        variant1.mkdirs();
        File answ1 = new File(variant1, "answ");
        answ1.mkdirs();
        File ques1 = new File(variant1, "ques");
        ques1.mkdirs();

        File q1 = new File(ques1, "1.txt");
        FileWriter w1 = new FileWriter(q1);
        w1.write("Вопрос 1");
        w1.close();
        File a1 = new File(answ1, "1.txt");
        FileWriter w2 = new FileWriter(a1);
        w2.write("Ответ 1");
        w2.close();

        File q2 = new File(ques1, "2.txt");
        FileWriter w3 = new FileWriter(q2);
        w3.write("Вопрос 2");
        w3.close();
        File a2 = new File(answ1, "2.txt");
        FileWriter w4 = new FileWriter(a2);
        w4.write("Ответ 2");
        w4.close();

        // Вариант 2
        File variant2 = new File(subject, "2");
        variant2.mkdirs();
        File answ2 = new File(variant2, "answ");
        answ2.mkdirs();
        File ques2 = new File(variant2, "ques");
        ques2.mkdirs();

        File q3 = new File(ques2, "1.txt");
        FileWriter w5 = new FileWriter(q3);
        w5.write("Вопрос 3");
        w5.close();
        File a3 = new File(answ2, "1.txt");
        FileWriter w6 = new FileWriter(a3);
        w6.write("Ответ 3");
        w6.close();
    }

    @Test
    public void testLoadData() {
        fileLoader = new FileLoader();
        Map<String, List<QuesAns>> data = fileLoader.loadData(tempDir.getPath() + "\\Test");

        assertEquals(1, data.size());
        assertTrue(data.containsKey("Математика"));
        assertEquals(2, data.get("Математика").size());
    }

    @Test
    public void testLoadDataEmptyDirectory() {
        File emptyDir = new File(tempDir, "Empty");
        emptyDir.mkdirs();

        fileLoader = new FileLoader();
        Map<String, List<QuesAns>> data = fileLoader.loadData(emptyDir.getPath());

        assertTrue(data.isEmpty());
    }

    @Test
    public void testLoadDataNonExistentDirectory() {
        fileLoader = new FileLoader();
        Map<String, List<QuesAns>> data = fileLoader.loadData(tempDir.getPath() + "\\NonExistent");

        assertTrue(data.isEmpty());
    }

    @Test
    public void testLoadDataWithGitkeep() throws Exception {
        File directory = new File(tempDir, "TestGitkeep");
        directory.mkdirs();

        File gitkeep = new File(directory, ".gitkeep");
        gitkeep.createNewFile();

        fileLoader = new FileLoader();
        Map<String, List<QuesAns>> data = fileLoader.loadData(directory.getPath());

        assertTrue(data.isEmpty());
    }
}