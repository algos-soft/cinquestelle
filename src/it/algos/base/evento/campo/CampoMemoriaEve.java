/**
 * Copyright(c): 2006
 * Company: Algos s.r.l.
 * Author: alex
 * Date: 17 mar 2006
 */

package it.algos.base.evento.campo;

import it.algos.base.campo.base.Campo;

/**
 * Evento di un campo che ha modificato il suo valore di memoria.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0 / 17 mar 2006
 */
public final class CampoMemoriaEve extends CampoEve {

    /**
     * Costruttore completo con parametri.
     *
     * @param campo sorgente di questo evento
     */
    public CampoMemoriaEve(Campo campo) {
        /* rimanda al costruttore della superclasse */
        super(campo);
    }

} // fine della classe
