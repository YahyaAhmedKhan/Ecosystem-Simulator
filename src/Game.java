import java.awt.EventQueue;
import javax.swing.JFrame;

public class Game extends JFrame {

    public Game() {
        
        initUI();
    }
    
    private void initUI() {
        Board board = new Board();
        EcosystemEngine engine = new EcosystemEngine();
        board.addEngine(engine);
        add(board);

        
        setResizable(false);
        pack();
        
        setTitle("Ecosystem");
        setLocationRelativeTo(null);        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        
        EventQueue.invokeLater(() -> {
            Game game = new Game();

            game.setVisible(true);
        });
    }
}