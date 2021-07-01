/**
 * Title:     ClienteAlbergoModello
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      3-mag-2004
 */
package it.algos.albergo.clientealbergo;

import it.algos.albergo.clientealbergo.indirizzoalbergo.IndirizzoAlbergo;
import it.algos.albergo.clientealbergo.tabelle.autorita.Autorita;
import it.algos.albergo.clientealbergo.tabelle.parente.Parentela;
import it.algos.albergo.clientealbergo.tabelle.parente.ParentelaModulo;
import it.algos.albergo.clientealbergo.tabelle.tipodocumento.TipoDocumento;
import it.algos.albergo.conto.ContoModulo;
import it.algos.albergo.tabelle.lingua.Lingua;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.campo.logica.CLEstratto;
import it.algos.base.campo.logica.CampoLogica;
import it.algos.base.campo.video.CVEstratto;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.lista.Lista;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.portale.Portale;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.tavola.renderer.RendererBase;
import it.algos.base.vista.Vista;
import it.algos.base.vista.VistaElemento;
import it.algos.base.wrapper.CampoValore;
import it.algos.base.wrapper.Estratti;
import it.algos.base.wrapper.EstrattoBase;
import it.algos.base.wrapper.SetValori;
import it.algos.base.wrapper.WrapFiltri;
import it.algos.gestione.anagrafica.Anagrafica;
import it.algos.gestione.anagrafica.categoria.CatAnagrafica;
import it.algos.gestione.anagrafica.cliente.Cliente;
import it.algos.gestione.anagrafica.cliente.ClienteModello;
import it.algos.gestione.indirizzo.Indirizzo;
import it.algos.gestione.indirizzo.IndirizzoModulo;
import it.algos.gestione.indirizzo.tabelle.citta.Citta;
import it.algos.gestione.indirizzo.tabelle.citta.CittaModulo;
import it.algos.gestione.indirizzo.tabelle.nazione.Nazione;
import it.algos.gestione.indirizzo.tabelle.nazione.NazioneModulo;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Date;

/**
 * Tracciato record della tavola ClienteAlbergoModello.
 * <br>
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
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 3-mag-2004 ore 7.58.25
 */
public final class ClienteAlbergoModello extends ClienteModello implements ClienteAlbergo {


    /**
     * titolo della finestra della lista (facoltativo).
     * (se vuoti, mette in automatico il nome della tavola)
     */
    private static final String TITOLO_FINESTRA_LISTA = "";

    /**
     * titolo della finestra della scheda (facoltativo).
     * (se vuoti, mette in automatico il nome della tavola)
     */
    private static final String TITOLO_FINESTRA_SCHEDA = "";

    /**
     * flag per aprire un nuovo cliente come capogruppo oppure no
     */
    private boolean capoGruppo;


    /**
     * Costruttore completo senza parametri.<br>
     */
    public ClienteAlbergoModello() {
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
     * Regolazioni immediate di riferimenti e variabili. <p>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        /* Regola i titoli delle finestre lista e scheda */
        super.regolaTitoli(TITOLO_FINESTRA_LISTA, TITOLO_FINESTRA_SCHEDA);

        /* attiva il trigger modifica con uso dei valori precedenti */
        this.setTriggerModificaAttivo(true, true);

        this.setUsaCampoPreferito(true);

        this.setCapoGruppo(true);

        /* aggiunge all'anagrafica il filtro per i clienti */
        this.addFiltroModello(this.getFiltroCliente());


    }// fine del metodo inizia


