/**
 * Copyright(c): 2006
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 20 lug 2006
 */

package it.algos.gestione.fattura.riga;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.campo.inizializzatore.InitFactory;
import it.algos.base.campo.logica.CampoLogica;
import it.algos.base.database.Db;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.modello.ModelloAlgos;
import it.algos.base.modulo.Modulo;
import it.algos.base.vista.Vista;
import it.algos.base.vista.VistaElemento;
import it.algos.base.wrapper.CampoValore;
import it.algos.base.wrapper.Estratti;
import it.algos.base.wrapper.EstrattoBase;
import it.algos.gestione.tabelle.iva.Iva;
import it.algos.gestione.tabelle.iva.IvaModulo;
import it.algos.gestione.tabelle.um.UM;
import it.algos.gestione.tabelle.um.UMModulo;

import java.util.ArrayList;

/**
 * Tracciato record della tavola RigaFattura.
 * </p>
 * Questa classe concreta: <ul>
 * <li> Regola il nome della tavola </li>
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
 * <li> Restituisce un estratto di informazioni </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 20 lug 2006
 */
public final class RigaFatturaModello extends ModelloAlgos implements RigaFattura {

    /**
     * Costruttore completo senza parametri.
     */
    public RigaFatturaModello() {
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
     * Regolazioni immediate di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* nome della tavola di archivio collegata
         * i nomi delle tavole sono sempre minuscoli
         * se vuoto usa il nome del modulo */
        super.setTavolaArchivio(NOME_TAVOLA);

        /* attiva il trigger nuovo record */
        this.setTriggerNuovoAttivo(true);

        /* attiva la gestione trigger modifica record, con valori precedenti */
        this.setTriggerModificaAttivo(true, true);

        /* attiva il trigger di eliminazione con valori precedenti */
        this.setTriggerEliminaAttivo(true, true);
    }


    public boolean inizializza(Modulo unModulo) {
        boolean riuscito;
        riuscito = super.inizializza(unModulo);
        /* valore di ritorno */
        return riuscito;
    } /* fine del metodo */


