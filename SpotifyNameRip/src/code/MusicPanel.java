package code;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Time;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

/**
 *
 * @author Lucas Bellin
 */
public class MusicPanel extends JPanel
{
    // classe que extende um Thread para executar o download
    // é necessário para que a Progress seja atualizado enquanto o download estiver sendo feito
    public class Runner extends Thread
    {
        public void run()
        {
            try
            {
                startDownload();
            }
            
            catch ( Exception e )
            {
                System.out.println( "ERRO: " + e.toString() );
                UI.next();
            }
            
        }
    }

    // método main que abre a janela com a Progress e um botão para iniciar ela
    public static int StartUI(String link, String name)
    {
        DownloadMusicUI DM = new DownloadMusicUI(link,name);

        return 0;
    }

    // link do arquivo de download
    String musicLink;
    String fileName;

    /**
     * Download
     *
     */
    public MusicPanel(String link, String name)
    {
        initComponents(link,name);
        
    }

    /**
     * startDownload
     *
     */
    private void startDownload()
    {
        try
        {
            progress.setValue( 0 );

            // arquivo local onde o arquivo do download será salvo
            File tmp = new File( fileName );

            downloadFile( musicLink, tmp );

            System.out.println("DONE!");
        }

        catch ( Exception e )
        {
            setBackground(new Color(238,44,44));
            UI.next();
            //e.printStackTrace();
        }
    }

    /**
     * downloadFile
     *
     * @param source String
     * @param target File
     * @throws Exception
     */
    private void downloadFile( String source, File target ) throws Exception
    {
        try{
        //jlMessage.setText( "Connecting ..." );

        URLConnection conn = new URL( source ).openConnection();

        int contentLength = conn.getContentLength();

        progress.setMinimum( 0 );
        progress.setMaximum( contentLength );
        progress.setValue( 0 );

        int bytesRead = 0;

        BufferedInputStream in = new BufferedInputStream( conn.getInputStream() );
        BufferedOutputStream out = new BufferedOutputStream( new FileOutputStream( target ) );

        byte[] buffer = new byte[4096]; // 4kB

        //jlMessage.setText( "Downloading ..." );

        DateFormat tf = DateFormat.getTimeInstance();

        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits( 1 );
        nf.setMaximumFractionDigits( 1 );
        nf.setGroupingUsed( true );

        long timeStarted = System.currentTimeMillis();

        while ( true )
        {
            int n = in.read( buffer );

            if ( n == -1 )
            {
                break;
            }

            out.write( buffer, 0, n );

            bytesRead += n;

            progress.setValue( bytesRead );

        }

        in.close();
        out.close();

        System.out.println( target.exists() );
        setBackground(new Color(50,205,50));
        UI.next();
        }catch(ConnectException e){
            setBackground(new Color(238,44,44));
            UI.next();
        }
    }

    private void initComponents(String link, String name)
    {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MusicPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(MusicPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MusicPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(MusicPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        musicLink=link;
        fileName=name+".mp3";
        setLayout( new GridBagLayout() );
        jlMessage.setText(name.substring(0,name.indexOf("-")));
        jlMessage2.setText(name.substring(name.indexOf("-")+1));

        jlMessage.setFont( new Font( "SansSerif" , Font.BOLD, 14 ) );
        jlMessage2.setFont( new Font( "SansSerif" , Font.PLAIN, 10 ) );
        add( jlMessage, new GridBagConstraints( 0, 0, 1, 1, 1.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets( 5, 5, 5, 5 ), 0, 0 ) );
        add( jlMessage2, new GridBagConstraints( 0, 1, 1, 1, 1.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets( 5, 5, 5, 5 ), 0, 0 ) );
        add( progress, new GridBagConstraints( 0, 2, 1, 1, 1.0, 0.0,  
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,  
                new Insets( 5, 5, 5, 5 ), 0, 0 ) );
        
        

    }
    
    public void startNow(){
        new MusicPanel.Runner().start();
    }

    private JLabel jlMessage = new JLabel( "Message:");
    private JLabel jlMessage2 = new JLabel( "Message:");
    
    private JProgressBar progress = new JProgressBar();
}
