/**
 * Copyright: Copyright (c) 2009
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      17-mag-2007
 */
package it.algos.albergo.evento;

import it.algos.base.evento.BaseEvent;
import it.algos.base.modulo.Modulo;

/**
 * Evento di tipo cambio data
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0 / 15 lug 2009
 */
public final class CambioDataEve extends BaseEvent {


    /**
     * Costruttore completo con parametri.
     * <p>
     * @param modulo di riferimento
     */
    public CambioDataEve(Modulo modulo) {

        /* rimanda al costruttore della superclasse */
        super(modulo);
    }

} // fine della classe