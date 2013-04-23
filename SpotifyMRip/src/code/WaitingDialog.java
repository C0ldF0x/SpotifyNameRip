/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

/**
 *
 * @author Pedro
 */
public class WaitingDialog extends JDialog{
    JLabel info = new JLabel("");
    JProgressBar pbar = new JProgressBar();

    WaitingDialog(String message, int min, int max){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.setTitle("Waiting");
        setLayout( new GridBagLayout() );
        setDefaultCloseOperation( DO_NOTHING_ON_CLOSE );
        setSize( 400, 90 );
        setLocationRelativeTo( null );
        info.setText(message);
        pbar.setMaximum(max);
        pbar.setMinimum(min);
        pbar.setValue(0);
        add( info, new GridBagConstraints( 0, 0, 1, 1, 1.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets( 5, 5, 5, 5 ), 0, 0 ) );
        add( pbar, new GridBagConstraints( 0, 1, 1, 1, 1.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets( 5, 5, 5, 5 ), 0, 0 ) );
        this.setVisible(true);
    }
    public void updateBar(int newValue) {
    pbar.setValue(newValue);
  }
}
