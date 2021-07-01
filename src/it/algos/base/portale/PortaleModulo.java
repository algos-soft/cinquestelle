/**
 * Title:     PortalePresentazione
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      25-ott-2007
 */
package it.algos.base.portale;

import it.algos.base.azione.Azione;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.errore.Errore;
import it.algos.base.gestore.Gestore;
import it.algos.base.gestore.GestoreBase;
import it.algos.base.modulo.Modulo;
import it.algos.base.modulo.OnEditingFinished;
import it.algos.base.scheda.Scheda;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.Iterator;

/**
 * Portale contenitore della scheda per la presentazione di un singolo record del Modulo.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 25-ott-2007 ore 15.44.10
 */
public final class PortaleModulo extends PortaleScheda {

    /* scheda utilizzata dal Portale */
    private Scheda scheda;

    /* connessione da utilizzare per l'accesso al database */
    private Connessione connessione;

    /* flag per la conferma o l'annullamento della sessione */
    private boolean confermato;
    
    private boolean floating = false;
    


    /**
     * Costruttore completo.
     * <p/>
     *
     * @param scheda la scheda da utilizzare
     * @param conn la connessione da utilizzare per l'accesso al database
     * @param modal true per finestra flottante (default = modale)
     */
    public PortaleModulo(Scheda scheda, Connessione conn, boolean floating) {
        /* rimanda al costruttore di questa classe */
        super(null);
        try { // prova ad eseguire il codice

            this.setScheda(scheda);
            this.setConnessione(conn);
            this.floating = floating;

            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo costruttore base
    
    /**
     * Costruttore con finestra modale.
     * <p/>
     *
     * @param scheda la scheda da utilizzare
     * @param conn la connessione da utilizzare per l'accesso al database
     */
    public PortaleModulo(Scheda scheda, Connessione conn) {
    	this(scheda, conn, false);
    }// fine del metodo costruttore base



    /**
     * Regolazioni immediate di riferimenti e variabili. <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */
        Scheda scheda;
        Modulo mod;
        String titolo;

        try { // prova ad eseguire il codice

            mod = this.getScheda().getModulo();
            titolo = mod.getNomeModulo();

            this.setUsaFinestra(true);
            this.setTitoloFinestra(titolo);
            this.setUsaDialogo(!floating);
            this.getToolBar().setUsaFrecce(false);

            scheda = this.getScheda();
            scheda.setConnessione(this.getConnessione());
            
            this.addScheda(scheda);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizia


    /**
     * Regolazioni di ri-avvio.
     * <p/>
     * Avvia una sessione di editing
     *
     * @param codice del record da caricare
     * @param nuovoRecord true se si sta presentando un nuovo record
     */
    public void avvia(int codice, boolean nuovoRecord) {

        super.avvia(codice, nuovoRecord);

        try { // prova ad eseguire il codice
            this.setConfermato(false);
            this.entraInFinestra();
            this.setFinestraVisibile(true);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Regola le azioni del portale.
     * <p/>
     * Metodo invocato dal ciclo Inizializza() <br>
     * Assegna a tutte le azioni del portale un gestore specifico (classe interna)
     */
    protected void regolaAzioni() {
        /* variabili e costanti locali di lavoro */
        Iterator unGruppo;
        Azione unAzione;
        Gestore unGestore;

        super.regolaAzioni();

        try { // prova ad eseguire il codice
            unGestore = new GestoreInterno();
            unGruppo = this.getAzioni().values().iterator();
            while (unGruppo.hasNext()) {
                unAzione = (Azione)unGruppo.next();
                unAzione.setGestore(unGestore);
            } /* fine del blocco while */
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Invocato dal pulsante Registra Scheda.
     * <p/>
     * Registra il record e chiude la scheda
     */
    private void registra() {
        /* variabili e costanti locali di lavoro */
        boolean riuscito;
        Scheda scheda;

        try {    // prova ad eseguire il codice

            /* chiude la scheda registrando il record */
            scheda = this.getSchedaCorrente();
            riuscito = scheda.richiediChiusuraNoDialogo(true, false);

            /* nasconde la finestra */
            if (riuscito) {
                this.setConfermato(true);
                this.setFinestraVisibile(false);
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Invocato dal pulsante Abbandona Scheda.
     * <p/>
     * Chiude la scheda senza registrare il record
     */
    private void abbandona() {
        /* variabili e costanti locali di lavoro */
        int codUscita;
        Scheda scheda;


        try {    // prova ad eseguire il codice

            scheda = this.getSchedaCorrente();
            codUscita = scheda.richiediChiusuraConDialogo(Scheda.BOTTONE_ANNULLA, false);

            /* chiude effettivamente la scheda */
            if (codUscita != Scheda.ANNULLA) {
                this.setFinestraVisibile(false);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Invocato dal pulsante Ricarica Scheda.
     * <p/>
     */
    private void ricarica() {
        /* variabili e costanti locali di lavoro */
        Scheda scheda;

        try {    // prova ad eseguire il codice
            scheda = this.getSchedaCorrente();
            scheda.avvia(scheda.getCodice());
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }

    private Scheda getScheda() {
        return scheda;
    }


    private void setScheda(Scheda scheda) {
        this.scheda = scheda;
    }


    public Connessione getConnessione() {
        return connessione;
    }


    private void setConnessione(Connessione connessione) {
        this.connessione = connessione;
    }


    public boolean isConfermato() {
        return confermato;
    }


    private void setConfermato(boolean confermato) {
        this.confermato = confermato;
    }


    /**
     * Gestore delle azioni del Portale
     * </p>
     */
    private final class GestoreInterno extends GestoreBase {

        /**
         * Azione di conferma di un form.
         * <p/>
         *
         * @param unEvento evento generato dall'interfaccia utente
         * @param unAzione oggetto interessato dall'evento
         */
        public void confermaForm(EventObject unEvento, Azione unAzione) {
            registra();
        }


        /**
         * Azione chiude la Scheda.
         * <p/>
         * Metodo invocato da azione/evento <code>AzChiudeScheda</code> <br>
         * Invoca il metodo delegato <br>
         *
         * @param unEvento evento generato dall'interfaccia utente
         * @param unAzione oggetto interessato dall'evento
         */
        public void chiudeScheda(ActionEvent unEvento, Azione unAzione) {
            abbandona();
        }


        /**
         * Bottone annulla modifiche in una <code>Scheda</code>.
         * <p/>
         * Metodo invocato da azione/evento <code>AzAnnullaModifiche</code> <br>
         * Invoca il metodo delegato <br>
         *
         * @param unEvento evento generato dall'interfaccia utente
         * @param unAzione oggetto interessato dall'evento
         */
        public void annullaModifiche(ActionEvent unEvento, Azione unAzione) {
            ricarica();
        }


    } // fine della classe 'interna'

}// fine della classe
