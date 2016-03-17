package ar.fiuba.tdd.template.tp0;

import java.util.ArrayList;

public class RegExParser {

    private int maxLength;

    public RegExParser(int maxLength) {
        this.maxLength = maxLength;
    }

    public ArrayList<RegExSymbol> parseRegEx(String regEx) {
        ArrayList<RegExSymbol> symbolArray = new ArrayList<>();
        int index = 0;
        while (index < regEx.length()) {
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
        boolean hasQuantifier = false;
        if (quantifier.isValid()) {
            hasQuantifier = true;
        }
        if (regEx.charAt(index) == '.') {
            return new RegExSymbol('.', quantifier.getQuantity(), true, hasQuantifier, null);
        } else if (regEx.charAt(index) == '\\') {
            return new RegExSymbol(this.getChar(regEx, index + 1), quantifier.getQuantity(), false, hasQuantifier, null);
        } else {
            return new RegExSymbol(regEx.charAt(index), quantifier.getQuantity(), false, hasQuantifier, null);
        }
    }

    private RegExSymbol createSet(String regEx, int index) {
        RegExQuantifier quantifier = this.getQuantifier(regEx, index);
        boolean hasQuantifier = false;
        if (quantifier.isValid()) {
            hasQuantifier = true;
        }
        String chars = this.getSetChars(regEx, index);
        return new RegExSymbol('[', quantifier.getQuantity(), false, hasQuantifier, chars);
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

}
