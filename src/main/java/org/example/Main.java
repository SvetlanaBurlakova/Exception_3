package org.example;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите информацию :");
        String data = scanner.nextLine();
        String[] strArr = data.split(" ");
        int lenCheck = CheckFullData(strArr);
        while (!(lenCheck == 0)) {
            if (lenCheck == -1) {
                System.out.println("Данных не достаточно, повторите ввод");
            } else {
                System.out.println("Данных слишком много, повторите ввод");
            }
            data = scanner.nextLine();
            strArr = data.split(" ");
            lenCheck = CheckFullData(strArr);
        }
        String name = null;
        String birthDate = null;
        String phone = null;
        String gender = null;
        try {
            name = GetName(strArr);
        } catch (NameDataNotFull e){
            System.out.println(e.getMessage());
        }
        try {
            birthDate = GetBirthdate(strArr);
        } catch (BirthdateDataNotFound e){
            System.out.println(e.getMessage());
        }
        try {
            phone = GetPhoneNumber(strArr);
        } catch (PhoneDataNotFound e){
            System.out.println(e.getMessage());
        }
        try {
            gender = GetGender(strArr);
        } catch (GenderDataNotFound e){
            System.out.println(e.getMessage());
        }
        if (name != null && phone != null && birthDate != null && gender != null) {
            String result = name + " " + birthDate + " " + phone + " " + gender + "\n";
            String fileName = name.split(" ")[0];
            try {
                FileWriter fileWriter = new FileWriter(fileName, true);
                fileWriter.append(result);
                fileWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }


    }
    public static int CheckFullData(String[] strArr){
        if (strArr.length == 6){
            return 0; // Возвращает 0, когда кол-во данных полное
        } else if (strArr.length < 6) {
            return -1; // Возвращает -1, когда данных недостаточно
        }
        else {
            return -2; // Возвращает -2, когда данных слишком много
        }
    }

    public static boolean isAlpha(String s) {
        return s != null && s.chars().allMatch(Character::isLetter);
    }

    public static boolean isDigit(String s) {
        return s != null && s.chars().allMatch(Character::isDigit);
    }
    public static String GetName(String[] strArr){
        String name = "";
        for (String str:
                strArr) {
            if (str.length() > 1 && isAlpha(str)){
                name+=str + " ";
            }
        }
        if (name.split(" ").length < 3) {
            throw new NameDataNotFull();
        }
        return name;
    }

    public static String GetBirthdate(String[] strArr){
        for (String str:
                strArr) {
            if (str.split("\\.").length == 3){
                return str;
            }
        }
        throw new BirthdateDataNotFound();
    }

    public static String GetPhoneNumber(String[] strArr){
        for (String str:
                strArr) {
            if (isDigit(str)){
                return str;
            }
        }
        throw new PhoneDataNotFound();
    }

    public static String GetGender(String[] strArr){
        for (String str:
                strArr) {
            if (isAlpha(str) && str.length() == 1 &&
                    (str.toLowerCase().equals("f") || str.toLowerCase().equals("m")) ){
                return str;
            }
        }
        throw new GenderDataNotFound();
    }

    static class NameDataNotFull extends IllegalArgumentException
    {

        public NameDataNotFull() {
            super("ФИО введено не полностью");
        }
    }
    static class BirthdateDataNotFound extends IllegalArgumentException {

        public BirthdateDataNotFound() {
            super("Дата рождения не введена, либо введена не верно");
        }
    }

    static class PhoneDataNotFound extends IllegalArgumentException {

        public PhoneDataNotFound() {
            super("Номер телефона не введен, либо введен не верно");
        }
    }

    static class GenderDataNotFound extends IllegalArgumentException {

        public GenderDataNotFound() {
            super("Пол не введен, либо введен не верно");
        }
    }
}