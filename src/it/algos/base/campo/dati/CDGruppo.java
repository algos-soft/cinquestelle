/**
 * Title:        CDGruppo.java
 * Package:      it.algos.base.campo.dati
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Ceresa, Colombo, Valbonesi
 * @version 1.0  /
 * Creato:       il 23 ottobre 2003 alle 11.51
 */
package it.algos.base.campo.dati;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.tipodati.tipoarchivio.TATesto;
import it.algos.base.campo.tipodati.tipoarchivio.TipoArchivio;
import it.algos.base.campo.tipodati.tipomemoria.TMArrayBool;
import it.algos.base.campo.tipodati.tipomemoria.TipoMemoria;
import it.algos.base.campo.tipodati.tipovideo.TVArrayBool;
import it.algos.base.campo.tipodati.tipovideo.TipoVideo;
import it.algos.base.database.util.Operatore;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;

import java.util.ArrayList;

/**
 * Classe concreta per implementare un oggetto da CDBase;
 * <p/>
 * Questa classe concreta e' responsabile di: <br>
 * A - Implementare i metodi astratti della superclasse per regolare le
 * conversioni tra i vari tipi di dati <br>
 *
 * @author Guido Andrea Ceresa
 * @author gac
 * @version 1.0  /  23 ottobre 2003 ore 11.51
 */
public final class CDGruppo extends CDBase {

    private static final TipoArchivio TIPO_ARCHIVIO = TATesto.getIstanza();

    private static final TipoMemoria TIPO_MEMORIA = TMArrayBool.getIstanza();

    private static final TipoVideo TIPO_VIDEO = TVArrayBool.getIstanza();

    /**
     * istanza della classe specializzata per contenere i dati
     */
    private ArrayList<String> titoli = null;


    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo utilizzando eventuali valori di default
     */
    public CDGruppo() {
        /** rimanda al costruttore di questa classe */
        this(null);
    } /* fine del metodo costruttore base */


