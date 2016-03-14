package ar.fiuba.tdd.template.tp0;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RegExGenerator {

    private int maxLength;
    private static final int DOT_CHAR_COUNT = 256;

    public RegExGenerator(int maxLength) {
        this.maxLength = maxLength;
    }

    public static void main(String [] args) {
        RegExGenerator generator = new RegExGenerator(10);
        System.out.print(generator.generate(".....................................", 1));
    }

    public List<String> generate(String regEx, int numberOfResults) {
        ArrayList<String> output = new ArrayList<>();
        for (int i = 0 ; i < numberOfResults ; ++i) {
            output.add(this.generate(regEx));
        }
        return output;
    }

    public String generate(String regEx) {
        StringBuilder output = new StringBuilder();
        int index = 0;
        while (index < regEx.length()) {
            if (regEx.charAt(index) == '[') {
                output.append(this.generateGroup(regEx, index));
                index = regEx.indexOf(']', index) + 1;
            } else {
                output.append(this.generateIndividual(regEx, index));
                if (regEx.charAt(index) == '\\') {
                    ++index;
                }
                ++index;
            }
            if (this.isQuantifier(this.getChar(regEx, index))) {
                ++index;
            }
        }
        return output.toString();
    }

    private String generateIndividual(String regEx, int index) {
        StringBuilder output = new StringBuilder();
        int number = this.getRandomNumber(regEx, index);
        for (int i = 0 ; i < number ; ++i) {
            if (regEx.charAt(index) == '.') {
                output.append(this.getDotRandomChar());
            } else if (regEx.charAt(index) == '\\') {
                output.append(this.getChar(regEx, index + 1));
            } else {
                output.append(regEx.charAt(index));
            }
        }
        return output.toString();
    }

    private char getDotRandomChar() {
        ArrayList<Integer> list = new ArrayList<>();
        //Prohibited dot characters
        list.add(10);
        list.add(13);
        list.add(133);
        int output;
        output = ThreadLocalRandom.current().nextInt(0, DOT_CHAR_COUNT);
        while (list.contains(output)) {
            output = ThreadLocalRandom.current().nextInt(0, DOT_CHAR_COUNT);
        }
        return (char)output;
    }

    private String generateGroup(String regEx, int index) {
        StringBuilder output = new StringBuilder();
        int number = this.getRandomNumber(regEx, index);
        String chars = this.getGroupChars(regEx, index);
        for (int i = 0 ; i < number ; ++i) {
            int random = ThreadLocalRandom.current().nextInt(0, chars.length());
            output.append(chars.charAt(random));
        }
        return output.toString();
    }

    private int getRandomNumber(String regEx, int index) {
        char quantifier;
        if (regEx.charAt(index) == '[') {
            quantifier = this.getChar(regEx, regEx.indexOf(']', index) + 1);
        } else if (regEx.charAt(index) != '\\') {
            quantifier = this.getChar(regEx, index + 1);
        } else {
            quantifier = this.getChar(regEx, index + 2);
        }
        return this.getRandomNumberByQuantifier(quantifier);
    }

    private int getRandomNumberByQuantifier(char quantifier) {
        int min;
        int max;
        switch (quantifier) {
            case '*':
                min = 0;
                max = this.maxLength;
                break;
            case '+':
                min = 1;
                max = this.maxLength;
                break;
            case '?':
                min = 0;
                max = 1;
                break;
            default:
                min = 1;
                max = 1;
        }
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    private String getGroupChars(String regEx, int index) {
        return regEx.substring(index + 1, regEx.indexOf(']', index));
    }

    private boolean isQuantifier(char character) {
        return Character.toString(character).matches("[\\*\\+\\?]");
    }

    private char getChar(String chain, int index) {
        if (index < chain.length()) {
            return chain.charAt(index);
        }
        return 0;
    }
}