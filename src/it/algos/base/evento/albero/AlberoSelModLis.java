/**
 * Title:     SelezioneModificataLis
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      23-mar-2006
 */
package it.algos.base.evento.albero;

import it.algos.base.evento.BaseListener;

/**
 * Listener di un albero che ha modificato il suo stato di selezione.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 25-feb-2004 ore 13.55.03
 * @see java.awt.event.ActionEvent
 */
public interface AlberoSelModLis extends BaseListener {

    /**
     * selezioneModificataAz, da selezioneModificataLis.
     * </p>
     * Esegue l'azione <br>
     * Rimanda al metodo sovrascritto, nell'oggetto specifico
     * della classe che genera questo evento <br>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @param unEvento evento che causa l'azione da eseguire <br>
     */
    public void alberoSelModAz(AlberoSelModEve unEvento);

}// fine della interfaccia
