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

    public int power(List<Card> myKingdom, List<Card> YourKingdom, Card MyCard, Card YourCard) {
        return 0;
    }

    public void power(Player player, Player opponent, List<Card> deck)
    {
        try{
            /**
             * While there is enought card
             * the player draw card from the deck until nbCardDraw is enough
             */
            if(deck.size()!=0)
            {
                int nbCardDraw = 0;
                while(nbCardDraw < 2 || deck.size() < 1)
                {
                    player.getHand().drawCard(deck);
                    nbCardDraw++;
                }
            }
        }catch (Exception e)
        {
            System.out.print(e.getMessage());
        }
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
    public int power(Hand myHand, Hand advHand,List<Card> deck, Card inFront, Card advFront) {
        try{
            /**
             * While there is enought card
             * the player draw card from the deck until nbCardDrawis enought
             */
            if(deck.size()!=0)
            {
                int nbCardDraw = 0;
                while(nbCardDraw < 2 || deck.size() < 1 )
                {
                    myHand.drawCard(deck);
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
