/**
 * Title:        ModelloAlgosBase.java
 * Package:      it.algos.base.modello.base
 * Description:  Modello di default
 * Copyright:    Copyright (c) 2002
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 29 novembre 2002 alle 16.37
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2002  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
// Package di questa classe e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.modello;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.costante.CostanteModulo;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
//-----------------------------------------------------------------------------
// Classe principale


//-----------------------------------------------------------------------------
/**
 * Questa classe concreta e' responsabile di:<br>
 * A - Modello di default da prendere come esempio <br>
 * B - Crea il <strong>tracciato record</strong> (Abstract Data Types) di una
 * tavola <br>
 * C - Mantiene il nome della tavola di archivo dove sono registrati tutti i
 * dati (records) del modello <br>
 * D - Crea i <strong>campi</strong> di questo modello (oltre a quelli base
 * della superclasse). <br>
 * E - Un eventuale file di dati iniziali va regolato come percorso e nomi dei
 * campi presenti <br>
 * F - Eventuali <strong>moduli</strong> vanno creati nel metodo <code>
 * regolaModuli</code> <br>
 * G - Eventuali <strong>tabelle</strong> vanno create nel metodo <code>
 * regolaTabelle</code> <br>
 * H - Regola i titoli delle finestre lista e scheda <br>
 * I - Ogni campo viene creato con un costruttore semplice con solo le piu'
 * comuni informazioni; le altre vengono regolate con chiamate successive <br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  29 novembre 2002 ore 16.37
 */
public class ModelloDefault extends ModelloAlgos {

    //-------------------------------------------------------------------------
    // Costanti statiche della classe    (private,pubbliche)  (class variables)
    //-------------------------------------------------------------------------
    /**
     * nome della tavola di archivio collegata (obbligatorio)
     */
    private static final String TAVOLA_ARCHIVIO = "ModelloDefault";

    /**
     * nome della cartella (facoltativo)
     */
    private static final String PATH_MODULO = CostanteModulo.PATH_BASE + "modello/base";

    /**
     * nome del file di dati per i dummy records (facoltativo)
     * se vuoto cerca nella cartella programma il file TAVOLA_ARCHIVIO + "Dati"
     * deve essere di tipo properties file
     */
    private static final String FILE_DATI = "";

    /**
     * nomi dei campi per i dummy records (facoltativo)
     * se vuoto usa tutte le properties del file di dati,
     * altrimenti usa solo i campi indicati
     */
    private static final String CAMPI_DUMMY = "";

    /**
     * titoli delle finestre (facoltativo)
     * (se vuoti, mette in automatico il nome della tavola)
     */
    private static final String TITOLO_FINESTRA_LISTA = "";

    private static final String TITOLO_FINESTRA_SCHEDA = "";

    /**
     * nome dei campi di prova (da sostituire)
     */
    private static final String[] listaCampi = {"alfa", "beta", "gamma"};


    /**
     * Costruttore completo senza parametri <br>
     */
    public ModelloDefault() {
        /** rimanda al costruttore della superclasse */
        super();

        /** regolazioni iniziali di riferimenti e variabili */
        try {                                   // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore completo */


    /**
     * Regolazioni iniziali di riferimenti e variabili
     * Metodo chiamato direttamente dal costruttore
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /** regola il nome della tavola dalla costante */
        super.setTavolaArchivio(TAVOLA_ARCHIVIO);

        /** Regola i titoli delle finestre lista e scheda */
        super.regolaTitoli(TITOLO_FINESTRA_LISTA, TITOLO_FINESTRA_SCHEDA);
    } /* fine del metodo inizia */


    /**
     * Regolazioni iniziali, dopo che sono stati regolati dalla sottoclasse
     * i parametri indispensabili (tra cui il riferimento al modulo)
     * Metodo chiamato dalla classe che crea questo oggetto
     * Viene eseguito una sola volta
     *
     * @param unModulo Abstract Data Types per le informazioni di un modulo
     */
    public boolean inizializza(Modulo unModulo) {
        /** invoca il metodo sovrascritto della superclasse */
        return super.inizializza(unModulo);
    } /* fine del metodo */


    /**
     * Regolazioni di avvio, ogni volta che questo oggetto deve 'ripartire', per
     * essere sicuri che sia 'pulito'
     * Metodo chiamato da altre classi
     * Viene eseguito tutte le volte che necessita
     */
    public void avviaModello() {
        /** invoca il metodo sovrascritto della superclasse */
        super.avviaModello();
    } /* fine del metodo */


    /**
     * creazione dei campi di questo modello (oltre a quelli base)
     * i campi verranno visualizzati nell'ordine di inserimento
     * (chiamato dalla superclasse)
     */
    protected void creaCampi() {
        /** variabile di lavoro */
        Campo unCampo = null;

        /** invoca il metodo sovrascritto della superclasse */
        super.creaCampi();

        /** i campi verranno visualizzati nell'ordine di inserimento */
        try {
            /** campo */
            unCampo = CampoFactory.testo("alfa");
            unCampo.setVisibileVistaDefault(true);
            this.addCampo(unCampo);

            /** campo */
            unCampo = CampoFactory.testo("beta");
            unCampo.setVisibileVistaDefault(true);
            this.addCampo(unCampo);

            /** campo */
            unCampo = CampoFactory.testo("gamma");
            unCampo.setVisibileVistaDefault(true);
            this.addCampo(unCampo);

        } catch (Exception unErrore) { // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo */

    //-------------------------------------------------------------------------
}// fine della classe it.algos.base.modello.base.ModelloAlgosBase.java
//-----------------------------------------------------------------------------

