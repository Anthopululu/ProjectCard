package sample;
import java.util.List;

public class Gnome extends Card{

    public Gnome()
    {
        this.setName("Gnome");
    }

    @Override
    public int power() {
        return 0;
    }

    /***
     * draw 2 cards
     * @param game
     */
    @Override
    public void power(Game game,int index)
    {
        try {
            //Check into the method if we can draw card
            game.DrawMultipleCard(game.playerTurn,2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
