/**
 * Title:     CampoEvent
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      25-feb-2004
 */
package it.algos.base.evento;

import java.util.EventObject;

/**
 * Eventi generici.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 25-feb-2004 ore 14.01.09
 */
public abstract class BaseEvent extends EventObject {

    /**
     * Costruttore completo con parametri.
     *
     * @param oggetto sorgente di questo evento
     */
    public BaseEvent(Object oggetto) {
        /* rimanda al costruttore della superclasse */
        super(oggetto);
    }

}// fine della classe
