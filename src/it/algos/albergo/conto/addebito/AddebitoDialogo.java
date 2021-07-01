/**
 * Copyright: Copyright (c) 2006
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      29-mar-2006
 */
package it.algos.albergo.conto.addebito;

import it.algos.albergo.AlbergoLib;
import it.algos.albergo.conto.Conto;
import it.algos.albergo.conto.ContoModulo;
import it.algos.albergo.listino.Listino;
import it.algos.albergo.listino.ListinoModulo;
import it.algos.albergo.prenotazione.periodo.Periodo;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.campo.inizializzatore.InitFactory;
import it.algos.base.campo.logica.CampoLogica;
import it.algos.base.campo.video.CampoVideo;
import it.algos.base.campo.video.decorator.CVDBottone2stati;
import it.algos.base.componente.bottone.BottoneDialogo;
import it.algos.base.dialogo.DialogoBase;
import it.algos.base.dialogo.DialogoListaLink;
import it.algos.base.errore.Errore;
import it.algos.base.layout.Layout;
import it.algos.base.libreria.Libreria;
import it.algos.base.lista.Lista;
import it.algos.base.listasingola.ListaSingola;
import it.algos.base.messaggio.MessaggioAvviso;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.navigatore.NavigatoreL;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFlusso;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.wrapper.CampoValore;
import it.algos.base.wrapper.TestoAlgos;

import javax.swing.JLabel;
import java.awt.KeyboardFocusManager;
import java.util.ArrayList;
import java.util.Date;

/**
 * Dialogo di inserimento continuo degli addebiti.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 22-mar-2006 ore 15.00.06
 */
public final class AddebitoDialogo extends DialogoBase implements Addebito {

    private int codice;

    private Campo campoData;

    private Campo campoConto;

    private Campo campoListino;

    private Campo campoQuantita;

    private Campo campoPrezzo;

    private Campo campoTotale;

    private Campo campoNote;

    private Navigatore navStriscia;

    /**
     * pannello dei bottoni nell'addebito
     */
    private Pannello panBottoni;

    /**
     * pannello selettore del conto
     */
    private PannelloConto panConto;


