import javax.swing.JFrame;
public class Main {
    public static void main(String[] args) {

        //set game resolution
        int gameheight = 1000;
        int gamewidth = 1000;

//        //Creating Egg Objects
//        Egg myEgg = new Egg();
//        Egg bigEgg = new Egg(10,5,6,"Yellow");
//
//        //Accessing them
//        System.out.println("Big Egg's Size: " + bigEgg.getSize());
//        System.out.println("Big Egg's Speed: " + bigEgg.getSpeed());
//        System.out.println("Big Egg's Health: " + bigEgg.getHealth());
//        System.out.println("Big Egg's Color: " + bigEgg.getColor());


        //create the game window
        JFrame GameWindow = new JFrame();
        GameWindow.setTitle("DodgyDuck");
        GameWindow.setVisible(true);
        GameWindow.setResizable(false);
        GameWindow.setSize(gamewidth, gameheight);
        DodgyDuck DodgyDuck = new DodgyDuck();
        GameWindow.add(DodgyDuck);





    }

}
