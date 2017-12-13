package sample;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Game {
    static int NB_CARD = 10;//Number of card in the hand and in the kingdom
    static int NB_CARD_DECK = 45;//Number of card in the deck
    List<Player> playerList;//playerList.get(0) = Player 1, playerList.get(1) = Player 2
    List<Card> deck;
    int playerTurn;//1 = Player 1, 2 = Player 2
    //Be careful about list, all of them start at 0, whereas the player start at 1
    //Animation start at 1 too

    public Game()
    {
        //Cucumber test initialization. Logic test.
        deck = CreateDeck();
        playerList = Arrays.asList(new Player(0), new Player(1));
        playerTurn = 1;
    }

    public static Card RandomCard()
    {
        Card result = null;
        Random rand = new Random();
        int n = rand.nextInt(6);
        if (n == 0) {
            result = new Dryad();
        }
        if (n == 1) {
            result = new Elf();
        }
        if (n == 2) {
            result = new Gnome();
        }
        if (n == 3) {
            result = new Goblin();
        }
        if (n == 4) {
            result = new Troll();
        }
        if (n == 5) {
            result = new Korrigan();
        }
        return result;
    }

    public boolean IsCorrectCard(int player, int indexHand, int indexKingdom)
    {
        //Check if we can put a card or not. Check if the card is not a default card on the hand (reverse) or if it one on the kingdom.
        boolean result = true;

        if(player == playerTurn)
        {
            //Check if the card is default or not
            if(playerList.get(player-1).getHand().IsDefaultCard(indexHand))
            {
                result = false;
            }
            //Check if the card is default or not
            if(!playerList.get(player-1).getKingdom().IsDefaultCard(indexKingdom))
            {
                result = false;
            }
        }
        else
        {
            result = false;
        }
        return result;
    }

    public List<Card> CreateDeck(){
        deck = new ArrayList<>();
        for (int i = 0; i < NB_CARD_DECK; i++) {
            deck.add(RandomCard());
        }
        return deck;
    }

    public Card putCard(int player, int indexHand, int indexKingdom)
    {
        return playerList.get(player-1).putCard(indexHand, indexKingdom);
    }

    public void putCardRandom(int player)
    {
        int indexHand = ListCard.NextFillIndex(playerList.get(player-1).getCardHand());
        int indexKingdom = ListCard.NextEmptyIndex(playerList.get(player-1).getCardKingdom());
        if(indexHand > 0 & indexKingdom > 0)
        {
            putCard(player, indexHand,  indexKingdom);
        }
    }

    public int NextCardHand()
    {
        return ListCard.NextFillIndex(playerList.get(playerTurn-1).getCardHand());
    }

    public int NextEmptyPlaceKingdom()
    {
        return ListCard.NextEmptyIndex(playerList.get(playerTurn-1).getCardKingdom());
    }

    public void DrawCard(int player, int indexHand) throws InterruptedException {
        playerList.get(player-1).DrawCard(deck, indexHand);
    }

    public void DrawMultipleCard(int player, int nb) throws InterruptedException {
        for(int i = 0; i < nb;i++)
        {
            int indexHand = ListCard.FindIndex(i);
            DrawCard(player,indexHand);
        }
    }

    public void ChangeTurnWithoutInterface()
    {
        if(playerTurn == 1)
        {
            playerTurn = 2;
        }
        else
        {
            playerTurn = 1;
        }
        //First index free, starting from the middle to the edge of the platform
        int indexHand = ListCard.NextEmptyIndex(playerList.get(playerTurn-1).getHand().getListOfCards());
        //Draw card if we have some place in the list card
        if(indexHand > -1)
        {
            try {
                DrawCard(playerTurn,indexHand);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void PlayTurnRandom()
    {
        putCardRandom(playerTurn);
        ChangeTurnWithoutInterface();
    }

    public void ChangeTurn()
    {
        if(playerTurn == 1)
        {
            playerTurn = 2;
        }
        else
        {
            playerTurn = 1;
        }
    }

    public boolean ShouldDrawCard()
    {
        boolean result = false;
        //First index free, starting from the middle to the edge of the platform
        int indexHand = ListCard.NextEmptyIndex(playerList.get(playerTurn-1).getCardHand());

        //Check if we have some place in the list card
        if(indexHand > -1)
        {
            result = true;
        }

        if(deck.size() < 1)
        {
            result = false;
        }
        return result;
    }

    public int NextEmptyIndex()
    {
        return ListCard.NextEmptyIndex(playerList.get(playerTurn-1).getCardHand());
    }

    public boolean ShouldResetKingdom()
    {
        boolean result = false;
        int indexKingdom = ListCard.NextEmptyIndex(playerList.get(playerTurn-1).getCardKingdom());
        //Clear kingdom if complete
        if(indexKingdom < 0)
        {
            result = true;
        }
        return result;
    }

    public void ResetKingdom()
    {
        playerList.get(playerTurn-1).ResetKingdom();
    }

    @Override
    public String toString() {
        return "Player turn : " + playerTurn +
                "\nPlayer1:\n" + playerList.get(0).toString() +
                "\nPlayer2:\n" + playerList.get(1).toString();
    }
}
