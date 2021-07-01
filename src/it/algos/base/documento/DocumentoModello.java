/**
 * Title:     DocumentoModello
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      8-gen-2007
 */
package it.algos.base.documento;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.database.dati.Dati;
import it.algos.base.documento.numeratore.NumeratoreDoc;
import it.algos.base.documento.numeratore.NumeratoreDocModulo;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.modello.ModelloAlgos;
import it.algos.base.modulo.Modulo;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.Query;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.query.selezione.QuerySelezione;
import it.algos.base.wrapper.CampoValore;

import java.util.ArrayList;
import java.util.Date;

/**
 * Modello generico di Documento.
 * </p>
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
 * @author alex
 * @version 1.0    / 8-gen-2007 ore 6.54.44
 */
public abstract class DocumentoModello extends ModelloAlgos implements Documento {

    /**
     * Nome chiave del documento
     * Utilizzato per creare una entry nella tavola tipidocumento
     * che mantiene la numerazione
     */
    private String chiave;

    /**
     * Nome del semaforo utilizzato durante la numerazione
     */
    private String nomeSemaforo;


    /**
     * Costruttore completo
     * <p/>
     *
     * @param chiave nome chiave del documento
     */
    public DocumentoModello(String chiave) {
        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza coi parametri */
        this.setChiave(chiave);

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

        /* attiva la gestione del trigger nuovo record */
        this.setTriggerNuovoAttivo(true);

        /* attiva la gestione dei trigger modifica, senza valori precedenti */
        this.setTriggerModificaAttivo(true, false);

        /* regola il nome del semaforo utilizzato per controllare la numerazione */
        this.setNomeSemaforo("numera_" + this.getChiave());

    }// fine del metodo inizia


