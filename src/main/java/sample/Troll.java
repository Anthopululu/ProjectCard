package sample;
import java.util.List;

public class Troll extends Card {

    public Troll()
    {
        this.setName("Troll");
        this.setPathToImage("Path");
    }

    @Override
    public int power() {
        return 0;
    }

    @Override
    public int power(List<Card> myHand, List<Card> advHand, List<Card> deck, Card inFront, Card advFront) {

        try{
            List<Card> tmp;
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
