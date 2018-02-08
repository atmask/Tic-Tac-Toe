//import java.io.File;

//import javafx.scene.media.Media;
//import javafx.scene.media.MediaPlayer;

/*
 * Game class of Tic Tac Toe
 */
public class Game {
//final private static int stalemate = 9;
final private static int whiteWin = -3;
final private static int blackWin = 3;
private static int[][] board; 
private boolean playerMoved = false;
private static int winnerIndex;
private static int stalemateCounter = 0;
	/*
	 * Create a game object of tic tac toe
	 * Pre: None
	 * Post: A tic tac toe game is created
	 */
	public Game(){
	board = new int[3][3];
	stalemateCounter = 0;
	//test board in which 'x' wins
	//	board[0][0]=1;
	//	board[1][1]=1;
	//	board[2][2]=1;
	}
	/**
	 * Check if a player has the game is over 
	 * Pre:None
	 * Post: true is returned if a player has won the game, false is returned otherwise
	 */
	public boolean checkState(){
		int index=-1;
		boolean gameWon = false;
		
		//check for win in a straight row
		for(int i = 0; i<board.length; i++){
			
			if((board[i][0]==board[i][1]&&
			 board[i][1]==board[i][2])&&
			 (board[i][0]==-1||
			 board[i][0]==1)){
				
				gameWon=true;
				index = i;
				i = board.length;
				//System.out.println("row win");	
			}
		}
		//check for win in a straight column
		for(int j = 0; j<board.length; j++){
			
			if(board[0][j]==board[1][j]&&
			 board[1][j]==board[2][j]&&
			 (board[0][j]==-1||
			 board[0][j]==1)){
				
				gameWon=true;
				index=j; 
				j=board.length;
				//System.out.println("column win: " + j);

			}
		}	
		//check for diagonal win
		if((board[0][0]==board[1][1]&&
			board[1][1]==board[2][2] ||
			board[2][0]==board[1][1]&&
			board[1][1]==board[0][2])&&
			(board[1][1]==-1||
			board[1][1]==1)){
				gameWon = true;
				//System.out.println("diagonal win");

		}
		//check for stalemate
		if(stalemateCounter>=9){
			gameWon = true;
			//System.out.println("Stalemate!");
			//System.exit(0);
		}
		//if Game has been won or stalemated call display winner
		if(gameWon == true){
			displayWinner(index);//call display winner class to find winner and print to console	
		}
	return gameWon;
	}
	/**
	 * Check if a player has won or if the game is over 
	 * Pre:None
	 * Post: True is returned if a player has won the game or the game is over, false is returned otherwise.
	 */
	public static boolean checkGameState(){
		int index=-1;
		boolean gameWon = false;
		
		//check for win in a straight row
		for(int i = 0; i<board.length; i++){
			
			if((board[i][0]==board[i][1]&&
			  board[i][1]==board[i][2])&&
			  (board[i][0]==-1||
			  board[i][0]==1)){
				gameWon=true;
				index = i;
				i = board.length;
				//System.out.println("row win");
			}
		}
		//check for win in a straight column
		for(int j = 0; j<board.length; j++){
			
			if(board[0][j]==board[1][j]&&
			 board[1][j]==board[2][j]&&
			 (board[0][j]==-1||
			 board[0][j]==1)){
				
				gameWon=true;
				index=j;
				j=board.length;	//System.out.println("column win: " + j);
			}
		}	
		//check for diagonal win
		if((board[0][0]==board[1][1]&&
			board[1][1]==board[2][2] ||
			board[2][0]==board[1][1]&&
			board[1][1]==board[0][2])&&
			(board[1][1]==-1||
			board[1][1]==1)){
				gameWon = true;
				//System.out.println("diagonal win");

		}
		//check for stalemate
		if(stalemateCounter>=9){
			gameWon = true;
			//System.out.println("Stalemate!");
		}
		//if Game has been won or stalemated call display winner
		if(gameWon == true){		
			displayWinner(index);
		}
			
	return gameWon;
	}
	/**
	 * Place a game piece on the board 'blac' = 1 'white'=-1 and empty = 0
	 * Pre: None
	 * Post: The player's piece is place on the board
	 */
	public synchronized boolean playerPlacePiece(int column, int row,  int piece){
	boolean spotTaken = false;
		
		while(playerMoved){//if the player has made their turn, wait
			try{
				//System.out.println("PLAYER WAITING");
				wait();
			}catch (InterruptedException e){}
		}
		
		//Begin player turn once woken up from wait
		//System.out.println("Player MOVING");

		if(board[row][column]==0){//set piece
			board[row] [column] = piece;
			playerMoved = true;
			//System.out.println("player done");
			stalemateCounter+=1;
			//wake up cpu thread
			notifyAll();
		}else{
			spotTaken = true;
			System.out.println("Spot Taken");
		}
		
	return spotTaken;
	}
	/**
	 * Place a game piece on the board 'x' = 1 'o'=-1 and empty = 0
	 * Pre: None
	 * Post: The player's piece is place on the board
	 */
	public synchronized boolean cpuPlacePiece(int piece, boolean difficult){
		boolean spotTaken = false;
		
		while(!playerMoved){//while the player hasn't moved the cpu thread must wait
			try{
				System.out.println("CPU WAITING");
				wait();
			}catch (InterruptedException e){}
		}
		
		//wake up cpu thread
		//System.out.println("CPU MOVING");
		int pos[][];
		int[][] center = new int[1][2];
		center[0][0] = 1;
		center[0][1] = 1;
		//System.out.println("Difficult: " + difficult);
		if(!checkGameState()){//if the game is not over it is the computer's turn
			
			if(checkSpot(1,1)){//take center spot if aviliable
				
				pos = center;
				
			}else{//find cpu spot
				
				if(difficult){//use spot finding algorithm
					pos = makeCpuMove(piece);
				}else{//take first empty spot
					pos = Game.getEmptySpots();
				}
				
			}
			
			//place piece
			if(board[pos[0][0]][pos[0][1]]==0){
				board[pos[0][0]] [pos[0][1]] = piece;//place piece
				stalemateCounter+=1;
			}else{//spot is taken! should never enter here
				spotTaken = true;	
			}
		//finish turn and wake up player thread
		playerMoved = false;
		//System.out.println("CPU DONE");
		notifyAll();
		}
	return spotTaken;
	}
	/**
	 * Find the sum of an empty spot's row and column 
	 * Pre: None
	 * Post: the sum values of each empty spot's column and row are returned
	 */
	private static int[][] evaluateSpot(){
		int[][] emptySpots = getEmptySpots();
		int[][] sum = new int [9-stalemateCounter][2];//9
			//run t6hrough all empty spots and find the sum of their row and column accordingly. These will be stored in array sum w/ meaningful index to emptySpots
			for(int i=0; i<emptySpots.length; i++){
				
				for(int j = 0; j<board.length; j++){
					
					sum[i][0] += board[emptySpots[i][0]][j];//find sum of all spot row's
					
					sum[i][1] += board[j][emptySpots[i][1]];//find sum of all empty spots columns
					
				}
				//error checking
				//System.out.println("Sum of row "+emptySpots[i][0]+" is "+sum[i][0]);
				//System.out.println("Sum of column "+emptySpots[i][1]+" is "+sum[i][1]);

			}
			
			//error checking: print sum array
//			for(int k = 0; k<sum.length; k++){
//				System.out.println("Sums: "+sum[k][0]+", "+sum[k][1]);
//			}

	return sum;
	}
	/**
	 * find the sum of both diagonals on the board
	 * Pre:none
	 * Post: The sum of the diagonals is returned
	 */
	private static int[][] evaluateDiagonal(){
		int[][] emptySpots = getEmptySpots();
		int upwardIndex =-1;
		int downwardIndex =-1;
		int[][]diagSum = new int[2][2];
		
		for(int i=0; i<emptySpots.length; i++){//check empty spots to see if an empty spot corresponds with a diagonal spot 
				if((emptySpots[i][0]==0 && emptySpots[i][1]==0)||(emptySpots[i][0]==1 && emptySpots[i][1]==1)//this will check a down-right sum
						||(emptySpots[i][0]==2 && emptySpots[i][1]==2)){
					downwardIndex = i;
					diagSum[0][0]=board[0][0]+board[1][1]+board[2][2];
					diagSum[0][1] = downwardIndex;
					
				}
				if((emptySpots[i][0]==2 && emptySpots[i][1]==0)||(emptySpots[i][0]==1 && emptySpots[i][1]==1)//this will check a up-right sum
						||(emptySpots[i][0]==0 && emptySpots[i][1]==2)){
					upwardIndex = i;
					diagSum[1][0]=board[0][2]+board[1][1]+board[2][0];
					diagSum[1][1] = upwardIndex;
				
				}
		}
			//error checking - print out diagSum array
//			for(int j = 0; j<diagSum.length; j++){
//				if(j==0){
//				System.out.println("Diag sum DR: "+diagSum[j][0]+", "+diagSum[j][1]);
//			}else{
//				System.out.println("Diag sum uR: "+diagSum[j][0]+", "+diagSum[j][1]);
//			}
//			
//			
//			}
	return diagSum;	
	}
	/**
	 * Compare the value of each move and return the location of the optimal move
	 * Pre: None 
	 * Post: The location of the optimal move is returned
	 */
	private static int[] evaluateMove(int piece){
		int[] spotLocation = new int[2];
		int[][] sum = evaluateSpot();
		int[][]diagSum = evaluateDiagonal();
		int[][]emptySpots = Game.getEmptySpots();
		int maxValue=Integer.MIN_VALUE;
		int minValue=Integer.MAX_VALUE;
		int maxIndex=0;
		int minIndex=0;
		
		//Find greatest value move 
		for(int i = 0; i<sum.length; i++){
			for(int j=0; j<2; j++){
				if(sum[i][j]>maxValue){
					maxValue = sum[i][j];
					maxIndex = i;
					//System.out.println("Current Greatest Val: "+sum[i][j]);
					//System.out.println("new Max Value: "+maxValue);
				}
			}
		}
		//Find least value move
		for(int i = 0; i<sum.length; i++){
			for(int j=0; j<2; j++){
				if(sum[i][j]<minValue){
					minValue = sum[i][j];
					minIndex = i;
					//System.out.println(sum[i][j]);
				}
			}
		}
		
		
		//check if diagonal sum is greater than current  max value or less than min value. If it is then replace w/ diagonal value and index
		if(diagSum[0][0]<minValue){
			minValue = diagSum[0][0];
			minIndex = diagSum[0][1];
		}
		if(diagSum[0][0]>maxValue){
			maxValue = diagSum[0][0];
			maxIndex = diagSum[0][1];
		}
		if(diagSum[1][0]<minValue){
			minValue = diagSum[1][0];
			minIndex = diagSum[1][1];
		}
		if(diagSum[1][0]>maxValue){
			maxValue = diagSum[1][0];
			maxIndex = diagSum[1][1];
		}
		
		//here		
		if(piece<0){//white>
			if((-(minValue))>=maxValue){//cpu can winn
				spotLocation[0]=emptySpots[minIndex][0];
				spotLocation[1]=emptySpots[minIndex][1];

			}else if((maxValue>(-(minValue)))){//cpu can block
				spotLocation[0]=emptySpots[maxIndex][0];
				spotLocation[1]=emptySpots[maxIndex][1];
			}
		}
		if(piece>0){//black<
			if((maxValue>=(-(minValue)))){
				spotLocation[0]=emptySpots[maxIndex][0];
				spotLocation[1]=emptySpots[maxIndex][1];
			}else if((-(minValue))>maxValue){
				spotLocation[0]=emptySpots[minIndex][0];
				spotLocation[1]=emptySpots[minIndex][1];
			}
		}
			System.out.println("sending spot: "+spotLocation[0]+", "+spotLocation[1]);
			return spotLocation;
	}
	/**
	 * Make the cpu Move
	 * Pre: None
	 * Post: The move is made
	 */
	private static int[][] makeCpuMove(int piece){
		int[][] positions = new int[1][2];
		int row = 1;
		int column = 1;
		//add one to both row and column
		int[] spot = evaluateMove(piece);
		row = spot[0];
		column = spot[1];
		positions[0][0] = row;
		positions[0][1] =column;

		return positions;
		//ttt.placePiece(column, row, (-(piece)));
	}

