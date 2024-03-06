import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        clearConsole();
        Scanner scanner = new Scanner(System.in);
        Scanner scanner1 = new Scanner(System.in);
        Scanner scanner2 = new Scanner(System.in);
        ArrayList<Player> players = new ArrayList<>();
        boolean registration = true;
        ArrayList<Word> words = new ArrayList<>();
        createWordsList(words);
        Collections.shuffle(words);
        StringBuilder hiddenWord = new StringBuilder(words.get(0).word);
        boolean isVictory = false;
        ArrayList<Integer> guessedLettersIndex = new ArrayList<>();
        ArrayList<Character> guessedLettersList = new ArrayList<>();
        ArrayList<Character> namedLetters = new ArrayList<>();
        int playerQueueCounter = 0;
        while(registration) {
            System.out.println("\u001B[95m" + "If the registration of players is completed, enter 'START'"  + "\u001B[0m");
            String name = scanner.next();
            if(!(name.equals("START") || name.equals("start") || name.equals("Start"))){
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
        System.out.println("\u001B[95m" + "This is the order of the players' moves in the game." + "\u001B[0m");
        int playersListCounter = 1;
        for (Player player : players) {
            System.out.println("\u001B[95m" + playersListCounter + " " + player.name + "\u001B[0m");
            playersListCounter++;
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        clearConsole();
        
        // System.out.println(words.get(0).description);
        // System.out.println();
        // for (int i = 0; i < words.get(0).word.length(); i++) {
        //     System.out.print("\u001B[34m" + "█ " + "\u001B[0m");
        // }
        // System.out.println();
        
        
        while(!isVictory){
            boolean hasSecondMove = false;
            clearConsole();
            for (int i = 0; i < words.get(0).word.length(); i++) {
                if(writeLetter(words.get(0).word.charAt(i), guessedLettersList)){
                    System.out.print("\u001B[34m" + words.get(0).word.charAt(i) + " " + "\u001B[0m");
                }
                else {
                    System.out.print("\u001B[34m" + "█ " + "\u001B[0m");
                }
            }
            System.out.println();
            System.out.println();
            System.out.println( "\u001B[36m" + words.get(0).description + "\u001B[0m");
            System.out.println();
            System.out.println( "\u001B[95m" + players.get(playerQueueCounter).name + ", your turn." + "\u001B[0m");
            System.out.println();
            System.out.print("Already named letters: " );
            for (int i = 0; i < namedLetters.size(); i++) {
                System.out.print("\u001B[36m" + namedLetters.get(i) +  ", " + "\u001B[0m");
            }
            System.out.println();
            String answer = scanner1.nextLine();
            StringBuilder playersAnswer = new StringBuilder(answer);
            char[] replyingLetter = answer.toCharArray();
            if(!isNamedLetter(replyingLetter[0], namedLetters) && answer.length() <= 1){
                namedLetters.add(replyingLetter[0]);
            }
            if(answer.length() > 1){
                if(playersAnswer.compareTo(hiddenWord) == 0){
                    clearConsole();
                    System.out.println("\u001B[92m" + "Congratulations, you have won. Your score is " + players.get(playerQueueCounter).score + "\u001B[0m");
                    try {
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    isVictory = true;
                    clearConsole();
                }
                else{
                    clearConsole();
                    System.out.println("\u001B[31m" + "Unfortunately, this is the wrong word." + players.get(playerQueueCounter).name + ", You are out of the game." + "\u001B[0m");
                    players.remove(playerQueueCounter);
                    try {
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    clearConsole();
                }

            }
            else{
                if(checkLetter(replyingLetter[0], hiddenWord, guessedLettersIndex, guessedLettersList)){
                    clearConsole();
                    players.get(playerQueueCounter).score += 100;
                    System.out.println("\u001B[92m" + "Correct lettet! You get 100 points. Your score " + players.get(playerQueueCounter).score + ". \nYou can move one more time." + "\u001B[0m");
                    try {
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    hasSecondMove = true;
                }
                else if(isNamedLetter(replyingLetter[0], namedLetters)){
                    clearConsole();
                    System.out.println("\u001B[31m" + "This letter already named. " +  players.get(playerQueueCounter).name + ", Chose another" + "\u001B[0m");
                    try {
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    hasSecondMove = true;
                }
                else{
                    clearConsole();
                    System.out.println("\u001B[31m" + "Unfortunately, this letter is not in the word." + "\u001B[0m");
                    try {
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
            if(!checkMaxScore(players, playerQueueCounter, guessedLettersIndex, hiddenWord)){
                clearConsole();
                System.out.println("\u001B[95m" + "Player "  + players.get(playerQueueCounter).name + " has max score. Now every player have chance for enter word." + "\u001B[0m");
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                clearConsole();

                for (int i = 0; i < players.size(); i++) {
                    if(i != playerQueueCounter){
                        System.out.println("\u001B[95m" + "Player " + players.get(i).name + ", enter word!" + "\u001B[0m");
                        String lastChanceAnswer = scanner2.next();
                        StringBuilder chanceReply = new StringBuilder(lastChanceAnswer);
                        if(chanceReply.compareTo(hiddenWord) == 0){
                            clearConsole();
                            System.out.println("\u001B[92m" + players.get(i).name + " guessed the word and win the game!" + "\u001B[0m");
                            try {
                                Thread.sleep(4000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            isVictory = true;
                        }
                        else{
                            clearConsole();
                            System.out.println("\u001B[31m" + "Unfortunatly, you didn't guess the word." + "\u001B[0m");
                            try {
                                Thread.sleep(4000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                    clearConsole();

                }
                
                if(!isVictory){
                    clearConsole();
                    System.out.println("\u001B[92m" + "No one guessed the word! " + players.get(playerQueueCounter).name + "  WIN!" + "\u001B[0m");
                    try {
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    isVictory = true;
                }
            }
            if(!hasSecondMove && playerQueueCounter < players.size() - 1 && !checkLetter(replyingLetter[0], hiddenWord, guessedLettersIndex, guessedLettersList)){
                playerQueueCounter++;
            }
            else if(!hasSecondMove && playerQueueCounter == players.size() - 1 &&  !checkLetter(replyingLetter[0], hiddenWord, guessedLettersIndex, guessedLettersList)){
                playerQueueCounter = 0;
            }

        }
    }
    public static boolean isNamedLetter(char a, ArrayList<Character> lettersList){
        boolean guesedLetter = false;
        for (int i = 0; i < lettersList.size() - 1; i++) {
            if (a == lettersList.get(i)) {
                guesedLetter = true;
                break;
            }
        }
        return guesedLetter;
    }

    public static boolean writeLetter(char letter, ArrayList<Character> guesedLetter){
        boolean writeLetter = false;
        for (int index = 0; index < guesedLetter.size(); index++) {
            if(letter == guesedLetter.get(index)){
                writeLetter = true;
            }
        }
        return writeLetter;
    }

    public static boolean isGuesedLetter(int a, ArrayList<Integer> lettersList){
        boolean guesedLetter = false;
        for (int i = 0; i < lettersList.size() - 1; i++) {
            if (a == lettersList.get(i)) {
                guesedLetter = true;
            }
        }
        return guesedLetter;
    }


    public static boolean checkMaxScore(ArrayList<Player> players, int playerQueueCounter, ArrayList<Integer> guessedLettersIndex, StringBuilder hiddenWord){
        int maxScore = players.get(playerQueueCounter).score;
        int minScore = players.get(0).score;
        for (int i = 0; i <= players.size() - 1; i++) {
            if(players.get(i).score < minScore){
                minScore = players.get(i).score;
            }
        }
        int noGuesedLetters = hiddenWord.length() - guessedLettersIndex.size();
        boolean hasChance = (maxScore - minScore) < (noGuesedLetters * 100);
        return hasChance;

    }



    public static boolean checkLetter(char letter, StringBuilder word, ArrayList<Integer> guessedLettersIndex, ArrayList<Character> guessedLettersList){
         boolean hasLetter = false;
        for (int i = 0; i < word.length(); i++) {
            if(Objects.equals(letter, word.charAt(i))){
                guessedLettersIndex.add(i);
                guessedLettersList.add(letter);
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