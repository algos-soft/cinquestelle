/**
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      29-dic-2004
 */
package it.algos.base.azione.adapter;

import it.algos.base.azione.AzioneBase;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Eventi modifiche alla selezione di un componente.
 * </p>
 * Questa classe adattatore astratta: <ul>
 * <li> An abstract adapter class that's notified when a lists selection value changes </li>
 * <li> The methods in this class are empty </li>
 * <li> This class exists as convenience for creating listener objects </li>
 * <li> Implementa tutti i metodi della interfaccia <code>ListSelectionListener</code> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 29-dic-2004 ore 8.42.29
 */
public abstract class AzAdapterListSelection extends AzioneBase implements ListSelectionListener {

    /**
     * valueChanged, da ListSelectionListener.
     * <p/>
     * Esegue l'azione <br>
     * Rimanda al metodo delegato, nel gestore specifico associato
     * all' oggetto che genera questo evento <br>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @param unEvento evento che causa l'azione da eseguire <br>
     */
    public void valueChanged(ListSelectionEvent unEvento) {
    }

}// fine della classe
