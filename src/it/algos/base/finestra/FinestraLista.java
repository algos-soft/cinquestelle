/**
 * Title:     FinestraLista
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      19-mar-2004
 */
package it.algos.base.finestra;

import it.algos.base.azione.Azione;
import it.algos.base.errore.Errore;
import it.algos.base.menu.barra.MenuBarraLista;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.portale.Portale;
import it.algos.base.pref.Pref;

import java.util.LinkedHashMap;

/**
 * //@TODO DESCRIZIONE SINTETICA DELLA CLASSE .
 * </p>
 * Questa classe: <ul>
 * <li> </li>
 * <li> </li>
 * </ul>
 *
 * @author Alberto Colombo, Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 19-mar-2004 ore 12.30.15
 */
public final class FinestraLista extends FinestraBase {

    /**
     * voce di default (di solito il modulo specifico sovrascrive questo attributo)
     */
    private static final String TITOLO = "Finestra della lista";


    /**
     * Costruttore base senza parametri. <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo <br>
     * Utilizza eventuali valori di default <br>
     */
    public FinestraLista() {
        /* rimanda al costruttore di questa classe */
        this(null);
    }// fine del metodo costruttore base


    /**
     * Costruttore completo con parametri. <br>
     *
     * @param unNavigatore navigatore che gestisce questo pannello
     */
    public FinestraLista(Navigatore unNavigatore) {
        /* rimanda al costruttore della superclasse */
        super(unNavigatore);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni immediate di riferimenti e variabili. <br>
     * Metodo chiamato direttamente dal costruttore <br>
     * <p/>
     * Regola l'attributo ridimensionabile in base alle preferenze <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */
        boolean ridimensionabile;

        try {    // prova ad eseguire il codice

            ridimensionabile = Pref.GUI.ridimensionabile.is();
            super.setResizable(ridimensionabile);

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }// fine del metodo inizia


    /**
     * Regolazioni iniziali <i>una tantum</i>.
     * <p/>
     * Metodo chiamato dalla classe che crea questo oggetto dopo che sono
     * stati regolati dalla sottoclasse i parametri indispensabili <br>
     * Viene eseguito una sola volta <br>
     * <p/>
     * Chiede al Modulo la lista di eventuali altri moduli e tabelle <br>
     * Regola i menu <br>
     * Cancella gli eventuali menu non utilizzati <br>
     */
    public void inizializza() {
        /* invoca il metodo sovrascritto della superclasse */
        super.inizializza();

        /* crea i menu dei moduli e delle tabelle */
        super.creaMenuModuli();

        /* regola (eventualmente cancellandoli) i menu dei moduli e delle tabelle */
        super.regolaMenuModuli();
    }


    /**
     * Crea la barra di menu della finestra lista.
     * <p/>
     */
    protected void creaBarraMenu() {
        /* variabili e costanti locali di lavoro */
        Navigatore nav;
        Portale portale;
        LinkedHashMap<String, Azione> azioni;

        try {    // prova ad eseguire il codice
            nav = this.getNavigatore();
            portale = nav.getPortaleLista();
            if (portale != null) {
                azioni = portale.getAzioni();
                super.menuBarra = new MenuBarraLista(azioni);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    }


    /**
     * Regolazioni di ri-avvio.
     * <p/>
     * Metodo chiamato da altre classi ogni volta che questo oggetto deve
     * <i>ripartire</i>, per essere sicuri che sia <i>pulito</i> <br>
     * Viene eseguito tutte le volte che necessita <br>
     */
    public void avvia() {
        /* invoca il metodo sovrascritto della superclasse */
        super.avvia();
    }// fine del metodo avvia

}// fine della classe