    /**
     * Costruttore completo
     *
     * @param unCampoParente campo 'contenitore' di questo oggetto
     */
    public CDGruppo(Campo unCampoParente) {
        /** rimanda al costruttore della superclasse */
        super(unCampoParente, TIPO_ARCHIVIO, TIPO_MEMORIA, TIPO_VIDEO);

        /** regolazioni iniziali di riferimenti e variabili */
        try {                                   // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore completo */


    /**
     * Regolazioni iniziali di riferimenti e variabili
     * Metodo chiamato direttamente dal costruttore
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        /* assegna l'icona specifica per il tipo di campo */
        this.setIcona(ICONA_CAMPO_NUM);

        this.titoli = new ArrayList<String>();

        /* operatore di ricerca di default */
        this.setOperatoreRicercaDefault(Operatore.MASCHERA);

    } /* fine del metodo inizia */

//    /**
//     * Allinea le variabili del Campo: da Memoria verso Archivio.
//     * </p>
//     * Vengono effettuate le conversioni tra i varii tipi di dati <br>
//     * Parte dalla variabile Memoria (gia' regolata), e regola
//     * di conseguenza Archivio <br>
//     * La variabile archivio e' allineata per la registrazione <br>
//     */
//    public void memoriaArchivio() {
//        /* variabili e costanti locali di lavoro */
//        Object oggettoMemoria;
//        ArrayList<Boolean> memoria = null;
//        String stringa = "";
//        String carattere = "";
//        boolean continua = true;
//
//        try { // prova ad eseguire il codice
//
//            super.memoriaArchivio();
//
//            /* recupera il valore in memoria */
//            oggettoMemoria = this.getMemoria();
//
//            /* controlla che il valore in memoria sia
//             * del tipo appropriato */
//            if (!(oggettoMemoria instanceof ArrayList)) {
//                continua = false;
//            }// fine del blocco if
//
//            /* effettua il casting */
//            if (continua) {
//                memoria = (ArrayList<Boolean>)oggettoMemoria;
//            }// fine del blocco if
//
//            /* logica di conversione */
//            if (continua) {
//                stringa = "";
//                for (boolean valore : memoria) {
//                    carattere = "0";
//                    if (valore) {
//                        carattere = "1";
//                    }// fine del blocco if-else
//                    stringa += carattere;
//                } // fine del ciclo for-each
//            }// fine del blocco if
//
//            /* registra il valore in Archivio */
//            if (continua) {
//                this.setArchivio(stringa);
//            }// fine del blocco if
//
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//    }

//    /**
//     * Allinea le variabili del Campo: da Archivio verso Memoria.
//     * <p/>
//     * Vengono effettuate le conversioni tra i varii tipi di dati <br>
//     * Parte dalla variabile Archivio (gia' regolata), e regola
//     * di conseguenza Memoria, Backup <br>
//     * Metodo sovrascritto nelle sottoclassi <br>
//     */
//    public void archivioMemoria() {
//        /* variabili e costanti locali di lavoro */
//        boolean continua = true;
//        Object oggettoArchivio = null;
//        String stringaArchivio = "";
//        String car = "";
//        boolean bool;
//        int k = 0;
//        ArrayList<Boolean> memoria;
//        ArrayList valori = null;
//
//        try {    // prova ad eseguire il codice
//
//            super.archivioMemoria();
//
//            /* crea un oggetto memoria vuoto */
//            memoria = new ArrayList<Boolean>();
//
//            /* recupera il valore archivio */
//            oggettoArchivio = this.getArchivio();
//
//            /* controlla che sia del tipo appropriato */
//            if (!(oggettoArchivio instanceof String)) {
//                continua = false;
//            }// fine del blocco if
//
//            /* effettua il casting */
//            if (continua) {
//                stringaArchivio = (String)oggettoArchivio;
//            }// fine del blocco if
//
//            /* Logica di conversione.
//             * la lunghezza dell'array di booleani � sempre pari
//             * al numero di elementi della lista valori.
//             * I valori vengono letti dalla stringa di archivio,
//             * il carattere "1" corrisponde a true.
//             * Se i caratteri della stringa in archivio sono
//             * di meno degli elementi dell'array memoria, gli
//             * elementi senza corrispondenza restano false. */
//            if (continua) {
//
//                valori = this.getListaValori();
//
//                for (k = 0; k < valori.size(); k++) {
//                    bool = false;
//                    if (stringaArchivio.length() > k) {
//                        car = stringaArchivio.substring(k, k + 1);
//                        bool = false;
//                        if (car.equals("1")) {
//                            bool = true;
//                        }// fine del blocco if
//                    }// fine del blocco if
//                    memoria.add(bool);
//                } // fine del ciclo for
//
//            }// fine del blocco if
//
//            /* registra il valore memoria e backup */
//            if (continua) {
//                this.setMemoria(memoria);
//                this.memoriaBackup();
//            }// fine del blocco if
//
//        } catch (Exception unErrore) {    // intercetta l'errore
//            Errore.crea(unErrore);
//        } /* fine del blocco try-catch */
//    }


    /**
     * Converte un valore da Archivio a Memoria.
     * <p/>
     * Chiamato solo se Archivio non � nullo e non e' vuoto.
     *
     * @param archivio il valore Archivio
     *
     * @return il valore Memoria corrispondente
     */
    protected Object memoriaDaArchivio(Object archivio) {
        /* variabili e costanti locali di lavoro */
        ArrayList<Boolean> memoria = null;
        boolean continua = true;
        String stringaArchivio = "";
        ArrayList valori = null;
        int k = 0;
        boolean bool;
        String car = "";


        try {    // prova ad eseguire il codice

            /* crea un oggetto memoria vuoto */
            memoria = new ArrayList<Boolean>();

            /* controlla che sia del tipo appropriato */
            if (!(archivio instanceof String)) {
                continua = false;
            }// fine del blocco if

            /* effettua il casting */
            if (continua) {
                stringaArchivio = (String)archivio;
            }// fine del blocco if

            /* Logica di conversione.
             * la lunghezza dell'array di booleani � sempre pari
             * al numero di elementi della lista valori.
             * I valori vengono letti dalla stringa di archivio,
             * il carattere "1" corrisponde a true.
             * Se i caratteri della stringa in archivio sono
             * di meno degli elementi dell'array memoria, gli
             * elementi senza corrispondenza restano false. */
            if (continua) {

                valori = this.getListaValori();

                for (k = 0; k < valori.size(); k++) {
                    bool = false;
                    if (stringaArchivio.length() > k) {
                        car = stringaArchivio.substring(k, k + 1);
                        bool = false;
                        if (car.equals("1")) {
                            bool = true;
                        }// fine del blocco if
                    }// fine del blocco if
                    memoria.add(bool);
                } // fine del ciclo for

            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return memoria;
    }


    /**
     * Converte un valore da Memoria a Archivio.
     * <p/>
     * Chiamato solo se Memoria non � nullo e non e' vuoto.
     *
     * @param memoria il valore Memoria
     *
     * @return il valore Archivio corrispondente
     */
    protected Object archivioDaMemoria(Object memoria) {
        /* variabili e costanti locali di lavoro */
        ArrayList<Boolean> listaMemoria = null;
        String stringa = "";
        String carattere = "";
        boolean continua = true;

        try {    // prova ad eseguire il codice

            /* controlla che il valore in memoria sia
             * del tipo appropriato */
            if (!(memoria instanceof ArrayList)) {
                continua = false;
            }// fine del blocco if

            /* effettua il casting */
            if (continua) {
                listaMemoria = (ArrayList<Boolean>)memoria;
            }// fine del blocco if

            /* logica di conversione */
            if (continua) {
                stringa = "";
                for (boolean valore : listaMemoria) {
                    carattere = "0";
                    if (valore) {
                        carattere = "1";
                    }// fine del blocco if-else
                    stringa += carattere;
                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return stringa;
    }


    /**
     * Verifica se il valore Archivio e' vuoto.
     * <p/>
     * Sovrascrive il metodo della superclasse.
     * Il valore e' vuoto se si verifica uno dei seguenti casi:
     * - e' nullo
     * - non e' una stringa
     * - il valore numerico della stringa e' zero
     *
     * @return true se il valore Archivio e' vuoto
     */
    protected boolean isValoreArchivioVuoto() {
        boolean isVuoto = true;
        Object unValore = null;
        String unaStringa = null;
        Integer unIntero = null;

        try {    // prova ad eseguire il codice
            unValore = this.getArchivio();
            if (unValore != null) {
                if (unValore instanceof String) {
                    unaStringa = (String)unValore;
                    unIntero = 0;
                    try { // prova ad eseguire il codice
                        unIntero = Integer.decode(unaStringa);
                    } catch (Exception unErrore) { // intercetta l'errore
                    }// fine del blocco try-catch

                    if (unIntero != 0) {
                        isVuoto = false;
                    }// fine del blocco if
                } /* fine del blocco if */
            } /* fine del blocco if */
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        return isVuoto;
    } /* fine del metodo */


    /**
     * Verifica se il valore Memoria e' vuoto.
     * <p/>
     * Sovrascrive il metodo della superclasse.
     * Il valore e' vuoto se si verifica uno dei seguenti casi:
     * - e' nullo
     * - non e' una ArrayList
     * - la ArrayList non ha elementi
     * - tutti i booleani della ArrayList sono falsi
     *
     * @return true se il valore memoria e' vuoto
     */
    protected boolean isValoreMemoriaVuoto() {
        boolean isVuoto = true;
        Object unValore = null;
        ArrayList<Boolean> listaValori = null;

        try {    // prova ad eseguire il codice
            unValore = this.getMemoria();
            if (unValore != null) {
                if (unValore instanceof ArrayList) {
                    listaValori = (ArrayList<Boolean>)unValore;
                    if (listaValori.size() > 0) {
                        for (boolean b : listaValori) {
                            if (b) {
                                isVuoto = false;
                            }// fine del blocco if
                        } // fine del ciclo for-each
                    }// fine del blocco if
                }// fine del blocco if

            } /* fine del blocco if */
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        return isVuoto;
    } /* fine del metodo */


    /**
     * Ritorna un oggetto che rappresenta il valore del campo
     * per l'utilizzo in un filtro.
     * <p/>
     * Si usa per costruire un filtro rappresentante il valore
     * di memoria corrente del campo.<br>
     * Sovrascrive il metodo della superclasse.
     * Crea la maschera di ricerca.
     *
     * @return il valore di filtro
     */
    public Object getValoreFiltro() {
        /* variabili e costanti locali di lavoro */
        String valoreFiltro = "";
        Object oggettoMemoria;
        ArrayList<Boolean> listaMemoria = null;
        int numElementi = 0;
        boolean valoreMemoria;


        try {    // prova ad eseguire il codice

            numElementi = this.getListaValori().size();
            oggettoMemoria = this.getMemoria();
            if (oggettoMemoria instanceof ArrayList) {
                listaMemoria = (ArrayList<Boolean>)oggettoMemoria;
            }// fine del blocco if

            for (int k = 0; k < numElementi; k++) {
                if (listaMemoria != null) {
                    if (listaMemoria.size() >= k) {
                        valoreMemoria = listaMemoria.get(k);
                        if (valoreMemoria) {
                            valoreFiltro += "1";
                        } else {
                            valoreFiltro += "_";
                        }// fine del blocco if-else
                    } else {
                        valoreFiltro += "_";
                    }// fine del blocco if-else


                } else {
                    valoreFiltro += "_";
                }// fine del blocco if-else


            } // fine del ciclo for

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return valoreFiltro;
    }


    /**
     * regola il valore del campo in memoria.
     */
    public void setMemoria(Object unValoreMemoria) {
        super.setMemoria(unValoreMemoria);
    } /* fine del metodo setter */


    /**
     * lista dei valori alla creazione
     */
    public void setValoriInterni(ArrayList valoriIniziali) {
        /* variabili e costanti locali di lavoro */
        ArrayList<String> lista = new ArrayList<String>();

        /* se sono stringhe passano direttamente, altrimenti usa
         * il metodo toString() dell'oggetto */
        for (Object oggetto : valoriIniziali) {
            if (oggetto instanceof String) {
                lista.add((String)oggetto);
            } else {
                lista.add(oggetto.toString());
            }// fine del blocco if-else
        } // fine del ciclo for

        this.titoli = lista;

    } /* fine del metodo setter */


    /**
     * elenco dei valori alla creazione (di solito stringa)
     */
    public void setValoriInterni(String unaStringaValori) {
        this.setValoriInterni(Lib.Array.creaLista(unaStringaValori));
    } /* fine del metodo setter */


    /**
     * array dei valori alla creazione
     */
    public void setValoriInterni(String[] unaStringaValori) {
        this.setValoriInterni(Lib.Testo.getStringaVirgola(unaStringaValori));
    } /* fine del metodo setter */


    /**
     * Recupera la lista dei valori (oggetti di tipo Object).
     *
     * @return arrayList di valori
     */
    public ArrayList getListaValori() {
        return this.titoli;
    }

}// fine della classe