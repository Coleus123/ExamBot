// FileLoader.java - добавляем недостающий метод для тестов
package ru.urfu.loader;

import ru.urfu.model.QuesAns;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

/**
 * Класс для загрузки данных из файловой системы
 */
public class FileLoader {

    /**
     * Загружает вопросы и ответы из директории
     * @param path путь к директории с данными
     * @return Map предмет -> список вариантов
     */
    public Map<String, List<QuesAns>> loadData(String path) {
        Map<String, List<QuesAns>> subjects = new HashMap<>();
        File rootDir = new File(path);

        if (!rootDir.exists()) {
            createDefaultDirectory(rootDir);
            return subjects;
        }

        List<File> subjectDirs = getSubjectDirectories(rootDir);
        if (subjectDirs.isEmpty()) {
            return subjects;
        }

        for (File subjectDir : subjectDirs) {
            List<QuesAns> variants = loadVariantsForSubject(subjectDir);
            if (!variants.isEmpty()) {
                subjects.put(subjectDir.getName(), variants);
            }
        }

        return subjects;
    }

    /**
     * Создает директорию по умолчанию, если она не существует
     */
    private void createDefaultDirectory(File dir) {
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    /**
     * Возвращает список директорий предметов
     */
    private List<File> getSubjectDirectories(File rootDir) {
        File[] files = rootDir.listFiles();
        List<File> subjectDirs = new ArrayList<>();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory() && !file.getName().equals(".gitkeep")) {
                    subjectDirs.add(file);
                }
            }
        }
        return subjectDirs;
    }

    /**
     * Загружает варианты для конкретного предмета
     */
    private List<QuesAns> loadVariantsForSubject(File subjectDir) {
        List<QuesAns> variants = new ArrayList<>();
        File[] variantDirs = subjectDir.listFiles();

        if (variantDirs == null) {
            return variants;
        }

        for (File variantDir : variantDirs) {
            if (variantDir.isDirectory() && !variantDir.getName().equals(".gitkeep")) {
                QuesAns variant = loadVariantFromDirectory(variantDir);
                if (variant != null && variant.getNumberOfQuestions() > 0) {
                    variants.add(variant);
                }
            }
        }
        return variants;
    }

    /**
     * Загружает один вариант из директории
     */
    private QuesAns loadVariantFromDirectory(File variantDir) {
        List<String> questions = new ArrayList<>();
        List<String> answers = new ArrayList<>();

        File[] contents = variantDir.listFiles();
        if (contents == null) {
            return null;
        }

        for (File item : contents) {
            if (item.isDirectory()) {
                if (item.getName().equals("ques")) {
                    questions = loadFilesFromDirectory(item);
                } else if (item.getName().equals("answ")) {
                    answers = loadFilesFromDirectory(item);
                }
            }
        }

        if (questions.isEmpty() || answers.isEmpty() || questions.size() != answers.size()) {
            return null;
        }

        QuesAns quesAns = new QuesAns();
        for (int i = 0; i < questions.size(); i++) {
            quesAns.addQuestionAndAnswer(questions.get(i), answers.get(i));
        }
        return quesAns;
    }

    /**
     * Загружает текстовые файлы из директории в порядке нумерации
     */
    private List<String> loadFilesFromDirectory(File directory) {
        List<String> contents = new ArrayList<>();
        File[] files = directory.listFiles();

        if (files == null) {
            return contents;
        }

        // Сортировка по номеру файла
        Arrays.sort(files, (f1, f2) -> {
            try {
                int num1 = Integer.parseInt(f1.getName().split("\\.")[0]);
                int num2 = Integer.parseInt(f2.getName().split("\\.")[0]);
                return Integer.compare(num1, num2);
            } catch (NumberFormatException e) {
                return f1.getName().compareTo(f2.getName());
            }
        });

        for (File file : files) {
            if (file.isFile() && !file.getName().equals(".gitkeep")) {
                try {
                    String content = new String(Files.readAllBytes(file.toPath()));
                    contents.add(content);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return contents;
    }
}