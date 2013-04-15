/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

/**
 *
 * @author Pedro
 */
public class SpotifyNameRip {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String aux = UrlManager.getString("https://embed.spotify.com/?uri=spotify:user:c0ldf0x:playlist:0sjcnAY6RFeKF6Ax65upxq");
        String[] nameList = Handler.convertIntoNames(aux);
        FileManager.exportToFile(nameList);
    }
}
