import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.net.URISyntaxException;
import java.util.Optional;

/*
 * colors
 * #101b2d
 * #384156
 * #000003
 * block diag and check for cpu to not take unaviliable spot if false ....
 */
public class TicTacToe extends Application {
	
	
	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage launcher){
		new menuStage();
		
	}
}
/**
 * The menu stage where a user can set piece and select difficulty
 * Pre:None
 * Post: The menu stage is launched
 */
class menuStage {
	boolean difficult;
	int piece;
	boolean levelSet, pieceSet;
	 menuStage(){
		Stage menuStage = new Stage();
		menuStage.setTitle("Tic Tac Toe");//set form title

		Group root = new Group();
		Scene scene = new Scene(root, Color.rgb(16,27,45));
		
		menuStage.setScene(scene);
		Canvas cv = new Canvas(800,400);
		root.getChildren().add(cv);
		GraphicsContext gc = cv.getGraphicsContext2D();
		menuStage.setResizable(false);
		
		//create title
		gc.setFill(Color.WHITE);
		Font titleFont = Font.font("QuickSand", FontWeight.MEDIUM, 38);
		gc.setFont(titleFont);
		gc.fillText("Tic Tac Toe", 275, 60);
		
		//create difficulty button rectangles
		Rectangle easyRect = new Rectangle(200, 60);
		easyRect.setLayoutX(150);
		easyRect.setLayoutY(100);
		//root.getChildren().add(easyRect);
		gc.setFill(Color.rgb(56,65,86));
		gc.fillRect(150, 100, 200, 60);
		gc.setFill(Color.WHITE);
		gc.fillText("EASY", 202, 143);
		
		Rectangle hardRect = new Rectangle(200, 60);
		hardRect.setLayoutX(400);
		hardRect.setLayoutY(100);
		//root.getChildren().add(hardRect);
		gc.setFill(Color.rgb(56,65,86));
		gc.fillRect(400, 100, 200, 60);
		gc.setFill(Color.WHITE);
		gc.fillText("HARD", 452, 143);
		
		//Create buttons to select your piece
		
//		gc.setFill(Color.rgb(56,65,86));
		gc.setFill(Color.BLACK);
		gc.fillRect(200, 200, 100, 100);
		Rectangle blackRect = new Rectangle(100, 100);
		blackRect.setLayoutX(200);
		blackRect.setLayoutY(200);
		
		Rectangle whiteRect = new Rectangle(100, 100);
		whiteRect.setLayoutX(450);
		whiteRect.setLayoutY(200);
		//root.getChildren().add(whiteRect);
		gc.setFill(Color.rgb(255,255,255));
		gc.fillRect(450, 200, 100, 100);
		
		
		Label mouseLocation = new Label(); 
		root.getChildren().add(mouseLocation);
		scene.setOnMouseClicked(
				new EventHandler<MouseEvent>(){
				
					public void handle(MouseEvent e){
						
					//	String mousePosition = "("+e.getX()+ "," + e.getY() + ")";
						double clickX = e.getX();
						double clickY = e.getY();
						
						
						if(easyRect.contains(clickX-150, clickY-100)){//when easyRect is clicked draw it's border
							//clear border of other btn and redraw hardbtn
							gc.clearRect(344, 87, 300, 80);
							gc.setFill(Color.rgb(56,65,86));
							gc.fillRect(400, 100, 200, 60);
							gc.setFill(Color.WHITE);
							gc.fillText("HARD", 452, 143);
							gc.setStroke(Color.GRAY);
							//stroke easybtn
							gc.setLineWidth(10);
							gc.strokeRect(149, 100, 200, 60);
							difficult = false;
							levelSet = true;
						}
						if(hardRect.contains(clickX-400, clickY-100)){//when hardRect is clicked draw it's border
							//clear border of other btn and redraw easybtn
							gc.clearRect(144, 94, 212, 72);
							gc.setFill(Color.rgb(56,65,86));
							gc.fillRect(150, 100, 200, 60);
							gc.setFill(Color.WHITE);
							gc.fillText("EASY", 202, 143);
							gc.setStroke(Color.GRAY);
							//stroke hardbtn
							gc.setLineWidth(10);
							gc.strokeRect(400, 100, 200, 60);
							difficult = true;
							levelSet = true;
						}
						if(blackRect.contains(clickX-200, clickY-200)){//when blackRect is clicked draw it's border piece 1
							//clear border of other btn and redraw easybtn
							gc.clearRect(350, 190, 220, 120);
							gc.setFill(Color.rgb(255,255,255));
							gc.fillRect(450, 200, 100, 100);
							gc.setStroke(Color.GRAY);
							//stroke hardbtn
							gc.setLineWidth(10);
							gc.strokeRect(200, 200, 100, 100);
							piece =1;
							pieceSet = true;
						}
						if(whiteRect.contains(clickX-450, clickY-200)){//when whiteRect is clicked draw it's border piece 2
							//clear border of other btn and redraw easybtn
							gc.clearRect(150, 190, 220, 120);
							gc.setFill(Color.BLACK);
							gc.fillRect(200, 200, 100, 100);
							gc.setStroke(Color.GRAY);
							//stroke hardbtn
							gc.setLineWidth(10);
							gc.strokeRect(450, 200, 100, 100);
							piece =-1;
							pieceSet = true;
						}
					

//						mouseLocation.setLayoutX(0);
//						mouseLocation.setLayoutY(0);
//						mouseLocation.setTextFill(Color.WHITE);
//						mouseLocation.setFont(Font.font("Cambria", FontWeight.BOLD,20));
//						mouseLocation.setText(mousePosition);
						
						//check each click- create start button and allow the user to begin game when a piece and difficulty have been set
						if(levelSet==true&&pieceSet==true){
							gc.setFill(Color.rgb(56,65,86));
							gc.fillRect(313, 338, 115, 50);
							Font startFont = Font.font("QuickSand", FontWeight.MEDIUM, 28);
							gc.setFont(startFont);
							gc.setFill(Color.WHITE);;
							gc.fillText("START", 330, 372);
							Rectangle startRect = new Rectangle(315,335,115,50);
							//root.getChildren().add(StartRect);
							if(startRect.contains(clickX, clickY)){
								menuStage.close();
								new tttStage(piece, difficult);
							}	
						}
					}
				});
		
		menuStage.show();
	}
	
	}


