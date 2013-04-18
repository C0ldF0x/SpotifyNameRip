/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Pedro
 */
class FileManager {

    static void exportToFile(String[] nameList) {
        try{
        // Create file 
        FileWriter fstream = new FileWriter("SNR - MusicList.txt");
        BufferedWriter out = new BufferedWriter(fstream);
        for (int i = 0; i < nameList.length; i++) {
            out.write(nameList[i]+"\n");
        }
        
        //Close the output stream
        out.close();
        }catch (Exception e){//Catch exception if any
        System.err.println("Error: " + e.getMessage());
        }
        exportToFileQuick(nameList);
    }   
    
    static void exportToFileQuick(String[] nameList) {
        try{
        // Create file 
        FileWriter fstream = new FileWriter("SNR - MusicList(easy).txt");
        BufferedWriter out = new BufferedWriter(fstream);
        for (int i = 0; i < nameList.length; i++) {
            if(nameList[i].startsWith("Track"))
                out.write(nameList[i].substring(nameList[i].indexOf("\t"))+" - ");
            if(nameList[i].startsWith("Artist"))
                out.write(nameList[i].substring(nameList[i].indexOf("\t"))+"\n");
        }
        
        //Close the output stream
        out.close();
        JOptionPane.showMessageDialog(null, "Files generated!");
        }catch (Exception e){//Catch exception if any
        System.err.println("Error: " + e.getMessage());
        }
        
    }   
    
    static void downloadFile(Music music){
        try {
            System.out.println("Downloading :"+music.getName() + " - "+ music.getArtist());
            
            URL website = new URL(music.getLink());
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            FileOutputStream fos = new FileOutputStream(music.getName() + " - "+ music.getArtist()+".mp3");
            fos.getChannel().transferFrom(rbc, 0, 1 << 24);
        } catch (IOException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
