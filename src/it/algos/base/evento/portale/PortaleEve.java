/**
 * Copyright(c): 2006
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 6 feb 2006
 */

package it.algos.base.evento.portale;

import it.algos.base.evento.BaseEvent;
import it.algos.base.portale.Portale;

/**
 * Superclasse per gli eventi di un portale.
 * </p>
 * Serve per mantenere le variabili
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 6 feb 2006
 */
public abstract class PortaleEve extends BaseEvent {

    /**
     * riferimento al portale sorgente di questo evento
     */
    private Portale portale;


    /**
     * Costruttore completo con parametri.
     *
     * @param portale sorgente di questo evento
     */
    public PortaleEve(Portale portale) {
        /* rimanda al costruttore della superclasse */
        super(portale);

        /* regola le variabili di istanza coi parametri */
        this.setPortale(portale);
    }


    public Portale getPortale() {
        return portale;
    }


    private void setPortale(Portale portale) {
        this.portale = portale;
    }


} // fine della classe