    public boolean relaziona() {
        return super.relaziona();
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
     */
    protected void creaCampi() {
        /* variabili e costanti locali di lavoro */
        Campo unCampo;
        CampoLogica cl;

        try { // prova ad eseguire il codice

            /* invoca il metodo sovrascritto della superclasse */
            super.creaCampi();

            this.getCampoChiave().setInit(InitFactory.contatore(this.getModulo()));

            /* campo fattura di riferimento */
            String nome = ((RigaFatturaModulo)this.getModulo()).getNomeModuloPadre();
            unCampo = CampoFactory.link(RigaFattura.Cam.fattura);
//            unCampo.setNomeModuloLinkato(FattBase.NOME_MODULO);
            unCampo.setNomeModuloLinkato(nome);
            unCampo.setAzioneDelete(Db.Azione.cascade);
            unCampo.setAbilitato(false);
            this.addCampo(unCampo);

            /* campo descrizione */
            unCampo = CampoFactory.testoArea(RigaFattura.Cam.descrizione);
            unCampo.decora().obbligatorio();
            unCampo.setLarLista(300);
            unCampo.setLarScheda(400);
            unCampo.setNumeroRighe(4);  // per editing in scheda
            this.addCampo(unCampo);

            /* campo unità di misura */
            unCampo = CampoFactory.comboLinkSel(RigaFattura.Cam.unita);
            unCampo.setNomeModuloLinkato(UM.NOME_MODULO);
            unCampo.setNomeCampoValoriLinkato(UM.Cam.sigla.get());
            unCampo.setLarghezza(40);
            unCampo.setRidimensionabile(false);
            this.addCampo(unCampo);

            /* campo unità di misura fissata a testo */
            unCampo = CampoFactory.testo(RigaFattura.Cam.unitafix);
            unCampo.setAbilitato(false);
            unCampo.setLarghezza(50);
            this.addCampo(unCampo);

            /* campo quantità */
            unCampo = CampoFactory.reale(RigaFattura.Cam.quantita);
            unCampo.setNumDecimali(2);
            unCampo.setLarghezza(60);
            unCampo.setRidimensionabile(false);
            this.addCampo(unCampo);

            /* campo prezzo unitario */
            unCampo = CampoFactory.valuta(RigaFattura.Cam.prezzoUnitario);
            unCampo.setLarghezza(80);
            unCampo.setRidimensionabile(false);
            this.addCampo(unCampo);

            /* campo importo netto
             * (quantità x prezzo unitario) */
            unCampo = CampoFactory.calcola(RigaFattura.Cam.importonetto,
                    CampoLogica.Calcolo.prodottoValuta,
                    RigaFattura.Cam.quantita,
                    RigaFattura.Cam.prezzoUnitario);
            unCampo.getCampoDB().setCampoFisico(true);
            unCampo.setVisibileVistaDefault(false);
            this.addCampo(unCampo);

            /* campo sconto percentuale */
            unCampo = CampoFactory.sconto(RigaFattura.Cam.percSconto);
            this.addCampo(unCampo);

            /* campo importo sconto
             * (importo netto x perc. sconto)*/
            unCampo = CampoFactory.calcola(RigaFattura.Cam.importoSconto,
                    CampoLogica.Calcolo.prodottoValuta,
                    RigaFattura.Cam.importonetto,
                    RigaFattura.Cam.percSconto);
            unCampo.getCampoDB().setCampoFisico(true);
            unCampo.setVisibileVistaDefault(false);
            this.addCampo(unCampo);

            /* campo imponibile
             * (importo netto - importo sconto)*/
            unCampo = CampoFactory.calcola(RigaFattura.Cam.imponibile,
                    CampoLogica.Calcolo.differenzaValuta,
                    RigaFattura.Cam.importonetto,
                    RigaFattura.Cam.importoSconto);
            unCampo.getCampoDB().setCampoFisico(true);
            unCampo.setVisibileVistaDefault(false);
            unCampo.setLarghezza(80);
            unCampo.setRidimensionabile(false);
            this.addCampo(unCampo);

            /* campo codifica iva */
            unCampo = CampoFactory.comboLinkPop(RigaFattura.Cam.codIva);
            unCampo.decora().obbligatorio();
            unCampo.setUsaNonSpecificato(false);
            unCampo.setNomeModuloLinkato(Iva.NOME_MODULO);
            unCampo.setNomeCampoValoriLinkato(Iva.Cam.sigla.get());
            unCampo.setLarScheda(100);
            this.addCampo(unCampo);

            /* campo codifica iva fissata a testo */
            unCampo = CampoFactory.testo(RigaFattura.Cam.codIvafix);
            unCampo.setLarScheda(100);
            this.addCampo(unCampo);

            /* campo percentuale iva */
            unCampo = CampoFactory.percentuale(RigaFattura.Cam.percIva.get());
            this.addCampo(unCampo);

            /* campo importo iva
             * (imponibile x perc. iva)*/
            unCampo = CampoFactory.calcola(RigaFattura.Cam.importoIva,
                    CampoLogica.Calcolo.prodottoValuta,
                    RigaFattura.Cam.imponibile,
                    RigaFattura.Cam.percIva);
            unCampo.getCampoDB().setCampoFisico(true);
            this.addCampo(unCampo);

            /* rende visibile il campo ordine */
            super.setCampoOrdineVisibileLista();

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
        ArrayList<String> lista;
        Vista vista;
        VistaElemento elem;
        Modulo mod;
        Campo campoIvaBreve;

        try { // prova ad eseguire il codice

            /* recupera il campo codice iva breve */
            mod = IvaModulo.get();
            campoIvaBreve = mod.getCampo(Iva.Cam.codbreve.get());

            /* crea la vista per la riga all'interno della fattura */
            vista = new Vista(Vis.fattura.toString(), this.getModulo());
            vista.addCampo(this.getCampoOrdine());
            vista.addCampo(Cam.descrizione.get());
            elem = vista.addCampo(Cam.unita.get());
            elem.setLarghezzaColonna(40);
            elem.setRidimensionabile(false);
            vista.addCampo(Cam.quantita.get());
            vista.addCampo(Cam.prezzoUnitario.get());
            vista.addCampo(Cam.percSconto.get());
            vista.addCampo(Cam.imponibile.get());
            elem = vista.addCampo(campoIvaBreve);
            elem.setTitoloColonna("iva");

            /* rende invisibile il campo ordine
             * e lo regola come ordine di default */
            elem = vista.getElemento(0);
            elem.setVisibile(false);
            vista.setCampoOrdineDefault(this.getCampoOrdine());

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
        /* variabili e costanti locali di lavoro */
        Vista unaVista;
        Campo unCampo;

        try { // prova ad eseguire il codice
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
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
                case descrizione:
                    unEstratto = this.getEstrattoTesto(Cam.descrizione.get(), (Integer)chiave);
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
     * Metodo invocato prima della creazione di un nuovo record.
     * <p/>
     * Per tutti i nuovi record, se la lista contiene il codice IVA,
     * regola di conseguenza il flag nonImponibile e la percentuale IVA.
     *
     * @param lista array coppia campo-valore contenente i
     * dati che stanno per essere registrati
     * @param conn la connessione utilizzata per effettuare la query
     *
     * @return true per continuare il processo di registrazione,
     *         false per non effettuare la registrazione
     */
    protected boolean nuovoRecordAnte(ArrayList<CampoValore> lista, Connessione conn) {
        CampoValore cv;
        CampoValore unCv;
        Campo campoCodIva;
        Campo campoPercIva;
        Campo campoUM;
//        Campo campoFlagImpo;
        int codIva;
        boolean flagEsente;
        Double percIva;
        Modulo modIva;

        try { // prova ad eseguire il codice

            campoCodIva = this.getCampo(Cam.codIva.get());
            campoPercIva = this.getCampo(Cam.percIva.get());
            campoUM = this.getCampo(Cam.unita.get());
//            campoFlagImpo = this.getCampo(Cam.flagNonImponibile.get());

            cv = Lib.Camp.getCampoValore(lista, campoCodIva);
            if (cv != null) {

                /* recupera il flag e la percentuale dalla tabella iva */
                codIva = (Integer)cv.getValore();
                modIva = IvaModulo.get();
                percIva = modIva.query().valoreDouble(Iva.Cam.valore.get(), codIva);
                flagEsente = modIva.query().valoreBool(Iva.Cam.fuoricampo.get(), codIva);

                /* aggiunge o modifica campo percentuale iva nella lista */
                unCv = Lib.Camp.getCampoValore(lista, campoPercIva);
                if (unCv == null) {
                    unCv = new CampoValore(campoPercIva, null);
                    lista.add(unCv);
                }// fine del blocco if-else
                unCv.setValore(percIva);

                /* regola il campo u.m. dal default */
                unCv = Lib.Camp.getCampoValore(lista, campoUM);
                if (unCv == null) {
                    unCv = new CampoValore(campoUM, null);
                    lista.add(unCv);
                }// fine del blocco if-else
                unCv.setValore(UMModulo.get().getRecordPreferito());

//                /* aggiunge o modifica campo flag imponibile nella lista */
//                unCv = Lib.Camp.getCampoValore(lista, campoFlagImpo);
//                if (unCv == null) {
//                    unCv = new CampoValore(campoFlagImpo, null);
//                    lista.add(unCv);
//                }// fine del blocco if-else
//                unCv.setValore(flagEsente);

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return true;
    } // fine del metodo

//    /**
//     * Metodo invocato dopo la creazione di un nuovo record.
//     * <p/>
//     * Se imponibile, non imponibile o iva sono valorizzati,
//     * aggiorna i totali della fattura.
//     *
//     * @param codice del record creato
//     * @param lista array coppia campo-valore contenente
//     *              i dati appena registrati
//     * @param conn la connessione utilizzata per effettuare la query
//     * @return true se riuscito
//     */
//    protected boolean nuovoRecordPost(int codice, ArrayList<CampoValore> lista, Connessione conn) {
//        /* variabili e costanti locali di lavoro */
//        int codFattura;
//        double impo=0;
//        double iva=0;
//        Campo campo;
//        CampoValore cv;
//
//
//        try { // prova ad eseguire il codice
//
//            /* recupera l'imponibile */
//            campo = this.getCampo(RigaFattura.Cam.imponibile.get());
//            cv = Lib.Camp.getCampoValore(lista, campo);
//            if (cv!=null) {
//                impo = (Double)cv.getValore();
//            }// fine del blocco if
//
//            /* recupera iva */
//            campo = this.getCampo(RigaFattura.Cam.importoIva.get());
//            cv = Lib.Camp.getCampoValore(lista, campo);
//            if (cv!=null) {
//                iva = (Double)cv.getValore();
//            }// fine del blocco if
//
//            /* se almeno uno è valorizzato aggiorna i totali fattura */
//            if ((impo!=0) || (iva!=0)) {
//                codFattura = this.getCodFattura(codice, conn);
//                this.syncFattura(codFattura, conn);
//            }// fine del blocco if
//
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//
//        /* valore di ritorno */
//        return true;
//    } // fine del metodo

//    /**
//     * Metodo invocato dopo la registrazione di un record esistente.
//     * <p/>
//     * Se è cambiato imponibile, non imponibile o IVA aggiorna
//     * i totali della fattura.
//     *
//     * @param codice del record modificato
//     * @param lista  array coppia campo-valore contenente
//     *               i dati appena registrati
//     * @param listaPre  array coppia campo-valore contenente
//     *                  i dati precedenti la registrazione
//     * @param conn la connessione utilizzata per effettuare la query
//     * @return true se riuscito
//     */
//    @Override protected boolean modificaRecordPost(int codice,
//                                                   ArrayList<CampoValore> lista,
//                                                   ArrayList<CampoValore> listaPre,
//                                                   Connessione conn) {
//        /* variabili e costanti locali di lavoro */
//        int codFattura;
//        boolean sync=false;
//        String nomeCampo;
//
//        try { // prova ad eseguire il codice
//
//            /* controllo modifica imponibile */
//            if (!sync) {
//                nomeCampo = RigaFattura.Cam.imponibile.get();
//                if (this.isModificato(nomeCampo, lista, listaPre)) {
//                    sync = true;
//                }// fine del blocco if
//            }// fine del blocco if
//
//            /* controllo modifica iva */
//            if (!sync) {
//                nomeCampo = RigaFattura.Cam.importoIva.get();
//                if (this.isModificato(nomeCampo, lista, listaPre)) {
//                    sync = true;
//                }// fine del blocco if
//            }// fine del blocco if
//
//            /* se almeno uno è cambiato aggiorna i totali fattura */
//            if (sync) {
//                codFattura = this.getCodFattura(codice, conn);
//                this.syncFattura(codFattura, conn);
//            }// fine del blocco if
//
//
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//
//        return true;
//    } // fine del metodo

//    /**
//     * Metodo invocato dopo l'eliminazione di un record esistente.
//     * <p/>
//     * Se imponibile, non imponibile o IVA erano valorizzati,
//     * aggiorna i totali della fattura.
//     *
//     * @param codice del record eliminato
//     * @param listaPre lista dei valori contenuti nel record al momento della eliminazione
//     * @param conn la connessione utilizzata per effettuare la query
//     * @return true se riuscito
//     */
//    @Override protected boolean eliminaRecordPost(int codice,
//                                                  ArrayList<CampoValore> listaPre,
//                                                  Connessione conn) {
//        /* variabili e costanti locali di lavoro */
//        int codFattura;
//        double impo=0;
//        double iva=0;
//        Campo campo;
//        CampoValore cv;
//
//        Campo campoLinkFattura;
//
//        try { // prova ad eseguire il codice
//
//            /* recupera l'imponibile eliminato */
//            campo = this.getCampo(RigaFattura.Cam.imponibile.get());
//            cv = Lib.Camp.getCampoValore(listaPre, campo);
//            if (cv!=null) {
//                impo = (Double)cv.getValore();
//            }// fine del blocco if
//
//            /* recupera iva eliminata */
//            campo = this.getCampo(RigaFattura.Cam.importoIva.get());
//            cv = Lib.Camp.getCampoValore(listaPre, campo);
//            if (cv!=null) {
//                iva = (Double)cv.getValore();
//            }// fine del blocco if
//
//            /* se almeno uno era valorizzato aggiorna i totali fattura */
//            if ((impo!=0) || (iva!=0)) {
//                campoLinkFattura = this.getCampo(RigaFattura.Cam.fattura.get());
//                cv = Lib.Camp.getCampoValore(listaPre, campoLinkFattura);
//                codFattura = (Integer)cv.getValore();
//                this.syncFattura(codFattura, conn);
//            }// fine del blocco if
//
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//
//        /* valore di ritorno */
//        return true;
//    } // fine del metodo


    /**
     * Recupera il codice della fattura proprietaria di una riga.
     * <p/>
     *
     * @param codRiga codice della riga
     * @param conn la connessione da utilizzare
     *
     * @return il codice della fattura
     */
    private int getCodFattura(int codRiga, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        int codFattura = 0;
        Modulo modRighe;

        try {    // prova ad eseguire il codice
            modRighe = this.getModulo();
            codFattura = modRighe.query().valoreInt(RigaFattura.Cam.fattura.get(), codRiga, conn);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codFattura;
    }


    /**
     * Controlla se un campo è stato modificato.
     * <p/>
     *
     * @param nomeCampo da controllare
     * @param lista valori registrati
     * @param listaPre valori precedenti la registrazione
     */
    private boolean isModificato(String nomeCampo,
                                 ArrayList<CampoValore> lista,
                                 ArrayList<CampoValore> listaPre) {
        /* variabili e costanti locali di lavoro */
        boolean modificato = false;
        Campo campo;
        CampoValore cv;
        Object valorePre = null;
        Object valorePost = null;

        try {    // prova ad eseguire il codice
            campo = this.getCampo(nomeCampo);

            /* recupera il valore prima della registrazione */
            cv = Lib.Camp.getCampoValore(listaPre, campo);
            if (cv != null) {
                valorePre = cv.getValore();
            }// fine del blocco if

            /* recupera il valore dopo della registrazione */
            cv = Lib.Camp.getCampoValore(lista, campo);
            if (cv != null) {
                valorePost = cv.getValore();
            }// fine del blocco if

            /* se è stato registrato un valore, ed è diverso
             * da quello precedente, è stato modificato */
            if (valorePost != null) {
                if (valorePre != null) {
                    if (!valorePre.equals(valorePost)) {
                        modificato = true;
                    }// fine del blocco if
                } else {
                    modificato = true;
                }// fine del blocco if-else
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return modificato;
    }

//    /**
//     * Sincronizza la fattura.
//     * <p/>
//     *
//     * @param codice della fattura
//     * @param conn connessione da utilizzare
//     *
//     * @return true se riuscito
//     */
//    private boolean syncFattura(int codice, Connessione conn) {
//        /* variabili e costanti locali di lavoro */
//        boolean riuscito = true;
//        FatturaModulo modFattura;
//
//        try { // prova ad eseguire il codice
//
//            // todo lanciare un evento ascoltato da FatturaModello
//            modFattura = FatturaModulo.get();
//            modFattura.syncTotali(codice, conn);
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//
//        /* valore di ritorno */
//        return riuscito;
//    } // fine del metodo


} // fine della classe
