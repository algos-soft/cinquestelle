package it.algos.base.libreria.test;
/**
 * Created by IntelliJ IDEA.
 * User: gac
 * Date: 8-lug-2006
 * Time: 8.09.07
 * To change this template use File | Settings | File Templates.
 */

import it.algos.base.libreria.Lib;
import it.algos.base.libreria.LibWeb;
import junit.framework.TestCase;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

public class LibWebTest extends TestCase {

    LibWeb libWeb;


    public void testUpload() throws Exception {
        boolean riuscito;
        String host;
        String utente;
        String password;
        String file;
        String post;
        String Xxx;

        host = "www.quattroprovince.it";
        utente = "quattrop";
        password = "berwy24p";
        file = "/Eventi/oldfeste.html";
        post = "prova testo5";
        Xxx = "test.html";

        Lib.File.sovrascrive(Xxx, post);

        /* connessione */
        riuscito = Lib.Web.upLoadFile(host, utente, password, file, Xxx);

        if (!riuscito) {
            fail("upload");
        }// fine del blocco if

    }


    public void oldissimo() throws Exception {
        boolean riuscito;
        URLConnection connessione;
        URL url;
        String indirizzo;
        OutputStreamWriter outputWriter;
        OutputStream output;
        String post;

        indirizzo = "http://www.quattroprovince.it/pippo";
        indirizzo = "ftp://parcodellerose-ftp:08+parco@ftp.parcodellerose.it/pippo2.txt;type=i";
//URL url = new URL("ftp://username:password@hostname/path/filename");
        indirizzo = "ftp://quattrop:berwy24p@quattroprovince.it/Eventi/oldfeste.html;type=i";
        indirizzo = "ftp://quattrop:berwy24p@www.quattroprovince.it/Eventi/oldfeste.html";


        url = new URL(indirizzo);
        URLConnection urlc = url.openConnection();
        urlc.setAllowUserInteraction(false);
        urlc.setDoOutput(true);
        urlc.connect();

        post = "prova testo3";

        /* connessione */

        /* regola l'uscita */
        output = urlc.getOutputStream();
        outputWriter = new OutputStreamWriter(output);

        /* carica la pagina sul server */
        outputWriter.write(post);
        outputWriter.flush();
        outputWriter.close();

//        riuscito = Lib.Web.upLoad("", "");

//        fail("upload");
    }

//    public void ol() {
//        /* variabili e costanti locali di lavoro */
//        FTPClient ftp = null;
//        boolean error = false;
//        InputStream input;
//        try {
//
//
//            ftp = new FTPClient();
//            int reply;
//            ftp.connect("www.quattroprovince.it");
//            input = ftp.getInputStream();
//            System.out.println("Connected to ftp.parcodellerose.it");
//            System.out.print(ftp.getReplyString());
//
//            // After connection attempt, you should check the reply code to verify
//            // success.
//            reply = ftp.getReplyCode();
//
//            if (!FTPReply.isPositiveCompletion(reply)) {
//                ftp.disconnect();
//                System.err.println("FTP server refused connection.");
//                System.exit(1);
//            }
//            ftp.login("quattrop", "berwy24p");
//            ftp.storeFile("pippo2.text", input);
//            ftp.logout();
//
////            ftp.rename("pippo.txt","pippo2.txt");
////          // transfer files
//////            OutputStream os = ftp.getOutputStream(); // To upload
////            OutputStreamWriter outputWriter = new OutputStreamWriter(os);
////            String post = "prova testo3";
////            outputWriter.write(post);
////            outputWriter.flush();
////            outputWriter.close();
//
//
//        } catch (IOException e) {
//            error = true;
//            e.printStackTrace();
//        } finally {
//            if (ftp.isConnected()) {
//                try {
//                    ftp.disconnect();
//                } catch (IOException ioe) {
//                    // do nothing
//                }
//            }
//            System.exit(error ? 1 : 0);
//        }

    //    }


    public static void xtestPostMail() throws Exception {
        String indirizzo;
        String oggetto;
        String testo;
        String mittente;
        boolean spedita;

        indirizzo = "guido.ceresa@libero.it";
        oggetto = "quinta server";
        testo = "<h2>Questo e' il corpo del messaggio HTML</h2> html <b>grassetto</b> fine";
        mittente = "gac@algos.it";

        spedita = Lib.Web.postMail(indirizzo, oggetto, testo, mittente);


        if (!spedita) {
        }// fine del blocco if

//    fail("test non ancora sviluppato");
    }


    public static void testPostMailAllegato() throws Exception {
        String indirizzo;
        String[] destinatari;
        String oggetto;
        String testo;
        String mittente;
        String allegato;
        boolean spedita;

        indirizzo = "guido.ceresa@libero.it";
        destinatari = new String[1];
        destinatari[0] = indirizzo;
        oggetto = "quinta senza allegato";
        testo = "<h2>Questo e' il corpo del messaggio HTML</h2> html <b>grassetto</b> fine";
        mittente = "gac@algos.it";
        allegato = "/Users/gac/Desktop/Volantino.rtf";
        allegato = "";
        spedita = Lib.Web.postMail(destinatari, oggetto, testo, mittente, allegato);


        if (!spedita) {
        }// fine del blocco if

//    fail("test non ancora sviluppato");
    }

}