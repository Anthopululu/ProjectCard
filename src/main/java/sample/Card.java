package sample;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public abstract class Card {

    String name;
    boolean isHand;
    boolean isKingdom;
    static DefaultCard DEFAULT_CARD = new DefaultCard();//The default card
    boolean isDeck;

    public Card()//To have an heritage
    {
        isDeck = true;
        isHand = false;
        isKingdom = false;
    }

    public Card(String name)
    {
        this.name = name;
        isDeck = true;
        isHand = false;
        isKingdom = false;
    }

    public boolean IsDeck()
    {
        return isDeck;
    }

    public boolean IsKingdom()
    {
        return isKingdom;
    }

    public boolean IsHand()
    {
        return isHand;
    }

    public void toKingdom()
    {
        isDeck = false;
        isHand = false;
        isKingdom = true;
    }

    public void toHand()
    {
        isDeck = false;
        isHand = true;
        isKingdom = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card)) return false;

        Card card = (Card) o;

        return name.equals(card.name);
    }

    public abstract int power();//To override the method at each class
    //public abstract int power(List<Card> myHand, List<Card> advHand, List<Card> deck, Card inFront, Card advFront);//To override the method at each class for the Korrigan


    public String getName()
    {
        //getting the lastName variable instance
        return name;
    }

    public void setName(String name)
    {
        //setting the firstName variable text
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    //public abstract int power(List<Card> myKingdom, List<Card> YourKingdom, Card MyCard, Card YourCard);

    public static Card RandomCard()
    {
        Card result = null;
        Random rand = new Random();
        int n = rand.nextInt(1);
        if (n == 0) {
            result = new Gnome();
        }
        if (n == 1) {
            result = new Elf();
        }
        if (n == 2) {
            result = new Dryad();
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

    public abstract List<Integer> power(Game game,int index);

    public List<String> getClassString()
    {
        return Arrays.asList("Card", this.name);
    }
}
