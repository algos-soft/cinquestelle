/**
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      29-dic-2004
 */
package it.algos.base.azione.adapter;

import it.algos.base.azione.AzioneBase;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Eventi delle finestre.
 * </p>
 * Questa classe adattatore astratta: <ul>
 * <li> An abstract adapter class for receiving <strong>window events</strong> </li>
 * <li> The methods in this class are empty </li>
 * <li> This class exists as convenience for creating listener objects </li>
 * <li> Implementa tutti i metodi della interfaccia <code>WindowListener</code> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 29-dic-2004 ore 8.41.20
 */
public abstract class AzAdapterWindow extends AzioneBase implements WindowListener {

    /**
     * windowClosing, da WindowListener.
     * <p/>
     * Esegue l'azione <br>
     * Rimanda al metodo delegato, nel gestore specifico associato
     * all' oggetto che genera questo evento <br>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @param unEvento evento che causa l'azione da eseguire <br>
     */
    public void windowClosing(WindowEvent unEvento) {
    }


    /**
     * windowClosed, da WindowListener.
     * <p/>
     * Esegue l'azione <br>
     * Rimanda al metodo delegato, nel gestore specifico associato
     * all' oggetto che genera questo evento <br>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @param unEvento evento che causa l'azione da eseguire <br>
     */
    public void windowClosed(WindowEvent unEvento) {
    }


    /**
     * windowOpened, da WindowListener.
     * <p/>
     * Esegue l'azione <br>
     * Rimanda al metodo delegato, nel gestore specifico associato
     * all' oggetto che genera questo evento <br>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @param unEvento evento che causa l'azione da eseguire <br>
     */
    public void windowOpened(WindowEvent unEvento) {
    }


    /**
     * windowIconified, da WindowListener.
     * <p/>
     * Esegue l'azione <br>
     * Rimanda al metodo delegato, nel gestore specifico associato
     * all' oggetto che genera questo evento <br>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @param unEvento evento che causa l'azione da eseguire <br>
     */
    public void windowIconified(WindowEvent unEvento) {
    }


    /**
     * windowDeiconified, da WindowListener.
     * <p/>
     * Esegue l'azione <br>
     * Rimanda al metodo delegato, nel gestore specifico associato
     * all' oggetto che genera questo evento <br>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @param unEvento evento che causa l'azione da eseguire <br>
     */
    public void windowDeiconified(WindowEvent unEvento) {
    }


    /**
     * windowActivated, da WindowListener.
     * <p/>
     * Esegue l'azione <br>
     * Rimanda al metodo delegato, nel gestore specifico associato
     * all' oggetto che genera questo evento <br>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @param unEvento evento che causa l'azione da eseguire <br>
     */
    public void windowActivated(WindowEvent unEvento) {
    }


    /**
     * windowDeactivated, da WindowListener.
     * <p/>
     * Esegue l'azione <br>
     * Rimanda al metodo delegato, nel gestore specifico associato
     * all' oggetto che genera questo evento <br>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @param unEvento evento che causa l'azione da eseguire <br>
     */
    public void windowDeactivated(WindowEvent unEvento) {
    }

}// fine della classe
