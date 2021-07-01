/**
 * Title:     MenuComposizione
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      20-mar-2004
 */
package it.algos.base.menu.menu;

import it.algos.base.errore.Errore;

import java.util.HashMap;

/**
 * Menu moduli.
 * </p>
 * Questa classe concreta: <ul>
 * <li> Costruisce il menu standard moduli vuoto </li>
 * <li> Viene regolato dalla classe <code>Navigatore</code> </li>
 * <li> Un riferimento a questo oggetto viene reso disponibile per
 * costruire le singole righe del menu </li>
 * </ul>
 *
 * @author Alberto Colombo, Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 20-mar-2004 ore 10.12.08
 */
public final class MenuModuli extends MenuBase {

    /**
     * voce del menu come viene visualizzato nella finestra
     */
    private static final String TITOLO = "Moduli";


    /**
     * Costruttore base senza parametri. <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo <br>
     * Utilizza eventuali valori di default <br>
     */
    public MenuModuli() {
        /* rimanda al costruttore di questa classe */
        this(null);
    }// fine del metodo costruttore base


    /**
     * Costruttore completo con parametri. <br>
     *
     * @param azioni collezione chiave-valore delle azioni
     */
    public MenuModuli(HashMap azioni) {
        /* rimanda al costruttore della superclasse */
        super(azioni, TITOLO);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni immediate di riferimenti e variabili.<p>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

    }// fine del metodo inizia

}// fine della classe
