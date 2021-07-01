/**
 * Copyright(c): 2006
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 2 feb 2006
 */

package it.algos.base.evento.portale;

import it.algos.base.evento.BaseAzione;

/**
 * Azione generica PortaleNuovoRec.
 * </p>
 * Questa classe adattatore astratta: <ul>
 * <li> Riceve tutti gli <strong>eventi</strong> PortaleNuovoRecEve </li>
 * <li> I metodi della classe sono vuoti </li>
 * <li> Questa classe esiste come utilità per creare oggetti di tipo listener </li>
 * <li> Implementa tutti i metodi della interfaccia <code>PortaleNuovoRecLis</code> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 2 feb 2006
 */
public abstract class PortaleNuovoRecAz extends BaseAzione implements PortaleNuovoRecLis {


    /**
     * portaleNuovoRecAz, da PortaleNuovoRecLis.
     * </p>
     * Esegue l'azione <br>
     * Rimanda al metodo sovrascritto, nell'oggetto specifico
     * della classe che genera questo evento <br>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @param unEvento evento che causa l'azione da eseguire <br>
     */
    public void portaleNuovoRecAz(PortaleNuovoRecEve unEvento) {
    }

} // fine della classe
