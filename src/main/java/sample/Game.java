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

    public static int Opponent(int player)
    {
        int result;
        if(player == 0)//Change to opponent
        {
            result = 1;
        }
        else
        {
            result = 0;
        }

        return result;
    }

    public List<Card> CreateDeck(){
        deck = new ArrayList<>();
        for (int i = 0; i < NB_CARD_DECK; i++) {
            deck.add(Card.RandomCard());
        }
        return deck;
    }

    public boolean IsCorrectCard(int player, int indexHand, int indexKingdom)
    {
        //Check if we can put a card or not. Check if the card is not a default card on the hand (reverse) or if it one on the kingdom.
        boolean result = true;

        if(player == playerTurn)
        {
            //Check if the card is default or not
            if(playerList.get(player).getHand().IsDefaultCard(indexHand))
            {
                result = false;
            }
            //Check if the card is default or not
            if(!playerList.get(player).getKingdom().IsDefaultCard(indexKingdom))
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

    public Card putCard(int player, int indexHand, int indexKingdom)
    {
        return playerList.get(player).putCard(indexHand, indexKingdom);
    }

    public Card putCardRandom(int player,int indexHand,int indexKingdom)
    {
        Card card = null;
        if(indexHand >= 0 & indexKingdom >= 0)
        {
            card = putCard(player, indexHand,  indexKingdom);
            int opponent = Opponent(player);
        }
        return card;
    }

    public int NextCardHand()
    {
        return ListCard.NextFillIndex(playerList.get(playerTurn).getCardHand());
    }

    public int NextEmptyPlaceKingdom()
    {
        return ListCard.NextEmptyIndex(playerList.get(playerTurn).getCardKingdom());
    }

    public void DrawCard(int player, int indexHand) throws InterruptedException {
        playerList.get(player).DrawCard(deck, indexHand);
    }

    public void DrawMultipleCard(int player, int nb) throws InterruptedException {
        for(int i = 0; i < nb;i++)
        {
            if(ShouldDrawCard())
            {
                int indexHand = ListCard.FindIndex(i);
                DrawCard(player,indexHand);
            }
        }
    }

    public void ChangeTurnWithoutInterface()
    {
        playerTurn = Opponent(playerTurn);

        if(ShouldDrawCard())
        {
            try {
                int indexHandNextEmpty = NextEmptyIndex();
                DrawCard(playerTurn, indexHandNextEmpty);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(ShouldResetKingdom())
        {
            ResetKingdom();
        }
    }

    public void PlayTurnRandom()
    {
        int indexHand = ListCard.NextFillIndex(playerList.get(playerTurn).getCardHand());
        int indexKingdom = ListCard.NextEmptyIndex(playerList.get(playerTurn).getCardKingdom());

        Card card = putCardRandom(playerTurn,indexHand,indexKingdom);
        if(card != null)
        {
            card.power(this,indexKingdom);
        }
        ChangeTurnWithoutInterface();
    }

    public void PlayMultipleTurnRandom(int nb)
    {
        for(int i = 0; i < nb;i++)
        {
            PlayTurnRandom();
        }
    }

    public boolean IsTheEnd()
    {
        boolean result = false;
        if(deck.size() <1)
        {
            int emptyIndexHand0 = ListCard.NextEmptyIndex(playerList.get(0).getCardHand());
            int emptyIndexHand1 = ListCard.NextEmptyIndex(playerList.get(1).getCardHand());
            if(emptyIndexHand0 < 1 & emptyIndexHand1 < 1)
            {
                result = true;
            }
        }
        return result;
    }

    public void ChangeTurn()
    {
        playerTurn = Opponent(playerTurn);
    }

    public boolean ShouldDrawCard()
    {
        boolean result = false;
        //First index free, starting from the middle to the edge of the platform
        int indexHand = ListCard.NextEmptyIndex(playerList.get(playerTurn).getCardHand());

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
        return ListCard.NextEmptyIndex(playerList.get(playerTurn).getCardHand());
    }

    public boolean ShouldResetKingdom()
    {
        boolean result = false;
        int indexKingdom = ListCard.NextEmptyIndex(playerList.get(playerTurn).getCardKingdom());
        //Clear kingdom if complete
        if(indexKingdom < 0)
        {
            result = true;
        }
        return result;
    }

    public void ResetKingdom()
    {
        playerList.get(playerTurn).ResetKingdom();
    }

    @Override
    public String toString() {
        return "Player turn : " + playerTurn +
                "\nPlayer1:\n" + playerList.get(0).toString() +
                "\nPlayer2:\n" + playerList.get(1).toString();
    }
}
