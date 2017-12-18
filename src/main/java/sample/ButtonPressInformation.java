package sample;

public class ButtonPressInformation {

    int id;
    int player;
    boolean hand;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public boolean isHand() {
        return hand;
    }

    public void setHand(boolean hand) {
        this.hand = hand;
    }

    public ButtonPressInformation(int id, int player, boolean hand) {
        this.id = id;
        this.player = player;
        this.hand = hand;
    }

    @Override
    public String toString() {
        return "ButtonPressInformation{" +
                "id=" + id +
                ", player=" + player +
                ", hand=" + hand +
                '}';
    }
}