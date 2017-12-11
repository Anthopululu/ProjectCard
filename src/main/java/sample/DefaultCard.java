package sample;

import java.util.List;

public class DefaultCard extends Card{

    public DefaultCard()
    {
        this.setName("Reverse");
    }

    public int power() {
        return 0;
    }

    @Override
    public int power(List<Card> myKingdom, List<Card> YourKingdom, List<Card> deck  , Card MyCard, Card YourCard) {
        return -1;
    }
}
