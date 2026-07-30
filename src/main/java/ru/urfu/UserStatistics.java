package ru.urfu;

import java.io.*;
import java.util.*;

/**
 * Хранит статистику пользователей по последним вариантам
 * по каждому предмету
 */
public class UserStatistics {
    private Map<String, Map<String, List<Long>>> userStatistics;
    private String path;

    public UserStatistics() {
        userStatistics = new HashMap<>();
        path = null;
    }

    /**
     * Считывает статистику каждого пользователя из файла
     */
    public void populateStatistics(String path){
        this.path = path;
        File files = new File(path);
        if (!files.exists()){
            files.mkdir();
            File gitkeepFile = new File(files, ".gitkeep");
            try {
                gitkeepFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return;
        }
        File[] filesPath = files.listFiles();
        List<File> subject = new ArrayList<>();
        if (files != null) {
            for (File file : filesPath) {
                if (file.isDirectory()) {
                    subject.add(file);
                }
            }
        }
        else{
            return;
        }
        for (File file : subject) {
            userStatistics.put(file.getName(), new HashMap<>());
        }
        for (File sub : subject){
            File[] statistics = sub.listFiles();
            for (File stat : statistics) {
                if(!stat.getName().equals(".gitkeep")) {
                    try (BufferedReader br = new BufferedReader(new FileReader(stat))) {
                        String[] data = br.readLine().split(" ");
                        List<Long> userStat = new ArrayList<>();
                        for (String singleData : data) {
                            userStat.add(Long.parseLong(singleData));
                        }
                        userStatistics.get(sub.getName()).put(stat.getName(), userStat);
                    } catch (IOException | NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * Получить средний балл
     */
    public Long getAverageScore(String subject, String userId){
        List<Long> data = userStatistics.get(subject).get(userId);
        Long sum = 0L;
        for(int i = 0; i < data.size() / 2; i++){
            sum += data.get(i);
        }
        return sum / (data.size() / 2);
    }

    /**
     * Получить среднее время
     */
    public Long getAverageTime(String subject, String userId){
        List<Long> data = userStatistics.get(subject).get(userId);
        Long sum = 0L;
        for(int i = data.size() / 2; i < data.size(); i++){
            sum += data.get(i);
        }
        return sum / (data.size() / 2);
    }

    /**
     * Проверяет, имеется ли статистика пользователя по данному предмету
     */
    public boolean check(String subject, String userId){
        if (!userStatistics.containsKey(subject)){
            return false;
        }
        return userStatistics.get(subject).containsKey(userId);
    }

    /**
     * Добавить данные в статистику
     */
    public void addStat(String subject, String userId, Long score, Long time){
        if (path == null) {
            throw new IllegalArgumentException("Ошибка: путь не может быть равен null." +
                    " Для начала пополните данные с помощью populateStatistics");
        }
        if(!userStatistics.get(subject).containsKey(userId)){
            List<Long> data = new ArrayList<>();
            data.add(score);
            data.add(time);
            userStatistics.get(subject).put(userId, data);
        }
        else {
            List<Long> data = userStatistics.get(subject).get(userId);
            if (data.size() / 2 < 5) {
                data.add(data.size() / 2, score);
                data.add(time);
            }
            else {
                data.remove(0);
                data.add(4, score);
                data.remove(5);
                data.add(time);
            }
        }
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(path + "\\" + subject + "\\" + userId))) {
            List<Long> data = userStatistics.get(subject).get(userId);
            for (int i = 0; i < data.size() - 1; i++) {
                    writer.write(data.get(i) + " ");
            }
            writer.write(data.get(data.size() - 1).toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
