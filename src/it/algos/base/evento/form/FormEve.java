/**
 * Title:     CampoEvent
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      25-feb-2004
 */
package it.algos.base.evento.form;

import it.algos.base.evento.BaseEvent;
import it.algos.base.form.FormBase;

/**
 * Eventi generici di un form.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 25-feb-2004 ore 14.01.09
 */
public final class FormEve extends BaseEvent {


    /**
     * Costruttore completo con parametri.
     */
    public FormEve(FormBase oggetto) {
        /* rimanda al costruttore della superclasse */
        super(oggetto);
    }


}// fine della classe
