package sample;
import java.util.List;

public class Dyrad extends Card {
    @Override
    public int power() {
        return 0;
    }

    @Override
    public int power(List<Card> myHand, List<Card> advHand, List<Card> deck, Card inFront, Card advFront) {
        return 0;
    }
}
