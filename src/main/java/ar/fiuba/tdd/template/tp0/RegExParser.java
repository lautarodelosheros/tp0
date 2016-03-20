package ar.fiuba.tdd.template.tp0;

import java.util.ArrayList;

public class RegExParser {

    private int maxLength;

    public RegExParser(int maxLength) {
        this.maxLength = maxLength;
    }

    public ArrayList<RegExSymbol> parseRegEx(String regEx) throws InvalidRegExException {
        ArrayList<RegExSymbol> symbolList = new ArrayList<>();
        int index = 0;
        while (index < regEx.length()) {
            this.checkInvalidSymbol(regEx.charAt(index));
            RegExQuantifier quantifier = this.getQuantifier(regEx, index);
            if (regEx.charAt(index) == '[') {
                symbolList.add(this.createSet(regEx, quantifier, index));
                index = this.getSetClosure(regEx, index) + 1;
            } else {
                symbolList.add(this.createIndividual(regEx, quantifier, index));
                if (regEx.charAt(index) == '\\') {
                    ++index;
                }
                ++index;
            }
            if (symbolList.get(symbolList.size() - 1).hasQuantifier()) {
                ++index;
            }
        }
        return symbolList;
    }

    private RegExSymbol createIndividual(String regEx, RegExQuantifier quantifier, int index) throws InvalidRegExException {
        if (regEx.charAt(index) == '.') {
            return new RegExSymbol('.', quantifier, true, null);
        } else if (regEx.charAt(index) == '\\') {
            return new RegExSymbol(this.getEscapedChar(regEx, index + 1), quantifier, false, null);
        } else {
            return new RegExSymbol(regEx.charAt(index), quantifier, false, null);
        }
    }

    private char getEscapedChar(String chain, int index) throws InvalidRegExException {
        if (index >= chain.length()) {
            throw new InvalidRegExException();
        }
        return chain.charAt(index);
    }

    private RegExSymbol createSet(String regEx, RegExQuantifier quantifier, int index) throws InvalidRegExException {
        String chars = this.getSetChars(regEx, index);
        return new RegExSymbol('[', quantifier, false, chars);
    }

    private String getSetChars(String regEx, int index) throws InvalidRegExException {
        int closeIndex = this.getSetClosure(regEx, index);
        StringBuilder setChars = new StringBuilder(regEx.substring(index + 1, closeIndex));
        this.checkSetIsNotEmpty(setChars);
        for (int i = 0 ; i < setChars.length() ; ++i) {
            if (setChars.charAt(i) == '[' || setChars.charAt(i) == ']') {
                throw new InvalidRegExException();
            } else if (setChars.charAt(i) == '\\') {
                setChars.deleteCharAt(i);
            }
        }
        return setChars.toString();
    }

    private int getSetClosure(String regEx, int index) throws InvalidRegExException {
        int closeIndex = index;
        boolean found = false;
        while (!found) {
            closeIndex = regEx.indexOf(']', closeIndex);
            if (closeIndex == -1) {
                throw new InvalidRegExException();
            } else if (regEx.charAt(closeIndex - 1) == '\\') {
                ++closeIndex;
            } else {
                found = true;
            }
        }
        return closeIndex;
    }

    private void checkSetIsNotEmpty(StringBuilder chain) throws InvalidRegExException {
        if (chain.length() == 0) {
            throw new InvalidRegExException();
        }
    }

    private RegExQuantifier getQuantifier(String regEx, int index) throws InvalidRegExException {
        if (regEx.charAt(index) == '[') {
            return new RegExQuantifier(this.getChar(regEx, this.getSetClosure(regEx, index) + 1), this.maxLength);
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
