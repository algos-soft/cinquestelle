/**
 * Copyright: Copyright (c) 2005
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      2-mar-2005
 */
package it.algos.albergo.ristorante.righemenutavolo;

import it.algos.albergo.ristorante.Ristorante;
import it.algos.albergo.ristorante.menu.Menu;
import it.algos.albergo.ristorante.tavolo.Tavolo;
import it.algos.base.azione.AzModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.database.dati.Dati;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.messaggio.MessaggioAvviso;
import it.algos.base.messaggio.MessaggioDialogo;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.NavigatoreBase;
import it.algos.base.navigatore.NavigatoreLS;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.Query;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.modifica.QueryUpdate;
import it.algos.base.query.selezione.QuerySelezione;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

/**
 * Navigatore Master del navigatore NN nel campo del menu.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 2-mar-2005 ore 9.44.32
 */
public final class RMTNavInMenu extends NavigatoreLS {

    /* moduli e campi di uso comune */
    private static Modulo moduloTavolo = null;

    private static Modulo moduloMenu = null;

    private static Campo campoTavoloChiave = null;

    private static Campo campoTavoloOccupato = null;

    private static Campo campoTavoloMezzaPensione = null;

    private static Campo campoTavoloPasto = null;

    private static Campo campoTavoloCliente = null;

    private static Campo campoTavoloCoperti = null;

    private static Campo campoRMTChiave = null;

    private static Campo campoRMTLinkMenu = null;


    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param modulo modulo di riferimento
     */
    public RMTNavInMenu(Modulo modulo) {

        /* rimanda al costruttore della superclasse */
        super(modulo);

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
        /* variabili e costanti locali di lavoro */
        this.setNomeChiave(RMT.NAV_IN_MENU);
        this.setOrizzontale(true);
        this.setUsaPannelloUnico(true);
        this.setUsaRicerca(true);
        this.setAggiornamentoTotaliContinuo(true);
        this.setNomeVista(RMT.VISTA_IN_MENU);
        this.setNomeSet(RMT.SET_TAVOLO_CAMERA);
        this.setRigheLista(8);
        this.addScheda(new RMTScheda(this.getModulo()));
        this.setIconeMedie();

    }// fine del metodo inizia


