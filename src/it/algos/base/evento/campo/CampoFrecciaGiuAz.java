/**
 * Copyright(c): 2006
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 6 ott 2006
 */

package it.algos.base.evento.campo;

import it.algos.base.evento.BaseAzione;

/**
 * Azione generica CampoFrecciaGiu.
 * </p>
 * Questa classe adattatore astratta: <ul>
 * <li> Riceve tutti gli <strong>eventi</strong> CampoFrecciaGiuEve </li>
 * <li> I metodi della classe sono vuoti </li>
 * <li> Questa classe esiste come utilità per creare oggetti di tipo listener </li>
 * <li> Implementa tutti i metodi della interfaccia <code>CampoFrecciaGiuLis</code> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 6 ott 2006
 */
public abstract class CampoFrecciaGiuAz extends BaseAzione implements CampoFrecciaGiuLis {


    /**
     * campoFrecciaGiuAz, da CampoFrecciaGiuLis.
     * </p>
     * Esegue l'azione <br>
     * Rimanda al metodo sovrascritto, nell'oggetto specifico
     * della classe che genera questo evento <br>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @param unEvento evento che causa l'azione da eseguire <br>
     */
    public void campoFrecciaGiuAz(CampoFrecciaGiuEve unEvento) {
    }

} // fine della classe
