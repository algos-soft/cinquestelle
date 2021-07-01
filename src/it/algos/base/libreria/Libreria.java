/**
 * Title:        Libreria.java
 * Package:      it.algos.base.libreria
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 4 ottobre 2002 alle 13.47
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2002  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
// Package di questa classe e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.libreria;

import it.algos.base.campo.base.Campo;
import it.algos.base.costante.CostanteCarattere;
import it.algos.base.errore.Errore;
import it.algos.base.modello.Modello;
import it.algos.base.modulo.Modulo;
import it.algos.base.progetto.Progetto;
import it.algos.base.wrapper.DueStringhe;

import javax.swing.text.JTextComponent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

//-----------------------------------------------------------------------------
// Classe principale
//-----------------------------------------------------------------------------


/**
 * Questa classe astratta e' responsabile di:<br> A - Raccogliere metodi di utilita' generale <br> B
 * - Usa solo metodi statici
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  4 ottobre 2002 ore 13.47
 */
public abstract class Libreria {

    //-------------------------------------------------------------------------
    // Costanti statiche della classe    (private,pubbliche)  (class variables)
    //-------------------------------------------------------------------------
    /**
     * nome di questa classe (per i messaggi di errore)
     */
    private static final String NOME_CLASSE = "Libreria";


    /**
     * Costruttore base senza parametri <br> Indispensabile anche se non viene utilizzato (anche
     * solo per compilazione in sviluppo) <br> Rimanda al costruttore completo utilizzando eventuali
     * valori di default
     */
    public Libreria() {
        /** rimanda al costruttore della superclasse */
        super();
    } /* fine del metodo costruttore base */

    //-------------------------------------------------------------------------
    // Metodi statici della classe                              (class methods)
    //-------------------------------------------------------------------------


    /**
     * Converte un oggetto in un int.
     * <p/>
     * Riconosce oggetti String e Integer.<br> Se l'oggetto e' nullo o non convertibile ritorna
     * zero.<br>
     *
     * @return l'intero corrispondente all'oggetto
     */
    public static int objToInt(Object unOggetto) {
        return getInt(unOggetto);
    } /* fine del metodo */


