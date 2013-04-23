package code;

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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Time;
import java.text.DateFormat;
import java.text.NumberFormat;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

/**
 *
 * @author Lucas Bellin
 */
public class DownloadMusicUI extends JDialog
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
    public DownloadMusicUI(String link, String name)
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
            e.printStackTrace();
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
        jlMessage.setText( "Connecting ..." );

        URLConnection conn = new URL( source ).openConnection();

        int contentLength = conn.getContentLength();

        progress.setMinimum( 0 );
        progress.setMaximum( contentLength );
        progress.setValue( 0 );

        int bytesRead = 0;

        BufferedInputStream in = new BufferedInputStream( conn.getInputStream() );
        BufferedOutputStream out = new BufferedOutputStream( new FileOutputStream( target ) );

        byte[] buffer = new byte[4096]; // 4kB

        jlMessage.setText( "Downloading ..." );

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

            long timeElapsed = System.currentTimeMillis() - timeStarted;

            double downloadRate = ( bytesRead / 1000.0 ) / ( timeElapsed / 1000.0 );

            long timeRemaining =  (long) ( 1000 * ( ( contentLength - bytesRead ) / ( downloadRate * 1000 ) ) );

            long timeArrival = timeStarted + timeElapsed + timeRemaining;

            jlMessage.setText( " Downloading: " + fileName +
                    ", Remaining time: " + timeRemaining/1000 + "s" +
                    ", Speed: " + nf.format( downloadRate ) + " kB/s" );
        }

        in.close();
        out.close();

        System.out.println( target.exists() );

        jlMessage.setText( "Download done!" );
        dispose();
        SpotifyNameRip.next();
        }catch(ConnectException e){
            System.out.println("Timed out.");
            dispose();
            SpotifyNameRip.next();
        }catch(FileNotFoundException e){
         System.out.println("File error.");
            dispose();
            SpotifyNameRip.next();
        }
    }

    private void initComponents(String link, String name)
    {
        musicLink=link;
        fileName=name+".mp3";
        setLayout( new GridBagLayout() );
        setDefaultCloseOperation( DISPOSE_ON_CLOSE );
        setSize( 400, 150 );
        setLocationRelativeTo( null );
        jlMessage.setText("Download: "+name);
        buttonsPane.setLayout( new FlowLayout() );
        buttonsPane.add( btStart );
        buttonsPane.add(btClose);
        buttonsPane.add(btExit);
        btStart.setEnabled(false);

        jlMessage.setFont( new Font( "SansSerif" , Font.PLAIN, 10 ) );
        add( jlMessage, new GridBagConstraints( 0, 0, 1, 1, 1.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets( 5, 5, 5, 5 ), 0, 0 ) );
        add( progress, new GridBagConstraints( 0, 1, 1, 1, 1.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets( 5, 5, 5, 5 ), 0, 0 ) );
        add( buttonsPane, new GridBagConstraints( 0, 2, 1, 1, 1.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets( 5, 5, 5, 5 ), 0, 0 ) );
        
        new Runner().start();
        
        btStart.addActionListener( new AbstractAction()
        {
            public void actionPerformed( ActionEvent e )
            {
                btStart.setEnabled(false);
                new Runner().start();
            }
        });
        btClose.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        btExit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                System.exit(0);
            }
        });
    }

    private JLabel jlMessage = new JLabel( "Message:");
    
    private JProgressBar progress = new JProgressBar();
    private JPanel buttonsPane = new JPanel();
    private JButton btStart = new JButton( "Start download" );
    private JButton btClose = new JButton ("Close");
    private JButton btExit = new JButton ("Exit");
}
