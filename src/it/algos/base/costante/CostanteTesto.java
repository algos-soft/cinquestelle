/**
 * Title:        CostanteTesto.java
 * Package:      it.algos.base.costante
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 29 settembre 2002 alle 22.13
 */
package it.algos.base.costante;

/**
 * Interfaccia per le costanti dei testi<br>
 * <br>
 * Costanti 'chiave' per recuperare un oggetto (di tipo String)
 * dalla collezione testi (di tipo Properties)<br>
 * <p/>
 * La 'chiave' è di tipo stringa e può essere usata direttamente;
 * uso le costanti perché sono più comode da ricordare
 * I valori di queste costanti devono essere tutti diversi tra loro;
 * la classe HashMap non accetta due volte lo stesso oggetto<br>
 * <p/>
 * Se inserisco nella classe per due (o tre) volte lo stesso oggetto,
 * non mi da errore, ma mi ritrovo una sola istanza dell'oggetto<br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  29 settembre 2002 ore 22.13
 */
public interface CostanteTesto {

    public static final String COPYRIGHT_SOCIETA = "CopSoc";

    public static final String COPYRIGHT_AUTORE_GAC = "CopAutGac";

    public static final String COPYRIGHT_AUTORE_ALE = "CopAutAle";

    public static final String COPYRIGHT_PROGRAMMA = "CopPro";

    public static final String BOTTONE_CHIUDE_FIN = "BotChiFin";

    public static final String BOTTONE_CHIUDE_FIN_TIP = "BotChiFinTip";

    public static final String BOTTONE_ESCE = "BotEsc";

    public static final String BOTTONE_ESCE_TIP = "BotEscTip";

    public static final String BOTTONE_CANCELLA = "BotCan";

    public static final String BOTTONE_CANCELLA_TIP = "BotCanTip";

    public static final String BOTTONE_REGISTRA = "BotReg";

    public static final String BOTTONE_REGISTRA_TIP = "BotRegTip";

    public static final String BOTTONE_ACCETTA = "BotOK";

    public static final String BOTTONE_ACCETTA_TIP = "BotOK";

    public static final String BOTTONE_ANNULLA = "BotAnn";

    public static final String BOTTONE_ANNULLA_TIP = "BotAnnTip";

    public static final String BOTTONE_CONTINUA = "BotCon";

    public static final String BOTTONE_CONTINUA_TIP = "BotConTip";


    public static final String TESTO_CHIUDE_FINESTRA = "AziChiFin";

    public static final String TESTO_CHIUDE_FINESTRA_TIP = "AziChiFinTip";

    public static final String TESTO_CHIUDE_FINESTRA_HELP = "AziChiFinHel";

    public static final String ESCE_PROGRAMMA = "AziEscPro";

    public static final String ESCE_PROGRAMMA_TIP = "AziEscProTip";

    public static final String ESCE_PROGRAMMA_HELP = "AziEscProHel";

    public static final String TESTO_CHIUDE_LISTA = "TestoChiudeLista";

    public static final String TESTO_CHIUDE_LISTA_TIP = "TestoChiudeListaTip";

    public static final String TESTO_CHIUDE_LISTA_HELP = "TestoChiudeListaHelp";

    public static final String TESTO_CHIUDE_SCHEDA = "TestoChiudeScheda";

    public static final String TESTO_CHIUDE_SCHEDA_TIP = "TestoChiudeSchedaTip";

    public static final String TESTO_CHIUDE_SCHEDA_HELP = "TestoChiudeSchedaHelp";

    public static final String TESTO_STAMPA = "AziSta";

    public static final String TESTO_STAMPA_TIP = "AziStaTip";

    public static final String TESTO_STAMPA_HELP = "AziStaHel";

    public static final String ABOUT = "AziAbo";

    public static final String ABOUT_TIP = "AziAboTip";

    public static final String ABOUT_HELP = "AziAboHel";

    public static final String UPDATE = "AziUpd";

    public static final String UPDATE_TIP = "AziUpdTip";

    public static final String UPDATE_HELP = "AziUpdHel";

    public static final String TESTO_CARICA_LISTA = "TestoCaricaLista";

