package utils;

import java.util.List;
import java.util.Random;

public class RandomUtil {
    private static final Random random = new Random();

    public static <T> T getRandomElement(List<T> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("List cannot be null or empty");
        }
        return list.get(random.nextInt(list.size()));
    }
}