    public boolean inizializza(Modulo unModulo) {
        /* variabili e costanti locali di lavoro */
        boolean riuscito;

        riuscito = super.inizializza(unModulo);

        try { // prova ad eseguire il codice

            /* Crea/regola il contatore per questo Documento.*/
            this.creaContatore();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    } /* fine del metodo */


    /**
     * Regola il contatore per questo Documento.
     * <p/>
     * Controlla che esista il record nella tavola Documenti;
     * se non esiste lo crea adesso.<br>
     * Se il prossimo ID è zero, regola il prossimo ID
     * analizzando i dati esistenti.
     */
    private void creaContatore() {
        /* variabili e costanti locali di lavoro */
        NumeratoreDocModulo modDoc;
        String nomeChiave;
        int chiave;
        ArrayList<CampoValore> valori;
        CampoValore cv;
        Campo campo;
        boolean continua;
        boolean rigenera = false;
        Ordine ordine;
        int ultimoNumero;
        Date ultimaData;

        try {    // prova ad eseguire il codice

            /* recupera il modulo Documento */
            modDoc = NumeratoreDocModulo.get();
            continua = (modDoc != null);

            if (continua) {
                /* controlla se esiste l'entry per la tavola di questo modello */
                nomeChiave = this.getChiave();
                chiave = modDoc.query().valoreChiave(NumeratoreDoc.Cam.chiave.get(), nomeChiave);

                /* se non esiste lo crea e recupera la chiave */
                if (chiave == 0) {
                    valori = new ArrayList<CampoValore>();
                    campo = modDoc.getCampo(NumeratoreDoc.Cam.chiave.get());
                    cv = new CampoValore(campo, nomeChiave);
                    valori.add(cv);
                    chiave = modDoc.query().nuovoRecord(valori);
                    rigenera = true;
                }// fine del blocco if-else

                /* ordina i documenti per data e numero */
                /* prende l'ultimo valore di numero e data */
                if (rigenera) {
                    ordine = new Ordine();
                    ordine.add(Documento.Cam.dataDoc.get());
                    ordine.add(Documento.Cam.numeroDoc.get());

                    ultimoNumero =
                            (Integer)this.query().valoreUltimoRecord(Documento.Cam.numeroDoc.get(),
                                    ordine);
                    ultimaData = (Date)this.query().valoreUltimoRecord(Documento.Cam.dataDoc.get(),
                            ordine);

                    /* registra il  valore */
                    modDoc.query().registraRecordValore(chiave,
                            NumeratoreDoc.Cam.ultimoNumero.get(),
                            ultimoNumero);
                    modDoc.query().registraRecordValore(chiave,
                            NumeratoreDoc.Cam.ultimaData.get(),
                            ultimaData);
                }// fine del blocco if


            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
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
     * @see it.algos.base.campo.video.decorator.VideoFactory
     */
    protected void creaCampi() {
        /* variabili e costanti locali di lavoro */
        Campo unCampo;

        try { // prova ad eseguire il codice

            /* invoca il metodo sovrascritto della superclasse */
            super.creaCampi();

            /* campo flag nuovo documento */
            unCampo = CampoFactory.checkBox(Documento.Cam.nuovoDoc);
            this.addCampo(unCampo);

            /* campo numero documento */
            unCampo = CampoFactory.intero(Documento.Cam.numeroDoc);
            unCampo.decora().obbligatorio();
            unCampo.setAbilitato(false);
            unCampo.setRicercabile(true);
            this.addCampo(unCampo);

            /* campo data documento */
            unCampo = CampoFactory.data(Documento.Cam.dataDoc);
            unCampo.decora().obbligatorio();
            unCampo.setRicercabile(true);
            this.addCampo(unCampo);

            /* campo anno documento */
            unCampo = CampoFactory.intero(Documento.Cam.annoDoc);
            this.addCampo(unCampo);

            /* campo documento confermato */
            unCampo = CampoFactory.checkBox(Documento.Cam.confermato);
            this.addCampo(unCampo);

            /* campo data conferma documento */
            unCampo = CampoFactory.data(Documento.Cam.dataConferma);
            unCampo.setAbilitato(false);
            unCampo.setInit(null);  // nessun inizializzatore di data
            this.addCampo(unCampo);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Controlla se un documento è confermato.
     * <p/>
     *
     * @param codice del record da controllare
     *
     * @return true se confermato
     */
    public boolean isConfermato(int codice) {
        /* variabili e costanti locali di lavoro */
        boolean confermato = true;
        Modulo modulo;
        String nomeCampo;

        try {    // prova ad eseguire il codice
            modulo = this.getModulo();
            nomeCampo = Documento.Cam.confermato.get();
            confermato = modulo.query().valoreBool(nomeCampo, codice);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return confermato;
    }


    /**
     * Controlla se un documento è confermabile.
     * <p/>
     * Sovrascritto dalle sottoclassi
     *
     * @param codice del record da controllare
     *
     * @return true se confermabile
     */
    public boolean isConfermabile(int codice) {
        /* variabili e costanti locali di lavoro */
        boolean confermabile = true;
        String testo;

        try {    // prova ad eseguire il codice
            testo = this.checkConfermabile(codice);
            if (Lib.Testo.isValida(testo)) {
                confermabile = false;
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return confermabile;
    }


    /**
     * Ritorna la motivazione per cui un documento non è confermabile.
     * <p/>
     *
     * @param codice del record da controllare
     *
     * @return la motivazione, stringa vuota se confermabile
     */
    public String checkConfermabile(int codice) {
        /* variabili e costanti locali di lavoro */
        String spiega = "";
        Query query;
        Filtro filtro;
        Dati dati;
        int intero;

        try { // prova ad eseguire il codice

            query = new QuerySelezione(this.getModulo());
            filtro = FiltroFactory.codice(this.getModulo(), codice);
            query.setFiltro(filtro);
            query.addCampo(Documento.Cam.numeroDoc.get());

            dati = this.query().querySelezione(query);

            /* controlla che ci sia il numero */
            intero = dati.getIntAt(0, this.getCampo(Documento.Cam.numeroDoc.get()));
            if (intero <= 0) {
                spiega += "- Manca il numero del documento\n";
            }// fine del blocco if

            dati.close();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return spiega;
    }


    /**
     * Ritorna il prossimo numero disponibile per questo tipo di documento.
     * <p/>
     *
     * @return il prossimo numero disponibile
     */
    private int getNextNumero() {
        /* variabili e costanti locali di lavoro */
        int numero = 0;
        NumeratoreDocModulo modDoc;
        String nomeChiave;

        try {    // prova ad eseguire il codice
            modDoc = NumeratoreDocModulo.get();
            nomeChiave = this.getChiave();
            numero = modDoc.getNextNumero(nomeChiave);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return numero;
    }


    /**
     * Regola il contatore con l'ultimo numero assegnato
     * a questo tipo di documento.
     * <p/>
     *
     * @param numero l'ultimo numero assegnato
     *
     * @return true se riuscito
     */
    private boolean setUltNumero(int numero) {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = false;
        NumeratoreDocModulo modDoc;
        String nomeChiave;

        try {    // prova ad eseguire il codice
            modDoc = NumeratoreDocModulo.get();
            nomeChiave = this.getChiave();
            riuscito = modDoc.setUltNumero(nomeChiave, numero);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * .
     * <p/>
     */
    protected boolean confermaNuovoRecord() {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = false;

        try {    // prova ad eseguire il codice
            ;
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Metodo invocato prima della creazione di un nuovo record.
     * <p/>
     * Puo' modificare i valori che stanno per essere registrati<br>
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
    @Override
    protected boolean nuovoRecordAnte(ArrayList<CampoValore> lista, Connessione conn) {
        CampoValore cv;
        Campo campo;
        int numero;

        try { // prova ad eseguire il codice

            /* regola il campo nuovo documento a true */
            campo = this.getCampo(Documento.Cam.nuovoDoc.get());
            cv = Lib.Camp.getCampoValore(lista, campo);
            if (cv == null) {
                cv = new CampoValore(campo);
                lista.add(cv);
            }// fine del blocco if-else
            cv.setValore(true);

            /* aggiunge o modifica campo numero documento nella lista */
            campo = this.getCampo(Documento.Cam.numeroDoc.get());
            numero = this.getNextNumero();
            cv = Lib.Camp.getCampoValore(lista, campo);
            if (cv == null) {
                cv = new CampoValore(campo, null);
                lista.add(cv);
            }// fine del blocco if-else
            cv.setValore(numero);

//            /* sincronizza l'anno documento nella lista */
//            this.syncAnno(lista);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return true;
    } // fine del metodo


    protected boolean registraRecordAnte(int codice,
                                         ArrayList<CampoValore> lista,
                                         Connessione conn) {

        /* variabili e costanti locali di lavoro */
        boolean riuscito = false;
        boolean continua = true;
        Campo campo;
        CampoValore cvNuovoDoc = null;
        boolean isNuovo = false;
        Campo campoNumero;
        Campo campoData;
        Campo campoConf;
        Campo campoDataConf;
        CampoValore cv;
        CampoValore cvNumero;
        CampoValore cvData;
        int numero;
        Date data;
        boolean controlla;
        boolean esiste;
        boolean flag;

        try { // prova ad eseguire il codice

            /* controlla che ci sia il campo nuovo documento,
             * se non c'e' lo aggiunge ora recuperando il valore
             * dal database */
            if (continua) {
                campo = this.getCampo(Cam.nuovoDoc.get());
                cvNuovoDoc = Lib.Camp.getCampoValore(lista, campo);
                if (cvNuovoDoc == null) {
                    isNuovo = this.query().valoreBool(campo, codice, conn);
                    cvNuovoDoc = new CampoValore(campo, isNuovo);
                    lista.add(cvNuovoDoc);
                }// fine del blocco if
            }// fine del blocco if

            /* recupera il flag nuovo documento */
            if (continua) {
                isNuovo = (Boolean)cvNuovoDoc.getValore();
            }// fine del blocco if

            /* se e' un nuovo documento, esegue le operazioni e i controlli
             * per nuovo documento */
            if (continua) {
                riuscito = true;
                if (isNuovo) {
                    riuscito = this.regolaNuovoDoc(lista);
                    continua = riuscito;
                }// fine del blocco if
            }// fine del blocco if

//            // todo mettere questo controllo in regolaNuovoDoc
//            // todo altrimenti se falliscono il contatore viene
//            // todo incrementato lo stesso!!  alex 16-01-07
//            /* controlla che la coppia numero-anno non
//             * si sovrapponga a record esistenti diversi da quello
//             * correntemente in registrazione */
//            if (continua) {
//
//                /* recupera i campi numero e data dal modello */
//                campoNumero = this.getCampo(Cam.numeroDoc.get());
//                campoData = this.getCampo(Cam.dataDoc.get());
//
//                /* recupera i cv numero e data dalla lista */
//                cvNumero = Lib.Camp.getCampoValore(lista, campoNumero);
//                cvData = Lib.Camp.getCampoValore(lista, campoData);
//
//                /* se nella lista non c'è nessuno dei due
//                 * non devo controllare nulla */
//                controlla = ((cvNumero != null) || (cvData != null));
//
//                /* procedo solo se devo effettuare il controllo */
//                if (controlla) {
//
//                    /* regolazione iniziale a false */
//                    riuscito = false;
//
//                    /* recupera il numero dalla lista - se non c'è lo recupera dal db */
//                    if (cvNumero != null) {
//                        numero = (Integer)cvNumero.getValore();
//                    } else {
//                        numero = this.query().valoreInt(campoNumero, codice, conn);
//                    }// fine del blocco if-else
//
//                    /* recupera la data dalla lista - se non c'è la recupera dal db */
//                    if (cvData != null) {
//                        data = (Date)cvData.getValore();
//                    } else {
//                        data = this.query().valoreData(campoData, codice, conn);
//                    }// fine del blocco if-else
//
//                    /* recupera l'anno dalla data */
//                    anno = Lib.Data.getAnno(data);
//
//                    /* controlla l'esistenza */
//                    // todo usare l'anno, non la data!
//                    esiste = this.chkGiaEsistente(numero, data, codice, conn);
//
//                    /* se non esiste è riuscito
//                     * se esiste solleva una eccezione e non è riuscito */
//                    if (!esiste) {
//                        riuscito = true;
//                    } else {
//                        Lib.Sist.beep();
//                        throw new Exception("Documento con pari data e numero già esistente!");
//                    }// fine del blocco if-else
//
//                }// fine del blocco if
//
//            }// fine del blocco if

            /**
             * se il documento viene confermato e manca la data di conferma,
             * aggiunge ora la data di oggi
             */
            campoConf = this.getCampo(Cam.confermato.get());
            cv = Lib.Camp.getCampoValore(lista, campoConf);
            if (cv != null) {
                flag = (Boolean)cv.getValore();
                if (flag) {
                    campoDataConf = this.getCampo(Cam.dataConferma.get());
                    Lib.Camp.chkCampoValoreValido(lista, campoDataConf, Lib.Data.getCorrente());
                }// fine del blocco if
            }// fine del blocco if

//            /* sincronizza l'anno documento nella lista */
//            this.syncAnno(lista);

            /* rimanda alla superclasse */
            if (continua) {
                riuscito = super.registraRecordAnte(codice, lista, conn);
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    } // fine del metodo


    /**
     * Sincronizza il campo Anno Documento con la data del documento.
     * <p/>
     * Modifica o crea il campoValore anno nella lista da registrare
     * Opera solo se la lista contiene la data del documento.
     *
     * @param lista dei CampoValore da registrare
     *
     * @return true se ruscito
     */
    private boolean syncAnno(ArrayList<CampoValore> lista) {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = true;
        boolean continua = true;
        Campo campo;
        CampoValore cv = null;
        Date data = null;
        Object valore;
        int anno;

        try {    // prova ad eseguire il codice

            /* controlla che nella lista ci sia la data, se no non fa nulla */
            if (continua) {
                campo = this.getCampo(Cam.dataDoc.get());
                cv = Lib.Camp.getCampoValore(lista, campo);
                if (cv != null) {
                    valore = cv.getValore();
                    data = Libreria.getDate(valore);
                } else {
                    continua = false;
                }// fine del blocco if-else
            }// fine del blocco if

            /* controlla che nella lista ci sia il campo anno doc,
             * se non c'e' lo aggiunge ora */
            if (continua) {
                campo = this.getCampo(Cam.annoDoc.get());
                cv = Lib.Camp.getCampoValore(lista, campo);
                if (cv == null) {
                    cv = new CampoValore(campo);
                    lista.add(cv);
                }// fine del blocco if
            }// fine del blocco if

            /* regola l'anno nel campoValore */
            if (continua) {
                anno = Lib.Data.getAnno(data);
                cv.setValore(anno);
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Regolazioni e controlli alla prima registrazione definitiva di un
     * nuovo documento.
     * <p/>
     *
     * @param lista degli oggetti CampoValore da registrare
     *
     * @return true se riuscito
     */
    private boolean regolaNuovoDoc(ArrayList<CampoValore> lista) {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = false;
        boolean continua = true;
        CampoValore cvNuovoDoc = null;
        CampoValore cvNumDoc = null;
        Campo campo;
        String semaforo = null;
        boolean impegnato = false;
        int numeroDef = 0;

        try {    // prova ad eseguire il codice

            /* recupera il campo Nuovo Documento che deve esistere */
            if (continua) {
                campo = this.getCampo(Cam.nuovoDoc.get());
                cvNuovoDoc = Lib.Camp.getCampoValore(lista, campo);
                continua = cvNuovoDoc != null;
            }// fine del blocco if

            /* controlla che ci sia il campo numero documento,
             * se non c'e' lo aggiunge ora */
            if (continua) {
                campo = this.getCampo(Cam.numeroDoc.get());
                cvNumDoc = Lib.Camp.getCampoValore(lista, campo);
                if (cvNumDoc == null) {
                    cvNumDoc = new CampoValore(campo);
                    lista.add(cvNumDoc);
                }// fine del blocco if
            }// fine del blocco if

            /* impegna il semaforo di numerazione */
            if (continua) {
                semaforo = this.getNomeSemaforo();
                if (Progetto.setSemaforo(semaforo, 5, true)) {   // ttl 5 sec, con messaggio
                    impegnato = true;
                } else {
                    continua = false;
                }// fine del blocco if-else
            }// fine del blocco if

            /* recupera il numero definitivo e controlla che non sia zero */
            if (continua) {
                numeroDef = this.getNextNumero();
                if (numeroDef <= 0) {
                    throw new Exception("Il numero di documento è zero.\nControllare il numeratore.");
                }// fine del blocco if-else
            }// fine del blocco if

            /* assegna il numero definitivo */
            if (continua) {
                cvNumDoc.setValore(numeroDef);
            }// fine del blocco if

            /* aggiorna il contatore */
            if (continua) {
                continua = this.setUltNumero(numeroDef);
            }// fine del blocco if

            /* spegne il flag nuovo documento */
            if (continua) {
                cvNuovoDoc.setValore(false);
            }// fine del blocco if

            /* controllo operazione riuscita */
            if (continua) {
                riuscito = true;
            }// fine del blocco if

            /* disimpegna il semaforo se l'aveva impegnato */
            if (impegnato) {
                Progetto.clearSemaforo(semaforo);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Controlla se esiste già un documento con un dato numero e anno
     * e con codice diverso da quello fornito.
     * <p/>
     *
     * @param numero del documento
     * @param anno del documento
     * @param codice del documento
     * @param conn connessione da utilizzare
     *
     * @return true se esiste
     */
    private boolean chkGiaEsistente(int numero, int anno, int codice, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        boolean esiste = false;
        Filtro filtroNum;
        Filtro filtroAnno;
        Filtro filtroCod;
        Filtro filtro;
        int quanti;

        try {    // prova ad eseguire il codice
            filtroNum = FiltroFactory.crea(Cam.numeroDoc.get(), numero);
            filtroAnno = FiltroFactory.crea(Cam.annoDoc.get(), anno);
            filtroCod = FiltroFactory.crea(this.getCampoChiave(), Filtro.Op.DIVERSO, codice);
            filtro = new Filtro();
            filtro.add(filtroNum);
            filtro.add(filtroAnno);
            filtro.add(filtroCod);
            quanti = this.query().contaRecords(filtro, conn);
            esiste = (quanti > 0);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return esiste;
    }


    private String getChiave() {
        return chiave;
    }


    private void setChiave(String chiave) {
        this.chiave = chiave;
    }


    private String getNomeSemaforo() {
        return nomeSemaforo;
    }


    private void setNomeSemaforo(String nomeSemaforo) {
        this.nomeSemaforo = nomeSemaforo;
    }

}// fine della classe
