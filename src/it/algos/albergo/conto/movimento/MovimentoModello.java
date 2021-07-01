/**
 * Title:     MovimentoModello
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      25-set-2007
 */
package it.algos.albergo.conto.movimento;

import it.algos.albergo.conto.Conto;
import it.algos.albergo.conto.ContoModulo;
import it.algos.albergo.conto.addebito.Addebito;
import it.algos.albergo.conto.pagamento.Pagamento;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.campo.inizializzatore.InitFactory;
import it.algos.base.database.Db;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.modello.ModelloAlgos;
import it.algos.base.modulo.Modulo;
import it.algos.base.vista.Vista;
import it.algos.base.vista.VistaElemento;
import it.algos.base.wrapper.Campi;
import it.algos.base.wrapper.CampoValore;

import java.util.ArrayList;

/**
 * Tracciato record della tavola Addebito.
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
 * @version 1.0 / 2 feb 2006
 */
public abstract class MovimentoModello extends ModelloAlgos implements Movimento {

    /* campo di Conto sincronizzato con il totale movimenti */
    private Campi campoContoSync;


    /**
     * Costruttore completo senza parametri.
     */
    public MovimentoModello() {
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

        /* attiva la gestione trigger nuovo record */
        this.setTriggerNuovoAttivo(true);

        /* attiva la gestione trigger modifica record, con valori precedenti */
        this.setTriggerModificaAttivo(true, true);

        /* attiva la gestione trigger elimina record, con valori precedenti */
        this.setTriggerEliminaAttivo(true, true);

    }


    public boolean inizializza(Modulo unModulo) {
        return super.inizializza(unModulo);
    } /* fine del metodo */


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

