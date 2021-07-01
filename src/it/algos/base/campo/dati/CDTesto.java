/**
 * Title:        CDTesto.java
 * Package:      it.algos.base.campo.dati
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Ceresa, Colombo, Valbonesi
 * @version 1.0  /
 * Creato:       il 23 ottobre 2003 alle 11.40
 */

package it.algos.base.campo.dati;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.tipodati.tipoarchivio.TATesto;
import it.algos.base.campo.tipodati.tipoarchivio.TipoArchivio;
import it.algos.base.campo.tipodati.tipomemoria.TMTesto;
import it.algos.base.campo.tipodati.tipomemoria.TipoMemoria;
import it.algos.base.campo.tipodati.tipovideo.TVTesto;
import it.algos.base.campo.tipodati.tipovideo.TipoVideo;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.tavola.renderer.RendererTesto;
import it.algos.base.validatore.ValidatoreFactory;

/**
 * Classe concreta per implementare un oggetto da CDBase;
 * <p/>
 * Questa classe concreta e' responsabile di: <br>
 * A - Implementare i metodi astratti della superclasse per regolare le
 * conversioni tra i vari tipi di dati <br>
 */
public class CDTesto extends CDFormat {

    private static final TipoArchivio TIPO_ARCHIVIO = TATesto.getIstanza();

    private static final TipoMemoria TIPO_MEMORIA = TMTesto.getIstanza();

    private static final TipoVideo TIPO_VIDEO = TVTesto.getIstanza();


    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo utilizzando eventuali valori di default
     */
    public CDTesto() {
        /** rimanda al costruttore di questa classe */
        this(null);
    } /* fine del metodo costruttore base */


    /**
     * Costruttore completo
     *
     * @param unCampoParente campo 'contenitore' di questo oggetto
     */
    public CDTesto(Campo unCampoParente) {
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
        this.setIcona(ICONA_CAMPO_TEXT);

        /* regola il renderer per la lista */
        this.setRenderer(new RendererTesto(this.getCampoParente()));

        /* assegna un validatore di default */
        this.setValidatore(ValidatoreFactory.testo());

        /* aggiunge gli operatori di ricerca disponibili */
        this.clearOperatoriRicerca();
        this.addOperatoreRicerca(Filtro.Op.COMINCIA);
        this.addOperatoreRicerca(Filtro.Op.CONTIENE);
        this.addOperatoreRicerca(Filtro.Op.FINISCE);
        this.addOperatoreRicerca(Filtro.Op.UGUALE);
        this.addOperatoreRicerca(Filtro.Op.MAGGIORE);
        this.addOperatoreRicerca(Filtro.Op.MAGGIORE_UGUALE);
        this.addOperatoreRicerca(Filtro.Op.MINORE);
        this.addOperatoreRicerca(Filtro.Op.MINORE_UGUALE);

        /* operatore di ricerca di default */
        this.setOperatoreRicercaDefault(Filtro.Op.CONTIENE);


    } /* fine del metodo inizia */


    /**
     * Ritorna un oggetto che rappresenta il valore del campo
     * per l'utilizzo in un filtro.
     * <p/>
     * Si usa per costruire un filtro rappresentante il valore
     * di memoria corrente del campo.<br>
     * In questa classe, depura la stringa da caratteri illegali
     *
     * @return il valore per l'utilizzo nel filtro
     */
    public Object getValoreFiltro() {
        /* variabili e costanti locali di lavoro */
        Object valore = null;
        String stringa = "";

        try { // prova ad eseguire il codice
            valore = super.getValoreFiltro();
            if (Lib.Testo.isValida(valore)) {
                stringa = (String)valore;
                stringa = stringa.replaceAll("\n", "");
                stringa = stringa.replaceAll("\r", "");
                stringa = stringa.replaceAll("\t", "");
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return stringa;
    }


    /**
     * Ritorna true se il campo e' di testo.
     *
     * @return true se è campo testo
     */
    public boolean isTesto() {
        return true;
    }


}// fine della classe