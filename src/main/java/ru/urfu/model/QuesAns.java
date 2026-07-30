package ru.urfu.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс QuesAns содержит список вопросов и соответствующих ответов.
 * Он предоставляет методы для доступа к вопросам и их ответам.
 */

public class QuesAns {
    private List<String> questions;
    private  List<String> answers;
    /**
     * Конструктор для инициализации списков вопросов и ответов.
     */

    public QuesAns() {

        this.questions=new ArrayList<>();
        this.answers=new ArrayList<>();
    }/**
     * Метод AddBasicQuestion инициализирует список основных вопросов и соответствующих им ответов.
     */
    public void AddBasicQuestion(){
        this.questions.add("Чему равен угол равностороннего треугольника?");
        this.answers.add("60");

        this.questions.add("Какая столица Франции?");
        this.answers.add("Париж");

        this.questions.add("В каком числе столько же букв, сколько и в его названии?");
        this.answers.add("100");

        this.questions.add("Какова скорость света в вакууме (в м/с)?");
        this.answers.add("299792458");

        this.questions.add("Какая самая большая планета в нашей Солнечной системе?");
        this.answers.add("Юпитер");


        this.questions.add ("Какое наименьшее простое число?");
        this.answers.add("2");


        this.questions.add("Кто изобрел телефон?");
        this.answers.add("Александр Грейам Белл");


        this.questions.add("Какой химический символ у золота?");
        this.answers.add("Au");


        this.questions.add("В каком году первый человек высадился на Луне?");
        this.answers.add("1969");

        this.questions.add("Какой океан самый большой на Земле?");
        this.answers.add("Тихий");
    }
    /**
     * Добавляет новый вопрос и соответствующий ему ответ в список.
     */
    public  void addQuestionAndAnswer(String question, String answer) {
        this.questions.add(question);
        this.answers.add(answer);
    }

    /**
     * Получает вопрос по указанному индексу, начиная с нуля
     * @return Строка вопроса.
     */
    public  String getQuestion(int index) {
        if (index >= 0 && index < this.questions.size()) {
            return this.questions.get(index);
        }
        return null;
    }

    /**
     * Получает ответ по указанному индексу, начиная с нуля

     * @return Строка ответа.
     */
    public  String getAnswer(int index) {
        if (index >= 0 && index < this.answers.size()) {
            return this.answers.get(index);
        }
        return null;
    }

    /**
     * Получает общее количество вопросов.
     *
     * @return Количество вопросов.
     */
    public  int getNumberOfQuestions() {
        return this.questions.size();
    }
    /**
     * Удаляет вопрос и соответствующий ответ по указанному индексу.
     *
     */
    public boolean removeQues_and_Ans(int index){
        if (index >=0 && index<this.questions.size()){
            questions.remove(index);
            answers.remove(index);
            return true;
        }
        return false;
    }


}





