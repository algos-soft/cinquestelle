/**
 * Title:     TestModello
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      22-apr-2004
 */
package it.algos.base.testbase;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.campo.lista.CampoLista;
import it.algos.base.errore.Errore;
import it.algos.base.modello.ModelloAlgos;
import it.algos.base.vista.Vista;
import it.algos.base.wrapper.EstrattoBase;
import it.algos.base.wrapper.WrapFiltri;

import java.util.ArrayList;

/**
 * Tracciato record della tavola TestModello.
 * </p>
 * Questa classe concreta: <ul>
 * <li> Crea il <strong>tracciato record</strong> (Abstract Data Types) di una
 * tavola </li>
 * <li> Mantiene il nome della tavola di archivo dove sono registrati tutti i
 * dati (records) del modello </li>
 * <li> Crea i <strong>campi</strong> di questo modello (oltre a quelli base
 * della superclasse) </li>
 * <li> Un eventuale file di dati iniziali va regolato come percorso e nomi dei
 * campi presenti </li>
 * <li> Eventuali <strong>moduli e tabelle</strong> vanno creati nel metodo <code>
 * regolaModuli</code> </li>
 * <li> Regola i titoli delle finestre lista e scheda
 * <li> Ogni campo viene creato con un costruttore semplice con solo le piu'
 * comuni informazioni; le altre vengono regolate con chiamate successive </li>
 * </ul>
 *
 * @author Alberto Colombo, Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 22-apr-2004 ore 12.22.18
 */
public final class TestModello extends ModelloAlgos {

    /**
     * nome della tavola di archivio collegata (facoltativo).
     * se vuoto usa il nome del modulo
     */
    private static final String TAVOLA_ARCHIVIO = "prova";

    /**
     * titolo della finestra della lista (facoltativo).
     * (se vuoti, mette in automatico il nome della tavola)
     */
    private static final String TITOLO_FINESTRA_LISTA = "";

    /**
     * titolo della finestra della scheda (facoltativo).
     * (se vuoti, mette in automatico il nome della tavola)
     */
    private static final String TITOLO_FINESTRA_SCHEDA = "";


    /**
     * Costruttore completo senza parametri.<br>
     */
    public TestModello() {
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
     * Regolazioni immediate di riferimenti e variabili.
     * <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* regola il nome della tavola dalla costante */
        super.setTavolaArchivio(TAVOLA_ARCHIVIO);

        /* Regola i titoli delle finestre lista e scheda */
        super.regolaTitoli(TITOLO_FINESTRA_LISTA, TITOLO_FINESTRA_SCHEDA);
    }// fine del metodo inizia


