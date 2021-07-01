/**
 * Title:        ModelloAlgos.java
 * Package:      it.algos.base.modello
 * Description:  Abstract Data Types per il tracciato record
 * Copyright:    Copyright (c) 2002
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 11 ottobre 2002 alle 14.46
 */
package it.algos.base.modello;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.campo.db.CampoDB;
import it.algos.base.campo.inizializzatore.InitFactory;
import it.algos.base.campo.lista.CampoLista;
import it.algos.base.campo.scheda.CampoScheda;
import it.algos.base.campo.video.CVTestoArea;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.lista.Lista;
import it.algos.base.modulo.Modulo;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.tavola.renderer.RendererBooleanoCheckmark;
import it.algos.base.validatore.ValidatoreFactory;
import it.algos.base.wrapper.Campi;
import it.algos.base.wrapper.CampoValore;
import it.algos.base.navigatore.Navigatore;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Questa classe concreta e' responsabile di:<br>
 * A - Creare i campi base presenti in tutte le tavole<br>
 * <br>
 * Ogni sottoclasse di questa classe, aggiungera' i propri campi<br>
 * <br>
 * Ogni campo viene creato con un costruttore semplice con solo le
 * informazioni piu' comuni; le altre variabili vengono regolate con chiamate
 * successive<br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  11 ottobre 2002 ore 14.46
 */
public abstract class ModelloAlgos extends Modello {

    /**
     * suffisso nel nome dei files di supporto dati (in formato txt)
     */
    private static final String DATI = "Dati";

    /**
     * suffisso di selezione dei files di supporto
     */
    private static final String SUFFISSO = ".txt";

    /**
     * percorso (path) della cartella per i file di supporto
     */
    protected String unPercorso = "";

    /**
     * collezione dei nomi dei campi fissi, secondo l'ordine di
     * presentazione - i nomi dei campi sono sempre i nomeInterno di ogni campo <br>
     * oggetti di tipo String
     */
    protected ArrayList listaCampiFissi = null;

    /**
     * testo della legenda sotto il campo note
     */
    private String legendaNote = "";


    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo utilizzando eventuali valori di default
     */
    public ModelloAlgos() {
        /** rimanda al costruttore di questa classe */
        this(null);
    } /* fine del metodo costruttore base */


