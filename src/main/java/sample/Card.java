package sample;

import java.util.List;

public abstract class Card {

    String name;
    String pathToImage;

    public Card()//To have an heritage
    {
        this.name = "none";
        this.pathToImage = "none";
    }

    public Card(String name, String pathToImage)
    {
        this.name = name;
        this.pathToImage = pathToImage;
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

    public String getPathToImage()
    {
        //getting the lastName variable instance
        return pathToImage;
    }

    public void setPathToImage(String name)
    {
        //setting the firstName variable text
        this.pathToImage = pathToImage;
    }

    @Override
    public String toString() {
        return this.name + " - " + pathToImage;
    }

    public abstract int power(List<Card> myKingdom, List<Card> YourKingdom, Card MyCard, Card YourCard);

    public abstract int power(Hand myHand, Hand advHand, List<Card> deck, Card inFront, Card advFront);
}
