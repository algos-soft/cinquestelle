/**
 * Title:     AlberoEve
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      23-mar-2006
 */
package it.algos.base.evento.albero;

import it.algos.base.albero.Albero;
import it.algos.base.evento.BaseEvent;

/**
 * Superclasse per gli eventi di un albero.
 * </p>
 * Serve per mantenere le variabili
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 25-feb-2004 ore 14.01.09
 */
public abstract class AlberoEve extends BaseEvent {

    /**
     * riferimento all'albero sorgente di questo evento
     */
    private Albero albero;


    /**
     * Costruttore completo con parametri.
     *
     * @param albero sorgente di questo evento
     */
    public AlberoEve(Albero albero) {
        /* rimanda al costruttore della superclasse */
        super(albero);

        /* regola le variabili di istanza coi parametri */
        this.setAlbero(albero);
    }


    public Albero getAlbero() {
        return this.albero;
    }


    private void setAlbero(Albero albero) {
        this.albero = albero;
    }
}// fine della classe
