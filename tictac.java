import java.util.*;
import static java.lang.Math.max;
import static java.lang.Math.min;

public class MyClass {
    public static void printboard(char[][] board) {
        System.out.println("Board:");
        for(char[] row : board){
            for(char c : row)
                System.out.print(c + " ");
            System.out.println();
        }
    }   

    public static void insert(char[][] board, int pos, char user) {
        char sym = 'X';
        if(user=='O')   sym='O';
        switch(pos){
            case 1:
                board[0][0]=sym;
                break;
            case 2:
                board[0][1]=sym;
                break;
            case 3:
                board[0][2]=sym;
                break;
            case 4:
                board[1][0]=sym;
                break;
            case 5:
                board[1][1]=sym;
                break;
            case 6:
                board[1][2]=sym;
                break;
            case 7:
                board[2][0]=sym;
                break;
            case 8:
                board[2][1]=sym;
                break;
            case 9:
                board[2][2]=sym;
                break;
        }
    }
    
    public static int checkwin(char[][] board) {
        for(int i=0; i<3; i++) {
            char key = board[i][0];
            boolean flag = true;
            for(int j=0; j<3; j++){
                if(board[i][j] != key){
                    flag = false;
                    break;
                }
            }
            if(flag){
                if(key == 'X')  return 10;
                if(key == 'O')  return -10;
            }  
        }
        for(int i=0; i<3; i++) {
            char key = board[0][i];
            boolean flag = true;
            for(int j=0; j<3; j++){
                if(board[j][i] != key){
                    flag = false;
                    break;
                }
            }
            if(flag){
               if(key == 'X')  return 10;
               if(key == 'O')  return -10;
            }
        }
        char key = board[1][1];
        if(board[0][0] == key && board[2][2] == key){
            if(key == 'X')  return 10;
            if(key == 'O')  return -10;
        }
        if(board[0][2] == key && board[2][0] == key){
            if(key == 'X')  return 10;
            if(key == 'O')  return -10;
        }
        return 0;
    }

    public static Boolean movesLeft(char board[][]) {
        for(int i=0; i<3; i++)
            for(int j=0; j<3; j++)
                if(board[i][j] == '_')
                    return true;
        return false;
    }
    
    public static int[] bestMove(char[][] board) {
        int move[] = new int[2];
        int bestScore = Integer.MIN_VALUE;
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                if(board[i][j] == '_'){
                    board[i][j] = 'X';
                    int moveScore = minimax(board, 0, false);
                    board[i][j] = '_';
                    if(moveScore > bestScore){
                        move[0] = i;
                        move[1] = j;
                        bestScore = moveScore;
                    }
                }
            }
        }
        return move;
    }

    public static int minimax(char board[][], int depth, Boolean isMax) {
        int score = checkwin(board);
        if(score == 10 || score == -10)
            return score;
        if(movesLeft(board))
            return 0;
        if(isMax){
            int best = Integer.MIN_VALUE;
            for(int i=0; i<3; i++){
                for(int j=0; j<3; j++){
                    if(board[i][j] == '_'){
                        board[i][j] = 'X';
                        best = Math.max(best, minimax(board, depth+1, false));
                        board[i][j] = '_';
                    }
                }
            }
            return best;
        }
        else {
            int best = Integer.MAX_VALUE;
            for(int i=0; i<3; i++){
                for(int j=0; j<3; j++){
                    if(board[i][j] == '_'){
                        board[i][j] = 'O';
                        best = Math.min(best, minimax(board, depth+1, true));
                        board[i][j] = '_';
                    }
                }
            }
            return best;
        }
    }

    public static int getPosition(int[] pos) {
        int x = pos[0];
        int y = pos[1];
        if(x == 0)  return 1+x+y;
        if(x == 1)  return 3+x+y;
        return 5+x+y;
    }

    public static void main(String[] args) {
        char[][] board;
        int moves[] = new int[9];
        boolean got_winner = false;
        System.out.println("User is X. Computer is O.");
        System.out.println("Positions:\n1 2 3\n4 5 6\n7 8 9");
        board = new char[][]{{'_','_','_'},{'_','_','_'},{'_','_','_'}};
        Scanner sr = new Scanner(System.in);
        printboard(board);
        while(movesLeft(board)){
            System.out.println("Enter position from 1-9: ");
            int pos = sr.nextInt();
            if(pos > 9 || pos < 0){
                System.out.println("Enter Valid Position");
            }
            if(moves[pos-1] == 1){
                System.out.println("Position already filled");
                continue;
            }
            moves[pos-1] = 1;
            insert(board, pos, 'X');
            int[] h = bestMove(board);
            if(checkwin(board) == 10){
                got_winner = true;
                printboard(board);
                System.out.println("\nYou win");
                break;
            }
            pos = getPosition(h);
            moves[pos-1] = 1;
            insert(board, pos, 'O');
            if(checkwin(board) == -10){
                got_winner = true;
                printboard(board);
                System.out.println("\nComputer Wins");
                break;
            }
            printboard(board);
        }
        if(!got_winner)
            System.out.println("\nMatch Draw");
    }
}
