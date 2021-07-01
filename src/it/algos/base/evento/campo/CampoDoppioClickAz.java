/**
 * Title:     CampoGUIAz
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      3-apr-2006
 */
package it.algos.base.evento.campo;

import it.algos.base.evento.BaseAzione;

/**
 * Doppio click nel componente di un campo.
 * </p>
 * Questa classe adattatore astratta: <ul>
 * <li> Riceve tutti gli <strong>eventi</strong> CampoMemoriaEve </li>
 * <li> I metodi della classe sono vuoti </li>
 * <li> Questa classe esiste come utilità per creare oggetti di tipo listener </li>
 * <li> Implementa tutti i metodi della interfaccia <code>CampoMemoriaLis</code> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0 / 17 mar 2006
 */
public abstract class CampoDoppioClickAz extends BaseAzione implements CampoDoppioClickLis {


    /**
     * campoDoppioClickAz, da CampoDoppioClickLis.
     * </p>
     * Esegue l'azione <br>
     * Rimanda al metodo sovrascritto, nell'oggetto specifico
     * della classe che genera questo evento <br>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @param unEvento evento che causa l'azione da eseguire <br>
     */
    public void campoDoppioClickAz(CampoDoppioClickEve unEvento) {
    }

} // fine della classe
