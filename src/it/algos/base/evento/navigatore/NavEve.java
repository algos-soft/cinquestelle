/**
 * Copyright(c): 2006
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 6 feb 2006
 */

package it.algos.base.evento.navigatore;

import it.algos.base.evento.BaseEvent;
import it.algos.base.navigatore.Navigatore;

/**
 * Superclasse per gli eventi di un navigatore.
 * </p>
 * Serve per mantenere le variabili
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 6 feb 2006
 */
public abstract class NavEve extends BaseEvent {

    /**
     * riferimento al navigatore sorgente di questo evento
     */
    private Navigatore navigatore;


    /**
     * Costruttore completo con parametri.
     *
     * @param navigatore sorgente di questo evento
     */
    public NavEve(Navigatore navigatore) {
        /* rimanda al costruttore della superclasse */
        super(navigatore);

        /* regola le variabili di istanza coi parametri */
        this.setNavigatore(navigatore);
    }


    public Navigatore getNavigatore() {
        return navigatore;
    }


    private void setNavigatore(Navigatore navigatore) {
        this.navigatore = navigatore;
    }


} // fine della classe
