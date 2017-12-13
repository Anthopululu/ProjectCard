package sample;

import java.util.List;

public class Kingdom {

    List<Card> kingdom;
    String name;

    public Kingdom()
    {
        this.name = "KingdomPlayer";
        ResetKingdom();
    }

    public Kingdom(List<Card> kingdom) {

        this.kingdom = kingdom;
    }

    public void ResetKingdom()
    {
        this.kingdom = ListCard.InitialiseListCard(10);
    }

    public List<Card> getKingdom() {
        return this.kingdom;
    }

    public void setKingdom(List<Card> kingdom) {
        this.kingdom = kingdom;
    }

    public Card set(int indexHand, Card card)
    {
        return kingdom.set(indexHand, card);
    }

    public String getName(){return this.name;}

    public void setName(String name){ this.name = name;}

    public boolean IsDefaultCard(int indexKingdom)
    {
        return kingdom.get(indexKingdom).equals(Card.DEFAULT_CARD);
    }

    public int size()
    {
        return kingdom.size();
    }

    public Card PutCard(Hand hand, int indexHand, int indexKingdom)
    {

        //Replace it by a default card
        Card card = hand.set(indexHand, new DefaultCard());
        Card card2 = kingdom.set(indexKingdom, card);
        //Let us know if the card is on the hand, kingdom or deck with boolean and getter associated.
        //Not use yet
        card2.toKingdom();
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

    @Override
    public String toString() {
        return kingdom.toString();
    }
}
