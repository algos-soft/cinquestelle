/**
 * Title:     Connettore
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      24-dic-2006
 */
package it.algos.base.database.connessione;

import it.algos.base.modulo.Modulo;

/**
 * Interfaccia implementata da oggetti che mantengono
 * una connessione al database.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 24-dic-2006 ore 12.09.18
 */
public interface Connettore {


    /**
     * Ritorna il modulo di riferimento dell'oggetto.
     * <p/>
     *
     * @return la connessione mantenuta da questo oggetto
     */
    public abstract Modulo getModulo();


    /**
     * Ritorna la connessione mantenuta dall'oggetto.
     * <p/>
     *
     * @return la connessione mantenuta da questo oggetto
     */
    public abstract Connessione getConnessione();


}// fine della interfaccia
