/**
 * Title:     SelezioneModificataEve
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      23-mar-2006
 */
package it.algos.base.evento.listasingola;

import it.algos.base.listasingola.ListaSingola;

/**
 * Eventi di una lista che ha modificato il suo stato di selezione.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 25-feb-2004 ore 14.01.09
 */
public final class ListaSelModEve extends ListaSingolaEve {


    /**
     * Costruttore completo con parametri.
     */
    public ListaSelModEve(ListaSingola lista) {
        /* rimanda al costruttore della superclasse */
        super(lista);
    }


}// fine della classe
