package ar.fiuba.tdd.template.tp0;

import java.util.ArrayList;
import java.util.List;

public class RegExGenerator {

    private int maxLength;

    public RegExGenerator(int maxLength) {
        this.maxLength = maxLength;
    }

    public static void main(String [] args) {
        RegExGenerator generator = new RegExGenerator(10);
        System.out.print(generator.generate("[abc]", 5));
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
        RegExParser parser = new RegExParser(this.maxLength);
        ArrayList<RegExSymbol> symbolList = parser.parseRegEx(regEx);
        for ( RegExSymbol symbol : symbolList) {
            output.append(symbol.generateMatch());
        }
        return output.toString();
    }

}