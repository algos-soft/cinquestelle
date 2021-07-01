/**
 * Copyright(c): 2006
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 21 gen 2006
 */

package it.algos.base.evento;


import javax.swing.event.EventListenerList;


public interface Eventi {


    /**
     * Aggiunge un listener alla lista.
     * <p/>
     * Serve per utilizzare la Enumeration della sottoclasse <br>
     * Metodo (sovra)scritto nelle Enumeration specifiche
     * (le Enumeration delle sottoclassi della classe dove
     * e' questa Enumeration) <br>
     * Controlla che il listener appartenga all'enumerazione <br>
     *
     * @param lista degli eventi a cui aggiungersi
     * @param listener dell'evento da lanciare
     */
    public abstract void add(EventListenerList lista, BaseListener listener);


    /**
     * Rimuove un listener dalla lista.
     * <p/>
     * Serve per utilizzare la Enumeration della sottoclasse <br>
     * Metodo (sovra)scritto nelle Enumeration specifiche
     * (le Enumeration delle sottoclassi della classe dove
     * e' questa Enumeration) <br>
     * Controlla che il listener appartenga all'enumerazione <br>
     *
     * @param lista degli eventi da cui rimuoverlo
     * @param listener dell'evento da non lanciare
     */
    public abstract void remove(EventListenerList lista, BaseListener listener);


    public abstract Class getListener();


    public abstract Class getEvento();


    public abstract Class getAzione();


    public abstract String getMetodo();


}
