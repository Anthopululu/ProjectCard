package sample;
import java.util.List;

public class Goblin extends Card{


    public Goblin()
    {
        this.setName("Goblin");
        this.setPathToImage("Path");
    }

    @Override
    public int power() {
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
     *
     * switch your hand with you opponent
     */
    @Override
    public int power(List<Card> myHand, List<Card> advHand, List<Card> deck, Card inFront, Card advFront) {

        /*
        Create a tmp element
        Stock you hand on tmp
        take the card of your adv
        adv get the card on tmp
         */
        try {
            List<Card> tmp = myHand;
            myHand = advHand;
            advHand = tmp;
        }
        catch (Exception e)
        {
            System.out.print(e.getMessage());
        }
        return 0;

    }

}
