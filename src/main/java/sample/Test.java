package sample;

import com.sun.javafx.scene.layout.region.Margins;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Test {


    public List<Card> getDeck() {
        return deck;
    }

    public void setDeck(List<Card> deck) {
        this.deck = deck;
    }

    public Hand getMyHand() {
        return myHand;
    }

    public void setMyHand(Hand myHand) {
        this.myHand = myHand;
    }

    public Hand getAdvHand() {
        return advHand;
    }

    public void setAdvHand(Hand advHand) {
        this.advHand = advHand;
    }

    public Card getAdvFrontCard() {
        return advFrontCard;
    }

    public void setAdvFrontCard(Card advFrontCard) {
        this.advFrontCard = advFrontCard;
    }

    public Card getMyFrontCard() {
        return myFrontCard;
    }

    public void setMyFrontCard(Card myFrontCard) {
        this.myFrontCard = myFrontCard;
    }

    private List<Card> deck;
    private Hand myHand;
    private Hand advHand;
    private Card advFrontCard;
    private Card myFrontCard;

    public Test()
    {
        this.deck = Genereted_card();
        List<Card> tmp = Genereted_card();
        this.myHand = new Hand(tmp);
        List<Card> tmp2 = Genereted_card();
        this.advHand = new Hand(tmp2);
    }

    public  void tester()
    {
        Scanner scn = new Scanner(System.in);
        advFrontCard =  new Elf();
        myFrontCard = new Gnome();

        while(true)
        {
            System.out.println("My Front Card"+ myFrontCard.name);
            System.out.println("ADV Front Card"+ advFrontCard.name);
            System.out.print("Deck : ");
            Display(deck);
            System.out.print("MyHand : ");
            Display(myHand.getListOfCards());
            System.out.print("AdvHand : ");
            Display(advHand.getListOfCards());
            System.out.println("0 = Dyrad # 1 = Elf # 2 = Gnome # 3 = Goblin # 4 = Korrigan # 5 = Troll");

            int option = Integer.parseInt(scn.nextLine());
            Card tmp = myHand.getListOfCards().get(option);

            tmp.power(myHand,advHand,deck,myFrontCard,advFrontCard);
            System.out.println("\n");
        }

    }

    public void Display(List<Card> cards)
    {
        for(int i = 0 ; i < cards.size();i++)
        {
            System.out.print(cards.get(i).name + " ");

        }
        System.out.println();
    }

    /***
     * Create a list of cards who contain all the different card
     * @return
     */
    public List<Card> Genereted_card()
    {
        List<Card> tmp = new ArrayList<Card>();
        Dryad card1 = new Dryad();
        Elf card2 = new Elf();
        Gnome card3 = new Gnome();
        Goblin card4 = new Goblin();
        Korrigan card5 = new Korrigan();
        Troll card6 = new Troll();

        tmp.add(card1);
        tmp.add(card2);
        tmp.add(card3);
        tmp.add(card4);
        tmp.add(card5);
        tmp.add(card6);

        return tmp;

    }
}
