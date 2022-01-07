import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener{
	
	// Planning on implementing settings page where game can be configured and saved in files
	static final int screenWidth = 500;
	static final int screenHeight = 500; 
	int points = 0;
	char direction = 'R';
	Boolean running = false;
	Timer t;
	Random r;
	int units = 4;
	int [][] board = new int [units][units];
	Font tileFont = new Font("Verdana", Font.BOLD, screenWidth/15);
	
	GamePanel(){
		r = new Random();
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.GRAY);
		this.setFocusable(true);
        this.setVisible(true);
		this.addKeyListener(new MyKeyAdapter());
		startG();
	}
	
	public void startG() {
		randomStart();
		printBoard();
	}

	public void printBoard(){
		for (int i = 0 ; i < units ; i++){

			for (int j = 0 ; j < units ; j++){

				if (j == units - 1){
					System.out.println(board[i][j]);
				}
				else{
					System.out.print(board[i][j]);
				}
			}
		}
	}

	public void randomStart(){
		int num = 0;
		for (int i = 0 ; i < units ; i++){

			for (int j = 0 ; j < units ; j++){
				num = r.nextInt(8);
				if (num <= 2){

					board[i][j] = 2;
					if (num == 1){
						board[i][j] = 4;
					}
				}
				else{
					board[i][j] = 0;
				}
			}
		}
	}

	public int numOpenTiles(){
		int count = 0;
		for (int i = 0 ; i < units ; i++){

			for (int j = 0 ; j < units ; j++){

				if (board[i][j] == 0){
					count++;
				}
			}
		}
		return count;
	}

	public boolean gameOver(){
		for (int i = 0 ; i < units ; i++){

			for (int j = 0 ; j < units ; j++){

				if (board[i][j] == 0){
					return false;
				}
			}
		}

		for (int i = 1 ; i < units - 1 ; i++){

			for (int j = 1 ; j < units - 1 ; j++){

				for (int shift = -1 ; shift < 2 ; shift+=2){
					if (board[i][j] == board[i + shift][j]){
						return false;
					}
					if (board[i][j] == board[i][j + shift]){
						return false;
					}
				}
			}
		}
		return true;
	}

	public void addNewTiles(){

		int openTiles = numOpenTiles();
		int num;
		int secondNum;

		for (int i = 0 ; i < units ; i++){

			for (int j = 0 ; j < units ; j++){

				num = r.nextInt((openTiles - 1)*3/2);
				secondNum = r.nextInt(4);

				if (board[i][j] == 0){

					if (num == 0){
						if (secondNum == 0){
							board[i][j] = 4;
						}
						else{
							board[i][j] = 2;
						}
					}
				}
			}
		}
	}

	public void move(){

		if (direction == 'L'){

			for (int k = 0 ; k < units ; k++){

				for (int i = 0 ; i < units ; i++){

					for (int j = 1 ; j < units ; j++){

						if (board[i][j-1] == 0 && board[i][j] != 0){
							board[i][j-1] = board[i][j];
							board[i][j] = 0;
						}
						else if (board[i][j-1] == board [i][j]){

							board[i][j-1] *= 2;
							board[i][j] = 0;
						}
					}
				}
			}
		}
		else if (direction == 'R'){
			
			for (int k = 0 ; k < units ; k++){

				for (int i = 0 ; i < units ; i++){

					for (int j = units - 1 ; j > 0 ; j--){

						if (board[i][j] == 0 && board[i][j-1] != 0){
							board[i][j] = board[i][j-1];
							board[i][j-1] = 0;
						}
						else if (board[i][j] == board [i][j-1]){

							board[i][j] *= 2;
							board[i][j-1] = 0;
						}
					}
				}
			}
		}
		else if (direction == 'U'){

			for (int k = 0 ; k < units ; k++){

				for (int i = 0 ; i < units ; i++){

					for (int j = 1 ; j < units ; j++){

						if (board[j-1][i] == 0 && board[j][i] != 0){
							board[j-1][i] = board[j][i];
							board[j][i] = 0;
						}
						else if (board[j-1][i] == board [j][i]){

							board[j-1][i] *= 2;
							board[j][i] = 0;
						}
					}
				}
			}
		}
		else if (direction == 'D'){
			
			for (int k = 0 ; k < units ; k++){

				for (int i = 0 ; i < units ; i++){

					for (int j = units - 1 ; j > 0 ; j--){

						if (board[j][i] == 0 && board[j-1][i] != 0){
							board[j][i] = board[j-1][i];
							board[j-1][i] = 0;
						}
						else if (board[j][i] == board [j-1][i]){

							board[j][i] *= 2;
							board[j-1][i] = 0;
						}
					}
				}
			}
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	
	public void draw(Graphics g) {
		background(g);
	}

	public void background(Graphics g){
		// Fill
		g.setColor(Color.lightGray);
		g.fillRect(0,0, screenWidth, screenHeight);

		// Alignment
		// g.setColor(Color.RED);
		// g.drawLine(0,0,screenWidth,screenHeight);
		// g.drawLine(screenWidth,0,0,screenHeight);

		// Borders
		g.setColor(Color.GRAY);
		g.fillRect(0,0,10,screenHeight);
		g.fillRect(screenWidth-10,0,10,screenHeight);

		g.fillRect(0,screenHeight-10,screenWidth,10);
		g.fillRect(0,0,screenWidth,10);
		
		// Rows
		for (int i = 0 ; i < (units-1) ; i++){
			g.fillRect(0, (i+1)*screenHeight/units - (i+1)*(10/(units-1)), screenWidth, 10);
		}
		
		// Columns
		for (int i = 0 ; i < (units-1) ; i++){
			g.fillRect((i+1)*screenWidth/units - (i+1)*(10/(units-1)), 0, 10, screenHeight);
		}

		for (int i = 0 ; i < units ; i++){

			for (int j = 0 ; j < units ; j++){

				if (board[i][j] != 0){
					drawTile(j, i, board[i][j], g);
				}
			}
		}
	}

	public int getExponent(int value){
		int exponent = 0;

		while (value != 1){
			value/=2;
			exponent++;
		}

		return exponent;
	}

	public void drawTile(int x, int y, int value, Graphics g){
		int xcoord = (16 + (4-units)) + x * (screenWidth/units) - (x+1)*(10/(units-1));
		int widthCap = (x+1) * (screenWidth/units) - (x+2)*(10/(units-1));

		int ycoord = (16 + (4-units)) + y * (screenWidth/units) - (y+1)*(10/(units-1));
		int heightCap = (y+1) * (screenWidth/units) - (y+2)*(10/(units-1));

		int exponent = getExponent(value);

		if (value <= 32){
			Color tile = new Color(255,255 - 26*exponent,255 - 51*exponent);
			g.setColor(tile);
		}
		else if (value <= 128 && value >= 64){
			Color tile = new Color(255,255 - 26*exponent,0);
			g.setColor(tile);
		}
		else{
			Color tile = new Color(255,255 - 26*exponent,255 - 26*exponent);
			g.setColor(tile);
		}
		
		g.fillRect(xcoord, ycoord, widthCap - xcoord, heightCap - ycoord);

		g.setColor(Color.BLUE);
		g.setFont(tileFont);
		g.drawString("" + value, (xcoord + (widthCap - xcoord)/2) - (screenWidth/units)/units, ycoord + (heightCap - ycoord)/2);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
	}
	
	public class MyKeyAdapter extends KeyAdapter{
		
		@Override
		public void keyPressed(KeyEvent k) {
			
			// WASD keys for direction
			switch(k.getKeyCode()) {
			case KeyEvent.VK_A:
				direction = 'L';
				System.out.println("left");
				break;
			case KeyEvent.VK_D:
				direction = 'R';
				System.out.println("right");
				break;
			case KeyEvent.VK_W:
				direction = 'U';
				System.out.println("up");
				break;
			case KeyEvent.VK_S:
				direction = 'D';
				System.out.println("down");
				break;
		}
		if (!gameOver()){
			move();
			addNewTiles();
			printBoard();
			repaint();
		}
		else{
			System.exit(0);
		}
	}
}
}