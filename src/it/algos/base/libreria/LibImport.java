/**
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      4-feb-2007
 */
package it.algos.base.libreria;

import it.algos.base.errore.Errore;
import it.algos.base.libreria.csv.CSVReader;
import it.algos.base.messaggio.MessaggioAvviso;

import javax.swing.JFileChooser;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * LibImport. </p> Questa classe astratta: <ul> <li> </li> </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 26-apr-2005 ore 10.42.51
 */
public abstract class LibImport {

    /**
     * tag iniziale di ogni record (formato vCard)
     */
    private static final String INIZIO = "BEGIN:";


    /**
     * Estrae un singolo record.
     * <p/>
     *
     * @param testo del file
     *
     * @return singolo record
     */
    private static LinkedHashMap<String, String> vCardEstraeSingolo(String testo) {
        /* variabili e costanti locali di lavoro */
        LinkedHashMap<String, String> record = null;
        boolean continua;
        String ret = "\n";
        String spazio = " ";
        String tagNick = "\nFN:";
        String nomeNick = "Sigla";  //@todo da sistemare era Utente.Cam.Nic.get()
        String valNick;
        String tagUrl = "EMAIL";
        String tagUrl2 = "pref:";
        String nomeUrl = "Descrizione";
        String valUrl;
        String tagNote = "NOTE:";
        String nomeNote = "Note";
        String valNote;
        int pos;
        int posEnd;

        try { // prova ad eseguire il codice
            continua = Lib.Testo.isValida(testo);

            if (continua) {
                record = new LinkedHashMap<String, String>();
            }// fine del blocco if

            if (continua) {
                pos = testo.indexOf(tagNick);
                if (pos != -1) {
                    pos += tagNick.length();
                    posEnd = testo.indexOf(ret, pos + 1);
                    valNick = testo.substring(pos, posEnd);

                    record.put(nomeNick, valNick);
                }// fine del blocco if
            }// fine del blocco if

            if (continua) {
                pos = testo.indexOf(tagUrl);
                if (pos != -1) {
                    pos = testo.indexOf(tagUrl2, pos);
                    pos += tagUrl2.length();
                    posEnd = testo.indexOf(ret, pos);
                    valUrl = testo.substring(pos, posEnd);

                    record.put(nomeUrl, valUrl);
                }// fine del blocco if
            }// fine del blocco if

            if (continua) {
                pos = testo.indexOf(tagNote);
                if (pos != -1) {
                    pos += tagNote.length();
                    posEnd = testo.indexOf(ret, pos + 1);
                    valNote = testo.substring(pos, posEnd);

                    record.put(nomeNote, valNote);
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return record;
    }


    /**
     * Importa un file in formato vCard.
     * <p/>
     *
     * @param testo del file
     *
     * @return lista dei dati
     */
    private static ArrayList<LinkedHashMap<String, String>> vCardEstrae(String testo) {
        /* variabili e costanti locali di lavoro */
        ArrayList<LinkedHashMap<String, String>> lista = null;
        boolean continua;
        int pos;
        String[] parti = null;

        try { // prova ad eseguire il codice
            continua = Lib.Testo.isValida(testo);

            if (continua) {
                continua = (testo.startsWith(INIZIO));
            }// fine del blocco if

            if (continua) {
                pos = testo.indexOf(INIZIO) + INIZIO.length();
                testo = testo.substring(pos);
            }// fine del blocco if

            if (continua) {
                parti = testo.split(INIZIO);
                continua = (parti.length > 0);
            }// fine del blocco if

            if (continua) {
                lista = new ArrayList<LinkedHashMap<String, String>>();

                /* traverso tutta la collezione */
                for (String stringa : parti) {
                    lista.add(LibImport.vCardEstraeSingolo(stringa));
                } // fine del ciclo for-each

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }


    /**
     * Importa un file in formato vCard.
     * <p/>
     *
     * @param fileUtenti in formato vCard
     *
     * @return lista dei dati
     */
    static ArrayList<LinkedHashMap<String, String>> vCard(File fileUtenti) {
        /* variabili e costanti locali di lavoro */
        ArrayList<LinkedHashMap<String, String>> lista = null;
        String testo;

        try { // prova ad eseguire il codice
            if (fileUtenti != null) {
                testo = Lib.File.legge(fileUtenti.getAbsolutePath());

                if (Lib.Testo.isValida(testo)) {
                    lista = LibImport.vCardEstrae(testo);
                }// fine del blocco if

            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }


    /**
     * Importa un file in formato vCard.
     * <p/>
     *
     * @param titolo del dialogo di selezione del file
     *
     * @return lista dei dati
     */
    static ArrayList<LinkedHashMap<String, String>> vCard(String titolo) {
        /* variabili e costanti locali di lavoro */
        ArrayList<LinkedHashMap<String, String>> lista = null;
        File fileUtenti;

        try { // prova ad eseguire il codice
            fileUtenti = LibFile.selFile(titolo);

            if (fileUtenti != null) {
                lista = LibImport.vCard(fileUtenti);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }


    /**
     * Importa un file in formato vCard.
     * <p/>
     *
     * @return lista dei dati
     */
    static ArrayList<LinkedHashMap<String, String>> vCard() {
        /* invoca il metodo sovrascritto della superclasse */
        return LibImport.vCard("");
    }


    /**
     * Legge il contenuto di un file in formato CSV codificato UTF-8.
     * </p>
     *
     * @param pathFile nome completo del file da leggere
     *
     * @return lista di liste (righe di colonne)
     */
    static List<List<String>> importCSV(String pathFile) {
        return importCSV(pathFile, "UTF-8");
    }

    /**
     * Legge il contenuto di un file in formato CSV. </p>
     *
     * @param pathFile nome completo del file da leggere
     * @param encodingName nome dell'encoding del file da leggere (v. Charset)
     *
     * @return lista di liste (righe di colonne)
     */
    static List<List<String>> importCSV(String pathFile, String encodingName) {
        /* variabili e costanti locali di lavoro */
        List<List<String>> lista = null;
        boolean continua;
        InputStream stream;
        InputStreamReader streamReader;
        CSVReader reader;
        List listaTemp = null;
        String[] riga;
        List<String> listaRiga;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Testo.isValida(pathFile));

            if (continua) {

                stream = new FileInputStream(pathFile);
                streamReader = new InputStreamReader(stream, encodingName);
                reader = new CSVReader(streamReader);
                
                listaTemp = reader.readAll();
                continua = (listaTemp != null && listaTemp.size() > 0);
            }// fine del blocco if

            if (continua) {
                lista = new ArrayList<List<String>>();
                /* traverso tutta la collezione */
                for (Object oggRiga : listaTemp) {
                    if (oggRiga != null) {

                        if (oggRiga instanceof String[]) {
                            riga = (String[])oggRiga;
                            listaRiga = new ArrayList<String>();

                            /* traverso tutta la collezione */
                            for (String colonna : riga) {
                                listaRiga.add(colonna);
                            } // fine del ciclo for-each

                            lista.add(listaRiga);
                        }// fine del blocco if
                    }// fine del blocco if

                } // fine del ciclo for-each

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }// fine del metodo


    /**
     * Legge il contenuto di un file in formato CSV e codificato in UTF-8.
     * </p>
     *
     * @return lista di liste (righe di colonne)
     */
    static List<List<String>> importCSV() {
        /* variabili e costanti locali di lavoro */
        List<List<String>> lista = null;
        boolean continua;
        JFileChooser fc;
        File file;

        try { // prova ad eseguire il codice

            /* selezione del file */
            fc = new JFileChooser();
            fc.showOpenDialog(null);
            file = fc.getSelectedFile();
            continua = (file != null);

            if (continua) {
                lista = importCSV(file.getAbsolutePath());
            }// fine del blocco if

            if (!continua) {
                new MessaggioAvviso("Non ho trovato il file da importare");
            }// fine del blocco if-else

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }// fine del metodo

}// fine della classe
