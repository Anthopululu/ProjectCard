package sample;
import jdk.nashorn.internal.runtime.ECMAException;

import java.util.ArrayList;
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
    public List<Integer> power(Game game,int index)
    {
        List<Integer> po = new ArrayList<Integer>();
        try
        {
            //The current player
            Player currentPlayer = game.playerList.get(game.playerTurn);
            // The oppenent
            Player advPlayer = game.playerList.get(Game.Opponent(game.playerTurn));
            Random rand = new Random();
            int nbCardOppenentHand = 10 - advPlayer.hand.nbCardEmptyHand();

            System.out.println(game.playerList.get(game.playerTurn).hand);
            System.out.println(game.playerList.get(Game.Opponent(game.playerTurn)).hand);
            if( nbCardOppenentHand > 1) {

                int index1 = rand.nextInt(advPlayer.hand.size());

                while(advPlayer.hand.get(index1) instanceof DefaultCard)
                {
                    index1 = rand.nextInt(advPlayer.hand.size());
                }
                int index2 = rand.nextInt(advPlayer.hand.size());

                while (index1 == index2 || advPlayer.hand.get(index2) instanceof DefaultCard ) {
                    index2 = rand.nextInt(advPlayer.hand.size());
                }

                if(currentPlayer.hand.nbCardEmptyHand() > 1)
                {

                    int emptySpaceHand = ListCard.NextEmptyIndex(currentPlayer.hand.getListOfCards());
                    currentPlayer.hand.set(emptySpaceHand,advPlayer.hand.get(index1));
                    advPlayer.hand.set(index1,new DefaultCard());

                    int emptySpaceHand2 = ListCard.NextEmptyIndex(currentPlayer.hand.getListOfCards());
                    currentPlayer.hand.set(emptySpaceHand2,advPlayer.hand.get(index2));
                    advPlayer.hand.set(index2,new DefaultCard());

                    po.add(index1);
                    po.add(index2);
                    po.add(emptySpaceHand);
                    po.add(emptySpaceHand2);
                }else {

                    if (currentPlayer.hand.nbCardEmptyHand() == 1) {
                        int emptySpaceHand = ListCard.NextEmptyIndex(currentPlayer.hand.getListOfCards());
                        currentPlayer.hand.set(emptySpaceHand, advPlayer.hand.get(index1));
                        advPlayer.hand.set(index1, new DefaultCard());

                        po.add(index1);
                        po.add(emptySpaceHand);

                    }
                }

            }
            else
            {
                if(nbCardOppenentHand == 1)
                {
                    int index1 = rand.nextInt(advPlayer.hand.size());
                    while(advPlayer.hand.get(index1) instanceof DefaultCard)
                    {
                        index1 = rand.nextInt(advPlayer.hand.size());
                    }

                    int emptySpaceHand = ListCard.NextEmptyIndex(currentPlayer.hand.getListOfCards());

                    if(emptySpaceHand != -1)
                    {
                        currentPlayer.hand.set(emptySpaceHand,advPlayer.hand.get(index1));
                        advPlayer.hand.set(index1,new DefaultCard());

                        po.add(index1);
                        po.add(emptySpaceHand);
                    }

                }
            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        System.out.println(po);
        return po;

    }
}
