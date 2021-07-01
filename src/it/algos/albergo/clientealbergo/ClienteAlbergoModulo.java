/**
 * Title:     ClienteAlbergoModulo
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      3-mag-2004
 */
package it.algos.albergo.clientealbergo;

import it.algos.albergo.Albergo;
import it.algos.albergo.AlbergoLib;
import it.algos.albergo.AlbergoModulo;
import it.algos.albergo.clientealbergo.compleanni.GestioneCompleanni;
import it.algos.albergo.clientealbergo.indirizzoalbergo.IndirizzoAlbergo;
import it.algos.albergo.clientealbergo.indirizzoalbergo.IndirizzoAlbergoModulo;
import it.algos.albergo.clientealbergo.tabelle.autorita.Autorita;
import it.algos.albergo.clientealbergo.tabelle.autorita.AutoritaModulo;
import it.algos.albergo.clientealbergo.tabelle.parente.Parentela;
import it.algos.albergo.clientealbergo.tabelle.parente.ParentelaModulo;
import it.algos.albergo.clientealbergo.tabelle.tipodocumento.TipoDocumento;
import it.algos.albergo.clientealbergo.tabelle.tipodocumento.TipoDocumentoModulo;
import it.algos.albergo.evento.CambioAziendaAz;
import it.algos.albergo.evento.CambioAziendaEve;
import it.algos.albergo.evento.CambioDataAz;
import it.algos.albergo.evento.CambioDataEve;
import it.algos.albergo.presenza.PresenzaModulo;
import it.algos.albergo.promemoria.PromemoriaModulo;
import it.algos.albergo.storico.NavStorico;
import it.algos.albergo.storico.Storico;
import it.algos.albergo.tabelle.lingua.Lingua;
import it.algos.albergo.tabelle.lingua.LinguaModulo;
import it.algos.base.albero.AlberoNodo;
import it.algos.base.azione.AzModulo;
import it.algos.base.azione.AzSpecifica;
import it.algos.base.campo.base.Campo;
import it.algos.base.costante.CostanteColore;
import it.algos.base.database.dati.Dati;
import it.algos.base.database.util.Operatore;
import it.algos.base.dialogo.DialogoBase;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.menu.menu.MenuBase;
import it.algos.base.modulo.Modulo;
import it.algos.base.modulo.ModuloBase;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.Query;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.query.selezione.QuerySelezione;
import it.algos.base.statusbar.StatusBar;
import it.algos.base.wrapper.Campi;
import it.algos.base.wrapper.CampoValore;
import it.algos.gestione.anagrafica.Anagrafica;
import it.algos.gestione.anagrafica.AnagraficaModulo;
import it.algos.gestione.anagrafica.categoria.CatAnagraficaModulo;
import it.algos.gestione.anagrafica.tabelle.titolo.Titolo;
import it.algos.gestione.anagrafica.tabelle.titolo.TitoloModulo;
import it.algos.gestione.contatto.ContattoModulo;
import it.algos.gestione.indirizzo.Indirizzo;
import it.algos.gestione.indirizzo.IndirizzoModulo;
import it.algos.gestione.indirizzo.tabelle.citta.Citta;
import it.algos.gestione.indirizzo.tabelle.citta.CittaModulo;
import it.algos.gestione.indirizzo.tabelle.nazione.Nazione;
import it.algos.gestione.indirizzo.tabelle.nazione.NazioneModulo;
import it.algos.gestione.indirizzo.tabelle.provincia.Provincia;
import it.algos.gestione.indirizzo.tabelle.provincia.ProvinciaModulo;
import it.algos.gestione.tabelle.contibanca.ContiBancaModulo;
import it.algos.gestione.tabelle.iva.IvaModulo;
import it.algos.gestione.tabelle.tipopagamento.TipoPagamentoModulo;

import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * ClienteAlbergoModulo - Contenitore dei riferimenti agli oggetti del package.
 * </p>
 * Questa classe concreta: <ul>
 * <li> Contiene tutti i riferimenti agli oggetti delle classi che servono
 * per gestire una parte del programma </li>
 * <li>  </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 3-mag-2004 ore 13.19.18
 */
public final class ClienteAlbergoModulo extends AnagraficaModulo {

    /**
     * Nome-chiave interno di questo modulo (obbligatorio)
     */
    private static final String NOME_CHIAVE = ClienteAlbergo.NOME_MODULO;

    /**
     * Titolo della finestra del navigatore
     * (usato nel Navigatore)
     */
    private static final String TITOLO_FINESTRA = ClienteAlbergo.TITOLO_FINESTRA;

    /**
     * Titolo del modulo come appare nel Menu Moduli degli altri moduli
     * (usato nel Navigatore)
     */
    private static final String TITOLO_MENU = ClienteAlbergo.TITOLO_MENU;

    /**
     * nome del programma che compare in basso (se vuoto, usa il default)
     * viene usato il nome del modulo che parte per primo
     */
    private static final String NOME_PROGRAMMA = TITOLO_FINESTRA;


    /**
     * Costruttore provvisorio, usato solo per portarsi il percorso
     * della classe (implicito) ed il nome chiave (esplicito) <br>
     */
    public ClienteAlbergoModulo() {
        /* regola la variabile di istanza con la costante */
        super.setNomeModulo(NOME_CHIAVE);

        /* regola il nome del programma (se questo modulo parte per primo) */
        super.setNomeProgramma(NOME_PROGRAMMA);
    } /* fine del metodo costruttore provvisorio */