    public static final String TESTO_CARICA_LISTA_TIP = "TestoCaricaListaTip";

    public static final String TESTO_CARICA_LISTA_HELP = "TestoCaricaListaHelp";

    public static final String TESTO_REGISTRA_LISTA = "TestoRegistraLista";

    public static final String TESTO_REGISTRA_LISTA_TIP = "TestoRegistraListaTip";

    public static final String TESTO_REGISTRA_LISTA_HELP = "TestoRegistraListaHelp";

    public static final String NUOVO_RECORD = "TestoRecordNuovo";

    public static final String NUOVO_RECORD_TIP = "TestoRecordNuovoTip";

    public static final String NUOVO_RECORD_HELP = "TestoRecordNuovoHelp";

    public static final String DUPLICA_RECORD = "TestoRecordDuplica";

    public static final String DUPLICA_RECORD_TIP = "TestoRecordDuplicaTip";

    public static final String DUPLICA_RECORD_HELP = "TestoRecordDuplicaHelp";

    public static final String MODIFICA_RECORD = "TestoRecordModifica";

    public static final String MODIFICA_RECORD_TIP = "TestoRecordModificaTip";

    public static final String MODIFICA_RECORD_HELP = "TestoRecordModificaHelp";

    public static final String ANNULLA_MODIFICHE = "AziAnnMod";

    public static final String ANNULLA_MODIFICHE_TIP = "AziAnnModTip";

    public static final String ANNULLA_MODIFICHE_HELP = "AziAnnModHel";

    public static final String REGISTRA = "AziReg";

    public static final String REGISTRA_TIP = "AziRegTip";

    public static final String REGISTRA_HELP = "AziRegHel";

    public static final String TESTO_CERCA = "AziCer";

    public static final String TESTO_CERCA_TIP = "AziCerTip";

    public static final String TESTO_CERCA_HELP = "AziCerHel";

    public static final String TESTO_PREFERENZE = "AziPre";

    public static final String TESTO_PREFERENZE_TIP = "AziPreTip";

    public static final String TESTO_PREFERENZE_HELP = "AziPreHel";

    public static final String ELIMINA_RECORD = "TestoRecordElimina";

    public static final String ELIMINA_RECORD_TIP = "TestoRecordEliminaTip";

    public static final String ELIMINA_RECORD_HELP = "TestoRecordEliminaHelp";

    public static final String ANNULLA_OPERAZIONE = "AziAnnOpe";

    public static final String ANNULLA_OPERAZIONE_TIP = "AziAnnOpeTip";

    public static final String ANNULLA_OPERAZIONE_HELP = "AziAnnOpeHel";

    public static final String TAGLIA_TESTO = "AziTagTes";

    public static final String TAGLIA_TESTO_TIP = "AziTagTesTip";

    public static final String TAGLIA_TESTO_HELP = "AziTagTesHel";

    public static final String COPIA_TESTO = "AziCopTes";

    public static final String COPIA_TESTO_TIP = "AziCopTesTip";

    public static final String COPIA_TESTO_HELP = "AziCopTesHel";

    public static final String INCOLLA_TESTO = "AziIncTes";

    public static final String INCOLLA_TESTO_TIP = "AziIncTesTip";

    public static final String INCOLLA_TESTO_HELP = "AziIncTesHel";

    public static final String ELIMINA_TESTO = "AziEliTes";

    public static final String ELIMINA_TESTO_TIP = "AziEliTesTip";

    public static final String ELIMINA_TESTO_HELP = "AziEliTesHel";

    public static final String SELEZIONA_TUTTO = "AziSelTut";

    public static final String SELEZIONA_TUTTO_TIP = "AziSelTutTip";

    public static final String SELEZIONA_TUTTO_HELP = "AziSelTutHel";

    public static final String MOSTRA_APPUNTI = "AziMosApp";

    public static final String MOSTRA_APPUNTI_TIP = "AziMosAppTip";

    public static final String MOSTRA_APPUNTI_HELP = "AziMosAppHel";

    public static final String TESTO_DESELEZIONA = "AziDes";

    public static final String TESTO_DESELEZIONA_TIP = "AziDesTip";

    public static final String TESTO_DESELEZIONA_HELP = "AziDesHel";

