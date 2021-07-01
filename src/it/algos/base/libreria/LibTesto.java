/**
 * Copyright: Copyright (c) 2006
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      31-mar-2006
 */
package it.algos.base.libreria;

import it.algos.base.errore.Errore;
import it.algos.base.wrapper.QuattroStringhe;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Random;

/**
 * Classe astratta con metodi statici. </p> Tutti i metodi sono statici <br> La classe contiene
 * metodi di utilità per i testi <br> I metodi non hanno modificatore così sono visibili all'esterno
 * del package solo utilizzando l'interfaccia unificata <b>Lib</b><br>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 31-mar-2006 ore 11.31.37
 */
public abstract class LibTesto {

    /**
     * tag per il carattere punto
     */
    private static final char PUNTO = '.';

    /**
     * tag per il carattere punto
     */
    private static final char BARRA = '/';

    /**
     * tag per la stringa vuota
     */
    private static final String VUOTA = "";

    /**
     * tag per la stringa spazio
     */
    private static final String SPAZIO = " ";

    /**
     * tag per la stringa virgola
     */
    private static final String VIRGOLA = ",";

    /**
     * tag per la stringa tabulatore
     */
    private static final String TAB = "\t";

    /**
     * tag per la stringa a capo
     */
    private static final String CR = "\n";


