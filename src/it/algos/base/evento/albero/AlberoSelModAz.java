/**
 * Title:     SelezioneModificataAz
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      23-mar-2006
 */
package it.algos.base.evento.albero;

import it.algos.base.evento.BaseAzione;

/**
 * Azione di un albero che ha modificato il suo stato di selezione.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 28-dic-2004 ore 16.13.23
 */
public abstract class AlberoSelModAz extends BaseAzione implements AlberoSelModLis {

    /**
     * alberoSelModAz, da alberoSelModLis.
     * </p>
     * Esegue l'azione <br>
     * Rimanda al metodo sovrascritto, nell'oggetto specifico
     * della classe che genera questo evento <br>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @param unEvento evento che causa l'azione da eseguire <br>
     */
    public void alberoSelModAz(AlberoSelModEve unEvento) {
    }

}// fine della classe
