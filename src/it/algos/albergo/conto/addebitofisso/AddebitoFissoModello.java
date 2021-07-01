/**
 * Copyright(c): 2006
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 21 apr 2006
 */

package it.algos.albergo.conto.addebitofisso;

import it.algos.albergo.conto.Conto;
import it.algos.albergo.conto.ContoModulo;
import it.algos.albergo.conto.addebito.Addebito;
import it.algos.albergo.conto.addebito.AddebitoModello;
import it.algos.albergo.listino.Listino;
import it.algos.albergo.listino.ListinoModulo;
import it.algos.albergo.pianodeicontialbergo.sottoconto.AlbSottocontoModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.vista.Vista;
import it.algos.base.vista.VistaElemento;

import java.util.ArrayList;

/**
 * Tracciato record della tavola Pensione.
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
 * @version 1.0 / 21 apr 2006
 */
public class AddebitoFissoModello extends AddebitoModello {

    /**
     * Testo dei campi nella scheda
     */
    private static final String TESTO_CREAZIONE = "data creazione";


    /**
     * Costruttore completo senza parametri.
     */
    public AddebitoFissoModello() {
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
        super.setTavolaArchivio(AddebitoFisso.NOME_TAVOLA);

        /* non ha nessun campo totale sincronizzato nel conto */
        this.setCampoContoSync(null);

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
        Campo unCampo;

        /* invoca il metodo sovrascritto della superclasse */
        super.creaCampi();

        try { // prova ad eseguire il codice

            /* campo data - esistente nella superclasse - lo nascondo in lista */
            unCampo = this.getCampo(Addebito.Cam.data.get());
            unCampo.setVisibileVistaDefault(false);
            unCampo.decora().etichetta(TESTO_CREAZIONE);
            this.addCampo(unCampo);

            /* campo data inizio validita */
            unCampo = CampoFactory.data(AddebitoFisso.Cam.dataInizioValidita);
            this.addCampo(unCampo);

            /* campo data fine validita */
            unCampo = CampoFactory.data(AddebitoFisso.Cam.dataFineValidita);
            this.addCampo(unCampo);

            /* campo data ultima sincronizzazione */
            unCampo = CampoFactory.data(AddebitoFisso.Cam.dataSincro);
            unCampo.setInit(null);
            this.addCampo(unCampo);

//            /* campo descrizione - esistente nella superclasse - lo nascondo */
//            unCampo = this.getCampo(Addebito.Cam.descrizione.get());
//            unCampo.setVisibileVistaDefault(false);
//            unCampo.setPresenteScheda(false);
//            unCampo.decora().eliminaObbligatorio();
//            this.addCampo(unCampo);

//            /* campo camera - esistente nella superclasse - lo nascondo */
//            unCampo = this.getCampo(Addebito.Cam.camera.get());
//            unCampo.setPresenteScheda(false);
//            this.addCampo(unCampo);

//            /* campo cliente - esistente nella superclasse - lo nascondo */
//            unCampo = this.getCampo(Addebito.Cam.cliente.get());
//            unCampo.setPresenteScheda(false);
//            this.addCampo(unCampo);

            /* campo quantita - esistente nella superclasse - lo mostro in lista */
            unCampo = this.getCampo(Addebito.Cam.quantita.get());
            unCampo.setVisibileVistaDefault();
            this.addCampo(unCampo);

            /* campo prezzo - esistente nella superclasse - lo mostro in lista */
            unCampo = this.getCampo(Addebito.Cam.prezzo.get());
            unCampo.setVisibileVistaDefault();
            this.addCampo(unCampo);

            /* campo totale - esistente nella superclasse - lo nascondo */
            unCampo = this.getCampo(Addebito.Cam.importo.get());
            unCampo.setVisibileVistaDefault(false);
            unCampo.setPresenteScheda(false);
            this.addCampo(unCampo);

            /* campo note in basso */
            super.setUsaCampoNote(false);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Creazione delle viste aggiuntive.
     * <p/>
     * Metodo invocato dal ciclo statico del progetto <br>
     * Eventuale creazione di viste aggiuntive, oltre alla vista base di default <br>
     * Costruisce degli ArrayList di riferimenti ordinati (oggetti <code>Vista</code>)
     * per individuare i campi che voglio vedere nelle liste alternative ed
     * aggiuntive a quella standard (costruita in automatico nella superclasse) <br>
     * Gli array vengono creati coi campi di questo modello, oppure con
     * viste di altri moduli, oppure con campi di altri modelli <br>
     * Viene chiamato <strong>dopo</strong> che nella sottoclasse sono stati
     * costruiti tutti i campi <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     *
     * @see it.algos.base.progetto.Progetto#preparaModuli()
     * @see #regolaViste
     */
    protected void creaViste() {
        Vista vista;
        VistaElemento elemento;
        Modulo modListino;
        Campo campoDescListino;

        try { // prova ad eseguire il codice

            modListino = ListinoModulo.get();
            campoDescListino = modListino.getCampo(Listino.Cam.descrizione.get());

            /* crea la vista per il navigatore nel conto */
            vista = this.creaVista(AddebitoFisso.Vis.vistaConto.get());

            if (campoDescListino != null) {
                elemento = vista.addCampo(campoDescListino);
                elemento.setTitoloColonna("listino");
            }// fine del blocco if
            vista.addCampo(AddebitoFisso.Cam.dataInizioValidita.get());
            vista.addCampo(AddebitoFisso.Cam.dataFineValidita.get());
            vista.addCampo(AddebitoFisso.Cam.dataSincro.get());
            vista.addCampo(Addebito.Cam.quantita.get());
            vista.addCampo(Addebito.Cam.prezzo.get());
            vista.addCampo(Addebito.Cam.importo.get());
            this.addVista(vista);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Crea la Vista di default
     *
     * @return la Vista creata
     */
    protected Vista creaVistaDefault() {
        /** variabili e costanti locali di lavoro */
        Vista vista = null;

        try {    // prova ad eseguire il codice

            /* crea una vista vuota */
            vista = new Vista(this.getModulo());

            /* aggiunge i campi desiderati */
            vista.addCampo(AddebitoFisso.Cam.dataInizioValidita.get());
            vista.addCampo(AddebitoFisso.Cam.dataFineValidita.get());
            vista.addCampo(Addebito.Cam.conto.get());
            vista.addCampo(Addebito.Cam.listino.get());
            vista.addCampo(Addebito.Cam.quantita.get());
            vista.addCampo(Addebito.Cam.prezzo.get());
            vista.addCampo(Addebito.Cam.importo.get());

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return vista;

    } /* fine del metodo */


    /**
     * Regolazione delle viste aggiuntive.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Eventuale regolazione delle caratteristiche specifiche di ogni copia dei
     * campi delle viste; le variazioni modificano <strong>solo</strong> le copie <br>
     * Viene chiamato <strong>dopo</strong> che nella superclasse sono state
     * <strong>clonate</strong> tutte le viste <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     *
     * @see #creaViste
     */
    protected void regolaViste() {
        /* variabili e costanti locali di lavoro */
        Vista vista;
        Campo campo;
        Ordine ordine;
        Modulo modListino;
        Modulo modConto;
        Modulo modSottoconto;
        Campo campoOrdSottoconto;

        try { // prova ad eseguire il codice

            modConto = ContoModulo.get();
            modListino = ListinoModulo.get();
            modSottoconto = AlbSottocontoModulo.get();

            /* regola la vista di default */
            vista = this.getVistaDefault();

            campo = modConto.getCampo(Conto.Cam.sigla.get());
            campo = vista.getCampo(campo);
            campo.setTitoloColonna("conto");
            campo.setLarLista(150);

            campo = modListino.getCampo(Listino.Cam.descrizione.get());
            campo = vista.getCampo(campo);
            campo.setLarLista(200);

            campo = vista.getCampo(Addebito.Cam.quantita.get());
            campo.setTitoloColonna("Q.tà");
            campo.setLarLista(30);

            /* regola la vista per il navigatore nel conto */
            campoOrdSottoconto = modSottoconto.getCampoOrdine();
            vista = this.getVista(AddebitoFisso.Vis.vistaConto.get());
            campo = modListino.getCampo(Listino.Cam.descrizione.get());
            campo = vista.getCampo(campo);
            ordine = new Ordine();
            ordine.add(campoOrdSottoconto);
            ordine.add(AddebitoFisso.Cam.dataInizioValidita.get());
            if (campo != null) {
                campo.setOrdine(ordine);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * .
     * <p/>
     */
    protected void creaSet() {
        /* variabili e costanti locali di lavoro */
        ArrayList<Campo> set;

        try {    // prova ad eseguire il codice
            set = new ArrayList<Campo>();

            set.add(this.getCampo(Addebito.Cam.data.get()));
//            set.add(this.getCampo(Addebito.Cam.camera.get()));
//            set.add(this.getCampo(Addebito.Cam.cliente.get()));
            set.add(this.getCampo(Addebito.Cam.listino.get()));
            set.add(this.getCampo(Addebito.Cam.note.get()));
            set.add(this.getCampo(Addebito.Cam.quantita.get()));
            set.add(this.getCampo(Addebito.Cam.prezzo.get()));
            set.add(this.getCampo(Addebito.Cam.importo.get()));

            this.addSet(Addebito.SET_CONTO, set);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    }

} // fine della classe
