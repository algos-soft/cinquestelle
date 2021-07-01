/**
 * Title:     TavolaSelAz
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      25.02.2006
 */
package it.algos.base.evento.tavola;

import it.algos.base.evento.BaseAzione;

/**
 * Azione generica TavolaSelAz.
 * </p>
 * Questa classe adattatore astratta: <ul>
 * <li> Riceve tutti gli <strong>eventi</strong> TavolaSelEve </li>
 * <li> I metodi della classe sono vuoti </li>
 * <li> Questa classe esiste come utilità per creare oggetti di tipo listener </li>
 * <li> Implementa tutti i metodi della interfaccia <code>TavolaSelLis</code> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 2 feb 2006
 */
public abstract class TavolaSelAz extends BaseAzione implements TavolaSelLis {


    /**
     * tavolaSelAz, da TavolaSelLis.
     * </p>
     * Esegue l'azione <br>
     * Rimanda al metodo sovrascritto, nell'oggetto specifico
     * della classe che genera questo evento <br>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @param unEvento evento che causa l'azione da eseguire <br>
     */
    public void tavolaSelAz(TavolaSelEve unEvento) {
    }

} // fine della classe
