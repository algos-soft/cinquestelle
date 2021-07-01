/**
 * Copyright: Copyright (c) 2006
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      15-feb-2007
 */
package it.algos.base.pref;

import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;

import java.util.ArrayList;

/**
 * Gestore della lettura e registrazione di un gruppo di preferenze
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 15-feb-2007 ore 15.41.17
 */
public abstract class PrefStore {

    /* true se store di preferenze comuni false se store di preferenze locali */
    private boolean comune;


    /**
     * Costruttore completo senza parametri.
     *
     * @param comune true se store comune false se locale
     */
    public PrefStore(boolean comune) {
        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice
            /* regola le variabili di istanza coi parametri */
            this.setComune(comune);

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
    }// fine del metodo inizia


    /**
     * Inizializza l'oggetto.
     */
    public void inizializza() {
    }


    /**
     * Carica le preferenze.
     * <p/>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @return vero se il caricamento è riuscito
     */
    public boolean carica() {
        return false;
    }


    /**
     * Registra le preferenze.
     * <p/>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @return vero se la registrazione è stata effettuata
     */
    public boolean registra() {
        return false;
    }


    /**
     * Controlla se una preferenza è da registrare nell'archivio
     * <p/>
     * A) Se la preferenza è comune, è da registrare se il valore corrente
     * è diverso dal valore di default
     * <p/>
     * B) Se la preferenza è locale:
     * b1) se esiste un valore comune, è da registrare se il valore corrente
     * è diverso dal valore comune
     * b2) se non esiste un valore comune, è da registrare se il valore corrente
     * è diverso dal valore di default
     *
     * @param chiave della preferenza
     * @param pref   oggetto preferenza
     *
     * @return true se la preferenza va registrata
     */
    protected boolean isDaRegistrare(String chiave, PrefGruppi pref) {
        /* variabili e costanti locali di lavoro */
        boolean registra = false;
        boolean continua;
        PrefStore storeComuni;
        Object valDefault = null;
        Object valCurr;
        Object valComune;

        try { // prova ad eseguire il codice

            valCurr = pref.getWrap().getValore();
            continua = (valCurr != null);

            if (continua) {
                valDefault = pref.getWrap().getStandard();
                continua = (valDefault != null);
            }// fine del blocco if

            if (continua) {
                if (this.isComune()) {
                    registra = !valCurr.equals(valDefault);
                } else {
                    storeComuni = Pref.getStoreComuni();
                    valComune = storeComuni.get(chiave);
                    if (valComune != null) {    //c'è sulle comuni
                        registra = !valCurr.equals(valComune);
                    } else {   // non c'è sulle comuni
                        registra = !valCurr.equals(valDefault);
                    }// fine del blocco if-else
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore, chiave);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return registra;
    }


    /**
     * Valore da registrare.
     * <p/>
     * Registra valori multipli, se la preferenza ha il flag regolato a vero <br>
     * Se la preferenza è di tipo testo, possono esserci dei valori precedentemente utilizzati <br>
     * Viene creata una lista coi valori precedenti, più il valore richiesto messo per primo <br>
     * Se manca il valore di default alla lista, lo aggiunge <br>
     *
     * @param wrap wrapper di informazioni della preferenza
     *
     * @return stringa di valori separati da virgola
     */
    protected String getValoreDaRegistrare(PrefWrap wrap) {
        /* variabili e costanti locali di lavoro */
        String valTesto = "";
        boolean continua;
        ArrayList<String> lista = null;
        String valDefault;
        Pref.TipoDati tipo;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (wrap != null);

            /* controllo del flag */
            if (continua) {
                valTesto = wrap.toString();
                continua = wrap.isUsaValoriMultipli();
            }// fine del blocco if

            /* valore selezionato */
            if (continua) {
                tipo = wrap.getTipoDati();
                continua = (tipo == Pref.TipoDati.stringa);
            }// fine del blocco if

            /* recupera la (eventuale) lista di valori precedentemente utilizzati */
            if (continua) {
                valTesto = wrap.toString();
                lista = wrap.getValoriUsati();
            }// fine del blocco if

            /* aggiunge (se manca) il valore di default */
            if (continua) {
                valDefault = wrap.getStandard().toString();
                if (Lib.Testo.isValida(valDefault)) {
                    if (lista == null) {
                        lista = new ArrayList<String>();
                    }// fine del blocco if

                    if (!lista.contains(valDefault)) {
                        lista.add(valDefault);
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

            /* mette sempre in testa il valore selezionato */
            if (continua) {
                if (lista != null) {
                    lista = Lib.Array.ordinaPrimo(lista, valTesto);
                    valTesto = Lib.Testo.getStringa(lista);
                }// fine del blocco if-else
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valTesto;
    }


    /**
     * Converte una stringa nel valore adeguato a una preferenza.
     * <p/>
     *
     * @param tipo   di dati per la convesrione
     * @param valStr da convertire
     *
     * @return valore convertito
     */
    protected Object getValorePref(Pref.TipoDati tipo, String valStr) {
        /* variabili e costanti locali di lavoro */
        Object valore = null;

        try {    // prova ad eseguire il codice

            switch (tipo) {
                case stringa:
                    valore = valStr;
                    break;
                case intero:
                    if (Lib.Testo.isValida(valStr)) {
                        valore = new Integer(valStr); //@todo controllare errori
                    } else {
                        valore = 0;
                    }// fine del blocco if-else
                    break;
                case doppio:
                    if (Lib.Testo.isValida(valStr)) {
                        valore = new Double(valStr); //@todo controllare errori
                    } else {
                        valore = 0.0;
                    }// fine del blocco if-else
                    break;
                case booleano:
                    if (valStr.equals("true")) { //@todo controllare altre possibili scritture
                        valore = true;
                    } else {
                        valore = false;
                    }// fine del blocco if-else
                    break;
                case data:
                    valore = Lib.Data.getData(valStr);
                    break;
                case combo:
                    if (Lib.Testo.isValida(valStr)) {
                        valore = new Integer(valStr);
                    } else {
                        valore = 1;
                    }// fine del blocco if-else
                    break;
                case radio:
                    if (Lib.Testo.isValida(valStr)) {
                        valore = new Integer(valStr);
                    } else {
                        valore = 1;
                    }// fine del blocco if-else
                    break;
                case area:
                    valStr = valStr.replaceAll(PrefWrap.SEP_AREA, "\\\n");
                    valore = valStr;
                    break;
                default: // caso non definito
                    break;
            } // fine del blocco switch

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return valore;
    }


    /**
     * Ritorna il wrapper corrispondente a una preferenza.
     * <p/>
     *
     * @param preferenza chiave della preferenza
     *
     * @return il wrapper
     */
    protected PrefWrap getWrapper(String preferenza) {
        /* variabili e costanti locali di lavoro */
        PrefWrap wrap = null;
        boolean continua;
        String nomeGruppo = "";
        String nomePref = "";
        String tag = ".";
        int pos = 0;
        PrefGruppi pref = null;

        try {    // prova ad eseguire il codice

            /* controllo di congruità */
            continua = Lib.Testo.isValida(preferenza);

            if (continua) {
                pos = preferenza.lastIndexOf(tag);
                continua = (pos != -1);
            }// fine del blocco if

            if (continua) {
                nomeGruppo = preferenza.substring(0, pos).trim();
                nomePref = preferenza.substring(pos + 1).trim();
                continua = (Lib.Testo.isValida(nomeGruppo) && Lib.Testo.isValida(nomePref));
            }// fine del blocco if

            if (continua) {
                pref = Pref.getPref(nomeGruppo, nomePref);
                continua = (pref != null);
            }// fine del blocco if

            if (continua) {
                wrap = pref.getWrap();
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return wrap;
    }


    /**
     * Ritorna il valore corrispondente a una preferenza.
     * <p/>
     *
     * @param preferenza chiave della preferenza
     *
     * @return valore convertito
     */
    public Object get(String preferenza) {
        return null;
    }


    /**
     * Chiude lo store.
     * <p/>
     */
    public void close() {
    }


    protected boolean isComune() {
        return comune;
    }


    private void setComune(boolean comune) {
        this.comune = comune;
    }

}// fine della classe
