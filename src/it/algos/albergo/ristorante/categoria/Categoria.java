/**
 * Copyright: Copyright (c) 2005
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      17-gen-2005
 */
package it.algos.albergo.ristorante.categoria;

import it.algos.base.interfaccia.Generale;

/**
 * Interfaccia per la tabella Categoria.
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
 * @version 1.0    / 17-gen-2005 ore 15.47.02
 */
public interface Categoria extends Generale {

    /**
     * Codifica del nome-chiave interno del modulo
     * (usato nel Modulo)
     */
    public static final String NOME_MODULO = "categoria";

    /**
     * Codifica della tavola di archivio collegata
     * (usato nel Modello)
     */
    public static final String NOME_TAVOLA = "categoria";

    /**
     * Codifica del titolo della finestra
     * (usato nel Navigatore)
     */
    public static final String TITOLO_FINESTRA = "Categorie di piatti";

    /**
     * Codifica del titolo del modulo come appare nel Menu Moduli
     * (usato nel Navigatore)
     */
    public static final String TITOLO_MENU = "Categorie di piatti";

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
    public static final String VISTA_ITALIANO = "vistaitaliano";

    public static final String VISTA_SIGLA_ITALIANO = "siglaitaliano";

    /**
     * codifica dei set di campi per la scheda
     * (oltre a quelli standard della superclasse)
     * (ATTENZIONE - il nome del Set DEVE essere diverso dai nomi dei campi)
     */
    public static final String SET_ITALIANO = "setitaliano";

    public static final String SET_STRANIERO = "setstraniero";

    /**
     * Codifica dei nomi dei campi (per il modello e per la scheda)
     * (oltre a quelli standard della superclasse)
     */
    public static final String CAMPO_ITALIANO = "italiano";

    public static final String CAMPO_TEDESCO = "tedesco";

    public static final String CAMPO_INGLESE = "inglese";

    public static final String CAMPO_FRANCESE = "francese";

    public static final String CAMPO_CARNE_PESCE = "carnepesce";

    public static final String CAMPO_CONTORNO = "contorno";

}// fine della interfaccia
