/**
 * Copyright: Copyright (c) 2005
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      4-feb-2005
 */
package it.algos.albergo.ristorante.righemenutavolo;

import it.algos.albergo.ristorante.Ristorante;
import it.algos.albergo.ristorante.sala.Sala;
import it.algos.albergo.ristorante.tavolo.Tavolo;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.campo.db.CampoDB;
import it.algos.base.database.Db;
import it.algos.base.errore.Errore;
import it.algos.base.modello.ModelloAlgos;
import it.algos.base.modulo.Modulo;
import it.algos.base.progetto.Progetto;
import it.algos.base.vista.Vista;
import it.algos.base.vista.VistaElemento;

import java.util.ArrayList;

/**
 * Tracciato record della tavola RigheMenuTavolo.
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
 * @version 1.0    / 4-feb-2005 ore 17.18.16
 */
public final class RMTModello extends ModelloAlgos implements RMT {

    /**
     * nome della tavola di archivio collegata (facoltativo) <br>
     * i nomi delle tavole sono sempre minuscoli <br>
     * se vuoto usa il nome del modulo <br>
     */
    private static final String TAVOLA_ARCHIVIO = NOME_TAVOLA;

    /**
     * testi usati nelle spiegazioni dei campi
     */
    private static final String TESTO_TAVOLO = "numero del tavolo";

    private static final String TESTO_CAMERA = "camera / cliente";

    private static final String TESTO_COPERTI = "coperti";

    private static final String TESTO_COMANDATO = "ordinato";

    /**
     * testi usati nelle legende dei campi
     */
    private static final String LEGENDA_TAVOLO =
            "seleziona il tavolo tra quelli non ancora apparecchiati";

    private static final String LEGENDA_CAMERA = "il numero di camera od il nome del cliente";

    private static final String LEGENDA_COPERTI = "posti effettivamente apparecchiati al tavolo";


