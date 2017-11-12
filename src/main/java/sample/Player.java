package sample;

public class Player {
    Kingdom kingdom;
    Hand hand;
    public Kingdom getKingdom() {
        return kingdom;
    }

    public void setKingdom(Kingdom kingdom) {
        this.kingdom = kingdom;
    }

    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public Player(Kingdom kingdom, Hand hand) {
        this.kingdom = kingdom;
        this.hand = hand;
    }


}
