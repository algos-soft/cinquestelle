/**
 * Copyright(c): 2006
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 29 gen 2006
 */

package it.algos.base.evento.campo;

import it.algos.base.evento.BaseAzione;

/**
 * Azione generica CampoVideo.
 * </p>
 * Questa classe adattatore astratta: <ul>
 * <li> Riceve tutti gli <strong>eventi</strong> CampoVideoEve </li>
 * <li> I metodi della classe sono vuoti </li>
 * <li> Questa classe esiste come utilità per creare oggetti di tipo listener </li>
 * <li> Implementa tutti i metodi della interfaccia <code>CampoVideoLis</code> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 29 gen 2006
 */
public abstract class CampoVideoAz extends BaseAzione implements CampoVideoLis {


    /**
     * campoVideoAz, da CampoVideoLis.
     * </p>
     * Esegue l'azione <br>
     * Rimanda al metodo sovrascritto, nell'oggetto specifico
     * della classe che genera questo evento <br>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @param unEvento evento che causa l'azione da eseguire <br>
     */
    public void campoVideoAz(CampoVideoEve unEvento) {
    }

} // fine della classe
