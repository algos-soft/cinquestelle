/**
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 23 giugno 2003 alle 12.03
 */
package it.algos.albergo.ristorante.sala;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.errore.Errore;
import it.algos.base.modello.ModelloAlgos;
import it.algos.base.vista.Vista;

/**
 * Tracciato record della tavola Sala.
 * </p>
 * Questa classe concreta: <ul>
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
 * @version 1.0  /  23 giugno 2003 ore 12.03
 */
public final class SalaModello extends ModelloAlgos implements Sala {

    /**
     * nome della tavola di archivio collegata (facoltativo)
     * (se vuoto usa il nome del modulo)
     */
    private static final String TAVOLA_ARCHIVIO = NOME_TAVOLA;

    /**
     * testi usati nelle spiegazioni dei campi (facoltativo)
     */
    private static final String LEGENDA_DESCRIZIONE = "nome descrittivo di una sala del ristorante";

    /**
     * titolo della colonna nelle viste esterne (facoltativo)
     */
    private static final String COLONNA_DESCRIZIONE = TITOLO_TABELLA;


    /**
     * Costruttore completo senza parametri.
     */
    public SalaModello() {
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

        try { // prova ad eseguire il codice

            /* campo descrizione */
            unCampo = CampoFactory.descrizione();
            unCampo.setVisibileVistaDefault();
            unCampo.decora().legenda(LEGENDA_DESCRIZIONE);
            unCampo.decora().obbligatorio();
            unCampo.setRicercabile(true);
            this.addCampo(unCampo);

            /* rende visibile il campo ordine */
            super.setCampoOrdineVisibileLista(); //

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Creazione delle viste aggiuntive.
     * <p/>
     * Metodo invocato dal ciclo statico del progetto <br>
     * Eventuale creazione di viste aggiuntive, oltre alla vista base di default <br>
     * Costruisce degli ArrayList di riferimenti ordinati (oggetti <code>Vista</code>) per
     * individuare i campi che voglio vedere nelle liste alternative ed
     * aggiuntive a quella standard (costruita in automatico nella superclasse) <br>
     * Gli array vengono creati coi campi di questo modello, oppure con
     * viste di altri moduli, oppure con campi di altri modelli <br>
     * Viene chiamato <strong>dopo</strong> che nella sottoclasse sono stati costruiti tutti i campi <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     */
    protected void creaViste() {
        try { // prova ad eseguire il codice

            /* crea la vista specifica (un solo campo) */
            super.addVista(VISTA_DESCRIZIONE, CAMPO_DESCRIZIONE);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regolazione delle viste aggiuntive.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Eventuale regolazione delle caratteristiche specifiche di ogni copia
     * dei campi delle viste; le variazioni modificano <strong>solo</strong> le copie <br>
     * Viene chiamato <strong>dopo</strong> che nella superclasse sono state
     * <strong>clonate</strong> tutte le viste <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     */
    protected void regolaViste() {
        /* variabili e costanti locali di lavoro */
        Vista unaVista = null;
        Campo unCampo = null;

        try { // prova ad eseguire il codice

            unaVista = this.getVista(VISTA_DESCRIZIONE);
            unCampo = unaVista.getCampo(CAMPO_DESCRIZIONE);
            unCampo.getCampoLista().setRidimensionabile(false);
            unCampo.setLarLista(110);
            unCampo.setTitoloColonna(COLONNA_DESCRIZIONE);
            /* primo ordine pubblico per campo Ordine di sala */
            unCampo.setOrdinePubblico(this.getCampoOrdine());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


}// fine della classe
