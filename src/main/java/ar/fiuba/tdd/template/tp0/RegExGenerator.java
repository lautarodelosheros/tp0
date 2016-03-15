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
        System.out.print(generator.generate("...", 5));
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
                output.append(this.generateSet(regEx, index));
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
        output = this.getRandomRange(0, DOT_CHAR_COUNT);
        while (list.contains(output)) {
            output = this.getRandomRange(0, DOT_CHAR_COUNT);
        }
        return (char)output;
    }

    private String generateSet(String regEx, int index) {
        StringBuilder output = new StringBuilder();
        int number = this.getRandomNumber(regEx, index);
        String chars = this.getSetChars(regEx, index);
        for (int i = 0 ; i < number ; ++i) {
            int random = this.getRandomRange(0, chars.length());
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
        int number;
        switch (quantifier) {
            case '*':
                number = this.getRandomRange(0, this.maxLength + 1);
                break;
            case '+':
                number = this.getRandomRange(1, this.maxLength + 1);
                break;
            case '?':
                number = this.getRandomRange(0, 2);
                break;
            default:
                number = 1;
        }
        return number;
    }

    private String getSetChars(String regEx, int index) {
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

    public int getRandomRange(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }
}