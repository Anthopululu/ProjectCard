package sample;


public class Kingdom {

    public ListOfCards getKingdom() {
        return kingdom;
    }

    public void setKingdom(ListOfCards kingdom) {
        this.kingdom = kingdom;
    }

    public Kingdom(ListOfCards kingdom) {
        this.kingdom = kingdom;
    }

    ListOfCards kingdom = new ListOfCards();

    public void SetDown(ListOfCards Hand){
           this.kingdom.getCards().add(Hand.getCards().remove(Hand.getCards().size()-1));
    }



}
