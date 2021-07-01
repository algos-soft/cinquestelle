/**
 * Copyright: Copyright (c) 2005
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      24-gen-2005
 */
package it.algos.albergo.ristorante.menu;

import it.algos.albergo.ristorante.Ristorante;
import it.algos.albergo.ristorante.righemenupiatto.RMP;
import it.algos.albergo.ristorante.righemenutavolo.RMT;
import it.algos.albergo.tabelle.azienda.Azienda;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.campo.inizializzatore.InitFactory;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.database.util.Operatore;
import it.algos.base.errore.Errore;
import it.algos.base.layout.Layout;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.modello.ModelloAlgos;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.wrapper.CampoValore;

import java.util.ArrayList;
import java.util.Date;

/**
 * Tracciato record della tavola Menu. </p> Questa classe concreta: <ul> <li> Crea il
 * <strong>tracciato record</strong> (Abstract Data Types) di una tavola </li> <li> Crea i
 * <strong>campi</strong> di questo modello (oltre a quelli base della superclasse) nel
 * metodo <code>creaCampi</code> </li> <li> Ogni campo viene creato con un costruttore
 * semplice con solo le piu' comuni informazioni; le altre vengono regolate con chiamate
 * successive </li> <li> Crea eventuali <strong>viste</strong> della <code>Lista</code>
 * (oltre a quella base) nel metodo <code>creaViste</code> </li> <li> Regola eventualmente
 * i valori delle viste nel metodo <code>regolaViste</code> </li> <li> Crea eventuali
 * <strong>set</strong> della <code>Scheda</code> (oltre a quello base) nel metodo
 * <code>creaSet</code> </li> <li> Regola eventualmente i valori dei set nel metodo
 * <code>regolaSet</code> </li> <li> Regola eventualmente i valori da inserire in un
 * <code>nuovoRecord</code> </li> </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 24-gen-2005 ore 11.53.02
 */
public final class MenuModello extends ModelloAlgos implements Menu {

    /**
     * nome della tavola di archivio collegata (facoltativo) <br>
     * i nomi delle tavole sono sempre minuscoli <br>
     * se vuoto usa il nome del modulo <br>
     */
    private static final String TAVOLA_ARCHIVIO = NOME_TAVOLA;


    /**
     * Costruttore completo senza parametri.
     */
    public MenuModello() {
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
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* regola il nome della tavola dalla costante */
        super.setTavolaArchivio(TAVOLA_ARCHIVIO);

        /* attiva la gestione trigger per nuovo record */
        this.setTriggerNuovoAttivo(true);

    }// fine del metodo inizia


    public boolean inizializza(Modulo unModulo) {
        return super.inizializza(unModulo);
    } /* fine del metodo */


