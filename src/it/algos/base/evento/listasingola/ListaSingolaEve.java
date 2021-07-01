/**
 * Title:     ListaSingolaEve
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      23-mar-2006
 */
package it.algos.base.evento.listasingola;

import it.algos.base.evento.BaseEvent;
import it.algos.base.listasingola.ListaSingola;

/**
 * Superclasse per gli eventi di una lista singola.
 * </p>
 * Serve per mantenere le variabili
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 25-feb-2004 ore 14.01.09
 */
public abstract class ListaSingolaEve extends BaseEvent {

    /**
     * riferimento alla lista sorgente di questo evento
     */
    private ListaSingola lista;


    /**
     * Costruttore completo con parametri.
     *
     * @param lista sorgente di questo evento
     */
    public ListaSingolaEve(ListaSingola lista) {
        /* rimanda al costruttore della superclasse */
        super(lista);

        /* regola le variabili di istanza coi parametri */
        this.setLista(lista);
    }


    public ListaSingola getLista() {
        return lista;
    }


    private void setLista(ListaSingola lista) {
        this.lista = lista;
    }
}// fine della classe
