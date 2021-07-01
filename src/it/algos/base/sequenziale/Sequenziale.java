/**
 * Title:        Sequenziale.java
 * Package:      it.algos.base.sequenziale
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 29 settembre 2002 alle 18.14
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2002  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
//  Package di questa classe e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.sequenziale;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

//-----------------------------------------------------------------------------
// Classe principale
//-----------------------------------------------------------------------------


/**
 * Classe generale del pacchetto che contiene varii metodi.<br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  29 settembre 2002 ore 18.14
 */
public class Sequenziale extends Object {

    //-------------------------------------------------------------------------
    // Costanti statiche della  classe   (private,pubbliche)  (class variables)
    //-------------------------------------------------------------------------
    /**
     * nome di questa classe (per i messaggi di errore)
     */
    private static final String NOMECLASSE = "Sequenziale";


    //-------------------------------------------------------------------------
    // Variabili statiche della classe         (private)      (class variables)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Riferimenti ad altri oggetti    (private,protette)  (instance variables)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Variabili degli oggetti (private,protette,pubbliche)(instance variables)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Costruttori della classe                                   constructors)
    //-------------------------------------------------------------------------
    /**
     * Costruttore
     */
    public Sequenziale(String unNome) {
        /** rimanda al costruttore della superclasse */
        super();
    } /* fine del metodo costruttore completo */


    //-------------------------------------------------------------------------
    // Metodi statici della classe                              (class methods)
    //-------------------------------------------------------------------------
    /**
     * Seleziona il file
     *
     * @param path percorso delle cartelle
     * @param nomeFile nome effettivo del file
     * @param suffisso suffisso, comprensivo di punto
     * @param indispensabile se non esiste apre il dialogo
     */
    public static File selezionaFile(String path,
                                     String nomeFile,
                                     String suffisso,
                                     boolean indispensabile) {
        /** variabili locali di lavoro */
        JFileChooser unDialogo = null;

        /** nome completo del file da aprire */
        String nomeLungo = path + nomeFile + suffisso;

        /** riferimento interno del file */
        File unFile = null;

        /** crea il riferimento al file */
        unFile = new File(nomeLungo);

        /** controlla che esista il file */
        if (unFile.isFile()) {
            return unFile;
        } // if

        /**  */
        if (indispensabile) {
            /** se non esiste il file apre il dialogo di selezione */
            unDialogo = new JFileChooser();
            SequenzialeFiltro unFiltro = new SequenzialeFiltro();
            unFiltro.addExtension(suffisso.substring(1, suffisso.length()));
            unFiltro.setDescription("Files di solo testo");
            unDialogo.setFileFilter(unFiltro);
            unDialogo.setCurrentDirectory(unFile);
            unDialogo.setDialogTitle("Apri il file " + nomeFile + suffisso);
            int risultato = unDialogo.showOpenDialog(null);

            /** l'utente ha premuto il bottone cancella */
            if (risultato == JFileChooser.CANCEL_OPTION) {
                unFile = null;
                return unFile;
            } // if

            /** recupera il nome selezionato */
            unFile = unDialogo.getSelectedFile();

            /** controlla che sia un nome valido */
            if (unFile == null || unFile.getName().equals("")) {
                /** altrimenti avvisa */
                JOptionPane.showMessageDialog(null,
                        "Nome del file selezionato non valido",
                        "Messaggio di errore",
                        JOptionPane.ERROR_MESSAGE);
                unFile = null;
            } // if
        } /* fine del blocco if */

        /** valore di ritorno */
        return unFile;
    } // selezionaFile


    /**
     * Apre un collegamento in lettura col file
     *
     * @param unFile file da collegare
     */
    public static FileInputStream apreFileLettura(File unFile) {
        /** riferimento interno del collegamento */
        FileInputStream unInput = null;

        /** controlla che esista un valido nome del file */
        if ((unFile != null) && (!unFile.getName().equals(""))) {
            try {                           // prova ad eseguire il codice
                unInput = new FileInputStream(unFile);
            } catch (Exception unErrore) {   // intercetta l'errore
                JOptionPane.showMessageDialog(null, "Non sono riuscito ad aprire il file");
            } // try-catch-finally
        } // if

        /** valore di ritorno */
        return unInput;
    } // apreFileLettura


    /**
     * Apre un collegamento in scrittura col file
     *
     * @param unFile file da collegare
     */
    public static FileOutputStream apreFileScrittura(File unFile) {
        /** riferimento interno del collegamento */
        FileOutputStream unOutput = null;

        /** controlla che esista un valido nome del file */
        if ((unFile != null) && (!unFile.getName().equals(""))) {
            try {                           // prova ad eseguire il codice
                unOutput = new FileOutputStream(unFile);
            } catch (Exception unErrore) {   // intercetta l'errore
                JOptionPane.showMessageDialog(null, "Non sono riuscito ad aprire il file");
            } // try-catch-finally
        } // if

        /** valore di ritorno */
        return unOutput;
    } // apreFileScrittura