    /**
     * Creazione dei campi.
     * <p/>
     * Metodo invocato dal ciclo statico del progetto <br>
     * Creazione dei campi record di questo modello <br>
     * I campi verranno visualizzati nell'ordine di inserimento <br>
     * Ogni campo viene creato con un costruttore semplice con solo le piu' comuni
     * informazioni; le altre vengono regolate con chiamate successive <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     *
     * @see it.algos.base.campo.base.CampoFactory
     * @see it.algos.base.campo.video.decorator.VideoFactory
     */
    protected void creaCampi() {
        /* variabili e costanti locali di lavoro */
        Campo unCampo;
        Navigatore nav;
        Modulo modulo;
        Ordine ordine;

        /* invoca il metodo sovrascritto della superclasse */
        super.creaCampi();

        try { // prova ad eseguire il codice

            /* campo data */
            unCampo = CampoFactory.data(Menu.Cam.data);
            unCampo.setInit(InitFactory.dataAttuale());
            unCampo.setRenderer(new MenuRendererData(unCampo));
            unCampo.setLarLista(95);
            unCampo.setLarScheda(75);
            this.addCampo(unCampo);

            /* campo pasto (1a col, pranzo, cena...) */
            unCampo = CampoFactory.radioMetodo(Menu.Cam.pasto.get());
            unCampo.setVisibileVistaDefault();
            unCampo.setLarLista(80);
            unCampo.getCampoDB().setNomeModuloValori(Ristorante.MODULO_LINGUA);
            unCampo.getCampoDB().setNomeMetodoValori("getNomiPasto");
            unCampo.getCampoDati().setNascondeNonSpecificato(true);
            unCampo.setUsaNonSpecificato(false);
            unCampo.setOrientamentoComponenti(Layout.ORIENTAMENTO_ORIZZONTALE);
            this.addCampo(unCampo);

            /* campo titolo del menu */
            unCampo = CampoFactory.testo(Menu.Cam.titolo);
            this.addCampo(unCampo);

            /* campo navigatore sub-lista piatti */
            modulo = Progetto.getModulo(Ristorante.MODULO_RIGHE_PIATTO);
            nav = modulo.getNavigatore(RMP.NAVIGATORE_IN_MENU);
            unCampo = CampoFactory.navigatore(Menu.Cam.piatti, nav);
            nav.setUsaFrecceSpostaOrdineLista(true);
            nav.setRigheLista(12);
            this.addCampo(unCampo);

            /* campo navigatore sub-lista tavoli */
            unCampo = CampoFactory.navigatoreComposto(Menu.Cam.ordini.get(),
                    Ristorante.MODULO_RIGHE_TAVOLO,
                    RMT.NAV_RMT_RMO);
            unCampo.decora().eliminaEtichetta();
            this.addCampo(unCampo);

            /* campo azienda */
            unCampo = CampoFactory.comboLinkPop(Menu.Cam.azienda);
            unCampo.setNomeModuloLinkato(Azienda.NOME_MODULO);
            unCampo.setNomeCampoValoriLinkato(Azienda.CAMPO_SIGLA);
            unCampo.decora().obbligatorio();
            unCampo.setLarScheda(80);
            unCampo.setUsaNonSpecificato(false);
            this.addCampo(unCampo);

            /* pone l'ordinamento del campo data */
            ordine = new Ordine();
            ordine.add(this.getCampo(Menu.Cam.data.get()), Operatore.DISCENDENTE);
            ordine.add(this.getCampo(Menu.Cam.pasto.get()), Operatore.DISCENDENTE);
            this.getCampo(Menu.Cam.data.get()).setOrdinePrivato(ordine);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
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
        /* variabili e costanti locali di lavoro */
        ArrayList unSet = null;

        try {    // prova ad eseguire il codice

            /* crea il set specifico (piu' campi - uso un array) */
            unSet = new ArrayList();
            unSet.add(Menu.Cam.data.get());
            unSet.add(Menu.Cam.pasto.get());
            unSet.add(Menu.Cam.titolo.get());
            super.creaSet(SET_TITOLO, unSet);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
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
    protected void regolaViste() {
    } /* fine del metodo */


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
    } /* fine del metodo */


    /**
     * Regola i valori dei campi per un nuovo record.
     * <p/>
     * Invocato prima della registrazione.
     * Permette di modificare i campi e i valori che stanno per essere registrati<br>
     * Viene sovrascritto dalle classi specifiche <br>
     * Le eventuali modifiche vanno fatte sulla lista che viene
     * passata come parametro.
     *
     * @param lista array coppia campo-valore contenente i
     * dati che stanno per essere registrati
     * @param conn la connessione utilizzata per effettuare la query
     *
     * @return true per continuare il processo di registrazione,
     *         false per non effettuare la registrazione
     */
    protected boolean nuovoRecordAnte(ArrayList<CampoValore> lista, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        Object valore;
        int codUltimoPasto;
        int codNuovoPasto;
        Date ultimaData;
        Date prossimaData;
        int numPasti;
        int numCol;
        Campo campoGiorno;
        Campo campoPasto;

        numPasti = 3;
        numCol = 1;

        try { // prova ad eseguire il codice

            /* recupera ultimo pasto e ultima data */
            valore = getModulo().query().valorePrimoRecord(Menu.Cam.pasto.get(),
                    Menu.Cam.data.get());
            codUltimoPasto = Libreria.getInt(valore);
            valore = getModulo().query().valorePrimoRecord(Menu.Cam.data.get(),
                    Menu.Cam.data.get());
            ultimaData = Libreria.getDate(valore);

            /* - se l'ultimo pasto era l'ultimo del ciclo, incrementa
             * la data e riparte dal primo
             * - se non era l'ultimo del ciclo, usa la data dell'ultimo
             * e incrementa il pasto*/
            if (codUltimoPasto == numPasti) {
                prossimaData = Lib.Data.add(ultimaData, 1);
                codNuovoPasto = 1;
            } else {
                prossimaData = ultimaData;
                codNuovoPasto = codUltimoPasto + 1;
            }// fine del blocco if-else

            /* solo per Elio che normalmente non usa la colazione */
            if (codNuovoPasto == numCol) {
                codNuovoPasto++;
            }// fine del blocco if

            /* spazzola la lista e sostituisce i valori */
            campoGiorno = this.getCampo(Menu.Cam.data.get());
            campoPasto = this.getCampo(Menu.Cam.pasto.get());
            for (CampoValore cv : lista) {
                if (cv.getCampo().equals(campoGiorno)) {
                    cv.setValore(prossimaData);
                }// fine del blocco if
                if (cv.getCampo().equals(campoPasto)) {
                    cv.setValore(codNuovoPasto);
                }// fine del blocco if

            }

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return true;
    } // fine del metodo

}// fine della classe