    /**
     * Costruttore completo senza parametri.
     * <p/>
     *
     * @param modulo di riferimento
     * @param codConto conto inizialmente selezionato (0 per nessuno)
     */
    public AddebitoDialogo(AddebitoModulo modulo, int codConto) {
        /* rimanda al costruttore della superclasse */
        super(modulo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.setCodice(codConto);
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
        try { // prova ad eseguire il codice

            this.creaDialogo();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizia


    /**
     * .
     * <p/>
     */
    public void inizializza() {
        /* variabili e costanti locali di lavoro */
        Pannello pan;

        try {    // prova ad eseguire il codice

            super.inizializza();

            /* prende i bottoni cancella e registra del dialogo
             * e li inserisce nel pannello bottoni del riquadro addebito */
            pan = this.getPanBottoni();
            pan.add(this.getBottoneCancella());
            pan.add(this.getBottoneRegistra());

            /* regola il valore iniziale del campo conto */
            campoConto.setValore(this.getCodice());

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Dialogo utente per la creazione di un nuovo addebito.
     * <p/>
     */
    private void creaDialogo() {
        /* variabili e costanti locali di lavoro */
        Campo campoStriscia;
        PannelloFlusso panSinistra;
        PannelloFlusso panStriscia;
        PannelloFlusso panGenerale;
        Navigatore nav;
        BottoneDialogo bot;
        Filtro filtroCompleto;
        Filtro filtro;
        ContoModulo modConto;
        PannelloConto panConto;


        try { // prova ad eseguire il codice

            /* creazione pannelli */
            panSinistra = new PannelloFlusso(Layout.ORIENTAMENTO_VERTICALE);
            panStriscia = new PannelloFlusso(Layout.ORIENTAMENTO_ORIZZONTALE);
            panGenerale = new PannelloFlusso(Layout.ORIENTAMENTO_ORIZZONTALE);

            /* regola il titolo della finestra */
            this.setTitolo("Registrazione addebiti");

            /* campo data (occore selezionare il valore iniziale) */
            campoData = CampoFactory.data(Addebito.Cam.data.get());
            campoData.setInit(InitFactory.dataAttuale());
            campoData.initValoreCampo();
            campoData.decora().obbligatorio();
            campoData.setValore(AlbergoLib.getDataProgramma());
            campoData.decora().etichetta("data addebito");
            campoData.decora().lucchetto(true);

            /* campo selettore conto */
            campoConto = CampoFactory.comboLinkSel("conto");
            campoConto.setNomeModuloLinkato(Conto.NOME_MODULO);
            campoConto.setNomeCampoValoriLinkato(Conto.Cam.sigla.get());
            campoConto.setLarScheda(200);
            campoConto.decora().eliminaEtichetta();
            campoConto.decora().lucchetto();
            campoConto.setUsaNuovo(false);

            /* regola il filtro per vedere solo i conti aperti dell'azienda attiva */
            filtroCompleto = new Filtro();
            modConto = ContoModulo.get();
            filtro = modConto.getFiltroAzienda();
            filtroCompleto.add(filtro);
            filtro = FiltroFactory.crea(Conto.Cam.chiuso.get(), false);
            filtroCompleto.add(filtro);
            campoConto.setFiltroCorrente(filtroCompleto);

            /* campo selettore listino */
            campoListino = CampoFactory.comboLinkSel(Addebito.Cam.listino.get());
            campoListino.setNomeModuloLinkato(Listino.NOME_MODULO);
            campoListino.setFiltroBase(FiltroFactory.creaFalso(Listino.Cam.disattivato.get()));
            campoListino.addColonnaCombo(Listino.Cam.descrizione.get());
            campoListino.decora().etichetta("listino");
            campoListino.decora().estrattoSotto(Listino.Estratto.descrizioneCameraPersona);
            campoListino.decora().lucchetto();
            campoListino.setLarScheda(200);
            this.addCampoCollezione(campoListino);

            /* campo quantita' */
            campoQuantita = CampoFactory.intero(Addebito.Cam.quantita.get());
            campoQuantita.decora().etichetta("quantità");
            campoQuantita.decora().obbligatorio();
            campoQuantita.getValidatore().setAccettaNegativi(false);

            /* campo prezzo */
            campoPrezzo = CampoFactory.valuta(Addebito.Cam.prezzo.get());
            campoPrezzo.decora().obbligatorio();

            /* campo totale */
            campoTotale = CampoFactory.calcola(Addebito.Cam.importo.get(),
                    CampoLogica.Calcolo.prodottoValuta,
                    Addebito.Cam.quantita.get(),
                    Addebito.Cam.prezzo.get());
            campoTotale.getCampoDB().setCampoFisico(true);

            /* campo note */
            campoNote = this.copiaCampo(Addebito.Cam.note.get());

            /* campo navigatore per strisciata inserimenti effettuati */
            nav = new NavStrisciata(this.getModulo());
            campoStriscia = CampoFactory.navigatore("addebiti eseguiti", nav);
            this.setNavStriscia(nav);

            panSinistra.add(campoData);
            panConto = new PannelloConto();
            this.setPanConto(panConto);
            panSinistra.add(panConto);
            panSinistra.add(creaPanPrezzi());

            panStriscia.add(campoStriscia);

            panGenerale.add(panSinistra);
            panGenerale.add(panStriscia);

            /* aggiunge il pannello completo al dialogo */
            this.addPannello(panGenerale);

            /* Aggiunge i bottoni */
            super.addBottoneChiudi();
            bot = super.addBottoneCancella();
            bot.setDismetti(false);
            bot = super.addBottoneRegistra();
            bot.setDismetti(false);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Assegno i tasti ai bottoni standard.
     * <p/>
     * Escape <br>
     * Enter <br>
     */
    protected void regolaAzioni() {
        /* variabili e costanti locali di lavoro */

        try { // prova ad eseguire il codice

            super.regolaAzioni();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    public void avvia() {
        super.avvia();
    }// fine del metodo avvia


    /**
     * Rende il dialogo visibile.
     * <p/>
     * Può essere sovrascritto per effettuare operazioni
     * subito prima che il dialogo diventi visibile
     */
    public void rendiVisibile() {
        /* variabili e costanti locali di lavoro */
        Campo campoChiave;
        int codMax;
        Filtro filtro;
        Navigatore nav;

        try { // prova ad eseguire il codice

            /*
             * regolo il filtro del navigatore della strisciata
             * per vedere solo gli addebiti inseriti da questo
             * momento in poi
             */
            campoChiave = this.getModulo().getCampoChiave();
            codMax = this.getModulo().query().valoreMassimo(campoChiave);
            filtro = FiltroFactory.crea(campoChiave, Filtro.Op.MAGGIORE, codMax);
            nav = this.getNavStriscia();
            nav.setFiltroCorrente(filtro);
            nav.aggiornaLista();

            /* cancellazione dei campi */
            this.eventoCancella();

            /* blocca il campo conto se valorizzato */
            if (this.getCodice() != 0) {
                this.lockConto();
            }// fine del blocco if

            /* regola l'etichetta Arrivo del pannello Conto */
            this.getPanConto().syncLabel();

            /* il dialogo diventa visibile */
            super.rendiVisibile();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


    }


    /**
     * Crea il pannello per l'inserimento del prezzo
     * <p/>
     *
     * @return il pannello creato
     */
    private Pannello creaPanPrezzi() {
        Pannello panPrezzi = null;
        Pannello panNumeri = null;

        try { // prova ad eseguire il codice

            panPrezzi = new PannelloFlusso(Layout.ORIENTAMENTO_VERTICALE);
            panNumeri = new PannelloFlusso(Layout.ORIENTAMENTO_ORIZZONTALE);

            /* pannello orizzontale con i campi quantita', prezzo, totale */
            panNumeri.add(campoQuantita);
            panNumeri.add(campoPrezzo);
            panNumeri.add(campoTotale);

            /* pannello verticale con:
             * campo listino, pannello prezzi, campo note e bottoni */
            panPrezzi.creaBordo("addebito");
            panPrezzi.add(campoListino);
            panPrezzi.add(panNumeri);
            panPrezzi.add(campoNote);
            panPrezzi.add(this.creaPanBot());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return panPrezzi;
    }


    /**
     */
    private Pannello creaPanBot() {
        Pannello pan = null;

        try { // prova ad eseguire il codice

            pan = new PannelloFlusso(Layout.ORIENTAMENTO_ORIZZONTALE);
            this.setPanBottoni(pan);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return pan;
    }


    /**
     * Metodo eseguito quando un campo perde il fuoco.
     * <p/>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @param campo interessato
     */
    protected void eventoUscitaCampo(Campo campo) {
        /* variabili e costanti locali di lavoro */
        PannelloConto panConto;

        try { // prova ad eseguire il codice

            if (campo.equals(campoConto)) {
                panConto = this.getPanConto();
                panConto.syncLabel();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Metodo eseguito quando un campo acquisisce il fuoco.
     * <p/>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @param campo interessato
     */
    protected void eventoEntrataCampo(Campo campo) {
        try { // prova ad eseguire il codice
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Metodo eseguito quando il valore memoria di un campo cambia.
     * <p/>
     * Sovrascritto dalle sottoclassi
     */
    @Override
    protected void eventoMemoriaModificata(Campo campo) {
        /* variabili e costanti locali di lavoro */
        int codListino;
        double prezzo;

        /* listino modificato */
        if (campo.equals(campoListino)) {
            codListino = (Integer)campo.getValore();
            prezzo = this.getPrezzoListino(codListino);
            campoPrezzo.setValore(prezzo);
        }// fine del blocco if

    }


    /**
     * Recupera un prezzo dal listino.
     * <p/>
     * Il prezzo è recuperato in funzione della data corrente di addebito.
     *
     * @param codListino il codice del listino
     *
     * @return il prezzo corrispondente
     */
    private double getPrezzoListino(int codListino) {
        /* variabili e costanti locali di lavoro */
        double prezzo = 0;
        Date data;

        try {    // prova ad eseguire il codice

            data = this.getDataAddebito();
            prezzo = ListinoModulo.getPrezzo(codListino, data);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return prezzo;
    }


    /**
     * Recupera il codice del conto selezionato.
     * <p/>
     *
     * @return il codice del conto selezionato, 0 se non selezionato
     */
    private int getCodContoSelezionato() {
        /* variabili e costanti locali di lavoro */
        int codice = 0;
        Campo campo;
        Object valore;

        try {    // prova ad eseguire il codice
            valore = campoConto.getValore();
            codice = Libreria.getInt(valore);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codice;
    }


    /**
     * Ritorna la data di addebito correntemente selezionato.
     * <p/>
     */
    private Date getDataAddebito() {
        return (Date)campoData.getValore();
    }


    /**
     * Controllo se cancellabile.
     * <p/>
     */
    public boolean isCancellabile() {
        /* variabili e costanti locali di lavoro */
        boolean cancellabile = false;
        ArrayList<Campo> campi;

        try { // prova ad eseguire il codice

            /* controllo abilitazione Cancella */
            campi = this.getCampiCancellabili();
            for (Campo campo : campi) {
                if (campo.getCampoDati().isModificato()) {
                    if (!campo.getCampoVideo().isSelezionato()) {
                        cancellabile = true;
                        break;
                    }// fine del blocco if-else
                }// fine del blocco if
            } // fine del ciclo for-each


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return cancellabile;
    }


    /**
     * Invocato quando si preme il bottone cancella.
     * <p/>
     */
    private void resetAddebito() {
        /* variabili e costanti locali di lavoro */
        CampoVideo cv;
        CVDBottone2stati cvd;
        int codListino;
        Double prezzo;
        ArrayList<Campo> campi;

        try {    // prova ad eseguire il codice

            /* recupera la lista dei campi cancellabili */
            campi = this.getCampiCancellabili();

            /* se il campo non e' bloccato rimette in memoria
             * il valore del backup */
            for (Campo campo : campi) {

                cv = campo.getCampoVideo();
                if (cv instanceof CVDBottone2stati) {
                    cvd = (CVDBottone2stati)cv;
                    if (!cvd.getStato()) {
                        campo.setValoreIniziale(campo.getCampoDati().getValoreMemoriaVuoto());
                    }// fine del blocco if
                }// fine del blocco if

            } // fine del ciclo for-each

            /* pone la quantita' a 1 */
            campoQuantita.setValoreIniziale(1);

            /* recupera il prezzo dal listino selezionato */
            codListino = (Integer)campoListino.getValore();
            prezzo = this.getPrezzoListino(codListino);
            campoPrezzo.setValore(prezzo);

            /* campo note */
            campoNote.setValore("");

            /* mando fuori fase il focus per forzare la successiva regolazione */
            KeyboardFocusManager focus;
            focus = KeyboardFocusManager.getCurrentKeyboardFocusManager();
            focus.focusNextComponent();

            /* posiziona il fuoco nel primo campo utile */
            /* (il primo non bloccabile o, se bloccabile, non bloccato)
             * ragiona solo sui campi effettivamente visibili nel pannello*/
            for (Campo campo : this.getCampiPannello()) {
                cv = campo.getCampoVideo();
                if (cv != null) {
                    if (cv instanceof CVDBottone2stati) {
                        cvd = (CVDBottone2stati)cv;
                        if (!cvd.getStato()) {
                            campo.grabFocus();
                            break;
                        }// fine del blocco if
                    } else {
                        campo.grabFocus();
                        break;
                    }// fine del blocco if-else
                }// fine del blocco if
            } // fine del ciclo for-each

            this.sincronizza();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Ritorna la lista dei campi cancellabili.
     * <p/>
     */
    private ArrayList<Campo> getCampiCancellabili() {
        /* variabili e costanti locali di lavoro */
        ArrayList<Campo> campi = null;

        try {    // prova ad eseguire il codice

            campi = new ArrayList<Campo>();

            campi.add(campoListino);
            campi.add(campoQuantita);
            campi.add(campoPrezzo);
            campi.add(campoNote);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return campi;
    }


    /**
     * Nuovo addebito.
     * <p/>
     */
    private void registraDati() {
        /* variabili e costanti locali di lavoro */
        ArrayList<CampoValore> valori;
        int codice;
        Navigatore nav;
        int codListino = 0;
        CampoValore cv;
        int codConto;

        try {    // prova ad eseguire il codice

            /* recupera il codice del conto selezionato */
            codConto = this.getCodContoSelezionato();
            /*
             * recupera i valori di tutti i campi da registrare
             * (il campo totale viene registrato automaticamente dal modello)
             */
            valori = new ArrayList<CampoValore>();
            valori.add(new CampoValore(Addebito.Cam.data.get(), campoData.getValore()));
            valori.add(new CampoValore(Addebito.Cam.conto.get(), codConto));
            valori.add(new CampoValore(Addebito.Cam.quantita.get(), campoQuantita.getValore()));
            valori.add(new CampoValore(Addebito.Cam.prezzo.get(), campoPrezzo.getValore()));
            valori.add(new CampoValore(Addebito.Cam.note.get(), campoNote.getValore()));

            codListino = (Integer)campoListino.getValore();
            cv = new CampoValore(Addebito.Cam.listino.get(), codListino);
            valori.add(cv);

            /* crea il nuovo addebito */
            codice = this.getModulo().query().nuovoRecord(valori);

            if (codice != -1) {

                /* rinfresca la lista del navigatore Addebiti corrente */
                nav = this.getModulo().getNavigatoreCorrente();
                nav.aggiornaLista();
                nav.getLista().setRecordVisibileSelezionato(codice);

                /* aggiorna la strisciata nel dialogo */
                this.getNavStriscia().aggiornaLista();
                this.getNavStriscia().getLista().setRecordVisibileSelezionato(codice);

            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Invocato quando si preme il bottone cancella.
     * <p/>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    @Override
    protected void eventoCancella() {
        this.resetAddebito();
    }


    /**
     * Invocato quando si preme il bottone registra.
     * <p/>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    @Override
    public void eventoRegistra() {
        registraAddebito();
    }


    /**
     * Invocato quando si preme il bottone registra.
     * <p/>
     */
    private void registraAddebito() {
        try {    // prova ad eseguire il codice

            if (this.isRegistrabile()) {
                this.registraDati();
                this.resetAddebito();
            } else {
                new MessaggioAvviso("Record non registrabile");
            }// fine del blocco if-else

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Sincronizzazione della scheda/dialogo.
     * <p/>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    public void sincronizza() {

        super.sincronizza();

        try { // prova ad eseguire il codice

            this.getBottoneCancella().setEnabled(isCancellabile());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * il pulsante Registra e' abilitato se, oltre ai
     * normali controlli della superclasse, è selezionato
     * un elemento dal popup listino / sottoconto appropriato
     */
    public boolean isRegistrabile() {
        /* variabili e costanti locali di lavoro */
        boolean registrabile = false;
        int codConto;

        try { // prova ad eseguire il codice

            registrabile = super.isRegistrabile();

            /* deve essere selezionato un valore dal popup addebiti */
            if (registrabile) {
                registrabile = false;
                if (campoListino != null) {
                    if (!campoListino.isVuoto()) {
                        registrabile = true;
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

            /* deve essere selezionato un conto */
            if (registrabile) {
                codConto = this.getCodContoSelezionato();
                registrabile = (codConto > 0);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return registrabile;
    }


    /**
     * Predispone il dialogo per un dato conto
     * <p/>
     */
    private void lockConto() {
        /* variabili e costanti locali di lavoro */
        CampoVideo cv;
        CVDBottone2stati cvd;

        try {    // prova ad eseguire il codice

            cv = campoConto.getCampoVideo();
            if (cv instanceof CVDBottone2stati) {
                cvd = (CVDBottone2stati)cv;
                cvd.setStato(true);
            }// fine del blocco if
            campoConto.setModificabile(false);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    private int getCodice() {
        return codice;
    }


    private void setCodice(int codice) {
        this.codice = codice;
    }


    private Navigatore getNavStriscia() {
        return navStriscia;
    }


    private void setNavStriscia(Navigatore navStriscia) {
        this.navStriscia = navStriscia;
    }


    private Pannello getPanBottoni() {
        return panBottoni;
    }


    private void setPanBottoni(Pannello panBottoni) {
        this.panBottoni = panBottoni;
    }


    private PannelloConto getPanConto() {
        return panConto;
    }


    private void setPanConto(PannelloConto panConto) {
        this.panConto = panConto;
    }


    /**
     * Pannello selettore del conto.
     * </p>
     */
    private final class PannelloConto extends PannelloFlusso {

        private JLabel labelArrivo;


        /**
         * Costruttore completo con parametri.
         * <p/>
         */
        public PannelloConto() {
            /* rimanda al costruttore della superclasse */
            super(Layout.ORIENTAMENTO_VERTICALE);

            try { // prova ad eseguire il codice
                /* regolazioni iniziali di riferimenti e variabili */
                this.inizia();
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }// fine del metodo costruttore completo


        /**
         * Regolazioni immediate di riferimenti e variabili. <br>
         * Metodo chiamato direttamente dal costruttore <br>
         *
         * @throws Exception unaEccezione
         */
        private void inizia() throws Exception {
            /* variabili e costanti locali di lavoro */
            JLabel label;

            try { // prova ad eseguire il codice

                label = new JLabel();
                this.setLabelArrivo(label);
                TestoAlgos.setLegenda(label);

                this.setGapMassimo(4);
                this.creaBordo("conto");
                this.add(campoConto);
                this.add(label);


            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

        }


        /**
         * Regola il testo della label Arrivo Con in base al cliente corrente.
         * <p/>
         */
        private void syncLabel() {
            /* variabili e costanti locali di lavoro */
            Object valore;
            int codConto;
            Modulo modConto;
            int codArrivoCon;
            String descArrivo;


            try {    // prova ad eseguire il codice
                valore = campoConto.getValore();
                codConto = Libreria.getInt(valore);
                modConto = ContoModulo.get();
                codArrivoCon = modConto.query().valoreInt(Conto.Cam.arrivoCon.get(), codConto);
                descArrivo = Periodo.TipiAP.getDescrizione(codArrivoCon);
                this.getLabelArrivo().setText("Arrivo: " + descArrivo);
            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch
        }


        private JLabel getLabelArrivo() {
            return labelArrivo;
        }


        private void setLabelArrivo(JLabel labelArrivo) {
            this.labelArrivo = labelArrivo;
        }


    } // fine della classe 'interna'


    /**
     * Navigatore per la strisciata addebiti eseguiti
     * </p>
     */
    public final class NavStrisciata extends NavigatoreL {

        /**
         * Costruttore completo con parametri. <br>
         *
         * @param unModulo di riferimento
         */
        public NavStrisciata(Modulo unModulo) {
            /* rimanda al costruttore della superclasse */
            super(unModulo);

            try { // prova ad eseguire il codice
                this.inizia();
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }// fine del metodo costruttore completo


        /**
         * Regolazioni immediate di riferimenti e variabili. <br>
         * Metodo chiamato direttamente dal costruttore <br>
         *
         * @throws Exception unaEccezione
         */
        private void inizia() throws Exception {
            try { // prova ad eseguire il codice
                this.setNomeVista(Addebito.Vis.vistaDialogo.get());
                this.setAggiornamentoTotaliContinuo(true);
                this.getLista().setOrdinabile(false);
                this.setRigheLista(20);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        public void inizializza() {
            /* variabili e costanti locali di lavoro */
            Lista lista;
            Ordine ordine;
            Modulo modulo;
            Campo campo;

            super.inizializza();

            try { // prova ad eseguire il codice

                /* modifica l'ordine della lista - ordinata per campo chiave
                 * in modo da vedere gli addebiti nella sequenza in cui vengono
                 * inseriti */
                lista = this.getLista();
                modulo = AddebitoModulo.get();
                campo = modulo.getCampoChiave();
                ordine = new Ordine();
                ordine.add(campo);
                lista.setOrdine(ordine);

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

        }// fine del metodo inizializza

    } // fine della classe 'interna'


    /**
     * Classe 'interna'. </p>
     */
    public final class DialogoRisolvi extends DialogoListaLink {

        /**
         * Costruttore base con parametri.
         * <p/>
         */
        public DialogoRisolvi(Modulo moduloLink, String campoLink) {

            super(moduloLink, campoLink);

        } /* fine del metodo costruttore completo */


        /**
         * Sincronizzazione della scheda/dialogo.
         * <p/>
         * Metodo sovrascritto nelle sottoclassi <br>
         */
        public void sincronizza() {
            /* variabili e costanti locali di lavoro */
            BottoneDialogo bottoneConferma;
            ListaSingola lista;
            int num;

            super.sincronizza();

            try { // prova ad eseguire il codice
                bottoneConferma = this.getBottoneConferma();
                lista = (ListaSingola)this.getCampoLista()
                        .getCampoVideo()
                        .getComponente();
                num = lista.getLista().getSelectedIndices().length;
                if (bottoneConferma.isEnabled()) {

                    if (num != 1) {
                        bottoneConferma.setEnabled(false);
                    }// fine del blocco if

                }// fine del blocco if

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }

    } // fine della classe 'interna'


}// fine della classe
