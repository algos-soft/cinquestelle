/**
 * Copyright(c): 2005
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 3-4-05
 */

package it.algos.base.selezione;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.errore.Errore;
import it.algos.base.modello.ModelloAlgos;

/**
 * Tracciato record della tavola Selezione.
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
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 3-4-05
 */
public final class SelezioneModello extends ModelloAlgos implements Selezione {

    /**
     * nome della tavola di archivio collegata (facoltativo) <br>
     * i nomi delle tavole sono sempre minuscoli <br>
     * se vuoto usa il nome del modulo <br>
     */
    private static final String TAVOLA_ARCHIVIO = NOME_TAVOLA;

    /**
     * Testo della colonna della Lista
     */
    private static final String COLONNA_SIGLA = TITOLO_TABELLA;

    /**
     * Testo della colonna della Lista
     */
    private static final String COLONNA_DESCRIZIONE = TITOLO_TABELLA;

    /**
     * Testo della etichetta sopra il campo
     */
    private static final String TESTO_SIGLA = "sigla";

    /**
     * Testo della legenda sotto il campo
     */
    private static final String LEGENDA_SIGLA = "sigla come appare nelle liste di altri moduli";

    /**
     * Testo della legenda sotto il campo
     */
    private static final String LEGENDA_DESCRIZIONE = "descrizione completa della tabella";


    /**
     * Costruttore completo senza parametri.
     */
    public SelezioneModello() {

        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore


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
    }


    /**
     * Creazione dei campi.
     * <p/>
     * Metodo invocato dal ciclo statico del progetto <br>
     * Creazione dei campi record di questo modello <br>
     * I campi verranno visualizzati nell'ordine di inserimento <br>
     * Ogni campo viene creato con un costruttore semplice con solo le piu'
     * comuni informazioni; le altre vengono regolate con chiamate successive <br>
     * Invoca il metodo sovrascritto della superclasse <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     *
     * @see it.algos.base.progetto.Progetto#preparaModuli
     * @see it.algos.base.modello.ModelloAlgos#creaCampi
     * @see it.algos.base.campo.base.CampoFactory
     * @see it.algos.base.campo.video.decorator.VideoFactory
     */
    protected void creaCampi() {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;

        try { // prova ad eseguire il codice
            /* invoca il metodo sovrascritto della superclasse */
            super.creaCampi();

            /* campo modulo */
            unCampo = CampoFactory.testo(CAMPO_MODULO);
            unCampo.setVisibileVistaDefault();
            unCampo.getCampoLista().setRidimensionabile(true);
            this.addCampo(unCampo);

            /* campo nome */
            unCampo = CampoFactory.testo(CAMPO_NOME);
            unCampo.setVisibileVistaDefault();
            unCampo.getCampoLista().setRidimensionabile(true);
            this.addCampo(unCampo);

            /* campo dati */
            unCampo = CampoFactory.testo(CAMPO_DATI);
            unCampo.setVisibileVistaDefault();
            unCampo.getCampoLista().setRidimensionabile(true);
            this.addCampo(unCampo);

            /* campo privata */
            unCampo = CampoFactory.checkBox(CAMPO_PRIVATA);
            unCampo.setVisibileVistaDefault();
            unCampo.getCampoLista().setRidimensionabile(true);
            this.addCampo(unCampo);

            /* rende visibile il campo ordine */
            super.setCampoOrdineVisibileLista(); //

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


} // fine della classe
