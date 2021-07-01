/**
 * Title:     ClienteAlbergoScheda
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      3-mag-2004
 */
package it.algos.albergo.clientealbergo;

import it.algos.albergo.AlbergoLib;
import it.algos.albergo.AlbergoModulo;
import it.algos.albergo.clientealbergo.tabelle.tipodocumento.TipoDocumentoModulo;
import it.algos.albergo.presenza.PresenzaModulo;
import it.algos.albergo.storico.Storico;
import it.algos.albergo.tabelle.azienda.Azienda;
import it.algos.albergo.tabelle.azienda.AziendaModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.costante.CostanteModulo;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.messaggio.MessaggioDialogo;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.pagina.Pagina;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFactory;
import it.algos.base.portale.Portale;
import it.algos.gestione.anagrafica.Anagrafica;
import it.algos.gestione.anagrafica.AnagraficaScheda;

import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

/**
 * Scheda specifica di Cliente Albergo </p> Questa classe: <ul> <li> </li> <li> </li> </ul>
 *
 * @author Alberto Colombo, Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 3-mag-2004 ore 13.19.37
 */
public final class ClienteAlbergoScheda extends AnagraficaScheda implements ClienteAlbergo {

    /**
     * nome del set di campi scheda da recuperare
     */
    public static final String SET_SCHEDA = CostanteModulo.SET_BASE_DEFAULT;

    /**
     * navigatore con la lista degli appartenti al gruppo
     */
    private NavGruppo navGruppo;

    /**
     * campo contenente la data di inizio dell'ultimo soggiorno
     */
    private Campo campoUltSoggiorno;

    /**
     * Pannello contenente lo storico del cliente / gruppo
     */
    private PanStorico panStorico;

    /**
     * Pannello di navigazione dello torico del cliente / gruppo
     */
    private Storico storico;

    /**
     * Bottone per aprire lo storico del cliente
     */
    private JButton botStorico;


