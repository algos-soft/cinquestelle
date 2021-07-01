package it.algos.albergo.stampeobbligatorie;

import it.algos.albergo.AlbergoLib;
import it.algos.albergo.AlbergoModulo;
import it.algos.albergo.stampeobbligatorie.istat.PannelloISTAT;
import it.algos.albergo.stampeobbligatorie.notifica.PannelloNotifica;
import it.algos.albergo.stampeobbligatorie.ps.PannelloPS;
import it.algos.albergo.stampeobbligatorie.testastampe.TestaStampeModulo;
import it.algos.albergo.tabelle.azienda.Azienda;
import it.algos.albergo.tabelle.azienda.AziendaModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.dialogo.DialogoBase;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.libro.Libro;
import it.algos.base.messaggio.MessaggioAvviso;
import it.algos.base.modulo.Modulo;
import it.algos.base.pagina.Pagina;
import it.algos.base.pannello.Pannello;

import javax.swing.JLabel;
import java.awt.Color;

/**
 * Dialogo di gestione delle stampe obbligatorie.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 08-giu-2007 ore 11.21.46
 */
public class StampeObbligatorieDialogo extends DialogoBase {

    /**
     * istanza unica di questa classe
     */
    private static StampeObbligatorieDialogo ISTANZA = null;

    private static final String NOME_CAMPO_DATA_ARRIVO = "data di arrivo";

    private PannelloObbligatorie panPs;

    private PannelloObbligatorie panNotifica;

    private PannelloObbligatorie panIstat;

    /* codice azienda di riferimento, valido per tutta la sessione */
    private int codAzienda;