    /**
     * Creazione dei campi di questo modello (oltre a quelli base).
     * <br>
     * i campi verranno visualizzati nell'ordine di inserimento <br>
     * (metodo chiamato dalla superclasse) <br>
     */
    protected void creaCampi() {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;

        /* invoca il metodo sovrascritto della superclasse */
        super.creaCampi();

        /* i campi verranno visualizzati nell'ordine di inserimento */
        try {

            /* campo sigla (esempio) */
            unCampo = CampoFactory.sigla();
            this.addCampo(unCampo);

            /* campo descrizione (esempio) */
            unCampo = CampoFactory.descrizione();
//            unCampo.setTestoLegenda("testo della legenda");
            this.addCampo(unCampo);

            /* campo testo (esempio) */
            unCampo = CampoFactory.testo("alfa");
            this.addCampo(unCampo);

            /* campo numero intero (esempio) */
            unCampo = CampoFactory.intero("intero");
            this.addCampo(unCampo);

            /* campo data di prova */
            unCampo = CampoFactory.data("data");
            this.addCampo(unCampo);

            /* campo gruppo check (esempio) */
            unCampo = CampoFactory.checkInterno("gruppochk");
            unCampo.setValoriInterni("alfa,beta,gamma,delta");
            this.addCampo(unCampo);


        } catch (Exception unErrore) { // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo */


    /**
     * Costruisce degli Array di riferimenti ordinati (oggetti Campo) per
     * individuare i campi che voglio vedere in un set di campi scheda <br>
     * Gli array vengono creati coi campi di questo modello <br>
     * Viene chiamato DOPO che nella sottoclasse specifica sono stati
     * costruiti tutti i campi <br>
     * Aggiunge gli array alla collezione <br>
     */
    protected void creaSet() {
        /** variabili e costanti locali di lavoro */
        ArrayList unSet = null;

        try {    // prova ad eseguire il codice

            /** crea il set specifico (piu' campi - uso un array) */
//            unSet = new ArrayList();
//            unSet.add(Categoria.CAMPO_SIGLA);
//            unSet.add(Categoria.CAMPO_ITALIANO);
//            unSet.add(Categoria.CAMPO_CARNE_PESCE);
//            super.creaSet(Categoria.SET_ITALIANO, unSet);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo */


    /**
     * Creazione (eventuale) delle viste aggiuntive <br>
     * Costruisce degli ArrayList di riferimenti ordinati (oggetti Vista) per
     * individuare i campi che voglio vedere nelle liste alternative ed
     * aggiuntive a quella standard (costruita in automatico nella superclasse) <br>
     * Gli array vengono creati coi campi di questo modello, oppure con
     * viste di altri moduli, oppure con campi di altri modelli <br>
     * Viene chiamato DOPO che nella sottoclasse specifica sono stati
     * costruiti tutti i campi <br>
     */
    protected void creaViste() {
        /** variabili e costanti locali di lavoro */
        ArrayList unArray = null;

        try {    // prova ad eseguire il codice

            /** crea la vista specifica (un solo campo) */
//            super.creaVista(Categoria.VISTA_SIGLA, Categoria.CAMPO_SIGLA);

            /** crea la vista specifica (piu' campi - uso un array) */
//            unArray = new ArrayList();
//            unArray.add(Categoria.CAMPO_SIGLA);
//            unArray.add(Categoria.CAMPO_ITALIANO);
//            super.creaVista(Categoria.VISTA_SIGLA_ITALIANO, unArray);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo */


    /**
     * regolazioni ulteriori dei singoli campi delle
     * viste dopo che sono stati clonati <br>
     * Viene chiamato DOPO che nella superclasse sono state
     * clonate tutte le viste <br>
     */
    protected void regolaViste() {
        /** variabili e costanti locali di lavoro */
        Vista unaVista = null;
        CampoLista unCampoLista = null;

        try {    // prova ad eseguire il codice

//            unaVista = this.getVista(Categoria.VISTA_SIGLA);
//            unCampoLista = unaVista.getCampo(Categoria.CAMPO_SIGLA).getCampoLista();
//            unCampoLista.setTitoloColonna("categoria");

        } catch (Exception unErrore) {    // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo */


    /**
     * Regolazioni di avvio, ogni volta che questo oggetto deve 'ripartire', per
     * essere sicuri che sia 'pulito' <br>
     * Metodo chiamato da altre classi <br>
     * Viene eseguito tutte le volte che necessita  <br>
     */
    public void avviaModello() {
        /** invoca il metodo sovrascritto della superclasse */
        super.avviaModello();
    } /* fine del metodo */


    /**
     * Crea un lista di filtri vuota e la aggiunge alla collezione.
     * <p/>
     * Metodo invocato nelle sottoclassi <br>
     *
     * @return lista di filtri (una per ogni popup)
     */
    @Override protected WrapFiltri addPopFiltro() {
        return super.addPopFiltro();

    }


    /**
     * Regolazione dei filtri per i popup.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     * <p/>
     * Crea uno o più filtri alla lista, tramite un popup posizionato in basso a destra <br>
     * I popup si posizionano bandierati a destra,
     * ma iniziando da sinistra (secondo l'ordine di creazione) <br>
     */
    @Override protected void regolaFiltriPop() {
        super.regolaFiltriPop();

    } /* fine del metodo */


    /**
     * Restituisce un estratto.
     * </p>
     * Restituisce un estratto conforme al nome ed al record richiesto <br>
     * Invoca il metodo sovrascritto della superclasse per gestire gli
     * estratti il cui nome coincide con un singolo campo esistente <br>
     * Se nella superclasse non viene recuperato un valore valido, occorre
     * scrivere in questa classe metodi specifici per ogni nome di estratto <br>
     * Utilizzare un gruppo condizionale di if per invocare i suddetti metodi <br>
     *
     * @param unNomeEstratto nome dell'estratto codificato nell'interfaccia
     * @param unCodiceRecord record sul quale effettuare la ricerca
     *
     * @return l'estratto costruito con la stringa ricavata
     */
    public EstrattoBase getEstrattoOld(String unNomeEstratto, int unCodiceRecord) {
        /* variabili e costanti locali di lavoro */
        EstrattoBase unEstratto = null;

        try {    // prova ad eseguire il codice

            /* invoca il metodo sovrascritto della superclasse */
            unEstratto = super.getEstrattoOld(unNomeEstratto, unCodiceRecord);

            /* se non ha recuperato un estratto valido, devo gestirlo qui;
             * i nomi identificativi degli estratti sono codificati nella
             * interfaccia di questo package */
            if (unEstratto == null) {

            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return unEstratto;
    } // fine del metodo


    /**
     * regolazioni particolari sui singoli campi <br>
     * il record vuoto e' stato creato nella superclasse Modello
     * che ha regolato il campo codice col valore progressivo e gli altri
     * campi con valori nulli (a seconda del tipo di campo) <br>
     *
     * @return codice chiave del record appena creato <br>
     */
    public int nuovoRecord(boolean visibile) {
        /** variabili e costanti locali di lavoro */
        int unCodice = 0;

        /** invoca il metodo sovrascritto della superclasse */
//        int unCodice = super.nuovoRecord(visibile);

        /** valore di ritorno del codice chiave */
        return unCodice;
    } /* fine del metodo */
}// fine della classe
