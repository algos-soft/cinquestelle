/**
 * Title:     FinestraNavigatore
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      21-apr-2004
 */
package it.algos.base.finestra;

import it.algos.base.azione.Azione;
import it.algos.base.errore.Errore;
import it.algos.base.menu.barra.MenuBarraNavigatore;
import it.algos.base.portale.Portale;

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
 * @version 1.0    / 21-apr-2004 ore 15.56.31
 */
public final class FinestraPortale extends FinestraBase {

    /**
     * portale che gestisce questa finestra
     */
    private Portale portale = null;


    /**
     * Costruttore base senza parametri. <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo <br>
     * Utilizza eventuali valori di default <br>
     */
    public FinestraPortale() {
        /* rimanda al costruttore di questa classe */
        this(null);
    }// fine del metodo costruttore base


    /**
     * Costruttore completo con parametri. <br>
     *
     * @param portale riferimento al portale proprietario
     */
    public FinestraPortale(Portale portale) {
        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza coi parametri */
        this.setPortale(portale);
        this.setPannelloPrincipale(portale);
        super.setNavigatore(portale.getNavigatore());

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
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
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


    /**
     * Crea la barra di menu della finestra lista.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     */
    protected void creaBarraMenu() {
        /* variabili e costanti locali di lavoro */
        Portale portale;
        LinkedHashMap<String, Azione> azioni;

        try {    // prova ad eseguire il codice
            portale = this.getPortale();
            if (portale != null) {
                azioni = portale.getAzioni();
                super.menuBarra = new MenuBarraNavigatore(azioni);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    }


    public Portale getPortale() {
        return portale;
    }


    public void setPortale(Portale portale) {
        this.portale = portale;
    }

}// fine della classe
