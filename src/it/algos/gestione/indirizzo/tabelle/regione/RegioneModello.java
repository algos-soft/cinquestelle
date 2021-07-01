/**
 * Copyright(c): 2005
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 3-4-05
 */

package it.algos.gestione.indirizzo.tabelle.regione;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.database.Db;
import it.algos.base.errore.Errore;
import it.algos.base.modello.ModelloAlgos;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.pref.Pref;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.vista.Vista;
import it.algos.base.wrapper.Estratti;
import it.algos.base.wrapper.EstrattoBase;
import it.algos.base.wrapper.WrapFiltri;
import it.algos.gestione.indirizzo.tabelle.nazione.Nazione;
import it.algos.gestione.indirizzo.tabelle.nazione.NazioneModulo;
import it.algos.gestione.indirizzo.tabelle.provincia.Provincia;
import it.algos.gestione.indirizzo.tabelle.provincia.ProvinciaModulo;

import java.util.ArrayList;

/**
 * Tracciato record della tavola Regione.
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
public final class RegioneModello extends ModelloAlgos implements Regione {


    /**
     * Costruttore completo senza parametri.
     */
    public RegioneModello() {

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

            /* campo descrizione */
            unCampo = CampoFactory.testo(Cam.regione);
            unCampo.setLarLista(200);
            unCampo.setLarScheda(250);
            unCampo.decora().obbligatorio();
            this.addCampo(unCampo);

            /* campo sigla */
            unCampo = CampoFactory.testo(Cam.sigla);
            unCampo.getCampoDati().setValidatore(null);
            unCampo.setLarghezza(40);
            if (usato) {
                this.addCampo(unCampo);
            }// fine del blocco if

            /* campo link nazione */
            unCampo = CampoFactory.comboLinkSel(Cam.linkNazione);
            unCampo.setNomeModuloLinkato(Nazione.NOME_MODULO);
            unCampo.setNomeVistaLinkata(Nazione.Vis.sigla.toString());
            unCampo.setNomeCampoValoriLinkato(Nazione.Cam.nazione.get());
            unCampo.getCampoDB().setNomeCampoOrdineLinkato(Nazione.Cam.checkEuropa.get());
            unCampo.setAzioneDelete(Db.Azione.setNull);
            unCampo.setUsaNuovo(false);
            unCampo.setLarScheda(150);
            unCampo.decora().estratto(Nazione.Est.sigla);
            this.addCampo(unCampo);

            /* campo navigatore sub-lista */
            mod = ProvinciaModulo.get();
            if (mod != null) {
                nav = mod.getNavigatore(Provincia.Nav.prov.toString());
                unCampo = CampoFactory.navigatore(Cam.subProvince, nav);
                unCampo.decora().eliminaEtichetta();
                if (Pref.Gen.livello.comboInt() >= Pref.Livello.alto.ordinal() + 1) {
                    this.addCampo(unCampo);
                }// fine del blocco if
            }// fine del blocco if

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
        ArrayList<String> unArray;

        try { // prova ad eseguire il codice

            /* crea la vista specifica (piu' campi - uso un array) */
            unArray = new ArrayList<String>();
            unArray.add(Cam.regione.get());
            unArray.add(Cam.linkNazione.get());
            super.addVista(Vis.regioneNazione, unArray);

            super.addVista(Vis.reg.toString(), Cam.regione.get());
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
        Vista vista;
        Campo campo;

        try { // prova ad eseguire il codice
            vista = this.getVista(Vis.regioneNazione);
            campo = vista.getCampo(Cam.regione.get());
            campo.setLarLista(120);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regolazione dei filtri per i popup.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     */
    @Override
    protected void regolaFiltriPop() {
        /* variabili e costanti locali di lavoro */
        WrapFiltri listaFiltri;
        Campo linkNaz;
        ArrayList<Integer> lista;
        String nazione;
        String stringa;
        EstrattoBase est;
        Filtro filtro;
        String regione;
        Modulo mod;

        try { // prova ad eseguire il codice
            mod = NazioneModulo.get();

            listaFiltri = super.addPopFiltro();
            listaFiltri.setTitolo("Nazioni");
            listaFiltri.setTesto("Tutte");
            linkNaz = this.getCampo(Cam.linkNazione.get());

            /* recupera i valori dal campo link */
            lista = this.query().valoriDistintiInt(linkNaz.getNomeInterno());

            /* crea una lista di filtri */
            for (int cod : lista) {
                filtro = FiltroFactory.crea(linkNaz, cod);
                est = NazioneModulo.get().getEstratto(Nazione.Est.descrizione, cod);
                nazione = est.getStringa();
                regione = mod.query().valoreStringa(Nazione.Cam.divisioniUno.get(), cod);
                stringa = nazione + " " + "(" + regione + ")";
                listaFiltri.add(filtro, stringa);
            } // fine del ciclo for-each

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Restituisce un estratto del campo sigla e nazione di un'altra tavola.
     * </p>
     * Stabilisce il campo di legame <br>
     * Recupera il codice <br>
     * Controlla che il tipo di estratto richiesto sia compatibile
     * con quello della tavola puntata <br>
     * Estrae dalla tavola puntata l'estratto richiesto <br>
     *
     * @param tipo codifica dell'estratto desiderato
     * @param chiave con cui effettuare la ricerca
     *
     * @return l'estratto costruito con la stringa ricavata
     */
    private EstrattoBase getEstrattoNazioneSigla(Estratti tipo, Object chiave) {
        /* variabili e costanti locali di lavoro */
        EstrattoBase estratto = null;
        Modulo modulo;
        String campoLink = Cam.linkNazione.get();
        String nomeModulo = Nazione.NOME_MODULO;
        int codNaz;

        try { // prova ad eseguire il codice
            if (Nazione.Est.sigladescrizione.getTipo() == tipo.getTipo()) {
                /* recupera il valore nel campo di link */
                codNaz = this.query().valoreInt(campoLink, (Integer)chiave);

                /* recupera il modulo interessato */
                modulo = Progetto.getModulo(nomeModulo);

                /* invoca il metodo delegato della superclasse */
                estratto = modulo.getEstratto(Nazione.Est.sigladescrizione, codNaz);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return estratto;
    }


    /**
     * Restituisce un estratto con regione e nazione.
     * </p>
     *
     * @param tipo codifica dell'estratto desiderato
     * @param chiave con cui effettuare la ricerca
     *
     * @return l'estratto costruito con la stringa ricavata
     */
    private EstrattoBase getEstrattoDescrizioneNazione(Estratti tipo, Object chiave) {
        /* variabili e costanti locali di lavoro */
        EstrattoBase estratto = null;
        EstrattoBase estrattoNaz;
        EstrattoBase.Tipo tipoEst;
        Estratti tipoNaz = Nazione.Est.descrizione;
        String campo = Cam.regione.get();
        String regione;
        String nazione;
        String testo;

        try { // prova ad eseguire il codice
            /* tipo di estratto codificato */
            tipoEst = tipo.getTipo();

            /* recupera i valori dal database */
            regione = this.query().valoreStringa(campo, (Integer)chiave);

            /* recupera l'estratto, da cui estrae il valore testo */
            estrattoNaz = this.getEstrattoPassante(tipoNaz, Cam.linkNazione.get(), chiave);
            nazione = estrattoNaz.getStringa();

            /* crea la stringa  */
            testo = regione + " - " + nazione;

            /* crea l'estratto */
            estratto = new EstrattoBase(testo, tipoEst);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return estratto;
    }


    /**
     * Restituisce un estratto.
     * </p>
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
        EstrattoBase unEstratto = null;

        try { // prova ad eseguire il codice

            /* selettore della variabile */
            switch ((Est)estratto) {
                case sigla:
                    unEstratto = this.getEstratto(estratto, chiave, Cam.sigla.get());
                    break;
                case descrizione:
                    unEstratto = this.getEstratto(estratto, chiave, Cam.regione.get());
                    break;
                case nazione:
                    unEstratto = this.getEstrattoPassante(Nazione.Est.descrizione,
                            Cam.linkNazione.get(),
                            chiave);
                    break;
                case regioneNazione:
                    unEstratto = this.getEstrattoDescrizioneNazione(estratto, chiave);
                    break;
                default: // caso non definito
                    break;
            } // fine del blocco switch

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unEstratto;
    }

} // fine della classe