	/**
	 * If game is won print the winner
	 * Pre: None
	 * Post: The winner is printed
	 */
	private static void displayWinner(int index){
		int winnerCheck=0;
		boolean winnerFound = false;

		if(index==-1){//check for diagonal winner
				winnerCheck =board[0][0]+board[1][1]+board[2][2];
					if(winnerCheck == whiteWin){
						System.out.println("White Wins 1");//-1
						winnerFound=true;
						//System.exit(0);
						
					} else if(winnerCheck == blackWin){
						System.out.println("Black wins 1");//1
						winnerFound=true;
						//System.exit(0);
					}
				winnerCheck =board[2][0]+board[1][1]+board[0][2];
					if(winnerCheck == whiteWin){
						System.out.println("White Wins 2");
						winnerFound=true;
						//System.exit(0);
					} else if(winnerCheck == blackWin){
						System.out.println("Black wins 2");//1
						winnerFound=true;
						//System.exit(0);
					}
				
					
		}else{//check for vertical or horizontal win
			winnerCheck = board[index][0]+board[index][1]+board[index][2];
			if(winnerCheck == whiteWin){
				System.out.println("White Wins 3");
				winnerFound=true;
				//System.exit(0);
			} else if(winnerCheck == blackWin){
				System.out.println("Black wins 3");//1
				winnerFound=true;
				//System.exit(0);
			}
			winnerCheck = board[0][index]+board[1][index]+board[2][index];
			if(winnerCheck == whiteWin){
				System.out.println("White Wins 4");
				winnerFound=true;
				//System.exit(0);
			} else if(winnerCheck == blackWin){
				System.out.println("Black wins 4");//1
				winnerFound=true;
				//System.exit(0);
			}
		}
		
		if(winnerFound == false){
			System.out.println("Stalemate!");
			//System.exit(0);
		}
		winnerIndex = index;
	}
	/**
	 * If game is won print the winner
	 * Pre: None
	 * Post: The winner is printed
	 */
	public String getWinner(){
		int winnerCheck=0;
		boolean winnerFound = false;
		String winner = "";

		if(winnerIndex==-1){//check for diagonal winner
				winnerCheck =board[0][0]+board[1][1]+board[2][2];
				System.out.println("winnerCheck 1: "+winnerCheck);
					if(winnerCheck == whiteWin){
						System.out.println("O Wins 1");
						winnerFound=true;
						//System.exit(0);
						winner = "White Wins";
					} else if(winnerCheck == blackWin){
						System.out.println("X wins 1");
						winnerFound=true;
						//System.exit(0);
						winner = "Black Wins";
					}
				winnerCheck =board[2][0]+board[1][1]+board[0][2];
					if(winnerCheck == whiteWin){
						System.out.println("O Wins 2");
						winnerFound=true;
						//System.exit(0);
						winner = "White Wins";
					} else if(winnerCheck == blackWin){
						System.out.println("X wins 2");
						winnerFound=true;
						//System.exit(0);
						winner = "Black Wins";

					}
				
					
		}else{//check for vertical or horizontal win
			winnerCheck = board[winnerIndex][0]+board[winnerIndex][1]+board[winnerIndex][2];
			if(winnerCheck == whiteWin){
				System.out.println("O Wins 3");
				winnerFound=true;
				//System.exit(0);
				winner = "White Wins";
			} else if(winnerCheck == blackWin){
				System.out.println("X wins 3");
				winnerFound=true;
				//System.exit(0);
				winner = "Black Wins";
			}
			winnerCheck = board[0][winnerIndex]+board[1][winnerIndex]+board[2][winnerIndex];
			if(winnerCheck == whiteWin){
				System.out.println("O Wins 4");
				winnerFound=true;
				//System.exit(0);
				winner = "White Wins";
			} else if(winnerCheck == blackWin){
				System.out.println("X wins 4");
				winnerFound=true;
				//System.exit(0);
				winner = "Black Wins";

			}
		}
		
		if(winnerFound == false){
			System.out.println("Stalemate! 2");
			//System.exit(0);
			winner = "Stalemate";

		}
		return winner;
	}
	/**
	 * Set human player's piece to either 1 or -1 and set CPU value to the opposite
	 * Pre: None
	 * Post: The player's piece is set 
	 */
	public void setPiece(int p){
		if(p == 1){
			playerMoved = false;
		}else{
			playerMoved = true;
		}
			//System.out.println("Error: The piece is already set (game.Java - setPiece)"+Thread.currentThread().getStackTrace()[1].getLineNumber());
	}
	/**
	 * return a string representation of the tic tac toe game
	 * Pre: None
	 * Post: A string object of the tic tac toe game is returned
	 */
	public String toString(){
		String gameString;
		gameString ="";
			for(int i = 0; i<board.length; i++){
				for(int j = 0; j<board[0].length; j++){
					if(board[i][j]==1){
						gameString+="X  ";
					}else if(board[i][j]==-1){
						gameString+="O  ";
					}else{
						gameString+="   ";
					}
					
				}
				gameString+="\n";
			}
				
		return gameString;
	}
	/**
	 * Return the board array 
	 * Pre:None
	 * Post: The board array is returned
	 */
	public int[][] getBoard(){
		return board;
	}
	/**
	 * Check if a spot on the board is empty. Return true if empty and false otherwise
	 * Pre: None
	 * Post: True is returned if the spot is empty and false is returned otherwise
	 */
	public boolean checkSpotEmpty(int row, int column){
		boolean spotEmpty = true;
		if(board[row][column]!=0){
			spotEmpty = false;
		}
		return spotEmpty;
	}
	/**
	 * Return the location of all empty spots
	 * Pre: None
	 * Post: the location of all empty spots is returned
	 */
	public static int[][] getEmptySpots(){
		int[][] emptySpots = new int [9-stalemateCounter][2];
		
		int emptySpot = 0;
		for(int i = 0; i<board.length; i++){
			for(int j = 0; j<board.length; j++){
				if(board[i][j] == 0){
					emptySpots[emptySpot][0] = i;
					System.out.println("EmptySpots:");
					System.out.println(i);
					System.out.println(j);
					emptySpots[emptySpot][1] = j;
					emptySpot++;
				}
			}
		}
		return emptySpots;
	}

	/**
	 * Checks whether a spot is empty. True is returned if the spot is empty, flas otherwise
	 * Pre: None
	 * Post: the state of the spot is returned
	 */
	public static boolean checkSpot(int r, int c){
		boolean spotEmpty =false;
			if(board[r][c] ==0){
				spotEmpty = true;
			}	
		return spotEmpty;
	}
}