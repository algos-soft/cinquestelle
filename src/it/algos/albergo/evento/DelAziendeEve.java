/**
 * Title:     DelAziendeEve
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      17-mag-2007
 */
package it.algos.albergo.evento;

import it.algos.base.evento.BaseEvent;
import it.algos.base.modulo.Modulo;

/**
 * Evento di tipo cambio azienda
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0 / 17 mag 2007
 */
public final class DelAziendeEve extends BaseEvent {


    /**
     * Costruttore completo con parametri.
     */
    public DelAziendeEve(Modulo modulo) {

        /* rimanda al costruttore della superclasse */
        super(modulo);
    }

} // fine della classe
