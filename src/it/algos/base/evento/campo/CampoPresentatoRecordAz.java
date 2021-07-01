/**
 * Copyright(c): 2008
 * Company: Algos s.r.l.
 * Author: alex
 * Date: 28 mag 2008
 */

package it.algos.base.evento.campo;

import it.algos.base.evento.BaseAzione;

/**
 * Azione generica: il campo ha presentato un record in scheda.
 * </p>
 * Questa classe adattatore astratta: <ul>
 * <li> Riceve tutti gli <strong>eventi</strong> CampoPresentatoRecordEve </li>
 * <li> I metodi della classe sono vuoti </li>
 * <li> Questa classe esiste come utilità per creare oggetti di tipo listener </li>
 * <li> Implementa tutti i metodi della interfaccia <code>CampoPresentatoRecordLis</code> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0 / 28 mag 2008
 */
public abstract class CampoPresentatoRecordAz extends BaseAzione implements CampoPresentatoRecordLis {


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
    public void presentatoRecordAz(CampoPresentatoRecordEve unEvento) {
        int a = 87;
    }

} // fine della classe