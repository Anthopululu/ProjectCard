package sample;

import jdk.nashorn.internal.runtime.ECMAException;

import java.util.ArrayList;
import java.util.List;

public class Dryad extends Card {

    public Dryad()
    {
        this.setName("Dryad");
    }

    public int power() {
        return 0;
    }

    /***
     * stole a card in front of your opponent and add it in front of you without activating its power.
     * @param game
     */
    @Override
    public List<Integer> power(Game game,int index)
    {
        List<Integer> po = new ArrayList<Integer>();
        try{
            //The current player
            Player currentPlayer = game.playerList.get(game.playerTurn - 1);
            //oppenent
            Player advPlayer = game.playerList.get(2 - game.playerTurn);

            if(advPlayer.kingdom.selectedCard(index) != null )
            {
                if(currentPlayer.kingdom.isFull())
                    currentPlayer.kingdom.ResetKingdom();

                int indexEmpty = ListCard.NextEmptyIndex(currentPlayer.kingdom.getKingdom());
                currentPlayer.kingdom.set(indexEmpty,advPlayer.kingdom.selectedCard(index));
                advPlayer.kingdom.set(indexEmpty, new DefaultCard());
            }

            return po;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return po;
    }


}
