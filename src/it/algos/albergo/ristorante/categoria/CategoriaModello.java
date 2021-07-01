/**
 * Copyright: Copyright (c) 2005
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      17-gen-2005
 */
package it.algos.albergo.ristorante.categoria;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.campo.lista.CampoLista;
import it.algos.base.errore.Errore;
import it.algos.base.modello.ModelloAlgos;
import it.algos.base.modulo.Modulo;
import it.algos.base.vista.Vista;

import java.util.ArrayList;

/**
 * Tracciato record della tavola Categoria.
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
 * @version 1.0    / 17-gen-2005 ore 15.48.14
 */
public final class CategoriaModello extends ModelloAlgos implements Categoria {

    /**
     * nome della tavola di archivio collegata (facoltativo) <br>
     * i nomi delle tavole sono sempre minuscoli <br>
     * se vuoto usa il nome del modulo <br>
     */
    private static final String TAVOLA_ARCHIVIO = NOME_TAVOLA;

    /**
     * etichette usate in alcuni campi
     */
    private static final String LABEL_CONTORNO = "contorno";

    /**
     * Testo della legenda sotto il campo sigla nella scheda
     */
    private static final String LEGENDA_SIGLA = "sigla come appare nelle liste di altri moduli";

    /**
     * Testo varii nella scheda
     */
    private static final String STAMPE = " della categoria che appare nelle stampe";

    private static final String LEGENDA_ITALIANO = "nome italiano" + STAMPE;

    private static final String LEGENDA_TEDESCO = "nome tedesco" + STAMPE;

    private static final String LEGENDA_INGLESE = "nome inglese" + STAMPE;

    private static final String LEGENDA_FRANCESE = "nome francese" + STAMPE;

    private static final String LEGENDA_CARNE_A = "suddivisione carne/pesce";

    private static final String LEGENDA_CARNE_B = "eventuale suddivisione per questa categoria";

    private static final String LEGENDA_CONTORNO = "indica se questa categoria è di tipo contorno";

    /**
     * Testo della colonna della Lista come appare nella Vista
     */
    private static final String COLONNA_SIGLA = "categoria";


    /**
     * Costruttore completo senza parametri.
     */
    public CategoriaModello() {
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
        int a = 87;
    }// fine del metodo inizia


