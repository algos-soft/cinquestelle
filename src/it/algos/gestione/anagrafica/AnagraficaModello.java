/**
 * Title:     AnagraficaModello
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      8-feb-2004
 */
package it.algos.gestione.anagrafica;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.campo.inizializzatore.InitFactory;
import it.algos.base.campo.lista.CampoLista;
import it.algos.base.database.Db;
import it.algos.base.errore.Errore;
import it.algos.base.layout.Layout;
import it.algos.base.modello.ModelloAlgos;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.toolbar.ToolBar;
import it.algos.base.vista.Vista;
import it.algos.base.wrapper.Estratti;
import it.algos.base.wrapper.EstrattoBase;
import it.algos.base.wrapper.WrapFiltri;
import it.algos.gestione.anagrafica.categoria.CatAnagrafica;
import it.algos.gestione.anagrafica.categoria.CatAnagraficaModulo;
import it.algos.gestione.anagrafica.tabelle.titolo.Titolo;
import it.algos.gestione.contatto.Contatto;
import it.algos.gestione.contatto.ContattoModulo;
import it.algos.gestione.indirizzo.Indirizzo;
import it.algos.gestione.indirizzo.IndirizzoModulo;
import it.algos.gestione.tabelle.contibanca.ContiBanca;
import it.algos.gestione.tabelle.contibanca.ContiBancaModulo;
import it.algos.gestione.tabelle.iva.Iva;
import it.algos.gestione.tabelle.tipopagamento.TipoPagamento;

import java.util.ArrayList;

/**
 * Tracciato record della tavola AnagraficaModello.
 * </p>
 * Questa classe concreta:
 * <ul> <li> Crea il <strong>tracciato record</strong> (Abstract Data Types) di una tavola
 * </li> <li> Mantiene il nome della tavola di archivo dove sono registrati tutti i dati (records) del modello
 * </li> <li> Crea i <strong>campi</strong> di questo modello (oltre a quelli base della superclasse)
 * </li> <li> Un eventuale file di dati iniziali va regolato come percorso e nomi dei campi presenti
 * </li> <li> Eventuali <strong>moduli e tabelle</strong> vanno creati nel metodo <code> regolaModuli</code>
 * </li> <li> Regola i titoli delle finestre lista e scheda
 * <li> Ogni campo viene creato con un costruttore semplice con solo le piu' comuni informazioni;
 * le altre vengono regolate con chiamate successive
 * </li> </ul>
 *
 * @author Alberto Colombo, Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 8-feb-2004 ore 20.37.59
 */
public class AnagraficaModello extends ModelloAlgos implements Anagrafica {

    /**
     * nome della tavola di archivio collegata (facoltativo) <br> i nomi delle tavole sono sempre
     * minuscoli <br> se vuoto usa il nome del modulo <br>
     */
    private static final String TAVOLA_ARCHIVIO = NOME_TAVOLA;


    /**
     * Costruttore completo senza parametri.<br>
     */
    protected AnagraficaModello() {
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
     * Regolazioni immediate di riferimenti e variabili. <br> Metodo chiamato direttamente dal
     * costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        /* regola il nome della tavola dalla costante */
        super.setTavolaArchivio(TAVOLA_ARCHIVIO);

//        /* attiva l'uso del campo Note */
//        super.setUsaCampoNote(true);

    }// fine del metodo inizia


