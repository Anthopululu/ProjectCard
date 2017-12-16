package sample;

import jdk.nashorn.internal.runtime.ECMAException;

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
    public void power(Game game)
    {
        try{
            //The current player
            Player currentPlayer = game.playerList.get(game.playerTurn - 1);
            Player advPlayer = game.playerList.get(2 - game.playerTurn);
                if(advPlayer.kingdom.size() > 0)
                {
                    currentPlayer.kingdom.addCard(advPlayer.kingdom.selectedCard(0));
                    // Should add a index instance of the advPlayer.kingdom.size() - 1
                    advPlayer.kingdom.removeCard(advPlayer.kingdom.size() - 1 );
                }

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }


}
