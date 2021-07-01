/**
 * Copyright(c): 2006
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 16-5-2007
 */

package it.algos.albergo.prenotazione;

import it.algos.albergo.Albergo;
import it.algos.albergo.AlbergoLib;
import it.algos.albergo.AlbergoModulo;
import it.algos.albergo.arrivipartenze.ArriviPartenzeModulo;
import it.algos.albergo.camera.CameraModulo;
import it.algos.albergo.camera.composizione.CompoCameraModulo;
import it.algos.albergo.clientealbergo.ClienteAlbergoModulo;
import it.algos.albergo.evento.CambioAziendaAz;
import it.algos.albergo.evento.CambioAziendaEve;
import it.algos.albergo.evento.CambioDataAz;
import it.algos.albergo.evento.CambioDataEve;
import it.algos.albergo.evento.DelAziendeAz;
import it.algos.albergo.evento.DelAziendeEve;
import it.algos.albergo.listino.Listino;
import it.algos.albergo.prenotazione.periodo.Periodo;
import it.algos.albergo.prenotazione.periodo.PeriodoModulo;
import it.algos.albergo.presenza.PresenzaModulo;
import it.algos.albergo.preventivo.PreventivoDialogo;
import it.algos.albergo.promemoria.PromemoriaModulo;
import it.algos.albergo.risorse.Risorsa;
import it.algos.albergo.storico.NavStorico;
import it.algos.albergo.sviluppopresenze.SviluppoDialogo;
import it.algos.albergo.tabelle.azienda.AziendaModulo;
import it.algos.albergo.tabelle.canaleprenotazione.Canale;
import it.algos.albergo.tabelle.canaleprenotazione.CanaleModulo;
import it.algos.base.albero.AlberoNodo;
import it.algos.base.azione.AzSpecifica;
import it.algos.base.campo.base.Campo;
import it.algos.base.costante.CostanteColore;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.errore.Errore;
import it.algos.base.finestra.Finestra;
import it.algos.base.libreria.Libreria;
import it.algos.base.modulo.Modulo;
import it.algos.base.modulo.ModuloBase;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.pannello.Pannello;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.statusbar.StatusBar;

import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;

/**
 * Periodi di una prenotazione alberghiera.
 * <p/>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 16-5-2007
 */
public final class PrenotazioneModulo extends ModuloBase implements Prenotazione {

    /**
     * Nome-chiave interno di questo modulo (obbligatorio)
     */
    private static final String NOME_CHIAVE = Prenotazione.NOME_MODULO;

    /**
     * Titolo della finestra del navigatore
     * (usato nel Navigatore)
     */
    private static final String TITOLO_FINESTRA = Prenotazione.TITOLO_FINESTRA;

