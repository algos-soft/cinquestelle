/**
 * Title:     CDReale
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      30-mar-2006
 */
package it.algos.base.campo.dati;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.tipodati.tipoarchivio.TAReale;
import it.algos.base.campo.tipodati.tipoarchivio.TipoArchivio;
import it.algos.base.campo.tipodati.tipomemoria.TMReale;
import it.algos.base.campo.tipodati.tipomemoria.TipoMemoria;
import it.algos.base.campo.tipodati.tipovideo.TVTesto;
import it.algos.base.campo.tipodati.tipovideo.TipoVideo;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.validatore.ValidatoreFactory;

import java.text.DecimalFormat;
import java.text.Format;

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
public class CDReale extends CDNumero {

    /**
     * costanti che rappresntano un singleton della classe appropriata
     */
    private static final TipoArchivio TIPO_ARCHIVIO = TAReale.getIstanza();

    private static final TipoMemoria TIPO_MEMORIA = TMReale.getIstanza();

    private static final TipoVideo TIPO_VIDEO = TVTesto.getIstanza();


    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo utilizzando eventuali valori di default
     */
    public CDReale() {
        /** rimanda al costruttore di questa classe */
        this(null);
    } /* fine del metodo costruttore base */


    /**
     * Costruttore completo
     *
     * @param unCampoParente campo 'contenitore' di questo oggetto
     */
    public CDReale(Campo unCampoParente) {
        /** rimanda al costruttore della superclasse */
        super(unCampoParente, CDReale.TIPO_ARCHIVIO, CDReale.TIPO_MEMORIA, CDReale.TIPO_VIDEO);

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

            /* assegna un validatore al campo */
            this.setValidatore(ValidatoreFactory.numReal());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    } /* fine del metodo inizia */


    /**
     * Allinea le variabili del Campo: da Memoria verso Video.
     * </p>
     * Vengono effettuate le conversioni tra i varii tipi di dati <br>
     * Parte dalla variabile Memoria (gia' regolata), e regola
     * di conseguenza Video <br>
     */
    public void memoriaVideo() {
        /* variabili e costanti locali di lavoro */
        Format format;
        String stringa;
        double doppio = 0;

        try {    // prova ad eseguire il codice

            /* recupera un eventuale format */
            format = this.getFormat();

            if (format != null) {

                doppio = this.getDoppioMemoria();

                /* converte l'oggetto in stringa */
                stringa = format.format(doppio);

                /* registra il valore del video */
                this.setVideo(stringa);

            } else {
                super.memoriaVideo();
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    }


    /**
     * Ritorna il double corrispondente al valore in memoria.
     * <p/>
     *
     * @return il numero Double corrispondente alla memoria
     */
    protected double getDoppioMemoria() {
        /* variabili e costanti locali di lavoro */
        double doppio = 0;
        Object valore;

        try {    // prova ad eseguire il codice
            valore = this.getMemoria();

            doppio = Libreria.getDouble(valore);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return doppio;
    }


    /**
     * Allinea le variabili del Campo: da Video verso Memoria.
     * <p/>
     * Vengono effettuate le conversioni tra i varii tipi di dati <br>
     * Parte dalla variabile Video (gia' regolata), e regola
     * di conseguenza Memoria <br>
     * La variabile memoria e' allineata per calcoli varii <br>
     */
    public void videoMemoria() {
        /* variabili e costanti locali di lavoro */
        double doppio;

        try {    // prova ad eseguire il codice

            doppio = this.getDoppioVideo();
            this.setMemoria(doppio);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    }


    /**
     * Ritorna il double corrispondente alla stringa contenuta nel video.
     * <p/>
     *
     * @return il numero Double corrispondente alla stringa in video
     */
    protected double getDoppioVideo() {
        /* variabili e costanti locali di lavoro */
        double doppio = 0;
        DecimalFormat format;
        String video;
        Number numero = null;

        try {    // prova ad eseguire il codice

            /*
             * recupera il Format
             * - se esiste, effettua la conversione tramite il Format
             * - altrimenti, tiene per buona quella gia' eseguita
             *   nella superclasse
             */
            format = (DecimalFormat)this.getFormat();

            if (format != null) {

                /* recupera il valore del video */
                video = (String)this.getVideo();

                if (Lib.Testo.isValida(video)) {

                    /* converte la stringa in oggetto */
                    try {
                        numero = format.parse(video);
                    } catch (Exception unErrore) {
                    }

                    if (numero != null) {

                        doppio = numero.doubleValue();

                    }// fine del blocco if

                }// fine del blocco if-else

            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return doppio;
    }


    /**
     * Controllo di validità del valore video.
     * <p/>
     * Controlla che il valore sia compatibile col tipo di dati del Campo <br>
     *
     * @param valoreVideo oggetto da controllare
     *
     * @return true se il valore è compatibile
     */
    public boolean isVideoValido(Object valoreVideo) {
        /* variabili e costanti locali di lavoro */
        boolean valido = false;
        String stringa;

        try { // prova ad eseguire il codice
            /* effettua la conversione */
            if (valoreVideo instanceof String) {

                /* provo a creare un double dalla stringa;
                 * se non funziona passa automaticamente al blocco catch */
                stringa = (String)valoreVideo;
                new Double(stringa);

                /* se tutto funziona ... */
                valido = true;

            } /* fine del blocco if */

        } catch (Exception unErrore) { // intercetta l'errore
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valido;
    }


    /**
     * Converte un valore di memoria.
     * <p/>
     * Trasforma in un valore accettabile per questo tipo di campo <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @param valore in ingresso
     *
     * @return valore accettabile convertito
     *         0 se non accettabile
     */
    public Object convertiMemoria(Object valore) {
        /* variabili e costanti locali di lavoro */
        double valoreCovertito = 0;
        boolean continua;
        String testo;

        try { // prova ad eseguire il codice

            continua = (valore != null);

            if (continua) {
                if (valore instanceof Double) {
                    valoreCovertito = (Double)valore;
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            if (continua) {
                if (valore instanceof String) {
                    testo = valore.toString();

                    try { // prova ad eseguire il codice
                        valoreCovertito = new Double(testo);
                    } catch (Exception unErrore) { // intercetta l'errore
                    }// fine del blocco try-catch

                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valoreCovertito;
    }


}// fine della classe