    /**
     * Utility di conversione di una stringa.
     * <p/>
     * Sostituisce tutte le occorrenze del carattere trovato <br>
     *
     * @param stringa testo in ingresso
     * @param vecchio carattere da eliminare
     * @param nuovo   carattere da sostituire
     *
     * @return stringa convertita garantisce una stringa non nulla
     */
    private static String converte(String stringa, char vecchio, char nuovo) {
        /* variabili e costanti locali di lavoro */
        String uscita = "";
        boolean continua;

        try { // prova ad eseguire il codice
            /* controllo di congruità all'ingresso */
            continua = Lib.Testo.isValida(stringa);

            if (continua) {
                /* opera la conversione */
                uscita = stringa.replace(vecchio, nuovo);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return uscita;
    }


    /**
     * Utility di conversione di una stringa.
     * <p/>
     * Trasforma i punti in barre <br>
     *
     * @param stringa testo in ingresso
     *
     * @return stringa convertita garantisce una stringa non nulla
     */
    static String convertePuntiInBarre(String stringa) {
        /* variabili e costanti locali di lavoro */
        char vecchio = PUNTO;
        char nuovo = BARRA;

        /* invoca il metodo delegato della classe */
        return LibTesto.converte(stringa, vecchio, nuovo);
    }


    /**
     * Utility di conversione di una stringa.
     * <p/>
     * Trasforma le barre in punti <br>
     *
     * @param stringa testo in ingresso
     *
     * @return stringa convertita garantisce una stringa non nulla
     */
    static String converteBarreInPunti(String stringa) {
        /* variabili e costanti locali di lavoro */
        char vecchio = BARRA;
        char nuovo = PUNTO;

        /* invoca il metodo delegato della classe */
        return LibTesto.converte(stringa, vecchio, nuovo);
    }


    /**
     * Utility di ricerca in una stringa di una stringa.
     * <p/>
     * Restituisce la prima o l'ultima occorenza tra due stringhe <br>
     *
     * @param testo  in ingresso
     * @param carUno primo carattere
     * @param carDue secondo carattere
     * @param primo  flag per cercare il primo o l'ultimo
     *
     * @return posizione del primo o dell'ultimo carattere trovato -1 se non trovato nessuno dei
     *         due
     */
    private static int getPosBase(String testo, String carUno, String carDue, boolean primo) {
        /* variabili e costanti locali di lavoro */
        int pos = -1;
        boolean continua = true;
        int posUno;
        int posDue;

        try { // prova ad eseguire il codice
            posUno = testo.indexOf(carUno);
            posDue = testo.indexOf(carDue);

            if ((posUno != -1) && (posDue != -1)) {
                if (primo) {
                    pos = Math.min(posUno, posDue);
                } else {
                    pos = Math.max(posUno, posDue);
                }// fine del blocco if-else
                continua = false;
            }// fine del blocco if

            if (continua) {
                if (posUno != -1) {
                    pos = posUno;
                }// fine del blocco if
                if (posDue != -1) {
                    pos = posDue;
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return pos;
    }


    /**
     * Utility di ricerca di due stringhe in un testo.
     * <p/>
     * Restituisce la prima occorrenza tra due stringhe <br>
     *
     * @param testo  in ingresso
     * @param strUno prima stringa
     * @param strDue seconda stringa
     *
     * @return posizione della prima stringa trovata -1 se non ha trovato nessuna delle due
     */
    static int getPos(String testo, String strUno, String strDue) {
        /* invoca il metodo delegato della classe */
        return LibTesto.getPosBase(testo, strUno, strDue, true);
    }


    /**
     * Utility di ricerca di due stringhe in un testo.
     * <p/>
     * Restituisce la seconda occorrenza tra due stringhe <br>
     *
     * @param testo  in ingresso
     * @param strUno prima stringa
     * @param strDue seconda stringa
     *
     * @return posizione della seconda stringa trovata -1 se non ha trovato nessuna delle due
     */
    static int getPosDue(String testo, String strUno, String strDue) {
        /* invoca il metodo delegato della classe */
        return LibTesto.getPosBase(testo, strUno, strDue, false);
    }


    /**
     * Crea una lista di nomi (chiavi) da una collezione di oggetti.
     * <p/>
     * Riceve una collezione ordinata di oggetti <br> Crea una ArrayList di stringhe, formata dai
     * nomi delle chiavi <br> Esegue solo se la collezione non è nulla <br>
     *
     * @param collezione di oggetti
     *
     * @return lista ordinata delle chiavi della collezione
     */
    static ArrayList<String> getListaChiavi(LinkedHashMap<String, Object> collezione) {
        /* variabili e costanti locali di lavoro */
        ArrayList<String> lista = null;
        boolean continua;

        try { // prova ad eseguire il codice
            /* controllo di congruità all'ingresso */
            continua = (collezione != null);

            /* crea l'istanza */
            if (continua) {
                lista = new ArrayList<String>();
            }// fine del blocco if

            /* spazzola tutta la collezione */
            if (continua) {
                for (Object oggetto : collezione.keySet()) {
                    lista.add(oggetto.toString());
                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }


    /**
     * Controlla che un oggetto contenga un valore valido di stringa.
     * <p/>
     * Sono esclusi i valori nulli <br> Sono escluse le stringhe vuote <br> Sono escluse le stringhe
     * di soli spazi <br>
     *
     * @param oggetto da controllare
     *
     * @return vero, se l'oggetto rappresenta una stringa valida falso, se l'oggetto non rappresenta
     *         una stringa valida
     */
    static boolean isValida(Object oggetto) {
        /* variabili e costanti locali di lavoro */
        boolean valida = false;
        boolean continua;
        String stringa = null;

        try { // prova ad eseguire il codice
            /* controllo di congruità all'ingresso */
            continua = (oggetto != null);

            if (continua) {
                continua = (oggetto instanceof String);
            }// fine del blocco if

            if (continua) {
                stringa = (String)oggetto;
            }// fine del blocco if

            if (continua) {
                if (stringa.equals(VUOTA)) {
                    valida = false;
                    continua = false;
                } else {
                    valida = true;
                }// fine del blocco if-else
            }// fine del blocco if

            if (continua) {
                if ((stringa.trim().length() < 1)) {
                    valida = false;
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valida;
    }


    /**
     * Controlla se il carattere iniziale del testo è una vocale.
     * <p/>
     *
     * @param testo da controllare
     *
     * @return vero, se il primo carattere è una vocale
     */
    static boolean isPrimaVocale(String testo) {
        /* variabili e costanti locali di lavoro */
        boolean vocale = false;
        boolean continua;
        String prima = "";

        try { // prova ad eseguire il codice
            /* controllo di congruità all'ingresso */
            continua = LibTesto.isValida(testo);

            if (continua) {
                prima = testo.substring(0, 1);
                prima = LibTesto.primaMaiuscola(prima);
            }// fine del blocco if

            if (continua) {
                vocale =
                        (prima.equals("A") ||
                                prima.equals("E") ||
                                prima.equals("I") ||
                                prima.equals("O") ||
                                prima.equals("U"));
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return vocale;
    }


    /**
     * Controlla che un oggetto contenga un valore nullo od una stringa vuota.
     * <p/>
     * Sono compresi i valori nulli <br> Sono comprese le stringhe vuote <br> Sono comprese le
     * stringhe di soli spazi <br>
     *
     * @param oggetto da controllare
     *
     * @return vero, se l'oggetto rappresenta una stringa nulla o vuota falso, se l'oggetto non
     *         rappresenta una stringa nulla o vuota
     */
    static boolean isVuota(Object oggetto) {
        /* invoca il metodo delegato della classe */
        return !LibTesto.isValida(oggetto);
    }


    /**
     * Forza il primo carattere della stringa a maiuscolo.
     * <p/>
     * Esegue solo se la stringa è valida <br>
     *
     * @param stringa testo in ingresso
     *
     * @return stringa convertita
     */
    static String primaMaiuscola(String stringa) {
        /* variabili e costanti locali di lavoro */
        String uscita = "";
        boolean continua;

        try { // prova ad eseguire il codice
            /* controllo di congruità all'ingresso */
            continua = LibTesto.isValida(stringa);

            if (continua) {
                uscita = stringa.substring(0, 1);
                uscita = uscita.toUpperCase();
                stringa = stringa.substring(1);
                uscita = uscita + stringa;
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return uscita;
    }


    /**
     * Forza il primo carattere della stringa a minuscolo.
     * <p/>
     * Esegue solo se la stringa è valida <br>
     *
     * @param stringa testo in ingresso
     *
     * @return stringa convertita
     */
    static String primaMinuscola(String stringa) {
        /* variabili e costanti locali di lavoro */
        String uscita = "";
        boolean continua;

        try { // prova ad eseguire il codice
            /* controllo di congruità all'ingresso */
            continua = LibTesto.isValida(stringa);

            if (continua) {
                uscita = stringa.substring(0, 1);
                uscita = uscita.toLowerCase();
                stringa = stringa.substring(1);
                uscita = uscita + stringa;
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return uscita;
    }


    /**
     * Inserisce virgolette prima e dopo la stringa.
     * <p/>
     *
     * @param stringaIn da elaborare
     *
     * @return stringa elaborata
     */
    static String setVirgolette(String stringaIn) {
        /* variabili e costanti locali di lavoro */
        String stringaOut = "";
        String tag = "\"";

        try { // prova ad eseguire il codice
            stringaOut = tag;
            stringaOut += stringaIn;
            stringaOut += tag;
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return stringaOut;
    }


    /**
     * Leva eventuali virgolette iniziali e finali.
     * <p/>
     * Elimina spazi vuoti iniziali e finali sia prima che dopo <br>
     *
     * @param stringaIn da elaborare
     *
     * @return stringa elaborata
     */
    static String levaVirgolette(String stringaIn) {
        /* variabili e costanti locali di lavoro */
        String stringaOut = "";
        boolean continua;
        String tag = "\"";

        try { // prova ad eseguire il codice
            continua = (Lib.Testo.isValida(stringaIn));
            stringaOut = stringaIn.trim();

            if (continua) {
                if (stringaOut.startsWith(tag)) {
                    stringaOut = stringaOut.substring(stringaOut.indexOf(tag) + tag.length());
                }// fine del blocco if
            }// fine del blocco if

            if (continua) {
                if (stringaOut.endsWith(tag)) {
                    stringaOut = stringaOut.substring(0, stringaOut.lastIndexOf(tag));
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return stringaOut;
    }


    /**
     * Elimina la coda terminale della stringa, se esiste.
     * <p/>
     * Esegue solo se la stringa è valida <br> Elimina spazi vuoti iniziali e finali <br>
     *
     * @param stringaIn testo in ingresso
     * @param coda      da eliminare
     *
     * @return stringa convertita
     */
    static String levaCoda(String stringaIn, String coda) {
        /* variabili e costanti locali di lavoro */
        String stringaOut = "";
        boolean continua;

        try { // prova ad eseguire il codice
            /* controllo di congruità all'ingresso */
            stringaOut = stringaIn;
            continua = LibTesto.isValida(stringaIn);

            if (continua) {
                stringaOut = stringaIn.trim();
                if (stringaOut.endsWith(coda)) {
                    stringaOut = stringaOut.substring(0, stringaOut.length() - coda.length());
                    stringaOut = stringaOut.trim();
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return stringaOut;
    }


    /**
     * Restituisce la parte di testo precedente al tag.
     * <p/>
     * Controlla la prima occorrenza del tag <br> Esegue solo se la stringa è valida <br> Elimina
     * spazi vuoti iniziali e finali <br>
     *
     * @param stringaIn testo in ingresso
     * @param tag       per la separazione
     *
     * @return stringa risultante
     */
    static String primaPrima(String stringaIn, String tag) {
        /* variabili e costanti locali di lavoro */
        String prima = "";
        boolean continua;
        int pos = 0;

        try { // prova ad eseguire il codice
            /* controllo di congruità all'ingresso */
            prima = stringaIn;
            continua = LibTesto.isValida(stringaIn);

            if (continua) {
                stringaIn = stringaIn.trim();
                pos = stringaIn.indexOf(tag);
                continua = (pos != -1);
            }// fine del blocco if

            if (continua) {
                prima = stringaIn.substring(0, pos);
                prima = prima.trim();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return prima;
    }


    /**
     * Restituisce la parte di testo precedente al tag.
     * todo ATTENZIONE! non funziona se la stringa ha più di 1 tag al suo interno!
     * <p/>
     * Controlla l'ultima occorrenza del tag <br> Esegue solo se la stringa è valida <br> Elimina
     * spazi vuoti iniziali e finali <br>
     *
     * @param stringaIn testo in ingresso
     * @param tag       per la separazione
     *
     * @return stringa risultante
     */
    static String primaUltima(String stringaIn, String tag) {
        /* variabili e costanti locali di lavoro */
        String prima = "";
        boolean continua;
        int pos = 0;

        try { // prova ad eseguire il codice
            /* controllo di congruità all'ingresso */
            prima = stringaIn;
            continua = LibTesto.isValida(stringaIn);

            if (continua) {
                stringaIn = stringaIn.trim();
                pos = stringaIn.lastIndexOf(tag);
                continua = (pos != -1);
            }// fine del blocco if

            if (continua) {
                prima = stringaIn.substring(0, pos);
                prima = prima.trim();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return prima;
    }


    /**
     * Restituisce la parte di testo successiva al tag.
     * <p/>
     * Controlla la prima occorrenza del tag <br> Esegue solo se la stringa è valida <br> Elimina
     * spazi vuoti iniziali e finali <br>
     *
     * @param stringaIn testo in ingresso
     * @param tag       per la separazione
     *
     * @return stringa risultante
     */
    static String dopoPrima(String stringaIn, String tag) {
        /* variabili e costanti locali di lavoro */
        String dopo = "";
        boolean continua;
        int pos = 0;

        try { // prova ad eseguire il codice
            /* controllo di congruità all'ingresso */
            dopo = stringaIn;
            continua = LibTesto.isValida(stringaIn);

            if (continua) {
                stringaIn = stringaIn.trim();
                pos = stringaIn.indexOf(tag);
                continua = (pos != -1);
            }// fine del blocco if

            if (continua) {
                pos += tag.length();
                dopo = stringaIn.substring(pos);
                dopo = dopo.trim();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return dopo;
    }


    /**
     * Restituisce la parte di testo successiva al tag.
     * <p/>
     * Esegue solo se la stringa è valida <br>
     * Controlla l'ultima occorrenza del tag <br>
     * Elimina spazi vuoti iniziali e finali <br>
     * Se il tag non esiste, ritorna la stringa originaria o una stringa vuota a seconda del flag <br>
     *
     * @param stringaIn   testo in ingresso
     * @param tag         per la separazione
     * @param facoltativo flag per il valore di ritorno di default
     *
     * @return testo successivo al tag
     */
    private static String dopoUltima(String stringaIn, String tag, boolean facoltativo) {
        /* variabili e costanti locali di lavoro */
        String dopo = "";
        boolean continua;
        int pos = 0;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            if (facoltativo) {
                dopo = stringaIn;
            }// fine del blocco if

            continua = (Lib.Clas.isValidi(stringaIn, tag));

            if (continua) {
                stringaIn = stringaIn.trim();
                pos = stringaIn.lastIndexOf(tag);
                continua = (pos != -1);
            }// fine del blocco if

            if (continua) {
                pos += tag.length();
                dopo = stringaIn.substring(pos);
                dopo = dopo.trim();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return dopo;
    }


    /**
     * Restituisce la parte di testo successiva al tag.
     * <p/>
     * Esegue solo se la stringa è valida <br>
     * Controlla l'ultima occorrenza del tag <br>
     * Se il tag non esiste, restituisce la stringa intera <br>
     * Elimina spazi vuoti iniziali e finali <br>
     *
     * @param stringaIn testo in ingresso
     * @param tag       per la separazione
     *
     * @return testo successivo al tag o intera stringa se il tag non esiste
     */
    static String dopoUltima(String stringaIn, String tag) {
        /* invoca il metodo sovrascritto della classe */
        return dopoUltima(stringaIn, tag, true);
    }


    /**
     * Restituisce la parte di testo successiva al tag.
     * <p/>
     * Esegue solo se la stringa è valida <br>
     * Controlla l'ultima occorrenza del tag <br>
     * Se il tag non esiste, restituisce una stringa vuota <br>
     * Elimina spazi vuoti iniziali e finali <br>
     *
     * @param stringaIn testo in ingresso
     * @param tag       per la separazione
     *
     * @return testo successivo al tag o stringa vuota se il tag non esiste
     */
    static String dopoUltimaEsistente(String stringaIn, String tag) {
        /* invoca il metodo sovrascritto della classe */
        return dopoUltima(stringaIn, tag, false);
    }


    /**
     * Crea una stringa da una lista di nomi.
     * <p/>
     * Ogni nome nella stringa viene diviso da uno specifico separatore <br> Esegue solo se la lista
     * non è nulla <br> Elimina il separatore finale <br>
     *
     * @param lista di nomi
     * @param sep   carattere (stringa) separatore nella stringa
     *
     * @return stringa di nomi separati dal separatore
     */
    static String getStringa(ArrayList<String> lista, String sep) {
        /* variabili e costanti locali di lavoro */
        String uscita = "";
        boolean continua;
        StringBuffer buffer = null;

        try { // prova ad eseguire il codice
            /* controllo di congruità all'ingresso */
            continua = (lista != null);

            /* controlla che esista un separatore valido */
            if (sep == null) {
                sep = "";
            }// fine del blocco if

            /* crea l'istanza */
            if (continua) {
                buffer = new StringBuffer();
            }// fine del blocco if

            /* spazzola tutta la collezione */
            if (continua) {
                for (String stringa : lista) {
                    buffer.append(stringa);
                    buffer.append(sep);
                } // fine del ciclo for-each
            }// fine del blocco if

            /* valore finale */
            if (continua) {
                uscita = buffer.toString();
            }// fine del blocco if

            /* elimina l'ultimo separatore */
            if (uscita.endsWith(sep)) {
                uscita = uscita.substring(0, uscita.lastIndexOf(sep));
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return uscita;
    }


    /**
     * Crea una stringa da una lista di nomi.
     * <p/>
     * Ogni nome nella stringa viene diviso da uno spazio <br> Esegue solo se la lista non è nulla
     * <br>
     *
     * @param lista di nomi
     *
     * @return stringa di nomi separati dallo spazio
     */
    static String getStringaSpazio(ArrayList<String> lista) {
        /* invoca il metodo sovrascritto */
        return LibTesto.getStringa(lista, LibTesto.SPAZIO);
    }


    /**
     * Crea una stringa da una lista di nomi.
     * <p/>
     * Ogni nome nella stringa viene diviso da una virgola <br> Esegue solo se la lista non è nulla
     * <br>
     *
     * @param lista di nomi
     *
     * @return stringa di nomi separati dalla virgola
     */
    static String getStringaVirgola(ArrayList<String> lista) {
        /* invoca il metodo sovrascritto */
        return LibTesto.getStringa(lista, LibTesto.VIRGOLA);
    }


    /**
     * Crea una stringa da un'array di nomi.
     * <p/>
     * Ogni nome nella stringa viene diviso da una virgola, seguita da spazio <br>
     * Esegue solo se l'array non è nullo <br>
     *
     * @param lista di nomi
     *
     * @return stringa di nomi separati da virgola e spazio
     */
    static String getStringaVirgolaSpazio(ArrayList<String> lista) {
        /* invoca il metodo sovrascritto */
        return LibTesto.getStringa(lista, LibTesto.VIRGOLA + LibTesto.SPAZIO);
    }


    /**
     * Crea una stringa da una lista di nomi.
     * <p/>
     * Ogni nome nella stringa viene diviso da un tabulatore <br> Esegue solo se la lista non è
     * nulla <br>
     *
     * @param lista di nomi
     *
     * @return stringa di nomi separati dal tab
     */
    static String getStringaTab(ArrayList<String> lista) {
        /* invoca il metodo sovrascritto */
        return LibTesto.getStringa(lista, LibTesto.TAB);
    }


    /**
     * Crea una stringa da una lista di nomi.
     * <p/>
     * Ogni nome nella stringa viene diviso da un ritorno a capo <br> Esegue solo se la lista non è
     * nulla <br>
     *
     * @param lista di nomi
     *
     * @return stringa di nomi separati dal ritorno a capo
     */
    static String getStringaCapo(ArrayList<String> lista) {
        /* invoca il metodo sovrascritto */
        return LibTesto.getStringa(lista, LibTesto.CR);
    }


    /**
     * Crea una stringa da un'array di nomi.
     * <p/>
     * Ogni nome nella stringa viene diviso da una virgola <br> Esegue solo se l'array non è nullo
     * <br>
     *
     * @param array di nomi
     *
     * @return stringa di nomi separati dalla virgola
     */
    static String getStringaVirgola(String[] array) {
        /* invoca il metodo sovrascritto */
        return LibTesto.getStringaVirgola(Lib.Array.creaLista(array));
    }


    /**
     * Crea una stringa da un'array di nomi.
     * <p/>
     * Ogni nome nella stringa viene diviso da una virgola, seguita da spazio <br>
     * Esegue solo se l'array non è nullo <br>
     *
     * @param array di nomi
     *
     * @return stringa di nomi separati dalla virgola
     */
    static String getStringaVirgolaSpazio(String[] array) {
        /* invoca il metodo sovrascritto */
        return LibTesto.getStringaVirgolaSpazio(Lib.Array.creaLista(array));
    }


    /**
     * Crea una stringa da un'array di interi.
     * <p/>
     * Ogni numero nella stringa viene diviso da una virgola <br>
     * Esegue solo se l'array non è nullo <br>
     *
     * @param array l'array da convertire
     *
     * @return la corrispondente stringa di interi separati da virgola
     */
    static String getStringaVirgola(int[] array) {
        /* invoca il metodo sovrascritto */
        return LibTesto.getStringaVirgola(Lib.Array.intToString(array));
    }


    /**
     * Crea una stringa da una lista di interi.
     * <p/>
     * Ogni numero nella stringa viene diviso da una virgola <br>
     * Esegue solo se l'array non è nullo <br>
     *
     * @param lista di numeri interi
     *
     * @return la corrispondente stringa di interi separati da virgola
     */
    static String getStringaIntVirgola(ArrayList<Integer> lista) {
        /* variabili e costanti locali di lavoro */
        String stringa = "";
        ArrayList<String> listaStr;

        try { // prova ad eseguire il codice
            listaStr = new ArrayList<String>();

            for (Integer num : lista) {
                listaStr.add(num.toString());
            } // fine del ciclo for-each

            stringa = getStringaVirgola(listaStr);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return stringa;
    }

//    /**
//     * Separa una stringa in una lista di stringhe.
//     * <p/>
//     * @param stringa da separare
//     * @param delimitatore stringa di delimitazione
//     */
//    public static ArrayList<String> separa(String stringa, String delimitatore) {
//        /* variabili e costanti locali di lavoro */
//        ArrayList<String> lista = new ArrayList<String>();
//        int lunghezza = 0;
//        boolean continua;
//
//        try { // prova ad eseguire il codice
////            /* controllo di congruità all'ingresso */
////            continua = (lista != null);
////
////            if (continua) {
////                /* traverso tutta la collezione */
////                for (String stringa : lista) {
////                    lunghezza = Math.max(lunghezza, stringa.length());
////                } // fine del ciclo for-each
////            }// fine del blocco if
//
//        }
//        catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//
//        /* valore di ritorno */
//        return lista;
//    }


    /**
     * Restituisce il massimo di caratteri di una lista.
     * <p/>
     * La lista deve essere di stringhe <br> Restituisce il numero di caratteri della stringa più
     * lunga <br>
     *
     * @param lista di nomi
     *
     * @return numero massimo di caratteri zero se la lista è nulla o vuota
     */
    static int maxCaratteri(ArrayList<String> lista) {
        /* variabili e costanti locali di lavoro */
        int lunghezza = 0;
        boolean continua;

        try { // prova ad eseguire il codice
            /* controllo di congruità all'ingresso */
            continua = (lista != null);

            if (continua) {
                /* traverso tutta la collezione */
                for (String stringa : lista) {
                    lunghezza = Math.max(lunghezza, stringa.length());
                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return lunghezza;
    }


    /**
     * Restituisce il minimo di caratteri di una lista.
     * <p/>
     * La lista deve essere di stringhe <br> Restituisce il numero di caratteri della stringa più
     * corta <br>
     *
     * @param lista di nomi
     *
     * @return numero minimo di caratteri zero se la lista è nulla o vuota
     */
    static int minCaratteri(ArrayList<String> lista) {
        /* variabili e costanti locali di lavoro */
        int lunghezza = 0;
        boolean continua;

        try { // prova ad eseguire il codice
            /* controllo di congruità all'ingresso */
            continua = (lista != null);

            if (continua) {
                /* inizializza la variabile */
                /* altrimenti resterebbe sempre zero */
                if (lista.size() > 0) {
                    lunghezza = lista.get(0).length();
                }// fine del blocco if

                /* traverso tutta la collezione */
                for (String stringa : lista) {
                    lunghezza = Math.min(lunghezza, stringa.length());
                } // fine del ciclo for-each

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return lunghezza;
    }


    /**
     * Restituisce il testo piu' lungo di una lista.
     * <p/>
     * La lista deve essere di stringhe <br>
     *
     * @param lista di nomi
     *
     * @return testo più lungo vuoto se la lista è nulla o vuota
     */
    static String maxTesto(ArrayList<String> lista) {
        /* variabili e costanti locali di lavoro */
        String testoMax = "";
        boolean continua;

        try { // prova ad eseguire il codice
            /* controllo di congruità all'ingresso */
            continua = (lista != null);

            if (continua) {
                /* traverso tutta la collezione */
                for (String stringa : lista) {
                    if (stringa.length() > testoMax.length()) {
                        testoMax = stringa;
                    }// fine del blocco if

                } // fine del ciclo for-each

            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return testoMax;
    }


    /**
     * Restituisce il testo piu' corto di una lista.
     * <p/>
     * La lista deve essere di stringhe <br>
     *
     * @param lista di nomi
     *
     * @return testo più corto vuoto se la lista è nulla o vuota
     */
    static String minTesto(ArrayList<String> lista) {
        /* variabili e costanti locali di lavoro */
        String testoMin = "";
        boolean continua;

        try { // prova ad eseguire il codice
            /* controllo di congruità all'ingresso */
            continua = (lista != null);

            if (continua) {
                /* inizializza la variabile */
                /* altrimenti resterebbe sempre zero */
                if (lista.size() > 0) {
                    testoMin = lista.get(0);
                }// fine del blocco if

                /* traverso tutta la collezione */
                for (String stringa : lista) {
                    if (stringa.length() < testoMin.length()) {
                        testoMin = stringa;
                    }// fine del blocco if

                } // fine del ciclo for-each

            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return testoMin;
    }


    /**
     * Formattazione del numero.
     * <p/>
     * Inserisce il separatore ogni 3 cifre <br> Esegue solo se il numero non ha punti o virgole
     * <br>
     *
     * @param numero da formattare
     * @param sep    carattere separatore (di solito il punto)
     *
     * @return numero formattato
     */
    static String formatNumero(String numero, char sep) {
        /* variabile locale di lavoro */
        String formattato = "";
        boolean continua;
        String num3;
        String num6;
        String num9;
        String punto;
        String virgola;
        int len;
        int pos;

        try { // prova ad eseguire il codice

            /* controllo di congruità */
            formattato = numero;
            punto = ".";
            virgola = ",";
            pos = numero.indexOf(punto);
            continua = (pos == -1);

            if (continua) {
                pos = numero.indexOf(virgola);
                continua = (pos == -1);
            }// fine del blocco if

            formattato = numero;

            if (continua) {
                len = numero.length();
                if (len > 3) {
                    num3 = numero.substring(0, len - 3);
                    num3 += sep;
                    num3 += numero.substring(len - 3);
                    formattato = num3;

                    if (len > 6) {
                        num6 = num3.substring(0, len - 6);
                        num6 += sep;
                        num6 += num3.substring(len - 6);
                        formattato = num6;

                        if (len > 9) {
                            num9 = num6.substring(0, len - 9);
                            num9 += sep;
                            num9 += num6.substring(len - 9);
                            formattato = num9;
                        }// fine del blocco if
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return formattato;
    }


    /**
     * Formattazione del numero.
     * <p/>
     * Inserisce il separatore ogni 3 cifre <br> Esegue solo se il numero non ha punti o virgole
     * <br>
     *
     * @param numero da formattare
     *
     * @return numero formattato
     */
    static String formatNumero(String numero) {
        /* invoca il metodo delegato della classe */
        return LibTesto.formatNumero(numero, PUNTO);
    }


    /**
     * Formattazione del numero.
     * <p/>
     * Inserisce il separatore ogni 3 cifre <br>
     * Esegue solo se il numero non ha punti o virgole <br>
     *
     * @param numero da formattare
     *
     * @return numero formattato
     */
    static String formatNumero(int numero) {
        /* invoca il metodo delegato della classe */
        return LibTesto.formatNumero("" + numero);
    }


    /**
     * Formattazione del numero.
     * <p/>
     * Inserisce il separatore ogni 3 cifre <br>
     * Esegue solo se il numero non ha punti o virgole <br>
     *
     * @param numero da formattare
     *
     * @return numero formattato
     */
    static String formatNumero(double numero) {
        /* variabili e costanti locali di lavoro */
        String stringa = "";
        String numDoppio;
        String prima;
        String dopo = "";
        int intero;
        String punto = ".";
        String virgola = ",";

        try { // prova ad eseguire il codice
            numDoppio = Double.toString(numero);

            if (numDoppio.contains(punto)) {
                prima = Lib.Testo.prima(numDoppio, punto);

                if (Lib.Testo.isValida(numDoppio)) {
                    intero = new Integer(prima);
                    prima = LibTesto.formatNumero(intero);
                } // fine del blocco if

                dopo = Lib.Testo.dopo(numDoppio, punto);
            } else {
                intero = new Integer(numDoppio);
                prima = LibTesto.formatNumero(intero);
            } // fine del blocco if-else

            if (Lib.Testo.isValida(dopo)) {
                stringa = prima + virgola + dopo;
            } else {
                stringa = prima;
            } // fine del blocco if-else

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return stringa;
    }


    /**
     * Formattazione del numero.
     * <p/>
     * Inserisce il separatore ogni 3 cifre <br>
     *
     * @param numeroIta da formattare
     *
     * @return numero formattato
     */
    static String formatNum(String numeroIta) {
        /* variabile locale di lavoro */
        String formattato = "";
        boolean continua;
        String num3;
        String num6;
        String num9;
        String punto = ".";
        String tagVir = ",";
        int len;
        char sep = PUNTO;
        int pos;
        String prima = "";
        String dopo = "";


        try { // prova ad eseguire il codice

            /* controllo di congruità */
            formattato = numeroIta;
            continua = (Lib.Testo.isValida(numeroIta));

            if (continua) {
                if (numeroIta.contains(punto)) {
                    pos = numeroIta.indexOf(punto);
                    formattato = numeroIta.substring(0, pos);
                    dopo = numeroIta.substring(pos + 1);
                }// fine del blocco if
            }// fine del blocco if

            if (continua) {
                len = formattato.length();
                if (len > 3) {
                    num3 = formattato.substring(0, len - 3);
                    num3 += sep;
                    num3 += formattato.substring(len - 3);
                    formattato = num3;

                    if (len > 6) {
                        num6 = num3.substring(0, len - 6);
                        num6 += sep;
                        num6 += num3.substring(len - 6);
                        formattato = num6;

                        if (len > 9) {
                            num9 = num6.substring(0, len - 9);
                            num9 += sep;
                            num9 += num6.substring(len - 9);
                            formattato = num9;
                        }// fine del blocco if
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

            if (continua) {
                if (Lib.Testo.isValida(dopo)) {
                    if (!dopo.equals("0")) {
                        formattato += tagVir;
                        formattato += dopo;
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return formattato;
    }


    /**
     * Formattazione del numero.
     * <p/>
     * Solo per numeri italiani con la virgola decimale <br> Inserisce il separatore ogni 3 cifre
     * precedenti la virgola <br>
     *
     * @param numeroIta da formattare
     *
     * @return numero formattato
     */
    static String formatNumIta(String numeroIta) {
        /* variabile locale di lavoro */
        String formattato = "";
        boolean continua;
        String num3;
        String num6;
        String num9;
        String punto = ".";
        String tagPunto = "\\.";
        String tagVir = ",";
        int len;
        char sep = PUNTO;
        int pos;
        String prima = "";
        String dopo = "";
        String dopoTemp = "";


        try { // prova ad eseguire il codice

            /* controllo di congruità */
            formattato = numeroIta;
            continua = (Lib.Testo.isValida(numeroIta));

            if (continua) {
                if (numeroIta.contains(tagVir)) {
                    prima = Lib.Testo.prima(numeroIta, tagVir);
                    dopo = Lib.Testo.dopo(numeroIta, tagVir);
                } else {
                    prima = numeroIta;
                    dopo = "";
                }// fine del blocco if-else
            }// fine del blocco if

            if (continua) {
                if (numeroIta.contains(punto)) {
                    dopoTemp = Lib.Testo.dopo(numeroIta, punto);
                    if (dopoTemp.length() < 3) {
                        dopo = dopoTemp;
                        prima = Lib.Testo.prima(numeroIta, punto);
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

            if (continua) {
                if (prima.contains(punto)) {
                    prima = prima.replaceAll(tagPunto, "");
                }// fine del blocco if
            }// fine del blocco if

            if (continua) {
                len = prima.length();
                if (len > 3) {
                    num3 = prima.substring(0, len - 3);
                    num3 += sep;
                    num3 += prima.substring(len - 3);
                    prima = num3;

                    if (len > 6) {
                        num6 = num3.substring(0, len - 6);
                        num6 += sep;
                        num6 += num3.substring(len - 6);
                        prima = num6;

                        if (len > 9) {
                            num9 = num6.substring(0, len - 9);
                            num9 += sep;
                            num9 += num6.substring(len - 9);
                            prima = num9;
                        }// fine del blocco if
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

            if (continua) {
                formattato = prima;
                if (Lib.Testo.isValida(dopo)) {
                    if (!dopo.equals("0")) {
                        formattato += tagVir;
                        formattato += dopo;
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return formattato;
    }


    /**
     * Formattazione del valore booleano.
     * <p/>
     *
     * @param booleano da trasformare in stringa
     *
     * @return valore in forma di stringa
     */
    static String formatBool(boolean booleano) {
        /* variabili e costanti locali di lavoro */
        String testo = "";

        try { // prova ad eseguire il codice
            if (booleano) {
                testo = "true";
            } else {
                testo = "false";
            }// fine del blocco if-else
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return testo;
    }


    /**
     * Restituisce una Stringa da un Oggetto.
     * <p/>
     * Controlla che l'oggetto esista e che sia una Stringa <br> Effettua il casting <br>
     *
     * @param unOggetto oggetto su cui effettuare il casting
     *
     * @return una stringa col valore preso dall'oggetto se l'oggetto non era valido restituisce una
     *         stringa vuota e non null
     */
    static String getStringa(Object unOggetto) {
        /* variabile locale di lavoro */
        String testo = "";
        Number numero;
        Boolean bool;
        Date data;

        try { // prova ad eseguire il codice
            if (unOggetto != null) {
                if (unOggetto instanceof String) {
                    testo = (String)unOggetto;
                }// fine del blocco if

                if (unOggetto instanceof Number) {
                    numero = (Number)unOggetto;
                    testo = numero.toString();
                }// fine del blocco if

                if (unOggetto instanceof Boolean) {
                    bool = (Boolean)unOggetto;
                    testo = bool.toString();
                }// fine del blocco if

                if (unOggetto instanceof Date) {
                    data = (Date)unOggetto;
                    testo = Lib.Data.getStringa(data);
                }// fine del blocco if


            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return testo;
    }


    /**
     * Riempie una stringa con un carattere fino alla lunghezza specificata.
     * <p/>
     * Se la lunghezza della stringa è maggiore o uguale alla lunghezza richiesta la stringa non
     * viene modificata.
     *
     * @param stringaIn stringa da elaborare
     * @param carattere di riempimento
     * @param len       lunghezza finale
     * @param pos       posizione di inserimento (Posizione.inizio, Posizione.fine)
     *
     * @return la stringa elaborata
     */
    static String pad(String stringaIn, char carattere, int len, Posizione pos) {
        /* variabile locale di lavoro */
        String stringaOut = "";
        int diff;
        String insert = "";

        try { // prova ad eseguire il codice
            stringaOut = stringaIn;
            diff = len - stringaIn.length();
            if (diff > 0) {

                for (int k = 0; k < diff; k++) {
                    insert += carattere;
                } // fine del ciclo for

                switch (pos) {
                    case inizio:
                        stringaOut = insert + stringaOut;
                        break;
                    case fine:
                        stringaOut += insert;
                        break;
                    default: // caso non definito
                        break;
                } // fine del blocco switch

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return stringaOut;
    }


    /**
     * Concatena una serie di stringhe tra di loro separandole con una stringa data.
     * <p/>
     *
     * @param sep      stringa di separazione
     * @param stringhe da concatenare
     *
     * @return la stringa concatenata
     */
    static String concat(String sep, String... stringhe) {
        /* variabili e costanti locali di lavoro */
        String blocco = "";
        String stringa;

        try {    // prova ad eseguire il codice

            for (int k = 0; k < stringhe.length; k++) {

                stringa = stringhe[k];

                /* aggiunge la stringa al blocco  */
                if (Lib.Testo.isValida(stringa)) {
                    if (blocco.length() > 0) {
                        blocco += sep;
                    }// fine del blocco if
                    blocco += stringa;
                }// fine del blocco if

            } // fine del ciclo for

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return blocco;
    }


    /**
     * Concatena una serie di stringhe tra di loro separandole con spazio.
     * <p/>
     * Aggiunge uno spazio tra una stringa e l'altra.
     *
     * @param stringhe da concatenare
     *
     * @return la stringa concatenata
     */
    static String concatSpace(String... stringhe) {
        return concat(" ", stringhe);
    }


    /**
     * Concatena una serie di stringhe tra di loro separandole con return.
     * <p/>
     *
     * @param stringhe da concatenare
     *
     * @return la stringa concatenata
     */
    static String concatReturn(String... stringhe) {
        return concat("\n", stringhe);
    }


    /**
     * Rimuove eventuali stringhe collocate a inizio e/o fine stringa.
     * <p/>
     *
     * @param stringa da pulire
     * @param filtri  array delle stringhe da eliminare
     * @param inizio  true per eliminare le ricorrenze all'inizio
     * @param fine    true per eliminare le ricorrenze alla fine
     *
     * @return stringa ripulita
     */
    private static String trim(String stringa, String[] filtri, boolean inizio, boolean fine) {
        /* variabili e costanti locali di lavoro */
        boolean pulisci = false;
        String unFiltro;

        try { // prova ad eseguire il codice

            /* rimuove all'inizio */
            if (inizio) {

                while (true) {
                    /* spazzola i filtri e determina se la stringa va pulita */
                    pulisci = false;
                    for (int k = 0; k < filtri.length; k++) {
                        unFiltro = filtri[k];
                        if (stringa.startsWith(unFiltro)) {
                            pulisci = true;
                            break;
                        }// fine del blocco if-else
                    } // fine del ciclo for

                    /* se va pulita, esegue, altrimenti esce dal loop */
                    if (pulisci) {
                        stringa = stringa.substring(1);
                    } else {
                        break;
                    }// fine del blocco if-else
                }// fine del blocco while
            }// fine del blocco if

            /* rimuove alla fine */
            if (fine) {

                while (true) {
                    /* spazzola i filtri e determina se la stringa va pulita */
                    pulisci = false;
                    for (int k = 0; k < filtri.length; k++) {
                        unFiltro = filtri[k];
                        if (stringa.endsWith(unFiltro)) {
                            pulisci = true;
                            break;
                        }// fine del blocco if-else
                    } // fine del ciclo for

                    /* se va pulita, esegue, altrimenti esce dal loop */
                    if (pulisci) {
                        stringa = stringa.substring(0, stringa.length() - 1);
                    } else {
                        break;
                    }// fine del blocco if-else
                }// fine del blocco while
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return stringa;
    }


    /**
     * Rimuove gli eventuali spazi a inizio stringa.
     * <p/>
     *
     * @param stringa da pulire
     *
     * @return stringa ripulita dagli spazi iniziali
     */
    static String trimLeftSpc(String stringa) {
        /* valore di ritorno */
        String[] filtro = {SPAZIO};
        return trim(stringa, filtro, true, false);
    }


    /**
     * Rimuove gli eventuali spazi a fine stringa.
     * <p/>
     *
     * @param stringa da pulire
     *
     * @return stringa ripulita dagli spazi finali
     */
    static String trimRightSpc(String stringa) {
        /* valore di ritorno */
        String[] filtro = {SPAZIO};
        return trim(stringa, filtro, false, true);
    }


    /**
     * Rimuove gli eventuali spazi a inizio e fine stringa.
     * <p/>
     *
     * @param stringa da pulire
     *
     * @return stringa ripulita dagli spazi iniziali e finali
     */
    static String trimSpc(String stringa) {
        /* valore di ritorno */
        String[] filtro = {SPAZIO};
        return trim(stringa, filtro, true, true);
    }


    /**
     * Rimuove gli eventuali return a inizio stringa.
     * <p/>
     *
     * @param stringa da pulire
     *
     * @return stringa ripulita dai return iniziali
     */
    static String trimLeftRet(String stringa) {
        /* valore di ritorno */
        String[] filtro = {CR};
        return trim(stringa, filtro, true, false);
    }


    /**
     * Rimuove gli eventuali return a fine stringa.
     * <p/>
     *
     * @param stringa da pulire
     *
     * @return stringa ripulita dai return finali
     */
    static String trimRightRet(String stringa) {
        /* valore di ritorno */
        String[] filtro = {CR};
        return trim(stringa, filtro, false, true);
    }


    /**
     * Rimuove gli eventuali return a inizio e fine stringa.
     * <p/>
     *
     * @param stringa da pulire
     *
     * @return stringa ripulita dai return iniziali e finali
     */
    static String trimRet(String stringa) {
        /* valore di ritorno */
        String[] filtro = {CR};
        return trim(stringa, filtro, true, true);
    }


    /**
     * Rimuove gli eventuali spazi e return a inizio stringa.
     * <p/>
     *
     * @param stringa da pulire
     *
     * @return stringa ripulita da spazi e return iniziali
     */
    static String trimLeft(String stringa) {
        /* valore di ritorno */
        String[] filtro = {SPAZIO, CR};
        return trim(stringa, filtro, true, false);
    }


    /**
     * Rimuove gli eventuali spazi e return a fine stringa.
     * <p/>
     *
     * @param stringa da pulire
     *
     * @return stringa ripulita da spazi e return finali
     */
    static String trimRight(String stringa) {
        /* valore di ritorno */
        String[] filtro = {SPAZIO, CR};
        return trim(stringa, filtro, false, true);
    }


    /**
     * Rimuove gli eventuali spazi e return a inizio e fine stringa.
     * <p/>
     *
     * @param stringa da pulire
     *
     * @return stringa ripulita da spazi e return iniziali e finali
     */
    static String trim(String stringa) {
        /* valore di ritorno */
        String[] filtro = {SPAZIO, CR};
        return trim(stringa, filtro, true, true);
    }


    /**
     * Estrae una porzione di testo.
     * <p/>
     * Estremi esclusi <br>
     *
     * @param testoIn completo
     * @param tagIni  da cui iniziare ad estrarre il testo
     * @param tagEnd  termine del testo da estrarre
     *
     * @return stringa di testo estratto
     */
    static String estrae(String testoIn, String tagIni, String tagEnd) {
        /* invoca il metodo delegato della classe */
        return LibTesto.estrae(testoIn, tagIni, tagEnd, tagEnd);
    }


    /**
     * Estrae una porzione di testo.
     * <p/>
     * Estremi esclusi <br>
     *
     * @param testoIn   completo
     * @param tagIni    da cui iniziare ad estrarre il testo
     * @param tagEndUno termine del testo da estrarre
     * @param tagEndDue termine alternativo del testo da estrarre
     *
     * @return stringa di testo estratto che termina al primo dei due tag trovati
     */
    static String estrae(String testoIn, String tagIni, String tagEndUno, String tagEndDue) {
        /* variabili e costanti locali di lavoro */
        String testoOut = "";
        boolean continua;
        int posIni = 0;
        int posEndUno;
        int posEndDue;
        int posEnd = 0;

        try { // prova ad eseguire il codice
            testoOut = testoIn;
            continua = (Lib.Testo.isValida(testoIn) && Lib.Testo.isValida(tagIni));

            if (continua) {
                posIni = testoIn.indexOf(tagIni);
                continua = (posIni != -1);
            }// fine del blocco if

            if (continua) {
                posIni += tagIni.length();
                posEndUno = testoIn.indexOf(tagEndUno, posIni);
                posEndDue = testoIn.indexOf(tagEndDue, posIni);

                if ((posEndUno != -1) && (posEndDue != -1)) {
                    posEnd = Math.min(posEndUno, posEndDue);
                }// fine del blocco if

                if ((posEndUno == -1) && (posEndDue == -1)) {
                    posEnd = testoIn.length();
                }// fine del blocco if

                if ((posEndUno != -1) && (posEndDue == -1)) {
                    posEnd = posEndUno;
                }// fine del blocco if

                if ((posEndUno == -1) && (posEndDue != -1)) {
                    posEnd = posEndDue;
                }// fine del blocco if
            }// fine del blocco if

            if (continua) {
                testoOut = testoIn.substring(posIni, posEnd);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return testoOut;
    }


    /**
     * Estrae una porzione di testo.
     * <p/>
     * Estremi esclusi <br> Parte dall'inizio <br>
     *
     * @param testoIn   completo
     * @param tagEndUno termine del testo da estrarre
     * @param tagEndDue termine alternativo del testo da estrarre
     *
     * @return stringa di testo estratto che termina al primo dei due tag trovati
     */
    static String estraeEnd(String testoIn, String tagEndUno, String tagEndDue) {
        /* variabili e costanti locali di lavoro */
        String testoOut = "";
        boolean continua;
        int posEndUno;
        int posEndDue;
        int posEnd = 0;

        try { // prova ad eseguire il codice
            testoOut = testoIn;
            continua = (Lib.Testo.isValida(testoIn) && Lib.Testo.isValida(tagEndUno));


            if (continua) {
                posEndUno = testoIn.indexOf(tagEndUno);
                posEndDue = testoIn.indexOf(tagEndDue);

                if ((posEndUno != -1) && (posEndDue != -1)) {
                    posEnd = Math.min(posEndUno, posEndDue);
                }// fine del blocco if

                if ((posEndUno == -1) && (posEndDue == -1)) {
                    posEnd = testoIn.length();
                }// fine del blocco if

                if ((posEndUno != -1) && (posEndDue == -1)) {
                    posEnd = posEndUno;
                }// fine del blocco if

                if ((posEndUno == -1) && (posEndDue != -1)) {
                    posEnd = posEndDue;
                }// fine del blocco if
            }// fine del blocco if

            if (continua) {
                testoOut = testoIn.substring(0, posEnd);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return testoOut;
    }


    /**
     * Elimina eventuali doppie quadre in testa e in coda della stringa.
     * <p/>
     * Funziona solo se le quadre sono IN TESTA alla stringa <br>
     *
     * @param stringa da elaborare
     *
     * @return stringa con doppie quadre eliminate
     */
    static String levaQuadre(String stringa) {
        /* variabili e costanti locali di lavoro */
        String testo = "";
        String tagIni = "[[";
        String tagEnd = "]]";

        try { // prova ad eseguire il codice
            stringa = stringa.trim();

            if (stringa.startsWith(tagIni)) {
                stringa = stringa.substring(tagIni.length());
            }// fine del blocco if

            if (stringa.endsWith(tagEnd)) {
                stringa = stringa.substring(0, stringa.length() - tagEnd.length());
            }// fine del blocco if

            /* valore di ritorno */
            testo = stringa.trim();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return testo;
    }


    /**
     * Elimina eventuali parentesi in testa e in coda della stringa.
     * <p/>
     *
     * @param stringa da elaborare
     *
     * @return stringa con parentesi eliminate
     */
    static String levaParentesi(String stringa) {
        /* variabili e costanti locali di lavoro */
        String testo = "";
        String tagIni = "(";
        String tagEnd = ")";

        try { // prova ad eseguire il codice
            stringa = stringa.trim();

            if (stringa.startsWith(tagIni)) {
                stringa = stringa.substring(tagIni.length());
            }// fine del blocco if

            if (stringa.endsWith(tagEnd)) {
                stringa = stringa.substring(0, stringa.length() - tagEnd.length());
            }// fine del blocco if

            /* valore di ritorno */
            testo = stringa.trim();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return testo;
    }


    /**
     * Elimina (se esistenti) i tag <small> in testa ed in coda alla stringa.
     * <p/>
     *
     * @return stringa con i tag levati
     */
    static String levaSmall(String stringa) {
        /* variabili e costanti locali di lavoro */
        String testo = "";
        boolean continua;
        String tagIni = "<small>";
        String tagEnd = "</small>";

        try { // prova ad eseguire il codice

            /* controllo di congruità */
            testo = stringa.trim();
            continua = (Lib.Clas.isValidi(stringa));

            /* primo giro */
            if (continua) {
                if (testo.startsWith(tagIni)) {
                    testo = Lib.Testo.dopo(testo, tagIni);
                }// fine del blocco if
                continua = (Lib.Testo.isValida(testo));
            }// fine del blocco if

            if (continua) {
                if (testo.endsWith(tagEnd)) {
                    testo = Lib.Testo.prima(testo, tagEnd);
                }// fine del blocco if
                continua = (Lib.Testo.isValida(testo));
            }// fine del blocco if

            if (continua) {
                testo = testo.trim();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return testo;
    }


    /**
     * Sostituisce in testo tutte le occorrenze.
     * <p/>
     *
     * @param testoIn da elaborare
     * @param leva    stringa da eliminare
     * @param metti   stringa da inserire
     *
     * @return stringa modificata
     */
    static String replaceAll(String testoIn, String leva, String metti) {
        /* variabili e costanti locali di lavoro */
        String testoOut = "";
        int pos = 0;
        String prima = "";
        String dopo;

        try { // prova ad eseguire il codice
            testoOut = testoIn;

//            while (pos != -1) {
//                pos = testoOut.indexOf(leva);
//
//                if (pos != -1) {
//                    prima = testoOut.substring(0, pos);
//                    pos += leva.length();
//                    dopo = testoOut.substring(pos);
//
//                    testoOut = prima + metti + dopo;
//                }// fine del blocco if
//            }

            while (pos != -1) {
                pos = testoIn.indexOf(leva);
                if (pos != -1) {
                    prima += testoIn.substring(0, pos);
                    prima += metti;
                    pos += leva.length();
                    testoIn = testoIn.substring(pos);
                }// fine del blocco if
            }

            testoOut = prima + testoIn;
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return testoOut;
    }


    /**
     * Elimina il titolo.
     * <p/>
     * Elimina la prima riga del testo <br> Se non ci sono ritorni a capo, elimina tutto il testo
     * <br>
     *
     * @param testoIn da elaborare
     *
     * @return testo elaborato
     */
    static String levaTitolo(String testoIn) {
        String testoOut = "";
        boolean continua;
        String aCapo = "\n";
        int pos;

        try { // prova ad eseguire il codice
            continua = (Lib.Testo.isValida(testoIn));

            if (continua) {
                pos = testoIn.indexOf(aCapo);

                if (pos != -1) {
                    testoOut = testoIn.substring(pos + aCapo.length());
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return testoOut;
    }


    /**
     * Restituisce la posizione del primo tag trovato dei due.
     * <p/>
     *
     * @param testo  da elaborare
     * @param tagUno primo tag da cercare
     * @param tagDue secondo tag da cercare
     * @param inizio della ricerca
     *
     * @return posizione del primo tag -1 se non esiste nessuno dei due tag
     */
    static int posPrima(String testo, String tagUno, String tagDue, int inizio) {
        int pos = -1;
        boolean continua;
        int posUno = 0;
        int posDue = 0;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Testo.isValida(tagUno) && Lib.Testo.isValida(tagDue));

            if (continua) {
                posUno = testo.indexOf(tagUno, inizio);
                posDue = testo.indexOf(tagDue, inizio);
                continua = (posUno != -1) || (posDue != -1);
            }// fine del blocco if

            if (continua) {
                if (posUno != -1) {
                    pos = posUno;
                }// fine del blocco if

                if (posDue != -1) {
                    pos = posDue;
                }// fine del blocco if

                if ((posUno != -1) && (posDue != -1)) {
                    pos = Math.min(posUno, posDue);
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return pos;
    }


    /**
     * Restituisce il testo precedente il primo dei due tag.
     * <p/>
     *
     * @param testoIn da elaborare
     * @param tagUno  primo tag da cercare
     * @param tagDue  secondo tag da cercare
     *
     * @return testo precedente il primo tag vuoto se non esiste nessuno dei due tag
     */
    static String testoPrima(String testoIn, String tagUno, String tagDue) {
        String testoOut = "";
        boolean continua;
        int pos;

        try { // prova ad eseguire il codice
            /* recupera la posizione */
            pos = LibTesto.posPrima(testoIn, tagUno, tagDue, 0);
            continua = (pos != -1);

            if (continua) {
                testoOut = testoIn.substring(0, pos);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return testoOut;
    }


    /**
     * Recupera dal testo una substringa compresa tra tagIni e tagEnd.
     * <p/>
     * tagIniPre serve per posizionarsi nel testo prima dell'occorrenza di tagIni che interessa <br>
     * (ce ne potrebbero essere altre) <br> Trova la prima occorrenza del tagIniPre <br> Trova la
     * prima occorrenza del tagIni Trova la prima occorrenza del tagFine Esegue solo se tutti i tag
     * sono validi <br> I due tag tagIniPre e tagIni possono essere uguali <br>
     *
     * @param testo     completo da cui estrarre la substringa
     * @param tagIniPre tag iniziale per individuare la sezione di testo interessata
     * @param tagIni    tag iniziale della stringa desiderata
     * @param tagFine   tag finale della stringa desiderata
     *
     * @return testo della substringa l'intero testo se i tag non sono validi
     */
    static String subTesto(String testo, String tagIniPre, String tagIni, String tagFine) {
        /* variabili e costanti locali di lavoro */
        String subStringa = "";
        boolean continua;
        int posPre = 0;
        int posIni = 0;
        int posFine = 0;

        try { // prova ad eseguire il codice
            /* controllo di congruità iniziale */
            continua =
                    (LibTesto.isValida(testo) && LibTesto.isValida(tagIniPre) && LibTesto.isValida(
                            tagIni) && LibTesto.isValida(tagFine));

            /* posizionamento preliminare */
            if (continua) {
                if (!tagIniPre.equals(tagIni)) {
                    posPre = testo.indexOf(tagIniPre);
                    continua = (posPre != -1);
                    posPre += tagIniPre.length();
                }// fine del blocco if
            }// fine del blocco if

            /* posizione iniziale */
            if (continua) {
                posIni = testo.indexOf(tagIni, posPre);
                continua = (posIni != -1);
                posIni += tagIni.length();
            }// fine del blocco if

            /* posizione finale */
            if (continua) {
                posFine = testo.indexOf(tagFine, posIni);
                continua = (posFine != -1);
            }// fine del blocco if

            /* posizione finale */
            if (continua) {
                continua = (posFine > posIni) && (posFine < testo.length());
            }// fine del blocco if

            if (continua) {
                subStringa = testo.substring(posIni, posFine);
                subStringa = subStringa.trim();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return subStringa;
    }


    /**
     * Recupera dal testo una substringa compresa tra tagIni e tagEnd.
     * <p/>
     * Trova la prima occorrenza del tagIni Trova la prima occorrenza del tagFine Esegue solo se
     * tutti i tag sono validi <br>
     *
     * @param testo   completo da cui estrarre la substringa
     * @param tagIni  tag iniziale della stringa desiderata
     * @param tagFine tag finale della stringa desiderata
     *
     * @return testo della substringa l'intero testo se i tag non sono validi
     */
    static String subTesto(String testo, String tagIni, String tagFine) {
        /* invoca il metodo delegato della classe */
        return LibTesto.subTesto(testo, tagIni, tagIni, tagFine);
    }


    /**
     * Elimina le righe commentate.
     *
     * @param testoIn testo in ingresso
     *
     * @return testo in uscita
     */
    static String eliminaRigheCommentate(String testoIn) {
        /* variabili e costanti locali di lavoro */
        String testoOut = "";
        boolean continua;
        String tag = "//";
        String sep = "\n";
        String[] righeOld = null;
        ArrayList<String> righeNew = null;
        StringBuffer buffer = null;

        try { // prova ad eseguire il codice
            /* controllo di congruità e sicurezza di uscita */
            testoOut = testoIn;
            continua = (Lib.Testo.isValida(testoIn));

            if (continua) {
                righeOld = testoIn.split(sep);
                continua = (righeOld != null) && (righeOld.length > 0);
            }// fine del blocco if

            if (continua) {
                righeNew = new ArrayList<String>();
                for (String riga : righeOld) {
                    if (!riga.startsWith(tag)) {
                        righeNew.add(riga);
                    }// fine del blocco if
                } // fine del ciclo for-each
            }// fine del blocco if

            if (continua) {
                buffer = new StringBuffer();
                for (String riga : righeNew) {
                    buffer.append(riga);
                    buffer.append(sep);
                } // fine del ciclo for-each
            }// fine del blocco if

            if (continua) {
                testoOut = buffer.toString();
                testoOut = Lib.Testo.levaCoda(testoOut, sep);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return testoOut;
    }


    /**
     * Ritorna una stringa casuale della lunghezza richiesta.
     *
     * @param lunghezza della stringa
     *
     * @return stringa casuale
     */
    static String getTestoRandom(int lunghezza) {
        String testo = "";
        Character carattere;
        int codcar;
        Random generator = new Random();

        try { // prova ad eseguire il codice
            for (int k = 0; k < lunghezza; k++) {
                codcar = generator.nextInt(25) + 65;
                carattere = (char)codcar;
                testo += carattere;
            } // fine del ciclo for
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return testo;
    }


    /**
     * Ordina la lista in base alla prima colonna.
     * <p/>
     *
     * @param listaIn da ordinare
     *
     * @return lista ordinata
     */
    static ArrayList<QuattroStringhe> ordina(ArrayList<QuattroStringhe> listaIn) {
        /* variabili e costanti locali di lavoro */
        ArrayList<QuattroStringhe> listaOut = null;
        LinkedHashMap<String, QuattroStringhe> mappa;
        ArrayList<String> listaTemp;
        QuattroStringhe quattro;

        try { // prova ad eseguire il codice
            /* crea le istanze delle liste temporanee e di uscita */
            listaOut = new ArrayList<QuattroStringhe>();
            listaTemp = new ArrayList<String>();
            mappa = new LinkedHashMap<String, QuattroStringhe>();

            /* crea una lista della sola prima colonna ed una mappa */
            for (QuattroStringhe wrap : listaIn) {
                listaTemp.add(wrap.getPrima());
                mappa.put(wrap.getPrima(), wrap);
            } // fine del ciclo for-each

            /* ordina la prima colonna */
            listaTemp = Lib.Array.ordina(listaTemp);

            /* costruisce la lista in uscita secondo l'ordinamento */
            for (String nome : listaTemp) {
                quattro = mappa.get(nome);
                listaOut.add(quattro);
            } // fine del ciclo for-each

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return listaOut;
    }


    /**
     * Classe interna Enumerazione.
     * <p/>
     * Posizione nella stringa
     */
    public enum Posizione {

        inizio(),
        fine()

    }// fine della classe


}// fine della classe
