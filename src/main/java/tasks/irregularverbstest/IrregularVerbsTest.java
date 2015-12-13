package tasks.irregularverbstest;

import javax.swing.*;

/**
 * Created by Allexb.
 * @author Bogomolnyy Alexander
 * @version 1.00.30
 */
public class IrregularVerbsTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                new WorkingWindow();
            }
        });
    }
}