    /**
     * Costruttore base.
     * <p/>
     */
    public StampeObbligatorieDialogo() {
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
     * Regolazioni iniziali di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal
     * costruttore (init) <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */
        Campo campo;
        int codAzienda;
        String azienda;
        JLabel labelAzienda;
        Libro libro;


        try { // prova ad eseguire il codice


            this.setTitolo("Stampe obbligatorie");
            this.getDialogo().setModal(false);

            /* azienda di riferimento */
            this.setCodAzienda(AlbergoModulo.getCodAzienda());

            /* campo data di arrivo */
            campo = CampoFactory.data(NOME_CAMPO_DATA_ARRIVO);
            campo.setValore(AlbergoLib.getDataProgramma());
            campo.inizializza();
            this.addCampoCollezione(campo);

            /* label azienda */
            codAzienda = AlbergoModulo.getCodAzienda();
            azienda = AziendaModulo.get()
                    .query()
                    .valoreStringa(Azienda.Cam.descrizione.get(), codAzienda);
            labelAzienda = new JLabel("Azienda: " + azienda);

            libro = this.creaLibro();

            /* costruzione grafica */
            this.addComponente(labelAzienda);
            this.addComponente(libro);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo inizia


    /**
     * Avvia e rende visibile.
     * <p/>
     */
    @Override public void avvia() {
        boolean continua = true;
        int codAzienda;
        String testo = "";

        try { // prova ad eseguire il codice
            /* controllo una sola azienda attiva */
            codAzienda = AlbergoModulo.getCodAzienda();
            if (codAzienda == 0) {
                testo = "Impossibile gestire le stampe obbligatorie per tutte le aziende.";
                testo += "\nAttivare prima una sola azienda.";
                new MessaggioAvviso(testo);
                continua = false;
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        if (continua) {
            super.avvia();
        }// fine del blocco if
    }


    /**
     * Crea il libro con le pagine.
     * <p/>
     *
     * @return il libro creato
     */
    private Libro creaLibro() {
        /* variabili e costanti locali di lavoro */
        Libro libro = null;
        Pannello pan;

        try {    // prova ad eseguire il codice
            libro = new Libro();
            libro.setForm(this);

            /* stampe di PS */
            pan = this.setPanPs(new PannelloPS(this));
            this.addPannello(libro, pan, "Pubblica Sicurezza");

            /* stampe di notifica */
            pan = this.setPanNotifica(new PannelloNotifica(this));
            this.addPannello(libro, pan, "Notifica arrivi");

            /* stampe istat */
            pan = this.setPanIstat(new PannelloISTAT(this));
            this.addPannello(libro, pan, "ISTAT");

            libro.avvia();

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return libro;
    }


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
    @Override public void inizializza() {
        /* variabili e costanti locali di lavoro */
        PannelloObbligatorie pan;

        try { // prova ad eseguire il codice
            super.inizializza();

            this.getModuloTesta().avvia();

            pan = this.getPanPs();
            if (pan != null) {
                pan.inizializza();
            } // fine del blocco if

            pan = this.getPanNotifica();
            if (pan != null) {
                pan.inizializza();
            } // fine del blocco if

            // dal 2014 le stampe ISTAT sono sostituite dall'invio
            // di un file al portale ISTAT. Il file viene creato
            // dalla funzione NotificaArrivi pertanto il pannello
            // ISTAT non è più utilizzato - per ora ne disattiviamo l'inizializzazione
            // in modo da non creare problemi con controli inutili. - alex mag-2015
//            pan = this.getPanIstat();
//            if (pan != null) {
//                pan.inizializza();
//            } // fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } /* fine del metodo */


    protected boolean chiudiDialogo() {
        /* variabili e costanti locali di lavoro */
        boolean chiuso = false;

        try { // prova ad eseguire il codice
            chiuso = super.chiudiDialogo();

//            /* alla chiusura del dialogo, rimuove i listeners che i
//            * pannelli avevano aggiunto a oggetti permanenti (liste) */
//            if (chiuso) {
//                this.getPanPs().rimuoviListeners();
//                this.getPanNotifica().rimuoviListeners();
//                this.getPanIstat().rimuoviListeners();
//            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return chiuso;

    }


    /**
     * Aggiunge al libro il pannello col titolo.
     * <p/>
     *
     * @param libro  del dialogo
     * @param pan    pagina da aggiungere al libro
     * @param titolo della pagina
     */
    private void addPannello(Libro libro, Pannello pan, String titolo) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Pagina pag = null;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(libro, titolo) && pan != null);

            if (continua) {
                pag = libro.addPagina(titolo, pan);
                continua = (pag != null);
            } // fine del blocco if

            if (continua) {
                pag.setMargine(Pagina.Margine.nessuno);
                pag.setUsaScorrevole(false);
            } // fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Sincronizzazione della scheda/dialogo.
     * <p/>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    @Override public void sincronizza() {
        try { // prova ad eseguire il codice
            super.sincronizza();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Ritorna il codice della azienda di riferimento.
     * <p/>
     *
     * @return il codice della azienda di riferimento
     */
    public int getCodAzienda() {
        return codAzienda;
    }


    private void setCodAzienda(int codAzienda) {
        this.codAzienda = codAzienda;
    }


    /**
     * Ritorna il codice della azienda attiva.
     * <p/>
     *
     * @return il codice della azienda attiva
     */
    Color getColoreSfondo() {
        return SFONDO_DIALOGO;
    }


    /**
     * Ritorna il modulo di testa.
     * <p/>
     *
     * @return il modulo di testa
     */
    private Modulo getModuloTesta() {
        return TestaStampeModulo.get();
    }


    private PannelloObbligatorie getPanPs() {
        return panPs;
    }


    private PannelloObbligatorie setPanPs(PannelloObbligatorie panPs) {
        this.panPs = panPs;
        return this.getPanPs();
    }


    private PannelloObbligatorie getPanNotifica() {
        return panNotifica;
    }


    private PannelloObbligatorie setPanNotifica(PannelloObbligatorie panNotifica) {
        this.panNotifica = panNotifica;
        return this.getPanNotifica();
    }


    private PannelloObbligatorie getPanIstat() {
        return panIstat;
    }


    private PannelloObbligatorie setPanIstat(PannelloObbligatorie panIstat) {
        this.panIstat = panIstat;
        return this.getPanIstat();
    }

    /**
     * Crea una istanza del dialogo o la rende visibile se esiste già
     * <p/>
     *
     * @return l'istanza del dialogo
     */
    public static StampeObbligatorieDialogo getIstanza() {

        if (ISTANZA == null) {
            ISTANZA = new StampeObbligatorieDialogo();
            ISTANZA.inizializza();
            ISTANZA.avvia();
        }// fine del blocco if

        ISTANZA.getDialogo().setVisible(true);

        return ISTANZA;
    } /* fine del metodo getter */



}// fine della classe