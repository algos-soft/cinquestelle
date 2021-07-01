/**
 * Copyright(c): 2005
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 3-4-05
 */

package it.algos.gestione.tabelle.valuta;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.campo.logica.CampoLogica;
import it.algos.base.errore.Errore;
import it.algos.base.modello.ModelloAlgos;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.pref.Pref;
import it.algos.base.vista.Vista;
import it.algos.base.wrapper.Estratti;
import it.algos.base.wrapper.EstrattoBase;
import it.algos.gestione.indirizzo.tabelle.nazione.Nazione;
import it.algos.gestione.indirizzo.tabelle.nazione.NazioneModulo;

/**
 * Tracciato record della tavola Valuta.
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
public final class ValutaModello extends ModelloAlgos implements Valuta {


    /**
     * Costruttore completo senza parametri.
     */
    public ValutaModello() {

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
        boolean usato;
        Modulo mod;
        Navigatore nav;

        try { // prova ad eseguire il codice
            /* invoca il metodo sovrascritto della superclasse */
            super.creaCampi();

            /* livello di uso del programma */
            usato = (Pref.Gen.livello.comboInt() >= Pref.Livello.medio.ordinal() + 1);

            /* campo valuta */
            unCampo = CampoFactory.testo(Valuta.Cam.valuta);
            unCampo.setVisibileVistaDefault();
            unCampo.setLarghezza(200);
            unCampo.setOrdinePubblico(this.getCampoOrdine());
            unCampo.decora().obbligatorio();
            this.addCampo(unCampo);

            /* campo codice iso */
            unCampo = CampoFactory.testo(Valuta.Cam.codiceIso);
            unCampo.setLarghezza(50);
            unCampo.getCampoLista().setModificabile(false);
            this.addCampo(unCampo);

            /* campo cambio */
            unCampo = CampoFactory.reale(Valuta.Cam.cambio);
            unCampo.getCampoDati().setNumDecimali(4);
            unCampo.setLarLista(75);
            unCampo.setLarScheda(70);
            unCampo.getCampoLista().setModificabile(false);
//                unCampo.decora().azione(new ValutaAzCambio(this.getModulo()));
            if (usato) {
                this.addCampo(unCampo);
            }// fine del blocco if

            /* campo concambio */
            unCampo = CampoFactory.calcola(Valuta.Cam.conCambio,
                    CampoLogica.Calcolo.inverso,
                    Valuta.Cam.cambio);
            unCampo.getCampoLista().setPresenteVistaDefault(false);
            unCampo.getCampoDati().setNumDecimali(4);
            unCampo.getCampoDB().setCampoFisico(false);
            unCampo.setLarghezza(60);
            if (usato) {
                this.addCampo(unCampo);
            }// fine del blocco if

            /* campo navigatore sub-lista */
            mod = NazioneModulo.get();
            if (mod != null) {
                nav = mod.getNavigatore(Nazione.Nav.naz.toString());
                unCampo = CampoFactory.navigatore(Valuta.Cam.subNazioni, nav);
                unCampo.decora().eliminaEtichetta();
                if (Pref.Gen.livello.comboInt() >= Pref.Livello.alto.ordinal() + 1) {
                    this.addCampo(unCampo);
                }// fine del blocco if
            }// fine del blocco if

            /* campo data ultimo cambio */
            unCampo = CampoFactory.data(Cam.dataCambio);
            if (usato) {
                this.addCampo(unCampo);
            }// fine del blocco if

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
     *
     * @see it.algos.base.costante.CostanteModulo#VISTA_BASE_DEFAULT
     * @see it.algos.base.costante.CostanteModello#NOME_CAMPO_SIGLA
     */
    @Override
    protected void creaViste() {
        try { // prova ad eseguire il codice
            /* crea la vista specifica (un solo campo) */
            super.addVista(Valuta.Vis.codiceIso, Valuta.Cam.codiceIso);
            super.addVista(Valuta.Vis.valuta, Valuta.Cam.valuta);

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
        Vista unaVista;
        Campo unCampo;
        String titolo = "valuta";

        try { // prova ad eseguire il codice
            unaVista = this.getVista(Valuta.Vis.codiceIso);
            unCampo = unaVista.getCampo(Valuta.Cam.codiceIso);
            unCampo.setTitoloColonna(titolo);
            unCampo.setLarLista(40);
            unCampo.getCampoLista().setRidimensionabile(false);

            unaVista = this.getVista(Valuta.Vis.valuta);
            unCampo = unaVista.getCampo(Valuta.Cam.valuta);
            unCampo.setTitoloColonna(titolo);
            unCampo.setLarLista(150);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
        //To change body of overridden methods use File | Settings | File Templates.
    }


    /**
     * Restituisce un estratto.
     * </p>
     * Metodo invocato dal modulo <br>
     * Restituisce un estratto conforme al tipo ed al record richiesto <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @param estratto codifica dell'estratto desiderato
     * @param chiave con cui effettuare la ricerca
     *
     * @return l'estratto costruito
     */
    public EstrattoBase getEstratto(Estratti estratto, Object chiave) {
        /* variabili e costanti locali di lavoro */
        EstrattoBase unEst = null;

        try { // prova ad eseguire il codice

            /* selettore della variabile */
            switch ((Valuta.Est)estratto) {
                case codiceIso:
                    unEst = this.getEst(estratto, chiave, Valuta.Cam.codiceIso);
                    break;
                default: // caso non definito
                    break;
            } // fine del blocco switch

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unEst;
    }

} // fine della classe
