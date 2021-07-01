/**
 * Title:     StatoGiornalieroLista
 * Copyright: Copyright (c) 2009
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      30-giu-2009
 */
package it.algos.albergo.statogiornaliero;

import it.algos.base.lista.ListaDefault;
import it.algos.base.tavola.Tavola;

/**
 * Lista del navigatore StatoGiornaliero
 * </p>
 * Allo scopo di creare una Tavola specifica.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 30-giu-2009 ore 22.05.10
 */
class StatoGiornalieroLista extends ListaDefault {



    /**
     * Costruttore completo.
     * <p>
     */
    StatoGiornalieroLista() {
        super();
    }// fine del metodo costruttore completo


    @Override
    protected Tavola creaTavola() {
        return new StatoTavola();
    }

}// fine della classe