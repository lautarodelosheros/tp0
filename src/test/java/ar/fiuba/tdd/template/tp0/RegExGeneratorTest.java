package ar.fiuba.tdd.template.tp0;

import org.junit.Test;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertTrue;

public class RegExGeneratorTest {

    private boolean validate(String regEx, int numberOfResults) {
        RegExGenerator generator = new RegExGenerator(256);
        List<String> results = generator.generate(regEx, numberOfResults);
        // force matching the beginning and the end of the strings
        Pattern pattern = Pattern.compile("^" + regEx + "$");
        return results.stream().reduce(true, (acc, item) -> {
                Matcher matcher = pattern.matcher(item);
                return acc && matcher.find();
            }, (item1, item2) -> item1 && item2);
    }


    @Test
    public void testAnyCharacter() {
        assertTrue(validate(".", 1));
    }

    @Test
    public void testMultipleCharacters() {
        assertTrue(validate("...", 1));
    }

    @Test
    public void testLiteral() {
        assertTrue(validate("\\@", 1));
    }

    @Test
    public void testLiteralDotCharacter() {
        assertTrue(validate("\\@..", 1));
    }

    @Test
    public void testZeroOrOneCharacter() {
        assertTrue(validate("\\@.h?", 1));
    }

    @Test
    public void testCharacterSet() {
        assertTrue(validate("[abc]", 1));
    }

    @Test
    public void testCharacterSetWithQuantifiers() {
        assertTrue(validate("[abc]+", 1));
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
        assertTrue(validate("[.+?]*", 1));
    }

    @Test
    public void testEscapedQuantifierWithQuantifier() {
        assertTrue(validate("\\++", 1));
    }

    @Test
    public void testIntegration() {
        assertTrue(validate("[AB]r?s t+uv w[xyzXYZ]*[lL] \\*+", 10));
    }

    // TODO: Add more tests!!!
}
