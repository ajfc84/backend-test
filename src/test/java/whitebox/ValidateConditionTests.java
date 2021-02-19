package whitebox;

import com.axreng.backend.Validate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ValidateConditionTests {
    @Test
    public void idLengthIs8(){
        Assertions.assertTrue(Validate.isValidId("12345678"));
    }

    @Test
    public void idLengthIsNot8(){
        Assertions.assertFalse(Validate.isValidId("12348"));
    }

    @Test
    public void isLengthIs8ButNotInteger() {
        Assertions.assertFalse(Validate.isValidId("a2345678"));
    }

    @Test
    public void isKeywordLengthBetween4And32() {
        Assertions.assertTrue(Validate.isValidKeyword("hello"));
    }

    @Test
    public void keywordLengthCannotBe4() {
        Assertions.assertFalse(Validate.isValidKeyword("hell"));
    }

    @Test
    public void keywordLengthCannotBeGreaterThan32() {
        Assertions.assertFalse(Validate.isValidKeyword("HelloWorldWithConditionCoverage!!"));
    }
}
