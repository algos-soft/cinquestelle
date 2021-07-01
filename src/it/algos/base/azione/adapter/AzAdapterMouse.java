/**
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      28-dic-2004
 */
package it.algos.base.azione.adapter;

import it.algos.base.azione.AzioneBase;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Eventi del mouse.
 * </p>
 * Questa classe adattatore astratta: <ul>
 * <li> An abstract adapter class for receiving <strong>mouse events</strong> </li>
 * <li> The methods in this class are empty </li>
 * <li> This class exists as convenience for creating listener objects </li>
 * <li> Implementa tutti i metodi della interfaccia <code>MouseListener</code> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 28-dic-2004 ore 16.03.36
 */
public abstract class AzAdapterMouse extends AzioneBase implements MouseListener {


    /**
     * mouseClicked, da MouseListener.
     * <p/>
     * Esegue l'azione <br>
     * Rimanda al metodo delegato, nel gestore specifico associato
     * all' oggetto che genera questo evento <br>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @param unEvento evento che causa l'azione da eseguire <br>
     */
    public void mouseClicked(MouseEvent unEvento) {
    } /* fine del metodo */


    /**
     * mouseEntered, da MouseListener.
     * <p/>
     * Esegue l'azione <br>
     * Rimanda al metodo delegato, nel gestore specifico associato
     * all' oggetto che genera questo evento <br>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @param unEvento evento che causa l'azione da eseguire <br>
     */
    public void mouseEntered(MouseEvent unEvento) {
    } /* fine del metodo */


    /**
     * mouseExited, da MouseListener.
     * <p/>
     * Esegue l'azione <br>
     * Rimanda al metodo delegato, nel gestore specifico associato
     * all' oggetto che genera questo evento <br>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @param unEvento evento che causa l'azione da eseguire <br>
     */
    public void mouseExited(MouseEvent unEvento) {
    } /* fine del metodo */


    /**
     * mousePressed, da MouseListener.
     * <p/>
     * Esegue l'azione <br>
     * Rimanda al metodo delegato, nel gestore specifico associato
     * all' oggetto che genera questo evento <br>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @param unEvento evento che causa l'azione da eseguire <br>
     */
    public void mousePressed(MouseEvent unEvento) {
    } /* fine del metodo */


    /**
     * mouseReleased, da MouseListener.
     * <p/>
     * Esegue l'azione <br>
     * Rimanda al metodo delegato, nel gestore specifico associato
     * all' oggetto che genera questo evento <br>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @param unEvento evento che causa l'azione da eseguire <br>
     */
    public void mouseReleased(MouseEvent unEvento) {
    } /* fine del metodo */

}// fine della classe
