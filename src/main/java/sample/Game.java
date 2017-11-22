package sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Game {
        List<Card> deck;
        Player player1 = new Player();


        public Game()
        {
                deck = new ArrayList();
        }


        public List<Card> CreateDeck(){
                for (int i = 0; i < 60; i++) {
                        Random rand = new Random();
                        int n = rand.nextInt(6);
                        if (n == 0) {
                                deck.add(new Dryad());
                        }
                        if (n == 1) {
                                deck.add(new Elf());
                        }
                        if (n == 2) {
                                deck.add(new Gnome());
                        }
                        if (n == 3) {
                                deck.add(new Goblin());
                        }
                        if (n == 4) {
                                deck.add(new Troll());
                        }
                        if (n == 5) {
                                deck.add(new Korrigan());
                        }

                }
                return deck;
        }
}
