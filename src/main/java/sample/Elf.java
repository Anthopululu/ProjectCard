package sample;
import java.util.ArrayList;
import java.util.List;


public class Elf extends  Card {

    public Elf()
    {
        this.setName("Elf");
    }

    @Override
    public int power() {
        return 0;
    }


    /***
     * Elf : copy and use the power of one of the card in front of you
     * @param game
     */
    @Override
    public List<Integer> power(Game game,int index)
    {
        List<Integer> po = new ArrayList<Integer>();
        try{
            //The current player
            Player currentPlayer = game.playerList.get(game.playerTurn);
            // The oppenent
            Player advPlayer = game.playerList.get(Game.Opponent(game.playerTurn));
            Card tmp = advPlayer.kingdom.selectedCard(index);
            if(!( tmp instanceof  DefaultCard) && !(tmp instanceof Elf))
            {
                po = advPlayer.kingdom.selectedCard(index).power(game,index);
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
