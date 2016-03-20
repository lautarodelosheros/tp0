package ar.fiuba.tdd.template.tp0;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertTrue;

public class RegExGeneratorTest {

    private int maxLength = 255;

    private boolean validate(String regEx, int numberOfResults) {
        RegExGenerator generator = new RegExGenerator(this.maxLength);
        ArrayList<String> results;
        try {
            results = generator.generate(regEx, numberOfResults);
        } catch (InvalidRegExException exception) {
            return false;
        }
        // force matching the beginning and the end of the strings
        Pattern pattern = Pattern.compile("^" + regEx + "$");
        return results.stream().reduce(true, (acc, item) -> {
                Matcher matcher = pattern.matcher(item);
                return acc && matcher.find();
            }, (item1, item2) -> item1 && item2);
    }


    @Test
    public void testAnyCharacter() {
        assertTrue(validate(".", 10));
    }

    @Test
    public void testMultipleCharacters() {
        assertTrue(validate("...", 10));
    }

    @Test
    public void testLiteral() {
        assertTrue(validate("\\@", 1));
    }

    @Test
    public void testLiteralDotCharacter() {
        assertTrue(validate("\\@..", 10));
    }

    @Test
    public void testZeroOrOneCharacter() {
        assertTrue(validate("\\@.h?", 10));
    }

    @Test
    public void testCharacterSet() {
        assertTrue(validate("[abc]", 10));
    }

    @Test
    public void testCharacterSetWithQuantifiers() {
        assertTrue(validate("[abc]+", 10));
    }

    @Test
    public void testAnyMultipleCharacters() {
        assertTrue(validate(".*", 1));
    }

    @Test
    public void testEscapedQuantifier() {
        assertTrue(validate("\\+", 1));
    }

    @Test
    public void testEscapedSetCharacters() {
        assertTrue(validate("\\[abc\\]", 1));
    }

    @Test
    public void testQuantifiersInSet() {
        assertTrue(validate("[.+?]*", 100));
    }

    @Test
    public void testEscapedQuantifierWithQuantifier() {
        assertTrue(validate("\\++", 1));
    }

    @Test
    public void testEscapedCharacterInsideSet() {
        assertTrue(validate("[ab\\@]", 100));
    }

    @Test
    public void testEscapedSetOpenerInsideSet() {
        assertTrue(validate("[ab\\[c]", 100));
    }

    @Test
    public void testEscapedSetCloserInsideSet() {
        assertTrue(validate("[ab\\]c]", 100));
    }

    @Test
    public void testIntegration() {
        assertTrue(validate("[AB]r?s \\;t+uv w[xy\\|XYZ]*[lL] \\*+", 100));
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testInvalidCloseSet() throws InvalidRegExException {
        thrown.expect(InvalidRegExException.class);
        RegExGenerator generator = new RegExGenerator(this.maxLength);
        generator.generate("abc]def");
    }

    @Test
    public void testInvalidOpenSet() throws InvalidRegExException {
        thrown.expect(InvalidRegExException.class);
        RegExGenerator generator = new RegExGenerator(this.maxLength);
        generator.generate("abc[def");
    }

    @Test
    public void testInvalidOpenSetInsideSet() throws InvalidRegExException {
        thrown.expect(InvalidRegExException.class);
        RegExGenerator generator = new RegExGenerator(this.maxLength);
        generator.generate("[abc[abc]");
    }

    @Test
    public void testInvalidOpenSetWithEscapedCloseSet() throws InvalidRegExException {
        thrown.expect(InvalidRegExException.class);
        RegExGenerator generator = new RegExGenerator(this.maxLength);
        generator.generate("[abc\\]d");
    }

    @Test
    public void testInvalidQuantifier() throws InvalidRegExException {
        thrown.expect(InvalidRegExException.class);
        RegExGenerator generator = new RegExGenerator(this.maxLength);
        generator.generate("*");
    }

    @Test
    public void testEmptySet() throws InvalidRegExException {
        thrown.expect(InvalidRegExException.class);
        RegExGenerator generator = new RegExGenerator(this.maxLength);
        generator.generate("[]");
    }

    @Test
    public void testInvalidEscape() throws InvalidRegExException {
        thrown.expect(InvalidRegExException.class);
        RegExGenerator generator = new RegExGenerator(this.maxLength);
        generator.generate("\\");
    }

}