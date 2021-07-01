/**
 * Copyright: Copyright (c) 2005
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      22-feb-2005
 */
package it.algos.albergo.ristorante.righemenuordini;

import it.algos.albergo.ristorante.Ristorante;
import it.algos.albergo.ristorante.categoria.Categoria;
import it.algos.albergo.ristorante.piatto.Piatto;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.database.Db;
import it.algos.base.errore.Errore;
import it.algos.base.modello.ModelloAlgos;
import it.algos.base.modulo.Modulo;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.vista.Vista;
import it.algos.base.vista.VistaElemento;

import javax.swing.SwingConstants;
import java.util.ArrayList;

/**
 * Tracciato record della tavola RMO.
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
 * @version 1.0    / 22-feb-2005 ore 10.16.31
 */
public final class RMOModello extends ModelloAlgos implements RMO {

    /**
     * nome della tavola di archivio collegata (facoltativo) <br>
     * i nomi delle tavole sono sempre minuscoli <br>
     * se vuoto usa il nome del modulo <br>
     */
    private static final String TAVOLA_ARCHIVIO = NOME_TAVOLA;


    /**
     * Costruttore completo senza parametri.
     */
    public RMOModello() {
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

            /* campo link a righe menu tavolo (RMT)*/
            unCampo = CampoFactory.link(CAMPO_RIGA_MENU_TAVOLO);
            unCampo.getCampoDB().setNomeModuloLinkato(Ristorante.MODULO_RIGHE_TAVOLO);
            unCampo.setAzioneDelete(Db.Azione.cascade);
            unCampo.setVisibileVistaDefault();
            this.addCampo(unCampo);

            /* campo link a righe menu piatto (RMP)*/
            unCampo = CampoFactory.link(CAMPO_RIGA_MENU_PIATTO);
            unCampo.getCampoDB().setNomeModuloLinkato(Ristorante.MODULO_RIGHE_PIATTO);
            unCampo.setAzioneDelete(Db.Azione.cascade);
            unCampo.setVisibileVistaDefault();
            this.addCampo(unCampo);

            /* campo quantita' ordinata */
            unCampo = CampoFactory.intero(CAMPO_QUANTITA);
            unCampo.setVisibileVistaDefault();
            unCampo.setModificabileLista(true);
            unCampo.setTitoloColonna("q.ta");
            unCampo.setLarLista(50);
            unCampo.setTotalizzabile(true);
            unCampo.setAllineamento(SwingConstants.LEFT);   // per non perdere la selezione quando si clicca in editing lista
            this.addCampo(unCampo);

            /* campo link all'eventuale piatto extra */
            unCampo = CampoFactory.comboLinkSel(CAMPO_PIATTO_EXTRA);
            unCampo.setNomeModuloLinkato(Ristorante.MODULO_PIATTO);
            unCampo.getCampoDB().setRelazionePreferita(true);
            unCampo.setAzioneDelete(Db.Azione.setNull);
            unCampo.getCampoDB().setNomeCampoValoriLinkato(Piatto.CAMPO_NOME_ITALIANO);
            unCampo.setLarghezza(200);
            unCampo.decora().etichetta("piatto extra");
            this.addCampo(unCampo);

            /* campo link all'eventuale contorno extra */
            unCampo = CampoFactory.comboLinkSel(CAMPO_CONTORNO_EXTRA);
            unCampo.setNomeModuloLinkato(Ristorante.MODULO_PIATTO);
            unCampo.setAzioneDelete(Db.Azione.setNull);
            unCampo.getCampoDB().setNomeCampoValoriLinkato(Piatto.CAMPO_NOME_ITALIANO);
            unCampo.setLarghezza(200);
            unCampo.decora().etichetta("contorno extra");
            this.addCampo(unCampo);


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    public boolean inizializza(Modulo unModulo) {
        /* variabili e costanti locali di lavoro */
        Campo campo;
        Campo campoFiltro;
        Filtro filtro;

        try { // prova ad eseguire il codice

            /* mette un filtro al popup contorno extra
             * per vedere solo i contorni */
            campo = this.getCampo(CAMPO_CONTORNO_EXTRA);
            campoFiltro =
                    Progetto.getModulo(Ristorante.MODULO_CATEGORIA)
                            .getCampo(Categoria.CAMPO_CONTORNO);
            filtro = FiltroFactory.crea(campoFiltro, true);
            campo.setFiltroCorrente(filtro);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        return super.inizializza(unModulo);

    } /* fine del metodo */


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
     *
     * @see #regolaViste
     */
    protected void creaViste() {
        /* variabili e costanti locali di lavoro */
        Vista vista = null;
        VistaElemento elem;

        try { // prova ad eseguire il codice

            /* Vista nel navigatore nel campo del menu */
            vista = this.creaVista(VISTA_IN_MENU);
            elem = vista.addCampo(this.getCampoChiave());
            elem.setRidimensionabile(true);
            elem.setLarghezzaColonna(300);
            elem.setTitoloColonna("piatto");
            vista.addCampo(CAMPO_QUANTITA);

            super.addVista(vista);


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
     *
     * @see #creaViste
     */
    protected void regolaViste() {
        /* variabili e costanti locali di lavoro */
        Vista vista = null;
        Campo campo = null;

        try { // prova ad eseguire il codice

            vista = this.getVista(VISTA_IN_MENU);
            campo = vista.getCampo(this.getCampoChiave());
            campo.getCampoDati().setRenderer(new RMORendererComanda());
            campo.setOrdinePrivato(this.getCampoOrdine());

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
     *
     * @see #regolaSet
     */
    protected void creaSet() {
        /* variabili e costanti locali di lavoro */
        ArrayList unSet = null;

        try {    // prova ad eseguire il codice

            /* crea il set specifico (piu' campi - uso un array) */
            unSet = new ArrayList();
            unSet.add(CAMPO_SIGLA);
            unSet.add(CAMPO_DESCRIZIONE);
//            super.creaSet(SET_xxx, unSet);

            unSet = new ArrayList();
            unSet.add(CAMPO_PIATTO_EXTRA);
            unSet.add(CAMPO_CONTORNO_EXTRA);
            super.creaSet(SET_EXTRA, unSet);

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
     *
     * @see #creaSet
     */
    protected void regolaSet() {
    } /* fine del metodo */


}// fine della classe