    /**
     * Costruttore <br>
     *
     * @param unNodo nodo dell'albero moduli
     */
    public ClienteAlbergoModulo(AlberoNodo unNodo) {
        /* rimanda al costruttore della superclasse */
        this(NOME_CHIAVE, unNodo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore


    /**
     * Costruttore completo <br>
     *
     * @param unNomeModulo nome interno del modulo
     * @param unNodo nodo dell'albero moduli
     */
    public ClienteAlbergoModulo(String unNomeModulo, AlberoNodo unNodo) {
        /* rimanda al costruttore della superclasse */
        super(unNomeModulo, unNodo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }/* fine del metodo costruttore completo */


    /**
     * Regolazioni immediate di riferimenti e variabili. <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        /* selezione del modello (obbligatorio) */
        super.setModello(new ClienteAlbergoModello());

        /* regola il titolo della finestra del navigatore */
        super.setTitoloFinestra(TITOLO_FINESTRA);

        /* regola il titolo di questo modulo nei menu di altri moduli */
        super.setTitoloMenu(TITOLO_MENU);

        super.addNavigatoreCorrente(new NavCliente(this));

        this.setSchedaPop(new ClienteAlbergoScheda(this));

        this.setNomePubblico("cliente");

        /* assegna una icona al modulo */
        this.setIcona("clientealbergo24");
    }// fine del metodo inizia


    /**
     * Regolazioni iniziali <i>una tantum</i>.
     * </p>
     * Metodo chiamato dalla classe Progetto <br>
     * Viene eseguito una sola volta <br>
     * <p/>
     * Inizializza il gestore , prima di tutto (servono i Comandi per
     * inzializzare i Campi) <br>
     * Tenta di inizializzare il modulo <br>
     * Prima inizializza il modello, se e' riuscito
     * inizializza anche gli altri oggetti del modulo <br>
     *
     * @return true se il modulo e' stato inizializzato
     */
    public boolean inizializza() {
        /* variabili e costanti locali di lavoro */
        Modulo mod;

        try { // prova ad eseguire il codice
            super.inizializza();

            /* si registra presso il modulo albergo per  */
            /* essere informato quando cambia l' azienda
            /* e quando vengono eliminate aziende  */
            mod = Progetto.getModulo(Albergo.NOME_MODULO);
            if (mod != null) {
                mod.addListener(AlbergoModulo.Evento.cambioAzienda, new AzioneCambioAzienda());
                mod.addListener(AlbergoModulo.Evento.cambioData, new AzioneCambioData());
            }// fine del blocco if

            /* regola l'azienda attiva */
            this.cambioAzienda();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return true;

    } // fine del metodo


    /**
     * Regolazioni di avvio, ogni volta che questo oggetto deve 'ripartire',
     * per essere sicuri che sia 'pulito'
     * <p/>
     * Metodo chiamato da altre classi <br>
     * Viene eseguito tutte le volte che necessita  <br>
     */
    @Override
    public void avvia() {
        try { // prova ad eseguire il codice
            super.avvia();
            this.setAvviato(false);

            /* selezione inziale di un filtro, tramite il popup */
            this.setPopup(ClienteAlbergo.Pop.evidenza);
            this.setPopup(ClienteAlbergo.Pop.parentela);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    } /* fine del metodo */


    /**
     * Creazione e regolazione dei Navigatori.
     * <p/>
     * Sovrascrive il metodo della superclasse per non fare nulla
     */
    protected void creaNavigatori() {
        /* variabili e costanti locali di lavoro */
//        Navigatore nav;

        try { // prova ad eseguire il codice

//            /* navigatore per il gruppo */
//            nav = NavigatoreFactory.listaScheda(this);
//            nav.setUsaFinestra(false);

//            /* assegna una ricerca specifica al navigatore di default */
//            nav = this.getNavigatoreDefault();
//            nav.setRicerca(new ClienteAlbergoRicerca());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }

    /**
     * Creazione del navigatore per lo storico.
     * <p/>
     * @return il navigatore per lo storico
     */
    public NavStorico getNavStorico() {
        /* variabili e costanti locali di lavoro */
        NavStorico nav = null;

        try { // prova ad eseguire il codice

            /* navigatore usato dal pannello Storico */
            nav = new NavStorico(this, ClienteAlbergo.Nav.gruppoStorico.get(), ClienteAlbergo.Vis.gruppoStorico.get());
            nav.inizializza();
            nav.avvia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return nav;
    }



    /**
     * Invocato prima della inizializzazione dei Navigatori.
     * <p/>
     * Opportunità per la sottoclasse di regolare i Navigatori
     * prima della inizializzazione
     */
    @Override
    protected void regolaNavigatori() {
        /* variabili e costanti locali di lavoro */
        Navigatore nav;

        try { // prova ad eseguire il codice
            super.regolaNavigatori();

            /* regola il navigatore default */
            nav = this.getNavigatoreDefault();
            nav.getPortaleLista().getToolBar().setUsaPreferito(false);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Crea tutti i Moduli indispensabili per il funzionamento di questo modulo.
     * <br>
     * Per ogni modulo, invoca il metodo della superclasse, passandogli
     * un'istanza provvisoria del modulo <br>
     * Questa istanza viene usata solo per portarsi il percorso della
     * classe (implicito) ed il nome chiave (esplicito) <br>
     * La creazione definitiva del Modulo viene delegata alla classe
     * Progetto nel metodo creaModulo() <br>
     */
    protected void creaModuli() {
        try { // prova ad eseguire il codice

            /**
             * Non invochiamo super.creaModuli perché questo
             * modulo richiede IndirizzoAlbergoModulo invece
             * di IndirizzoModulo
             */
            super.creaModulo(new TitoloModulo());
            super.creaModulo(new ContattoModulo());
            super.creaModulo(new TipoPagamentoModulo());
            super.creaModulo(new IvaModulo());
            super.creaModulo(new ContiBancaModulo());   //?
            super.creaModulo(new CatAnagraficaModulo());

            super.creaModulo(new TipoDocumentoModulo());
            super.creaModulo(new AutoritaModulo());
            super.creaModulo(new IndirizzoAlbergoModulo());
            super.creaModulo(new ParentelaModulo());
            super.creaModulo(new LinguaModulo());
            super.creaModulo(new PromemoriaModulo());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo


    /**
     * Aggiunge i moduli (e le tabelle) al menu.
     * <br>
     * Aggiunge alla collezione moduli (di questo modulo), gli eventuali
     * moduli (o tabelle), che verranno poi inserite nel menu moduli e
     * tabelle, dalla classe Navigatore <br>
     * I moduli e le tabelle appaiono nei rispettivi menu, nell'ordine in
     * cui sono elencati in questo metodo <br>
     * Per ogni modulo, invoca il metodo della superclasse, passandogli
     * il nome-chiave del modulo <br>
     */
    protected void addModuliVisibili() {
        try { // prova ad eseguire il codice
            super.addModuliVisibili();

            // moduli
            AlbergoLib.addModuliVisibili(this);

            // tabelle
            super.addModuloVisibile(Titolo.NOME_MODULO);
            super.addModuloVisibile(Parentela.NOME_MODULO);
            super.addModuloVisibile(IndirizzoAlbergo.NOME_MODULO);
            super.addModuloVisibile(TipoDocumento.NOME_MODULO);
            super.addModuloVisibile(Autorita.NOME_MODULO);
            super.addModuloVisibile(Lingua.NOME_MODULO);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo


    /**
     * Crea eventuali azioni specifiche del modulo.
     * <p/>
     * Le azioni vengono aggiunte al navigatore corrente <br>
     * Le azioni vengono aggiunte alla
     * toolbar della lista oppure al menu Archivio <br>
     * Metodo invocato dal ciclo inizializza <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     */
    @Override
    protected void creaAzioni() {
        try { // prova ad eseguire il codice

//            /* importazione anagrafiche da file esterno */
//            super.addAzione(new AzioneImport());

            /* Ricerca Compleanni */
            super.addAzione(new AzCompleanni());



        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Dialogo di importazione dati (una tantum).
     * <p/>
     */
    private void importDatiIniziali() {
        /* variabili e costanti locali di lavoro */
        ImportClientiDialogo dialogo;

        try {    // prova ad eseguire il codice
            dialogo = new ImportClientiDialogo(this);
            dialogo.inizializza();
            dialogo.avvia();

            if (dialogo.isConfermato()) {
                dialogo.esegueImport();
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Dialogo di gestione compleanni.
     * <p/>
     */
    private void compleanni() {
        /* variabili e costanti locali di lavoro */
        GestioneCompleanni compleanni;
        Navigatore nav;

        try {    // prova ad eseguire il codice
//            new MessaggioAvviso("Funzione non ancora attiva");
            if (true) {
                nav = this.getNavigatoreCorrente();
                compleanni = new GestioneCompleanni(nav);
                compleanni.avvia();
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }






    /**
     * Dialogo di visualizzazione storico di un cliente.
     * <p/>
     * @param codice del cliente
     */
    public static void showStorico(int codice) {

        try {    // prova ad eseguire il codice

            Storico stor = new Storico();
            stor.avvia(codice);

            Modulo mod = ClienteAlbergoModulo.get();
            String nome = mod.query().valoreStringa(Anagrafica.Cam.soggetto.get(), codice);

            DialogoBase dialogo = new DialogoBase();
            dialogo.setTitolo("Storico di "+nome);
            dialogo.addComponente(stor);
            dialogo.avvia();

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }



    /**
     * Controlla se un cliente è da considerarsi bambino a una certa data.
     * <p/>
     *
     * @param codCliente da controllare
     * @param dataRif data di controllo
     *
     * @return true se da considerarsi bambino
     */
    public static boolean isBambino(int codCliente, Date dataRif) {
        /* variabili e costanti locali di lavoro */
        boolean bambino = false;
        Modulo mod;
        int etaMaxBambini = 12;
        Date dataMax;
        Date dataNato;
        int giornoNato;
        int meseNato;
        int annoNato;
        int annoMax;

        try {    // prova ad eseguire il codice

            mod = ClienteAlbergoModulo.get();
            dataNato = mod.query().valoreData(ClienteAlbergo.Cam.dataNato.get(), codCliente);

            giornoNato = Lib.Data.getNumeroGiorno(dataNato);
            meseNato = Lib.Data.getNumeroMese(dataNato);
            annoNato = Lib.Data.getAnno(dataNato);

            annoMax = annoNato + etaMaxBambini;

            dataMax = Lib.Data.creaData(giornoNato, meseNato, annoMax);

            bambino = (Lib.Data.isPosteriore(dataRif, dataMax));

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return bambino;
    }


    /**
     * Controlla se è un capogruppo.
     * <p/>
     *
     * @param codCliente da controllare
     *
     * @return true se capogruppo
     */
    public static boolean isCapogruppo(int codCliente) {
        /* variabili e costanti locali di lavoro */
        boolean capogruppo = false;
        Modulo modClienti;

        try {    // prova ad eseguire il codice
            modClienti = ClienteAlbergoModulo.get();
            capogruppo = modClienti.query().valoreBool(modClienti.getCampoPreferito(), codCliente);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return capogruppo;
    }


    /**
     * Ritorna il codice del capogruppo di un cliente.
     * <p/>
     *
     * @param codCliente da controllare
     *
     * @return il codice del capogruppo
     */
    public static int getCodCapogruppo(int codCliente) {
        /* variabili e costanti locali di lavoro */
        int codCapo = 0;
        Modulo modClienti;

        try {    // prova ad eseguire il codice
            modClienti = ClienteAlbergoModulo.get();
            codCapo = modClienti.query().valoreInt(ClienteAlbergo.Cam.linkCapo.get(), codCliente);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codCapo;
    }


    /**
     * Ritorna il codice di tutti i membri del gruppo di un cliente
     * compreso il cliente stesso.
     * <p/>
     * I membri sono ritornati in ordine di Capogruppo e sottordine di Parentela
     *
     * @param codCliente da controllare
     *
     * @return la lista dei codici dei membri
     */
    public static int[] getCodMembri(int codCliente) {
        /* variabili e costanti locali di lavoro */
        int[] codMembri = null;
        int codCapo = 0;
        Modulo modClienti;
        Modulo modParentela;
        Filtro filtro;
        Ordine ordine;
        Campo campo;

        try {    // prova ad eseguire il codice
            codCapo = getCodCapogruppo(codCliente);
            modClienti = ClienteAlbergoModulo.get();
            modParentela = ParentelaModulo.get();

            filtro = FiltroFactory.crea(ClienteAlbergo.Cam.linkCapo.get(), codCapo);

            ordine = new Ordine();
            campo = modClienti.getCampo(ClienteAlbergo.Cam.capogruppo);
            ordine.add(campo, Operatore.DISCENDENTE);
            campo = modParentela.getCampoOrdine();
            ordine.add(campo);

            codMembri = modClienti.query().valoriChiave(filtro, ordine);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codMembri;
    }


    /**
     * Ritorna il codice dell'indirizzo di un cliente.
     * <p/>
     * Se il cliente non ha indirizzo e ha un capogruppo, ritorna
     * il codice indirizzo del capogruppo
     *
     * @param codCliente codice del cliente
     *
     * @return il codice dell'indirizzo
     */
    public static int getCodIndirizzo(int codCliente) {
        /* variabili e costanti locali di lavoro */
        int codInd = 0;
        int codCapo;
        Modulo mod;

        try { // prova ad eseguire il codice

            mod = ClienteAlbergoModulo.get();
            codInd = mod.query().valoreInt(ClienteAlbergo.Cam.indirizzoInterno.get(), codCliente);

            if (codInd == 0) {
                codCapo = ClienteAlbergoModulo.getCodCapogruppo(codCliente);
                if ((codCapo > 0) && (codCapo != codCliente)) {
                    codInd = mod.query().valoreInt(
                            ClienteAlbergo.Cam.indirizzoInterno.get(),
                            codCapo);
                }// fine del blocco if

            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        return codInd;
    }


    /**
     * Ritorna i campi di Indirizzo di un cliente.
     * <p/>
     *
     * @param codCliente il codice del cliente
     *
     * @return una mappa che usa come chiave l'elemento campo dall'interfaccia
     *         Campi e come valore il valore stringa del campo corrispondente
     */
    public static HashMap<Campi, String> getIndirizzo(int codCliente) {
        /* variabili e costanti locali di lavoro */
        HashMap<Campi, String> mappa = null;
        int codIndirizzo;
        Query query;
        Filtro filtro;
        Dati dati;
        Modulo modIndirizzo;
        Campo campoCitta;
        Campo campoProvincia;
        Campo campoNazione;

        try {    // prova ad eseguire il codice

            mappa = new HashMap<Campi, String>();
            codIndirizzo = ClienteAlbergoModulo.getCodIndirizzo(codCliente);

            modIndirizzo = IndirizzoAlbergoModulo.get();
            filtro = FiltroFactory.codice(modIndirizzo, codIndirizzo);
            query = new QuerySelezione(modIndirizzo);
            query.addCampo(Indirizzo.Cam.indirizzo);
            query.addCampo(Indirizzo.Cam.indirizzo2);
            query.addCampo(Indirizzo.Cam.cap);
            campoCitta = CittaModulo.get().getCampo(Citta.Cam.citta);
            query.addCampo(campoCitta);
            campoProvincia = ProvinciaModulo.get().getCampo(Provincia.Cam.sigla);
            query.addCampo(campoProvincia);
            campoNazione = NazioneModulo.get().getCampo(Nazione.Cam.nazione);
            query.addCampo(campoNazione);
            query.setFiltro(filtro);
            dati = modIndirizzo.query().querySelezione(query);

            mappa.put(Indirizzo.Cam.indirizzo, dati.getStringAt(Indirizzo.Cam.indirizzo.get()));
            mappa.put(Indirizzo.Cam.indirizzo2, dati.getStringAt(Indirizzo.Cam.indirizzo2.get()));
            mappa.put(Indirizzo.Cam.cap, dati.getStringAt(Indirizzo.Cam.cap.get()));
            mappa.put(Indirizzo.Cam.citta, dati.getStringAt(campoCitta));
            mappa.put(Provincia.Cam.sigla, dati.getStringAt(campoProvincia));
            mappa.put(Nazione.Cam.nazione, dati.getStringAt(campoNazione));
            dati.close();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return mappa;
    }


    /**
     * Controlla la validità dei documenti a una certa data.
     * <p/>
     *
     * @param codCliente da controllare
     * @param dataCheck data alla quale controllare la validità del documento
     *
     * @return elenco degli elementi di errore riscontrati
     */
    public static ArrayList<ClienteAlbergo.ErrDatiAnag> checkDocValido(
            int codCliente, Date dataCheck) {
        /* variabili e costanti locali di lavoro */
        ArrayList<ClienteAlbergo.ErrDatiAnag> errori = new ArrayList<ClienteAlbergo.ErrDatiAnag>();
        int tipoDoc;
        int autoritaDoc;
        String numeroDoc;
        Date dataRilascio;
        Date scadenza;
        Modulo mod;
        Query query;
        Filtro filtro;
        Dati dati;

        try {    // prova ad eseguire il codice

            mod = ClienteAlbergoModulo.get();

            query = new QuerySelezione(mod);
            query.addCampo(ClienteAlbergo.Cam.tipoDoc.get());
            query.addCampo(ClienteAlbergo.Cam.autoritaDoc.get());
            query.addCampo(ClienteAlbergo.Cam.dataDoc.get());
            query.addCampo(ClienteAlbergo.Cam.numDoc.get());
            query.addCampo(ClienteAlbergo.Cam.scadenzaDoc.get());
            filtro = FiltroFactory.codice(mod, codCliente);
            query.setFiltro(filtro);
            dati = mod.query().querySelezione(query);
            tipoDoc = dati.getIntAt(ClienteAlbergo.Cam.tipoDoc.get());
            autoritaDoc = dati.getIntAt(ClienteAlbergo.Cam.autoritaDoc.get());
            dataRilascio = dati.getDataAt(ClienteAlbergo.Cam.dataDoc.get());
            numeroDoc = dati.getStringAt(ClienteAlbergo.Cam.numDoc.get());
            scadenza = dati.getDataAt(ClienteAlbergo.Cam.scadenzaDoc.get());
            dati.close();

            /* controllo esistenza tipo doc */
            if (tipoDoc <= 0) {
                errori.add(ClienteAlbergo.ErrDatiAnag.tipoDocMancante);
            }// fine del blocco if

            /* controllo esistenza autorita doc */
            if (autoritaDoc <= 0) {
                errori.add(ClienteAlbergo.ErrDatiAnag.autoritaRilDocDocMancante);
            }// fine del blocco if

            /* controllo esistenza data rilascio doc */
            if (Lib.Data.isVuota(dataRilascio)) {
                errori.add(ClienteAlbergo.ErrDatiAnag.dataRilDocDocMancante);
            }// fine del blocco if

            /* controllo esistenza numero doc */
            if (!Lib.Testo.isValida(numeroDoc)) {
                errori.add(ClienteAlbergo.ErrDatiAnag.numeroDocMancante);
            }// fine del blocco if

            /* controllo scadenza doc */
            if (!Lib.Data.isPosterioreUguale(dataCheck, scadenza)) {
                errori.add(ClienteAlbergo.ErrDatiAnag.docScaduto);
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return errori;
    }


    /**
     * Controlla se un cliente è valido ai fini della stampa di PS
     * a una certa data.
     * <p/>
     *
     * @param codCliente il codice cliente
     * @param dataCheck data alla quale controllare la validità
     *
     * @return true se è valido
     */
    public static boolean isValidoPS(int codCliente, Date dataCheck) {
        /* variabili e costanti locali di lavoro */
        boolean valido = false;
        ArrayList<ClienteAlbergo.ErrDatiAnag> errori;

        try {    // prova ad eseguire il codice
            errori = checkValidoPS(codCliente, dataCheck);
            valido = (errori.size() == 0);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return valido;
    }


    /**
     * Controlla se i dati di un cliente sono validi
     * per il registro di PS a una certa data.
     * <p/>
     *
     * @param codCliente il codice del cliente da controllare
     * @param dataCheck data alla quale controllare la validità
     *
     * @return elenco degli elementi di errore riscontrati
     */
    public static ArrayList<ClienteAlbergo.ErrDatiAnag> checkValidoPS(
            int codCliente, Date dataCheck) {
        /* variabili e costanti locali di lavoro */
        ArrayList<ClienteAlbergo.ErrDatiAnag> errori = new ArrayList<ClienteAlbergo.ErrDatiAnag>();
        Modulo modCliente;
        Modulo modIndirizzo;
        Query query;
        Filtro filtro;
        Dati dati;

        String cognome;
        String nome;
        int cittaNascita;
        Date dataNascita;
        int cittaResidenza = 0;

        int codIndirizzo;

        try {    // prova ad eseguire il codice

            /* recupera i dati da Anagrafica */
            modCliente = ClienteAlbergoModulo.get();

            query = new QuerySelezione(modCliente);
            query.addCampo(Anagrafica.Cam.cognome.get());
            query.addCampo(Anagrafica.Cam.nome.get());
            query.addCampo(ClienteAlbergo.Cam.luogoNato.get());
            query.addCampo(ClienteAlbergo.Cam.dataNato.get());
            filtro = FiltroFactory.codice(modCliente, codCliente);
            query.setFiltro(filtro);

            dati = modCliente.query().querySelezione(query);
            cognome = dati.getStringAt(Anagrafica.Cam.cognome.get());
            nome = dati.getStringAt(Anagrafica.Cam.nome.get());
            cittaNascita = dati.getIntAt(ClienteAlbergo.Cam.luogoNato.get());
            dataNascita = dati.getDataAt(ClienteAlbergo.Cam.dataNato.get());
            dati.close();

            /* recupera i dati da Indirizzo, se esistente */
            codIndirizzo = ClienteAlbergoModulo.getCodIndirizzo(codCliente);
            if (codIndirizzo > 0) {
                modIndirizzo = IndirizzoAlbergoModulo.get();
                query = new QuerySelezione(modIndirizzo);
                query.addCampo(Indirizzo.Cam.citta);
                filtro = FiltroFactory.codice(modIndirizzo, codIndirizzo);
                query.setFiltro(filtro);
                dati = modIndirizzo.query().querySelezione(query);
                cittaResidenza = dati.getIntAt(Indirizzo.Cam.citta.get());
                dati.close();
            }// fine del blocco if-else

            /* controllo dati e costruzione errore */
            errori = ClienteAlbergoModulo.checkDocValido(codCliente, dataCheck);

            if (!Lib.Testo.isValida(cognome)) {
                errori.add(ClienteAlbergo.ErrDatiAnag.cognomeMancante);
            }// fine del blocco if

            if (!Lib.Testo.isValida(nome)) {
                errori.add(ClienteAlbergo.ErrDatiAnag.nomeMancante);
            }// fine del blocco if

            if (cittaNascita <= 0) {
                errori.add(ClienteAlbergo.ErrDatiAnag.luogoNascitaMancante);
            }// fine del blocco if

            if (Lib.Data.isVuota(dataNascita)) {
                errori.add(ClienteAlbergo.ErrDatiAnag.dataNascitaMancante);
            }// fine del blocco if

            if (cittaResidenza <= 0) {
                errori.add(ClienteAlbergo.ErrDatiAnag.localitaResidenzaMancante);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return errori;
    }


    /**
     * Controlla se un cliente è valido ai fini della scheda di notifica
     * in qualità di capogruppo scheda (a una certa data) o di membro.
     * <p/>
     *
     * @param codCliente il codice cliente
     * @param capo true se il controllo è da effettuare in qualità di capogruppo della scheda
     * false se in qualità di membro
     * @param dataNotifica la data della notifica, significativo solo per capogruppo scheda
     *
     * @return true se è valido
     */
    public static boolean isValidoNotifica(int codCliente, boolean capo, Date dataNotifica) {
        /* variabili e costanti locali di lavoro */
        boolean valido = false;
        ArrayList<ClienteAlbergo.ErrDatiAnag> errori;

        try {    // prova ad eseguire il codice
            errori = checkValidoNotifica(codCliente, capo, dataNotifica);
            valido = (errori.size() == 0);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return valido;
    }


    /**
     * Controlla se i dati di un cliente sono validi per la scheda di notifica
     * in qualità di Capogruppo scheda (a una certa data) o di Membro.
     * <p/>
     *
     * @param codCliente il codice del cliente da controllare
     * @param capo se il controllo è da effetuare in qualità di Capogruppo della scheda o di Membro
     * @param dataNotifica la data della notifica (significativo solo per capogruppo)
     *
     * @return elenco degli errori riscontrati.
     */
    public static ArrayList<ClienteAlbergo.ErrDatiAnag> checkValidoNotifica(
            int codCliente, boolean capo, Date dataNotifica) {
        /* variabili e costanti locali di lavoro */
        ArrayList<ClienteAlbergo.ErrDatiAnag> errori = new ArrayList<ClienteAlbergo.ErrDatiAnag>();

        try {    // prova ad eseguire il codice
            if (capo) {
                errori = checkValidoNotificaCapo(codCliente, dataNotifica);
            } else {
                errori = checkValidoNotificaMembro(codCliente, dataNotifica);
            }// fine del blocco if-else
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return errori;
    }


    /**
     * Controlla se i dati di un cliente sono validi per la scheda di notifica
     * in qualità di Capogruppo a una certa data.
     * <p/>
     *
     * @param codCliente il codice del cliente da controllare
     * @param dataNotifica la data della notifica
     *
     * @return elenco degli errori riscontrati.
     */
    private static ArrayList<ClienteAlbergo.ErrDatiAnag> checkValidoNotificaCapo(
            int codCliente, Date dataNotifica) {
        /* variabili e costanti locali di lavoro */
        ArrayList<ClienteAlbergo.ErrDatiAnag> errori = new ArrayList<ClienteAlbergo.ErrDatiAnag>();
        Modulo modCliente;
        Modulo modIndirizzo;
        Query query;
        Filtro filtro;
        Dati dati;

        String cognome;
        String nome;
        int cittaNascita;
        Date dataNascita;
        String indirizzo = "";
        int cittaResidenza = 0;

        int codIndirizzo;

        try {    // prova ad eseguire il codice

            /* recupera i dati da Anagrafica */
            modCliente = ClienteAlbergoModulo.get();

            query = new QuerySelezione(modCliente);
            query.addCampo(Anagrafica.Cam.cognome.get());
            query.addCampo(Anagrafica.Cam.nome.get());
            query.addCampo(ClienteAlbergo.Cam.luogoNato.get());
            query.addCampo(ClienteAlbergo.Cam.dataNato.get());
            query.addCampo(ClienteAlbergo.Cam.tipoDoc.get());
            query.addCampo(ClienteAlbergo.Cam.dataDoc.get());
            query.addCampo(ClienteAlbergo.Cam.numDoc.get());
            query.addCampo(ClienteAlbergo.Cam.autoritaDoc.get());
            filtro = FiltroFactory.codice(modCliente, codCliente);
            query.setFiltro(filtro);

            dati = modCliente.query().querySelezione(query);
            cognome = dati.getStringAt(Anagrafica.Cam.cognome.get());
            nome = dati.getStringAt(Anagrafica.Cam.nome.get());
            cittaNascita = dati.getIntAt(ClienteAlbergo.Cam.luogoNato.get());
            dataNascita = dati.getDataAt(ClienteAlbergo.Cam.dataNato.get());
            dati.close();

            /* recupera i dati da Indirizzo, se esistente */
            codIndirizzo = ClienteAlbergoModulo.getCodIndirizzo(codCliente);
            if (codIndirizzo > 0) {
                modIndirizzo = IndirizzoAlbergoModulo.get();
                query = new QuerySelezione(modIndirizzo);
                query.addCampo(Indirizzo.Cam.citta);
                query.addCampo(Indirizzo.Cam.indirizzo);
                filtro = FiltroFactory.codice(modIndirizzo, codIndirizzo);
                query.setFiltro(filtro);
                dati = modIndirizzo.query().querySelezione(query);
                cittaResidenza = dati.getIntAt(Indirizzo.Cam.citta.get());
                indirizzo = dati.getStringAt(Indirizzo.Cam.indirizzo.get());
                dati.close();
            }// fine del blocco if-else

            /* controllo dati e costruzione errore */
            errori = ClienteAlbergoModulo.checkDocValido(codCliente, dataNotifica);

            if (!Lib.Testo.isValida(cognome)) {
                errori.add(ClienteAlbergo.ErrDatiAnag.cognomeMancante);
            }// fine del blocco if

            if (!Lib.Testo.isValida(nome)) {
                errori.add(ClienteAlbergo.ErrDatiAnag.nomeMancante);
            }// fine del blocco if

            if (cittaNascita <= 0) {
                errori.add(ClienteAlbergo.ErrDatiAnag.luogoNascitaMancante);
            }// fine del blocco if

            if (Lib.Data.isVuota(dataNascita)) {
                errori.add(ClienteAlbergo.ErrDatiAnag.dataNascitaMancante);
            }// fine del blocco if

            if (!Lib.Testo.isValida(indirizzo)) {
                errori.add(ClienteAlbergo.ErrDatiAnag.indirizzoResidenzaMancante);
            }// fine del blocco if

            if (cittaResidenza <= 0) {
                errori.add(ClienteAlbergo.ErrDatiAnag.localitaResidenzaMancante);
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return errori;
    }


    /**
     * Controlla se i dati di un cliente sono validi per la scheda di notifica
     * in qualità di Membro.
     * <p/>
     *
     * @param codCliente il codice del cliente da controllare
     * @param dataNotifica la data della notifica
     *
     * @return elenco degli errori riscontrati.
     */
    private static ArrayList<ClienteAlbergo.ErrDatiAnag> checkValidoNotificaMembro(
            int codCliente, Date dataNotifica) {
        /* variabili e costanti locali di lavoro */
        ArrayList<ClienteAlbergo.ErrDatiAnag> errori = new ArrayList<ClienteAlbergo.ErrDatiAnag>();

        Modulo modCliente;
        Query query;
        Filtro filtro;
        Dati dati;

        String cognome;
        String nome;
        int cittaNascita;
        Date dataNascita;

        try {    // prova ad eseguire il codice

            /* recupera i dati da Anagrafica */
            modCliente = ClienteAlbergoModulo.get();

            query = new QuerySelezione(modCliente);
            query.addCampo(Anagrafica.Cam.cognome.get());
            query.addCampo(Anagrafica.Cam.nome.get());
            query.addCampo(ClienteAlbergo.Cam.luogoNato.get());
            query.addCampo(ClienteAlbergo.Cam.dataNato.get());
            filtro = FiltroFactory.codice(modCliente, codCliente);
            query.setFiltro(filtro);

            dati = modCliente.query().querySelezione(query);
            cognome = dati.getStringAt(Anagrafica.Cam.cognome.get());
            nome = dati.getStringAt(Anagrafica.Cam.nome.get());
            cittaNascita = dati.getIntAt(ClienteAlbergo.Cam.luogoNato.get());
            dataNascita = dati.getDataAt(ClienteAlbergo.Cam.dataNato.get());
            dati.close();

            /* controllo dati e costruzione errore */
            errori = ClienteAlbergoModulo.checkDocValido(codCliente, dataNotifica);

            if (!Lib.Testo.isValida(cognome)) {
                errori.add(ClienteAlbergo.ErrDatiAnag.cognomeMancante);
            }// fine del blocco if

            if (!Lib.Testo.isValida(nome)) {
                errori.add(ClienteAlbergo.ErrDatiAnag.nomeMancante);
            }// fine del blocco if

            if (cittaNascita <= 0) {
                errori.add(ClienteAlbergo.ErrDatiAnag.luogoNascitaMancante);
            }// fine del blocco if

            if (Lib.Data.isVuota(dataNascita)) {
                errori.add(ClienteAlbergo.ErrDatiAnag.dataNascitaMancante);
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return errori;
    }


    /**
     * Controlla se i dati di un cliente sono validi ai fini della dichiarazione ISTAT
     * <p/>
     *
     * @param codCliente il codice del cliente da controllare
     * @param dataCheck la data alla quale effettuare il controllo (x validità doc)
     *
     * @return true se è valido
     */
    public static boolean isValidoISTAT(int codCliente, Date dataCheck) {
        /* variabili e costanti locali di lavoro */
        boolean valido = false;
        ArrayList<ClienteAlbergo.ErrDatiAnag> errori;

        try {    // prova ad eseguire il codice
            errori = checkValidoISTAT(codCliente, dataCheck);
            valido = (errori.size() == 0);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return valido;
    }


    /**
     * Controlla se i dati di un cliente sono validi ai fini della dichiarazione ISTAT
     * <p/>
     * - Deve avere la città se no non si può sapere qual'è la nazione
     * - La città deve essere collegata a una nazione
     *
     * @param codCliente il codice del cliente da controllare
     * @param dataCheck la data alla quale effettuare il controllo (x validità doc)
     *
     * @return elenco degli errori riscontrati.
     */
    public static ArrayList<ClienteAlbergo.ErrDatiAnag> checkValidoISTAT(
            int codCliente, Date dataCheck) {
        /* variabili e costanti locali di lavoro */
        ArrayList<ClienteAlbergo.ErrDatiAnag> errori = new ArrayList<ClienteAlbergo.ErrDatiAnag>();
        int codIndirizzo;
        Modulo modIndirizzo;
        Modulo modCitta;
        int codCitta;
        int codNazione = 0;

        try {    // prova ad eseguire il codice

            /* recupera i dati di città di residenza e nazione di residenza dall'indirizzo */
            codIndirizzo = ClienteAlbergoModulo.getCodIndirizzo(codCliente);
            modIndirizzo = IndirizzoModulo.get();
            codCitta = modIndirizzo.query().valoreInt(Indirizzo.Cam.citta.get(), codIndirizzo);
            if (codCitta > 0) {
                modCitta = CittaModulo.get();
                codNazione = modCitta.query().valoreInt(Citta.Cam.linkNazione.get(), codCitta);
            }// fine del blocco if

            /* controllo dati e costruzione errore */
            errori = ClienteAlbergoModulo.checkDocValido(codCliente, dataCheck);

            if (codCitta <= 0) {
                errori.add(ClienteAlbergo.ErrDatiAnag.localitaResidenzaMancante);
            }// fine del blocco if

            if (codNazione <= 0) {
                errori.add(ClienteAlbergo.ErrDatiAnag.nazioneResidenzaMancante);
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return errori;
    }


    /**
     * Controlla se i dati di un cliente sono validi per confermarne
     * l'arrivo a una certa data.
     * <p/>
     * Controlla dati per PS, Notifica, ISTAT
     *
     * @param codCliente il codice del cliente da controllare
     * @param dataCheck la data alla quale effettuare il controllo (x validità doc)
     * @param capoScheda true se viene registrato come Capogruppo Scheda (serve x Notifica)
     *
     * @return elenco degli errori riscontrati.
     *         <p/>
     */
    public static ArrayList<ClienteAlbergo.ErrDatiAnag> checkValidoArrivo(
            int codCliente, Date dataCheck, boolean capoScheda) {
        /* variabili e costanti locali di lavoro */
        ArrayList<ClienteAlbergo.ErrDatiAnag> errori;
        ArrayList<ClienteAlbergo.ErrDatiAnag> erroriOut = null;

        try {    // prova ad eseguire il codice
            errori = new ArrayList<ClienteAlbergo.ErrDatiAnag>();
            errori.addAll(checkValidoPS(codCliente, dataCheck));
            errori.addAll(checkValidoNotifica(codCliente, capoScheda, dataCheck));
            errori.addAll(checkValidoISTAT(codCliente, dataCheck));

            /* pulisce la lista dai doppioni */
            erroriOut = new ArrayList<ClienteAlbergo.ErrDatiAnag>();
            for (ClienteAlbergo.ErrDatiAnag errore : errori) {
                if (!erroriOut.contains(errore)) {
                    erroriOut.add(errore);
                }// fine del blocco if
            }


        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return erroriOut;
    }


    /**
     * Controlla se i dati di un cliente sono validi per confermarne
     * l'arrivo a una certa data
     * <p/>
     * Controlla dati per PS, Notifica, ISTAT
     *
     * @param codCliente il codice del cliente da controllare
     * @param dataCheck la data alla quale effettuare il controllo (x validità doc)
     * @param capoScheda true se viene registrato come Capogruppo Scheda (serve x Notifica)
     *
     * @return true se è valido
     */
    public static boolean isValidoArrivo(int codCliente, Date dataCheck, boolean capoScheda) {
        /* variabili e costanti locali di lavoro */
        boolean valido = false;
        ArrayList<ClienteAlbergo.ErrDatiAnag> errori;

        try {    // prova ad eseguire il codice
            errori = checkValidoArrivo(codCliente, dataCheck, capoScheda);
            valido = (errori.size() == 0);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return valido;
    }


    /**
     * Crea un nuovo membro di un gruppo.
     * <p/>
     * Regola i valori di default dal capogruppo.
     *
     * @param codCapo codice del capogruppo
     *
     * @return il codice del cliente creato
     */
    public static int nuovoMembro(int codCapo) {
        /* variabili e costanti locali di lavoro */
        int codMembro = 0;
        int codice;
        int codLingua = 0;
        boolean corrispondenza = false;
        boolean famiglia = false;
        boolean continua;
        ArrayList<CampoValore> valori;
        CampoValore cv;
        Modulo mod;
        Query query;
        Filtro filtro;
        Dati dati;

        try {    // prova ad eseguire il codice

            /* crea il record */
            mod = ClienteAlbergoModulo.get();
            codice = mod.query().nuovoRecord();
            continua = (codice > 0);

            /* recupera alcuni valori dal capogruppo */
            if (continua) {
                query = new QuerySelezione(mod);
                query.addCampo(ClienteAlbergo.Cam.lingua.get());
                query.addCampo(ClienteAlbergo.Cam.checkPosta.get());
                query.addCampo(ClienteAlbergo.Cam.checkFamiglia.get());
                filtro = FiltroFactory.codice(mod, codCapo);
                query.setFiltro(filtro);
                dati = mod.query().querySelezione(query);
                codLingua = dati.getIntAt(ClienteAlbergo.Cam.lingua.get());
                corrispondenza = dati.getBoolAt(ClienteAlbergo.Cam.checkPosta.get());
                famiglia = dati.getBoolAt(ClienteAlbergo.Cam.checkFamiglia.get());
                dati.close();
            }// fine del blocco if

            /* modifica il record */
            if (continua) {
                valori = new ArrayList<CampoValore>();
                cv = new CampoValore(ClienteAlbergo.Cam.capogruppo.get(), false);
                valori.add(cv);
                cv = new CampoValore(ClienteAlbergo.Cam.linkCapo.get(), codCapo);
                valori.add(cv);
                cv = new CampoValore(ClienteAlbergo.Cam.lingua.get(), codLingua);
                valori.add(cv);
                cv = new CampoValore(ClienteAlbergo.Cam.checkPosta.get(), corrispondenza);
                valori.add(cv);
                cv = new CampoValore(ClienteAlbergo.Cam.checkFamiglia.get(), famiglia);
                valori.add(cv);

                continua = mod.query().registraRecordValori(codice, valori);
            }// fine del blocco if

            /* regola il ritorno */
            if (continua) {
                codMembro = codice;
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codMembro;
    }


    /**
     * Ritorna un filtro per il moduo ClienteAlbergo che seleziona tutti
     * i membri del gruppo di un dato cliente.
     * <p/>
     *
     * @param codice del cliente
     *
     * @return il filtro che seleziona tutti i membri del gruppo
     * se il codice passato è zero, ritorna un filtro che non seleziona nessuno
     */
    public static Filtro getFiltroGruppo(int codice) {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = null;
        Filtro filtroLink;
        Filtro filtroCod;
        int codLinkGruppo;
        Modulo modCliente = ClienteAlbergoModulo.get();

        try {    // prova ad eseguire il codice

            filtro = FiltroFactory.nessuno(modCliente);

            if (codice > 0) {
                codLinkGruppo = modCliente.query()
                        .valoreInt(ClienteAlbergo.Cam.linkCapo.get(), codice);

                filtroLink = FiltroFactory.crea(ClienteAlbergo.Cam.linkCapo.get(), codLinkGruppo);
                filtroCod = FiltroFactory.crea(
                        ClienteAlbergoModulo.get().getCampoChiave(),
                        codLinkGruppo);

                filtro = new Filtro();
                filtro.add(filtroLink);
                filtro.add(Filtro.Op.OR, filtroCod);
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return filtro;
    }


    /**
     * Crea un filtro che seleziona i clienti con compleanno che rientra nelle condizioni indicate.
     * <p/>
     * Se viene specificato uno dei primi 4 parametri devono essere specificati anche tutti gli altri.
     * Per non porre condizioni sul periodo lasciare i 4 parametri a zero.
     *
     * @param gDal giorno di inizio (compreso)
     * @param mDal mese di inizio
     * @param gAl giorno di fine (compreso)
     * @param mAl mese di fine
     * @param annoMin anno minimo di permanenza del cliente (0 per tutti)
     * @param corr true per includere solo i clienti con flag Corrispondenza attivo
     *
     * @return l'elenco dei codici cliente in ordine di cognome e nome
     */
    public static Filtro getFiltroCompleanni(
            int gDal, int mDal, int gAl, int mAl, int annoMin, boolean corr) {
        /* variabili e costanti locali di lavoro */
        Filtro filtroOut = null;
        boolean continua = true;
        boolean rangeSpecificato;
        int indice1;
        int indice2;
        Filtro unFiltro;
        Filtro filtroRange = null;
        Filtro filtroRangeA;
        Filtro filtroRangeB;
        Filtro filtroCorr = null;
        Filtro filtroNoData = null;
        Filtro filtroTot = null;
        Modulo modClienti;
        Ordine ordine;
        int[] codici = new int[0];
        ArrayList<Integer> codiciFilt;
        Date ultSogg;
        int anno;

        try {    // prova ad eseguire il codice

            /* controlla se è stato specificato un range */
            rangeSpecificato = ((gDal != 0) | (mDal != 0) | (gAl != 0) | (mAl != 0));

            /* se esiste, controllo congruità range - ci devono essere tutti i 4 parametri */
            if (rangeSpecificato) {
                if ((gDal == 0) | (mDal == 0) | (gAl == 0) | (mAl == 0)) {
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            /* costruzione eventuale filtro sul range */
            if (continua) {
                if (rangeSpecificato) {
                    indice1 = Lib.Data.getIndiceGiornoDellAnno(gDal, mDal);
                    indice2 = Lib.Data.getIndiceGiornoDellAnno(gAl, mAl);

                    filtroRange = new Filtro();

                    if (indice1 <= indice2) {
                        unFiltro = FiltroFactory.crea(
                                ClienteAlbergo.Cam.indiceGiornoNato.get(),
                                Filtro.Op.MAGGIORE_UGUALE,
                                indice1);
                        filtroRange.add(unFiltro);
                        unFiltro = FiltroFactory.crea(
                                ClienteAlbergo.Cam.indiceGiornoNato.get(),
                                Filtro.Op.MINORE_UGUALE,
                                indice2);
                        filtroRange.add(unFiltro);
                    } else {

                        filtroRangeA = new Filtro();
                        unFiltro = FiltroFactory.crea(
                                ClienteAlbergo.Cam.indiceGiornoNato.get(),
                                Filtro.Op.MAGGIORE_UGUALE,
                                indice1);
                        filtroRangeA.add(unFiltro);
                        unFiltro = FiltroFactory.crea(
                                ClienteAlbergo.Cam.indiceGiornoNato.get(),
                                Filtro.Op.MINORE_UGUALE,
                                372);
                        filtroRangeA.add(unFiltro);

                        filtroRangeB = new Filtro();
                        unFiltro = FiltroFactory.crea(
                                ClienteAlbergo.Cam.indiceGiornoNato.get(),
                                Filtro.Op.MAGGIORE_UGUALE,
                                1);
                        filtroRangeB.add(unFiltro);
                        unFiltro = FiltroFactory.crea(
                                ClienteAlbergo.Cam.indiceGiornoNato.get(),
                                Filtro.Op.MINORE_UGUALE,
                                indice2);
                        filtroRangeB.add(unFiltro);

                        filtroRange.add(filtroRangeA);
                        filtroRange.add(Filtro.Op.OR, filtroRangeB);

                    }// fine del blocco if-else

//                    filtroRange = new Filtro();
//                    unFiltro = FiltroFactory.crea(ClienteAlbergo.Cam.giornoNato.get(), Filtro.Op.MAGGIORE_UGUALE, gDal);
//                    filtroRange.add(unFiltro);
//                    unFiltro = FiltroFactory.crea(ClienteAlbergo.Cam.meseNato.get(), Filtro.Op.MAGGIORE_UGUALE, mDal);
//                    filtroRange.add(unFiltro);
//                    unFiltro = FiltroFactory.crea(ClienteAlbergo.Cam.giornoNato.get(), Filtro.Op.MINORE_UGUALE, gAl);
//                    filtroRange.add(unFiltro);
//                    unFiltro = FiltroFactory.crea(ClienteAlbergo.Cam.meseNato.get(), Filtro.Op.MINORE_UGUALE, mAl);
//                    filtroRange.add(unFiltro);
                }// fine del blocco if
            }// fine del blocco if

            /* costruzione eventuale filtro sul flag corrispondenza */
            if (continua) {
                if (corr) {
                    filtroCorr = FiltroFactory.creaVero(ClienteAlbergo.Cam.checkPosta);
                }// fine del blocco if
            }// fine del blocco if

            /* costruzione filtro che esclude quelli senza indice giorno di nascita */
            if (continua) {
                filtroNoData = FiltroFactory.crea(
                        ClienteAlbergo.Cam.indiceGiornoNato.get(), Filtro.Op.DIVERSO, 0);
            }// fine del blocco if

            /* costruzione filtro completo */
            if (continua) {

                filtroTot = new Filtro();

                if (filtroRange != null) {
                    filtroTot.add(filtroRange);
                }// fine del blocco if

                if (filtroCorr != null) {
                    filtroTot.add(filtroCorr);
                }// fine del blocco if

                filtroTot.add(filtroNoData);

            }// fine del blocco if

            /* recupera i codici cliente in ordine alfabetico di cognome e nome */
            if (continua) {
                modClienti = ClienteAlbergoModulo.get();
                ordine = new Ordine();
                ordine.add(Anagrafica.Cam.cognome.get());
                ordine.add(Anagrafica.Cam.nome.get());
                codici = modClienti.query().valoriChiave(filtroTot, ordine);
            }// fine del blocco if

            /**
             * se specificato ultimo soggiorno minimo, spazzola i
             * codici e tiene solo quelli che soddistano la condizione specificata
             */
            if (continua) {
                if (annoMin != 0) {
                    codiciFilt = new ArrayList<Integer>();
                    for (int cod : codici) {
                        ultSogg = PresenzaModulo.getDataUltimoSoggiorno(cod);
                        anno = Lib.Data.getAnno(ultSogg);
                        if (anno >= annoMin) {
                            codiciFilt.add(cod);
                        } else {
                            int a = 87;
                        }// fine del blocco if-else
                    }
                    codici = new int[codiciFilt.size()];
                    for (int k = 0; k < codiciFilt.size(); k++) {
                        codici[k] = codiciFilt.get(k);
                    } // fine del ciclo for
                }// fine del blocco if
            }// fine del blocco if

            /* costruzione del filtro finale in uscita in base ai codici selezionati */
            if (continua) {
                modClienti = ClienteAlbergoModulo.get();
                if (codici.length > 0) {
                    filtroOut = FiltroFactory.elenco(modClienti, codici);
                } else {
                    filtroOut = FiltroFactory.nessuno(modClienti);
                }// fine del blocco if-else
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return filtroOut;
    }


    /**
     * Invocato quando cambia l'azienda attiva.
     * <p/>
     */
    private void cambioAzienda() {
        try { // prova ad eseguire il codice
            this.regolaFinestra();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }

    /**
     * Invocato al cambio di data del programma.
     * <p/>
     */
    private void cambioData () {
        try {    // prova ad eseguire il codice
            this.regolaFinestra();
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Regola la descrizione dell'azienda visibile nella status bar della finestra.
     * <p/>
     */
    private void regolaFinestra() {
        Modulo modulo;
        AlbergoModulo modAlbergo = null;
        String desc;
        Navigatore nav;
        JLabel label;
        boolean continua;

        try { // prova ad eseguire il codice

            /* recupera il modulo Albergo se esiste */
            continua = false;
            modulo = Progetto.getModulo(Albergo.NOME_MODULO);
            if ((modulo != null) && (modulo instanceof AlbergoModulo)) {
                modAlbergo = (AlbergoModulo)modulo;
                continua = true;
            }// fine del blocco if

            /* regola la status bar della finestra */
            if (continua) {
                desc = modAlbergo.getDescAziendaAttiva();
                nav = this.getNavigatoreCorrente();
                if (nav != null) {
                    StatusBar sb = nav.getStatusBar();
                    if (sb != null) {
                        label = new JLabel(desc);
                        label.setHorizontalAlignment(JLabel.CENTER);
                        label.setForeground(CostanteColore.BLU);
                        sb.setCenterComponent(label);
                        sb.validate();
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Apertura di un nuovo membro del gruppo.
     * <p/>
     * Bottone posto alla seconda riga della toolbar <br>
     */
    public final class AzioneImport extends AzSpecifica {

        public static final String CHIAVE = "NuovoMembroAz";


        /**
         * Costruttore completo con parametri.
         */
        public AzioneImport() {
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
            super.setChiave(CHIAVE);
            super.setNome("ImportIniziale");
            super.setTooltip("Importazione iniziale dei dati");
            super.setHelp("Importazione iniziale dei dati");
            super.setIconaPiccola("restore16");
            super.setIconaMedia("restore24");
            super.setIconaGrande("");
            super.setCarattereAcceleratore(' ');
            super.setCarattereMnemonico(0);
            super.setCarattereComando(null);
            super.setAttiva(true);
            super.setAbilitataPartenza(true);
            super.setColonnaMenu(MenuBase.MenuTipo.ARCHIVIO);
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
                importDatiIniziali();
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }

    }// fine della classe

    /**
     * Azione Gestione Compleanni.
     * <p/>
     */
    public final class AzCompleanni extends AzModulo {

        public static final String CHIAVE = "Compleanni";


        /**
         * Costruttore completo con parametri.
         */
        public AzCompleanni() {
            /* rimanda al costruttore della superclasse */
            super(ClienteAlbergoModulo.get());

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
            super.setChiave(CHIAVE);
            super.setNome("Compleanni");
            super.setTooltip("Ricerca compleanni");
            super.setIconaMedia("torta24");
            super.setIconaGrande("");
            super.setAttiva(true);
            super.setAbilitataPartenza(true);
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
                compleanni();
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }

    }// fine della classe





    /**
     * Azione per cambiare azienda
     */
    private class AzioneCambioAzienda extends CambioAziendaAz {

        /**
         * cambioAziendaAz, da CambioAziendaLis.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo sovrascritto, nell'oggetto specifico
         * della classe che genera questo evento <br>
         * Sovrascritto nelle sottoclassi <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void cambioAziendaAz(CambioAziendaEve unEvento) {
            cambioAzienda();
        }
    } // fine della classe interna



    /**
     * Azione al cambio della data programma
     */
    private class AzioneCambioData extends CambioDataAz {

        /**
         * Esegue l'azione
         * <p>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void cambioDataAz(CambioDataEve unEvento) {
            cambioData();
        }
    } // fine della classe interna


    /**
     * Restituisce in runtime l'istanza presente in progetto.
     * <p/>
     *
     * @return modulo presente in Progetto
     */
    public static ClienteAlbergoModulo get() {
        return (ClienteAlbergoModulo)ModuloBase.get(NOME_CHIAVE);
    }


    /**
     * Main method.
     *
     * @param argomenti eventuali parametri in ingresso (quasi mai)
     */
    public static void main(String[] argomenti) {
        /* crea una istanza di se stessa */
        Modulo primoModuloProvvisorio = new ClienteAlbergoModulo();

        /**
         * inizializza tutti i moduli usati da questo modulo <br>
         * lancia il modulo iniziale del programma (obbligatorio) <br>
         */
        Progetto.lancia(argomenti, primoModuloProvvisorio);
    } // fine del metodo main

}// fine della classe
