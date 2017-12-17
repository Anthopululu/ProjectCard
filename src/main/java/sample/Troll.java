package sample;
import java.util.List;

public class Troll extends Card {

    public Troll()
    {
        this.setName("Troll");
    }

    @Override
    public int power() {
        return 0;
    }

    /***
     * swap the cards in front of you with the cards in front of your opponent
     * @param game
     */
    @Override
    public void power(Game game)
    {
        try {
            //The current player
            Player currentPlayer = game.playerList.get(game.playerTurn - 1);
            // The opponent
            Player advPlayer = game.playerList.get(2 - game.playerTurn);

            Card tmp = currentPlayer.kingdom.newestCardOnTheKingdom();
            currentPlayer.kingdom.removeCard(currentPlayer.kingdom.size() - 1);
            currentPlayer.kingdom.addCard(advPlayer.kingdom.newestCardOnTheKingdom());
            advPlayer.kingdom.removeCard(advPlayer.kingdom.size() - 1);
            advPlayer.kingdom.addCard(tmp);

        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

}
