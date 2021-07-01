/**
 * Title:     PanAddebitiMem
 * Copyright: Copyright (c) 2007
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      14-mag-2007
 */
package it.algos.albergo.conto;

import it.algos.albergo.conto.addebito.Addebito;
import it.algos.albergo.conto.addebito.AddebitoSchedaMem;
import it.algos.albergo.listino.Listino;
import it.algos.albergo.listino.ListinoModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.campo.logica.CampoLogica;
import it.algos.base.costante.CostanteColore;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.database.dati.Dati;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.modello.Modello;
import it.algos.base.modulo.Modulo;
import it.algos.base.modulo.ModuloMemoria;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.navigatore.NavigatoreBase;
import it.algos.base.pannello.PannelloBase;
import it.algos.base.query.Query;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.query.selezione.QuerySelezione;
import it.algos.base.wrapper.CampoValore;

import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Date;

/**
 * Pannello di inserimento addebiti su database in memoria.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 14-mag-2007
 */
public final class PanAddebitiMem extends PannelloBase {

    /* modulo interno specifico con gestione del database in memoria */
    private ModuloAddMemoria modMemoria;

    /* data nella quale effettuare gli addebiti */
    private Date dataAddebiti;

    /* numero di persone per le quali effettuare gli addebiti per persona */
    private int numPersone;


    /* nome del campo Descrizione Listino (duplicato) */
    public static final String NOME_CAMPO_DESCLISTINO = "desclistino";

    /* nome del campo Ambito Prezzo (duplicato) */
    public static final String NOME_CAMPO_AMBITO = "ambito";


