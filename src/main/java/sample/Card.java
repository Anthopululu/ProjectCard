package sample;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public abstract class Card {

    String name;
    boolean isHand;
    boolean isKingdom;

    public Card()//To have an heritage
    {
        isHand = false;
        isKingdom = false;
    }

    public Card(String name)
    {
        this.name = name;
        isHand = false;
        isKingdom = false;
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
        isHand = false;
        isKingdom = true;
    }

    public void toHand()
    {
        isHand = true;
        isKingdom = false;
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

    public abstract int power(List<Card> myHand, List<Card> advHand, List<Card> deck, Card inFront, Card advFront);

    public List<String> getClassString()
    {
        return Arrays.asList("Card", this.name);
    }
}
