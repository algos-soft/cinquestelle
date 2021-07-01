/**
 * Title:     ListaEve
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      25-feb-2004
 */
package it.algos.base.evento.lista;

import it.algos.base.lista.Lista;

/**
 * Eventi di una lista che ha modificato il suo stato di selezione.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 25-feb-2004 ore 14.01.09
 */
public final class ListaGenEve extends ListaEve {


    /**
     * Costruttore completo con parametri.
     */
    public ListaGenEve(Lista lista) {
        /* rimanda al costruttore della superclasse */
        super(lista);
    }


}// fine della classe
