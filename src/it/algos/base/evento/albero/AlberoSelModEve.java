/**
 * Title:     SelezioneModificataEve
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      23-mar-2006
 */
package it.algos.base.evento.albero;

import it.algos.base.albero.Albero;

/**
 * Eventi di un albero che ha modificato il suo stato di selezione.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 25-feb-2004 ore 14.01.09
 */
public final class AlberoSelModEve extends AlberoEve {


    /**
     * Costruttore completo con parametri.
     */
    public AlberoSelModEve(Albero albero) {
        /* rimanda al costruttore della superclasse */
        super(albero);
    }


}// fine della classe
