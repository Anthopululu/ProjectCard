package sample;

import java.util.ArrayList;
import java.util.List;

public class Hand {

    public List<Card> getListOfCards() {
        return listOfCards;
    }

    private List<Card> listOfCards;

    /***
     * Create a constructor
     * You either start with a hand already created or
     * you start with nothing and you draw a number a card a the begening
     * @param hand
     */
    public Hand(List<Card> hand) {
        this.listOfCards = hand;
    }

    public Hand()
    {
        listOfCards = ListCard.InitialiseListCard(10);
    }

    public List getHand() {
        return listOfCards;
    }

    public void setHand(List<Card> hand) {
        this.listOfCards = hand;
    }

    public Card DrawCard(List<Card> deck, int indexHand)
    {
        //Remove it from the deck
        Card card = deck.remove(deck.size()-1);
        //Let us know if the card is on the hand, kingdom or deck with boolean and getter associated.
        //Not use yet
        card.toHand();
        //Add it in the hand
        listOfCards.set(indexHand, card);
        return card;
    }

    public boolean IsDefaultCard(int indexHand)
    {
        return listOfCards.get(indexHand).equals(Card.DEFAULT_CARD);
    }

    /***
     *Draw a deck from the deck
     * Get the last Card from the deck
     * Add it to the hand and remove it from the deck
     * @param deck
     */
    public void drawCard(List<Card> deck)
    {
        try{
            if(deck.size() > 0 )
            {
                listOfCards.add(deck.get(deck.size() - 1));
                deck.remove(deck.size() - 1);
            }
        }
        catch (Exception e)
        {
            System.out.print(e.getMessage());
        }
    }

    /***
     * stock the card selected on a tmp
     * Remove the card selected and return it
     * @param index
     * @return
     */
    public Card put_Down(int index)
    {
        try{
            Card tmp = (Card) listOfCards.get(index);
            listOfCards.remove(index);
            return tmp;
        }catch (Exception e )
        {
            System.out.print(e.getMessage());
            return null;
        }
    }

    /***
     * Stock the first card
     * Set the first card with the second card
     * then set the second card with the socket card
     * @param index1
     * @param index2
     */
    public void Mix(int index1 , int index2)
    {
        try{
            Card tmp = (Card) listOfCards.get(index1);
            listOfCards.set(index1,listOfCards.get(index2));
            listOfCards.set(index2,tmp);
        }catch (Exception e)
        {
            System.out.print(e.getMessage());
        }
    }

    public int size()
    {
        return listOfCards.size();
    }

    public boolean isEmpty()
    {
        if(listOfCards.isEmpty())
            return true;
        else return false;
    }

    public Card set(int indexHand, Card card)
    {
        return listOfCards.set(indexHand, card);
    }

    public boolean contains(Card card)
    {
        return listOfCards.contains(card);
    }

    public void deleteIndex(int index)
    {
        listOfCards.remove(index);
    }

    public void delete(Card card){
        listOfCards.remove(card);
    }

    public void add(Card card)
    {
        listOfCards.add(card);
    }

    public Card get(int index) {
        return listOfCards.get(index);
    }

    @Override
    public String toString() {
        return listOfCards.toString();
    }
}