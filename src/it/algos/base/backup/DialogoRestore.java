/**
 * Title:     DialogoRestore
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      26-gen-2007
 */
package it.algos.base.backup;

import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.LibFile;
import it.algos.base.messaggio.MessaggioAvviso;

import javax.swing.*;
import java.io.File;

/**
 * Dialogo di impostazione del restore.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 26-gen-2007
 */
public class DialogoRestore extends DialogoBR {


    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param backup oggetto Backup di riferimento
     */
    public DialogoRestore(Backup backup) {
        /* rimanda al costruttore della superclasse */
        super(backup);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni immediate di riferimenti e variabili. <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */
        String testo;
        JLabel label;
        Icon icona;
        JPanel pan;

        try { // prova ad eseguire il codice
            this.setTitolo("Restore");
            this.setMessaggio("Ripristino dei dati da backup");
            this.setIcona(Lib.Risorse.getIconaBase("restore32"));

//            /* crea il componente di avviso */
//            icona = Lib.Risorse.getIconaBase("Danger36");
//            testo = "Attenzione! Questa procedura elimina i dati correnti!";
//            label = new JLabel(testo);
//            label.setForeground(Color.red);
//            pan = new JPanel();
//            pan.setLayout(new FlowLayout());
//            pan.setOpaque(false);
//            pan.add(new JLabel(icona));
//            pan.add(label);
//            Lib.Comp.bloccaDimMax(pan);
//
//            this.setCompAvviso(pan);

            this.avvia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    public void inizializza() {
        super.inizializza();
    } /* fine del metodo */


    /**
     * Recupera il voce del dialogo.
     * <p/>
     *
     * @return il voce del dialogo
     */
    protected String getTitoloDialogo() {
        return ("Seleziona la posizione del backup");
    }


    /**
     * Recupera il voce del campo percorso.
     * <p/>
     *
     * @return il voce del campo percorso
     */
    protected String getTitoloCampoPercorso() {
        /* valore di ritorno */
        return "Cartella del backup da recuperare";
    }


    /**
     * Controlla che la directory selezionata per il backup / restore sia valida.
     * <p/>
     *
     * @return true se valida
     */
    protected boolean isPercorsoValido(File dir) {
        return this.isContieneDBValido(dir);
    }


    /**
     * Controlla se il percorso correntemente selezionato contiene un
     * database di backup valido per questo Progetto.
     * <p/>
     * Per essere valido la cartella deve contenere almeno
     * i files .data e .script.
     * Il nome del database di backup deve essere quello stabilito
     * dal Progetto.
     *
     * @param dir directory selezionata
     *
     * @return true se contiene un database valido.
     */
    private boolean isContieneDBValido(File dir) {
        /* variabili e costanti locali di lavoro */
        boolean valido = false;
        boolean continua = true;
        File file;
        String nomeDB;
        String nomeFileScript;
        String nomeFileData;
        String testo;

        try {    // prova ad eseguire il codice

            nomeDB = this.getBackup().getNomeDatabase();
            nomeFileScript = nomeDB + ".script";
            nomeFileData = nomeDB + ".data";

            /* controlla che il percorso sia una directory e che sia raggiungibile */
            if (continua) {
                continua = LibFile.isEsisteDir(dir);
            }// fine del blocco if

            /* controlla che contenga il file .script */
            if (continua) {
                file = new File(dir + "/" + nomeFileScript);
                continua = LibFile.isEsisteFile(file);
            }// fine del blocco if

            /* controlla che contenga il file .data */
            if (continua) {
                file = new File(dir + "/" + nomeFileData);
                continua = LibFile.isEsisteFile(file);
            }// fine del blocco if

            /* messaggio se non valido */
            if (!continua) {
                testo = "La cartella selezionata non contiene un\n";
                testo += "database di backup valido per questo progetto.\n";
                testo += "Deve contenere almeno i files ";
                testo += nomeFileScript;
                testo += " e ";
                testo += nomeFileData;
                new MessaggioAvviso(testo);
                continua = false;
            }// fine del blocco if

            if (continua) {
                valido = true;
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return valido;
    }


}// fine della classe
