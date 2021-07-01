/**
 * Copyright: Copyright (c) 2005
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      1-mar-2005
 */

package it.algos.albergo.ristorante.righetavoloordini;


/**
 * Interfaccia per il package RigheTavoloOrdini.
 * <p/>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 1-mar-2005 ore 21.26.36
 */
public interface RTO {

    /**
     * Codifica del nome-chiave interno del modulo
     * (usato nel Modulo)
     */
    public static final String NOME_MODULO = "RigheTavoloOrdini";

    /**
     * Codifica della tavola di archivio collegata
     * (usato nel Modello)
     */
    public static final String NOME_TAVOLA = "righetavoloordini";

    /**
     * Codifica del titolo della finestra
     * (usato nel Navigatore)
     */
    public static final String TITOLO_FINESTRA = "Righe tavolo ordini";

    /**
     * Codifica del titolo del modulo come appare nel Menu Moduli
     * (usato nel Navigatore)
     */
    public static final String TITOLO_MENU = "RTO";

    /**
     * Codifica del titolo della tabella come appare nella altre Liste
     * (usato nel Modello)
     */
    public static final String TITOLO_TABELLA = NOME_TAVOLA;


    /**
     * codifica dei nomi dei campi disponibili all'esterno del modulo
     */
    public static final String CAMPO_RMORDINI = "linkrighemenuordini";

    public static final String CAMPO_MODIFICA = "linkmodifiche";

    public static final String CAMPO_ANTICIPO = "anticipo";

    /**
     * Codifica delle viste (espanse e clonate) per la Lista
     * (oltre a quelle standard della superclasse)
     * (ATTENZIONE - il nome della Vista DEVE essere diverso dai nomi dei campi)
     */
    public static final String VISTA_IN_MENU = "rtovistainmenu";

    /**
     * codifica dei navigatori
     * (oltre a quello standard)
     */
    public static final String NAV_IN_MENU = "nav_in_menu";

}