package sample;

import java.util.List;

public class Kingdom {

    List<Card> kingdom;

    public List getKingdom() {
        return this.kingdom;
    }

    public Kingdom(List<Card> kingdom) {
        this.kingdom = kingdom;
    }

    public Kingdom()
    {
        this.kingdom = Game.InitialiseListCard(10);
    }

    public void setKingdom(List<Card> kingdom) {
        this.kingdom = kingdom;
    }

    public Card set(int indexHand, Card card)
    {
        return kingdom.set(indexHand, card);
    }

    public Card PutCard(Hand hand, int indexHand, int indexKingdom)
    {

        //Replace it by a default card
        Card card = hand.set(indexHand, new DefaultCard());
        kingdom.set(indexKingdom, card);
        //Let us know if the card is on the hand, kingdom or deck with boolean and getter associated.
        //Not use yet
        card.toKingdom();
        return card;
    }

    public boolean contains(Card card)
    {
        return kingdom.contains(card);
    }

    public void addCard(Card c)
    {
        kingdom.add(c);
    }

    public void removeCard(int n)
    {
        kingdom.remove(n);
    }

    public Card get(int index) {
        return kingdom.get(index);
    }
}
