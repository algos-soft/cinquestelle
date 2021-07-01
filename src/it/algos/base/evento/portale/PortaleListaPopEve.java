/**
 * Copyright(c): 2006
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 20 lug 2006
 */

package it.algos.base.evento.portale;

import it.algos.base.evento.BaseEvent;
import it.algos.base.query.filtro.Filtro;

/**
 * Evento di tipo PortaleListaPop
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 20 lug 2006
 */
public final class PortaleListaPopEve extends BaseEvent {


    /**
     * Costruttore completo con parametri.
     */
    public PortaleListaPopEve(Filtro filtro) {
        /* rimanda al costruttore della superclasse */
        super(filtro);
    }


    public Filtro getFiltro() {
        return (Filtro)this.getSource();
    }


} // fine della classe
