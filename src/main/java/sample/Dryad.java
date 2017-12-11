package sample;

import java.util.List;

public class Dryad extends Card {

    public Dryad()
    {
        this.setName("Dryad");
    }

    public int power() {
        return 0;
    }

    @Override
    public int power(List<Card> myKingdom, List<Card> YourKingdom,List<Card> deck  ,Card MyCard, Card YourCard) {
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
