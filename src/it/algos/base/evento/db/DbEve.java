/**
 * Title:     CampoEvent
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      25-feb-2004
 */
package it.algos.base.evento.db;

import it.algos.base.evento.BaseEvent;

/**
 * Eventi generici di un database.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 25-feb-2004 ore 14.01.09
 */
public class DbEve extends BaseEvent {


    /**
     * Costruttore completo con parametri.
     */
    public DbEve(Object oggetto) {
        /* rimanda al costruttore della superclasse */
        super(oggetto);
    }


}// fine della classe
