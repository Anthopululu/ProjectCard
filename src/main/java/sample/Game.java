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
            if(handPlayer.get(player-1).get(indexHand).equals(Card.DEFAULT_CARD))
            {
                result = false;
            }
            if(!kingdomPlayer.get(player-1).get(indexKingdom).equals(Card.DEFAULT_CARD))
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

    public static List<Card> InitialiseListCard(int number)
    {
            List<Card> result = new ArrayList<>();
            for(int i = 0; i < number;i++)
            {
                    result.add(new DefaultCard());
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

    void UpdateMessageDeckCardLeft()
    {
        textDeckCardLeft.setText("Card Left " + deck.size());
    }

    public void DrawMultipleCard(int player, int nb) throws InterruptedException {
        for(int i = 0; i < nb;i++)
        {
            CountDownLatch latch = new CountDownLatch(1);
            int indexHand = SequenceNumberMiddle10(i);
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

    /*Junit test method*/
    public void DrawCardWithoutAnimation(int player, int indexHand, CountDownLatch latch) throws InterruptedException {
        Card card = handPlayer.get(player-1).DrawCard(deck, indexHand);
        animation.EndDrawCardAnimation(player,indexHand, card, latch);
    }

    public void DrawMultipleCardWithoutAnimation(int player, int nb) throws InterruptedException {
        for(int i = 0; i < nb;i++)
        {
            CountDownLatch latch = new CountDownLatch(1);
            int indexHand = SequenceNumberMiddle10(i);
            DrawCardWithoutAnimation(player,indexHand, latch);
            latch.await();
        }
        UpdateMessageDeckCardLeft();
    }
    /*End of junit test method*/

    //To get the right index of the card to add. Starting from the middle
    public int SequenceNumberMiddle10(int index)
    {
        int u0 = 5;
        double t = Math.pow(-1, index);
        double i = Math.ceil((double)index/2);
        int result = (int)(u0 + t * i);
        return result;
    }

    public CountDownLatch Play()
    {
        CountDownLatch latch = new CountDownLatch(1);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    DrawMultipleCardWithoutAnimation(1, 5);
                    DrawMultipleCardWithoutAnimation(2, 5);
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
        int indexHand = nextEmptyIndexSequenceNumberMiddle10(playerTurn);
        indexHand = SequenceNumberMiddle10(indexHand);
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

    //Where to add the next card drawn
    public int nextEmptyIndexSequenceNumberMiddle10(int player)
    {
        int result = 0;
        //Check if exists
        if(handPlayer.get(player-1).contains(Card.DEFAULT_CARD))
        {
            int i = 0;
            Card card = handPlayer.get(player-1).get(SequenceNumberMiddle10(i));
            while(!card.equals(Card.DEFAULT_CARD))
            {
                i++;
                card = handPlayer.get(player-1).get(SequenceNumberMiddle10(i));
            }
            result = i;
        }
        else
        {
            result = -1;
        }
        return result;
    }

    @Override
    public String toString() {
        return "handPlayer1=" + handPlayer.get(0) +
                "\nkingdomPlayer1=" + kingdomPlayer.get(0) +
                "\nhandPlayer2=" + handPlayer.get(1) +
                "\nkingdomPlayer2=" + kingdomPlayer.get(1);
    }
}
