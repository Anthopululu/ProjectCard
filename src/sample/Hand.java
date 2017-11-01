package sample;

import java.util.ArrayList;

public class Hand {
    ListOfCards hand = new ListOfCards();

    public Hand(ListOfCards hand) {
        this.hand = hand;
    }

    public ListOfCards getHand() {
        return hand;
    }

    public void setHand(ListOfCards hand) {
        this.hand = hand;
    }

    public void Draw(ListOfCards Deck){
        this.hand.getCards().add(Deck.getCards().remove(Deck.getCards().size()-1));
    }

}
