/**
 * Copyright(c): 2006
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 17 mag 2006
 */

package it.algos.gestione.anagrafica;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.layout.Layout;
import it.algos.base.libreria.Lib;
import it.algos.base.messaggio.MessaggioAvviso;
import it.algos.base.modulo.Modulo;
import it.algos.base.pagina.Pagina;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFactory;
import it.algos.base.scheda.SchedaBase;
import it.algos.base.wrapper.EstrattoBase;
import it.algos.gestione.indirizzo.Indirizzo;
import it.algos.gestione.indirizzo.IndirizzoModulo;

import java.awt.Dimension;
import java.util.ArrayList;

/**
 * Presentazione grafica di un singolo record di Anagrafica.
 * </p>
 * Questa classe concreta: <ul>
 * <li> Crea le pagine del <code>Libro</code> che vengono visualizzate nel
 * PortaleScheda del Navigatore </li>
 * <li> Ogni pagina viene creata con un set di campi o aggiungendo i singoli campi </li>
 * <li> I campi vengono posizionati in automatico oppure singolarmente </li>
 * <li> Se uno stesso campo viene posizionato su pi&ugrave; pagine, risulter&agrave;
 * visibile solo nell'ultima pagina in cui viene posizionato </li>
 * <li> Se il <code>Modello>/code> prevede il campo note, crea la pagina note </li>
 * <li> Se il flag programma &egrave; attivo, crea la pagina programmatore </li>
 * <ul/>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 17 mag 2006
 */
public class AnagraficaScheda extends SchedaBase implements Anagrafica {

    private static final String TITOLO_PAGINA_GENERALE = "generale";

    private static final String TITOLO_PAGINA_CONTABILE = "contabile";

    private Pannello panPS;

    private Pannello panCFPI;

    /**
     * flag di attivazione segnalazione omonimi su nuovo record
     */
    private boolean controlloOmonimi;

    /**
     * flag per usare bordi nei set di campi
     */
    protected final static boolean USA_BORDO = true;

    /**
     * flag per usare il campo fax
     */
    protected final static boolean USA_FAX = true;

    /**
     * flag per posizionare codice fiscale e partita iva nella pagina contabilità
     */
    protected final static boolean CF_PI_CONTABILITA = true;


    /**
     * Costruttore completo.
     *
     * @param unModulo di riferimento per la scheda
     */
    public AnagraficaScheda(Modulo unModulo) {
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
     * Regolazioni immediate di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* scheda a dimensione fissa */
        this.setPreferredSize(new Dimension(720, 600));

        /* controllo omonimi su nuovo record attivato di default */
        this.setControlloOmonimi(true);
    }


