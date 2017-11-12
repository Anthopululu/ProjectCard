package sample;

import java.util.List;

public class Dryad extends Card {

    public int power() {
        return 0;
    }

    public int power(List<Card> myHand, List<Card> advHand, List<Card> deck, Card inFront, Card advFront) {
        return 0;
    }

    @Override
    public String toString() {
        return "Dryad";
    }


    @Override
    public int power(List<Card> myKingdom, List<Card> YourKingdom, Card MyCard, Card YourCard) {
        try {
            myKingdom.add(YourCard);
            YourKingdom.add(MyCard);
            myKingdom.remove(MyCard);
            YourKingdom.remove(YourCard);

        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        return 0;

    }
}
