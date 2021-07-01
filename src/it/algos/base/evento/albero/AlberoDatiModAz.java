/**
 * Title:     AlberoDatiModAz
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      24-mar-2006
 */
package it.algos.base.evento.albero;

import it.algos.base.evento.BaseAzione;

/**
 * Azione di un albero che ha modificato il suo modello dati.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 24-mar-2006 ore 16.13.23
 */
public abstract class AlberoDatiModAz extends BaseAzione implements AlberoDatiModLis {

    /**
     * alberoDatiModAz, da alberoDatiModLis.
     * </p>
     * Esegue l'azione <br>
     * Rimanda al metodo sovrascritto, nell'oggetto specifico
     * della classe che genera questo evento <br>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @param unEvento evento che causa l'azione da eseguire <br>
     */
    public void alberoDatiModAz(AlberoDatiModEve unEvento) {
    }

}// fine della classe
