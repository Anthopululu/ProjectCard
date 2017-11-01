package sample;

import java.util.ArrayList;

public class ListOfCards {
    ArrayList cards = new ArrayList();




    public void addTroll(Troll t){
        this.cards.add(t);
    }

    public void addKorrigan(Korrigan k) {
        this.cards.add(k);
    }

    public void addGnome(Gnome g) {
        this.cards.add(g);
    }

    public void addGoblin(Goblin g) {
        this.cards.add(g);
    }

    public void addElf(Elf e) {
        this.cards.add(e);
    }

    public void addDryad(Dryad d) {
        this.cards.add(d);
    }


    public ArrayList getCards() {
        return cards;
    }

    public void setCards(ArrayList cards) {
        this.cards = cards;
    }
}
