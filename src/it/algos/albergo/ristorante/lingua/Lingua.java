/**
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Ceresa, Colombo, Valbonesi
 * @version 1.0  /
 * Creato:       il 12 agosto 2003 alle 18.55
 */
package it.algos.albergo.ristorante.lingua;

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
 * @version 1.0  /  12 agosto 2003 ore 18.55
 */
public interface Lingua extends Generale {

    /**
     * Codifica del nome-chiave interno del modulo
     * (usato nel Modulo)
     */
    public static final String NOME_MODULO = "Lingua";

    /**
     * Codifica della tavola di archivio collegata
     * (usato nel Modello)
     */
    public static final String NOME_TAVOLA = "lingua";

    /**
     * Codifica del titolo della finestra
     * (usato nel Navigatore)
     */
    public static final String TITOLO_FINESTRA = "Lingue utilizzate";

    /**
     * Codifica del titolo del modulo come appare nel Menu Moduli
     * (usato nel Navigatore)
     */
    public static final String TITOLO_MENU = "Lingue del menu";

    /**
     * Codifica del titolo della tabella come appare nella altre Liste
     * (usato nel Modello)
     */
    public static final String TITOLO_TABELLA = NOME_TAVOLA;

    /**
     * Codifica dei nomi dei campi (per il modello e per la scheda)
     * (oltre a quelli standard della superclasse)
     */
    public static final String CAMPO_NOME = "nome";

    public static final String CAMPO_COLAZIONE = "colazione";

    public static final String CAMPO_PRANZO = "pranzo";

    public static final String CAMPO_CENA = "cena";

    public static final String CAMPO_SURGELATI = "surgelati";

    public static final String CAMPO_INTOLLERANZE = "intolleranze";

    public static final String CAMPO_CONGIUNZIONE = "congiunzione";

    public static final String CAMPO_CONTORNO_MINUSCOLO = "contornominuscolo";


    /**
     * Lingue utilizzate
     */
    public static final String[] LINGUA = {"Italiano", "Deutsch", "English", "Francais"};

    /**
     * Codici ad uso interno delle lingue utilizzate
     * (fanno riferimento a LINGUA[])
     */
    public static final int ITALIANO = 0;

    public static final int TEDESCO = 1;

    public static final int INGLESE = 2;

    public static final int FRANCESE = 3;

    /**
     * Codice della lingua principale del programma
     * (fa riferimento a LINGUA[])
     */
    public static final int LINGUA_PRINCIPALE = ITALIANO;


}// fine della interfaccia

