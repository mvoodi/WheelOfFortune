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
        int playersListCounter = 1;
        for(int i = 0; i < players.size(); i++) {
            System.out.println(playersListCounter + " " + players.get(i).name);
        }
        ArrayList<Word> words = new ArrayList<>();
        createWordsList(words);
        Collections.shuffle(words);
        StringBuilder hiddenWord = new StringBuilder(words.get(0).word);
        System.out.println(words.get(0).description);
        for (int i = 0; i < words.get(0).word.length(); i++) {
            System.out.print("\u001B[34m" + "█ " + "\u001B[0m");
        }
        boolean isVictory = false;
        ArrayList<Integer> guessedLetters = new ArrayList<>();
        ArrayList<Character> namedLetters = new ArrayList<>();
        int playerQueueCounter = 0;
        while(isVictory){
            System.out.println(words.get(0).description);
            System.out.println(players.get(playerQueueCounter).name + ", your turn.");
            String playersAnswer = scanner.nextLine();
            if(playersAnswer.length() > 1){
                if(playersAnswer.equals(hiddenWord)){
                    System.out.println("Congratulations, you have won. Your score is " + players.get(playerQueueCounter).score);
                    isVictory = true;
                }
                else{
                    System.out.println("Unfortunately, this is the wrong word." + players.get(playerQueueCounter).name + "You are out of the game.");
                    players.remove(playerQueueCounter);
                }

            }
            else{
                if(checkLetter(playersAnswer, hiddenWord, guessedLetters)){
                    players.get(playerQueueCounter).score += 100;
                }
                else{
                    System.out.println("Unfortunately, this letter is not in the word.");
                }
            }
            if(playerQueueCounter < players.size() - 1 && !checkLetter(playersAnswer, hiddenWord, guessedLetters)){
                playerQueueCounter++;
            }
            else if(playerQueueCounter == players.size() - 1 &&  !checkLetter(playersAnswer, hiddenWord, guessedLetters)){
                playerQueueCounter = 0;
            }
            
        }





    }
    public static boolean checkLetter(String letter, StringBuilder word, ArrayList<Integer> guessedletters){
         boolean hasLetter = false;
        for (int i = 0; i < word.length(); i++) {
            if(letter.equals(word.charAt(i))){
                guessedletters.add(i);
                hasLetter = true;
            }
        }
        return hasLetter;
    }

    public static void createWordsList(ArrayList<Word> words){
        Word elephant = new Word();
        elephant.word = "elephant";
        elephant.description = "A large mammal with a long trunk, large ears, and tusks, typically found in Africa and Asia.";
        words.add(elephant);

        Word galaxy = new Word();
        galaxy.word = "galaxy";
        galaxy.description = "A vast system of stars, gas, dust, and dark matter held together by gravitational forces.";
        words.add(galaxy);

        Word rainbow = new Word();
        rainbow.word = "rainbow";
        rainbow.description = "A meteorological phenomenon caused by reflection, refraction, and dispersion of light in water droplets.";
        words.add(rainbow);

        Word volcano = new Word();
        volcano.word = "volcano";
        volcano.description = "A mountain or hill with a crater or vent through which lava, rock fragments, hot vapor, and gases are ejected.";
        words.add(volcano);

        Word whale = new Word();
        whale.word = "whale";
        whale.description = "A large marine mammal of the order Cetacea, having a streamlined body, flippers, and a blowhole for breathing.";
        words.add(whale);

        Word tornado = new Word();
        tornado.word = "tornado";
        tornado.description = "A rapidly rotating column of air extending from a thunderstorm to the ground, often causing destruction along its path.";
        words.add(tornado);

        Word desert = new Word();
        desert.word = "desert";
        desert.description = "A dry, barren area of land, often sandy, with little or no precipitation and sparse vegetation.";
        words.add(desert);

        Word eclipse = new Word();
        eclipse.word = "eclipse";
        eclipse.description = "An astronomical event where one celestial body partially or completely covers another, such as a solar or lunar eclipse.";
        words.add(eclipse);

        Word avalanche = new Word();
        avalanche.word = "avalanche";
        avalanche.description = "A sudden and rapid flow of snow, ice, and debris down a mountain slope, often triggered by factors like weather or human activity.";
        words.add(avalanche);

        Word octopus = new Word();
        octopus.word = "octopus";
        octopus.description = "A marine mollusk with a soft body, eight tentacles, and a distinct head, known for its intelligence and ability to change color.";
        words.add(octopus);
    }


    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}