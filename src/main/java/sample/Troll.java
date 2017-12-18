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
            Player currentPlayer = game.playerList.get(game.playerTurn - 1);
            // The oppenent
            Player advPlayer = game.playerList.get(2 - game.playerTurn);

            if(advPlayer.kingdom.selectedCard(index) instanceof DefaultCard)
            {
                Card tmp = currentPlayer.kingdom.get(index);
                currentPlayer.kingdom.set(index,advPlayer.kingdom.selectedCard(index));
                advPlayer.kingdom.set(index,tmp);

                po.add(index);
            }
            return po;
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return po;
    }

}
