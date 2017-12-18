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

    public List<Card> getKingdom() {
        return this.kingdom;
    }

    public void ResetKingdom()
    {
        this.kingdom = ListCard.InitialiseListCard(Game.NB_CARD);
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

    // The methode give return the last card on the kingdom
    // Should change when i  understand the code
    public Card selectedCard(int index)
    {
        if(kingdom.size() != 0)
        {
            return kingdom.get(kingdom.size() - 1);
        }return  null;
    }

    public Card newestCardOnTheKingdom()
    {
        return kingdom.get(kingdom.size() - 1);
    }

    public boolean isEmpty()
    {
        boolean isEmpty = true;
        for (int i = 0; i < kingdom.size();i++)
        {
            if(kingdom.get(i) != null)
                isEmpty = false;
        }
        return isEmpty;
    }

    public boolean isFull()
    {
        boolean isFull = true;
        for (int i = 0; i < kingdom.size();i++)
        {
            if(kingdom.get(i) == null)
                isFull = false;
        }
        return isFull;
    }

    @Override
    public String toString() {
        return kingdom.toString();
    }
}
