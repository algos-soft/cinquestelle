/**
 * Title:        CostanteModulo.java
 * Package:      it.algos.base.costante
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 15 dicembre 2002 alle 14.13
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2002  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
//  Package di questa interfaccia e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.costante;

import it.algos.base.modulo.Modulo;

//-----------------------------------------------------------------------------
// Classe principale
//-----------------------------------------------------------------------------


/**
 * Questa interfaccia e' responsabile di: <br>
 * A - Definire le costanti coi nomi completi di path delle classi standard <br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  15 dicembre 2002 ore 14.13
 */
public interface CostanteModulo {

    //-------------------------------------------------------------------------
    // Costanti statiche della classe    (private,pubbliche)  (class variables)
    //-------------------------------------------------------------------------
    /**
     * percorso base dei path
     */
    public static final String PATH_BASE = "it/algos/base/";

    /**
     * nome completo del gestore
     */
    public static final String PATH_GESTORE = PATH_BASE + "gestore/GestoreDefault";

    /**
     * nome completo del modello
     */
    public static final String PATH_MODELLO = PATH_BASE + "modello/base/ModelloDefault";

    /**
     * nome completo della tabella
     */
    public static final String PATH_TABELLA = PATH_BASE + "modello/tabella/TabellaDefault";

    /**
     * nome completo della finestra della lista
     */
    public static final String PATH_FINESTRA_LISTA =
            PATH_BASE + "finestra/lista/FinestraListaDefault";

    /**
     * nome completo della finestra della scheda
     */
    public static final String PATH_FINESTRA_SCHEDA =
            PATH_BASE + "finestra/scheda/FinestraSchedaDefault";

    /**
     * nome completo della tavola base della lista
     */
    public static final String PATH_TAVOLA = PATH_BASE + "tavola/TavolaDefault";

    /**
     * percorso fino alle cartelle dei programmi (escluse)
     */
    public static final String PATH_PROGRAMMI = "it/algos/";

    /**
     * non apre nessuna finestra - serve a creare il gestore ed attende
     * ulteriori chiamate ai metodi
     * - non servono altri parametri
     */
    public static final int AVVIO_NULLO = 0;

    /**
     * apertura della finestra lista (visibile)
     * - non servono altri parametri -
     * - eventualmente si usa un filtroOld ai records -
     * esempio: inizio normale come applicazione completa (default)
     */
    public static final int AVVIO_APPLICAZIONE = 1;

    /**
     * apertura della finestra lista (visibile)
     * - non servono altri parametri -
     * - eventualmente si usa un filtroOld ai records -
     * esempio: inizio normale come modulo (opzionale)
     */
    public static final int AVVIO_MODULO = 2;

    /**
     * apertura della finestra lista (visibile)
     * - non servono altri parametri -
     * - eventualmente si usa un filtroOld ai records -
     * esempio: avvio normale come applicazione completa (default)
     */
    public static final int AVVIO_NORMALE = 15;

    /**
     * apertura della finestra lista (visibile)
     * - non servono altri parametri -
     * - eventualmente si usa un filtroOld ai records -
     * esempio: inizio normale della gestione (opzionale)
     */
    public static final int AVVIO_LISTA = 3;

    /**
     * apertura delle finestre lista (visibile) e scheda (invisibile
     * ma gia pronta)
     * - scheda per nuovo record, non servono altri parametri -
     * - eventualmente si usa un filtroOld ai records -
     * esempio: inizio normale della gestione (opzionale)
     */
    public static final int AVVIO_LISTA_SCHEDA = 4;

    /**
     * apertura della finestra scheda nuovo record (visibile) e
     * lista (visibile sotto)
     * - non servono altri parametri -
     * - eventualmente si usa un filtroOld ai records -
     * esempio: inizio con creazione automatica di un nuovo record
     */
    public static final int AVVIO_NUOVO_LISTA = 5;

    /**
     * apertura della finestra modifica record (visibile) e
     * lista (visibile sotto)
     * - serve anche il codice del record da aprire -
     * - eventualmente si usa un filtroOld ai records -
     * esempio: inizio con riapertura dell'ultimo record inserito
     */
    public static final int AVVIO_MODIFICA_LISTA = 6;

    /**
     * apertura della sola scheda nuovo record (visibile)
     * - non servono altri parametri -
     * esempio: creazione di un nuovo record chiamato da un'altra classe
     */
    public static final int AVVIO_NUOVO = 7;

    /**
     * apertura della sola scheda modifica record (visibile)
     * - serve anche il codice del record da aprire -
     * esempio: modifica di un record chiamata da un'altra classe
     */
    public static final int AVVIO_MODIFICA = 8;

    /**
     * creazione della sola scheda nuovo record (invisibile)
     * - non servono altri parametri -
     * esempio: creazione di un nuovo record da programma
     */
    public static final int AVVIO_NUOVO_INVISIBILE = 9;

    /**
     * creazione della sola cheda modifica record (invisibile)
     * - serve anche il codice del record da aprire -
     * esempio: modifica di un record da programma
     */
    public static final int AVVIO_MODIFICA_INVISIBILE = 10;

    /**
     * creazione del pannello interno della sub-lista/scheda (visibile)
     * - serve anche unCodiceClasse e unaChiaveIndirizzo -
     * esempio: visione di una lista di indirizzi da Rubrica
     */
    public static final int AVVIO_SUB = 10;

    /**
     * creazione del pannello interno della sub-scheda (visibile)
     * - serve anche il codice del record da aprire -
     * esempio: visione di un indirizzo da Rubrica
     * nome della vista normale
     * <p/>
     * nome della vista normale
     */
//    public static final int AVVIO_SUB_SCHEDA = 11;

    /**
     * nome della vista normale
     */
    public static final String VISTA_BASE_DEFAULT = Modulo.VISTA_BASE_DEFAULT;

    public static final String VISTA_SIGLA = Modulo.VISTA_SIGLA;

    /**
     * nome del set dei campi di default per la scheda
     */
    public static final String SET_BASE_DEFAULT = Modulo.SET_BASE_DEFAULT;

    /**
     * nome del set dei campi di default per la pagina Programmatore della scheda
     */
    public static final String SET_PROGRAMMATORE = Modulo.SET_PROGRAMMATORE;

}// fine della interfaccia