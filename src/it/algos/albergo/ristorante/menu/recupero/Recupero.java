/**
 * Title:     Recupero
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      23-mar-2005
 */
package it.algos.albergo.ristorante.menu.recupero;

import it.algos.albergo.ristorante.Ristorante;
import it.algos.albergo.ristorante.categoria.Categoria;
import it.algos.albergo.ristorante.fisso.Fisso;
import it.algos.albergo.ristorante.lingua.Lingua;
import it.algos.albergo.ristorante.menu.Menu;
import it.algos.albergo.ristorante.modifica.Modifica;
import it.algos.albergo.ristorante.piatto.Piatto;
import it.algos.albergo.ristorante.righemenuordini.RMO;
import it.algos.albergo.ristorante.righemenuordini.RMONavInMenu;
import it.algos.albergo.ristorante.righemenupiatto.RMP;
import it.algos.albergo.ristorante.righemenutavolo.RMT;
import it.algos.albergo.ristorante.righetavoloordini.RTO;
import it.algos.albergo.ristorante.sala.Sala;
import it.algos.albergo.ristorante.tavolo.Tavolo;
import it.algos.base.campo.base.Campo;
import it.algos.base.database.Db;
import it.algos.base.database.DbFactory;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.database.connessione.ConnessioneJDBC;
import it.algos.base.database.dati.Dati;
import it.algos.base.database.util.Operatore;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.messaggio.MessaggioAvviso;
import it.algos.base.modulo.Modulo;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.Query;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.modifica.QueryDelete;
import it.algos.base.query.modifica.QueryInsert;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.query.selezione.QuerySelezione;
import it.algos.base.wrapper.CampoValore;

import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Recupero dati da Postgres
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 23-mar-2005 ore 9.43.32
 */
public abstract class Recupero extends Object {

    private static final int ANNULLA = 0;

    private static final int CATEGORIA = 1;

    private static final int FISSO = 2;

    private static final int LINGUA = 3;

    private static final int MENU = 4;

    private static final int MODIFICA = 5;

    private static final int PIATTO = 6;

    private static final int RMPIATTO = 7;

    private static final int RMTAVOLO = 8;

    private static final int SALA = 9;

    private static final int TAVOLO = 10;

    private static final int CREA_RMO = 11;

    private static final int CREA_COMANDE = 12;

    private static final int REGOLA_COMANDE = 13;

    private static final int AUTO = 14;

    private static final Object[] valori =
            {"Annulla",
                    "Categoria",
                    "Fisso",
                    "Lingua",
                    "Menu",
                    "Modifica",
                    "Piatto",
                    "RMP",
                    "RMT",
                    "Sala",
                    "Tavolo",
                    "RMO",
                    "Comande1",
                    "Comande2",
                    "Auto"};

    private static Modulo moduloCategoria = Progetto.getModulo(Categoria.NOME_MODULO);

    private static Modulo moduloFisso = Progetto.getModulo(Fisso.NOME_MODULO);

    private static Modulo moduloLingua = Progetto.getModulo(Lingua.NOME_MODULO);

    private static Modulo moduloMenu = Progetto.getModulo(Menu.NOME_MODULO);

    private static Modulo moduloModifica = Progetto.getModulo(Modifica.NOME_MODULO);

    private static Modulo moduloPiatto = Progetto.getModulo(Piatto.NOME_MODULO);

    private static Modulo moduloRMO = Progetto.getModulo(RMO.NOME_MODULO);

    private static Modulo moduloRMP = Progetto.getModulo(RMP.NOME_MODULO);

    private static Modulo moduloRMT = Progetto.getModulo(RMT.NOME_MODULO);

    private static Modulo moduloRTO = Progetto.getModulo(RTO.NOME_MODULO);

    private static Modulo moduloSala = Progetto.getModulo(Sala.NOME_MODULO);

    private static Modulo moduloTavolo = Progetto.getModulo(Tavolo.NOME_MODULO);

    private static Modulo moduloRTOOld = Progetto.getModulo(RTOOld.NOME_MODULO);

