package seedu.address.logic.commands.cardcommands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.CardBuilder.DEFAULT_DECK;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.commandresult.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.MasterDeck;
import seedu.address.model.ModelStub;
import seedu.address.model.ReadOnlyMasterDeck;
import seedu.address.model.card.Card;
import seedu.address.model.deck.Deck;
import seedu.address.testutil.AddCardDescriptorBuilder;
import seedu.address.testutil.CardBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullCard_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_cardAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingCardAdded modelStub = new ModelStubAcceptingCardAdded();
        Card validCard = new CardBuilder().build(); // Default Card
        AddCardDescriptorBuilder cardDescBuilder = new AddCardDescriptorBuilder(validCard);
        CommandResult commandResult = new AddCommand(cardDescBuilder.build()).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validCard), commandResult.getFeedbackToUser());
        assertEquals(List.of(validCard), modelStub.cardsAdded);
    }

    @Test
    public void execute_duplicateCard_throwsCommandException() {
        ModelStub modelStub = new ModelStubWithDuplicatedCard();
        Card validCard = new CardBuilder().build();
        AddCardDescriptorBuilder cardDescBuilder = new AddCardDescriptorBuilder(validCard);
        AddCommand addCommand = new AddCommand(cardDescBuilder.build());
        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_CARD, () -> addCommand.execute(modelStub));
    }

    @Test
    public void execute_noSelectedDeck_throwsCommandException() {
        ModelStub modelStub = new ModelStubWithoutSelectedDeck();
        Card validCard = new CardBuilder().build();
        AddCardDescriptorBuilder cardDescBuilder = new AddCardDescriptorBuilder(validCard);
        AddCommand addCommand = new AddCommand(cardDescBuilder.build());
        assertThrows(AssertionError.class, AddCommand.MESSAGE_NO_SELECTED_DECK, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        AddCommand.AddCardDescriptor questionGravity = new AddCardDescriptorBuilder()
                .withQuestion("What is gravity").withAnswer("Not sure").withTag("easy").build();
        AddCommand.AddCardDescriptor questionPhoto = new AddCardDescriptorBuilder()
                .withQuestion("What is photosynthesis").withAnswer("Not sure either").withTag("hard").build();
        AddCommand addACommand = new AddCommand(questionGravity);
        AddCommand addBCommand = new AddCommand(questionPhoto);

        // same object -> returns true
        assertEquals(addACommand, addACommand);

        // same values -> returns true
        AddCommand addGravityCommandCopy = new AddCommand(questionGravity);
        assertEquals(addACommand, addGravityCommandCopy);

        // different types -> returns false
        assertNotEquals(1, addACommand);

        // null -> returns false
        assertNotEquals(null, addACommand);

        // different card -> returns false
        assertNotEquals(addACommand, addBCommand);
    }

    /**
     * A Model stub that contains a duplicated card.
     */
    private static class ModelStubWithDuplicatedCard extends ModelStub {
        final Deck selectedDeck = new Deck(DEFAULT_DECK);

        ModelStubWithDuplicatedCard() {
        }

        @Override
        public Optional<Deck> getSelectedDeck() {
            return Optional.of(selectedDeck);
        }

        @Override
        public boolean hasCard(Card card) {
            return true;
        }
    }

    /**
     * A Model stub that always accept the card being added.
     * The selectedDeck is always the DEFAULT_DECK
     */
    private static class ModelStubAcceptingCardAdded extends ModelStub {
        final Deck selectedDeck = new Deck(DEFAULT_DECK);
        final ArrayList<Card> cardsAdded = new ArrayList<>();

        @Override
        public Optional<Deck> getSelectedDeck() {
            return Optional.of(selectedDeck);
        }

        @Override
        public boolean hasCard(Card card) {
            requireNonNull(card);
            return cardsAdded.stream().anyMatch(card::isSameCard);
        }

        @Override
        public void addCard(Card card) {
            requireNonNull(card);
            cardsAdded.add(card);
        }

        @Override
        public ReadOnlyMasterDeck getMasterDeck() {
            return new MasterDeck();
        }
    }

    /**
     * A Model stub that does not have any deck selected.
     */
    private static class ModelStubWithoutSelectedDeck extends ModelStub {

        @Override
        public boolean hasCard(Card card) {
            return false;
        }

        @Override
        public Optional<Deck> getSelectedDeck() {
            return Optional.empty();
        }
    }

}