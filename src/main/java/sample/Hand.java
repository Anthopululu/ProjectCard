package sample;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    List listOfCards;

    public Hand(List hand) {
        this.listOfCards = new ArrayList();
    }

    public List getHand() {
        return listOfCards;
    }

    public void setHand(List hand) {
        this.listOfCards = listOfCards;
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


}