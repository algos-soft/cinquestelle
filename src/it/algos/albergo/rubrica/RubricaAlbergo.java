package it.algos.albergo.rubrica;

import it.algos.gestione.rubrica.Rubrica;

/**
 * Estende la rubrica generale; utilizzata in albergo.
 * </p>
 * Sezioni che compongono l'interfaccia (default) (scorciatoia): <ul>
 * <li> Costanti generali (default true) (cg) </li>
 * <li> Enumeration Cam per i campi (default true) (ec) </li>
 * <li> Enumeration Vis per le viste (default false) (ev) </li>
 * <li> Enumeration Set per i campi (default false) (es) </li>
 * <li> Enumeration Nav per i navigatori (default false) (en) </li>
 * <li> Enumeration Pop per i filtri (default false) (ep) </li>
 * <li> Enumeration Est per gli estratti (default false) (ee) </li>
 * <ul/>
 *
 * @author Guido Andrea Ceresa ed Alessandro Valbonesi
 * @author Gac
 * @version 1.0 / 7-apr-2009 ore 14:08
 */
public interface RubricaAlbergo extends Rubrica {

    /**
     * Codifica del nome-chiave interno del modulo
     * (usato nel Modulo)
     */
    public static final String NOME_MODULO = "Rubrica";

    /**
     * Codifica della tavola di archivio collegata
     * (usato nel Modello)
     */
    public static final String NOME_TAVOLA = Rubrica.NOME_TAVOLA;

    /**
     * Codifica del titolo della finestra
     * (usato nel Navigatore)
     */
    public static final String TITOLO_FINESTRA = NOME_MODULO;

    /**
     * Codifica del titolo del modulo come appare nel Menu Moduli
     * (usato nel Navigatore)
     */
    public static final String TITOLO_MENU = Rubrica.TITOLO_MENU;

    /**
     * Codifica del titolo della tabella come appare nella altre Liste
     * (usato nel Modello)
     */
    public static final String TITOLO_TABELLA = NOME_TAVOLA;
}// fine della classe