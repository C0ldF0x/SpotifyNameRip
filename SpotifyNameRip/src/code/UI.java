/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

import java.awt.Color;
import java.awt.Container;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JPanel;

/**
 *
 * @author Pedro
 */
public class UI extends JFrame{
    
    JPanel basePanel = new JPanel();
    
    public UI(){
        super("SNR");
        Container c = getContentPane();
        c.setBackground(Color.LIGHT_GRAY);
        
        String [] head = {"Music", "Artist", "Duration","Link"};
        String [][] content = new String[MusicRepository.musicRepo.size()][4];
        int i=0;
        for (Music m : MusicRepository.musicRepo) {
            if (m!=null) {
                content[i][0]=m.getName();
                content[i][1]=m.getArtist();
                content[i][2]=m.getDuration();
                content[i][3]=m.getLink();
                i++;
            }
        }
        
        TableDialog td = new TableDialog(this, "SNR",content,head);
        td.setVisible(true);
        
        this.setSize(1, 1);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

    
    }
    
}
