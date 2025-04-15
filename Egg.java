public class Egg {

// Properties
    private int Size = 10;
    private int Speed = 2;
    private int Health = 5;
    private String Color = "White";


    //Default Constructor
    public Egg(){}
    //Constructor
    public Egg(int Size, int Speed, int Health, String Color)
    {
        this.Size = Size;
        this.Speed = Speed;
        this.Health = Health;
        this.Color = Color;
    }

    //Methods
    public int getSize()
    {

        return Size;
    }
    public void setSize()
    {
        this.Size = Size;
    }
    public int getSpeed ()
    {
        return Speed;
    }
    public void setSpeed()
    {
        this.Speed = Speed;
    }
    public int getHealth()
    {
        return Health;
    }
    public void setHealth()
    {
        this.Health = Health;
    }
    public String getColor()
    {
        return Color;
    }
    public void setColor()
    {
        this.Color = Color;
    }


}
