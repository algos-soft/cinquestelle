/**
 * Title:     Menu
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      20-mar-2004
 */
package it.algos.base.menu.menu;

/**
 * //@TODO DESCRIZIONE SINTETICA DELLA INTERFACCIA .
 * </p>
 * Questa interfaccia: <ul>
 * <li>
 * <li>
 * </ul>
 *
 * @author Alberto Colombo, Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 20-mar-2004 ore 8.03.33
 */
public interface Menu {

    /**
     * voce del menu archivio come viene visualizzato nella finestra. <br>
     * usato in pi&ugrave classi
     */
    public static final String TITOLO_ARCHIVIO = "Archivio";

    /**
     * voce del menu strumenti come viene visualizzato nella finestra. <br>
     * usato in pi&ugrave classi
     */
    public static final String TITOLO_STRUMENTI = "Strumenti";


    /**
     * restituisce una istanza concreta
     *
     * @return istanza di <code>MenuBase</code>
     */
    public abstract MenuBase getMenu();

}// fine della interfaccia
