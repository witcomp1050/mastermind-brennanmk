package edu.wit.comp1050;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Arrays;

public class masterMindCode {
    //variable declarations
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
        randomColorGenerator colorGenerator = new randomColorGenerator(colors); //create a randomColorGenerator
        Color[] code = new Color[numberOfPegs]; //create an array of colors, the size based on the user entry for number of pegs
        if (allowBlank) { //check if blanks are allowed
            if (allowDuplicate) { //check if duplicates are allowed
                for (int i = 0; i < numberOfPegs; i++) { //for every peg generate a color
                    code[i] = colorGenerator.generateColor();
                }
            } else { //if duplicates are not allowed
                for (int i = 0; i < numberOfPegs; i++) {
                    Color color = colorGenerator.generateColor();
                    if (Arrays.stream(code).anyMatch(j -> j == color)) { //check to make sure the color is not already in the code, if so subtract 1 from the iterator
                        if (i != 0) {
                            i--; }
                    } else { //if the color is not present in the code it can be added
                            code[i] = color;
                        }
                    }
                }
        } else { //if blanks are not allowed
            if (allowDuplicate) { //check if duplicates are allowed
                for (int i = 0; i < numberOfPegs; i++) { //generate a color for every peg
                    Color color = colorGenerator.generateColor();

                    if (color == Color.DARKGRAY) { //check to see if the randomly generated color is grey, if so subtract 1 from iterator
                        i--;
                    } else { //if the color is not blank add it to the code array
                        code[i] = color;
                    }
                }
            } else { //if duplicates are not allowed
                for (int i = 0; i < numberOfPegs; i++) {
                    Color color = colorGenerator.generateColor();

                    if (color == Color.DARKGRAY) { //check to see if the randomly generated color is grey, if so subtract 1 from iterator
                        i--;
                    } else if(Arrays.stream(code).anyMatch(j -> j == color)) { //if the generated color is not a blank, check to make sure the color is not already present in the code
                        if (i != 0) {
                                i--;
                            }
                    }
                    else{ //if the color is not a blank, and is not present in the code add it to the array
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
                System.out.print("BLANK ");
            }
        }
        System.out.print("\n");
        return code;
    }

    public boolean codeChecker(Color[] code, Circle[] circles, Circle[] pegs, int row) { //function to check if the code is correct
        int correct = 0;
        int rowMin = numberOfPegs * row;
        int pegsMin = numberOfPegs * row;

        Color[] check = new Color[numberOfPegs];
        for (int i = 0; i < numberOfPegs; i++) { //for loop to populate which colors to check the code against
            check[i] = (Color) circles[rowMin].getFill();
            rowMin++;

        }

        boolean[] accountedFor = new boolean[numberOfPegs]; //boolean array to check which pegs are accounted for

        for (int i = 0; i < numberOfPegs; i++){ //set accounted for to false
            accountedFor[i] = false;
        }
        int tempMin = pegsMin; //create a tempMin variable to be used in the first for loop

        for (int i = 0; i < numberOfPegs; i++) { //for loop to check the code against the user entry
            if (code[i] == check[i]) { //if the code matches set the peg to red
                pegs[tempMin].setFill(Color.RED);
                accountedFor[i] = true;
                correct++;
                tempMin++;
            }
        }

        for (int i = 0; i < numberOfPegs; i++) { //for loop to check the code against the user entry
            for (int j = 0; j < numberOfPegs; j++) {
                if (check[i] == code[j] && pegs[pegsMin].getFill() != Color.RED && !accountedFor[j]) { //check to see if the color matches, if the color is accounted for in the code already, and verify that the peg is not already set to red, if the conditions are met set the peg color to black.
                    pegs[pegsMin].setFill(Color.BLACK);
                    accountedFor[j] = true;
                    break;
                }
            }
        pegsMin++;
        }

        return correct == numberOfPegs; //if all of the pegs are correct return true, else return false
    }
}