    /**
     * Costruttore completo.
     *
     * @param unModulo di riferimento per la scheda
     */
    public ClienteAlbergoScheda(Modulo unModulo) {
        /* rimanda al costruttore della superclasse */
        super(unModulo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore


    /**
     * Regolazioni immediate di riferimenti e variabili. <br> Metodo chiamato direttamente dal
     * costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */
        Modulo mod;
        NavGruppo nav;

        try { // prova ad eseguire il codice

            /* rimuove eventuale dimensione fissa preimopstata */
            this.setPreferredSize(null);

            /* crea il navigatore per il gruppo */
            mod = this.getModulo();
            nav = this.setNavGruppo(new NavGruppo(mod));
            nav.setSchedaRif(this);

            /* crea e registra il campo Ultimo Soggiorno */
            Campo campo;
            campo = CampoFactory.data("ultimo soggiorno");
            campo.setAbilitato(false);
            campo.avvia();
            this.setCampoUltSoggiorno(campo);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo inizia


    /**
     * Regolazioni dinamiche dell'oggetto.
     * <p/>
     * Regola le caratteristiche dinamiche in base alla impostazione corrente delle caratteristiche
     * statiche <br> Metodo invocato dalla classe che crea questo oggetto <br> Viene eseguito una
     * sola volta <br>
     * <p/>
     * Sovrascritto nelle sottoclassi <br>
     */
    @Override
    public void inizializza() {
        /* variabili e costanti locali di lavoro */


        try { // prova ad eseguire il codice

            super.inizializza();

            /* aggiunge il bottone Storico alla scheda */
            JButton bot = AlbergoLib.addBotInfoScheda(this);
            bot.addActionListener(new AzInfoStorico());
            this.setBotStorico(bot);


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regolazioni di ri-avvio.
     * <p/>
     * Metodo chiamato da altre classi ogni volta che questo oggetto deve <i>ripartire</i>, per
     * essere sicuri che sia <i>pulito</i> <br> Viene eseguito tutte le volte che necessita <br>
     * <p/>
     * Recupera il db <br> Chiede al db i dati del record <br> Regola i dati della scheda <br>
     */
    @Override
    public void avvia(int codice) {
        /* variabili e costanti locali di lavoro */
        int codCapo;
        NavGruppo nav;
        Campo campo;
        CLIndirizzo logicaIndirizzo;
        boolean capoGruppo;
        Date data;

        try { // prova ad eseguire il codice

            super.avvia(codice);

            /**
             * assegna il codice cliente e il codice capogruppo alla logica indirizzo.
             * - se è capogruppo assegna Zero,
             * - se non è capogruppo assegna il codice capogruppo
             */
            campo = this.getCampo(ClienteAlbergo.Cam.indirizzoInterno);
            logicaIndirizzo = (CLIndirizzo)campo.getCampoLogica();
            capoGruppo = this.getBool(ClienteAlbergo.Cam.capogruppo.get());
            if (capoGruppo) {
                codCapo = 0;
            } else {
                codCapo = this.getInt(ClienteAlbergo.Cam.linkCapo.get());
            }// fine del blocco if-else
            logicaIndirizzo.setCodCliente(codice);
            logicaIndirizzo.setCodCapoGruppo(codCapo);
            campo.avvia();

            /* navigatore per il gruppo */
            nav = this.getNavGruppo();
            nav.regolaFiltro(codice);

            /* aggiorna la data ultimo soggiorno */
            data = PresenzaModulo.getDataUltimoSoggiorno(codice);
            this.getCampoUltSoggiorno().setValore(data);


            /* avvia il pannello dello storico */
            PanStorico panStor = this.getPanStorico();
            if (panStor != null) {
                panStor.avvia(codice);
            }// fine del blocco if

            Storico stor = this.getStorico();
            if (stor != null) {
                stor.avvia(codice);
            }// fine del blocco if


//            ToolBar tb = this.getPortale().getToolBar();
//            Jumper jumper = new Jumper(Layout.ORIENTAMENTO_ORIZZONTALE);
//            JToolBar jtb = tb.getToolBar();
//            jtb.setBorderPainted(true);
//            JPanel pan = jumper.getPanFisso();
//            jtb.add(pan);


            /**
             * == Patch specifica per HPDR ==
             * Se l'azienda è HPDR (B) il campo Note Personali
             * non è visibile
             */
            boolean visibile = true;
            int codAz = AlbergoModulo.getCodAzienda();
            String sigla = AziendaModulo.get().query().valoreStringa(Azienda.Cam.sigla.get(), codAz);
            if (sigla.equals("HPDR")) {
                visibile=false;
            }// fine del blocco if
            Campo campoNote = this.getCampo(ClienteAlbergo.Cam.notePersonali);
            campoNote.setVisibile(visibile);
            /** == end patch HPDR == */

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Crea le pagine della scheda.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     */
    @Override
    protected void creaPagine() {
        try {    // prova ad eseguire il codice
            this.creaPaginaGenerale();
            this.creaPaginaDocumento();
            this.creaPaginaNote();

//            this.creaPaginaStorico();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    } /* fine del metodo */


    /**
     * Crea il pannello superiore con i dati generali.
     * <p/>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @return il pannello creato
     */
    @Override
    protected Pannello creaPanTop() {
        /* variabili e costanti locali di lavoro */
        Pannello pan = null;

        try {    // prova ad eseguire il codice

            pan = PannelloFactory.orizzontale(this);
            pan.add(ClienteAlbergo.Cam.checkEvidenza);
            pan.add(Anagrafica.Cam.privatosocieta);
            pan.add(Anagrafica.Cam.consensoPrivacy);
            pan.add(Anagrafica.Cam.sesso);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return pan;
    }


    /**
     * Crea il pannello centrale.
     * <p/>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @return il pannello creato
     */
    @Override
    protected Pannello creaPanCentrale() {
        /* variabili e costanti locali di lavoro */
        Pannello pan = null;
        String indirizzo;
        Portale gruppo;
        Navigatore nav;

        try { // prova ad eseguire il codice

            pan = PannelloFactory.orizzontale(this);
            pan.setGapFisso(60);
            pan.add(ClienteAlbergo.Cam.indirizzoInterno.get());
            pan.add(this.creaPortaleGruppo());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return pan;
    }


    /**
     * Crea il pannello inferiore.
     * <p/>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @return il pannello creato
     */
    @Override
    protected Pannello creaPanBottom() {
        /* variabili e costanti locali di lavoro */
        Pannello pan = null;

        try { // prova ad eseguire il codice

            pan = PannelloFactory.orizzontale(this);
            pan.setGapFisso(47);
            pan.add(this.creaPanContatti());
            pan.add(this.creaPanLingua());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return pan;
    }


    /**
     * Portale dei gruppi familiari.
     * <p/>
     *
     * @return portale
     */
    private Portale creaPortaleGruppo() {
        /* variabili e costanti locali di lavoro */
        Portale gruppo = null;
        boolean continua;
        Navigatore nav;

        try { // prova ad eseguire il codice

            nav = this.getNavGruppo();
            continua = (nav != null);

            if (continua) {
                nav.avvia(); // per essere sicuro che le dimensioni siano quelle definitive
                gruppo = nav.getPortaleNavigatore();
                continua = (gruppo != null);
            }// fine del blocco if

            if (continua) {
                gruppo.getPortale().bloccaLarMax();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return gruppo;
    }


    /**
     * Crea il pannello contatti.
     * <p/>
     *
     * @return il pannello creato
     */
    protected Pannello creaPanContatti() {
        /* variabili e costanti locali di lavoro */
        Pannello pan = null;
        Pannello panContatti;
        Pannello panTelefoni;
        Pannello panFaxMail;
        Pannello panCompleto;
        Pannello panNote;
        int gap = 10;

        try {    // prova ad eseguire il codice

            pan = PannelloFactory.verticale(this);

            if (USA_FAX) {

                panContatti = PannelloFactory.verticale(this);
                panContatti.creaBordo("contatti");
                panContatti.setGapPreferito(2);

                panTelefoni = PannelloFactory.orizzontale(this);
                panTelefoni.setUsaGapFisso(true);
                panTelefoni.setGapPreferito(gap);
                panTelefoni.add(Anagrafica.Cam.telefono);
                panTelefoni.add(Anagrafica.Cam.cellulare);
                panTelefoni.add(ClienteAlbergo.Cam.telUfficio);

                panFaxMail = PannelloFactory.orizzontale(this);
                panFaxMail.setUsaGapFisso(true);
                panFaxMail.setGapPreferito(gap);
                panFaxMail.add(Anagrafica.Cam.fax);
                panFaxMail.add(Anagrafica.Cam.email);

                panContatti.add(panTelefoni);
                panContatti.add(panFaxMail);

                panNote = PannelloFactory.verticale(this);
                panNote.creaBordo("note");
                panNote.add(Anagrafica.Cam.note);

                panCompleto = PannelloFactory.verticale(this);
                panCompleto.setGapPreferito(5);
                panCompleto.add(panContatti);
                panCompleto.add(panNote);

                pan.add(panCompleto);

            } else {
                panContatti = PannelloFactory.orizzontale(this);
                panContatti.add(Set.telcellmail);
                pan.add(panContatti);
            }// fine del blocco if-else

            pan.add(Anagrafica.Cam.contatti.get());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return pan;
    }


    /**
     * Crea il pannello Lingua.
     * <p/>
     *
     * @return il pannello creato
     */
    private Pannello creaPanLingua() {
        /* variabili e costanti locali di lavoro */
        Pannello pan = null;

        try {    // prova ad eseguire il codice
            pan = PannelloFactory.verticale(this);
            pan.setGapPreferito(10);
            pan.creaBordo();
            pan.add(ClienteAlbergo.Cam.parentela);
            pan.add(ClienteAlbergo.Cam.checkPosta);
            pan.add(ClienteAlbergo.Cam.lingua);
            pan.add(ClienteAlbergo.Cam.checkFamiglia);
            pan.add(this.getCampoUltSoggiorno());

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return pan;
    }


    /**
     * Crea la pagina con i dati per la polizia.
     * <p/>
     */
    protected void creaPaginaDocumento() {
        /* variabili e costanti locali di lavoro */
        Pagina pag;
        Pannello pan;

        try {    // prova ad eseguire il codice

            /* crea la pagina Generale */
            pag = super.addPagina("documenti");

            pan = PannelloFactory.orizzontale(this);
            pan.add(ClienteAlbergo.Cam.tipoDoc);
            pan.add(ClienteAlbergo.Cam.numDoc);
            pag.add(pan);

            pan = PannelloFactory.orizzontale(this);
            pan.add(ClienteAlbergo.Cam.autoritaDoc);
            pan.add(ClienteAlbergo.Cam.dataDoc);
            pan.add(ClienteAlbergo.Cam.scadenzaDoc);
            pag.add(pan);

            pan = PannelloFactory.orizzontale(this);
            pan.add(ClienteAlbergo.Cam.luogoNato);
            pan.add(ClienteAlbergo.Cam.dataNato);
            pag.add(pan);


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Crea la pagina note.
     * <p/>
     */
    protected void creaPaginaNote() {
        /* variabili e costanti locali di lavoro */
        Pagina pag;

        try {    // prova ad eseguire il codice

            /* crea la pagina Generale */
            pag = super.addPagina("note");

            pag.add(ClienteAlbergo.Cam.noteprep);
            pag.add(ClienteAlbergo.Cam.notePersonali);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


//    /**
//     * Crea la pagina Storico Presenze.
//     * <p/>
//     */
//    protected void creaPaginaStorico() {
//        /* variabili e costanti locali di lavoro */
//        Pagina pag;
//        PanStorico panStorico;
//
//        try {    // prova ad eseguire il codice
//
//            pag = super.addPagina("storico");
//            panStorico = new PanStorico();
//            this.setPanStorico(panStorico);
//            pag.add(panStorico);
//
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//    }


    /**
     * Crea la pagina Storico Cliente.
     * <p/>
     */
    protected void creaPaginaStorico() {
        /* variabili e costanti locali di lavoro */
        Pagina pag;
        Storico storico;

        try {    // prova ad eseguire il codice

            pag = super.addPagina("storico");
            storico = new Storico();
            this.setStorico(storico);
            pag.add(storico);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regola il campo soggetto.
     * <p/>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    @Override
    protected String regolaSoggetto() {
        /* variabili e costanti locali di lavoro */
        String soggetto = "";
        NavGruppo navGruppo;
        Modulo mod;
        int cod;

        try { // prova ad eseguire il codice

            soggetto = super.regolaSoggetto();

            /* navigatore per il gruppo */
            navGruppo = this.getNavGruppo();

            cod = this.getCodice();
            mod = this.getModulo();

            mod.query().registraRecordValore(cod, Anagrafica.Cam.soggetto.get(), soggetto);
            navGruppo.regolaFiltro(cod);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return soggetto;
    }


    /**
     * Controlla se la scheda e' valida. </p> Tutti i campi visibili devono avere un valore valido
     * (o  vuoto). I campi obbligatori non devono essere vuoti.
     *
     * @return true se i campi sono tutti validi <br>
     */
    public boolean isValida() {
        boolean valida = false;

        try { // prova ad eseguire il codice
            valida = super.isValida();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valida;
    }


    public void sincronizza() {
        /* variabili e costanti locali di lavoro */
        int codCliente;
        JButton bot;

        super.sincronizza();

        try { // prova ad eseguire il codice

            /**
             * abilitazione del bottone Storico solo se c'è un codice cliente
             */
            codCliente = this.getCodice();
            bot = this.getBotStorico();
            bot.setEnabled(codCliente != 0);


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
        super.eventoMemoriaModificata(campo);

        if (this.isCampo(campo, ClienteAlbergo.Cam.parentela)) {
            this.getNavGruppo().aggiornaLista();
        }// fine del blocco if

        /* se modifico il tipo documento ricalcola l'autorità
         * emittente e la scadenza */
        if (this.isCampo(campo, ClienteAlbergo.Cam.tipoDoc)) {
            this.chkAutorita();
            this.chkScadenza();
        }// fine del blocco if

    }


    @Override
    protected void eventoUscitaCampoModificato(Campo campo) {
        /* variabili e costanti locali di lavoro */
        try { // prova ad eseguire il codice

            super.eventoUscitaCampoModificato(campo);

            /* se modifico la data di emissione ricalcola la scadenza */
            if (this.isCampo(campo, ClienteAlbergo.Cam.dataDoc)) {
                this.chkScadenza();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Calcola la data di scadenza del documento.
     * <p/>
     * Ci deve essere tipo di documento e data del rilascio.
     * Se la data di scadenza è già esistente chiede se la si vuole ricalcolare.
     * Calcola la data di scadenza e assegna il valore al campo.
     */
    private void chkScadenza() {
        /* variabili e costanti locali di lavoro */
        int tipoDoc;
        boolean continua;
        Date dataRilascio = null;
        Date dataScadenza;
        Date dataScadenzaNew = null;
        MessaggioDialogo messaggio;

        try {    // prova ad eseguire il codice

            /* controlla che esista il tipo di documento */
            tipoDoc = this.getInt(ClienteAlbergo.Cam.tipoDoc.get());
            continua = (tipoDoc > 0);

            /* controlla che esista la data del rilascio */
            if (continua) {
                dataRilascio = this.getData(ClienteAlbergo.Cam.dataDoc.get());
                continua = (Lib.Data.isValida(dataRilascio));
            }// fine del blocco if

            /* calcola la nuova data di scadenza
            * se uguale alla data del rilascio gli anni non sono stati impostati in tabella
            * e non procede ulteriormente */
            if (continua) {
                dataScadenzaNew = TipoDocumentoModulo.getDataScadenza(tipoDoc, dataRilascio);
                continua = !dataScadenzaNew.equals(dataRilascio);
            }// fine del blocco if

            /* se esiste già la data di scadenza chiede se la si vuole ricalcolare */
            if (continua) {
                dataScadenza = this.getData(ClienteAlbergo.Cam.scadenzaDoc.get());
                if (Lib.Data.isValida(dataScadenza)) {
                    messaggio = new MessaggioDialogo("Vuoi ricalcolare la data di scadenza?");
                    continua = messaggio.isConfermato();
                }// fine del blocco if
            }// fine del blocco if

            /* ricalcola la scadenza e assegna il valore */
            if (continua) {
                this.setValore(ClienteAlbergo.Cam.scadenzaDoc.get(), dataScadenzaNew);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Assegna l'autorità emittente per il tipo di documento corrente.
     * <p/>
     * Determina l'autorità emittente e assegna il valore al campo.
     */
    private void chkAutorita() {
        /* variabili e costanti locali di lavoro */
        int tipoDoc;
        boolean continua;
        int codAutorita = 0;
        int codAutoritaEsistente;
        MessaggioDialogo messaggio;

        try {    // prova ad eseguire il codice

            /* controlla che esista il tipo di documento */
            tipoDoc = this.getInt(ClienteAlbergo.Cam.tipoDoc.get());
            continua = (tipoDoc > 0);

            /* recupera l'autorità emittente */
            if (continua) {
                codAutorita = TipoDocumentoModulo.getAutoritaEmittente(tipoDoc);
                continua = codAutorita != 0;
            }// fine del blocco if

            /* se esiste già l'autorità emittente chiede se la si vuole sostituire */
            if (continua) {
                codAutoritaEsistente = this.getInt(ClienteAlbergo.Cam.autoritaDoc.get());
                if (codAutoritaEsistente != 0) {
                    messaggio = new MessaggioDialogo("Vuoi riassegnare l'autorità emittente?");
                    continua = messaggio.isConfermato();
                }// fine del blocco if
            }// fine del blocco if

            /* assegna il valore */
            if (continua) {
                this.setValore(ClienteAlbergo.Cam.autoritaDoc.get(), codAutorita);
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }


    protected String getTestoRiferimento() {
        /* variabili e costanti locali di lavoro */
        String testo = "";
        Object valore;
        try { // prova ad eseguire il codice
            valore = this.getValore(Anagrafica.Cam.soggetto.get());
            testo = Libreria.getString(valore);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        return testo;
    }


    private NavGruppo getNavGruppo() {
        return navGruppo;
    }


    private NavGruppo setNavGruppo(NavGruppo navGruppo) {
        this.navGruppo = navGruppo;
        return this.getNavGruppo();
    }


    private Campo getCampoUltSoggiorno() {
        return campoUltSoggiorno;
    }


    private void setCampoUltSoggiorno(Campo campo) {
        this.campoUltSoggiorno = campo;
    }


    private PanStorico getPanStorico() {
        return panStorico;
    }


    private void setPanStorico(PanStorico panStorico) {
        this.panStorico = panStorico;
    }


    private Storico getStorico() {
        return storico;
    }


    private void setStorico(Storico storico) {
        this.storico = storico;
    }


    private JButton getBotStorico() {
        return botStorico;
    }


    private void setBotStorico(JButton botStorico) {
        this.botStorico = botStorico;
    }


    /**
     * Azione Informazioni Storiche
     * </p>
     */
    private final class AzInfoStorico implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            /* variabili e costanti locali di lavoro */
            int codice;

            try { // prova ad eseguire il codice
                codice = getCodice();
                if (codice > 0) {
                    ClienteAlbergoModulo.showStorico(codice);
                }// fine del blocco if
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

        }
    } // fine della classe 'interna'

}// fine della classe
