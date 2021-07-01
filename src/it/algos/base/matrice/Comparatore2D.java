/**
 * Title:     Comparatore2D
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      4-ott-2004
 */
package it.algos.base.matrice;

import it.algos.base.errore.Errore;
import it.algos.base.wrapper.IntBool;
import it.algos.base.wrapper.IntBoolBool;

import java.util.Comparator;

/**
 * Comparatore di due arrays di oggetti in base al valore
 * di una colonna.
 * </p>
 * Questa classe: <ul>
 * <li> Compara due array di oggetti (tipicamente righe di una matrice
 * bidimensionale) per determinarne l'ordine</li>
 * <li> La comparazione viene eseguita in base al valore contenuto
 * in una colonna specificata nel costruttore</li>
 * <li> Per avere un risultato congruo, entrambi gli oggetti da
 * comparare devono implementare l'interfaccia Comparable e
 * devono essere della stessa classe.</li>
 * <li> Se uno dei due oggetti non implementa Comparable e l'altro si',
 * quello che non implementa Comparable viene considerato
 * precedente all'altro.</li>
 * <li> Se nessuno dei due oggetti implementa Comparable,
 * vengono considerati uguali.</li>
 * <li> Se entrambi gli oggetti implementano Comparable:
 * - se sono della stessa classe, vengono confrontati.<br>
 * - se non sono della stessa classe, genera un errore.<br></li>
 * <li> Se entrambi gli oggetti sono String, effettua la comparazione
 * in modalita' case-sensitive o case-insensitive a seconda del flag.
 * </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 4-ott-2004 ore 8.56.25
 */
public final class Comparatore2D extends Object implements Comparator {

    /**
     * costanti per il risultato
     */
    private static final int PRIMA = -1;

    private static final int UGUALE = 0;

    private static final int DOPO = +1;

    /**
     * Elenco di colonne sulle quali
     * effettuare la comparazione.<br>
     * Ogni elemento e' un oggetto IntBoolBool, dove:
     * - l'int rappresenta l'indice della colonna
     * (0 per la prima)<br>
     * - il primo boolean il verso
     * (true = ascendente, false = discendente)<br>
     * - il secondo boolean l'opzione case-sensitive (solo per le stringhe)
     * (true = case sensitive, false = case insensitive)<br>
     */
    private IntBoolBool[] colonne = new IntBoolBool[0];