    /**
     * Regolazioni dinamiche dell'oggetto.
     * <p/>
     * Regola le caratteristiche dinamiche in base alla impostazione
     * corrente delle caratteristiche statiche <br>
     * Metodo invocato dalla classe che crea questo oggetto (di norma) <br>
     * Viene eseguito una sola volta <br>
     * Sovrascritto nelle sottoclassi <br>
     */
    public void inizializza() {

        try { // prova ad eseguire il codice
            /* invoca il metodo sovrascritto della superclasse */
            super.inizializza();

            this.regolaComune();

            /* aggiunge l'azione Crea Tavoli alla lista  */
            this.addAzione(new AzCreaTavoli(this.getModulo()));

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regola i riferimenti ad oggetti di uso comune..
     * <p/>
     */
    private void regolaComune() {
        /* variabili e costanti locali di lavoro */

        try {    // prova ad eseguire il codice

            /* moduli */
            moduloTavolo = Progetto.getModulo(Ristorante.MODULO_TAVOLO);
            moduloMenu = Progetto.getModulo(Ristorante.MODULO_MENU);

            /* campi */
            campoTavoloChiave = moduloTavolo.getCampoChiave();
            campoRMTChiave = this.getModulo().getCampoChiave();
            campoRMTLinkMenu = this.getModulo().getCampo(RMT.Cam.menu);
            campoTavoloOccupato = moduloTavolo.getCampo(Tavolo.Cam.occupato);
            campoTavoloMezzaPensione = moduloTavolo.getCampo(Tavolo.Cam.mezzapensione);
            campoTavoloPasto = moduloTavolo.getCampo(Tavolo.Cam.pasto);
            campoTavoloCliente = moduloTavolo.getCampo(Tavolo.Cam.nomecliente);
            campoTavoloCoperti = moduloTavolo.getCampo(Tavolo.Cam.numcoperti);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Sincronizza lo stato del Navigatore.
     * <p/>
     * Controlla lo stato di tutti i suoi componenti <br>
     * Elabora la sua business logic <br>
     * Regola la GUI di ogni conseguenza (menu, bottoni, ecc) <br>
     * Invoca il metodo sovrascritto della superclasse <br>
     */
    public void sincronizza() {
        try { // prova ad eseguire il codice
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* invoca il metodo sovrascritto della superclasse */
        super.sincronizza();
    }


    /**
     * Crea i tavoli per il menu corrente.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Cancella tutti i tavoli del menu corrente.<br>
     * Crea i tavoli in base all'attuale piano dei tavoli e al pasto di questo menu<br>
     */
    public void creaTavoli() {

        try { // prova ad eseguire il codice

            /* controlla se il codice pasto e' specificato per il menu corrente */
            if (this.getCodicePastoMenu() != 0) {

                /* cancella tutti i tavoli del menu corrente */
                if (this.eliminaTuttiTavoli()) {

                    /* crea i tavoli */
                    this.creaTavoliMenu();

                    /* riavvia il navigatore per aggiornare la lista */
                    this.avvia();

                    /* contrassegna il navigatore come modificato */
                    super.fire(NavigatoreBase.Evento.statoModificato);

                    /* sincronizza nuovamente il navigatore */
                    this.sincronizza();


                }// fine del blocco if
            } else {
                new MessaggioAvviso("Il tipo di pasto del menu non e' specificato.");
            }// fine del blocco if-else

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Elimina tutti i tavoli del menu corrente.
     * <p/>
     * Se ci sono tavoli chiede conferma.<br>
     *
     * @return true se ha eliminato tutti i tavoli o non c'erano tavoli da eliminare.
     */
    private boolean eliminaTuttiTavoli() {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = false;
        int quanti = 0;
        MessaggioDialogo dialogo = null;
        int risposta = 0;

        try {    // prova ad eseguire il codice
            quanti = this.getModulo().query().contaRecords(this.filtroTavoli());
            if (quanti > 0) {
                dialogo = new MessaggioDialogo("Attenzione!\n" +
                        "Nel menu ci sono gia' " +
                        quanti +
                        " tavoli.\n" +
                        "Vuoi eliminarli e ricreare i tavoli?");
                risposta = dialogo.getRisposta();
                if (risposta == 0) {
                    riuscito = this.getModulo().query().eliminaRecords(this.filtroTavoli());
                }// fine del blocco if

            } else {
                riuscito = true;
            }// fine del blocco if-else

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Crea i tavoli per il menu corrente.
     * <p/>
     * Seleziona tutti i tavoli attualmente marcati occupati.
     * Spazzola i tavoli e crea il corrispondente tavolo nel menu.
     * Se il tavolo e' marcato mezza pensione, lo crea solo se il
     * pasto del menu e' lo stesso di quello indicato nel tavolo.
     */
    private void creaTavoliMenu() {
        /* variabili e costanti locali di lavoro */
        ArrayList tavoliCreabili = null;
        DatiTavolo datoTavolo = null;

        int chiaveTavolo = 0;
        String clienteTavolo = "";
        int copertiTavolo = 0;

        int codice = 0;
        Query query = null;

        try {    // prova ad eseguire il codice
            tavoliCreabili = this.getTavoliCreabili();

            for (int k = 0; k < tavoliCreabili.size(); k++) {
                datoTavolo = (DatiTavolo)tavoliCreabili.get(k);

                chiaveTavolo = datoTavolo.getChiaveTavolo();
                clienteTavolo = datoTavolo.getCamera();
                copertiTavolo = datoTavolo.getCoperti();

                /* chiede al navigatore la creazione di un record
                 * il record creato e' gia' linkato al menu */
                codice = this.creaRecord();

                /* regola il nuovo record in base ai dati del tavolo  */
                if (codice != -1) {
                    query = new QueryUpdate(this.getModulo());
                    query.addCampo(RMT.Cam.tavolo.get(), new Integer(chiaveTavolo));
                    query.addCampo(RMT.Cam.camera.get(), clienteTavolo);
                    query.addCampo(RMT.Cam.coperti.get(), new Integer(copertiTavolo));
                    query.setFiltro(FiltroFactory.crea(campoRMTChiave, codice));
                    this.getModulo().query().queryModifica(query);
                }// fine del blocco if

            } // fine del ciclo for

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Ritorna la lista dei tavoli creabili per questo menu.
     * <p/>
     * Un tavolo e' creabile se e' occupato a pensione completa o se e'
     * occupeto a 1/2 pensione e ha lo stesso codice pasto del menu corrente
     *
     * @return l'una lista di oggetti DatiTavolo (classe interna)
     */
    private ArrayList getTavoliCreabili() {
        /* variabili e costanti locali di lavoro */
        ArrayList<DatiTavolo> tavoliCreabili = null;
        boolean creabile = false;
        int chiaveTavolo = 0;
        boolean mezzaPensione = false;
        int codicePastoTavolo = 0;
        String clienteTavolo = "";
        int copertiTavolo = 0;
        Query query = null;
        Filtro filtro = null;
        Dati dati = null;
        DatiTavolo dato = null;

        try {    // prova ad eseguire il codice

            tavoliCreabili = new ArrayList<DatiTavolo>();

            query = new QuerySelezione(moduloTavolo);
            query.addCampo(campoTavoloChiave);
            query.addCampo(campoTavoloMezzaPensione);
            query.addCampo(campoTavoloPasto);
            query.addCampo(campoTavoloCliente);
            query.addCampo(campoTavoloCoperti);
            filtro = FiltroFactory.crea(campoTavoloOccupato, true);
            query.setFiltro(filtro);
            dati = moduloTavolo.query().querySelezione(query);

            for (int k = 0; k < dati.getRowCount(); k++) {
                chiaveTavolo = Libreria.objToInt(dati.getValueAt(k, 0));
                mezzaPensione = Libreria.objToBool(dati.getValueAt(k, 1));
                codicePastoTavolo = Libreria.objToInt(dati.getValueAt(k, 2));
                clienteTavolo = Lib.Testo.getStringa(dati.getValueAt(k, 3));
                copertiTavolo = Libreria.objToInt(dati.getValueAt(k, 4));

                creabile = true;
                if (mezzaPensione) {
                    if (codicePastoTavolo != this.getCodicePastoMenu()) {
                        creabile = false;
                    }// fine del blocco if
                }// fine del blocco

                if (creabile) {
                    dato = new DatiTavolo();
                    dato.setChiaveTavolo(chiaveTavolo);
                    dato.setCamera(clienteTavolo);
                    dato.setCoperti(copertiTavolo);
                    tavoliCreabili.add(dato);
                }// fine del blocco if

            } // fine del ciclo for

            dati.close();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return tavoliCreabili;
    }


    /**
     * Ritorna il codice del pasto del menu corrente.
     * <p/>
     * Il codice e' recuperato dalla scheda.
     *
     * @return il codice del pasto.
     */
    private int getCodicePastoMenu() {
        /* variabili e costanti locali di lavoro */
        int codicePasto = 0;

        try {    // prova ad eseguire il codice

            // todo provvisorio alex 09-03-05
            // todo il valore del pasto va recuperato dalla scheda del menu
            // todo non riesco a risalire al portale
            // todo il campo che contiene il Navigatore non ha riferimento al portale
            // todo vedi addCampo in Pagina e creaCampi in SchedaBase
            // todo per adesso uso il valore registrato nel db
            // todo attenzione: finche' non registro il menu il valore non e' aggiornato
//            campoPilota = this.getNavPilota().getCampoPilota();
//            scheda = campoPilota.getPortale().getScheda();
//            campoPasto = scheda.getCampo(Menu.CAMPO_PASTO);
//            valore = campoPasto.getCampoDati().getMemoria();
//            codicePasto = Libreria.objToInt(valore);

            Campo campoMenuPasto = moduloMenu.getCampo(Menu.Cam.pasto.get());

            codicePasto = moduloMenu.query().valoreInt(campoMenuPasto,
                    this.getCodiceMenuCorrente());

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codicePasto;
    }


    /**
     * Ritorna un filtro per selezionare tutti i tavoli (RMT) del menu corrente.
     * <p/>
     *
     * @return il filtro per tutti i tavoli
     */
    private Filtro filtroTavoli() {
        return FiltroFactory.crea(campoRMTLinkMenu, this.getCodiceMenuCorrente());
    }


    /**
     * Seleziona il tavolo successivo.
     * <p/>
     * Se e' posizionato sull'ultimo tavolo, non succede nulla
     */
    public void tavoloSuccessivo() {
        /* variabili e costanti locali di lavoro */
        int riga = 0;
        int totTavoli = 0;

        try {    // prova ad eseguire il codice
            /* recupera il numero totale di tavoli presenti in questa lista di tavoli */
            totTavoli = this.getLista().getNumRecordsVisualizzati();

            /* recupera la riga selezionata */
            riga = this.getLista().getRigaSelezionata();

            if (riga > -1) {
                if (riga < totTavoli) {
                    riga++;
                    this.getLista().getTavola().setRowSelectionInterval(riga, riga);
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Ritorna il codice del menu corrente.
     * <p/>
     *
     * @return il codice del menu che pilota il navigatore
     */
    private int getCodiceMenuCorrente() {
        return this.getValorePilota();
    }

//    /**
//     * Ritorna la chiave di riga per un dato tavolo nel menu corrente.
//     * <p/>
//     *
//     * @param chiaveTavolo il codice chiave del tavolo da cercare
//     *
//     * @return la chiave dela riga, 0 se non trovato.
//     */
//    private int getChiaveRigaTavolo(int chiaveTavolo) {
//        /* variabili e costanti locali di lavoro */
//        int chiaveRiga = 0;
//        Campo campoMenuCodice = null;
//        Filtro filtro = null;
//        Filtro f = null;
//        int[] valoriChiave = null;
//        int codiceMenu = 0;
//
//        try {	// prova ad eseguire il codice
//            campoMenuCodice = moduloMenu.getCampoChiave();
//            codiceMenu = this.getCodiceMenuCorrente();
//            if (codiceMenu != 0) {
//                filtro = new Filtro();
//                f = FiltroFactory.crea(campoMenuCodice, this.getCodiceMenuCorrente());
//                filtro.add(f);
//                f = FiltroFactory.crea(campoTavoloChiave, chiaveTavolo);
//                filtro.add(f);
//                valoriChiave = this.getModulo().query().valoriChiave(filtro);
//                if (valoriChiave.length > 0) {
//                    chiaveRiga = valoriChiave[0];
//                }// fine del blocco if
//            }// fine del blocco if
//        } catch (Exception unErrore) {	// intercetta l'errore
//            Errore.crea(unErrore);
//        } // fine del blocco try-catch
//
//        /* valore di ritorno */
//        return chiaveRiga;
//    }
//

//    /**
//     * Recupera il codice chiave di un tavolo dato il numero del tavolo.
//     * <p/>
//     *
//     * @param numeroTavolo il numero del tavolo
//     *
//     * @return il codice chiave del tavolo
//     */
//    private int getChiaveTavolo(int numeroTavolo) {
//        /* variabili e costanti locali di lavoro */
//        int chiave = 0;
//        Filtro filtro = null;
//        int[] valoriChiave = null;
//
//        try {	// prova ad eseguire il codice
//            filtro = FiltroFactory.crea(campoTavoloNumero, numeroTavolo);
//            valoriChiave = moduloTavolo.query().valoriChiave(filtro);
//            if (valoriChiave.length > 0) {
//                chiave = valoriChiave[0];
//            }// fine del blocco if
//        } catch (Exception unErrore) {	// intercetta l'errore
//            Errore.crea(unErrore);
//        } // fine del blocco try-catch
//
//        /* valore di ritorno */
//        return chiave;
//    }

//    /**
//     * Ritorna il numero di un tavolo data la chiave.
//     * <p/>
//     *
//     * @param chiaveTavolo la chiave del tavolo
//     *
//     * @return il numero del tavolo
//     */
//    private int getNumeroTavolo(int chiaveTavolo) {
//        /* variabili e costanti locali di lavoro */
//        int numeroTavolo = 0;
//        Object valore = null;
//
//        try {	// prova ad eseguire il codice
//            valore = moduloTavolo.query().valoreCampo(campoTavoloNumero, chiaveTavolo);
//            numeroTavolo = Libreria.objToInt(valore);
//        } catch (Exception unErrore) {	// intercetta l'errore
//            Errore.crea(unErrore);
//        } // fine del blocco try-catch
//
//        /* valore di ritorno */
//        return numeroTavolo;
//    }
//


    /**
     * Creazione dei tavoli per il menu
     */
    private final class AzCreaTavoli extends AzModulo {

        /**
         * Costruttore completo con parametri.
         *
         * @param modulo di riferimento
         */
        public AzCreaTavoli(Modulo modulo) {
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
            super.setTooltip("Crea i tavoli per questo menu");
            super.setIconaMedia("CreaTavoli24");
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
                creaTavoli();
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }

    }// fine della classe


    /**
     * Classe interna. </p>
     * Wrapper con i dati di un tavolo da creare.<br>
     * Contiene:
     * - chiave del tavolo
     * - camera/cliente
     * - numero di coperti
     */
    public final class DatiTavolo {

        private int chiaveTavolo = 0;

        private String camera = "";

        private int coperti = 0;


        public int getChiaveTavolo() {
            return chiaveTavolo;
        }


        public void setChiaveTavolo(int chiaveTavolo) {
            this.chiaveTavolo = chiaveTavolo;
        }


        public String getCamera() {
            return camera;
        }


        public void setCamera(String camera) {
            this.camera = camera;
        }


        public int getCoperti() {
            return coperti;
        }


        public void setCoperti(int coperti) {
            this.coperti = coperti;
        }
    } // fine della classe 'interna'

}// fine della classe
