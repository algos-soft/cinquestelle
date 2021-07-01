/**
 * Title:     DelAziendeAz
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      17-mag-2007
 */
package it.algos.albergo.evento;

import it.algos.base.evento.BaseAzione;

/**
 * Azione specifica eliminazione aziende.
 * </p>
 * Questa classe adattatore astratta: <ul>
 * <li> Riceve tutti gli <strong>eventi</strong> ModEmergenzaEve </li>
 * <li> I metodi della classe sono vuoti </li>
 * <li> Questa classe esiste come utilità per creare oggetti di tipo listener </li>
 * <li> Implementa tutti i metodi della interfaccia <code>ModEmergenzaLis</code> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 24 mag 2006
 */
public abstract class DelAziendeAz extends BaseAzione implements DelAziendeLis {


    /**
     * delAziendeAz, da DelAziendeLis.
     * </p>
     * Esegue l'azione <br>
     * Rimanda al metodo sovrascritto, nell'oggetto specifico
     * della classe che genera questo evento <br>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @param unEvento evento che causa l'azione da eseguire <br>
     */
    public abstract void delAziendeAz(DelAziendeEve unEvento);

} // fine della classe