    /**
     * Costruttore completo con parametri. <br>
     *
     * @param colonne elenco degli indici delle colonne di comparazione
     * Array di oggetti IntBoolBool, dove:
     * - l'int rappresenta l'indice della colonna da comparare
     * (0 per la prima)<br>
     * - il primo boolean il verso di ordinamento della colonna
     * (true = ascendente, false = discendente)
     * - il secondo boolean l'opzione case-sensitive (solo per le stringhe)
     * (true = case sensitive, false = case insensitive)<br>
     */
    public Comparatore2D(IntBoolBool[] colonne) {
        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza coi parametri */
        this.setColonne(colonne);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Costruttore con colonne e verso. <br>
     *
     * @param colonne elenco degli indici delle colonne di comparazione
     * Array di oggetti IntBool, dove:
     * - l'int rappresenta l'indice della colonna da comparare
     * (0 per la prima)<br>
     * - il boolean il verso di ordinamento della colonna
     * (true = ascendente, false = discendente)<br>
     * Il flag case-sensitive e' impostato di default a true.
     */
    public Comparatore2D(IntBool[] colonne) {

        /* variabili e costanti locali di lavoro */
        IntBoolBool[] nuoveColonne = null;

        nuoveColonne = new IntBoolBool[colonne.length];
        for (int k = 0; k < colonne.length; k++) {
            nuoveColonne[k].setInt(colonne[k].getInt());
            nuoveColonne[k].setBool1(colonne[k].getBool());
            nuoveColonne[k].setBool2(true);
        } // fine del ciclo for

        /* regola le variabili di istanza coi parametri */
        this.setColonne(nuoveColonne);

    }// fine del metodo costruttore completo


    /**
     * Costruttore con colonne. <br>
     *
     * @param colonne elenco degli indici delle colonne di comparazione
     * Array di oggetti IntBool, dove:
     * - l'int rappresenta l'indice della colonna da comparare
     * (0 per la prima)<br>
     * Il verso delle colonne e' impostato di default a ascendente.<br>
     * Il flag case-sensitive e' impostato di default a true.<br>
     */
    public Comparatore2D(int[] colonne) {

        /* variabili e costanti locali di lavoro */
        IntBoolBool[] nuoveColonne = null;

        nuoveColonne = new IntBoolBool[colonne.length];
        for (int k = 0; k < colonne.length; k++) {
            nuoveColonne[k].setInt(colonne[k]);
            nuoveColonne[k].setBool1(true);
            nuoveColonne[k].setBool2(true);
        } // fine del ciclo for

        /* regola le variabili di istanza coi parametri */
        this.setColonne(nuoveColonne);

    }// fine del metodo costruttore completo


    /**
     * Costruttore con una colonna. <br>
     *
     * @param colonna indice della colonne di comparazione
     * Il verso della colonna e' impostato di default a ascendente.<br>
     * Il flag case-sensitive e' impostato di default a false.<br>
     */
    public Comparatore2D(int colonna) {

        IntBoolBool[] nuovaColonna = new IntBoolBool[1];
        nuovaColonna[0] = new IntBoolBool(colonna, true, false);
        this.setColonne(nuovaColonna);

    }// fine del metodo costruttore completo


    /**
     * Costruttore con una colonna e verso. <br>
     *
     * @param colonna indice della colonne di comparazione
     * @param verso true per ascendente, false per discendente
     * Il flag case-sensitive e' impostato di default a false.<br>
     */
    public Comparatore2D(int colonna, boolean verso) {

        IntBoolBool[] nuovaColonna = new IntBoolBool[1];
        nuovaColonna[0] = new IntBoolBool(colonna, verso, false);
        this.setColonne(nuovaColonna);

    }// fine del metodo costruttore completo


    /**
     * Costruttore con una colonna, verso e flag case-sensitive. <br>
     *
     * @param colonna indice della colonne di comparazione
     * @param verso true per ascendente, false per discendente
     * @param sensitive true per comparazione case-sensitive
     */
    public Comparatore2D(int colonna, boolean verso, boolean sensitive) {

        IntBoolBool[] nuovaColonna = new IntBoolBool[1];
        nuovaColonna[0] = new IntBoolBool(colonna, verso, sensitive);
        this.setColonne(nuovaColonna);

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
     * Compara i due argomenti per determinarne l'ordine.
     * Chiamato dal metodo Arrays.sort di Java
     * Ritorna PRIMA(-1),  UGUALE(0), o DOPO(+1) se il primo
     * argomento e' minore, uguale o maggiore del secondo.
     * <p/>
     *
     * @param o1 il primo oggetto (Object[])
     * @param o2 il secondo oggetto (Object[])
     *
     * @return -1, 0 oppure 1
     */
    public int compare(Object o1, Object o2) {
        /* variabili e costanti locali di lavoro */
        int risultato = UGUALE;
        boolean continua = true;
        Object[] a1 = null;
        Object[] a2 = null;

        try {    // prova ad eseguire il codice

            /* tratta i due parametri come Object[]*/
            if (continua) {
                if (o1 instanceof Object[]) {
                    a1 = (Object[])o1;
                } else {
                    continua = false;
                    throw new Exception("Il primo parametro non e' un array di Object.");
                }// fine del blocco if-else
                if (o2 instanceof Object[]) {
                    a2 = (Object[])o2;
                } else {
                    continua = false;
                    throw new Exception("Il secondo parametro non e' un array di Object.");
                }// fine del blocco if-else
            }// fine del blocco if

            /*
            * Spazzola le colonne di comparazione
            * e compara i due arrays su ogni colonna
            * fino a quando il risultato non e' UGUALE
            * o fino all'esaurimento delle colonne
            * di comparazione
            */
            if (continua) {
                boolean terminato = false;
                int i = 0;
                int colonna = 0;
                boolean verso = false;
                boolean caseSens = false;
                int risultatoColonna = 0;
                while (terminato == false) {

                    /*
                     * recupera l'indice e il verso della colonna
                     * di comparazione
                     */
                    colonna = this.getIndiceColonna(i);
                    verso = this.getVersoColonna(i);
                    caseSens = this.getCaseColonna(i);

                    /* compara i due arrays sulla colonna */
                    risultatoColonna = this.comparaColonna(a1, a2, colonna, verso, caseSens);

                    /*
                     * se il risultato non e' UGUALE o se ha
                     * esaurito le colonne, esce
                     */
                    if (risultatoColonna != UGUALE || i >= this.quanteColonne() - 1) {
                        terminato = true;
                    }// fine del blocco if

                    /* incrementa il contatore di loop */
                    i++;

                } /* fine del blocco while */

                /* usa il risultato dell'ultima colonna esaminata */
                risultato = risultatoColonna;

            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return risultato;
    }


    /**
     * Compara due array di oggetti basandosi su una data colonna.
     * <p/>
     * I due array devono avere lo stesso numero di elementi,
     * altrimenti solleva una eccezione.<br>
     * Per avere un risultato congruo, entrambi gli oggetti da
     * comparare devono implementare l'interfaccia Comparable e
     * devono essere della stessa classe.<br>
     * Se uno dei due oggetti non implementa Comparable e l'altro si',
     * quello che non implementa Comparable viene considerato
     * precedente all'altro.<br>
     * Se nessuno dei due oggetti implementa Comparable,
     * vengono considerati uguali.<br>
     * Se entrambi gli oggetti implementano Comparable:
     * - se sono della stessa classe, vengono confrontati.
     * - se non sono della stessa classe, genera un errore.<br>
     * Se entrambi gli oggetti sono String, effettua la comparazione
     * in modalita' case-sensitive o case-insensitive a seconda
     * del parametro caseSensitive.<br>
     * Ritorna PRIMA(-1),  UGUALE(0), o DOPO(+1) se il primo
     * argomento e' minore, uguale o maggiore del secondo, in funzione
     * del verso di ordinamento richiesto.
     *
     * @param o1 il primo array
     * @param o2 il secondo array
     * @param i l'indice della colonna da confrontare (0 per la prima)
     * @param asc true se il verso di ordinamento e' ascendente
     * (false = discendente)
     * @param c true per effettuare la comparazione case-sensitive
     * quando si tratta di stringhe
     *
     * @return PRIMA, UGUALE oppure DOPO
     */
    private int comparaColonna(Object[] o1, Object[] o2, int i, boolean asc, boolean c) {
        /* variabili e costanti locali di lavoro */
        int risultato = UGUALE;
        boolean continua = true;
        boolean compara = false;
        Object obj1 = null;
        Object obj2 = null;
        Comparable comp1 = null;
        Comparable comp2 = null;
        Class classe1 = null;
        Class classe2 = null;

        try {    // prova ad eseguire il codice

            /*
             * controlla che i due array abbiano lo stesso
             * numero di elementi
             */
            if (continua) {
                if (o1.length != o2.length) {
                    continua = false;
                    throw new Exception("I due array non hanno lo stesso numero di elementi.");
                }// fine del blocco if
            }// fine del blocco if

            /*
             * controlla che la colonna richiesta esista negli arrays
             * e recupera gli oggetti da confrontare
             */
            if (continua) {
                if (i < o1.length) {
                    obj1 = o1[i];
                    obj2 = o2[i];
                } else {
                    continua = false;
                    throw new Exception("Colonna specificata inesistente negli arrays.");
                }// fine del blocco if-else
            }// fine del blocco if

            /*
             * patch - se si tratta di Boolean, li trasforma in Integer 0/1
             * perche' la classe Boolean non impelemta Comparable
             */
            if (continua) {
                if (obj1 instanceof Boolean) {
                    if (((Boolean)obj1).booleanValue()) {
                        obj1 = new Integer(1);
                    } else {
                        obj1 = new Integer(0);
                    }// fine del blocco if-else
                }// fine del blocco if
                if (obj2 instanceof Boolean) {
                    if (((Boolean)obj2).booleanValue()) {
                        obj2 = new Integer(1);
                    } else {
                        obj2 = new Integer(0);
                    }// fine del blocco if-else
                }// fine del blocco if
            }// fine del blocco if

            /* tratta i due oggetti come Comparable */
            if (continua) {
                if (obj1 instanceof Comparable) {
                    comp1 = (Comparable)obj1;
                }// fine del blocco if
                if (obj2 instanceof Comparable) {
                    comp2 = (Comparable)obj2;
                }// fine del blocco if
            }// fine del blocco if

            /*
             * controlla se nessuno, uno, o due oggetti sono
             * dei Comparable e si comporta di conseguenza
             */
            if (continua) {
                if (comp1 != null) {    // comp1 non nullo
                    if (comp2 != null) {    // comp1 non nullo, comp2 non nullo
                        compara = true; // esegue la comparazione
                    } else {   // comp1 non nullo, comp2 nullo
                        risultato = DOPO;
                        continua = false;
                    }// fine del blocco if-else
                } else { // comp1 nullo
                    if (comp2 != null) {    // comp1 nullo, comp2 non nullo
                        risultato = PRIMA;
                        continua = false;
                    } else {   // comp1 nullo, comp2 nullo
                        risultato = UGUALE;
                        continua = false;
                    }// fine del blocco if-else
                }// fine del blocco if-else
            }// fine del blocco if

            /*
             * A questo punto ho due Comparable validi.
             * Recupero la classe e controllo se li posso confrontare.
             */
            if (continua) {
                classe1 = comp1.getClass();
                classe2 = comp2.getClass();
                if (classe1 == classe2) {
                    compara = true;
                } else {
                    continua = false;
                    throw new Exception("Classi non comparabili.");
                }// fine del blocco if-else
            }// fine del blocco if

            /*
             * A questo punto ho due Comparable della stessa classe.
             * Se la classe e' String, e il flag caseSensitive e' spento,
             * trasformo i valori da comparare in minuscolo.
             */
            if (continua) {
                if (comp1 instanceof String) {
                    if (c == false) {
                        comp1 = ((String)comp1).toLowerCase();
                        comp2 = ((String)comp2).toLowerCase();
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

            /*
            * A questo punto posso effettuare la comparazione.
            */
            if (continua) {
                if (compara) {
                    risultato = comp1.compareTo(comp2);
                    if (risultato > 0) {
                        risultato = DOPO;
                    } else {
                        if (risultato < 0) {
                            risultato = PRIMA;
                        } else {
                            risultato = UGUALE;
                        }// fine del blocco if-else
                    }// fine del blocco if-else
                }// fine del blocco if
            }// fine del blocco if

            /*
             * Al termine, se il verso richiesto e' discendente,
             * giro il risultato al contrario.
             */
            if (asc == false) {
                switch (risultato) {
                    case PRIMA:
                        risultato = DOPO;
                        break;
                    case DOPO:
                        risultato = PRIMA;
                        break;
                    default:
                        break;
                }
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return risultato;
    } // fine del metodo


    /**
     * Ritorna il numero di colonne di comparazione. <br>
     */
    private int quanteColonne() {
        /* variabili e costanti locali di lavoro */
        int quanteColonne = 0;

        try {    // prova ad eseguire il codice
            quanteColonne = this.getColonne().length;
        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return quanteColonne;
    } // fine del metodo


    private IntBoolBool[] getColonne() {
        return colonne;
    }


    private void setColonne(IntBoolBool[] colonne) {
        this.colonne = colonne;
    }


    /**
     * Recupera un elemento dalle colonne di comparazione. <br>
     *
     * @param i l'indice nell'array delle colonne (0 per la prima)
     *
     * @return l'elemento di tipo IntBoolean
     */
    private IntBoolBool getColonna(int i) {
        /* variabili e costanti locali di lavoro */
        IntBoolBool elemento = null;

        try {    // prova ad eseguire il codice
            elemento = (IntBoolBool)this.getColonne()[i];
        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return elemento;
    } // fine del metodo


    /**
     * Recupera l'indice di una colonna di comparazione. <br>
     *
     * @param i l'indice nell'array delle colonne (0 per la prima)
     *
     * @return l'indice della colonna di comparazione (0 per la prima)
     */
    private int getIndiceColonna(int i) {
        /* variabili e costanti locali di lavoro */
        int indice = 0;

        try {    // prova ad eseguire il codice
            indice = this.getColonna(i).getInt();
        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return indice;
    } // fine del metodo


    /**
     * Recupera il verso di una colonna di comparazione. <br>
     *
     * @param i l'indice nell'array delle colonne (0 per la prima)
     *
     * @return il verso della colonna di comparazione
     *         (true = ascendente, false = discendente)
     */
    private boolean getVersoColonna(int i) {
        /* variabili e costanti locali di lavoro */
        boolean verso = false;

        try {    // prova ad eseguire il codice
            verso = this.getColonna(i).getBool1();
        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return verso;
    } // fine del metodo


    /**
     * Recupera flag caseSensitive di una colonna di comparazione. <br>
     *
     * @param i l'indice nell'array delle colonne (0 per la prima)
     *
     * @return il flag caseSensitive della colonna di comparazione
     *         (true = case-sensitive, false = case-insensitive)
     */
    private boolean getCaseColonna(int i) {
        /* variabili e costanti locali di lavoro */
        boolean caseSensitive = false;

        try {    // prova ad eseguire il codice
            caseSensitive = this.getColonna(i).getBool2();
        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return caseSensitive;
    } // fine del metodo


}// fine della classe
