/**
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      29-dic-2004
 */
package it.algos.base.azione.adapter;

import it.algos.base.azione.AzioneBase;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * Eventi fuoco di un campo.
 * </p>
 * Questa classe adattatore astratta: <ul>
 * <li> An abstract adapter class for receiving <strong>keyboard focus events</strong> </li>
 * <li> The methods in this class are empty </li>
 * <li> This class exists as convenience for creating listener objects </li>
 * <li> Implementa tutti i metodi della interfaccia <code>FocusListener</code> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 29-dic-2004 ore 8.41.01
 */
public abstract class AzAdapterFocus extends AzioneBase implements FocusListener {

    /**
     * focusGained, da FocusListener.
     * <p/>
     * Esegue l'azione <br>
     * Rimanda al metodo delegato, nel gestore specifico associato
     * all' oggetto che genera questo evento <br>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @param unEvento evento che causa l'azione da eseguire <br>
     */
    public void focusGained(FocusEvent unEvento) {
    }


    /**
     * focusLost, da FocusListener.
     * <p/>
     * Esegue l'azione <br>
     * Rimanda al metodo delegato, nel gestore specifico associato
     * all' oggetto che genera questo evento <br>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @param unEvento evento che causa l'azione da eseguire <br>
     */
    public void focusLost(FocusEvent unEvento) {
    }

}// fine della classe
