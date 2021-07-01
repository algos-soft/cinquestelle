/**
 * Title:        DatiDettaglio.java
 * Package:      it.algos.albergo.ristorante.menu.stampa.cucina
 * Description:
 * Copyright:    Copyright (c) 2002, 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /
 * Creato:       il 7 novembre 2003 alle 13.36
 */

package it.algos.albergo.ristorante.menu.stampa.cucina;

import it.algos.albergo.ristorante.Ristorante;
import it.algos.albergo.ristorante.lingua.LinguaModulo;
import it.algos.albergo.ristorante.menu.Menu;
import it.algos.albergo.ristorante.modifica.Modifica;
import it.algos.albergo.ristorante.modifica.ModificaModulo;
import it.algos.albergo.ristorante.piatto.Piatto;
import it.algos.albergo.ristorante.righemenuordini.RMO;
import it.algos.albergo.ristorante.righemenupiatto.RMP;
import it.algos.albergo.ristorante.righemenutavolo.RMT;
import it.algos.albergo.ristorante.righetavoloordini.RTO;
import it.algos.albergo.ristorante.tavolo.Tavolo;
import it.algos.base.campo.base.Campo;
import it.algos.base.costante.CostanteDB;
import it.algos.base.database.dati.Dati;
import it.algos.base.database.util.Operatore;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Libreria;
import it.algos.base.messaggio.MessaggioAvviso;
import it.algos.base.modulo.Modulo;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.Query;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.query.selezione.QuerySelezione;
import it.algos.base.wrapper.DueInt;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Questa classe concreta e' responsabile di: <br>
 * A - Recuperare e mantenere tutti i dati per la stampa dettagliata
 * degli ordini per la cucina <br>
 *
 * @author Guido Andrea Ceresa
 * @author Alessandro Valbonesi
 * @author alex
 * @version 1.0  /  7 novembre 2003 ore 13.36
 */
public final class DatiDettaglio extends Object implements CostanteDB {

    /**
     * il codice del menu
     */
    private int codiceMenu = 0;

    /**
     * la congiunzione tra piatto e contorno
     */
    private String congiunzione = null;

    /* quanti tavoli hanno gia' ordinato */
    private int quantiTavoliOrdinati = 0;

    /* quanti piatti extra sono stati ordinati in tutto */
    private int quantiOrdiniExtra = 0;

    /* quanti piatti con modifica sono stati ordinati in tutto */
    private int quanteModifiche = 0;

    /**
     * moduli e modelli di uso comune
     */
    private Modulo moduloMenu = null;

    private LinguaModulo moduloLingua = null;

    private Modulo moduloRMP = null;

    private Modulo moduloPiatto = null;

    private Modulo moduloCategoria = null;

    private Modulo moduloRTO = null;

    private Modulo moduloRMT = null;

    private Modulo moduloRMO = null;

    private Modulo moduloTavolo = null;

    private ModificaModulo moduloModifica = null;

    /**
     * l'elenco completo delle righe da stampare (oggetti RigaDettaglio)
     */
    private ArrayList elencoRighe = new ArrayList();

    /**
     * l'elenco delle righe da menu (oggetti RigaDettaglio)
     */
    private ArrayList elencoRigheMenu = new ArrayList();

    /**
     * l'elenco delle righe da extra (oggetti RigaDettaglio)
     */
    private ArrayList elencoRigheExtra = new ArrayList();

    /**
     * l'elenco delle righe da variante (oggetti RigaDettaglio)
     */
    private ArrayList elencoRigheVariante = new ArrayList();

    /**
     * l'elenco dei codici dei tavoli apparecchiati per
     * questo menu (oggetti Integer)
     */
    private ArrayList elencoTavoli = new ArrayList();

    /**
     * la data del menu
     */
    private String dataMenu = "";

    /**
     * il pasto del menu
     */
    private String pastoMenu = "";


