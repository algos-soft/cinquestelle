/**
 * Title:        CDBSub.java
 * Package:      it.algos.base.campo.db
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Ceresa, Colombo, Valbonesi
 * @version 1.0  /
 * Creato:       il 21 agosto 2003 alle 16.41
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2003  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
// Package di questa classe e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.campo.db;

import it.algos.base.campo.base.Campo;
import it.algos.base.costante.CostanteModulo;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.vista.Vista;
//-----------------------------------------------------------------------------
// Classe principale


//-----------------------------------------------------------------------------
/**
 * Questa classe concreta e' responsabile di: <br>
 * A - Gestire la sezione DB di un campo Sublista <br>
 * B - ... <br>
 *
 * @author Guido Andrea Ceresa
 * @author Alberto Colombo
 * @author Alessandro Valbonesi
 * @author gac
 * @version 1.0  /  21 agosto 2003 ore 16.41
 */
public final class CDBSub extends CDBBase {

    //-------------------------------------------------------------------------
    // Costanti statiche della classe    (private,pubbliche)  (class variables)
    //-------------------------------------------------------------------------
    /**
     * abilita alcuni messaggi di debug
     */
    private static final boolean DEBUG = false;

    //-------------------------------------------------------------------------
    // Variabili statiche della classe         (private)      (class variables)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Riferimenti ad altri oggetti    (private,protette)  (instance variables)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Variabili degli oggetti (private,protette,pubbliche)(instance variables)
    //-------------------------------------------------------------------------
//    /** modulo del campo sub lista */
//    private Modulo unModuloSub = null;

    /**
     * Nome interno del campo del modulo sub che ha una relazione molti a uno
     * con il campo di testa (deve obbligatoriamente essere un campo linkato)
     */
    private String unNomeCampoLinkRighe = null;

    /**
     * Campo chiave nella tavola del modulo che gestisce il campo sub
     */
    private Campo unCampoSubTesta = null;

    /**
     * Campo del modulo sub che ha una relazione molti a uno
     * con il campo di testa
     */
    private Campo unCampoSubRighe = null;

    /**
     * nome della Vista (serie di campi di un'altro modello)
     * che vengono visualizzati nella lista
     */
    private String unNomeVistaSub = null;


    //-------------------------------------------------------------------------
    // Costruttori della classe                                   constructors)
    //-------------------------------------------------------------------------
    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo utilizzando eventuali valori di default
     */
    public CDBSub() {
        /** rimanda al costruttore di questa classe */
        this(null);
    } /* fine del metodo costruttore base */


