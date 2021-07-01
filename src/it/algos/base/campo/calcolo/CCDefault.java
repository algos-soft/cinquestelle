/**
 * Title:     CCDefault
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      6-mar-2004
 */
package it.algos.base.campo.calcolo;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;

/**
 * Specializzazione del Campo per le funzionalita' di calcolo .
 * </p>
 * Questa classe concreta: <ul>
 * <li> Fornisce una implementazione alla classe Base </li>
 * <li> Non implementa il metodo setValoreMemoria() della superclasse </li>
 * </ul>
 *
 * @author Alberto Colombo, Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 6-mar-2004 ore 12.10.10
 */
public final class CCDefault extends CCBase {

    /**
     * Costruttore base senza parametri. <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo <br>
     * Utilizza eventuali valori di default <br>
     */
    public CCDefault() {
        /* rimanda al costruttore di questa classe */
        this(null);
    }// fine del metodo costruttore base


    /**
     * Costruttore completo con parametri.
     *
     * @param unCampoParente campo 'contenitore' di questo oggetto <br>
     */
    public CCDefault(Campo unCampoParente) {
        /* rimanda al costruttore della superclasse */
        super(unCampoParente);

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
     * </p>
     * Metodo chiamato dalla classe che crea questo oggetto <br>
     * Viene eseguito una sola volta <br>
     */
    public void inizializza() {
        super.inizializza();
    } /* fine del metodo */

}// fine della classe
