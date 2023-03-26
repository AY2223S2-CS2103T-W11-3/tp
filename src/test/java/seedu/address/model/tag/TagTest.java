package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

public class TagTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tag(null));
    }

    @Test
    public void isValidTagName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));

        // valid tag name
        List<String> validTagNameTestList = List.of("easy", "medium", "hard", "easY", "MediUm", "HARD");
        for (String name : validTagNameTestList) {
            assertTrue(Tag.isValidTagName(name));
        }

        // invalid tag name
        assertFalse(Tag.isValidTagName("random"));
        assertFalse(Tag.isValidTagName("easyyy"));
        assertFalse(Tag.isValidTagName("easy medium"));
    }

}
