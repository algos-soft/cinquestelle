/**
 * Copyright: Copyright (c) 2005
 * Company:   Algos s.r.l.
 * Author:    gac
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 30 luglio 2003 alle 18.19
 */
package it.algos.albergo.ristorante.sala;

import it.algos.base.interfaccia.Generale;

/**
 * Interfaccia per la tabella Sala.
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
 * <li> Mantiene la codifica delle viste (per la lista) </li>
 * <li> Mantiene la codifica dei set di campi (per la scheda) </li>
 * <li> Mantiene la codifica dei campi (per il modello e per la scheda) </li>
 * <li> Mantiene la codifica dei moduli (per gli altri package) </li>
 * <li> Mantiene altre costanti utilizzate in tutto il package </li>
 * <li> Dichiara (astratti) i metodi  utilizzati nel package che devono
 * essere visti all'esterno </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 4-gen-2005 ore 17.09.51
 */
public interface Sala extends Generale {

    /**
     * Codifica del nome-chiave interno del modulo
     * (usato nel Modulo)
     */
    public static final String NOME_MODULO = "Sala";

    /**
     * Codifica della tavola di archivio collegata
     * (usato nel Modello)
     */
    public static final String NOME_TAVOLA = "sala";

    /**
     * Codifica del titolo della finestra
     * (usato nel Navigatore)
     */
    public static final String TITOLO_FINESTRA = "Sale del ristorante";

    /**
     * Codifica del titolo del modulo come appare nel Menu Moduli
     * (usato nel Navigatore)
     */
    public static final String TITOLO_MENU = "Sale del ristorante";

    /**
     * Codifica del titolo della tabella come appare nella altre Liste
     * (usato nel Modello)
     */
    public static final String TITOLO_TABELLA = NOME_TAVOLA;

}// fine della interfaccia

