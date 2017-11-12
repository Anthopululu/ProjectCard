package sample;

import java.util.List;

public class Kingdom {

    List kingdom;

    public List getKingdom() {
        return this.kingdom;
    }

    public void setKingdom(List kingdom) {
        this.kingdom = kingdom;
    }

    public Kingdom(List kingdom) {
        this.kingdom = kingdom;
    }

    public void addCard(Card c)
    {
        kingdom.add(c);
    }

    public void removeCard(int n)
    {
        kingdom.remove(n);
    }
}
