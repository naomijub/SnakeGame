import java.awt.EventQueue;
import javax.swing.JFrame;

public class SnakeGame extends JFrame {

	public SnakeGame(){
		add(new Board());
        
        setResizable(false);
        pack();
        
        setTitle("Snake");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		  
		EventQueue.invokeLater(new Runnable() {
	            @Override
	            public void run() {                
	                JFrame ex = new SnakeGame();
	                ex.setVisible(true);                
	            }
	        });
	}

}
