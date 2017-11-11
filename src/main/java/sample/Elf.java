package sample;
import java.util.List;


public class Elf extends  Card {

    public Elf()
    {
        this.setName("Elf");
        this.setPathToImage("Path");
    }

    @Override
    public int power() {
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
    public int power(List<Card> myHand, List<Card> advHand, List<Card> deck, Card inFront, Card advFront) {
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
