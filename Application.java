import java.awt.EventQueue;
import javax.swing.JFrame;

/**
 * Creates JFrame and adds the Game
 */
public class Application extends JFrame
{
    public Application()
    {
        add(new Game());
        
        //setResizable(false);
        pack();
        
        setTitle("Risk");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
}
