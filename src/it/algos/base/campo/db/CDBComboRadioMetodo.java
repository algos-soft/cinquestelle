/**
 * Title:     CDBComboRadioMetodo
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      15-giu-2004
 */
package it.algos.base.campo.db;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.modulo.Modulo;
import it.algos.base.progetto.Progetto;

import java.lang.reflect.Method;

/**
 * </p>
 * Questa classe: <ul>
 * Implementa il modello dati per un campo Combo o Radio <br>
 * che acquisisce la propria lista valori da un metodo.<p>
 * </ul>
 *
 * @author Alberto Colombo, Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 15-giu-2004 ore 15.08.52
 */
public final class CDBComboRadioMetodo extends CDBBase {

    /**
     * Modulo che implementa il metodo fornitore dei valori
     */
    private String nomeModuloValori = null;

    /**
     * nome del metodo fornitore dei valori
     */
    private String nomeMetodoValori = null;

    /**
     * Istanza del modulo fornitore dei valori
     * (determinato in fase di inizializzazione)
     */
    private Modulo istanzaModuloValori = null;

    /**
     * Istanza del metodo fornitore dei valori
     * (determinato in fase di inizializzazione)
     */
    private Method istanzaMetodoValori = null;


    /**
     * Costruttore base senza parametri. <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo <br>
     * Utilizza eventuali valori di default <br>
     */
    public CDBComboRadioMetodo() {
        /* rimanda al costruttore di questa classe */
        this(null);
    }// fine del metodo costruttore base


    /**
     * Costruttore completo con parametri. <br>
     *
     * @param unCampoParente campo 'contenitore' di questo oggetto
     */
    public CDBComboRadioMetodo(Campo unCampoParente) {
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
     * Regolazioni immediate di riferimenti e variabili. <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
    }// fine del metodo inizia


    /**
     * Inizializzazione
     */
    public void inizializza() {
        /* variabili e costanti locali di lavoro */
        boolean continua = false;
        String errore = null;
        String messaggio = null;
        Campo unCampo = null;
        String nomeModulo = null;
        String nomeCampo = null;
        Class classe = null;
        Method metodo = null;
        Class[] parametri = null;
        Modulo modulo = null;
        Object[] arrayOggetti = null;

        /* invoca il metodo sovrascritto della superclasse */
        super.inizializza();

        /* esegue le inizializzazioni specifiche di questa classe */
        try {    // prova ad eseguire il codice

            continua = true;

            /* verifica se il nome del modulo valori e' stato specificato
             * se non e' specificato usa il modulo stesso del campo */
            if (continua) {
                if (Lib.Testo.isValida(this.getNomeModuloValori()) == false) {
                    modulo = this.getCampoParente().getModulo();
                    nomeModulo = modulo.getNomeChiave();
                    this.setNomeModuloValori(nomeModulo);
                }// fine del blocco if
            }// fine del blocco if

            /* verifica se il nome del metodo valori e' stato specificato
             * se non e' specificato usa il nome stesso del campo */
            if (continua) {
                if (Lib.Testo.isValida(this.getNomeMetodoValori()) == false) {
                    nomeCampo = this.getCampoParente().getNomeInterno();
                    this.setNomeMetodoValori(nomeCampo);
                }// fine del blocco if
            }// fine del blocco if

            /* verifica se nel progetto esiste il modulo fornitore di valori */
            if (continua) {
                if (Progetto.isEsisteModulo(this.getNomeModuloValori()) == false) {
                    errore = "non esiste il modulo fornitore di valori: ";
                    errore += this.getNomeModuloValori();
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            /* recupera il modulo che implementa il metodo fornitore di valori */
            if (continua) {
                modulo = Progetto.getModulo(this.getNomeModuloValori());
                this.setIstanzaModuloValori(modulo);
            }// fine del blocco if

            /* recupera il metodo fornitore di valori dal modulo */
            if (continua) {
                classe = this.getIstanzaModuloValori().getClass();
                parametri = new Class[0]; // cerca un metodo senza parametri
                try {    // prova ad eseguire il codice
                    metodo = classe.getMethod(this.getNomeMetodoValori(), parametri);
                } catch (NoSuchMethodException unErrore) {    // intercetta l'errore
                } // fine del blocco try-catch

                /* se il metodo non esiste, la variabile 'metodo' e' nulla */
                if (metodo == null) {
                    errore = "non esiste il metodo fornitore di valori: ";
                    errore += this.getNomeMetodoValori() + "\n";
                    errore += "nel modulo " + this.getNomeModuloValori();
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            /* controlla che tipo di ritorno del metodo sia un Object[] */
            if (continua) {
                arrayOggetti = new Object[0];
                classe = metodo.getReturnType();
                if (classe != arrayOggetti.getClass()) {
                    errore = "il metodo ";
                    errore += this.getNomeMetodoValori();
                    errore += " nel modulo " + this.getNomeModuloValori() + "\n";
                    errore += "non ritorna un Object[]";
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            /* registra il metodo fornitore di valori nel campo */
            if (continua) {
                this.setIstanzaMetodoValori(metodo);
            }// fine del blocco if

            /* in caso di errore solleva una eccezione */
            if (!continua) {
                if (Lib.Testo.isValida(errore)) {
                    unCampo = this.getCampoParente();
                    messaggio = "Modulo: " + unCampo.getModulo().getNomeChiave() + "\n";
                    messaggio += "Campo: " + unCampo.getNomeInterno() + "\n";
                    messaggio += "Problema: " + errore;
                    throw new Exception(messaggio);
                }// fine del blocco if
            }// fine del blocco if

            /* crea e/o regola il campo sul DB. */
            if (continua) {
                if (!super.allineaCampo()) {
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if-else


        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    } /* fine del metodo */


    public String getNomeModuloValori() {
        return nomeModuloValori;
    }


    public void setNomeModuloValori(String nomeModuloValori) {
        this.nomeModuloValori = nomeModuloValori;
    }


    public String getNomeMetodoValori() {
        return nomeMetodoValori;
    }


    public void setNomeMetodoValori(String nomeMetodoValori) {
        this.nomeMetodoValori = nomeMetodoValori;
    }


    public Modulo getIstanzaModuloValori() {
        return istanzaModuloValori;
    }


    private void setIstanzaModuloValori(Modulo istanzaModuloValori) {
        this.istanzaModuloValori = istanzaModuloValori;
    }


    public Method getIstanzaMetodoValori() {
        return istanzaMetodoValori;
    }


    private void setIstanzaMetodoValori(Method istanzaMetodoValori) {
        this.istanzaMetodoValori = istanzaMetodoValori;
    }
}// fine della classe
