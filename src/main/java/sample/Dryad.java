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

    //stole a card in front of your opponent and add it in front of you without activating its power.
    @Override
    public List<Integer> power(Game game,int index)
    {
        List<Integer> po = new ArrayList<Integer>();
        try{
            //The current player
            Player currentPlayer = game.playerList.get(game.playerTurn);
            //opponent
            Player advPlayer = game.playerList.get(Game.Opponent(game.playerTurn));

            if(!(advPlayer.kingdom.selectedCard(index) instanceof DefaultCard ))
            {
                if(ListCard.NextEmptyIndex(currentPlayer.kingdom.getKingdom()) <= 0)
                {
                    currentPlayer.kingdom.ResetKingdom();
                }


                System.out.println(currentPlayer.kingdom);
                System.out.println(advPlayer.kingdom);

                int indexEmpty = ListCard.NextEmptyIndex(currentPlayer.kingdom.getKingdom());
                currentPlayer.kingdom.set(indexEmpty,advPlayer.kingdom.selectedCard(index));
                advPlayer.kingdom.set(index, new DefaultCard());
                po.add(indexEmpty);
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
