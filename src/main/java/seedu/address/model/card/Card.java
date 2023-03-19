package seedu.address.model.card;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.model.deck.Deck;
import seedu.address.model.tag.Tag;

/**
 * Represents a Card in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Card {

    // Identity fields
    private final Question question;
    private final Answer answer;


    // Data fields
    private final Set<Tag> tags = new HashSet<>();
    private Deck deck;
    private boolean isFlipped = true;

    /**
     * Every field must be present and not null.
     */
    public Card(Question question, Answer answer, Set<Tag> tags, Deck deck) {
        requireAllNonNull(question, answer, tags);
        this.question = question;
        this.answer = answer;
        this.tags.addAll(tags);
        this.deck = deck;
    }

    public Question getQuestion() {
        return question;
    }

    public Answer getAnswer() {
        return answer;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public Optional<Deck> getDeck() {
        return Optional.of(deck);
    }

    public void setDeck(Deck newDeck) {
        this.deck = newDeck;
    }

    /**
     * Returns true if both persons have the same question.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameCard(Card otherCard) {
        if (otherCard == this) {
            return true;
        }

        return otherCard != null
                && otherCard.getQuestion().equals(getQuestion())
                && otherCard.getAnswer().equals(getAnswer());
    }

    /**
     * Checks if the card belongs to a given deck.
     *
     * @param deck The deck to check if card belongs to.
     * @return true if card belongs to the deck.
     */
    public boolean isInDeck(Deck deck) {
        return this.deck.equals(deck);
    }

    /* ============================== FOR REVIEW FUNCTIONS ============================== */

    /**
     * Returns true if the card is flipped.
     */
    public boolean isFlipped() {
        return isFlipped;
    }

    /**
     * Sets card as flipped.
     */
    public void setAsFlipped() {
        this.isFlipped = true;
    }

    /**
     * Sets card as unflipped.
     */
    public void setAsUnflipped() {
        this.isFlipped = false;
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Card)) {
            return false;
        }

        Card otherCard = (Card) other;
        return otherCard.getQuestion().equals(getQuestion())
                && otherCard.getAnswer().equals(getAnswer())
                && otherCard.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(question, answer, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getQuestion())
                .append("; Answer: ")
                .append(getAnswer());

        Set<Tag> tags = getTags();
        if (!tags.isEmpty()) {
            builder.append("; Tags: ");
            tags.forEach(builder::append);
        }
        return builder.toString();
    }

}
