/**
 * Copyright: Copyright (c) 2005
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      24-ago-2005
 */
package it.algos.base.campo.dati;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.tipodati.tipoarchivio.TipoArchivio;
import it.algos.base.campo.tipodati.tipomemoria.TipoMemoria;
import it.algos.base.campo.tipodati.tipovideo.TipoVideo;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.tavola.renderer.RendererFormat;

import javax.swing.JFormattedTextField;
import javax.swing.text.DefaultFormatter;
import java.text.Format;

/**
 * Intercetta i metodi di regolazione del format per il campo testo a video.
 * </p>
 * Questa classe: <ul>
 * <li> </li>
 * <li> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 24-ago-2005 ore 17.18.57
 */
public abstract class CDFormat extends CDBase implements CampoFormat {

    /**
     * Oggetto delegato a convertire il valore del campo da Object
     * a stringa localizzata e viceversa.<br>
     * Utilizzato nei passaggi dei valori da Memoria a Video
     * in entrambe le direzioni.
     */
    private Format format;

    /**
     * Formattatore durante l'editing
     */
    JFormattedTextField.AbstractFormatter editFormatter = null;

    /**
     * Formattatore per la visualizzazione
     */
    JFormattedTextField.AbstractFormatter displayFormatter = null;


    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo utilizzando eventuali valori di default
     */
    public CDFormat() {
        /* rimanda al costruttore di questa classe */
        this(null, null, null, null);
    } /* fine del metodo costruttore base */


    /**
     * Costruttore con solo campo parente <br>
     * Rimanda al costruttore completo utilizzando eventuali valori di default
     *
     * @param unCampoParente campo 'contenitore' di questo oggetto
     */
    public CDFormat(Campo unCampoParente) {
        /* rimanda al costruttore di questa classe */
        this(unCampoParente, null, null, null);
    } /* fine del metodo costruttore base */


    /**
     * Costruttore completo
     *
     * @param unCampoParente campo 'contenitore' di questo oggetto
     * @param unTipoArchivio il tipo di dati per la variabile Archivio
     * @param tipoMemoria il tipo di dati per la variabile Memoria
     * (interfaccia TipoMemoria)
     * @param tipoVideo il tipo di dati per la variabile Video
     * (interfaccia TipoVideo)
     */
    public CDFormat(Campo unCampoParente,
                    TipoArchivio unTipoArchivio,
                    TipoMemoria tipoMemoria,
                    TipoVideo tipoVideo) {

        /* rimanda al costruttore della superclasse */
        super(unCampoParente, unTipoArchivio, tipoMemoria, tipoVideo);

        try {                                   // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) {           // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore completo */


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */
        Format formato;
        DefaultFormatter editForm;
        DefaultFormatter displayForm;

        try { // prova ad eseguire il codice

            /**
             * La sottoclasse crea un formato e lo registra
             */
            formato = this.createFormat();
            this.setFormat(formato);

            /* crea e registra i formattatori di default */
            editForm = new DefaultFormatter();
            editForm.setOverwriteMode(false);
            displayForm = new DefaultFormatter();

            this.setEditFormatter(editForm);
            this.setDisplayFormatter(displayForm);

            /* regola il renderer per la lista */
            this.setRenderer(new RendererFormat(this.getCampoParente()));

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


    }// fine del metodo inizia


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
            valoreOut = getFormat().parseObject(stringaIn);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valoreOut;
    }


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
        Object memoria;
        String stringa="";

        try {    // prova ad eseguire il codice

            /* recupera un eventuale format */
            format = this.getFormat();

            if (format != null) {
                /* recupera il valore della memoria */
                memoria = this.getMemoria();

                if (memoria != null) {

                    /* converte l'oggetto in stringa */
                    try { // prova ad eseguire il codice
                        stringa = format.format(memoria);
                    } catch (IllegalArgumentException unErrore) { // intercetta l'errore
                    }// fine del blocco try-catch

                    /* registra il valore del video */
                    this.setVideo(stringa);

                } /* fine del blocco if */

            } else {
                super.memoriaVideo();
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
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
        Format format;
        String video;
        Object oggetto = null;
        Object memoria;

        try {    // prova ad eseguire il codice

            /*
             * recupera il Format
             * - se esiste, effettua la conversione tramite il Format
             * - altrimenti, esegue nella superclasse
             */
            format = this.getFormat();

            if (format != null) {

                /* recupera il valore del video */
                video = (String)this.getVideo();

                if (Lib.Testo.isValida(video)) {

                    /* converte la stringa in oggetto */
                    try { // prova ad eseguire il codice
                        oggetto = format.parseObject(video);
//                        System.out.print("\n"+video);
//                        System.out.print("\n"+oggetto);
                    } catch (Exception unErrore) { // intercetta l'errore
                    }// fine del blocco try-catch

                    if (oggetto != null) {
                        memoria = oggetto;
                    } else {
                        memoria = this.getValoreMemoriaVuoto();
                    }// fine del blocco if-else

                    /* registra il valore in memoria */
                    this.setMemoria(memoria);

                } /* fine del blocco if */

            } else {
                super.videoMemoria();
            }// fine del blocco if-else

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    }


    /**
     * Crea un oggetto Format da assegnare al campo.
     * <p/>
     * Invocato dal ciclo Inizia.
     * Implementato nelle sottoclassi concrete.
     * Il metodo deve creare e ritornare il Format adeguato.
     * Se il metodo ritorna null non viene usato il format
     * e la conversione viene effettuata secondo le regole della superclasse
     *
     * @return l'oggetto Format
     */
    public Format createFormat() {
        return null;
    }


    /**
     * Trasforma un valore nella sua rappresentazione stringa.
     * <p/>
     * Se il campo ha un format, usa il format.
     * Se non ha un format, e il valore e' una stringa, ritorna la stringa
     *
     * @param valore da trasformare
     *
     * @return il valore rappresentato come stringa
     */
    public String format(Object valore) {
        /* variabili e costanti locali di lavoro */
        String stringa = "";
        Format formato;

        try { // prova ad eseguire il codice
            formato = this.getFormat();
            if (formato != null) {
                stringa = formato.format(valore);
            } else {
                if (valore instanceof String) {
                    stringa = (String)valore;
                }// fine del blocco if
            }// fine del blocco if-else
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return stringa;
    }


    /**
     * Ritorna il formattatore per l'editing.
     * <p/>
     *
     * @return il formattatore per l'editing
     */
    public JFormattedTextField.AbstractFormatter getEditFormatter() {
        return editFormatter;
    }


    protected void setEditFormatter(JFormattedTextField.AbstractFormatter editFormatter) {
        this.editFormatter = editFormatter;
    }


    /**
     * Ritorna il formattatore per il display.
     * <p/>
     *
     * @return il formattatore per il display
     */
    public JFormattedTextField.AbstractFormatter getDisplayFormatter() {
        return displayFormatter;
    }


    protected void setDisplayFormatter(JFormattedTextField.AbstractFormatter displayFormatter) {
        this.displayFormatter = displayFormatter;
    }


    /**
     * Ritorna il formattatore del campo
     * <p/>
     *
     * @return l'oggetto formattatore del campo
     */
    public Format getFormat() {
        return format;
    }


    /**
     * Assegna il formattatore del campo
     * <p>
     * @param format il formattatore
     */
    public void setFormat(Format format) {
        this.format = format;
    }



}// fine della classe
