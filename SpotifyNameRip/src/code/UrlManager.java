/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 *
 * @author Pedro
 */
public class UrlManager {
    public static String getString(String site){
        String page="";
        try {
            String webPage = site;
            URL url = new URL(webPage);
            URLConnection urlConnection = url.openConnection();
            InputStream is = urlConnection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);

            int numCharsRead;
            char[] charArray = new char[1024];
            StringBuilder sb = new StringBuilder();
            while ((numCharsRead = isr.read(charArray)) > 0) {
                sb.append(charArray, 0, numCharsRead);
            }
            page = sb.toString();
            
        } catch (Exception e) {
            System.out.println("Url not found!");
        }
        
        
        return page;
    }

    static void downloadMusics() {
        for (int i = 0; i < MusicRepository.musicRepo.size(); i++) {
            MusicRepository.showStatus();
            if(!MusicRepository.musicRepo.get(i).downloaded){
                //FileManager.downloadFile(MusicRepository.musicRepo.get(i).out());
                MusicRepository.musicRepo.get(i).downloaded=true;
            }
        }
    }
    
    
}
