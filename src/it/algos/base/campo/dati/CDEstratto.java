/**
 * Title:        CDIntero.java
 * Package:      it.algos.base.campo.dati
 * Description:
 * Copyright:    Copyright (c) 2007
 * Company:      Algos s.r.l.
 * @author Ceresa, Valbonesi
 * @version 1.0  /
 * Creato:       23 nov 2007 ore 14.23
 */
package it.algos.base.campo.dati;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.logica.CLEstratto;
import it.algos.base.campo.logica.CampoLogica;
import it.algos.base.campo.tipodati.tipoarchivio.TAIntero;
import it.algos.base.campo.tipodati.tipoarchivio.TipoArchivio;
import it.algos.base.campo.tipodati.tipomemoria.TMIntero;
import it.algos.base.campo.tipodati.tipomemoria.TipoMemoria;
import it.algos.base.campo.tipodati.tipovideo.TVIntero;
import it.algos.base.campo.tipodati.tipovideo.TipoVideo;
import it.algos.base.errore.Errore;

/**
 * Campo dati Estratto;
 * <p/>
 *
 * @author Guido Andrea Ceresa
 * @author alex
 * @version 1.0  /  23 nov 2007 ore 14.23
 */
public abstract class CDEstratto extends CDBase {

    /**
     * costanti che rappresntano un singleton della classe appropriata
     */
    private static final TipoArchivio TIPO_ARCHIVIO = TAIntero.getIstanza();

    private static final TipoMemoria TIPO_MEMORIA = TMIntero.getIstanza();

    private static final TipoVideo TIPO_VIDEO = TVIntero.getIstanza();


    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo utilizzando eventuali valori di default
     */
    public CDEstratto() {
        /** rimanda al costruttore di questa classe */
        this(null);
    } /* fine del metodo costruttore base */


    /**
     * Costruttore completo
     *
     * @param unCampoParente campo 'contenitore' di questo oggetto
     */
    public CDEstratto(Campo unCampoParente) {
        /** rimanda al costruttore della superclasse */
        super(unCampoParente, TIPO_ARCHIVIO, TIPO_MEMORIA, TIPO_VIDEO);

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
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    } /* fine del metodo inizia */


    /**
     * Verifica se il campo e' vuoto.
     * <p/>
     *
     * @return true se il campo e' vuoto
     */
    public boolean isVuoto() {
        /* variabili e costanti locali di lavoro */
        boolean vuoto = true;
        CLEstratto cl;
        boolean esiste;


        try { // prova ad eseguire il codice
            cl = this.getCampoLogica();
            if (cl != null) {
                esiste = cl.isEsisteRecordEsterno();
                vuoto = !esiste;
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return vuoto;
    }


    /**
     * Ritorna true se il campo e' numerico.
     *
     * @return true se è campo numerico
     */
    public boolean isNumero() {
        return true;
    }


    /**
     * Ritorna il campo Logica specifico
     * <p/>
     *
     * @return il campo Logica
     */
    private CLEstratto getCampoLogica() {
        /* variabili e costanti locali di lavoro */
        CLEstratto clEstratto = null;
        CampoLogica campoLogica;

        try { // prova ad eseguire il codice
            campoLogica = this.getCampoParente().getCampoLogica();
            if (campoLogica != null) {
                if (campoLogica instanceof CLEstratto) {
                    clEstratto = (CLEstratto)campoLogica;
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return clEstratto;
    }


}// fine della classe
