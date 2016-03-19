package ar.fiuba.tdd.template.tp0;

import java.util.ArrayList;

public class RegExGenerator {

    private int maxLength;

    public RegExGenerator(int maxLength) {
        this.maxLength = maxLength;
    }

    public static void main(String [] args) {
        RegExGenerator generator = new RegExGenerator(10);
        try {
            System.out.print(generator.generate("[]", 100));
        } catch (InvalidRegExException exception) {
            System.out.print("Exception thrown: " + exception);
        }
    }

    public ArrayList<String> generate(String regEx, int numberOfResults) throws InvalidRegExException {
        ArrayList<String> output = new ArrayList<>();
        for (int i = 0 ; i < numberOfResults ; ++i) {
            output.add(this.generate(regEx));
        }
        return output;
    }

    public String generate(String regEx) throws InvalidRegExException {
        StringBuilder output = new StringBuilder();
        RegExParser parser = new RegExParser(this.maxLength);
        ArrayList<RegExSymbol> symbolList = parser.parseRegEx(regEx);
        for ( RegExSymbol symbol : symbolList) {
            output.append(symbol.generateMatch());
        }
        return output.toString();
    }

}