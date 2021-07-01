package it.algos.base.azione; /**
 * Copyright: Copyright (c) 2007
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      2-mag-2007
 */

import it.algos.base.modulo.Modulo;

/**
 * //@TODO DESCRIZIONE SINTETICA DELLA CLASSE .
 * </p>
 * Questa classe: <ul>
 * <li> </li>
 * <li> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 2-mag-2007 ore 11.32.23
 */
public class AzModulo extends AzioneLocale {


    /**
     * Costruttore completo con parametri.
     *
     * @param modulo di riferimento
     * @param posMenu di posizionamento
     */
    public AzModulo(Modulo modulo, String posMenu) {
        /* rimanda al costruttore della superclasse */
        super(modulo, posMenu);
        super.setUsaIconaModulo(true);
    }// fine del metodo costruttore completo


    /**
     * Costruttore completo con parametri.
     *
     * @param modulo di riferimento
     */
    public AzModulo(Modulo modulo) {
        /* rimanda al costruttore della superclasse */
        super(modulo);
        super.setUsaIconaModulo(true);
    }// fine del metodo costruttore completo


}// fine della classe
