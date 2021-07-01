/**
 * Copyright(c): 2005
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 3-4-05
 */

package it.algos.gestione.indirizzo.tabelle.citta;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.database.Db;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.modello.ModelloAlgos;
import it.algos.base.modulo.Modulo;
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
 * Tracciato record della tavola Citta.
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
public class CittaModello extends ModelloAlgos {


    /**
     * Costruttore completo senza parametri.
     */
    public CittaModello() {

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
        int lar1 = 50;
        int lar2 = 80;

        try { // prova ad eseguire il codice
            /* invoca il metodo sovrascritto della superclasse */
            super.creaCampi();

            /* campo nome della citta */
            unCampo = CampoFactory.testo(Citta.Cam.citta);
            unCampo.setLarLista(180);
            unCampo.setLarScheda(300);
            unCampo.decora().obbligatorio();
            unCampo.setRicercabile(true);
            this.addCampo(unCampo);

            /* campo CAP */
            unCampo = CampoFactory.testo(Citta.Cam.cap);
            unCampo.setLarLista(50);
            unCampo.setLarScheda(lar2);
            unCampo.setRidimensionabile(false);
            unCampo.setRicercabile(true);
            this.addCampo(unCampo);

            /* campo prefisso */
            unCampo = CampoFactory.testo(Citta.Cam.prefisso);
            unCampo.setLarghezza(100);
            unCampo.setRidimensionabile(false);
            this.addCampo(unCampo);

            /* campo codice istat */
            unCampo = CampoFactory.testo(Citta.Cam.codice);
            unCampo.setLarghezza(lar2);
            unCampo.setRidimensionabile(false);
            this.addCampo(unCampo);

            /* campo link provincia */
            unCampo = CampoFactory.comboLinkSel(Citta.Cam.linkProvincia);
            unCampo.setNomeModuloLinkato(Provincia.NOME_MODULO);
//            unCampo.setNomeVistaLinkata(Provincia.Vis.siglaNazione.toString());
            unCampo.setNomeColonnaListaLinkata(Provincia.Cam.nomeCorrente.get());
            unCampo.setNomeCampoValoriLinkato(Provincia.Cam.nomeCorrente.get());
            unCampo.setAzioneDelete(Db.Azione.setNull);
            unCampo.setUsaModifica(true);
            unCampo.setUsaNuovo(true);
            unCampo.setLarScheda(210);
            unCampo.setRicercabile(true);

            unCampo.decora().estratto(Provincia.Est.sigla);
            unCampo.decora().estrattoSotto(Provincia.Est.regione);

            /* todo - l'estratto sotto incasina il campo video quanto cancelli
             * todo - tutto il contenuto del campo
             * todo - alex 5-12-07 */
//            unCampo.decora().estrattoSotto(Provincia.Est.nazione);

            this.addCampo(unCampo);

            /* campo link nazione */
            unCampo = CampoFactory.comboLinkSel(Citta.Cam.linkNazione);
            unCampo.setNomeModuloLinkato(Nazione.NOME_MODULO);
            unCampo.setNomeVistaLinkata(Nazione.Vis.sigla.toString());
            unCampo.setNomeCampoValoriLinkato(Nazione.Cam.nazione.get());
            unCampo.setAzioneDelete(Db.Azione.setNull);
            unCampo.setUsaNuovo(true);
            unCampo.setLarScheda(150);
            unCampo.setRicercabile(true);
            unCampo.decora().estratto(Nazione.Est.sigla);
//            unCampo.decora().estrattoSotto(Nazione.Est.descrizione);
            this.addCampo(unCampo);

            /* campo flag wikipedia */
            unCampo = CampoFactory.checkBox(Citta.Cam.verificato);
            unCampo.setLarScheda(200);
            unCampo.setRicercabile(true);
            unCampo.setTestoComponente("verificato");
            this.addCampo(unCampo);

            /* campo altitudine */
            unCampo = CampoFactory.testo(Citta.Cam.altitudine);
            unCampo.setLarghezza(lar2);
            this.addCampo(unCampo);

            /* campo abitanti */
            unCampo = CampoFactory.testo(Citta.Cam.abitanti);
            unCampo.setLarghezza(lar2);
            this.addCampo(unCampo);

            /* campo coordinate */
            unCampo = CampoFactory.testo(Citta.Cam.coordinate);
            this.addCampo(unCampo);

            /* campo status */
            unCampo = CampoFactory.testo(Citta.Cam.status);
            unCampo.setLarScheda(300);
            this.addCampo(unCampo);

            /* campo note standard in basso */
            super.setUsaCampoNote(true);
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

        try { // prova ad eseguire il codice
            /* crea la vista specifica */
            super.addVista(Citta.Vis.citta.toString(), Citta.Cam.citta.get());

            /* crea la vista per il navigatore del dialogo di riclassificazione */
            vista = new Vista(this.getModulo());
            vista.setNomeVista(Citta.Vis.riclassificazione.toString());
            vista.addCampo(Citta.Cam.citta);
            vista.addCampo(Citta.Cam.cap);
            vista.addCampo(Citta.Cam.linkProvincia);
            vista.addCampo(Citta.Cam.linkNazione);
            vista.addCampo(Citta.Cam.note);
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
        Vista unaVista;
        Campo unCampo;

        try { // prova ad eseguire il codice

            unaVista = this.getVistaDefault();
            unaVista.setCampoOrdineDefault(this.getCampo(Citta.Cam.citta.get()));

            unaVista = this.getVista(Citta.Vis.citta);
            unCampo = unaVista.getCampo(Citta.Cam.citta);
            unCampo.setLarLista(150);

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
            linkNaz = this.getCampo(Citta.Cam.linkNazione.get());

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

            /* crea una lista di filtri */
            listaFiltri = super.addPopFiltro();
            listaFiltri.setTitolo("Inserimento");
            listaFiltri.setTesto("Tutte");
            listaFiltri.add(FiltroFactory.creaVero(Citta.Cam.verificato.get()), "Controllate");
            listaFiltri.add(FiltroFactory.creaFalso(Citta.Cam.verificato.get()), "Manuale");

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Restituisce un estratto con CAP, Provincia, Nazione.
     * </p>
     * Invoca il metodo delegato della superclasse <br>
     *
     * @param chiave con cui effettuare la ricerca
     *
     * @return l'estratto costruito con la stringa ricavata
     */
    private EstrattoBase getEstrattoCapProvNaz(Object chiave) {
        /* variabili e costanti locali di lavoro */
        EstrattoBase estratto = null;
        boolean continua;
        int codCitta;
        int codProv;
        int codNaz;
        Modulo modulo;
        String stringa = "";
        String stringaCap;
        String stringaProv;
        String stringaNaz;

        try { // prova ad eseguire il codice

            codCitta = Libreria.getInt(chiave);
            continua = (codCitta > 0);

            if (continua) {

                stringaCap = this.query().valoreStringa(Citta.Cam.cap.get(), codCitta);
                codProv = this.query().valoreInt(Citta.Cam.linkProvincia.get(), codCitta);

                modulo = ProvinciaModulo.get();
                stringaProv = modulo.query().valoreStringa(Provincia.Cam.nomeCorrente.get(),
                        codProv);
                codNaz = modulo.query().valoreInt(Provincia.Cam.linkNazione.get(), codProv);

                modulo = NazioneModulo.get();
                stringaNaz = modulo.query().valoreStringa(Nazione.Cam.nazione.get(), codNaz);

                stringa = Lib.Testo.concat(" - ", stringaCap, stringaProv, stringaNaz);

            }// fine del blocco if

            estratto = new EstrattoBase(stringa, EstrattoBase.Tipo.stringa);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return estratto;
    }


    /**
     * Restituisce un estratto con Provincia e Nazione.
     * </p>
     *
     * @param chiave con cui effettuare la ricerca
     *
     * @return l'estratto costruito con la stringa ricavata
     */
    private EstrattoBase getEstrattoProvNaz(Object chiave) {
        /* variabili e costanti locali di lavoro */
        EstrattoBase estratto = null;
        boolean continua;
        int codCitta;
        int codProv;
        int codNaz;
        Modulo modulo;
        String stringa = "";
        String stringaProv;
        String stringaNaz;

        try { // prova ad eseguire il codice

            codCitta = Libreria.getInt(chiave);
            continua = (codCitta > 0);

            if (continua) {

                codProv = this.query().valoreInt(Citta.Cam.linkProvincia.get(), codCitta);

                modulo = ProvinciaModulo.get();
                stringaProv = modulo.query().valoreStringa(Provincia.Cam.nomeCorrente.get(),
                        codProv);
                codNaz = modulo.query().valoreInt(Provincia.Cam.linkNazione.get(), codProv);

                modulo = NazioneModulo.get();
                stringaNaz = modulo.query().valoreStringa(Nazione.Cam.nazione.get(), codNaz);

                stringa = Lib.Testo.concat(" - ", stringaProv, stringaNaz);

            }// fine del blocco if

            estratto = new EstrattoBase(stringa, EstrattoBase.Tipo.stringa);

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
            switch ((Citta.Est)estratto) {
                case citta:
                    unEstratto = this.getEstratto(estratto, chiave, Citta.Cam.citta.get());
                    break;
                case cap:
                    unEstratto = this.getEstratto(estratto, chiave, Citta.Cam.cap.get());
                    break;
                case nazione:
                    unEstratto = this.getEstrattoPassante(Provincia.Est.nazione,
                            Citta.Cam.linkProvincia.get(),
                            chiave);
                    break;
                case capProvinciaNazione:
                    unEstratto = this.getEstrattoCapProvNaz(chiave);
                    break;

                case provinciaNazione:
                    unEstratto = this.getEstrattoProvNaz(chiave);
                    break;

                case provinciaRegioneNazione:
                    unEstratto = this.getEstrattoPassante(Provincia.Est.provinciaRegioneNazione,
                            Citta.Cam.linkProvincia.get(),
                            chiave);
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