    /**
     * Converte una lista di oggetti in un array di int
     */
    public static int[] objToInt(ArrayList unaListaOggetti) {
        /** variabili e costanti locali di lavoro */
        int dim;
        int[] arrayInt = null;

        /**  */
        try {    // prova ad eseguire il codice
            /** dimensiona la risposta */
            dim = unaListaOggetti.size();
            arrayInt = new int[dim];

            /** invoca il metodo delegato per ogni singolo elemento */
            for (int k = 0; k < dim; k++) {
                arrayInt[k] = objToInt(unaListaOggetti.get(k));
            } /* fine del blocco for */
        } catch (Exception unErrore) {    // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /** valore di ritorno */
        return arrayInt;
    } /* fine del metodo */


    /**
     * Converte un oggetto in un boolean.
     * <p/>
     * Riconosce oggetti Boolean.<br> Se l'oggetto e' nullo o non convertibile ritorna false.<br>
     *
     * @return il booleano corrispondente all'oggetto
     */
    public static boolean getBool(Object unOggetto) {
        /** variabili e costanti locali di lavoro */
        boolean bool = false;

        if (unOggetto != null) {
            if (unOggetto instanceof Boolean) {
                try { // prova ad eseguire il codice
                    Boolean unBooleano = (Boolean)unOggetto;
                    bool = unBooleano.booleanValue();
                } catch (Exception unErrore) { // intercetta l'errore
                    Errore.crea(unErrore);
                }// fine del blocco try-catch
            } /* fine del blocco if */

            /* caso String */
            if (unOggetto instanceof String) {
                String oggetto = (String)unOggetto;
                if (oggetto.equalsIgnoreCase("true")) {
                    bool = true;
                } else {
                    bool = false;
                }// fine del blocco if-else
            }// fine del blocco if

            /* caso Integer */
            if (unOggetto instanceof Integer) {
                Integer oggetto = (Integer)unOggetto;
                if (oggetto.intValue() == 1) {
                    bool = true;
                } else {
                    bool = false;
                }// fine del blocco if-else
            }// fine del blocco if

        }// fine del blocco if

        /** valore di ritorno */
        return bool;
    }


    /**
     * Converte un oggetto in un boolean.
     * <p/>
     * Riconosce oggetti Boolean.<br> Se l'oggetto e' nullo o non convertibile ritorna false.<br>
     *
     * @return il booleano corrispondente all'oggetto
     */
    public static boolean objToBool(Object unOggetto) {
        return getBool(unOggetto);
    } /* fine del metodo */


    /**
     * Converte un oggetto in una Stringa.
     * <p/>
     *  Se l'oggetto e' nullo o non convertibile ritorna stringa vuota.
     *
     * @return la stringa corrispondente all'oggetto
     */
    public static String getString(Object unOggetto) {
        /* variabili e costanti locali di lavoro */
        String stringa="";

        if (unOggetto != null) {
            if (unOggetto instanceof String) {
                stringa = (String)unOggetto;
            } /* fine del blocco if */
        }// fine del blocco if

        /* valore di ritorno */
        return stringa;
    } /* fine del metodo */



    /**
     * Converte un oggetto in un int.
     * <p/>
     * Riconosce oggetti String e Integer.<br> Se l'oggetto e' nullo o non convertibile ritorna
     * zero.<br>
     *
     * @return l'intero corrispondente all'oggetto
     */
    public static int getInt(Object unOggetto) {
        /* variabili e costanti locali di lavoro */
        int intero = 0;
        Number numero;

        if (unOggetto != null) {

            if (unOggetto instanceof Number) {
                try {    // prova ad eseguire il codice
                    numero = (Number)unOggetto;
                    intero = numero.intValue();
                } catch (Exception unErrore) {    // intercetta l'errore
                    /* mostra il messaggio di errore */
                    Errore.crea(unErrore);
                } /* fine del blocco try-catch */
            } /* fine del blocco if */

            if (unOggetto instanceof String) {
                try {    // prova ad eseguire il codice
                    intero = Integer.parseInt((String)unOggetto);
                } catch (Exception unErrore) {    // intercetta l'errore
                    /* in caso di eccezione non fa nulla - ritornera' zero */
                } /* fine del blocco try-catch */
            } /* fine del blocco if */
        }// fine del blocco if

        /* valore di ritorno */
        return intero;
    } /* fine del metodo */


    /**
     * Converte un oggetto in un double.
     * <p/>
     * Riconosce oggetti String e Integer.<br> Se l'oggetto e' nullo o non convertibile ritorna
     * zero.<br>
     *
     * @return il double corrispondente all'oggetto
     */
    public static double getDouble(Object unOggetto) {
        /* variabili e costanti locali di lavoro */
        double doppio = 0;
        Number numero;

        if (unOggetto != null) {

            if (unOggetto instanceof Number) {
                try {    // prova ad eseguire il codice
                    numero = (Number)unOggetto;
                    doppio = numero.doubleValue();
                } catch (Exception unErrore) {    // intercetta l'errore
                    /* mostra il messaggio di errore */
                    Errore.crea(unErrore);
                } /* fine del blocco try-catch */
            } /* fine del blocco if */

            if (unOggetto instanceof String) {
                try {    // prova ad eseguire il codice
                    doppio = Double.parseDouble((String)unOggetto);
                } catch (Exception unErrore) {    // intercetta l'errore
                    /* in caso di eccezione non fa nulla - ritornera' zero */
                } /* fine del blocco try-catch */
            } /* fine del blocco if */
        }// fine del blocco if

        /* valore di ritorno */
        return doppio;
    } /* fine del metodo */


    /**
     * Restituisce una data da un Oggetto.
     * <p/>
     * Controlla che l'oggetto esista <br> Controlla che sia della classe prevista<br> Effettua il
     * casting<br>
     *
     * @param unOggetto oggetto su cui effettuare il casting
     *
     * @return una data (java.util.Date) col valore preso dall'oggetto se l'oggetto non era valido
     *         restituisce la data convenzionalmente vuota
     */
    public static Date getDate(Object unOggetto) {
        /* variabile locale di lavoro */
        Date data;
        Date dataOut = Lib.Data.getVuota();
        SimpleDateFormat df;
        String stringa;

        try { // prova ad eseguire il codice

            if (unOggetto != null) {

                if (unOggetto instanceof String) {
                    stringa = (String)unOggetto;
                    df = Progetto.getDateFormat();

                    try {    // prova ad eseguire il codice
                        data = df.parse(stringa);
                        dataOut = data;
                    } catch (Exception unErrore) {    // intercetta l'errore
                        /* in caso di eccezione non fa nulla - ritornera' null */
                        int a = 87;
                    } /* fine del blocco try-catch */
                } /* fine del blocco if */

                if (unOggetto instanceof Date) {
                    dataOut = (Date)unOggetto;
                }// fine del blocco if

            }

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return dataOut;
    }


    /**
     * Formatta la stringa dei titoli delle colonne (nomi dei campi), mettendo per primo il codice
     * (se esiste nella stringa)
     *
     * @param unaStringa titoli delle colonne in una lista
     *
     * @return titoli riordinati
     */
    public static String riposizionaColonnaCodice(String unaStringa) {
        /** variabile locale di lavoro */
        String unaStringaRiordinata = unaStringa;
        int pos = 0;
        int lun = 0;

        /** posizione del titolo del campo chiave */
        pos = unaStringa.indexOf(Modello.NOME_CAMPO_CHIAVE);

        /** se esiste, lo sposta */
        if (pos > 0) {
            unaStringaRiordinata = Modello.NOME_CAMPO_CHIAVE;
            unaStringaRiordinata += ",";
            unaStringaRiordinata += unaStringa.substring(0, pos - 1);
            lun = Modello.NOME_CAMPO_CHIAVE.length();
            unaStringaRiordinata += unaStringa.substring(pos + lun, unaStringa.length());
        } /** fine del blocco if/else */

        /** valore di ritorno */
        return unaStringaRiordinata;
    } /* fine del metodo */


    /**
     * Formatta un testo ad una larghezza massima di caratteri Di norma 60 caratteri
     */
    public static String formattaTesto(String unTestoArrivo) {
        /** variabile locale di lavoro */
        String unTestoRitorno = "";
        int larghezza = 60;

        while (unTestoArrivo.length() > larghezza) {
            unTestoRitorno += unTestoArrivo.substring(0, larghezza);
            unTestoRitorno += "\n";
            unTestoArrivo = unTestoArrivo.substring(larghezza, unTestoArrivo.length());
        } // fine del blocco while

        /** valore di ritorno */
        return unTestoRitorno += unTestoArrivo;
    } /* fine del metodo */


    /**
     * Controlla l'esistenza di un Campo col titolo specificato. Controlla se un Campo, con un
     * determinato titolo di colonna, esiste in una collezione di campi <br>
     *
     * @param unaLista collezione di Campi
     * @param unTitolo titolo della colonna da testare
     *
     * @return vero se la colonna esiste nella collezione
     */
    public static boolean isEsisteTitolo(ArrayList unaLista, String unTitolo) {
        /* variabili e costanti locali di lavoro */
        boolean esiste = false;
        Campo unCampo = null;
        String titolo = "";

        try { // prova ad eseguire il codice
            /* spazzola tutta la collezione di campi */
            for (int k = 0; k < unaLista.size(); k++) {
                /* recupera il singolo campo */
                unCampo = (Campo)unaLista.get(k);

                /* recupera il titolo della colonna */
                titolo = unCampo.getCampoLista().getTitoloColonna();

                /* controllo di congruita' */
                if (titolo.equalsIgnoreCase(unTitolo)) {
                    esiste = true;
                    break;
                } /* fine del blocco if */
            } /* fine del blocco for */
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return esiste;
    }


    /**
     * Costruisce un nome di una tavola, partendo dal nome intero della classe, meno un suffisso da
     * eliminare
     *
     * @param classe nome della classe
     * @param suffisso parte del nome della classe da eliminare
     */
    public static String regolaNomeTavola(String classe, String suffisso) {
        /* variabili e costanti locali di lavoro */
        String nomeTavola = "";

        /** aggiungo il nome della classe, depurato del suo suffisso da eliminare */
        try {
            nomeTavola = classe.substring(0, classe.length() - suffisso.length());
        } catch (Exception unErrore) { // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /** valore di ritorno */
        return nomeTavola;
    } /* fine del metodo */


    /**
     * Costruisce un nome completo di un file, partendo dal nome completo del pacchetto, dal nome
     * intero della classe, meno un suffisso da eliminare
     *
     * @param pacchetto nome completo del pacchetto
     * @param classe nome della classe
     * @param suffisso parte del nome della classe da eliminare
     */
    public static String regolaNome(String pacchetto, String classe, String suffisso) {
        /* variabili e costanti locali di lavoro */
        String nomeBase;

        /** inizio mettendo il nome completo (col path) del pacchetto */
        nomeBase = pacchetto + ".";

        /** aggiungo il nome della classe, depurato del suo suffisso da eliminare */
        try {
            nomeBase += classe.substring(0, classe.length() - suffisso.length());
        } catch (Exception unErrore) { // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /** valore di ritorno */
        return nomeBase;
    } /* fine del metodo */


    /**
     * Aggiunge a ogni campo di una lista il nome della tavola (su una lista)
     */
    public static ArrayList aggiungeNomeTavola(String unaTavola, ArrayList unaLista) {
        ArrayList unaListaDestinazione = null;
        unaListaDestinazione = new ArrayList();
        String unNome = "";
        String unNomeCompleto = "";

        for (int k = 0; k < unaLista.size(); k++) {
            unNome = (String)unaLista.get(k);
            unNomeCompleto = aggiungeNomeTavola(unaTavola, unNome);
            unaListaDestinazione.add(unNomeCompleto);
        } /* fine del blocco for */
        return unaListaDestinazione;
    } /* fine del metodo */


    /**
     * Aggiunge a un campo il nome di una tavola (su singola stringa)
     */
    public static String aggiungeNomeTavola(String unaTavola, String unNomeCampo) {
        return unaTavola + "." + unNomeCampo;
    } /* fine del metodo */


    /**
     * Rimuove da ogni campo di una lista il nome della tavola (su una lista)
     */
    public static String rimuoveNomeTavola(ArrayList unaListaCompleta) {
        ArrayList unaListaDestinazione = null;
        unaListaDestinazione = new ArrayList();
        String unNomeCompleto = "";
        String unNome = "";

        for (int k = 0; k < unaListaCompleta.size(); k++) {
            unNomeCompleto = (String)unaListaCompleta.get(k);
            unNome = rimuoveNomeTavola(unNomeCompleto);
            unaListaDestinazione.add(unNome);
        } /* fine del blocco for */
        return Lib.Testo.getStringaVirgola(unaListaDestinazione);
    } /* fine del metodo */


    /**
     * Rimuove da un campo il nome di una tavola (su singola stringa)
     */
    public static String rimuoveNomeTavola(String unNomeCompleto) {
        /** variabili e costanti locali di lavoro */
        String sep = ".";
        String unNomeCampo = "";
        int pos = 0;

        pos = unNomeCompleto.indexOf(sep);

        /** controllo di congruita' */
        if (pos != 0) {
            unNomeCampo = unNomeCompleto.substring(pos + 1, unNomeCompleto.length());
        } /* fine del blocco if */

        return unNomeCampo;
    } /* fine del metodo */


    /**
     * Ritorna il nome completo Sql di un campo a partire da un nome di tavola e un nome Sql di
     * campo
     *
     * @param unNomeTavola il nome della tavola
     * @param unNomeSqlCampo il nome Sql del campo
     *
     * @return il nome completo Sql del campo
     */
    public static String nomeCompletoSqlCampo(String unNomeTavola, String unNomeSqlCampo) {
        /** variabili e costanti locali di lavoro */
        String unNomeCompletoSqlCampo = "";
        unNomeCompletoSqlCampo = unNomeTavola + CostanteCarattere.PUNTO + unNomeSqlCampo;
        return unNomeCompletoSqlCampo;
    } /* fine del metodo */


    /**
     * Ritorna il nome alias Sql di un campo a partire da un nome di tavola e un nome Sql di campo
     *
     * @param unNomeTavola il nome della tavola
     * @param unNomeSqlCampo il nome Sql del campo
     *
     * @return il nome alias Sql del campo
     */
    public static String nomeAliasSqlCampo(String unNomeTavola, String unNomeSqlCampo) {
        /** variabili e costanti locali di lavoro */
        String unNomeAliasSqlCampo = "";
        unNomeAliasSqlCampo = unNomeTavola + CostanteCarattere.UNDERSCORE + unNomeSqlCampo;
        return unNomeAliasSqlCampo;
    } /* fine del metodo */


    /**
     * Elimina da una stringa la parte finale
     *
     * @param unTesto la stringa da elaborare
     * @param unFinale la stringa da eliminare se si trova alla fine
     *
     * @return la stringa ripulita
     */
    public static String stripFinale(String unTesto, String unFinale) {
        String unTestoLocale = unTesto;
        int lun = 0;    // lunghezza di caratteri della stringa finale

        /** lunghezza della stringa finale */
        lun = unFinale.length();

        /** Se esiste la elimina */
        if (unTestoLocale.endsWith(unFinale)) {
            unTestoLocale = unTestoLocale.substring(0, unTestoLocale.length() - lun);
        } /* fine del blocco if-else */

        return unTestoLocale;
    } /* fine del metodo */


    /**
     * Estrae la radice da un nome di file o directory
     *
     * @param unPercorso il percorso completo del file o directory
     *
     * @return la stringa corrispondente alla radice
     */
    public static String radiceFile(String unPercorso) {
        /* variabili e costanti locali di lavoro */
        String radice = "";
        ArrayList listaParti = null;
        char separatore = CostanteCarattere.SEP_DIR;

        // separa il percorso nelle varie parti
        listaParti = Lib.Array.creaLista(unPercorso, separatore);

        // elimina l'ultima parte
        listaParti.remove(listaParti.size() - 1);

        // spazzola le parti e ricompone la stringa
        for (int k = 0; k < listaParti.size(); k++) {
            radice += (String)listaParti.get(k);
            radice += String.valueOf(separatore);
        } // fine del ciclo for

        return radice;
    } /* fine del metodo */


    /**
     * Estrae la foglia (parte finale) da un nome di file o directory
     *
     * @param unPercorso il percorso completo del file o directory
     *
     * @return la stringa corrispondente alla foglia
     */
    public static String fogliaFile(String unPercorso) {
        /* variabili e costanti locali di lavoro */
        String foglia = "";
        ArrayList listaParti = null;
        char separatore = CostanteCarattere.SEP_DIR;

        // separa il percorso nelle varie parti
        listaParti = Lib.Array.creaLista(unPercorso, separatore);

        // tiene l'ultima parte
        foglia = (String)listaParti.get(listaParti.size() - 1);

        return foglia;
    } /* fine del metodo */


    /**
     * Ritorna una stringa casuale di una data lunghezza, composta dai caratteri da A a Z.
     *
     * @param lunghezza la lunghezza della stringa da ritornare
     *
     * @return una stringa casuale della lunghezza richiesta
     */
    public static String stringaCasualeAZ(int lunghezza) {
        /** variabili e costanti locali di lavoro */
        String unaStringa = "";
        String unCarattere = "";
        int unCodiceASCII = 0;
        int unMinimo = 65;
        int unMassimo = 90;
        int unRange = unMassimo - unMinimo;

        /** Crea un nuovo generatore di numeri casuali */
        Random unGeneratore = new Random();

        /** ciclo for */
        for (int k = 0; k < lunghezza; k++) {
            unCodiceASCII = unGeneratore.nextInt(unRange) + unMinimo;
            unCarattere = new Character((char)unCodiceASCII).toString();
            unaStringa += unCarattere;
        } /* fine del blocco for */

        return unaStringa;
    } /* fine del metodo */


    /**
     * Ritorna una stringa casuale di 20 caratteri, composta dai caratteri da A a Z.
     *
     * @return la stringa casuale
     */
    public static String stringaCasualeAZ() {
        return stringaCasualeAZ(20);
    } /* fine del metodo */


    /**
     * Aggiunge un prefisso a tutti i caratteri di controllo per poterli registrare nel DB
     */
    public static String converteTestoPerDb2(String unTesto) {
        /** Definisce un elenco di stringhe da prefissare
         *  aggiungere all'array tutte quelle che si desidera prefissare */
        String[] unElencoStringhe = {"\'"};

        /** ciclo for per tutte le stringhe da prefissare */
        String unTestoRisultante = unTesto;
        String prefisso = "\\";
        String[] unaStringa = null;

        for (int k = 0; k <= unElencoStringhe.length - 1; k++) {

            // passa il contenuto del testo risultante sul testo da elaborare
            unTesto = unTestoRisultante;
            unTestoRisultante = "";

            // divide il testo da elaborare in un array attorno al separatore
            String cerca = unElencoStringhe[k];
            unaStringa = unTesto.split(cerca, 0);

            /** ciclo for per tutte le ricorrenze della stringa da prefissare*/
            for (int i = 0; i < unaStringa.length; i++) {
                unTestoRisultante += unaStringa[i];
                // Non aggiunge dopo l'ultimo elemento
                if (i < unaStringa.length - 1) {
                    unTestoRisultante += prefisso;
                    unTestoRisultante += cerca;
                } /* fine del blocco if */
            } /* fine del blocco for */

        } /* fine del blocco for */

        return unTestoRisultante;
    } /* fine del metodo */


    /**
     * Restituisce lo stesso array ricevuto in ingresso, ma ordinato all'incontrario
     */
    public static void inverteOrdineArray(ArrayList unaLista) {
        /** variabili e costanti locali di lavoro */
        ArrayList unaListaLocale = null;

        /** ricopia in locale e cancella l'originale */
        unaListaLocale = (ArrayList)unaLista.clone();
        unaLista.clear();

        /** rimette nell'originale tutti gli oggetti, nell'ordine voluto */
        for (int k = unaListaLocale.size() - 1; k >= 0; k--) {
            unaLista.add(unaListaLocale.get(k));
        } /* fine del blocco for */
    } /* fine del metodo */


    /**
     * Elimina il valore dalla lista, se esiste
     */
    public static String eliminaElemento(String unaStringa, String unNome) {
        /** variabili e costanti locali di lavoro */
        ArrayList unaListaLocale = null;

        unaListaLocale = Lib.Array.creaLista(unaStringa);

        /** controllo di congruita' */
        if (Lib.Array.isEsisteNome(unaListaLocale, unNome)) {
            unaListaLocale.remove(unNome);
        } /* fine del blocco if */

        return Lib.Testo.getStringaVirgola(unaListaLocale);
    } /* fine del metodo */


    /**
     * Converte un int in una String
     */
    public static String intToString(int unIntero) {
        return "" + unIntero;
    } /* fine del metodo */


    /**
     * Ritorna il nome della classe e del metodo che hanno chiamato questo metodo.
     */
    public static DueStringhe nomeClasseMetodoChiamante() {
        /* variabili e costanti locali di lavoro */
        DueStringhe nomiClasseMetodo = new DueStringhe();
        String unNomeClasse;
        String unNomeMetodo;

        try {                                   // prova ad eseguire il codice
            Throwable t = new IllegalArgumentException();
            unNomeClasse = t.getStackTrace()[1].getClassName();
            unNomeMetodo = t.getStackTrace()[1].getMethodName();

            nomiClasseMetodo = new DueStringhe(unNomeClasse, unNomeMetodo);
        } catch (Exception unErrore) {           // intercetta l'errore
            /* messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        return nomiClasseMetodo;
    } /* fine del metodo */


    /**
     * Ritorna il nome della classe e del metodo che hanno chiamato questo metodo, risalendo nello
     * stack del numero di livelli specificato nel parametro.
     *
     * @param numeroLivelliStack numero di livelli da risalire nello stack (1 per il metodo
     * direttamente chiamante)
     *
     * @return due stringhe
     */
    public static DueStringhe nomeClasseMetodoChiamante(int numeroLivelliStack) {
        /* variabili e costanti locali di lavoro */
        DueStringhe nomiClasseMetodo = null;
        String unNomeClasse;
        String unNomeMetodo;

        try { // prova ad eseguire il codice
            Throwable t = new IllegalArgumentException();
            unNomeClasse = t.getStackTrace()[numeroLivelliStack].getClassName();
            unNomeMetodo = t.getStackTrace()[numeroLivelliStack].getMethodName();

            nomiClasseMetodo = new DueStringhe(unNomeClasse, unNomeMetodo);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return nomiClasseMetodo;
    }


    /**
     * Ritorna il nome della classe attualmente in esecuzione. <br>
     *
     * @return il nome della classe in esecuzione (la classe chiamante questo metodo)
     */
    public static String nomeClasseCorrente() {
        /* variabili e costanti locali di lavoro */
        String nomeClasse = null;
        Throwable t;

        try {    // prova ad eseguire il codice
            t = new IllegalArgumentException();
            nomeClasse = t.getStackTrace()[1].getClassName();

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return nomeClasse;
    } // fine del metodo


    /**
     * Ricerca un elemento in un array di stringhe
     *
     * @param a l'array di stringhe nel quale cercare
     * @param s la stringa da cercare
     * @param c true per ricerca case-sensitive false per ricerca case-insensitive
     *
     * @return l'indice del primo elemento trovato nell'array, -1 se non trovato
     */
    public static int posizioneStringaInArray(String[] a, String s, boolean c) {
        /** variabili e costanti locali di lavoro */
        int posizione = -1;
        int i = 0;
        String elemento = null;

        // spazzola l'array ala ricerca della stringa
        for (i = 0; i < a.length; i++) {

            // recupera l'elemento
            elemento = a[i];

            /* effettua il confronto in modalita'
             * case sensitive o case-insensitive
             * in funzione del parametro c*/
            if (c) {    // case-sensitive
                if (s.equals(elemento)) {
                    posizione = i;
                }
            } else {    // case-insensitive
                if (s.equalsIgnoreCase(elemento)) {
                    posizione = i;
                }
            } /* fine del blocco if-else */

            // se trovato forza uscita dal ciclo
            if (posizione > -1) {
                break;
            }
        }

        /** valore di ritorno */
        return posizione;
    } /* fine del metodo */


    /**
     * Ribalta una lista di oggetti
     *
     * @param unaLista la ArrayList da ribaltare
     *
     * @return la ArrayList ribaltata
     */
    public static ArrayList ribaltaLista(ArrayList unaLista) {
        ArrayList unaListaRibaltata = new ArrayList();

        /** spazzola la lista
         * partendo dall'ultimo elemento verso il primo
         * e costruisce la lista ribaltata */
        for (int k = unaLista.size() - 1; k >= 0; k--) {
            unaListaRibaltata.add(unaLista.get(k));
        } /* fine del blocco for */

        return unaListaRibaltata;
    } /* fine del metodo */


    /**
     * Elimina i duplicati (stessi oggetti) da una lista di oggetti
     *
     * @param unaLista la lista da elaborare
     *
     * @return la lista con i duplicati eliminati
     */
    public static ArrayList eliminaDuplicatiLista(ArrayList unaLista) {
        ArrayList unaListaPulita = new ArrayList();
        Object unOggetto = null;

        try {    // prova ad eseguire il codice
            unaListaPulita = new ArrayList();

            /* spazzola la lista originale
             * e trasferisce gli elementi nella lista
             * destinazione se non sono gia' presenti */
            for (int k = 0; k < unaLista.size(); k++) {
                unOggetto = unaLista.get(k);
                /*  */
                if (unaListaPulita.contains(unOggetto) == false) {
                    unaListaPulita.add(unOggetto);
                }// fine del blocco if

            } // fine del ciclo for
        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        return unaListaPulita;
    } /* fine del metodo */


    /**
     * Converte una HashMap in una ArrayList
     *
     * @param unaMappa la HashMap da convertire
     *
     * @return una ArrayList
     */
    public static ArrayList hashMapToArrayList(Map unaMappa) {
        /* variabili e costanti locali di lavoro */
        ArrayList unaLista = null;
        Object unOggetto = null;

        try { // prova ad eseguire il codice

            unaLista = new ArrayList();

            /* traversa tutta la collezione */
            Iterator unGruppo = unaMappa.values().iterator();
            while (unGruppo.hasNext()) {

                /* recupera l'oggetto dalla mappa */
                unOggetto = unGruppo.next();

                /* aggiunge l'oggetto alla lista */
                unaLista.add(unOggetto);

            } /* fine del blocco while */

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unaLista;
    } /* fine del metodo */


    /**
     * Converte una mappa in una lista
     *
     * @param mappa la HashMap da convertire
     *
     * @return una ArrayList
     */
    public static List mapToList(Map mappa) {
        /* variabili e costanti locali di lavoro */
        List lista = null;
        Object unOggetto = null;

        try { // prova ad eseguire il codice

            lista = new ArrayList();

            /* traversa tutta la collezione */
            Iterator unGruppo = mappa.values().iterator();
            while (unGruppo.hasNext()) {

                /* recupera l'oggetto dalla mappa */
                unOggetto = unGruppo.next();

                /* aggiunge l'oggetto alla lista */
                lista.add(unOggetto);

            } /* fine del blocco while */

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    } /* fine del metodo */


    /**
     * Converte un array di oggetti in una ArrayList di oggetti
     *
     * @param unArray l'array da convertire
     *
     * @return una ArrayList contenente gli elementi dell'array
     */
    public static ArrayList objectArrayToArrayList(Object[] unArray) {
        /** variabili e costanti locali di lavoro */
        ArrayList unaLista = null;
        Object unOggetto = null;

        try {    // prova ad eseguire il codice
            unaLista = new ArrayList();
            for (int k = 0; k < unArray.length; k++) {
                unOggetto = unArray[k];
                unaLista.add(unOggetto);
            } // fine del ciclo for
        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /** valore di ritorno */
        return unaLista;
    } /* fine del metodo */


    /**
     * Restituisce un modulo dalla istanza di Progetto
     *
     * @param unaChiave chiave del modulo da recuperare
     *
     * @return il modulo recuperato (null se non esiste)
     */
    public static Modulo getModulo(String unaChiave) {
        return Progetto.getModulo(unaChiave);
    } /* fine del metodo */


    /**
     * Restituisce un campo da una lista di campi
     */
    public static Campo getCampo(ArrayList unaLista, String unNomeCampo) {
        /** variabili e costanti locali di lavoro */
        Campo unCampoTemporaneo = null;
        Campo unCampoRitorno = null;
        Object unOggetto = null;

        try {    // prova ad eseguire il codice
            /** ciclo for */
            for (int k = 0; k < unaLista.size(); k++) {
                unOggetto = unaLista.get(k);
                /** controllo di congruita' */
                if (unOggetto instanceof Campo) {
                    /** casting per recuperare il nome */
                    unCampoTemporaneo = (Campo)unOggetto;

                    /** controllo di congruita' */
                    if (unCampoTemporaneo.getNomeInterno()
                            .equalsIgnoreCase(unNomeCampo)) {
                        unCampoRitorno = unCampoTemporaneo;
                        break;
                    } /* fine del blocco if */

                } /* fine del blocco if */

            } /* fine del blocco for */
        } catch (Exception unErrore) {    // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /** valore di ritorno */
        return unCampoRitorno;
    } /* fine del metodo */


    /**
     * Crea una lista di numeri interi progressivi, pari alla dimensione ricevuta come parametro
     */
    public static ArrayList creaListaNumeri(int massimo) {
        /** variabili e costanti locali di lavoro */
        ArrayList unaLista = null;

        try {    // prova ad eseguire il codice
            /** costruisce l'istanza vuota */
            unaLista = new ArrayList();

            /** Crea un'istanza Integer per ogni valore */
            for (int k = 1; k <= massimo; k++) {
                unaLista.add(new Integer(k));
            } /* fine del blocco for */

        } catch (Exception unErrore) {    // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /** valore di ritorno */
        return unaLista;

    } /* fine del metodo */


    /**
     * Crea una lista di stringhe a partire da una stringa separata.
     *
     * @param stringa da leggere
     * @param sep carattere separatore
     *
     * @return la lista creata
     */
    public static ArrayList<String> creaLista(String stringa, String sep) {
        /** variabili e costanti locali di lavoro */
        ArrayList<String> lista = null;
        String[] parti;
        String parte;

        try {    // prova ad eseguire il codice

            lista = new ArrayList<String>();
            parti = stringa.split("\\" + sep);
            for (int k = 0; k < parti.length; k++) {
                parte = parti[k];
                lista.add(parte);
            } // fine del ciclo for

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /** valore di ritorno */
        return lista;

    } /* fine del metodo */


    /**
     * Crea una lista di stringhe a partire da una stringa separata da virgole.
     *
     * @param stringa da leggere
     *
     * @return la lista creata
     */
    public static ArrayList<String> creaListaVirgola(String stringa) {
        return Libreria.creaLista(stringa, ",");
    } /* fine del metodo */

//    /**
//     * Estrae il valore booleano da un oggetto.
//     * <p/>
//     * L'oggetto puo' essere di classe:
//     * - Boolean (true/false)
//     * - String ("true"/"false")
//     * - Integer (0/1)
//     *
//     * @param o l'oggetto dal quale estrarre il valore.
//     *
//     * @return il valore booleano.
//     */
//    public static boolean estraeBooleano(Object o) {
//        /** variabili e costanti locali di lavoro */
//        boolean valore = false;
//
//        try {    // prova ad eseguire il codice
//
//            /* caso Boolean */
//            if (o instanceof Boolean) {
//                Boolean oggetto = (Boolean)o;
//                valore = oggetto.booleanValue();
//            }// fine del blocco if
//
//            /* caso String */
//            if (o instanceof String) {
//                String oggetto = (String)o;
//                if (oggetto.equalsIgnoreCase("true")) {
//                    valore = true;
//                } else {
//                    valore = false;
//                }// fine del blocco if-else
//            }// fine del blocco if
//
//            /* caso Integer */
//            if (o instanceof Integer) {
//                Integer oggetto = (Integer)o;
//                if (oggetto.intValue() == 1) {
//                    valore = true;
//                } else {
//                    valore = false;
//                }// fine del blocco if-else
//            }// fine del blocco if
//
//        } catch (Exception unErrore) {    // intercetta l'errore
//            /** mostra il messaggio di errore */
//            Errore.crea(unErrore);
//        } /* fine del blocco try-catch */
//
//        /** valore di ritorno */
//        return valore;
//
//    } /* fine del metodo */

//    /**
//     * Estrae il valore int da un oggetto.
//     * <p/>
//     * L'oggetto puo' essere di classe:
//     * - Integer
//     * - Short
//     *
//     * @param o l'oggetto dal quale estrarre il valore.
//     *
//     * @return il valore booleano.
//     */
//    public static int estraeInt(Object o) {
//        /** variabili e costanti locali di lavoro */
//        int valore = 0;
//
//        try {    // prova ad eseguire il codice
//
//            /* caso Integer */
//            if (o instanceof Integer) {
//                Integer oggetto = (Integer)o;
//                valore = oggetto.intValue();
//            }// fine del blocco if
//
//            /* caso Short */
//            if (o instanceof Short) {
//                Short oggetto = (Short)o;
//                valore = oggetto.intValue();
//            }// fine del blocco if
//
//        } catch (Exception unErrore) {    // intercetta l'errore
//            /** mostra il messaggio di errore */
//            Errore.crea(unErrore);
//        } /* fine del blocco try-catch */
//
//        /** valore di ritorno */
//        return valore;
//
//    } /* fine del metodo */

    // Ensures that all the text in a JTextComponent will be
    // selected whenever the cursor is in that field (gains focus):


    public static void setSelectAllFieldText(JTextComponent comp) {
        FocusAdapter allTextSelector = null;

        // Lazy initialization of one adapter for all components:
        allTextSelector = new java.awt.event.FocusAdapter() {
            public void focusGained(FocusEvent ev) {
                JTextComponent textComp = (JTextComponent)ev.getSource();
                textComp.selectAll();
            }
        };

        comp.addFocusListener(allTextSelector);
    }


    /**
     * Restituisce la data e l'ora correnti.
     *
     * @param f Formato data/ora: inserire nella stringa le seguenti  lettere per ottenere l'output
     * corrispondente. <ul> <li><b>D</b> giorno;</li> <li><b>M</b> mese;</li> <li><b>Y</b>
     * anno;</li> <li><b>h</b> ora;</li> <li><b>m</b> minuto;</li> <li><b>s</b> secondo.</li> </ul>
     */
    public static String getData(String f) {
        String ris;
        String mesi[] =
                {"gen",
                        "feb",
                        "mar",
                        "apr",
                        "mag",
                        "giu",
                        "lug",
                        "ago",
                        "set",
                        "ott",
                        "nov",
                        "dic"};
        Calendar calendar = Calendar.getInstance();

        ris =
                (f.indexOf("D") != -1 ? calendar.get(Calendar.DATE) + " " : "") +
                        (f.indexOf("M") != -1 ? mesi[calendar.get(Calendar.MONTH)] + " " : "") +
                        (f.indexOf("Y") != -1 ? calendar.get(Calendar.YEAR) + "  " : "") +
                        (f.indexOf("h") != -1 ? calendar.get(Calendar.HOUR_OF_DAY) + "" : "") +

                        (f.indexOf("m") != -1 ?
                                (calendar.get(Calendar.MINUTE) < 10 ?
                                        ":0" + calendar.get(Calendar.MINUTE) :
                                        ":" + calendar.get(Calendar.MINUTE)) :
                                "") +

                        (f.indexOf("s") != -1 ?
                                (calendar.get(Calendar.SECOND) < 10 ?
                                        ":0" + calendar.get(Calendar.SECOND) :
                                        ":" + calendar.get(Calendar.SECOND)) :
                                "");
        return ris;
    }


    /**
     * Restituisce la data e l'ora correnti.
     * <p/>
     * Formato data/ora: inserire nella stringa le seguenti  lettere per ottenere l'output
     * corrispondente. <ul> <li><b>D</b> giorno;</li> <li><b>M</b> mese;</li> <li><b>Y</b>
     * anno;</li> <li><b>h</b> ora;</li> <li><b>m</b> minuto;</li> <li><b>s</b> secondo.</li> </ul>
     */
    public static String getData() {
        return getData("DMY").trim();
    }

}// fine della classe