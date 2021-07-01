/**
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      26-apr-2005
 */
package it.algos.base.libreria;

import it.algos.base.dialogo.DialogoFactory;
import it.algos.base.errore.Errore;
import it.algos.base.property.Property;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;

/**
 * LibFile. </p> Questa classe astratta: <ul> <li> </li> </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 26-apr-2005 ore 10.42.51
 */
public abstract class LibFile {

    /**
     * Sovrascrive un file su disco locale.
     * <p/>
     * Se il file esiste, lo cancella <br>
     *
     * @param nome del file
     * @param contenuto del file
     *
     * @return vero se il file è stato scritto
     */
    static boolean sovrascrive(String nome, String contenuto) {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = false;
        File file;
        FileWriter uscita = null;
        int pos;
        String nomeCart;
        File cartella;

        /* seleziona il file */
        file = new File(nome);

        try { // prova ad eseguire il codice
            try { // prova ad eseguire il codice
                uscita = new FileWriter(file);
            } catch (Exception unErrore) { // intercetta l'errore
                pos = nome.lastIndexOf("/");
                nomeCart = nome.substring(0, pos);
                cartella = new File(nomeCart);
                cartella.mkdir();
                try { // prova ad eseguire il codice
                    uscita = new FileWriter(file);
                } catch (Exception unErrore2) { // intercetta l'errore
                    new Errore(unErrore2);
                }// fine del blocco try-catch

            }// fine del blocco try-catch

            if (uscita != null) {
                uscita.write(contenuto);
                uscita.flush();
                uscita.close();
                riuscito = true;
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Scrive un file su disco locale.
     * <p/>
     * Se il file esiste, chiede conferma prima di sovrascriverlo <br>
     *
     * @param path completo del file
     * @param contenuto del file
     *
     * @return vero se il file è stato scritto
     */
    static boolean scrive(String path, String contenuto) {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = false;
        String titolo = "File esistente";
        String nome;
        String messIni = "Il file ";
        String messEnd = " esiste già. Vuoi sovrascriverlo?";
        String messaggio;
        File file;
        String tagPunto = ".";
        String tagSlasch = ".";

        try { // prova ad eseguire il codice
            /* seleziona il file */
            file = new File(path);

            /* costruisce un messaggio di conferma col solo nome significativo del file */
            nome = path;
            if (nome.contains(tagPunto)) {
                nome = nome.substring(0, nome.lastIndexOf(tagPunto));
            }// fine del blocco if

            if (nome.contains(tagSlasch)) {
                nome = nome.substring(0, nome.lastIndexOf(tagSlasch + 1));
            }// fine del blocco if

            messaggio = messIni + nome + messEnd;

            try { // prova ad eseguire il codice

                if (LibFile.isEsisteFile(file)) {
                    if (DialogoFactory.getConferma(titolo, messaggio)) {
                        riuscito = LibFile.sovrascrive(path, contenuto);
                    }// fine del blocco if
                } else {
                    riuscito = LibFile.sovrascrive(path, contenuto);
                }// fine del blocco if-else

            } catch (Exception unErrore) { // intercetta l'errore
                riuscito = LibFile.sovrascrive(path, contenuto);
            }// fine del blocco try-catch

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Legge un file dal disco locale.
     * <p/>
     *
     * @param nomeFile nome completo del path del file
     *
     * @return testo del file
     */
    static String legge(String nomeFile) {
        /* variabili e costanti locali di lavoro */
        String riga;
        FileReader entrata;
        BufferedReader buffer;
        StringBuffer stringa;
        String testo = "";
        String aCapo = "\n";

        try {        // prova ad eseguire il codice
            /* apre il file */
            entrata = new FileReader(nomeFile);
            buffer = new BufferedReader(entrata);

            stringa = new StringBuffer();

            while ((riga = buffer.readLine()) != null) {
                stringa.append(riga);
                stringa.append(aCapo);
            }// fine del blocco while

            /* chiude il file */
            entrata.close();
            buffer.close();

            testo = stringa.toString();

        } catch (FileNotFoundException unErrore) {
        } catch (Exception unErrore) {
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return testo;
    }


    /**
     * Cancella un file dal disco locale.
     * <p/>
     *
     * @param nomeFile nome completo del path del file
     *
     * @return vero se il file è stato cancellato
     */
    static boolean cancella(String nomeFile) {
        /* variabili e costanti locali di lavoro */
        boolean cancellato = false;
        File file;

        try {        // prova ad eseguire il codice
            /* crea un oggetto file dal nome */
            file = new File(nomeFile);

            cancellato = file.delete();

        } catch (Exception unErrore) {
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return cancellato;
    }


    /**
     * Cancella il contenuto di una directory.
     * <p/>
     *
     * @param nomeDir completo del path della cartella
     *
     * @return vero se il contenuto è stato cancellato
     */
    static boolean cancellaContenutoDir(String nomeDir) {
        /* variabili e costanti locali di lavoro */
        boolean cancellato = false;
        boolean continua;
        File cartella;
        File[] files = null;

        try {        // prova ad eseguire il codice
            /* crea un oggetto cartella dal nome */
            cartella = new File(nomeDir);
            continua = cartella.isDirectory();

            if (continua) {
                files = cartella.listFiles();
                continua = files != null && files.length > 0;
            }// fine del blocco if

            if (continua) {
                cancellato = true;
                for (File file : files) {
                    if (!cancella(file.getAbsolutePath())) {
                        cancellato = false;
                        break;
                    }// fine del blocco if
                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) {
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return cancellato;
    }


    /**
     * Determina se esiste un dato file.
     * <p/>
     *
     * @param path il percorso assoluto del file da cercare
     *
     * @return true se esiste
     */
    static boolean isEsisteFile(String path) {
        return isEsisteFile(new File(path));
    }


    /**
     * Lista dei files contenuti in una cartella.
     * <p/>
     *
     * @param pathDir path completo della cartella
     *
     * @return lista files
     */
    static ArrayList<File> getFiles(String pathDir) {
        /* variabili e costanti locali di lavoro */
        ArrayList<File> lista = null;
        boolean continua;
        File cartella = null;
        File[] files = null;

        try {        // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(pathDir));

            /* crea un oggetto cartella dal nome */
            if (continua) {
                cartella = new File(pathDir);
                continua = cartella.isDirectory();
            }// fine del blocco if

            if (continua) {
                files = cartella.listFiles();
                continua = files != null && files.length > 0;
            }// fine del blocco if

            if (continua) {
                lista = new ArrayList<File>();
                for (File file : files) {
                    lista.add(file);
                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) {
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }


    /**
     * Lista nomi dei files contenuti in una cartella.
     * <p/>
     *
     * @param pathDir path completo della cartella
     *
     * @return lista nomi files
     */
    static ArrayList<String> getNomiFiles(String pathDir) {
        /* variabili e costanti locali di lavoro */
        ArrayList<String> lista = null;
        boolean continua;
        ArrayList<File> listaFiles = null;
        String nome;

        try {        // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(pathDir));

            /* crea un oggetto cartella dal nome */
            if (continua) {
                listaFiles = getFiles(pathDir);
                continua = (listaFiles != null && listaFiles.size() > 0);
            }// fine del blocco if

            if (continua) {
                lista = new ArrayList<String>();
                for (File file : listaFiles) {
                    nome = file.getName();
                    lista.add(nome);
                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) {
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }


    /**
     * Ultima data di modifica del file.
     * <p/>
     *
     * @param path il percorso assoluto del file
     *
     * @return data ultima modifica
     */
    static Date getData(String path) {
        /* variabili e costanti locali di lavoro */
        Date data = null;
        boolean continua;
        File file;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(path));

            if (continua) {
                file = new File(path);
                data = new Date(file.lastModified());
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return data;
    }


    /**
     * Ritorna l'estensione di un file.
     * <p/>
     * (i caratteri dopo l'ultimo punto, punto escluso)
     *
     * @param path il percorso completo del file
     * @return l'estensione del file
     */
    static String getEstensione(String path) {
        /* variabili e costanti locali di lavoro */
        String estensione="";
        int posPunto;

        try { // prova ad eseguire il codice
            posPunto = path.lastIndexOf('.');
            if (posPunto>0) {
                estensione = path.substring(posPunto+1);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return estensione;
    }


    /**
     * Rimuove l'estensione da un nome di file.
     * <p/>
     * (i caratteri dopo l'ultimo punto, punto incluso)
     *
     * @param path il percorso completo del file
     * @return il nome del file con l'estensione rimossa
     */
    public static String stripEstensione(String path) {
        /* variabili e costanti locali di lavoro */
        String stringa=path;
        int posPunto;

        try { // prova ad eseguire il codice
            posPunto = path.lastIndexOf('.');
            if (posPunto>0) {
                stringa = path.substring(0,posPunto);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return stringa;
    }




    //************
    /**
     * Apre un collegamento in lettura col file
     *
     * @param file file da leggere
     *
     * @return un FileReader aperto sul file
     */
    public static FileReader apreFileLettura(File file) {
        /* variabili e costanti locali di lavoro */
        FileReader reader = null;

        try { // prova ad eseguire il codice
            if (file != null) {
                reader = new FileReader(file);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return reader;
    }


    /**
     * Apre un buffer in lettura col file
     *
     * @param file file da leggere
     *
     * @return un BufferedReader aperto sul file
     */
    public static BufferedReader apreBufferLettura(File file) {
        /* variabili e costanti locali di lavoro */
        BufferedReader buffer = null;
        FileReader reader;

        try { // prova ad eseguire il codice
            reader = LibFile.apreFileLettura(file);
            buffer = new BufferedReader(reader);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return buffer;
    }


    /**
     * Legge una riga.
     * <p/>
     *
     * @param buffer da leggere
     *
     * @return stringa della riga
     */
    public static String leggeRiga(BufferedReader buffer) {
        /* variabili e costanti locali di lavoro */
        String riga = "";

        try {        // prova ad eseguire il codice
            riga = buffer.readLine();
        } catch (Exception unErrore) {
            //non fa nulla
        } // fine del blocco try-catch

        /* valore di ritorno */
        return riga;
    }


    /**
     * Legge una riga.
     * <p/>
     * Restituisce un array dei valori separati da tab <br>
     *
     * @param buffer da leggere
     *
     * @return array dei valori
     */
    public static ArrayList<String> leggeArrayRiga(BufferedReader buffer) {
        /* variabili e costanti locali di lavoro */
        ArrayList<String> lista = null;
        String riga;
        String tab = "\t";
        String[] split;

        try {        // prova ad eseguire il codice
            riga = LibFile.leggeRiga(buffer);

            if (riga != null) {
                lista = new ArrayList<String>();

                if (Lib.Testo.isValida(riga)) {
                    split = riga.split(tab);

                    for (String valore : split) {
                        lista.add(valore);
                    } // fine del ciclo for-each
                }// fine del blocco if
            }// fine del blocco if


        } catch (Exception unErrore) {
            //non fa nulla
        } // fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }


    /**
     * Apre un collegamento in lettura col file
     *
     * @param file file da leggere
     *
     * @return un FileReader aperto sul file
     */
    public static FileInputStream apreStreamLettura(File file) {
        /* variabili e costanti locali di lavoro */
        FileInputStream stream = null;

        try { // prova ad eseguire il codice
            if (file != null) {
                stream = new FileInputStream(file);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return stream;
    }


    /**
     * Carica in memoria tutto il contenuto di un file.
     * <p/>
     *
     * @param reader collegamento in lettura
     *
     * @return una stringa con il contenuto del file
     */
    public static String leggeFile(FileReader reader) {
        /* variabili e costanti locali di lavoro */
        String testo = "";
        char[] caratteri;

        /* variabile locale di lunghezza */
        int lun = 0;
        int c;

        /* variabile locale per l'array di bytes */
        byte[] unSet;


        try { // prova ad eseguire il codice

            lun = 100;
            caratteri = new char[lun];

            while ((c = reader.read(caratteri, 0, lun)) != -1) {
                int a = c;
            }// fine del blocco while

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        try {
            unSet = new byte[lun];// prepara un array di bytes
            testo = new String(unSet);//trasporta da array nella stringa
        } catch (Exception unErrore) {   // intercetta l'errore
            JOptionPane.showMessageDialog(null, "Non sono riuscito a caricare il file");
        } // try-catch-finally

        /* valore di ritorno */
        return testo;
    }


    /**
     * Carica in memoria tutto il contenuto di un file.
     * <p/>
     *
     * @param reader collegamento in lettura
     *
     * @return una stringa con il contenuto del file
     */
    public static String leggeFile(FileInputStream reader) {
        /* variabili e costanti locali di lavoro */
        String testo = "";
        String carattere;
        String testo2 = "";
        byte[] unSet;
        int lun;
        try { // prova ad eseguire il codice
            lun = reader.available();// quanti caratteri ci sono
            unSet = new byte[lun];// prepara un array di bytes
            reader.read(unSet);
            testo = new String(unSet);//trasporta da array nella stringa

            // todo a questo punto devo fare questa porcata
            // todo che probabilmente non funzionera' mai su altri sistemi
            // todo perche' il reader legge i due byte unicode per ogni carattere
            // todo e la funzione String(byte[]�bytes) che dovrebbe decodificare
            // todo l'array di bytes non decodifica un cazzo
            // todo guido aiuto!
            // todo alex 29-04-05
            for (int k = 0; k < testo.length(); k++) {
                carattere = testo.substring(k, k + 1);
                if (carattere.equals("\u0000") == false) {
                    testo2 += carattere;
                }// fine del blocco if
            } // fine del ciclo for
            testo = testo2;

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return testo;
    }


    /**
     * Apre un file di testo e carica il contenuto.
     *
     * @param file il file da leggere
     *
     * @return unTesto stringa di testo letta dal file
     */
    public static String leggeFile(File file) {
        /* variabile locale di lavoro */
        String unTesto = "";
        FileInputStream stream;

        try { // prova ad eseguire il codice

            /* controlla il risultato */
            if (file.isFile()) {
                /* procede */
                stream = new FileInputStream(file);
                unTesto = leggeFile(stream);
                stream.close();
            } /* fine del blocco if */
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno del testo */
        return unTesto;
    } /* fine del metodo */


    /**
     * Apre un file di testo e carica il contenuto.
     *
     * @param nomeFile nome completo del file (col path ed il suffisso)
     *
     * @return unTesto stringa di testo letta dal file
     */
    public static String leggeFile(String nomeFile) {
        return leggeFile(new File(nomeFile));
    } /* fine del metodo */


    /**
     * Apre un file e restituisce un array di righe.
     *
     * @param file il file da leggere
     *
     * @return array dei contenuto di ogni riga
     */
    public static String[] leggeRighe(File file) {
        /* variabile locale di lavoro */
        String[] righe = null;
        String testo = null;
        String acapo = "";

        try { // prova ad eseguire il codice
            acapo = "\n";
            testo = leggeFile(file);
            righe = testo.split(acapo);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno del testo */
        return righe;
    } /* fine del metodo */


    /**
     * Apre un file e restituisce un array di righe.
     *
     * @param nomeFile nome completo del file (col path ed il suffisso)
     *
     * @return array dei contenuto di ogni riga
     */
    public static String[] leggeRighe(String nomeFile) {
        return leggeRighe(new File(nomeFile));
    } /* fine del metodo */


    /**
     * Apre un file di properties e restituisce una mappa.
     * <p/>
     * Le righe che iniziano con asterisco sono commenti alla property e vengono restituite nel
     * campo commento.
     *
     * @param file il file da leggere
     *
     * @return mappa ordinata di oggetti Property nella sequenza di apparizione nel file chiave - la
     *         chiave della property valore - l'oggetto Property
     */
    public static LinkedHashMap leggeProperties(File file) {
        /* variabile locale di lavoro */
        LinkedHashMap unaMappa = null;
        String[] righe;
        String riga;
        String sep;
        String[] parti;
        String parte1 = "";
        String parte2 = "";
        String testoCom = "";
        boolean fineBlocco = false;
        Property prop;

        try { // prova ad eseguire il codice
            sep = "=";

            unaMappa = new LinkedHashMap();

            righe = leggeRighe(file);
            for (int k = 0; k < righe.length; k++) {

                riga = righe[k];
                riga = riga.trim();

                if (riga.length() > 0) {

                    if (isCommento(riga)) { // riga di commento

                        /* leva il carattere di commento */
                        riga = riga.replaceFirst("\\*", "");
                        /* trimma la riga */
                        riga = riga.trim();
                        /* aggiunge newline all'inizio */
                        riga = "\n" + riga;
                        /* aggiunge la riga al testo di commento */
                        testoCom += riga;

                    } else {  // riga di valori
                        parte1 = "";
                        parte2 = "";
                        parti = riga.split(sep);
                        if (parti.length > 0) {
                            parte1 = parti[0];
                        }// fine del blocco if
                        if (parti.length > 1) {
                            parte2 = parti[1];
                        }// fine del blocco if
                        parte1 = parte1.trim();
                        parte2 = parte2.trim();
                        if (Lib.Testo.isValida(parte1)) {
                            fineBlocco = true;
                        }// fine del blocco if

                    }// fine del blocco if-else

                    /* se e' arrivato alla fine del blocco commento-propery
                     * crea la property e la aggiunge alla collezione */
                    if (fineBlocco) {

                        /* leva il primo newline dal commento e lo trimma */
                        testoCom = testoCom.replaceFirst("\n", "");

                        prop = new Property();
                        prop.setChiave(parte1);
                        prop.setValore(parte2);
                        prop.setCommento(testoCom);
                        unaMappa.put(prop.getChiave(), prop);
                        fineBlocco = false;
                        testoCom = "";
                    }// fine del blocco if

                }// fine del blocco if

            } // fine del ciclo for

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno del testo */
        return unaMappa;
    } /* fine del metodo */


    /**
     * Determina se una riga e' una riga di commento.
     * <p/>
     * Una riga e' di commento se inizia con il carattere asterisco.
     *
     * @param riga la riga da controllare
     *
     * @return true se e' una riga di commento
     */
    private static boolean isCommento(String riga) {
        /* variabili e costanti locali di lavoro */
        boolean isCommento = false;
        String commento = "*";

        try {    // prova ad eseguire il codice
            if (Lib.Testo.isValida(riga)) {
                if (riga.substring(0, 1).equals(commento)) {
                    isCommento = true;
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return isCommento;
    }


    /**
     * Aggiunge una property in coda a un file di properties.
     * <p/>
     *
     * @param file il file nel quale scrivere
     * @param property la property da aggiungere le righe di commento devono essere separate da
     * newline
     *
     * @return true se riuscito
     */
    public static boolean addProperty(File file, Property property) {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = false;
        RandomAccessFile out = null;
        String testo = "";
        String chiave;
        String valore;
        String commento;
        String testoChiave = "";
        String testoValore = "";
        String testoCommento = "";
        String bloccoCommento = "";
        String[] righeCommento;
        String sep = " = ";
        String com = "* ";
        boolean continua = true;

        try { // prova ad eseguire il codice

            /* recupera i dati dalla property */
            chiave = property.getChiave();
            valore = property.getValore();
            commento = property.getCommento();

            /* controlla che la chiave sia valida */
            if (continua) {
                if (!Lib.Testo.isValida(chiave)) {
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            /* crea un random writer sul file */
            if (continua) {
                out = new RandomAccessFile(file, "rw");
                if (out == null) {
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            /* regola la chiave e il valore e il commento */
            if (continua) {
                testoChiave = chiave;
                if (Lib.Testo.isValida(valore)) {
                    testoValore = valore;
                }// fine del blocco if
                if (Lib.Testo.isValida(commento)) {
                    testoCommento = commento;
                }// fine del blocco if
            }// fine del blocco if

            /* costruisce il testo da scrivere */
            if (continua) {

                /* costruisce il commento */
                if (Lib.Testo.isValida(testoCommento)) {
                    righeCommento = testoCommento.split("\n");
                    for (int k = 0; k < righeCommento.length; k++) {
                        if (k > 0) {
                            bloccoCommento += "\r\n";
                        }// fine del blocco if
                        bloccoCommento += com + righeCommento[k];
                    } // fine del ciclo for
                }// fine del blocco if

                /* costruisce il testo completo */
                testo = "";
                testo += bloccoCommento;
                if (Lib.Testo.isValida(bloccoCommento)) {
                    testo += "\r\n";
                }// fine del blocco if
                testo += testoChiave;
                testo += sep;
                testo += testoValore;
            }// fine del blocco if

            /* aggiunge due acapo se non e' la prima entry del file */
            if (continua) {
                if (out.length() != 0) {
                    testo = "\r\n\r\n" + testo;
                }// fine del blocco if
            }// fine del blocco if

            /* scrive il testo in coda al file */
            if (continua) {
                /* posiziona il puntatore alla fine del file */
                out.seek(out.length());
                /* scrive il testo */
                out.writeBytes(testo);
                riuscito = true;
            }// fine del blocco if

            /* chiude il writer */
            if (out != null) {
                out.close();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Crea un file vuoto.
     * <p/>
     * Viene creato solo se non esiste gia' <br> Viene creata tutta la gerarchia di directory se non
     * esistono.
     *
     * @param file il file da creare
     *
     * @return true se riuscito
     */
    public static boolean creaFile(File file) {
        /* variabile locale di lavoro */
        boolean riuscito = false;
        String dirName;
        FileWriter writer;
        boolean continua = true;

        try { // prova ad eseguire il codice

            /* recupera il nome della directory */
            dirName = file.getParent();

            /* crea la directory se non esiste */
            if (Lib.Testo.isValida(dirName)) {
                if (!isEsisteDir(dirName)) {
                    if (!creaDir(new File(dirName))) {
                        continua = false;
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

            /* crea il file se non esiste
             * o se esiste ma non e' un file */
            if (continua) {
                if (!isEsisteFile(file)) {
                    writer = new FileWriter(file);
                    writer.close();
                }// fine del blocco if
                riuscito = true;
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;

    } /* fine del metodo */


    /**
     * Seleziona una directory esistente o ne crea una nuova.
     * <p/>
     *
     * @return la directory selezionata o creata, null se annullato
     */
    public static File creaDir() {
        return creaDir("");
    }


    /**
     * Seleziona una directory esistente o ne crea una nuova.
     * <p/>
     *
     * @param titolo del dialogo di selezione
     *
     * @return la directory selezionata o creata, null se annullato
     */
    public static File creaDir(String titolo) {
        return getFileBase(OperazioneFile.crea, TipoFile.soloDirs, titolo);
    }


    /**
     * Crea una directory.
     * <p/>
     * Viene creata solo se non esiste già <br> Crea tutta la gerarchia di directory specificata.
     *
     * @param dir la directory da creare
     *
     * @return true se riuscito
     */
    public static boolean creaDir(File dir) {
        /* variabile locale di lavoro */
        boolean riuscito = false;

        try { // prova ad eseguire il codice
            if (!isEsisteDir(dir)) {
                dir.mkdirs();
                riuscito = true;
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;

    } /* fine del metodo */


    /**
     * Determina se esiste un dato file.
     * <p/>
     *
     * @param file il File da cercare
     *
     * @return true se esiste
     */
    public static boolean isEsisteFile(File file) {
        /* variabili e costanti locali di lavoro */
        boolean esiste = false;

        try {    // prova ad eseguire il codice
            if (file.exists()) {
                esiste = true;
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return esiste;
    }


    /**
     * Determina se esiste una directory.
     * <p/>
     *
     * @param dir la directory da cercare
     *
     * @return true se esiste
     */
    public static boolean isEsisteDir(File dir) {
        /* variabili e costanti locali di lavoro */
        boolean esiste = false;

        try {    // prova ad eseguire il codice
            if (dir.exists()) {
                if (dir.isDirectory()) {
                    esiste = true;
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return esiste;
    }


    /**
     * Determina se esiste una directory.
     * <p/>
     *
     * @param path la directory da cercare
     *
     * @return true se esiste
     */
    public static boolean isEsisteDir(String path) {
        return isEsisteDir(new File(path));
    }


    /**
     * Svuota un file esistente.
     * <p/>
     *
     * @param file il file da svuotare
     *
     * @return true se riuscito
     */
    public static boolean svuotaFile(File file) {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = false;

        try {    // prova ad eseguire il codice
            if (isEsisteFile(file)) {
                file.delete();
                riuscito = creaFile(file);
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Seleziona o crea un file o una directory.
     * <p/>
     *
     * @param opzione per aprire/registrare
     * @param tipo di files (files, dirs, files_e_dirs)
     * @param titolo del dialogo di selezione
     *
     * @return il file/directory selezionato o creato, null se annullato
     */
    private static File getFileBase(OperazioneFile opzione, TipoFile tipo, String titolo) {
        /* variabili e costanti locali di lavoro */
        JFrame fin;
        JFileChooser dialogo;
        File file = null;
        int risposta = 0;
        String stringa;

        try {        // prova ad eseguire il codice
            fin = new JFrame();
            dialogo = new JFileChooser();
            dialogo.setFileSelectionMode(tipo.getTipoChooser());
            if (Lib.Testo.isValida(titolo)) {
                dialogo.setDialogTitle(titolo);
            }// fine del blocco if

            /* selettore della variabile */
            switch (opzione) {
                case crea:
                    dialogo.setApproveButtonText("Crea");
                    risposta = dialogo.showSaveDialog(fin);
                    break;
                case selezione:
                    dialogo.setApproveButtonText("Seleziona");
                    risposta = dialogo.showOpenDialog(fin);
                    break;
                default: // caso non definito
                    break;
            } // fine del blocco switch

            if (risposta == JFileChooser.APPROVE_OPTION) {
                file = dialogo.getSelectedFile();
                stringa = file.getPath();
                file = new File(stringa);
            }

        } catch (Exception unErrore) {
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return file;
    }


    /**
     * Crea un nuovo file.
     * <p/>
     *
     * @return il file selezionato o creato, null se annullato
     */
    public static File creaFile() {
        return creaFile("");
    }


    /**
     * Crea un nuovo file.
     * <p/>
     *
     * @param titolo del dialogo
     *
     * @return il file selezionato o creato, null se annullato
     */
    public static File creaFile(String titolo) {
        return getFileBase(OperazioneFile.crea, TipoFile.soloFiles, titolo);
    }


    /**
     * Seleziona un file esistente o ne crea uno nuovo
     * <p/>
     * Controlla che esista il nome selezionato nel dialogo <br>
     * Questo perché il dialogo è editabile <br>
     *
     * @return nullo se non esiste
     */
    public static File getFile() {
        return getFile("");
    }


    /**
     * Seleziona un file esistente o ne crea uno nuovo.
     * <p/>
     * Controlla che esista il nome selezionato nel dialogo <br>
     * Questo perché il dialogo è editabile <br>
     *
     * @param titolo del dialogo
     *
     * @return nullo se non esiste
     */
    public static File getFile(String titolo) {
        /* variabili e costanti locali di lavoro */
        File file = null;
        File unFile;

        try { // prova ad eseguire il codice
            unFile = getFileBase(OperazioneFile.selezione, TipoFile.soloFiles, titolo);

            if (unFile != null) {
                if (isEsisteFile(unFile)) {
                    file = unFile;
                }// fine del blocco if
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return file;
    }


    /**
     * Seleziona un file esistente.
     * <p/>
     * Controlla che esista il nome selezionato nel dialogo <br> Questo perché il dialogo è
     * editabile <br>
     *
     * @param titolo del dialogo
     *
     * @return nullo se non esiste
     */
    public static File selFile(String titolo) {
        return getFileBase(OperazioneFile.selezione, TipoFile.soloFiles, titolo);
    }


    /**
     * Seleziona una directory esistente o ne crea una nuova
     * <p/>
     * Controlla che esista il nome selezionato nel dialogo <br> Questo perché il dialogo è
     * editabile <br>
     *
     * @return nullo se non esiste
     */
    public static File getDir() {
        return getDir("");
    }


    /**
     * Seleziona una directory esistente o ne crea una nuova
     * <p/>
     * Controlla che esista il nome selezionato nel dialogo <br>
     * Questo perché il dialogo è editabile <br>
     *
     * @param titolo del dialogo
     *
     * @return nullo se non esiste
     */
    public static File getDir(String titolo) {
        /* variabili e costanti locali di lavoro */
        File dir = null;
        File unaDir;

        try { // prova ad eseguire il codice
            unaDir = getFileBase(OperazioneFile.selezione, TipoFile.soloDirs, titolo);

            if (unaDir != null) {
                if (isEsisteFile(unaDir)) {
                    dir = unaDir;
                }// fine del blocco if
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return dir;
    }


    /**
     * Classe interna Enumerazione.
     * <p/>
     * Codifica operazioni di selezione file
     */
    private enum OperazioneFile {

        crea(),
        selezione()

    }// fine della classe


    /**
     * Classe interna Enumerazione.
     * <p/>
     * Codifica tipi di selezione file
     */
    private enum TipoFile {

        soloFiles(JFileChooser.FILES_ONLY),
        soloDirs(JFileChooser.DIRECTORIES_ONLY),
        files_e_dirs(JFileChooser.FILES_AND_DIRECTORIES);

        /**
         * costante di JFileChooser corrispondente.
         */
        private int tipoChooser;


        /**
         * Costruttore completo con parametri.
         *
         * @param tipoChooser costante per JFileChooser
         */
        TipoFile(int tipoChooser) {
            try { // prova ad eseguire il codice
                this.setTipoChooser(tipoChooser);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        public int getTipoChooser() {
            return tipoChooser;
        }


        private void setTipoChooser(int tipoChooser) {
            this.tipoChooser = tipoChooser;
        }
    }// fine della classe

}// fine della classe