    /**
     * Creazione dei campi.
     * <p/>
     * Metodo invocato dal ciclo statico del progetto <br> Creazione dei campi record di questo
     * modello <br> I campi verranno visualizzati nell'ordine di inserimento <br> Ogni campo viene
     * creato con un costruttore semplice con solo le piu' comuni informazioni; le altre vengono
     * regolate con chiamate successive <br> Invoca il metodo sovrascritto della superclasse <br>
     * Metodo sovrascritto nelle sottoclassi <br> (metodo chiamato dalla superclasse) <br>
     *
     * @see it.algos.base.progetto.Progetto#preparaModuli
     * @see it.algos.base.modello.ModelloAlgos#creaCampi
     * @see it.algos.base.campo.base.CampoFactory
     * @see it.algos.base.campo.video.decorator.VideoFactory
     */
    protected void creaCampi() {
        /* variabili e costanti locali di lavoro */
        Campo unCampo;
        Modulo modulo;
        Navigatore nav;
        int lar1 = 60;
        int lar2 = 80;
        int lar3 = 130;
        int lar4 = 140;
        int lar5 = 160;
        int lar6 = 200;
        int lar7 = 180;

        try { // prova ad eseguire il codice

            /* invoca il metodo sovrascritto della superclasse */
            super.creaCampi();

            /* campo soggetto */
            unCampo = CampoFactory.testo(Cam.soggetto);
            unCampo.decora().obbligatorio();
            unCampo.setLarLista(lar4);
            unCampo.setLarScheda(lar6);
            unCampo.setRidimensionabile(true);
            unCampo.setRicercabile(true);
            this.addCampo(unCampo);

            /* campo link alla categoria di anagrafica */
            unCampo = CampoFactory.comboLinkPop(Cam.categoria);
            unCampo.setNomeModuloLinkato(CatAnagraficaModulo.NOME_MODULO);
            unCampo.setNomeColonnaListaLinkata(CatAnagrafica.Cam.sigla.get());
            unCampo.setNomeCampoValoriLinkato(CatAnagrafica.Cam.sigla.get());
            this.addCampo(unCampo);

//            // inizializzatore con primo check acceso
//            ArrayList<Boolean> valori = new ArrayList<Boolean>();
//            valori.add(true);
//            unCampo.setInit(InitFactory.boolArrayList(valori));
//            this.addCampo(unCampo);

            /* campo tipo di anagrafica (privato-societa) */
            unCampo = CampoFactory.radioInterno(Cam.privatosocieta);
            unCampo.setInit(InitFactory.intero(Anagrafica.Tipi.privato.getCodice()));
            unCampo.setRicercabile(true);
            unCampo.setValoriInterni(Anagrafica.Tipi.getLista());
            unCampo.setUsaNonSpecificato(false);
            unCampo.setLarLista(lar1);
            unCampo.setLarScheda(lar2);
            unCampo.getCampoLista().setRidimensionabile(false);
            unCampo.setOrientamentoComponenti(Layout.ORIENTAMENTO_VERTICALE);
            unCampo.decora().eliminaEtichetta();
            unCampo.decora().obbligatorio();
            this.addCampo(unCampo);

            /* campo titolo usando la classe Factory (privato) */
            unCampo = CampoFactory.comboLinkPop(Cam.titolo);
            unCampo.setNomeModuloLinkato(MODULO_TITOLO);
            unCampo.setNomeVistaLinkata(Titolo.VISTA_SIGLA);
            unCampo.setNomeCampoValoriLinkato(Titolo.CAMPO_SIGLA);
            unCampo.setAzioneDelete(Db.Azione.setNull);
            unCampo.setLarScheda(lar2);
            unCampo.setUsaNuovo(true);
            unCampo.setNuovoIniziale(true);
            this.addCampo(unCampo);

            /* campo nome (privato) */
            unCampo = CampoFactory.testo(Cam.nome);
            unCampo.setLarLista(lar3);
            unCampo.setLarScheda(lar5);
            this.addCampo(unCampo);

            /* campo cognome (privato) */
            unCampo = CampoFactory.testo(Cam.cognome);
            unCampo.decora().obbligatorio();
            unCampo.setLarLista(lar3);
            unCampo.setLarScheda(lar5);
            this.addCampo(unCampo);

            /* campo codice fiscale (privato) */
            unCampo = CampoFactory.codiceFiscale(Cam.codFiscale);
            unCampo.setLarScheda(lar6);
            this.addCampo(unCampo);

            /* campo sesso (privato) */
            unCampo = CampoFactory.radioInterno(Cam.sesso);
            unCampo.setInit(InitFactory.intero(1));
            unCampo.setValoriInterni("maschio,femmina");
            unCampo.setUsaNonSpecificato(false);
            unCampo.getCampoLista().setRidimensionabile(false);
            unCampo.setOrientamentoComponenti(Layout.ORIENTAMENTO_VERTICALE);
            unCampo.decora().eliminaEtichetta();
            this.addCampo(unCampo);

            /* campo data di nascita */
            unCampo = CampoFactory.data(Cam.dataNascita);
            unCampo.setInit(null);
            this.addCampo(unCampo);

            /* campo ragione sociale (societa) */
            unCampo = CampoFactory.testo(Cam.ragSociale);
            unCampo.decora().obbligatorio();
            unCampo.setLarScheda(280);
            this.addCampo(unCampo);

            /* campo persona di riferimento (societa) */
            unCampo = CampoFactory.testo(Cam.riferimento);
            this.addCampo(unCampo);

            /* campo partita iva (societa) */
            unCampo = CampoFactory.partitaIVA(Cam.partitaIva);
            unCampo.setLarScheda(lar6);
            this.addCampo(unCampo);

            /* campo consenso privacy */
            unCampo = CampoFactory.checkBox(Cam.consensoPrivacy);
            unCampo.setLarScheda(lar7);
//            unCampo.decora().etichetta(".");
            this.addCampo(unCampo);

            /* campo data consenso privacy */
            unCampo = CampoFactory.data(Cam.dataPrivacy);
            unCampo.setInit(null);
            this.addCampo(unCampo);

            /* campo navigatore indirizzi */
            modulo = IndirizzoModulo.get();
            if (modulo != null) {
                nav = modulo.getNavigatore(Indirizzo.Nav.indirizziAnagrafica.get());
                if (nav != null) {
                    unCampo = CampoFactory.navigatore(Cam.indirizzi, nav);
                    this.regolaNavigatore(nav);
                    this.addCampo(unCampo);
                }// fine del blocco if
            }// fine del blocco if

            /* campo telefono */
            unCampo = CampoFactory.testo(Cam.telefono);
            unCampo.setLarScheda(lar4);
            this.addCampo(unCampo);

            /* campo cellulare */
            unCampo = CampoFactory.testo(Cam.cellulare);
            unCampo.setLarScheda(lar4);
            this.addCampo(unCampo);

            /* campo fax */
            unCampo = CampoFactory.testo(Cam.fax);
            unCampo.setLarScheda(lar4);
            this.addCampo(unCampo);

            /* campo e-mail */
            unCampo = CampoFactory.testo(Cam.email);
            unCampo.setLarScheda(lar7);
            this.addCampo(unCampo);

            /* campo note */
            unCampo = CampoFactory.testoArea(Cam.note);
            unCampo.setNumeroRighe(8);
            unCampo.setLarScheda(250);
            this.addCampo(unCampo);

            /* campo navigatore contatti per eventuali ulteriori informazioni oltre tel, cell, fax ed e-mail */
            modulo = ContattoModulo.get();
            if (modulo != null) {
                nav = modulo.getNavigatore(Contatto.Nav.contattiAnagrafica.get());
                if (nav != null) {
                    unCampo = CampoFactory.navigatore(Cam.contatti, nav);
                    this.regolaNavigatore(nav);
                    this.addCampo(unCampo);
                }// fine del blocco if
            }// fine del blocco if

            /* campo navigatore conti bancari */
            modulo = ContiBancaModulo.get();
            if (modulo != null) {
                nav = modulo.getNavigatore(ContiBanca.Nav.contiAnagrafica.get());
                if (nav != null) {
                    unCampo = CampoFactory.navigatore(Cam.contibanca, nav);
                    this.regolaNavigatore(nav);
                    this.addCampo(unCampo);
                }// fine del blocco if
            }// fine del blocco if

            /* campo link pagamento */
            unCampo = CampoFactory.comboLinkPop(Cam.pagamento);
            unCampo.setNomeModuloLinkato(TipoPagamento.NOME_MODULO);
            unCampo.setNomeVistaLinkata(TipoPagamento.VISTA_SIGLA);
            unCampo.setNomeCampoValoriLinkato(TipoPagamento.CAMPO_SIGLA);
            this.addCampo(unCampo);

            /* campo iva */
            unCampo = CampoFactory.comboLinkPop(Cam.iva);
            unCampo.setNomeModuloLinkato(Iva.NOME_MODULO);
            unCampo.setNomeVistaLinkata(Iva.VISTA_SIGLA);
            unCampo.setNomeCampoValoriLinkato(Iva.CAMPO_SIGLA);
            this.addCampo(unCampo);

            /* campo applica rivalsa */
            unCampo = CampoFactory.radioInterno(Cam.applicaRivalsa);
            unCampo.setValoriInterni(ValoriOpzione.getLista());
            unCampo.setInit(InitFactory.intero(ValoriOpzione.getValoreDefault()));
            this.addCampo(unCampo);

            /* campo applica r.a. */
            unCampo = CampoFactory.radioInterno(Cam.applicaRA);
            unCampo.setValoriInterni(ValoriOpzione.getLista());
            unCampo.setInit(InitFactory.intero(ValoriOpzione.getValoreDefault()));
            this.addCampo(unCampo);

            /* campo percentuale r.a. */
            unCampo = CampoFactory.percentuale(Cam.percRA);
            this.addCampo(unCampo);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Creazione delle viste aggiuntive.
     * <p/>
     * Metodo invocato dal ciclo statico del progetto <br> Eventuale creazione di viste aggiuntive,
     * oltre alla vista base di default <br> Costruisce degli ArrayList di riferimenti ordinati
     * (oggetti <code>Vista</code>) per individuare i campi che voglio vedere nelle liste
     * alternative ed aggiuntive a quella standard (costruita in automatico nella superclasse) <br>
     * Gli array vengono creati coi campi di questo modello, oppure con viste di altri moduli,
     * oppure con campi di altri modelli <br> Viene chiamato <strong>dopo</strong> che nella
     * sottoclasse sono stati costruiti tutti i campi <br> Metodo sovrascritto nelle sottoclassi
     * <br> (metodo chiamato dalla superclasse) <br>
     *
     * @see it.algos.base.progetto.Progetto#preparaModuli()
     * @see #regolaViste
     */
    protected void creaViste() {
        /* variabili e costanti locali di lavoro */
        Vista vista;

        try { // prova ad eseguire il codice

            /* vista per il navigatore di default */
            vista = new Vista(Anagrafica.Vis.standard.toString(), this.getModulo());
            vista.addCampo(Cam.soggetto.get());
            this.addVista(vista);

            /* crea la vista specifica (un solo campo) */
            super.addVista(Vis.soggetto.toString(), Anagrafica.Cam.soggetto.get());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regolazione delle viste aggiuntive.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br> Eventuale regolazione delle caratteristiche
     * specifiche di ogni copia dei campi delle viste; le variazioni modificano
     * <strong>solo</strong> le copie <br> Viene chiamato <strong>dopo</strong> che nella
     * superclasse sono state <strong>clonate</strong> tutte le viste <br> Metodo sovrascritto nelle
     * sottoclassi <br> (metodo chiamato dalla superclasse) <br>
     *
     * @see #creaViste
     */
    protected void regolaViste() {
        /* variabili e costanti locali di lavoro */
        Vista unaVista;
        Campo unCampo;
        CampoLista cl;

        try { // prova ad eseguire il codice

//            unaVista = this.getVista(VISTA_SOGGETTO);
//            unCampo = unaVista.getCampo(Cam.soggetto.get());
//            unCampo.getCampoLista().setRidimensionabile(false);
//            unCampo.setTitoloColonna(COLONNA_SOGGETTO);

            unaVista = this.getVista(Vis.soggetto);
            unCampo = unaVista.getCampo(Cam.soggetto.get());
            cl = unCampo.getCampoLista();
            cl.setRidimensionabile(false);
            cl.setTitoloColonna("soggetto");

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Creazione dei set aggiuntivi.
     * <p/>
     * Metodo invocato dal ciclo statico del progetto <br> Eventuale creazione di set aggiuntivi,
     * oltre al set base di default <br> Costruisce degli ArrayList di riferimenti ordinati (oggetti
     * <code>Campo</code>) per individuare i campi che voglio vedere in un set di campi scheda <br>
     * Gli array vengono creati coi campi di questo modello, oppure con campi di altri moduli <br>
     * Viene chiamato <strong>dopo</strong> che nella sottoclasse specifica sono stati costruiti
     * tutti i campi <br> Metodo sovrascritto nelle sottoclassi <br> (metodo chiamato dalla
     * superclasse) <br>
     */
    @Override
    protected void creaSet() {
        /* variabili e costanti locali di lavoro */
        ArrayList<String> unSet;

        try {    // prova ad eseguire il codice

//            /* set categoria/tipo */
//            unSet = new ArrayList<String>();
//            if (this.getCampo(Cam.categoria.get()) != null) {
//                unSet.add(Cam.categoria.get());
//            }// fine del blocco if
//            unSet.add(Cam.privatosocieta.get());
//            super.creaSet(Set.catTipo, unSet);

//            /* set campi superiori */
//            unSet = new ArrayList<String>();
//            if (this.getCampo(Cam.categoria.get()) != null) {
//                unSet.add(Cam.categoria.get());
//            }// fine del blocco if
//            unSet.add(Cam.privatosocieta.get());
//            if (this.getCampo(Cam.sesso.get()) != null) {
//                unSet.add(Cam.sesso.get());
//            }// fine del blocco if
//            if (this.getCampo(Cam.consensoPrivacy.get()) != null) {
//                unSet.add(Cam.consensoPrivacy.get());
//            }// fine del blocco if
//            super.creaSet(Set.testata, unSet);

            /* set consenso */
            unSet = new ArrayList<String>();
            if (this.getCampo(Cam.consensoPrivacy.get()) != null) {
                unSet.add(Cam.consensoPrivacy.get());
            }// fine del blocco if
            if (this.getCampo(Cam.dataPrivacy.get()) != null) {
                unSet.add(Cam.dataPrivacy.get());
            }// fine del blocco if
            super.creaSet(Set.consenso, unSet);

            /* dati del privato */
            super.creaSet(Set.privato, Cam.titolo, Cam.nome, Cam.cognome, Cam.soggetto);

            /* dati della società */
            super.creaSet(Set.societa, Cam.ragSociale, Cam.soggetto);

            /* telefoni */
            super.creaSet(Set.telcellmail, Cam.telefono, Cam.cellulare, Cam.email);

            /* telefoni e fax */
            super.creaSet(Set.telcellfaxmail, Cam.telefono, Cam.cellulare, Cam.fax, Cam.email);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

    } /* fine del metodo */


    /**
     * Regolazione dei set aggiuntivi.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br> Eventuale regolazione delle caratteristiche
     * specifiche di ogni copia dei campi dei set; le variazioni modificano <strong>solo</strong> le
     * copie <br> Viene chiamato <strong>dopo</strong> che nella sottoclasse specifica sono stati
     * costruiti tutti i campi <br> Metodo sovrascritto nelle sottoclassi <br> (metodo chiamato
     * dalla superclasse) <br>
     */
    protected void regolaSet() {
    } /* fine del metodo */


    /**
     * Restituisce un estratto con la descrizione significativa. </p> Nel caso di privato, recupera
     * il nome e cognome <br> Nel caso di società, recupera la ragione sociale <br>
     *
     * @param tipo codifica dell'estratto desiderato
     * @param chiave con cui effettuare la ricerca
     *
     * @return l'estratto costruito con la stringa ricavata
     */
    private EstrattoBase getEstrattoDescrizione(Estratti tipo, Object chiave) {
        /* variabili e costanti locali di lavoro */
        EstrattoBase estratto = null;
        EstrattoBase.Tipo tipoEst;
        Modulo mod;
        int codTipo;
        int cod;

        try { // prova ad eseguire il codice
            /* tipo di estratto codificato */
            tipoEst = tipo.getTipo();

            if (tipoEst == EstrattoBase.Tipo.stringa) {
                cod = (Integer)chiave;
                mod = this.getModulo();

                codTipo = mod.query()
                        .valoreInt(Cam.privatosocieta.get(), cod);

                if (codTipo == Anagrafica.Tipi.privato.getCodice()) {
                    estratto = this.getEstrattoNomeCognome(tipo, chiave);
                }// fine del blocco if

                if (codTipo == Anagrafica.Tipi.societa.getCodice()) {
                    estratto = this.getEstrattoRagioneSociale(tipo, chiave);
                }// fine del blocco if

            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return estratto;
    }


    /**
     * Restituisce un estratto con nome e cognome. </p>
     *
     * @param tipo codifica dell'estratto desiderato
     * @param chiave con cui effettuare la ricerca
     *
     * @return l'estratto costruito con la stringa ricavata
     */
    private EstrattoBase getEstrattoNomeCognome(Estratti tipo, Object chiave) {
        /* variabili e costanti locali di lavoro */
        EstrattoBase estratto = null;
        EstrattoBase.Tipo tipoEst;
        Modulo mod;
        String nome;
        String cognome;
        int cod;

        try { // prova ad eseguire il codice
            /* tipo di estratto codificato */
            tipoEst = tipo.getTipo();

            if (tipoEst == EstrattoBase.Tipo.stringa) {

                cod = (Integer)chiave;
                mod = this.getModulo();

                nome = mod.query().valoreStringa(Cam.nome.get(), cod);
                cognome = mod.query().valoreStringa(Cam.cognome.get(), cod);

                /* crea l'estratto */
                estratto = new EstrattoBase(nome + " " + cognome);

            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return estratto;
    }


    /**
     * Restituisce un estratto con la ragione sociale. </p>
     *
     * @param tipo codifica dell'estratto desiderato
     * @param chiave con cui effettuare la ricerca
     *
     * @return l'estratto costruito con la stringa ricavata
     */
    private EstrattoBase getEstrattoRagioneSociale(Estratti tipo, Object chiave) {
        /* variabili e costanti locali di lavoro */
        EstrattoBase estratto = null;
        EstrattoBase.Tipo tipoEst;
        int cod;

        try { // prova ad eseguire il codice
            /* tipo di estratto codificato */
            tipoEst = tipo.getTipo();

            if (tipoEst == EstrattoBase.Tipo.stringa) {
                cod = (Integer)chiave;
                estratto = this.getEstrattoTesto(Cam.ragSociale.get(), cod);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore

            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return estratto;
    }


    /**
     * Restituisce un estratto con l'etichetta completa per l'invio di corrispondenza. </p>
     *
     * @param tipo codifica dell'estratto desiderato
     * @param chiave con cui effettuare la ricerca
     *
     * @return l'estratto costruito con la stringa ricavata
     */
    private EstrattoBase getEstrattoEtichetta(Estratti tipo, Object chiave) {
        /* variabili e costanti locali di lavoro */
        EstrattoBase estratto = null;
        EstrattoBase.Tipo tipoEst;
        int cod;

        try { // prova ad eseguire il codice

//            cod = (Integer)chiave;
//            mod = this.getModulo();
//
//            nome = mod.query().valoreStringa(Cam.nome.get(), cod);
//            cognome = mod.query().valoreStringa(Cam.cognome.get(), cod);

            /* crea l'estratto */
            String testo = "questo è il testo che ritorna";
            estratto = new EstrattoBase(testo, EstrattoBase.Tipo.stringa);

//            /* tipo di estratto codificato */
//            tipoEst = tipo.getTipo();
//
//            if (tipoEst == EstrattoBase.Tipo.STRINGA) {
//                cod = (Integer)chiave;
//                estratto = this.getEstrattoTesto(Cam.ragSociale.get(),cod);
//            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore

            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return estratto;
    }


    /**
     * Restituisce un estratto. </p> Restituisce un estratto conforme al tipo ed al record richiesto
     * <br> Metodo sovrascritto nelle sottoclassi <br>
     *
     * @param estratto codifica dell'estratto desiderato
     * @param chiave con cui effettuare la ricerca
     *
     * @return l'estratto costruito
     */
    @Override
    public EstrattoBase getEstratto(Estratti estratto, Object chiave) {
        /* variabili e costanti locali di lavoro */
        EstrattoBase unEstratto = null;

        try { // prova ad eseguire il codice

            /* selettore della variabile */
            switch ((Anagrafica.Estratto)estratto) {
                case descrizione:
                    unEstratto = this.getEstrattoDescrizione(estratto, chiave);
                    break;
                case ragioneSociale:
                    unEstratto = this.getEstrattoRagioneSociale(estratto, chiave);
                    break;
                case nomeCognome:
                    unEstratto = this.getEstrattoNomeCognome(estratto, chiave);
                    break;
                case etichetta:
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


    /**
     * Regolazione dei filtri per i popup.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br> Metodo sovrascritto nelle sottoclassi <br> (metodo
     * chiamato dalla superclasse) <br>
     */
    @Override
    protected void regolaFiltriPop() {
        /* variabili e costanti locali di lavoro */
        Filtro filtro;
        WrapFiltri popFiltri;
        String testo = "tutte";
        Modulo mod;
        Campo linkCat;
        String sigla;

        try { // prova ad eseguire il codice


            /* utilizza il popup base di filtri */
            popFiltri = super.addPopFiltro();
            popFiltri.setTitolo("privato/società");
            popFiltri.setTesto(testo);
            popFiltri.add(FiltroFactory.crea(Cam.privatosocieta.get(), Anagrafica.Tipi.privato.ordinal() + 1),
                    Anagrafica.Tipi.privato.toString());
            popFiltri.add(FiltroFactory.crea(Cam.privatosocieta.get(), Anagrafica.Tipi.societa.ordinal() + 1),
                    Anagrafica.Tipi.societa.toString());


            /* crea il popup sulla categoria di anagrafica */
            mod = CatAnagraficaModulo.get();
            popFiltri = super.addPopFiltro();
            popFiltri.setTitolo("Categorie");
            popFiltri.setTesto("tutte");
            linkCat = this.getCampo(Anagrafica.Cam.categoria.get());

            /* recupera i valori dalla tabella */
            Ordine ord = new Ordine();
            ord.add(CatAnagrafica.Cam.sigla.get());
            int[] chiavi = mod.query().valoriChiave(ord);

            /* crea una lista di filtri */
            for (int cod : chiavi) {
                sigla = mod.query().valoreStringa(CatAnagrafica.Cam.sigla.get(),cod);
                filtro = FiltroFactory.crea(linkCat, cod);
                popFiltri.add(filtro, sigla);
            } // fine del ciclo for-each

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    private void regolaNavigatore(Navigatore nav) {

        try { // prova ad eseguire il codice
            nav.setUsaRicerca(false);
            nav.setUsaStampaLista(false);
            nav.setIconePiccole();
            nav.setOrizzontale(true);
            nav.setUsaFrecceSpostaOrdineLista(false);
            nav.setRigheLista(3);
            nav.setUsaFinestraPop(true);
            nav.getPortaleLista().setPosToolbar(ToolBar.Pos.ovest);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } // fine del metodo

}// fine della classe
