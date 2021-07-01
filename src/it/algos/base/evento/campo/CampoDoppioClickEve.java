/**
 * Title:     CampoGUIEve
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      3-apr-2006
 */
package it.algos.base.evento.campo;

import it.algos.base.campo.base.Campo;

/**
 * Evento per il doppio click in un campo.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0 / 3-apr-2006
 */
public final class CampoDoppioClickEve extends CampoEve {

    /**
     * Costruttore completo con parametri.
     *
     * @param campo sorgente di questo evento
     */
    public CampoDoppioClickEve(Campo campo) {
        /* rimanda al costruttore della superclasse */
        super(campo);
    }

} // fine della classe
