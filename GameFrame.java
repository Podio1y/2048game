import javax.swing.JFrame;

public class GameFrame extends JFrame{

	GameFrame(){
		
		GamePanel game = new GamePanel();
		this.add(game);
		this.setTitle("2048");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.pack();
		this.setResizable(false);
		this.setLocationRelativeTo(null);
	}
	
	public static void main(String[] args) {
        GameFrame frame = new GameFrame();
    }
}