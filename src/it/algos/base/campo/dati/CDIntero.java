/**
 * Title:        CDIntero.java
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
import it.algos.base.campo.tipodati.tipoarchivio.TAIntero;
import it.algos.base.campo.tipodati.tipoarchivio.TipoArchivio;
import it.algos.base.campo.tipodati.tipomemoria.TMIntero;
import it.algos.base.campo.tipodati.tipomemoria.TipoMemoria;
import it.algos.base.campo.tipodati.tipovideo.TVTesto;
import it.algos.base.campo.tipodati.tipovideo.TipoVideo;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.validatore.ValidatoreFactory;

import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;

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
public final class CDIntero extends CDNumero {

    /**
     * costanti che rappresntano un singleton della classe appropriata
     */
    private static final TipoArchivio TIPO_ARCHIVIO = TAIntero.getIstanza();

    private static final TipoMemoria TIPO_MEMORIA = TMIntero.getIstanza();

    private static final TipoVideo TIPO_VIDEO = TVTesto.getIstanza();


    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo utilizzando eventuali valori di default
     */
    public CDIntero() {
        /** rimanda al costruttore di questa classe */
        this(null);
    } /* fine del metodo costruttore base */


    /**
     * Costruttore completo
     *
     * @param unCampoParente campo 'contenitore' di questo oggetto
     */
    public CDIntero(Campo unCampoParente) {
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

            /* assegna un validatore di default al campo */
            this.setValidatore(ValidatoreFactory.numInt());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    } /* fine del metodo inizia */


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
        DecimalFormat format;
        String video;
        Number numero = null;
        Integer intero;

        try {    // prova ad eseguire il codice

//            /* invoca il metodo sovrascritto della superclasse */
//            super.videoMemoria();

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
                    try { // prova ad eseguire il codice
                        numero = format.parse(video);
                    } catch (Exception unErrore) { // intercetta l'errore
                    }// fine del blocco try-catch

                    if (numero != null) {
                        intero = numero.intValue();

                        /* registra il valore del video */
                        this.setMemoria(intero);
                    }// fine del blocco if

                } /* fine del blocco if */

            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
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

        try { // prova ad eseguire il codice
            /* effettua la conversione */
            if (valoreVideo instanceof String) {
                Integer.decode((String)valoreVideo);

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
        Integer valoreCovertito = 0;
        boolean continua;
        String testo;

        try { // prova ad eseguire il codice

            continua = (valore != null);

            if (continua) {
                if (valore instanceof Integer) {
                    valoreCovertito = (Integer)valore;
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            if (continua) {
                if (valore instanceof String) {
                    testo = valore.toString();

                    try { // prova ad eseguire il codice
                        valoreCovertito = Integer.decode(testo);
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

            nf = NumberFormat.getIntegerInstance();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return nf;
    }


    /**
     * Ritorna il valore a livello di memoria
     * per il campo editato nella cella.
     * <p/>
     *
     * @param stringaIn il valore in uscita dall'editor in formato stringa
     *
     * @return il valore convertito a livello memoria
     */
    protected Object getValoreEditor(String stringaIn) {
        /* variabili e costanti locali di lavoro */
        Object valoreOut = null;

        try { // prova ad eseguire il codice
            valoreOut = Libreria.getInt(stringaIn);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valoreOut;
    }


}// fine della classe
