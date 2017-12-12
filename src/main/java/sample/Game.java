package sample;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;


public class Game {
    static int NB_CARD = 10;//Number of card in the hand and in the kingdom
    static int NB_CARD_DECK = 45;//Number of card in the deck
    List<Card> deck;
    List<Hand> handPlayer;//HandPlayer.get(0) = Hand player 1, HandPlayer.get(1) = Hand player 2
    List<Kingdom> kingdomPlayer;//KingdomPlayer.get(0) = Kingdom player 1, KingdomPlayer.get(1) = Kingdom player 2
    boolean blockAnimation = false;//If we are allowed to put card/use power or if we need to wait the end of the animation of other things
    int playerTurn;//1 = Player 1, 2 = Player 2
    //Be careful about list, all of them start at 0, whereas the player start at 1
    //Animation start at 1 too

    Animation animation;

    Text textDeckCardLeft;

    public Game()
    {
        //Cucumber test initialization. Logic test.
        deck = new ArrayList();
        this.CreateDeck();
        kingdomPlayer = Arrays.asList(new Kingdom(), new Kingdom());
        handPlayer = Arrays.asList(new Hand(), new Hand());
        playerTurn = 1;
    }

    public Game(ImageView AnimationView,GridPane KingdomPlayer1,GridPane KingdomPlayer2,GridPane HandPlayer1,GridPane HandPlayer2, Text textDeckCardLeft)
    {
        //Interface test, game with interface
        deck = new ArrayList();
        this.CreateDeck();
        kingdomPlayer = Arrays.asList(new Kingdom(), new Kingdom());
        handPlayer = Arrays.asList(new Hand(), new Hand());
        animation = new Animation(AnimationView, KingdomPlayer1, KingdomPlayer2, HandPlayer1, HandPlayer2, blockAnimation);
        this.textDeckCardLeft = textDeckCardLeft;
        playerTurn = 1;
    }

    public boolean IsCorrectCard(int player, int indexHand, int indexKingdom)
    {
        //Check if we can put a card or not. Check if the card is not a default card on the hand (reverse) or if it one on the kingdom.
        boolean result = true;
        if(player == playerTurn)
        {
            if(handPlayer.get(player-1).IsDefaultCard(indexHand))
            {
                result = false;
            }
            if(!kingdomPlayer.get(player-1).IsDefaultCard(indexKingdom))
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
            for (int i = 0; i < NB_CARD_DECK; i++) {
                    Random rand = new Random();
                    int n = rand.nextInt(6);
                    if (n == 0) {
                            deck.add(new Dryad());
                    }
                    if (n == 1) {
                            deck.add(new Elf());
                    }
                    if (n == 2) {
                            deck.add(new Gnome());
                    }
                    if (n == 3) {
                            deck.add(new Goblin());
                    }
                    if (n == 4) {
                            deck.add(new Troll());
                    }
                    if (n == 5) {
                            deck.add(new Korrigan());
                    }
            }
            return deck;
    }

    public void DrawCard(int player, int indexHand, CountDownLatch latch) throws InterruptedException {
        Card card = handPlayer.get(player-1).DrawCard(deck, indexHand);
        UpdateMessageDeckCardLeft();
        animation.AnimateDrawCard(player,indexHand, card, latch);
    }

    public void UpdateMessageDeckCardLeft()
    {
        textDeckCardLeft.setText("Card Left " + deck.size());
    }

    public void DrawMultipleCard(int player, int nb) throws InterruptedException {
        for(int i = 0; i < nb;i++)
        {
            CountDownLatch latch = new CountDownLatch(1);
            int indexHand = ListCard.FindIndex(i);
            DrawCard(player,indexHand, latch);
            //Wait the end of the animation
            latch.await();

        }
    }

    public void putCard(int player, int indexHand, int indexKingdom, CountDownLatch latch)
    {
        animation.AnimatePutCard(player, indexHand, indexKingdom, latch);
        kingdomPlayer.get(player-1).PutCard(handPlayer.get(player-1), indexHand,  indexKingdom);
    }

    public void putCardWithoutInterface(int player)
    {
        int indexHand = ListCard.NextFillIndex(handPlayer.get(player-1).getListOfCards());
        int indexKingdom = ListCard.NextEmptyIndex(kingdomPlayer.get(player-1).getKingdom());
        if(indexHand > 0 & indexKingdom > 0)
        {
            kingdomPlayer.get(player-1).PutCard(handPlayer.get(player-1), indexHand,  indexKingdom);
        }
    }

    /*Junit interface test method*/
    public void DrawCardWithoutAnimationInterface(int player, int indexHand, CountDownLatch latch) throws InterruptedException {
        Card card = handPlayer.get(player-1).DrawCard(deck, indexHand);
        animation.EndDrawCardAnimation(player,indexHand, card, latch);
    }

    public void DrawMultipleCardWithoutAnimationInterface(int player, int nb) throws InterruptedException {
        for(int i = 0; i < nb;i++)
        {
            CountDownLatch latch = new CountDownLatch(1);
            int indexHand = ListCard.FindIndex(i);
            DrawCardWithoutAnimationInterface(player,indexHand, latch);
            latch.await();
        }
        UpdateMessageDeckCardLeft();
    }
    /*End of junit interface test method*/

    /*Cucumber test method*/
    public void DrawCardWithoutInterface(int player, int indexHand) throws InterruptedException {
        handPlayer.get(player-1).DrawCard(deck, indexHand);
    }

    public void DrawMultipleCardWithoutInterface(int player, int nb) throws InterruptedException {
        for(int i = 0; i < nb;i++)
        {
            int indexHand = ListCard.FindIndex(i);
            DrawCardWithoutInterface(player,indexHand);
        }
    }
    /*End of cucumber test method*/



    public CountDownLatch Play()
    {
        CountDownLatch latch = new CountDownLatch(1);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    DrawMultipleCard(1, 5);
                    DrawMultipleCard(2, 5);
                    latch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        //Need time to initialize the interface. Don't know how to do otherwise
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(500),
                (ActionEvent ae) -> {
                    t.start();
                }));
        timeline.play();

        return latch;
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
        //First index free, starting from the middle to the edge of the platform
        int indexHand = ListCard.NextEmptyIndex(handPlayer.get(playerTurn-1).getListOfCards());
        //Draw card if we have some place in the list card
        if(indexHand > -1)
        {
            try {
                DrawCard(playerTurn,indexHand,null);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
        int indexHand = ListCard.NextEmptyIndex(handPlayer.get(playerTurn-1).getListOfCards());
        //Draw card if we have some place in the list card
        if(indexHand > -1)
        {
            try {
                DrawCardWithoutInterface(playerTurn,indexHand);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void PlayTurn(int player, int indexHand, int indexKingdom)
    {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                CountDownLatch latch = new CountDownLatch(1);
                putCard(player, indexHand, indexKingdom, latch);
                try {
                    //Wait the end of the animation
                    latch.await();
                    ChangeTurn();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

    public void PlayTurnWithoutInterface()
    {
        putCardWithoutInterface(playerTurn);
        ChangeTurnWithoutInterface();
    }

    @Override
    public String toString() {
        return "Player turn : " + playerTurn +
                "\nhandPlayer1=" + handPlayer.get(0) +
                "\nkingdomPlayer1=" + kingdomPlayer.get(0) +
                "\nhandPlayer2=" + handPlayer.get(1) +
                "\nkingdomPlayer2=" + kingdomPlayer.get(1);
    }
}
