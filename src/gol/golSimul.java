package gol;

public class golSimul {

    public int checkNeighbour(String[][] board, int row, int column){ // row and column referring to each cell we are referencing
        int neighbourCount = 0;
        int rows = board.length;
        int columns = board[0].length;

        if(row > 0 && column > 0 && "*".equals(board[row-1][column-1])){
            neighbourCount ++;
        }
        if(row > 0 && "*".equals(board[row-1][column])){
            neighbourCount ++;
        }
        if(row > 0 && column < columns - 1 && "*".equals(board[row - 1][column + 1])){
            neighbourCount ++;
        }
        if(column > 0 && "*".equals(board[row][column - 1])){
            neighbourCount ++;
        }
        if(column < columns - 1 && "*".equals(board[row][column + 1])){
            neighbourCount ++;
        }
        if(row < rows - 1 && column > 0 && "*".equals(board[row + 1][column - 1])){
            neighbourCount ++;
        }
        if(row < rows - 1 && "*".equals(board[row + 1][column])) {
            neighbourCount++;
        }
        if(row < rows - 1 && column < columns - 1 && "*".equals(board[row + 1][column + 1])){
            neighbourCount ++;
        }
        
        return neighbourCount;
    }
    
    public String[][] simulate(String[][] board) {
        String[][] newBoard = new String[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                int aliveNeighbours = checkNeighbour(board, i, j);
                String currentCell = board[i][j];

                if(currentCell != null && currentCell == "*"){
                    if(aliveNeighbours < 2 || aliveNeighbours > 3){
                        newBoard[i][j] = " ";
                    }else{
                        newBoard[i][j] = currentCell;
                    }

                }else{
                    if(aliveNeighbours == 3){
                        newBoard[i][j] = "*";
                    }else{
                        newBoard[i][j] = " ";
                    }
                }


            }
        }
        return newBoard;

    }
    
}
