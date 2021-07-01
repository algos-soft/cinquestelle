/**
 * Copyright: Copyright (c) 2007
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      22-nov-2007
 */
package it.algos.base.campo.logica;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.video.CVEstratto;
import it.algos.base.campo.video.CampoVideo;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.errore.Errore;
import it.algos.base.form.Form;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.messaggio.MessaggioDialogo;
import it.algos.base.modulo.Modulo;
import it.algos.base.portale.Portale;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.scheda.Scheda;
import it.algos.base.wrapper.Estratti;
import it.algos.base.wrapper.EstrattoBase;

import javax.swing.*;

/**
 * Logica base del campo Estratto
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 22-nov-2007 ore 14.45.41
 */
public abstract class CLEstratto extends CLBase {

    private Estratti estratto;


    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param unCampoParente campo 'contenitore' di questo oggetto
     */
    public CLEstratto(Campo unCampoParente) {
        /* rimanda al costruttore della superclasse */
        super(unCampoParente);

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
    }


    /**
     * Regolazioni di avvio, ogni volta che questo oggetto deve 'ripartire', per
     * essere sicuri che sia 'pulito' <br>
     * Metodo chiamato da altre classi <br>
     * Viene eseguito tutte le volte che necessita  <br>
     */
    public void avvia() {
        super.avvia();
    } /* fine del metodo */


