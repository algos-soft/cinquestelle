/**
 * Title:     MenuBarraNavigatore
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      21-lug-2004
 */
package it.algos.base.menu.barra;

import it.algos.base.errore.Errore;
import it.algos.base.menu.menu.MenuArchivioNavigatore;

import java.util.HashMap;

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
 * @version 1.0    / 21-lug-2004 ore 10.03.40
 */
public final class MenuBarraNavigatore extends MenuBarraBase {

    /**
     * Costruttore base senza parametri. <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo <br>
     * Utilizza eventuali valori di default <br>
     */
    public MenuBarraNavigatore() {
        /* rimanda al costruttore di questa classe */
        this(null);
    }// fine del metodo costruttore base


    /**
     * Costruttore completo con parametri. <br>
     *
     * @param azioni collezione chiave-valore delle azioni
     */
    public MenuBarraNavigatore(HashMap azioni) {
        /* rimanda al costruttore della superclasse */
        super(azioni);

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
     * Crea il menu archivio specifico della lista.
     * <p/>
     * Aggiunge il menu alla barra menu <br>
     */
    protected void creaArchivio() {
        /* crea il menu moduli */
        this.menuArchivio = new MenuArchivioNavigatore(azioni);

        /* aggiunge il menu alla barra */
        super.aggiunge(menuArchivio);
    } /* fine del metodo */

}// fine della classe
