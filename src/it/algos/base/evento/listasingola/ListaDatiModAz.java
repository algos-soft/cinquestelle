/**
 * Title:     DatiModificatiAz
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      23-mar-2006
 */
package it.algos.base.evento.listasingola;

import it.algos.base.evento.BaseAzione;

/**
 * Azione di una lista che ha modificato il suo modello dati.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 28-dic-2004 ore 16.13.23
 */
public abstract class ListaDatiModAz extends BaseAzione implements ListaDatiModLis {

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
    public void listaDatiModAz(ListaDatiModEve unEvento) {
    }

}// fine della classe