        try { // prova ad eseguire il codice

            /* invoca il metodo sovrascritto della superclasse */
            super.creaCampi();

            /* campo data */
            unCampo = CampoFactory.data(Cam.data);
            unCampo.setInit(InitFactory.dataAttuale());
            unCampo.decora().obbligatorio();
            unCampo.setRicercabile(true);
            unCampo.setVisibileVistaDefault();
            this.addCampo(unCampo);

            /* campo conto */
            unCampo = CampoFactory.comboLinkSel(Cam.conto);
            unCampo.setNomeModuloLinkato(Conto.NOME_MODULO);
            unCampo.setAzioneDelete(Db.Azione.cascade);
            unCampo.setVisibileVistaDefault();
            unCampo.addColonnaCombo(Conto.NOME_MODULO, Conto.Cam.dataApertura.get());
            unCampo.decora().obbligatorio();
            unCampo.decora().etichetta("conto");
            unCampo.setLarScheda(180);
            unCampo.setRicercabile(true);
            unCampo.setUsaNuovo(false);
            this.addCampo(unCampo);

            /* campo importo */
            unCampo = this.creaCampoImporto();
            unCampo.getCampoDB().setCampoFisico(true);
            unCampo.getCampoLista().setPresenteVistaDefault(false);
            unCampo.setVisibileVistaDefault(true);
            unCampo.setRidimensionabile(false);
            unCampo.setTotalizzabile(true);
            unCampo.decora().obbligatorio();
            this.addCampo(unCampo);

            /* campo note */
            unCampo = CampoFactory.testo(Cam.note);
            unCampo.setLarghezza(200);
            unCampo.setRicercabile(true);
            this.addCampo(unCampo);


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Crea il campo Importo.
     * <p/>
     *
     * @return il campo Importo creato
     */
    protected Campo creaCampoImporto() {
        /* variabili e costanti locali di lavoro */
        Campo unCampo = null;

        try {    // prova ad eseguire il codice

            unCampo = CampoFactory.valuta(Cam.importo);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return unCampo;
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
        Vista vista;
        VistaElemento elemento;

        try { // prova ad eseguire il codice

            /* vista per il navigatore movimenti all'interno del conto */
            vista = this.creaVista(Movimento.Vis.vistaConto.get());
            vista.addCampo(Movimento.Cam.data.get());
            elemento = vista.addCampo(Movimento.Cam.importo.get());
            elemento.setTitoloColonna("importo");
            elemento.setLarghezzaColonna(60);
            elemento = vista.addCampo(Movimento.Cam.note.get());
            this.addVista(vista);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Metodo invocato dopo la creazione di un nuovo record.
     * <p/>
     * Se l'importo totale della registrazione è diverso da zero,
     * aggiorna il corrispondente totale movimenti nel conto.
     *
     * @param codice del record creato
     * @param lista array coppia campo-valore contenente
     * i dati appena registrati
     * @param conn la connessione utilizzata per effettuare la query
     *
     * @return true se riuscito
     */
    protected boolean nuovoRecordPost(int codice, ArrayList<CampoValore> lista, Connessione conn) {

        /* variabili e costanti locali di lavoro */
        boolean continua = true;
        boolean riuscito = true;
        Campo campoImporto = null;
        Campo campoLinkConto = null;
        CampoValore cv;
        double importo = 0;
        Modulo unModulo = null;
        MovimentoModulo modulo = null;
        ContoModulo modConto;
        int codConto = 0;

        try { // prova ad eseguire il codice

            /* recupera il modulo e controlla che sia del tipo giusto */
            if (continua) {
                unModulo = this.getModulo();
                if (!(unModulo instanceof MovimentoModulo)) {
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            /* recupera alcune variabili di uso comune */
            if (continua) {
                modulo = (MovimentoModulo)this.getModulo();
                campoImporto = modulo.getCampo(Movimento.Cam.importo.get());
                campoLinkConto = modulo.getCampo(Movimento.Cam.conto.get());
            }// fine del blocco if

            /* controlla se è stato registrato l'importo, lo recupera
             * e controlla se è diverso da zero */
            if (continua) {
                cv = Lib.Camp.getCampoValore(lista, campoImporto);
                if (cv != null) {
                    importo = Libreria.getDouble(cv.getValore());
                    if (importo == 0) {
                        continua = false;
                    }// fine del blocco if
                } else {
                    continua = false;
                }// fine del blocco if-else
            }// fine del blocco if

            /* recupera il codice del conto */
            if (continua) {
                cv = Lib.Camp.getCampoValore(lista, campoLinkConto);
                if (cv != null) {
                    codConto = Libreria.getInt(cv.getValore());
                    if (codConto == 0) {
                        continua = false;
                    }// fine del blocco if
                } else {
                    continua = false;
                }// fine del blocco if-else
            }// fine del blocco if

            /* delega al modulo conto l'aggiornamento del totale */
            if (continua) {
                modConto = ContoModulo.get();
                riuscito = modConto.updateTotale(codConto, modulo, conn);
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    } // fine del metodo


    /**
     * Metodo invocato dopo la registrazione di un record esistente.
     * <p/>
     * Se l'importo totale della registrazione è cambiato,
     * aggiorna il corrispondente totale movimenti nel conto.
     *
     * @param codice del record
     * @param lista array coppia campo-valore contenente i dati appena registrati
     * @param listaPre array coppia campo-valore contenente i dati precedenti la registrazione
     *
     * @return true se riuscito
     */
    protected boolean registraRecordPost(int codice,
                                         ArrayList<CampoValore> lista,
                                         ArrayList<CampoValore> listaPre,
                                         Connessione conn) {

        /* variabili e costanti locali di lavoro */
        boolean continua = true;
        boolean riuscito = true;
        Campo campoImporto = null;
        CampoValore cv;
        double importo = 0;
        double importoPre = 0;
        Modulo unModulo = null;
        MovimentoModulo modulo = null;
        ContoModulo modConto;
        int codConto = 0;


        try { // prova ad eseguire il codice

            /* recupera il modulo e controlla che sia del tipo giusto */
            if (continua) {
                unModulo = this.getModulo();
                if (!(unModulo instanceof MovimentoModulo)) {
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            /* recupera alcune variabili di uso comune */
            if (continua) {
                modulo = (MovimentoModulo)this.getModulo();
                campoImporto = modulo.getCampo(Movimento.Cam.importo.get());
            }// fine del blocco if

            /* controlla se è stato registrato l'importo e lo recupera */
            if (continua) {
                cv = Lib.Camp.getCampoValore(lista, campoImporto);
                if (cv != null) {
                    importo = Libreria.getDouble(cv.getValore());
                } else {
                    continua = false;
                }// fine del blocco if-else
            }// fine del blocco if

            /* recupera l'importo precedente */
            if (continua) {
                cv = Lib.Camp.getCampoValore(listaPre, campoImporto);
                if (cv != null) {
                    importoPre = Libreria.getDouble(cv.getValore());
                } else {
                    continua = false;  // errore, ci deve essere per forza
                    riuscito = false;
                }// fine del blocco if-else
            }// fine del blocco if

            /* controlla se l'importo è cambiato */
            if (continua) {
                if (importo == importoPre) {
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            /* recupera il codice del conto */
            if (continua) {
                codConto = modulo.query().valoreInt(Movimento.Cam.conto.get(), codice, conn);
                if (codConto == 0) {
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            /* delega al modulo conto l'aggiornamento del totale corrispondente */
            if (continua) {
                modConto = ContoModulo.get();
                riuscito = modConto.updateTotale(codConto, modulo, conn);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    } // fine del metodo


    /**
     * Metodo invocato dopo l'eliminazione di un record esistente.
     * <p/>
     * Se la registrazione eliminata aveva l'importo totale diverso da zero,
     * aggiorna il corrispondente totale movimenti nel conto.
     *
     * @param codice del record eliminato
     * @param listaPre lista dei valori contenuti nel record al momento della eliminazione
     * @param conn la connessione utilizzata per effettuare la query
     *
     * @return true se riuscito
     */
    protected boolean eliminaRecordPost(int codice,
                                        ArrayList<CampoValore> listaPre,
                                        Connessione conn) {

        /* variabili e costanti locali di lavoro */
        boolean continua = true;
        boolean riuscito = true;
        Campo campoImporto = null;
        Campo campoLinkConto = null;
        CampoValore cv;
        double importo = 0;
        Modulo unModulo = null;
        MovimentoModulo modulo = null;
        ContoModulo modConto;
        int codConto = 0;

        try { // prova ad eseguire il codice

            /* recupera il modulo e controlla che sia del tipo giusto */
            if (continua) {
                unModulo = this.getModulo();
                if (!(unModulo instanceof MovimentoModulo)) {
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            /* recupera alcune variabili di uso comune */
            if (continua) {
                modulo = (MovimentoModulo)this.getModulo();
                campoImporto = modulo.getCampo(Movimento.Cam.importo.get());
                campoLinkConto = modulo.getCampo(Addebito.Cam.conto.get());
            }// fine del blocco if

            /* recupera il totale della registrazione eliminata e
             * controlla se era diverso da zero */
            if (continua) {
                cv = Lib.Camp.getCampoValore(listaPre, campoImporto);
                if (cv != null) {
                    importo = Libreria.getDouble(cv.getValore());
                    if (importo == 0) {
                        continua = false;
                    }// fine del blocco if
                } else {
                    continua = false;
                }// fine del blocco if-else
            }// fine del blocco if

            /* recupera il codice del conto */
            if (continua) {
                cv = Lib.Camp.getCampoValore(listaPre, campoLinkConto);
                if (cv != null) {
                    codConto = Libreria.getInt(cv.getValore());
                    if (codConto == 0) {
                        continua = false;
                    }// fine del blocco if
                } else {
                    continua = false;
                }// fine del blocco if-else
            }// fine del blocco if

            /* delega al modulo conto l'aggiornamento del totale corrispondente */
            if (continua) {
                modConto = ContoModulo.get();
                riuscito = modConto.updateTotale(codConto, modulo, conn);
            }// fine del blocco if
            
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    } // fine del metodo


    public Campi getCampoContoSync() {
        return campoContoSync;
    }


    protected void setCampoContoSync(Campi campoContoSync) {
        this.campoContoSync = campoContoSync;
    }

} // fine della classe
