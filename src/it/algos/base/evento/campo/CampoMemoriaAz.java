/**
 * Copyright(c): 2006
 * Company: Algos s.r.l.
 * Author: alex
 * Date: 17 mar 2006
 */

package it.algos.base.evento.campo;

import it.algos.base.evento.BaseAzione;

/**
 * Azione di un campo che ha modificato il suo valore di memoria.
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
public abstract class CampoMemoriaAz extends BaseAzione implements CampoMemoriaLis {


    /**
     * campoMemoriaAz, da CampoMemoriaLis.
     * </p>
     * Esegue l'azione <br>
     * Rimanda al metodo sovrascritto, nell'oggetto specifico
     * della classe che genera questo evento <br>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @param unEvento evento che causa l'azione da eseguire <br>
     */
    public void campoMemoriaAz(CampoMemoriaEve unEvento) {
    }

} // fine della classe
