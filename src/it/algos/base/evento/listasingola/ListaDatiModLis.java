/**
 * Title:     ListaGenLis
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      23-mar-2006
 */
package it.algos.base.evento.listasingola;

import it.algos.base.evento.BaseListener;

/**
 * Listener di una lista che ha modificato il suo modello dati.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 25-feb-2004 ore 13.55.03
 * @see java.awt.event.ActionEvent
 */
public interface ListaDatiModLis extends BaseListener {

    /**
     * datiModificatiAz, da datiModificatiLis.
     * </p>
     * Esegue l'azione <br>
     * Rimanda al metodo sovrascritto, nell'oggetto specifico
     * della classe che genera questo evento <br>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @param unEvento evento che causa l'azione da eseguire <br>
     */
    public void listaDatiModAz(ListaDatiModEve unEvento);

}// fine della interfaccia