    /**
     * Costruttore completo  <br>
     *
     * @param unModulo Abstract Data Types per le informazioni del modulo
     */
    public ModelloAlgos(Modulo unModulo) {
        /** rimanda al costruttore della superclasse */
        super(unModulo);

        /** regolazioni iniziali di riferimenti e variabili */
        try {                                   // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore completo */


    /**
     * Regolazioni iniziali di riferimenti e variabili
     * Metodo chiamato direttamente dal costruttore
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */
        Filtro filtro;

        try { // prova ad eseguire il codice
            this.listaCampiFissi = new ArrayList();

            filtro = FiltroFactory.creaVero(Modello.NOME_CAMPO_VISIBILE);
            this.addFiltroModello(filtro);


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } /* fine del metodo inizia */


    /**
     * Regolazioni iniziali, dopo che sono stati regolati dalla sottoclasse
     * i parametri indispensabili (tra cui il riferimento al modulo)
     * Metodo chiamato dalla classe che crea questo oggetto
     * Viene eseguito una sola volta
     *
     * @param unModulo Abstract Data Types per le informazioni di un modulo
     */
    public boolean inizializza(Modulo unModulo) {
        /* variabili e costanti locali di lavoro */
        Filtro filtro;
        boolean inizializzato=false;

        try { // prova ad eseguire il codice

            inizializzato = super.inizializza(unModulo);
            
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return inizializzato;

    } /* fine del metodo */


    /**
     * Regolazioni di avvio, ogni volta che questo oggetto deve 'ripartire', per
     * essere sicuri che sia 'pulito'
     * Metodo chiamato da altre classi
     * Viene eseguito tutte le volte che necessita
     */
    public void avviaModello() {
        /** invoca il metodo sovrascritto della superclasse */
        super.avviaModello();
    } /* fine del metodo */


    /**
     * regola il nome base completo del pacchetto
     * (usato per la costruzione da template)
     */
    protected void regolaNomePercorso(String unFileSystem, String unPacchetto) {
        /** prova ad eseguire il codice */
        try {
            this.unPercorso = unFileSystem + unPacchetto + "/";
            this.unPercorso = Lib.Testo.convertePuntiInBarre(unPercorso);
        } catch (Exception unErrore) { // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo */


    /**
     * regola il nome base completo del pacchetto
     */
    protected void regolaNomeTavola(String unaClasse, String unSuffisso) {
        /** prova ad eseguire il codice */
        try {
            this.unaTavolaArchivio = Libreria.regolaNomeTavola(unaClasse, unSuffisso);
        } catch (Exception unErrore) { // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo */

//    /**
//     * Crea i valori dei campi per un nuovo record.
//     * <p/>
//     * Invoca il metodo sovrascritto della superclasse <br>
//     * Modifica eventuali valori del singolo campo <br>
//     * Viene sovrascritto dalle classi specifiche <br>
//     *
//     * @return lista di oggetti di classe <code>CampoValore</code> <br>
//     */
//    public ArrayList<CampoValore> nuovoRecord() {
//        /* variabili e costanti locali di lavoro */
//        ArrayList<CampoValore> lista = null;
//        Object valore;
//        Iterator unGruppo;
//        Campo campo;
//        CampoDati campoDati;
//        CampoValore campoValore;
//
//        try { // prova ad eseguire il codice
//            lista = super.nuovoRecord();
//
//            unGruppo = this.getCampiFisici().values().iterator();
//
//            while (unGruppo.hasNext()) {
//                campo = (Campo)unGruppo.next();
//                campoDati = campo.getCampoDati();
//                valore = campoDati.getValoreNuovoRecord();
//                campoValore = new CampoValore(campo, valore);
//                lista.add(campoValore);
//            } /* fine del blocco while */
//
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//
//        /* valore di ritorno */
//        return lista;
//    } // fine del metodo


    /**
     * Creazione dei campi.
     * <p/>
     * Creazione dei campi base presenti in tutte le tavole <br>
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
        Campo campo;
        CampoDB campoDb;
        CampoLista campoLista;
        CampoScheda campoScheda;

        try {    // prova ad eseguire il codice

            /* campo codice
             * intero, chiave primaria, inizializzatore sequenziale*/
            campo = CampoFactory.intero(NOME_CAMPO_CHIAVE);
            if (this.getModulo().isUsaTransazioni()) {
                campo.setInit(InitFactory.contatore(this.getModulo()));
            } else {
                campo.setInit(InitFactory.sequenziale(campo));
            }// fine del blocco if-else
            campo.setValidatore(ValidatoreFactory.numIntPosMaggZero());
            campoDb = campo.getCampoDB();
            campoDb.setChiavePrimaria(true);
            campoDb.setFissoAlgos(true);
            campoLista = campo.getCampoLista();
            campoLista.setPresenteVistaDefault(true);
            campoLista.setVisibileOriginale(false);
            campoLista.setVisibileEspansione(false);
            campo.setTitoloColonna("#");
            campoLista.setTestoTooltip("codice del record");
            campo.setLarLista(45);
            campoLista.setRidimensionabile(false);
            campoScheda = campo.getCampoScheda();
            campo.setLarScheda(45);
            campoScheda.setPresenteScheda(false);
            this.addCampo(campo);

            /* campo ordine progressivo
             * intero, indicizzato non unico, inizializzatore sequenziale */
            campo = CampoFactory.intero(NOME_CAMPO_ORDINE);
            campo.setInit(InitFactory.sequenziale(campo));
            campo.setValidatore(ValidatoreFactory.numIntPosMaggZero());
            campoDb = campo.getCampoDB();
            campoDb.setIndicizzato(true);
            campoDb.setFissoAlgos(true);
            campoLista = campo.getCampoLista();
            campoLista.setPresenteVistaDefault(true);
            campoLista.setVisibileOriginale(false);
            campoLista.setVisibileEspansione(false);
            campo.setTitoloColonna("#");
            campoLista.setTestoTooltip("ordine progressivo");
            campo.setLarLista(30); // 3 cifre
            campoLista.setRidimensionabile(false);
            campoScheda = campo.getCampoScheda();
//            VideoFactory.etichetta(campo, "ordine di presentazione");
            campo.setLarScheda(45);
            campoScheda.setPresenteScheda(false);
            this.addCampo(campo);

            /* campo timestamp creazione */
            campo = CampoFactory.timestamp(NOME_CAMPO_DATA_CREAZIONE);
            campo.setInit(InitFactory.timestampAttuale());
            campoDb = campo.getCampoDB();
            campoDb.setFissoAlgos(true);
            campo.getCampoLista().setPresenteVistaDefault(false);
            campoScheda = campo.getCampoScheda();
            campoScheda.setPresenteScheda(false);
            campo.setUsaRangeRicerca(true);   // nel caso sia ricercabile
            campo.getCampoDati().setRicercaSoloPorzioneData(true);
            campo.decora().etichetta("data di creazione");
            this.addCampo(campo);

            /* campo timestamp modifica */
            campo = CampoFactory.timestamp(NOME_CAMPO_DATA_MODIFICA);
            campo.setInit(InitFactory.timestampAttuale());
            campoDb = campo.getCampoDB();
            campoDb.setFissoAlgos(true);
            campo.getCampoLista().setPresenteVistaDefault(false);
            campoScheda = campo.getCampoScheda();
            campoScheda.setPresenteScheda(false);
            campo.setUsaRangeRicerca(true);   // nel caso sia ricercabile
            campo.getCampoDati().setRicercaSoloPorzioneData(true);
            campo.decora().etichetta("data di modifica");
            this.addCampo(campo);

            /* campo id utente creazione */
            campo = CampoFactory.intero(NOME_CAMPO_ID_CREAZIONE);
            campo.setInit(InitFactory.idUtenteCorrente());
            campoDb = campo.getCampoDB();
            campoDb.setFissoAlgos(true);
            campo.getCampoLista().setPresenteVistaDefault(false);
            campoScheda = campo.getCampoScheda();
            campoScheda.setPresenteScheda(false);
            this.addCampo(campo);

            /* campo id utente modifica */
            campo = CampoFactory.intero(NOME_CAMPO_ID_MODIFICA);
            campo.setInit(InitFactory.idUtenteCorrente());
            campoDb = campo.getCampoDB();
            campoDb.setFissoAlgos(true);
            campo.getCampoLista().setPresenteVistaDefault(false);
            campoScheda = campo.getCampoScheda();
            campoScheda.setPresenteScheda(false);
            this.addCampo(campo);

            /* campo visibile */
            campo = CampoFactory.checkBox(NOME_CAMPO_VISIBILE);
            campoDb = campo.getCampoDB();
            campoDb.setFissoAlgos(true);
            campo.getCampoLista().setPresenteVistaDefault(false);
            campo.setTitoloColonna("vis");
            campo.setLarLista(30);
            campo.setRidimensionabile(false);
            campoScheda = campo.getCampoScheda();
            campoScheda.setPresenteScheda(false);
            this.addCampo(campo);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo creazioneCampiBase */


    /**
     * Aggiunge un campo "note" standard al modello.
     * <p/>
     *
     * @return il campo Note aggiunto
     */
    protected Campo creaCampoNote() {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;
        CVTestoArea unCampoVideo;

        try { // prova ad eseguire il codice
            unCampo = CampoFactory.testo(NOME_CAMPO_NOTE);
            unCampoVideo = new CVTestoArea(unCampo);
            unCampo.setCampoVideo(unCampoVideo);
            unCampo.setTestoEtichetta("note");
            unCampoVideo.setNumeroRighe(3);
            unCampo.setLarghezza(300);
            if (Lib.Testo.isValida(this.getLegendaNote())) {
                unCampo.decora().legenda(this.getLegendaNote());
            }// fine del blocco if
            this.addCampo(unCampo);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unCampo;
    }


    /**
     * Aggiunge un campo "preferito" al modello.
     * <p/>
     */
    protected void creaCampoPreferito() {
        /* variabili e costanti locali di lavoro */
        Campo unCampo;

        try { // prova ad eseguire il codice
            unCampo = CampoFactory.checkBox(NOME_CAMPO_PREFERITO);
            unCampo.setAbilitato(false);
            unCampo.setVisibileVistaDefault(false);
            unCampo.setPresenteScheda(false);
            unCampo.setTitoloColonna("pref");
            unCampo.setRenderer(new RendererBooleanoCheckmark());
            this.addCampo(unCampo);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Ritorna il codice del record preferito.
     * <p/>
     *
     * @return il codice del record impostato come preferito
     */
    public int getRecordPreferito() {
        /* variabili e costanti locali di lavoro */
        int codice = 0;
        Campo campoPref;
        Filtro filtroPref;
        boolean continua;

        try { // prova ad eseguire il codice
            campoPref = this.getCampoPreferito();
            continua = campoPref != null;

            if (continua) {
                filtroPref = FiltroFactory.crea(campoPref, true);
                codice = this.query().valoreChiave(filtroPref);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codice;
    } /* fine del metodo */


    protected void setCampoOrdineIniziale(Campi campo) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Lista lista;
        Campo unCampo = null;
        LinkedHashMap<String,Navigatore> navigatori;

        try { // prova ad eseguire il codice

            navigatori = this.getModulo().getNavigatori();

            for (Navigatore nav : navigatori.values()) {

                /* recupera la lista */
                lista = nav.getLista();
                continua = (lista != null);

                /* recupera il campo */
                if (continua) {
                    unCampo = this.getCampo(campo.get());
                    continua = (unCampo != null);
                }// fine del blocco if

                /* regola il campo di ordinamento iniziale  */
                if (continua) {
                    lista.setCampoOrdinamento(unCampo);
                }// fine del blocco if

            } // fine del ciclo for-each


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Metodo invocato prima della registrazione di un record esistente.
     * <p/>
     * Si accerta che la lista contenga i campi data modifica e utente modifica
     * con i valori della data corrente e dell'utente corrente
     * - se mancano i campi li aggiunge
     * - se i campi ci sono modifica i valori
     *
     * @param codice del record
     * @param lista array coppia campo-valore contenente i
     * dati che stanno per essere registrati
     * @param conn connessione utilizzata
     *
     * @return true per continuare il processo di registrazione,
     *         false per non effettuare la registrazione
     */
    protected boolean registraRecordAnte(int codice,
                                         ArrayList<CampoValore> lista,
                                         Connessione conn) {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = false;
        Campo campo;
        CampoValore cv;
        Object valore;

        try { // prova ad eseguire il codice

            /* controllo data e utente modifica */
            if (this.isAggiornaDataUtenteModifica()) {

                /* data modifica */
                campo = this.getCampoDataModifica();
                cv = Lib.Camp.getCampoValore(lista, campo);
                if (cv == null) {
                    cv = new CampoValore(campo);
                    lista.add(cv);
                }// fine del blocco if-else
                valore = Lib.Data.getTimestampCorrente();
                cv.setValore(valore);

                /* utente modifica */
                campo = this.getCampoUtenteModifica();
                cv = Lib.Camp.getCampoValore(lista, campo);
                if (cv == null) {
                    cv = new CampoValore(campo);
                    lista.add(cv);
                }// fine del blocco if-else
                valore = Progetto.getIdUtenteCorrente();
                cv.setValore(valore);

            }// fine del blocco if

            /* rimanda alla superclasse */
            riuscito = super.registraRecordAnte(codice, lista, conn);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    } // fine del metodo


    private String getLegendaNote() {
        return legendaNote;
    }


    protected void setLegendaNote(String legendaNote) {
        this.legendaNote = legendaNote;
    }


}// fine della classe