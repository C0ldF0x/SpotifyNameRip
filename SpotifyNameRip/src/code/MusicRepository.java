/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

import java.util.ArrayList;

/**
 *
 * @author Pedro
 */
public class MusicRepository {
    static ArrayList<Music> musicRepo = new ArrayList<>();
    
    public static void addMusic(Music m){
        musicRepo.add(m);
        System.out.println("Music created and added to repo!");
        System.out.println(m.getArtist()+" - "+m.getName());
    }
    
    public static void showStatus(){
        for (int i = 0; i < musicRepo.size(); i++) {
            System.out.print(musicRepo.get(i).out() + "Downloaded: ");
            if(musicRepo.get(i).downloaded)
                System.out.println("Yes");
            else
                System.out.println("No");
        }
    }
}
