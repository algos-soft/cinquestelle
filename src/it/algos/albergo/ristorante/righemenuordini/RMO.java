/**
 * Copyright: Copyright (c) 2005
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      22-feb-2005
 */
package it.algos.albergo.ristorante.righemenuordini;

import it.algos.base.interfaccia.Generale;

/**
 * Interfaccia per il package RigheMenuOrdini.
 * <p/>
 * Costanti pubbliche, codifiche e metodi (astratti) di questo package <br>
 * Questa interfaccia: <ul>
 * <li> Implementa il <i>design pattern</i> <b>Facade</b> </li>
 * <li> Fornisce un'interfaccia unificata per tutte le chiamate alle classi di
 * questo package </li>
 * <li> Mantiene la codifica del nome-chiave interno del modulo (usato nel Modello) </li>
 * <li> Mantiene la codifica della tavola di archivio collegata (usato nel Modello) </li>
 * <li> Mantiene la codifica del titolo della finestra (usato nel Navigatore) </li>
 * <li> Mantiene la codifica del nome del modulo come appare nel Menu Moduli
 * (usato nel Navigatore) </li>
 * <li> Mantiene la codifica delle viste (per la lista) </li>
 * <li> Mantiene la codifica dei set di campi (per la scheda) </li>
 * <li> Mantiene la codifica dei campi (per il modello e per la scheda) </li>
 * <li> Mantiene la codifica dei moduli (per gli altri package) </li>
 * <li> Mantiene altre costanti utilizzate in tutto il package </li>
 * <li> Dichiara (astratti) i metodi  utilizzati nel package che devono
 * essere visti all'esterno </li>
 * <ul/>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 22-feb-2005 ore 10.15.27
 */
public interface RMO extends Generale {

    /**
     * Codifica del nome-chiave interno del modulo
     * (usato nel Modulo)
     */
    public static final String NOME_MODULO = "RigheMenuOrdini";

    /**
     * Codifica della tavola di archivio collegata
     * (usato nel Modello)
     */
    public static final String NOME_TAVOLA = "righemenuordini";

    /**
     * Codifica del titolo della finestra
     * (usato nel Navigatore)
     */
    public static final String TITOLO_FINESTRA = "RigheMenuOrdini";

    /**
     * Codifica del titolo del modulo come appare nel Menu Moduli
     * (usato nel Navigatore)
     */
    public static final String TITOLO_MENU = "RigheMenuOrdini";

    /**
     * Codifica del titolo della tabella come appare nella altre Liste
     * (usato nel Modello)
     */
    public static final String TITOLO_TABELLA = NOME_TAVOLA;

    /**
     * Codifica delle viste (espanse e clonate) per la Lista
     * (oltre a quelle standard della superclasse)
     * (ATTENZIONE - il nome della Vista DEVE essere diverso dai nomi dei campi)
     */
    public static final String VISTA_IN_MENU = "vistainmenuordini";

    /**
     * Set con i solo campi extra
     * Usato nel navigatore in menu
     */
    public static final String SET_EXTRA = "setextra";

    /**
     * codifica dei navigatori
     * (oltre a quello standard)
     */
    public static final String NAV_RMO_RTO = "rmo_nav_rmo_rto";

    public static final String NAV_IN_MENU = "rmo_nav_in_menu";

    /**
     * Codifica dei nomi dei campi (per il modello e per la scheda)
     * (oltre a quelli standard della superclasse)
     */
    public static final String CAMPO_RIGA_MENU_TAVOLO = "linkrighetavolo";

    public static final String CAMPO_RIGA_MENU_PIATTO = "linkrighepiatto";

    public static final String CAMPO_QUANTITA = "quantita";

    public static final String CAMPO_PIATTO_EXTRA = "linkextrapiatto";

    public static final String CAMPO_CONTORNO_EXTRA = "linkextracontorno";

}// fine della interfaccia
