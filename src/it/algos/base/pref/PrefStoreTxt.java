/**
 * Copyright: Copyright (c) 2006
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      15-feb-2007
 */
package it.algos.base.pref;

import it.algos.base.config.Config;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.progetto.Progetto;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * //@TODO DESCRIZIONE SINTETICA DELLA CLASSE .
 * </p>
 * Questa classe: <ul>
 * <li> </li>
 * <li> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 15-feb-2007 ore 15.43.04
 */
public final class PrefStoreTxt extends PrefStore {

    private String cartella = Lib.Sist.getDirPreferenze();

    private static final String SUF_NET = "_shared.txt";

    private static final String SUF_LOC = "Pref.txt";


    /**
     * Costruttore completo
     * <p/>
     *
     * @param comune true se store comune false se locale
     */
    public PrefStoreTxt(boolean comune) {
        /* rimanda al costruttore della superclasse */
        super(comune);

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
    }// fine del metodo inizia


    /**
     * Carica le preferenze locali.
     * <p/>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @return vero se il caricamento è riuscito
     */
    @Override
    public boolean carica() {
        /* variabili e costanti locali di lavoro */
        boolean caricato = false;
        boolean continua;
        String tag = "=";
        String sigla;
        String valStr;
        ArrayList<String> lista;
        int pos;

        try { // prova ad eseguire il codice

            lista = this.getLista();
            continua = (lista != null);

            if (continua) {
                /* traverso tutta la collezione */
                for (String riga : lista) {
                    pos = riga.indexOf(tag);

                    if (pos != -1) {
                        sigla = riga.substring(0, pos).trim();
                        valStr = riga.substring(pos + 1).trim();
                        this.caricaSingola(sigla, valStr);
                    }// fine del blocco if

                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return caricato;
    }


    /**
     * Carica la singola preferenza.
     * <p/>
     * Recupera la preferenza <br>
     * Regola il valore corrente <br>
     *
     * @param preferenza gruppo più nome
     * @param valStr stringa del valore
     */
    private void caricaSingola(String preferenza, String valStr) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        PrefWrap wrap = null;
        Pref.TipoDati tipo = null;
        Object valore = null;
        ArrayList<String> lista;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = Lib.Testo.isValida(valStr);

            if (continua) {
                wrap = this.getWrapper(preferenza);
                continua = (wrap != null);
            }// fine del blocco if

            if (continua) {
                tipo = wrap.getTipoDati();
                continua = (tipo != null);
            }// fine del blocco if

            if (continua) {
                valore = this.getValorePref(tipo, valStr);
                continua = (valore != null);
            }// fine del blocco if

            if (continua) {
                if (tipo == Pref.TipoDati.stringa) {
                    lista = Lib.Array.creaLista(valStr);
                    valore = lista.get(0);
                    wrap.setValoriUsati(lista);
                }// fine del blocco if

                wrap.setValore(valore);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
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
        /* variabili e costanti locali di lavoro */
        Object valore = null;
        boolean continua;
        String tag = "=";
        String sigla;
        String valStr;
        ArrayList<String> lista;
        int pos;
        PrefWrap wrap;
        Pref.TipoDati tipo;

        try { // prova ad eseguire il codice

            lista = this.getLista();
            continua = (lista != null);

            if (continua) {
                /* traverso tutta la collezione */
                for (String riga : lista) {
                    pos = riga.indexOf(tag);

                    if (pos != -1) {
                        sigla = riga.substring(0, pos).trim();
                        valStr = riga.substring(pos + 1).trim();

                        if (sigla.equals(preferenza)) {
                            wrap = this.getWrapper(preferenza);
                            tipo = wrap.getTipoDati();
                            valore = this.getValorePref(tipo, valStr);
                            break;
                        }// fine del blocco if

                    }// fine del blocco if

                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valore;
    }


    /**
     * Ritorna la lista di valori testo delle preferenze.
     * <p/>
     *
     * @return la lista dei valori
     */
    private ArrayList<String> getLista() {
        /* variabili e costanti locali di lavoro */
        ArrayList<String> lista = null;
        boolean continua;
        String nomeFile;
        String testo = "";
        String[] parti = null;

        try { // prova ad eseguire il codice
            nomeFile = this.getNomeFile();
            continua = Lib.Testo.isValida(nomeFile);

            if (continua) {
                testo = Lib.File.legge(nomeFile);
                continua = Lib.Testo.isValida(testo);
            }// fine del blocco if

            if (continua) {
                parti = testo.split("\n");
                continua = ((parti != null) && (parti.length > 0));
            }// fine del blocco if

            if (continua) {
                lista = Lib.Array.creaLista(parti);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }


    /**
     * Registra le preferenze locali.
     * <p/>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @return vero se la registrazione è stata effettuata
     */
    @Override
    public boolean registra() {
        /* variabili e costanti locali di lavoro */
        boolean registrato = false;
        boolean registro;
        String nomeFile;
        String valTesto;
        StringBuffer buffer;
        String testo;
        LinkedHashMap<String, PrefGruppi> preferenze;
        PrefWrap prefWrap;
        String chiave;
        PrefGruppi pref;

        try { // prova ad eseguire il codice

            nomeFile = this.getNomeFile();
            buffer = new StringBuffer();
            preferenze = Pref.getPref();

            /* traverso tutta la collezione */
            /* registro SOLO le preferenze con valore corrente diverso da quello di default */
            /* traverso tutta la collezione */
            for (Map.Entry map : preferenze.entrySet()) {
                chiave = (String)map.getKey();
                pref = (PrefGruppi)map.getValue();

                prefWrap = pref.getWrap();

                registro = this.isDaRegistrare(chiave, pref);

                if (registro) {
                    valTesto = this.getValoreDaRegistrare(prefWrap);
                    buffer.append("\n");
                    buffer.append(chiave);
                    buffer.append("=");
                    buffer.append(valTesto);
                }// fine del blocco if
            } // fine del ciclo for-each

            testo = buffer.toString().trim();

            /* registra il file */
            /* se non c'è nulla da scrivere, cancella il precedente file,
             * invece che scriverlo vuoto */
            if (Lib.Testo.isValida(testo)) {
                Lib.File.sovrascrive(nomeFile, testo);
            } else {
                Lib.File.cancella(nomeFile);
            }// fine del blocco if-else

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return registrato;
    }


    /**
     * Nome del file di preferenze.
     * <p/>
     *
     * @return nome del file
     */
    public String getNomeFile() {
        /* variabili e costanti locali di lavoro */
        String nomeFile = "";
        String programma;
        Object val;
        String nomeFilePref;
        String chiave;

        try { // prova ad eseguire il codice

            if (this.isComune()) {
                chiave = Config.INDIRIZZO_FILE_PREF_SHARED;
                val = Config.getValore(chiave);

                nomeFilePref = Lib.Testo.getStringa(val);

                if (Lib.Testo.isVuota(nomeFilePref)) {
                    /* recupera il nome del programma in esecuzione */
                    programma = Progetto.getNomePrimoModulo().toLowerCase();

                    /* aggiunge cartella  esuffisso */
                    nomeFile = cartella + "/" + programma + SUF_NET;
                } else {
                    nomeFile = nomeFilePref;
                }// fine del blocco if-else
            } else {
                chiave = Config.INDIRIZZO_FILE_PREF_LOCAL;
                val = Config.getValore(chiave);

                nomeFilePref = Lib.Testo.getStringa(val);

                if (Lib.Testo.isVuota(nomeFilePref)) {
                    /* recupera il nome del programma in esecuzione */
                    programma = Progetto.getNomePrimoModulo().toLowerCase();

                    /* aggiunge cartella  esuffisso */
                    nomeFile = cartella + "/" + programma + SUF_LOC;
                } else {
                    nomeFile = nomeFilePref;
                }// fine del blocco if-else
            }// fine del blocco if-else


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return nomeFile;
    }

}// fine della classe
