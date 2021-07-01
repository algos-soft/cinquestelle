/**
 * Title:     ListaEve
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      25-feb-2004
 */
package it.algos.base.evento.lista;

import it.algos.base.evento.BaseEvent;
import it.algos.base.lista.Lista;

/**
 * Superclasse per gli eventi di una lista.
 * </p>
 * Serve per mantenere le variabili
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 25-feb-2004 ore 14.01.09
 */
public abstract class ListaEve extends BaseEvent {

    /**
     * riferimento alla lista sorgente di questo evento
     */
    private Lista lista;


    /**
     * Costruttore completo con parametri.
     *
     * @param lista sorgente di questo evento
     */
    public ListaEve(Lista lista) {
        /* rimanda al costruttore della superclasse */
        super(lista);

        /* regola le variabili di istanza coi parametri */
        this.setLista(lista);
    }


    public Lista getLista() {
        return lista;
    }


    private void setLista(Lista lista) {
        this.lista = lista;
    }
}// fine della classe
