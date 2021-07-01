/**
 * Title:     ListaModCellaAz
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      22-mag-2007
 */
package it.algos.base.evento.lista;

import it.algos.base.evento.BaseAzione;

/**
 * Azione cella della lista modificata e registrata.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 2 feb 2006
 */
public abstract class ListaModCellaAz extends BaseAzione implements ListaModCellaLis {


    /**
     * listaModCellaAz, da ListaModCellaLis.
     * </p>
     * Esegue l'azione <br>
     * Rimanda al metodo sovrascritto, nell'oggetto specifico
     * della classe che genera questo evento <br>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @param unEvento evento che causa l'azione da eseguire <br>
     */
    public void listaModCellaAz(ListaModCellaEve unEvento) {
    }

} // fine della classe
