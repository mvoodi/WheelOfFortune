import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Player> players = new ArrayList<>();
        boolean registration = true;
        System.out.println("If the registration of players is completed, enter 'START'");
        while(registration) {
            String name = scanner.next();
            if(!(name.equals("START") || name.equals("start"))){
                Player player = new Player();
                player.name = name;
                players.add(player);
            }
            else{
                registration = false;
            }
            clearConsole();

        }
        Collections.shuffle(players);
        System.out.println("This is the order of the players' moves in the game.");
        for ( Player i : players) {
            System.out.println(" " + i);
        }



    }



    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}