/**
 * Title:        CDLink.java
 * Package:      it.algos.base.campo.dati
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Ceresa, Colombo, Valbonesi
 * @version 1.0  /
 * Creato:       il 23 ottobre 2003 alle 14.23
 */
package it.algos.base.campo.dati;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.tipodati.tipoarchivio.TALink;
import it.algos.base.campo.tipodati.tipoarchivio.TipoArchivio;
import it.algos.base.campo.tipodati.tipomemoria.TMIntero;
import it.algos.base.campo.tipodati.tipomemoria.TipoMemoria;
import it.algos.base.campo.tipodati.tipovideo.TVTesto;
import it.algos.base.campo.tipodati.tipovideo.TipoVideo;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Libreria;
import it.algos.base.tavola.renderer.RendererNumero;

/**
 * Classe concreta per implementare un oggetto da CDBase;
 * <p/>
 * Questa classe concreta e' responsabile di: <br>
 * A - Implementare i metodi astratti della superclasse per regolare le
 * conversioni tra i vari tipi di dati <br>
 *
 * @author Guido Andrea Ceresa
 * @author gac
 * @version 1.0  /  23 ottobre 2003 ore 14.23
 */
public final class CDLink extends CDBase {

    private static final TipoArchivio TIPO_ARCHIVIO = TALink.getIstanza();

    private static final TipoMemoria TIPO_MEMORIA = TMIntero.getIstanza();

    private static final TipoVideo TIPO_VIDEO = TVTesto.getIstanza();


    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo utilizzando eventuali valori di default
     */
    public CDLink() {
        /** rimanda al costruttore di questa classe */
        this(null);
    } /* fine del metodo costruttore base */


    /**
     * Costruttore completo
     *
     * @param unCampoParente campo 'contenitore' di questo oggetto
     */
    public CDLink(Campo unCampoParente) {
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

        /* assegna l'icona specifica per il tipo di campo */
        this.setIcona(ICONA_CAMPO_NUM);

        /* regola il renderer per la lista */
        this.setRenderer(new RendererNumero(this.getCampoParente()));

    } /* fine del metodo inizia */


    /**
     * converte da memoria a video
     */
    public void memoriaVideo() {
        /** variabili e costanti locali di lavoro */
        String unaStringa = "";
        Integer unIntero = null;

        /** invoca il metodo sovrascritto della superclasse */
        super.memoriaVideo();

        /* effettua la (eventuale) conversione */
        try {    // prova ad eseguire il codice

            /** recupera il valore della memoria */
            unIntero = (Integer)getMemoria();

            if (unIntero != super.getValoreMemoriaVuoto()) {

                /** converte l'intero in Stringa */
                unaStringa = unIntero.toString();

                /** registra il valore del video */
                this.setVideo(unaStringa);

            } /* fine del blocco if */

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

    } /* fine del metodo */


    /**
     * converte da video a memoria
     */
    public void videoMemoria() {
        /** variabile di lavoro */
        Object memoria = null;
        int intero = 0;

        /** invoca il metodo sovrascritto della superclasse */
        super.videoMemoria();

        try {    // prova ad eseguire il codice

            if (this.isValoreVideoVuoto() == false) {
                /* recupera il valore video e lo trasforma in Integer */
                intero = Libreria.getInt(this.getVideo());
                memoria = new Integer(intero);
            } else {
                memoria = this.getValoreMemoriaVuoto();
            }// fine del blocco if-else

            /* registra il valore in memoria */
            this.setMemoria(memoria);

//            /* recupera il valore video e lo trasforma in Integer */
//            intero = Libreria.getInt(this.getVideo());
//            unIntero = new Integer(intero);
//
//            /* registra il valore in memoria */
//            this.setMemoria(unIntero);

//            /** recupera il valore del video */
//            unaStringa = (String)getVideo();
//
//            /** effettua la (eventuale) conversione */
//            if (unaStringa.equals(super.getValoreVideoVuoto()) == false) {
//                if (unaStringa.length() > 0) {
//
//                    unIntero = Integer.decode(unaStringa);
//
//                    /** registra il valore in memoria */
//                    this.setMemoria(unIntero);
//
//                } /* fine del blocco if */
//            } /* fine del blocco if */

        } catch (Exception unErrore) {    // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo memoriaVideo */

}// fine della classe