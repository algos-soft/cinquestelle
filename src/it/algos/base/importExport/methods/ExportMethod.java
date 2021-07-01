/**
 * Title:     ExportMethod
 * Copyright: Copyright (c) 2008
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      03-01-2008
 */
package it.algos.base.importExport.methods;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;

import java.io.FileOutputStream;
import java.util.ArrayList;

/**
 * Metodo di esportazione.
 * <p/>
 * Va sempre inizializzato prima di chiamare i metodi writeXXX
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0 / 03-01-2008
 */
public abstract class ExportMethod {

    /**
     * Elenco dei campi corrispondenti alle colonne dei dati da esportare
     */
    private ArrayList<Campo> campi;

    /**
     * FileOutputStream per la scrittura
     */
    private FileOutputStream stream;

    /**
     * titolo dei dati esportati, dove supportato (es. nome dello shhet excel)
     */
    private String titoloDati;


    /**
     * Costruttore completo con parametri. <br>
     */
    public ExportMethod() {
        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza coi parametri */

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        try { // prova ad eseguire il codice
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regolazioni iniziali.
     * </p>
     *
     * @param campi corrispondenti alle colonne
     * @param stream per la scrittura su file
     */
    public void inizializza(ArrayList<Campo> campi, FileOutputStream stream) {
        /* variabili e costanti locali di lavoro */

        try {    // prova ad eseguire il codice

            this.setCampi(campi);
            this.setStream(stream);

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    } // fine del metodo


    /**
     * Eventuali operazioni di chiusura specifiche del metodo.
     * <p/>
     */
    public void close() {
    }


    /**
     * Scrive i titoli delle colonne.
     * <p/>
     *
     * @param titoli delle colonne
     */
    public void writeTitles(ArrayList<String> titoli) {
    }


    /**
     * Scrive una riga di dati.
     * <p/>
     *
     * @param valori da scrivere nelle celle
     */
    public void writeRow(ArrayList<Object> valori) {
    }


    /**
     * Ritorna il campo corrispondente a una data colonna.
     * <p/>
     *
     * @param colonna numero della colonna (0 per la prima)
     *
     * @return il campo corrispondente
     */
    protected Campo getCampo(int colonna) {
        /* variabili e costanti locali di lavoro */
        Campo campo = null;
        ArrayList<Campo> campi;

        try {    // prova ad eseguire il codice
            campi = this.getCampi();
            if (colonna < campi.size()) {
                campo = campi.get(colonna);
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return campo;
    }


    protected ArrayList<Campo> getCampi() {
        return campi;
    }


    private void setCampi(ArrayList<Campo> campi) {
        this.campi = campi;
    }


    protected FileOutputStream getStream() {
        return stream;
    }


    private void setStream(FileOutputStream stream) {
        this.stream = stream;
    }


    protected String getTitoloDati() {
        return titoloDati;
    }


    public void setTitoloDati(String titoloDati) {
        this.titoloDati = titoloDati;
    }
} // fine della classe