package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Arrays;

public class masterMindCode {
    public int numberOfPegs, numberOfRows;
    public boolean allowDuplicate, allowBlank;
    public Color[] colors;

    masterMindCode(int pegs, int rows, boolean duplicates, boolean blank, int counter, Color[] colorList){ //masterMindCode constructor
        numberOfPegs = pegs;
        numberOfRows = rows;
        allowDuplicate = duplicates;
        allowBlank = blank;
        numberOfRows = counter;
        colors = colorList;
    }

    public Color[] generateCode() { //function to generate a random code
        randomColorGenerator colorGenerator = new randomColorGenerator(colors);
        Color[] code = new Color[numberOfPegs];
        if (allowBlank) {
            if (allowDuplicate) {
                for (int i = 0; i < numberOfPegs; i++) {
                    code[i] = colorGenerator.generateColor();
                }
            } else {
                for (int i = 0; i < numberOfPegs; i++) {
                    Color color = colorGenerator.generateColor();
                    if (Arrays.stream(code).anyMatch(j -> j == color)) {
                        if (i != 0) {
                            i--; }
                    } else {
                            code[i] = color;
                        }
                    }
                }
        } else {
            if (allowDuplicate) {
                for (int i = 0; i < numberOfPegs; i++) {
                    Color color = colorGenerator.generateColor();

                    if (Arrays.stream(code).anyMatch(j -> j == Color.BLACK)) {
                        i--;
                    } else {
                        code[i] = color;
                    }
                }
            } else {
                for (int i = 0; i < numberOfPegs; i++) {
                    Color color = colorGenerator.generateColor();

                    if (color == Color.BLACK) {
                        i--;
                    } else if(Arrays.stream(code).anyMatch(j -> j == color)) {
                        if (i != 0) {
                                i--;
                            }
                    }
                    else{
                        code[i] = color;
                    }
                }
            }
        }
        for (Color color : code) { //print out code in console
            if (color == Color.RED) {
                System.out.print("RED ");
            }else if(color == Color.BLUE){
                System.out.print("BLUE ");
            }else if(color == Color.YELLOW){
                System.out.print("YELLOW ");
            }else if(color == Color.GREEN){
                System.out.print("GREEN ");
            }else if(color == Color.WHITE){
                System.out.print("WHITE ");
            }else if(color == Color.BLACK){
               System.out.print("BLACK ");
            }else {
                System.out.print("ERROR ");
            }
        }
        System.out.print("\n");
        return code; //return the code
    }

    public boolean codeChecker(Color[] code, Circle[] circles, Circle[] pegs, int row) { //function to check if the code is correct
        int correct = 0;
        int rowMin = numberOfPegs * row;
        int pegsMin = numberOfPegs * row;

        Color[] check = new Color[numberOfPegs];
        for (int i = 0; i < numberOfPegs; i++) { //for loop to check if the colors the user entered matches the code
            check[i] = (Color) circles[rowMin].getFill();
            rowMin++;

        }

        for (int i = 0; i < numberOfPegs; i++) {

            if (code[i] == check[i]) {
                pegs[pegsMin].setFill(Color.RED);
                correct++;
            } else {
                for (int j = 0; j < numberOfPegs; j++) {
                    if (check[i] == code[j] && pegs[pegsMin].getFill() != Color.RED) {
                        pegs[pegsMin].setFill(Color.BLACK);
                    }
                }
            }

            pegsMin++;

        }

        return correct == numberOfPegs;
    }
}
