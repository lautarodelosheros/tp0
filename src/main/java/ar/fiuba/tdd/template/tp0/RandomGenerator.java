package ar.fiuba.tdd.template.tp0;

import java.util.concurrent.ThreadLocalRandom;

public class RandomGenerator {

    public static int getRandomRange(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }

}
