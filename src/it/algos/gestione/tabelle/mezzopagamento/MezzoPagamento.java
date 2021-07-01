/**
 * Title:     ModoPagamento
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      18-lug-2006
 */
package it.algos.gestione.tabelle.mezzopagamento;

import it.algos.base.interfaccia.Generale;

/**
 * Interfaccia Mezzo di Pagamento.
 * </p>
 * Es. assegno, carta di credito, contante, etc.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 18-lug-2006 ore 8.08.42
 */
public interface MezzoPagamento extends Generale {

    /**
     * Codifica del nome-chiave interno del modulo
     * (usato nel Modulo)
     */
    public static final String NOME_MODULO = "Mezzi pagamento";

    /**
     * Codifica della tavola di archivio collegata
     * (usato nel Modello)
     */
    public static final String NOME_TAVOLA = "mezzopagamento";

    /**
     * Codifica del voce della finestra
     * (usato nel Navigatore)
     */
    public static final String TITOLO_FINESTRA = MezzoPagamento.NOME_MODULO;

    /**
     * Codifica del voce del modulo come appare nel Menu Moduli
     * (usato nel Navigatore)
     */
    public static final String TITOLO_MENU = "Mezzi pagamento";

    /**
     * Codifica del voce della tabella come appare nella altre Liste
     * (usato nel Modello)
     */
    public static final String TITOLO_TABELLA = MezzoPagamento.NOME_TAVOLA;

    /**
     * Codifica delle viste (espanse e clonate) per la Lista
     * (oltre a quelle standard della superclasse)
     * (ATTENZIONE - il nome della Vista DEVE essere diverso dai nomi dei campi)
     */
    //Non ce ne sono

    /**
     * codifica dei set di campi per la scheda
     * (oltre a quelli standard della superclasse)
     * (ATTENZIONE - il nome del Set DEVE essere diverso dai nomi dei campi)
     */
    //Non ce ne sono

    /**
     * Codifica dei nomi dei campi (per il modello e per la scheda)
     * (oltre a quelli standard della superclasse)
     * <p/>
     * Codifica dei nomi dei campi (per il modello e per la scheda)
     * (oltre a quelli standard della superclasse)
     * <p/>
     * Codifica dei nomi dei campi (per il modello e per la scheda)
     * (oltre a quelli standard della superclasse)
     */
    /**
     * Codifica dei nomi dei campi (per il modello e per la scheda)
     * (oltre a quelli standard della superclasse)
     */
    public static final String CAMPO_SIGLA = Generale.CAMPO_SIGLA;

    public static final String CAMPO_DESCRIZIONE = Generale.CAMPO_DESCRIZIONE;

    /**
     * codifica degli estratti pubblici disponibili all'esterno del modulo
     * (ATTENZIONE - gli oggetti sono di classe Estratto)
     *
     * @see it.algos.base.wrapper.EstrattoBase
     */
    //Non ce ne sono

}// fine della interfaccia
