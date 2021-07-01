/**
 * Copyright(c): 2006
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 2 feb 2006
 */

package it.algos.albergo.conto;

import it.algos.albergo.Albergo;
import it.algos.albergo.AlbergoLib;
import it.algos.albergo.AlbergoModulo;
import it.algos.albergo.camera.Camera;
import it.algos.albergo.camera.CameraModulo;
import it.algos.albergo.clientealbergo.ClienteAlbergoModulo;
import it.algos.albergo.conto.addebito.Addebito;
import it.algos.albergo.conto.addebito.AddebitoModulo;
import it.algos.albergo.conto.addebitofisso.AddebitoFisso;
import it.algos.albergo.conto.addebitofisso.AddebitoFissoModulo;
import it.algos.albergo.conto.movimento.Movimento;
import it.algos.albergo.conto.movimento.MovimentoModello;
import it.algos.albergo.conto.movimento.MovimentoModulo;
import it.algos.albergo.conto.pagamento.Pagamento;
import it.algos.albergo.conto.pagamento.PagamentoModulo;
import it.algos.albergo.conto.sconto.Sconto;
import it.algos.albergo.conto.sconto.ScontoModulo;
import it.algos.albergo.conto.sospeso.Sospeso;
import it.algos.albergo.conto.sospeso.SospesoModulo;
import it.algos.albergo.evento.CambioAziendaAz;
import it.algos.albergo.evento.CambioAziendaEve;
import it.algos.albergo.evento.DelAziendeAz;
import it.algos.albergo.evento.DelAziendeEve;
import it.algos.albergo.listino.Listino;
import it.algos.albergo.listino.ListinoModulo;
import it.algos.albergo.pensioni.WrapAddebiti;
import it.algos.albergo.pianodeicontialbergo.conto.AlbContoModulo;
import it.algos.albergo.pianodeicontialbergo.mastro.AlbMastroModulo;
import it.algos.albergo.pianodeicontialbergo.sottoconto.AlbSottocontoModulo;
import it.algos.albergo.presenza.Presenza;
import it.algos.albergo.presenza.PresenzaModulo;
import it.algos.albergo.promemoria.PromemoriaModulo;
import it.algos.albergo.storico.NavStorico;
import it.algos.albergo.tabelle.azienda.AziendaModulo;
import it.algos.base.albero.AlberoNodo;
import it.algos.base.azione.AzModulo;
import it.algos.base.azione.Azione;
import it.algos.base.azione.AzioneBase;
import it.algos.base.campo.base.Campo;
import it.algos.base.costante.CostanteColore;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.database.dati.Dati;
import it.algos.base.dialogo.Dialogo;
import it.algos.base.dialogo.DialogoAnnullaConferma;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.lista.Lista;
import it.algos.base.menu.menu.MenuBase;
import it.algos.base.messaggio.MessaggioAvviso;
import it.algos.base.modello.Modello;
import it.algos.base.modulo.Modulo;
import it.algos.base.modulo.ModuloBase;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.Query;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.query.selezione.QuerySelezione;
import it.algos.base.scheda.Scheda;
import it.algos.base.statusbar.StatusBar;
import it.algos.base.wrapper.Campi;
import it.algos.base.wrapper.SetValori;
import it.algos.gestione.anagrafica.Anagrafica;
import it.algos.gestione.tabelle.mezzopagamento.MezzoPagamentoModulo;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;

/**
 * Conto - Contenitore dei riferimenti agli oggetti del package.
 * <p/>
 * Questa classe concreta: <ul>
 * <li> Contiene tutti i riferimenti agli oggetti delle classi che servono
 * per gestire una parte del programma </li>
 * <li> Regola il riferimento al Modello specifico (obbligatorio) </li>
 * <li> Regola i titoli di Menu e Finestra del Navigatore </li>
 * <li> Regola eventualmente alcuni aspetti specifici del Navigatore </li>
 * <li> Crea altri eventuali <strong>Moduli</strong> indispensabili per il
 * funzionamento di questo modulo </li>
 * <li> Rende visibili nel Menu gli altri moduli </li>
 * <li> Regola eventuali funzionalit&agrave; specifiche del Navigatore </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 2 feb 2006
 */
public final class ContoModulo extends ModuloBase implements Conto {

    /**
     * Nome-chiave interno di questo modulo (obbligatorio)
     */
    private static final String NOME_CHIAVE = Conto.NOME_MODULO;

    /**
     * Titolo della finestra del navigatore
     * (usato nel Navigatore)
     */
    private static final String TITOLO_FINESTRA = Conto.TITOLO_FINESTRA;

    /**
     * Titolo del modulo come appare nel Menu Moduli degli altri moduli
     * (usato nel Navigatore)
     */
    private static final String TITOLO_MENU = Conto.TITOLO_MENU;

    /**
     * nome del programma che compare in basso (se vuoto, usa il default)
     * viene usato il nome del modulo che parte per primo
     */
    private static final String NOME_PROGRAMMA = TITOLO_FINESTRA;

    /**
     * filtro per isolare l'azienda corrente
     */
    private Filtro filtroAzienda;

    /**
     * Pannello per registrazione addebiti in memoria
     * all'apertura di un nuovo conto - creato una volta
     * sola e mantenuto nel Modulo Conto per prestazioni
     */
    private PanAddebitiMem panAddebiti;


    /**
     * Costruttore provvisorio, usato solo per portarsi il percorso
     * della classe (implicito) ed il nome chiave (esplicito).
     */
    public ContoModulo() {
        /* regola la variabile di istanza con la costante */
        super.setNomeModulo(NOME_CHIAVE);

        /* regola il nome del programma (se questo modulo parte per primo) */
        super.setNomeProgramma(NOME_PROGRAMMA);
    } /* fine del metodo costruttore provvisorio */


