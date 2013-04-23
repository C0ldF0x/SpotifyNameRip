/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

import static code.SpotifyNameRip.index;
import java.awt.Color;
import java.awt.Container;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Pedro
 */
public class UI extends JFrame{
    
    JPanel basePanel = new JPanel();
    static MusicPanel [] mpArray = new MusicPanel[MusicRepository.musicRepo.size()];
     static int indexPanel=0;
    public UI(){
        super("SNR");
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

        Container c = getContentPane();
        c.setBackground(Color.LIGHT_GRAY);
        //c.setLayout();
        JPanel a = new JPanel();a.setLayout(new BoxLayout(a,BoxLayout.Y_AXIS));
        JScrollPane jsp = new JScrollPane(a);
        add(jsp);
        
        for (int i = 0; i < mpArray.length; i++) {
            mpArray[i]=new MusicPanel(MusicRepository.musicRepo.get(i).getLink(),MusicRepository.musicRepo.get(i).getName() + " - " + MusicRepository.musicRepo.get(i).getArtist());
            a.add(mpArray[i]);
            a.add(new JSeparator(JSeparator.HORIZONTAL));
        }
        next();
        
        
        this.setSize(300, 400);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

    
    }
    public static void next(){
       mpArray[indexPanel].startNow();
       indexPanel++;
    
    }
}