    /**
     * Titolo del modulo come appare nel Menu Moduli degli altri moduli
     * (usato nel Navigatore)
     */
    private static final String TITOLO_MENU = Prenotazione.TITOLO_MENU;

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
    public PrenotazioneModulo() {
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
    public PrenotazioneModulo(AlberoNodo unNodo) {
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
        /* preferenze specifiche di questo modulo */
        new PrenotazionePref();

        /* selezione del modello (obbligatorio) */
        super.setModello(new PrenotazioneModello());

        /* regola il titolo della finestra del navigatore */
        super.setTitoloFinestra(TITOLO_FINESTRA);

        /* regola il titolo di questo modulo nei menu di altri moduli */
        super.setTitoloMenu(TITOLO_MENU);

        /* assegna una icona al modulo */
        this.setIcona("prenotazione24");

        this.setSchedaPop(new PrenotazioneScheda(this));
    }


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

        super.inizializza();

        try { // prova ad eseguire il codice

            /**
             * si registra presso il modulo albergo per
             * essere informato su alcuni eventi
             */
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
            this.setAvviato(false);

            /* selezione inziale di un filtro, tramite il popup */
            this.setPopup(Prenotazione.Pop.anni, 1);
            this.setPopup(Prenotazione.Pop.aperte, 1);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    } /* fine del metodo */


    /**
     * Sincronizza gli elementi.
     * <p/>
     * Metodo invocato dal metodo fire() <br>
     * Può essere invocato anche da altri metodi interni <br>
     * (in salita) <b>
     */
    @Override
    public void sincroInterno() {
        super.sincroInterno();

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
            super.creaNavigatori();

            nav = super.getNavigatoreDefault();
            nav.setUsaPannelloUnico(true);
            nav.setRigheLista(24);
            nav.addSchedaCorrente(new PrenotazioneScheda(this));

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Crea tutti i Moduli indispensabili per il funzionamento di questo modulo.
     * </p>
     * Per ogni modulo, invoca il metodo della superclasse, passandogli
     * un'istanza provvisoria del modulo <br>
     * Questa istanza viene usata solo per portarsi il percorso della
     * classe (implicito) ed il nome chiave (esplicito) <br>
     * La creazione definitiva del Modulo viene delegata alla classe
     * Progetto nel metodo creaModulo() <br>
     */
    protected void creaModuli() {
        try { // prova ad eseguire il codice
            super.creaModulo(new ClienteAlbergoModulo());
            super.creaModulo(new PeriodoModulo());
            super.creaModulo(new ArriviPartenzeModulo());
            super.creaModulo(new PresenzaModulo());
            super.creaModulo(new CanaleModulo());
            super.creaModulo(new PromemoriaModulo());
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Aggiunge i moduli (e le tabelle) al menu.
     * </p>
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
            super.addModuloVisibile(Listino.NOME_MODULO);
            super.addModuloVisibile(Canale.NOME_MODULO);
            super.addModuloVisibile(Risorsa.NOME_MODULO);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Creazione del navigatore per lo storico.
     * <p/>
     *
     * @return il Navigatore pe lo storico
     */
    public NavStorico getNavStorico() {
        /* variabili e costanti locali di lavoro */
        NavStorico nav = null;

        try { // prova ad eseguire il codice

            /* navigatore usato dal pannello Storico */
            nav = new NavStorico(this, Prenotazione.Nav.storico.get(),
                    Prenotazione.Vis.storico.get());
            nav.inizializza();
            nav.avvia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return nav;
    }


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

            /* statistiche prenotazioni */
            super.addAzione(new AzStatistiche());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regola il filtro del modulo in funzione dell'azienda attiva.
     * <p/>
     */
    public void regolaFiltro() {
        Filtro filtro = null;
        AlbergoModulo modAlbergo;
        Campo campoAzienda;
        int codAz;

        try { // prova ad eseguire il codice
            modAlbergo = (AlbergoModulo) Progetto.getModulo(Albergo.NOME_MODULO);
            if (modAlbergo != null) {
                codAz = AlbergoModulo.getCodAzienda();
                if (codAz > 0) {
                    campoAzienda = PrenotazioneModulo.get()
                            .getCampo(Prenotazione.Cam.azienda.get());
                    filtro = FiltroFactory.crea(campoAzienda, codAz);
                }// fine del blocco if
            }// fine del blocco if

            this.setFiltroAzienda(filtro);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Restituisce in runtime l'istanza presente in progetto.
     * <p/>
     *
     * @return modulo presente in Progetto
     */
    public static PrenotazioneModulo get() {
        return (PrenotazioneModulo) ModuloBase.get(NOME_CHIAVE);
    }


    /**
     * Invocato quando cambia l'azienda attiva.
     * <p/>
     */
    private void cambioAzienda() {
        try { // prova ad eseguire il codice
            Filtro filtro = this.getFiltroAzienda();
            this.getModello().setFiltroModello(filtro);      // filtro al modulo Prenotazioni
            PeriodoModulo.get().getModello().setFiltroModello(filtro);     // filtro al modulo Periodi
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
        Filtro filtro;
        Campo campo;
        int codAzPrincipale = 0;

        try { // prova ad eseguire il codice

            /* recupera il codice della azienda principale e controlla che esista */
            codAzPrincipale = AziendaModulo.getCodAziendaPrincipale();
            if (codAzPrincipale == 0) {
                continua = false;
            }// fine del blocco if

            /* elimina tutti i record che non appartengono alla azienda principale */
            if (continua) {
                campo = this.getCampo(Cam.azienda.get());
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
    private void cambioData() {
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
        String desc = "";
        Pannello pan;
        Navigatore nav;
        Finestra fin;
        JLabel label;
        boolean continua = true;

        try { // prova ad eseguire il codice

            /* recupera il modulo Albergo se esiste */
            if (continua) {
                continua = false;
                modulo = Progetto.getModulo(Albergo.NOME_MODULO);
                if ((modulo != null) && (modulo instanceof AlbergoModulo)) {
                    modAlbergo = (AlbergoModulo) modulo;
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
     * Aggiorna i navigatori del modulo.
     * <p/>
     * I Navigatori utilizzano il filtro azienda.
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
     * Presenta un dialogo per un preventivo.
     * <p/>
     */
    public void preventivo() {
        /* variabili e costanti locali di lavoro */
        PreventivoDialogo preventivo;
        String titolo;
        String messaggio;

        try { // prova ad eseguire il codice

            /* crea il dialogo */
            preventivo = new PreventivoDialogo();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Apre la funzione Statistiche.
     * <p/>
     */
    public void sviluppoPresenze() {

        try { // prova ad eseguire il codice

            new SviluppoDialogo().avvia();


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Sincronizza il flag chiuso della prenotazione in base ai periodi.
     * <p/>
     * Invocato dal trigger dei periodi dopo creazione, modifica o
     * cancellazione di un periodo
     * - Se la prenotazione non periodi non la modifica
     * - Se la prenotazione ha tutti i periodi chiusi la pone Chiusa
     * - Se la prenotazione ha almeno un periodo aperto la pone Aperta
     *
     * @param codPrenotazione codice della prenotazione
     * @param conn            la connessione da utilizzare per le query
     * @return true se riuscito
     */
    public static boolean syncStatoPrenotazione(int codPrenotazione, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = true;
        Modulo modPeriodo;
        Modulo modPrenotazione;
        Filtro filtro;
        ArrayList valori;
        boolean chiuso = false;
        boolean statoDesiderato;
        boolean statoAttuale;

        try {    // prova ad eseguire il codice
            modPeriodo = PeriodoModulo.get();
            modPrenotazione = PrenotazioneModulo.get();

            filtro = FiltroFactory.crea(Periodo.Cam.prenotazione.get(), codPrenotazione);
            valori = modPeriodo.query().valoriCampo(Periodo.Cam.partito.get(), filtro, conn);
            if (valori.size() > 0) {

                /* spazzola il flag chiuse dei periodi e se ne trova uno aperto interrompe */
                for (Object valore : valori) {
                    chiuso = Libreria.getBool(valore);
                    if (!chiuso) {
                        break;
                    }// fine del blocco if
                }

                /* determina lo stato desiderato */
                statoDesiderato = chiuso;

                /* legge lo stato attuale */
                statoAttuale = modPrenotazione.query()
                        .valoreBool(Prenotazione.Cam.chiusa.get(), codPrenotazione, conn);

                /* se diversi modifica il record */
                if (statoAttuale != statoDesiderato) {
                    riuscito = modPrenotazione.query().registraRecordValore(
                            codPrenotazione,
                            Prenotazione.Cam.chiusa.get(),
                            statoDesiderato,
                            conn);
                }// fine del blocco if

            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Controlla se una prenotazione è confermata.
     * <p/>
     *
     * @param codPrenotazione da controllare
     * @return true se la prenotazione è confermata.
     */
    public static boolean isConfermata(int codPrenotazione) {
        /* variabili e costanti locali di lavoro */
        boolean confermata = false;
        Modulo modPrenotazione;

        try {    // prova ad eseguire il codice
            modPrenotazione = PrenotazioneModulo.get();
            if (modPrenotazione != null) {
                confermata = modPrenotazione.query().valoreBool(
                        Cam.confermata.get(),
                        codPrenotazione);
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return confermata;
    }


    /**
     * Crea un filtro che isola le prenotazioni valide.
     * <p/>
     * Sono considerate valide tutte le prenotazioni:
     * - non disdette
     * - non opzioni
     * le prenotazioni non confermate sono considerate valide.
     *
     * @return il filtro creato
     */
    public static Filtro getFiltroValide() {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = new Filtro();
        Filtro unFiltro;
        Modulo modPren = PrenotazioneModulo.get();

        try {    // prova ad eseguire il codice

            /* filtro escludi disdette */
            unFiltro = FiltroFactory.crea(modPren.getCampo(Prenotazione.Cam.disdetta), false);
            filtro.add(unFiltro);

            /* filtro escludi opzioni */
            unFiltro = FiltroFactory.crea(modPren.getCampo(Prenotazione.Cam.opzione), false);
            filtro.add(unFiltro);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return filtro;
    }

    /**
     * Crea una nuova prenotazione.
     * <p/>
     * Crea una prenotazione
     * ** Recupera l'azienda corrente e la regola per il nuovo record di prenotazione
     * Crea un periodo coi dati provenienti dal book
     *
     * @return codice della prenotazione appena creata
     *         zero se non è stata creata
     */
    public int nuovaPrenotazione(int codCamera, Date dataInizio, Date dataFine) {
        /* variabili e costanti locali di lavoro */
        int codPrenotazione = 0;
        int codPeriodo;
        Modulo modPrenotazione;
        Modulo modPeriodo;
        int codAzienda;
        int codPensione = Listino.PensioniPeriodo.pensioneCompleta.getCodice();  // FB
        int codCompo;
        int adulti;
        int bambini;
        Listino.PensioniPeriodo trat;
        int codTipo;

        try {    // prova ad eseguire il codice

            // Crea una prenotazione
            modPrenotazione = PrenotazioneModulo.get();
            codPrenotazione = modPrenotazione.query().nuovoRecord();

            if (codPrenotazione > 0) {
                codAzienda = AlbergoModulo.getCodAzienda();
                modPrenotazione.query().registraRecordValore(codPrenotazione, Prenotazione.Cam.azienda.getNome(), codAzienda);
            }// fine del blocco if


            // crea un periodo
            modPeriodo = PeriodoModulo.get();
            codPeriodo = modPeriodo.query().nuovoRecord();

            if (codPeriodo > 0) {
                modPeriodo.query().registraRecordValore(codPeriodo, Periodo.Cam.prenotazione.getNome(), codPrenotazione);
                modPeriodo.query().registraRecordValore(codPeriodo, Periodo.Cam.camera.getNome(), codCamera);
                modPeriodo.query().registraRecordValore(codPeriodo, Periodo.Cam.trattamento.getNome(), codPensione);
                modPeriodo.query().registraRecordValore(codPeriodo, Periodo.Cam.arrivoPrevisto.getNome(), dataInizio);
                modPeriodo.query().registraRecordValore(codPeriodo, Periodo.Cam.partenzaPrevista.getNome(), dataFine);

                codCompo = CameraModulo.getComposizioneStandard(codCamera);
                adulti = CompoCameraModulo.getNumLettiAdulti(codCompo);
                bambini = CompoCameraModulo.getNumLettiBambini(codCompo);
                modPeriodo.query().registraRecordValore(codPeriodo, Periodo.Cam.preparazione.getNome(), codCompo);
                modPeriodo.query().registraRecordValore(codPeriodo, Periodo.Cam.adulti.getNome(), adulti);
                modPeriodo.query().registraRecordValore(codPeriodo, Periodo.Cam.bambini.getNome(), bambini);

                trat = Listino.PensioniPeriodo.get(codPensione);
                
                /* arrivo */
                codTipo = Periodo.TipiAP.getTipoArrivoDefault(trat).getCodice();
                modPeriodo.query().registraRecordValore(codPeriodo, Periodo.Cam.arrivoCon.getNome(), codTipo);

                /* partenza */
                codTipo = Periodo.TipiAP.getTipoPartenzaDefault(trat).getCodice();
                modPeriodo.query().registraRecordValore(codPeriodo, Periodo.Cam.partenzaCon.getNome(), codTipo);

            }// fine del blocco if

            // presenta la scheda
            // se non confermata, cancella tutto
            if (!modPrenotazione.presentaRecord(codPrenotazione)) {
                modPrenotazione.query().eliminaRecord(codPrenotazione);
            }// fine del blocco if

        } catch (Exception
                unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codPrenotazione;
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
         * <p/>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void cambioDataAz(CambioDataEve unEvento) {
            cambioData();
        }
    } // fine della classe interna


    /**
     * Presenta un dialogo per un preventivo.
     * </p>
     */
    private final class AzStatistiche extends AzSpecifica {

        /**
         * Costruttore senza parametri.
         */
        public AzStatistiche() {
            /* rimanda al costruttore della superclasse */
            super();

            /* regola le variabili*/
            super.setChiave("Sviluppo presenze");
            super.setIconaMedia("Torta24");
            super.setTooltip("Sviluppo delle presenze in base alle prenotazioni");
            super.setUsoLista(true);
        }// fine del metodo costruttore senza parametri


        /**
         * actionPerformed, da ActionListener.
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void actionPerformed(ActionEvent unEvento) {
            try { // prova ad eseguire il codice
                sviluppoPresenze();
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }
    } // fine della classe 'interna'


    public Filtro getFiltroAzienda() {
        return filtroAzienda;
    }


    private void setFiltroAzienda(Filtro filtro) {
        this.filtroAzienda = filtro;
    }


    /**
     * Main method.
     *
     * @param argomenti eventuali parametri in ingresso (quasi mai)
     */
    public static void main(String[] argomenti) {
        /* crea una istanza di se stessa */
        Modulo primoModuloProvvisorio = new PrenotazioneModulo();

        /**
         * inizializza tutti i moduli usati da questo modulo <br>
         * lancia il modulo iniziale del programma (obbligatorio) <br>
         */
        Progetto.lancia(argomenti, primoModuloProvvisorio);
    } // fine del metodo main

} // fine della classe
