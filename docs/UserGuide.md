---
layout: page
title: User Guide
---

PowerCards (PCs) is a **desktop app for managing contacts, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, PCs can get your contact management tasks done faster than traditional GUI apps.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

# Quick start

1. Ensure you have Java `11` or above installed in your Computer.

2. Download the latest `powercards.jar` from [here]().

3. Copy the file to the folder you want to use as the _home folder_ for your PCs.

4. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar powercards.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

5. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>

6. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

# Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add q\QUESTION`, `QUESTION` is a parameter which can be used as `add q\What is chemical symbol for Oxygen?`.

* If a parameter is expected only once in the command but you specified it multiple times, only the last occurrence of the parameter will be taken.<br>
  e.g. if you specify `d\science d\chemsitry`, only `d\chemsitry` will be taken.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

</div>

--------------------------------------------------------------------------------------------------------------------

## Main Mode

Main mode will be started by default when the program is launched. 
Main mode refers to when no deck is selected and the user wishes to manage their list of decks.

### Viewing help : `help`

Shows a message explaning how to access the help page.

[//]: # (![help message]&#40;images/helpMessage.png&#41;)

Format: `help`

### Adding a new Deck : `addDeck`

Adds a new deck.

Format: `addDeck DECK_NAME`

Examples:
* `addDeck Science`

### Editing a Deck : `editDeck`

Edits the name of a deck.

Format: `editDeck INDEX d\ DECK_NAME`

### Deleting a Deck : `deleteDeck`

Deletes an existing Deck in the masterDeck.

Format: `deleteDeck INDEX`

* Deletes the card at the specified `INDEX`. The index refers to the index number shown in the displayed card list. The index **must be a positive integer** 1, 2, 3, …​

### Selecting a Deck : `selectDeck`

Selects a deck from the list of decks. 

Format: `selectDeck INDEX`

Examples:
* `selectDeck 2`

### Reviewing a masterDeck: `review`

Begins reviewing the **selected** Deck. 
Changes current mode to Review Mode.

Format: `review INDEX`

Examples:
* `review 2`

## Deck Mode

Deck mode refers to when a deck is selected and the user wishes to manage the list of cards within this selected deck.

### Unselecting a Deck : `unselectDeck`

Unselects the currently selected deck and return to Main mode.

Format: `unselectDeck`

Examples:
* `unselectDeck`


### Adding a Powercard: `add`

Adds a Powercard to the **selected** Deck.

Format: `add q\QUESTION a\ANSWER t\TAG`

<!-- <div markdown="span" class="alert alert-primary">:bulb: **Tip:**
Tagging is optional and should be of value Easy, Medium, or Hard.
</div> -->

Examples:
* `add q\What is chemical symbol for Oxygen? a\O`
* `add q\What is gravity? a\A force of attraction between objects due to their mass t\Easy`

### Listing all Powercards : `list`

Shows a list of all Powercards in the **selected** Deck.

Format: `list`

### Editing a Powercard : `edit`

Edits an existing Powercard in the **selected** Deck. 

Format: `edit INDEX [q\QUESTION] [a\ANSWER] [t\TAG]`

* Edits the card at the specified `INDEX`. The index refers to the index number shown in the displayed card list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.

Examples:
*  `edit 1 q\What is chemical symbol for Carbon? a\C` Edits the question and answer of the 1st Powercard to be `What is chemical symbol for Oxygen?` and `C` respectively.

### Deleting a Powercard : `delete`

Deletes an existing Powercard in the **selected** Deck.

Format: `delete INDEX`

* Deletes the card at the specified `INDEX`. The index refers to the index number shown in the displayed card list. The index **must be a positive integer** 1, 2, 3, …​

### Reviewing a masterDeck: `review`

Begins reviewing the **selected** Deck.
Changes current mode to Review Mode.

Format: `review INDEX`

Examples:
* `review 2`

### Setting the number of cards per review: `setNumCardsPerReview`

Sets the number of cards per review to a non-zero positive integer. After setting, only the entered number of cards will appear in future reviews.
Setting it to none sets back to view all cards in future reviews.

Format: `setNumCardsPerReview LIMIT_NUM` or `setNumCardsPerReview none`

Examples:
* `setNumCardsPerReview 30`
* `setNumCardsPerReview none`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Clearing the data : `clear`

Clears existing decks and cards. 

Format: `clear`

--------------------------------------------------------------------------------------------------------------------

## Review Mode

Review mode is started when `review INDEX` has been entered on the main mode.

### Ending the Review: `endReview`

Ends the review.
Changes current mode to Deck mode.

Format: `endReview`

### Next Card: `]`

Displays the next Powercard.

Format: `]`

### Previous Card: `[`

Displays the previous Powercard.

Format: `[`

### Flipping the Powercard: `\ `

Flips the Powercard to check the answer.

Format: `\ `

### Marking the Powercard as correct: `'`

Marks the current Powercard as correct.

Format: `'`

### Marking the Powercard as wrong: `;`

Marks the current PowerCard as wrong.

Format: `;`

### Tagging a Powercard: `tag`

Tags the current PowerCard as either Easy, Medium, or Hard.

Format: `tag ENUM`

Examples:
* `tag Easy`
* `tag hard`


--------------------------------------------------------------------------------------------------------------------

### Saving the data

PCs data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

PCs data are saved as a JSON file `[JAR file location]/data/masterdeck.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, PCs will discard all data and start with an empty data file at the next run.
</div>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._


--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous PCs home folder.

--------------------------------------------------------------------------------------------------------------------

## Command summary

### Main mode

| Action      | Format, Examples                                                        |
|-------------|-------------------------------------------------------------------------|
| Add deck    | `addDeck DECK_NAME` <br /> e.g., `addDeck Science`                      |
| Edit deck   | `editDeck d\DECK_NAME` <br /> e.g., `editDeck d\Physics`                |
| Select Deck | `selectDeck INDEX` <br /> e.g., `selectDeck 2`                          |
| Delete Deck | `deleteDeck INDEX`                                                      |
| Review      | `review`                                                                |
| Set limit   | `setNumCardsPerReview LIMIT_NUM` <br /> e.g., `setNumCardsPerReview 30` |
| Clear       | `clear`                                                                 |
| Help        | `help`                                                                  |
| Exit        | `exit`                                                                  |

### Deck mode

| Action        | Format, Examples                                                                                                                         |
|---------------|------------------------------------------------------------------------------------------------------------------------------------------|
| Select Deck   | `selectDeck INDEX` <br /> e.g., `selectDeck 2`                                                                                           |
| Unselect Deck | `unselect`                                                                                                                               |
| Add Card      | `add q\QUESTION a\ANSWER [t\TAG]` <br /> e.g., `add q\What is gravity? a\A force of attraction between objects due to their mass t\Easy` |
| Edit Card     | `edit INDEX [q\QUESTION] [a\ANSWER] [t\TAG]` <br /> e.g., `edit 1 q\What is chemical symbol for Caarbon? a\C t\Hard`                     |
| Delete Card   | `delete INDEX`                                                                                                                           |
| Review        | `review`                                                                                                                                 |
| Set limit     | `setNumCardsPerReview LIMIT_NUM` <br /> e.g., `setNumCardsPerReview 30`                                                                  |
| Exit          | `exit`                                                                                                                                   |

### Review mode

| Action        | Format, Examples |
|---------------|------------------|
| End Review    | `endReview`      |
| Previous Card | `[`              |
| Next Card     | `]`              |
| Flip          | `\`              |
| Correct       | `'`              |
| Wrong         | `;`              |
| Tag           | `tag`            |
| Exit          | `exit`           |