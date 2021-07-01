/**
 * Copyright(c): 2005
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 3-4-05
 */

package it.algos.gestione.indirizzo;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.database.Db;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.database.dati.Dati;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.modello.Modello;
import it.algos.base.modello.ModelloAlgos;
import it.algos.base.modulo.Modulo;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFlusso;
import it.algos.base.query.Query;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.query.selezione.QuerySelezione;
import it.algos.base.vista.Vista;
import it.algos.base.wrapper.CampoValore;
import it.algos.base.wrapper.Estratti;
import it.algos.base.wrapper.EstrattoBase;
import it.algos.gestione.anagrafica.Anagrafica;
import it.algos.gestione.indirizzo.tabelle.citta.Citta;
import it.algos.gestione.indirizzo.tabelle.citta.CittaModulo;
import it.algos.gestione.indirizzo.tabelle.nazione.Nazione;
import it.algos.gestione.indirizzo.tabelle.nazione.NazioneModulo;
import it.algos.gestione.indirizzo.tabelle.provincia.Provincia;
import it.algos.gestione.indirizzo.tabelle.provincia.ProvinciaModulo;
import it.algos.gestione.indirizzo.tabelle.via.Via;
import it.algos.gestione.indirizzo.tabelle.via.ViaModulo;

import javax.swing.JTextArea;
import java.util.ArrayList;