    /**
     * Creazione di un nuovo record nel modulo esterno.
     * <p/>
     *
     * @return true se il record è stato creato
     */
    public boolean nuovoRecord() {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = false;
        boolean continua;
        Modulo modulo;
        Connessione conn = null;
        int cod = 0;
        boolean registrato = false;

        try {    // prova ad eseguire il codice

            /* recupera il modulo esterno */
            modulo = this.getModuloEsterno();
            continua = (modulo != null);

            /* recupera la connessione interna */
            if (continua) {
                conn = this.getConnessione();
                continua = (conn != null);
            }// fine del blocco if

            /* crea un nuovo record sul modulo esterno */
            if (continua) {
                cod = this.creaRecordEsterno(modulo, conn);
                continua = (cod > 0);
            }// fine del blocco if

            /* presenta il record in modifica */
            if (continua) {
                registrato = this.editRecord(cod);
            }// fine del blocco if

            /**
             * Se l'editing è confermato, invoca il metodo delegato alle operazioni post-conferma
             * Se l'editing è annullato, cancella il nuovo record appena creato
             */
            if (continua) {
                if (registrato) {
                    this.recordEsternoCreato(cod);
                    riuscito = true;
                } else {
                    modulo.query().eliminaRecord(cod, conn);
                }// fine del blocco if-else
            }// fine del blocco if

            /* valore di ritorno */
            return riuscito;

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Presenta il record del modulo esterno in una scheda per modifica.
     * <p/>
     *
     * @return true se la modifica è stata confermata.
     */
    public boolean modificaRecord() {
        /* variabili e costanti locali di lavoro */
        boolean confermato = false;
        int codice;

        try {    // prova ad eseguire il codice

            codice = this.getCodRecordEsterno();
            confermato = this.editRecord(codice);

            /* Se la modifica è confermata, invoca il metodo
             * delegato alle operazioni post-modifica */
            if (confermato) {
                this.recordEsternoModificato();
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return confermato;
    }


    /**
     * Editing di un record esterno.
     * <p/>
     * Presenta il record in scheda
     *
     * @param codice del record da editare
     *
     * @return true se l'editing è stato confermato
     */
    private boolean editRecord(int codice) {
        /* variabili e costanti locali di lavoro */
        boolean confermato = false;
        boolean continua = true;
        Modulo mod = null;
        String nomeScheda;
        Scheda scheda = null;

        try {    // prova ad eseguire il codice

            /* controlla che il codice da editare sia != 0 */
            continua = (codice != 0);

            /* recupera il modulo esterno */
            if (continua) {
                mod = this.getModuloEsterno();
                continua = (mod != null);
            }// fine del blocco if

            /**
             * recupera la scheda da utilizzare.
             * prima prova con la scheda pop specifica
             * se non trovata usa la scheda pop del modulo esterno
             */
            if (continua) {
                nomeScheda = this.getNomeSchedaPop();
                if (Lib.Testo.isValida(nomeScheda)) {
                    scheda = mod.getScheda(nomeScheda);
                }// fine del blocco if
                if (scheda == null) {
                    scheda = mod.getSchedaPop();
                }// fine del blocco if
                continua = (scheda != null);
            }// fine del blocco if

            /* richiede al modulo esterno la presentazione del record */
            if (continua) {
                confermato = mod.presentaRecord(codice, scheda, this.getConnessione());
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return confermato;
    }


    /**
     * Eliminazione di un record del campo.
     * <p/>
     */
    public void eliminaRecord() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        int cod = 0;
        Modulo mod;
        Connessione conn = null;
        MessaggioDialogo messaggio;

        try {    // prova ad eseguire il codice

            mod = this.getModuloEsterno();
            continua = (mod != null);

            if (continua) {
                conn = this.getConnessione();
                continua = (conn != null);
            }// fine del blocco if

            if (continua) {
                cod = this.getCodRecordEsterno();
                continua = (cod > 0);
            }// fine del blocco if

            /* chiede conferma */
            if (continua) {
                messaggio = new MessaggioDialogo("Confermi l'eliminazione del record?");
                /* procede solo dopo conferma esplicita */
                if (messaggio.getRisposta() == JOptionPane.NO_OPTION) {
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            if (continua) {
                continua = mod.query().eliminaRecord(cod, conn);
            }// fine del blocco if

            /* Se l'eliminazione è confermata, invoca il metodo
             * delegato alle operazioni post-eliminazione */
            if (continua) {
                this.recordEsternoEliminato();
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Invocato dopo la creazione di un record esterno.
     * <p/>
     *
     * @param codice del record esterno creato
     */
    protected void recordEsternoCreato(int codice) {
        this.getCampoParente().avvia();
    }


    /**
     * Invocato dopo la modifica del record esterno.
     * <p/>
     */
    protected void recordEsternoModificato() {
        this.getCampoParente().avvia();
    }


    /**
     * Invocato dopo la eliminazione del record esterno.
     * <p/>
     */
    protected void recordEsternoEliminato() {
        this.getCampoParente().avvia();
    }


    /**
     * Recupera il modulo esterno.
     * <p/>
     *
     * @return il modulo esterno
     */
    public Modulo getModuloEsterno() {
        /* variabili e costanti locali di lavoro */
        Modulo modulo = null;
        boolean continua;
        Estratti estratto;
        String nomeModulo = "";

        try {    // prova ad eseguire il codice

            estratto = this.getEstratto();
            continua = (estratto != null);

            if (continua) {
                nomeModulo = estratto.getNomeModulo();
                continua = Lib.Testo.isValida(nomeModulo);
            }// fine del blocco if

            if (continua) {
                modulo = Progetto.getModulo(nomeModulo);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return modulo;
    }


    /**
     * Recupera l'estratto dal modulo esterno.
     * <p/>
     *
     * @param cod il codice record interno.
     *
     * @return l'estratto richiesto
     */
    public EstrattoBase getEstratto(int cod) {
        /* variabili e costanti locali di lavoro */
        Estratti estratto;
        EstrattoBase estrattoBase = null;
        boolean continua;
        String nomeModulo = "";
        Modulo modulo = null;
        int codice = 0;

        try {    // prova ad eseguire il codice

            /* recupera l'oggetto Estratti dal campo */
            estratto = this.getEstratto();
            continua = (estratto != null);

            /* recupera il nome del modulo dall'estratto */
            if (continua) {
                nomeModulo = estratto.getNomeModulo();
                continua = Lib.Testo.isValida(nomeModulo);
            }// fine del blocco if

            /* recupera il modulo */
            if (continua) {
                modulo = Progetto.getModulo(nomeModulo);
                continua = (modulo != null);
            }// fine del blocco if

            /* recupera il codice del record del modulo esterno */
            if (continua) {
                codice = this.getCodRecordEsterno(cod);
            }// fine del blocco if

            /* recupera l'estratto con il record da visualizzare */
            if (continua) {
                estrattoBase = modulo.getEstratto(estratto, codice, this.getConnessione());
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return estrattoBase;
    }


    /**
     * Controlla se esiste il record esterno da visualizzare.
     * <p/>
     *
     * @return true se esiste
     */
    public boolean isEsisteRecordEsterno() {
        /* variabili e costanti locali di lavoro */
        boolean esiste = false;
        boolean continua = true;
        int codEsterno = 0;
        Modulo modulo;
        Connessione conn;
        Filtro filtro;
        int quanti;

        try { // prova ad eseguire il codice

            /* recupera il codice esterno */
            if (continua) {
                codEsterno = this.getCodRecordEsterno();
                continua = (codEsterno != 0);
            }// fine del blocco if

            /* controlla se esiste fisicamente il record */
            if (continua) {
                modulo = this.getModuloEsterno();
                conn = this.getConnessione();
                filtro = FiltroFactory.codice(modulo, codEsterno);
                quanti = modulo.query().contaRecords(filtro, conn);
                if (quanti > 0) {
                    esiste = true;
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return esiste;
    }


    /**
     * Ritorna il codice del record esterno per il quale recuperare l'estratto.
     * <p/>
     *
     * @param codRecordInterno il codice interno del record interno mantenuto dal campo
     *
     * @return il codice del record esterno
     */
    protected abstract int getCodRecordEsterno(int codRecordInterno);


    /**
     * Crea un nuovo record sul modulo esterno.
     * <p/>
     *
     * @param modulo esterno sul quale creare il record
     * @param conn la connessione da utilizzare
     *
     * @return il codice del record creato, <=0 se non creato
     */
    protected abstract int creaRecordEsterno(Modulo modulo, Connessione conn);


    /**
     * Ritorna il codice del record esterno corrente.
     * <p/>
     *
     * @return il codice del record esterno
     */
    protected int getCodRecordEsterno() {
        /* variabili e costanti locali di lavoro */
        int codice = 0;

        try {    // prova ad eseguire il codice
            codice = this.getCodRecordEsterno(this.getRifEsterno());
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codice;
    }


    /**
     * Recupera la connessione interna
     * (la connessione del Navigatore che contiene il campo)
     * Il campo deve usare questa connessione per tutte le query,
     * per non uscire da una eventuale transazione in corso.
     * <p/>
     *
     * @return la connessione
     */
    public Connessione getConnessione() {
        /* variabili e costanti locali di lavoro */
        Connessione conn = null;
        boolean continua;
        Campo parente;
        Form form = null;
        Portale portale = null;

        try {    // prova ad eseguire il codice

            parente = this.getCampoParente();
            continua = (parente != null);

            if (continua) {
                form = parente.getForm();
                continua = (form != null);
            }// fine del blocco if

            if (continua) {
                portale = form.getPortale();
                continua = (portale != null);
            }// fine del blocco if

            if (continua) {
                conn = portale.getConnessione();
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return conn;
    }


    /**
     * Registra il numero di riferimento al record esterno nella memoria del campo.
     * <p/>
     *
     * @param num numero di riferimento al record esterno
     */
    protected void setRifEsterno(int num) {
        /* variabili e costanti locali di lavoro */
        Campo campoParente;

        try {    // prova ad eseguire il codice
            campoParente = this.getCampoParente();
            if (campoParente != null) {
                campoParente.setValore(num);
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Recupera il numero di riferimento al record esterno dalla memoria del campo.
     * <p/>
     *
     * @return numero di riferimento al record esterno
     */
    public int getRifEsterno() {
        /* variabili e costanti locali di lavoro */
        int num = 0;
        Campo campoParente;
        Object valore;

        try {    // prova ad eseguire il codice
            campoParente = this.getCampoParente();
            if (campoParente != null) {
                valore = campoParente.getValore();
                num = Libreria.getInt(valore);
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return num;
    }


    /**
     * Determina se usare un bottone unico o due bottoni separati per Nuovo e Modifica
     * <p/>
     *
     * @param flag true per usare un solo bottone con icona variabile,
     * false per usare due bottoni separati
     */
    public void setUsaNuovoModifica(boolean flag) {
        /* variabili e costanti locali di lavoro */
        CampoVideo cVideo;
        CVEstratto cvEstratto;

        try { // prova ad eseguire il codice
            cVideo = this.getCampoVideoNonDecorato();
            if (cVideo != null) {
                if (cVideo instanceof CVEstratto) {
                    cvEstratto = (CVEstratto)cVideo;
                    cvEstratto.setUsaNuovoModificaInsieme(flag);
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    public Estratti getEstratto() {
        return estratto;
    }


    public void setEstratto(Estratti estratto) {
        this.estratto = estratto;
    }


}// fine della classe
