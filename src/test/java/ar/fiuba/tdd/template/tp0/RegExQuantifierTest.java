package ar.fiuba.tdd.template.tp0;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RegExQuantifierTest {

    @Test
    public void testValidAsterisk() {
        RegExQuantifier quantifier = new RegExQuantifier('*', 100);
        assertTrue(quantifier.isValid());
    }

    @Test
    public void testValidPlus() {
        RegExQuantifier quantifier = new RegExQuantifier('+', 100);
        assertTrue(quantifier.isValid());
    }

    @Test
    public void testValidQuestion() {
        RegExQuantifier quantifier = new RegExQuantifier('?', 100);
        assertTrue(quantifier.isValid());
    }

    @Test
    public void testInvalidQuantifier() {
        RegExQuantifier quantifier = new RegExQuantifier('@', 100);
        assertFalse(quantifier.isValid());
    }

    @Test
    public void testAsteriskQuantity() {
        RegExQuantifier quantifier = new RegExQuantifier('*', 100);
        int quantity = quantifier.getQuantity();
        assertTrue(quantity >= 0 && quantity <= 100);
    }

    @Test
    public void testPlusQuantity() {
        RegExQuantifier quantifier = new RegExQuantifier('+', 100);
        int quantity = quantifier.getQuantity();
        assertTrue(quantity > 0 && quantity <= 100);
    }

    @Test
    public void testQuestionQuantity() {
        RegExQuantifier quantifier = new RegExQuantifier('?', 100);
        int quantity = quantifier.getQuantity();
        assertTrue(quantity >= 0 && quantity <= 1);
    }

    @Test
    public void testInvalidQuantity() {
        RegExQuantifier quantifier = new RegExQuantifier('@', 100);
        int quantity = quantifier.getQuantity();
        assertTrue(quantity == 1);
    }

}
