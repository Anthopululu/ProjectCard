package sample;
import java.util.List;

public class Troll extends Card {

    public Troll()
    {
        this.setName("Troll");
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

    /***
     *
     * @param myHand
     * @param advHand
     * @param deck
     * @param inFront
     * @param advFront
     * @return
     */
    @Override
    public int power(Hand myHand, Hand advHand, List<Card> deck, Card inFront, Card advFront) {

        try{
            Hand tmp;
            tmp = advHand;
            advHand = myHand;
            myHand = tmp;
        }catch (Exception e)
        {
            System.out.print(e.getMessage());
        }

        return 0;
    }
}
