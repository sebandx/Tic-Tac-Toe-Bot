import java.util.*;
public class TicTacToe {
  //Checks how many moves will result in X winning
  public static int winnerPredicitonX() {
    int opportunities = 0;
    //Checks all possible moves X can play
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        if (board[i][j] != 'X' && board[i][j] != 'O') {
          board[i][j] = 'X';
          //Checks if move will result in X winning
          if(winner() == 'X'){
            opportunities++;
          }
          board[i][j] = (char)(i*3 + j + 1 + '0');
        }
      }
    }
    return opportunities;
  }

  //Checks how many moves will result in O winning
  public static int winnerPredictionO() {
    int opportunities = 0;
    //Checks all possible moves O can play
    for(int i = 0; i < 3; i++){
      for(int j = 0; j < 3; j++){
        if(board[i][j] != 'X' && board[i][j] != 'O'){
          board[i][j] = 'O';
          //Checks if move will result in O winning
          if(winner() == 'O'){
            opportunities++;
          }
          board[i][j] = (char)(i*3 + j + 1 + '0');
        }
      }
    }
    return opportunities;
  }

  //Class-wide array so that no input is necessary for methods
  static char[][] board = {{'1', '2', '3'}, {'4', '5', '6'}, {'7', '8', '9'}};

  public static void bestMove() {
    double bestScore = -1.0/0.0;
    int[] move = new int[2];
    //Loops through all possible moves
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        if (board[i][j] != 'X' && board[i][j] != 'O') {
          board[i][j] = 'O';
          double score = minimax(true);
          //Resets board position to normal
          board[i][j] = (char)(i*3 + j + 1 + '0');
          //Stores the most optimal move so far that has been found played
          if (score > bestScore) {
            bestScore = score;
            move[0] = i;
            move[1] = j;
          }
        }
      }
    }
    //Plays the move
    board[move[0]][move[1]] = 'O';   
  }

  public static double minimax(boolean isMaximizing) {
    //Checks if game has ended, if true, returns corressponding value
    char result = winner();
    if (result == 'X') {
      return -1.0;
    }
    else if (result == 'O') {
      return 1.0;
    }
    else if (result == 'T') {
      return 0.0;
    }

    if (isMaximizing == false) {
      double bestScore = -1.0/0.0;
      for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
          //Checks if position is occupied
          if (board[i][j] != 'X' && board[i][j] != 'O') {
            board[i][j] = 'O';
            //Calls minimax so that the program can check if their has been a winner, and if not play another move. (except this time the program would play X)
            //This allows the program to cycle through all possibilities
            double score = minimax(true);
            board[i][j] = (char)(i*3 + j + 1 + '0');
            bestScore = Math.max(score, bestScore);
          } 
        }
      }
      return bestScore;
    } else {
      //Similar to above
      double bestScore = 1.0/0.0;
      for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
          if (board[i][j] != 'X' && board[i][j] != 'O') {
            board[i][j] = 'X';
            double score = minimax(false);
            board[i][j] = (char)(i*3 + j + 1 + '0');
            bestScore = Math.min(score, bestScore);
          }
        }
      }
      return bestScore;
    }
  }

  public static void displayBoard(){
    //Displays game board
    System.out.print("|---|---|---|\n");
    for(int x = 0; x < 3; x++){
      for(int y = 0; y < 3; y++){
        System.out.print("| " + board[x][y] + " ");
      }
      System.out.print("|\n|---|---|---|\n");
    }
  }

  public static char winner() {
    //Checks if there is a horizontal 3 in a row 
    for (int i = 0; i < 3; i++) {
      char firstElement = board[i][0];
      for (int j = 1; j < 3; j++) {
        if (board[i][j] != firstElement) {
          break;
        } else if (j == 2) {
          return firstElement;
        }
      }
    }

    //Checks if there is a vertical 3 in a row
    for (int i = 0; i < 3; i++) {
      char firstElement = board[0][i];
      for (int j = 0; j < 3; j++) {
        if (board[j][i] != firstElement) {
          break;
        } else if (j == 2) {
          return firstElement;
        }
      }
    }

    //diagonals 1
    int xCoordinate = 0;
    for (int i = 0; i < 3; i++) {
      char firstElement = board[0][0];
      if (board[i][xCoordinate] != firstElement) {
        break;
      } else if (i == 2) {
        return firstElement;
      }
      xCoordinate++;
    }
    //diagonal 2
    xCoordinate = 2;
    for(int i = 0; i < 3; i++){
      char firstElement = board[0][2];
      if (board[i][xCoordinate] != firstElement) {
        break;
      } else if (i == 2){
        return firstElement;
      }
      xCoordinate--;
    }
    for (int k = 0; k < 3; k++) {
      for (int j = 0; j < 3; j++) {
        if (board[k][j] != 'X' && board[k][j] != 'O') {
          return 'N';
        }
      }
    }
    return 'T';
  }
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    Random whoGoesFirst = new Random();
    //Determines if the user wants to play multiplayer or vs a computer
    boolean multiplayer;
    System.out.println("A) Play with another player");
    System.out.println("B) Play with the computer");
    char choice = input.next().charAt(0);
    while (choice != 'A' && choice != 'B') {
      System.out.println("Please enter a valid choice");
      choice = input.next().charAt(0);
    }
    if (choice == 'A') {
      multiplayer = true;
    }
    else {
      multiplayer = false;
    }
    //Multiplayer Game
    int playerMove;
    if (multiplayer == true) {
      //Determines who goes first if user chooses multiplayer option 
      int randomInt = whoGoesFirst.nextInt(2);
      if (randomInt == 0) {
        System.out.println("Player 1 is X, Player 2 is O");
      } else {
        System.out.println("Player 1 is O, Player 2 is X");
      }
      displayBoard();
      for (int turn = 1; turn <= 9; turn++) {
        System.out.println("X's turn, enter a slot to place X in.");
        playerMove = input.nextInt();
        while (board[(playerMove - 1) / 3][(playerMove-1) % 3] == 'X' || board[(playerMove-1) / 3][(playerMove-1) % 3] == 'O') {
          System.out.println("Error: This cell has already been picked. Please pick another cell.");
          playerMove = input.nextInt();
        }
        board[(playerMove-1) / 3][(playerMove-1) % 3] = 'X';
        displayBoard();
        //Checks if X is winner
        if (winner() == 'X') {
          System.out.println("X is the winner");
          break;
        }
        //Checks if game is a tie
        if (winner() == 'T') {
          System.out.println("The game is a tie");
          break;
        }
        //Checks if X will win
        if(winnerPredicitonX() > 1){
          System.out.println("X will be the winner");
        }
        turn++;
        
        System.out.println("O's turn, enter a slot to place O in.");
        playerMove = input.nextInt();
        //Makes sure player cannot choose occupied cells
        while (board[(playerMove - 1) / 3][(playerMove-1) % 3] == 'X' || board[(playerMove-1) / 3][(playerMove-1) % 3] == 'O') {
          System.out.println("Error: This cell has already been picked. Please pick another cell.");
          playerMove = input.nextInt();
        }
        //Sets board position to the player move
        board[(playerMove - 1) / 3][(playerMove-1) % 3] = 'O';
        displayBoard();
        //Checks if O won
        if (winner() == 'O') {
          System.out.println("O is the winner");
          break;
        }
        //Checks if game is a tie
        if (winner() == 'T') {
          System.out.println("The game is a tie");
          break;
        }
        //Checks if O will win
        if(winnerPredictionO() > 1){
          System.out.println("O will be the winner");
        }
      }
    }
    //Player vs Computer
    else {
      System.out.println("Player is X, Computer is O.");
      displayBoard();
      int turn = 0;
      while (winner() == 'N') {
        if (turn % 2 == 0) {
          System.out.println("X's turn, enter a slot to place X in.");
          playerMove = input.nextInt();
          //Makes sure player doesn't place in already placed cells
          while (board[(playerMove - 1) / 3][(playerMove-1) % 3] == 'X' || board[(playerMove-1) / 3][(playerMove-1) % 3] == 'O') {
            System.out.println("Error: This cell has already been picked. Please pick another cell.");
            playerMove = input.nextInt();
          }
          //Sets board position to the player's move
          board[(playerMove-1) / 3][(playerMove-1) % 3] = 'X';
          displayBoard();
        }
        else {
          //Computer move
          bestMove();
          displayBoard();
        }
        //Checks if the game has ended
        if (winner() == 'T') {
          System.out.println("The game is a tie!");
          break;
        }
        else if (winner() == 'O') {
          System.out.println("O Wins!");
          break;
        }
        else if (winner() == 'T') {
          System.out.println("The game is a tie!");
          break;
        }
        //Checks if Bot will win
        if (winnerPredictionO() > 1) {
          System.out.println("O will be the winner");
        }
        turn++;
      }
    }
  }
}
  


