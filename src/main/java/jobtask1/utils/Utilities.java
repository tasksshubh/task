package jobtask1.utils;

import java.util.Random;

public class Utilities {
    public static String getRandomValue(){
        Random random = new Random();
        int randomInt = random.nextInt(100000);
        return Integer.toString(randomInt);
    }
}
