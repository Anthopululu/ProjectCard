package sample;

import java.util.Arrays;
import java.util.List;

public class Player {
    Kingdom kingdom;
    Hand hand;
    int PlayerNB;
    public Kingdom getKingdom() {
        return kingdom;
    }

    public void setKingdom(Kingdom kingdom) {
        this.kingdom = kingdom;
    }

    public Hand getHand() {
        return hand;
    }

    public Player(int nb)
    {
        this.kingdom = new Kingdom();
        this.hand = new Hand();
        PlayerNB = nb;
    }

    public List<Card> getCardHand()
    {
        return hand.getListOfCards();
    }

    public List<Card> getCardKingdom()
    {
        return kingdom.getKingdom();
    }

    public void DrawCard(List<Card> deck, int indexHand) throws InterruptedException {
        hand.DrawCard(deck, indexHand);
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public void ResetKingdom()
    {
        kingdom.ResetKingdom();
    }

    public Player(Kingdom kingdom, Hand hand) {
        this.kingdom = kingdom;
        this.hand = hand;
    }

    public Card putCard(int indexHand, int indexKingdom)
    {
        return kingdom.PutCard(hand, indexHand,  indexKingdom);
    }

    @Override
    public String toString() {
        return "Kingdom=" + kingdom +
                "\nHand=" + hand;
    }
}
