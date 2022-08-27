package escom;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Puzzle {
    public String solution;
    public String progress;
    public int lives = 6;

    public Puzzle(String solution) {
        this.solution = solution; //Guardamos la palabra para adivinar
        this.progress = initializeProgress(solution); //Creamos una cadena con los espacios para cada letra
    }

    public String initializeProgress(String solution) {
        return solution.replaceAll("\\S", "_");
    }

    public void putGuessedRightLetter(char c){
        int index = -1;
        StringBuilder newProgress = new StringBuilder(progress);
        do {
            index = solution.indexOf(c, index+1);
            if(index != -1)
                newProgress.setCharAt(index, c);
        }while(index != -1);
        progress =  newProgress.toString();
    }

    public void checkAnswer(String answer){
        answer = answer.replaceAll("\\s", "");
        for (int i = 0; i < answer.length(); i++) {
            char letter = answer.charAt(i);
            if(lives <= 0 ){
                return;
            }
            if (solution.contains(String.valueOf(letter))){
                if(progress.contains(String.valueOf(letter))){
                    System.out.println(letter + " - es correcta");
                    putGuessedRightLetter(letter);
                }else{
                    System.out.println(letter + " - ya habia sido adivinada");
                }
            } else {
                System.out.println(letter + " - es incorrecta");
                lives--;
            }
        }
    }
    public void printHanged(){
        System.out.println("in state:" + (6 - lives) );
    }

    public void attempt()  {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        printStatus();
        try{
            String answer = br.readLine();
            checkAnswer(answer);
        }catch (Exception e){
            System.out.println(e);
        }

        if(!progress.contains("_")){
            System.out.println("Felicidades, has ganado");
        }else if (lives == 0){
            System.out.println("Has perdido :(");
        }else {
            System.out.println("Presiona <ENTER> para continuar");
            try{
                System.in.read();
            }catch(Exception e){
                System.out.println(e);
            }

            System.out.print("\033[H\033[2J");
            System.out.flush();
            attempt();
        }
    }

    public void printStatus(){
        printHanged();
        System.out.println("Vidas restantes: " + getLives());
        System.out.println(getProgress());
        System.out.println("Ingresa la o las letras que deesprobar:");
    }

    public String getSolution(){
        return solution;
    }
    public String getProgress() {
        return progress;
    }
    public int getLives() {
        return lives;
    }
}