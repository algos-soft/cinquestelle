/**
 * Copyright(c): 2005
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 3-4-05
 */

package it.algos.gestione.indirizzo.tabelle.provincia;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.database.Db;
import it.algos.base.errore.Errore;
import it.algos.base.modello.ModelloAlgos;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.pref.Pref;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.vista.Vista;
import it.algos.base.wrapper.Estratti;
import it.algos.base.wrapper.EstrattoBase;
import it.algos.base.wrapper.WrapFiltri;
import it.algos.gestione.indirizzo.tabelle.citta.Citta;
import it.algos.gestione.indirizzo.tabelle.citta.CittaModulo;
import it.algos.gestione.indirizzo.tabelle.nazione.Nazione;
import it.algos.gestione.indirizzo.tabelle.nazione.NazioneModulo;

import java.util.ArrayList;

/**
 * Tracciato record della tavola Provincia.
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
public final class ProvinciaModello extends ModelloAlgos implements Provincia {


    /**
     * Costruttore completo senza parametri.
     */
    public ProvinciaModello() {

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
     * Regolazioni iniziali, dopo che sono stati regolati dalla sottoclasse
     * i parametri indispensabili (tra cui il riferimento al modulo)
     * Metodo chiamato dalla classe che crea questo oggetto
     * Viene eseguito una sola volta
     *
     * @param unModulo Abstract Data Types per le informazioni di un modulo
     */
    @Override public boolean inizializza(Modulo unModulo) {
        /* variabili e costanti locali di lavoro */
        boolean inizializzato = false;

        try { // prova ad eseguire il codice
            inizializzato = super.inizializza(unModulo);
            super.setCampoOrdineIniziale(Cam.nomeCorrente);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return inizializzato;
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
     */
    protected void creaCampi() {
        /* variabili e costanti locali di lavoro */
        Campo unCampo;
        Modulo mod;
        Navigatore nav;

        try { // prova ad eseguire il codice
            /* invoca il metodo sovrascritto della superclasse */
            super.creaCampi();

            /* allarga il campo ordine progressivo a sinistra */
            unCampo = this.getCampo(NOME_CAMPO_ORDINE);
            unCampo.setLarLista(40);

            /* campo nome corrente */
            unCampo = CampoFactory.testo(Cam.nomeCorrente);
            unCampo.setLarLista(150);
            unCampo.setLarScheda(250);
            unCampo.setOrdinePubblico(this.getCampoOrdine());
            this.addCampo(unCampo);

            /* campo nome completo */
            unCampo = CampoFactory.testo(Cam.nomeCompleto);
            unCampo.setLarScheda(250);
            unCampo.decora().obbligatorio();
            this.addCampo(unCampo);

            /* campo sigla postale (ex-targa) */
            unCampo = CampoFactory.testo(Cam.sigla);
            unCampo.setLarghezza(45);
            this.addCampo(unCampo);

            /* campo sigla ISO */
            unCampo = CampoFactory.testo(Cam.iso);
            unCampo.setVisibileVistaDefault(false);
            unCampo.setLarghezza(60);
            this.addCampo(unCampo);

            /* campo regione */
            unCampo = CampoFactory.testo(Cam.regioneBreve);
            unCampo.setLarLista(150);
            unCampo.setLarScheda(250);
            this.addCampo(unCampo);

            /* campo regione */
            unCampo = CampoFactory.testo(Cam.regioneCompleto);
            unCampo.setVisibileVistaDefault(false);
            unCampo.setLarScheda(250);
            this.addCampo(unCampo);

            /* campo link stato */
            unCampo = CampoFactory.comboLinkSel(Cam.linkNazione);
            unCampo.setNomeModuloLinkato(Nazione.NOME_MODULO);
            unCampo.setNomeVistaLinkata(Nazione.Vis.sigla.toString());
            unCampo.setNomeCampoValoriLinkato(Nazione.Cam.nazione.get());
            unCampo.setAzioneDelete(Db.Azione.setNull);
            unCampo.setUsaNuovo(false);
            unCampo.setLarScheda(250);
            unCampo.decora().estratto(Nazione.Est.sigla);
            this.addCampo(unCampo);

            /* campo navigatore sub-lista */
            mod = CittaModulo.get();
            if (mod != null) {
                nav = mod.getNavigatore(Citta.Nav.citta.toString());
                unCampo = CampoFactory.navigatore(Cam.subCitta, nav);
                unCampo.decora().eliminaEtichetta();
                if (Pref.Gen.livello.comboInt() >= Pref.Livello.alto.ordinal() + 1) {
                    this.addCampo(unCampo);
                }// fine del blocco if
            }// fine del blocco if

            /* campo note standard in basso */
            super.setUsaCampoNote(true);

            /* rende visibile il campo ordine */
            super.setCampoOrdineVisibileLista(false); //

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
            unArray.add(Cam.sigla.get());
            unArray.add(Cam.linkNazione.get());
            super.addVista(Vis.siglaNazione, unArray);

            super.addVista(Vis.prov.toString(), Cam.nomeCorrente.get());
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
        Vista unaVista;
        Campo unCampo;

        try { // prova ad eseguire il codice

            unaVista = this.getVista(Vis.siglaNazione);
            unCampo = unaVista.getCampo(Cam.sigla.get());
            unCampo.setTitoloColonna("pv");
            unCampo.setLarLista(25);
            unCampo.setRidimensionabile(false);

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
     * <p/>
     * Crea uno o più filtri alla lista, tramite un popup posizionato in basso a destra <br>
     * I popup si posizionano bandierati a destra,
     * ma iniziando da sinistra (secondo l'ordine di creazione) <br>
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
        Modulo mod;

        try { // prova ad eseguire il codice
            mod = NazioneModulo.get();

            listaFiltri = super.addPopFiltro();
            listaFiltri.setTitolo("Nazioni");
            listaFiltri.setTesto("Tutte");
            linkNaz = this.getCampo(Provincia.Cam.linkNazione.get());

            /* recupera i valori dal campo link */
            lista = this.query().valoriDistintiInt(linkNaz.getNomeInterno());

            /* crea una lista di filtri */
            for (int cod : lista) {
                filtro = FiltroFactory.crea(linkNaz, cod);
                est = mod.getEstratto(Nazione.Est.descrizione, cod);
                nazione = est.getStringa();
                stringa = nazione;
                listaFiltri.add(filtro, stringa);
            } // fine del ciclo for-each

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Restituisce un estratto del campo sigla.
     * </p>
     * Invoca il metodo delegato della superclasse <br>
     *
     * @param tipo codifica dell'estratto desiderato
     * @param chiave con cui effettuare la ricerca
     *
     * @return l'estratto costruito con la stringa ricavata
     */
    private EstrattoBase getEstrattoRegioneNazione(Estratti tipo, Object chiave) {
        /* variabili e costanti locali di lavoro */
        EstrattoBase estratto = null;
        EstrattoBase.Tipo tipoEst;
        Modulo modRegione;
        String modulo;

        try { // prova ad eseguire il codice
            /* tipo di estratto codificato */
            tipoEst = tipo.getTipo();

//            if (tipoEst == EstrattoBase.Tipo.STRINGA) {
//                modulo = Regione.Estratto.regioneNazione.getNomeModulo();
//                modRegione = Progetto.getModulo(modulo);
//
//                /* recupera l'estratto */
//                if (modRegione != null) {
//                    estratto = modRegione.getEstratto(
//                            Regione.Estratto.regioneNazione, chiave);
//                }// fine del blocco if
//
//            }// fine del blocco if
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
            switch ((Provincia.Est)estratto) {
                case descrizione:
                    unEstratto = this.getEstratto(estratto, chiave, Cam.nomeCorrente.get());
                    break;
                case sigla:
                    unEstratto = this.getEstratto(estratto, chiave, Cam.sigla.get());
                    break;
                case regione:
                    unEstratto = this.getEstratto(estratto, chiave, Cam.regioneBreve.get());
                    break;
                case regioneCompleto:
                    unEstratto = this.getEstratto(estratto, chiave, Cam.regioneCompleto.get());
                    break;
                case nazione:
                    unEstratto = this.getEstrattoPassante(Nazione.Est.descrizione,
                            Cam.linkNazione.get(),
                            chiave);
                    break;
                case regioneNazione:
//                    unEstratto = this.getEstrattoPassante(Regione.Est.regioneNazione, Cam.linkRegione.get(), chiave);
                    break;
                case provinciaRegioneNazione:
//                    unEstratto =
//                            this.getEstrattoComposto(Regione.Est.regioneNazione,
//                                    Cam.provincia.get(),
//                                    Cam.linkRegione.get(),
//                                    chiave);
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