    private static Db dbSource = null;

    private static Connessione connSource = null;

    private static String host = "localhost";   // dov'e' il db postgres


    /**
     * Presenta un dialogo per la selezione dell'archivio da recuperare.
     * <p/>
     */
    public static void apriDialogo() {
        /* variabili e costanti locali di lavoro */
        int scelta = 0;
        String unTitolo = "";

        try {    // prova ad eseguire il codice

            if (Progetto.isEsisteModulo(Ristorante.MODULO_RTO_OLD)) {
                unTitolo = "Archivio da recuperare";
                scelta = JOptionPane.showOptionDialog(null,
                        "",
                        unTitolo,
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        valori,
                        valori[0]);

                esegui(scelta);
            } else {
                new MessaggioAvviso(
                        "Per effettuare il recupero bisogna attivare il server\nPostgres prima di lanciare il programma.");
            }// fine del blocco if-else

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    }


    /**
     * Recupera l'archivio selezionato.
     * <p/>
     */
    public static void esegui(int scelta) {
        /* variabili e costanti locali di lavoro */
        String com = "";
        ArrayList esclusi = null;
        ConnessioneJDBC connSource = null;
        Filtro filtro = null;

        try {    // prova ad eseguire il codice

            if (scelta != ANNULLA) {
                dbSource = DbFactory.crea(Db.SQL_POSTGRES);
                dbSource.setHost(host);
                dbSource.inizializza();
                connSource = (ConnessioneJDBC)dbSource.creaConnessione();
                connSource.open();
            }// fine del blocco if

            switch (scelta) {
                case ANNULLA:     // annulla
                    break;

                case CATEGORIA:
                    try { // prova ad eseguire il codice
                        com = "ALTER TABLE categoria RENAME COLUMN descrizione TO italiano";
                        connSource.getConnection().createStatement().executeUpdate(com);
                        com = "ALTER TABLE categoria RENAME COLUMN nometedesco TO tedesco";
                        connSource.getConnection().createStatement().executeUpdate(com);
                        com = "ALTER TABLE categoria RENAME COLUMN nomeinglese TO inglese";
                        connSource.getConnection().createStatement().executeUpdate(com);
                        com = "ALTER TABLE categoria RENAME COLUMN nomefrancese TO francese";
                        connSource.getConnection().createStatement().executeUpdate(com);
                    } catch (Exception unErrore) { // intercetta l'errore
                    }// fine del blocco try-catch

                    esclusi = recuperaCampi(moduloCategoria, "contorno");
                    recuperaDati(moduloCategoria, esclusi);

                    /* accende il flag Contorno sul record contorno */
                    Campo campoCatContorno = moduloCategoria.getCampo(Categoria.CAMPO_CONTORNO);
                    Campo campoCatSigla = moduloCategoria.getCampo(Categoria.CAMPO_SIGLA);
                    filtro = FiltroFactory.crea(campoCatSigla, "contorni");
                    int chiave = moduloCategoria.query().valoreChiave(filtro);
                    if (chiave != 0) {
                        moduloCategoria.query().registraRecordValore(chiave,
                                campoCatContorno,
                                new Boolean(true));
                    }// fine del blocco if
                    break;

                case FISSO:
                    recuperaDati(moduloFisso);
                    break;

                case LINGUA:
                    try { // prova ad eseguire il codice
                        connSource.getConnection().createStatement().executeUpdate(com);
                        com = "ALTER TABLE lingua RENAME COLUMN descrizione TO nome";
                        connSource.getConnection().createStatement().executeUpdate(com);
                        com =
                                "ALTER TABLE lingua RENAME COLUMN nominuscolocontorno TO contornominuscolo";
                        connSource.getConnection().createStatement().executeUpdate(com);
                    } catch (Exception unErrore) { // intercetta l'errore
                    }// fine del blocco try-catch                   com ="ALTER TABLE categoria RENAME COLUMN descrizione TO italiano" ;

                    esclusi = recuperaCampi(moduloLingua, "surgelati");
                    recuperaDati(moduloLingua, esclusi);
                    break;

                case MENU:
                    recuperaDati(moduloMenu);
                    break;

                case MODIFICA:
                    recuperaDati(moduloModifica);
                    break;

                case PIATTO:
                    esclusi = recuperaCampi(moduloPiatto, "congelato");
                    recuperaDati(moduloPiatto, esclusi);
                    break;

                case RMPIATTO:

                    esclusi = recuperaCampi(moduloRMP, "congelatopiatto,congelatocontorno");
                    recuperaDati(moduloRMP, esclusi);

                    /* dopo il recupero, elimina tutte le righe che
                     * non hanno linkpiatto valorizzato */
                    filtro = FiltroFactory.crea(RMP.CAMPO_PIATTO, 0);
                    moduloRMP.query().eliminaRecords(filtro);

                    break;

                case RMTAVOLO:
                    recuperaDati(moduloRMT);

                    /* dopo il recupero, elimina tutte le righe che
                     * non hanno un link valido a menu e a tavolo */
                    filtro = FiltroFactory.crea(RMT.Cam.menu.get(), 0);
                    moduloRMT.query().eliminaRecords(filtro);
                    filtro = FiltroFactory.crea(RMT.Cam.tavolo.get(), 0);
                    moduloRMT.query().eliminaRecords(filtro);

                    break;

                case SALA:
                    recuperaDati(moduloSala);
                    break;

                case TAVOLO:
                    recuperaDati(moduloTavolo);
                    break;

                case CREA_RMO:
                    creaRMO();
                    break;

                case CREA_COMANDE:
                    creaComande();
                    break;

                case REGOLA_COMANDE:
                    regolaComande();
                    break;

                case AUTO:
                    esegui(SALA);
                    esegui(TAVOLO);

                    esegui(LINGUA);
                    esegui(MODIFICA);

                    esegui(CATEGORIA);
                    esegui(PIATTO);
                    esegui(FISSO);

                    esegui(MENU);
                    esegui(RMPIATTO);
                    esegui(RMTAVOLO);

                    esegui(CREA_RMO);

                    esegui(CREA_COMANDE);
                    esegui(REGOLA_COMANDE);

                    break;
                default: // caso non definito
                    break;
            } // fine del blocco switch

            if (connSource != null) {
                if (connSource.isOpen()) {
                    connSource.close();
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo */


    /**
     * Recupero Modifiche.
     * <p/>
     */
    private static ArrayList recuperaCampi(Modulo modulo, String nomiEsclusi) {
        /* variabili e costanti locali di lavoro */
        ArrayList campi = null;
        ArrayList listaNomi = null;
        String nomeCampo = "";
        Campo campo = null;

        try {    // prova ad eseguire il codice
            campi = new ArrayList();

            listaNomi = Lib.Array.creaLista(nomiEsclusi);

            for (int k = 0; k < listaNomi.size(); k++) {
                nomeCampo = (String)listaNomi.get(k);
                campo = modulo.getCampo(nomeCampo);
                campi.add(campo);
            } // fine del ciclo for

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return campi;
    }


    /**
     * Recupero Modifiche.
     * <p/>
     */
    private static void recuperaDati(Modulo modulo) {
        try {    // prova ad eseguire il codice

            recuperaDati(modulo, null);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Recupero Modifiche.
     * <p/>
     */
    private static void recuperaDati(Modulo modulo, ArrayList campiEsclusi) {
        /* variabili e costanti locali di lavoro */
        Query query = null;
        Dati dati = null;
        Campo campo = null;
        Object valore = null;
        HashMap mappa = null;
        ArrayList campi = null;

        try {    // prova ad eseguire il codice

            /* recupera la lista dei campi da recuperare */
            mappa = modulo.getModello().getCampiFisici();
            campi = Libreria.hashMapToArrayList(mappa);

            if (campiEsclusi != null) {
                for (int k = 0; k < campiEsclusi.size(); k++) {
                    campo = (Campo)campiEsclusi.get(k);
                    campi.remove(campo);
                } // fine del ciclo for

            }// fine del blocco if

            dbSource = DbFactory.crea(Db.SQL_POSTGRES);
            dbSource.setHost(host);
            dbSource.inizializza();
            connSource = dbSource.creaConnessione();
            connSource.open();

            /* elimina tutti i dati attualmente presenti */
            eliminaDati(modulo);

            query = new QuerySelezione(modulo);
            query.setCampi(campi);
            query.addOrdine(modulo.getCampoOrdine());
            dati = connSource.querySelezione(query);

            for (int k = 0; k < dati.getRowCount(); k++) {

                query = new QueryInsert(modulo);

                for (int j = 0; j < dati.getColumnCount(); j++) {
                    valore = dati.getValueAt(k, j);
                    campo = (Campo)campi.get(j);
                    query.addCampo(campo, valore);
                } // fine del ciclo for

                modulo.query().queryModifica(query);

            } // fine del ciclo for

            dati.close();
            connSource.close();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Elimina tutti i dati di un modulo.
     * <p/>
     */
    private static void eliminaDati(Modulo modulo) {
        /* variabili e costanti locali di lavoro */
        Query query = null;

        try {    // prova ad eseguire il codice
            query = new QueryDelete(modulo);
            modulo.query().queryModifica(query);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Crea le RMO in base alle RMP per ogni RMT esistente.
     * Crea le RMO relative agli Extra analizzando le RMT di Postgres
     * <p/>
     */
    private static void creaRMO() {
        /* variabili e costanti locali di lavoro */
        int quanteRMO = 0;
        Filtro filtro = null;

        try {    // prova ad eseguire il codice

            /* crea le RMO base */
            quanteRMO = moduloRMO.query().contaRecords();
            if (quanteRMO == 0) {
                creaRMOBase();
            }// fine del blocco if

            /* crea le RMO Extra */
            filtro = FiltroFactory.crea(RMO.CAMPO_PIATTO_EXTRA, Operatore.DIVERSO, 0);
            quanteRMO = moduloRMO.query().contaRecords(filtro);
            if (quanteRMO == 0) {
                creaRMOExtra();
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Crea le RMO in base alle RMP per ogni RMT esistente.
     * <p/>
     */
    private static void creaRMOBase() {
        /* variabili e costanti locali di lavoro */
        int[] chiaviRMT = null;
        int chiaveRMT = 0;
        RMONavInMenu navRMO = null;
        Ordine ordine = null;

        try {    // prova ad eseguire il codice
            navRMO = (RMONavInMenu)moduloRMO.getNavigatore(RMO.NAV_IN_MENU);
            ordine = new Ordine();
            ordine.add(moduloRMT.getCampoChiave());
            chiaviRMT = moduloRMT.query().valoriChiave(ordine);
            for (int k = 0; k < chiaviRMT.length; k++) {
                chiaveRMT = chiaviRMT[k];

                System.out.println(k + "/" + chiaviRMT.length);

                navRMO.setValorePilota(chiaveRMT);
                navRMO.creaRMO();

            } // fine del ciclo for

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Analizza le RMT su Postgres e crea le RMO extra.
     * <p/>
     */
    private static void creaRMOExtra() {
        /* variabili e costanti locali di lavoro */
        Query query = null;
        Filtro filtro = null;
        Filtro filtro2 = null;
        Dati datiRTOPG = null;
        Campo campoLinkExtraPiatto = null;
        Campo campoLinkExtraContorno = null;
        int linkRMT = 0;
        int linkExtraPiatto = 0;
        int linkExtraContorno = 0;
        RMONavInMenu navRMO = null;
        int codice = 0;
        int quanti = 0;
        boolean continua = true;

        try {    // prova ad eseguire il codice

            campoLinkExtraPiatto = moduloRMO.getCampo(RMO.CAMPO_PIATTO_EXTRA);
            campoLinkExtraContorno = moduloRMO.getCampo(RMO.CAMPO_CONTORNO_EXTRA);

            /* crea un oggetto dati con le RTO Extra da Postgres */
            query = new QuerySelezione(moduloRTOOld);
            query.addCampo(RTOOld.CAMPO_LINKRMT);
            query.addCampo(RTOOld.CAMPO_LINK_EXTRAPIATTO);
            query.addCampo(RTOOld.CAMPO_LINK_EXTRACONTORNO);
            filtro = FiltroFactory.crea(RTOOld.CAMPO_FLAG_EXTRA, true);
            query.setFiltro(filtro);
            datiRTOPG = moduloRTOOld.query().querySelezione(query);

            navRMO = (RMONavInMenu)moduloRMO.getNavigatore(RMO.NAV_IN_MENU);

            for (int k = 0; k < datiRTOPG.getRowCount(); k++) {

                System.out.println((k + 1) + "/" + datiRTOPG.getRowCount());

                linkRMT = datiRTOPG.getIntAt(k, 0);
                linkExtraPiatto = datiRTOPG.getIntAt(k, 1);
                linkExtraContorno = datiRTOPG.getIntAt(k, 2);

                continua = true;

                /* controlla che il link rmt sia valido ed esista */
                if (continua) {
                    continua = false;
                    if (linkRMT != 0) {
                        if (moduloRMT.query().isEsisteRecord(linkRMT)) {
                            continua = true;
                        }// fine del blocco if
                    }// fine del blocco if
                }// fine del blocco if

                /* controlla che esista almeno un elemento della coppia
                 * linkextrapiatto / linkextracontorno */
                if (continua) {
                    continua = false;
                    if ((linkExtraPiatto != 0) || (linkExtraContorno != 0)) {
                        continua = true;
                    }// fine del blocco if
                }// fine del blocco if

                /* controlla che il record non sia già esistente.
                 * Nella vecchia versione non si poteva registrare una
                 * quantità diversa da 1 negli extra, perciò si creavano
                 * più RTO uguali per lo stesso extra. Ora si crea una sola
                 * RMO con l'extra e si registra la quantità desiderata. */
                if (continua) {
                    continua = false;
                    filtro2 = new Filtro();
                    filtro = FiltroFactory.crea(RMO.CAMPO_RIGA_MENU_TAVOLO, linkRMT);
                    filtro2.add(filtro);
                    filtro = FiltroFactory.crea(RMO.CAMPO_PIATTO_EXTRA, linkExtraPiatto);
                    filtro2.add(filtro);
                    filtro = FiltroFactory.crea(RMO.CAMPO_CONTORNO_EXTRA, linkExtraContorno);
                    filtro2.add(filtro);
                    quanti = moduloRMO.query().contaRecords(filtro2);
                    if (quanti == 0) {
                        continua = true;
                    }// fine del blocco if
                }// fine del blocco if

                /* crea il record e lo regola */
                if (continua) {
                    navRMO.setValorePilota(linkRMT);
                    codice = navRMO.creaRecord();
                    moduloRMO.query().registraRecordValore(codice,
                            campoLinkExtraPiatto,
                            new Integer(linkExtraPiatto));
                    moduloRMO.query().registraRecordValore(codice,
                            campoLinkExtraContorno,
                            new Integer(linkExtraContorno));
                }// fine del blocco if

            } // fine del ciclo for

            datiRTOPG.close();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Analizza le RMT su Postgres e crea le comande.
     * <p/>
     */
    private static void creaComande() {
        /* variabili e costanti locali di lavoro */
        Query query = null;
        Dati dati = null;

        int linkRMT = 0;
        int linkRMP = 0;
        int linkExtraPiatto = 0;
        int linkExtraContorno = 0;
        int linkModifica = 0;
        boolean anticipo = false;

        try {    // prova ad eseguire il codice

            query = new QuerySelezione(moduloRTOOld);
            query.addCampo(RTOOld.CAMPO_LINKRMT);
            query.addCampo(RTOOld.CAMPO_LINKRMP);
            query.addCampo(RTOOld.CAMPO_LINK_EXTRAPIATTO);
            query.addCampo(RTOOld.CAMPO_LINK_EXTRACONTORNO);
            query.addCampo(RTOOld.CAMPO_LINKMODIFICA);
            query.addCampo(RTOOld.CAMPO_FLAG_ANTICIPO);
            query.addOrdine(moduloRTOOld.getCampoChiave());
            dati = moduloRTOOld.query().querySelezione(query);

            for (int k = 0; k < dati.getRowCount(); k++) {

                System.out.println((k + 1) + "/" + dati.getRowCount());

                linkRMT = dati.getIntAt(k, 0);
                linkRMP = dati.getIntAt(k, 1);
                linkExtraPiatto = dati.getIntAt(k, 2);
                linkExtraContorno = dati.getIntAt(k, 3);
                linkModifica = dati.getIntAt(k, 4);
                anticipo = dati.getBoolAt(k, 5);
                creaRTO(linkRMT,
                        linkRMP,
                        linkExtraPiatto,
                        linkExtraContorno,
                        linkModifica,
                        anticipo);
            } // fine del ciclo for

            dati.close();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Crea una RTO.
     * <p/>
     *
     * @param linkRMT il codice della RMT
     * @param linkRMP il codice della RMP
     * @param linkExtraPiatto il codice del piatto extra
     * @param linkExtraContorno il codice del contorno extra
     * @param linkModifica il codice della modifica
     * @param anticipo il flag di anticipo
     */
    private static void creaRTO(int linkRMT,
                                int linkRMP,
                                int linkExtraPiatto,
                                int linkExtraContorno,
                                int linkModifica,
                                boolean anticipo) {
        /* variabili e costanti locali di lavoro */
        boolean continua = true;
        int codiceRMO = 0;

        ArrayList campiValore = null;
        CampoValore cv = null;

        Campo campoRTOlinkRMO = null;
        Campo campoRTOlinkModifica = null;
        Campo campoRTOanticipo = null;

        try {    // prova ad eseguire il codice

            campoRTOlinkRMO = moduloRTO.getCampo(RTO.CAMPO_RMORDINI);
            campoRTOlinkModifica = moduloRTO.getCampo(RTO.CAMPO_MODIFICA);
            campoRTOanticipo = moduloRTO.getCampo(RTO.CAMPO_ANTICIPO);
            campiValore = new ArrayList();

            /* controlla che la RMT sia valida ed esista */
            if (continua) {
                continua = false;
                if (linkRMT != 0) {
                    if (moduloRMT.query().isEsisteRecord(linkRMT)) {
                        continua = true;
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

            /* controlla che esista il link a RMP oppure almeno un elemento
             * della coppia linkExtraPiatto / linkExtraContorno */
            if (continua) {
                continua = false;
                if (linkRMP != 0) {
                    continua = true;
                } else {
                    if ((linkExtraPiatto != 0) || (linkExtraContorno != 0)) {
                        continua = true;
                    }// fine del blocco if
                }// fine del blocco if-else
            }// fine del blocco if

            /* recupera il codice della RMO di competenza
             * controlla che la RMO sia valida ed esista */
            if (continua) {
                continua = false;
                codiceRMO = getCodiceRMO(linkRMT, linkRMP, linkExtraPiatto, linkExtraContorno);
                if (codiceRMO != 0) {
                    if (moduloRMO.query().isEsisteRecord(codiceRMO)) {
                        continua = true;
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

            /* crea un nuovo record su RTO e lo regola */
            if (continua) {
                campiValore.clear();
                cv = new CampoValore(campoRTOlinkRMO, new Integer(codiceRMO));
                campiValore.add(cv);
                cv = new CampoValore(campoRTOlinkModifica, new Integer(linkModifica));
                campiValore.add(cv);
                cv = new CampoValore(campoRTOanticipo, new Boolean(anticipo));
                campiValore.add(cv);
                moduloRTO.query().nuovoRecord(campiValore);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Recupera il codice della RMO di competenza di una data RTO.
     * <p/>
     *
     * @param linkRMT il codice della RMT
     * @param linkRMP il codice della RMP
     * @param linkExtraPiatto il codice del piatto extra
     * @param linkExtraContorno il codice del contorno extra
     *
     * @return il codice della RMO
     */
    private static int getCodiceRMO(int linkRMT,
                                    int linkRMP,
                                    int linkExtraPiatto,
                                    int linkExtraContorno) {

        /* variabili e costanti locali di lavoro */
        int codiceRMO = 0;
        Query query = null;
        Filtro filtro = null;
        Filtro filtroTotale = null;
        Dati dati = null;

        try {    // prova ad eseguire il codice
            query = new QuerySelezione(moduloRMO);
            query.addCampo(moduloRMO.getCampoChiave());

            filtroTotale = FiltroFactory.crea(RMO.CAMPO_RIGA_MENU_TAVOLO, linkRMT);

            /* se ha un link a RMP, cerca la RMO con tale link a RMP.
             * se non ha link a RMP, cerca la RMO con pari piatto e contorno */
            if (linkRMP != 0) {
                filtro = FiltroFactory.crea(RMO.CAMPO_RIGA_MENU_PIATTO, linkRMP);
                filtroTotale.add(filtro);
            } else {
                filtro = FiltroFactory.crea(RMO.CAMPO_PIATTO_EXTRA, linkExtraPiatto);
                filtroTotale.add(filtro);
                filtro = FiltroFactory.crea(RMO.CAMPO_CONTORNO_EXTRA, linkExtraContorno);
                filtroTotale.add(filtro);
            }// fine del blocco if-else

            query.setFiltro(filtroTotale);
            dati = moduloRMO.query().querySelezione(query);
            codiceRMO = dati.getIntAt(0, 0);

            dati.close();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codiceRMO;
    }


    /**
     * Regola le comande (RMO e RTO).
     * <p/>
     * Spazzola le RMO
     * Assegna i numeri d'ordine in sequenza alle RTO
     * Assegna la quantita' alla RMO
     */
    private static void regolaComande() {
        /* variabili e costanti locali di lavoro */
        Query queryRMO = null;
        Dati datiRMO = null;

        Query queryRTO = null;
        Filtro filtro = null;
        Dati datiRTO = null;

        int codiceRMO = 0;
        int codiceRTO = 0;

        Campo campoRMOQta = null;
        int qta = 0;

        try {    // prova ad eseguire il codice

            campoRMOQta = moduloRMO.getCampo(RMO.CAMPO_QUANTITA);

            queryRMO = new QuerySelezione(moduloRMO);
            queryRMO.addCampo(moduloRMO.getCampoChiave());
            queryRMO.addOrdine(moduloRMO.getCampoChiave());
            datiRMO = moduloRMO.query().querySelezione(queryRMO);

            for (int k = 0; k < datiRMO.getRowCount(); k++) {

                System.out.println((k + 1) + "/" + datiRMO.getRowCount());

                codiceRMO = datiRMO.getIntAt(k, 0);

                /* recupero tutte le RTO della RMO */
                queryRTO = new QuerySelezione(moduloRTO);
                queryRTO.addCampo(moduloRTO.getCampoChiave());

                filtro = FiltroFactory.crea(RTO.CAMPO_RMORDINI, codiceRMO);
                queryRTO.setFiltro(filtro);
                queryRTO.addOrdine(moduloRTO.getCampoChiave());
                datiRTO = moduloRTO.query().querySelezione(queryRTO);

                /* spazzola le RTO e riassegna il campo Ordine partendo da 1 */
                for (int j = 0; j < datiRTO.getRowCount(); j++) {
                    codiceRTO = datiRTO.getIntAt(j, 0);
                    moduloRTO.query().registraRecordValore(codiceRTO,
                            moduloRTO.getCampoOrdine(),
                            new Integer(j + 1));
                } // fine del ciclo for

                qta = datiRTO.getRowCount();
                datiRTO.close();

                /* assegna la quantità alla riga RMO */
                moduloRMO.query().registraRecordValore(codiceRMO, campoRMOQta, new Integer(qta));

            } // fine del ciclo for

            datiRMO.close();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }

}// fine della classe
