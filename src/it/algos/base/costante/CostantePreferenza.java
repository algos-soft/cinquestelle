/**
 * Title:        CostantePreferenza.java
 * Package:      it.algos.base.costante
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 29 settembre 2002 alle 22.12
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
 * Interfaccia per le costanti delle preferenze<br>
 * <br>
 * Costanti 'chiave' per recuperare un oggetto (di solito di tipo String ma pi\uFFFD
 * generalmente Object) dalla collezione testi (di tipo HashMap)<br>
 * <p/>
 * La 'chiave' \uFFFD obbligatoriamente di tipo stringa e può essere usata
 * direttamente; uso le costanti perch\uFFFD sono pi\uFFFD comode da ricordare
 * I valori di queste costanti devono essere tutti diversi tra loro;
 * la classe HashMap non accetta due volte lo stesso oggetto<br>
 * <p/>
 * Se inserisco nella classe per due (o tre) volte lo stesso oggetto,
 * non mi da errore, ma mi ritrovo una sola istanza dell'oggetto<br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  29 settembre 2002 ore 22.12
 */
public interface CostantePreferenza {

    //-------------------------------------------------------------------------
    // Costanti statiche della classe    (private,pubbliche)  (class variables)
    //-------------------------------------------------------------------------
    //----------------------------------------------------------------
    //  Costanti 'chiave' per individuare
    //	una preferenza di gestione generale del programma
    //----------------------------------------------------------------
    public static final String PREF_BOOL_UTENTE_PROGRAMMATORE = "GeneraleIsProgrammatore";

    public static final String PREF_BOOL_USA_DB = "GeneraleIsUsaDataBase";

    public static final String PREF_LIVELLO = "GeneraleLivello";

    //----------------------------------------------------------------
    //  Costanti 'chiave' per individuare
    //	una preferenza dei database
    //----------------------------------------------------------------
    public static final String PREF_DB_DRIVER = "DataBaseDriver";

    public static final String PREF_DB_PERCORSO = "DataBasePercorso";

    public static final String PREF_DB_INDIRIZZO = "DataBaseIndirizzo";

    public static final String PREF_DB_PORTA = "DataBasePorta";

    public static final String PREF_DB_ARCHIVIO = "DataBaseArchivio";

    public static final String PREF_DB_UTENTE = "DataBaseUtente";

    public static final String PREF_DB_PASSWORD = "DataBasePassword";

    public static final String PREF_BOOL_USA_DB_LOCALE = "DataBaseIsUsaLocale";

    public static final String PREF_BOOL_CREA_ARCHIVI_PARTENZA = "DataBaseIsCreaArchiviPartenza";

    public static final String PREF_BOOL_CREA_CAMPI_ARCHIVIO = "DataBaseIsCreaCampiArchivio";

    public static final String PREF_BOOL_DB_USA_ALIAS = "DataBaseIsUsaAlias";

    //----------------------------------------------------------------
    //  Costanti 'chiave' per individuare
    //	una preferenza della GUI (Graphical User Interface)
    //----------------------------------------------------------------
    public static final String PREF_LOOK_DEFAULT = "GuiLookDefault";

    public static final String PREF_BOOL_DESELEZIONA_LISTA = "GuiIsDeselezionaLista";

    public static final String PREF_BOOL_TRIM_CAMPO_MODIFICATO = "GuiIsTriCampoModificato";

    public static final String PREF_BOOL_COMPORTAMENTO_VERBOSO = "GuiIsComportamentoVerboso";

    public static final String PREF_BOOL_USA_TAB_PAGINA_SINGOLA = "GuiIsUsaTabPaginaSingola";

    public static final String PREF_BOOL_MOSTRA_SOLO_AZIONI_ATTIVE = "GuiIsMostraSoloAzioniAttive";

    public static final String PREF_BOOL_CAMPO_SELEZIONATO = "GuiIsCampoSelezionato";

    public static final String PREF_BOOL_LISTA_RIDIMENSIONABILE =
            "GuiIsFinestraListaRidimensionabile";

    public static final String PREF_BOOL_DIMENSIONE_LISTA_VARIABILE =
            "GuiIsDimensioneListaVariabile";

    public static final String PREF_BOOL_DIMENSIONE_FINESTRA_VARIABILE =
            "GuiIsDimensioneFinestraVariabile";

    public static final String PREF_BOOL_CHIUDE_SCHEDA_DOPO_REGISTRAZIONE =
            "GuiIsChiudeSchedaDopoRegistrazione";

    public static final String PREF_BOOL_CONTINUA_NUOVO_RECORD = "GuiIsContinuaNuovoRecord";

    public static final String PREF_BOOL_ORDINAMENTO_ASCII = "GuiIsOrdinamentoAscii";

    public static final String PREF_BOOL_SELEZIONE_LISTA_DISCONTINUA =
            "GuiIsSelezioneListaDiscontinua";

    public static final String PREF_BOOL_APRE_DIVERSE_FINESTRE_SCHEDA =
            "GuiIsApreDiverseFinestreScheda";

    public static final String PREF_BOOL_SELEZIONE_CON_SCHEDA_APERTA =
            "GuiIsSelezioneConSchedaAperta";

    public static final String PREF_BOOL_ETICHETTA_SCHEDA_RIDIMENSIONABILE =
            "GuiIsEtichettaSchedaRidimensionabile";

    public static final String PREF_BOOL_TOOL_TIP_COLONNA = "GuiIsToolTipColonnaAutomatico";

    public static final String PREF_BOOL_CONTROLLA_MOSTRA_TUTTI = "GuiIsControllaMostraTutti";

    public static final String PREF_BOOL_MOSTRA_LEGENDA_CAMPI = "GuiIsMostraLegendaCampi";
    //-------------------------------------------------------------------------
}// fine della interfaccia it.algos.base.costante.CostantePreferenza.java
//-----------------------------------------------------------------------------

