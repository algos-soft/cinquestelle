/**
 * Title:        CostanteGestore.java
 * Package:      it.algos.base.costante
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 13 ottobre 2002 alle 18.05
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2002  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
//  Package di questa interfaccia e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.costante;

//-----------------------------------------------------------------------------
// Classe principale


//-----------------------------------------------------------------------------
/**
 * Interfaccia per le costanti di definizione della modalita di gestione
 * della classe e di partenza delle finestre<br>
 * <p/>
 * Ogni costante indica il tipo di finestre che verranno aperte alla partenza
 * di un modulo/applicazione
 * <p/>
 * Ogni costante indica il tipo di selezione e filtroOld dei records in apertura
 * <p/>
 * Questa interfaccia e' responsabile di:<br>
 * A - Definire le costanti (static final class variables)
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  13 ottobre 2002 ore 18.05
 */
public interface CostanteGestore {
    //-------------------------------------------------------------------------
    // Costanti statiche della classe    (private,pubbliche)  (class variables)
    //-------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // Costanti per identificare gli oggetti interessati dagli eventi
    //--------------------------------------------------------------------------

    /**     */
    public static final String CHIAVE_CAMPO_SELETTORE = "testoselettore";

    //--------------------------------------------------------------------------
    // Selezione e filtroOld dei records in apertura
    //--------------------------------------------------------------------------
    /**
     * seleziona tutti i records della tavola archivio
     * - non servono altri parametri -
     * esempio: la lista completa
     */
    public static final int RECORDS_TUTTI = 1;

    /**
     * non seleziona nessun record
     * - non servono altri parametri -
     * esempio: non mi viene in mente, ma non si sa mai
     */
    public static final int RECORDS_ZERO = 2;

    /**
     * seleziona un set di records dalla tavola archivio secondo un criterio
     * - serve anche il criterio di filtroOld -
     * esempio: una lista filtrata
     */
    public static final int RECORDS_FILTRATI = 3;

    /**
     * seleziona un record ed il set completo della tavola archivio
     * - serve anche il codice del record da aprire -
     * esempio: una scheda specifica che 'vede' sotto tutti i records
     */
    public static final int RECORDS_TUTTI_UNO = 4;

    /**
     * seleziona un record ed il set filtrato della tavola archivio
     * - serve anche il criterio di filtroOld -
     * - serve anche il codice del record da aprire -
     * esempio: una scheda specifica che 'vede' sotto solo un set filtrato
     */
    public static final int RECORDS_FILTRATI_UNO = 5;

    /**
     * seleziona un solo record
     * - non servono altri parametri -
     * esempio: un nuovo record
     */
    public static final int RECORDS_UNO = 6;

    //--------------------------------------------------------------------------
    // Selezione e filtroOld dei records nella lista
    //--------------------------------------------------------------------------
    /**
     * crezione o visualizzazione finestra lista
     */
    public static final int FILTRO_INIZIO = 0;

    /**
     * caricamento di liste in import
     */
    public static final int FILTRO_IMPORT = 1;

    /**
     * dialogo di ricerca
     */
    public static final int FILTRO_RICERCA = 2;

    /**
     * proiezione di lista esterna
     */
    public static final int FILTRO_PROIEZIONE = 3;

    /**
     * ritorno da modifica record
     */
    public static final int FILTRO_MODIFICA_RECORD = 4;

    /**
     * ritorno da nuovo record
     */
    public static final int FILTRO_NUOVO_RECORD = 5;

    /**
     * modifica ordinamento
     */
    public static final int FILTRO_MODIFICA_ORDINE = 6;

    /**
     * elimina uno o piu' records
     */
    public static final int FILTRO_ELIMINA_RECORD = 7;

    /**
     * mostra solo i records selezionati
     */
    public static final int FILTRO_SOLO_SELEZIONATI = 8;

    /**
     * rimuove i records selezionati
     */
    public static final int FILTRO_RIMUOVE_SELEZIONATI = 9;

    /**
     * mostra tutti i records
     */
    public static final int FILTRO_MOSTRA_TUTTI = 10;
    //-------------------------------------------------------------------------
}// fine della interfaccia it.algos.base.gestore.CostanteGestore.java
//-----------------------------------------------------------------------------

