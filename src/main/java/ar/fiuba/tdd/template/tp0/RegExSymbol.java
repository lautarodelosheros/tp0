package ar.fiuba.tdd.template.tp0;

import java.util.ArrayList;

public class RegExSymbol {

    private static final int DOT_CHAR_COUNT = 256;

    private char symbol;
    private int quantity;
    private boolean isDot;
    private boolean hasQuantifier;
    private String charSet;

    public RegExSymbol(char symbol, int quantity, boolean isDot, boolean hasQuantifier, String charSet) {
        this.symbol = symbol;
        this.quantity = quantity;
        this.isDot = isDot;
        this.hasQuantifier = hasQuantifier;
        this.charSet = charSet;
    }

    public boolean hasQuantifier() {
        return this.hasQuantifier;
    }

    public String generateMatch() {
        StringBuilder output = new StringBuilder();
        for (int i = 0 ; i < this.quantity ; ++i) {
            if (this.isDot) {
                output.append(this.getDotChar());
            } else if (this.charSet != null) {
                output.append(this.getSetChar());
            } else {
                output.append(this.symbol);
            }
        }
        return output.toString();
    }

    private char getDotChar() {
        ArrayList<Integer> list = new ArrayList<>();
        //Prohibited dot characters
        list.add(10);
        list.add(13);
        list.add(133);
        int output;
        output = RandomGenerator.getRandomRange(0, DOT_CHAR_COUNT);
        while (list.contains(output)) {
            output = RandomGenerator.getRandomRange(0, DOT_CHAR_COUNT);
        }
        return (char)output;
    }

    private char getSetChar() {
        return this.charSet.charAt(RandomGenerator.getRandomRange(0, this.charSet.length()));
    }

}
