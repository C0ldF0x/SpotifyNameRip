/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

import java.io.BufferedWriter;
import java.io.FileWriter;

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
    }   
}