    /**
     * Regolazioni iniziali, dopo che sono stati regolati dalla sottoclasse
     * i parametri indispensabili (tra cui il riferimento al modulo)
     * Metodo chiamato dalla classe che crea questo oggetto
     * Viene eseguito una sola volta
     *
     * @param unModulo Abstract Data Types per le informazioni di un modulo
     */
    @Override
    public boolean inizializza(Modulo unModulo) {
        /* variabili e costanti locali di lavoro */
        Campo unCampo;
        boolean riuscito = false;

        try { // prova ad eseguire il codice

            /* regola il campo preferito (capogruppo) */
            unCampo = this.getCampoPreferito();
            unCampo.setTitoloColonna("capo");
            unCampo.setLarLista(40);
            unCampo.setRicercabile(true);
            unCampo.setTestoComponente("capogruppo");   // per il dialogo di ricerca

            riuscito = super.inizializza(unModulo);


            super.setCampoOrdineIniziale(Cliente.Cam.soggetto);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    } /* fine del metodo */


    /**
     * Creazione dei campi di questo modello (oltre a quelli base).
     * <br>
     * i campi verranno visualizzati nell'ordine di inserimento <br>
     * (metodo chiamato dalla superclasse) <br>
     */
    protected void creaCampi() {
        /* variabili e costanti locali di lavoro */
        Campo unCampo;
        CLEstratto clEstratto;
        String chiave;
        int lar = 120;

        /* invoca il metodo sovrascritto della superclasse */
        super.creaCampi();

        /* i campi verranno visualizzati nell'ordine di inserimento */
        try {

            /* campo tipo di cliente (privato/società) */
            unCampo = this.getCampo(Anagrafica.Cam.privatosocieta.get());
            unCampo.decora().eliminaEtichetta();

            /* campo link al capogruppo */
            unCampo = CampoFactory.intero(ClienteAlbergo.Cam.linkCapo);
            unCampo.setVisibileVistaDefault(true);
            this.addCampo(unCampo);

            /* campo link alla tabella parentela */
            unCampo = CampoFactory.comboLinkPop(ClienteAlbergo.Cam.parentela);
            unCampo.setNomeModuloLinkato(Parentela.NOME_MODULO);
            unCampo.setNomeColonnaListaLinkata(Parentela.Cam.descrizione.get());
            unCampo.setNomeCampoValoriLinkato(Parentela.Cam.descrizione.get());
            unCampo.setUsaNuovo(true);
            Campo campo = ParentelaModulo.get().getCampo(Parentela.Cam.descrizione);
            unCampo.setOrdineElenco(campo);
            this.addCampo(unCampo);

            /* recupera il campo indirizzo dalla superclasse
             * regola il navigatore per l'uso a record singolo */
            chiave = Anagrafica.Cam.indirizzi.get();
            this.getCampiModello().remove(chiave);

            /**
             * Campo indirizzo - campo estratto interno con logica
             * specifica per gestione indirizzo di gruppo
             */
            unCampo = CampoFactory.estrattoInterno(ClienteAlbergo.Cam.indirizzoInterno,
                    IndirizzoAlbergo.Est.indirizzoCliente);
            unCampo.setNomeColonnaListaLinkata(Indirizzo.Cam.citta.get());
            CLIndirizzo cl = new CLIndirizzo(unCampo);
            cl.setEstratto(IndirizzoAlbergo.Est.indirizzoCliente); // riassegna l'estratto
            unCampo.setCampoLogica(cl);
            ((CVEstratto)unCampo.getCampoVideoNonDecorato()).setUsaNuovoModificaInsieme(false);
            this.addCampo(unCampo);

            /* campo elenco altri contatti creato nella superclasse
             * lo rimuove dalla collezione  */
            this.removeCampo(Cliente.Cam.contatti);

            /* campo telefono creato nella superclasse
             * lo stringo per far posto al telefono ufficio */
            unCampo = this.getCampo(Cliente.Cam.telefono);
            unCampo.setLarScheda(lar);

            /* campo cellulare creato nella superclasse
             * lo stringo per far posto al telefono ufficio */
            this.getCampo(Cliente.Cam.cellulare).setLarScheda(lar);

            /* campo fax creato nella superclasse
             * lo stringo per omogeneità con gli altri telefoni */
            this.getCampo(Cliente.Cam.fax).setLarScheda(lar);

            /* campo telefono ufficio (a gentile richiesta) */
            unCampo = CampoFactory.testo(ClienteAlbergo.Cam.telUfficio);
            unCampo.setLarScheda(lar);
            this.addCampo(unCampo);

            /* campo data di rilascio del documento */
            unCampo = CampoFactory.data(ClienteAlbergo.Cam.dataDoc);
            unCampo.setInit(null);
            this.addCampo(unCampo);

            /* campo link alla tabella tipo documento */
            unCampo = CampoFactory.comboLinkPop(ClienteAlbergo.Cam.tipoDoc);
            unCampo.setNomeModuloLinkato(TipoDocumento.NOME_MODULO);
            unCampo.setNomeColonnaListaLinkata(TipoDocumento.Cam.sigla.get());
            unCampo.setNomeCampoValoriLinkato(TipoDocumento.Cam.sigla.get());
            unCampo.setLarScheda(150);
            unCampo.setUsaNuovo(true);
            unCampo.setNuovoIniziale(true);
            this.addCampo(unCampo);

            /* campo link alla tabella autorita' */
            unCampo = CampoFactory.comboLinkPop(ClienteAlbergo.Cam.autoritaDoc);
            unCampo.setNomeModuloLinkato(Autorita.NOME_MODULO);
            unCampo.setNomeColonnaListaLinkata(Autorita.Cam.sigla.get());
            unCampo.setNomeCampoValoriLinkato(Autorita.Cam.sigla.get());
            unCampo.setLarScheda(150);
            unCampo.setUsaNuovo(true);
            unCampo.setNuovoIniziale(true);
            this.addCampo(unCampo);

            /* campo numero documento */
            unCampo = CampoFactory.testo(ClienteAlbergo.Cam.numDoc);
            this.addCampo(unCampo);

            /* campo scadenza documento */
            unCampo = CampoFactory.data(ClienteAlbergo.Cam.scadenzaDoc);
            unCampo.setInit(null);
            this.addCampo(unCampo);

            /* campo data di nascita */
            unCampo = CampoFactory.data(ClienteAlbergo.Cam.dataNato);
            unCampo.setInit(null);
            this.addCampo(unCampo);

            /* campo fisico indice del giorno/mese di nascita (calcolato) */
            unCampo = CampoFactory.calcola(ClienteAlbergo.Cam.indiceGiornoNato,
                    CampoLogica.Calcolo.indiceGiornoDellAnno,
                    ClienteAlbergo.Cam.dataNato);
            unCampo.getCampoDB().setCampoFisico(true);
            this.addCampo(unCampo);

            /* campo fisico anno di nascita (calcolato) */
            unCampo = CampoFactory.calcola(ClienteAlbergo.Cam.annoNato,
                    CampoLogica.Calcolo.anno,
                    ClienteAlbergo.Cam.dataNato);
            unCampo.getCampoDB().setCampoFisico(true);
            unCampo.getCampoDati().setUsaSeparatoreMigliaia(false);
            unCampo.setAllineamentoLista(SwingConstants.CENTER);
            this.addCampo(unCampo);

            /* campo luogo di nascita */
            unCampo = CampoFactory.comboLinkSel(ClienteAlbergo.Cam.luogoNato);
            unCampo.setNomeModuloLinkato(Citta.NOME_MODULO);
            unCampo.setNomeColonnaListaLinkata(Citta.Cam.citta.get());
            unCampo.setNomeCampoValoriLinkato(Citta.Cam.citta.get());
            this.addCampo(unCampo);

            /* campo note personali */
            unCampo = CampoFactory.testoArea(ClienteAlbergo.Cam.notePersonali);
            unCampo.setNumeroRighe(30);
            unCampo.setLarScheda(550);
            this.addCampo(unCampo);

            /* campo check box evidenza */
            unCampo = CampoFactory.checkBox(ClienteAlbergo.Cam.checkEvidenza);
            unCampo.setTestoComponente("evidenza");
            unCampo.setRicercabile(true);
            unCampo.setRenderer(new RendererEvidenza(unCampo));
            this.addCampo(unCampo);

            /* campo check box famiglia */
            unCampo = CampoFactory.checkBox(ClienteAlbergo.Cam.checkFamiglia);
            unCampo.setTestoComponente("famiglia");
            unCampo.setRicercabile(true);
            this.addCampo(unCampo);

            /* campo check corrispondenza */
            unCampo = CampoFactory.checkBox(ClienteAlbergo.Cam.checkPosta);
            unCampo.setTestoComponente("corrispondenza");
            unCampo.setLarScheda(160);
            unCampo.setRicercabile(true);
            this.addCampo(unCampo);

            /* campo combo lingua */
            unCampo = CampoFactory.comboLinkPop(ClienteAlbergo.Cam.lingua);
            unCampo.setNomeModuloLinkato(Lingua.NOME_MODULO);
            unCampo.setNomeColonnaListaLinkata(Lingua.Cam.sigla.get());
            unCampo.setNomeCampoValoriLinkato(Lingua.Cam.descrizione.get());
            unCampo.decora().eliminaEtichetta();
            unCampo.decora().etichettaSinistra("lingua");
            unCampo.setRicercabile(true);
            this.addCampo(unCampo);

            /* campo note preparazione */
            unCampo = CampoFactory.testo(ClienteAlbergo.Cam.noteprep);
            unCampo.setLarScheda(150);
            this.addCampo(unCampo);

            /**
             * campo logico data ultimo soggiorno
             * con renderer
             */
            unCampo = CampoFactory.data(ClienteAlbergo.Cam.ultSoggiorno);
            unCampo.getCampoDB().setCampoFisico(false);
            unCampo.setRenderer(new RendererUltSoggiorno(unCampo));
            this.addCampo(unCampo);

            /* regola alcune caratteristiche del campo Note della superclasse */
            unCampo = this.getCampo(Anagrafica.Cam.note);
            if (unCampo != null) {
                unCampo.setLarScheda(380);
                unCampo.setNumeroRighe(3);
                unCampo.decora().eliminaEtichetta();
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo */


    /**
     * Restituisce il filtro di anagrafica.
     * <p/>
     * Filtra solo i records di anagrafica che hanno la categoria=Cliente <br>
     *
     * @return filtro dei clienti
     */
    private Filtro getFiltroCliente() {
        Filtro filtro = null;

        try { // prova ad eseguire il codice
            filtro = FiltroFactory.crea(Anagrafica.Cam.categoria.get(), CatAnagrafica.Tipo.cliente.getCodice());
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return filtro;
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
        VistaElemento elem;

        try { // prova ad eseguire il codice
            super.creaViste();

            /* vista per il navigatore di default */
            vista = new Vista(ClienteAlbergo.Vis.standardAlbergo.toString(), this.getModulo());
            vista.addCampo(ClienteAlbergo.Cam.checkEvidenza.get());
            elem = vista.addCampo(Anagrafica.Cam.soggetto.get());
            elem.setTitoloColonna("cliente");
            elem = vista.addCampo(ClienteAlbergo.Cam.parentela.get());
            elem.setTitoloColonna("parentela");
            vista.addCampo(ClienteAlbergo.Cam.annoNato.get());
            elem = vista.addCampo(Anagrafica.Cam.telefono.get());
            elem.setTitoloColonna("telefono");
            vista.addCampo(Anagrafica.Cam.email.get());
            vista.addCampo(ClienteAlbergo.Cam.capogruppo.get());
            vista.addCampo(ClienteAlbergo.Cam.ultSoggiorno.get());
            this.addVista(vista);

            /* vista per il navigatore del gruppo all'interno della scheda */
            vista = new Vista(ClienteAlbergo.Vis.gruppoParentela.toString(), this.getModulo());
            vista.addCampo(Anagrafica.Cam.soggetto);
            elem = vista.addCampo(ClienteAlbergo.Cam.parentela);
            elem.setTitoloColonna("parentela");
            vista.addCampo(ClienteAlbergo.Cam.annoNato.get());
            this.addVista(vista);

            /* vista per il dialogo di gestione PS */
            vista = new Vista(ClienteAlbergo.Vis.ps.toString(), this.getModulo());
//            mod = CameraModulo.get();
//            campo = mod.getCampo(Camera.Cam.camera.get());
//            vista.addCampo(campo);
            elem = vista.addCampo(Anagrafica.Cam.soggetto);
            elem.setLarghezzaColonna(200);
//            mod = ClienteAlbergoModulo.get();
//            campo = mod.getCampo(ClienteAlbergo.Cam.parentela.get());
            vista.addCampo(ClienteAlbergo.Cam.parentela);
            this.addVista(vista);


            /* vista per il navigatore del gruppo all'interno dello Storico */
            vista = new Vista(ClienteAlbergo.Vis.gruppoStorico.toString(), this.getModulo());

            elem = vista.addCampo(Anagrafica.Cam.soggetto);
            elem.setLarghezzaColonna(150);

            elem = vista.addCampo(ClienteAlbergo.Cam.parentela);
            elem.setTitoloColonna("parentela");
            elem.setLarghezzaColonna(60);

            vista.addCampo(ClienteAlbergo.Cam.capogruppo);

            elem = vista.addCampo(ClienteAlbergo.Cam.annoNato);
            elem.setTitoloColonna("nato");

            elem = vista.addCampo(ClienteAlbergo.Cam.checkEvidenza);
            elem.setTitoloColonna("evid");


            this.addVista(vista);


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }

//    /**
//     * Regolazione delle viste aggiuntive.
//     * <p/>
//     * Metodo invocato dal ciclo inizializza <br>
//     * Eventuale regolazione delle caratteristiche specifiche di ogni copia dei
//     * campi delle viste; le variazioni modificano <strong>solo</strong> le copie <br>
//     * Viene chiamato <strong>dopo</strong> che nella superclasse sono state
//     * <strong>clonate</strong> tutte le viste <br>
//     * Metodo sovrascritto nelle sottoclassi <br>
//     * (metodo chiamato dalla superclasse) <br>
//     *
//     * @see #creaViste
//     */
//    @Override protected void regolaViste() {
//        /* variabili e costanti locali di lavoro */
//        boolean continua;
//        Vista vista;
//        Campo campo = null;
//        Campo campoDesc=null;
//
//        try {    // prova ad eseguire il codice
//            super.regolaViste();
//
//            vista = this.getVista(ClienteAlbergo.Vis.gruppoParentela.toString());
//            continua = (vista != null);
//
//            if (continua) {
//                campoDesc = ParentelaModulo.get().getCampo(Parentela.Cam.descrizione);
//            }// fine del blocco if
//
//            if (continua) {
////                campo = vista.getCampo(ClienteAlbergo.Cam.parentela.get());
//                campo = vista.getCampo(campoDesc);
//                continua = (campo != null);
//            }// fine del blocco if
//
//            if (continua) {
//                campo.setModificabileLista(true);
//            }// fine del blocco if
//        } catch (Exception unErrore) {    // intercetta l'errore
//            new Errore(unErrore);
//        } // fine del blocco try-catch
//    }


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
        Filtro filtro;
        WrapFiltri popFiltri;
        Modulo mod;
        Campo linkPar;
        ArrayList<Integer> lista;
        EstrattoBase est;
        String parentela;

        try { // prova ad eseguire il codice

            /* crea il popup clienti in evidenza */
            popFiltri = super.addPopFiltro();
            popFiltri.setTitolo(ClienteAlbergo.Pop.evidenza.get());
            filtro = FiltroFactory.crea(ClienteAlbergo.Cam.checkEvidenza.get(), true);
            popFiltri.add(filtro, "in evidenza");
            filtro = FiltroFactory.crea(ClienteAlbergo.Cam.checkEvidenza.get(), false);
            popFiltri.add(filtro, "normali");

            /* crea il popup parentela */
            mod = ParentelaModulo.get();

            popFiltri = super.addPopFiltro();
            popFiltri.setTitolo(ClienteAlbergo.Pop.parentela.get());
            linkPar = this.getCampo(ClienteAlbergo.Cam.parentela.get());

            /* recupera i valori dal campo link */
            lista = this.query().valoriDistintiInt(linkPar.getNomeInterno());

            /* crea una lista di filtri */
            for (int cod : lista) {
                filtro = FiltroFactory.crea(linkPar, cod);
                est = mod.getEstratto(Parentela.Est.parentela, cod);
                parentela = est.getStringa();
                popFiltri.add(filtro, parentela);
            } // fine del ciclo for-each

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } /* fine del metodo */


    /**
     * Metodo invocato dopo la creazione di un nuovo record.
     * <p/>
     * Viene sovrascritto dalle classi specifiche <br>
     *
     * @param codice del record creato
     * @param set    oggetto set di valori contenente
     *               i dati appena registrati
     * @param conn   la connessione utilizzata per effettuare la query
     *
     * @return true se riuscito
     */
    protected boolean nuovoRecordPost(int codice, SetValori set, Connessione conn) {

        try { // prova ad eseguire il codice
            /* dopo aver creato un nuovo record, lo pone sempre come capogruppo linkato a se stesso */
            this.query().registraRecordValore(codice,
                    ClienteAlbergo.Cam.capogruppo.get(),
                    true,
                    conn);
            this.query().registraRecordValore(codice,
                    ClienteAlbergo.Cam.linkCapo.get(),
                    codice,
                    conn);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return true;
    } // fine del metodo

//    /**
//     * Metodo invocato dopo la creazione di un nuovo record.
//     * <p/>
//     * Viene sovrascritto dalle classi specifiche <br>
//     *
//     * @param codice del record creato
//     * @param lista  array coppia campo-valore contenente
//     *               i dati appena registrati
//     * @param conn   la connessione utilizzata per effettuare la query
//     *
//     * @return true se riuscito
//     */
//    @Override
//    protected boolean nuovoRecordPost(int codice, ArrayList<CampoValore> lista, Connessione conn) {
//        /* variabili e costanti locali di lavoro */
//        ClienteAlbergoModulo modCliente;
//        String nomeCampo = ClienteAlbergo.Cam.linkCapo.get();
//        int codSelezionato;    // del record precedentemente selezionato
//        int codLink;
//
//        try { // prova ad eseguire il codice
//
//            /* modulo */
//            modCliente = (ClienteAlbergoModulo)this.getModulo();
//
////            /* flag che differenzia le due azioni
////             * di default è vero - se parte l'azione specifica
////             * (nuovo cliente non capogruppo) diventa falso */
////            if (isCapoGruppo()) {
////                /* se è capogruppo il link è a se stesso */
////                this.query().registraRecordValore(codice, nomeCampo, codice);
////                this.query().registraRecordValore(codice, NOME_CAMPO_PREFERITO, true);
////            } else {
////                /* se non è capogruppo */
////                if (isUnRecordSelezionato()) {
////                    codSelezionato = this.recordSelezionato();
////                    codLink = modCliente.query().valoreInt(nomeCampo, codSelezionato);
////                    this.query().registraRecordValore(codice, nomeCampo, codLink);
////                } else {
////                    //?
////                }// fine del blocco if-else
////                this.query().registraRecordValore(codice, NOME_CAMPO_PREFERITO, false);
////            }// fine del blocco if-else
//
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//
//        /* valore di ritorno */
//        return true;
//
//    } // fine del metodo


    /**
     * Controlla se è selezionato uno ed un solo record.
     * <p/>
     *
     * @return true se uno ed un solo record è selezionato.
     */
    private boolean isUnRecordSelezionato() {
        /* variabili e costanti locali di lavoro */
        boolean isUnRecordSelezionato = false;
        Lista lista;

        try {    // prova ad eseguire il codice
            lista = this.getLista();

            if (lista != null) {
                isUnRecordSelezionato = lista.isRigaSelezionata();
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return isUnRecordSelezionato;
    }


    /**
     * Codice chiave del record selezionato.
     * <p/>
     *
     * @return il codice chiave del record selezionato.
     */
    private int recordSelezionato() {
        /* variabili e costanti locali di lavoro */
        int recordSelezionato = 0;
        Lista lista;

        try {    // prova ad eseguire il codice
            lista = this.getLista();

            if (lista != null) {
                recordSelezionato = lista.getChiaveSelezionata();
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return recordSelezionato;
    }


    private Lista getLista() {
        /* variabili e costanti locali di lavoro */
        Lista lista = null;
        boolean continua;
        Modulo mod;
        Navigatore nav = null;
        Portale portale = null;

        try {    // prova ad eseguire il codice
            mod = this.getModulo();
            continua = (mod != null);

            if (continua) {
                nav = mod.getNavigatoreDefault();
                continua = (nav != null);
            }// fine del blocco if

            if (continua) {
                portale = nav.getPortaleLista();
                continua = (portale != null);
            }// fine del blocco if

            if (continua) {
                lista = portale.getLista();
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }


    /**
     * Metodo invocato dopo la registrazione di un record esistente.
     * <p/>
     * Viene sovrascritto dalle classi specifiche <br>
     *
     * @param codice   del record
     * @param lista    array coppia campo-valore contenente
     *                 i dati appena registrati
     * @param listaPre array coppia campo-valore contenente
     *                 i dati precedenti la registrazione
     *
     * @return true se riuscito
     */
    @Override
    protected boolean registraRecordPost(int codice,
                                         ArrayList<CampoValore> lista,
                                         ArrayList<CampoValore> listaPre,
                                         Connessione conn) {

        /* variabili e costanti locali di lavoro */
        boolean continua;
        CampoValore soggPre;
        CampoValore soggPost;
        Campo campoSoggetto;
        String valPre;
        String valPost = "";
        ContoModulo modConti = null;

        try { // prova ad eseguire il codice
            /* quando cambia la sigla, manda un comando al Conto per sincronizzare i conti */
            campoSoggetto = this.getCampo(Cliente.Cam.soggetto.get());
            soggPost = Lib.Camp.getCampoValore(lista, campoSoggetto);

            continua = (soggPost != null);

            if (continua) {
                soggPre = Lib.Camp.getCampoValore(listaPre, campoSoggetto);
                valPre = (String)soggPre.getValore();
                valPost = (String)soggPost.getValore();
                continua = (!valPre.equals(valPost));
            }// fine del blocco if

            if (continua) {
                modConti = ContoModulo.get();
                continua = (modConti != null);
            }// fine del blocco if

            if (continua) {
                modConti.sincronizzaSigla(codice, valPost);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return true;
    } // fine del metodo


    public boolean isCapoGruppo() {
        return capoGruppo;
    }


    public void setCapoGruppo(boolean capoGruppo) {
        this.capoGruppo = capoGruppo;
    }


    /**
     * Restituisce una lista di valori da utilizzare per comporre una riga.
     * </p>
     *
     * @param cod codice cliente
     *
     * @return lista di valori
     */
    private ArrayList<String> getPS(int cod) {
        /* variabili e costanti locali di lavoro */
        ArrayList<String> lista = null;
        boolean continua;
        ClienteAlbergoModulo modCliente = null;
        Modulo modCitta = null;
        Modulo modNazione = null;
        Modulo modIndirizzo = null;
        int codCittaNascita;
        String cognome = "";
        String nome = "";
        String luogo = "";
        Date valNascita;
        String nascita = "";
        int codNazione;
        String nazione = "";
        int codInd;
        int codResidenza;
        String residenza = "";

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(cod));

            if (continua) {
                modCliente = ClienteAlbergoModulo.get();
                continua = (modCliente != null);
            }// fine del blocco if

            if (continua) {
                modCitta = CittaModulo.get();
                continua = (modCitta != null);
            }// fine del blocco if

            if (continua) {
                modNazione = NazioneModulo.get();
                continua = (modNazione != null);
            }// fine del blocco if

            if (continua) {
                modIndirizzo = IndirizzoModulo.get();
                continua = (modIndirizzo != null);
            }// fine del blocco if

            if (continua) {

                cognome = this.query().valoreStringa(Cliente.Cam.cognome.get(), cod);
                nome = this.query().valoreStringa(Cliente.Cam.nome.get(), cod);

                codCittaNascita = this.query().valoreInt(ClienteAlbergo.Cam.luogoNato.get(), cod);
                luogo = modCitta.query().valoreStringa(Citta.Cam.citta.get(), codCittaNascita);
                valNascita = this.query().valoreData(ClienteAlbergo.Cam.dataNato.get(), cod);
                nascita = Lib.Data.getStringa(valNascita);

                codInd = ClienteAlbergoModulo.getCodIndirizzo(cod);

                codResidenza = modIndirizzo.query().valoreInt(Indirizzo.Cam.citta.get(), codInd);
                residenza = modCitta.query().valoreStringa(Citta.Cam.citta.get(), codResidenza);

                codNazione = modCitta.query().valoreInt(Citta.Cam.linkNazione.get(), codResidenza);
                nazione = modNazione.query().valoreStringa(Nazione.Cam.nazione.get(), codNazione);

            }// fine del blocco if

            if (continua) {
                lista = new ArrayList<String>();
                lista.add(cognome);
                lista.add(nome);
                lista.add(luogo);
                lista.add(nascita);
                lista.add(residenza);
                lista.add(nazione);
                lista.add(cognome);
                lista.add(cognome);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }


    /**
     * Restituisce un estratto con una riga di stampa per la PS.
     * </p>
     *
     * @param cod codice cliente
     *
     * @return l'estratto costruito con la stringa ricavata
     */
    private EstrattoBase getListaPS(int cod) {
        /* variabili e costanti locali di lavoro */
        EstrattoBase estratto = null;
        ArrayList<String> lista = null;
        boolean continua;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(cod));

            if (continua) {
                lista = this.getPS(cod);
                continua = (lista != null) && (lista.size() > 0);
            }// fine del blocco if

            if (continua) {
                estratto = new EstrattoBase(lista, EstrattoBase.Tipo.lista);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return estratto;
    }


    /**
     * Restituisce un estratto con una riga di stampa per la PS.
     * </p>
     *
     * @param cod codice cliente
     *
     * @return l'estratto costruito con la stringa ricavata
     */
    private EstrattoBase getStringaPS(int cod) {
        /* variabili e costanti locali di lavoro */
        EstrattoBase estratto = null;
        boolean continua;
        ArrayList<String> lista = null;
        String testo = "";
        String sep = "\t";

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(cod));

            if (continua) {
                lista = this.getPS(cod);
                continua = (lista != null) && (lista.size() > 0);
            }// fine del blocco if

            if (continua) {
                for (String stringa : lista) {
                    testo += stringa;
                    testo += sep;
                } // fine del ciclo for-each
            }// fine del blocco if

            estratto = new EstrattoBase(testo, EstrattoBase.Tipo.stringa);

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
     * @param chiave   con cui effettuare la ricerca
     *
     * @return l'estratto costruito
     */
    public EstrattoBase getEstratto(Estratti estratto, Object chiave) {
        /* variabili e costanti locali di lavoro */
        EstrattoBase unEstratto = null;

        try { // prova ad eseguire il codice

            /* test di congruità tra le Enumeration */
            if (estratto instanceof ClienteAlbergo.Est) {
                switch ((ClienteAlbergo.Est)estratto) {
                    case pubblicaSicurezza:
                        unEstratto = this.getStringaPS(Libreria.getInt(chiave));
                        break;
                    case ps:
                        unEstratto = this.getListaPS(Libreria.getInt(chiave));
                        break;
                    default: // caso non definito
                        break;
                } // fine del blocco switch

            } else {
                unEstratto = super.getEstratto(estratto, chiave);
            }// fine del blocco if-else


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unEstratto;
    }


    /**
     * Renderer del campo Evidenza
     */
    private final class RendererEvidenza extends RendererBase {


        private Icon iconaEvidenza;

        private JLabel label;


        /**
         * Costruttore completo con parametri. <br>
         *
         * @param campo di riferimento
         */
        public RendererEvidenza(Campo campo) {
            /* rimanda al costruttore della superclasse */
            super(campo);

            try { // prova ad eseguire il codice
                /* regolazioni iniziali di riferimenti e variabili */
                this.inizia();
            } catch (Exception unErrore) { // intercetta l'errore
                new Errore(unErrore);
            }// fine del blocco try-catch
        }// fine del metodo costruttore completo


        /**
         * Regolazioni immediate di riferimenti e variabili. <br>
         * Metodo chiamato direttamente dal costruttore <br>
         *
         * @throws Exception unaEccezione
         */
        private void inizia() throws Exception {
            /* variabili e costanti locali di lavoro */

            try { // prova ad eseguire il codice

                iconaEvidenza = Lib.Risorse.getIconaBase("Danger16");

                label = new JLabel();
                label.setOpaque(true);
                label.setHorizontalAlignment(JLabel.CENTER);
                label.setVerticalAlignment(JLabel.CENTER);

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        public Component getTableCellRendererComponent(JTable jTable,
                                                       Object o,
                                                       boolean isSelected,
                                                       boolean b1,
                                                       int i,
                                                       int i1) {
            /* variabili e costanti locali di lavoro */
            boolean evidenza;

            try { // prova ad eseguire il codice

                /* regola icona e testo */
                evidenza = Libreria.getBool(o);
                label.setIcon(null);
                if (evidenza) {
                    label.setIcon(iconaEvidenza);
                }// fine del blocco if

                /** regola i colori della label
                 * in funzione del fatto che la riga sia
                 * selezionata o meno*/
                label.setOpaque(true); // devo rimetterlo se no non funziona
                if (isSelected) {
                    label.setBackground(jTable.getSelectionBackground());
                    label.setForeground(jTable.getSelectionForeground());
                } else {
                    label.setBackground(jTable.getBackground());
                    label.setForeground(jTable.getForeground());
                }

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return label;
        }

    } // fine della classe 'interna'

}// fine della classe