    /**
     * Costruttore completo.
     *
     * @param unNodo nodo dell'albero moduli
     */
    public ContoModulo(AlberoNodo unNodo) {
        /* rimanda al costruttore della superclasse */
        super(NOME_CHIAVE, unNodo);

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
        /* variabili e costanti locali di lavoro */

        try { // prova ad eseguire il codice

            /* selezione del modello (obbligatorio) */
            super.setModello(new ContoModello());

            /* regola il titolo della finestra del navigatore */
            super.setTitoloFinestra(TITOLO_FINESTRA);

            /* regola il titolo di questo modulo nei menu di altri moduli */
            super.setTitoloMenu(TITOLO_MENU);

            /* regola il flag (le tabelle usano finestre piu' piccole) (facoltativo) */
            super.setTabella(false);

            /* business logic */
            this.setLogicaSpecifica(new ContoLogica(this));

            /* scheda pop*/
            this.setSchedaPop(new ContoScheda(this));

            /* assegna una icona al modulo */
            this.setIcona("conto24");

//            new ContoPref();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regolazioni iniziali <i>una tantum</i>.
     * </p>
     * Metodo chiamato dalla classe Progetto <br>
     * Viene eseguito una sola volta <br>
     * <p/>
     * Inizializza il gestore , prima di tutto (servono i Comandi per
     * inzializzare i Campi) <br>
     * Tenta di inizializzare il modulo <br>
     * Prima inizializza il modello, se e' riuscito
     * inizializza anche gli altri oggetti del modulo <br>
     *
     * @return true se il modulo e' stato inizializzato
     */
    @Override
    public boolean inizializza() {
        /* variabili e costanti locali di lavoro */
        Modulo mod;
        PanAddebitiMem pan;

        super.inizializza();

        try { // prova ad eseguire il codice

            /* crea e registra un oggetto per inserimento addebiti in memoria
             * usato nel dialogo di apertura conto */
            pan = new PanAddebitiMem();
            this.setPanAddebiti(pan);

            /**
             * si registra presso il modulo albergo per
             * essere informato quando cambia l' azienda
             * e quando vengono eliminate aziende
             */
            mod = Progetto.getModulo(Albergo.NOME_MODULO);
            if (mod != null) {
                mod.addListener(AlbergoModulo.Evento.cambioAzienda, new AzioneCambioAzienda());
                mod.addListener(AlbergoModulo.Evento.eliminaAziende, new AzioneDelAziende());
            }// fine del blocco if

            /* regola l'azienda attiva */
            this.cambioAzienda();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return true;
    } // fine del metodo


    /**
     * Regolazioni di avvio, ogni volta che questo oggetto deve 'ripartire',
     * per essere sicuri che sia 'pulito'
     * <p/>
     * Metodo chiamato da altre classi <br>
     * Viene eseguito tutte le volte che necessita  <br>
     */
    @Override public void avvia() {
        try { // prova ad eseguire il codice
            super.avvia();
            this.setAvviato(false);

            /* selezione inziale di un filtro, tramite il popup */
            this.setPopup(Conto.Pop.anni, 1);
            this.setPopup(Conto.Pop.tipi, 1);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } /* fine del metodo */


    /**
     * Creazione e regolazione dei Navigatori.
     * <p/>
     * Metodo invocato dal ciclo inizia del costruttore <br>
     * Regola il Navigatore di default <br>
     * Crea altri Navigatori (oltre a quello standard) <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    protected void creaNavigatori() {
        /* variabili e costanti locali di lavoro */
        Navigatore nav;

        try { // prova ad eseguire il codice

            nav = new ContoNavigatore(this);
            this.addNavigatoreCorrente(nav);

//            /* navigatore dei Conti dentro al dialogo di annulla partenza */
//            nav = new ContoNavPartenze(this);
//            this.addNavigatore(nav);


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Creazione del navigatore per lo storico.
     * <p/>
     */
    public NavStorico getNavStorico() {
        /* variabili e costanti locali di lavoro */
        NavStorico nav = null;

        try { // prova ad eseguire il codice

            /* navigatore usato dal pannello Storico */
            nav = new NavStorico(this, Conto.Nav.storico.get(), Conto.Vis.storico.get());
            nav.inizializza();
            nav.avvia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return nav;
    }



    protected void regolaNavigatori() {
        /* variabili e costanti locali di lavoro */
        Navigatore nav;

        try { // prova ad eseguire il codice
            nav = this.getNavigatoreDefault();
            nav.setAggiornamentoTotaliContinuo(true);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }

    /**
     * Crea tutti i Moduli indispensabili per il funzionamento di questo modulo.
     * </p>
     * Metodo invocato dal ciclo inizia del costruttore <br>
     * Metodo sovrascritto nelle classi specifiche <br>
     */
    protected void creaModuli() {
        try { // prova ad eseguire il codice
            super.creaModulo(new CameraModulo());
            super.creaModulo(new ListinoModulo());
            super.creaModulo(new AziendaModulo());
            super.creaModulo(new ClienteAlbergoModulo());
            super.creaModulo(new AddebitoModulo());
            super.creaModulo(new AddebitoFissoModulo());
            super.creaModulo(new AlbSottocontoModulo());
            super.creaModulo(new PagamentoModulo());
            super.creaModulo(new ScontoModulo());
            super.creaModulo(new SospesoModulo());
//            super.creaModulo(new CameraContoModulo());
            super.creaModulo(new PromemoriaModulo());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }

    /**
     * Aggiunge i moduli (e le tabelle) al menu.
     * </p>
     * Metodo invocato dal ciclo inizializza <br>
     * Aggiunge alla collezione moduli (di questo modulo), gli eventuali
     * moduli (o tabelle), che verranno poi inserite nel menu moduli e
     * tabelle, dalla classe Navigatore <br>
     * I moduli e le tabelle appaiono nei rispettivi menu, nell'ordine in
     * cui sono elencati in questo metodo <br>
     * Per ogni modulo, invoca il metodo della superclasse, passandogli
     * il nome-chiave del modulo <br>
     */
    protected void addModuliVisibili() {
        try { // prova ad eseguire il codice

            // moduli generali
            AlbergoLib.addModuliVisibili(this);

            // moduli specifici
            super.addModuloVisibile(AddebitoFisso.NOME_MODULO);
            super.addModuloVisibile(Addebito.NOME_MODULO);
            super.addModuloVisibile(Pagamento.NOME_MODULO);
            super.addModuloVisibile(Sconto.NOME_MODULO);
            super.addModuloVisibile(Sospeso.NOME_MODULO);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Crea eventuali azioni specifiche del modulo.
     * <p/>
     * Le azioni vengono aggiunte al navigatore corrente <br>
     * Le azioni vengono aggiunte alla
     * toolbar della lista oppure al menu Archivio <br>
     * Metodo invocato dal ciclo inizializza <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     */
    @Override
    protected void creaAzioni() {
        /* variabili e costanti locali di lavoro */
        JMenu menu;
        JMenuItem menuTot;
        Azione azione;
        JMenuItem rigamenu;

        try { // prova ad eseguire il codice

            menuTot = new JMenuItem("Non serve");
            azione = CameraModulo.get().getAzioneModulo();
            rigamenu = new JMenuItem(azione.getAzione());
            menuTot.add(rigamenu);

            azione = ListinoModulo.get().getAzioneModulo();
            rigamenu = new JMenuItem(azione.getAzione());
            menuTot.add(rigamenu);

            azione = AziendaModulo.get().getAzioneModulo();
            rigamenu = new JMenuItem(azione.getAzione());
            menuTot.add(rigamenu);

            menu = new JMenu("Piano dei conti");

            super.creaRigaMenu(AlbMastroModulo.get(), menu);
            super.creaRigaMenu(AlbContoModulo.get(), menu);
            super.creaRigaMenu(AlbSottocontoModulo.get(), menu);

            menuTot.add(menu);


            azione = MezzoPagamentoModulo.get().getAzioneModulo();
            rigamenu = new JMenuItem(azione.getAzione());
            menuTot.add(rigamenu);

            this.setMenuTabelle(menuTot);

            /* chiusura conto */
            super.addAzione(new ChiudiContoAz(this));

            /* saldo sospesi */
            super.addAzione(new SaldoSospesiAz(this));

            /* stampa conto */
            super.addAzione(new StampaContoAz(this));

            /* Registrazione manuale addebiti */
            super.addAzione(new AddebitiAz(this));

            /* esecuzione addebiti fissi */
            super.addAzione(new AutoAddebitiAz(this));

            /* Controllo addebiti mancanti */
            super.addAzione(new AddebitiMancantiAz(this));

            /* riepilogo incassi */
            super.addAzione(new RiepilogoAz());

            /* revisione una tantum 10/07 */
            if (Progetto.isProgrammatore()) {
                super.addAzione(new RevisioneAz());
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


//    /**
//     * Nuovo conto.
//     * </p>
//     * Apre un dialogo <br>
//     * Metodo invocato dal bottone specifico nella toolbar della lista <br>
//     */
//    public void nuovoConto(ActionEvent unEvento) {
//        /* variabili e costanti locali di lavoro */
//        ContoLogica logica;
//
//        try { // prova ad eseguire il codice
//            logica = (ContoLogica)this.getLogicaSpecifica();
//
//            if (logica != null) {
//                logica.nuovoConto();
//            }// fine del blocco if
//
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//    }


    /**
     * Creazione di un nuovo conto.
     * </p>
     *
     * @param wrapConto contenente le informazioni
     * @param conn connessione da utilizzare (se null utilizza quella del modulo Conto)
     *
     * @return il codice del conto creato
     */
    public int nuovoConto(WrapConto wrapConto, Connessione conn) {
        return this.getLogica().nuovoConto(wrapConto, conn);
    }


    /**
     * Aggiunge una serie di addebiti continuativi a un conto.
     * <p/>
     *
     * @param codConto codice del conto
     * @param wrapAddebiti contenente i dati degli addebiti
     * @param conn connessione da utilizzare
     *
     * @return true se riuscito
     */
    public boolean creaAddebiti(int codConto, WrapAddebiti wrapAddebiti, Connessione conn) {
        return this.getLogica().creaAddebiti(codConto, wrapAddebiti, conn);
    }


    /**
     * Inserimento manuale addebiti multipli.
     * </p>
     * Apre un dialogo <br>
     * Metodo invocato dal bottone specifico nella toolbar della lista <br>
     */
    public void inserimentoAddebiti() {
        /* variabili e costanti locali di lavoro */
        AddebitoModulo modAddebito;
        int codContoPartenza = 0;
        Navigatore nav;
        Scheda scheda;
        int codice;

        try { // prova ad eseguire il codice

            modAddebito = AddebitoModulo.get();

            if (modAddebito != null) {
                int[] selezionati = this.getCodContiSelezionati();
                if (selezionati.length == 1) {
                    codContoPartenza = selezionati[0];
                }// fine del blocco if

                modAddebito.esegueAddebiti(codContoPartenza);

                /* aggiorna i campi navigatore nella eventuale scheda aperta*/
                nav = this.getNavigatoreCorrente();
                if (nav != null) {
                    scheda = nav.getScheda();
                    if (scheda != null) {
                        codice = scheda.getCodice();
                        if (codice > 0) {
                            scheda.aggiornaListeNavigatori();
                        }// fine del blocco if
                    }// fine del blocco if

                    /* aggiorna la lista del navigatore corrente per vedere i nuovi totali */
                    nav.aggiornaLista();

                }// fine del blocco if

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Sincronizzazione addebiti fissi.
     * </p>
     * Apre un dialogo <br>
     * Metodo invocato dal bottone specifico nella toolbar della lista <br>
     */
    private void sincronizzazioneAddebitiFissi() {
        /* variabili e costanti locali di lavoro */
        Navigatore nav;
        Lista lista;
        ArrayList<Integer> listaCodici;
        int[] codiciSelezionati;
        int[] codiciEsecuzione;
        Date dataInizio, dataFine;
        ContoDialogoEseguiFissi dialogo;

        Modulo modConto;
        AddebitoFissoModulo modAddFissi;
        boolean chiuso;
        boolean continua = true;

        try { // prova ad eseguire il codice

            /* recupera la lista dei codici selezionati */
            nav = this.getModulo().getNavigatoreCorrente();
            lista = nav.getLista();
            codiciSelezionati = lista.getChiaviSelezionate();

            /* se contiene conti chiusi, visualizza un messaggio */
            if (codiciSelezionati.length > 0) {
                modConto = Albergo.Moduli.Conto();
                for (int cod : codiciSelezionati) {
                    chiuso = modConto.query().valoreBool(Conto.Cam.chiuso.get(), cod);
                    if (chiuso) {
                        new MessaggioAvviso("Non e' possibile addebitare conti chiusi!" +
                                "\nSeleziona solo conti aperti, o nessun conto per addebitarli tutti.");
                        continua = false;
                        break;
                    }// fine del blocco if
                }// fine del blocco for
            }// fine del blocco if


            if (continua) {

                /* presenta il dialogo */
                listaCodici = Lib.Array.creaLista(codiciSelezionati);
                dialogo = new ContoDialogoEseguiFissi(listaCodici);
                dialogo.avvia();

                /* esegue l'operazione */
                if (dialogo.isConfermato()) {
                    dataInizio = dialogo.getDataInizio();
                    dataFine = dialogo.getDataFine();

                    /* crea la lista dei codici conto da controllare */
                    if (codiciSelezionati.length > 0) {
                        if (dialogo.isSoloSelezionati()) {
                            codiciEsecuzione = this.getCodContiSelezionati();
                        } else {
                            codiciEsecuzione = ContoModulo.getCodContiAperti();
                        }// fine del blocco if-else
                    } else {    // tutti i conti aperti
                        codiciEsecuzione = ContoModulo.getCodContiAperti();
                    }// fine del blocco if-else

                    modAddFissi = Albergo.Moduli.AddebitoFisso();
                    if (modAddFissi != null) {
                        listaCodici = Lib.Array.creaLista(codiciEsecuzione);
                        modAddFissi.esegueAddebitiFissi(dataInizio, dataFine, listaCodici);
                    }// fine del blocco if

                }// fine del blocco if
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Controllo addebiti mancanti.
     * </p>
     * Apre un dialogo <br>
     * Metodo invocato dal bottone specifico nella toolbar della lista <br>
     */
    private void controlloAddebitiMancanti() {
        /* variabili e costanti locali di lavoro */
        ContoDialogoControllo dialogo;
        Date dataInizio, dataFine;
        int tipoControllo;
        Modulo modConto;
        Navigatore nav;
        Lista lista;
        int[] codici;
        ArrayList<Integer> codiciSelezionati;
        int[] codiciControllo;

        try { // prova ad eseguire il codice

            modConto = Albergo.Moduli.Conto();

            /* recupera la lista dei codici selezionati */
            nav = modConto.getNavigatoreCorrente();
            lista = nav.getLista();
            codici = lista.getChiaviSelezionate();
            codiciSelezionati = Lib.Array.creaLista(codici);

            dialogo = new ContoDialogoControllo(codiciSelezionati);
            dialogo.avvia();

            if (dialogo.isConfermato()) {
                dataInizio = dialogo.getDataInizio();
                dataFine = dialogo.getDataFine();
                tipoControllo = dialogo.getTipoControllo();

                /* crea la lista dei codici conto da controllare */
                if (codiciSelezionati.size() > 0) {
                    if (dialogo.isSoloSelezionati()) {
                        codiciControllo = this.getCodContiSelezionati();
                    } else {
                        codiciControllo = ContoModulo.getCodContiAperti();
                    }// fine del blocco if-else
                } else {    // tutti i conti aperti
                    codiciControllo = ContoModulo.getCodContiAperti();
                }// fine del blocco if-else

                /* rimuove dall'elenco da controllare tutti i conti aperti
                 * dopo la data di fine controllo, che e' normale non abbiano
                 * addebiti nel periodo controllato */
                ArrayList<Integer> codiciFiltrati;
                codiciFiltrati = new ArrayList<Integer>();
                for (int cod : codiciControllo) {
                    Object oggetto;
                    Date dataApertura;
                    oggetto = modConto.query().valoreCampo(Conto.Cam.dataApertura.get(), cod);
                    if (oggetto instanceof Date) {
                        dataApertura = (Date)oggetto;
                        if (Lib.Data.isPrecedenteUguale(dataFine, dataApertura)) {
                            codiciFiltrati.add(cod);
                        }// fine del blocco if
                    }// fine del blocco if
                }

                this.getLogica().controlloAddebitiMancanti(dataInizio,
                        dataFine,
                        tipoControllo,
                        codiciFiltrati);

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }




    /**
     * Ritorna la lista dei codici di tutti i conti aperti,
     * ordinati per sigla del conto.
     * <p/>
     *
     * @return l'elenco dei conti aperti
     */
    public static int[] getCodContiAperti() {
        /* variabili e costanti locali di lavoro */
        int[] codici = null;
        ContoModulo modConto;
        Filtro filtroConti;
        Ordine ordine;

        try {    // prova ad eseguire il codice

            modConto = Albergo.Moduli.Conto();
            filtroConti = FiltroFactory.crea(Conto.Cam.chiuso.get(), false);
            ordine = new Ordine();
            ordine.add(Conto.Cam.sigla.get());
            codici = modConto.query().valoriChiave(filtroConti, ordine);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codici;
    }


    /**
     * Ritorna la lista dei codici di tutti i conti correntemente selezionati
     * <p/>
     * @return la lista dei codici dei conti selezionati
     */
    private int[] getCodContiSelezionati() {
        /* variabili e costanti locali di lavoro */
        Navigatore nav;
        Lista lista;
        int[] codici = null;

        try {    // prova ad eseguire il codice

            nav = this.getNavigatoreCorrente();
            lista = nav.getLista();
            codici = lista.getChiaviSelezionate();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codici;
    }


    /**
     * Chiusura del conto.
     * </p>
     * Apre un dialogo <br>
     * Metodo invocato dal bottone specifico nella toolbar della lista <br>
     */
    private void chiusuraConto() {
        /* variabili e costanti locali di lavoro */
        ContoLogica.Chiusura chiusura;
        int codice;

        try { // prova ad eseguire il codice
            codice = this.getModulo()
                    .getNavigatoreCorrente()
                    .getLista()
                    .getChiaveSelezionata();
            if (codice > 0) {

                chiusura = this.chiusuraConto(codice, false);

                /* aggiorna la lista del navigatore corrente */
                if (chiusura == ContoLogica.Chiusura.confermato) {
                    this.getNavigatoreCorrente().aggiornaLista();
                }// fine del blocco if

            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Chiusura del conto.
     * </p>
     * Apre un dialogo <br>
     *
     * @param codice del conto da chiudere
     * @param usatoDaPartenza flag per aggiungere un bottone specifico
     *
     * @return titpologia specifica di chiusura
     */
    public ContoLogica.Chiusura chiusuraConto(int codice, boolean usatoDaPartenza) {
        /* variabili e costanti locali di lavoro */
        ContoLogica.Chiusura chiusura = null;

        try { // prova ad eseguire il codice
            if (codice > 0) {
                chiusura = this.getLogica().chiusuraConto(codice, usatoDaPartenza);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return chiusura;
    }


    /**
     * Stampa del conto.
     * </p>
     * Metodo invocato dal bottone specifico nella toolbar della lista <br>
     */
    private void stampaConto() {
        /* variabili e costanti locali di lavoro */
        int codice = 0;
        boolean continua = true;
        ContoDialogoStampa dialogo = null;
        Campo campo = null;
        Conto.TipiStampa tipoStampa;
        boolean raggruppaPens;

        try { // prova ad eseguire il codice

            /* recupero e controllo il codice selezionato */
            if (continua) {
                codice = this.getModulo()
                        .getNavigatoreCorrente()
                        .getLista()
                        .getChiaveSelezionata();
                continua = (codice > 0);
            }// fine del blocco if

            /* dialogo opzioni stampa conto */
            if (continua) {
                dialogo = new ContoDialogoStampa();
                dialogo.avvia();
                continua = dialogo.isConfermato();
            }// fine del blocco if

            /* esegue la stampa */
            if (continua) {
                tipoStampa = dialogo.getTipoStampa();
                raggruppaPens = dialogo.isRaggruppaPensione();
                this.getLogica().stampaConto(codice, tipoStampa, raggruppaPens);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Incasso sospesi.
     * </p>
     * Metodo invocato dal bottone specifico nella toolbar della lista <br>
     */
    private void incassoSospesi() {
        /* variabili e costanti locali di lavoro */
        int codice = 0;
        boolean continua = true;

        try { // prova ad eseguire il codice

            /* recupero il codice selezionato */
            if (continua) {
                codice = this.getModulo()
                        .getNavigatoreCorrente()
                        .getLista()
                        .getChiaveSelezionata();
                continua = (codice > 0);
            }// fine del blocco if

            /* esegue */
            if (continua) {
                this.getLogica().incassoSospesi(codice);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Riepilogo di gestione.
     * </p>
     * Metodo invocato dal bottone specifico nella toolbar della lista <br>
     */
    public void riepilogoGestione(ActionEvent unEvento) {
        /* variabili e costanti locali di lavoro */
        Dialogo dialogo;

        try { // prova ad eseguire il codice

            dialogo = new ContoDialogoRiepilogo();
            dialogo.avvia();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Ritorna la logica specifica di questo modulo.
     * <p/>
     *
     * @return l'oggetto Logica
     */
    private ContoLogica getLogica() {
        /* variabili e costanti locali di lavoro */
        ContoLogica logica = null;

        try {    // prova ad eseguire il codice
            logica = (ContoLogica)this.getLogicaSpecifica();
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return logica;
    }


    /**
     * Regola il filtro del modulo in funzione dell'azienda attiva.
     * <p/>
     */
    public void regolaFiltro() {
        Filtro filtro = null;
        AlbergoModulo modAlbergo;
        Campo campoAzienda;
        int codAz;

        try { // prova ad eseguire il codice
            modAlbergo = AlbergoModulo.get();
            if (modAlbergo != null) {
                codAz = AlbergoModulo.getCodAzienda();
                if (codAz > 0) {
                    campoAzienda = ContoModulo.get().getCampo(Conto.Cam.azienda);
                    filtro = FiltroFactory.crea(campoAzienda, codAz);
                }// fine del blocco if
            }// fine del blocco if

            this.setFiltroAzienda(filtro);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regola la descrizione dell'azienda visibile nella status bar della finestra.
     * <p/>
     */
    private void regolaFinestra() {
        Modulo modulo;
        AlbergoModulo modAlbergo = null;
        String desc;
        Navigatore nav;
        JLabel label;
        boolean continua = true;

        try { // prova ad eseguire il codice

            /* recupera il modulo Albergo se esiste */
            if (continua) {
                continua = false;
                modulo = Progetto.getModulo(Albergo.NOME_MODULO);
                if ((modulo != null) && (modulo instanceof AlbergoModulo)) {
                    modAlbergo = (AlbergoModulo)modulo;
                    continua = true;
                }// fine del blocco if
            }// fine del blocco if

            /* regola la status bar della finestra */
            if (continua) {
                desc = modAlbergo.getDescAziendaAttiva();
                nav = this.getNavigatoreCorrente();
                if (nav != null) {
                    StatusBar sb = nav.getStatusBar();
                    if (sb != null) {
                        label = new JLabel(desc);
                        label.setHorizontalAlignment(JLabel.CENTER);
                        label.setForeground(CostanteColore.BLU);
                        sb.setCenterComponent(label);
                        sb.validate();
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Aggiorna i navigatori del modulo
     * <p/>
     * I Navigatori utilizzano il filtro azienda.
     */
    private void aggiornaNavigatori() {
        LinkedHashMap<String, Navigatore> navigatori;

        try { // prova ad eseguire il codice
            navigatori = this.getNavigatori();
            for (Navigatore nav : navigatori.values()) {
//                nav.setFiltroBase(this.getFiltroAzienda());
                nav.aggiornaLista();
            }
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Restituisce la stringa rappresentante la sigla del conto.
     * <p/>
     *
     * @param codCamera il codice della camera
     * @param codCliente il codice del cliente
     *
     * @return la sigla
     */
    public static String creaSigla(int codCamera, int codCliente) {
        /* variabili e costanti locali di lavoro */
        String sigla = "";
        Modulo modCamera;
        Modulo modCliente;
        String camera;
        String cliente;

        try { // prova ad eseguire il codice
            modCamera = Albergo.Moduli.Camera();
            modCliente = Albergo.Moduli.Cliente();
            camera = modCamera.query().valoreStringa(Camera.Cam.camera.get(), codCamera);
            cliente = modCliente.query().valoreStringa(Anagrafica.Cam.soggetto.get(), codCliente);

            sigla = camera;
            if (Lib.Testo.isValida(cliente)) {
                if (Lib.Testo.isValida(sigla)) {
                    sigla += " - ";
                }// fine del blocco if
                sigla += cliente;
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return sigla;
    }


    /**
     * Aggiorna le date di inizio e fine validità di un conto
     * in base agli addebiti continuativi attualmente presenti.
     * <p/>
     * @param codConto codice del conto da controllare
     * @param cn connessione da utilizzare, se nulla usa quella del modulo Conto
     * @return true se riuscito
     */
    public static boolean updateValidita (int codConto, Connessione cn) {
        /* variabili e costanti locali di lavoro */
        Connessione conn;
        boolean continua=true;

        try {    // prova ad eseguire il codice

            Modulo modConto = ContoModulo.get();

            // seleziona la connessione
            if (cn!=null) {
                conn=cn;
            } else {
                conn=modConto.getConnessione();
            }// fine del blocco if-else
            
            // identifica il periodo di validità
            Modulo modAddFisso = AddebitoFissoModulo.get();
            Query query = new QuerySelezione(modAddFisso);
            query.addCampo(AddebitoFisso.Cam.dataInizioValidita);
            query.addCampo(AddebitoFisso.Cam.dataFineValidita);
            Filtro filtro = FiltroFactory.crea(Addebito.Cam.conto.get(), codConto);
            query.setFiltro(filtro);
            Dati dati = modAddFisso.query().querySelezione(query,conn);

            Date dataMin=null;
            Date dataMax=null;
            for (int k = 0; k < dati.getRowCount(); k++) {

                Date dataIni = dati.getDataAt(k,AddebitoFisso.Cam.dataInizioValidita.get());
                Date dataEnd = dati.getDataAt(k,AddebitoFisso.Cam.dataFineValidita.get());

                if (dataMin==null) {
                    dataMin=dataIni;
                } else {
                    if (Lib.Data.isPrecedente(dataMin,dataIni)) {
                        dataMin=dataIni;
                    }// fine del blocco if
                }// fine del blocco if-else

                if (dataMax==null) {
                    dataMax=dataEnd;
                } else {
                    if (Lib.Data.isPosteriore(dataMax,dataEnd)) {
                        dataMax=dataEnd;
                    }// fine del blocco if
                }// fine del blocco if-else

            } // fine del ciclo for
            dati.close();

            // controlli di congruità finali sulle due date
            if (dataMin==null) {
                dataMin=Lib.Data.getVuota();
            }// fine del blocco if
            if (dataMax==null) {
                dataMax=Lib.Data.getVuota();
            }// fine del blocco if

            // scrive il periodo di validità nel conto
            SetValori sv = new SetValori(modConto);
            sv.add(Conto.Cam.validoDal, dataMin);
            sv.add(Conto.Cam.validoAl, dataMax);
            continua = modConto.query().registraRecordValori(codConto, sv.getListaValori(), conn);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return continua;
    }



    /**
     * Ritorna il totale movimenti di un certo tipo per un conto.
     * <p/>
     *
     * @param codConto codice del conto
     * @param mod modulo movimenti da totalizzare
     * @param conn connessione da utilizzare per la ricerca addebiti
     *
     * @return il totale pensione
     */
    private static double getTotMovimenti(int codConto, Modulo mod, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        double totale = 0;
        Modulo moduloMovi;
        Filtro filtro;
        Number valore;

        try {    // prova ad eseguire il codice

            moduloMovi = mod;
            filtro = FiltroFactory.crea(Movimento.Cam.conto.get(), codConto);

            /* se non è stata passata una connessione usa quella del modulo */
            if (conn == null) {
                conn = moduloMovi.getConnessione();
            }// fine del blocco if

            valore = moduloMovi.query().somma(Addebito.Cam.importo.get(), filtro, conn);
            totale = Libreria.getDouble(valore);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return totale;
    }


    /**
     * Ritorna il totale addebiti per un conto.
     * <p/>
     *
     * @param codConto codice del conto
     * @param tipoAmbito degli addebiti (pensione o extra, null per tutto)
     * @param conn connessione da utilizzare per la ricerca addebiti
     *
     * @return il totale pensione
     */
    public static double getTotAddebiti(int codConto,
                                        Listino.AmbitoPrezzo.Tipo tipoAmbito,
                                        Connessione conn) {
        /* variabili e costanti locali di lavoro */
        double totale = 0;
        Modulo modAddebito;
        Filtro filtro;
        Filtro filtroTot;
        Number valore;
        boolean flag;

        try {    // prova ad eseguire il codice

            modAddebito = AddebitoModulo.get();
            filtroTot = new Filtro();
            filtro = FiltroFactory.crea(Addebito.Cam.conto.get(), codConto);
            filtroTot.add(filtro);

            if (tipoAmbito != null) {
                if (tipoAmbito.equals(Listino.AmbitoPrezzo.Tipo.pensioni)) {
                    flag = true;
                } else {
                    flag = false;
                }// fine del blocco if-else
                filtro = Listino.AmbitoPrezzo.getFiltroListino(flag);
                filtroTot.add(filtro);
            }// fine del blocco if

            /* se non è stata passata una connessione usa quella del modulo */
            if (conn == null) {
                conn = modAddebito.getConnessione();
            }// fine del blocco if

            valore = modAddebito.query().somma(Addebito.Cam.importo.get(), filtroTot, conn);
            totale = Libreria.getDouble(valore);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return totale;
    }


    /**
     * Ritorna il totale pagamenti per un conto.
     * <p/>
     *
     * @param codConto codice del conto
     * @param titolo dei pagamenti (caparra, acconto... o null per tutto)
     * @param conn connessione da utilizzare per la ricerca pagamenti
     *
     * @return il totale pagamenti
     */
    public static double getTotPagamenti(int codConto,
                                         Pagamento.TitoloPagamento titolo,
                                         Connessione conn) {
        /* variabili e costanti locali di lavoro */
        double totale = 0;
        Modulo modPagamento;
        Filtro filtro;
        Filtro filtroTot;
        Number valore;

        try {    // prova ad eseguire il codice

            modPagamento = PagamentoModulo.get();
            filtroTot = new Filtro();
            filtro = FiltroFactory.crea(Pagamento.Cam.conto.get(), codConto);
            filtroTot.add(filtro);

            if (titolo != null) {
                filtro = FiltroFactory.crea(Pagamento.Cam.titolo.get(), titolo.getCodice());
                filtroTot.add(filtro);
            }// fine del blocco if

            /* se non è stata passata una connessione usa quella del modulo */
            if (conn == null) {
                conn = modPagamento.getConnessione();
            }// fine del blocco if

            valore = modPagamento.query().somma(Pagamento.Cam.importo.get(), filtroTot, conn);
            totale = Libreria.getDouble(valore);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return totale;
    }



    /**
     * Crea un filtro che seleziona tutti conti
     * relativi a un dato periodo passando attraverso le presenze
     * (i conti di tutte le presenze relative al periodo)
     * <p/>
     * @param codPeriodo codice del periodo
     * @return il filtro per selezionare i conti
     */
    public static Filtro getFiltroContiPeriodo(int codPeriodo) {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = FiltroFactory.nessuno(ContoModulo.get());  // di default nessun conto
        boolean continua;
        Filtro filtroConto;
        Modulo modPresenze;
        Modulo modConti = null;
        ArrayList lista = null;
        int codConto;
        Filtro filtroPresenze = null;

        try { // prova ad eseguire il codice

            modPresenze = PresenzaModulo.get();
            continua = (modPresenze != null);

            if (continua) {
                modConti = ContoModulo.get();
                continua = (modConti != null);
            }// fine del blocco if

            // filtro che seleziona le presenze
            if (continua) {
                filtroPresenze = PresenzaModulo.getFiltroPresenzePeriodo(codPeriodo);
            }// fine del blocco if

            // elenco univoco dei conti relativi alle presenze
            if (continua) {
                lista = modPresenze.query().valoriDistinti(Presenza.Cam.conto.get(), filtroPresenze);
                continua = (lista != null) && (lista.size() > 0);
            }// fine del blocco if

            // filtro che seleziona i conti
            if (continua) {
                filtro = new Filtro();
                for (Object ogg : lista) {
                    codConto = Libreria.getInt(ogg);
                    filtroConto = FiltroFactory.codice(modConti, codConto);
                    filtro.add(Filtro.Op.OR, filtroConto);
                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return filtro;
    }


    /**
     * Ritorna il totale sconti per un conto.
     * <p/>
     *
     * @param codConto codice del conto
     * @param conn connessione da utilizzare per la query
     *
     * @return il totale sconti
     */
    public static double getTotSconti(int codConto, Connessione conn) {
        return getTotMovimenti(codConto, ScontoModulo.get(), conn);
    }


    /**
     * Ritorna il totale sospesi per un conto.
     * <p/>
     *
     * @param codConto codice del conto
     * @param conn connessione da utilizzare per la query
     *
     * @return il totale sospesi
     */
    public static double getTotSospesi(int codConto, Connessione conn) {
        return getTotMovimenti(codConto, SospesoModulo.get(), conn);
    }


    public Filtro getFiltroAzienda() {
        return filtroAzienda;
    }


    private void setFiltroAzienda(Filtro filtro) {
        this.filtroAzienda = filtro;
    }


    public PanAddebitiMem getPanAddebiti() {
        return panAddebiti;
    }


    private void setPanAddebiti(PanAddebitiMem panAddebiti) {
        this.panAddebiti = panAddebiti;
    }


    /**
     * Invocato quando cambia l'azienda attiva.
     * <p/>
     */
    private void cambioAzienda() {
        try { // prova ad eseguire il codice

            this.getModello().setFiltroModello(this.getFiltroAzienda());
            this.regolaFinestra();
            this.aggiornaNavigatori();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Elimina tutte le aziende tranne quella principale.
     * <p/>
     * Invocato quando viene premuto l'apposito tasto.
     */
    private void delAziende() {
        /* variabili e costanti locali di lavoro */
        boolean continua = true;
        Modulo moduloAzienda;
        Filtro filtro;
        Campo campo;
        int codAzPrincipale = 0;

        try { // prova ad eseguire il codice

            /* recupera il codice della azienda principale e controlla che esista */
            if (continua) {
                moduloAzienda = AziendaModulo.get();
                campo = moduloAzienda.getCampoPreferito();
                filtro = FiltroFactory.crea(campo, Filtro.Op.UGUALE, true);
                codAzPrincipale = moduloAzienda.query().valoreChiave(filtro);
                if (codAzPrincipale == 0) {
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            /* elimina tutti i conti che non appartengono alla azienda principale */
            if (continua) {
                campo = this.getCampo(Conto.Cam.azienda.get());
                filtro = FiltroFactory.crea(campo, Filtro.Op.DIVERSO, codAzPrincipale);
                this.query().eliminaRecords(filtro);
                this.aggiornaNavigatori();
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Azione per cambiare azienda
     */
    private class AzioneCambioAzienda extends CambioAziendaAz {

        /**
         * cambioAziendaAz, da CambioAziendaLis.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo sovrascritto, nell'oggetto specifico
         * della classe che genera questo evento <br>
         * Sovrascritto nelle sottoclassi <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void cambioAziendaAz(CambioAziendaEve unEvento) {
            cambioAzienda();
        }
    } // fine della classe interna


    /**
     * Azione per eliminare delle aziende
     */
    private class AzioneDelAziende extends DelAziendeAz {

        /**
         * cambioAziendaAz, da CambioAziendaLis.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo sovrascritto, nell'oggetto specifico
         * della classe che genera questo evento <br>
         * Sovrascritto nelle sottoclassi <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void delAziendeAz(DelAziendeEve unEvento) {
            delAziende();
        }
    } // fine della classe interna


    public void chiude() {

        super.chiude();

        /* chiude il pannello addebiti memoria che mantiene una connessione */
        this.getPanAddebiti().close();

    }


    /**
     * Ritorna un filtro per identificare i dati dei quali eseguire
     * il backup per questo modulo.
     * <p/>
     *
     * @return il filtro da applicare in fase di backup
     */
    public Filtro getFiltroBackup() {
        ContoModulo modulo = ContoModulo.get();
        return modulo.getFiltroAzienda();
    }


    /**
     * Sincronizzazione della sigla di tutti i conti di un cliente.
     * </p>
     * Chiamato dal trigger di ClienteAlbergo <br>
     */
    public void sincronizzaSigla(int codCliente, String sigla) {
        /* variabili e costanti locali di lavoro */
        int[] valoriCod;
        Filtro filtro;
        int cod;
        int codCamera;
        String newSigla;

        try { // prova ad eseguire il codice
            filtro = FiltroFactory.crea(Conto.Cam.pagante.get(), codCliente);

            valoriCod = this.query().valoriChiave(filtro);

            for (int k = 0; k < valoriCod.length; k++) {
                cod = valoriCod[k];
                codCamera = this.query().valoreInt(Conto.Cam.camera.get(), cod);
                newSigla = this.creaSigla(codCamera, codCliente);
                this.query().registraRecordValore(cod, Conto.Cam.sigla.get(), newSigla);
            } // fine del ciclo for

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Aggiorna un totale di un dato conto in base ai movimenti.
     * <p/>
     *
     * @param codConto da aggiornare
     * @param modMovi modulo movimenti per il quale aggiornare il totale
     * @param conn connessione da utilizzare
     *
     * @return true se riuscito
     */
    public boolean updateTotale(int codConto, MovimentoModulo modMovi, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        double totale;
        Campo campoSync;
        boolean continua = true;
        boolean riuscito = true;


        try { // prova ad eseguire il codice

            /* se la connessione è nulla usa quella del modulo */
            if (conn == null) {
                conn = this.getConnessione();
            }// fine del blocco if

            /* recupera il campo sincronizzato */
            campoSync = this.getCampoSync(modMovi);
            if (campoSync == null) {
                continua = false;
            }// fine del blocco if

            /* recupera il totale dei movimenti */
            /* registra il valore */
            if (continua) {
                totale = ContoModulo.getTotMovimenti(codConto, modMovi, conn);
                riuscito = this.query().registraRecordValore(codConto, campoSync, totale, conn);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Ritorna il campo di Conto il cui valore deve essere sincronizzato
     * con i movimenti.
     * <p/>
     *
     * @param mod il modulo Movimenti da sincronizzare
     *
     * @return il campo di Conto sincronizzato
     */
    private Campo getCampoSync(MovimentoModulo mod) {
        /* variabili e costanti locali di lavoro */
        Campo campo = null;
        String nomeCampo = null;
        Modello modello = null;
        MovimentoModello modelloMov;
        Campi csync = null;
        boolean continua = true;

        try {    // prova ad eseguire il codice

            /* recupera il modello */
            if (continua) {
                modello = mod.getModello();
                if (modello == null) {
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            /* si accerta che sia della classe attesa */
            if (continua) {
                if (!(modello instanceof MovimentoModello)) {
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            /* effettua il casting e recupera il campo della enum */
            if (continua) {
                modelloMov = (MovimentoModello)modello;
                csync = modelloMov.getCampoContoSync();
                if (csync == null) {
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            /* recupera il campo dal modulo Conto */
            if (continua) {
                nomeCampo = csync.get();
                campo = this.getCampo(nomeCampo);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return campo;
    }




    /**
     * Restituisce in runtime l'istanza presente in progetto.
     * <p/>
     *
     * @return modulo presente in Progetto
     */
    public static ContoModulo get() {
        return (ContoModulo)ModuloBase.get(ContoModulo.NOME_CHIAVE);
    }



    public final class ChiudiContoAz extends AzModulo {

        public static final String CHIAVE = "ChiudiContoAz";


        /**
         * Costruttore completo con parametri.
         *
         * @param modulo di riferimento
         */
        public ChiudiContoAz(Modulo modulo) {
            /* rimanda al costruttore della superclasse */
            super(modulo, POS);

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
         * Metodo chiamato direttamente dal costruttore <br>
         *
         * @throws Exception unaEccezione
         */
        private void inizia() throws Exception {
            /* regola le variabili*/
            super.setChiave(CHIAVE);
            super.setNome("Chiusura conto");
            super.setTooltip("Chiusura di un conto");
            super.setHelp("");
            super.setIconaPiccola("");
            super.setIconaMedia("ChiusuraConto24");
            super.setIconaGrande("");
            super.setCarattereAcceleratore(' ');
            super.setCarattereMnemonico(0);
            super.setCarattereComando(null);
            super.setAttiva(true);
            super.setAbilitataPartenza(true);
            super.setColonnaMenu(MenuBase.MenuTipo.ARCHIVIO);
            super.setUsoLista(true);
        }// fine del metodo inizia


        /**
         * actionPerformed, da ActionListener.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo delegato, nel gestore specifico associato
         * all' oggetto che genera questo evento <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void actionPerformed(ActionEvent unEvento) {
            try { // prova ad eseguire il codice
                chiusuraConto();
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }

    }// fine della classe


    public final class StampaContoAz extends AzModulo {

        public static final String CHIAVE = "StampaContoAz";


        /**
         * Costruttore completo con parametri.
         *
         * @param modulo di riferimento
         */
        public StampaContoAz(Modulo modulo) {
            /* rimanda al costruttore della superclasse */
            super(modulo, POS);

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
         * Metodo chiamato direttamente dal costruttore <br>
         *
         * @throws Exception unaEccezione
         */
        private void inizia() throws Exception {
            /* regola le variabili*/
            super.setChiave(CHIAVE);
            super.setNome("Stampa conto");
            super.setTooltip("Stampa di un conto");
            super.setHelp("");
            super.setIconaPiccola("");
            super.setIconaMedia("StampaConto24");
            super.setIconaGrande("");
            super.setCarattereAcceleratore(' ');
            super.setCarattereMnemonico(0);
            super.setCarattereComando(null);
            super.setAttiva(true);
            super.setAbilitataPartenza(true);
            super.setColonnaMenu(MenuBase.MenuTipo.ARCHIVIO);
            super.setUsoLista(true);
        }// fine del metodo inizia


        /**
         * actionPerformed, da ActionListener.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo delegato, nel gestore specifico associato
         * all' oggetto che genera questo evento <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void actionPerformed(ActionEvent unEvento) {
            try { // prova ad eseguire il codice
                stampaConto();
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }

    }// fine della classe


    /**
     * Azione pagamento sospesi per un conto
     */
    public final class SaldoSospesiAz extends AzModulo {

        public static final String CHIAVE = "pagaSospesiAz";


        /**
         * Costruttore completo con parametri.
         *
         * @param modulo di riferimento
         */
        public SaldoSospesiAz(Modulo modulo) {
            /* rimanda al costruttore della superclasse */
            super(modulo, POS);

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
         * Metodo chiamato direttamente dal costruttore <br>
         *
         * @throws Exception unaEccezione
         */
        private void inizia() throws Exception {
            /* regola le variabili*/
            super.setChiave(CHIAVE);
            super.setNome("Incasso sospesi");
            super.setTooltip("Incasso sospesi");
            super.setHelp("");
            super.setIconaPiccola("");
            super.setIconaMedia("SaldoSospesi24");
            super.setIconaGrande("");
            super.setCarattereAcceleratore(' ');
            super.setCarattereMnemonico(0);
            super.setCarattereComando(null);
            super.setAttiva(true);
            super.setAbilitataPartenza(true);
            super.setColonnaMenu(MenuBase.MenuTipo.ARCHIVIO);
            super.setUsoLista(true);
        }// fine del metodo inizia


        /**
         * actionPerformed, da ActionListener.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo delegato, nel gestore specifico associato
         * all' oggetto che genera questo evento <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void actionPerformed(ActionEvent unEvento) {
            try { // prova ad eseguire il codice
                incassoSospesi();
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }

    }// fine della classe


    private final class AddebitiAz extends AzModulo {

        /**
         * Costruttore completo con parametri.
         *
         * @param modulo di riferimento
         */
        public AddebitiAz(Modulo modulo) {
            /* rimanda al costruttore della superclasse */
            super(modulo, POS);

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
         * Metodo chiamato direttamente dal costruttore <br>
         *
         * @throws Exception unaEccezione
         */
        private void inizia() throws Exception {
            /* regola le variabili*/
            super.setChiave("AddebitiAz");
            super.setNome("Registrazione addebiti");
            super.setTooltip("Registrazione addebiti");
            super.setHelp("");
            super.setIconaPiccola("");
            super.setIconaMedia("Addebiti24");
            super.setIconaGrande("");
            super.setCarattereAcceleratore(' ');
            super.setCarattereMnemonico(0);
            super.setCarattereComando(null);
            super.setAttiva(true);
            super.setAbilitataPartenza(true);
            super.setColonnaMenu(MenuBase.MenuTipo.ARCHIVIO);
            super.setUsoLista(true);
        }// fine del metodo inizia


        /**
         * actionPerformed, da ActionListener.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo delegato, nel gestore specifico associato
         * all' oggetto che genera questo evento <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void actionPerformed(ActionEvent unEvento) {
            try { // prova ad eseguire il codice
                inserimentoAddebiti();
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }

    }// fine della classe


    private final class AutoAddebitiAz extends AzModulo {

        /**
         * Costruttore completo con parametri.
         *
         * @param modulo di riferimento
         */
        public AutoAddebitiAz(Modulo modulo) {
            /* rimanda al costruttore della superclasse */
            super(modulo, POS);

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
         * Metodo chiamato direttamente dal costruttore <br>
         *
         * @throws Exception unaEccezione
         */
        private void inizia() throws Exception {
            /* regola le variabili*/
            super.setChiave("AutoAddebitiAz");
            super.setNome("Autoaddebiti");
            super.setTooltip("Esecuzione degli addebiti giornalieri");
            super.setHelp("");
            super.setIconaPiccola("");
            super.setIconaMedia("Autoaddebiti24");
            super.setIconaGrande("");
            super.setCarattereAcceleratore(' ');
            super.setCarattereMnemonico(0);
            super.setCarattereComando(null);
            super.setAttiva(true);
            super.setAbilitataPartenza(true);
            super.setColonnaMenu(MenuBase.MenuTipo.ARCHIVIO);
            super.setUsoLista(true);
        }// fine del metodo inizia


        /**
         * actionPerformed, da ActionListener.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo delegato, nel gestore specifico associato
         * all' oggetto che genera questo evento <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void actionPerformed(ActionEvent unEvento) {
            try { // prova ad eseguire il codice
                sincronizzazioneAddebitiFissi();
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }

    }// fine della classe


    private final class AddebitiMancantiAz extends AzModulo {

        /**
         * Costruttore completo con parametri.
         *
         * @param modulo di riferimento
         */
        public AddebitiMancantiAz(Modulo modulo) {
            /* rimanda al costruttore della superclasse */
            super(modulo, POS);

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
         * Metodo chiamato direttamente dal costruttore <br>
         *
         * @throws Exception unaEccezione
         */
        private void inizia() throws Exception {
            /* regola le variabili*/
            super.setChiave("AddebitiMancantiAz");
            super.setNome("Addebiti mancanti");
            super.setTooltip("Controllo degli addebiti mancanti");
            super.setHelp("");
            super.setIconaPiccola("");
            super.setIconaMedia("AddebitiMancanti24");
            super.setIconaGrande("");
            super.setCarattereAcceleratore(' ');
            super.setCarattereMnemonico(0);
            super.setCarattereComando(null);
            super.setAttiva(true);
            super.setAbilitataPartenza(true);
            super.setColonnaMenu(MenuBase.MenuTipo.ARCHIVIO);
            super.setUsoLista(true);
        }// fine del metodo inizia


        /**
         * actionPerformed, da ActionListener.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo delegato, nel gestore specifico associato
         * all' oggetto che genera questo evento <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void actionPerformed(ActionEvent unEvento) {
            try { // prova ad eseguire il codice
                controlloAddebitiMancanti();
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }

    }// fine della classe


    private final class RiepilogoAz extends AzioneBase {

        /**
         * Costruttore completo con parametri.
         */
        public RiepilogoAz() {
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
         * Metodo chiamato direttamente dal costruttore <br>
         *
         * @throws Exception unaEccezione
         */
        private void inizia() throws Exception {
            /* regola le variabili*/
            super.setTooltip("Riepilogo di gestione");
            super.setIconaMedia("Torta24");
            super.setUsoLista(true);
        }// fine del metodo inizia


        /**
         * actionPerformed, da ActionListener.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo delegato, nel gestore specifico associato
         * all' oggetto che genera questo evento <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void actionPerformed(ActionEvent unEvento) {
            try { // prova ad eseguire il codice
                riepilogoGestione(unEvento);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }

    }// fine della classe


    private final class RevisioneAz extends AzioneBase {

        /**
         * Costruttore completo con parametri.
         */
        public RevisioneAz() {
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
         * Metodo chiamato direttamente dal costruttore <br>
         *
         * @throws Exception unaEccezione
         */
        private void inizia() throws Exception {
            /* regola le variabili*/
            super.setTooltip("Revisione movimenti una tantum");
            super.setIconaMedia("Refresh24");
            super.setUsoLista(true);
        }// fine del metodo inizia


        /**
         * actionPerformed, da ActionListener.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo delegato, nel gestore specifico associato
         * all' oggetto che genera questo evento <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void actionPerformed(ActionEvent unEvento) {
            try { // prova ad eseguire il codice
                /* chiede conferma */
                Dialogo mex = new DialogoAnnullaConferma("Eseguo revisione dati?");
                mex.avvia();
                if (mex.isConfermato()) {
//                    revisioneUnaTantum1007();
                }// fine del blocco if
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }

    }// fine della classe


    /**
     * Main method.
     *
     * @param argomenti eventuali parametri in ingresso (quasi mai)
     */
    public static void main(String[] argomenti) {
        /* crea una istanza di se stessa */
        Modulo primoModuloProvvisorio = new ContoModulo();

        /**
         * inizializza tutti i moduli usati da questo modulo <br>
         * lancia il modulo iniziale del programma (obbligatorio) <br>
         */
        Progetto.lancia(argomenti, primoModuloProvvisorio);
    } // fine del metodo main


} // fine della classe
