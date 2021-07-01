/**
 * Title:     AzAdapterWindowFocus
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      13-ago-2007
 */
package it.algos.base.azione.adapter;

import it.algos.base.azione.AzioneBase;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

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
public abstract class AzAdapterWindowFocus extends AzioneBase implements WindowFocusListener {

    /**
     * windowGainedFocus, da WindowFocusListener.
     * <p/>
     * Esegue l'azione <br>
     * Rimanda al metodo delegato, nel gestore specifico associato
     * all' oggetto che genera questo evento <br>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @param e evento che causa l'azione da eseguire <br>
     */
    public void windowGainedFocus(WindowEvent e) {
    }


    /**
     * windowLostFocus, da WindowFocusListener.
     * <p/>
     * Esegue l'azione <br>
     * Rimanda al metodo delegato, nel gestore specifico associato
     * all' oggetto che genera questo evento <br>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @param e evento che causa l'azione da eseguire <br>
     */
    public void windowLostFocus(WindowEvent e) {
    }

}// fine della classe
