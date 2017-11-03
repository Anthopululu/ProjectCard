package sample;

public class Korrigan extends Card {

    public Korrigan()
    {
        this.name="Korrigan";
        this.pathToImage = "default";
    }

    public Korrigan(String name, String pathToImage)
    {
        this.name=name;
        this.pathToImage = pathToImage;
    }

    @Override
    public int power(){
        return 1;
    }
}
