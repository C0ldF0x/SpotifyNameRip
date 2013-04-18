/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

import javax.swing.JOptionPane;

/**
 *
 * @author Pedro
 */
public class SpotifyNameRip {

    static int index=0;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //This block edit the embed code to get the link
        String aux=  JOptionPane.showInputDialog(null, "Paste Playlist's Embed Code\n(right-click in Playlist name and \"Copy Embed Code\")", "SNR", JOptionPane.INFORMATION_MESSAGE);
        aux=aux.substring(13);
        aux=aux.substring(0,aux.indexOf("\""));
        aux = UrlManager.getString(aux);
        
        //This line searchs in the playlist's page's source code for the music's info
        Handler.convertIntoNames(aux);
        
        
        //This cycle searchs in the mp3 file provider for a link to download the music
        WaitingDialog wd = new WaitingDialog("Searching for download links",0,MusicRepository.musicRepo.size());
        for(int i=0;i<MusicRepository.musicRepo.size();i++){   
            Handler.getMusicLink(MusicRepository.musicRepo.get(i));
            wd.updateBar(i);
        }
        wd.dispose();
        
        
        WaitingDialog wd2 = new WaitingDialog("Searching for download links (2)",0,MusicRepository.musicRepo.size());
        for(int i=0;i<MusicRepository.musicRepo.size();i++){   
            if("".equals(MusicRepository.musicRepo.get(i).getLink()))
                Handler.forceGetMusic(MusicRepository.musicRepo.get(i));
            wd2.updateBar(i);
        }
        wd2.dispose();

        
        boolean allFound=true;
        for(Music m:MusicRepository.musicRepo)
           if("".equals(m.getLink())){
                System.out.println(m.getName() + " - " + m.getArtist() + ": Download link not found. Try again later." );
                allFound=false;
           }
        if(!allFound){
            int op =JOptionPane.showConfirmDialog(null, "At least one music isn't available for download. Continue?", "Confirm download", JOptionPane.YES_NO_OPTION);
            if(op==0)
                next();//This line starts the download proccess
            else
                System.exit(0);
        }
        
        
    }

    
    public static void next(){
        Music m = MusicRepository.musicRepo.get(index);
        if(!"".equals(m.getLink()) && !m.getLink().contains(" ")){
                System.out.println("Download windows open for: "+m.getName());
                    DownloadMusicUI dm = new DownloadMusicUI(m.getLink(), m.getName() + " - " + m.getArtist());
                    dm.setVisible(true);
                    index++;
            }else{
                System.out.println("Bad link generation for " + m.getName() + " - " + m.getArtist());
                index++;next();
            }
    }
}
