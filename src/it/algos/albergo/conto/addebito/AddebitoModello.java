/**
 * Copyright(c): 2006
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 2 feb 2006
 */

package it.algos.albergo.conto.addebito;

import it.algos.albergo.Albergo;
import it.algos.albergo.conto.Conto;
import it.algos.albergo.conto.ContoModulo;
import it.algos.albergo.conto.movimento.Movimento;
import it.algos.albergo.conto.movimento.MovimentoModello;
import it.algos.albergo.listino.Listino;
import it.algos.albergo.listino.ListinoModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.campo.logica.CampoLogica;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.vista.Vista;
import it.algos.base.vista.VistaElemento;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Tracciato record della tavola Addebito.
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
 * @version 1.0 / 2 feb 2006
 */
public class AddebitoModello extends MovimentoModello implements Addebito {


    /**
     * Costruttore completo senza parametri.
     */
    public AddebitoModello() {
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
        super.setTavolaArchivio(Addebito.NOME_TAVOLA);

        /* campo totale sincronizzato */
        this.setCampoContoSync(Conto.Cam.totImporto);

    }


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

            /* campo listino */
            unCampo = CampoFactory.comboLinkSel(Addebito.Cam.listino);
            unCampo.setVisibileVistaDefault();
            unCampo.setNomeModuloLinkato(Listino.NOME_MODULO);
            unCampo.setNomeColonnaListaLinkata(Listino.Cam.descrizione.get());
            unCampo.setFiltroBase(FiltroFactory.creaFalso(Listino.Cam.disattivato.get()));
            unCampo.decora().obbligatorio();
            unCampo.decora().estrattoSotto(Listino.Estratto.descrizioneCameraPersona);
            unCampo.setLarScheda(150);
            unCampo.setRicercabile(true);
            unCampo.addColonnaCombo(Listino.Cam.descrizione.get());
            this.addCampo(unCampo);

            /* campo riga di listino */
            unCampo = CampoFactory.intero(Addebito.Cam.codRigaListino);
            this.addCampo(unCampo);

            /* campo quantità */
            unCampo = CampoFactory.intero(Addebito.Cam.quantita);
            unCampo.setVisibileVistaDefault(true);
            unCampo.decora().obbligatorio();
            unCampo.getValidatore().setAccettaNegativi(false);
            this.addCampo(unCampo);

            /* campo prezzo */
            unCampo = CampoFactory.valuta(Addebito.Cam.prezzo);
            unCampo.setVisibileVistaDefault(true);
            unCampo.decora().obbligatorio();
            ((DecimalFormat)unCampo.getFormat()).setMinimumFractionDigits(2);
            ((DecimalFormat)unCampo.getFormat()).setMaximumFractionDigits(2);
            this.addCampo(unCampo);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Crea il campo Importo.
     * <p/>
     * Per questo modello, l'importo è calcolato (q.tà x prezzo unitario)
     *
     * @return il campo Importo creato
     */
    protected Campo creaCampoImporto() {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;

        try {    // prova ad eseguire il codice

            unCampo = CampoFactory.calcola(Addebito.Cam.importo,
                    CampoLogica.Calcolo.prodottoValuta,
                    Addebito.Cam.quantita.get(),
                    Addebito.Cam.prezzo.get());

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return unCampo;
    }


    /**
     * Crea la vista di default.
     * <p/>
     * Metodo invocato dal ciclo di preparazione dei moduli <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @return la vista creata
     */
    @Override
    protected Vista creaVistaDefault() {
        /** variabili e costanti locali di lavoro */
        Vista vista = null;

        try {    // prova ad eseguire il codice

            /* crea una vista vuota */
            vista = new Vista(this.getModulo());

            /* aggiunge i campi desiderati */
            vista.addCampo(Movimento.Cam.data.get());
            vista.addCampo(Movimento.Cam.conto.get());
            vista.addCampo(Addebito.Cam.listino.get());
            vista.addCampo(Addebito.Cam.quantita.get());
            vista.addCampo(Addebito.Cam.prezzo.get());
            vista.addCampo(Movimento.Cam.importo.get());
            vista.addCampo(Movimento.Cam.note.get());

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return vista;

    } /* fine del metodo */


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
        Campo campo;

        /* invoca il metodo sovrascritto della superclasse */
        super.creaViste();

        try { // prova ad eseguire il codice

            /* vista dall'interno del conto */
            vista = this.creaVista(Addebito.Vis.vistaConto.get());
            vista.addCampo(Addebito.Cam.data.get());
            campo = Albergo.Moduli.ListinoBase().getCampo(Listino.Cam.descrizione.get());
            elemento = vista.addCampo(campo);
            elemento.setTitoloColonna("listino");
            elemento.setLarghezzaColonna(200);
//            vista.addCampo(Addebito.Cam.descrizione.get());
            elemento = vista.addCampo(Addebito.Cam.quantita.get());
            elemento = vista.addCampo(Addebito.Cam.prezzo.get());
            elemento = vista.addCampo(Addebito.Cam.importo.get());
            elemento.setTitoloColonna("importo");
            elemento.setLarghezzaColonna(60);
            elemento = vista.addCampo(Addebito.Cam.note.get());
            this.addVista(vista);

            /* vista nel dialogo di esecuzione addebiti multipli */
            vista = this.creaVista(Addebito.Vis.vistaDialogo.get());
            elemento = vista.addCampo(Addebito.Cam.data.get());
            elemento = vista.addCampo(Addebito.Cam.conto.get());
            elemento.setLarghezzaColonna(110);
            elemento = vista.addCampo(Addebito.Cam.listino.get());
            elemento.setLarghezzaColonna(110);
            elemento = vista.addCampo(Addebito.Cam.quantita.get());
            elemento = vista.addCampo(Addebito.Cam.prezzo.get());
            elemento = vista.addCampo(Addebito.Cam.importo.get());
            elemento = vista.addCampo(Addebito.Cam.note.get());
            elemento.setLarghezzaColonna(110);
            this.addVista(vista);


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


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
        Campo campoChiaveAddebito;
        Campo campoSottocOrdine;
        Campo campoSiglaConto;
        Campo campoDescListino;
        Modulo modConto;
        Modulo modListino;
        Modulo modSottoconto;

        /* invoca il metodo sovrascritto della superclasse */
        super.regolaViste();

        try { // prova ad eseguire il codice

            /* vista default - regola il nome della colonna conto */
            vista = this.getVistaDefault();

            modConto = ContoModulo.get();
            campoSiglaConto = modConto.getCampo(Conto.Cam.sigla.get());
            campo = vista.getCampo(campoSiglaConto);
            campo.setTitoloColonna("conto cliente");
            campo.setLarLista(150);

            modListino = ListinoModulo.get();
            campoDescListino = modListino.getCampo(Listino.Cam.descrizione.get());
            campo = vista.getCampo(campoDescListino);
            campo.setLarLista(200);

            /* vista in conto */
            vista = this.getVista(Addebito.Vis.vistaConto.get());
            campo = vista.getCampo(Addebito.Cam.data.get());
            modSottoconto = Albergo.Moduli.AlbSottoconto();
            campoSottocOrdine = modSottoconto.getCampoOrdine();
            campoChiaveAddebito = this.getCampoChiave();
            campo.getCampoLista().addOrdinePrivato(campoSottocOrdine);
            campo.getCampoLista().addOrdinePrivato(campoChiaveAddebito);


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

        /* invoca il metodo sovrascritto della superclasse */
        super.creaSet();

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
