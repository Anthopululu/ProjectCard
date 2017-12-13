package sample;
import java.util.List;


public class Elf extends  Card {

    public Elf()
    {
        this.setName("Elf");
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
     *Elf : copy and use the power of one of the card in front of you
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
            if(advFront != null && !(advFront instanceof Elf) )
            {
                advFront.power(myHand,advHand,deck,inFront,advFront);
            }
        }catch (Exception e)
        {
            System.out.print(e.getMessage());
        }
        return 0;
    }
}
