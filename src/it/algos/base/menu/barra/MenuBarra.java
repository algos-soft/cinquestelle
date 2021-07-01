/**
 * Title:     MenuBarra
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      19-mar-2004
 */
package it.algos.base.menu.barra;

import it.algos.base.menu.menu.Menu;

/**
 * Regola la creazione dei menu in una <CODE>Finestra</CODE>.
 * </p>
 * Questa interfaccia: <ul>
 * <li> Implementa il <i>design pattern</i> <b>Facade</b> </li>
 * <li> Fornisce un interfaccia unificata per tutte le chiamate alle classi di
 * questo package </li>
 * <li> Mantiene la codifica delle azioni per tipologia di <CODE>Finestra</CODE> </li>
 * </ul>
 *
 * @author Alberto Colombo, Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 19-mar-2004 ore 16.24.11
 */
public interface MenuBarra {

    /**
     * codifica per azioni pertinenti alla sola lista
     */
    public static final int TIPO_LISTA = 1;

    /**
     * codifica per azioni pertinenti alla sola scheda
     */
    public static final int TIPO_SCHEDA = 2;

    /**
     * codifica per azioni pertinenti sia alla lista  che alla scheda
     */
    public static final int TIPO_LISTA_SCHEDA = 3;

    /**
     * codifica per azioni pertinenti al dialogo
     */
    public static final int TIPO_DIALOGO = 4;

    /**
     * codifica per azioni non meglio specificate
     */
    public static final int TIPO_NON_SPECIFICATO = 5;


    /**
     * riferimento al menu archivio
     */
    public abstract Menu getMenuArchivio();


    /**
     * riferimento al menu moduli
     */
    public abstract Menu getMenuModuli();


    /**
     * riferimento al menu composizione
     */
    public abstract Menu getMenuComposizione();


    /**
     * riferimento al menu strumenti
     */
    public abstract Menu getMenuStrumenti();


    /**
     * riferimento al menu tabelle
     */
    public abstract Menu getMenuTabelle();


    /**
     * riferimento al menu specifico
     */
    public abstract Menu getMenuSpecifico();


    /**
     * riferimento al menu aiuto
     */
    public abstract Menu getMenuHelp();


    /**
     * riferimento al menu servizio
     */
    public abstract Menu getMenuServizio();


    /**
     * restituisce una istanza concreta.
     *
     * @return istanza di <code>MenuBarraBase</code>
     */
    public abstract MenuBarraBase getMenuBarra();

}// fine della interfaccia
