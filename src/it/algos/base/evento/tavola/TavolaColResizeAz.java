/**
 * Title:     TavolaColResizeAz
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      19-gen-2007
 */
package it.algos.base.evento.tavola;

import it.algos.base.evento.BaseAzione;

/**
 * Azione di resizing colonna in una tavola.
 * </p>
 * Questa classe adattatore astratta: <ul>
 * <li> Riceve tutti gli <strong>eventi</strong> TavolaSelEve </li>
 * <li> I metodi della classe sono vuoti </li>
 * <li> Questa classe esiste come utilità per creare oggetti di tipo listener </li>
 * <li> Implementa tutti i metodi della interfaccia <code>TavolaSelLis</code> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0 / 19-gen-2007
 */
public abstract class TavolaColResizeAz extends BaseAzione implements TavolaColResizeLis {


    /**
     * tavolaColResizeAz, da TavolaColResizeLis.
     * </p>
     * Esegue l'azione <br>
     * Rimanda al metodo sovrascritto, nell'oggetto specifico
     * della classe che genera questo evento <br>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @param unEvento evento che causa l'azione da eseguire <br>
     */
    public void tavolaColResizeAz(TavolaColResizeEve unEvento) {
    }

} // fine della classe
