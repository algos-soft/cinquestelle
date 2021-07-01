/**
 * Copyright:    Copyright (c) 2002, 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 10 febbraio 2003 alle 11.40
 */
package it.algos.albergo.ristorante.lingua;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.errore.Errore;
import it.algos.base.modello.ModelloAlgos;

/**
 * Tracciato record della tavola Lingua.
 * </p>
 * Questa classe concreta: <ul>
 * <li> Mantiene il nome della tavola di archivo dove sono registrati tutti i
 * dati (records) del modello </li>
 * <li> Crea il <strong>tracciato record</strong> (Abstract Data Types) di una
 * tavola </li>
 * <li> Crea i <strong>campi</strong> di questo modello (oltre a quelli base
 * della superclasse) nel metodo <code>creaCampi</code> </li>
 * <li> Ogni campo viene creato con un costruttore semplice con solo le piu'
 * comuni informazioni; le altre vengono regolate con chiamate successive </li>
 * <li> Crea eventuali <strong>viste</strong> della <code>Lista</code>
 * (oltre a quella base) nel metodo <code>creaViste</code> </li>
 * <li> Regola eventualmente i valori delle viste nel metodo <code>regolaViste</code> </li>
 * <li> Crea eventuali <strong>set</strong> della <code>Scheda</code>
 * (oltre a quello base) nel metodo <code>creaSet</code> </li>
 * <li> Regola eventualmente i valori dei set nel metodo <code>regolaSet</code> </li>
 * <li> Regola eventualmente i valori da inserire in un <code>nuovoRecord</code> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  10 febbraio 2003 ore 11.40
 */
public final class LinguaModello extends ModelloAlgos implements Lingua {

    /**
     * nome della tavola di archivio collegata (facoltativo)
     * (se vuoto usa il nome del modulo)
     */
    private static final String TAVOLA_ARCHIVIO = NOME_TAVOLA;

    /**
     * etichette usate in alcuni campi
     */
    private static final String LABEL_NOME = "lingua";

    private static final String LABEL_COLAZIONE = "prima colazione";

    private static final String LABEL_PRANZO = "pranzo";

    private static final String LABEL_CENA = "cena";

    private static final String LABEL_SURGELATI = "testo surgelati";
    
    private static final String LABEL_INTOLLERANZE = "testo intolleranze";

    private static final String LABEL_CONGIUNZIONE = "testo congiunzione";

    private static final String LABEL_CONTORNO_MINUSCOLO_ = "contorno minuscolo";

    /**
     * testi usati nelle spiegazioni dei campi (facoltativo)
     */
    private static final String LEGENDA_STAMPE = "testo come appare nelle stampe";

    private static final String LEGENDA_SURGELATI = "avviso di piatti surgelati";

    private static final String LEGENDA_INTOLLERANZE = "annotazione allergie o intolleranze stampata sul menu";

    private static final String LEGENDA_CONGIUNZIONE =
            "congiunzione tra il secondo piatto ed il contorno";

    private static final String LEGENDA_CONTORNO_MINUSCOLO =
            "converte l'iniziale dei contorni in minuscolo.";


    /**
     * Costruttore completo senza parametri.
     */
    public LinguaModello() {
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
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* regola il nome della tavola dalla costante */
        super.setTavolaArchivio(TAVOLA_ARCHIVIO);
        this.setUsaCampoPreferito(true);
    }// fine del metodo inizia


    /**
     * Creazione dei campi.
     * <p/>
     * Metodo invocato dal ciclo statico del progetto <br>
     * Creazione dei campi record di questo modello <br>
     * I campi verranno visualizzati nell'ordine di inserimento <br>
     * Ogni campo viene creato con un costruttore semplice con solo le piu'
     * comuni informazioni; le altre vengono regolate con chiamate successive <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     *
     * @see it.algos.base.campo.base.CampoFactory
     * @see it.algos.base.campo.video.decorator.VideoFactory;
     */
    protected void creaCampi() {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;

        /* invoca il metodo sovrascritto della superclasse */
        super.creaCampi();

        /* campi di questa tabella (oltre a quelli Algos standard) */
        try {
            /* campo nome */
            unCampo = CampoFactory.testo(CAMPO_NOME);
            unCampo.setVisibileVistaDefault();
            unCampo.decora().etichetta(LABEL_NOME);
            this.addCampo(unCampo);

            /* campo colazione */
            unCampo = CampoFactory.testo(CAMPO_COLAZIONE);
            unCampo.decora().etichetta(LABEL_COLAZIONE);
            unCampo.decora().legenda(LEGENDA_STAMPE);
            this.addCampo(unCampo);

            /* campo pranzo */
            unCampo = CampoFactory.testo(CAMPO_PRANZO);
            unCampo.decora().etichetta(LABEL_PRANZO);
            unCampo.decora().legenda(LEGENDA_STAMPE);
            this.addCampo(unCampo);

            /* campo cena */
            unCampo = CampoFactory.testo(CAMPO_CENA);
            unCampo.decora().etichetta(LABEL_CENA);
            unCampo.decora().legenda(LEGENDA_STAMPE);
            this.addCampo(unCampo);

            /* campo avviso surgelati */
            unCampo = CampoFactory.testo(CAMPO_SURGELATI);
            unCampo.decora().etichetta(LABEL_SURGELATI);
            unCampo.decora().legenda(LEGENDA_SURGELATI);
            this.addCampo(unCampo);
            
            /* campo avviso intolleranze o allergie */
            unCampo = CampoFactory.testoArea(CAMPO_INTOLLERANZE);
            unCampo.setLarScheda(300);
            unCampo.setNumeroRighe(3);
            unCampo.decora().etichetta(LABEL_INTOLLERANZE);
            unCampo.decora().legenda(LEGENDA_INTOLLERANZE);
            this.addCampo(unCampo);

            /* campo congiunzione tra primi e secondi piatti */
            unCampo = CampoFactory.testo(CAMPO_CONGIUNZIONE);
            unCampo.setLarScheda(100);
            unCampo.decora().etichetta(LABEL_CONGIUNZIONE);
            unCampo.decora().legenda(LEGENDA_CONGIUNZIONE);
            this.addCampo(unCampo);

            /* campo flag - indica se per la lingua il nome del piatto va
* automaticamente trasformato con l'iniziale minuscola o mantenuto
* come scritto quando il piatto è utilizzato nel menu come contorno */
            unCampo = CampoFactory.checkBox(CAMPO_CONTORNO_MINUSCOLO);
            unCampo.setTestoComponente(LABEL_CONTORNO_MINUSCOLO_);
            unCampo.decora().legenda(LEGENDA_CONTORNO_MINUSCOLO);
            this.addCampo(unCampo);

            /* rende visibile il campo ordine */
            super.setCampoOrdineVisibileLista(); //

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    } /* fine del metodo */

}// fine della classe