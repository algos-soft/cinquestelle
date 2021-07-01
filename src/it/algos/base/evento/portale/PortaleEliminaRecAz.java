/**
 * Title:     PortaleEliminaRecAz
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      24-nov-2006
 */
package it.algos.base.evento.portale;

import it.algos.base.evento.BaseAzione;

/**
 * Azione generica PortaleEliminaRec.
 * </p>
 * Questa classe adattatore astratta: <ul>
 * <li> Riceve tutti gli <strong>eventi</strong> PortaleEliminaRecEve </li>
 * <li> I metodi della classe sono vuoti </li>
 * <li> Questa classe esiste come utilità per creare oggetti di tipo listener </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 2 feb 2006
 */
public abstract class PortaleEliminaRecAz extends BaseAzione implements PortaleEliminaRecLis {


    /**
     * PortaleEliminaRecAz, da PortaleEliminaRecLis.
     * </p>
     * Esegue l'azione <br>
     * Rimanda al metodo sovrascritto, nell'oggetto specifico
     * della classe che genera questo evento <br>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @param unEvento evento che causa l'azione da eseguire <br>
     */
    public void portaleEliminaRecAz(PortaleEliminaRecEve unEvento) {
        int a = 87;
    }

} // fine della classe
