package gol;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.io.FileNotFoundException;

public class golMain {


    private static void printBoard(String[][] board) {
        // Print column headers 0,1,2,3 etc.
        System.out.print("  ");
        for (int col = 0; col < board[0].length; col++) {
            System.out.print(col + " ");
        }
        System.out.println(); // new line
    
        // Print a separator line
        System.out.print("  ");
        for (int col = 0; col < board[0].length; col++) {
            System.out.print("--");
        }
        System.out.println(); // new line
    
        // Print each row with a row header
        for (int row = 0; row < board.length; row++) {
            System.out.print(row + "| "); // Row header
            for (String cell : board[row]) {
                System.out.print((cell == null || cell.trim().isEmpty() ? " " : cell) + " ");
                //if cell is null or empty, true print " ", false print cell
            }
            System.out.println();
        }
    }

    public static void main(String[] args)throws IOException, FileNotFoundException{
    //ensure there is a gol file
        if (args.length <= 0 ){
        System.err.println("please initiate program with a gol file");
        System.exit(1);
    }
    //read gol file, line by line
    File file = new File(args[0]);
    BufferedReader bufferedRead = new BufferedReader(new FileReader(file));

    // declase line variable
    String line;
    String[][] board = new String[0][0];

    int rowLength = 0;
    int columnLength = 0;

    int initRow = 0;
    int initColumn = 0;
    
    while((line = bufferedRead.readLine()) != null){
        // System.out.println(line);
        String[] transformedLine = line.split(" ");


        //for DATA case
        LinkedHashMap<String, Character> charMap = new LinkedHashMap<>();
        int fillRow;
        int fillColumn;
        int rowCompare = initRow; // if iterate to new readLine, row++, column = 0
        int columnCompare = 0; //if skip over white space, coulmn++

        if(transformedLine[0].equals("#")){
            continue;
        }else{
            switch (transformedLine[0]) {
                case "GRID":
                    rowLength = Integer.parseInt(transformedLine[1]);
                    columnLength = Integer.parseInt(transformedLine[2]);
                    board = new String[rowLength][columnLength];//create 2d array object with correct size
                    break;

                case "START":
                //init pos on board
                    initRow = Integer.parseInt(transformedLine[1]);
                    initColumn = Integer.parseInt(transformedLine[2]);
                    board [initRow][initColumn] = "*";//follows on from GRID case
                    // printBoard(board);

                    break;

                case "DATA":
                    while((line = bufferedRead.readLine()) != null){ //read next line after DATA, breaks once no more lines left
                        char[] charArray = line.toCharArray(); //charArray re-initialised to empty every row
                        columnCompare = 0; //re-initialise to 0
                        for(char c: charArray){//iterate through each char element in char array
                            //return index
                            if(c =='*'){
                                charMap.put(String.format("%s, %s", rowCompare, columnCompare), '*');
                                columnCompare ++; //go to next char (column)
                            }else if(c == ' '){
                                columnCompare ++; // go to next char (column)
                            }   
                        }
                        rowCompare ++; //moving onto next row, another readLine
                    }

                    //DATA case is flawed above, we adjust the column counter within charMap
                    int colOffSet = 0;
                    Iterator<String> iterator = charMap.keySet().iterator();
                    if(iterator.hasNext()){
                        String firstKey = iterator.next();
                        colOffSet = initColumn - Integer.parseInt(firstKey.split(",")[1].trim());
                    }
                    //add offset to column counter
                    for(String key:charMap.keySet()){
                    fillRow = Integer.parseInt(key.split(",")[0].trim());
                    fillColumn = Integer.parseInt(key.split(",")[1].trim()) + colOffSet;
                    board [fillRow][fillColumn] = "*";
                    System.out.printf("row:%s, column:%s\n", fillRow, fillColumn);
                    }

            
                    break;


                default:
                    break;
            }
        }
    } //end of while loop
    bufferedRead.close();

    int generation = 0;
    golSimul simulator = new golSimul();

    while(generation < 5){
        printBoard(board);
        String[][] newBoard = simulator.simulate(board);
        board = newBoard;
        generation++;
    }













    }

}
