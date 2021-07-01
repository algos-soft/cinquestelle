/**
 * Title:     CampoEvent
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      25-feb-2004
 */
package it.algos.base.evento.campo;

import it.algos.base.campo.base.Campo;

/**
 * Eventi di un campo che ha perso il fuoco.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 25-feb-2004 ore 14.01.09
 */
public final class CampoPerdeFuocoEve extends CampoEve {


    /**
     * Costruttore completo con parametri.
     *
     * @param campo sorgente di questo evento
     */
    public CampoPerdeFuocoEve(Campo campo) {
        /* rimanda al costruttore della superclasse */
        super(campo);
    }


}// fine della classe
