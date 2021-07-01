/**
 * Title:     LibArray
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      27-gen-2005
 */
package it.algos.base.libreria;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.wrapper.CinqueStringhe;
import it.algos.base.wrapper.DieciStringhe;
import it.algos.base.wrapper.DueStringhe;
import it.algos.base.wrapper.NoveStringhe;
import it.algos.base.wrapper.OttoStringhe;
import it.algos.base.wrapper.QuattroStringhe;
import it.algos.base.wrapper.SeiStringhe;
import it.algos.base.wrapper.SetteStringhe;
import it.algos.base.wrapper.TreStringhe;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Repository di funzionalità per la gestione degli Arrays.
 * <p/>
 * Tutti i metodi sono statici <br> I metodi non hanno modificatore così sono visibili all'esterno
 * del package solo utilizzando l'interfaccia unificata <b>Lib</b><br>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 27-gen-2005 ore 12.04.04
 */
public abstract class LibArray {

    private static final String SPAZIO = " ";

    /**
     * tag per la stringa virgola
     */
    private static final String VIRGOLA = ",";


    /**
     * Utility di conversione di una stringa.
     * <p/>
     * Crea una lista da un testo usando una stringa come separatore <br> Di solito la stringa è
     * sempre di 1 carattere <br> Elementi nulli o vuoti non vengono aggiunti alla lista <br>
     * Vengono eliminati gli spazi vuoti iniziali e finali <br> Se il separatore è vuoto o nullo,
     * restituisce una lista di un elemento uguale al testo ricevuto <br>
     *
     * @param testo da suddividere
     * @param sep carattere stringa di separazione
     *
     * @return una lista contenente le parti di stringa separate
     */
    static ArrayList<String> creaLista(String testo, String sep) {
        /* variabili e costanti locali di lavoro */
        ArrayList<String> lista = null;
        String[] array;

        try { // prova ad eseguire il codice
            /* protezione in ingresso */
            if (testo == null) {
                testo = "";
            }// fine del blocco if

            /* crea l'istanza di ritorno */
            lista = new ArrayList<String>();

            /* estrazione della matrice di stringhe */
            /* non uso Lib.Testo.isValida per gestire anche il tab \t */
            if ((sep != null) && (!sep.equals(" "))) {
                array = testo.split(sep);
            } else {
                array = new String[1];
                array[0] = testo;
            }// fine del blocco if-else

            /* spazzola la collezione */
            for (String stringa : array) {
                if (Lib.Testo.isValida(stringa)) {
                    lista.add(stringa.trim());
                }// fine del blocco if
            } // fine del ciclo for-each

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }


    /**
     * Utility di conversione di una stringa.
     * <p/>
     * Crea una lista da un testo usando una stringa come separatore <br> Di solito la stringa è
     * sempre di 1 carattere <br> Anche gli elementi vuoti vengono aggiunti alla lista <br> Non
     * vengono eliminati gli spazi vuoti iniziali e finali <br> Se il separatore è vuoto o nullo,
     * restituisce una lista di un elemento uguale al testo ricevuto <br>
     *
     * @param testo da suddividere
     * @param sep carattere stringa di separazione
     *
     * @return una lista contenente le parti di stringa separate
     */
    static ArrayList<String> creaListaDura(String testo, String sep) {
        /* variabili e costanti locali di lavoro */
        ArrayList<String> lista = null;
        String[] array;

        try { // prova ad eseguire il codice
            /* protezione in ingresso */
            if (testo == null) {
                testo = "";
            }// fine del blocco if

            /* crea l'istanza di ritorno */
            lista = new ArrayList<String>();

            /* estrazione della matrice di stringhe */
            /* non uso Lib.Testo.isValida per gestire anche il tab \t */
            if ((sep != null) && (!sep.equals(" "))) {
                array = testo.split(sep);
            } else {
                array = new String[1];
                array[0] = testo;
            }// fine del blocco if-else

            /* spazzola la collezione */
            for (String stringa : array) {
                lista.add(stringa);
            } // fine del ciclo for-each

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }


    /**
     * Utility di conversione di una stringa.
     * <p/>
     * Crea una lista da un testo usando un carattere separatore <br> Elementi nulli o vuoti non
     * vengono aggiunti alla lista <br> Vengono eliminati gli spazi vuoti iniziali e finali <br> Se
     * il separatore è vuoto o nullo, restituisce una lista di un elemento uguale al testo ricevuto
     * <br>
     *
     * @param testo da suddividere
     * @param sep carattere char di separazione
     *
     * @return una lista contenente le parti di stringa separate
     */
    static ArrayList<String> creaLista(String testo, char sep) {
        /* invoca il metodo sovrascritto (quasi) */
        return LibArray.creaLista(testo, String.valueOf(sep));
    }


    /**
     * Utility di conversione di una stringa.
     * <p/>
     * Crea una lista da un testo usando la virgola come separatore <br> Elementi nulli o vuoti non
     * vengono aggiunti alla lista <br> Vengono eliminati gli spazi vuoti iniziali e finali <br>
     *
     * @param testo da suddividere
     *
     * @return una lista contenente le parti di stringa separate
     */
    static ArrayList<String> creaLista(String testo) {
        /* variabili e costanti locali di lavoro */
        char sep = ',';

        /* invoca il metodo delegato della classe */
        return LibArray.creaLista(testo, sep);
    }


    /**
     * Converte un array di stringhe in una lista di stringhe.
     * <p/>
     * Esegue solo se l'array non è nullo <br>
     *
     * @param array da convertire
     *
     * @return lista di stringhe contenente gli elementi dell'array
     */
    static ArrayList<String> creaLista(String[] array) {
        /* variabili e costanti locali di lavoro */
        ArrayList<String> lista = null;
        boolean continua;

        try {    // prova ad eseguire il codice
            /* controllo di congruità all'ingresso */
            continua = (array != null);

            /* crea l'istanza */
            if (continua) {
                lista = new ArrayList<String>();
            }// fine del blocco if

            /* spazzola tutta la collezione */
            if (continua) {
                for (String stringa : array) {
                    lista.add(stringa);
                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }


    /**
     * Converte un array di interi in una lista di Integer.
     * <p/>
     * Esegue solo se l'array non è nullo <br>
     *
     * @param array da convertire
     *
     * @return lista di interi contenente gli elementi dell'array
     */
    static ArrayList<Integer> creaLista(int[] array) {
        /* variabili e costanti locali di lavoro */
        ArrayList<Integer> lista = null;
        boolean continua;

        try { // prova ad eseguire il codice
            /* controllo di congruità all'ingresso */
            continua = (array != null);

            /* crea l'istanza */
            if (continua) {
                lista = new ArrayList<Integer>();
            }// fine del blocco if

            /* spazzola tutta la collezione */
            if (continua) {
                for (Integer intero : array) {
                    lista.add(intero);
                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }


    /**
     * Crea una lista bidimensionale da un pacchetto di testo delimitato in righe e colonne.
     * <p/>
     * Usa \n per il delimitatore di record e \t per il delimitatore di campo Tutte le righe hanno
     * lo stesso numero di colonne, pari al numero di colonne della riga più lunga esistente nel
     * pacchetto analizzato.
     *
     * @param pacchetto di testo da analizzare
     *
     * @return la lista bidimensionale
     */
    static ArrayList creaLista2D(String pacchetto) {
        /* invoca il metodo sovrascritto della libreria specifica */
        return creaLista2D(pacchetto, "\t", "\n");
    }


    /**
     * Crea una lista bidimensionale da un pacchetto di testo delimitato in righe e colonne.
     * <p/>
     * Tutte le righe hanno lo stesso numero di colonne, pari al numero di colonne della riga più
     * lunga esistente nel pacchetto analizzato.
     *
     * @param pacchetto di testo da analizzare
     * @param delimCol il delimitatore di colonna
     * @param delimRow il delimitatore di riga
     *
     * @return la lista bidimensionale
     */
    static ArrayList creaLista2D(String pacchetto, String delimCol, String delimRow) {
        /* variabili e costanti locali di lavoro */
        ArrayList<Object> listaRighe = new ArrayList<Object>();
        ArrayList<String> listaCampi;
        String[] righe;
        String[] colonne;
        String riga;
        String colonna;
        int numColonne;
        int maxColonne = 0;
        ArrayList<Object> unaLista;
        int diff;

        try {    // prova ad eseguire il codice

            /* crea una lista di liste */
            if (pacchetto.length() > 0) {
                righe = pacchetto.split(delimRow);
                for (int k = 0; k < righe.length; k++) {

                    riga = righe[k];
                    colonne = riga.split(delimCol);

                    listaCampi = new ArrayList<String>();
                    for (int j = 0; j < colonne.length; j++) {
                        colonna = colonne[j];
                        listaCampi.add(colonna);
                    } // fine del ciclo for

                    listaRighe.add(listaCampi);

                    /* memorizza il numero massimo di colonne incontrato */
                    numColonne = listaCampi.size();
                    if (numColonne > maxColonne) {
                        maxColonne = numColonne;
                    }// fine del blocco if

                } // fine del ciclo for
            }// fine del blocco if

            /* spazzola tutte le righe e si assicura che abbiano
  * tutte il massimo numero di colonne, eventualmente
  * aggiungendo colonne vuote in coda alle righe più corte */
            for (Object unaRiga : listaRighe) {
                if (unaRiga instanceof ArrayList) {
                    unaLista = (ArrayList)unaRiga;
                    if (unaLista.size() < maxColonne) {
                        diff = maxColonne - unaLista.size();
                        for (int k = 0; k < diff; k++) {
                            unaLista.add("");
                        } // fine del ciclo for
                    }// fine del blocco if
                }// fine del blocco if
            }

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return listaRighe;
    }


    /**
     * Converte un array di interi in un array di Stringhe.
     * <p/>
     * Esegue solo se l'array non è nullo <br>
     *
     * @param array da convertire
     *
     * @return array di stringhe
     */
    static String[] intToString(int[] array) {
        /* variabili e costanti locali di lavoro */
        String[] stringhe = null;
        boolean continua;

        try { // prova ad eseguire il codice
            /* controllo di congruità all'ingresso */
            continua = (array != null);

            /* crea l'istanza */
            if (continua) {
                stringhe = new String[array.length];
            }// fine del blocco if

            /* spazzola tutta la collezione */
            if (continua) {

                for (int k = 0; k < array.length; k++) {
                    stringhe[k] = array[k] + "";
                } // fine del ciclo for
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return stringhe;
    }


    /**
     * Converte un array di booleani in una lista di Boolean.
     * <p/>
     * Esegue solo se l'array non è nullo <br>
     *
     * @param array da convertire
     *
     * @return lista di interi contenente gli elementi dell'array
     */
    static ArrayList<Boolean> creaLista(boolean[] array) {
        /* variabili e costanti locali di lavoro */
        ArrayList<Boolean> lista = null;
        boolean continua;

        try { // prova ad eseguire il codice
            /* controllo di congruità all'ingresso */
            continua = (array != null);

            /* crea l'istanza */
            if (continua) {
                lista = new ArrayList<Boolean>();
            }// fine del blocco if

            /* spazzola tutta la collezione */
            if (continua) {
                for (Boolean bool : array) {
                    lista.add(bool);
                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }


    /**
     * Converte un array di oggetti in una lista di Object.
     * <p/>
     * Esegue solo se l'array non è nullo <br>
     *
     * @param array da convertire
     *
     * @return lista di oggetti contenente gli elementi dell'array
     */
    static ArrayList<Object> creaLista(Object[] array) {
        /* variabili e costanti locali di lavoro */
        ArrayList<Object> lista = null;
        boolean continua;

        try { // prova ad eseguire il codice
            /* controllo di congruità all'ingresso */
            continua = (array != null);

            /* crea l'istanza */
            if (continua) {
                lista = new ArrayList<Object>();
            }// fine del blocco if

            /* spazzola tutta la collezione */
            if (continua) {
                for (Object oggetto : array) {
                    lista.add(oggetto);
                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }


    /**
     * Converte una lista di String in un array di stringhe.
     * <p/>
     * Esegue solo se la lista non è nulla e non è vuota <br>
     *
     * @param lista da convertire
     *
     * @return array di stringhe contenute nella lista
     */
    static String[] creaStrArray(ArrayList<String> lista) {
        /* variabili e costanti locali di lavoro */
        String[] array = null;
        boolean continua;
        String stringa;

        try { // prova ad eseguire il codice
            /* controllo di congruità all'ingresso */
            continua = (lista != null);
            if (continua) {
                continua = (lista.size() > 0);
            }// fine del blocco if

            /* crea l'istanza */
            if (continua) {
                array = new String[lista.size()];
            }// fine del blocco if

            /* spazzola tutta la collezione */
            if (continua) {
                for (int k = 0; k < lista.size(); k++) {
                    stringa = lista.get(k);
                    array[k] = stringa;
                } // fine del ciclo for
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return array;
    }


    /**
     * Converte una lista di Integer in un array di interi.
     * <p/>
     * Esegue solo se la lista non è nulla e non è vuota <br>
     *
     * @param lista da convertire
     *
     * @return array di interi contenuti nella lista
     */
    static int[] creaIntArray(ArrayList<Integer> lista) {
        /* variabili e costanti locali di lavoro */
        int[] array = new int[0];
        boolean continua;
        Integer intero;

        try { // prova ad eseguire il codice
            /* controllo di congruità all'ingresso */
            continua = (lista != null);
            if (continua) {
                continua = (lista.size() > 0);
            }// fine del blocco if

            /* crea l'istanza */
            if (continua) {
                array = new int[lista.size()];
            }// fine del blocco if

            /* spazzola tutta la collezione */
            if (continua) {
                for (int k = 0; k < lista.size(); k++) {
                    intero = lista.get(k);
                    array[k] = intero;
                } // fine del ciclo for
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return array;
    }


    /**
     * Confronto di due arrays di interi.
     * <p/>
     * Esegue solo se i due arrays non sono nulli <br>
     *
     * @param primo array di interi
     * @param secondo array di interi
     *
     * @return true se hanno le stesse dimensioni e gli stessi valori.
     */
    static boolean isUguali(int[] primo, int[] secondo) {
        /* variabili e costanti locali di lavoro */
        boolean uguali = false;
        boolean continua;

        try {    // prova ad eseguire il codice
            /* controllo di congruità all'ingresso */
            continua = ((primo != null) && (secondo != null));

            /* controllo delle dimensioni */
            if (continua) {
                continua = (primo.length == secondo.length);
            }// fine del blocco if

            /* controllo del contenuto */
            if (continua) {
                uguali = true;
                for (int k = 0; k < primo.length; k++) {
                    if (primo[k] != secondo[k]) {
                        uguali = false;
                        break;
                    }// fine del blocco if
                } // fine del ciclo for
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return uguali;
    }


    /**
     * Confronto di due arrays di stringhe.
     * <p/>
     * Esegue solo se i due arrays non sono nulli <br>
     *
     * @param primo array di stringhe
     * @param secondo array di stringhe
     *
     * @return true se hanno le stesse dimensioni e gli stessi valori.
     */
    static boolean isUguali(String[] primo, String[] secondo) {
        /* variabili e costanti locali di lavoro */
        boolean uguali = false;
        boolean continua;

        try {    // prova ad eseguire il codice
            /* controllo di congruità all'ingresso */
            continua = ((primo != null) && (secondo != null));

            /* controllo delle dimensioni */
            if (continua) {
                continua = (primo.length == secondo.length);
            }// fine del blocco if

            /* controllo del contenuto */
            if (continua) {
                uguali = true;
                for (int k = 0; k < primo.length; k++) {
                    if (!primo[k].equals(secondo[k])) {
                        uguali = false;
                        break;
                    }// fine del blocco if
                } // fine del ciclo for
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return uguali;
    }


    /**
     * Elimina da una lista i valori doppi.
     * <p/>
     * Esegue solo se la lista non è nulla e non è vuota <br>
     *
     * @param lista da convertire
     *
     * @return lista con valori univoci
     */
    static ArrayList<Object> listaUnica(ArrayList<Object> lista) {
        /* variabili e costanti locali di lavoro */
        ArrayList<Object> listaUscita = null;
        boolean continua;

        try {    // prova ad eseguire il codice
            /* controllo di congruità all'ingresso */
            continua = (lista != null);

            /* crea l'istanza */
            if (continua) {
                listaUscita = new ArrayList<Object>();
            }// fine del blocco if

            /* spazzola tutta la collezione */
            if (continua) {
                for (Object oggetto : lista) {
                    if (!listaUscita.contains(oggetto)) {
                        listaUscita.add(oggetto);
                    }// fine del blocco if
                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return listaUscita;
    }


    /**
     * Elimina da una lista i valori doppi.
     * <p/>
     * Esegue solo se la lista non è nulla e non è vuota <br>
     *
     * @param lista da convertire
     *
     * @return lista con valori univoci
     */
    static ArrayList<String> listaUnicaStr(ArrayList<String> lista) {
        /* variabili e costanti locali di lavoro */
        ArrayList<String> listaUscita = null;
        boolean continua;

        try {    // prova ad eseguire il codice
            /* controllo di congruità all'ingresso */
            continua = (lista != null);

            /* crea l'istanza */
            if (continua) {
                listaUscita = new ArrayList<String>();
            }// fine del blocco if

            /* spazzola tutta la collezione */
            if (continua) {
                for (String oggetto : lista) {
                    if (!listaUscita.contains(oggetto)) {
                        listaUscita.add(oggetto);
                    }// fine del blocco if
                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return listaUscita;
    }


    /**
     * Elimina da una lista i valori doppi.
     * <p/>
     * Esegue solo se la lista non è nulla e non è vuota <br>
     *
     * @param lista da convertire
     *
     * @return lista con valori univoci
     */
    static ArrayList<Integer> listaUnicaInt(ArrayList<Integer> lista) {
        /* variabili e costanti locali di lavoro */
        ArrayList<Integer> listaUscita = null;
        boolean continua;

        try {    // prova ad eseguire il codice
            /* controllo di congruità all'ingresso */
            continua = (lista != null);

            /* crea l'istanza */
            if (continua) {
                listaUscita = new ArrayList<Integer>();
            }// fine del blocco if

            /* spazzola tutta la collezione */
            if (continua) {
                for (Integer oggetto : lista) {
                    if (!listaUscita.contains(oggetto)) {
                        listaUscita.add(oggetto);
                    }// fine del blocco if
                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return listaUscita;
    }


    /**
     * Restituisce la posizione di un oggetto in un array di oggetti.
     * <p/>
     * ATTENZIONE - Gli array partono da 0 ! <br>
     *
     * @param valori l'array nel quale cercare
     * @param valore il singolo valore da cercare
     *
     * @return posizione del valore nell'array 0 la prima, -1 non trovato
     */
    static int getPosizione(Object[] valori, Object valore) {
        /* variabili e costanti locali di lavoro */
        int pos = -1;

        try {    // prova ad eseguire il codice
            for (int k = 0; k < valori.length; k++) {
                if (valori[k].toString().equals(valore.toString())) {
                    pos = k;
                    break;
                }// fine del blocco if
            } // fine del ciclo for
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return pos;
    }


    /**
     * Restituisce la posizione di un oggetto in un array di stringhe.
     * <p/>
     * ATTENZIONE - Gli array partono da 0 ! <br>
     *
     * @param valori l'array nel quale cercare
     * @param valore il singolo valore intero da cercare
     *
     * @return posizione del valore nell'array 0 la prima, -1 non trovato
     */
    static int getPosizione(String[] valori, String valore) {
        /* variabili e costanti locali di lavoro */
        int pos = -1;

        try {    // prova ad eseguire il codice
            for (int k = 0; k < valori.length; k++) {
                if (valori[k].equals(valore)) {
                    pos = k;
                    break;
                }// fine del blocco if
            } // fine del ciclo for
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return pos;
    }


    /**
     * Restituisce la posizione di un oggetto in un array di interi.
     * <p/>
     * ATTENZIONE - Gli array partono da 0 ! <br>
     *
     * @param valori l'array nel quale cercare
     * @param valore il singolo valore intero da cercare
     *
     * @return posizione del valore nell'array 0 la prima, -1 non trovato
     */
    static int getPosizione(int[] valori, int valore) {
        /* variabili e costanti locali di lavoro */
        int pos = -1;

        try {    // prova ad eseguire il codice
            for (int k = 0; k < valori.length; k++) {
                if (valori[k] == valore) {
                    pos = k;
                    break;
                }// fine del blocco if
            } // fine del ciclo for
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return pos;
    }


    /**
     * Restituisce la posizione di un oggetto in una lista.
     * <p/>
     * ATTENZIONE - Le liste partono da 1 ! <br>
     *
     * @param valori la lista nella quale cercare
     * @param valore il singolo valore da cercare
     *
     * @return posizione del valore nella lista 1 la prima, -1 non trovato
     */
    static int getPosizione(ArrayList<Object> valori, Object valore) {
        /* variabili e costanti locali di lavoro */
        int pos = -1;

        try {    // prova ad eseguire il codice
            for (int k = 0; k < valori.size(); k++) {
                if (valori.get(k) == valore) {
                    pos = k;
                    break;
                }// fine del blocco if
            } // fine del ciclo for
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* convenzionalmente numeriamo le liste partendo da 1 */
        if (pos != -1) {
            pos++;
        }// fine del blocco if

        /* valore di ritorno */
        return pos;
    }


    /**
     * Restituisce la posizione di un oggetto in una lista.
     * <p/>
     * ATTENZIONE - Le liste partono da 1 ! <br>
     *
     * @param valori la lista nella quale cercare
     * @param valore il singolo valore da cercare
     *
     * @return posizione del valore nella lista 1 la prima, -1 non trovato
     */
    static int getPosStr(ArrayList<String> valori, String valore) {
        /* variabili e costanti locali di lavoro */
        int pos = -1;

        try {    // prova ad eseguire il codice
            for (int k = 0; k < valori.size(); k++) {
                if (valori.get(k).equals(valore)) {
                    pos = k;
                    break;
                }// fine del blocco if
            } // fine del ciclo for
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* convenzionalmente numeriamo le liste partendo da 1 */
        if (pos != -1) {
            pos++;
        }// fine del blocco if

        /* valore di ritorno */
        return pos;
    }


    /**
     * Controlla se un dato valore esiste in un array di oggetti.
     * <p/>
     *
     * @param valori l'array nel quale cercare
     * @param valore il singolo valore da cercare
     *
     * @return true se esiste
     */
    static boolean isEsiste(Object[] valori, Object valore) {
        return (LibArray.getPosizione(valori, valore) != -1);
    }


    /**
     * Controlla se un dato valore esiste in un array di stringhe.
     * <p/>
     *
     * @param valori l'array nel quale cercare
     * @param valore il singolo valore da cercare
     *
     * @return true se esiste
     */
    static boolean isEsiste(String[] valori, String valore) {
        return (LibArray.getPosizione(valori, valore) != -1);
    }


    /**
     * Controlla se un dato valore esiste in un array di interi.
     * <p/>
     *
     * @param valori l'array nel quale cercare
     * @param valore il singolo valore intero da cercare
     *
     * @return true se esiste
     */
    static boolean isEsiste(int[] valori, int valore) {
        return (LibArray.getPosizione(valori, valore) != -1);
    }


    /**
     * Controlla se un dato valore esiste in una lista.
     * <p/>
     *
     * @param valori la lista nella quale cercare
     * @param valore il singolo valore da cercare
     *
     * @return true se esiste
     */
    static boolean isEsiste(ArrayList<Object> valori, Object valore) {
        return (LibArray.getPosizione(valori, valore) != -1);
    }


    /**
     * Controlla se un dato valore esiste in una lista.
     * <p/>
     *
     * @param valori la lista nella quale cercare
     * @param valore il singolo valore da cercare
     *
     * @return true se esiste
     */
    static boolean isEsisteStr(ArrayList<String> valori, String valore) {
        return (LibArray.getPosStr(valori, valore) != -1);
    }


    /**
     * Converte una stringa in un array di interi.
     * <p/>
     * Gli elementi della stringa possono essere separati da virgola o da spazio <br>
     *
     * @param stringa da convertire
     *
     * @return il corrispondente array di int
     */
    static int[] getArray(String stringa) {
        /* variabili e costanti locali di lavoro */
        int[] array = null;
        String[] num;
        String virgola;
        String spazio;

        try { // prova ad eseguire il codice
            virgola = LibArray.VIRGOLA;
            spazio = LibArray.SPAZIO;

            num = stringa.split(virgola);

            if (num.length > 1) {
                array = new int[num.length];
                for (int k = 0; k < num.length; k++) {
                    array[k] = Libreria.objToInt(num[k]);
                } // fine del ciclo for
            }// fine del blocco if

            num = stringa.split(spazio);

            if (num.length > 1) {
                array = new int[num.length];
                for (int k = 0; k < num.length; k++) {
                    array[k] = Libreria.objToInt(num[k]);
                } // fine del ciclo for
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return array;
    }


    /**
     * Converte una lista di dieci stringhe in lista di due stringhe.
     * <p/>
     * Per ogni istanza della lista, estrae la componente DueStringhe <br>
     *
     * @param listaIn da convertire
     *
     * @return il corrispondente array di DueStringhe
     */
    static ArrayList<DueStringhe> getDueStringhe(ArrayList<DieciStringhe> listaIn) {
        /* variabili e costanti locali di lavoro */
        ArrayList<DueStringhe> listaOut = null;
        boolean continua;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (listaIn != null && listaIn.size() > 0);

            if (continua) {
                listaOut = new ArrayList<DueStringhe>();
                for (DieciStringhe dieci : listaIn) {
                    listaOut.add(dieci.getDue());
                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return listaOut;
    }


    /**
     * Converte una lista di dieci stringhe in lista di tre stringhe.
     * <p/>
     * Per ogni istanza della lista, estrae la componente DueStringhe <br>
     *
     * @param listaIn da convertire
     *
     * @return il corrispondente array di TreStringhe
     */
    static ArrayList<TreStringhe> getTreStringhe(ArrayList<DieciStringhe> listaIn) {
        /* variabili e costanti locali di lavoro */
        ArrayList<TreStringhe> listaOut = null;
        boolean continua;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (listaIn != null && listaIn.size() > 0);

            if (continua) {
                listaOut = new ArrayList<TreStringhe>();
                for (DieciStringhe dieci : listaIn) {
                    listaOut.add(dieci.getTre());
                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return listaOut;
    }


    /**
     * Converte una lista di dieci stringhe in lista di quattro stringhe.
     * <p/>
     * Per ogni istanza della lista, estrae la componente DueStringhe <br>
     *
     * @param listaIn da convertire
     *
     * @return il corrispondente array di QuattroStringhe
     */
    static ArrayList<QuattroStringhe> getQuattroStringhe(ArrayList<DieciStringhe> listaIn) {
        /* variabili e costanti locali di lavoro */
        ArrayList<QuattroStringhe> listaOut = null;
        boolean continua;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (listaIn != null && listaIn.size() > 0);

            if (continua) {
                listaOut = new ArrayList<QuattroStringhe>();
                for (DieciStringhe dieci : listaIn) {
                    listaOut.add(dieci.getQuattro());
                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return listaOut;
    }


    /**
     * Converte una lista di dieci stringhe in lista di cinque stringhe.
     * <p/>
     * Per ogni istanza della lista, estrae la componente DueStringhe <br>
     *
     * @param listaIn da convertire
     *
     * @return il corrispondente array di CinqueStringhe
     */
    static ArrayList<CinqueStringhe> getCinqueStringhe(ArrayList<DieciStringhe> listaIn) {
        /* variabili e costanti locali di lavoro */
        ArrayList<CinqueStringhe> listaOut = null;
        boolean continua;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (listaIn != null && listaIn.size() > 0);

            if (continua) {
                listaOut = new ArrayList<CinqueStringhe>();
                for (DieciStringhe dieci : listaIn) {
                    listaOut.add(dieci.getCinque());
                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return listaOut;
    }


    /**
     * Converte una lista di dieci stringhe in lista di sei stringhe.
     * <p/>
     * Per ogni istanza della lista, estrae la componente DueStringhe <br>
     *
     * @param listaIn da convertire
     *
     * @return il corrispondente array di SeiStringhe
     */
    static ArrayList<SeiStringhe> getSeiStringhe(ArrayList<DieciStringhe> listaIn) {
        /* variabili e costanti locali di lavoro */
        ArrayList<SeiStringhe> listaOut = null;
        boolean continua;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (listaIn != null && listaIn.size() > 0);

            if (continua) {
                listaOut = new ArrayList<SeiStringhe>();
                for (DieciStringhe dieci : listaIn) {
                    listaOut.add(dieci.getSei());
                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return listaOut;
    }


    /**
     * Converte una lista di dieci stringhe in lista di sette stringhe.
     * <p/>
     * Per ogni istanza della lista, estrae la componente DueStringhe <br>
     *
     * @param listaIn da convertire
     *
     * @return il corrispondente array di SetteStringhe
     */
    static ArrayList<SetteStringhe> getSetteStringhe(ArrayList<DieciStringhe> listaIn) {
        /* variabili e costanti locali di lavoro */
        ArrayList<SetteStringhe> listaOut = null;
        boolean continua;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (listaIn != null && listaIn.size() > 0);

            if (continua) {
                listaOut = new ArrayList<SetteStringhe>();
                for (DieciStringhe dieci : listaIn) {
                    listaOut.add(dieci.getSette());
                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return listaOut;
    }


    /**
     * Converte una lista di dieci stringhe in lista di otto stringhe.
     * <p/>
     * Per ogni istanza della lista, estrae la componente OttoStringhe <br>
     *
     * @param listaIn da convertire
     *
     * @return il corrispondente array di OttoStringhe
     */
    static ArrayList<OttoStringhe> getOttoStringhe(ArrayList<DieciStringhe> listaIn) {
        /* variabili e costanti locali di lavoro */
        ArrayList<OttoStringhe> listaOut = null;
        boolean continua;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (listaIn != null && listaIn.size() > 0);

            if (continua) {
                listaOut = new ArrayList<OttoStringhe>();
                for (DieciStringhe dieci : listaIn) {
                    listaOut.add(dieci.getOtto());
                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return listaOut;
    }


    /**
     * Converte una lista di dieci stringhe in lista di nove stringhe.
     * <p/>
     * Per ogni istanza della lista, estrae la componente DueStringhe <br>
     *
     * @param listaIn da convertire
     *
     * @return il corrispondente array di NoveStringhe
     */
    static ArrayList<NoveStringhe> getNoveStringhe(ArrayList<DieciStringhe> listaIn) {
        /* variabili e costanti locali di lavoro */
        ArrayList<NoveStringhe> listaOut = null;
        boolean continua;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (listaIn != null && listaIn.size() > 0);

            if (continua) {
                listaOut = new ArrayList<NoveStringhe>();
                for (DieciStringhe dieci : listaIn) {
                    listaOut.add(dieci.getNove());
                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return listaOut;
    }


    /**
     * Converte una lista di due stringhe in lista di dieci stringhe.
     * <p/>
     *
     * @param listaIn da convertire
     *
     * @return il corrispondente array di DieciStringhe
     */
    static ArrayList<DieciStringhe> setDueStringhe(ArrayList<DueStringhe> listaIn) {
        /* variabili e costanti locali di lavoro */
        ArrayList<DieciStringhe> listaOut = null;
        boolean continua;
        DieciStringhe dieci;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (listaIn != null && listaIn.size() > 0);

            /* costruisci la lista fissa di dieci stringhe */
            if (continua) {
                listaOut = new ArrayList<DieciStringhe>();

                for (DueStringhe due : listaIn) {
                    dieci = new DieciStringhe(due);
                    listaOut.add(dieci);
                } // fine del ciclo for-each

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return listaOut;
    }


    /**
     * Converte una lista di tre stringhe in lista di dieci stringhe.
     * <p/>
     *
     * @param listaIn da convertire
     *
     * @return il corrispondente array di DieciStringhe
     */
    static ArrayList<DieciStringhe> setTreStringhe(ArrayList<TreStringhe> listaIn) {
        /* variabili e costanti locali di lavoro */
        ArrayList<DieciStringhe> listaOut = null;
        boolean continua;
        DieciStringhe dieci;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (listaIn != null && listaIn.size() > 0);

            /* costruisci la lista fissa di dieci stringhe */
            if (continua) {
                listaOut = new ArrayList<DieciStringhe>();

                for (TreStringhe tre : listaIn) {
                    dieci = new DieciStringhe(tre);
                    listaOut.add(dieci);
                } // fine del ciclo for-each

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return listaOut;
    }


    /**
     * Converte una lista di quattro stringhe in lista di dieci stringhe.
     * <p/>
     *
     * @param listaIn da convertire
     *
     * @return il corrispondente array di DieciStringhe
     */
    static ArrayList<DieciStringhe> setQuattroStringhe(ArrayList<QuattroStringhe> listaIn) {
        /* variabili e costanti locali di lavoro */
        ArrayList<DieciStringhe> listaOut = null;
        boolean continua;
        DieciStringhe dieci;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (listaIn != null && listaIn.size() > 0);

            /* costruisci la lista fissa di dieci stringhe */
            if (continua) {
                listaOut = new ArrayList<DieciStringhe>();

                for (QuattroStringhe quattro : listaIn) {
                    dieci = new DieciStringhe(quattro);
                    listaOut.add(dieci);
                } // fine del ciclo for-each

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return listaOut;
    }


    /**
     * Converte una lista di cinque stringhe in lista di dieci stringhe.
     * <p/>
     *
     * @param listaIn da convertire
     *
     * @return il corrispondente array di DieciStringhe
     */
    static ArrayList<DieciStringhe> setCinqueStringhe(ArrayList<CinqueStringhe> listaIn) {
        /* variabili e costanti locali di lavoro */
        ArrayList<DieciStringhe> listaOut = null;
        boolean continua;
        DieciStringhe dieci;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (listaIn != null && listaIn.size() > 0);

            /* costruisci la lista fissa di dieci stringhe */
            if (continua) {
                listaOut = new ArrayList<DieciStringhe>();

                for (CinqueStringhe cinque : listaIn) {
                    dieci = new DieciStringhe(cinque);
                    listaOut.add(dieci);
                } // fine del ciclo for-each

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return listaOut;
    }


    /**
     * Converte una lista di sei stringhe in lista di dieci stringhe.
     * <p/>
     *
     * @param listaIn da convertire
     *
     * @return il corrispondente array di DieciStringhe
     */
    static ArrayList<DieciStringhe> setSeiStringhe(ArrayList<SeiStringhe> listaIn) {
        /* variabili e costanti locali di lavoro */
        ArrayList<DieciStringhe> listaOut = null;
        boolean continua;
        DieciStringhe dieci;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (listaIn != null && listaIn.size() > 0);

            /* costruisci la lista fissa di dieci stringhe */
            if (continua) {
                listaOut = new ArrayList<DieciStringhe>();

                for (SeiStringhe sei : listaIn) {
                    dieci = new DieciStringhe(sei);
                    listaOut.add(dieci);
                } // fine del ciclo for-each

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return listaOut;
    }


    /**
     * Converte una lista di sette stringhe in lista di dieci stringhe.
     * <p/>
     *
     * @param listaIn da convertire
     *
     * @return il corrispondente array di DieciStringhe
     */
    static ArrayList<DieciStringhe> setSetteStringhe(ArrayList<SetteStringhe> listaIn) {
        /* variabili e costanti locali di lavoro */
        ArrayList<DieciStringhe> listaOut = null;
        boolean continua;
        DieciStringhe dieci;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (listaIn != null && listaIn.size() > 0);

            /* costruisci la lista fissa di dieci stringhe */
            if (continua) {
                listaOut = new ArrayList<DieciStringhe>();

                for (SetteStringhe sette : listaIn) {
                    dieci = new DieciStringhe(sette);
                    listaOut.add(dieci);
                } // fine del ciclo for-each

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return listaOut;
    }


    /**
     * Converte una lista di otto stringhe in lista di dieci stringhe.
     * <p/>
     *
     * @param listaIn da convertire
     *
     * @return il corrispondente array di DieciStringhe
     */
    static ArrayList<DieciStringhe> setOttoStringhe(ArrayList<OttoStringhe> listaIn) {
        /* variabili e costanti locali di lavoro */
        ArrayList<DieciStringhe> listaOut = null;
        boolean continua;
        DieciStringhe dieci;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (listaIn != null && listaIn.size() > 0);

            /* costruisci la lista fissa di dieci stringhe */
            if (continua) {
                listaOut = new ArrayList<DieciStringhe>();

                for (OttoStringhe otto : listaIn) {
                    dieci = new DieciStringhe(otto);
                    listaOut.add(dieci);
                } // fine del ciclo for-each

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return listaOut;
    }


    /**
     * Converte una lista di nove stringhe in lista di dieci stringhe.
     * <p/>
     *
     * @param listaIn da convertire
     *
     * @return il corrispondente array di DieciStringhe
     */
    static ArrayList<DieciStringhe> setNoveStringhe(ArrayList<NoveStringhe> listaIn) {
        /* variabili e costanti locali di lavoro */
        ArrayList<DieciStringhe> listaOut = null;
        boolean continua;
        DieciStringhe dieci;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (listaIn != null && listaIn.size() > 0);

            /* costruisci la lista fissa di dieci stringhe */
            if (continua) {
                listaOut = new ArrayList<DieciStringhe>();

                for (NoveStringhe nove : listaIn) {
                    dieci = new DieciStringhe(nove);
                    listaOut.add(dieci);
                } // fine del ciclo for-each

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return listaOut;
    }


    /**
     * Controlla se un dato nome esiste in una lista.
     * <p/>
     * La lista deve essere di oggetti di tipo String <br>
     *
     * @param lista lista di tipo String
     * @param nome da cercare nella lista
     *
     * @return vero se il nome figura nella lista
     */
    static boolean isEsisteNome(ArrayList<String> lista, String nome) {
        /* variabili e costanti locali di lavoro */
        boolean esiste = false;
        boolean continua;

        try { // prova ad eseguire il codice
            /* controllo di congruità all'ingresso */
            continua = ((lista != null) && Lib.Testo.isValida(nome));

            if (continua) {

                /* traverso tutta la collezione */
                for (String stringa : lista) {
                    if (nome.equals(stringa)) {
                        esiste = true;
                        break;
                    } /* fine del blocco if */
                } // fine del ciclo for-each
            } /* fine del blocco if */

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return esiste;
    }


    /**
     * Aggiunge un nome ad una lista, solo se non già esistente.
     * <p/>
     * La lista deve essere di oggetti di tipo String <br>
     *
     * @param lista lista di tipo String
     * @param nome da inserire nella lista
     *
     * @return vero se il nome viene aggiunto
     */
    static boolean addValoreUnico(ArrayList<String> lista, String nome) {
        /* variabili e costanti locali di lavoro */
        boolean aggiunto = false;
        boolean continua;

        try { // prova ad eseguire il codice
            /* controllo di congruità all'ingresso */
            continua = ((lista != null) && Lib.Testo.isValida(nome));

            if (continua) {
                if (!Lib.Array.isEsisteNome(lista, nome)) {
                    lista.add(nome);
                    aggiunto = true;
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return aggiunto;
    }


    /**
     * Aggiunge un numero ad una lista, solo se non già esistente.
     * <p/>
     * La lista deve essere di oggetti di tipo int <br>
     *
     * @param lista lista di tipo int
     * @param valore numerico da inserire nella lista
     *
     * @return vero se il nome viene aggiunto
     */
    static boolean addValoreUnico(ArrayList<Integer> lista, int valore) {
        /* variabili e costanti locali di lavoro */
        boolean aggiunto = false;
        boolean continua;

        try { // prova ad eseguire il codice
            /* controllo di congruità all'ingresso */
            continua = (lista != null);

            if (continua) {
                if (!Lib.Array.isEsiste(lista.toArray(), valore)) {
                    lista.add(valore);
                    aggiunto = true;
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return aggiunto;
    }


    /**
     * Aggiunge i valori ad una lista, solo se non già esistenti.
     * <p/>
     * Aggiunge tutti e solo i valori non già presenti <br>
     *
     * @param listaSorgente valori da aggiungere
     * @param listaDestinazione lista a cui aggiungere i valori
     *
     * @return vero se almeno un valore è stato aggiunto
     */
    static boolean addValoriUnici(ArrayList<String> listaSorgente,
                                  ArrayList<String> listaDestinazione) {
        /* variabili e costanti locali di lavoro */
        boolean aggiunto = false;
        boolean continua;

        try { // prova ad eseguire il codice
            /* controllo di congruità all'ingresso */
            continua = ((listaSorgente != null) && (listaDestinazione != null));

            if (continua) {
                /* spazzola tutta la collezione */
                for (String riga : listaSorgente) {
                    if (Lib.Array.addValoreUnico(listaDestinazione, riga)) {
                        aggiunto = true;
                    }// fine del blocco if
                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return aggiunto;
    }


    /**
     * Rimuove i valori da una lista.
     * <p/>
     *
     * @param listaDaEliminare valori da rimuovere
     * @param listaDestinazione lista da cui rimuovere i valori
     *
     * @return vero se almeno un valore è stato rimosso
     */
    static boolean rimuoveValori(ArrayList<String> listaDaEliminare,
                                 ArrayList<String> listaDestinazione) {
        /* variabili e costanti locali di lavoro */
        boolean rimosso = false;
        boolean continua;

        try { // prova ad eseguire il codice
            /* controllo di congruità all'ingresso */
            continua = ((listaDaEliminare != null) && (listaDestinazione != null));

            if (continua) {
                /* spazzola tutta la collezione */
                for (String riga : listaDaEliminare) {
                    listaDestinazione.remove(riga);
                    if (listaDestinazione.remove(riga)) {
                        rimosso = true;
                    }// fine del blocco if
                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return rimosso;
    }


    /**
     * Controlla la validità di una lista.
     * <p/>
     * Deve essere non nullo <br> Deve essere non vuoto <br>
     *
     * @param lista arry da controllare
     *
     * @return vero se l'array è valido
     */
    static boolean isValido(ArrayList<String> lista) {
        /* variabili e costanti locali di lavoro */
        boolean valido = false;

        try { // prova ad eseguire il codice
            if (lista != null) {
                if (lista.size() > 0) {
                    valido = true;
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valido;
    }


    /**
     * Controlla la validità di un array (matrice).
     * <p/>
     * Deve essere non nullo <br> Deve essere non vuoto <br>
     *
     * @param array da controllare
     *
     * @return vero se l'array è valido
     */
    static boolean isValido(String[] array) {
        /* variabili e costanti locali di lavoro */
        boolean valido = false;

        try { // prova ad eseguire il codice
            if (array != null) {
                if (array.length > 0) {
                    valido = true;
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valido;
    }


    /**
     * Controlla la validità di una lista.
     * <p/>
     * Deve essere non nullo <br> Deve essere non vuoto <br>
     *
     * @param lista arry da controllare
     *
     * @return vero se l'array è valido
     */
    static boolean isValidoInt(ArrayList<Integer> lista) {
        /* variabili e costanti locali di lavoro */
        boolean valido = false;

        try { // prova ad eseguire il codice
            if (lista != null) {
                if (lista.size() > 0) {
                    valido = true;
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valido;
    }


    /**
     * Ordina alfabeticamente una matrice.
     * <p/>
     *
     * @param matrice da ordinare alfabeticamente
     *
     * @return lista in ordine alfabetico
     */
    private static ArrayList<String> ordina(String[] matrice) {
        /* variabili e costanti locali di lavoro */
        ArrayList<String> ordinata = null;

        try { // prova ad eseguire il codice
            java.util.Arrays.sort(matrice);
            ordinata = new ArrayList<String>();

            for (String valore : matrice) {
                ordinata.add(valore);
            } // fine del ciclo for-each

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return ordinata;
    }


    /**
     * Ordina alfabeticamente una lista.
     * <p/>
     *
     * @param lista da ordinare alfabeticamente
     *
     * @return lista in ordine alfabetico
     */
    static ArrayList<String> ordina(ArrayList<String> lista) {
        /* variabili e costanti locali di lavoro */
        ArrayList<String> ordinata = null;
        boolean continua;
        String[] matrice;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            ordinata = lista;
            continua = (lista != null && lista.size() > 0);

            if (continua) {
                matrice = new String[lista.size()];
                matrice = lista.toArray(matrice);
                ordinata = LibArray.ordina(matrice);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return ordinata;
    }


    /**
     * Ordina una lista, spostando al primo posto il valore indicato.
     * <p/>
     * Se il valore non esiste, lo aggiunge <br>
     *
     * @param lista da ordinare alfabeticamente, dopo il primo valore prefissato
     * @param primoValore primo valore prefissato
     *
     * @return lista in ordine alfabetico, dopo il primo valore prefissato
     */
    static ArrayList<String> ordinaPrimo(ArrayList<String> lista, String primoValore) {
        /* variabili e costanti locali di lavoro */
        ArrayList<String> ordinata = null;
        boolean continua;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            ordinata = lista;
            continua = (lista != null && lista.size() > 0);

            if (continua) {
                if (lista.contains(primoValore)) {
                    lista.remove(primoValore);
                }// fine del blocco if

                ordinata = ordina(lista);
                ordinata.add(0, primoValore);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return ordinata;
    }


    /**
     * Ordina alfabeticamente una lista di stringhe (ordina sulla prima colonna).
     * <p/>
     *
     * @param listaIn da ordinare alfabeticamente
     *
     * @return lista in ordine alfabetico
     */
    static ArrayList<TreStringhe> ordinaTre(ArrayList<TreStringhe> listaIn) {
        /* variabili e costanti locali di lavoro */
        ArrayList<TreStringhe> listaOut = null;
        boolean continua;
        LinkedHashMap<String, TreStringhe> mappa;
        ArrayList<String> listaTemp;
        String unNome;
        TreStringhe unTre;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (listaIn != null && listaIn.size() > 0);

            if (continua) {
                listaOut = new ArrayList<TreStringhe>();
                listaTemp = new ArrayList<String>();
                mappa = new LinkedHashMap<String, TreStringhe>();

                for (TreStringhe tre : listaIn) {
                    unNome = tre.getPrima();
                    mappa.put(unNome, tre);
                    listaTemp.add(unNome);
                } // fine del ciclo for-each

                /* ordina la lista di riferimento */
                listaTemp = Lib.Array.ordina(listaTemp);

                for (String nome : listaTemp) {
                    unTre = mappa.get(nome);
                    listaOut.add(unTre);
                } // fine del ciclo for-each

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return listaOut;
    }


    /**
     * Ordina alfabeticamente una lista di stringhe (ordina sulla prima colonna).
     * <p/>
     *
     * @param unica flag se la lista deve contenere solo valori univoci (della chiave)
     * @param listaIn da ordinare alfabeticamente
     *
     * @return lista in ordine alfabetico
     */
    static ArrayList<QuattroStringhe> ordinaQuattro(ArrayList<QuattroStringhe> listaIn,
                                                    boolean unica) {
        /* variabili e costanti locali di lavoro */
        ArrayList<QuattroStringhe> listaOut = null;
        boolean continua;
        LinkedHashMap<String, QuattroStringhe> mappa;
        ArrayList<String> listaTemp;
        String unNome;
        QuattroStringhe unQuattro;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (listaIn != null && listaIn.size() > 0);

            if (continua) {
                listaOut = new ArrayList<QuattroStringhe>();
                listaTemp = new ArrayList<String>();
                mappa = new LinkedHashMap<String, QuattroStringhe>();

                for (QuattroStringhe quattro : listaIn) {
                    unNome = quattro.getPrima();
                    mappa.put(unNome, quattro);

                    if (unica) {
                        if (!listaTemp.contains(unNome)) {
                            listaTemp.add(unNome);
                        }// fine del blocco if
                    } else {
                        listaTemp.add(unNome);
                    }// fine del blocco if-else

                } // fine del ciclo for-each

                /* ordina la lista di riferimento */
                listaTemp = Lib.Array.ordina(listaTemp);

                for (String nome : listaTemp) {
                    unQuattro = mappa.get(nome);
                    listaOut.add(unQuattro);
                } // fine del ciclo for-each

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return listaOut;
    }


    /**
     * Ordina alfabeticamente una lista di stringhe (ordina sulla prima colonna).
     * <p/>
     *
     * @param unica flag se la lista deve contenere solo valori univoci (della chiave)
     * @param listaIn da ordinare alfabeticamente
     *
     * @return lista in ordine alfabetico
     */
    static ArrayList<CinqueStringhe> ordinaCinque(ArrayList<CinqueStringhe> listaIn,
                                                  boolean unica) {
        /* variabili e costanti locali di lavoro */
        ArrayList<CinqueStringhe> listaOut = null;
        boolean continua;
        LinkedHashMap<String, CinqueStringhe> mappa;
        ArrayList<String> listaTemp;
        String unNome;
        CinqueStringhe unCinque;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (listaIn != null && listaIn.size() > 0);

            if (continua) {
                listaOut = new ArrayList<CinqueStringhe>();
                listaTemp = new ArrayList<String>();
                mappa = new LinkedHashMap<String, CinqueStringhe>();

                for (CinqueStringhe cinque : listaIn) {
                    unNome = cinque.getPrima();
                    mappa.put(unNome, cinque);

                    if (unica) {
                        if (!listaTemp.contains(unNome)) {
                            listaTemp.add(unNome);
                        }// fine del blocco if
                    } else {
                        listaTemp.add(unNome);
                    }// fine del blocco if-else

                } // fine del ciclo for-each

                /* ordina la lista di riferimento */
                listaTemp = Lib.Array.ordina(listaTemp);

                for (String nome : listaTemp) {
                    unCinque = mappa.get(nome);
                    listaOut.add(unCinque);
                } // fine del ciclo for-each

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return listaOut;
    }


    /**
     * Ordina alfabeticamente una lista di stringhe (ordina sulla prima e seconda colonna).
     * <p/>
     *
     * @param unica flag se la lista deve contenere solo valori univoci (della chiave)
     * @param listaIn da ordinare alfabeticamente
     *
     * @return lista in ordine alfabetico
     */
    static ArrayList<DieciStringhe> ordinaDieci(ArrayList<DieciStringhe> listaIn, boolean unica) {
        /* variabili e costanti locali di lavoro */
        ArrayList<DieciStringhe> listaOut = null;
        boolean continua;
        LinkedHashMap<String, DieciStringhe> mappa;
        ArrayList<String> listaTemp;
        String sigla;
        DieciStringhe unDieci;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (listaIn != null && listaIn.size() > 0);

            if (continua) {
                listaOut = new ArrayList<DieciStringhe>();
                listaTemp = new ArrayList<String>();
                mappa = new LinkedHashMap<String, DieciStringhe>();

                for (DieciStringhe dieci : listaIn) {
                    sigla = dieci.getPrima() + dieci.getSeconda();
                    mappa.put(sigla, dieci);

                    if (unica) {
                        if (!listaTemp.contains(sigla)) {
                            listaTemp.add(sigla);
                        }// fine del blocco if
                    } else {
                        listaTemp.add(sigla);
                    }// fine del blocco if-else

                } // fine del ciclo for-each

                /* ordina la lista di riferimento */
                listaTemp = Lib.Array.ordina(listaTemp);

                for (String siglaOrdinata : listaTemp) {
                    unDieci = mappa.get(siglaOrdinata);
                    listaOut.add(unDieci);
                } // fine del ciclo for-each

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return listaOut;
    }


    /**
     * Ordina alfabeticamente una lista di stringhe (ordina sulla prima colonna).
     * <p/>
     * Ammette i doppi valori della chiave (prima colonna)
     *
     * @param listaIn da ordinare alfabeticamente
     *
     * @return lista in ordine alfabetico
     */
    static ArrayList<QuattroStringhe> ordinaQuattro(ArrayList<QuattroStringhe> listaIn) {
        /* invoca il metodo sovrascritto della superclasse */
        return ordinaQuattro(listaIn, false);
    }


    /**
     * Ordina alfabeticamente una lista di stringhe (ordina sulla prima colonna).
     * <p/>
     * Non ammette i doppi valori della chiave (prima colonna)
     *
     * @param listaIn da ordinare alfabeticamente
     *
     * @return lista in ordine alfabetico
     */
    static ArrayList<QuattroStringhe> ordinaUnicaQuattro(ArrayList<QuattroStringhe> listaIn) {
        /* invoca il metodo sovrascritto della superclasse */
        return ordinaQuattro(listaIn, true);
    }


    /**
     * Ordina alfabeticamente una lista di stringhe (ordina sulla prima colonna).
     * <p/>
     *
     * @param listaIn da ordinare alfabeticamente
     *
     * @return lista in ordine alfabetico
     */
    static ArrayList<CinqueStringhe> ordinaCinque(ArrayList<CinqueStringhe> listaIn) {
        /* invoca il metodo sovrascritto della superclasse */
        return ordinaCinque(listaIn, false);
    }


    /**
     * Ordina alfabeticamente una lista di stringhe (ordina sulla prima colonna).
     * <p/>
     * Non ammette i doppi valori della chiave (prima colonna)
     *
     * @param listaIn da ordinare alfabeticamente
     *
     * @return lista in ordine alfabetico
     */
    static ArrayList<CinqueStringhe> ordinaUnicaCinque(ArrayList<CinqueStringhe> listaIn) {
        /* invoca il metodo sovrascritto della superclasse */
        return ordinaCinque(listaIn, true);
    }


    /**
     * Ordina alfabeticamente una lista di stringhe (ordina sulla prima e seconda colonna).
     * <p/>
     * Non ammette i doppi valori della chiave (prima colonna)
     *
     * @param listaIn da ordinare alfabeticamente
     *
     * @return lista in ordine alfabetico
     */
    static ArrayList<DieciStringhe> ordinaDieci(ArrayList<DieciStringhe> listaIn) {
        /* invoca il metodo sovrascritto della superclasse */
        return ordinaDieci(listaIn, false);
    }


    /**
     * Ordina alfabeticamente una lista di stringhe (ordina sulla prima e seconda colonna).
     * <p/>
     * Non ammette i doppi valori della chiave (prima colonna)
     *
     * @param listaIn da ordinare alfabeticamente
     *
     * @return lista in ordine alfabetico
     */
    static ArrayList<DieciStringhe> ordinaUnicaDieci(ArrayList<DieciStringhe> listaIn) {
        /* invoca il metodo sovrascritto della superclasse */
        return ordinaDieci(listaIn, true);
    }


    /**
     * Ordina alfabeticamente una mappa (secondo la chiave).
     * <p/>
     *
     * @param mappa da ordinare alfabeticamente
     *
     * @return mappa con chiavi in ordine alfabetico
     */
    static LinkedHashMap ordina(LinkedHashMap mappa) {
        /* variabili e costanti locali di lavoro */
        LinkedHashMap ordinata = null;
        String[] matrice;
        ArrayList<String> lista;
        int k = 0;
        Object valore;

        try { // prova ad eseguire il codice
            /* costruisce la mappa ordinata */
            ordinata = new LinkedHashMap();

            /* costruisce una matrice di appoggio */
            matrice = new String[mappa.size()];

            /* recupera una lista di sole chiavi */
            for (Object ogg : mappa.keySet()) {
                matrice[k++] = (String)ogg;
            } // fine del ciclo for-each

            /* ordina le chiavi */
            lista = LibArray.ordina(matrice);

            /* ricostruisce una mappa ordinata secondo le chiavi */
            for (String chiave : lista) {
                valore = mappa.get(chiave);
                ordinata.put(chiave, valore);
            } // fine del ciclo for-each

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return ordinata;
    }


    /**
     * Somma due liste.
     * <p/>
     * Esegue solo se gli array non sono nulli <br>
     *
     * @param prima lista da sommare
     * @param seconda lista da sommare
     *
     * @return lista somma delle due
     */
    static ArrayList<String> sommaStr(ArrayList<String> prima, ArrayList<String> seconda) {
        /* variabili e costanti locali di lavoro */
        ArrayList<String> somma = null;
        boolean continua;

        try { // prova ad eseguire il codice
            continua = (prima != null && LibArray.isValido(seconda));

            if (continua) {
                somma = new ArrayList<String>();
            }// fine del blocco if

            if (continua) {
                /* traverso tutta la collezione */
                for (String stringa : prima) {
                    somma.add(stringa);
                } // fine del ciclo for-each

                /* traverso tutta la collezione */
                for (String stringa : seconda) {
                    somma.add(stringa);
                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return somma;
    }


    /**
     * Somma due liste di dieci stringhe.
     * <p/>
     * Esegue solo se gli array non sono nulli <br>
     *
     * @param prima lista da sommare
     * @param seconda lista da sommare
     *
     * @return lista somma delle due
     */
    public static ArrayList<DieciStringhe> sommaDieci(ArrayList<DieciStringhe> prima,
                                                      ArrayList<DieciStringhe> seconda) {
        /* variabili e costanti locali di lavoro */
        ArrayList<DieciStringhe> somma = null;
        boolean continua;

        try { // prova ad eseguire il codice
            continua = (prima != null && seconda != null);

            if (continua) {
                somma = new ArrayList<DieciStringhe>();
            }// fine del blocco if

            if (continua) {
                /* traverso tutta la collezione */
                for (DieciStringhe dieci : prima) {
                    somma.add(dieci);
                } // fine del ciclo for-each

                /* traverso tutta la collezione */
                for (DieciStringhe dieci : seconda) {
                    somma.add(dieci);
                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return somma;
    }


    /**
     * Somma due liste.
     * <p/>
     * Esegue solo se gli array non sono nulli <br>
     *
     * @param prima lista da sommare
     * @param seconda lista da sommare
     *
     * @return lista somma delle due
     */
    static ArrayList<Object> sommaObj(ArrayList<Object> prima, ArrayList<Object> seconda) {
        /* variabili e costanti locali di lavoro */
        ArrayList<Object> somma = null;
        boolean continua;

        try { // prova ad eseguire il codice
            continua = (prima != null) && (seconda != null);

            if (continua) {
                somma = new ArrayList<Object>();
            }// fine del blocco if

            if (continua) {
                /* traverso tutta la collezione */
                for (Object uno : prima) {
                    somma.add(uno);
                } // fine del ciclo for-each

                /* traverso tutta la collezione */
                for (Object due : seconda) {
                    somma.add(due);
                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return somma;
    }


    /**
     * Somma due liste.
     * <p/>
     * Esegue solo se gli array non sono nulli <br>
     *
     * @param prima lista da sommare
     * @param seconda lista da sommare
     *
     * @return lista somma delle due
     */
    static ArrayList<Campo> sommaCampi(ArrayList<Campo> prima, ArrayList<Campo> seconda) {
        /* variabili e costanti locali di lavoro */
        ArrayList<Campo> somma = null;
        boolean continua;

        try { // prova ad eseguire il codice
            continua = (prima != null) && (seconda != null);

            if (continua) {
                somma = new ArrayList<Campo>();
            }// fine del blocco if

            if (continua) {
                /* traverso tutta la collezione */
                for (Campo uno : prima) {
                    somma.add(uno);
                } // fine del ciclo for-each

                /* traverso tutta la collezione */
                for (Campo due : seconda) {
                    somma.add(due);
                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return somma;
    }


    /**
     * Intersezione di due array (matrici).
     * <p/>
     * Esegue solo se gli array non sono nulli <br>
     *
     * @param primo array da confrontare
     * @param secondo array da confrontare
     *
     * @return array intersezione dei due (tutte e solo le stringhe che appartengono ad entrambi)
     */
    static String[] intersezione(String[] primo, String[] secondo) {
        /* variabili e costanti locali di lavoro */
        String[] intersezione = null;
        boolean continua;
        ArrayList<String> temp;

        try { // prova ad eseguire il codice
            continua = LibArray.isValido(primo) && LibArray.isValido(primo);

            if (continua) {
                /* lista temporanea di appoggio */
                temp = new ArrayList<String>();

                /* traverso tutta la collezione */
                for (String strUno : primo) {
                    for (String strDue : secondo) {
                        if (strUno != null && strDue != null) {
                            if (strDue.equals(strUno)) {
                                temp.add(strUno);
                                break;
                            }// fine del blocco if
                        }// fine del blocco if
                    } // fine del ciclo for-each
                } // fine del ciclo for-each

                /* crea e regola l'array definitivo */
                intersezione = new String[temp.size()];
                intersezione = temp.toArray(intersezione);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return intersezione;
    }


    /**
     * Differenza di due array (matrici).
     * <p/>
     * Esegue solo se gli array non sono nulli <br>
     *
     * @param primo array da confrontare
     * @param secondo array da confrontare
     *
     * @return array differenza dei due (solo le stringhe del primo che non appartengono anche al
     *         secondo)
     */
    static String[] differenza(String[] primo, String[] secondo) {
        /* variabili e costanti locali di lavoro */
        String[] differenza = null;
        boolean continua;
        ArrayList<String> temp;

        try { // prova ad eseguire il codice
            continua = LibArray.isValido(primo) && LibArray.isValido(primo);

            if (continua) {
                /* lista temporanea di appoggio */
                temp = new ArrayList<String>();

                /* traverso tutta la collezione */
                for (String strUno : primo) {
                    if (strUno != null) {
                        if (!Lib.Array.isEsiste(secondo, strUno)) {
                            temp.add(strUno);
                        }// fine del blocco if
                    }// fine del blocco if

                } // fine del ciclo for-each

                /* crea e regola l'array definitivo */
                differenza = new String[temp.size()];
                differenza = temp.toArray(differenza);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return differenza;
    }


    /**
     * Converts a list of Integer objects to an array of integer primitives
     *
     * @param integerList the integer list
     * @return an array of integer primitives
     */
    static int[] toIntArray(List<Integer> integerList) {
        int[] intArray = new int[integerList.size()];
        for (int i = 0; i < integerList.size(); i++) {
            intArray[i] = integerList.get(i);
        }
        return intArray;
    }


    /**
     * Converts a list of Double objects to an array of double primitives
     *
     * @param doubleList the double list
     * @return an array of double primitives
     */
    public static double[] toDoubleArray(List<Double> doubleList) {
        double[] doubleArray = new double[doubleList.size()];
        for (int i = 0; i < doubleList.size(); i++) {
            doubleArray[i] = doubleList.get(i);
        }
        return doubleArray;
    }


}// fine della classe
