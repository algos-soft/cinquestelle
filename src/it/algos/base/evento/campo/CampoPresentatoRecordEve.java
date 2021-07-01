/**
 * Copyright(c): 2008
 * Company: Algos s.r.l.
 * Author: alex
 * Date: 28 mag 2008
 */

package it.algos.base.evento.campo;

import it.algos.base.campo.base.Campo;

/**
 * Evento di tipo: il campo ha presentato un record in scheda
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0 / 28 mag 2008
 */
public final class CampoPresentatoRecordEve extends CampoEve {


    /**
     * Costruttore completo con parametri.
     *
     * @param campo sorgente di questo evento
     */
    public CampoPresentatoRecordEve(Campo campo) {
        /* rimanda al costruttore della superclasse */
        super(campo);
    }

} // fine della classe