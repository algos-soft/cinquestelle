/**
 * Title:     NavPresenze
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      9-lug-2008
 */
package it.algos.albergo.storico5stelle;

import it.algos.base.azione.AzModulo;
import it.algos.base.errore.Errore;
import it.algos.base.messaggio.MessaggioDialogo;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.NavigatoreLS;

import javax.swing.JFileChooser;
import java.awt.event.ActionEvent;
import java.io.File;

/**
 * Scheda Storico 5Stelle
 * </p>
 * Questa classe: <ul>
 * <li> </li>
 * <li> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 9-lug-2008 ore 9.51.20
 */
final class StoricoNavigatore extends NavigatoreLS {

    private static final String CHIAVE_AZ_IMPORTA_STORICO = "importastorico";

    private static final String CHIAVE_AZ_CREA_PRESENZE = "creapresenze";


    /**
     * Costruttore base senza parametri.
     * <p/>
     * Utilizzato solo per debug
     */
    public StoricoNavigatore() {
        super();
    }// fine del metodo costruttore base


    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param unModulo modulo di riferimento
     */
    public StoricoNavigatore(Modulo unModulo) {
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
     * Regolazioni immediate di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        try { // prova ad eseguire il codice
            this.setUsaPannelloUnico(true);
            this.addSchedaCorrente(new StoricoScheda(this.getModulo()));
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizia


    /**
     * Inizializzazione dell'oggetto.<br>
     * Regola le caratteristiche dinamiche in base alla impostazione
     * corrente delle caratteristiche statiche.<br>
     * Viene chiamato dall'inizializzazione del Modulo.<br>
     * Puo' essere chiamato piu' volte.<br>
     * Se l'inizializzazione ha successo imposta il flag inizializzato a true.<br>
     * Il flag puo' essere successivamente modificato dalle sottoclassi se non
     * riescono a portare a termine la propria inizializzazione specifica.<br>
     */
    public void inizializza() {
        try { // prova ad eseguire il codice

            super.inizializza();

            this.addAzione(new AzImportaStorico());
            this.addAzione(new AzCreaPresenze());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizializza


    /**
     * Importa lo storico.
     * <p/>
     */
    private void importaStorico() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Modulo modStorico;
        JFileChooser chooser;
        File inputFile;

        try {    // prova ad eseguire il codice

            /* seleziona il file di input */
            chooser = new JFileChooser();
            chooser.showOpenDialog(null);
            inputFile = chooser.getSelectedFile();
            continua = (inputFile != null);

            /* cancella lo storico corrente */
            if (continua) {
                modStorico = StoricoModulo.get();
                modStorico.query().eliminaRecords();
            }// fine del blocco if

            /* importa lo storico */
            if (continua) {
                continua = StoricoLogica.importaStorico(inputFile, this);
            }// fine del blocco if

            /* ricarica la lista */
            if (continua) {
                this.getLista().caricaTutti();
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Crea le presenze.
     * <p/>
     */
    private void creaPresenze() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        MessaggioDialogo dialogo;

        try {    // prova ad eseguire il codice

            dialogo = new MessaggioDialogo("Vuoi creare le presenze dallo storico?");
            continua = dialogo.isConfermato();
            if (continua) {
                continua = StoricoLogica.creaPresenze(this);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /*
    * Azione Importa Storico.
    * </p>
    */
    private final class AzImportaStorico extends AzModulo {

        /**
         * Costruttore
         * <p/>
         */
        public AzImportaStorico() {
            /* rimanda al costruttore della superclasse */
            super(StoricoModulo.get());

            /* regola le variabili*/
            super.setChiave(CHIAVE_AZ_IMPORTA_STORICO);
            super.setIconaMedia("ImportaStorico24");
            super.setTooltip("importa lo storico da file .csv");
            super.setAbilitataPartenza(true);
            super.setUsoLista(true);
        }// fine del metodo costruttore senza parametri


        /**
         * actionPerformed, da ActionListener.
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void actionPerformed(ActionEvent unEvento) {
            try { // prova ad eseguire il codice
                importaStorico();
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }

    } // fine della classe 'azione interna'

    /*
    * Azione Importa Storico.
    * </p>
    */
    private final class AzCreaPresenze extends AzModulo {

        /**
         * Costruttore
         * <p/>
         */
        public AzCreaPresenze() {
            /* rimanda al costruttore della superclasse */
            super(StoricoModulo.get());

            /* regola le variabili*/
            super.setChiave(CHIAVE_AZ_CREA_PRESENZE);
            super.setIconaMedia("CreaPresenze24");
            super.setTooltip("crea le presenze dallo storico");
            super.setAbilitataPartenza(true);
            super.setUsoLista(true);
        }// fine del metodo costruttore senza parametri


        /**
         * actionPerformed, da ActionListener.
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void actionPerformed(ActionEvent unEvento) {
            try { // prova ad eseguire il codice
                creaPresenze();
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }

    } // fine della classe 'azione interna'

}