    /**
     * Regolazioni iniziali, dopo che sono stati regolati dalla sottoclasse
     * i parametri indispensabili (tra cui il riferimento al modulo)
     * Metodo chiamato dalla classe che crea questo oggetto
     * Viene eseguito una sola volta
     *
     * @param unModulo Abstract Data Types per le informazioni di un modulo
     */
    public boolean inizializza(Modulo unModulo) {
        boolean riuscito = false;

        /** invoca il metodo sovrascritto della superclasse */
        //super.inizializza(unModulo);
        riuscito = super.inizializza(unModulo);
        return riuscito;

    } /* fine del metodo */


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
     * @see it.algos.base.campo.video.decorator.VideoFactory
     */
    protected void creaCampi() {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;
        CampoLista unCampoLista = null;
        int lar = 0;

        lar = 250;

        /* invoca il metodo sovrascritto della superclasse */
        super.creaCampi();

        try { // prova ad eseguire il codice

            /* campo sigla */
            unCampo = CampoFactory.sigla();
            unCampo.decora().obbligatorio();
            unCampo.decora().legenda(LEGENDA_SIGLA);
            unCampo.setUsaRangeRicerca(true);
            unCampo.getCampoLista().setOrdinePubblico(this.getCampoOrdine());

            //todo provvisorio
//            unCampo.setModificabileLista(true);

            this.addCampo(unCampo);

            /* campo nome italiano */
            unCampo = CampoFactory.testo(CAMPO_ITALIANO);
            unCampoLista = unCampo.getCampoLista();
            unCampoLista.setOrdinePubblico(this.getCampoOrdine());
            unCampo.setVisibileVistaDefault();
//            unCampo.setRicercabile(true);
            unCampo.setTitoloColonna("descrizione");
            unCampo.setLarLista(150);
            unCampo.getCampoLista().setTestoTooltip(LEGENDA_ITALIANO);
            unCampo.decora().etichetta("descrizione");
            unCampo.setLarScheda(lar);
            unCampo.decora().legenda(LEGENDA_ITALIANO);
            this.addCampo(unCampo);

            /* campo nome tedesco */
            unCampo = CampoFactory.testo(CAMPO_TEDESCO);
            unCampo.setLarScheda(lar);
            unCampo.decora().legenda(LEGENDA_TEDESCO);
            this.addCampo(unCampo);

            /* campo nome inglese */
            unCampo = CampoFactory.testo(CAMPO_INGLESE);
            unCampo.setLarScheda(lar);
            unCampo.decora().legenda(LEGENDA_INGLESE);
            this.addCampo(unCampo);

            /* campo nome francese */
            unCampo = CampoFactory.testo(CAMPO_FRANCESE);
            unCampo.setLarScheda(lar);
            unCampo.decora().legenda(LEGENDA_FRANCESE);
            this.addCampo(unCampo);

            /* campo checkbox carne/pesce */
            unCampo = CampoFactory.checkBox(CAMPO_CARNE_PESCE);
            unCampo.setVisibileVistaDefault();
            unCampo.setTitoloColonna("c/p");
            unCampo.getCampoLista().setTestoTooltip(LEGENDA_CARNE_A);
            unCampo.setTestoComponente(LEGENDA_CARNE_A);
            unCampo.decora().legenda(LEGENDA_CARNE_B);
            this.addCampo(unCampo);

            /* campo checkbox contorno */
            unCampo = CampoFactory.checkBox(CAMPO_CONTORNO);
            unCampo.setVisibileVistaDefault();
            unCampo.setRicercabile(false);
            unCampo.setTitoloColonna("c");
            unCampo.setTestoComponente(LABEL_CONTORNO);
            unCampo.decora().legenda(LEGENDA_CONTORNO);
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
        /* variabili e costanti locali di lavoro */
        ArrayList unArray = null;

        try { // prova ad eseguire il codice

            /* crea la vista specifica (un solo campo) */
            super.addVista(VISTA_SIGLA, CAMPO_SIGLA);

            /* crea la vista specifica (un solo campo) */
            super.addVista(VISTA_ITALIANO, CAMPO_ITALIANO);

            /* crea la vista specifica (piu' campi - uso un array) */
            unArray = new ArrayList();
            unArray.add(CAMPO_SIGLA);
            unArray.add(CAMPO_ITALIANO);
            super.addVista(VISTA_SIGLA_ITALIANO, unArray);

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

            unaVista = this.getVista(VISTA_SIGLA);
            unCampo = unaVista.getCampo(CAMPO_SIGLA);
            unCampo.getCampoLista().setRidimensionabile(false);
//            unCampo.setTitoloColonna(COLONNA_SIGLA);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Creazione dei set aggiuntivi.
     * <p/>
     * Metodo invocato dal ciclo statico del progetto <br>
     * Eventuale creazione di set aggiuntivi, oltre al set base di default <br>
     * Costruisce degli ArrayList di riferimenti ordinati (oggetti <code>Campo</code>) per
     * individuare i campi che voglio vedere in un set di campi scheda <br>
     * Gli array vengono creati coi campi di questo modello, oppure con
     * campi di altri moduli <br>
     * Viene chiamato <strong>dopo</strong> che nella sottoclasse specifica sono stati
     * costruiti tutti i campi <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     */
    protected void creaSet() {
        /* variabili e costanti locali di lavoro */
        ArrayList<String> unSet;

        try {    // prova ad eseguire il codice

            /* crea il set specifico (piu' campi - uso un array) */
            unSet = new ArrayList<String>();
            unSet.add(CAMPO_SIGLA);
            unSet.add(CAMPO_ITALIANO);
            unSet.add(CAMPO_CARNE_PESCE);
            unSet.add(CAMPO_CONTORNO);
            super.creaSet(SET_ITALIANO, unSet);

            /* crea il set specifico (piu' campi - uso un array) */
            unSet = new ArrayList<String>();
            unSet.add(CAMPO_TEDESCO);
            unSet.add(CAMPO_INGLESE);
            unSet.add(CAMPO_FRANCESE);
            super.creaSet(SET_STRANIERO, unSet);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    }

//    /**
//     * Crea i valori dei campi per un nuovo record.
//     * <p/>
//     * Invoca il metodo sovrascritto della superclasse <br>
//     * Modifica eventuali valori del singolo campo <br>
//     * Viene sovrascritto dalle classi specifiche <br>
//     *
//     * @return lista di oggetti di classe <code>CampoValore</code> <br>
//     */
//    public ArrayList nuovoRecord() {
//        /* variabili e costanti locali di lavoro */
//        ArrayList lista = null;
//
//        try { // prova ad eseguire il codice
//            /* recupera la lista dei valori standard */
//            lista = super.nuovoRecord();
//
////            /* modifica il valore del campo specifico */
////            super.nuovoValore(lista, CAMPO_SIGLA, "");
//
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//
//        /* valore di ritorno */
//        return lista;
//    }


}// fine della classe
