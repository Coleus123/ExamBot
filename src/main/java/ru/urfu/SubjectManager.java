// SubjectManager.java - исправленная версия
package ru.urfu;

import java.util.*;

/**
 * Хранит названия предметов и варианты к ним
 */
public class SubjectManager {
    private final Map<String, List<QuesAns>> subjects;
    private final FileLoader fileLoader;

    public SubjectManager() {
        this.subjects = new HashMap<>();
        this.fileLoader = new FileLoader();
    }

    /**
     * Пополняет хешмап названиями предметов и вариантами по указанному пути
     * @param path путь к директории с данными
     */
    public void populateData(String path) {
        Map<String, List<QuesAns>> loadedData = fileLoader.loadData(path);
        subjects.putAll(loadedData);
    }

    /**
     * Возвращает количество вариантов в предмете
     * @param subject название предмета
     * @return количество вариантов
     */
    public int quantityVariants(String subject) {
        List<QuesAns> variants = subjects.get(subject);
        return variants != null ? variants.size() : 0;
    }

    /**
     * Возвращает список предметов
     * @return список названий предметов
     */
    public List<String> allSubjects() {
        return new ArrayList<>(subjects.keySet());
    }

    /**
     * Возвращает вариант по номеру и предмету
     * @param subject название предмета
     * @param number номер варианта (начиная с 1)
     * @return вариант или null, если не найден
     */
    public QuesAns getVariant(String subject, int number) {
        List<QuesAns> variants = subjects.get(subject);
        if (variants == null || variants.size() < number || number <= 0) {
            return null;
        }
        return variants.get(number - 1);
    }

    /**
     * Проверяет, существует ли предмет
     * @param subject название предмета
     * @return true если предмет существует
     */
    public boolean hasSubject(String subject) {
        return subjects.containsKey(subject);
    }
}