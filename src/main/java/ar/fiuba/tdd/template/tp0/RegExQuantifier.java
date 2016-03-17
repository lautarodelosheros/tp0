package ar.fiuba.tdd.template.tp0;

public class RegExQuantifier {

    private char symbol;
    private int maxLength;

    public RegExQuantifier(char symbol, int maxLength) {
        this.symbol = symbol;
        this.maxLength = maxLength;
    }

    public boolean isValid() {
        return Character.toString(this.symbol).matches("[\\*\\+\\?]");
    }

    public int getQuantity() {
        switch (this.symbol) {
            case '*':
                return RandomGenerator.getRandomRange(0, this.maxLength + 1);
            case '+':
                return RandomGenerator.getRandomRange(1, this.maxLength + 1);
            case '?':
                return RandomGenerator.getRandomRange(0, 2);
            default:
                return 1;
        }
    }

}
