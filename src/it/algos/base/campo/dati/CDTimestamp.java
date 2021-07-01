/**
 * Title:        CDTimestamp.java
 * Package:      it.algos.base.campo.dati
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Ceresa, Colombo, Valbonesi
 * @version 1.0  /
 * Creato:       il 23 ottobre 2003 alle 12.21
 */
package it.algos.base.campo.dati;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.tipodati.tipoarchivio.TATimestamp;
import it.algos.base.campo.tipodati.tipoarchivio.TipoArchivio;
import it.algos.base.campo.tipodati.tipomemoria.TMTimestamp;
import it.algos.base.campo.tipodati.tipomemoria.TipoMemoria;
import it.algos.base.campo.tipodati.tipovideo.TVTesto;
import it.algos.base.campo.tipodati.tipovideo.TipoVideo;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;

import java.sql.Timestamp;

/**
 * Classe concreta per implementare un oggetto da CDBase;
 * <p/>
 * Questa classe concreta e' responsabile di: <br>
 * A - Implementare i metodi astratti della superclasse per regolare le
 * conversioni tra i vari tipi di dati <br>
 *
 * @author Guido Andrea Ceresa
 * @author gac
 * @version 1.0  /  23 ottobre 2003 ore 12.21
 */
public final class CDTimestamp extends CDBase {

    private static final TipoArchivio TIPO_ARCHIVIO = TATimestamp.getIstanza();

    private static final TipoMemoria TIPO_MEMORIA = TMTimestamp.getIstanza();

    private static final TipoVideo TIPO_VIDEO = TVTesto.getIstanza();


    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo utilizzando eventuali valori di default
     */
    public CDTimestamp() {
        /** rimanda al costruttore di questa classe */
        this(null);
    } /* fine del metodo costruttore base */


    /**
     * Costruttore completo
     *
     * @param unCampoParente campo 'contenitore' di questo oggetto
     */
    public CDTimestamp(Campo unCampoParente) {
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

    } /* fine del metodo inizia */


    /**
     * Ritorna true se il campo e' Timestamp.
     *
     * @return true se è campo Timestamp
     */
    public boolean isTimestamp() {
        return true;
    }


    /**
     * converte da memoria a video
     */
    public void memoriaVideo() {
        /** variabili e costanti locali di lavoro */
        String unaStringa = "";
        Timestamp unaOra = new Timestamp(0);

        /** invoca il metodo sovrascritto della superclasse */
        super.memoriaVideo();

        /** effettua la (eventuale) conversione */
        try {    // prova ad eseguire il codice

            /** recupera il valore della memoria */
            unaOra = (Timestamp)getMemoria();

            if (unaOra != null) {
                unaStringa = unaOra.toString();
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /** registra il valore del video iniziale */
        this.setVideo(unaStringa);
    } /* fine del metodo */


    /**
     * converte da video a memoria
     */
    public void videoMemoria() {
        /* variabili e costanti locali di lavoro */
        Object video = null;
        String stringa = "";
        Object vuoto = null;
        Timestamp ora = null;

        /** invoca il metodo sovrascritto della superclasse */
        super.videoMemoria();

        try {    // prova ad eseguire il codice

            /* recupera il valore memoria vuoto per il tipo specifico */
            vuoto = this.getValoreMemoriaVuoto();

            /* recupera il video e lo converte in stringa */
            video = this.getVideo();
            stringa = Lib.Testo.getStringa(video);

            /* se la stringa e' valorizzata effettua la conversione, altrimenti
             * registra il valore vuoto */
            if (stringa.length() > 0) {
                try { // prova ad eseguire il codice
                    ora = Timestamp.valueOf(stringa);
                    /* registra il valore in memoria */
                    this.setMemoria(ora);
                } catch (Exception unErrore) { // intercetta l'errore
                }// fine del blocco try-catch
            } else {
                /* registra il valore vuoto in memoria */
                this.setMemoria(vuoto);
            }// fine del blocco if-else

//            /* recupera il valore del video */
//            unaStringa = (String)getVideo();
//
//            /* effettua la (eventuale) conversione */
//            if (unaStringa.length() > 0) {
//                try {	// prova ad eseguire il codice
//                    ora = Timestamp.valueOf(unaStringa);
//                    /* registra il valore in memoria */
//                    this.setMemoria(ora);
//                } catch (Exception unErrore) { // intercetta l'errore
//                    /* registra il valore in memoria */
//                    this.setMemoria(vuoto);
//                }// fine del blocco try-catch
//
//            } else {
//                /* registra il valore in memoria */
//                this.setMemoria(vuoto);
//            }// fine del blocco if-else

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    } /* fine del metodo videoMemoria */

}// fine della classe