/**
 * The game form where the tic tac toe board is displayed
 * Pre: A piece and difficulty are set and the start button clicked
 */
class tttStage{
	Game ttt = new Game();
	tttStage(int piece, boolean difficult){
		
		//initialize form
		Stage tttStage = new Stage();
		tttStage.setTitle("Tic Tac Toe");//set form title
		Group root = new Group();
		Scene scene = new Scene(root, Color.rgb(16,27,45));
		tttStage.setScene(scene);
		Canvas cv = new Canvas(800,400);
		root.getChildren().add(cv);
		GraphicsContext gc = cv.getGraphicsContext2D();
		tttStage.setResizable(false);
		
		//create a 2-D array of Rectangles to match board layout and monitor clicks
		Rectangle[][] spotClick = new Rectangle[3][3];//create rect array
		
		
		int rectY = 36;
		for(int i = 0; i<spotClick.length; i++){//init spotClick //row
			int rectX = 207;
			for(int j = 0; j<spotClick.length; j++){//column
				spotClick[i][j] = new Rectangle(100,100);
				spotClick[i][j].setLayoutX(rectX);
				spotClick[i][j].setLayoutY(rectY);
				//root.getChildren().add(spotClick[i][j]);
				rectX+=112;

			}
			rectY+=112;
		}
		
		
		//create board template
		gc.setFill(Color.rgb(56,65,86));//backdrop
		gc.fillRect(200, 30, 350, 350);
		gc.setFill(Color.rgb(56,75,100));//borders
		gc.fillRect(200, 30, 350, 20);//rows
		gc.fillRect(200, 140, 350, 20);
		gc.fillRect(200, 250, 350, 20);
		gc.fillRect(200, 360, 350, 20);
		gc.fillRect(200, 30, 20, 350);//columns
		gc.fillRect(310, 30, 20, 350);
		gc.fillRect(422, 30, 20, 350);
		gc.fillRect(530, 30, 20, 350);

		tttStage.show();
		player p = new player(scene, gc, ttt, spotClick, tttStage, piece);
		cpu c = new cpu(piece, ttt, difficult, gc, spotClick);
		Thread pt = new Thread(p, "player Thread");
		Thread ct = new Thread(c, "CPU Thread");
		
		
		ttt.setPiece(piece);
		
			pt.start();
			ct.start();
		
		
	}
	
	/**
	 * A method that performs the player move and add their piece to the board
	 * Pre: None
	 * Post: The player's move is made (added to form and in board array)
	 */
	public static class player implements Runnable{
		private Scene scene;
		private GraphicsContext gc;
		private Game ttt;
		private Rectangle[][] spotClick;
		private Boolean winnerFound = false;
		private Stage tttStage;
		private int piece;
		

