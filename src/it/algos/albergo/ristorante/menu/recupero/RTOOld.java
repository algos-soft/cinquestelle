/**
 * Copyright: Copyright (c) 2005
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      1-mar-2005
 */

package it.algos.albergo.ristorante.menu.recupero;


/**
 * Interfaccia per il package RigheTavoloOrdini.
 * <p/>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 1-mar-2005 ore 21.26.36
 */
public interface RTOOld {

    /**
     * Codifica del nome-chiave interno del modulo
     * (usato nel Modulo)
     */
    public static final String NOME_MODULO = "RTOOld";

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
    public static final String CAMPO_LINKRMT = "linkrighemenutavolo";

    public static final String CAMPO_LINKRMP = "linkrighemenupiatto";

    public static final String CAMPO_FLAG_EXTRA = "extra";

    public static final String CAMPO_LINK_EXTRAPIATTO = "linkextrapiatto";

    public static final String CAMPO_LINK_EXTRACONTORNO = "linkextracontorno";

    public static final String CAMPO_FLAG_MODIFICA = "modifica";

    public static final String CAMPO_LINKMODIFICA = "linkmodifiche";

    public static final String CAMPO_FLAG_ANTICIPO = "anticipo";


}