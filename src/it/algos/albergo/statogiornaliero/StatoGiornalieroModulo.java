/**
 * Title:     StatoGiornalieroModulo
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      3-giu-2009
 */
package it.algos.albergo.statogiornaliero;

import it.algos.base.campo.base.Campo;
import it.algos.base.modulo.ModuloMemoria;

import java.util.ArrayList;

/**
 * Modulo memoria
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 3-giu-2009 ore 13.46.20
 */
class StatoGiornalieroModulo extends ModuloMemoria {


    /**
     * Costruttore completo
     * <p>
     * rispetta la visibilità in lista definita nei campi
     * @param campi elenco dei campi
     */
    public StatoGiornalieroModulo(ArrayList<Campo> campi) {
        /* rimanda al costruttore della superclasse */
        super(campi, false);

    }// fine del metodo costruttore completo



}// fine della classe
