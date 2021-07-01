/**
 * Copyright(c): 2006
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 24 mag 2006
 */

package it.algos.base.evento.pannello;

import it.algos.base.evento.BaseEvent;
import it.algos.base.pannello.Pannello;

/**
 * Evento di tipo pannello pensioni modificato
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 24 mag 2006
 */
public final class PanModificatoEve extends BaseEvent {


    /**
     * Costruttore completo con parametri.
     */
    public PanModificatoEve(Pannello pannello) {

        /* rimanda al costruttore della superclasse */
        super(pannello);
    }

} // fine della classe