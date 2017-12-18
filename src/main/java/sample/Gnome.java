package sample;
import java.util.ArrayList;
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
    public List<Integer> power(Game game,int index)
    {
        List<Integer> po = new ArrayList<Integer>();
        try {
            //Check into the method if we can draw card
            game.DrawMultipleCard(game.playerTurn,2);
            return po;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return po;
    }

}
