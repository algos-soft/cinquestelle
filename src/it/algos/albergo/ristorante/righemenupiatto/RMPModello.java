/**
 * Copyright: Copyright (c) 2005
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      25-gen-2005
 */
package it.algos.albergo.ristorante.righemenupiatto;

import it.algos.albergo.ristorante.Ristorante;
import it.algos.albergo.ristorante.categoria.Categoria;
import it.algos.albergo.ristorante.categoria.CategoriaModulo;
import it.algos.albergo.ristorante.piatto.Piatto;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.campo.db.CampoDB;
import it.algos.base.database.Db;
import it.algos.base.errore.Errore;
import it.algos.base.modello.ModelloAlgos;
import it.algos.base.modulo.Modulo;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.vista.Vista;

import java.util.ArrayList;

/**
 * Tracciato record della tavola RMP.
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
 * @version 1.0    / 25-gen-2005 ore 18.22.42
 */
public final class RMPModello extends ModelloAlgos implements RMP {

    /**
     * nome della tavola di archivio collegata (facoltativo) <br>
     * i nomi delle tavole sono sempre minuscoli <br>
     * se vuoto usa il nome del modulo <br>
     */
    private static final String TAVOLA_ARCHIVIO = NOME_TAVOLA;


    /**
     * Costruttore completo senza parametri.
     */
    public RMPModello() {
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
     * .
     * <p/>
     */
    public boolean inizializza(Modulo modulo) {
        return super.inizializza(modulo);
    }


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
        Campo unCampo;
        Campo campo;
        CampoDB unCampoDB;

        /* invoca il metodo sovrascritto della superclasse */
        super.creaCampi();

        try { // prova ad eseguire il codice

            /* campo link al record di menu */
            unCampo = CampoFactory.link(CAMPO_MENU);
            unCampo.setVisibileVistaDefault();
            unCampo.setNomeModuloLinkato(Ristorante.MODULO_MENU);
            unCampo.setAzioneDelete(Db.Azione.cascade);
            this.addCampo(unCampo);

            /* campo link al record di piatto */
            unCampo = CampoFactory.comboLinkSel(CAMPO_PIATTO);
            unCampo.setVisibileVistaDefault();
            unCampoDB = unCampo.getCampoDB();
            unCampo.setNomeModuloLinkato(Ristorante.MODULO_PIATTO);
            unCampoDB.setNomeVistaLinkata(Piatto.VISTA_CATEGORIA_NOME);
            unCampoDB.setNomeCampoValoriLinkato(Piatto.CAMPO_NOME_ITALIANO);
            unCampoDB.setRelazionePreferita(true);
            unCampo.decora().obbligatorio();
            unCampo.setLarScheda(300); // lo standard e' solo 200
            unCampo.decora().etichetta("piatto principale");
            unCampo.setUsaNuovo(true);
            campo = Ristorante.Moduli.Piatto().getCampo(Piatto.CAMPO_NOME_ITALIANO);
            unCampo.setOrdineElenco(campo);
            this.addCampo(unCampo);

            /*
            * campo link al record di piatto inteso come contorno
            * usato solo in scheda - in lista se lo costruisce da solo col renderer
            */
            unCampo = CampoFactory.comboLinkSel(CAMPO_CONTORNO);
            unCampoDB = unCampo.getCampoDB();
            unCampoDB.setNomeModuloLinkato(Ristorante.MODULO_PIATTO);
            unCampoDB.setNomeCampoValoriLinkato(Piatto.CAMPO_NOME_ITALIANO);
            campo =
                    Progetto.getModulo(Ristorante.MODULO_CATEGORIA)
                            .getCampo(Categoria.CAMPO_CONTORNO);
            FiltroFactory.link(unCampo, campo, true);
            unCampo.setLarScheda(300); // lo standard e' solo 200
            unCampo.decora().etichetta("contorno");
            unCampo.setUsaNuovo(true);
            campo = Ristorante.Moduli.Piatto().getCampo(Piatto.CAMPO_NOME_ITALIANO);
            unCampo.setOrdineElenco(campo);
            this.addCampo(unCampo);

            /*
             * campo flag piatto principale congelato
             */
            unCampo = CampoFactory.checkBox(CAMPO_PIATTO_CONGELATO);
            unCampo.setLarLista(80);
            unCampo.setTitoloColonna("piatto cong.");
            unCampo.setTestoComponente("congelato");
            unCampo.setModificabileLista(true);
            unCampo.decora().etichetta("_");    // per allineamento in scheda
            this.addCampo(unCampo);

            /*
             * campo flag contorno congelato
             */
            unCampo = CampoFactory.checkBox(CAMPO_CONTORNO_CONGELATO);
            unCampo.setLarLista(80);
            unCampo.setTitoloColonna("cont. cong.");
            unCampo.setTestoComponente("congelato");
            unCampo.setModificabileLista(true);
            unCampo.decora().etichetta("_");    // per allineamento in scheda
            this.addCampo(unCampo);


            this.setCampoOrdineVisibileLista();

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
        Vista vista;
        Modulo moduloCategoria;
        Campo campo;

        try { // prova ad eseguire il codice

            /* crea la vista per le righe nel menu */
            vista = this.creaVista(VISTA_IN_MENU);
            moduloCategoria = CategoriaModulo.get();
            campo = moduloCategoria.getCampo(Categoria.CAMPO_SIGLA);
            vista.addCampo(campo);
            vista.addCampo(this.getCampoChiave());
            vista.addCampo(RMP.CAMPO_PIATTO_CONGELATO);
            vista.addCampo(RMP.CAMPO_CONTORNO_CONGELATO);
            this.addVista(vista);

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
        Vista vista;
        Campo campo;
        Modulo moduloCategoria;
        Campo campoCatSigla;

        try { // prova ad eseguire il codice
            moduloCategoria = Progetto.getModulo(Ristorante.MODULO_CATEGORIA);
            campoCatSigla = moduloCategoria.getCampo(Categoria.CAMPO_SIGLA);

            vista = this.getVista(VISTA_IN_MENU);
            campo = vista.getCampo(campoCatSigla);
            campo.setLarLista(80);
            campo.setRidimensionabile(true);
            campo.setTitoloColonna("categoria");

            campo = vista.getCampo(this.getCampoChiave());
            campo.setLarLista(300);
            campo.setRidimensionabile(true);
            campo.setTitoloColonna("piatto");
            campo.getCampoDati().setRenderer(new RendererPiattoContorno());

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
            unSet.add(CAMPO_PIATTO);
            unSet.add(CAMPO_CONTORNO);
            unSet.add(CAMPO_PIATTO_CONGELATO);
            unSet.add(CAMPO_CONTORNO_CONGELATO);
            super.creaSet(SET_PIATTO_CONTORNO, unSet);

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
    } /* fine del metodo */

}// fine della classe
