package it.algos.albergo.presenza;

import it.algos.albergo.Albergo;
import it.algos.albergo.AlbergoLib;
import it.algos.albergo.AlbergoModulo;
import it.algos.albergo.arrivipartenze.ArriviPartenzeModulo;
import it.algos.albergo.camera.Camera;
import it.algos.albergo.camera.CameraModulo;
import it.algos.albergo.clientealbergo.ClienteAlbergoModulo;
import it.algos.albergo.conto.Conto;
import it.algos.albergo.conto.ContoModulo;
import it.algos.albergo.evento.CambioAziendaAz;
import it.algos.albergo.evento.CambioAziendaEve;
import it.algos.albergo.evento.CambioDataAz;
import it.algos.albergo.evento.CambioDataEve;
import it.algos.albergo.evento.DelAziendeAz;
import it.algos.albergo.evento.DelAziendeEve;
import it.algos.albergo.prenotazione.PrenotazioneModulo;
import it.algos.albergo.promemoria.PromemoriaModulo;
import it.algos.albergo.stampeobbligatorie.testastampe.TestaStampeModulo;
import it.algos.albergo.storico.NavStorico;
import it.algos.albergo.tabelle.azienda.AziendaModulo;
import it.algos.base.albero.AlberoNodo;
import it.algos.base.campo.base.Campo;
import it.algos.base.costante.CostanteColore;
import it.algos.base.database.util.Operatore;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.modulo.Modulo;
import it.algos.base.modulo.ModuloBase;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.navigatore.NavigatoreL;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.statusbar.StatusBar;

import javax.swing.JLabel;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;

/**
 * Modulo Presenze
 * </p>
 *
 * @author Guido Andrea Ceresa ed Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 22-gen-2008 ore  16:22
 */
public final class PresenzaModulo extends ModuloBase implements Presenza {

    /**
     * Nome-chiave interno di questo modulo (obbligatorio)
     */
    private static final String NOME_CHIAVE = Presenza.NOME_MODULO;

    /**
     * Titolo della finestra del navigatore
     * (usato nel Navigatore)
     */
    private static final String TITOLO_FINESTRA = Presenza.TITOLO_FINESTRA;

    /**
     * Titolo del modulo come appare nel Menu Moduli degli altri moduli
     * (usato nel Navigatore)
     */
    private static final String TITOLO_MENU = Presenza.TITOLO_MENU;

    /**
     * nome del programma che compare in basso (se vuoto, usa il default)
     * viene usato il nome del modulo che parte per primo
     */
    private static final String NOME_PROGRAMMA = TITOLO_FINESTRA;

    /**
     * filtro per isolare l'azienda corrente
     */
    private Filtro filtroAzienda;


    /**
     * Costruttore provvisorio, usato solo per portarsi il percorso
     * della classe (implicito) ed il nome chiave (esplicito).
     */
    public PresenzaModulo() {
        /* regola la variabile di istanza con la costante */
        super.setNomeModulo(NOME_CHIAVE);

        /* regola il nome del programma (se questo modulo parte per primo) */
        super.setNomeProgramma(NOME_PROGRAMMA);
    } /* fine del metodo costruttore provvisorio */


