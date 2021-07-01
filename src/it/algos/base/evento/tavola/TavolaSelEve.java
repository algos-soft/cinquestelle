/**
 * Title:     TavolaSelEve
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      25.02.2006
 */
package it.algos.base.evento.tavola;

import it.algos.base.tavola.Tavola;

/**
 * Evento di tipo TavolaSelEve
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 2 feb 2006
 */
public class TavolaSelEve extends TavolaEve {


    /**
     * Costruttore completo con parametri.
     */
    public TavolaSelEve() {
        /* rimanda al costruttore della superclasse */
        this(null);
    }


    /**
     * Costruttore completo con parametri.
     *
     * @param tavola che ha generato l'evento
     */
    public TavolaSelEve(Tavola tavola) {
        /* rimanda al costruttore della superclasse */
        super(tavola);
    }

} // fine della classe
