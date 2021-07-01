/**
 * Copyright: Copyright (c) 2005
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      2-mar-2005
 */
package it.algos.albergo.ristorante.menu;

import it.algos.albergo.AlbergoLib;
import it.algos.albergo.ristorante.Ristorante;
import it.algos.albergo.ristorante.categoria.CategoriaModulo;
import it.algos.albergo.ristorante.fisso.Fisso;
import it.algos.albergo.ristorante.fisso.FissoModulo;
import it.algos.albergo.ristorante.piatto.Piatto;
import it.algos.albergo.ristorante.piatto.PiattoModulo;
import it.algos.albergo.ristorante.righemenupiatto.RMP;
import it.algos.albergo.ristorante.righemenupiatto.RMPModulo;
import it.algos.base.azione.Azione;
import it.algos.base.azione.AzioneBase;
import it.algos.base.campo.base.Campo;
import it.algos.base.database.util.Operatore;
import it.algos.base.dialogo.Dialogo;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.lista.Lista;
import it.algos.base.messaggio.MessaggioAvviso;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.NavigatoreLS;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.scheda.Scheda;
import it.algos.base.wrapper.CampoValore;

import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;

/**
 * Navigatore dei Menu.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 2-mar-2005 ore 09.57.34
 */
public final class MenuNavigatore extends NavigatoreLS {

    /* riferimento all'azione Duplica Menu Singolo */
    private Azione azDuplicaMenuSingolo;


