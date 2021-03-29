class TestDrive {
    public static void main(String[] args) throws InterruptedException {
//        Scanner scanner = new Scanner(System.in);
//        String burgerType = scanner.nextLine();


        // create factory
        BurgerFactory burgerFactory = new BurgerStore();
        // order burger from factory
        burgerFactory.orderBurger("Chinese Burger");
        burgerFactory.orderBurger("American Burger");
        burgerFactory.orderBurger("Russian Burger");
    }
}

abstract class BurgerFactory {

    abstract Burger createBurger(String type);

    Burger orderBurger(String type) throws InterruptedException {
        Burger burger = createBurger(type);
        if (burger == null) {
            System.out.println("Sorry, we are not able to create this kind of burger\n");
            return null;
        }
        System.out.println("Making a " + burger.getName());
        burger.putBun();
        burger.putCutlet(); /* write your code here */
        burger.putSauce(); /* write your code here */
        Thread.sleep(1500L);
        System.out.println("Done a " + burger.getName() + "\n");
        return burger;
    }
}

class BurgerStore extends BurgerFactory {
    @Override
    Burger createBurger(String type) {
        switch (type) {
            case "Chinese Burger" :
                return new ChineseBurger(); /* write your code here */
            case "American Burger" :
                return new AmericanBurger(); /* write your code here */
            case "Russian Burger" :
                return new RussianBurger(); /* write your code here */
            default:
                return null;
        }
    }
}

abstract class Burger {
    private String name;

    Burger(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }

    void putBun() {
        System.out.println("Putting bun");
    }

    void putCutlet() {
        System.out.println("Putting cutlet");
    }

    void putSauce() {
        System.out.println("Putting sauce");
    }

}

class ChineseBurger extends Burger {
    ChineseBurger() {
        super("Chinese Burger");
    }
    /* write your code here */
}

class AmericanBurger extends Burger {
    AmericanBurger() {
        super("American Burger");
    }
    /* write your code here */
}

class RussianBurger extends Burger {
    RussianBurger() {
        super("Russian Burger");
    }
    /* write your code here */
}
