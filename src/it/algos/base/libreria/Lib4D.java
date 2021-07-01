/**
 * Title:     Lib4D
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      23-giu-2007
 */
package it.algos.base.libreria;

import it.algos.base.errore.Errore;
import it.algos.base.messaggio.MessaggioAvviso;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;

import org.apache.commons.codec.binary.Base64;

/**
 * Librerie per la comunicazione con 4D.
 * <p/>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 23-giu-2007 ore 16.04.04
 */
public abstract class Lib4D {

    /**
     * Interroga un metodo di 4D passandogli dei parametri e recupera la risposta.
     * <p/>
     *
     * @param indirizzo del web server di 4d
     * @param porta del web server di 4d
     * @param user il nome utente
     * @param password la password
     * @param metodo nome del metodo
     * @param parametri da passare al metodo, ogni parametro nella forma "nome=valore"
     *
     * @return la stringa di risposta
     */
    static String query4D(String indirizzo,
                          int porta,
                          String user,
                          String password,
                          String metodo,
                          String... parametri) {
        /* variabili e costanti locali di lavoro */
        String risposta = "";
        URL url;
        URLConnection conn = null;

        try {    // prova ad eseguire il codice

            /* recupera l'URL del metodo */
            url = getUrlMetodo(indirizzo, porta, metodo, parametri);

            /* crea e apre la connessione */
            conn = creaConnessione(url, user, password);

            /* legge la risposta */
            if (conn != null) {
                risposta = getRisposta(conn);
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore, unErrore.getMessage());
        } // fine del blocco try-catch

        /* valore di ritorno */
        return risposta;
    }


    /**
     * Ritorna l'URL di un metodo sul server 4d.
     * <p/>
     *
     * @param indirizzo del web server di 4d
     * @param porta del web server di 4d
     * @param metodo nome del metodo
     * @param parametri da passare al metodo, ogni parametro nella forma "nome=valore"
     *
     * @return l'URL del metodo comprensivo dei parametri
     */
    private static URL getUrlMetodo(String indirizzo,
                                    int porta,
                                    String metodo,
                                    String... parametri) {
        /* variabili e costanti locali di lavoro */
        URL url = null;
        String stringaBase;
        String stringaPar;
        String stringa;
        String parametro;
        String parName;
        String parValue;
        String[] parti;

        try {    // prova ad eseguire il codice

            stringaBase = getUrl4DActions(indirizzo, porta);
            stringaBase += "/";
            stringaBase += metodo;

            /* crea la stringa relativa ai parametri */
            stringaPar = "";
            if (parametri.length > 0) {
                stringaPar += "?";
                for (int k = 0; k < parametri.length; k++) {

                    /* aggiunge il separatore tra i parametri */
                    if (k > 0) {
                        stringaPar += "&";
                    }// fine del blocco if

                    /* recupera il parametro */
                    parametro = parametri[k];

                    /* divide il parametro nellle parti nome - valore */
                    parti = parametro.split("=");
                    parName = parti[0];
                    parValue = "";
                    if (parti.length > 1) {
                        parValue = parti[1];
                    }// fine del blocco if

                    /* codifica il valore come ISO-8859-1 */
                    parValue = URLEncoder.encode(parValue, "ISO-8859-1");

                    /* riassembla il parametro */
                    stringaPar += parName;
                    stringaPar += "=";
                    stringaPar += parValue;

                } // fine del ciclo for
            }// fine del blocco if

            stringa = stringaBase + stringaPar;
            url = new URL(stringa);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return url;
    }


    /**
     * Crea una connessione HTTP con il server 4D.
     * <p/>
     *
     * @param url l'URL per la connessione
     * @param user il nome utente
     * @param password la password
     *
     * @return la connessione creata e aperta, null se non riuscito
     */
    private static HttpURLConnection creaConnessione(URL url, String user, String password) {
        /* variabili e costanti locali di lavoro */
        HttpURLConnection conn = null;
        URLConnection uConn;
        String stringa;
        String messaggio;

        try {    // prova ad eseguire il codice

            /* crea e registra la connessione */
            uConn = url.openConnection();
            if (uConn instanceof HttpURLConnection) {
                conn = (HttpURLConnection)uConn;
            }// fine del blocco if

            /* regola le proprietà della connessione */
            stringa = "Basic " + encode(user + ":" + password);
            conn.setRequestProperty("Authorization", stringa);

            conn.connect();

        } catch (ConnectException unErrore) {    // intercetta l'errore
            messaggio = "Impossibile stabilire una connessione con il server 4D.";
            messaggio += "\n";
            messaggio += "Motivo: " + unErrore.getMessage();
            messaggio += "\n";
            messaggio += "- Controllare che il server 4D sia attivo";
            messaggio += "\n";
            messaggio += "- Controllare che il web server 4D sia avviato";
            messaggio += "\n";
            messaggio += "- Controllare i parametri di connessione del programma";

            new MessaggioAvviso(messaggio);
            conn = null;

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return conn;
    }


    /**
     * Ritorna la risposta.
     * <p/>
     *
     * @param conn la connessione
     *
     * @return la risposta
     */
    private static String getRisposta(URLConnection conn) {
        /* variabili e costanti locali di lavoro */
        String risposta = "";
        InputStream input;
        InputStreamReader inputReader;
        BufferedReader buffer;
        StringBuffer testoBuff;
        String stringa;
        String messaggio;

        try {    // prova ad eseguire il codice

            /* legge la risposta */
            input = conn.getInputStream();
            inputReader = new InputStreamReader(input, "ISO-8859-1");
            buffer = new BufferedReader(inputReader);
            testoBuff = new StringBuffer();
            while ((stringa = buffer.readLine()) != null) {
                testoBuff.append(stringa);
                testoBuff.append("\n");
            }
            buffer.close();
            inputReader.close();
            input.close();
            risposta = testoBuff.toString();

        } catch (IOException unErrore) {    // intercetta l'errore
            messaggio = "Connessione con il server 4D rifiutata.";
            messaggio += "\n";
            messaggio += "Motivo: " + unErrore.getMessage();
            messaggio += "\n";
            messaggio += "- Controllare i parametri di connessione del programma";

            new MessaggioAvviso(messaggio);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return risposta;
    }


    /**
     * Ritorna l'URL delle 4DActions.
     * <p/>
     *
     * @param indirizzo del web server di 4d
     * @param porta del web server di 4d
     *
     * @return l'URL delle 4DActions
     */
    private static String getUrl4DActions(String indirizzo, int porta) {
        /* variabili e costanti locali di lavoro */
        String url = null;

        try {    // prova ad eseguire il codice

            url = "http://";
            url += indirizzo;
            url += ":";
            url += porta;
            url += "/4daction";

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return url;
    }


    private static String encode(String source) {
    	byte[] b = source.getBytes();
    	String out = Base64.encodeBase64String(b);
    	return out;
    }


}// fine della classe
