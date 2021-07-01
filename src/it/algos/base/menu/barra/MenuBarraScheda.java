/**
 * Title:     MenuBarraLista
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      19-mar-2004
 */
package it.algos.base.menu.barra;

import it.algos.base.errore.Errore;
import it.algos.base.menu.menu.MenuArchivioScheda;
import it.algos.base.menu.menu.MenuStrumentiScheda;

import java.util.LinkedHashMap;

/**
 * Barra di menu associata ad una <code>FinestraScheda</code>.
 * </p>
 * Questa classe concreta: <ul>
 * <li> Costruisce i menu visibili in una <code>FinestraScheda</code> </li>
 * </ul>
 *
 * @author Alberto Colombo, Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 19-mar-2004 ore 16.34.23
 */
public final class MenuBarraScheda extends MenuBarraBase {

    /**
     * Costruttore base senza parametri. <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo <br>
     * Utilizza eventuali valori di default <br>
     */
    public MenuBarraScheda() {
        /* rimanda al costruttore di questa classe */
        this(null);
    }// fine del metodo costruttore base


    /**
     * Costruttore completo con parametri. <br>
     *
     * @param azioni collezione chiave-valore delle azioni
     */
    public MenuBarraScheda(LinkedHashMap azioni) {
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
     * Crea il menu archivio specifico della scheda.
     * <p/>
     * Aggiunge il menu alla barra menu <br>
     */
    protected void creaArchivio() {
        /* crea il menu moduli */
        this.menuArchivio = new MenuArchivioScheda(azioni);

        /* aggiunge il menu alla barra */
        super.aggiunge(menuArchivio);
    } /* fine del metodo */


    /**
     * Crea il menu strumenti specifico della scheda.
     * <p/>
     * Aggiunge il menu alla barra menu <br>
     */
    protected void creaStrumenti() {
        /* crea il menu moduli */
        this.menuStrumenti = new MenuStrumentiScheda(azioni);

        /* aggiunge il menu alla barra */
        super.aggiunge(menuStrumenti);
    } /* fine del metodo */

}// fine della classe
