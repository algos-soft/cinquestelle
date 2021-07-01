package it.algos.albergo.clientealbergo;

import it.algos.albergo.AlbergoLib;
import it.algos.base.azione.AzSpecifica;
import it.algos.base.azione.Azione;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.dialogo.DialogoAnnullaConferma;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Libreria;
import it.algos.base.lista.Lista;
import it.algos.base.lista.ListaBase;
import it.algos.base.messaggio.MessaggioDialogo;
import it.algos.base.messaggio.MessaggioErrore;
import it.algos.base.modello.Modello;
import it.algos.base.modello.ModelloAlgos;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.navigatore.NavigatoreLS;
import it.algos.base.navigatore.info.InfoLista;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.scheda.Scheda;
import it.algos.base.vista.Vista;
import it.algos.gestione.anagrafica.Anagrafica;

import java.awt.event.ActionEvent;

/**
 * Navigatore del gruppo nella scheda del cliente (albergo).
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 5-giu-2007 ore 13.55.52
 */
public final class NavGruppo extends NavigatoreLS {

    /* scheda di riferimento*/
    private Scheda schedaRif;


    /**
     * Costruttore completo con parametri.
     *
     * @param unModulo modulo di riferimento
     */
    public NavGruppo(Modulo unModulo) {
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
        try { // prova ad eseguire il codice
            this.setNomeVista(ClienteAlbergo.Vis.gruppoParentela.toString());

            this.addSchedaCorrente(new GruppoScheda(this.getModulo()));
            this.setUsaPannelloUnico(true);
            this.setUsaFinestraPop(true);
            this.setUsaNuovo(true);
            this.setUsaSelezione(false);
            this.setUsaRicerca(false);
            this.setUsaStampaLista(false);
            this.setRigheLista(4);
            this.setUsaPreferito(true);
            this.setUsaStatusBarLista(false);
            this.setIconePiccole();
            this.setTipoBottoni(Lista.Bottoni.aggiunta);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizia


    /**
     * Inizializzazione dell'oggetto.
     * <p/>
     * Regola le caratteristiche dinamiche in base alla impostazione
     * corrente delle caratteristiche statiche.<br>
     * Viene chiamato dall'inizializzazione del Modulo.<br>
     */
    @Override
    public void inizializza() {
        /* variabili e costanti locali di lavoro */

        try { // prova ad eseguire il codice


            super.inizializza();

            Azione azione = new AzioneVaiMembro();
            this.addAzione(azione);

            this.regolaOrdine();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo inizializza


    /**
     * Regolazioni di ri-avvio.
     * <p/>
     * Metodo chiamato da altre classi ogni volta che questo oggetto deve
     * <i>ripartire</i>, per essere sicuri che sia <i>pulito</i> <br>
     * Viene eseguito tutte le volte che necessita <br>
     * <p/>
     * Avvia ogni componente del navigatore, se presente <br>
     * Avvia il Gestore <br>
     */
    public void avvia() {
        /* variabili e costanti locali di lavoro */
        Azione azione;

        try { // prova ad eseguire il codice

            /* modifica il tooltip del pulsante record preferito (capogruppo) */
            azione = this.getPortaleLista().getAzione(Azione.PREFERITO);
            if (azione != null) {
                azione.setTooltip("Imposta la persona selezionata come Capogruppo");
            }// fine del blocco if

            /* modifica il tooltip del pulsante aggiungi membro */
            azione = this.getPortaleLista().getAzione(Azione.AGGIUNGI_RECORD);
            if (azione != null) {
                azione.setTooltip("Sposta una persona dal suo gruppo in questo gruppo");
            }// fine del blocco if

            /* modifica il tooltip del pulsante aggiungi membro */
            azione = this.getPortaleLista().getAzione(Azione.RIMUOVI_RECORD);
            if (azione != null) {
                azione.setTooltip(
                        "Rimuove la persona selezionata da questo gruppo e la rende Capogruppo");
            }// fine del blocco if

            super.avvia();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Aggiunge una persona al gruppo.
     * <p/>
     *
     * @return il codice del record aggiunto, -1 se non aggiunto.
     */
    @Override
    protected int aggiungiRecord() {
        /* variabili e costanti locali di lavoro */
        DialogoAdd dialogo;
        Modulo mod;
        int codNuovo;
        int codMeStesso;
        int codLinkCapo;

        try { // prova ad eseguire il codice
            dialogo = new DialogoAdd(this);
            dialogo.avvia();

            if (dialogo.isConfermato()) {

                codNuovo = dialogo.getCodice();
                codMeStesso = this.getCodScheda();

                mod = ClienteAlbergoModulo.get();

                /* cambia il link al capogruppo e pone il soggetto non capogruppo */
                codLinkCapo = mod.query().valoreInt(ClienteAlbergo.Cam.linkCapo.get(), codMeStesso);
                mod.query().registraRecordValore(codNuovo,
                        ClienteAlbergo.Cam.linkCapo.get(),
                        codLinkCapo);
                mod.query().registraRecordValore(codNuovo,
                        ClienteAlbergo.Cam.capogruppo.get(),
                        false);
                this.aggiornaLista();

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return 0;
    }


    /**
     * Azione rimuovi record in una <code>Lista</code>.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     */
    @Override
    protected void rimuoviRecord() {
        /* variabili e costanti locali di lavoro */
        int codice = 0;
        boolean continua = true;
        String soggetto;
        MessaggioDialogo messaggio;

        try { // prova ad eseguire il codice

            /* recupera il codice della persona selezionata */
            if (continua) {
                codice = this.getLista().getChiaveSelezionata();
                continua = codice > 0;
            }// fine del blocco if

            /* chiede conferma per la rimozione */
            if (continua) {
                soggetto = this.query().valoreStringa(Anagrafica.Cam.soggetto.get(), codice);
                messaggio = new MessaggioDialogo("Sei sicuro di voler rimuovere " +
                        soggetto +
                        " da questo gruppo?");
                if (!messaggio.isConfermato()) {
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            /**
             * pone il link capogruppo a se stesso e imposta la
             * persona rimossa come capogruppo (unico componente)
             */
            if (continua) {
                this.setCodiceLinkCapo(codice, false);
                this.query().registraRecordValore(codice,
                        ClienteAlbergo.Cam.capogruppo.get(),
                        true);
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    protected boolean modificaRecord() {
        /* variabili e costanti locali di lavoro */
        DialogoEdit dialogo;
        int codice;
        int codParentela;

        try { // prova ad eseguire il codice
            codice = this.getLista().getRecordSelezionato();
            dialogo = new DialogoEdit(codice);
            dialogo.avvia();
            if (dialogo.isConfermato()) {
                codParentela = dialogo.getCodParentela();
                this.query().registra(codice, ClienteAlbergo.Cam.parentela.get(), codParentela);
                this.aggiornaLista();
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return true;
    }


    /**
     * Va alla scheda del membro correntemente selezionato.
     * <p/>
     * Chiude questa scheda registrando eventuali modifiche
     * e apre la scheda del membro selezionato
     */
    private void vaiMembro() {
        /* variabili e costanti locali di lavoro */
        int codice;
        boolean chiusa;

        try {    // prova ad eseguire il codice
            codice = this.getLista().getChiaveSelezionata();
            Scheda scheda = this.getSchedaRif();
            chiusa = scheda.richiediChiusuraNoDialogo(true, false);
            if (chiusa) {
                scheda.avvia(codice);
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Ritorna il pacchetto di informazioni del portale Lista.
     * <p/>
     * Puo' essere sovrascritto dalle sottoclassi per leggere
     * e/o modificare le informazioni di stato prima che vengano
     * utilizzate per sincronizzare la GUI.
     *
     * @param info il pacchetto di informazioni per la sincronizzazione
     */
    @Override
    public InfoLista getInfoLista(InfoLista info) {
        /* variabili e costanti locali di lavoro */
        boolean abilitato;
        int[] codici = null;
        int codLinkCapo;
        boolean esiste = false;
        boolean abilitaPreferito = false;
        Filtro filtro;
        Filtro filtroGruppo;
        Filtro filtroPref;
        int codPref;
        Modulo mod;
        int cod;

        try { // prova ad eseguire il codice
            codLinkCapo = this.getCapoGruppo();

            /* abilitazione pulsante rimuovi riga
            * almeno una riga deve essere selezionata e la selezione
            * non deve contenere il capogruppo */
            abilitato = info.isPossoCancellareRecord();

            if (abilitato) {
                codici = this.getLista().getChiaviSelezionate();
                abilitato = ((codici != null) && (codici.length > 0));
            }// fine del blocco if

            if (abilitato) {
                for (int k = 0; k < codici.length; k++) {
                    if (codici[k] == codLinkCapo) {
                        esiste = true;
                        break;
                    }// fine del blocco if
                } // fine del ciclo for
                info.setPossoCancellareRecord(!esiste);
            }// fine del blocco if

            //@todo - questo non funziona(inizio)
//            if (this.getLista().isRigaSelezionata()) {
//                filtro = new Filtro();
//                filtroGruppo = FiltroFactory.crea(ClienteAlbergo.Cam.linkCapo.get(), codLinkCapo);
//                filtroPref = FiltroFactory.creaVero(Modello.NOME_CAMPO_PREFERITO);
//                filtro.add(filtroGruppo);
//                filtro.add(filtroPref);
//                mod = this.getModulo();
//                codPref = mod.query().valoreChiave(filtro);
//
//                cod = this.getLista().getChiaveSelezionata();
//
//                abilitaPreferito =(cod!=codPref);
//
//                this.getPortaleLista().getAzione(Azione.PREFERITO).getAzione().setEnabled(abilitaPreferito);
//            }// fine del blocco if
            //@todo - questo non funziona(fine)

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return info;
    }


    public void sincronizza() {
        /* variabili e costanti locali di lavoro */
        Azione azione;
        Scheda scheda;
        int chiave;
        boolean abilita = false;

        try { // prova ad eseguire il codice
            super.sincronizza();

            /* se la scheda non è attiva, disabilita Aggiungi Record, Rimuovi Record, Set Capogruppo */
            scheda = this.getSchedaRif();
            if (!scheda.isAttivo()) {
                azione = this.getPortaleLista().getAzione(Azione.AGGIUNGI_RECORD);
                if (azione != null) {
                    azione.setEnabled(false);
                }// fine del blocco if
                azione = this.getPortaleLista().getAzione(Azione.RIMUOVI_RECORD);
                if (azione != null) {
                    azione.setEnabled(false);
                }// fine del blocco if
                azione = this.getPortaleLista().getAzione(Azione.PREFERITO);
                if (azione != null) {
                    azione.setEnabled(false);
                }// fine del blocco if
            }// fine del blocco if

            /**
             * Abilitazione dell'azione Vai a Membro.
             * La scheda deve essere attiva
             * Ci deve essere uno e un solo record selezionato
             * Il record deve essere diverso dal record corrente della scheda
             */
            azione = this.getPortaleLista().getAzione(AzioneVaiMembro.CHIAVE);
            if (azione != null) {
                if (this.getSchedaRif().isAttivo()) {
                    chiave = this.getLista().getChiaveSelezionata();
                    if (chiave > 0) {
                        if (chiave != this.getSchedaRif().getCodice()) {
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
     * Ordine delle colonne.
     * <p/>
     */
    private void regolaOrdine() {
        /* variabili e costanti locali di lavoro */
        Vista vista;
        Campo campoPref;
//        Campo campoOrd;
        Ordine ord;
        ListaBase lista;

        try { // prova ad eseguire il codice

            lista = (ListaBase)this.getLista();
            vista = lista.getVista();

            campoPref = vista.getCampo(Modello.NOME_CAMPO_PREFERITO);

            ord = AlbergoLib.getOrdineGruppo();
            campoPref.setOrdine(ord);

            lista.ordina(campoPref, ListaBase.VERSO_DEL_CAMPO);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Ritorna la connessione da utilizzare per l'accesso al database
     * <p/>
     * Usa la connessione della scheda di riferimento.
     * Se manca, rinvia alla superclasse
     *
     * @return la connessione da utilizzare
     */
    public Connessione getConnessione() {
        /* variabili e costanti locali di lavoro */
        Connessione conn = null;

        try { // prova ad eseguire il codice
            conn = this.getSchedaRif().getConnessione();
            if (conn == null) {
                conn = super.getConnessione();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return conn;
    }


    /**
     * Testo da visualizzare per conferma preferito.
     * <p/>
     *
     * @return il testo del messaggio visualizzato per confermare
     *         l'impostazione del Record Preferito
     */
    protected String getMessaggioRecordPreferito() {
        return "Vuoi impostare la persona come capogruppo?";
    }


    /**
     * Assegna un link capogruppo al record selezionato nel navigatore o a tutti
     * i record visualizzati nel navigatore.
     * <p/>
     *
     * @param codGruppo codice di gruppo da assegnare
     * @param tutti false per solo quello selezionato, true per tutti
     */
    private void setCodiceLinkCapo(int codGruppo, boolean tutti) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        int[] codici;
        Modulo mod;
        Connessione conn;

        try { // prova ad eseguire il codice

            if (tutti) {
                codici = this.getLista().getChiaviVisualizzate();
            } else {
                codici = this.getLista().getChiaviSelezionate();
            }// fine del blocco if-else
            continua = ((codici != null) && (codici.length > 0));

            if (continua) {
                mod = this.getModulo();
                conn = this.getConnessione();
                for (int k = 0; k < codici.length; k++) {
                    mod.query().registraRecordValore(codici[k],
                            ClienteAlbergo.Cam.linkCapo.get(),
                            codGruppo,
                            conn);
                } // fine del ciclo for

                /* rinfresca la lista */
                codGruppo = this.getCodScheda();
                this.regolaFiltro(codGruppo);
                this.aggiornaLista();

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Imposta un record come Preferito.
     * <p/>
     *
     * @param codice del record da rendere Preferito
     */
    protected void setPreferito(int codice) {
        super.setPreferito(codice);
    }


    /**
     * Evento di modifica del record preferito.
     * <p/>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    protected void preferitoModificato(int codice) {
        /* variabili e costanti locali di lavoro */
        Scheda scheda;
        Navigatore nav;
        int capoCorrente;
        int capoNuovo;
        int indCorrente;
        int indNuovo;
        boolean esegui;


        try { // prova ad eseguire il codice


            capoCorrente = this.getCapoGruppo();
            capoNuovo = codice;

            indCorrente = this.query().valoreInt(ClienteAlbergo.Cam.indirizzoInterno.get(),
                    capoCorrente);
            indNuovo = this.query().valoreInt(ClienteAlbergo.Cam.indirizzoInterno.get(), capoNuovo);

            esegui = ((indCorrente != 0) && (indNuovo == 0));
            if (esegui) {
                this.query().registra(capoCorrente,
                        ClienteAlbergo.Cam.indirizzoInterno.get(),
                        indNuovo);
                this.query().registra(capoNuovo,
                        ClienteAlbergo.Cam.indirizzoInterno.get(),
                        indCorrente);
            }// fine del blocco if

            this.setCodiceLinkCapo(codice, true);

            scheda = this.getSchedaRif();

            if (scheda != null) {
                nav = scheda.getPortale().getNavigatore();
                if (nav != null) {
                    nav.aggiornaLista();
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Modifica il filtro.
     * <p/>
     *
     * @param codice del record in scheda
     */
    public void regolaFiltro(int codice) {
        /* variabili e costanti locali di lavoro */
        Filtro filtro;

        try {    // prova ad eseguire il codice

            filtro = ClienteAlbergoModulo.getFiltroGruppo(codice);
            this.setFiltroBase(filtro);
            this.aggiornaLista();

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    private int getCodScheda() {
        /* variabili e costanti locali di lavoro */
        int linkCapoGruppo = 0;
        Scheda schedaRif;

        try { // prova ad eseguire il codice
            schedaRif = this.getSchedaRif();

            if (schedaRif != null) {
                linkCapoGruppo = schedaRif.getCodice();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return linkCapoGruppo;
    }


    /**
     * Ritorna il link capogruppo del record corrente.
     * <p/>
     *
     * @return il link al capogruppo
     */
    private int getCapoGruppo() {
        /* variabili e costanti locali di lavoro */
        int linkCapoGruppo = 0;
        int codScheda;
        Modulo mod;

        try { // prova ad eseguire il codice
            codScheda = this.getCodScheda();
            mod = ClienteAlbergoModulo.get();
            linkCapoGruppo = mod.query().valoreInt(ClienteAlbergo.Cam.linkCapo.get(), codScheda);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return linkCapoGruppo;
    }


    private Scheda getSchedaRif() {
        return schedaRif;
    }


    public void setSchedaRif(Scheda scheda) {
        this.schedaRif = scheda;
    }


    /**
     * Classe 'interna'. </p>
     */
    private final class DialogoAdd extends DialogoAnnullaConferma {

        private Campo campoSoggetto;

        private NavGruppo navGruppo;


        /**
         * Costruttore completo.
         * <p/>
         *
         * @param navigatore di riferimento
         */
        public DialogoAdd(NavGruppo navigatore) {
            super();
            this.setNavGruppo(navigatore);
        } /* fine del metodo costruttore completo */


        /**
         * Regolazioni dinamiche dell'oggetto.
         * <p/>
         * Regola le caratteristiche dinamiche in base alla impostazione
         * corrente delle caratteristiche statiche <br>
         * Metodo invocato dalla classe che crea questo oggetto <br>
         * Viene eseguito una sola volta <br>
         * <p/>
         * Sovrascritto nelle sottoclassi <br>
         */
        @Override
        public void inizializza() {
            /* variabili e costanti locali di lavoro */
            Campo campoSoggetto;
            Filtro filtroCapo;
            Filtro filtroGruppo;
            Filtro filtro;

            try { // prova ad eseguire il codice

                this.setTitolo("Aggiungi una persona al gruppo");

                /**
                 * non deve appartenere già al gruppo al quale sto aggiungendo
                 */
//                filtroCapo = FiltroFactory.creaFalso(ClienteAlbergo.Cam.capogruppo.get());
                filtroGruppo = FiltroFactory.crea(ClienteAlbergo.Cam.linkCapo.get(),
                        Filtro.Op.DIVERSO,
                        getCapoGruppo());
                filtro = new Filtro();
//                filtro.add(filtroCapo);
                filtro.add(filtroGruppo);

                campoSoggetto = CampoFactory.comboLinkSel(Anagrafica.Cam.soggetto);
                this.setCampoSoggetto(campoSoggetto);
                campoSoggetto.setNomeModuloLinkato(ClienteAlbergo.NOME_MODULO);
                campoSoggetto.setNomeCampoValoriLinkato(Anagrafica.Cam.soggetto.get());
                campoSoggetto.setFiltroBase(filtro);
                campoSoggetto.setLarScheda(200);
                campoSoggetto.setUsaNuovo(false);

                campoSoggetto.addColonnaCombo(ClienteAlbergo.Cam.parentela.get());
                campoSoggetto.addColonnaCombo(ModelloAlgos.NOME_CAMPO_PREFERITO);

                this.addCampo(campoSoggetto);

                super.inizializza();

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch


        } /* fine del metodo */


        /**
         * Determina se il dialogo e' confermabile o registrabile.
         * <p/>
         *
         * @return true se confermabile / registrabile
         */
        @Override
        public boolean isConfermabile() {
            /* variabili e costanti locali di lavoro */
            boolean confermabile = false;

            try { // prova ad eseguire il codice
                confermabile = super.isConfermabile();

                if (confermabile) {
                    confermabile = (this.getCodice() > 0);
                }// fine del blocco if

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return confermabile;
        }


        /**
         * Restituisce il codice del record selezionato nel navigatore.
         * <p/>
         *
         * @return il codice del record
         */
        public int getCodice() {
            /* variabili e costanti locali di lavoro */
            int cod = 0;
            Campo campoSoggetto;

            try {    // prova ad eseguire il codice
                campoSoggetto = this.getCampoSoggetto();

                if (campoSoggetto != null) {
                    cod = this.getInt(campoSoggetto.getNomeInterno());
                }// fine del blocco if
            } catch (Exception unErrore) {    // intercetta l'errore
                new Errore(unErrore);
            } // fine del blocco try-catch

            /* valore di ritorno */
            return cod;
        }


        /**
         * Invocato quando si preme il bottone conferma o registra.
         * <p/>
         * Controlla la possibilità di spostare il soggetto selezionato.
         */
        public void confermaRegistra() {
            /* variabili e costanti locali di lavoro */
            int codice;
            NavGruppo nav;
            Connessione conn;
            Modulo modulo;
            int codGruppo;
            boolean capo;
            boolean continua = true;
            Filtro filtro;
            int quanti;
            String mex;
            String soggetto;

            try { // prova ad eseguire il codice
                nav = this.getNavGruppo();
                conn = nav.getConnessione();
                modulo = nav.getModulo();
                codice = this.getCodice();

                capo = modulo.query().valoreBool(ClienteAlbergo.Cam.capogruppo.get(), codice);

                /**
                 * Se il soggetto che voglio aggiungere è un capogruppo e non è l'unico
                 * componente del suo gruppo, non si può toglierlo dal suo gruppo perché
                 * questo resterebbe senza capogruppo.
                 * Bisogna prima impostare un altro capogruppo nel suo gruppo
                 */
                if (capo) {
                    codGruppo = modulo.query().valoreInt(ClienteAlbergo.Cam.linkCapo.get(), codice);
                    if (codGruppo > 0) {
                        filtro = FiltroFactory.crea(ClienteAlbergo.Cam.linkCapo.get(), codGruppo);
                        quanti = modulo.query().contaRecords(filtro, conn);
                        if (quanti > 1) {
                            soggetto = modulo.query().valoreStringa(Anagrafica.Cam.soggetto.get(),
                                    codice);
                            mex =
                                    soggetto +
                                            " non può essere tolto dal suo gruppo perché è il capogruppo.\n" +
                                            "Bisogna prima impostare un capogruppo diverso nel gruppo di " +
                                            soggetto +
                                            ".";
                            new MessaggioErrore(mex);
                            continua = false;
                        }// fine del blocco if
                    }// fine del blocco if
                }// fine del blocco if

                /* se ha passato i controlli invoca il metodo nella superclasse */
                if (continua) {
                    super.confermaRegistra();
                }// fine del blocco if

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

        }


        private Campo getCampoSoggetto() {
            return campoSoggetto;
        }


        private void setCampoSoggetto(Campo campoSoggetto) {
            this.campoSoggetto = campoSoggetto;
        }


        private NavGruppo getNavGruppo() {
            return navGruppo;
        }


        private void setNavGruppo(NavGruppo navGruppo) {
            this.navGruppo = navGruppo;
        }
    } // fine della classe 'interna'


    /**
     * Dialogo di modifica di um membro del gruppo
     * </p>
     */
    private final class DialogoEdit extends DialogoAnnullaConferma {

        /**
         * codice del cliente membro attualmente in fase di editing
         */
        private int codMembro;


        /**
         * Costruttore completo.
         * <p/>
         *
         * @param codMembro del cliente membro in fase di editing
         */
        public DialogoEdit(int codMembro) {
            /* rimanda al costruttore della superclasse */
            super();

            this.setCodMembro(codMembro);

            try { // prova ad eseguire il codice
                /* regolazioni iniziali di riferimenti e variabili */
                this.inizia();
            } catch (Exception unErrore) { // intercetta l'errore
                new Errore(unErrore);
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
            Campo campoPopParentela;
            Modulo modCliente;

            try { // prova ad eseguire il codice

                this.setTitolo("Modifica membro");

                modCliente = ClienteAlbergoModulo.get();
                campoPopParentela = modCliente.getCloneCampo(ClienteAlbergo.Cam.parentela.get());
                this.addCampo(campoPopParentela);

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

        }


        public void avvia() {
            /* variabili e costanti locali di lavoro */
            Modulo modCliente;
            int codCliente;
            int codParentela;

            try { // prova ad eseguire il codice

                /* assegna il codice di parentela al campo */
                modCliente = ClienteAlbergoModulo.get();
                codCliente = this.getCodMembro();
                codParentela = modCliente.query().valoreInt(ClienteAlbergo.Cam.parentela.get(),
                        codCliente);
                this.setCodParentela(codParentela);
                super.avvia();

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch


        }// fine del metodo avvia


        /**
         * Ritorna il codice di parentela attualmente presente nel dialogo.
         * <p/>
         *
         * @return il codice parentela
         */
        private int getCodParentela() {
            /* variabili e costanti locali di lavoro */
            int codParentela = 0;
            Campo campo;
            Object valore;

            try {    // prova ad eseguire il codice
                campo = this.getCampo(ClienteAlbergo.Cam.parentela);
                valore = campo.getValore();
                codParentela = Libreria.getInt(valore);
            } catch (Exception unErrore) {    // intercetta l'errore
                new Errore(unErrore);
            } // fine del blocco try-catch

            /* valore di ritorno */
            return codParentela;
        }


        /**
         * Assegna un codice di parentela al dialogo.
         * <p/>
         *
         * @param codParentela il codice parentela
         */
        private void setCodParentela(int codParentela) {
            /* variabili e costanti locali di lavoro */
            Campo campo;

            try {    // prova ad eseguire il codice
                campo = this.getCampo(ClienteAlbergo.Cam.parentela);
                campo.setValore(codParentela);
            } catch (Exception unErrore) {    // intercetta l'errore
                new Errore(unErrore);
            } // fine del blocco try-catch

        }


        private int getCodMembro() {
            return codMembro;
        }


        private void setCodMembro(int codMembro) {
            this.codMembro = codMembro;
        }
    } // fine della classe 'interna'


    /**
     * Apertura di un nuovo membro del gruppo.
     * <p/>
     * Bottone posto alla seconda riga della toolbar <br>
     */
    private final class AzioneVaiMembro extends AzSpecifica {

        private static final String CHIAVE = "vaiMembro";


        /**
         * Costruttore completo con parametri.
         */
        public AzioneVaiMembro() {
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
            this.setChiave(CHIAVE);
            this.setUsoLista(true);
            super.setTooltip("Va alla scheda del membro selezionato");
            super.setIconaPiccola("frecciaDx16");
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
                vaiMembro();
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }

    }// fine della classe

}// fine della classe
