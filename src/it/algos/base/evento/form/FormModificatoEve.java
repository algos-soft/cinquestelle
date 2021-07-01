/**
 * Title:     FormModificatoEve
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      20-apr-2006
 */
package it.algos.base.evento.form;

import it.algos.base.evento.BaseEvent;
import it.algos.base.form.FormBase;

/**
 * Eventi di un form che ha modificato il valore di un campo.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 25-feb-2004 ore 14.01.09
 */
public final class FormModificatoEve extends BaseEvent {


    /**
     * Costruttore completo con parametri.
     */
    public FormModificatoEve(FormBase oggetto) {
        /* rimanda al costruttore della superclasse */
        super(oggetto);
    }


}// fine della classe
