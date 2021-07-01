/**
 * Title:        CDData.java
 * Package:      it.algos.base.campo.dati
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Ceresa, Colombo, Valbonesi
 * @version 1.0  /
 * Creato:       il 23 ottobre 2003 alle 12.16
 */

package it.algos.base.campo.dati;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.formatter.display.DFData;
import it.algos.base.campo.formatter.edit.EFData;
import it.algos.base.campo.tipodati.tipoarchivio.TAData;
import it.algos.base.campo.tipodati.tipoarchivio.TipoArchivio;
import it.algos.base.campo.tipodati.tipomemoria.TMData;
import it.algos.base.campo.tipodati.tipomemoria.TipoMemoria;
import it.algos.base.campo.tipodati.tipovideo.TVTesto;
import it.algos.base.campo.tipodati.tipovideo.TipoVideo;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.tavola.renderer.RendererData;
import it.algos.base.validatore.ValidatoreFactory;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Classe concreta per implementare un oggetto da CDBase;
 * <p/>
 * Questa classe concreta e' responsabile di: <br>
 * A - Implementare i metodi astratti della superclasse per regolare le
 * conversioni tra i vari tipi di dati <br>
 *
 * @author Guido Andrea Ceresa
 * @author gac
 * @version 1.0  /  23 ottobre 2003 ore 12.16
 */
public final class CDData extends CDFormat {

    private static final TipoArchivio TIPO_ARCHIVIO = TAData.getIstanza();

    private static final TipoMemoria TIPO_MEMORIA = TMData.getIstanza();

    private static final TipoVideo TIPO_VIDEO = TVTesto.getIstanza();

    /**
     * Costruttore completo
     *
     * @param unCampoParente campo 'contenitore' di questo oggetto
     */
    public CDData(Campo unCampoParente) {
        /* rimanda al costruttore della superclasse */
        super(unCampoParente, TIPO_ARCHIVIO, TIPO_MEMORIA, TIPO_VIDEO);

        /* regolazioni iniziali di riferimenti e variabili */
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
        /* variabili e costanti locali di lavoro */

        try { // prova ad eseguire il codice

            /* assegna l'icona specifica per il tipo di campo */
            this.setIcona(ICONA_CAMPO_DATE);

            /* regola l'uso di default del range di ricerca */
            this.setUsaRangeRicerca(true);

            /* assegna i formattatori di default */
            this.setDisplayFormatter(new DFData());
            this.setEditFormatter(new EFData());

            /* regola il renderer per la lista */
            this.setRenderer(new RendererData(this.getCampoParente()));

            /* assegna il validatore di default - dopo il 01-01-1900 */
            this.setValidatore(ValidatoreFactory.dateDopo(Lib.Data.creaData(1, 1, 1900)));

            /* aggiunge gli operatori di ricerca disponibili */
            this.clearOperatoriRicerca();
            this.addOperatoreRicerca(Filtro.Op.UGUALE);
            this.addOperatoreRicerca(Filtro.Op.MAGGIORE);
            this.addOperatoreRicerca(Filtro.Op.MAGGIORE_UGUALE);
            this.addOperatoreRicerca(Filtro.Op.MINORE);
            this.addOperatoreRicerca(Filtro.Op.MINORE_UGUALE);


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

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
     * .
     * <p/>
     */
    public void avvia() {
        super.avvia();
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
//        return new SimpleDateFormat("dd-MM-yyyy");
        return Progetto.getDateFormat();
    }

    /**
     * converte da memoria a video
     */
    public void memoriaVideo() {

        /* Se il valore Memoria e' vuoto,
         * assegna a Video il proprio valore vuoto,
         * altrimenti trasporta il valore */
        if (this.isValoreMemoriaVuoto()) {
            this.setVideo(this.getValoreVideoVuoto());
        } else {

            super.memoriaVideo();

        } /* fine del blocco if */

//        super.memoriaVideo();

    }

    /**
     * converte da video a memoria
     */
    public void videoMemoria() {
        /* variabili e costanti locali di lavoro */
        String stringa;
        Date data = null;
        SimpleDateFormat format;
        Object oggetto;

        try { // prova ad eseguire il codice

            /* cerca di estrarre una data */
            Object video = this.getVideo();
            if (video instanceof String) {
                stringa = (String)video;
                format = Progetto.getShortDateFormat();
                try { // prova ad eseguire il codice
                    oggetto = format.parseObject(stringa);
                    if ((oggetto != null) && (oggetto instanceof Date)) {
                        data = (Date)oggetto;
                    }// fine del blocco if
                } catch (Exception unErrore) { // intercetta l'errore
                }// fine del blocco try-catch
            }// fine del blocco if

            /* se riuscito la registra in memoria, altrimenti
             * rinvia alla superclasse */
            if (data != null) {
                this.setMemoria(data);
            } else {
                super.videoMemoria();
            }// fine del blocco if-else


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


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
     *         data vuota se non accettabile
     */
    public Object convertiMemoria(Object valore) {
        /* variabili e costanti locali di lavoro */
        Date valoreCovertito = Lib.Data.getVuota();
        boolean continua;
        String testo;

        try { // prova ad eseguire il codice

            continua = (valore != null);

            if (continua) {
                if (valore instanceof Date) {
                    valoreCovertito = (Date)valore;
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            if (continua) {
                if (valore instanceof String) {
                    testo = valore.toString();

                    try { // prova ad eseguire il codice
                        valoreCovertito = Libreria.getDate(testo);
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
     * Ritorna true se il campo e' data.
     *
     * @return true se è campo data
     */
    public boolean isData() {
        return true;
    }

    /**
     * Assegna il formato al campo data
     * <p>
     * @param pattern come da SimpleDateFormat
     */
    public void setDateFormat(String pattern){
        super.setFormat(new SimpleDateFormat(pattern));
    }



}// fine della classe

