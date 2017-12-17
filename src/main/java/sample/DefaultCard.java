package sample;

import java.util.List;

public class DefaultCard extends Card{

    public DefaultCard()
    {
        this.setName("Reverse");
    }

    @Override
    public int power() {
        return 0;
    }

    @Override
    public void power(Game game,int index)
    {

    }
}
