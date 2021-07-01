/**
 * Copyright(c): 2006
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 6 feb 2006
 */

package it.algos.base.evento.modulo;

import it.algos.base.evento.BaseEvent;
import it.algos.base.modulo.Modulo;

/**
 * Superclasse per gli eventi di un modulo.
 * </p>
 * Serve per mantenere le variabili
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 6 feb 2006
 */
public abstract class ModEve extends BaseEvent {

    /**
     * riferimento al modulo sorgente di questo evento
     */
    private Modulo modulo;


    /**
     * Costruttore completo con parametri.
     *
     * @param modulo sorgente di questo evento
     */
    public ModEve(Modulo modulo) {
        /* rimanda al costruttore della superclasse */
        super(modulo);

        /* regola le variabili di istanza coi parametri */
        this.setModulo(modulo);
    }


    public Modulo getModulo() {
        return modulo;
    }


    private void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }


} // fine della classe
