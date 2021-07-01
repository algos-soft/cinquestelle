/**
 * Copyright(c): 2006
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 16-5-2007
 */

package it.algos.albergo.prenotazione.periodo.periodoaddebito;

import it.algos.albergo.conto.addebito.Addebito;
import it.algos.albergo.conto.addebitofisso.AddebitoFisso;
import it.algos.albergo.conto.addebitofisso.AddebitoFissoModello;
import it.algos.albergo.conto.movimento.Movimento;
import it.algos.albergo.prenotazione.periodo.Periodo;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.campo.logica.CampoLogica;
import it.algos.base.database.Db;
import it.algos.base.errore.Errore;
import it.algos.base.vista.Vista;
import it.algos.base.vista.VistaElemento;

/**
 * Tracciato record della tavola Periodi.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 16-5-2007
 */
public final class AddebitoPeriodoModello extends AddebitoFissoModello implements AddebitoPeriodo {

    /**
     * Costruttore completo senza parametri.
     */
    public AddebitoPeriodoModello() {
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
     * Regolazioni immediate di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* regola il nome della tavola dalla costante */
        super.setTavolaArchivio(AddebitoPeriodo.NOME_TAVOLA);
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
     */
    protected void creaCampi() {
        /* variabili e costanti locali di lavoro */
        Campo unCampo;
        CampoLogica cl;

        try { // prova ad eseguire il codice
            /* invoca il metodo sovrascritto della superclasse */
            super.creaCampi();

            /* campo link periodo */
            unCampo = CampoFactory.link(AddebitoPeriodo.Cam.periodo);
            unCampo.setNomeModuloLinkato(Periodo.NOME_MODULO);
            unCampo.setAzioneDelete(Db.Azione.cascade);
            this.addCampo(unCampo);

//            /* campo data fine addebito (invisibile, per valorizzazione da trigger) */
//            unCampo = CampoFactory.data(AddebitoFisso.Cam.dataFineAddebito);
//            unCampo.setInit(null);
//            this.addCampo(unCampo);

//            /* campo giorni (differenza date) */
//            unCampo = CampoFactory.calcola(AddebitoPeriodo.Cam.giorni,
//                    CampoLogica.Calcolo.differenzaDate,
//                    AddebitoFisso.Cam.dataInizioValidita.get(),
//                    AddebitoFisso.Cam.dataFineAddebito.get());
//            unCampo.getCampoDB().setCampoFisico(true);
//            this.addCampo(unCampo);
//

            /* campo giorni (differenza date specializzata) */
            unCampo = CampoFactory.intero(AddebitoPeriodo.Cam.giorni);
            cl = new CLCalcGiorni(unCampo,
                    AddebitoFisso.Cam.dataInizioValidita,
                    AddebitoFisso.Cam.dataFineValidita);
            unCampo.setCampoLogica(cl);
            this.addCampo(unCampo);

            /* campo valore della registrazione */
            unCampo = CampoFactory.calcola(AddebitoPeriodo.Cam.valore,
                    CampoLogica.Calcolo.prodottoValuta,
                    AddebitoPeriodo.Cam.giorni.get(),
                    Addebito.Cam.importo.get());
            unCampo.getCampoDB().setCampoFisico(true);
            unCampo.setTotalizzabile(true);
            this.addCampo(unCampo);

            /* campo data ultima sincronizzazione - esistente nella superclasse - lo elimino*/
            this.getCampiModello().remove(AddebitoFisso.Cam.dataSincro.get());

            /* campo conto - esistente nella superclasse - lo elimino*/
            this.getCampiModello().remove(Movimento.Cam.conto.get());

//            /* regola il campo quantità rendendolo modificabile */
//            unCampo = this.getCampo(Addebito.Cam.quantita.get());
//            unCampo.setModificabileLista(true);

            /* regola il campo prezzo rendendolo modificabile */
            unCampo = this.getCampo(Addebito.Cam.prezzo.get());
            unCampo.setModificabileLista(true);

            /* regola il campo importo totale */
            unCampo = this.getCampo(Addebito.Cam.importo.get());
            unCampo.setTotalizzabile(false);

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
        /* variabili e costanti locali di lavoro */
        Vista vista;
        VistaElemento elemento;

        try { // prova ad eseguire il codice
            super.creaViste();

            /* crea la vista per il navigatore nel periodo */
            vista = this.creaVista(AddebitoPeriodo.Vis.periodi.toString());

            vista.addCampo(AddebitoFisso.Cam.dataInizioValidita.get());
            vista.addCampo(AddebitoFisso.Cam.dataFineValidita.get());

//            vista.addCampo(AddebitoFisso.Cam.dataFineAddebito.get());
            vista.addCampo(AddebitoPeriodo.Cam.giorni.get());

            elemento = vista.addCampo(Addebito.Cam.listino.get());
            elemento.setLarghezzaColonna(150);
            elemento.setTitoloColonna("listino");
            vista.addCampo(Addebito.Cam.quantita.get());
            vista.addCampo(Addebito.Cam.prezzo.get());
            vista.addCampo(Addebito.Cam.importo.get());
            vista.addCampo(AddebitoPeriodo.Cam.valore.get());
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
    }

} // fine della classe
