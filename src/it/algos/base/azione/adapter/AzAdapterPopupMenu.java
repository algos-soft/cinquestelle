/**
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      29-dic-2004
 */
package it.algos.base.azione.adapter;

import it.algos.base.azione.AzioneBase;

import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

/**
 * Eventi specifici di un popup.
 * </p>
 * Questa classe adattatore astratta: <ul>
 * <li> An abstract adapter class for receiving <strong>events</strong> from PopupMenu</li>
 * <li> The methods in this class are empty </li>
 * <li> This class exists as convenience for creating listener objects </li>
 * <li> Implementa tutti i metodi della interfaccia <code>PopupMenuListener</code> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 29-dic-2004 ore 8.42.57
 */
public abstract class AzAdapterPopupMenu extends AzioneBase implements PopupMenuListener {

    /**
     * popupMenuCanceled, da PopupMenuListener.
     * <p/>
     * Esegue l'azione <br>
     * Rimanda al metodo delegato, nel gestore specifico associato
     * all' oggetto che genera questo evento <br>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @param unEvento evento che causa l'azione da eseguire <br>
     */
    public void popupMenuCanceled(PopupMenuEvent unEvento) {
    }


    /**
     * popupMenuWillBecomeInvisible, da PopupMenuListener.
     * <p/>
     * Esegue l'azione <br>
     * Rimanda al metodo delegato, nel gestore specifico associato
     * all' oggetto che genera questo evento <br>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @param unEvento evento che causa l'azione da eseguire <br>
     */
    public void popupMenuWillBecomeInvisible(PopupMenuEvent unEvento) {
    }


    /**
     * popupMenuWillBecomeVisible, da PopupMenuListener.
     * <p/>
     * Esegue l'azione <br>
     * Rimanda al metodo delegato, nel gestore specifico associato
     * all' oggetto che genera questo evento <br>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @param unEvento evento che causa l'azione da eseguire <br>
     */
    public void popupMenuWillBecomeVisible(PopupMenuEvent unEvento) {
    }

}// fine della classe
