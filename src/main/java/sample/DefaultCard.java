package sample;

import java.util.List;

public class DefaultCard extends Card{

    public DefaultCard()
    {
        this.setName("Reverse");
    }

    @Override
    public int power() {
        return 0;
    }

    @Override
    public void power(Game game)
    {

    }

    public int power(List<Card> myKingdom, List<Card> YourKingdom, Card MyCard, Card YourCard) {
        return 0;
    }

    @Override
    public int power(Hand myHand, Hand advHand, List<Card> deck, Card inFront, Card advFront) {
        return -1;
    }
}
