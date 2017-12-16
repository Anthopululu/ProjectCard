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
    public void power(Game game)
    {
        try
        {
            //The current player
            Player currentPlayer = game.playerList.get(game.playerTurn - 1);
            // The oppenent
            Player advPlayer = game.playerList.get(2 - game.playerTurn);
            Random rand = new Random();

            if(advPlayer.hand.size() > 1 ) {
                int index1 = rand.nextInt(advPlayer.hand.size());
                int index2 = rand.nextInt(advPlayer.hand.size());
                while (index1 != index2) {
                    index2 = rand.nextInt(advPlayer.hand.size());
                }

                Card tmp;
                tmp = advPlayer.hand.get(index1);
                currentPlayer.hand.add(tmp);
                advPlayer.hand.deleteIndex(index1);
                tmp = advPlayer.hand.get(index2);
                currentPlayer.hand.add(tmp);
                advPlayer.hand.deleteIndex(index2);
            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

    }

}
