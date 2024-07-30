package kr.or.kosa.cmsplusmain.util;

import java.util.Random;

public class RandomNumberGenerator {
    public static String generateRandomNumber(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Length must be greater than 0");
        }

        Random random = new Random();
        StringBuilder randomNumber = new StringBuilder();

        for (int i = 0; i < length; i++) {
            randomNumber.append(random.nextInt(10)); // 0-9 사이의 숫자를 추가
        }

        return randomNumber.toString();
    }
}
