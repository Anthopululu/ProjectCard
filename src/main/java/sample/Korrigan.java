package sample;
import java.util.List;
import java.util.Random;

public class Korrigan extends Card  {
    private String name = "Korrigan";
    private String img = "Url";

    public  Korrigan()
    {
        this.setName(name);
        this.setPathToImage(img);
    }

    @Override
    public int power() {
        return 0;
    }

    /***
     *Korrigan cards draw 2 random cards within your opponent han
     */
    @Override
    public int power(List<Card> myHand, List<Card> advHand,List<Card> deck, Card inFront, Card advFront) {
        Random rand = new Random();
        /*
        If the oppenent had enough card, draw 2 random cards from his hand,
        if he only had 1 cards take it
        if he doesn't had card return nothing
        */
        if(advHand.size() > 1 )
        {
            try{
                int card1 = rand.nextInt(advHand.size());
                int card2 = rand.nextInt(advHand.size());
                while(card1 != card2)
                {
                    card2 = rand.nextInt(advHand.size());
                }
                Card tmp = new Korrigan();
                tmp = advHand.get(card1);
                myHand.add(tmp);
                advHand.remove(tmp);
                tmp = advHand.get(card2);
                myHand.add(tmp);
                advHand.remove(tmp);
                return 1;
            }
            catch (Exception e)
            {
                System.out.print(e.getMessage());
            }
        }else if(advHand.size() == 1)
        {
            try{
                int cards = rand.nextInt(advHand.size());
                myHand.add(advHand.get(0));
                advHand.remove(advHand.get(0));
                return 1;
            }catch (Exception e)
            {
                System.out.print(e.getMessage());
            }
        }
        else {
            System.out.print("Connard, tu n'as pas de carte");
        }
        return 0;
    }

}