		/**
		 * Constructor of player that creates an instance of player and sets global variables
		 * Pre: None
		 * Post: Instance of player created and global variables set
		 */
		public player(Scene sc, GraphicsContext g, Game t, Rectangle[][] spot, 
				Stage stage, int p){
			
			scene = sc;
			gc = g;
			ttt = t;
			spotClick = spot;
			tttStage = stage;
			piece = p;
			
		}
			
		public void run() {
			
			if(piece ==1){
				gc.setFill(Color.BLACK);	
			}else{
				gc.setFill(Color.WHITE);	
			}
			
			
				scene.setOnMouseClicked(
						new EventHandler<MouseEvent>(){
						
							public void handle(MouseEvent e){
								
								//String mousePosition = "("+e.getX()+ "," + e.getY() + ")";
								double clickX = e.getX();
								double clickY = e.getY();
								//boolean spotTaken = true;
					
	
										for(int i = 0; i<spotClick.length; i++){
											for(int j = 0; j<spotClick.length; j++){
												if(spotClick[i][j].contains(clickX-spotClick[i][j].getLayoutX(), clickY-spotClick[i][j].getLayoutY())){//when easyRect is clicked draw it's border
													//place player piece
													ttt.playerPlacePiece(j, i, piece);
													//print the board in console
													drawBoard(ttt, gc, spotClick);
													
													//This controls audio for the click sound
													//It follows a relative path so the jar audio would work
													//The template is from from https://stackoverflow.com/questions/24347658/getting-a-mp3-file-to-play-using-javafx
													try {
													Media sound = new Media(getClass().getResource("/audio/ButtonOffClick.mp3").toURI().toString());
													MediaPlayer mediaPlayer = new MediaPlayer(sound);
													mediaPlayer.play();		
													} catch (URISyntaxException e1) {
														e1.printStackTrace();
													}
													
													if(ttt.checkState() && !winnerFound){//print out winner dialog if game is won
														winnerFound = true;
														Alert alert = new Alert(AlertType.INFORMATION);
														alert.setTitle("Game Over!");
														alert.setHeaderText(null);
														alert.setContentText(ttt.getWinner());
														//alert.showAndWait(); 
														Optional<ButtonType> result = alert.showAndWait();
														if (result.get() == ButtonType.OK){
																System.out.println("winner");
																tttStage.close();
																new menuStage();
														}
													}
												}
											}
										}
								}
						});
			
									
									
							
						
		
		}
	}//end of player subclass
	/**
	 * A subclass that performs the cpu move and calls on the placeCpuPiece method of Game to place their piece to the board
	 * Pre: None
	 * Post: The player's move is made (added to form and in board array)
	 */
	public static class cpu implements Runnable{
		private int piece;
		private Game ttt;
		private boolean difficult;
		private GraphicsContext gc;
		private Rectangle[][] spotClick;

		/**
		 *Constructor that creates an instance of cpu
		 *Pre: None
		 *Post: An instance of cpu is created
		 */
		public cpu(int p, Game t, boolean d, GraphicsContext g, Rectangle[][] spot){
			piece = -p;
			ttt = t;
			difficult = d;
			gc = g;
			spotClick = spot;
			
		}
		/**
		 * Runnable that executes the computer's move
		 */
		public void run() {
			
				while(!ttt.checkState()){
					
					ttt.cpuPlacePiece(piece, difficult);
					//print the board to console
					drawBoard(ttt, gc, spotClick);
					
//					String musicFile = "res/ButtonClickOn.mp3";     // For example
//					Media sound = new Media(new File(musicFile).toURI().toString());
//					MediaPlayer mediaPlayer = new MediaPlayer(sound);
//					mediaPlayer.play();
		
				}
				
				
		}
		
	}//end of cpu subclass
	
	public static void drawBoard(Game ttt, GraphicsContext gc, Rectangle[][] spotClick){
		int[][] board;
		board = ttt.getBoard();
			for(int i = 0; i<board.length; i++){
				for(int j = 0; j<board.length; j++){
					if(board[i][j] == 1){
						gc.setFill(Color.BLACK);	
						gc.fillRect(spotClick[i][j].getLayoutX()+12, spotClick[i][j].getLayoutY()+12, 90, 90);
					}else if(board[i][j] == -1){
						gc.setFill(Color.WHITE);	
						gc.fillRect(spotClick[i][j].getLayoutX()+12, spotClick[i][j].getLayoutY()+12, 90, 90);
					}
				}
			}
		
		
		
	}
	
}