/**
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      28-dic-2004
 */
package it.algos.base.evento.campo;

import it.algos.base.evento.BaseAzione;

/**
 * Azione generica di un campo.
 * </p>
 * Questa classe adattatore astratta: <ul>
 * <li> Riceve tutti gli <strong>eventi</strong> di un campo </li>
 * <li> I metodi della classe sono vuoti </li>
 * <li> Questa classe esiste comu utilità per creare oggetti di tipo listener </li>
 * <li> Implementa tutti i metodi della interfaccia <code>CampoLis</code> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 28-dic-2004 ore 16.13.23
 */
public abstract class CampoBaseAz extends BaseAzione implements CampoBaseLis {

    /**
     * campoAz, da CampoLis.
     * </p>
     * Esegue l'azione <br>
     * Rimanda al metodo sovrascritto, nell'oggetto specifico
     * della classe che genera questo evento <br>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @param unEvento evento che causa l'azione da eseguire <br>
     */
    public void campoAz(CampoBaseEve unEvento) {
    }

}// fine della classe