    /**
     * Carica il file in memoria
     *
     * @param unInput collegamento in lettura
     */
    public static String leggeFile(FileInputStream unInput) {
        /** variabile locale di lunghezza */
        int lun = 0;

        /** variabile locale di testo */
        String unTesto = null;

        /** variabile locale per l'array di bytes */
        byte[] unSet = null;

        try {                           // prova ad eseguire il codice
            lun = unInput.available();// quanti caratteri ci sono
            unSet = new byte[lun];// prepara un array di bytes
            unInput.read(unSet);           //carica dal file nell'array
            unTesto = new String(unSet);//trasporta da array nella stringa
        } catch (Exception unErrore) {   // intercetta l'errore
            JOptionPane.showMessageDialog(null, "Non sono riuscito a caricare il file");
        } // try-catch-finally

        /** valore di ritorno */
        return unTesto;
    } // leggeFile


    /**
     * Apre un file di testo e carica il contenuto in un array di stringhe
     *
     * @param path percorso delle cartelle
     * @param nomeFile nome effettivo del file
     * @param suffisso suffisso, comprensivo di punto
     * @param indispensabile se non esiste apre il dialogo
     *
     * @return unTesto stringa di testo letta dal file
     */
    public static String leggeFile(String path,
                                   String nomeFile,
                                   String suffisso,
                                   boolean indispensabile) {
        /** variabile locale di lavoro */
        String unTesto = "";
        FileInputStream input = null;
        File unFile = null;

        unFile = Sequenziale.selezionaFile(path, nomeFile, suffisso, indispensabile);

        /** controlla il risultato */
        if (unFile.isFile()) {
            /** procede */
            input = Sequenziale.apreFileLettura(unFile);
            unTesto = Sequenziale.leggeFile(input);

            Sequenziale.chiudeFile(input);
        } else {
            if (indispensabile) {
// mostra un errore
            } /* fine del blocco if/else */
        } /* fine del blocco if/else */

        /** valore di ritorno del testo */
        return unTesto;
    } /* fine del metodo */


    /**
     * Registra il file su disco
     *
     * @param unInput collegamento in scrittura
     */
    public static boolean scriveFile(FileOutputStream unOutput, String unTesto) {
        /** variabile locale di lunghezza */
        int lun = 0;

        /** variabile locale di controllo */
        boolean isEseguito = false;

        /** variabile locale per l'array di bytes */
        byte[] unSet = null;

        try {                           // prova ad eseguire il codice
            lun = unTesto.length(); // quanti caratteri ci sono
            unSet = new byte[lun];// prepara un array di bytes
            unSet = unTesto.getBytes();//trasporta da stringa ad array
            unOutput.write(unSet);        // registra da array nel file
            isEseguito = true;
        } catch (Exception unErrore) {   // intercetta l'errore
            JOptionPane.showMessageDialog(null, "Non sono riuscito a registrare il file");
        } // try-catch-finally

        /** valore di ritorno */
        return isEseguito;
    } // scriveFile


    /**
     * Chiude un file aperto in lettura
     *
     * @param unInput collegamento in lettura
     */
    public static boolean chiudeFile(FileInputStream unInput) {
        /** variabile locale di controllo */
        boolean isEseguito = false;

        try {                           // prova ad eseguire il codice
            unInput.close();              // chiude il file
            isEseguito = true;
        } catch (Exception unErrore) {   // intercetta l'errore
            JOptionPane.showMessageDialog(null, "Non sono riuscito a chiudere il file");
        } // try-catch-finally

        /** valore di ritorno */
        return isEseguito;
    } // chiudeFile


    /**
     * Chiude un file aperto in scrittura
     *
     * @param unOutput collegamento in scrittura
     */
    public static boolean chiudeFile(FileOutputStream unOutput) {
        /** variabile locale di controllo */
        boolean isEseguito = false;

        try {                           // prova ad eseguire il codice
            unOutput.close();              // chiude il file
            isEseguito = true;
        } catch (Exception unErrore) {   // intercetta l'errore
            JOptionPane.showMessageDialog(null, "Non sono riuscito a chiudere il file");
        } // try-catch-finally

        /** valore di ritorno */
        return isEseguito;
    } // chiudeFile
    //-------------------------------------------------------------------------
    // Metodi privati chiamati dal costruttore prima di tornare alla sottoclasse
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi protetti chiamati dalla sottoclasse subito dopo il costruttore
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi privati degli oggetti (usati nella classe)     (instance methods)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi protetti degli oggetti (usati nelle sottoclassi)    (instance m.)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi che verranno sovrascritti nelle sottoclassi          (overriding)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi che sovrascrivono metodi della superclasse           (overriding)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi pubblici degli oggetti (usati in altre classi)      (instance m.)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi di regolazione delle variabili locali                    (setter)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi di restituzione delle variabili locali                   (getter)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi di gestione interni, invocati dai metodi azione     (instance m.)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi azione                                         (instance methods)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Classi azione interne degli eventi                         (inner class)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
}// fine della classe principale it.algos.base.sequenziale.Sequenziale.java

//-----------------------------------------------------------------------------

