import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Random;
import java.io.File;

class Word {

    /*
        Assumption made: Text file has one movie title per line.
        Selecting a random movie title from a text file via reservoir sampling.
     */
    public String selectRandomWord(File file) {
        String result = null;
        int n = 0;
        Random rand = new Random();

        try {
                for(Scanner scan = new Scanner(file); scan.hasNext(); ) {
                    n++;
                    String line = scan.nextLine();
                    if (rand.nextInt(n) == 0) {
                        result = line;
                    }
                }
        }
        catch(FileNotFoundException e) {
            System.out.println("File not found in class GetWord, method selectWord.");
        }

        return result;

    }

}


class HangmanGame{

    private String answer;
    private int chances = 2;
    public String[] currentAnswer;

    public void setAnswer(String word){
        this.answer = word;
    }

    public void initializeCurrentAnswer(){
        this.currentAnswer = new String[answer.length()];
        for(int i = 0; i < currentAnswer.length; i++){
            currentAnswer[i] = "_ ";
        }
    }

    public String getAnswer(){
        return this.answer;
    }

    public void displayCurrentAnswer(){

        for(String s : currentAnswer){
            System.out.print(s);
        }
        System.out.println();

    }

    public boolean checkWinner(){
        boolean winner = false;

        for(int i = 0; i < currentAnswer.length; i++){
            if(currentAnswer[i].equals("_ ")){
                winner = false;
                break;
            }else{
                winner = true;
            }
        }

        return winner;
    }

    public void guessLetter(){

        System.out.println("Guess a letter.");
        Scanner scan = new Scanner(System.in);
        String guess = scan.nextLine();

        int index = answer.indexOf(guess);
        if(index < 0){
            System.out.println("There are no " + guess + " in the title.");
            this.chances--;
        } else {
            while (index >= 0 ) {
                currentAnswer[index] = guess + " ";
                index = answer.indexOf(guess, index + 1);
            }
        }
    }

    public void playGame(String newAnswer){
        this.setAnswer(newAnswer);
        this.initializeCurrentAnswer();

        System.out.println("Let's play Hangman. You have "  + this.chances + " chances.");
        this.displayCurrentAnswer();

        while( this.chances > 0 ){

            this.guessLetter();

            if(chances < 1){
                System.out.println("Sorry, you lost.");
                break;
            }
            if(this.checkWinner()){
                System.out.println("WINNER!");
                break;
            }

            System.out.println("Guess again.");
            System.out.println("You have " + this.chances + " chances.");
            this.displayCurrentAnswer();
        }

    }
}


public class Hangman {

    public static void main(String[] args){
        File textFile = new File("movies.txt");
        Word movie = new Word();
        String selectedMovie = movie.selectRandomWord(textFile);

        HangmanGame newGame = new HangmanGame();
        System.out.println(selectedMovie);
        newGame.playGame(selectedMovie);


    }
}
