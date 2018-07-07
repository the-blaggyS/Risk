import javax.swing.JFrame;
import java.awt.EventQueue;

/**
 * It creates only a new Thread and executes Application
 */
public class Main
{

    public Main()
    {
        EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                Application app = new Application();
                app.setVisible(true);
            }
        }
        );
    }

}
