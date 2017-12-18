package sample;
import java.util.ArrayList;
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
    public List<Integer> power(Game game,int index)
    {
        List<Integer> po = new ArrayList<Integer>();
        try {
            //The current player
            Player currentPlayer = game.playerList.get(game.playerTurn);
            // The oppenent
            Player advPlayer = game.playerList.get(Game.Opponent(game.playerTurn));

            Kingdom tmp = currentPlayer.kingdom;

            currentPlayer.setKingdom(advPlayer.kingdom);

            advPlayer.setKingdom(tmp);

            return po;
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return po;
    }

}
