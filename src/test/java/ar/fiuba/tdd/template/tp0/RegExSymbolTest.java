package ar.fiuba.tdd.template.tp0;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RegExSymbolTest {

    @Test
    public void testHasQuantifierWithValidQuantifier() {
        RegExQuantifier quantifier = new RegExQuantifier('*', 10);
        RegExSymbol symbol = new RegExSymbol('a', quantifier, false, null);
        assertTrue(symbol.hasQuantifier());
    }

    @Test
    public void testHasQuantifierWithInvalidQuantifier() {
        RegExQuantifier quantifier = new RegExQuantifier('a', 10);
        RegExSymbol symbol = new RegExSymbol('a', quantifier, false, null);
        assertFalse(symbol.hasQuantifier());
    }

    @Test
    public void testMatchNoDotNoSetNoQuantifier() {
        RegExQuantifier quantifier = new RegExQuantifier('a', 10);
        RegExSymbol symbol = new RegExSymbol('a', quantifier, false, null);
        assertTrue(symbol.generateMatch().equals("a"));
    }

    @Test
    public void testMatchDotNoQuantifier() {
        RegExQuantifier quantifier = new RegExQuantifier('a', 10);
        RegExSymbol symbol = new RegExSymbol('a', quantifier, true, null);
        char match = symbol.generateMatch().charAt(0);
        assertTrue(match != 10 && match != 13 && match != 133);
    }

}
