package utils;

import java.util.Random;

public class RandomUtils {

    public static String getRandomGender() {
        String[] words = {"Male", "Female", "Other"};
        int orderNumber = getRandomInt(0, words.length - 1);

        return words[orderNumber];
    }

    public static int getRandomInt(int min, int max) {
        Random r = new Random();

        return r.nextInt((max - min) + 1) + min;
    }
}