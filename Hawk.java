public class Hawk {

//Properties
    private int Size = 10;
    private int Speed = 2;
    private int Health = 5;
    private int Damage = 10;


    // Default Constructor
    public Hawk(){}

    // Constructor
    public Hawk(int Size, int Speed, int Health, int Damage){
        this.Size = Size;
        this.Speed = Speed;
        this.Health = Health;
        this.Damage = Damage;

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
    public int getDamage()
    {
        return Damage;
    }
public void setDamage()
{
    this.Damage = Damage;
}

}
