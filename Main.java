public class Main {
    public static void main(String[] args) {

        //Creating Egg Objectss
        Egg myEgg = new Egg();
        Egg bigEgg = new Egg(10,5,6,"Yellow");

        //Accessing them
        System.out.println("Big Egg's Size: " + bigEgg.getSize());
        System.out.println("Big Egg's Speed: " + bigEgg.getSpeed());
        System.out.println("Big Egg's Health: " + bigEgg.getHealth());
        System.out.println("Big Egg's Color: " + bigEgg.getColor());
    }

}
