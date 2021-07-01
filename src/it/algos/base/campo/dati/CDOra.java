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
import it.algos.base.campo.formatter.display.DFOra;
import it.algos.base.campo.formatter.edit.EFOra;
import it.algos.base.campo.tipodati.tipoarchivio.TAOra;
import it.algos.base.campo.tipodati.tipoarchivio.TipoArchivio;
import it.algos.base.campo.tipodati.tipomemoria.TMOra;
import it.algos.base.campo.tipodati.tipomemoria.TipoMemoria;
import it.algos.base.campo.tipodati.tipovideo.TVTesto;
import it.algos.base.campo.tipodati.tipovideo.TipoVideo;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.LibTesto;
import it.algos.base.libreria.Libreria;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.tavola.renderer.RendererOra;
import it.algos.base.validatore.ValidatoreFactory;

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
 * @version 1.0  /  23 ottobre 2003 ore 12.21
 */
public final class CDOra extends CDFormat {

    private static final TipoArchivio TIPO_ARCHIVIO = TAOra.getIstanza();

    private static final TipoMemoria TIPO_MEMORIA = TMOra.getIstanza();

    private static final TipoVideo TIPO_VIDEO = TVTesto.getIstanza();


    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo utilizzando eventuali valori di default
     */
    public CDOra() {
        /* rimanda al costruttore di questa classe */
        this(null);
    } /* fine del metodo costruttore base */


    /**
     * Costruttore completo
     *
     * @param unCampoParente campo 'contenitore' di questo oggetto
     */
    public CDOra(Campo unCampoParente) {
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
            this.setIcona(ICONA_CAMPO_ORA);

            /* regola l'uso di default del range di ricerca */
            this.setUsaRangeRicerca(true);

            /* assegna i formattatori di default */
            this.setDisplayFormatter(new DFOra());
            this.setEditFormatter(new EFOra());

            /* regola il renderer per la lista */
            this.setRenderer(new RendererOra(this.getCampoParente()));

            /* assegna il validatore di default */
            this.setValidatore(ValidatoreFactory.ora());

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
     * Ritorna true se il campo e' ora.
     *
     * @return true se è campo ora
     */
    public boolean isOra() {
        return true;
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
        return Progetto.getShortOraFormat();
    }


    /**
     * converte da memoria a video
     */
    public void memoriaVideo() {
        /* variabili e costanti locali di lavoro */
        Object memoria;
        int intMem;
        String stringa;
        boolean continua;
        String unaStringa = "";
        Integer unaOra = null;
        Object ogg;

        /* effettua la (eventuale) conversione */
        try { // prova ad eseguire il codice

            memoria = this.getMemoria();
            intMem = Libreria.getInt(memoria);
            stringa = this.converti(intMem);
            this.setVideo(stringa);

//            /* invoca il metodo sovrascritto della superclasse */
//            super.memoriaVideo();

//            /* recupera il valore della memoria */
//            ogg = getMemoria();
//            continua = (ogg != null);
//
//            if (continua) {
//                if (ogg instanceof Integer) {
//                    unaOra = (Integer)getMemoria();
//                }// fine del blocco if
//                continua = (unaOra != null);
//            }// fine del blocco if
//
//            if (continua) {
//                unaStringa = unaOra.toString();
//                continua = (Lib.Testo.isValida(unaStringa));
//            }// fine del blocco if
//
//            /* registra il valore del video iniziale */
//            if (continua) {
//                this.setVideo(unaStringa);
//            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * converte da video a memoria
     */
    public void videoMemoria() {
        /* variabili e costanti locali di lavoro */
        Object video;
        String strVideo;
        int valMem;

        try {    // prova ad eseguire il codice

            video = this.getVideo();
            strVideo = Libreria.getString(video);
            valMem = this.converti(strVideo);
            this.setMemoria(valMem);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    } /* fine del metodo videoMemoria */


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

        try { // prova ad eseguire il codice
            int valInt = Libreria.getInt(valore);
            stringa = CDOra.converti(valInt);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return stringa;
    }


    /**
     * Converte da stringa a valore intero.
     * <p/>
     *
     * @param stringaIn da covertire
     *
     * @return intero
     */
    public static int converti(String stringaIn) {
        /* variabili e costanti locali di lavoro */
        int valore = 0;
        String txtOra;
        String txtMin;
        int ora;
        int minuti;

        try { // prova ad eseguire il codice
            txtOra = stringaIn.substring(0, 2);
            txtMin = stringaIn.substring(3, 5);
            ora = Libreria.getInt(txtOra);
            minuti = Libreria.getInt(txtMin);

            ora *= (60 * 60);
            minuti *= 60;

            valore = ora + minuti;

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valore;
    }


    /**
     * Converte da valore intero a stringa formattata.
     * <p/>
     *
     * @param valore intero da covertire
     *
     * @return stringa
     */
    public static String converti(int valore) {
        /* variabili e costanti locali di lavoro */
        String stringa = "";

        try {    // prova ad eseguire il codice

            valore /= 60;

            int minuti = valore % 60;
            int ore = (int)(valore / 60);

            String strMin = Lib.Testo.getStringa(minuti);
            strMin = Lib.Testo.pad(strMin, '0', 2, LibTesto.Posizione.inizio);

            String strOra = Lib.Testo.getStringa(ore);
            strOra = Lib.Testo.pad(strOra, '0', 2, LibTesto.Posizione.inizio);

            stringa = strOra + ":" + strMin;

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return stringa;
    }


}// fine della classe