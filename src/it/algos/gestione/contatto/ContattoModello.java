/**
 * Title:     ContattoModello
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      28-apr-2004
 */
package it.algos.gestione.contatto;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.campo.db.CampoDB;
import it.algos.base.database.Db;
import it.algos.base.errore.Errore;
import it.algos.base.modello.ModelloAlgos;
import it.algos.base.modulo.Modulo;
import it.algos.base.vista.Vista;
import it.algos.gestione.anagrafica.Anagrafica;
import it.algos.gestione.contatto.tabelle.TipoContatto;
import it.algos.gestione.contatto.tabelle.TipoContattoModulo;

/**
 * Tracciato record della tavola ContattoModello.
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
 * @version 1.0    / 28-apr-2004 ore 7.57.21
 */
public final class ContattoModello extends ModelloAlgos implements Contatto {


    /**
     * Costruttore completo senza parametri.<br>
     */
    public ContattoModello() {
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
     * Regolazioni immediate di riferimenti e variabili. <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
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
        Campo unCampo;
        CampoDB unCampoDB;

        try { // prova ad eseguire il codice
            /* invoca il metodo sovrascritto della superclasse */
            super.creaCampi();

            /* campo link al record di anagrafica */
            unCampo = CampoFactory.link(Cam.angrafica);
            unCampoDB = unCampo.getCampoDB();
            unCampoDB.setNomeModuloLinkato(Anagrafica.NOME_MODULO);
            unCampo.setAzioneDelete(Db.Azione.cascade);
            unCampo.getCampoScheda().setPresenteScheda(false);
            this.addCampo(unCampo);

            /* campo link tipo */
            unCampo = CampoFactory.comboLinkPop(Cam.tipo);
            unCampo.setLarScheda(90);
            unCampo.setRidimensionabile(false);
            unCampoDB = unCampo.getCampoDB();
            unCampoDB.setNomeModuloLinkato(TipoContatto.NOME_MODULO);
            unCampoDB.setNomeColonnaListaLinkata(TipoContatto.CAMPO_SIGLA);
            unCampoDB.setNomeCampoValoriLinkato(TipoContatto.CAMPO_SIGLA);
            this.addCampo(unCampo);

            /* campo contatto */
            unCampo = CampoFactory.testo(Cam.contatto);
            unCampo.setLarghezza(180);
            this.addCampo(unCampo);

            /* campo link luoghi */
//            unCampo = CampoFactory.comboLink(Cam.luogo);
//            this.addCampo(unCampo);

            /* campo "preferito" */
            this.setUsaCampoPreferito(true);

            /* rende visibile il campo ordine */
//            super.setCampoOrdineVisibileLista(); //

            /* uso del campo note */
//            super.setUsaCampoNote(true);
            unCampo = super.creaCampoNote();
            unCampo.setLarghezza(180);
            unCampo.setVisibileVistaDefault();

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
    @Override
    protected void creaViste() {
        /* variabili e costanti locali di lavoro */
        Vista vista;

        try { // prova ad eseguire il codice

            /* vista per la lista indirizzi nella scheda anagrafica */
            vista = new Vista(Contatto.Vis.contattiInAnag.toString(), this.getModulo());
            vista.addCampo(Contatto.Cam.tipo.get());
            vista.addCampo(Contatto.Cam.contatto.get());
            vista.addCampo(Contatto.Cam.note.get());
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
     *
     * @see it.algos.base.costante.CostanteModulo#VISTA_BASE_DEFAULT
     * @see it.algos.base.costante.CostanteModello#NOME_CAMPO_SIGLA
     */
    @Override
    protected void regolaViste() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Modulo modTipo;
        Vista unaVista = null;
        Campo unCampo;

        try { // prova ad eseguire il codice

            modTipo = TipoContattoModulo.get();
            continua = (modTipo != null);

            /* regola la vista specifica */
            if (continua) {
                unaVista = this.getVista(Contatto.Vis.contattiInAnag.toString());
                continua = (unaVista != null);
            }// fine del blocco if

            /* campo tipo */
            if (continua) {
                unCampo = modTipo.getCampo(TipoContatto.Cam.sigla.get());
                unCampo = unaVista.getCampo(unCampo);
                if (unCampo != null) {
                    unCampo.setTitoloColonna("tipo");
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }
}// fine della classe