    /**
     * Costruttore completo.
     *
     * @param unNodo nodo dell'albero moduli
     */
    public PresenzaModulo(AlberoNodo unNodo) {
        /* rimanda al costruttore della superclasse */
        super(NOME_CHIAVE, unNodo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore


    /**
     * Regolazioni immediate di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* selezione del modello (obbligatorio) */
        super.setModello(new PresenzaModello());

        /* regola il titolo della finestra del navigatore */
        super.setTitoloFinestra(TITOLO_FINESTRA);

        /* regola il titolo di questo modulo nei menu di altri moduli */
        super.setTitoloMenu(TITOLO_MENU);

        /* assegna una icona al modulo */
        this.setIcona("presenza24");

        /* assegna la scheda popup */
        this.setSchedaPop(new PresenzaScheda(this));

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
                mod.addListener(AlbergoModulo.Evento.eliminaAziende, new AzioneDelAziende());
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
            this.getLista().ordina(this.getCampo(Cam.entrata));
            this.getLista().setUltimaRigaVisibile();
            this.setAvviato(false);

            /* selezione inziale di un filtro, tramite il popup */
            this.setPopup(Pop.presenti, 1);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } /* fine del metodo */


    /**
     * Crea tutti i Moduli indispensabili per il funzionamento di questo modulo.
     * </p>
     * Metodo invocato dal ciclo inizia del costruttore <br>
     * Metodo sovrascritto nelle classi specifiche <br>
     * <p/>
     * Aggiunge alla collezione moduli (del Progetto), i moduli necessari <br>
     */
    @Override
    protected void creaModuli() {
        try { // prova ad eseguire il codice
            super.creaModulo(new CameraModulo());
            super.creaModulo(new ClienteAlbergoModulo());
            super.creaModulo(new ContoModulo());
            super.creaModulo(new PrenotazioneModulo());
            super.creaModulo(new ArriviPartenzeModulo());
            super.creaModulo(new TestaStampeModulo());
            super.creaModulo(new PromemoriaModulo());

//            // todo provvisorio da rimuovere dopo integrazione dati
//            super.creaModulo(new StoricoModulo());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


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

            // moduli
            AlbergoLib.addModuliVisibili(this);

            // tabelle
            super.addModuloVisibile(Camera.NOME_MODULO);

//            // todo provvisorio da rimuovere dopo integrazione dati
//            super.addModuloVisibile(Storico.NOME_MODULO);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo addModuliVisibili


    /**
     * Regola il filtro del modulo in funzione dell'azienda attiva.
     * <p/>
     */
    public void regolaFiltro() {
        Filtro filtro = null;
        AlbergoModulo modAlbergo;
        Modulo modulo;
        Campo campoAzienda;
        int codAz;

        try { // prova ad eseguire il codice
            modAlbergo = (AlbergoModulo)Progetto.getModulo(Albergo.NOME_MODULO);
            if (modAlbergo != null) {
                codAz = AlbergoModulo.getCodAzienda();
                if (codAz > 0) {
                    modulo = Progetto.getModulo(Presenza.NOME_MODULO);
                    campoAzienda = modulo.getCampo(Presenza.Cam.azienda.get());
                    filtro = FiltroFactory.crea(campoAzienda, codAz);
                }// fine del blocco if
            }// fine del blocco if

            this.setFiltroAzienda(filtro);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
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
        boolean continua = true;

        try { // prova ad eseguire il codice

            /* recupera il modulo Albergo se esiste */
            if (continua) {
                continua = false;
                modulo = Progetto.getModulo(Albergo.NOME_MODULO);
                if ((modulo != null) && (modulo instanceof AlbergoModulo)) {
                    modAlbergo = (AlbergoModulo)modulo;
                    continua = true;
                }// fine del blocco if
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
     * Creazione e regolazione dei Navigatori.
     * <p/>
     * Anche eventuale creazione <br>
     * Metodo invocato dal ciclo inizia del costruttore <br>
     * Regola il Navigatore di default <br>
     * Crea altri Navigatori (oltre a quello standard) <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    @Override
    protected void creaNavigatori() {
        /* variabili e costanti locali di lavoro */
        Navigatore nav;

        try { // prova ad eseguire il codice

            /* navigatore principale */
            nav = new PresenzaNavigatore(this);
            this.addNavigatoreCorrente(nav);

            /* navigatore delle Presenze dentro al Conto */
            nav = new NavigatoreL(this);
            nav.setNomeChiave(Presenza.Nav.conto.get());
            nav.setNomeVista(Presenza.Vis.conto.get());
            nav.setRigheLista(6);
            nav.setUsaToolBarLista(false);
            nav.setUsaStatusBarLista(false);
            this.addNavigatore(nav);

            /* navigatore lista con Cliente e Parentela */
            nav = new NavigatoreL(this);
            nav.setNomeChiave(Presenza.Nav.clienteparentela.get());
            nav.setNomeVista(Presenza.Vis.clienteParentela.get());
            nav.setRigheLista(6);
            nav.setUsaToolBarLista(false);
            nav.setUsaStatusBarLista(false);
            nav.setUsaFiltriPopLista(false);
//            nav.getLista().getTavola().setEnabled(false);
            this.addNavigatore(nav);

            /* navigatore lista con Camera, Cliente e Parentela */
            nav = new NavigatoreL(this);
            nav.setNomeChiave(Presenza.Nav.arrivoCameraClienteParentela.get());
            nav.setNomeVista(Presenza.Vis.partenzaCambioManuale.get());
            nav.setRigheLista(6);
            nav.setUsaToolBarLista(false);
            nav.setUsaStatusBarLista(false);
            this.addNavigatore(nav);

//            /* navigatore usato dal dialogo di gestione del registro di PS */
//            nav = new NavigatoreL(this);
//            nav.setNomeChiave(Presenza.Nav.gestioneps.get());
//            nav.setNomeVista(Presenza.Vis.gestioneps.get());
//            nav.setRigheLista(14);
//            nav.setUsaToolBarLista(false);
//            nav.setUsaFiltriPopLista(false);
//            nav.getLista().getTavola().setEnabled(false);
//            this.addNavigatore(nav);

            /* navigatore usato dal dialogo di annullamento arrivo */
            nav = new NavigatoreL(this);
            nav.setNomeChiave(Presenza.Nav.annullaArrivo.get());
            nav.setNomeVista(Presenza.Vis.annullaArrivo.get());
            nav.setRigheLista(14);
            nav.setUsaToolBarLista(false);
            nav.setUsaStatusBarLista(true);
            nav.setUsaFiltriPopLista(false);
            nav.getLista().getTavola().setEnabled(false);
            nav.getLista().setUsaTotali(false);
            this.addNavigatore(nav);

            /* navigatore usato dal dialogo di annullamento partenze */
            nav = new NavigatoreL(this);
            nav.setNomeChiave(Presenza.Nav.annullaPartenza.get());
            nav.setNomeVista(Presenza.Vis.annullaPartenza.get());
            nav.setRigheLista(14);
            nav.setUsaToolBarLista(false);
            nav.setUsaStatusBarLista(true);
            nav.setUsaFiltriPopLista(false);
            nav.getLista().getTavola().setEnabled(false);
            nav.getLista().setUsaTotali(false);
            this.addNavigatore(nav);

            /* navigatore usato dal dialogo di conferma cambio */
            nav = new NavigatoreL(this);
            nav.setNomeChiave(Presenza.Nav.confermaCambio.get());
            nav.setNomeVista(Presenza.Vis.confermaCambio.get());
            nav.setRigheLista(6);
            nav.setUsaToolBarLista(false);
            nav.setUsaStatusBarLista(true);
            nav.setUsaFiltriPopLista(false);
            this.addNavigatore(nav);


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }

    /**
     * Creazione del navigatore per lo storico.
     * <p/>
     */
    public NavStorico getNavStorico() {
        /* variabili e costanti locali di lavoro */
        NavStorico nav = null;

        try { // prova ad eseguire il codice

            /* navigatore usato dal pannello Storico */
            nav = new NavStorico(this, Presenza.Nav.storico.get(), Presenza.Vis.storico.get());
            nav.inizializza();
            nav.avvia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return nav;
    }



    /**
     * Aggiorna i navigatori del modulo
     * <p/>
     */
    private void aggiornaNavigatori() {
        LinkedHashMap<String, Navigatore> navigatori;

        try { // prova ad eseguire il codice
            navigatori = this.getNavigatori();
            for (Navigatore nav : navigatori.values()) {
//                nav.setFiltroBase(this.getFiltroAzienda());
                nav.aggiornaLista();
            }
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Invocato quando cambia l'azienda attiva.
     * <p/>
     */
    private void cambioAzienda() {
        try { // prova ad eseguire il codice
            PresenzaModello modello = (PresenzaModello)this.getModello();
            modello.setFiltroModello(this.getFiltroAzienda());
            this.regolaFinestra();
            this.aggiornaNavigatori();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Elimina tutte le aziende tranne quella principale.
     * <p/>
     * Invocato quando viene premuto l'apposito tasto.
     */
    private void delAziende() {
        /* variabili e costanti locali di lavoro */
        boolean continua = true;
        Modulo moduloAzienda;
        Filtro filtro;
        Campo campo;
        int codAzPrincipale = 0;

        try { // prova ad eseguire il codice

            /* recupera il codice della azienda principale e controlla che esista */
            if (continua) {
                moduloAzienda = AziendaModulo.get();
                campo = moduloAzienda.getCampoPreferito();
                filtro = FiltroFactory.crea(campo, Filtro.Op.UGUALE, true);
                codAzPrincipale = moduloAzienda.query().valoreChiave(filtro);
                if (codAzPrincipale == 0) {
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            /* elimina tutti i record che non appartengono alla azienda principale */
            if (continua) {
                campo = this.getCampo(Presenza.Cam.azienda.get());
                filtro = FiltroFactory.crea(campo, Filtro.Op.DIVERSO, codAzPrincipale);
                this.query().eliminaRecords(filtro);
                this.aggiornaNavigatori();
            }// fine del blocco if


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
     * Controlla se un cliente è già presente.
     * </p>
     *
     * @param cod del cilente
     *
     * @return true se è presente
     */
    public static boolean isPresente(int cod) {
        /* variabili e costanti locali di lavoro */
        boolean presente = false;
        Filtro filtro;
        Filtro filtroCliente;
        Filtro filtroChiusa;

        try { // prova ad eseguire il codice
            filtro = new Filtro();
            filtroCliente = FiltroFactory.crea(Cam.cliente.get(), cod);
            filtroChiusa = FiltroFactory.creaFalso(Cam.chiuso.get());
            filtro.add(filtroCliente);
            filtro.add(filtroChiusa);

            presente = PresenzaModulo.get().query().isEsisteRecord(filtro);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return presente;
    }// fine del metodo


    /**
     * Ritorna i clienti presenti.
     * </p>
     *
     * @return codici dei clienti presenti
     */
    public static int[] getClientiPresenti() {
        /* variabili e costanti locali di lavoro */
        int[] clienti = null;
        Filtro filtro;

        try { // prova ad eseguire il codice

            filtro = PresenzaModulo.getFiltroAperte();
            clienti = PresenzaModulo.getClienti(filtro);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return clienti;
    }


    /**
     * Ritorna i clienti attualmente presenti in una data camera.
     * </p>
     *
     * @param codCamera della camera
     *
     * @return codici dei clienti presenti nella camera
     */
    public static int[] getClientiPresenti(int codCamera) {
        /* variabili e costanti locali di lavoro */
        int[] clienti = null;
        Filtro filtro;

        try { // prova ad eseguire il codice

            filtro = new Filtro();
            filtro.add(PresenzaModulo.getFiltroAperte());
            filtro.add(PresenzaModulo.getFiltroCamera(codCamera));
            clienti = PresenzaModulo.getClienti(filtro);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return clienti;
    }


//    /**
//     * Ritorna la data dell'ultima registrazione di P.S.
//     * per una data azienda.
//     * <p/>
//     *
//     * @param codAzienda codice dell'azienda
//     *
//     * @return la data dell'ultima registrazione di P.S.
//     */
//    public static Date getDataUltimaRegistrazionePS(int codAzienda) {
//        /* variabili e costanti locali di lavoro */
//        Date data = null;
//        Modulo modPresenza;
//        Filtro filtroAzienda;
//        Filtro filtroPS;
//        Filtro filtroNoCambi;
//        Filtro filtro;
//        Ordine ordine;
//        Query query;
//        Dati dati;
//
//        try {    // prova ad eseguire il codice
//
//            modPresenza = PresenzaModulo.get();
//            filtroAzienda = FiltroFactory.crea(Presenza.Cam.azienda.get(), codAzienda);
//            filtroNoCambi = PresenzaModulo.getFiltroNoCambiEntrata();
//            filtroPS = FiltroFactory.crea(Presenza.Cam.ps.get(), Filtro.Op.DIVERSO, 0);
//            filtro = new Filtro();
//            filtro.add(filtroAzienda);
//            filtro.add(filtroNoCambi);
//            filtro.add(filtroPS);
//            ordine = new Ordine();
//            ordine.add(Presenza.Cam.ps.get());
//            query = new QuerySelezione(modPresenza);
//            query.addCampo(Presenza.Cam.arrivo.get());
//            query.setFiltro(filtro);
//            query.setOrdine(ordine);
//
//            dati = modPresenza.query().querySelezione(query);
//            if (dati.getRowCount() > 0) {
//                data = dati.getDataAt(dati.getRowCount() - 1, Presenza.Cam.arrivo.get());
//            }// fine del blocco if
//
//            dati.close();
//
//        } catch (Exception unErrore) {    // intercetta l'errore
//            new Errore(unErrore);
//        } // fine del blocco try-catch
//
//        /* valore di ritorno */
//        return data;
//    }


    /**
     * Ritorna un filtro che isola tutte le presenze aperte.
     * <p/>
     *
     * @param provvisorie true per includere anche quelle "provvisorie"
     *
     * @return il filtro creato
     */
    private static Filtro getFiltroAperte(boolean provvisorie) {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = null;
        Filtro filtroAperte;
        Filtro filtroNoProvvisorie;

        try { // prova ad eseguire il codice
            filtro = new Filtro();
            filtroAperte = FiltroFactory.creaFalso(Cam.chiuso);
            filtroNoProvvisorie = FiltroFactory.creaFalso(Presenza.Cam.provvisoria);
            filtro.add(filtroAperte);
            if (!provvisorie) {
                filtro.add(filtroNoProvvisorie);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return filtro;
    }


    /**
     * Ritorna un filtro che isola tutte le presenze aperte.
     * <p/>
     * Comprende anche le presenze col flag inPartenzaOggi <br>
     *
     * @return il filtro creato
     */
    public static Filtro getFiltroAperteTotali() {
        return getFiltroAperte(true);
    }


    /**
     * Ritorna un filtro che isola tutte le presenze aperte.
     * <p/>
     * Esclude sempre le presenze col flag inPartenzaOggi <br>
     *
     * @return il filtro creato
     */
    public static Filtro getFiltroAperte() {
        return getFiltroAperte(false);
    }


    /**
     * Ritorna un filtro che isola tutte le presenze in una data camera.
     * <p/>
     *
     * @param codCamera della camera
     *
     * @return il filtro creato
     */
    public static Filtro getFiltroCamera(int codCamera) {
        return FiltroFactory.crea(Cam.camera.get(), codCamera);
    }


    /**
     * Ritorna un filtro che isola tutte le presenze relative a un dato periodo
     * <p/>
     *
     * @param codPeriodo periodo di riferimento
     *
     * @return il filtro creato
     */
    public static Filtro getFiltroPresenzePeriodo(int codPeriodo) {
        return FiltroFactory.crea(PresenzaModulo.get().getCampo(Cam.periodo), codPeriodo);
    }


    /**
     * Ritorna un filtro che isola tutte le presenze che risultano
     * presenti in un dato in un giorno
     * <p/>
     *
     * @param data di riferimento
     *
     * @return il filtro creato
     */
    public static Filtro getFiltroPresenze(Date data) {
        /* variabili e costanti locali di lavoro */
        Filtro filtroTot = null;
        Filtro filtroIn;
        Filtro filtroOut1;
        Filtro filtroOut2;
        Filtro filtroOut;

        try { // prova ad eseguire il codice

            filtroIn = FiltroFactory.crea(Cam.entrata.get(), Filtro.Op.MINORE_UGUALE, data);

            filtroOut1 = FiltroFactory.crea(Cam.uscita.get(), Filtro.Op.MAGGIORE, data);
            filtroOut2 = FiltroFactory.crea(Cam.uscita.get(), Lib.Data.getVuota());
            filtroOut = new Filtro();
            filtroOut.add(filtroOut1);
            filtroOut.add(Filtro.Op.OR, filtroOut2);

            filtroTot = new Filtro();
//            filtroTot.add(PresenzaModulo.getFiltroAzienda(AlbergoModulo.getCodAzienda()));
            filtroTot.add(filtroIn);
            filtroTot.add(filtroOut);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return filtroTot;
    }


    /**
     * Ritorna un filtro che isola tutte le presenze che risultano
     * presenti in un dato periodo
     * <p/>
     *
     * @param d1 inizio periodo (compreso) - null per partire dall'inizio
     * @param d2 fine periodo (compreso) - null per arrivare alla fine
     *
     * @return il filtro creato
     */
    public static Filtro getFiltroPresenze(Date d1, Date d2) {
        /* variabili e costanti locali di lavoro */
        Filtro filtroTot = null;
        Filtro filtroIn=null;
        Filtro filtroOut1;
        Filtro filtroOut2;
        Filtro filtroOut=null;

        try { // prova ad eseguire il codice

            /* entrati prima di d2 */
            if (Lib.Data.isValida(d2)) {
                filtroIn = FiltroFactory.crea(Cam.entrata.get(), Filtro.Op.MINORE_UGUALE, d2);
            }// fine del blocco if


            /* usciti dopo d1 o non ancora usciti */
            if (Lib.Data.isValida(d1)) {
                filtroOut1 = FiltroFactory.crea(Cam.uscita.get(), Filtro.Op.MAGGIORE, d1);
                filtroOut2 = FiltroFactory.crea(Cam.uscita.get(), Lib.Data.getVuota());
                filtroOut = new Filtro();
                filtroOut.add(filtroOut1);
                filtroOut.add(Filtro.Op.OR, filtroOut2);
            }// fine del blocco if


            /* costruzione filtro finale */
            filtroTot = new Filtro();

            if (filtroIn!=null) {
                filtroTot.add(filtroIn);
            }// fine del blocco if

            if (filtroOut!=null) {
                filtroTot.add(filtroOut);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return filtroTot;
    }



//    /**
//     * Ritorna un filtro che isola le presenze arrivate
//     * in un dato giorno per l'azienda attiva.
//     * </p>
//     *
//     * @param giorno di arrivo
//     *
//     * @return il filtro sulle presenze
//     */
//    public static Filtro getFiltroPresenzeArrivate(Date giorno) {
//        /* variabili e costanti locali di lavoro */
//        Filtro filtro = null;
//        int codAzienda;
//
//        try { // prova ad eseguire il codice
//            codAzienda = AlbergoModulo.getCodAzienda();
//            filtro = PresenzaModulo.getFiltroPresenzeArrivate(giorno, codAzienda);
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//
//        /* valore di ritorno */
//        return filtro;
//    }


    /**
     * Ritorna un filtro che isola le presenze arrivate
     * in un dato giorno per una data azienda.
     * </p>
     *
     * @param giorno     di arrivo
     *
     * @return il filtro sulle presenze
     */
    public static Filtro getFiltroPresenzeArrivate(Date giorno) {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = new Filtro();
        boolean continua;
        Modulo mod;
        Filtro filtroGiorno;
//        Filtro filtroAzienda;
        Filtro filtroNoCambi;

        try { // prova ad eseguire il codice

            mod = PresenzaModulo.get();
            continua = (mod != null);

            if (continua) {
                filtroGiorno = FiltroFactory.crea(Presenza.Cam.arrivo.get(), giorno);
                filtroNoCambi = PresenzaModulo.getFiltroNoCambiEntrata();
                filtro.add(filtroGiorno);
                filtro.add(filtroNoCambi);
//                if (codAzienda != 0) {
//                    filtroAzienda = FiltroFactory.crea(Presenza.Cam.azienda.get(), codAzienda);
//                    filtro.add(filtroAzienda);
//                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return filtro;
    }


//    /**
//     * Ritorna un filtro che isola le presenze arrivate
//     * in un dato periodo per l'azienda attiva.
//     * </p>
//     *
//     * @param dataIni data di inizio del periodo (null per cominciare dall'inizio)
//     * @param dataEnd data di fine del periodo (null per arrivare alla fine)
//     *
//     * @return il filtro sulle presenze
//     */
//    public static Filtro getFiltroPresenzeArrivate(Date dataIni, Date dataEnd) {
//        return getFiltroPresenzeArrivate(dataIni, dataEnd, AlbergoModulo.getCodAzienda());
//    }


    /**
     * Ritorna un filtro che isola le presenze arrivate
     * in un dato periodo.
     * </p>
     *
     * @param dataIni    data di inizio del periodo (null per cominciare dall'inizio)
     * @param dataEnd    data di fine del periodo (null per arrivare alla fine)
     *
     * @return il filtro sulle presenze
     */
    public static Filtro getFiltroPresenzeArrivate(Date dataIni, Date dataEnd) {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = null;

        try { // prova ad eseguire il codice

            /* costruisce il filtro le entrate del periodo */
            Filtro filtroEntrate = getFiltroPresenzeEntrate(dataIni, dataEnd);

            /* costruisce il filtro per escludere i cambi in entrata */
            Filtro filtroNoCambi = PresenzaModulo.getFiltroNoCambiEntrata();

            /* filtro finale */
            filtro = new Filtro();
            filtro.add(filtroEntrate);
            filtro.add(filtroNoCambi);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return filtro;
    }


//    /**
//     * Ritorna un filtro che isola le presenze entrate
//     * in un dato periodo per l'azienda attiva.
//     * </p>
//     *
//     * @param dataIni data di inizio del periodo (null per cominciare dall'inizio)
//     * @param dataEnd data di fine del periodo (null per arrivare alla fine)
//     *
//     * @return il filtro sulle presenze
//     */
//    public static Filtro getFiltroPresenzeEntrate(Date dataIni, Date dataEnd) {
//        return getFiltroPresenzeEntrate(dataIni, dataEnd, AlbergoModulo.getCodAzienda());
//    }



    /**
     * Ritorna un filtro che isola le presenze entrate
     * in un dato periodo.
     * </p>
     *
     * @param dataIni    data di inizio del periodo (null per cominciare dall'inizio)
     * @param dataEnd    data di fine del periodo (null per arrivare alla fine)
     *
     * @return il filtro sulle presenze
     */
    public static Filtro getFiltroPresenzeEntrate(Date dataIni, Date dataEnd) {
        /* variabili e costanti locali di lavoro */
        Filtro filtroPeriodo = null;
        boolean continua;
        Modulo mod;
        Filtro filtroInizio = null;
        Filtro filtroFine = null;

        try { // prova ad eseguire il codice

            mod = PresenzaModulo.get();
            continua = (mod != null);

            if (continua) {

                if (Lib.Data.isValida(dataIni)) {
                    filtroInizio = FiltroFactory.crea(Presenza.Cam.entrata.get(),
                            Filtro.Op.MAGGIORE_UGUALE,
                            dataIni);
                }// fine del blocco if

                if (Lib.Data.isValida(dataEnd)) {
                    filtroFine = FiltroFactory.crea(Presenza.Cam.entrata.get(),
                            Filtro.Op.MINORE_UGUALE,
                            dataEnd);
                }// fine del blocco if

                /**
                 * se è stata specificata almeno una data costruisce
                 * il filtro sul perdiodo
                 */
                if ((filtroInizio != null) || (filtroFine != null)) {
                    filtroPeriodo = new Filtro();
                    if (filtroInizio != null) {
                        filtroPeriodo.add(filtroInizio);
                    }// fine del blocco if
                    if (filtroFine != null) {
                        filtroPeriodo.add(filtroFine);
                    }// fine del blocco if
                }// fine del blocco if

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return filtroPeriodo;
    }




//    /**
//     * Ritorna un filtro che isola le presenze chiuse e uscite con partenza
//     * in un dato giorno per l'azienda attiva.
//     * </p>
//     *
//     * @param giorno di partenza
//     *
//     * @return il filtro sulle presenze
//     */
//    public static Filtro getFiltroPresenzePartite(Date giorno) {
//        /* variabili e costanti locali di lavoro */
//        Filtro filtro = null;
//        int codAzienda;
//
//        try { // prova ad eseguire il codice
//            codAzienda = AlbergoModulo.getCodAzienda();
//            filtro = PresenzaModulo.getFiltroPresenzePartite(giorno, codAzienda);
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//
//        /* valore di ritorno */
//        return filtro;
//    }


    /**
     * Ritorna un filtro che isola le presenze chiuse e partite
     * in un dato giorno.
     * </p>
     *
     * @param giorno     di partenza
     *
     * @return il filtro sulle presenze
     */
    public static Filtro getFiltroPresenzePartite(Date giorno) {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = new Filtro();
        boolean continua;
        Modulo mod;
        Filtro filtroChiuse;
        Filtro filtroGiorno;
        Filtro filtroNoCambi;
        Date giornoPartenza;

        try { // prova ad eseguire il codice

            mod = PresenzaModulo.get();
            continua = (mod != null);

            if (continua) {

                /**
                 * controllo giorno partenza
                 * se nullo usa data vuota
                 */
                giornoPartenza = giorno;
                if (Lib.Data.isVuota(giornoPartenza)) {
                    giornoPartenza = Lib.Data.getVuota();
                }// fine del blocco if

                filtroChiuse = FiltroFactory.creaVero(Presenza.Cam.chiuso.get());
                filtroGiorno = FiltroFactory.crea(Presenza.Cam.uscita.get(), giornoPartenza);
                filtroNoCambi = PresenzaModulo.getFiltroNoCambiUscita();
                filtro.add(filtroChiuse);
                filtro.add(filtroGiorno);
                filtro.add(filtroNoCambi);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return filtro;
    }




//    /**
//     * Ritorna un filtro che isola le presenze partite
//     * in un periodo per l'azienda attiva.
//     * </p>
//     *
//     * @param dataIni data di inizio del periodo (null per cominciare dall'inizio)
//     * @param dataEnd data di fine del periodo (null per arrivare alla fine)
//     *
//     * @return il filtro sulle presenze
//     */
//    public static Filtro getFiltroPresenzePartite(Date dataIni, Date dataEnd) {
//        /* variabili e costanti locali di lavoro */
//        Filtro filtro = null;
//        int codAzienda;
//
//        try { // prova ad eseguire il codice
//
//            codAzienda = AlbergoModulo.getCodAzienda();
//            filtro = getFiltroPresenzePartite(dataIni, dataEnd, codAzienda);
//
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//
//        /* valore di ritorno */
//        return filtro;
//    }


    /**
     * Ritorna un filtro che isola le presenze partite
     * in un periodo per una data azienda.
     * </p>
     *
     * @param dataIni    data di inizio del periodo (null per cominciare dall'inizio)
     * @param dataEnd    data di fine del periodo (null per arrivare alla fine)
     *
     * @return il filtro sulle presenze
     */
    public static Filtro getFiltroPresenzePartite(Date dataIni, Date dataEnd) {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = null;

        try { // prova ad eseguire il codice

            /* costruisce il filtro le entrate del periodo */
            Filtro filtroUscite = getFiltroPresenzeUscite(dataIni, dataEnd);

            /* costruisce il filtro per escludere i cambi in entrata */
            Filtro filtroNoCambi = PresenzaModulo.getFiltroNoCambiUscita();

            /* filtro finale */
            filtro = new Filtro();
            filtro.add(filtroUscite);
            filtro.add(filtroNoCambi);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return filtro;
    }


    /**
     * Ritorna un filtro che isola le presenze chiuse e partite
     * in un dato giorno da una data camera.
     * </p>
     *
     * @param giorno     di partenza
     * @param codCamera  codice della camera dalla quale è partita
     *
     * @return il filtro sulle presenze
     */
    public static Filtro getFiltroPresenzePartite(Date giorno, int codCamera) {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = null;
        Filtro filtro1;
        Filtro filtroCamera;

        try { // prova ad eseguire il codice

            filtro1 = getFiltroPresenzePartite(giorno);
            filtroCamera = FiltroFactory.crea(Presenza.Cam.camera.get(), codCamera);

            filtro = new Filtro();
            filtro.add(filtro1);
            filtro.add(filtroCamera);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return filtro;

    }



//    /**
//     * Ritorna un filtro che isola le presenze uscite (per partenza o cambio)
//     * in un dato periodo per l'azienda attiva.
//     * </p>
//     *
//     * @param dataIni data di inizio del periodo (null per cominciare dall'inizio)
//     * @param dataEnd data di fine del periodo (null per arrivare alla fine)
//     *
//     * @return il filtro sulle presenze
//     */
//    public static Filtro getFiltroPresenzeUscite(Date dataIni, Date dataEnd) {
//        return getFiltroPresenzeUscite(dataIni, dataEnd, AlbergoModulo.getCodAzienda());
//    }


    /**
     * Ritorna un filtro che isola le presenze uscite (per partenza o cambio)
     * in un periodo.
     * </p>
     *
     * @param dataIni    data di inizio del periodo (null per cominciare dall'inizio)
     * @param dataEnd    data di fine del periodo (null per arrivare alla fine)
     *
     * @return il filtro sulle presenze
     */
    public static Filtro getFiltroPresenzeUscite(Date dataIni, Date dataEnd) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Modulo mod;
        Filtro filtroInizio = null;
        Filtro filtroFine = null;
        Filtro filtroPeriodo = null;

        try { // prova ad eseguire il codice

            mod = PresenzaModulo.get();
            continua = (mod != null);

            if (continua) {

                if (Lib.Data.isValida(dataIni)) {
                    filtroInizio = FiltroFactory.crea(Presenza.Cam.uscita.get(),
                            Filtro.Op.MAGGIORE_UGUALE,
                            dataIni);
                }// fine del blocco if

                if (Lib.Data.isValida(dataEnd)) {
                    filtroFine = FiltroFactory.crea(Presenza.Cam.uscita.get(),
                            Filtro.Op.MINORE_UGUALE,
                            dataEnd);
                }// fine del blocco if

                /**
                 * se è stata specificata almeno una data costruisce
                 * il filtro sul perdiodo
                 */
                if ((filtroInizio != null) || (filtroFine != null)) {
                    filtroPeriodo = new Filtro();
                    if (filtroInizio != null) {
                        filtroPeriodo.add(filtroInizio);
                    }// fine del blocco if
                    if (filtroFine != null) {
                        filtroPeriodo.add(filtroFine);
                    }// fine del blocco if
                }// fine del blocco if

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return filtroPeriodo;
    }



//    /**
//     * Ritorna un filtro che isola le presenze che risultavano aperte
//     * (già arrivate ma non ancora partite)
//     * in un periodo per l'azienda attiva.
//     * </p>
//     *
//     * @param dataIni data di inizio del periodo
//     * @param dataEnd data di fine del periodo
//     *
//     * @return il filtro sulle presenze
//     */
//    public static Filtro getFiltroPresenzeAperte(Date dataIni, Date dataEnd) {
//        /* variabili e costanti locali di lavoro */
//        Filtro filtro = null;
//        boolean continua;
//        Modulo mod;
//        Filtro filtroInizio;
//        Filtro filtroFine;
//        Filtro filtroPeriodo;
//        Filtro filtroAzienda;
//        Filtro filtroNoCambi;
//        int codAzienda;
//
//        try { // prova ad eseguire il codice
//
//            codAzienda = AlbergoModulo.getCodAzienda();
//
//            mod = PresenzaModulo.get();
//            continua = (mod != null);
//
//            if (continua) {
//                filtroPeriodo = new Filtro();
//
//                filtroInizio = FiltroFactory.crea(Presenza.Cam.uscita.get(),
//                        Filtro.Op.MAGGIORE_UGUALE,
//                        dataIni);
//                filtroFine = FiltroFactory.crea(Presenza.Cam.uscita.get(),
//                        Filtro.Op.MINORE_UGUALE,
//                        dataEnd);
//
//                filtroPeriodo.add(filtroInizio);
//                filtroPeriodo.add(Filtro.Op.AND, filtroFine);
//
//                filtroNoCambi = PresenzaModulo.getFiltroNoCambiUscita();
//
//                filtro = new Filtro();
//                filtro.add(filtroPeriodo);
//                filtro.add(filtroNoCambi);
//                if (codAzienda != 0) {
//                    filtroAzienda = FiltroFactory.crea(Presenza.Cam.azienda.get(), codAzienda);
//                    filtro.add(filtroAzienda);
//                }// fine del blocco if
//            }// fine del blocco if
//
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//
//        /* valore di ritorno */
//        return filtro;
//    }


//    /**
//     * Ritorna un filtro che isola le presenze chiuse e uscite con cambio
//     * in un dato giorno per l'azienda attiva.
//     * </p>
//     *
//     * @param giorno di arrivo
//     *
//     * @return il filtro sulle presenze
//     */
//    public static Filtro getFiltroPresenzeUsciteCambio(Date giorno) {
//        /* variabili e costanti locali di lavoro */
//        Filtro filtro = null;
//        int codAzienda;
//
//        try { // prova ad eseguire il codice
//            codAzienda = AlbergoModulo.getCodAzienda();
//            filtro = PresenzaModulo.getFiltroPresenzeUsciteCambio(giorno, codAzienda);
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//
//        /* valore di ritorno */
//        return filtro;
//    }


//    /**
//     * Ritorna un filtro che isola le presenze entrate con cambio
//     * in un dato giorno per l'azienda attiva.
//     * </p>
//     *
//     * @param giorno di arrivo
//     *
//     * @return il filtro sulle presenze
//     */
//    public static Filtro getFiltroPresenzeEntrateCambio(Date giorno) {
//        /* variabili e costanti locali di lavoro */
//        Filtro filtro = null;
//        int codAzienda;
//
//        try { // prova ad eseguire il codice
//            codAzienda = AlbergoModulo.getCodAzienda();
//            filtro = PresenzaModulo.getFiltroPresenzeEntrateCambio(giorno, codAzienda);
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//
//        /* valore di ritorno */
//        return filtro;
//    }


    /**
     * Ritorna un filtro che isola le presenze chiuse e uscite con cambio
     * in un dato giorno.
     * </p>
     *
     * @param giorno     di uscita
     *
     * @return il filtro sulle presenze
     */
    public static Filtro getFiltroPresenzeUsciteCambio(Date giorno) {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = new Filtro();
        boolean continua;
        Modulo mod;
        Filtro filtroChiuse;
        Filtro filtroGiorno;
        Filtro filtroSoloCambi;

        try { // prova ad eseguire il codice

            mod = PresenzaModulo.get();
            continua = (mod != null);

            if (continua) {
                filtroChiuse = FiltroFactory.creaVero(Presenza.Cam.chiuso.get());
                filtroGiorno = FiltroFactory.crea(Presenza.Cam.uscita.get(), giorno);
                filtroSoloCambi = PresenzaModulo.getFiltroSoloCambiUscita();
                filtro.add(filtroChiuse);
                filtro.add(filtroGiorno);
                filtro.add(filtroSoloCambi);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return filtro;
    }


    /**
     * Ritorna un filtro che isola le presenze entrate con cambio
     * in un dato giorno.
     * </p>
     *
     * @param giorno     di entrata
     *
     * @return il filtro sulle presenze
     */
    public static Filtro getFiltroPresenzeEntrateCambio(Date giorno) {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = new Filtro();
        boolean continua;
        Modulo mod;
        Filtro filtroGiorno;
        Filtro filtroSoloCambi;

        try { // prova ad eseguire il codice

            mod = PresenzaModulo.get();
            continua = (mod != null);

            if (continua) {
                filtroGiorno = FiltroFactory.crea(Presenza.Cam.entrata.get(), giorno);
                filtroSoloCambi = PresenzaModulo.getFiltroSoloCambiEntrata();
                filtro.add(filtroGiorno);
                filtro.add(filtroSoloCambi);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return filtro;
    }




    /**
     * Ritorna l'elenco delle presenze arrivate in un dato periodo.
     * <p/>
     * I cambi non vengono considerati <br>
     *
     * @param dataIni data di inizio del periodo
     * @param dataEnd data di fine del periodo
     *
     * @return elenco di codici delle presenze
     */
    public static int[] getPresenzeArrivate(Date dataIni, Date dataEnd) {
        /* variabili e costanti locali di lavoro */
        int[] presenze = null;
        Filtro filtro;
        Modulo modPresenza;

        try { // prova ad eseguire il codice

            filtro = getFiltroPresenzeArrivate(dataIni, dataEnd);
            modPresenza = PresenzaModulo.get();
            presenze = modPresenza.query().valoriChiave(filtro);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return presenze;
    }



    /**
     * Ritorna l'elenco delle presenze chiuse e partite in un dato giorno.
     * <p/>
     *
     * @param data data della partenza
     *
     * @return elenco di codici delle presenze
     */
    public static int[] getPresenzePartite(Date data) {
        /* variabili e costanti locali di lavoro */
        int[] presenze = null;
        Filtro filtro;
        Modulo modPresenza;

        try { // prova ad eseguire il codice

            filtro = getFiltroPresenzePartite(data);
            modPresenza = PresenzaModulo.get();
            presenze = modPresenza.query().valoriChiave(filtro);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return presenze;
    }


    /**
     * Ritorna il numero di persone partite in un dato giorno.
     * <p/>
     * I cambi non vengono considerati <br>
     *
     * @param data data della partenze
     *
     * @return totale persone partite
     */
    public static int getNumPersonePartite(Date data) {
        /* variabili e costanti locali di lavoro */
        int persone = 0;
        int[] presenze;

        try { // prova ad eseguire il codice
            presenze = getPresenzePartite(data);

            if (presenze != null) {
                persone = presenze.length;
            } // fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return persone;
    }


    /**
     * Ritorna l'elenco delle presenze partite in un dato periodo.
     * <p/>
     * I cambi non vengono considerati <br>
     *
     * @param dataIni data di inizio del periodo
     * @param dataEnd data di fine del periodo
     *
     * @return elenco di codici delle presenze
     */
    public static int[] getPresenzePartite(Date dataIni, Date dataEnd) {
        /* variabili e costanti locali di lavoro */
        int[] presenze = null;
        Filtro filtro;
        Modulo modPresenza;

        try { // prova ad eseguire il codice

            filtro = getFiltroPresenzePartite(dataIni, dataEnd);
            modPresenza = PresenzaModulo.get();
            presenze = modPresenza.query().valoriChiave(filtro);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return presenze;
    }


    /**
     * Ritorna il numero di persone partite in un dato periodo.
     * <p/>
     * I cambi non vengono considerati <br>
     *
     * @param dataIni data di inizio del periodo
     * @param dataEnd data di fine del periodo
     *
     * @return totale persone partite
     */
    public static int getNumPersonePartite(Date dataIni, Date dataEnd) {
        /* variabili e costanti locali di lavoro */
        int persone = 0;
        int[] presenze;

        try { // prova ad eseguire il codice
            presenze = getPresenzePartite(dataIni, dataEnd);

            if (presenze != null) {
                persone = presenze.length;
            } // fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return persone;
    }


    /**
     * Ritorna un filtro che isola tutte le presenze con entrata non cambio
     * <p/>
     *
     * @return il filtro creato
     */
    private static Filtro getFiltroNoCambiEntrata() {
        return FiltroFactory.creaFalso(Presenza.Cam.cambioEntrata.get());
    }


    /**
     * Ritorna un filtro che isola tutte le presenze con uscita non cambio
     * <p/>
     *
     * @return il filtro creato
     */
    private static Filtro getFiltroNoCambiUscita() {
        return FiltroFactory.creaFalso(Presenza.Cam.cambioUscita.get());
    }


    /**
     * Ritorna un filtro che isola tutte le presenze con uscita per cambio
     * <p/>
     *
     * @return il filtro creato
     */
    private static Filtro getFiltroSoloCambiUscita() {
        return FiltroFactory.creaVero(Presenza.Cam.cambioUscita.get());
    }


    /**
     * Ritorna un filtro che isola tutte le presenze con entrata per cambio
     * <p/>
     *
     * @return il filtro creato
     */
    private static Filtro getFiltroSoloCambiEntrata() {
        return FiltroFactory.creaVero(Presenza.Cam.cambioEntrata.get());
    }


    /**
     * Ritorna un filtro che isola tutte le presenze provvisorie.
     * <p/>
     *
     * @return il filtro creato
     */
    public static Filtro getFiltroProvvisorie() {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = null;

        try { // prova ad eseguire il codice
            filtro = FiltroFactory.creaVero(Cam.provvisoria);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return filtro;
    }


    /**
     * Ritorna un filtro che isola tutte le presenze provvisorie in una data camera.
     * <p/>
     *
     * @param codCamera della camera
     *
     * @return il filtro creato
     */
    public static Filtro getFiltroProvvisorie(int codCamera) {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = null;

        try { // prova ad eseguire il codice
            filtro = getFiltroProvvisorie();
            filtro.add(getFiltroCamera(codCamera));
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return filtro;
    }


    /**
     * Ritorna l'elenco delle presenze provvisorie in una data camera.
     * <p/>
     *
     * @param codCamera della camera
     *
     * @return l'elenco delle presenze provvisorie
     */
    public static int[] getPresenzeProvvisorie(int codCamera) {
        /* variabili e costanti locali di lavoro */
        int[] presenze = null;
        Filtro filtro;
        Modulo mod;

        try { // prova ad eseguire il codice
            filtro = getFiltroProvvisorie(codCamera);
            mod = PresenzaModulo.get();
            presenze = mod.query().valoriChiave(filtro);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return presenze;
    }


    /**
     * Ritorna un filtro che isola tutte le presenze aperte in una data camera.
     * <p/>
     * Esclude sempre le presenze provvisorie
     *
     * @param codCamera della camera
     *
     * @return il filtro creato
     */
    public static Filtro getFiltroAperte(int codCamera) {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = null;

        try { // prova ad eseguire il codice
            filtro = new Filtro();
            filtro.add(getFiltroAperte(false));
            filtro.add(getFiltroCamera(codCamera));
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return filtro;
    }


    /**
     * Ritorna un filtro che isola tutte le presenze aperte in una data camera.
     * <p/>
     * Controlla se nella camera ci sono presenze "provvisorie"
     * Se ce ne sono, seleziona solo quelle
     * Altrimenti, seleziona tutte le presenze aperte nella camera
     *
     * @param codCamera da controllare
     *
     * @return il filtro per le presenze da visualizzare
     */
    public static Filtro getFiltroAperteSmart(int codCamera) {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = null;
        Filtro filtroTutte;
        Filtro filtroProvvisorie;
        Modulo mod;
        boolean esistono;

        try { // prova ad eseguire il codice

            mod = PresenzaModulo.get();

            filtroTutte = PresenzaModulo.getFiltroAperteTotali(codCamera);
            filtroProvvisorie = FiltroFactory.creaVero(Presenza.Cam.provvisoria);
            filtro = new Filtro();
            filtro.add(filtroTutte);
            filtro.add(filtroProvvisorie);
            esistono = mod.query().isEsisteRecord(filtro);

            /* se non esistono presenze "provvisorie" seleziona tutte */
            if (!esistono) {
                filtro = filtroTutte;
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return filtro;
    }


    /**
     * Ritorna l'elenco delle presenze aperte per una data camera.
     * <p/>
     * Esclude sempre le presenze provvisorie
     *
     * @param codCamera della camera
     *
     * @return elenco di codici delle presenze
     */
    public static int[] getPresenzeAperteSmart(int codCamera) {
        /* variabili e costanti locali di lavoro */
        int[] presenze = null;
        Filtro filtro;
        Modulo modPresenza;

        try { // prova ad eseguire il codice

            filtro = getFiltroAperteSmart(codCamera);
            modPresenza = PresenzaModulo.get();
            presenze = modPresenza.query().valoriChiave(filtro);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return presenze;
    }


    /**
     * Ritorna il totale di persone presenti in un dato giorno.
     * <p/>
     *
     * @param giorno da considerare
     *
     * @return numero di persone presenti
     */
    public static int getPersone(Date giorno) {
        /* variabili e costanti locali di lavoro */
        int persone = 0;
        Filtro filtro;
        Modulo modPresenza;

        try { // prova ad eseguire il codice

//todo

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return persone;
    }


    /**
     * Ritorna un filtro che isola tutte le presenze chiuse per
     * una data azienda.
     * <p/>
     *
     * @param codAzienda codice dell'azienda (0 per tutte le aziende)
     *
     * @return il filtro creato
     */
    public static Filtro getFiltroChiuseAzienda(int codAzienda) {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = null;
        Filtro filtroChiuse;
        Filtro filtroAzienda;

        try { // prova ad eseguire il codice
            filtro = new Filtro();
            filtroChiuse = FiltroFactory.creaVero(Cam.chiuso);
            filtroChiuse.add(filtro);
            if (codAzienda > 0) {
                filtroAzienda = FiltroFactory.crea(Cam.azienda.get(), codAzienda);
                filtro.add(filtroAzienda);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return filtro;
    }


//    /**
//     * Ritorna un filtro che isola tutte le presenze chiuse
//     * per l'azienda corrente in una data camera.
//     * <p/>
//     *
//     * @param codCamera della camera
//     *
//     * @return il filtro creato
//     */
//    public static Filtro getFiltroChiuse(int codCamera) {
//        /* variabili e costanti locali di lavoro */
//        Filtro filtro = null;
//        int codAzienda;
//
//        try { // prova ad eseguire il codice
//            codAzienda = AlbergoModulo.getCodAzienda();
//            filtro = getFiltroChiuse(codCamera, codAzienda);
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//
//        /* valore di ritorno */
//        return filtro;
//    }


    /**
     * Ritorna un filtro che isola tutte le presenze
     * chiuse per una data camera.
     * <p/>
     *
     * @param codCamera  della camera
     *
     * @return il filtro creato
     */
    public static Filtro getFiltroChiuse(int codCamera) {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = null;
        Filtro filtroChiuse;
        Filtro filtroCamera;

        try { // prova ad eseguire il codice
            filtro = new Filtro();
            filtroChiuse = FiltroFactory.creaVero(Cam.chiuso);
            filtro.add(filtroChiuse);
            filtroCamera = getFiltroCamera(codCamera);
            filtro.add(filtroCamera);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return filtro;
    }


    /**
     * Ritorna un filtro che isola tutte le presenze attuali in una data camera.
     * <p/>
     * Comprende le presenze "provvisorie" (con flag partenzaOggi=true)
     *
     * @param codCamera della camera
     *
     * @return il filtro creato
     */
    public static Filtro getFiltroAperteTotali(int codCamera) {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = null;

        try { // prova ad eseguire il codice
            filtro = new Filtro();
            filtro.add(getFiltroAperteTotali());
            filtro.add(getFiltroCamera(codCamera));
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return filtro;
    }


    /**
     * Ritorna l'elenco delle presenze aperte per una data camera.
     * <p/>
     * Esclude sempre le presenze provvisorie
     *
     * @param codCamera della camera
     *
     * @return elenco di codici delle presenze
     */
    public static int[] getPresenzeAperte(int codCamera) {
        /* variabili e costanti locali di lavoro */
        int[] presenze = null;
        Filtro filtro;
        Modulo modPresenza;

        try { // prova ad eseguire il codice

            filtro = getFiltroAperte(codCamera);
            modPresenza = PresenzaModulo.get();
            presenze = modPresenza.query().valoriChiave(filtro);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return presenze;
    }


    /**
     * Ritorna l'elenco delle presenze aperte per una data camera.
     * <p/>
     * Comprende le presenze "provvisorie" (con flag partenzaOggi=true)
     *
     * @param codCamera della camera
     *
     * @return elenco di codici delle presenze
     */
    public static int[] getPresenzeAperteTotali(int codCamera) {
        /* variabili e costanti locali di lavoro */
        int[] presenze = null;
        Filtro filtro;
        Modulo modPresenza;

        try { // prova ad eseguire il codice

            filtro = getFiltroAperteTotali(codCamera);
            modPresenza = PresenzaModulo.get();
            presenze = modPresenza.query().valoriChiave(filtro);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return presenze;
    }


    /**
     * Presenze alla data.
     * <p/>
     *
     * @param data di riferimento
     *
     * @return codici delle presenze in albergo alla data di riferimento
     */
    public static int[] getCodPresenze(Date data) {
        /* variabili e costanti locali di lavoro */
        int[] presenze = null;
        Filtro filtro;
        Modulo modPresenza;

        try { // prova ad eseguire il codice
            filtro = getFiltroPresenze(data);

            modPresenza = PresenzaModulo.get();
            presenze = modPresenza.query().valoriChiave(filtro);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return presenze;
    }


    /**
     * Presenze alla data.
     * <p/>
     *
     * @param data di riferimento
     *
     * @return numero di persone presenti in albergo
     */
    public static int getNumPresenze(Date data) {
        /* variabili e costanti locali di lavoro */
        int numeroPresenze = 0;
        int[] presenze;

        try { // prova ad eseguire il codice
            presenze = getCodPresenze(data);

            if (presenze != null) {
                numeroPresenze = presenze.length;
            } // fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return numeroPresenze;
    }


    /**
     * Ritorna un filtro che isola tutte le presenze in una data camera alla data.
     * <p/>
     *
     * @param codCamera interessata
     * @param data      di riferimento
     *
     * @return il filtro creato
     */
    public static Filtro getFiltroPresenze(final int codCamera, Date data) {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = null;

        try { // prova ad eseguire il codice
            filtro = new Filtro();
            filtro.add(getFiltroPresenze(data));
            filtro.add(getFiltroCamera(codCamera));
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return filtro;
    }


    /**
     * Ritorna l'elenco dei conti aperti dato un elenco di presenze.
     * <p/>
     *
     * @param presenze elenco dei codici di presenza
     *
     * @return elenco dei corrispondenti codici dei conti aperti
     */
    public static int[] getContiAperti(int[] presenze) {
        /* variabili e costanti locali di lavoro */
        int[] codiciConto = null;
        ArrayList<Integer> listaConti = new ArrayList<Integer>();
        int codConto;
        Modulo modPresenza;
        Modulo modConto;
        boolean chiuso;

        try { // prova ad eseguire il codice
            modPresenza = PresenzaModulo.get();
            modConto = ContoModulo.get();
            for (int codPresenza : presenze) {
                codConto = modPresenza.query().valoreInt(Presenza.Cam.conto.get(), codPresenza);
                chiuso = modConto.query().valoreBool(Conto.Cam.chiuso.get(), codConto);
                if (!chiuso) {
                    if (!listaConti.contains(codConto)) {
                        listaConti.add(codConto);
                    }// fine del blocco if
                }// fine del blocco if
            }
            codiciConto = Lib.Array.creaIntArray(listaConti);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codiciConto;
    }


    /**
     * Ritorna la camera per una data presenza.
     * <p/>
     *
     * @param codPresenza codice della presenza
     *
     * @return codice della camera corrispondente
     */
    public static int getCamera(int codPresenza) {
        /* variabili e costanti locali di lavoro */
        int codCamera = 0;
        Modulo modPresenza;

        try { // prova ad eseguire il codice

            modPresenza = PresenzaModulo.get();
            codCamera = modPresenza.query().valoreInt(Cam.camera.get(), codPresenza);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codCamera;
    }


    /**
     * Ritorna l'elenco dei clienti corrispondenti a un filtro di presenze.
     * <p/>
     *
     * @param filtroPresenze filtro sulle presenze
     *
     * @return elenco dei codici cliente corrispondenti
     */
    private static int[] getClienti(Filtro filtroPresenze) {
        /* variabili e costanti locali di lavoro */
        int[] clienti = null;
        int[] presenze;
        Modulo modPresenza;
        Object ogg;

        try { // prova ad eseguire il codice

            modPresenza = PresenzaModulo.get();

            presenze = modPresenza.query().valoriChiave(filtroPresenze);

            clienti = new int[presenze.length];
            for (int k = 0; k < presenze.length; k++) {
                ogg = modPresenza.query().valore(modPresenza.getCampoChiave().getNomeInterno(),
                        presenze[k],
                        Presenza.Cam.cliente.get());
                clienti[k] = Libreria.getInt(ogg);
            } // fine del ciclo for
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return clienti;

    }


    /**
     * Ritorna le presenze.
     * </p>
     *
     * @return codici delle presenze
     */
    private static ArrayList<Integer> getListaPresenze() {
        /* variabili e costanti locali di lavoro */
        ArrayList<Integer> clienti = null;
        int[] presenti;
        Filtro filtro;
        Modulo modPresenza;

        try { // prova ad eseguire il codice
            modPresenza = PresenzaModulo.get();
            filtro = FiltroFactory.creaFalso(Cam.chiuso);

            presenti = modPresenza.query().valoriChiave(filtro);
            clienti = Lib.Array.creaLista(presenti);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return clienti;
    }


    /**
     * Ritorna l'elenco delle camere attualmente occupate.
     * </p>
     *
     * @return codici delle camere occupate in ordine di nome della camera
     */
    public static int[] getCamereOccupate() {
        /* variabili e costanti locali di lavoro */
        int[] camereOut = null;
        int[] presenze;
        ArrayList<Integer> camere;
        Filtro filtro;
        Ordine ordine;
        Modulo modPresenza;
        Modulo modCamera;
        Campo campo;
        int codPresenza;
        int codCamera;

        try { // prova ad eseguire il codice

            modPresenza = PresenzaModulo.get();
            filtro = FiltroFactory.creaFalso(Cam.chiuso);

            modCamera = CameraModulo.get();
            campo = modCamera.getCampo(Camera.Cam.camera);
            ordine = new Ordine(campo);

            presenze = modPresenza.query().valoriChiave(filtro, ordine);

            camere = new ArrayList<Integer>();
            for (int k = 0; k < presenze.length; k++) {
                codPresenza = presenze[k];
                codCamera = modPresenza.query().valoreInt(Presenza.Cam.camera.get(), codPresenza);
                if (!camere.contains(codCamera)) {
                    camere.add(codCamera);
                }// fine del blocco if
            } // fine del ciclo for

            /* crea l'array da ritornare */
            camereOut = new int[camere.size()];
            for (int k = 0; k < camereOut.length; k++) {
                camereOut[k] = camere.get(k);
            } // fine del ciclo for


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return camereOut;
    }


    /**
     * Ritorna la data di inizio dell'ultimo soggiorno
     * di un dato cliente.
     * <p/>
     * Cerca l'arrivo più recente del cliente nelle presenze.
     *
     * @param codCliente codice del cliente
     *
     * @return la data di inizio ultimo soggiorno, data vuota se non trovato
     */
    public static Date getDataUltimoSoggiorno(int codCliente) {
        /* variabili e costanti locali di lavoro */
        Date dataUltimo = Lib.Data.getVuota();
        boolean continua;
        Filtro filtroTot;
        Filtro filtro;
        Ordine ordine;
        Modulo modPresenze;
        ArrayList lista;
        Object oggetto;

        try { // prova ad eseguire il codice

            continua = (codCliente > 0);

            if (continua) {

                filtroTot = new Filtro();

                /* solo il cliente specificato */
                filtro = FiltroFactory.crea(Presenza.Cam.cliente.get(), codCliente);
                filtroTot.add(filtro);

                /* esclusi i periodi con entrata con cambio */
                filtro = FiltroFactory.crea(Presenza.Cam.cambioEntrata.get(), false);
                filtroTot.add(filtro);

                /* ordine per data di arrivo, prima la più recente */
                ordine = new Ordine();
                ordine.add(Presenza.Cam.entrata.get(), Operatore.DISCENDENTE);

                modPresenze = PresenzaModulo.get();
                lista = modPresenze.query().valoriCampo(Presenza.Cam.entrata.get(), filtroTot, ordine);

                if (lista.size() > 0) {
                    oggetto = lista.get(0);
                    dataUltimo = Libreria.getDate(oggetto);
                }// fine del blocco if

            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return dataUltimo;
    }


//    /**
//     * Ritorna la data di inizio dell'ultimo soggiorno
//     * di un dato cliente nell'azienda attiva.
//     * <p/>
//     *
//     * @param codCliente codice del cliente
//     *
//     * @return la data di inizio ultimo soggiorno, data vuota se non trovato
//     */
//    public static Date getDataUltimoSoggiorno(int codCliente) {
//        /* variabili e costanti locali di lavoro */
//        Date dataUltimo = null;
//        boolean continua;
//        int codAzienda = 0;
//
//        try { // prova ad eseguire il codice
//            try { // prova ad eseguire il codice
//                codAzienda = AlbergoModulo.getCodAzienda();
//                continua = true;
//            } catch (Exception unErrore) { // intercetta l'errore
//                continua = false;
//            }// fine del blocco try-catch
//
//            if (continua) {
//                dataUltimo = getDataUltimoSoggiorno(codCliente, codAzienda);
//            }// fine del blocco if
//
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//
//        /* valore di ritorno */
//        return dataUltimo;
//    }


    /**
     * Ritorna un filtro che isola i record di una azienda data.
     * </p>
     *
     * @param codAzienda codice dell'azienda (0 per tutte)
     *
     * @return il filtro per l'azienda data
     */
    public static Filtro getFiltroAzienda(int codAzienda) {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = null;

        try { // prova ad eseguire il codice
            if (codAzienda != 0) {
                filtro = FiltroFactory.crea(Presenza.Cam.azienda.get(), codAzienda);
            } // fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return filtro;
    }


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
     * Azione per eliminare delle aziende
     */
    private class AzioneDelAziende extends DelAziendeAz {

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
        public void delAziendeAz(DelAziendeEve unEvento) {
            delAziende();
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


    private Filtro getFiltroAzienda() {
        return filtroAzienda;
    }


    private void setFiltroAzienda(Filtro filtro) {
        this.filtroAzienda = filtro;
    }


    /**
     * Restituisce in runtime l'istanza presente in progetto.
     * <p/>
     *
     * @return modulo presente in Progetto
     */
    public static PresenzaModulo get() {
        return (PresenzaModulo)ModuloBase.get(NOME_CHIAVE);
    }


    /**
     * Main method.
     *
     * @param argomenti eventuali parametri in ingresso (quasi mai)
     */
    public static void main(String[] argomenti) {
        /* crea una istanza di se stessa */
        Modulo primoModuloProvvisorio = new PresenzaModulo();

        /**
         * inizializza tutti i moduli usati da questo modulo <br>
         * lancia il modulo iniziale del programma (obbligatorio) <br>
         */
        Progetto.lancia(argomenti, primoModuloProvvisorio);
    } // fine del metodo main


}// fine della classe