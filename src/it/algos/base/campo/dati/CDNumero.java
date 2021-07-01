/**
 * Package:      it.algos.base.campo.dati
 * Description:
 * Copyright:    Copyright (c) 2005
 * Company:      Algos s.r.l.
 * @author Ceresa, Valbonesi
 * @version 1.0  /
 * Creato:       il 10 ottobre 2005 alle 10.23
 */
package it.algos.base.campo.dati;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.tipodati.tipoarchivio.TipoArchivio;
import it.algos.base.campo.tipodati.tipomemoria.TipoMemoria;
import it.algos.base.campo.tipodati.tipovideo.TipoVideo;
import it.algos.base.errore.Errore;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.tavola.renderer.RendererNumero;
import it.algos.base.validatore.Validatore;
import it.algos.base.validatore.ValidatoreFactory;

import java.text.Format;
import java.text.NumberFormat;

/**
 * Campo dati astratto per tutti i campi numerici;
 *
 * @author Guido Andrea Ceresa
 * @author alex
 * @version 1.0  /  10 ottobre 2005 alle 10.23
 */
public abstract class CDNumero extends CDFormat {


    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo utilizzando eventuali valori di default
     */
    public CDNumero() {
        /** rimanda al costruttore di questa classe */
        this(null, null, null, null);
    } /* fine del metodo costruttore base */


    /**
     * Costruttore completo
     *
     * @param unCampoParente campo 'contenitore' di questo oggetto
     */
    public CDNumero(Campo unCampoParente, TipoArchivio ta, TipoMemoria tm, TipoVideo tv) {
        /** rimanda al costruttore della superclasse */
        super(unCampoParente, ta, tm, tv);

        /** regolazioni iniziali di riferimenti e variabili */
        try {                                   // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore completo */


    /**
     * Regolazioni iniziali di riferimenti e variabili
     * Metodo chiamato direttamente dal costruttore
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        try { // prova ad eseguire il codice

            /* assegna l'icona specifica per il tipo di campo */
            this.setIcona(ICONA_CAMPO_NUM);

            /* regola l'uso di default del range di ricerca */
            this.setUsaRangeRicerca(true);

            /* assegna un validatore di default al campo */
            this.setValidatore(ValidatoreFactory.num());

            /* regola il renderer per la lista */
            this.setRenderer(new RendererNumero(this.getCampoParente()));

            /* aggiunge gli operatori di filtro disponibili */
            this.clearOperatoriRicerca();
            this.addOperatoreRicerca(Filtro.Op.UGUALE);
            this.addOperatoreRicerca(Filtro.Op.MAGGIORE);
            this.addOperatoreRicerca(Filtro.Op.MAGGIORE_UGUALE);
            this.addOperatoreRicerca(Filtro.Op.MINORE);
            this.addOperatoreRicerca(Filtro.Op.MINORE_UGUALE);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch   } /* fine del metodo inizia */
    } /* fine del metodo inizia */


    /**
     * Regolazioni dinamiche dell'oggetto.
     * <p/>
     * Regola le caratteristiche dinamiche in base alle impostazioni
     * correnti delle caratteristiche statiche <br>
     * Metodo invocato dalla classe che crea questo oggetto (di norma) <br>
     * Viene eseguito una sola volta <br>
     * Sovrascritto nelle sottoclassi <br>
     */
    public void inizializza() {

        try { // prova ad eseguire il codice
            /* invoca il metodo sovrascritto della superclasse */
            super.inizializza();


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Crea un oggetto Format da assegnare al campo.
     * <p/>
     * Invocato dal cliclo Inizia.
     * Implementato nelle sottoclassi concrete.
     * Il metodo deve creare e ritornare il Format adeguato.
     * Se il metodo ritorna null non viene usato il format
     * e la conversione viene effettuata secondo le regole della superclasse
     *
     * @return l'oggetto Format
     */
    public Format createFormat() {
        NumberFormat nf = null;

        try { // prova ad eseguire il codice

            nf = NumberFormat.getInstance();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return nf;
    }


    /**
     * Regola il numero di decimali per il campo
     * <p/>
     *
     * @param numDecimali numero di massimo di cifre decimali inseribili
     * e numero fisso di cifre decimali visualizzate
     */
    public void setNumDecimali(int numDecimali) {
        /* variabili e costanti locali di lavoro */
        Format format;
        NumberFormat nf = null;
        Validatore vld;

        try { // prova ad eseguire il codice

            /* regola il format */
            format = this.getFormat();
            if (format != null) {
                if (format instanceof NumberFormat) {
                    nf = (NumberFormat)format;
                    nf.setMinimumFractionDigits(numDecimali);
                }// fine del blocco if
            }// fine del blocco if

            /* regola il validatore */
            vld = this.getValidatore();
            if (vld != null) {
                vld.setMaxCifreDecimali(numDecimali);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Restituisce il numero di decimali per il campo
     * <p/>
     *
     * @return il numero massimo di decimali per il campo
     */
    public int getNumDecimali() {
        /* variabili e costanti locali di lavoro */
        int numDec = 0;
        Validatore vld;

        try {    // prova ad eseguire il codice

            /* regola il validatore */
            vld = this.getValidatore();
            if (vld != null) {
                numDec = vld.getMaxCifreDecimali();
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return numDec;
    }


    /**
     * Uso del separatore delle migliaia nel rendering
     * <p/>
     * @param flag true per usare il separatore delle migliaia
     */
    public void setUsaSeparatoreMigliaia(boolean flag){
        try { // prova ad eseguire il codice
            Format format = this.getFormat();
            if (format!=null) {
                if (format instanceof NumberFormat) {
                    NumberFormat nf = (NumberFormat)format;
                    nf.setGroupingUsed(flag);
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


//    /**
//     * Assegna un Cell Editor al campo per l'eventuale editing
//     * all'interno delle liste.
//     * <p/>
//     * Metodo invocato dal ciclo Inizia.
//     * Di default è un editor di testo.
//     * Sovrascritto dalle sottoclassi.
//     */
//    protected void assegnaCellEditor() {
//        /* variabili e costanti locali di lavoro */
//        TableCellEditor tEditor;
//        DefaultCellEditor dEditor;
//        Component comp;
//        JTextField field;
//
//        super.assegnaCellEditor();
//
//        try {    // prova ad eseguire il codice
//
//            /* essendo un numero regola l'allineamento
//             * del JTextField a destra */
//            tEditor = this.getEditor();
//            if (tEditor instanceof DefaultCellEditor) {
//                dEditor = (DefaultCellEditor)tEditor;
//                comp = dEditor.getComponent();
//                if (comp != null) {
//                    if (comp instanceof JTextField) {
//                        field = (JTextField)comp;
//                        field.setHorizontalAlignment(JTextField.RIGHT);
//                    }// fine del blocco if
//                }// fine del blocco if
//            }// fine del blocco if
//
//        } catch (Exception unErrore) {    // intercetta l'errore
//            Errore.crea(unErrore);
//        } // fine del blocco try-catch
//    }


    /**
     * Ritorna true se il campo e' numerico.
     *
     * @return true se è campo numerico
     */
    public boolean isNumero() {
        return true;
    }


}// fine della classe
