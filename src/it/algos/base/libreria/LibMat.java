/**
 * Title:     LibMat
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      17-gen-2004
 */
package it.algos.base.libreria;

import it.algos.base.errore.Errore;

import java.awt.Dimension;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Repository di funzionalita' matematiche. </p> Classe astratta con soli metodi statici <br>
 *
 * @author Alberto Colombo, Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 17-gen-2004 ore 9.41.24
 */
public abstract class LibMat {

    /**
     * Somma due dimensioni. <br>
     *
     * @param primaDim prima delle due dimensioni
     * @param secondaDim seconda delle due dimensioni
     *
     * @return la somma delle due dimensioni
     */
    public static Dimension sommaDimensioni(Dimension primaDim, Dimension secondaDim) {
        /* variabili e costanti locali di lavoro */
        Dimension dimensioneSomma = null;
        int larghezza = 0;
        int larghezzaPrima = 0;
        int larghezzaSeconda = 0;
        int altezza = 0;
        int altezzaPrima = 0;
        int altezzaSeconda = 0;

        try {    // prova ad eseguire il codice

            if (primaDim == null) {
                return secondaDim;
            }// fine del blocco if
            if (secondaDim == null) {
                return primaDim;
            }// fine del blocco if

            /* recupera i valori e calcola la larghezza somma */
            larghezzaPrima = primaDim.width;
            larghezzaSeconda = secondaDim.width;
            larghezza = larghezzaPrima + larghezzaSeconda;

            /* recupera i valori e calcola l'altezza somma */
            altezzaPrima = primaDim.height;
            altezzaSeconda = secondaDim.height;
            altezza = altezzaPrima + altezzaSeconda;

            /* crea la nuova dimensione */
            dimensioneSomma = new Dimension(larghezza, altezza);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return dimensioneSomma;
    } // fine del metodo


    /**
     * Sottrae una dimensione da un'altra. <br>
     *
     * @param primaDim prima delle due dimensioni (dalla quale sottrarre)
     * @param secondaDim seconda delle due dimensioni (da sottrarre)
     *
     * @return la somma delle due dimensioni
     */
    public static Dimension sottraeDimensioni(Dimension primaDim, Dimension secondaDim) {
        /* variabili e costanti locali di lavoro */
        Dimension dimensioneSomma = null;
        int larghezza = 0;
        int larghezzaPrima = 0;
        int larghezzaSeconda = 0;
        int altezza = 0;
        int altezzaPrima = 0;
        int altezzaSeconda = 0;

        try {    // prova ad eseguire il codice

            if (primaDim == null) {
                return new Dimension(0, 0);
            }// fine del blocco if
            if (secondaDim == null) {
                return primaDim;
            }// fine del blocco if

            /* recupera i valori e calcola la larghezza */
            larghezzaPrima = primaDim.width;
            larghezzaSeconda = secondaDim.width;
            larghezza = larghezzaPrima - larghezzaSeconda;

            /* recupera i valori e calcola l'altezza */
            altezzaPrima = primaDim.height;
            altezzaSeconda = secondaDim.height;
            altezza = altezzaPrima - altezzaSeconda;

            /* crea la nuova dimensione */
            dimensioneSomma = new Dimension(larghezza, altezza);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return dimensioneSomma;
    } // fine del metodo


    /**
     * Restituisce la massima di due dimensioni. </p> Viene calcolato separatamente il massimo dei
     * due valori di larghezza, ed il massimo dei due valori di altezza <br>
     *
     * @param primaDim prima delle due dimensioni
     * @param secondaDim seconda delle due dimensioni
     *
     * @return la dimensione col massimo dei valori
     */
    public static Dimension massimaDimensione(Dimension primaDim, Dimension secondaDim) {

        /* variabili e costanti locali di lavoro */
        Dimension dimensioneMassima = null;
        int larghezza = 0;
        int larghezzaPrima = 0;
        int larghezzaSeconda = 0;
        int altezza = 0;
        int altezzaPrima = 0;
        int altezzaSeconda = 0;

        try {    // prova ad eseguire il codice
            if (primaDim == null) {
                return secondaDim;
            }// fine del blocco if
            if (secondaDim == null) {
                return primaDim;
            }// fine del blocco if

            /* recupera i valori e calcola la larghezza massima */
            larghezzaPrima = primaDim.width;
            larghezzaSeconda = secondaDim.width;
            larghezza = Math.max(larghezzaPrima, larghezzaSeconda);

            /* recupera i valori e calcola l'altezza massima */
            altezzaPrima = primaDim.height;
            altezzaSeconda = secondaDim.height;
            altezza = Math.max(altezzaPrima, altezzaSeconda);

            /* crea la nuova dimensione */
            dimensioneMassima = new Dimension(larghezza, altezza);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return dimensioneMassima;
    } // fine del metodo


    /**
     * Restituisce la minima di due dimensioni. </p> Viene calcolato separatamente il minimo dei due
     * valori di larghezza, ed il minimo dei due valori di altezza <br>
     *
     * @param primaDim prima delle due dimensioni
     * @param secondaDim seconda delle due dimensioni
     *
     * @return la dimensione col massimo dei valori
     */
    public static Dimension minimaDimensione(Dimension primaDim, Dimension secondaDim) {

        /* variabili e costanti locali di lavoro */
        Dimension dimensioneMinima = null;
        int larghezza = 0;
        int larghezzaPrima = 0;
        int larghezzaSeconda = 0;
        int altezza = 0;
        int altezzaPrima = 0;
        int altezzaSeconda = 0;

        try {    // prova ad eseguire il codice
            if (primaDim == null) {
                return secondaDim;
            }// fine del blocco if
            if (secondaDim == null) {
                return primaDim;
            }// fine del blocco if

            /* recupera i valori e calcola la larghezza massima */
            larghezzaPrima = primaDim.width;
            larghezzaSeconda = secondaDim.width;
            larghezza = Math.min(larghezzaPrima, larghezzaSeconda);

            /* recupera i valori e calcola l'altezza massima */
            altezzaPrima = primaDim.height;
            altezzaSeconda = secondaDim.height;
            altezza = Math.min(altezzaPrima, altezzaSeconda);

            /* crea la nuova dimensione */
            dimensioneMinima = new Dimension(larghezza, altezza);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return dimensioneMinima;
    } // fine del metodo


    /**
     * Verifica che una dimensione sia contenuta entro un'altra. </p> Gli estremi sono compresi,
     * quindi se due valori sono uguali la risposta e' affermativa <br>
     *
     * @param dimContenitore dimensione contenente
     * @param dimContenuto dimensione contenuta
     *
     * @return vero se la seconda e piu' piccola della prima <br>
     */
    public static boolean isContenuta(Dimension dimContenitore, Dimension dimContenuto) {
        /* variabili e costanti locali di lavoro */
        boolean contenuta = false;

        try { // prova ad eseguire il codice

            if (dimContenitore != null) {
                if (dimContenuto != null) {
                    if ((dimContenitore.width >= dimContenuto.width) &&
                            (dimContenitore.height >= dimContenuto.height)) {
                        contenuta = true;
                    }/* fine del blocco if */
                } else {
                    /* se il contenuto e' null, e' sempre
                     * contenibile nel contenitore */
                    contenuta = true;
                }// fine del blocco if-else
            } else {
                /* se il contenitore e' nullo, non puo' contenere nulla */
                contenuta = false;
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }/* fine del blocco try-catch */

        /* valore di ritorno */
        return contenuta;
    }// fine del metodo


    /**
     * Controlla se un determinato intero esiste in un array.
     *
     * @param array array di riferimento
     * @param valore intero da ricercare
     *
     * @return true se esiste
     */
    static boolean isEsiste(int[] array, int valore) {
        /* variabili locali di lavoro */
        boolean esiste = false;
        int intero = 0;

        try { // prova ad eseguire il codice
            for (int k = 0; k < array.length; k++) {
                intero = array[k];
                if (intero == valore) {
                    esiste = true;
                    break;
                }// fine del blocco if
            } // fine del ciclo for
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return esiste;
    }


    /**
     * Controlla se un determinato intero esiste in una lista.
     *
     * @param array arrayList di riferimento (oggetti Integer)
     * @param valore intero da ricercare
     *
     * @return true se esiste
     */
    static boolean isEsiste(ArrayList array, int valore) {
        /* variabili locali di lavoro */
        boolean esiste = false;
        Integer numero = null;
        int intero = 0;

        try { // prova ad eseguire il codice
            for (int k = 0; k < array.size(); k++) {
                numero = (Integer)array.get(k);
                intero = numero.intValue();
                if (intero == valore) {
                    esiste = true;
                    break;
                }// fine del blocco if
            } // fine del ciclo for
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return esiste;
    }


    /**
     * Ritorna il numero di cifre decimali di un numero double.
     * <p/>
     *
     * @param numero da controllare
     *
     * @return il numero di cifre decimali
     */
    static int contaCifreDecimali(double numero) {
        /* variabili e costanti locali di lavoro */
        int numCifre = 0;
        String stringa;
        BigDecimal bd;

        try {    // prova ad eseguire il codice

            /* trasforma il double in stringa */
            stringa = Double.toString(numero);

            /* crea un BigDecimal dalla stringa */
            bd = new BigDecimal(stringa);

            /* rimuove gli eventuali zeri finali */
            bd = bd.stripTrailingZeros();

            /* conta i decimali */
            numCifre = bd.scale();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return numCifre;
    }


    /**
     * Arrotondamento matematico dei decimali al numero prefissato.
     * <p/>
     *
     * @param numero da arrotondare
     * @param numDecimali cifre decimali da considerare
     *
     * @return numero arrotondato
     */
    static double arrotonda(double numero, int numDecimali) {
        /* variabili e costanti locali di lavoro */
        double arrotondato = 0;
        BigDecimal bd;
        BigDecimal newDec;

        try {    // prova ad eseguire il codice

            bd = new BigDecimal(numero);
            newDec = bd.setScale(numDecimali, BigDecimal.ROUND_HALF_UP);
            arrotondato = newDec.doubleValue();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return arrotondato;
    }


    /**
     * Ritorna il minimo intero di una serie.
     * <p/>
     *
     * @param num elenco di interi
     *
     * @return minimo intero
     */
    static int getMin(int... num) {
        /* variabili e costanti locali di lavoro */
        int min = 2000000000;
        int curr;

        try {    // prova ad eseguire il codice

            for (int i = 0; i < num.length; i++) {
                curr = num[i];
                if (curr < min) {
                    min = curr;
                }// fine del blocco if
            }

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return min;
    }


    /**
     * Ritorna il massimo intero di una serie.
     * <p/>
     *
     * @param num elenco di interi
     *
     * @return massimo intero
     */
    static int getMax(int... num) {
        /* variabili e costanti locali di lavoro */
        int max = 0;
        int curr;

        try {    // prova ad eseguire il codice

            for (int i = 0; i < num.length; i++) {
                curr = num[i];
                if (curr > max) {
                    max = curr;
                }// fine del blocco if
            }

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return max;
    }


    /**
     * Converte un oggetto in un int.
     * <p/>
     * Riconosce oggetti String e Integer.<br> Se l'oggetto e' nullo o non convertibile ritorna
     * zero.<br>
     *
     * @param stringa da convertire
     *
     * @return l'intero corrispondente all'oggetto
     */
    static int getInt(String stringa) {
        /* variabili e costanti locali di lavoro */
        int intero = 0;

//        if (unOggetto != null) {

//            if (unOggetto instanceof String) {
//                try {    // prova ad eseguire il codice
//                    numero = Integer.decode(unOggetto);
//                    intero = numero.intValue();
//                } catch (Exception unErrore) {    // intercetta l'errore
//                    /* mostra il messaggio di errore */
//                    Errore.crea(unErrore);
//                } /* fine del blocco try-catch */
//            } /* fine del blocco if */

        try {    // prova ad eseguire il codice
            stringa = stringa.replaceAll("\\.", "");
            stringa = stringa.replaceAll(",", "");
            stringa = stringa.trim();
            intero = Integer.parseInt(stringa);
        } catch (Exception unErrore) {    // intercetta l'errore
            /* in caso di eccezione non fa nulla - ritornera' zero */
        } /* fine del blocco try-catch */
//        } /* fine del blocco if */

        /* valore di ritorno */
        return intero;
    }


    /**
     * Restituisce un numero puro.
     * <p/>
     *
     * @return il numero
     */
    static double getNum(String numeroInglese) {
        /* variabili e costanti locali di lavoro */
        double numero = 0;
        boolean continua;
        String valNumero = "";
        String tagVir = ",";
        String tagPar = "(";
        String tagQuad = "[[";

        try { // prova ad eseguire il codice
            continua = (Lib.Testo.isValida(numeroInglese));

            /* patch riconosciuta*/
            if (continua) {
                if (numeroInglese.contains(tagPar)) {
                    numeroInglese = Lib.Testo.prima(numeroInglese, tagPar).trim();
                }// fine del blocco if
                continua = (Lib.Testo.isValida(numeroInglese));
            }// fine del blocco if

            /* patch riconosciuta*/
            if (continua) {
                if (numeroInglese.contains(tagQuad)) {
                    numeroInglese = Lib.Testo.prima(numeroInglese, tagQuad).trim();
                }// fine del blocco if
                continua = (Lib.Testo.isValida(numeroInglese));
            }// fine del blocco if

            if (continua) {
                if (numeroInglese.contains(tagVir)) {
                    valNumero = numeroInglese.replaceAll(tagVir, "");
                } else {
                    valNumero = numeroInglese;
                }// fine del blocco if-else
                numero = Double.valueOf(valNumero);
            }// fine del blocco if

            if (continua) {
                try { // prova ad eseguire il codice
                    numero = Double.valueOf(valNumero);
                } catch (Exception unErrore) { // intercetta l'errore
                }// fine del blocco try-catch
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return numero;
    }


    /**
     * Restituisce un numero puro di due decimali.
     * <p/>
     *
     * @return il numero
     */
    static double getNum2(String numeroInglese) {
        /* variabili e costanti locali di lavoro */
        Double numero = 0.0;
        boolean continua;
        String valore = "";
        String tag = ".";
        int pos;
        String prima;
        String dopo;

        try { // prova ad eseguire il codice
            continua = (Lib.Testo.isValida(numeroInglese));

            if (continua) {
                numero = LibMat.getNum(numeroInglese);
                continua = (numero != null);
            }// fine del blocco if

            if (continua) {
                valore = numero.toString();
                continua = (Lib.Testo.isValida(valore));
            }// fine del blocco if

            if (continua) {
                if (valore.contains(tag)) {
                    pos = valore.indexOf(tag);
                    prima = valore.substring(0, pos);
                    dopo = valore.substring(pos + 1);
                    if (dopo.length() > 2) {
                        dopo = dopo.substring(0, 2);
                    }// fine del blocco if
                    valore = prima;
                    valore += tag;
                    valore += dopo;
                }// fine del blocco if
                continua = (Lib.Testo.isValida(valore));
            }// fine del blocco if

            if (continua) {
                try { // prova ad eseguire il codice
                    numero = Double.valueOf(valore);
                } catch (Exception unErrore) { // intercetta l'errore
                }// fine del blocco try-catch
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return numero;
    }


    /**
     * Restituisce una stringa con due decimali.
     * <p/>
     *
     * @return la stringa del numero
     */
    static String getValNumIng(String numeroInglese) {
        /* variabili e costanti locali di lavoro */
        String valore = "";
        String valoreIng;
        boolean continua;
        Double numero;
        String prima;
        String dopo;
        String tagPunto = ".";
        String tagVir = ",";

        try { // prova ad eseguire il codice
            numero = getNum2(numeroInglese);
            continua = (numero != null);

            if (continua) {
                try { // prova ad eseguire il codice
                    valoreIng = numero.toString();
                    prima = Lib.Testo.prima(valoreIng, tagPunto);
                    dopo = Lib.Testo.dopo(valoreIng, tagPunto);
                    valore = prima + tagVir + dopo;
                    valore = Lib.Testo.formatNumIta(valore);
                } catch (Exception unErrore) { // intercetta l'errore
                }// fine del blocco try-catch
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valore;
    }


    /**
     * Restituisce una stringa con due decimalii.
     * <p/>
     *
     * @return la stringa del numero
     */
    static String getValNum(Double numero) {
        /* variabili e costanti locali di lavoro */
        String valore = "";
        boolean continua;
        String tag = ".";
        int pos;
        String prima;
        String dopo;

        try { // prova ad eseguire il codice
            try { // prova ad eseguire il codice
                valore = numero.toString();
                continua = true;
            } catch (Exception unErrore) { // intercetta l'errore
                continua = false;
            }// fine del blocco try-catch

            if (continua) {
                if (valore.contains(tag)) {
                    pos = valore.indexOf(tag);
                    prima = valore.substring(0, pos);
                    dopo = valore.substring(pos + 1);
                    if (dopo.length() > 2) {
                        dopo = dopo.substring(0, 2);
                    }// fine del blocco if
                    valore = prima;
                    valore += tag;
                    valore += dopo;
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valore;
    }


    /**
     * Recupera la percentuale di due numeri.
     * <p/>
     *
     * @param tot primo numero
     * @param par secondonumero
     *
     * @return la percentuale del primo rispetto al secondo
     */
    static int getPer(int tot, int par) {
        /* variabili e costanti locali di lavoro */
        int percentuale = 0;

        try { // prova ad eseguire il codice


            if (par != 0) {
                par *= 100;
                percentuale = par / tot;
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return percentuale;
    }


    /**
     * Controlla se un numero è un multiplo esatto dell'altro.
     * <p/>
     *
     * @param numero da controllare
     * @param multiplo moltiplicatore
     *
     * @return vero se il numero è un multiplo esatto senza resto
     */
    static boolean isMultiplo(int numero, int multiplo) {
        return ((numero % multiplo)==0);
    }


    /**
     * Controlla se un numero è un multiplo esatto dell'altro.
     * <p/>
     *
     * @param numero da controllare
     *
     * @return vero se il numero è un multiplo esatto senza resto
     */
    static boolean isMultiplo(int numero) {
        /* invoca il metodo sovrascritto della superclasse */
        return LibMat.isMultiplo(numero, 100);
    }

}// fine della classe
