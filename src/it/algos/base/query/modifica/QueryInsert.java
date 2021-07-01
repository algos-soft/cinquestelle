/**
 * Title:     QueryInsert
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      26-nov-2004
 */
package it.algos.base.query.modifica;

import it.algos.base.modulo.Modulo;
import it.algos.base.query.Query;

/**
 * Implementazione concreta di una Query per inserimento di records
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 26-nov-2004 ore 9.43.32
 */
public final class QueryInsert extends QueryModifica {

    /**
     * Costruttore completo
     * <p/>
     *
     * @param modulo il modulo di riferimento.
     */
    public QueryInsert(Modulo modulo) {
        /* rimanda al costruttore della superclasse */
        super(modulo, Query.TIPO_INSERT);
    }// fine del metodo costruttore completo

}// fine della classe
