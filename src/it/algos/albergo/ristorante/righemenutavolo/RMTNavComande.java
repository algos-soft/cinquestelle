/**
 * Title:     RMTNavComande
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      23-gen-2007
 */
package it.algos.albergo.ristorante.righemenutavolo;

import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.NavigatoreNN;

/**
 * Navigatore inserito nel campo del menu.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 23-gen-2007 ore 9.44.32
 */
public final class RMTNavComande extends NavigatoreNN {


    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param moduloMaster modulo di riferimento
     * @param chiaveNavMaster nome chiave del navigatore Master (nel modulo Master)
     * @param moduloSlave modulo per il navigatore slave
     * @param chiaveNavSlave nome chiave del navigatore Slave (nel modulo Slave)
     */
    public RMTNavComande(Modulo moduloMaster,
                         String chiaveNavMaster,
                         Modulo moduloSlave,
                         String chiaveNavSlave) {

        /* rimanda al costruttore della superclasse */
        super(moduloMaster, chiaveNavMaster, moduloSlave, chiaveNavSlave);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        this.setNomeChiave(RMT.NAV_RMT_RMO);
        this.setUsaPannelloUnico(false);
        this.setOrizzontale(false);
        this.setUsaPannelloUnico(false);
    }// fine del metodo inizia


    /**
     * Regolazioni dinamiche dell'oggetto.
     * <p/>
     * Regola le caratteristiche dinamiche in base alla impostazione
     * corrente delle caratteristiche statiche <br>
     * Metodo invocato dalla classe che crea questo oggetto (di norma) <br>
     * Viene eseguito una sola volta <br>
     * Sovrascritto nelle sottoclassi <br>
     */
    public void inizializza() {
        /* variabili e costanti locali di lavoro */

        super.inizializza();

        try { // prova ad eseguire il codice
            ;
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    public void avvia() {
        super.avvia();
    }


    /**
     * Sincronizza lo stato del Navigatore.
     * <p/>
     * Controlla lo stato di tutti i suoi componenti <br>
     * Elabora la sua business logic <br>
     * Regola la GUI di ogni conseguenza (menu, bottoni, ecc) <br>
     * Invoca il metodo sovrascritto della superclasse <br>
     */
    public void sincronizza() {
        super.sincronizza();
    }


}// fine della classe