    /**
     * Costruttore completo
     *
     * @param unCampoParente campo 'contenitore' di questo oggetto
     */
    public CDBSub(Campo unCampoParente) {
        /** rimanda al costruttore della superclasse */
        super(unCampoParente);

        /** regolazioni iniziali di riferimenti e variabili */
        try {                                   // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore completo */


    //-------------------------------------------------------------------------
    // Metodi statici della classe                              (class methods)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi privati chiamati dal costruttore prima di tornare alla sottoclasse
    //-------------------------------------------------------------------------
    /**
     * Regolazioni iniziali di riferimenti e variabili
     * Metodo chiamato direttamente dal costruttore
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        /** il campo sub non e' fisico */
        this.setCampoFisico(false);

        /** il campo sub non e' visibile in lista */
        this.getCampoParente().setVisibileVistaDefault(false);

        /** di default usa la vista default del modulo linkato */
        this.setNomeVistaSub(CostanteModulo.VISTA_BASE_DEFAULT);

    } /* fine del metodo inizia */

    //-------------------------------------------------------------------------
    // Metodi protetti chiamati dalla sottoclasse subito dopo il costruttore
    //-------------------------------------------------------------------------

    /* Tenta di inizializzare il campo.
    * @return true se il campo e' stato inizializzato.
    */


    public void inizializza() {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = true;
        Modulo unModuloTesta = null;
        Modulo unModuloRighe = null;
        Campo unCampoPuntatore = null;  // campo di righe
        Campo unCampoPuntato = null;    // campo di testa
        String unaTavolaTesta = null;
        String unaTavolaPuntata = null;
        boolean continua = true;
        String errore = "";

        /** invoca il metodo sovrascritto della superclasse */
        super.inizializza();

        /** esegue ulteriori inizializzazioni specifiche del campoDBSub */
        try {    // prova ad eseguire il codice

            /* ricava alcuni riferimenti di base */
            if (continua) {
                /** ricava il modulo testa (il modulo che gestisce questo campo)*/
                unModuloTesta = this.getCampoParente().getModulo();
                /** ricava il modulo righe */
                unModuloRighe = this.getModuloSub();
                /** ricava il campo puntatore */
                unCampoPuntatore =
                        unModuloRighe.getModello().getCampo(this.getNomeCampoLinkRighe());
            }// fine del blocco if

            /* controlla che il campo puntatore sia un campo linkato */
            if (continua) {
                if (unCampoPuntatore.getCampoDB().isLinkato() == false) {
                    throw new Exception("Il campo del modulo Righe non ha relazioni");
                }// fine del blocco if
            }// fine del blocco if

//           /* controlla che il campo puntatore sia inizializzato */
//           if (continua) {
//               if (unCampoPuntatore.isInizializzato() == false) {
//                   errore = "Il campo puntatore non e' inizializzato";
//                   continua = false;
//               }// fine del blocco if
//           }// fine del blocco if

            /* ricava il campo puntato (il campo della testa al quale
       * il campo puntatore punta */
            if (continua) {
                unCampoPuntato = unCampoPuntatore.getCampoDB().getCampoLinkChiave();
            }// fine del blocco if

//           /* controlla che il campo puntato sia inizializzato */
//           if (continua) {
//               if (unCampoPuntato.isInizializzato() == false) {
//                   errore = "Il campo puntato non e' inizializzato";
//                   continua = false;
//               }// fine del blocco if
//           }// fine del blocco if

            /* ricava i nomi della tavola puntata e della tavola testa */
            if (continua) {
//               unaTavolaPuntata
//                       = unCampoPuntato.getCampoDB().getNomeTavolaSql();
                unaTavolaTesta = unModuloTesta.getModello().getTavolaArchivio();
            }// fine del blocco if

            /* controlla che il campo puntato appartenga alla tavola testa */
            if (continua) {
                if (unaTavolaPuntata.equalsIgnoreCase(unaTavolaTesta) == false) {
                    throw new Exception(
                            "Il campo del modulo Righe non punta al modulo del campo sub");
                }// fine del blocco if
            }// fine del blocco if

            /* regola i riferimenti definitivi */
            if (continua) {
                /* regola i riferimenti al campo sub testa
          * e al campo sub righe */
                this.setCampoSubTesta(unCampoPuntato);
                this.setCampoSubRighe(unCampoPuntatore);
                /** registra nel modulo righe il riferimento
                 *  al modulo chiamante */
                unModuloRighe.setModuloChiamante(unModuloTesta);
            }// fine del blocco if

//           /* se non e' riuscito imposta come non inizializzato
//            * e mostra un errore */
//           if (continua == false) {
//               super.setInizializzato(false);
//               riuscito = false;
//
//               /* se debug abilitato mostra l'errore */
//               if (DEBUG) {
//                   throw new Exception(errore);
//               }// fine del blocco if
//
//           }// fine del blocco if

//            /** controlla che il campo puntatore sia un campo linkato */
//            if (unCampoPuntatore.getCampoDB().isLinkato()) {
//
//                /** ricava il campo puntato (il campo della testa al quale
//                 *  il campo puntatore punta */
//                unCampoPuntato = unCampoPuntatore.getCampoDB().getCampoLinkChiave();
//
//                /** ricava il nome della tavola puntata e della tavola testa */
//                unaTavolaPuntata = unCampoPuntato.getCampoDB().getNomeTavolaSql();
//                unaTavolaTesta = unModuloTesta.getModello().getTavolaArchivio();
//
//                /** controlla che il campo puntato sia sulla tavola testa */
//                if (unaTavolaPuntata.equalsIgnoreCase(unaTavolaTesta)) {
//
//                    /** regola i riferimenti al campo sub testa
//                     *  e al campo sub righe */
//                    this.setCampoSubTesta(unCampoPuntato);
//                    this.setCampoSubRighe(unCampoPuntatore);
//
//                    /** registra nel modulo righe il riferimento
//                     *  al modulo chiamante */
//                    unModuloRighe.setModuloChiamante(unModuloTesta);
//
//                } else {
//                    unTestoAggiuntoErrore = "Il campo del modulo Righe non punta al modulo del campo sub";
//                    throw new Exception();
//                } /* fine del blocco if/else */
//
//            } else {
//                unTestoAggiuntoErrore = "Il campo del modulo Righe non ha relazioni";
//                throw new Exception();
//            } /* fine del blocco if/else */

        } catch (Exception unErrore) {    // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

    } /* fine del metodo */


    //-------------------------------------------------------------------------
    // Metodi privati degli oggetti (usati nella classe)     (instance methods)
    //-------------------------------------------------------------------------
    /**
     * ritorna il modulo sub
     */
    private Modulo getModuloSub() {
        return this.getCampoParente().getCampoLogica().getModuloInterno();
    } /* fine del metodo getter */


    //-------------------------------------------------------------------------
    // Metodi protetti degli oggetti (usati nelle sottoclassi)    (instance m.)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi che verranno sovrascritti nelle sottoclassi          (overriding)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi che sovrascrivono metodi della superclasse           (overriding)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi pubblici degli oggetti (usati in altre classi)      (instance m.)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi di regolazione delle variabili locali                    (setter)
    //-------------------------------------------------------------------------
    /**
     * metodo setter per modificare il valore della variabile privata
     */
    private void setCampoSubTesta(Campo unCampoSubTesta) {
        this.unCampoSubTesta = unCampoSubTesta;
    } /* fine del metodo setter */


    /**
     * metodo setter per modificare il valore della variabile privata
     */
    private void setCampoSubRighe(Campo unCampoSubRighe) {
        this.unCampoSubRighe = unCampoSubRighe;
    } /* fine del metodo setter */


    /**
     * metodo setter per modificare il valore della variabile privata
     */
    public void setNomeVistaSub(String unNomeVistaSub) {
        this.unNomeVistaSub = unNomeVistaSub;
    } /* fine del metodo setter */


    /**
     * metodo setter per modificare il valore della variabile privata
     */
    public void setNomeCampoLinkRighe(String unNomeCampoLinkRighe) {
        this.unNomeCampoLinkRighe = unNomeCampoLinkRighe;
    } /* fine del metodo setter */


    //-------------------------------------------------------------------------
    // Metodi di restituzione delle variabili locali                   (getter)
    //-------------------------------------------------------------------------
    /**
     * ritorna il campo testa
     */
    public Campo getCampoSubTesta() {
        return this.unCampoSubTesta;
    } /* fine del metodo getter */


    /**
     * ritorna il campo righe
     */
    public Campo getCampoSubRighe() {
        return this.unCampoSubRighe;
    } /* fine del metodo getter */


    /**
     * ritorna il nome del campo linkato del modulo Sub
     */
    public String getNomeCampoLinkRighe() {
        return this.unNomeCampoLinkRighe;
    } /* fine del metodo getter */


    /**
     * ritorna il nome della vista del modulo sub utilizzata dal campo
     */
    public String getNomeVistaSub() {
        return this.unNomeVistaSub;
    } /* fine del metodo getter */


    /**
     * ritorna la vista del modulo sub utilizzata dal campo
     */
    public Vista getVistaSub() {
        /** variabili e costanti locali di lavoro */
        Modulo unModuloSub = null;
        String unNomeVistaSub = "";
        Vista unaVistaSub = null;

        try {
            // prova ad eseguire il codice
            unModuloSub = this.getModuloSub();
            unNomeVistaSub = this.getNomeVistaSub();
            unaVistaSub = unModuloSub.getModello().getVista(unNomeVistaSub);
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        return unaVistaSub;
    } /* fine del metodo getter */
    //-------------------------------------------------------------------------
    // Metodi di gestione interni, invocati dai metodi azione     (instance m.)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi azione                                         (instance methods)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Classi azione interne degli eventi                         (inner class)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
}// fine della classe it.algos.base.campo.db.CDBSub.java
//-----------------------------------------------------------------------------

