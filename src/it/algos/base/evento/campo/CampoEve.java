/**
 * Copyright(c): 2006
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 6 feb 2006
 */

package it.algos.base.evento.campo;

import it.algos.base.campo.base.Campo;
import it.algos.base.evento.BaseEvent;

/**
 * Superclasse per gli eventi di un campo.
 * </p>
 * Serve per mantenere le variabili
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 6 feb 2006
 */
public abstract class CampoEve extends BaseEvent {


    /**
     * riferimento al campo sorgente di questo evento
     */
    private Campo campo;


    /**
     * Costruttore completo con parametri.
     *
     * @param campo sorgente di questo evento
     */
    public CampoEve(Campo campo) {
        /* rimanda al costruttore della superclasse */
        super(campo);

        /* regola le variabili di istanza coi parametri */
        this.setCampo(campo);
    }


    public Campo getCampo() {
        return campo;
    }


    private void setCampo(Campo campo) {
        this.campo = campo;
    }

} // fine della classe
