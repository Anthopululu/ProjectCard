package sample;
import java.util.List;

public class Gnome extends Card{

    public Gnome()
    {
        this.setName("Gnome");
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
     * @return int
     *
     * draw 2 cards
     */
    @Override
    public int power(List<Card> myHand, List<Card> advHand,List<Card> deck, Card inFront, Card advFront) {
        try{
            /**
             * While there is enought card
             * the player draw card from the dech until nbCardDrawis enought
             */
            if(deck.size()!=0)
            {
                int nbCardDraw = 0;
                while(nbCardDraw < 2 || deck.size() < 1 )
                {
                    myHand.add(deck.get(deck.size() - 1));
                    deck.remove(deck.get(deck.size() - 1));
                    nbCardDraw++;
                }
            }
        }catch (Exception e)
        {
            System.out.print(e.getMessage());
        }
        return 0;
    }
    
}