    /**
     * Costruttore completo
     * <p/>
     */
    public PanAddebitiMem() {
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
        /* variabili e costanti locali di lavoro */
        Navigatore nav;
        Component comp;

        try { // prova ad eseguire il codice

            /* crea e registra il modulo in memoria */
            this.creaModuloMem();

            /* costruisce il bordo con titolo */
            this.regolaBordo("Addebiti");

            /* aggiunge il portale Navigatore al pannello */
            nav = this.getNavigatore();
            comp = nav.getPortaleNavigatore();
            this.add(comp);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


    }// fine del metodo inizia


    /**
     * Resetta il pannello.
     * <p/>
     * Svuota il database.
     */
    public void reset() {
        try { // prova ad eseguire il codice

            this.getModMemoria().query().eliminaRecords();
            this.getNavigatore().aggiornaLista();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    } /* fine del metodo */


    /**
     * Crea e registra il modulo in memoria
     * <p/>
     */
    private void creaModuloMem() {
        /* variabili e costanti locali di lavoro */
        ModuloAddMemoria modulo;
        ArrayList<Campo> campi;
        Campo campo;

        try {    // prova ad eseguire il codice

            campi = new ArrayList<Campo>();

            /* campo link listino solo in scheda */
            campo = CampoFactory.comboLinkSel(Addebito.Cam.listino.get());
            campo.setNomeModuloLinkato(ListinoModulo.NOME_MODULO);
            campo.setNomeCampoValoriLinkato(ListinoModulo.Cam.sigla.get());
            campo.addColonnaCombo(ListinoModulo.NOME_MODULO, ListinoModulo.Cam.descrizione.get());
            campo.decora().obbligatorio();
            campo.decora().etichetta("listino");
            campo.decora().estrattoSotto(Listino.Estratto.descrizioneCameraPersona);
            campo.setUsaNuovo(false);
            campo.setVisibileVistaDefault(false);
            campi.add(campo);

            /* campo descrizione listino solo in lista */
            campo = CampoFactory.testo(NOME_CAMPO_DESCLISTINO);
            campo.setVisibileVistaDefault();
            campo.setTitoloColonna("descrizione");
            campo.setLarLista(200);
            campo.setPresenteScheda(false);
            campi.add(campo);

            /* campo quantità */
            campo = CampoFactory.intero(Addebito.Cam.quantita.get());
            campo.setVisibileVistaDefault();
            campo.setTitoloColonna("q.ta");
            campo.decora().obbligatorio();
            campi.add(campo);

            /* campo prezzo */
            campo = CampoFactory.valuta(Addebito.Cam.prezzo.get());
            campo.setVisibileVistaDefault();
            campo.decora().obbligatorio();
            campi.add(campo);

            /* campo totale */
            campo = CampoFactory.calcola(Addebito.Cam.importo,
                    CampoLogica.Calcolo.prodottoValuta,
                    Addebito.Cam.quantita.get(),
                    Addebito.Cam.prezzo.get());
            campo.getCampoDB().setCampoFisico(true);
            campo.setVisibileVistaDefault();
            campo.setTotalizzabile(true);
            campi.add(campo);

            /* campo note */
            campo = CampoFactory.testo(Addebito.Cam.note.get());
            campo.setVisibileVistaDefault(false);
            campo.decora().etichetta("note");
            campi.add(campo);

            /* campo ambito (pensione o extra) */
            campo = CampoFactory.intero(NOME_CAMPO_AMBITO);
            campo.setVisibileVistaDefault(false);
            campo.setPresenteScheda(false);
            campi.add(campo);

            /* crea e avvia il modulo interno */
            modulo = new ModuloAddMemoria(campi);
            this.setModMemoria(modulo);
            this.regolaNavigatore();
            modulo.avvia();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Regolazione del Navigatore.
     * <p/>
     */
    private void regolaNavigatore() {
        /* variabili e costanti locali di lavoro */
        Navigatore nav;
        Modulo modulo;

        try {    // prova ad eseguire il codice
            modulo = this.getModMemoria();
            nav = modulo.getNavigatoreDefault();
            nav.setUsaPannelloUnico(true);
            nav.setUsaFinestraPop(true);
            nav.setRigheLista(3);
            nav.setUsaRicerca(false);
            nav.setUsaSelezione(false);
            nav.setUsaStampaLista(false);
            nav.setAggiornamentoTotaliContinuo(true);
            nav.addSchedaCorrente(new AddebitoSchedaMem(modulo, this));
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Regola il titolo del bordo del pannello.
     * <p/>
     * Assegna un bordo con titolo al pannello
     *
     * @param titolo il titolo del bordo
     */
    private void regolaBordo(String titolo) {
        /* variabili e costanti locali di lavoro */
        Border bordo;


        try {    // prova ad eseguire il codice

            /* crea un bordo con titolo rosso */
            this.setBorder(null);
            bordo = this.creaBordo(titolo);
            if (bordo instanceof CompoundBorder) {
                CompoundBorder cBordo = (CompoundBorder)bordo;
                Border outBordo = cBordo.getOutsideBorder();
                if (outBordo instanceof TitledBorder) {
                    TitledBorder tBordo = (TitledBorder)outBordo;
                    tBordo.setTitleColor(CostanteColore.ROSSO);
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Assegna il titolo al bordo del pannello.
     * <p/>
     *
     * @param titolo il titolo del bordo
     */
    public void setTitolo(String titolo) {
        try {    // prova ad eseguire il codice
            this.regolaBordo(titolo);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Ritorna il Navigatore del modulo Memoria.
     * <p/>
     *
     * @return il Navigatore
     */
    public Navigatore getNavigatore() {
        /* variabili e costanti locali di lavoro */
        Navigatore nav = null;
        Modulo modulo;

        try {    // prova ad eseguire il codice
            modulo = this.getModMemoria();
            if (modulo != null) {
                nav = modulo.getNavigatoreDefault();
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return nav;
    }


    /**
     * Chiude l'oggetto.
     * <p/>
     */
    public void close() {
        try {    // prova ad eseguire il codice
            this.getModMemoria().chiude();
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Aggiunge un addebito all'elenco.
     * <p/>
     *
     * @param codListino codice del listino
     * @param qta la quantità
     * @param data per recuperare il prezzo dal listino se variabile
     *
     * @return il codice dell'addebito aggiunto
     */
    public int addAddebito(int codListino, int qta, Date data) {
        /* variabili e costanti locali di lavoro */
        int codice = 0;
        Modulo mod;
        ArrayList<CampoValore> valori;
        CampoValore cv;
        Campo campo;
        double prezzo;
        Navigatore nav;

        try {    // prova ad eseguire il codice

            mod = this.getModMemoria();

            valori = new ArrayList<CampoValore>();

            campo = mod.getCampo(Addebito.Cam.listino.get());
            cv = new CampoValore(campo, codListino);
            valori.add(cv);

            campo = mod.getCampo(Addebito.Cam.quantita.get());
            cv = new CampoValore(campo, qta);
            valori.add(cv);

            campo = mod.getCampo(Addebito.Cam.prezzo.get());
            prezzo = ListinoModulo.getPrezzo(codListino, data);
            cv = new CampoValore(campo, prezzo);
            valori.add(cv);

            /* crea un nuovo record, aggiorna la lista e lancia l'evento stato modificato */
            nav = this.getNavigatore();
            codice = mod.query().nuovoRecord(valori);
            nav.fire(NavigatoreBase.Evento.statoModificato);
            nav.aggiornaLista();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codice;
    }


    /**
     * Elimina un addebito dall'elenco.
     * <p/>
     *
     * @param codAddebito da eliminare
     */
    public void delAddebito(int codAddebito) {
        /* variabili e costanti locali di lavoro */
        Modulo mod;
        Navigatore nav;

        try {    // prova ad eseguire il codice

            /* elimina il record, aggiorna la lista e lancia l'evento stato modificato */
            mod = this.getModMemoria();
            mod.query().eliminaRecord(codAddebito);
            nav = this.getNavigatore();
            nav.fire(NavigatoreBase.Evento.statoModificato);
            nav.aggiornaLista();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Ritorna l'elenco degli addebiti correntemente inseriti.
     * <p/>
     *
     * @return elenco di oggetti WrapAddebito
     */
    public ArrayList<WrapAddebito> getAddebiti() {
        /* variabili e costanti locali di lavoro */
        ArrayList<WrapAddebito> addebiti = new ArrayList<WrapAddebito>();
        Modulo mod;
        Query query;
        Ordine ordine;
        Dati dati;
        Campo campoListino;
        Campo campoQuantita;
        Campo campoPrezzo;
        Campo campoNote;
        int codListino;
        int quantita;
        double prezzo;
        String note;
        WrapAddebito wrapper;


        try {    // prova ad eseguire il codice

            mod = this.getModMemoria();

            campoListino = mod.getCampo(Addebito.Cam.listino.get());
            campoQuantita = mod.getCampo(Addebito.Cam.quantita.get());
            campoPrezzo = mod.getCampo(Addebito.Cam.prezzo.get());
            campoNote = mod.getCampo(Addebito.Cam.note.get());

            query = new QuerySelezione(mod);
            query.addCampo(campoListino);
            query.addCampo(campoQuantita);
            query.addCampo(campoPrezzo);
            query.addCampo(campoNote);
            ordine = new Ordine();
            ordine.add(mod.getCampoOrdine());
            query.setOrdine(ordine);
            dati = mod.query().querySelezione(query);
            for (int k = 0; k < dati.getRowCount(); k++) {
                codListino = dati.getIntAt(k, campoListino);
                quantita = dati.getIntAt(k, campoQuantita);
                prezzo = dati.getDoubleAt(k, campoPrezzo);
                note = dati.getStringAt(k, campoNote);
                wrapper = new WrapAddebito(codListino, quantita, prezzo, note);
                addebiti.add(wrapper);
            } // fine del ciclo for
            dati.close();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return addebiti;
    }

//    /**
//     * Ritorna il totale degli addebiti presenti nel pannello.
//     * <p/>
//     * @param flag true per le pensioni false per gli extra
//     * @return il totale addebiti
//     */
//    private double getTotale(boolean flag) {
//        /* variabili e costanti locali di lavoro */
//        double totale  = 0;
//        boolean continua;
//        Modulo modulo;
//        Filtro filtro=null;
//        Number numero;
//        ArrayList<Integer> lista=null;
//
//        try {    // prova ad eseguire il codice
//            modulo = this.getModMemoria();
//            continua = modulo!=null;
//
//            if (continua) {
//                lista = Listino.AmbitoPrezzo.getCodPensioni(flag);
//                continua = (lista != null && lista.size() > 0);
//            }// fine del blocco if
//
//            if (continua) {
//                filtro = new Filtro();
//                for (int cod : lista) {
//                    filtro.add(Filtro.Op.OR, FiltroFactory.crea(NOME_CAMPO_AMBITO, cod));
//                } // fine del ciclo for-each
//            }// fine del blocco if
//
//            if (continua) {
//                numero = modulo.query().somma(Addebito.Cam.importo.get(), filtro);
//                totale = Libreria.getDouble(numero);
//            }// fine del blocco if
//
//
////                    filtro = FiltroFactory.crea(NOME_CAMPO_AMBITO, ambito.getCodice());
//        } catch (Exception unErrore) {    // intercetta l'errore
//            new Errore(unErrore);
//        } // fine del blocco try-catch
//
//        /* valore di ritorno */
//        return totale;
//    }


    /**
     * Modulo interno con database in memoria.
     * <p/>
     */
    public final class ModuloAddMemoria extends ModuloMemoria {

        /**
         * Costruttore completo <br>
         *
         * @param campi campi specifici del modulo (oltre ai campi standard)
         */
        public ModuloAddMemoria(ArrayList<Campo> campi) {

            super("addebitiMem", campi, false);

            /**
             * regolazioni iniziali di riferimenti e variabili
             */
            try { // prova ad eseguire il codice
                this.inizia();
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }/* fine del blocco try-catch */

        }/* fine del metodo costruttore completo */


        /**
         * Regolazioni immediate di riferimenti e variabili. <br>
         * Metodo chiamato direttamente dal costruttore <br>
         *
         * @throws Exception unaEccezione
         */
        private void inizia() throws Exception {
            /* variabili e costanti locali di lavoro */
            Modello modello;

            try { // prova ad eseguire il codice
                /* crea e assegna il modello dati */
                modello = new ModelloAddMemoria();
                this.setModello(modello);

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        /**
         * Modello dati per il modulo interno in memoria.
         * </p>
         */
        private class ModelloAddMemoria extends ModelloMemoria {

            /**
             * Costruttore completo con parametri. <br>
             */
            public ModelloAddMemoria() {
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
             * Regolazioni immediate di riferimenti e variabili. <br>
             * Metodo chiamato direttamente dal costruttore <br>
             *
             * @throws Exception unaEccezione
             */
            private void inizia() throws Exception {
            }


            protected boolean nuovoRecordAnte(ArrayList<CampoValore> lista, Connessione conn) {

                try { // prova ad eseguire il codice
                    this.syncCampi(lista);
                } catch (Exception unErrore) { // intercetta l'errore
                    Errore.crea(unErrore);
                }// fine del blocco try-catch

                /* valore di ritorno */
                return true;
            } // fine del metodo


            protected boolean registraRecordAnte(int codice,
                                                 ArrayList<CampoValore> lista,
                                                 Connessione conn) {
                try { // prova ad eseguire il codice
                    this.syncCampi(lista);
                } catch (Exception unErrore) { // intercetta l'errore
                    Errore.crea(unErrore);
                }// fine del blocco try-catch

                /* valore di ritorno */
                return super.registraRecordAnte(codice, lista, conn);

            } // fine del metodo


            /**
             * Sincronizza i campi duplicati con il codice listino.
             * <p/>
             *
             * @param lista di oggetti CampoValore
             */
            private void syncCampi(ArrayList<CampoValore> lista) {
                /* variabili e costanti locali di lavoro */
                Campo campoListino;
                Campo campoDescrizione;
                Campo campoAmbito;
                CampoValore cvListino;
                CampoValore cvDescrizione;
                CampoValore cvAmbito;
                Modulo modListino;
                String descrizione;
                int ambito;
                int codListino;

                try { // prova ad eseguire il codice

                    /* regola i campi in funzione del codice listino */
                    campoListino = this.getCampo(Addebito.Cam.listino.get());
                    cvListino = Lib.Camp.getCampoValore(lista, campoListino);
                    if (cvListino != null) {

                        /* recupera il modulo e il codice listino */
                        modListino = ListinoModulo.get();
                        codListino = Libreria.getInt(cvListino.getValore());

                        /* regola il campo descrizione listino */
                        campoDescrizione = this.getCampo(NOME_CAMPO_DESCLISTINO);
                        cvDescrizione = Lib.Camp.getCampoValore(lista, campoDescrizione);
                        if (cvDescrizione == null) {
                            cvDescrizione = new CampoValore(campoDescrizione, null);
                            lista.add(cvDescrizione);
                        }// fine del blocco if
                        descrizione =
                                modListino.query().valoreStringa(Listino.Cam.descrizione.get(),
                                        codListino);
                        cvDescrizione.setValore(descrizione);

                        /* regola il campo ambito */
                        campoAmbito = this.getCampo(NOME_CAMPO_AMBITO);
                        cvAmbito = Lib.Camp.getCampoValore(lista, campoAmbito);
                        if (cvAmbito == null) {
                            cvAmbito = new CampoValore(campoAmbito, null);
                            lista.add(cvAmbito);
                        }// fine del blocco if
                        ambito = modListino.query().valoreInt(Listino.Cam.ambitoPrezzo.get(),
                                codListino);
                        cvAmbito.setValore(ambito);

                    }// fine del blocco if

                } catch (Exception unErrore) { // intercetta l'errore
                    Errore.crea(unErrore);
                }// fine del blocco try-catch

            }// fine del blocco try-catch

        } // fine della classe 'interna'

    } // fine della classe 'interna'


    /**
     * Wrapper con i dati di un addebito.
     * <p/>
     */
    public final class WrapAddebito {

        private int codListino;

        private int quantita;

        private double prezzo;

        private String note;


        /**
         * Costruttore completo con parametri. <br>
         *
         * @param codListino
         * @param quantita
         * @param prezzo
         * @param note
         */
        public WrapAddebito(int codListino, int quantita, double prezzo, String note) {
            /* rimanda al costruttore della superclasse */
            super();

            /* regola le variabili di istanza coi parametri */
            this.setCodListino(codListino);
            this.setQuantita(quantita);
            this.setPrezzo(prezzo);
            this.setNote(note);

        }// fine del metodo costruttore completo


        public int getCodListino() {
            return codListino;
        }


        private void setCodListino(int codListino) {
            this.codListino = codListino;
        }


        public int getQuantita() {
            return quantita;
        }


        private void setQuantita(int quantita) {
            this.quantita = quantita;
        }


        public double getPrezzo() {
            return prezzo;
        }


        private void setPrezzo(double prezzo) {
            this.prezzo = prezzo;
        }


        public String getNote() {
            return note;
        }


        private void setNote(String note) {
            this.note = note;
        }

    } // fine della classe 'interna'


    private ModuloAddMemoria getModMemoria() {
        return modMemoria;
    }


    private void setModMemoria(ModuloAddMemoria modMemoria) {
        this.modMemoria = modMemoria;
    }


    public Date getDataAddebiti() {
        return dataAddebiti;
    }


    public void setDataAddebiti(Date dataAddebiti) {
        this.dataAddebiti = dataAddebiti;
    }


    public int getNumPersone() {
        return numPersone;
    }


    public void setNumPersone(int numPersone) {
        this.numPersone = numPersone;
    }


}// fine della classe
