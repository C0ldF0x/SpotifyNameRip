/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Pedro
 */
public class Handler {
    public static void convertIntoNames(String sourceCode){
        String aux = sourceCode;
        aux=sourceCode.substring(sourceCode.indexOf("mainContainer"),sourceCode.indexOf("engageView"));
        String [] beta = aux.split("\n");
        WaitingDialog wd = new WaitingDialog("Searching for music's info in playlist page",0,beta.length);
        
        //System.out.println(aux);
        beta[2]="";
        for (int i = 0; i < beta.length; i++) {
            wd.updateBar(i);
            if(!beta[i].contains("<li class") || beta[i].startsWith("</li><li") )
                beta[i]="";
            else{
            
            beta[i]=beta[i].replace("<li class=\"", "");
            beta[i]=beta[i].replace("\"","");
            beta[i]=beta[i].replace("</li>","");
            beta[i]=beta[i].replace("		","");
            
            
            if(beta[i].startsWith("artist"))
                beta[i]=beta[i].substring(beta[i].indexOf(">")+1);
            if(beta[i].startsWith("duration"))
                beta[i]=beta[i].substring(beta[i].indexOf(">")+1);
            if(beta[i].startsWith("track-title")){
                beta[i]=beta[i].substring(beta[i].indexOf(">")+1);
                beta[i]=beta[i].substring(beta[i].indexOf(".")+1);
            }
        }
        }
        
        List<String> result = new ArrayList<>();
        
        
        for (int i = 0; i < beta.length; i++) {
            if(!beta[i].equals(""))
                result.add(beta[i]);
        }
        wd.dispose();
        //this line creates the Music object
        createMusics(result);
        
        
    }
    //method for mp3skull site
    static void getMusicLink(Music music){
        try{
        String name = music.getName() + " - " + music.getArtist();
        name=name.replaceAll("-", "");
        name=name.replaceAll(" ", "_");
        String url = "http://mp3skull.com/mp3/"+name+".html";
        
        String code = UrlManager.getString(url);
        
        code=code.substring(code.indexOf("right_song"));
        
        code=code.substring(code.indexOf("<a href=\"")+9);
        code=code.substring(0,code.indexOf("\""));
        
        music.setLink(code);
        
            System.out.println("Link added: "+code);
        }catch(StringIndexOutOfBoundsException e){
            System.out.println("Error finding link for: "+music.getName() + " - "+music.getArtist());
        }
    }
    
    public static void createMusics(List<String> musics){
        WaitingDialog wd = new WaitingDialog("Adding musics to the repository",0,musics.size());
        for (int i = 0; i < musics.size(); i+=3) {
            wd.updateBar(i);
            MusicRepository.addMusic(new Music(musics.get(i),musics.get(i+1),musics.get(i+2)));
        }
        wd.dispose();
    }
    //method for emp3world
    // for music "Linkin Park - Numb" link is http://www.emp3world.com/search/Linkin_Park_-_numb_mp3_download.html
    static void forceGetMusic(Music music) {
        try{
            String name = music.getName() + " - " + music.getArtist();
            name=name.replaceAll(" ", "_");
            String url = "http://www.emp3world.com/search/"+name+"_mp3_download.html";
            //this line gets the source code from the link above
            String code = UrlManager.getString(url);
            
            code=code.substring(code.indexOf("id=\"left_box\""));
            code=code.substring(code.indexOf("<a href=\"h")+9);
            code=code.substring(0,code.indexOf("\""));
            System.out.println(code);
            System.out.println("Link added: "+code);
        }catch(StringIndexOutOfBoundsException e){
            System.out.println("Error finding link for: "+music.getName() + " - "+music.getArtist());
        }
    }
}