    /**
     * Costruttore completo
     * <p/>
     *
     * @param unCodiceMenu il codice del menu per il quale si vogliono recuperare i dati
     */
    public DatiDettaglio(int unCodiceMenu) {
        /* rimanda al costruttore della superclasse */
        super();

        /* regolazioni iniziali di riferimenti e variabili */
        try {                                   // prova ad eseguire il codice
            this.inizia(unCodiceMenu);
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore completo */


    /**
     * Regolazioni iniziali di riferimenti e variabili
     * <p/>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @param unCodiceMenu il codice del menu
     */
    private void inizia(int unCodiceMenu) throws Exception {

        /* regola le variabili lavoro della classe */
        this.regolaVariabili(unCodiceMenu);

        /* recupera tutti i dati del menu */
        this.recuperaDatiMenu();

    } /* fine del metodo inizia */


    /**
     * Regola le variabili di lavoro della classe
     *
     * @param unCodiceMenu il codice del menu
     */
    private void regolaVariabili(int unCodiceMenu) {
        this.codiceMenu = unCodiceMenu;

        /* regolazioni moduli e modelli di uso comune */
        this.moduloMenu = Progetto.getModulo(Ristorante.MODULO_MENU);
        this.moduloLingua = (LinguaModulo)Progetto.getModulo(Ristorante.MODULO_LINGUA);
        this.moduloRMP = Progetto.getModulo(Ristorante.MODULO_RIGHE_PIATTO);
        this.moduloPiatto = Progetto.getModulo(Ristorante.MODULO_PIATTO);
        this.moduloCategoria = Progetto.getModulo(Ristorante.MODULO_CATEGORIA);
        this.moduloRTO = Progetto.getModulo(Ristorante.MODULO_RIGHE_ORDINI);
        this.moduloRMT = Progetto.getModulo(Ristorante.MODULO_RIGHE_TAVOLO);
        this.moduloRMO = Progetto.getModulo(RMO.NOME_MODULO);
        this.moduloTavolo = Progetto.getModulo(Ristorante.MODULO_TAVOLO);
        this.moduloModifica = (ModificaModulo)Progetto.getModulo(Modifica.NOME_MODULO);

        /* recupera la congiunzione */
        this.congiunzione = moduloLingua.getLogica().getCongiunzione();

    } /* fine del metodo */


    /**
     * Recupera tutti i dati del menu
     */
    private void recuperaDatiMenu() {

        try {                                   // prova ad eseguire il codice
            /* recupera i dati generali */
            this.recuperaDatiGenerali();

            /* recupera l'elenco dei tavoli */
            this.recuperaTavoli();

            /* recupera le righe di dettaglio */
            this.recuperaDatiDettaglio();

            /* recupera il numero di tavoli che hanno ordinato */
            this.quantiTavoliOrdinati = this.quantiTavoliOrdinati();

            /* recupera il numero di ordini sui piatti extra */
            this.quantiOrdiniExtra = this.quantiOrdiniExtra();

            /* recupera il numero di modifiche sui piatti da menu o extra */
            this.quanteModifiche = this.quanteModifiche();

        } catch (Exception unErrore) {           // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

    } /* fine del metodo */


    /**
     * Recupera l'elenco dei codici dei tavoli apparecchiati per il menu
     * ordinati per numero del tavolo
     */
    private void recuperaTavoli() {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = null;
        Ordine ordine = null;
        Campo campoTavoloNumero = null;

        try {

            /* regolazioni */
            campoTavoloNumero = moduloTavolo.getCampo(Tavolo.Cam.numtavolo);

            /* crea un filtro per selezionare i tavoli del menu */
            filtro = FiltroFactory.crea(RMT.Cam.menu.get(), this.codiceMenu);

            /* crea un ordine per numero di tavolo */
            ordine = new Ordine();
            ordine.add(campoTavoloNumero);

            /* recupera la lista dei tavoli */
            elencoTavoli = moduloRMT.query().valoriCampo(RMT.Cam.tavolo.get(), filtro, ordine);

        } catch (Exception unErrore) {
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

    } /* fine del metodo */


    /**
     * Recupera l'elenco dei tavoli che hanno gia' ordinato
     * o che non hanno ancora ordinato.
     * <p/>
     *
     * @param ordinato true per i tavoli che hanno gia' ordinato
     * false per i tavoli che non hanno ancora ordinato
     *
     * @return l'elenco dei codici dei tavoli (oggetti Integer)
     */
    private ArrayList elencoTavoliOrdinati(boolean ordinato) {
        /* variabili e costanti locali di lavoro */
        ArrayList elencoTavoli = null;
        Filtro filtro = null;

        try {    // prova ad eseguire il codice

            filtro = FiltroFactory.crea(RMT.Cam.menu.get(), this.codiceMenu);
            filtro.add(FiltroFactory.crea(RMT.Cam.comandato.get(), ordinato));
            elencoTavoli = moduloRMT.query().valoriCampo(RMT.Cam.tavolo.get(), filtro);

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return elencoTavoli;
    } // fine del metodo


    /**
     * Recupera il numero di tavoli che hanno gia' ordinato.
     * <p/>
     *
     * @return il numero di tavoli che hanno gia' ordinato
     */
    private int quantiTavoliOrdinati() {
        return (elencoTavoliOrdinati(true)).size();
    } // fine del metodo


    /**
     * Recupera il numero totale di ordini sui piatti extra.
     * <p/>
     *
     * @return il numero di ordini sui piatti extra
     */
    private int quantiOrdiniExtra() {
        /* variabili e costanti locali di lavoro */
        int quanti = 0;
        ArrayList righeExtra = null;
        RigaDettaglio unaRiga = null;

        try {    // prova ad eseguire il codice
            /* recupera le righe degli Extra */
            righeExtra = this.getRigheDettaglioExtra();
            /* spazzola e calcola il totale ordinato */
            for (int k = 0; k < righeExtra.size(); k++) {
                unaRiga = (RigaDettaglio)righeExtra.get(k);
                quanti = quanti + unaRiga.getQuantitaTotale();
            } // fine del ciclo for

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return quanti;
    } // fine del metodo


    /**
     * Recupera il numero di modifiche ordinate sui piatti da menu o extra.<br>
     *
     * @return il numero totale di modifiche
     */
    private int quanteModifiche() {
        /* variabili e costanti locali di lavoro */
        int quante = 0;
        ArrayList righe = null;

        try {    // prova ad eseguire il codice
            /* recupera tutte le righe di dettaglio */
            righe = this.getRigheDettaglioModifica();
            /* dato che ogni riga si riferisce a in solo
            *  piatto ordinato, il totale e' pari al numero
            *  delle righe */
            quante = righe.size();
        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return quante;
    } // fine del metodo


    /**
     * Ritorna il numero di coperti corrispondenti a un
     * elenco di tavoli.
     * <p/>
     *
     * @param elencoTavoli l'elenco dei codici dei tavoli per il<br>
     * quale ritornare il numero di coperti<br>
     * (elenco di oggetti Integer)
     *
     * @return il numero di coperti corrispondenti
     */
    private int copertiTavoli(ArrayList elencoTavoli) {
        /* variabili e costanti locali di lavoro */
        int quantiCoperti = 0;
        ArrayList valori = null;

        int codTavolo = 0;
        Filtro filtro = null;

        try {    // prova ad eseguire il codice

            /* spazzola l'elenco dei tavoli e recupera i coperti */
            for (int k = 0; k < elencoTavoli.size(); k++) {

                codTavolo = Libreria.getInt(elencoTavoli.get(k));

                /* prepara un filtroOld per selezionare le RMT
                 * di questo menu e di questo tavolo (una sola)*/
                filtro = this.filtroRigheTavolo(codTavolo);

                /* recupera l'elenco dei valori del campo coperti
                 * (dovrebbe essere uno solo)
                 * Aggiunge il valore al totale */
                valori = moduloRMT.query().valoriCampo(RMT.Cam.coperti.get(), filtro);
                if (valori.size() == 1) {
                    quantiCoperti = quantiCoperti + Libreria.objToInt(valori.get(0));
                } else {
                    new MessaggioAvviso("Errore. Trovate piu' righe per lo stesso tavolo.");
                }// fine del blocco if-else

            } // fine del ciclo for

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return quantiCoperti;
    } // fine del metodo


    /**
     * Recupera i dati generali del menu
     */
    private void recuperaDatiGenerali() {
        /** variabili e costanti locali di lavoro */
        Date unaData = null;
        Object valore = null;
        int codPasto = 0;
        DateFormat df = null;

        try {                                   // prova ad eseguire il codice

            /* recupero la data del menu */
            valore = moduloMenu.query().valoreCampo(Menu.Cam.data.get(), this.codiceMenu);
            unaData = Libreria.getDate(valore);
            if (unaData != null) {
                df = new SimpleDateFormat("EEEE, d MMMM yyyy");
                this.dataMenu = df.format(unaData);
            }// fine del blocco if

            /* recupero nome del il pasto */
            codPasto = moduloMenu.query().valoreInt(Menu.Cam.pasto.get(), this.codiceMenu);
            this.pastoMenu = moduloLingua.getLogica().getNomePasto(codPasto);

        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

    } /* fine del metodo */


    /**
     * Crea l'elenco completo delle righe per la stampa
     */
    private void recuperaDatiDettaglio() {

        try {

            /* crea le righe dal Menu */
            this.creaRigheMenu();

            /* crea le righe dagli Extra */
            this.creaRigheExtra();

            /* crea le righe dalle Varianti */
            this.creaRigheVarianti();

            /* combina le righe in un pacchetto finale */
            this.combinaRighe();

            /* regola i valori delle quantita' ordinate per ogni riga */
            this.regolaValori();

        } catch (Exception unErrore) {
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

    } /* fine del metodo */


    /**
     * Crea le righe relative ai piatti in menu
     */
    private void creaRigheMenu() {

        /* variabili e costanti locali di lavoro */
        RigaDettaglio unaRiga = null;

        /* campi */
        Campo campoPiattoComandabile = moduloPiatto.getCampo(Piatto.CAMPO_COMANDA);

        /* varie */
        ArrayList listaCodiciRighe = null;

        Filtro filtro = null;
        Ordine ordine = null;
        int codRiga = 0;
        int codPiatto = 0;
        int codContorno = 0;

        try {

            /* recupera tutti i codici delle righe ordinabili di menu
             * ordinate sull' ordine pubblico del campo RMP.codice
             * (ordine categoria + ordine riga) */
            filtro = FiltroFactory.crea(RMP.CAMPO_MENU, codiceMenu);
            filtro.add(FiltroFactory.crea(campoPiattoComandabile, true));
            ordine = moduloRMP.getCampoChiave().getCampoLista().getOrdinePubblico();
            listaCodiciRighe = moduloRMP.query().valoriCampo(moduloRMP.getCampoChiave(),
                    filtro,
                    ordine);

            /* spazzola le righe di menu e crea le corrispondenti righe di stampa */
            for (int k = 0; k < listaCodiciRighe.size(); k++) {

                /* recupera il codice della riga di menu */
                codRiga = Libreria.getInt(listaCodiciRighe.get(k));

                /* recupera i dati della singola riga di menu */
                codPiatto = moduloRMP.query().valoreInt(RMP.CAMPO_PIATTO, codRiga);
                codContorno = moduloRMP.query().valoreInt(RMP.CAMPO_CONTORNO, codRiga);

                /* crea una nuova riga di base */
                unaRiga = this.creaRigaBase(RigaDettaglio.TIPO_DA_MENU, codPiatto, codContorno, 0);

                /* esegue le regolazioni specifiche per questo tipo di riga */
                unaRiga.setCodiceRMP(codRiga);

                /* aggiunge la riga alla collezione */
                this.elencoRigheMenu.add(unaRiga);

            } /* fine del blocco for */

        } catch (Exception unErrore) {
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

    } /* fine del metodo */


    /**
     * Crea le righe relative ai piatti extra
     */
    private void creaRigheExtra() {
        /** variabili e costanti locali di lavoro */
        RigaDettaglio unaRiga = null;

        /* campi */
        Campo campoRMTLinkMenu = moduloRMT.getCampo(RMT.Cam.menu);
        Campo campoCategoriaOrdine = moduloCategoria.getCampoOrdine();

        /* varie */
        DueInt coppiaPiatti = null;
        ArrayList unElencoOrdini = new ArrayList();
        ArrayList elencoOrdiniUnivoco = new ArrayList();
        Query query = null;
        Filtro filtro = null;
        Dati dati = null;
        int codPiatto = 0;
        int codContorno = 0;

        try {

            /*
             * recupera cod piatto e cod contorno di tutte le RMO relative a
             * questo menu che non hanno link a RMP,
             * ordinate per categoria.ordine
             */
            query = new QuerySelezione(moduloRMO);
            query.addCampo(RMO.CAMPO_PIATTO_EXTRA);
            query.addCampo(RMO.CAMPO_CONTORNO_EXTRA);
            filtro = FiltroFactory.crea(campoRMTLinkMenu, codiceMenu);
            filtro.add(FiltroFactory.crea(RMO.CAMPO_RIGA_MENU_PIATTO, 0));
            query.setFiltro(filtro);
            query.addOrdine(campoCategoriaOrdine);
            dati = moduloRMO.query().querySelezione(query);

            /* spazzola le righe di ordine e crea un elenco in memoria */
            for (int k = 0; k < dati.getRowCount(); k++) {
                codPiatto = dati.getIntAt(k, 0);
                codContorno = dati.getIntAt(k, 1);
                coppiaPiatti = new DueInt(codPiatto, codContorno);
                unElencoOrdini.add(coppiaPiatti);
            } /* fine del blocco for */

            /* spazzola l'elenco e ne crea uno nuovo eliminando
             * gli eventuali doppioni (piatto e contorno uguale)*/
            for (int k = 0; k < unElencoOrdini.size(); k++) {

                /* recupera la coppia */
                coppiaPiatti = (DueInt)unElencoOrdini.get(k);

                /* verifica se questa coppia esiste nell'elenco destinazione (usa equals())
                 * se non esiste lo aggiunge all'elenco */
                if (elencoOrdiniUnivoco.indexOf(coppiaPiatti) == -1) {
                    elencoOrdiniUnivoco.add(coppiaPiatti);
                } /* fine del blocco if */

            } /* fine del blocco for */

            /* spazzola l'elenco univoco, crea le righe di base
             * e le aggiunge alla collezione */
            for (int k = 0; k < elencoOrdiniUnivoco.size(); k++) {

                /* recupera la coppia */
                coppiaPiatti = (DueInt)elencoOrdiniUnivoco.get(k);
                codPiatto = coppiaPiatti.x;
                codContorno = coppiaPiatti.y;

                /* crea una nuova riga di base */
                unaRiga = this.creaRigaBase(RigaDettaglio.TIPO_EXTRA, codPiatto, codContorno, 0);

                /* esegue le regolazioni specifiche per questo tipo di riga */
                unaRiga.setCodiceExtraPiatto(codPiatto);
                unaRiga.setCodiceExtraContorno(codContorno);
                unaRiga.setExtra(true);

                /* aggiunge la riga alla collezione */
                this.elencoRigheExtra.add(unaRiga);

            } /* fine del blocco for */

            /* chiude l'oggetto dati */
            dati.close();

        } catch (Exception unErrore) {
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

    } /* fine del metodo */


    /**
     * Crea le righe relative alle modifiche
     */
    private void creaRigheVarianti() {
        /** variabili e costanti locali di lavoro */
        RigaDettaglio unaRiga = null;

        /* campi */
        Campo campoRTOLinkModifica = moduloRTO.getCampo(RTO.CAMPO_MODIFICA);
        Campo campoRMOLinkRMP = moduloRMO.getCampo(RMO.CAMPO_RIGA_MENU_PIATTO);
        Campo campoRMOcodPiattoExtra = moduloRMO.getCampo(RMO.CAMPO_PIATTO_EXTRA);
        Campo campoRMOcodContornoExtra = moduloRMO.getCampo(RMO.CAMPO_CONTORNO_EXTRA);
        Campo campoRMTLinkMenu = moduloRMT.getCampo(RMT.Cam.menu);

        /* varie */
        WrapperModifica wrapperModifica = null;
        ArrayList unElencoOrdini = new ArrayList();
        ArrayList elencoOrdiniUnivoco = new ArrayList();
        Query query = null;
        Filtro filtro = null;
        Dati dati = null;
        boolean extra = false;
        int codRTO = 0;
        int codRMP = 0;
        int codModifica = 0;
        int codPiatto = 0;
        int codContorno = 0;

        try {                                   // prova ad eseguire il codice

            /*
             * recupera i codici di tutte le righe ordine relative a questo menu
             * che hanno un link a modifica
             */
            query = new QuerySelezione(moduloRTO);
            query.addCampo(moduloRTO.getCampoChiave());     //0
            query.addCampo(campoRMOLinkRMP);                //1
            query.addCampo(campoRTOLinkModifica);           //2
            query.addCampo(campoRMOcodPiattoExtra);         //3
            query.addCampo(campoRMOcodContornoExtra);       //4
            filtro = FiltroFactory.crea(campoRMTLinkMenu, codiceMenu);
            filtro.add(FiltroFactory.crea(RTO.CAMPO_MODIFICA, Operatore.DIVERSO, 0));
            query.setFiltro(filtro);
            dati = moduloRTO.query().querySelezione(query);

            /*
             * spazzola le righe di ordine trovate e crea un elenco in memoria
             * contenente i codici di riga ordine, piatto, contorno e modifica
             */
            for (int k = 0; k < dati.getRowCount(); k++) {

                /* recupera il codice della RMP */
                codRTO = dati.getIntAt(k, 0);

                /* recupera il codice della RMP */
                codRMP = dati.getIntAt(k, 1);

                /* recupera il flag Extra */
                if (codRMP == 0) {
                    extra = true;
                } else {
                    extra = false;
                }// fine del blocco if-else

                /* recupera il codice della modifica */
                codModifica = dati.getIntAt(k, 2);

                /*
                 * verifica se si tratta di comanda da menu o extra
                 * usa i codici di piatto e contorno recuperandoli dai
                 * campi appropriati
                 */
                if (extra == false) {
                    /* recupera i codici piatto e contorno dalla tavola RMP */
                    codPiatto = moduloRMP.query().valoreInt(RMP.CAMPO_PIATTO, codRMP);
                    codContorno = moduloRMP.query().valoreInt(RMP.CAMPO_CONTORNO, codRMP);
                } else {    // comanda di Extra
                    /* recupera i codici piatto e contorno dalla tavola RTO */
                    codPiatto = dati.getIntAt(k, 3);
                    codContorno = dati.getIntAt(k, 4);
                }// fine del blocco if-else

                /* aggiunge una riga all'elenco in memoria */
                wrapperModifica = new WrapperModifica(new Integer(codRTO),
                        extra,
                        new Integer(codRMP),
                        new Integer(codPiatto),
                        new Integer(codContorno),
                        new Integer(codModifica));
                unElencoOrdini.add(wrapperModifica);
            } /* fine del blocco for */

            /* spazzola l'elenco precedente e ne crea uno nuovo eliminando
    * gli eventuali doppioni (piatto, contorno e modifica uguale)*/
            for (int k = 0; k < unElencoOrdini.size(); k++) {
                /* recupera le informazioni */
                wrapperModifica = (WrapperModifica)unElencoOrdini.get(k);
                /* verifica se esiste nell'elenco destinazione (usa equals())
                 * usa solo i valori di piatto, contorno e modifica per la comparazione
                 * se non esiste lo aggiunge all'elenco */
                if (elencoOrdiniUnivoco.indexOf(wrapperModifica) == -1) {
                    elencoOrdiniUnivoco.add(wrapperModifica);
                } /* fine del blocco if */
            } /* fine del blocco for */

            /* spazzola l'elenco univoco, crea le righe di base
             * e le aggiunge alla collezione */
            for (int k = 0; k < elencoOrdiniUnivoco.size(); k++) {
                /* recupera l'elemento */
                wrapperModifica = (WrapperModifica)elencoOrdiniUnivoco.get(k);
                /* recupera i dati della riga di ordine */
                codRTO = wrapperModifica.c.intValue();
                extra = wrapperModifica.e;
                codRMP = wrapperModifica.m.intValue();
                codPiatto = wrapperModifica.x.intValue();
                codContorno = wrapperModifica.y.intValue();
                codModifica = wrapperModifica.z.intValue();

                /* crea una nuova riga di base */
                unaRiga = this.creaRigaBase(RigaDettaglio.TIPO_VARIANTE,
                        codPiatto,
                        codContorno,
                        codModifica);

                /* esegue le regolazioni specifiche per questo tipo di riga */
                if (extra) {
                    unaRiga.setExtra(extra);
                    unaRiga.setCodiceExtraPiatto(codPiatto);
                    unaRiga.setCodiceExtraContorno(codContorno);
                } else {
                    unaRiga.setCodiceRMP(codRMP);
                }// fine del blocco if-else
                unaRiga.setCodiceModifica(codModifica);

                /* aggiunge la riga alla collezione */
                this.elencoRigheVariante.add(unaRiga);

            } /* fine del blocco for */

            /* chiude l'oggetto dati */
            dati.close();

        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo */


    /**
     * Combina le righe nei pacchetti Menu, Extra e Varianti
     * in un solo pacchetto completo.
     * Il pacchetto e' ordinato per ordine di categoria,
     * e all'interno di una categoria le righe appaiono
     * nella sequenza: menu, extra, varianti
     */
    private void combinaRighe() {
        /* variabili e costanti locali di lavoro */
        int[] codiciCat = null;
        int codice = 0;
        Ordine ordine = null;

        try {                                   // prova ad eseguire il codice

            /*
             * recupera una lista di tutti i codici categoria
             * ordinata per ordine categoria
             */
            ordine = new Ordine();
            ordine.add(moduloCategoria.getCampoOrdine());
            codiciCat = moduloCategoria.query().valoriChiave(ordine);

            /*
             * per ogni categoria, spazzola nell'ordine
             * le righe menu, extra e varianti
             * e crea il pacchetto righe finale
             */
            for (int k = 0; k < codiciCat.length; k++) {

                /* recupera il codice categoria*/
                codice = codiciCat[k];

                /* aggiunge le righe menu per questa categoria */
                aggiungiRigheCategoria(elencoRigheMenu, codice);

                /* aggiunge le righe extra per questa categoria */
                aggiungiRigheCategoria(elencoRigheExtra, codice);

                /* aggiunge le righe variante per questa categoria */
                aggiungiRigheCategoria(elencoRigheVariante, codice);

            } /* fine del blocco for */

        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

    } /* fine del metodo */


    /**
     * Aggiunge al pacchetto righe finale le righe
     * di un pacchetto parziale prendendo solo quelle
     * relative a una categoria
     *
     * @param unPacchettoRighe un pacchetto righe parziale
     * @param codiceCategoria il codice della categoria da considerare
     */
    private void aggiungiRigheCategoria(ArrayList unPacchettoRighe, int codiceCategoria) {
        /* variabili e costanti locali di lavoro */
        RigaDettaglio unaRiga = null;

        try {

            /*
             * spazzola il pacchetto righe parziale
             * e aggiunge al pacchetto finale solo le righe della
             * categoria richiesta
             */
            for (int k = 0; k < unPacchettoRighe.size(); k++) {
                unaRiga = (RigaDettaglio)unPacchettoRighe.get(k);
                if (unaRiga.getCodiceCategoria() == codiceCategoria) {
                    elencoRighe.add(unaRiga);
                } /* fine del blocco if */
            } /* fine del blocco for */

        } catch (Exception unErrore) {           // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

    } /* fine del metodo */


    /**
     * Regola i valori delle quantita' ordinate
     * per tutte le righe di dettaglio
     */
    private void regolaValori() {
        /* variabili e costanti locali di lavoro */
        ArrayList unPacchettoRighe = null;
        RigaDettaglio unaRiga = null;

        try {

            /* recupera il pacchetto righe */
            unPacchettoRighe = this.getRigheDettaglio();

            /*
             * spazzola il pacchetto delle righe dettaglio
             * e per ognuna riempie l'array delle quantita'
             * ordinate per tavolo
             */
            for (int k = 0; k < unPacchettoRighe.size(); k++) {
                unaRiga = (RigaDettaglio)unPacchettoRighe.get(k);
                this.caricaComanda(unaRiga);
            } /* fine del blocco for */

        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

    } /* fine del metodo */


    /**
     * Riempie le comande di ogni tavolo
     * per una data riga di dettaglio.
     * <p/>
     *
     * @param unaRiga la riga per la quale riempire le comande
     */
    private void caricaComanda(RigaDettaglio unaRiga) {

        /* variabili e costanti locali di lavoro */
        RigaDettaglio riga = null;
        ArrayList elencoTavoli = null;
        Filtro filtro = null;
        Query query = null;
        Dati dati = null;
        int codTavolo = 0;
        boolean flagFO = false;
        int codModifica = 0;
        int posizione = 0;
        Campo campoTavoloChiave = null;
        Campo campoRTOfuoriOrario = null;
        Campo campoRTOlinkModifica = null;
        boolean interessaCucina = false;

        try {                                   // prova ad eseguire il codice

            /* recupera la riga passata nei parametri */
            riga = unaRiga;

            /* recupera i campi */
            campoTavoloChiave = moduloTavolo.getCampoChiave();
            campoRTOfuoriOrario = moduloRTO.getCampo(RTO.CAMPO_ANTICIPO);
            campoRTOlinkModifica = moduloRTO.getCampo(RTO.CAMPO_MODIFICA);

            /* recupera l'elenco dei codici dei tavoli apparecchiati */
            elencoTavoli = this.getTavoli();

            /*
             * prepara un filtro per selezionare tutti gli ordini (RTO)
             * relativi a questa riga dettaglio
             */
            filtro = this.filtroOrdiniRiga(riga);

            /* recupera tutte le righe ordine relative a questa riga dettaglio */
            query = new QuerySelezione(moduloRTO);
            query.addCampo(campoTavoloChiave);
            query.addCampo(campoRTOfuoriOrario);
            query.addCampo(campoRTOlinkModifica);
            query.setFiltro(filtro);
            dati = moduloRTO.query().querySelezione(query);

            /* spazzola tutti gli ordini relativi a questa riga
             * per ognuna recupera il codice del tavolo che l'ha ordinata
             * e riempie le quantita' ordinate dai singoli tavoli */
            for (int k = 0; k < dati.getRowCount(); k++) {

                /* recupera il codice del tavolo al quale questa riga si riferisce */
                codTavolo = dati.getIntAt(k, campoTavoloChiave);

                /* recupera il flag fuori orario della riga */
                flagFO = dati.getBoolAt(k, campoRTOfuoriOrario);

                /* recupera il codice della eventuale modifica */
                codModifica = dati.getIntAt(k, campoRTOlinkModifica);

                /* determina la posizione del tavolo nell'elenco dei tavoli apparecchiati */
                posizione = elencoTavoli.indexOf(new Integer(codTavolo));

                /* incrementa di 1 la quantita' ordinata dal tavolo per la riga*/
                riga.incrementaQuantitaTavolo(posizione);

                /* se il flag fuori orario e' acceso, incrementa
                 * anche la quantita' fuori orario */
                if (flagFO) {
                    riga.incrementaQuantitaFOTavolo(posizione);
                }// fine del blocco if

                /* controlla l'esistenza di modifiche */
                if (codModifica != 0) {
                    riga.setEsistonoModifiche(posizione);
                    /* determina se e' di interesse della cucina */
                    interessaCucina = moduloModifica.getLogica().isInteressaCucina(codModifica);
                    if (interessaCucina) {
                        riga.setEsistonoModificheCucina(posizione);
                    }// fine del blocco if
                }// fine del blocco if

            } /* fine del blocco for */

            /* chiude l'oggetto dati */
            dati.close();

        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

    } /* fine del metodo */


    /**
     * Prepara un filtro per selezionare tutte le RTO
     * relative a una data riga di dettaglio
     * <p/>
     *
     * @param unaRiga la riga di dettaglio per la quale preparare il filtroOld
     *
     * @return il filtro per selezionare le righe ordine
     */
    private Filtro filtroOrdiniRiga(RigaDettaglio unaRiga) {

        /* variabili e costanti locali di lavoro */
        Filtro filtroFinale = null;
        Filtro filtro = null;
        Campo campoRMPChiave = null;
        Campo campoRMTLinkMenu = null;
        Campo campoRTOCodModifica = null;
        Campo campoRMOExtraPiatto = null;
        Campo campoRMOExtraContorno = null;
        int codRMP = 0;
        int codModifica = 0;
        int codPiatto = 0;
        int codContorno = 0;

        /* campi */
        campoRMPChiave = moduloRMP.getCampoChiave();
        campoRMTLinkMenu = moduloRMT.getCampo(RMT.Cam.menu);
        campoRTOCodModifica = moduloRTO.getCampo(RTO.CAMPO_MODIFICA);
        campoRMOExtraPiatto = moduloRMO.getCampo(RMO.CAMPO_PIATTO_EXTRA);
        campoRMOExtraContorno = moduloRMO.getCampo(RMO.CAMPO_CONTORNO_EXTRA);

        try {                                   // prova ad eseguire il codice

            /* crea un filtro vuoto iniziale */
            filtroFinale = new Filtro();

            /* -se da menu, filtro in base al codice della RMP
             * -se extra, filtro in base a menu, extrapiatto
             * ed extracontorno */
            if (unaRiga.isExtra() == false) {   // riga da Menu

                /* recupera il codice della RMP */
                codRMP = unaRiga.getCodiceRMP();

                /* crea un filtro per selezionare solo i record di RTO
                 * relativi alla riga menu (RMP) */
                filtro = FiltroFactory.crea(campoRMPChiave, codRMP);
                filtroFinale.add(filtro);

            } else {    // riga Extra

                /* recupera il codice del piatto extra */
                codPiatto = unaRiga.getCodiceExtraPiatto();

                /* recupera il codice del contorno extra */
                codContorno = unaRiga.getCodiceExtraContorno();

                /* filtro per selezionare solo in questo menu */
                filtro = FiltroFactory.crea(campoRMTLinkMenu, codiceMenu);
                filtroFinale.add(filtro);

                /* filtro per selezionare solo i record relativi al piatto extra */
                filtro = FiltroFactory.crea(campoRMOExtraPiatto, codPiatto);
                filtroFinale.add(filtro);

                /* filtro per selezionare solo i record relativi al contorno extra */
                filtro = FiltroFactory.crea(campoRMOExtraContorno, codContorno);
                filtroFinale.add(filtro);

            }// fine del blocco if-else

            /*
            * Se ha la modifica, seleziona solo i record con la modifica
            */
            codModifica = unaRiga.getCodiceModifica();
            if (codModifica != 0) {
                filtro = FiltroFactory.crea(campoRTOCodModifica, codModifica);
                filtroFinale.add(filtro);
            }// fine del blocco if


        } catch (Exception unErrore) {
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return filtroFinale;
    } /* fine del metodo */


    /**
     * Ritorna un filtro per selezionare tutte le
     * RMT di questo menu.
     * <p/>
     *
     * @return un filtro per selezionare le righe
     */
    private Filtro filtroRigheTavolo() {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = null;
        Campo campoRMTLinkMenu = null;

        try {    // prova ad eseguire il codice
            campoRMTLinkMenu = moduloRMT.getCampo(RMT.Cam.menu);
            filtro = FiltroFactory.crea(campoRMTLinkMenu, codiceMenu);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return filtro;
    } // fine del metodo


    /**
     * Ritorna un filtro per selezionare tutte le
     * RMT di questo menu per un dato codice tavolo.
     * <p/>
     * (dovrebbe essere sempre una sola)
     *
     * @param codiceTavolo il codice tavolo
     *
     * @return un pacchetto filtroOld per selezionare le righe
     */
    private Filtro filtroRigheTavolo(int codiceTavolo) {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = null;
        Campo campoRMTLinkTavolo = null;

        try {    // prova ad eseguire il codice

            campoRMTLinkTavolo = moduloRMT.getCampo(RMT.Cam.tavolo);
            filtro = this.filtroRigheTavolo();
            filtro.add(FiltroFactory.crea(campoRMTLinkTavolo, codiceTavolo));

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return filtro;
    } // fine del metodo


    /**
     * Crea una riga di dettaglio con le informazioni di base.
     * <p/>
     *
     * @param tipoRiga il tipo di riga (vedi costanti della classe RigaDettaglio)
     * @param codPiatto il codice del piatto
     * @param codContorno il codice del contorno (null per nessun contorno)
     * @param codModifica il codice della modifica (null per nessuna modifica)
     *
     * @return la riga creata
     */
    private RigaDettaglio creaRigaBase(int tipoRiga,
                                       int codPiatto,
                                       int codContorno,
                                       int codModifica) {

        /* variabili e costanti locali di lavoro */
        RigaDettaglio unaRiga = null;
        Campo campoTestoPiatto = null;
        Campo campoCategoriaPiatto = null;
        Campo campoTestoModifica = null;
        String testoPiatto = "";
        int codCategoria = 0;
        String testoContorno = "";
        String testoModifica = "";
        String testoRiga = "";
        int codiceCategoria = 0;
        int codicePiatto = 0;
        int codiceContorno = 0;
        int codiceModifica = 0;

        try {                                   // prova ad eseguire il codice

            codicePiatto = codPiatto;
            codiceContorno = codContorno;
            codiceModifica = codModifica;

            /* regolazione campi */
            campoTestoPiatto = moduloPiatto.getCampo(Piatto.CAMPO_NOME_ITALIANO);
            campoCategoriaPiatto = moduloPiatto.getCampo(Piatto.CAMPO_CATEGORIA);
            campoTestoModifica = moduloModifica.getCampo(Modifica.CAMPO_DESCRIZIONE);

            /* recupera i dati dal Piatto */
            if (codicePiatto != 0) {
                testoPiatto = moduloPiatto.query().valoreStringa(campoTestoPiatto, codicePiatto);
                codCategoria = moduloPiatto.query().valoreInt(campoCategoriaPiatto, codicePiatto);
            } /* fine del blocco if */

            /* recupera i dati dal contorno, se esiste */
            if (codiceContorno != 0) {
                testoContorno = moduloPiatto.query().valoreStringa(campoTestoPiatto,
                        codiceContorno);
            } /* fine del blocco if */

            /* recupera i dati dalla modifica, se esiste */
            if (codiceModifica != 0) {
                testoModifica = moduloModifica.query().valoreStringa(campoTestoModifica,
                        codiceModifica);
            } /* fine del blocco if */

            /* costruisce il testo completo per la riga */
            /* aggiunge il testo del piatto principale */
            testoRiga = testoPiatto;

            /* aggiunge il testo del contorno */
            if (testoContorno != "") {
                testoRiga += congiunzione + testoContorno;
            } /* fine del blocco if */

            /* aggiunge il testo della modifica */
            if (testoModifica != "") {
                testoRiga += " - " + testoModifica;
            } /* fine del blocco if */

            /* crea la riga e la riempie con i dati */
            unaRiga = new RigaDettaglio();
            unaRiga.setTipoRiga(tipoRiga);

            // Controllo di congruita', se fallisce rimane zero
            if (codCategoria != 0) {
                codiceCategoria = codCategoria;
            } /* fine del blocco if */
            unaRiga.setCodiceCategoria(codiceCategoria);
            unaRiga.setTestoRiga(testoRiga);
            unaRiga.creaElencoQuantitaVuoto(this.getTavoli().size());

        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return unaRiga;

    } /* fine del metodo */


    /**
     * restituisce la lista delle righe di dettaglio
     *
     * @return una ArrayList di oggetti RigaDettaglio
     */
    public ArrayList getRigheDettaglio() {
        return this.elencoRighe;
    } /* fine del metodo getter */


    /**
     * restituisce la lista delle righe di dettaglio di un dato tipo
     *
     * @param tipo il tipo di righe da restituire (costanti di RigaDettaglio)
     *
     * @return una ArrayList di oggetti RigaDettaglio
     */
    private ArrayList getRigheDettaglio(int tipo) {
        /* variabili e costanti locali di lavoro */
        ArrayList lista = null;
        ArrayList righe = null;
        RigaDettaglio unaRiga = null;

        try {    // prova ad eseguire il codice
            /* costruisce l'oggetto che questo metodo restituisce */
            lista = new ArrayList();

            /* recupera tutte le righe dettaglio */
            righe = this.getRigheDettaglio();

            /* filtra solo quelle da menu */
            for (int k = 0; k < righe.size(); k++) {
                unaRiga = (RigaDettaglio)righe.get(k);
                if (unaRiga.getTipoRiga() == tipo) {
                    lista.add(unaRiga);
                }// fine del blocco if
            } // fine del ciclo for
        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    } /* fine del metodo getter */


    /**
     * restituisce la lista delle righe di dettaglio
     * per i soli piatti da menu.
     * <p/>
     *
     * @return una ArrayList di oggetti RigaDettaglio
     */
    public ArrayList getRigheDettaglioMenu() {
        /* valore di ritorno */
        return this.getRigheDettaglio(RigaDettaglio.TIPO_DA_MENU);
    } /* fine del metodo getter */


    /**
     * restituisce la lista delle righe di dettaglio.
     * per i soli piatti extra
     * <p/>
     *
     * @return una ArrayList di oggetti RigaDettaglio
     */
    public ArrayList getRigheDettaglioExtra() {
        /* valore di ritorno */
        return this.getRigheDettaglio(RigaDettaglio.TIPO_EXTRA);
    } /* fine del metodo getter */


    /**
     * restituisce la lista delle righe di dettaglio
     * relative alle sole modifiche
     * <p/>
     *
     * @return una ArrayList di oggetti RigaDettaglio
     */
    public ArrayList getRigheDettaglioModifica() {
        /* valore di ritorno */
        return this.getRigheDettaglio(RigaDettaglio.TIPO_VARIANTE);
    } /* fine del metodo getter */


    /**
     * restituisce la lista dei codici dei tavoli apparecchiati per questo menu
     * <p/>
     *
     * @return una ArrayList di oggetti Integer
     */
    public ArrayList getTavoli() {
        return this.elencoTavoli;
    } /* fine del metodo getter */


    /**
     * restituisce il numero di tavoli apparecchiati per questo menu
     * <p/>
     *
     * @return il numero di tavoli apparecchiati
     */
    public int getNumeroTavoli() {
        return this.elencoTavoli.size();
    } /* fine del metodo getter */


    /**
     * restituisce la data del menu come stringa
     * <p/>
     *
     * @return la stringa della data del menu
     */
    public String getDataMenu() {
        return this.dataMenu;
    } /* fine del metodo getter */


    /**
     * restituisce il pasto menu come stringa
     * <p/>
     *
     * @return la stringa del pasto del menu
     */
    public String getPastoMenu() {
        return this.pastoMenu;
    } /* fine del metodo getter */


    /**
     * restituisce il numero di tavoli che hanno ordinato
     * <p/>
     *
     * @return il numero di tavoli che hanno ordinato
     */
    public int getQuantiTavoliOrdinati() {
        return quantiTavoliOrdinati;
    }


    /**
     * restituisce il numero totale di ordini effettuati
     * a fronte di piatti extra
     * <p/>
     *
     * @return il numero di extra ordinati
     */
    public int getQuantiOrdiniExtra() {
        return quantiOrdiniExtra;
    }


    /**
     * restituisce il numero totale modifiche
     * a fronte di piatti da menu o extra
     * <p/>
     *
     * @return il numero di modifiche
     */
    public int getQuanteModifiche() {
        return quanteModifiche;
    }


    /**
     * Controlla se tutti i tavoli apparecchiati hanno ordinato
     * <p/>
     *
     * @return true se tutti hanno ordinato
     */
    public boolean isTuttiOrdinati() {
        /* variabili e costanti locali di lavoro */
        boolean completi = false;

        if (this.getNumeroTavoli() == this.quantiTavoliOrdinati()) {
            completi = true;
        }// fine del blocco if

        return completi;
    }


    /**
     * Ritorna il numero totale di coperti per questo menu.
     * <p/>
     *
     * @return il numero totale di coperti
     */
    public int getQuantiCoperti() {
        return this.copertiTavoli(this.getTavoli());
    }// fine del metodo getter


    /**
     * Ritorna il numero di coperti relativi ai tavoli
     * che hanno ordinato.
     * <p/>
     *
     * @return il numero totale di coperti
     */
    public int getQuantiCopertiOrdinati() {
        return this.copertiTavoli(this.elencoTavoliOrdinati(true));
    }// fine del metodo getter


    /**
     * Ritorna il numero di coperti relativi ai tavoli
     * che non hanno ancora ordinato.
     * <p/>
     *
     * @return il numero totale di coperti
     */
    public int getQuantiCopertiNonOrdinati() {
        return this.copertiTavoli(this.elencoTavoliOrdinati(false));
    }// fine del metodo getter

}// fine della classe