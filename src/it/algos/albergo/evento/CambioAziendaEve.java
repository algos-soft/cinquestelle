/**
 * Copyright(c): 2006
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 24 mag 2006
 */

package it.algos.albergo.evento;

import it.algos.base.evento.BaseEvent;
import it.algos.base.modulo.Modulo;

/**
 * Evento di tipo cambio azienda
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 24 mag 2006
 */
public final class CambioAziendaEve extends BaseEvent {


    /**
     * Costruttore completo con parametri.
     */
    public CambioAziendaEve(Modulo modulo) {

        /* rimanda al costruttore della superclasse */
        super(modulo);
    }

} // fine della classe
