package sample;
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
    public void power(Game game,int index)
    {

        try{
            //The current player
            Player currentPlayer = game.playerList.get(game.playerTurn - 1);
            // The oppenent
            Player advPlayer = game.playerList.get(2 - game.playerTurn);
            if(advPlayer.kingdom.selectedCard(index) != null && !(advPlayer.kingdom.selectedCard(0) instanceof Elf))
            {
                advPlayer.useThePowerOfTheNewCardPutDowOnTheKingdom(game,index);
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

}
