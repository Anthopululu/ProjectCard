package sample;
import java.util.Random;

public class Deck {


    public static ListOfCards NewDeck(){
        ListOfCards cards = new ListOfCards();
        for (int i = 0; i < 60; i++) {
            Random rand = new Random();
            int n = rand.nextInt(6);
            if (n == 0) {
                Dryad d = new Dryad();
                cards.addDryad(d);
            }
            if (n == 1) {
                Elf e = new Elf();
                cards.addElf(e);
            }
            if (n == 2) {
                Gnome gn = new Gnome();
                cards.addGnome(gn);
            }
            if (n == 3) {
                Goblin goblin = new Goblin();
                cards.addGoblin(goblin);
            }
            if (n == 4) {
                Troll t = new Troll();
                cards.addTroll(t);
            }
            if (n == 5) {
                Korrigan k = new Korrigan();
                cards.addKorrigan(k);
            }

        }
        return cards;


    }




}