    /**
     * Regolazioni di ri-avvio.
     * <p/>
     * Metodo chiamato da altre classi ogni volta che questo oggetto deve
     * <i>ripartire</i>, per essere sicuri che sia <i>pulito</i> <br>
     * Viene eseguito tutte le volte che necessita <br>
     * <p/>
     * Recupera il db <br>
     * Chiede al db i dati del record <br>
     * Regola i dati della scheda <br>
     */
    @Override
    public void avvia(int codice) {

        try { // prova ad eseguire il codice

            super.avvia(codice);

            /* se nuovo record cancella il soggetto se presente (creato da combo) */
            if (this.isNuovoRecord()) {
                this.setValore(Cam.soggetto.get(), "");
            }// fine del blocco if

            this.regolaPanPS();
            this.regolaFuoco();

            /* risincronizza dopo lo switch del pannello PS */
            this.sincronizza();

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
            this.creaPaginaContabile();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    } /* fine del metodo */


    /**
     * Crea la pagina Generale.
     * <p/>
     */
    protected void creaPaginaGenerale() {
        /* variabili e costanti locali di lavoro */
        Pagina pag;

        try {    // prova ad eseguire il codice

            /* crea la pagina Generale */
            pag = super.addPagina(TITOLO_PAGINA_GENERALE);
            pag.add(this.creaPanTop());  // pannello generale superiore
            pag.add(this.creaPanPS());   // pannello centrale Privato/Società
            pag.add(this.creaPanCentrale()); // pannello centrale indirizzo
            pag.add(this.creaPanBottom()); // pannello inferiore

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Costruisce la pagina contabile.
     * <br>
     * Costruisce la pagina vuota, col titolo <br>
     * Aggiunge i varii campi, presi dal set <i>normale</i> di cliente:<ul>
     * <li> Campo banca propria</li>
     * <li> Campo banca esterna</li>
     * <li> Campo link pagamento</li>
     * <li> Campo link IVA</li>
     * <li> Campo codice fiscale</li>
     * <li> Campo partita IVA</li>
     * </ul>
     *
     * @see SchedaBase#addPagina
     * @since 8-5-04
     */
    protected void creaPaginaContabile() {
        /* variabili e costanti locali di lavoro */
        Pagina pag;
        Pannello pan;

        try { // prova ad eseguire il codice

            pag = super.addPagina(TITOLO_PAGINA_CONTABILE);

            /* pannello con pagamento / iva / opzioni */
            pan = PannelloFactory.orizzontale(this);
            pan.add(this.creaPanPagIva());
            pan.add(this.creaPanOpzioni());

            pag.add(pan); // pannello superiore
            if (CF_PI_CONTABILITA) {
                pag.add(this.creaPanCFPI());   // pannello CF/Partita Iva
            }// fine del blocco if
            pag.add(Cam.contibanca.get());  // navigatore conti

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
    protected Pannello creaPanTop() {
        /* variabili e costanti locali di lavoro */
        Pannello pan = null;

        try {    // prova ad eseguire il codice

            pan = PannelloFactory.orizzontale(this);
            pan.add(Anagrafica.Cam.categoria);
            pan.add(Anagrafica.Cam.privatosocieta);
            pan.add(Anagrafica.Cam.consensoPrivacy);
            pan.add(Anagrafica.Cam.sesso);
//            pan.add(Set.testata);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return pan;
    }


    /**
     * Crea e registra il pannello centrale che contiene
     * alternativamente il pannello Privato o il pannello Societa'
     * <p/>
     *
     * @return il pannello creato
     */
    protected Pannello creaPanPS() {
        /* variabili e costanti locali di lavoro */
        Pannello pan = null;

        try {    // prova ad eseguire il codice

            pan = PannelloFactory.orizzontale(this);
            pan.creaBordo();
            pan.setPreferredSize(655, 70);
            pan.bloccaDim();
            this.setPanPS(pan);

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
    protected Pannello creaPanCentrale() {
        /* variabili e costanti locali di lavoro */
        Pannello pan = null;

        try {    // prova ad eseguire il codice

            pan = PannelloFactory.orizzontale(this);
            pan.add(Cam.indirizzi.get());

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
    protected Pannello creaPanBottom() {
        /* variabili e costanti locali di lavoro */
        Pannello pan = null;

        try { // prova ad eseguire il codice

            pan = PannelloFactory.orizzontale(this);
            pan.setGapFisso(20);
            pan.add(this.creaPanContatti());
            pan.add(this.creaPanNote());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return pan;
    }


    /**
     * Crea e registra il pannello che contiene
     * alternativamente il codice fiscale o la partita iva'
     * <p/>
     *
     * @return il pannello creato
     */
    public Pannello creaPanCFPI() {
        /* variabili e costanti locali di lavoro */
        Pannello pan = null;

        try {    // prova ad eseguire il codice

            pan = PannelloFactory.orizzontale(this);
            this.setPanCFPI(pan);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return pan;
    }


    /**
     * Crea il pannello specifico per Privato.
     * <p/>
     *
     * @return il pannello creato
     */
    protected Pannello creaPanPrivato() {
        /* variabili e costanti locali di lavoro */
        Pannello pan = null;
        Pannello panSopra;
        Pannello panSotto;

        try {    // prova ad eseguire il codice
            pan = PannelloFactory.verticale(this);

            panSopra = PannelloFactory.orizzontale(this);
            panSopra.add(Set.privato.toString());
            pan.add(panSopra);

            if (!CF_PI_CONTABILITA) {
                panSotto = PannelloFactory.orizzontale(this);
                panSotto.add(Cam.codFiscale.get());
                pan.add(panSotto);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return pan;

    }


    /**
     * Crea il pannello specifico per Societa'.
     * <p/>
     *
     * @return il pannello creato
     */
    private Pannello creaPanSocieta() {
        /* variabili e costanti locali di lavoro */
        Pannello pan = null;
        Pannello panSopra;

        try {    // prova ad eseguire il codice

            pan = PannelloFactory.verticale(this);

            panSopra = PannelloFactory.orizzontale(this);
            panSopra.add(Set.societa.toString());
            pan.add(panSopra);

            if (!CF_PI_CONTABILITA) {
                pan.add(Cam.partitaIva.get());
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return pan;
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
        Pannello pan2;
        Pannello pan3;
        Pannello pan4;

        try {    // prova ad eseguire il codice

            pan = PannelloFactory.verticale(this);
            pan.setDimFissa(385,250);
            if (USA_BORDO) {
                pan.creaBordo("contatti");
            }// fine del blocco if

            if (USA_FAX) {
                pan2 = PannelloFactory.orizzontale(this);

                pan3 = PannelloFactory.verticale(this);
                pan3.add(Cam.telefono);
                pan3.add(Cam.fax);

                pan4 = PannelloFactory.verticale(this);
                pan4.add(Cam.cellulare);
                pan4.add(Cam.email);

                pan2.add(pan3);
                pan2.add(pan4);
                pan.add(pan2);
            } else {
                pan2 = PannelloFactory.orizzontale(this);
                pan2.add(Set.telcellmail);
                pan.add(pan2);
            }// fine del blocco if-else

            pan.add(Cam.contatti.get());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return pan;
    }


    /**
     * Crea il pannello note.
     * <p/>
     *
     * @return il pannello creato
     */
    private Pannello creaPanNote() {
        /* variabili e costanti locali di lavoro */
        Pannello pan = null;

        try {    // prova ad eseguire il codice

            pan = PannelloFactory.verticale(this);
            pan.add(Anagrafica.Cam.note);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return pan;
    }


    /**
     * Crea il pannello pagamento / iva.
     * <p/>
     *
     * @return il pannello creato
     */
    private Pannello creaPanPagIva() {
        /* variabili e costanti locali di lavoro */
        Pannello pan = null;

        try {    // prova ad eseguire il codice

            pan = PannelloFactory.verticale(this);
            pan.creaBordo();
            pan.add(Cam.pagamento.get());
            pan.add(Cam.iva.get());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return pan;
    }


    /**
     * Crea il pannello opzioni di fatturazione.
     * <p/>
     *
     * @return il pannello creato
     */
    private Pannello creaPanOpzioni() {
        /* variabili e costanti locali di lavoro */
        Pannello pan = null;
        Pannello panRA;

        try {    // prova ad eseguire il codice

            panRA = PannelloFactory.orizzontale(this);
            panRA.setAllineamento(Layout.ALLINEA_BASSO);
            panRA.add(Cam.applicaRA.get());
            panRA.add(Cam.percRA.get());

            pan = PannelloFactory.orizzontale(this);
            pan.creaBordo();
            pan.add(Cam.applicaRivalsa.get());
            pan.add(panRA);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return pan;
    }


    /**
     * Mostra la sezione privato o società.
     * <p/>
     * Sostituisce il contenuto del pannello centrale
     * in funzione del tipo di anagrafica corrente <br>
     */
    protected void regolaPanPS() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Pannello panPS;
        Pannello panCFPI;
        Pannello pan;
        boolean privato;
        Campo unCampo;

        try {    // prova ad eseguire il codice

            /* recupera il pannello switch */
            panPS = this.getPanPS();
            continua = (panPS != null);

            if (continua) {
                /* valore */
                privato = this.isPrivato();

                /* crea il pannello da visualizzare */
                if (privato) {
                    pan = this.creaPanPrivato();
                } else {
                    pan = this.creaPanSocieta();
                }// fine del blocco if-else

                /* aggiunge il pannello creato al pannello switch */
                panPS.removeAll();
                panPS.add(pan);

                unCampo = this.getCampo(Cam.sesso);
                if (unCampo != null) {
                    unCampo.setVisibile(privato);
                }// fine del blocco if
                unCampo = this.getCampo(Cam.consensoPrivacy);
                if (unCampo != null) {
                    unCampo.setVisibile(privato);
                }// fine del blocco if

                if (CF_PI_CONTABILITA) {
                    /* recupera il pannello switch */
                    panCFPI = this.getPanCFPI();
                    if (panCFPI != null) {
                        panCFPI.removeAll();
                        if (this.isPrivato()) {
                            panCFPI.add(Cam.codFiscale);
                        } else {
                            panCFPI.add(Cam.partitaIva);
                        }// fine del blocco if-else
                    }// fine del blocco if
                }// fine del blocco if

                this.repaint();
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Metodo eseguito quando il valore memoria di un campo cambia.
     * <p/>
     * Sovrascritto dalle sottoclassi
     */
    @Override
    protected void eventoMemoriaModificata(Campo campo) {

        super.eventoMemoriaModificata(campo);

        try { // prova ad eseguire il codice

            if (campo.equals(this.getCampo(Cam.privatosocieta.get()))) {
                this.regolaPanPS();
                this.regolaFuoco();
            }// fine del blocco if

            /**
             * Se il campo Applica r.a. viene posto su Applica, e la percentuale
             * corrente di r.a. è = 0, assegna la percentuale di r.a. da default
             */
            if (campo.equals(this.getCampo(Cam.applicaRA.get()))) {
                int valore = (Integer)campo.getValore();
                ValoriOpzione elem = ValoriOpzione.getElemento(valore);
                if (elem != null) {
                    if (elem.equals(ValoriOpzione.si)) {
                        if ((Double)this.getValore(Cam.percRA.get()) == 0) {
                            //todo recuperare da preferenze (anagrafica o fattura?)
                            this.setValore(Cam.percRA.get(), 0.2);
                        }// fine del blocco if
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Metodo eseguito quando un campo modificato perde il fuoco.
     * <p/>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @param campo interessato
     */
    @Override
    protected void eventoUscitaCampoModificato(Campo campo) {
        try { // prova ad eseguire il codice
            super.eventoUscitaCampoModificato(campo);

            /* se è nome o cognome regola le maiuscole */
            if (super.isCampo(campo, Cam.nome, Cam.cognome)) {
                this.regolaMaiuscola(campo);
            }// fine del blocco if

            /* se è nome o cognome o ragione sociale regola il soggetto  */
            if (super.isCampo(campo, Cam.nome, Cam.cognome, Cam.ragSociale)) {
                this.regolaSoggetto();
            }// fine del blocco if

            /* se è nome o cognome o ragione sociale, ed è un nuovo record, controlla omonimi */
            if (super.isCampo(campo, Cam.nome, Cam.cognome, Cam.ragSociale)) {
                if (this.isControlloOmonimi()) {
                    if (this.isNuovoRecord()) {
                        this.chkOmonimi();
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Forza maiuscola la prima lettera del campo.
     * <p/>
     * Diverse possibilità di regolare la -forzatura- della maiuscola:<ul>
     * <li> Sempre </li>
     * <li> Mai </li>
     * <li> Con un flag generale di preferenza </li>
     * <li> Solo se il campo in memoria è vuoto </li>
     * <li> Solo se il campo sul db è vuoto </li>
     * <li> Solo se siamo in un nuovo record </li>
     * <ul/>
     */
    private void regolaMaiuscola(Campo campo) {
        /* variabili e costanti locali di lavoro */
        Prima prima = Prima.nuovo;

        try {    // prova ad eseguire il codice

            switch (prima) {
                case sempre:
                    this.regolaMaiuscolaBase(campo);
                    break;
                case mai:
                    break;
                case flag:

                    break;
                case memoria:

                    break;
                case db:

                    break;
                case nuovo:
                    if (this.isNuovoRecord()) {
                        this.regolaMaiuscolaBase(campo);
                    }// fine del blocco if
                    break;
                default: // caso non definito
                    break;
            } // fine del blocco switch

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Forza maiuscola la prima lettera del campo.
     */
    private void regolaMaiuscolaBase(Campo campo) {
        /* variabili e costanti locali di lavoro */
        String testo;

        try {    // prova ad eseguire il codice

            testo = this.getString(campo.getNomeInterno());

            if (Lib.Testo.isValida(testo)) {
                testo = Lib.Testo.primaMaiuscola(testo);
                campo.setValore(testo);
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Regola il campo soggetto.
     * <p/>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @return il soggetto assegnato
     */
    protected String regolaSoggetto() {
        /* variabili e costanti locali di lavoro */
        String soggetto = "";

        try {    // prova ad eseguire il codice
            soggetto = this.getSoggetto();

            if (this.isNuovoRecord()) {
                this.getCampo(Cam.soggetto.get()).setValore(soggetto);
            } else {
                if (Lib.Testo.isVuota(getValore(Cam.soggetto.get()))) {
                    this.getCampo(Cam.soggetto.get()).setValore(soggetto);
                }// fine del blocco if
            }// fine del blocco if-else

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return soggetto;
    }


    /**
     * Creazione del soggetto.
     * <p/>
     *
     * @return stringa
     */
    private String getSoggetto() {
        /* variabili e costanti locali di lavoro */
        String soggetto = "";
        String ragione;
        String cognome;
        String nome;
        Campo campoRagione;
        Campo campoCognome;
        Campo campoNome;
        boolean privato;
        int max = 15;

        try { // prova ad eseguire il codice
            privato = this.isPrivato();

            if (privato) {
                campoCognome = this.getCampo(Cam.cognome.get());
                cognome = (String)campoCognome.getCampoDati().getMemoria();

                campoNome = this.getCampo(Cam.nome.get());
                nome = (String)campoNome.getCampoDati().getMemoria();

                if (Lib.Testo.isValida(cognome)) {
                    soggetto = cognome;
                    if (Lib.Testo.isValida(nome)) {
                        soggetto += " " + nome;
                    }// fine del blocco if
                }// fine del blocco if
            } else {
                campoRagione = this.getCampo(Cam.ragSociale.get());
                ragione = (String)campoRagione.getCampoDati().getMemoria();

                if (Lib.Testo.isValida(ragione)) {
                    if (ragione.length() < max) {
                        max = ragione.length();
                    }// fine del blocco if
                    soggetto = ragione.substring(0, max);
                }// fine del blocco if
            }// fine del blocco if-else


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return soggetto;
    }


    /**
     * Controlla se esistono omonimi dell'anagrafica corrente.
     * <p/>
     * Invocato sulla modifica di nome, cognome o ragione sociale
     * solo in caso di nuovo record.
     * Se esiste già un omonimo, avvisa.
     */
    private void chkOmonimi() {
        /* variabili e costanti locali di lavoro */
        int[] codici = null;
        boolean continua;
        boolean privato;
        String nome = "";
        String cognome;
        int codCorrente;
        int cod;
        ArrayList<Integer> codiciDef = null;
        String testo;
        Modulo modIndirizzo;
        AnagraficaModulo modAnag;
        int codIndirizzo;
        EstrattoBase estratto;
        String soggetto;
        String stringa;

        try {    // prova ad eseguire il codice

            privato = this.isPrivato();

            /* recupero i dati e controllo se sono sufficienti per
             * fare una ricerca degli omonimi */
            if (privato) {
                nome = this.getString(Cam.nome.get());
                cognome = this.getString(Cam.cognome.get());
                continua = (Lib.Testo.isValida(nome) && (Lib.Testo.isValida(cognome)));
            } else {
                cognome = this.getString(Cam.ragSociale.get());
                continua = (Lib.Testo.isValida(cognome));
            }// fine del blocco if-else

            /* recupera i codici degli eventuali omonimi (compreso questo) */
            if (continua) {
                if (privato) {
                    codici = AnagraficaModulo.getAnagrafiche(cognome, nome, false);
                } else {
                    codici = AnagraficaModulo.getAnagrafiche(cognome, null, true);
                }// fine del blocco if-else
            }// fine del blocco if

            /* continua solo se ne ha trovati */
            if (continua) {
                continua = ((codici != null) && (codici.length > 0));
            }// fine del blocco if

            /* toglie dai codici trovati il codice della scheda corrente
            * crea una nuova lista definitiva dei codici degli omonimi */
            if (continua) {
                codCorrente = this.getCodice();
                codiciDef = new ArrayList<Integer>();
                for (int k = 0; k < codici.length; k++) {
                    cod = codici[k];
                    if (cod != codCorrente) {
                        codiciDef.add(cod);
                    }// fine del blocco if
                } // fine del ciclo for
            }// fine del blocco if

            /* messaggio di avviso */
            if (continua) {
                if (codiciDef.size() > 1) {    // trovati più di 1
                    testo =
                            "Attenzione: esistono già altri " +
                                    codiciDef.size() +
                                    " '" +
                                    nome +
                                    " " +
                                    cognome +
                                    "' in archivio";
                } else {   // trovato 1 solo
                    cod = codiciDef.get(0);
                    modAnag = (AnagraficaModulo)this.getModulo();
                    codIndirizzo = modAnag.getCodIndirizzo(cod);
                    modIndirizzo = IndirizzoModulo.get();
                    estratto = modIndirizzo.getEstratto(Indirizzo.Est.etichettaDocumento,
                            codIndirizzo);
                    stringa = estratto.getStringa();
                    soggetto = modAnag.query().valoreStringa(Anagrafica.Cam.soggetto.get(), cod);
                    testo = "Attenzione: " + nome + " " + cognome + " esiste già in archivio.";
                    testo += "\n" + soggetto;
                    testo += "\n" + stringa;
                }// fine del blocco if-else
                new MessaggioAvviso(testo);
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Regola il campo che deve prendere il fuoco.
     * <p/>
     */
    protected void regolaFuoco() {
        /* variabili e costanti locali di lavoro */
        boolean privato;
        String campoFuoco;

        try { // prova ad eseguire il codice
            privato = this.isPrivato();

            if (privato) {
                campoFuoco = Cam.nome.get();
            } else {
                campoFuoco = Cam.ragSociale.get();
            }// fine del blocco if-else
            this.vaiCampo(campoFuoco);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Controlla il campo privato/societa.
     * </p>
     * Ritorna obbligatoriamente un valore valido <br>
     *
     * @return vero se privato
     *         falso se societa
     */
    public boolean isPrivato() {
        return this.getBool(Cam.privatosocieta.get());
    }


    /**
     * Controlla se la scheda e' valida.
     * </p>
     * Tutti i campi visibili devono avere un valore valido (o  vuoto).
     * I campi obbligatori non devono essere vuoti.
     *
     * @return true se i campi sono tutti validi <br>
     */
    public boolean isValida() {
        return super.isValida();
    }


    public void sincronizza() {
        /* variabili e costanti locali di lavoro */
        Campo campo;
        int intero;
        boolean visibile;
        ValoriOpzione elem;
        boolean consenso;

        try { // prova ad eseguire il codice

            /* la data consenso è visibile solo se c'è il consenso e se è un privato*/
            if (this.isPrivato()) {
                consenso = this.getBool(Cam.consensoPrivacy.get());
            } else {
                consenso = false;
            }// fine del blocco if-else
            if (this.getCampo(Cam.dataPrivacy.get()) != null) {
                campo = this.getCampo(Cam.dataPrivacy.get());
                campo.setVisibile(consenso);
            }// fine del blocco if

            /* la percentuale di r.a. è visibile solo solo
             * se si applica la r.a. */
            visibile = false;
            intero = (Integer)this.getValore(Cam.applicaRA.get());
            elem = ValoriOpzione.getElemento(intero);
            if (elem != null) {
                if (elem.equals(ValoriOpzione.si)) {
                    visibile = true;
                }// fine del blocco if
            }// fine del blocco if
            campo = this.getCampo(Cam.percRA.get());
            campo.setVisibile(visibile);

            super.sincronizza();


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    private Pannello getPanPS() {
        return panPS;
    }


    private void setPanPS(Pannello panPS) {
        this.panPS = panPS;
    }


    private Pannello getPanCFPI() {
        return panCFPI;
    }


    private void setPanCFPI(Pannello panCFPI) {
        this.panCFPI = panCFPI;
    }


    private boolean isControlloOmonimi() {
        return controlloOmonimi;
    }


    /**
     * flag di attivazione segnalazione omonimi su nuovo record
     * <p/>
     *
     * @param controlloOmonimi true per segnalare se esistono omonimi quando si modifica un nuovo record
     */
    public void setControlloOmonimi(boolean controlloOmonimi) {
        this.controlloOmonimi = controlloOmonimi;
    }


    /**
     * Classe interna Enumerazione.
     */
    public enum Prima {

        sempre(),
        mai(),
        flag(),
        memoria(),
        db(),
        nuovo()

    }// fine della classe

} // fine della classe
