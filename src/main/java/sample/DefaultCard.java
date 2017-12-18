package sample;

import java.util.ArrayList;
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
    public List<Integer> power(Game game,int index)
    {
        return null;
    }
}
