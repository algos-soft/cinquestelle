/**
 * Copyright(c): 2006
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 29 gen 2006
 */

package it.algos.base.evento.navigatore;

import it.algos.base.evento.BaseAzione;

/**
 * Azione generica NavStato.
 * </p>
 * Questa classe adattatore astratta: <ul>
 * <li> Riceve tutti gli <strong>eventi</strong> NavStatoEve </li>
 * <li> I metodi della classe sono vuoti </li>
 * <li> Questa classe esiste come utilità per creare oggetti di tipo listener </li>
 * <li> Implementa tutti i metodi della interfaccia <code>NavStatoLis</code> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 29 gen 2006
 */
public abstract class NavStatoAz extends BaseAzione implements NavStatoLis {


    /**
     * navStatoAz, da NavStatoLis.
     * </p>
     * Esegue l'azione <br>
     * Rimanda al metodo sovrascritto, nell'oggetto specifico
     * della classe che genera questo evento <br>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @param unEvento evento che causa l'azione da eseguire <br>
     */
    public void navStatoAz(NavStatoEve unEvento) {
    }

} // fine della classe
