/**
 * Copyright(c): 2006
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 20 lug 2006
 */

package it.algos.base.evento.campo;

import it.algos.base.evento.BaseAzione;

/**
 * Azione generica Bottone.
 * </p>
 * Questa classe adattatore astratta: <ul>
 * <li> Riceve tutti gli <strong>eventi</strong> BottoneEve </li>
 * <li> I metodi della classe sono vuoti </li>
 * <li> Questa classe esiste come utilità per creare oggetti di tipo listener </li>
 * <li> Implementa tutti i metodi della interfaccia <code>BottoneLis</code> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 20 lug 2006
 */
public abstract class BottoneAz extends BaseAzione implements BottoneLis {


    /**
     * bottoneAz, da BottoneLis.
     * </p>
     * Esegue l'azione <br>
     * Rimanda al metodo sovrascritto, nell'oggetto specifico
     * della classe che genera questo evento <br>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @param unEvento evento che causa l'azione da eseguire <br>
     */
    public void bottoneAz(BottoneEve unEvento) {
        int a = 87;
    }

} // fine della classe
