package sample;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.junit.After;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class GameTest {
    Game game;


    /*Scenario 1 */
    @Given("^My game is created$")
    public void CreateGame()
    {
        this.game = new Game();
        assertEquals(game.playerTurn > 0, true);
        assertEquals(game.deck.size(), Game.NB_CARD_DECK);
        assertEquals(game.playerList.get(0).getHand().size(), Game.NB_CARD);
        assertEquals(game.playerList.get(1).getHand().size(), Game.NB_CARD);
        assertEquals(game.playerList.get(0).getKingdom().size(), Game.NB_CARD);
        assertEquals(game.playerList.get(1).getKingdom().size(), Game.NB_CARD);
    }

    @Then("^Player 1 draw 5 card$")
    public void drawCardPlayer1() throws Exception {
        game.DrawMultipleCard(0,5);
        assertEquals(game.deck.size(), Game.NB_CARD_DECK-5);
    }

    @And("^Player 2 draw 5 card$")
    public void drawCardPlayer2() throws Exception {
        game.DrawMultipleCard(1,5);
        assertEquals(game.deck.size(), Game.NB_CARD_DECK-10);
    }

    /*Scenario 2 */
    @Given("^My game is started$")
    public void StartGame() throws Exception {
        this.game = new Game();
        game.DrawMultipleCard(0,5);
        game.DrawMultipleCard(1,5);
    }

    @Then("^Random play turn")
    public void playTurn()
    {
        int nb_card_deck = game.deck.size();
        int index = 0;
        Card cardDrawed = game.deck.get(game.deck.size()-1);
        int indexHandDraw = ListCard.NextEmptyIndex(game.playerList.get(game.playerTurn).getCardHand());
        try {
            game.DrawCard(game.playerTurn, indexHandDraw);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Check if the size of the deck is reduced when a card is drawed
        assertTrue(game.deck.size() < nb_card_deck);
        //Check if the card drawed is the same with the new card in the hand
        assertEquals(cardDrawed, game.playerList.get(game.playerTurn).getHand().get(indexHandDraw));


        int indexHand = ListCard.NextFillIndex(game.playerList.get(game.playerTurn).getCardHand());
        int indexKingdom = ListCard.NextEmptyIndex(game.playerList.get(game.playerTurn).getCardKingdom());
        Card cardPlayed = game.playerList.get(0).putCard(indexHand,indexKingdom);
        int playerTurn = game.playerTurn;


        //Play turn
        game.PlayTurnRandom();


        //test if true

        assertTrue(nb_card_deck > game.deck.size());
        //assertTrue()
    }

    /*Scenario 3 */
    @Given("^My game will reset kingdom$")
    public void MidGame() throws Exception {
        this.game = new Game();
        game.DrawMultipleCard(0,5);
        game.DrawMultipleCard(1,5);
        //Just before a reset kingdom
        game.PlayMultipleTurnRandom((Game.NB_CARD*2)-1);
    }

    @Then("^Play a turn and reset$")
    public void TurnToResetGame()
    {
        int player = Game.Opponent(game.playerTurn);
        //Number of empty card in kingdom for the first player
        int nbEmptyCardKingdom = ListCard.EmptyIndex(game.playerList.get(player).getCardKingdom()).size();
        assertEquals(nbEmptyCardKingdom, 0);//Should be empty of default card
        game.PlayMultipleTurnRandom(1);
        //Number of empty card in kingdom for the first player, after the reset
        int nbEmptyCardKingdom2 = ListCard.EmptyIndex(game.playerList.get(player).getCardKingdom()).size();
        assertEquals(nbEmptyCardKingdom2, Game.NB_CARD);//Should be full
    }
}