    /**
     * Costruttore completo con parametri.
     *
     * @param unModulo modulo di riferimento
     */
    public MenuNavigatore(Modulo unModulo) {
        /* rimanda al costruttore della superclasse */
        super(unModulo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        this.setUsaFinestra(true);
        this.setNomeChiave(Menu.NAVIGATORE_MENU);
        this.setUsaPannelloUnico(false);

    }// fine del metodo inizia


    /**
     * Regolazioni dinamiche dell'oggetto.
     * <p/>
     * Regola le caratteristiche dinamiche in base alla impostazione
     * corrente delle caratteristiche statiche <br>
     * Metodo invocato dalla classe che crea questo oggetto (di norma) <br>
     * Sovrascritto nelle sottoclassi <br>
     */
    public void inizializza() {

        try { // prova ad eseguire il codice

            /* invoca il metodo sovrascritto della superclasse */
            super.inizializza();

            /* aggiunge l'azione Statistiche alla lista */
            this.addAzione(new AzStatistiche());

            /* crea, registra e aggiunge l'azione Duplica Gruppi di Menu alla lista */
            this.addAzione(new AzDuplicaGruppiMenu());

            /* crea, registra e aggiunge l'azione Duplica Singolo Menu alla scheda */
            azDuplicaMenuSingolo = new AzDuplicaSingoloMenu();
            this.addAzione(azDuplicaMenuSingolo);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    public void avvia() {
        super.avvia();
        int a = 87;
    }


    /**
     * Sincronizza lo stato del Navigatore.
     * <p/>
     * Controlla lo stato di tutti i suoi componenti <br>
     * Elabora la sua business logic <br>
     * Regola la GUI di ogni conseguenza (menu, bottoni, ecc) <br>
     * Invoca il metodo sovrascritto della superclasse <br>
     */
    public void sincronizza() {
        try { // prova ad eseguire il codice
            this.regolaAbilitazioneDuplica();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* invoca il metodo sovrascritto della superclasse */
        super.sincronizza();
    }


    /**
     * Abilitazione dell'azione Duplica Menu.
     * <p/>
     * Abilitato solo se la scheda non e' nuovo record, non è modificata ed è attiva
     * <br>.
     */
    private void regolaAbilitazioneDuplica() {
        /* variabili e costanti locali di lavoro */
        boolean abilita = false;
        Azione azione = null;
        Scheda scheda = null;

        try { // prova ad eseguire il codice

            azione = azDuplicaMenuSingolo;
            if (azione != null) {
                scheda = this.getScheda();
                if (!scheda.isNuovoRecord()) {
                    if (!scheda.isModificata()) {
                        if (scheda.isAttivo()) {
                            abilita = true;
                        }// fine del blocco if
                    }// fine del blocco if
                }// fine del blocco if

                azione.setEnabled(abilita);

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Creazione fisica di un nuovo record.
     * <p/>
     * Crea anche i piatti di base.
     *
     * @return il codice del record creato, -1 se non creato.
     */
    public int creaRecord() {
        /* variabili e costanti locali di lavoro */
        int codFisso = 0;
        RMPModulo moduloRMP = null;
        Modulo moduloFisso = null;
        Modulo moduloCategoria = null;
        Modulo moduloPiatto = null;
        ArrayList listaFissi = null;
        int[] codFissi = null;
        Campo campoLinkMenu = null;
        Campo campoLinkPiatto = null;
        Campo campoOrdine = null;
        Campo campoCongelato = null;
        int codiceMenu = 0;
        ArrayList campi = null;
        CampoValore campoValore = null;
        Ordine ordine = null;
        int codCategoria = 0;
        boolean congelato = false;
        int currCategoria = 0;
        int nrOrdine = 0;

        /* crea il record di menu nella superclasse */
        codiceMenu = super.creaRecord();

        /* crea i piatti fissi per questo menu */
        try {    // prova ad eseguire il codice
            moduloRMP = this.getModuloRMP();
            moduloFisso = this.getModuloFisso();
            moduloCategoria = this.getModuloCategoria();
            moduloPiatto = this.getModuloPiatto();
            campoLinkMenu = moduloRMP.getCampo(RMP.CAMPO_MENU);
            campoLinkPiatto = moduloRMP.getCampo(RMP.CAMPO_PIATTO);
            campoOrdine = moduloRMP.getCampoOrdine();
            campoCongelato = moduloRMP.getCampo(RMP.CAMPO_PIATTO_CONGELATO);

            /* crea un ordine su ordine categoria - ordine piatto fisso */
            ordine = new Ordine();
            ordine.add(moduloCategoria.getCampoOrdine());
            ordine.add(moduloFisso.getCampoOrdine());

            listaFissi = moduloFisso.query().valoriCampo(Fisso.CAMPO_PIATTO, ordine);
            codFissi = Libreria.objToInt(listaFissi);

            /* crea una riga di menu per ogni piatto fisso */
            currCategoria = 0;
            nrOrdine = 0;
            for (int k = 0; k < codFissi.length; k++) {
                codFisso = codFissi[k];

                /* recupera la categoria del piatto
                 * intercetta il cambio categoria per resettare l'ordine */
                codCategoria = moduloPiatto.query().valoreInt(Piatto.CAMPO_CATEGORIA, codFisso);
                congelato = moduloPiatto.query().valoreBool(Piatto.CAMPO_CONGELATO, codFisso);
                if (codCategoria != currCategoria) {
                    nrOrdine = 1;
                    currCategoria = codCategoria;
                } else {
                    nrOrdine++;
                }// fine del blocco if-else

                /* crea la riga con i valori calcolati */
                campi = new ArrayList();
                campoValore = new CampoValore(campoLinkMenu, new Integer(codiceMenu));
                campi.add(campoValore);
                campoValore = new CampoValore(campoLinkPiatto, new Integer(codFisso));
                campi.add(campoValore);
                campoValore = new CampoValore(campoOrdine, new Integer(nrOrdine));
                campi.add(campoValore);
                campoValore = new CampoValore(campoCongelato, new Boolean(congelato));
                campi.add(campoValore);
                moduloRMP.query().nuovoRecord(campi);

            } // fine del ciclo for
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codiceMenu;
    }


    /**
     * Duplica alcuni menu.
     * <p/>
     * Metodo invocato da gestore <br>
     * Presenta un dialogo di selezione/scelta <br>
     * Inserire: data inizio, durata (giorni), data inizio duplicazione  <br>
     */
    private void duplicaGruppo() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Dialogo dialogo;
        Campo campoDataInizioCopia;
        Campo campoDurata;
        Campo campoDataInizioIncolla;
        Date dataInizioCopia = null;
        int durata = 0;
        int deltaDate=0;
        Date dataInizioIncolla = null;
        Date dataFineCopia = null;
        Date dataFineIncolla = null;
        Modulo mod = null;
        Filtro filtro = null;
        Filtro filtroInizio;
        Filtro filtroFine;
        boolean esiste = false;
        Date dataVariabile;
        Date dataVariabileNuova;
        int lista[] = new int[0];
        ArrayList<Integer> duplicati = new ArrayList<Integer>();

        try {    // prova ad eseguire il codice
            dialogo = new DialogoDuplicaGruppo();
            dialogo.avvia();
            continua = dialogo.isConfermato();

            if (continua) {
                mod = MenuModulo.get();
                dataInizioCopia=dialogo.getData(DialogoDuplicaGruppo.NOME_CAMPO_DATA_INIZIO_COPIA);
                durata = dialogo.getInt(DialogoDuplicaGruppo.NOME_CAMPO_DURATA);
                dataInizioIncolla=dialogo.getData(DialogoDuplicaGruppo.NOME_CAMPO_DATA_INIZIO_INCOLLA);
                continua = ((dataInizioCopia != null) && (durata > 0) && (dataInizioIncolla != null));
            }// fine del blocco if

            if (continua) {
                durata--;
                deltaDate= Lib.Data.diff(dataInizioIncolla,dataInizioCopia);
                dataFineCopia = Lib.Data.add(dataInizioCopia, durata);
                dataFineIncolla = Lib.Data.add(dataInizioIncolla, durata);

                continua = ((dataFineCopia != null) && (dataFineIncolla != null));
            }// fine del blocco if

            // controlla che ci sia almeno 1 menu nel periodo da copiare
            if (continua) {
                for (int k = 0; k <= durata; k++) {
                    dataVariabile = Lib.Data.add(dataInizioCopia, k);
                    filtro = FiltroFactory.crea(Menu.Cam.data.get(), dataVariabile);
                    if (mod.query().isEsisteRecord(filtro)) {
                        esiste = true;
                        break;
                    }// fine del blocco if-else
                } // fine del ciclo for

                if (!esiste) {
                    new MessaggioAvviso("Non esiste nessun menu da copiare.");
                    continua = false;
                }// fine del blocco if-else
            }// fine del blocco if

            // controlla che non ci siano menu nel periodo da incollare
            if (continua) {
                filtro = new Filtro();
                filtroInizio = FiltroFactory.crea(Menu.Cam.data.get(), Operatore.MAGGIORE_UGUALE, dataInizioIncolla);
                filtroFine = FiltroFactory.crea(Menu.Cam.data.get(), Operatore.MINORE_UGUALE, dataFineIncolla);
                filtro.add(filtroInizio);
                filtro.add(filtroFine);

                if (mod.query().isEsisteRecord(filtro)) {
                    new MessaggioAvviso("Esistono già dei menu nel periodo destinazione.");
                    continua = false;
                }// fine del blocco if-else
            }// fine del blocco if

            // recupera tutti i records di menu nel periodo selezionato
            // crea (duplica) i records di menu
            // modifica la data
            // crea i records di RigheMenuPiatto
            // modifica il link al record di menu
            if (continua) {
                filtro = new Filtro();
                filtroInizio = FiltroFactory.crea(Menu.Cam.data.get(), Operatore.MAGGIORE_UGUALE, dataInizioCopia);
                filtroFine = FiltroFactory.crea(Menu.Cam.data.get(), Operatore.MINORE_UGUALE, dataFineCopia);
                filtro.add(filtroInizio);
                filtro.add(filtroFine);
                lista = mod.query().valoriChiave(filtro);

                for (int codOld : lista) {
                    dataVariabile = mod.query().valoreData(Menu.Cam.data.get(), codOld);
                    dataVariabileNuova = Lib.Data.add(dataVariabile, deltaDate);
                    int dup = this.duplicaMenu(codOld, dataVariabileNuova);
                    if (dup>0) {
                        duplicati.add(dup);
                    }// fine del blocco if

                    
                } // fine del ciclo for-each

            }// fine del blocco if

            if (continua) {
                filtro = FiltroFactory.elenco(MenuModulo.get(),duplicati);
                this.setFiltroCorrente(filtro);
                this.aggiornaLista();
            }// fine del blocco if



        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }

    /**
     * Duplica il menu correntemente aperto in scheda.
     * <p/>
     * Metodo invocato da gestore <br>
     * Chiede conferma
     * Recupera il codice scheda corrente <br>
     * Duplica il record <br>
     * Regola la data del record duplicato con la data di oggi <br>
     * Regola i piatti del nuovo menu come quelli copiati <br>
     * Carica in scheda e visualizza il nuovo record <br>
     * Il bottone/azione e' abilitato solo se la scheda non e' modificata <br>
     */
    private void duplicaSingolo() {
        /* variabili e costanti locali di lavoro */
        int risposta = 0;
        String titolo = null;
        String messaggio = null;
        boolean continua = true;
        int codice = 0;
        int codiceDuplicato = 0;
        Campo campoGiorno = null;
        Date dataOdierna = null;
        Modulo moduloRMP = null;
        Filtro filtro = null;
        Filtro filtroLista = null;
        Lista lista;
        Ordine ordine = null;
        int[] chiaviRMP = null;
        int[] ordiniRMP = null;
        int chiaveRMP = 0;
        int ordineRMP = 0;
        int chiaveRMPdup = 0;
        Campo rmpCampoMenu = null;
        Campo rmpCampoOrdine = null;
        ArrayList valori = null;

        try {    // prova ad eseguire il codice

            /* chiede conferma */
            titolo = "Duplicazione Singolo Menu";
            messaggio = "Vuoi duplicare il menu corrente e crearne uno nuovo in data odierna?";
            risposta = JOptionPane.showConfirmDialog(null,
                    messaggio,
                    titolo,
                    JOptionPane.YES_NO_OPTION);
            if (risposta != 0) {
                continua = false;
            }// fine del blocco if

            /* se confermato, esegue */
            if (continua) {
                moduloRMP = Progetto.getModulo(RMP.NOME_MODULO);
                rmpCampoOrdine = moduloRMP.getCampoOrdine();
                rmpCampoMenu = moduloRMP.getCampo(RMP.CAMPO_MENU);
                campoGiorno = this.getModulo().getCampo(Menu.Cam.data.get());

                codice = this.getCodiceMenuCorrente();
                codiceDuplicato = this.getModulo().query().duplicaRecord(codice);

                if (codiceDuplicato != -1) {
                    dataOdierna = AlbergoLib.getDataProgramma();
                    this.getModulo().query().registraRecordValore(codiceDuplicato,
                            campoGiorno,
                            dataOdierna);

                    filtro = FiltroFactory.crea(moduloRMP.getCampo(RMP.CAMPO_MENU), codice);
                    ordine = new Ordine();
                    ordine.add(moduloRMP.getCampoOrdine());
                    chiaviRMP = moduloRMP.query().valoriChiave(filtro, ordine);
                    valori = moduloRMP.query().valoriCampo(rmpCampoOrdine, filtro, ordine);
                    ordiniRMP = Lib.Array.creaIntArray(valori);

                    for (int k = 0; k < chiaviRMP.length; k++) {
                        chiaveRMP = chiaviRMP[k];
                        ordineRMP = ordiniRMP[k];
                        chiaveRMPdup = moduloRMP.query().duplicaRecord(chiaveRMP);
                        /* associa la RMP duplicata al menu duplicato */
                        moduloRMP.query().registraRecordValore(chiaveRMPdup,
                                rmpCampoMenu,
                                codiceDuplicato);
                        /* pone l'ordine della RMP duplicata uguale a quello originale */
                        moduloRMP.query().registraRecordValore(chiaveRMPdup,
                                rmpCampoOrdine,
                                new Integer(ordineRMP));
                    } // fine del ciclo for

                    /* se la lista ha un filtro corrente, vi aggiunge il nuovo record
                     * e riavvia la lista rendendo il duplicato visibile e selezionato */
                    lista = this.getLista();
                    filtroLista = lista.getFiltroCorrente();
                    if (filtroLista != null) {
                        if (filtroLista.isValido()) {
                            filtro = FiltroFactory.codice(this.getModulo(), codiceDuplicato);
                            lista.addFiltroCorrente(Operatore.OR, filtro);
                            lista.avvia();
                            lista.setRecordVisibileSelezionato(codiceDuplicato);
                        }// fine del blocco if
                    }// fine del blocco if

                    /* avvia la scheda con il nuovo record */
                    this.getScheda().avvia(codiceDuplicato);
                }// fine del blocco if

            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }

    /**
     * Duplica il menu.
     * <p/>
     * Duplica il record <br>
     * Regola la data del record duplicato con la data indicata <br>
     * Regola i piatti del nuovo menu come quelli copiati <br>
     * @param codice del menu da duplicare
     * @param dataMenu data per il menu duplicato
     * @return codice del menu duplicato
     */
    private int duplicaMenu(int codice, Date dataMenu) {
        /* variabili e costanti locali di lavoro */
        int codiceDuplicato=0;
        Campo campoGiorno;
        Modulo moduloRMP;
        Filtro filtro;
        Ordine ordine;
        int[] chiaviRMP;
        int[] ordiniRMP;
        int chiaveRMP;
        int ordineRMP;
        int chiaveRMPdup;
        Campo rmpCampoMenu;
        Campo rmpCampoOrdine;
        ArrayList valori;

        try {    // prova ad eseguire il codice

            moduloRMP = Progetto.getModulo(RMP.NOME_MODULO);
            rmpCampoOrdine = moduloRMP.getCampoOrdine();
            rmpCampoMenu = moduloRMP.getCampo(RMP.CAMPO_MENU);
            campoGiorno = this.getModulo().getCampo(Menu.Cam.data.get());

            codiceDuplicato = this.getModulo().query().duplicaRecord(codice);

            if (codiceDuplicato != -1) {
                this.getModulo().query().registraRecordValore(codiceDuplicato, campoGiorno, dataMenu);

                filtro = FiltroFactory.crea(moduloRMP.getCampo(RMP.CAMPO_MENU), codice);
                ordine = new Ordine();
                ordine.add(moduloRMP.getCampoOrdine());
                chiaviRMP = moduloRMP.query().valoriChiave(filtro, ordine);
                valori = moduloRMP.query().valoriCampo(rmpCampoOrdine, filtro, ordine);
                ordiniRMP = Lib.Array.creaIntArray(valori);

                for (int k = 0; k < chiaviRMP.length; k++) {
                    chiaveRMP = chiaviRMP[k];
                    ordineRMP = ordiniRMP[k];
                    chiaveRMPdup = moduloRMP.query().duplicaRecord(chiaveRMP);

                    /* associa la RMP duplicata al menu duplicato */
                    moduloRMP.query().registraRecordValore(chiaveRMPdup, rmpCampoMenu, codiceDuplicato);

                    /* pone l'ordine della RMP duplicata uguale a quello originale */
                    moduloRMP.query().registraRecordValore(chiaveRMPdup, rmpCampoOrdine, ordineRMP);
                } // fine del ciclo for
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codiceDuplicato;

    }

    /**
     * Apre il dialogo Statistiche.
     * <p/>
     */
    private void statistiche() {
        /* variabili e costanti locali di lavoro */
        Dialogo dialogo;

        try {    // prova ad eseguire il codice
            dialogo = new DialogoStatistiche();
            dialogo.avvia();
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Ritorna il codice del menu correntemente visualizzato in scheda.
     * <p/>
     *
     * @return il codice del menu
     */
    private int getCodiceMenuCorrente() {
        /* variabili e costanti locali di lavoro */
        int codMenu = 0;
        Scheda scheda = null;

        try {    // prova ad eseguire il codice
            scheda = this.getScheda();
            if (scheda != null) {
                codMenu = scheda.getCodice();
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codMenu;
    }


    /**
     * Presenta un dialogo per la selezione della seconda lingua
     * da stampare nel menu cliente.
     * <p/>
     *
     * @return il codice della seconda lingua scelta, 0 se annullato.
     */
    private int dialogoSecondaLingua() {
        /** variabili e costanti locali di lavoro */
        int codiceLingua = 0;
        String unTitolo = "";
        String unMessaggio = "";
        Object[] valori = {"Tedesco", "Inglese", "Francese"};

        try {    // prova ad eseguire il codice
            codiceLingua = Piatto.TEDESCO;
            unTitolo = "Seconda lingua del Menu";
            unMessaggio =
                    "Seleziona la seconda lingua da utilizzare nella stampa del menu\nLa prima e' l'italiano";
            codiceLingua = JOptionPane.showOptionDialog(null,
                    unMessaggio,
                    unTitolo,
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    valori,
                    valori[0]);
            codiceLingua++;
        } catch (Exception unErrore) {    // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return codiceLingua;
    } /* fine del metodo */


    /**
     * Ritorna il modulo Piatto.
     * <p/>
     *
     * @return il modulo Piatto
     */
    private PiattoModulo getModuloPiatto() {
        return (PiattoModulo) Progetto.getModulo(Ristorante.MODULO_PIATTO);
    }


    /**
     * Ritorna il modulo RMP.
     * <p/>
     *
     * @return il modulo RMP
     */
    private RMPModulo getModuloRMP() {
        return (RMPModulo) Progetto.getModulo(Ristorante.MODULO_RIGHE_PIATTO);
    }


    /**
     * Ritorna il modulo Fisso.
     * <p/>
     *
     * @return il modulo Fisso
     */
    private FissoModulo getModuloFisso() {
        return (FissoModulo) Progetto.getModulo(Ristorante.MODULO_FISSO);
    }


    /**
     * Ritorna il modulo Categoria.
     * <p/>
     *
     * @return il modulo Categoria
     */
    private CategoriaModulo getModuloCategoria() {
        return (CategoriaModulo) Progetto.getModulo(Ristorante.MODULO_CATEGORIA);
    }


    /**
     * Azione Statistiche
     */
    private final class AzStatistiche extends AzioneBase {

        /**
         * Costruttore completo con parametri.
         */
        public AzStatistiche() {
            /* rimanda al costruttore della superclasse */
            super();

            try { // prova ad eseguire il codice
                /* regolazioni iniziali di riferimenti e variabili */
                this.inizia();
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }// fine del metodo costruttore completo


        /**
         * Regolazioni statiche di riferimenti e variabili.
         * </p>
         * Metodo chiamato direttamente dal costruttore <br>
         *
         * @throws Exception unaEccezione
         */
        private void inizia() throws Exception {
            /* regola le variabili*/
            super.setTooltip("Analisi statistiche");
            super.setIconaMedia("Torta24");
            super.setUsoLista(true);
        }// fine del metodo inizia


        /**
         * actionPerformed, da ActionListener.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo delegato, nel gestore specifico associato
         * all' oggetto che genera questo evento <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void actionPerformed(ActionEvent unEvento) {
            try { // prova ad eseguire il codice
                statistiche();
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }

    }// fine della classe

    /**
     * Azione Duplica Menu Gruppo
     */
    private final class AzDuplicaGruppiMenu extends AzioneBase {

        /**
         * Costruttore completo con parametri.
         */
        public AzDuplicaGruppiMenu() {
            /* rimanda al costruttore della superclasse */
            super();

            try { // prova ad eseguire il codice
                /* regolazioni iniziali di riferimenti e variabili */
                this.inizia();
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }// fine del metodo costruttore completo


        /**
         * Regolazioni statiche di riferimenti e variabili.
         * </p>
         * Metodo chiamato direttamente dal costruttore <br>
         *
         * @throws Exception unaEccezione
         */
        private void inizia() throws Exception {
            /* regola le variabili*/
            this.setTooltip("Duplica un gruppo di menu");
            this.setIconaMedia("Duplica24");
            super.setUsoLista(true);
        }// fine del metodo inizia


        /**
         * actionPerformed, da ActionListener.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo delegato, nel gestore specifico associato
         * all' oggetto che genera questo evento <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void actionPerformed(ActionEvent unEvento) {
            try { // prova ad eseguire il codice
                duplicaGruppo();
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }

    }// fine della classe

    /**
     * Azione Duplica Menu Singolo
     */
    private final class AzDuplicaSingoloMenu extends AzioneBase {

        /**
         * Costruttore completo con parametri.
         */
        public AzDuplicaSingoloMenu() {
            /* rimanda al costruttore della superclasse */
            super();

            try { // prova ad eseguire il codice
                /* regolazioni iniziali di riferimenti e variabili */
                this.inizia();
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }// fine del metodo costruttore completo


        /**
         * Regolazioni statiche di riferimenti e variabili.
         * </p>
         * Metodo chiamato direttamente dal costruttore <br>
         *
         * @throws Exception unaEccezione
         */
        private void inizia() throws Exception {
            /* regola le variabili*/
            this.setTooltip("Duplica il menu corrente");
            this.setIconaMedia("Duplica24");
            this.setUsoScheda(true);
        }// fine del metodo inizia


        /**
         * actionPerformed, da ActionListener.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo delegato, nel gestore specifico associato
         * all' oggetto che genera questo evento <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void actionPerformed(ActionEvent unEvento) {
            try { // prova ad eseguire il codice
                duplicaSingolo();
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }

    }// fine della classe


}// fine della classe
