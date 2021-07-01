/**
 * Copyright: Copyright (c) 2006
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      9-feb-2007
 */
package it.algos.base.libreria;

import it.algos.base.errore.Errore;
import it.algos.base.pref.Pref;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Classe astratta con metodi statici. </p> Questa classe astratta: <ul> <li>  </li> <li>  </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 9-feb-2007 ore 20.27.53
 */
public abstract class LibPref {

    /**
     * Recupera solo la parte terminale del nome canonico.
     * <p/>
     * Il nome viene restituito sempre minuscolo <br>
     *
     * @param nomeCanonico da elaborare/convertire
     *
     * @return nome elaborato
     */
    static String getNome(String nomeCanonico) {
        /* variabili e costanti locali di lavoro */
        String nomeFinale = "";
        String sep = ".";
        int pos;

        try { // prova ad eseguire il codice
            /* sicurezza */
            nomeFinale = nomeCanonico;

            /* ricerca l'ultimo punto separatore */
            pos = nomeCanonico.lastIndexOf(sep);

            if (pos != -1) {
                nomeFinale = nomeCanonico.substring(++pos);
                nomeFinale = nomeFinale.toLowerCase();
                nomeFinale = nomeFinale.trim();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return nomeFinale;
    }


    /**
     * Recupera un oggetto dalla mappa di parametri.
     * <p/>
     *
     * @param parametro nome chiave del parametro
     *
     * @return oggetto
     */
    private static Object getOggetto(String parametro) {
        /* variabili e costanti locali di lavoro */
        Object oggetto = null;
        LinkedHashMap<String, Object> mappa;
        boolean continua;

        try { // prova ad eseguire il codice
            mappa = Pref.getArgomenti();
            continua = mappa != null;

            if (continua) {
                oggetto = mappa.get(parametro);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return oggetto;
    }


    /**
     * Recupera una preferenza booleana.
     * <p/>
     *
     * @param parametro nome chiave del parametro
     *
     * @return oggetto booleano
     */
    static boolean getBool(String parametro) {
        /* variabili e costanti locali di lavoro */
        boolean valore = false;
        Object oggetto;
        boolean continua;

        try { // prova ad eseguire il codice
            oggetto = LibPref.getOggetto(parametro);
            continua = oggetto != null;

            if (continua) {
                if (oggetto instanceof Boolean) {
                    valore = (Boolean)oggetto;
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valore;
    }


    /**
     * Recupera un parametro intero.
     * <p/>
     *
     * @param parametro nome chiave del parametro
     *
     * @return oggetto intero
     */
    static int getInt(String parametro) {
        /* variabili e costanti locali di lavoro */
        int valore = 0;
        Object oggetto;
        boolean continua;

        try { // prova ad eseguire il codice

            oggetto = LibPref.getOggetto(parametro);
            continua = oggetto != null;

            if (continua) {
                if (oggetto instanceof Integer) {
                    valore = (Integer)oggetto;
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valore;
    }


    /**
     * Recupera un parametro stringa.
     * <p/>
     *
     * @param parametro nome chiave del parametro
     *
     * @return oggetto stringa
     */
    static String getStr(String parametro) {
        /* variabili e costanti locali di lavoro */
        String valore = "";
        Object oggetto;
        boolean continua;

        try { // prova ad eseguire il codice

            oggetto = LibPref.getOggetto(parametro);
            continua = oggetto != null;

            if (continua) {
                if (oggetto instanceof String) {
                    valore = (String)oggetto;
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valore;
    }


    /**
     * Recupera un argomento.
     * <p/>
     *
     * @param argomento nome chiave
     *
     * @return oggetto
     */
    static Object getArg(String argomento) {
        /* variabili e costanti locali di lavoro */
        Object oggetto = null;
        LinkedHashMap<String, Object> mappa;

        try { // prova ad eseguire il codice
            mappa = Pref.getArgomenti();

            if (mappa != null) {
                oggetto = mappa.get(argomento);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return oggetto;
    }


    /**
     * Recupera tutti gli argomenti.
     * <p/>
     *
     * @return testo di tutti gli argomenti nella forma chiave/valore per ogni riga
     */
    static String getArg() {
        /* variabili e costanti locali di lavoro */
        String testo = "";
        LinkedHashMap<String, Object> mappa;

        try { // prova ad eseguire il codice
            mappa = Pref.getArgomenti();

            if (mappa != null) {

                /* traverso tutta la collezione */
                for (Map.Entry map : mappa.entrySet()) {
                    testo += map.getKey();
                    testo += " = ";
                    testo += map.getValue();
                    testo += "\n";
                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return testo;
    }


    /**
     * Controlla l'esistenza di un qrgomento.
     * <p/>
     *
     * @return vero se esiste mnella mappa
     */
    static boolean isArg(String argomento) {
        /* variabili e costanti locali di lavoro */
        boolean esiste = false;
        LinkedHashMap<String, Object> mappa;

        try { // prova ad eseguire il codice
            mappa = Pref.getArgomenti();
            esiste = mappa.containsKey(argomento);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return esiste;
    }

}// fine della classe
