package ar.fiuba.tdd.template.tp0;

import java.util.ArrayList;

public class RegExParser {

    private int maxLength;

    public RegExParser(int maxLength) {
        this.maxLength = maxLength;
    }

    public ArrayList<RegExSymbol> parseRegEx(String regEx) throws InvalidRegExException {
        ArrayList<RegExSymbol> symbolArray = new ArrayList<>();
        int index = 0;
        while (index < regEx.length()) {
            this.checkInvalidSymbol(regEx.charAt(index));
            if (regEx.charAt(index) == '[') {
                symbolArray.add(this.createSet(regEx, index));
                index = regEx.indexOf(']', index) + 1;
            } else {
                symbolArray.add(this.createIndividual(regEx, index));
                if (regEx.charAt(index) == '\\') {
                    ++index;
                }
                ++index;
            }
            if (symbolArray.get(symbolArray.size() - 1).hasQuantifier()) {
                ++index;
            }
        }
        return symbolArray;
    }

    private RegExSymbol createIndividual(String regEx, int index) {
        RegExQuantifier quantifier = this.getQuantifier(regEx, index);
        if (regEx.charAt(index) == '.') {
            return new RegExSymbol('.', quantifier.getQuantity(), true, quantifier.isValid(), null);
        } else if (regEx.charAt(index) == '\\') {
            return new RegExSymbol(this.getChar(regEx, index + 1), quantifier.getQuantity(), false, quantifier.isValid(), null);
        } else {
            return new RegExSymbol(regEx.charAt(index), quantifier.getQuantity(), false, quantifier.isValid(), null);
        }
    }

    private RegExSymbol createSet(String regEx, int index) {
        RegExQuantifier quantifier = this.getQuantifier(regEx, index);
        String chars = this.getSetChars(regEx, index);
        return new RegExSymbol('[', quantifier.getQuantity(), false, quantifier.isValid(), chars);
    }

    private String getSetChars(String regEx, int index) {
        return regEx.substring(index + 1, regEx.indexOf(']', index));
    }

    private RegExQuantifier getQuantifier(String regEx, int index) {
        if (regEx.charAt(index) == '[') {
            return new RegExQuantifier(this.getChar(regEx, regEx.indexOf(']', index) + 1), this.maxLength);
        } else if (regEx.charAt(index) != '\\') {
            return new RegExQuantifier(this.getChar(regEx, index + 1), this.maxLength);
        } else {
            return new RegExQuantifier(this.getChar(regEx, index + 2), this.maxLength);
        }
    }

    private char getChar(String chain, int index) {
        if (index < chain.length()) {
            return chain.charAt(index);
        }
        return 0;
    }

    private void checkInvalidSymbol(char symbol) throws InvalidRegExException {
        if (Character.toString(symbol).matches("[\\*\\+\\?\\]]")) {
            throw new InvalidRegExException();
        }
    }

}
