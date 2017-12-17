package sample;
import jdk.nashorn.internal.runtime.ECMAException;

import java.util.List;
import java.util.Random;

public class Korrigan extends Card  {

    public  Korrigan()
    {
        this.setName("Korrigan");
    }

    @Override
    public int power() {
        return 0;
    }

    /***
     * Korrigan cards draw 2 random cards within your opponent hand
     * @param game
     */
    @Override
    public void power(Game game,int index)
    {
        try
        {
            //The current player
            Player currentPlayer = game.playerList.get(game.playerTurn - 1);
            // The oppenent
            Player advPlayer = game.playerList.get(2 - game.playerTurn);
            Random rand = new Random();

            int nbCard = 0;
            for(int i = 0 ; i < advPlayer.hand.size();i++)
            {
                if(advPlayer.hand.get(i) != null)
                    nbCard++;
            }

            if(nbCard > 1 ) {
                int index1 = rand.nextInt(advPlayer.hand.size());
                while(advPlayer.hand.get(index1) == null)
                {
                    index1 = rand.nextInt(advPlayer.hand.size());
                }
                int index2 = rand.nextInt(advPlayer.hand.size());

                while (index1 == index2 && advPlayer.hand.get(index2) == null ) {
                    index2 = rand.nextInt(advPlayer.hand.size());
                }

                if(!currentPlayer.hand.isFull())
                {
                    Card tmp;
                    tmp = advPlayer.hand.get(index1);
                    currentPlayer.hand.add(tmp);
                    advPlayer.hand.deleteIndex(index1);
                    if(!currentPlayer.hand.isFull())
                    {
                        tmp = advPlayer.hand.get(index2);
                        currentPlayer.hand.add(tmp);
                        advPlayer.hand.deleteIndex(index2);
                    }
                }
            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

    }

}
