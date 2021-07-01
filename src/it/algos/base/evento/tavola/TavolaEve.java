/**
 * Title:     TavolaEve
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      25.02.2006
 */
package it.algos.base.evento.tavola;

import it.algos.base.evento.BaseEvent;
import it.algos.base.tavola.Tavola;

/**
 * Superclasse per gli eventi di una tavola.
 * </p>
 * Serve per mantenere le variabili
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 25-feb-2004 ore 14.01.09
 */
public abstract class TavolaEve extends BaseEvent {

    /**
     * riferimento alla sorgente di questo evento
     */
    private Tavola tavola;


    /**
     * Costruttore completo con parametri.
     */
    public TavolaEve() {
        this(null);
    }


    /**
     * Costruttore completo con parametri.
     *
     * @param tavola sorgente di questo evento
     */
    public TavolaEve(Tavola tavola) {
        /* rimanda al costruttore della superclasse */
        super(tavola);

        /* regola le variabili di istanza coi parametri */
        this.setTavola(tavola);
    }


    public Tavola getTavola() {
        return tavola;
    }


    private void setTavola(Tavola tavola) {
        this.tavola = tavola;
    }
}// fine della classe