/**
 * Tracciato record della tavola Indirizzo.
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
public class IndirizzoModello extends ModelloAlgos implements Indirizzo {


    /**
     * Costruttore completo senza parametri.
     */
    public IndirizzoModello() {

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

        try { // prova ad eseguire il codice

            /* regola il nome della tavola dalla costante */
            this.setTavolaArchivio(Indirizzo.NOME_TAVOLA);

            /* attiva l'uso del campo Note */
            super.setUsaCampoNote(true);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

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
        Ordine ordine;

        try { // prova ad eseguire il codice
            /* invoca il metodo sovrascritto della superclasse */
            super.creaCampi();

            /* campo link al record di anagrafica */
            unCampo = CampoFactory.comboLinkSel(Indirizzo.Cam.anagrafica);
            unCampo.setVisibileVistaDefault(true);
            unCampo.setNomeModuloLinkato(Anagrafica.NOME_MODULO);
            unCampo.setNomeVistaLinkata(Anagrafica.Vis.soggetto.toString());
            unCampo.setNomeCampoValoriLinkato(Anagrafica.Cam.soggetto.get());
            unCampo.setAzioneDelete(Db.Azione.cascade);
            unCampo.setLarScheda(200);
            unCampo.setRidimensionabile(false);
            this.addCampo(unCampo);

            /* campo link via */
            unCampo = CampoFactory.comboLinkPop(Cam.via.get());
            unCampo.setVisibileVistaDefault();
            unCampo.setNomeModuloLinkato(Via.NOME_MODULO);
            unCampo.setNomeVistaLinkata(Via.Vis.sigla.toString());
            unCampo.setNomeCampoValoriLinkato(Via.Cam.sigla.get());
            unCampo.setUsaNuovo(true);
            unCampo.setLarScheda(90);
            unCampo.setTestoEtichetta("via");
            this.addCampo(unCampo);

            /* campo indirizzo */
            unCampo = CampoFactory.testo(Indirizzo.Cam.indirizzo);
            unCampo.setLarLista(150);
            unCampo.setLarScheda(250);
            unCampo.decora().obbligatorio();
            unCampo.setRidimensionabile(true);
            this.addCampo(unCampo);

            /* campo eventuale seconda riga di indirizzo */
            unCampo = CampoFactory.testo(Indirizzo.Cam.indirizzo2);
            unCampo.setLarScheda(250);
            unCampo.setRidimensionabile(true);
            this.addCampo(unCampo);

//            /* campo numero civico */
//            unCampo = CampoFactory.testo(Cam.numeroCivico.get());
//            unCampo.setVisibileVistaDefault(true);
//            unCampo.setTitoloColonna(COLONNA_NUMERO);
//            unCampo.setLarLista(30);
//            unCampo.setRidimensionabile(false);
//            unCampo.setLarScheda(50);
//            unCampo.decora().legenda(LEGENDA_NUMERO);
//            this.addCampo(unCampo);

            /* campo link citta */
            unCampo = CampoFactory.comboLinkSel(Indirizzo.Cam.citta);
            unCampo.setNomeModuloLinkato(Citta.NOME_MODULO);
            unCampo.setNomeVistaLinkata(Citta.Vis.citta.toString());
            unCampo.setNomeCampoValoriLinkato(Citta.Cam.citta.get());
            unCampo.setAzioneDelete(Db.Azione.setNull);
            ordine = new Ordine();
            ordine.add(Citta.Cam.citta.get());
            unCampo.setOrdineElenco(ordine);
            unCampo.setRidimensionabile(true);
            unCampo.setLarScheda(250);
            unCampo.decora().estrattoSotto(Citta.Est.provinciaNazione);
            this.addCampo(unCampo);

            /* campo codice avviamento postale */
            unCampo = CampoFactory.suggerito(Indirizzo.Cam.cap.get(),
                    Indirizzo.Cam.citta.get(),
                    Citta.Cam.cap.get());
            unCampo.setLarghezza(50);
            unCampo.setRidimensionabile(true);
            this.addCampo(unCampo);

            /* campo tipo indirizzo */
            unCampo = CampoFactory.checkInterno(Indirizzo.Cam.tipo.get());
            unCampo.setValoriInterni(Indirizzo.TipiSede.getLista());
            unCampo.setRicercabile(true);
            unCampo.setRenderer(new RendererTipo(unCampo));
            unCampo.setLarLista(100);
            this.addCampo(unCampo);

            /* campo "preferito" */
            this.setUsaCampoPreferito(true);

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

            /* vista per la lista indirizzi nella scheda anagrafica */
            vista = new Vista(Indirizzo.Vis.indirizziInAnag.toString(), this.getModulo());
//            vista.addCampo(this.getCampoOrdine());
            vista.addCampo(Indirizzo.Cam.indirizzo.get());
            vista.addCampo(Indirizzo.Cam.cap.get());
            vista.addCampo(Indirizzo.Cam.citta.get());
            vista.addCampo(Indirizzo.Cam.tipo.get());
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
        /* variabili e costanti locali di lavoro */
        Campo unCampo;

        try { // prova ad eseguire il codice
            unCampo = this.getCampo(Modello.NOME_CAMPO_NOTE);
            unCampo.setLarScheda(250);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    } /* fine del metodo */


    /**
     * Restituisce un estratto di .....
     * </p>
     * Controlla che il tipo di estratto richiesto sia un pannello <br>
     *
     * @param estratto desiderato
     * @param chiave con cui effettuare la ricerca
     *
     * @return l'estratto costruito con la stringa ricavata
     */
    private EstrattoBase getEstrattoIndirizzo(Estratti estratto, Object chiave) {
        /* variabili e costanti locali di lavoro */
        EstrattoBase unEstratto = null;
        EstrattoBase.Tipo tipoEst;
        String rigaUno;
        String rigaDue;
        String rigaQuattro = "Nota: ";
        int cod;
        String campoInd = Indirizzo.Cam.indirizzo.get();
        String campoVia = Indirizzo.Cam.via.get();
        String campoCap = Indirizzo.Cam.cap.get();
        String campoNote = Indirizzo.Cam.note.get();

        Modulo modVia;
        int linkVia;
        String via = "";
        String indirizzo;
        String ind;
        String num = "";
        String cap;
        String citta;
        String nazione;
        String nota;
        Pannello pan;
        JTextArea area;
        String testo;

        try { // prova ad eseguire il codice
            /* tipo di estratto codificato */
            tipoEst = estratto.getTipo();

            /* codice di questo record */
            cod = (Integer)chiave;

            /* recupera valore di link  */
            linkVia = this.query().valoreInt(campoVia, cod);
            modVia = ViaModulo.get();
            if (modVia != null) {
                unEstratto = modVia.getEstratto(Via.Est.sigla, linkVia);
                if (unEstratto != null) {
                    via = unEstratto.getStringa();
                }// fine del blocco if
            }// fine del blocco if

            /* recupera i valori dal database */
            indirizzo = this.query().valoreStringa(campoInd, cod);
//            num = this.query().valoreStringa(campoNum, cod);
            rigaUno = indirizzo;
            if (Lib.Testo.isValida(via) && Lib.Testo.isValida(indirizzo)) {
                rigaUno = via + " " + indirizzo;
            }// fine del blocco if

            /* seconda riga */
            cap = this.query().valoreStringa(campoCap, cod);
            unEstratto = this.getEstrattoPassante(Citta.Est.citta,
                    Indirizzo.Cam.citta.get(),
                    chiave);
            citta = unEstratto.getStringa();


            unEstratto = this.getEstrattoPassante(Citta.Est.nazione,
                    Indirizzo.Cam.citta.get(),
                    chiave);
            nazione = unEstratto.getStringa();

            rigaDue = cap + " " + citta + " " + nazione;

            nota = this.query().valoreStringa(campoNote, cod);
            rigaQuattro += nota;

            /* testo completo */
            testo = rigaUno + "\n";
            testo += rigaDue + "\n";
//            testo += rigaTre + "\n";
            if (Lib.Testo.isValida(nota)) {
                testo += rigaQuattro;
            }// fine del blocco if


            area = new JTextArea(testo);
            area.setOpaque(false);

            /* crea il pannello */
            pan = new PannelloFlusso();
            pan.getPanFisso().setOpaque(false);
            pan.add(area);
            pan.setPreferredSize(300, 70);
//            pan.creaBordo();

            /* crea l'estratto */
            unEstratto = new EstrattoBase(pan, EstrattoBase.Tipo.pannello);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unEstratto;
    }


    /**
     * Restituisce un estratto di tipo Etichetta Documento
     * </p>
     *
     * @param estratto desiderato
     * @param chiave con cui effettuare la ricerca
     *
     * @return l'estratto costruito con la stringa ricavata
     */
    private EstrattoBase getEstrattoEtichetta(Estratti estratto, Object chiave) {
        /* variabili e costanti locali di lavoro */
        EstrattoBase unEstratto = null;
        String rigaUno;
        String rigaDue;
        String rigaTre;
        String rigaQuattro;
        int cod;
        Query query;
        Filtro filtro;
        Dati dati;

        Campo campoIndirizzo = this.getCampo(Indirizzo.Cam.indirizzo.get());
        Campo campoIndirizzo2 = this.getCampo(Indirizzo.Cam.indirizzo2.get());
        Campo campoCap = this.getCampo(Indirizzo.Cam.cap.get());
        Campo campoLocalita = CittaModulo.get().getCampo(Citta.Cam.citta.get());
        Campo campoProv = ProvinciaModulo.get().getCampo(Provincia.Cam.sigla.get());
        Campo campoNazione = NazioneModulo.get().getCampo(Nazione.Cam.nazione.get());

        String indirizzo;
        String indirizzo2;
        String cap;
        String localita;
        String provincia;
        String nazione;

        String riga;

        try { // prova ad eseguire il codice

            /* codice del record */
            cod = (Integer)chiave;

            /* costruisce ed esegue la query */
            query = new QuerySelezione(this.getModulo());
            query.addCampo(campoIndirizzo);
            query.addCampo(campoIndirizzo2);
            query.addCampo(campoCap);
            query.addCampo(campoLocalita);
            query.addCampo(campoProv);
            query.addCampo(campoNazione);
            filtro = FiltroFactory.codice(this.getModulo(), cod);
            query.setFiltro(filtro);
            dati = this.query().querySelezione(query);

            /* recupera i dati */
            indirizzo = dati.getStringAt(0, campoIndirizzo);
            indirizzo2 = dati.getStringAt(0, campoIndirizzo2);
            cap = dati.getStringAt(0, campoCap);
            localita = dati.getStringAt(0, campoLocalita);
            provincia = dati.getStringAt(0, campoProv);
            nazione = dati.getStringAt(0, campoNazione);
            dati.close();

            /* costruisce le righe */
            rigaUno = indirizzo;
            rigaDue = indirizzo2;
            rigaTre = Lib.Testo.concatSpace(cap, localita, provincia);
            rigaQuattro = nazione;

            /* blocco completo */
            riga = Lib.Testo.concatReturn(rigaUno, rigaDue, rigaTre, rigaQuattro);

            /* crea l'estratto */
            unEstratto = new EstrattoBase(riga, EstrattoBase.Tipo.stringa);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unEstratto;
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
        EstrattoBase unEstratto = null;

        try { // prova ad eseguire il codice

            /* selettore della variabile */
            switch ((Est)estratto) {
                case indirizzo:
                    unEstratto = this.getEstrattoIndirizzo(estratto, chiave);
                    break;
                case etichettaDocumento:
                    unEstratto = this.getEstrattoEtichetta(estratto, chiave);
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


    protected boolean nuovoRecordAnte(ArrayList<CampoValore> lista, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = false;

        try { // prova ad eseguire il codice
            riuscito = super.nuovoRecordAnte(lista, conn);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    } // fine del metodo


} // fine della classe
