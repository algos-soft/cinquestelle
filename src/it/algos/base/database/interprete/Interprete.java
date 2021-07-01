/**
 * Title:     Interprete
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      27-ott-2004
 */
package it.algos.base.database.interprete;

import it.algos.base.database.Db;

/**
 * Interprete di comandi per il database.
 * </p>
 * Un Interprete e' delegato a trasformare gli oggetti astratti
 * (query, filtri, ecc...) in comandi specifici per il database.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 27-ott-2004 ore 10.21.59
 */
public interface Interprete {

    /**
     * Ritorna il database proprietario di questo Interprete
     * <p/>
     *
     * @return il database proprietario
     */
    public abstract Db getDatabase();


}// fine della interfaccia
