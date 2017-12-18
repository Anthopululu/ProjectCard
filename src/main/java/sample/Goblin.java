package sample;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Goblin extends Card{


    public Goblin()
    {
        this.setName("Goblin");
    }

    @Override
    public int power() {
        return 0;
    }

    /***
     * switch your hand with you opponent
     * @param game
     */
    @Override
    public List<Integer> power(Game game,int index)
    {
        List<Integer> po = new ArrayList<Integer>();
           try{
               //The current player
               Player currentPlayer = game.playerList.get(game.playerTurn - 1);
               // The oppenent
               Player advPlayer = game.playerList.get(2 - game.playerTurn);

               Hand tmpHand = currentPlayer.getHand();
               currentPlayer.hand = advPlayer.hand;
               advPlayer.hand = tmpHand;

               return po;

           }catch (Exception e)
           {
               System.out.println(e.getMessage());
           }
        return po;
    }


}
