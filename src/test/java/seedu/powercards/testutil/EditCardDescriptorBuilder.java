package seedu.powercards.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.powercards.logic.commands.cardcommands.EditCommand;
import seedu.powercards.logic.commands.cardcommands.EditCommand.EditCardDescriptor;
import seedu.powercards.model.card.Answer;
import seedu.powercards.model.card.Card;
import seedu.powercards.model.card.Question;
import seedu.powercards.model.deck.Deck;
import seedu.powercards.model.tag.Tag;

/**
 * A utility class to help with building EditCardDescriptor objects.
 */
public class EditCardDescriptorBuilder {

    private EditCommand.EditCardDescriptor descriptor;

    public EditCardDescriptorBuilder() {
        descriptor = new EditCommand.EditCardDescriptor();
    }

    public EditCardDescriptorBuilder(EditCommand.EditCardDescriptor descriptor) {
        this.descriptor = new EditCommand.EditCardDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditCardDescriptor} with fields containing {@code card}'s details
     */
    public EditCardDescriptorBuilder(Card card) {
        descriptor = new EditCardDescriptor();
        descriptor.setQuestion(card.getQuestion());
        descriptor.setAnswer(card.getAnswer());
        descriptor.setTags(card.getTags());
    }

    /**
     * Sets the {@code Question} of the {@code EditCardDescriptor} that we are building.
     */
    public EditCardDescriptorBuilder withQuestion(String question) {
        descriptor.setQuestion(new Question(question));
        return this;
    }

    /**
     * Sets the {@code Answer} of the {@code EditCardDescriptor} that we are building.
     */
    public EditCardDescriptorBuilder withAnswer(String answer) {
        descriptor.setAnswer(new Answer(answer));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditCardDescriptor}
     * that we are building.
     */
    public EditCardDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    /**
     * Sets the {@code Deck} of the {@code EditCardDescriptor} that we are building.
     */
    public EditCardDescriptorBuilder withDeck(Deck deck) {
        descriptor.setDeck(deck);
        return this;
    }

    public EditCommand.EditCardDescriptor build() {
        return descriptor;
    }
}
