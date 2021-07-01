/**
 * Title:        CLElenco.java
 * Package:      it.algos.base.campo.logica
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 22 luglio 2003 alle 19.48
 */

package it.algos.base.campo.logica;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.dati.CampoDati;
import it.algos.base.campo.db.CDBLinkato;
import it.algos.base.errore.Errore;

import java.util.ArrayList;

/**
 * Questa classe concreta e' responsabile di: <br>
 * A - Regolare le funzionalita dei rapporti interni dei Campi <br>
 * B - Viene usata insieme alla classe CDElenco <br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  22 luglio 2003 ore 19.48
 */
public final class CLElenco extends CLBase {

    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo utilizzando eventuali valori di default
     */
    public CLElenco() {
        /** rimanda al costruttore di questa classe */
        this(null);
    } /* fine del metodo costruttore base */


    /**
     * Costruttore completo
     *
     * @param unCampoParente campo 'contenitore' di questo oggetto
     */
    public CLElenco(Campo unCampoParente) {
        /** rimanda al costruttore della superclasse */
        super(unCampoParente);

        /** regolazioni iniziali di riferimenti e variabili */
        try {                                   // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore completo */


    /**
     * Regolazioni iniziali di riferimenti e variabili <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
    } /* fine del metodo inizia */


    /**
     * Regolazioni iniziali <i>una tantum</i>.
     * </p>
     * Metodo chiamato dalla classe che crea questo oggetto <br>
     * Viene eseguito una sola volta <br>
     */
    public void inizializza() {

        /* invoca il metodo sovrascritto della superclasse */
        super.inizializza();

    } /* fine del metodo */


    /**
     * Regolazioni di avvio, ogni volta che questo oggetto deve 'ripartire', per
     * essere sicuri che sia 'pulito' <br>
     * Metodo chiamato da altre classi <br>
     * Viene eseguito tutte le volte che necessita  <br>
     */
    public void avvia() {
        /** invoca il metodo sovrascritto della superclasse */
        super.avvia();
    } /* fine del metodo */


    /**
     * carica dal database una lista di valori e la mette nel modello dati
     *
     * @deprecated
     */
    private void caricaLista() {
        /** variabili e costanti locali di lavoro */
        ArrayList unaLista = null;
        CampoDati unCampoDati = null;
        CDBLinkato unCampoDBLinkato = null;
        //@todo da cancellare
        try {    // prova ad eseguire il codice
            /* recupera il campo DB specializzato */
            unCampoDBLinkato = (CDBLinkato)unCampoParente.getCampoDB();

            /* recupera la lista dal campo DB */
            unaLista = unCampoDBLinkato.caricaLista();

            /* recupera il campo dati */
            unCampoDati = unCampoParente.getCampoDati();

            /* registra i valori nel modello dei dati del campo */
            if (unaLista != null) {
                unCampoDati.setValoriInterni(unaLista);
//                unCampoDatiElenco.regolaElementiAggiuntivi();
            } /* fine del blocco if */

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

    } /* fine del metodo */
}// fine della classe