    public static final String SOLO_SELEZIONATI = "AziSolSel";

    public static final String SOLO_SELEZIONATI_TIP = "AziSolSelTip";

    public static final String SOLO_SELEZIONATI_HELP = "AziSolSelHel";

    public static final String NASCONDE_SELEZIONATI = "AziRimSel";

    public static final String NASCONDE_SELEZIONATI_TIP = "AziRimSelTip";

    public static final String NASCONDE_SELEZIONATI_HELP = "AziRimSelHel";

    public static final String TESTO_SELEZIONA_TUTTI_RECORDS = "AziSelRec";

    public static final String TESTO_SELEZIONA_TUTTI_RECORDS_TIP = "AziSelRecTip";

    public static final String TESTO_SELEZIONA_TUTTI_RECORDS_HELP = "AziSelRecHel";

    public static final String CARICA_TUTTI = "AziCarTut";

    public static final String CARICA_TUTTI_TIP = "AziCarTutTip";

    public static final String CARICA_TUTTI_HELP = "AziCarTutHel";

    public static final String PRIMO_RECORD = "AzioneRecordPrimo";

    public static final String PRIMO_RECORD_TIP = "AziRecPriTip";

    public static final String PRIMO_RECORD_HELP = "AziRecPriHel";

    public static final String RECORD_PRECEDENTE = "AzioneRecordPrecedente";

    public static final String RECORD_PRECEDENTE_TIP = "AziRecPreTip";

    public static final String RECORD_PRECEDENTE_HELP = "AziRecPreHel";

    public static final String RECORD_SUCCESSIVO = "AzioneRecordSuccessivo";

    public static final String RECORD_SUCCESSIVO_TIP = "AziRecSucTip";

    public static final String TESTO_RECORD_SUCCESSIVO_HELP = "AziRecSucHel";

    public static final String ULTIMO_RECORD = "AzioneRecordUltimo";

    public static final String ULTIMO_RECORD_TIP = "AziRecUltTip";

    public static final String ULTIMO_RECORD_HELP = "AziRecUltHel";


    public static final String ERRORE_GENERICO = "ErrGen";

    public static final String ERRORE_INIZIA = "ErrIniCla";

    public static final String TEST_PROVA = "MesTesPro";

    public static final String MESS_CANCELLA = "MesSicCan";

    public static final String ERRORE_DRIVER = "ErrDri";

    public static final String ERRORE_COLLEGAMENTO = "ErrColDB";

    public static final String ERRORE_CLASSE = "ErrCla";

    public static final String ERRORE_USO_DB = "ErrUsoDB";

    public static final String MESS_CANCELLA_MULTIPLO_INIZIO = "MesCanMul";

    public static final String MESS_CANCELLA_MULTIPLO_FINE = "MesCanFin";

    public static final String MESS_CONFERMA_CANCELLA_UNO = "MesConCanUno";

    public static final String MESS_CONFERMA_CANCELLA_MOLTI = "MesConCanMol";

    public static final String MESS_REGISTRAZIONE_EFFETTUATA = "MesRegEff";

    public static final String MESS_NON_REGISTRATO = "MesNonReg";

    public static final String MESS_NON_CANCELLATO_UNO = "MesNonCanUno";

    public static final String MESS_NON_CANCELLATO_MOLTI = "MesNonCanMol";

    public static final String MESS_NON_RECORDS_DA_CANCELLARE = "MesNonRecCan";

    public static final String MESS_NUOVO = "MesGio";

    public static final String MESSAGGIO_CONFERMA_CANCELLAZIONE = "MessaggioConfermaCancellazione";

    public static final String MESSAGGIO_CONFERMA_REGISTRAZIONE = "MessaggioConfermaRegistrazione";

    public static final String MESSAGGIO_RECORD_NON_REGISTRABILE = "MessaggioRecordNonRegistrabile";

    public static final String MESSAGGIO_USCITA_RECORD_NON_REGISTRABILE =
            "MessaggioUscitaRecordNonRegistrabile";

    public static final String MESSAGGIO_COMPLETA_RECORD_NON_REGISTRABILE =
            "MessaggioCompletaRecordNonRegistrabile";

}// fine della interfaccia
