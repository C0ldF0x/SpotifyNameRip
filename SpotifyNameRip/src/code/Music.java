/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

/**
 *
 * @author Pedro
 */
public class Music {
    private String name;
    private String artist;
    private String duration;
    private String downloadLink;
    public boolean downloaded;
    
    public Music(String d, String n, String a){
        name=n;
        artist=a;
        duration=d;
        downloaded=false;
        downloadLink="";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
    
    public void setLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }
    
    public String getLink() {
        return downloadLink;
    }
    
    public String out(){
        return (this.artist + " - " + this.name + "\tDownload link: "+this.downloadLink);
    }
    
    
}
