/**
 * Copyright(c): 2008
 * Company: Algos s.r.l.
 * Author: alex
 * Date: 3-4-08
 */

package it.algos.albergo.clientealbergo.indirizzoalbergo;

import it.algos.base.campo.base.Campo;
import it.algos.base.modulo.Modulo;
import it.algos.gestione.indirizzo.IndirizzoScheda;

/**
 * Scheda indirizzo di un cliente Albergo.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0 / 3-4-08
 */
public class IndirizzoAlbergoScheda extends IndirizzoScheda implements IndirizzoAlbergo {

    /**
     * Costruttore completo.
     * <p/>
     *
     * @param modulo di riferimento
     */
    public IndirizzoAlbergoScheda(Modulo modulo) {
        super(modulo);
    }// fine del metodo costruttore


    /**
     * Metodo eseguito quando un campo modificato perde il fuoco.
     * <p/>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @param campo interessato
     */
    protected void eventoUscitaCampoModificato(Campo campo) {
        super.eventoUscitaCampoModificato(campo);
    }


} // fine della classe