    /**
     * Costruttore completo senza parametri.
     */
    public RMTModello() {
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


    public boolean inizializza(Modulo unModulo) {
        return super.inizializza(unModulo);
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
        CampoDB unCampoDB = null;
        Campo campo;
        Modulo modulo;

        /* invoca il metodo sovrascritto della superclasse */
        super.creaCampi();

        try { // prova ad eseguire il codice

            /* campo link al record di menu */
            unCampo = CampoFactory.link(RMT.Cam.menu);
            unCampo.setVisibileVistaDefault();
            unCampo.setNomeModuloLinkato(Ristorante.MODULO_MENU);
            unCampo.setAzioneDelete(Db.Azione.cascade);
            this.addCampo(unCampo);

            /* campo link al record di tavolo */
            unCampo = CampoFactory.comboLinkPop(RMT.Cam.tavolo);
            unCampo.setVisibileVistaDefault();
            unCampo.decora().etichetta(TESTO_TAVOLO);
            unCampoDB = unCampo.getCampoDB();
            unCampo.setNomeModuloLinkato(Ristorante.MODULO_TAVOLO);
            unCampo.setAzioneDelete(Db.Azione.setNull);
            unCampoDB.setRelazionePreferita(true);
            unCampo.setNomeCampoValoriLinkato(Tavolo.Cam.numtavolo.get());
            unCampo.setNomeVistaLinkata(Tavolo.VISTA_SALA_TAVOLO);
            unCampo.setLarScheda(100);
            unCampo.setRicercabile(true);

            /* assegna l'ordine per il combo */
            modulo = Ristorante.Moduli.Tavolo();
            if (modulo != null) {
                campo = modulo.getCampo(Tavolo.Cam.numtavolo);
                if (campo != null) {
                    unCampo.setOrdineElenco(campo);
                }// fine del blocco if
            }// fine del blocco if

            this.addCampo(unCampo);

            /* campo camera associata al tavolo (testo libero) */
            unCampo = CampoFactory.testo(RMT.Cam.camera);
            unCampo.setVisibileVistaDefault();
            unCampo.setTitoloColonna(TESTO_CAMERA);
            unCampo.setLarLista(150);
            unCampo.decora().etichetta("note");
            unCampo.setTitoloColonna("note");
            unCampo.setRicercabile(true);
            this.addCampo(unCampo);

            /* campo coperti effettivi del tavolo */
            unCampo = CampoFactory.intero(RMT.Cam.coperti);
            unCampo.setVisibileVistaDefault();
            unCampo.decora().etichetta(TESTO_COPERTI);
            unCampo.setLarLista(60);
            unCampo.getCampoLista().setRidimensionabile(false);
            unCampo.setTotalizzabile(true);
            this.addCampo(unCampo);

            /* campo controllo tavolo comandato */
            unCampo = CampoFactory.checkBox(RMT.Cam.comandato);
            unCampo.setVisibileVistaDefault();
            unCampo.setTitoloColonna(TESTO_COMANDATO);
            unCampo.setTestoComponente(TESTO_COMANDATO);
            unCampo.setLarLista(50);
            unCampo.getCampoLista().setRidimensionabile(false);
            unCampo.setRicercabile(true);
            unCampo.decora().etichetta(" ");
            this.addCampo(unCampo);

            /* rende visibile il campo ordine */
            super.setCampoOrdineVisibileLista();

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
        ArrayList<String> unArray = null;
        VistaElemento elem = null;

        try { // prova ad eseguire il codice

            /* crea la vista specifica per la scheda del menu */
            unArray = new ArrayList<String>();
            unArray.add(RMT.Cam.tavolo.get());
            unArray.add(RMT.Cam.camera.get());
            unArray.add(RMT.Cam.coperti.get());
            unArray.add(RMT.Cam.comandato.get());
            super.addVista(VISTA_IN_MENU, unArray);

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
        Modulo unModulo = null;

        try { // prova ad eseguire il codice
            unaVista = this.getVista(VISTA_IN_MENU);
            unaVista.getCampo(Sala.CAMPO_DESCRIZIONE).setLarLista(60);
            unaVista.getCampo(RMT.Cam.camera.get()).setLarLista(60);

            unModulo = Progetto.getModulo(Ristorante.MODULO_TAVOLO);
            unCampo = unModulo.getCampo(Tavolo.Cam.numtavolo);
            unaVista.setCampoOrdineDefault(unCampo);

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
        ArrayList unSet = null;

        try {    // prova ad eseguire il codice

            /* crea il set specifico (piu' campi - uso un array) */
            unSet = new ArrayList();
            unSet.add(RMT.Cam.tavolo.get());
            unSet.add(RMT.Cam.coperti.get());
            unSet.add(RMT.Cam.camera.get());
            unSet.add(RMT.Cam.comandato.get());
            super.creaSet(SET_TAVOLO_CAMERA, unSet);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regolazione dei set aggiuntivi.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Eventuale regolazione delle caratteristiche specifiche di ogni copia
     * dei campi dei set; le variazioni modificano <strong>solo</strong> le copie <br>
     * Viene chiamato <strong>dopo</strong> che nella sottoclasse specifica sono stati
     * costruiti tutti i campi <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     */
    protected void regolaSet() {
        try { // prova ad eseguire il codice
            ;
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

//        unCampo = Libreria.getCampo(unSet, RigheMenuTavoloOld.CAMPO_TAVOLO);
//        unCampo.setTestoEtichettaScheda("numero del tavolo");
//        VideoFactory.legenda(
//                unCampo,
//                "seleziona il tavolo tra quelli non ancora apparecchiati",
//                true);
//
//        unCampo = Libreria.getCampo(unSet, RigheMenuTavoloOld.CAMPO_COPERTI);
//        VideoFactory.legenda(
//                unCampo, "posti effettivamente apparecchiati al tavolo", true);
//
//        unCampo = Libreria.getCampo(unSet, RigheMenuTavoloOld.CAMPO_CAMERA);
//        unCampo.setTestoEtichettaScheda("camera / cliente");
//        VideoFactory.legenda(
//                unCampo, "il numero di camera od il nome del cliente", true);
    }

}// fine della classe
