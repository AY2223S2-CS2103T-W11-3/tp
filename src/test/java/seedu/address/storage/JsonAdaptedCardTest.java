package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedCard.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalCards.VARIABLE;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.card.Answer;
import seedu.address.model.card.Question;

public class JsonAdaptedCardTest {
    private static final String INVALID_QUESTION = " ";
    private static final String INVALID_ANSWER = " ";
    private static final String INVALID_TAG = "#Hard";

    private static final String VALID_QUESTION = VARIABLE.getQuestion().toString();
    private static final String VALID_ANSWER = VARIABLE.getAnswer().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = VARIABLE.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());
    private static final String VALID_DECK = VARIABLE.getDeck().toString();
    @Test
    public void toModelType_validCardDetails_returnsCard() throws Exception {
        JsonAdaptedCard card = new JsonAdaptedCard(VARIABLE);
        assertEquals(VARIABLE, card.toModelType());
    }

    @Test
    public void toModelType_invalidQuestion_throwsIllegalValueException() {
        JsonAdaptedCard card =
                new JsonAdaptedCard(INVALID_QUESTION, VALID_ANSWER, VALID_TAGS, VALID_DECK);
        String expectedMessage = Question.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, card::toModelType);
    }

    @Test
    public void toModelType_nullQuestion_throwsIllegalValueException() {
        JsonAdaptedCard card = new JsonAdaptedCard(null, VALID_ANSWER, VALID_TAGS, VALID_DECK);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Question.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, card::toModelType);
    }

    @Test
    public void toModelType_invalidAnswer_throwsIllegalValueException() {
        JsonAdaptedCard card =
                new JsonAdaptedCard(VALID_QUESTION, INVALID_ANSWER, VALID_TAGS, VALID_DECK);
        String expectedMessage = Answer.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, card::toModelType);
    }

    @Test
    public void toModelType_nullAnswer_throwsIllegalValueException() {
        JsonAdaptedCard card = new JsonAdaptedCard(VALID_QUESTION, null, VALID_TAGS, VALID_DECK);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Answer.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, card::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedCard person =
                new JsonAdaptedCard(VALID_QUESTION, VALID_ANSWER, invalidTags, VALID_DECK);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

}
