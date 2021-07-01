/**
 * Copyright: Copyright (c) 2006
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      31-mar-2006
 */
package it.algos.base.libreria;

import com.wildcrest.j2printerworks.ImagePanel;

import it.algos.base.albero.AlberoNodo;
import it.algos.base.azione.Azione;
import it.algos.base.campo.base.Campo;
import it.algos.base.evento.BaseListener;
import it.algos.base.finestra.Finestra;
import it.algos.base.matrice.MatriceBase;
import it.algos.base.modello.Modello;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.stampa.Printer;
import it.algos.base.wrapper.CampoValore;
import it.algos.base.wrapper.CinqueStringhe;
import it.algos.base.wrapper.DieciStringhe;
import it.algos.base.wrapper.DueDate;
import it.algos.base.wrapper.DueStringhe;
import it.algos.base.wrapper.FtpWrap;
import it.algos.base.wrapper.IntStringa;
import it.algos.base.wrapper.NoveStringhe;
import it.algos.base.wrapper.OttoStringhe;
import it.algos.base.wrapper.QuattroStringhe;
import it.algos.base.wrapper.SeiStringhe;
import it.algos.base.wrapper.SetteStringhe;
import it.algos.base.wrapper.TreStringhe;

import org.apache.commons.net.ftp.FTPFile;

import javax.mail.MessagingException;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.event.EventListenerList;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Window;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Classe astratta di interfaccia per accedere alle librerie del package. </p> Tutti i metodi sono
 * statici <br> I metodi sono divisi in classi interne per facilitarne il reperimento <br> I metodi
 * sono intercettati e rinviati alle  singole librerie <br> Le librerie sono nello stesso package e
 * non hanno modificatore del metodo, così i metodi sono visibili all'esterno del package solo
 * utilizzando questa interfaccia unificata <br>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 31-mar-2006 ore 11.14.27
 */
public abstract class Lib {

    /**
     * Classe 'interna' per facilitare il reperimento. </p>
     */
    public static class Testo {

        /**
         * Utility di conversione di una stringa.
         * <p/>
         * Trasforma i punti in barre <br>
         *
         * @param entrata stringa in ingresso
         *
         * @return stringa convertita
         */
        public static String convertePuntiInBarre(String entrata) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibTesto.convertePuntiInBarre(entrata);
        }


        /**
         * Utility di conversione di una stringa.
         * <p/>
         * Trasforma le barre in punti <br>
         *
         * @param entrata stringa in ingresso
         *
         * @return stringa convertita
         */
        public static String converteBarreInPunti(String entrata) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibTesto.converteBarreInPunti(entrata);
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
        public static int getPos(String testo, String strUno, String strDue) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibTesto.getPos(testo, strUno, strDue);
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
        public static int getPosDue(String testo, String strUno, String strDue) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibTesto.getPosDue(testo, strUno, strDue);
        }


        /**
         * Crea una lista di nomi (chiavi) da una collezione di oggetti.
         * <p/>
         * Riceve una collezione ordinata di oggetti <br> Crea una ArrayList di stringhe, formata
         * dai nomi delle chiavi <br>
         *
         * @param collezione di oggetti
         *
         * @return lista ordinata delle chiavi della collezione
         */
        public static ArrayList<String> getListaChiavi(LinkedHashMap<String, Object> collezione) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibTesto.getListaChiavi(collezione);
        }


        /**
         * Controlla che un oggetto contenga un valore valido di stringa.
         * <p/>
         * Sono esclusi i valori nulli <br> Sono escluse le stringhe vuote <br> Sono escluse le
         * stringhe di soli spazi <br>
         *
         * @param oggetto da controllare
         *
         * @return vero, se l'oggetto rappresenta una stringa valida falso, se l'oggetto non
         *         rappresenta una stringa valida
         */
        public static boolean isValida(Object oggetto) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibTesto.isValida(oggetto);
        }


        /**
         * Controlla se il carattere iniziale del testo è una vocale.
         * <p/>
         *
         * @param testo da controllare
         *
         * @return vero, se il ,primo carattere è una vocale
         */
        public static boolean isPrimaVocale(String testo) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibTesto.isPrimaVocale(testo);
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
        public static boolean isVuota(Object oggetto) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibTesto.isVuota(oggetto);
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
        public static String primaMaiuscola(String stringa) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibTesto.primaMaiuscola(stringa);
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
        public static String primaMinuscola(String stringa) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibTesto.primaMinuscola(stringa);
        }


        /**
         * Inserisce virgolette prima e dopo la stringa.
         * <p/>
         *
         * @param stringaIn da elaborare
         *
         * @return stringa elaborato
         */
        public static String setVirgolette(String stringaIn) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibTesto.setVirgolette(stringaIn);
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
        public static String levaVirgolette(String stringaIn) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibTesto.levaVirgolette(stringaIn);
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
        public static String levaCoda(String stringaIn, String coda) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibTesto.levaCoda(stringaIn, coda);
        }


        /**
         * Restituisce la parte di testo precedente al tag.
         * <p/>
         * Esegue solo se la stringa è valida <br> Elimina spazi vuoti iniziali e finali <br>
         *
         * @param stringaIn testo in ingresso
         * @param tag       per la separazione
         *
         * @return stringa risultante
         */
        public static String prima(String stringaIn, String tag) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibTesto.primaUltima(stringaIn, tag);
        }


        /**
         * Restituisce la parte di testo precedente al tag.
         * <p/>
         * Controlla la prima occorrenza del tag <br> Esegue solo se la stringa è valida <br>
         * Elimina spazi vuoti iniziali e finali <br>
         *
         * @param stringaIn testo in ingresso
         * @param tag       per la separazione
         *
         * @return stringa risultante
         */
        public static String primaPrima(String stringaIn, String tag) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibTesto.primaPrima(stringaIn, tag);
        }


        /**
         * Restituisce la parte di testo precedente al tag.
         * <p/>
         * Controlla l'ultima occorrenza del tag <br> Esegue solo se la stringa è valida <br>
         * Elimina spazi vuoti iniziali e finali <br>
         *
         * @param stringaIn testo in ingresso
         * @param tag       per la separazione
         *
         * @return stringa risultante
         */
        public static String primaUltima(String stringaIn, String tag) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibTesto.primaUltima(stringaIn, tag);
        }


        /**
         * Restituisce la parte di testo successiva al tag.
         * <p/>
         * Esegue solo se la stringa è valida <br> Elimina spazi vuoti iniziali e finali <br>
         *
         * @param stringaIn testo in ingresso
         * @param tag       per la separazione
         *
         * @return stringa risultante
         */
        public static String dopo(String stringaIn, String tag) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibTesto.dopoUltima(stringaIn, tag);
        }


        /**
         * Restituisce la parte di testo successiva al tag.
         * <p/>
         * Controlla la prima occorrenza del tag <br> Esegue solo se la stringa è valida <br>
         * Elimina spazi vuoti iniziali e finali <br>
         *
         * @param stringaIn testo in ingresso
         * @param tag       per la separazione
         *
         * @return stringa risultante
         */
        public static String dopoPrima(String stringaIn, String tag) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibTesto.dopoPrima(stringaIn, tag);
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
        public static String dopoUltima(String stringaIn, String tag) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibTesto.dopoUltima(stringaIn, tag);
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
        public static String dopoUltimaEsistente(String stringaIn, String tag) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibTesto.dopoUltimaEsistente(stringaIn, tag);
        }


        /**
         * Crea una stringa da una lista di nomi.
         * <p/>
         * Ogni nome nella stringa viene diviso da unas virgola <br> Esegue solo se la lista non è
         * nulla <br>
         *
         * @param lista di nomi
         *
         * @return stringa di nomi separati dalla virgola
         */
        public static String getStringa(ArrayList<String> lista) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibTesto.getStringaVirgola(lista);
        }


        /**
         * Crea una stringa da una lista di nomi.
         * <p/>
         * Ogni nome nella stringa viene diviso da uno specifico separatore <br> Esegue solo se la
         * lista non è nulla <br>
         *
         * @param lista di nomi
         * @param sep   carattere (stringa) separatore nella stringa
         *
         * @return stringa di nomi separati dal separatore
         */
        public static String getStringa(ArrayList<String> lista, String sep) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibTesto.getStringa(lista, sep);
        }


        /**
         * Crea una stringa da una lista di nomi.
         * <p/>
         * Ogni nome nella stringa viene diviso da uno spazio <br> Esegue solo se la lista non è
         * nulla <br>
         *
         * @param lista di nomi
         *
         * @return stringa di nomi separati dallo spazio
         */
        public static String getStringaSpazio(ArrayList<String> lista) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibTesto.getStringaSpazio(lista);
        }


        /**
         * Crea una stringa da una lista di nomi.
         * <p/>
         * Ogni nome nella stringa viene diviso da una virgola <br> Esegue solo se la lista non è
         * nulla <br>
         *
         * @param lista di nomi
         *
         * @return stringa di nomi separati dalla virgola
         */
        public static String getStringaVirgola(ArrayList<String> lista) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibTesto.getStringaVirgola(lista);
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
        public static String getStringaTab(ArrayList<String> lista) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibTesto.getStringaTab(lista);
        }


        /**
         * Crea una stringa da una lista di nomi.
         * <p/>
         * Ogni nome nella stringa viene diviso da un ritorno a capo <br> Esegue solo se la lista
         * non è nulla <br>
         *
         * @param lista di nomi
         *
         * @return stringa di nomi separati dal ritorno a capo
         */
        public static String getStringaCapo(ArrayList<String> lista) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibTesto.getStringaCapo(lista);
        }


        /**
         * Crea una stringa da un'array di nomi.
         * <p/>
         * Ogni nome nella stringa viene diviso da una virgola <br>
         * Esegue solo se l'array non è nullo <br>
         *
         * @param array di nomi
         *
         * @return stringa di nomi separati dalla virgola
         */
        public static String getStringaVirgola(String[] array) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibTesto.getStringaVirgola(array);
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
        public static String getStringaVirgolaSpazio(String[] array) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibTesto.getStringaVirgolaSpazio(array);
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
        public static String getStringaVirgola(int[] array) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibTesto.getStringaVirgola(array);
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
        public static String getStringaIntVirgola(ArrayList<Integer> lista) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibTesto.getStringaIntVirgola(lista);
        }


        /**
         * Restituisce il massimo di caratteri di una lista.
         * <p/>
         * La lista deve essere di stringhe <br> Restituisce il numero di caratteri della stringa
         * più lunga <br>
         *
         * @param lista di nomi
         *
         * @return numero massimo di caratteri zero se la lista è nulla o vuota
         */
        public static int maxCaratteri(ArrayList<String> lista) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibTesto.maxCaratteri(lista);
        }


        /**
         * Restituisce il minimo di caratteri di una lista.
         * <p/>
         * La lista deve essere di stringhe <br> Restituisce il numero di caratteri della stringa
         * più corta <br>
         *
         * @param lista di nomi
         *
         * @return numero minimo di caratteri zero se la lista è nulla o vuota
         */
        public static int minCaratteri(ArrayList<String> lista) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibTesto.minCaratteri(lista);
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
        public static String maxTesto(ArrayList<String> lista) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibTesto.maxTesto(lista);
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
        public static String minTesto(ArrayList<String> lista) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibTesto.minTesto(lista);
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
        public static String formatNumero(String numero, char sep) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibTesto.formatNumero(numero, sep);
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
        public static String formatNumero(String numero) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibTesto.formatNumero(numero);
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
        public static String formatNum(String numero) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibTesto.formatNum(numero);
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
        public static String formatNumero(int numero) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibTesto.formatNumero(numero);
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
        public static String formatNumero(double numero) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibTesto.formatNumero(numero);
        }


        /**
         * Formattazione del numero.
         * <p/>
         * Solo per numeri italiani con la virgola decimale <br>
         * Inserisce il separatore ogni 3 cifre precedenti la virgola <br>
         *
         * @param numeroIta da formattare
         *
         * @return numero formattato
         */
        public static String formatNumIta(String numeroIta) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibTesto.formatNumIta(numeroIta);
        }


        /**
         * Formattazione del valore booleano.
         * <p/>
         *
         * @param booleano da trasformare in stringa
         *
         * @return valore in forma di stringa
         */
        public static String formatBool(boolean booleano) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibTesto.formatBool(booleano);
        }


        /**
         * Restituisce una Stringa da un Oggetto.
         * <p/>
         * Controlla che l'oggetto esista e che sia una Stringa <br>
         * Effettua il casting <br>
         *
         * @param unOggetto oggetto su cui effettuare il casting
         *
         * @return una stringa col valore preso dall'oggetto se l'oggetto non era valido restituisce
         *         una stringa vuota e non null
         */
        public static String getStringa(Object unOggetto) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibTesto.getStringa(unOggetto);
        }


        /**
         * Restituisce la larghezza in pixel di una stringa. </p>
         *
         * @param testo testo di cui calcolare la larghezza
         * @param font  tipo di font utilizzato
         *
         * @return larghezza valore in pixel
         */
        public static int getLarPixel(String testo, Font font) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return Lib.Fonte.getLarPixel(testo, font);
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
        public static String pad(String stringaIn,
                                 char carattere,
                                 int len,
                                 LibTesto.Posizione pos) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibTesto.pad(stringaIn, carattere, len, pos);
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
        public static String concat(String sep, String... stringhe) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibTesto.concat(sep, stringhe);
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
        public static String concatSpace(String... stringhe) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibTesto.concatSpace(stringhe);
        }


        /**
         * Concatena una serie di stringhe tra di loro separandole con return.
         * <p/>
         *
         * @param stringhe da concatenare
         *
         * @return la stringa concatenata
         */
        public static String concatReturn(String... stringhe) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibTesto.concatReturn(stringhe);
        }


        /**
         * Rimuove gli eventuali spazi a inizio stringa.
         * <p/>
         *
         * @param stringa da pulire
         *
         * @return stringa ripulita dagli spazi iniziali
         */
        public static String trimLeftSpc(String stringa) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibTesto.trimLeftSpc(stringa);
        }


        /**
         * Rimuove gli eventuali spazi a fine stringa.
         * <p/>
         *
         * @param stringa da pulire
         *
         * @return stringa ripulita dagli spazi finali
         */
        public static String trimRightSpc(String stringa) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibTesto.trimRightSpc(stringa);
        }


        /**
         * Rimuove gli eventuali spazi a inizio e fine stringa.
         * <p/>
         *
         * @param stringa da pulire
         *
         * @return stringa ripulita dagli spazi iniziali e finali
         */
        public static String trimSpc(String stringa) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibTesto.trimSpc(stringa);
        }


        /**
         * Rimuove gli eventuali return a inizio stringa.
         * <p/>
         *
         * @param stringa da pulire
         *
         * @return stringa ripulita dai return iniziali
         */
        public static String trimLeftRet(String stringa) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibTesto.trimLeftRet(stringa);
        }


        /**
         * Rimuove gli eventuali return a fine stringa.
         * <p/>
         *
         * @param stringa da pulire
         *
         * @return stringa ripulita dai return finali
         */
        public static String trimRightRet(String stringa) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibTesto.trimRightRet(stringa);
        }


        /**
         * Rimuove gli eventuali return a inizio e fine stringa.
         * <p/>
         *
         * @param stringa da pulire
         *
         * @return stringa ripulita dai return iniziali e finali
         */
        public static String trimRet(String stringa) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibTesto.trimRet(stringa);
        }


        /**
         * Rimuove gli eventuali spazi e return a inizio stringa.
         * <p/>
         *
         * @param stringa da pulire
         *
         * @return stringa ripulita da spazi e return iniziali
         */
        public static String trimLeft(String stringa) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibTesto.trimLeft(stringa);
        }


        /**
         * Rimuove gli eventuali spazi e return a fine stringa.
         * <p/>
         *
         * @param stringa da pulire
         *
         * @return stringa ripulita da spazi e return finali
         */
        public static String trimRight(String stringa) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibTesto.trimRight(stringa);
        }


        /**
         * Rimuove gli eventuali spazi e return a inizio e fine stringa.
         * <p/>
         *
         * @param stringa da pulire
         *
         * @return stringa ripulita da spazi e return iniziali e finali
         */
        public static String trim(String stringa) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibTesto.trim(stringa);
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
        public static String estrae(String testoIn, String tagIni, String tagEnd) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibTesto.estrae(testoIn, tagIni, tagEnd);
        }


        /**
         * Estrae una porzione di testo.
         * <p/>
         * Estremi esclusi <br>
         *
         * @param testoIn   completo
         * @param tagIni    da cui iniziare ad estrarre il testo
         * @param tagEnd    termine del testo da estrarre
         * @param tagEndDue termine alternativo del testo da estrarre
         *
         * @return stringa di testo estratto
         */
        public static String estrae(String testoIn,
                                    String tagIni,
                                    String tagEnd,
                                    String tagEndDue) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibTesto.estrae(testoIn, tagIni, tagEnd, tagEndDue);
        }


        /**
         * Estrae una porzione di testo.
         * <p/>
         * Estremi esclusi <br> Parte dall'inizio <br>
         *
         * @param testoIn   completo
         * @param tagEnd    termine del testo da estrarre
         * @param tagEndDue termine alternativo del testo da estrarre
         *
         * @return stringa di testo estratto
         */
        public static String estraeEnd(String testoIn, String tagEnd, String tagEndDue) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibTesto.estraeEnd(testoIn, tagEnd, tagEndDue);
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
        public static String levaQuadre(String stringa) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibTesto.levaQuadre(stringa);
        }


        /**
         * Sostituisce in testo tutte le occorrene.
         * <p/>
         *
         * @param testoIn da elaborare
         * @param leva    stringa da eliminare
         * @param metti   stringa da inserire
         *
         * @return stringa modificata
         */
        public static String replaceAll(String testoIn, String leva, String metti) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibTesto.replaceAll(testoIn, leva, metti);
        }


        /**
         * Elimina il titolo.
         * <p/>
         * Elimina la prima riga del testo <br> Se non ci sono ritorni a capo, elimina tutto il
         * testo <br>
         *
         * @param testoIn da elaborare
         *
         * @return testo elaborato
         */
        public static String levaTitolo(String testoIn) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibTesto.levaTitolo(testoIn);
        }


        /**
         * Restituisce la posizione del primo tag trovato dei due.
         * <p/>
         *
         * @param testo  da elaborare
         * @param tagUno primo tag da cercare
         * @param tagDue secondo tag da cercare
         *
         * @return posizione del primo tag -1 se non esiste nessuno dei due tag
         */
        public static int posPrima(String testo, String tagUno, String tagDue) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibTesto.posPrima(testo, tagUno, tagDue, 0);
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
        public static int posPrima(String testo, String tagUno, String tagDue, int inizio) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibTesto.posPrima(testo, tagUno, tagDue, inizio);
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
        public static String testoPrima(String testoIn, String tagUno, String tagDue) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibTesto.testoPrima(testoIn, tagUno, tagDue);
        }


        /**
         * Recupera dal testo una substringa compresa tra tagIni e tagEnd.
         * <p/>
         * tagIniPre serve per posizionarsi nel testo prima dell'occorrenza di tagIni che interessa
         * <br> (ce ne potrebbero essere altre) <br> Trova la prima occorrenza del tagIniPre <br>
         * Trova la prima occorrenza del tagIni Trova la prima occorrenza del tagFine Esegue solo se
         * tutti i tag sono validi <br> I due tag tagIniPre e tagIni possono essere uguali <br>
         *
         * @param testo     completo da cui estrarre la substringa
         * @param tagIniPre tag iniziale per individuare la sezione di testo interessata
         * @param tagIni    tag iniziale della stringa desiderata
         * @param tagFine   tag finale della stringa desiderata
         *
         * @return testo della substringa l'intero testo se i tag non sono validi
         */
        public static String subTesto(String testo,
                                      String tagIniPre,
                                      String tagIni,
                                      String tagFine) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibTesto.subTesto(testo, tagIniPre, tagIni, tagFine);
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
        public static String subTesto(String testo, String tagIni, String tagFine) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibTesto.subTesto(testo, tagIni, tagFine);
        }


        /**
         * Elimina le righe commentate.
         *
         * @param testoIn testo in ingresso
         *
         * @return testo in uscita
         */
        public static String eliminaRigheCommentate(String testoIn) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibTesto.eliminaRigheCommentate(testoIn);
        }


        /**
         * Ritorna una stringa casuale della lunghezza richiesta.
         *
         * @param lunghezza della stringa
         *
         * @return stringa casuale
         */
        public static String getTestoRandom(int lunghezza) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibTesto.getTestoRandom(lunghezza);
        }


        /**
         * Elimina (se esistenti) i tag <small> in testa ed in coda alla stringa.
         * <p/>
         *
         * @return stringa con i tag levati
         */
        public static String levaSmall(String stringa) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibTesto.levaSmall(stringa);
        }


        /**
         * Elimina eventuali parentesi in testa e in coda della stringa.
         * <p/>
         *
         * @param stringa da elaborare
         *
         * @return stringa con parentesi eliminate
         */
        public static String levaParentesi(String stringa) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibTesto.levaParentesi(stringa);
        }


        /**
         * Ordina la lista in base alla prima colonna.
         * <p/>
         *
         * @param listaIn da ordinare
         *
         * @return lista ordinata
         */
        public static ArrayList<QuattroStringhe> ordina(ArrayList<QuattroStringhe> listaIn) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibTesto.ordina(listaIn);
        }
    } // fine della classe 'interna'


    /**
     * Classe 'interna' per facilitare il reperimento. </p>
     */
    public static class Mat {


        /**
         * Somma due dimensioni. <br>
         *
         * @param primaDim   prima delle due dimensioni
         * @param secondaDim seconda delle due dimensioni
         *
         * @return la somma delle due dimensioni
         */
        public static Dimension sommaDimensioni(Dimension primaDim, Dimension secondaDim) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibMat.sommaDimensioni(primaDim, secondaDim);
        } // fine del metodo


        /**
         * Sottrae una dimensione da un'altra. <br>
         *
         * @param primaDim   prima delle due dimensioni (dalla quale sottrarre)
         * @param secondaDim seconda delle due dimensioni (da sottrarre)
         *
         * @return la somma delle due dimensioni
         */
        public static Dimension sottraeDimensioni(Dimension primaDim, Dimension secondaDim) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibMat.sottraeDimensioni(primaDim, secondaDim);
        } // fine del metodo


        /**
         * Restituisce la massima di due dimensioni. </p> Viene calcolato separatamente il massimo
         * dei due valori di larghezza, ed il massimo dei due valori di altezza <br>
         *
         * @param primaDim   prima delle due dimensioni
         * @param secondaDim seconda delle due dimensioni
         *
         * @return la dimensione col massimo dei valori
         */
        public static Dimension massimaDimensione(Dimension primaDim, Dimension secondaDim) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibMat.massimaDimensione(primaDim, secondaDim);
        } // fine del metodo


        /**
         * Restituisce la minima di due dimensioni. </p> Viene calcolato separatamente il minimo dei
         * due valori di larghezza, ed il minimo dei due valori di altezza <br>
         *
         * @param primaDim   prima delle due dimensioni
         * @param secondaDim seconda delle due dimensioni
         *
         * @return la dimensione col massimo dei valori
         */
        public static Dimension minimaDimensione(Dimension primaDim, Dimension secondaDim) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibMat.minimaDimensione(primaDim, secondaDim);
        } // fine del metodo


        /**
         * Verifica che una dimensione sia contenuta entro un'altra. </p> Gli estremi sono compresi,
         * quindi se due valori sono uguali la risposta e' affermativa <br>
         *
         * @param dimContenitore dimensione contenente
         * @param dimContenuto   dimensione contenuta
         *
         * @return vero se la seconda e piu' piccola della prima <br>
         */
        public static boolean isContenuta(Dimension dimContenitore, Dimension dimContenuto) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibMat.isContenuta(dimContenitore, dimContenuto);
        }// fine del metodo


        /**
         * Controlla se un determinato intero esiste in un array.
         *
         * @param array  array di riferimento
         * @param valore intero da ricercare
         *
         * @return true se esiste
         */
        public static boolean isEsiste(int[] array, int valore) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibMat.isEsiste(array, valore);
        }


        /**
         * Controlla se un determinato intero esiste in una lista.
         *
         * @param array  arrayList di riferimento (oggetti Integer)
         * @param valore intero da ricercare
         *
         * @return true se esiste
         */
        public static boolean isEsiste(ArrayList array, int valore) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibMat.isEsiste(array, valore);
        }


        /**
         * Ritorna il numero di cifre decimali di un numero double.
         * <p/>
         *
         * @param numero da controllare
         *
         * @return il numero di cifre decimali
         */
        public static int contaCifreDecimali(double numero) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibMat.contaCifreDecimali(numero);
        }


        /**
         * Arrotondamento matematico dei decimali al numero prefissato.
         * <p/>
         *
         * @param numero      da arrotondare
         * @param numDecimali cifre decimali da considerare
         *
         * @return numero arrotondato
         */
        public static double arrotonda(double numero, int numDecimali) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibMat.arrotonda(numero, numDecimali);
        }


        /**
         * Ritorna il minimo intero di una serie.
         * <p/>
         *
         * @param num elenco di interi
         *
         * @return minimo intero
         */
        public static int getMin(int... num) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibMat.getMin(num);
        }


        /**
         * Ritorna il massimo intero di una serie.
         * <p/>
         *
         * @param num elenco di interi
         *
         * @return massimo intero
         */
        public static int getMax(int... num) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibMat.getMax(num);
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
        public static int getInt(String stringa) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibMat.getInt(stringa);
        }


        /**
         * Restituisce un numero puro.
         * <p/>
         *
         * @return il numero
         */
        public static double getNum(String numeroInglese) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibMat.getNum(numeroInglese);
        }


        /**
         * Restituisce un numero puro di due decimali.
         * <p/>
         *
         * @return il numero
         */
        public static double getNum2(String numeroInglese) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibMat.getNum2(numeroInglese);
        }


        /**
         * Restituisce una stringa con due decimalii.
         * <p/>
         *
         * @return la stringa del numero
         */
        public static String getValNumIng(String numeroInglese) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibMat.getValNumIng(numeroInglese);
        }


        /**
         * Restituisce una stringa con due decimalii.
         * <p/>
         *
         * @return la stringa del numero
         */
        public static String getValNum(Double numero) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibMat.getValNum(numero);
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
        public static int getPer(int tot, int par) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibMat.getPer(tot, par);
        }


        /**
         * Controlla se un numero è un multiplo esatto dell'altro.
         * <p/>
         *
         * @param numero   da controllare
         * @param multiplo moltiplicatore
         *
         * @return vero se il numero è un multiplo esatto senza resto
         */
        public static boolean isMultiplo(int numero, int multiplo) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibMat.isMultiplo(numero, multiplo);
        }


        /**
         * Controlla se un numero è un multiplo esatto dell'altro.
         * <p/>
         *
         * @param numero da controllare
         *
         * @return vero se il numero è un multiplo esatto senza resto
         */
        public static boolean isMultiplo(int numero) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibMat.isMultiplo(numero);
        }

    } // fine della classe 'interna'


    /**
     * Classe 'interna' per facilitare il reperimento. </p>
     */
    public static class Array {

        /**
         * Utility di conversione di una stringa.
         * <p/>
         * Crea una lista da un testo usando una stringa come separatore <br> Di solito la stringa è
         * sempre di 1 carattere <br> Elementi nulli o vuoti non vengono aggiunti alla lista <br>
         * Vengono eliminati gli spazi vuoti iniziali e finali <br> Se il separatore è vuoto o
         * nullo, restituisce una lista di un elemento uguale al testo ricevuto <br>
         *
         * @param testo da suddividere
         * @param sep   carattere stringa di separazione
         *
         * @return una lista contenente le parti di stringa separate
         */
        public static ArrayList<String> creaLista(String testo, String sep) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibArray.creaLista(testo, sep);
        }


        /**
         * Utility di conversione di una stringa.
         * <p/>
         * Crea una lista da un testo usando una stringa come separatore <br> Di solito la stringa è
         * sempre di 1 carattere <br> Anche gli elementi vuoti vengono aggiunti alla lista <br> Non
         * vengono eliminati gli spazi vuoti iniziali e finali <br> Se il separatore è vuoto o
         * nullo, restituisce una lista di un elemento uguale al testo ricevuto <br>
         *
         * @param testo da suddividere
         * @param sep   carattere stringa di separazione
         *
         * @return una lista contenente le parti di stringa separate
         */
        public static ArrayList<String> creaListaDura(String testo, String sep) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibArray.creaListaDura(testo, sep);
        }


        /**
         * Utility di conversione di una stringa.
         * <p/>
         * Crea una lista da un testo usando un carattere separatore <br> Elementi nulli o vuoti non
         * vengono aggiunti alla lista <br> Vengono eliminati gli spazi vuoti iniziali e finali <br>
         * Se il separatore è vuoto o nullo, restituisce una lista di un elemento uguale al testo
         * ricevuto <br>
         *
         * @param testo da suddividere
         * @param sep   carattere char di separazione
         *
         * @return una lista contenente le parti di stringa separate
         */
        public static ArrayList<String> creaLista(String testo, char sep) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibArray.creaLista(testo, sep);
        }


        /**
         * Utility di conversione di una stringa.
         * <p/>
         * Crea una lista da un testo usando la virgola come separatore <br> Elementi nulli o vuoti
         * non vengono aggiunti alla lista <br> Vengono eliminati gli spazi vuoti iniziali e finali
         * <br>
         *
         * @param testo da suddividere
         *
         * @return una lista contenente le parti di stringa separate
         */
        public static ArrayList<String> creaLista(String testo) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibArray.creaLista(testo);
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
        public static ArrayList<String> creaLista(String[] array) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibArray.creaLista(array);
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
        public static ArrayList<Integer> creaLista(int[] array) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibArray.creaLista(array);
        }


        /**
         * Crea una lista bidimensionale da un pacchetto di testo delimitato in righe e colonne.
         * <p/>
         * Usa \n per il delimitatore di record e \t per il delimitatore di campo Tutte le righe
         * hanno lo stesso numero di colonne, pari al numero di colonne della riga più lunga
         * esistente nel pacchetto analizzato.
         *
         * @param pacchetto di testo da analizzare
         *
         * @return la lista bidimensionale
         */
        public static ArrayList creaLista2D(String pacchetto) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibArray.creaLista2D(pacchetto);
        }


        /**
         * Crea una lista bidimensionale da un pacchetto di testo delimitato in righe e colonne.
         * <p/>
         * Tutte le righe hanno lo stesso numero di colonne, pari al numero di colonne della riga
         * più lunga esistente nel pacchetto analizzato.
         *
         * @param pacchetto di testo da analizzare
         * @param delimCol  il delimitatore di colonna
         * @param delimRow  il delimitatore di riga
         *
         * @return la lista bidimensionale
         */
        public static ArrayList creaLista2D(String pacchetto, String delimCol, String delimRow) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibArray.creaLista2D(pacchetto, delimCol, delimRow);
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
        public static String[] intToString(int[] array) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibArray.intToString(array);
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
        public static ArrayList<Boolean> creaLista(boolean[] array) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibArray.creaLista(array);
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
        public static ArrayList<Object> creaLista(Object[] array) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibArray.creaLista(array);
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
        public static String[] creaStrArray(ArrayList<String> lista) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibArray.creaStrArray(lista);
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
        public static int[] creaIntArray(ArrayList<Integer> lista) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibArray.creaIntArray(lista);
        }


        /**
         * Comparazione di due arrays di interi.
         * <p/>
         * Esegue solo se i due arrays non sono nulli <br>
         *
         * @param primo   array di interi
         * @param secondo array di interi
         *
         * @return true se hanno le stesse dimensioni e gli stessi valori.
         */
        public static boolean isUguali(int[] primo, int[] secondo) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibArray.isUguali(primo, secondo);
        }


        /**
         * Confronto di due arrays di stringhe.
         * <p/>
         * Esegue solo se i due arrays non sono nulli <br>
         *
         * @param primo   array di stringhe
         * @param secondo array di stringhe
         *
         * @return true se hanno le stesse dimensioni e gli stessi valori.
         */
        public static boolean isUguali(String[] primo, String[] secondo) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibArray.isUguali(primo, secondo);
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
        public static ArrayList<Object> listaUnica(ArrayList<Object> lista) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibArray.listaUnica(lista);
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
        public static ArrayList<String> listaUnicaStr(ArrayList<String> lista) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibArray.listaUnicaStr(lista);
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
        public static ArrayList<Integer> listaUnicaInt(ArrayList<Integer> lista) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibArray.listaUnicaInt(lista);
        }


        /**
         * Restituisce la posizione di un oggetto in un array.
         * <p/>
         * ATTENZIONE - Gli array partono da 0 ! <br>
         *
         * @param valori l'array nel quale cercare
         * @param valore il singolo valore da cercare
         *
         * @return posizione del valore nell'array 0 la prima, -1 non trovato
         */
        public static int getPosizione(Object[] valori, Object valore) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibArray.getPosizione(valori, valore);
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
        public static int getPosizione(int[] valori, int valore) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibArray.getPosizione(valori, valore);
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
        public static int getPosizione(ArrayList<Object> valori, Object valore) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibArray.getPosizione(valori, valore);
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
        public static int getPosizione(ArrayList<String> valori, String valore) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibArray.getPosStr(valori, valore);
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
        public static boolean isEsiste(Object[] valori, Object valore) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibArray.isEsiste(valori, valore);
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
        public static boolean isEsiste(String[] valori, String valore) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibArray.isEsiste(valori, valore);
        }


        /**
         * Controlla se un dato valore manca in un array.
         * <p/>
         *
         * @param valori l'array nel quale cercare
         * @param valore il singolo valore da cercare
         *
         * @return true se non esiste
         */
        public static boolean isNonEsiste(Object[] valori, Object valore) {
            /* invoca il metodo sovrascritto della classe */
            return !Lib.Array.isEsiste(valori, valore);
        }


        /**
         * Controlla se un dato valore manca in un array di interi.
         * <p/>
         *
         * @param valori l'array nel quale cercare
         * @param valore il singolo valore intero da cercare
         *
         * @return true se esiste
         */
        public static boolean isEsiste(int[] valori, int valore) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibArray.isEsiste(valori, valore);
        }


        /**
         * Controlla se un dato valore esiste in un array di interi.
         * <p/>
         *
         * @param valori l'array nel quale cercare
         * @param valore il singolo valore intero da cercare
         *
         * @return true se non esiste
         */
        public static boolean isNonEsiste(int[] valori, int valore) {
            /* invoca il metodo sovrascritto della classe */
            return !Lib.Array.isEsiste(valori, valore);
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
        public static boolean isEsiste(ArrayList<Object> valori, Object valore) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibArray.isEsiste(valori, valore);
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
        public static boolean isEsisteStr(ArrayList<String> valori, String valore) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibArray.isEsisteStr(valori, valore);
        }


        /**
         * Controlla se un dato valore manca in una lista.
         * <p/>
         *
         * @param valori la lista nella quale cercare
         * @param valore il singolo valore da cercare
         *
         * @return true se non esiste
         */
        public static boolean isNonEsiste(ArrayList<Object> valori, Object valore) {
            /* invoca il metodo sovrascritto della classe */
            return !Lib.Array.isEsiste(valori, valore);
        }


        /**
         * Converte una stringa in un array di interi.
         * <p/>
         *
         * @param stringa da convertire
         *
         * @return il corrispondente array di int
         */
        public static int[] getArray(String stringa) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibArray.getArray(stringa);
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
        public static ArrayList<DueStringhe> getDueStringhe(ArrayList<DieciStringhe> listaIn) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibArray.getDueStringhe(listaIn);
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
        public static ArrayList<TreStringhe> getTreStringhe(ArrayList<DieciStringhe> listaIn) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibArray.getTreStringhe(listaIn);
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
        public static ArrayList<QuattroStringhe> getQuattroStringhe(ArrayList<DieciStringhe> listaIn) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibArray.getQuattroStringhe(listaIn);
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
        public static ArrayList<CinqueStringhe> getCinqueStringhe(ArrayList<DieciStringhe> listaIn) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibArray.getCinqueStringhe(listaIn);
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
        public static ArrayList<SeiStringhe> getSeiStringhe(ArrayList<DieciStringhe> listaIn) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibArray.getSeiStringhe(listaIn);
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
        public static ArrayList<SetteStringhe> getSetteStringhe(ArrayList<DieciStringhe> listaIn) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibArray.getSetteStringhe(listaIn);
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
        public static ArrayList<OttoStringhe> getOttoStringhe(ArrayList<DieciStringhe> listaIn) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibArray.getOttoStringhe(listaIn);
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
        public static ArrayList<NoveStringhe> getNoveStringhe(ArrayList<DieciStringhe> listaIn) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibArray.getNoveStringhe(listaIn);
        }


        /**
         * Converte una lista di due stringhe in lista di dieci stringhe.
         * <p/>
         *
         * @param listaIn da convertire
         *
         * @return il corrispondente array di DieciStringhe
         */
        public static ArrayList<DieciStringhe> setDueStringhe(ArrayList<DueStringhe> listaIn) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibArray.setDueStringhe(listaIn);
        }


        /**
         * Converte una lista di tre stringhe in lista di dieci stringhe.
         * <p/>
         *
         * @param listaIn da convertire
         *
         * @return il corrispondente array di DieciStringhe
         */
        public static ArrayList<DieciStringhe> setTreStringhe(ArrayList<TreStringhe> listaIn) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibArray.setTreStringhe(listaIn);
        }


        /**
         * Converte una lista di quattro stringhe in lista di dieci stringhe.
         * <p/>
         *
         * @param listaIn da convertire
         *
         * @return il corrispondente array di DieciStringhe
         */
        public static ArrayList<DieciStringhe> setQuattroStringhe(ArrayList<QuattroStringhe> listaIn) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibArray.setQuattroStringhe(listaIn);
        }


        /**
         * Converte una lista di cinque stringhe in lista di dieci stringhe.
         * <p/>
         *
         * @param listaIn da convertire
         *
         * @return il corrispondente array di DieciStringhe
         */
        public static ArrayList<DieciStringhe> setCinqueStringhe(ArrayList<CinqueStringhe> listaIn) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibArray.setCinqueStringhe(listaIn);
        }


        /**
         * Converte una lista di sei stringhe in lista di dieci stringhe.
         * <p/>
         *
         * @param listaIn da convertire
         *
         * @return il corrispondente array di DieciStringhe
         */
        public static ArrayList<DieciStringhe> setSeiStringhe(ArrayList<SeiStringhe> listaIn) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibArray.setSeiStringhe(listaIn);
        }


        /**
         * Converte una lista di sette stringhe in lista di dieci stringhe.
         * <p/>
         *
         * @param listaIn da convertire
         *
         * @return il corrispondente array di DieciStringhe
         */
        public static ArrayList<DieciStringhe> setSetteStringhe(ArrayList<SetteStringhe> listaIn) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibArray.setSetteStringhe(listaIn);
        }


        /**
         * Converte una lista di otto stringhe in lista di dieci stringhe.
         * <p/>
         *
         * @param listaIn da convertire
         *
         * @return il corrispondente array di DieciStringhe
         */
        public static ArrayList<DieciStringhe> setOttoStringhe(ArrayList<OttoStringhe> listaIn) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibArray.setOttoStringhe(listaIn);
        }


        /**
         * Converte una lista di nove stringhe in lista di dieci stringhe.
         * <p/>
         *
         * @param listaIn da convertire
         *
         * @return il corrispondente array di DieciStringhe
         */
        public static ArrayList<DieciStringhe> setNoveStringhe(ArrayList<NoveStringhe> listaIn) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibArray.setNoveStringhe(listaIn);
        }


        /**
         * Controlla se un dato nome esiste in una lista.
         * <p/>
         * La lista deve essere di oggetti di tipo String <br>
         *
         * @param lista lista di tipo String
         * @param nome  da cercare nella lista
         *
         * @return vero se il nome figura nella lista
         */
        public static boolean isEsisteNome(ArrayList<String> lista, String nome) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibArray.isEsisteNome(lista, nome);
        }


        /**
         * Aggiunge un nome ad una lista, solo se non già esistente.
         * <p/>
         * La lista deve essere di oggetti di tipo String <br>
         *
         * @param lista lista di tipo String
         * @param nome  da inserire nella lista
         *
         * @return vero se il nome viene aggiunto
         */
        public static boolean addValoreUnico(ArrayList<String> lista, String nome) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibArray.addValoreUnico(lista, nome);
        }


        /**
         * Aggiunge un numero ad una lista, solo se non già esistente.
         * <p/>
         * La lista deve essere di oggetti di tipo int <br>
         *
         * @param lista  lista di tipo int
         * @param valore numerico da inserire nella lista
         *
         * @return vero se il nome viene aggiunto
         */
        public static boolean addValoreUnico(ArrayList<Integer> lista, int valore) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibArray.addValoreUnico(lista, valore);
        }


        /**
         * Aggiunge i valori ad una lista, solo se non già esistenti.
         * <p/>
         * Aggiunge tutti e solo i valori non già presenti <br>
         *
         * @param listaSorgente     valori da aggiungere
         * @param listaDestinazione lista a cui aggiungere i valori
         *
         * @return vero se almeno un valore è stato aggiunto
         */
        public static boolean addValoriUnici(ArrayList<String> listaSorgente,
                                             ArrayList<String> listaDestinazione) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibArray.addValoriUnici(listaSorgente, listaDestinazione);
        }


        /**
         * Rimuove i valori da una lista.
         * <p/>
         *
         * @param listaDaEliminare  valori da romuovere
         * @param listaDestinazione lista da cui rimuovere i valori
         *
         * @return vero se almeno un valore è stato rimosso
         */
        public static boolean rimuoveValori(ArrayList<String> listaDaEliminare,
                                            ArrayList<String> listaDestinazione) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibArray.rimuoveValori(listaDaEliminare, listaDestinazione);
        }


        /**
         * Controlla la validità di un array.
         * <p/>
         * Deve essere non nullo <br> Deve essere non vuoto <br>
         *
         * @param lista arry da controllare
         *
         * @return vero se l'array è valido
         */
        public static boolean isValido(ArrayList<String> lista) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibArray.isValido(lista);
        }


        /**
         * Controlla la validità di un array.
         * <p/>
         * Deve essere non nullo <br> Deve essere non vuoto <br>
         *
         * @param lista arry da controllare
         *
         * @return vero se l'array è valido
         */
        public static boolean isValidoInt(ArrayList<Integer> lista) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibArray.isValidoInt(lista);
        }


        /**
         * Ordina alfabeticamente una lista.
         * <p/>
         *
         * @param lista da ordinare alfabeticamente
         *
         * @return lista in ordine alfabetico
         */
        public static ArrayList<String> ordina(ArrayList<String> lista) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibArray.ordina(lista);
        }


        /**
         * Ordina una lista, spostando al primo posto il valore indicato.
         * <p/>
         * Se il valore non esiste, lo aggiunge <br>
         *
         * @param lista       da ordinare alfabeticamente, dopo il primo valore prefissato
         * @param primoValore primo valore prefissato
         *
         * @return lista in ordine alfabetico, dopo il primo valore prefissato
         */
        public static ArrayList<String> ordinaPrimo(ArrayList<String> lista, String primoValore) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibArray.ordinaPrimo(lista, primoValore);
        }


        /**
         * Ordina alfabeticamente una lista di stringhe (ordina sulla prima colonna).
         * <p/>
         *
         * @param listaIn da ordinare alfabeticamente
         *
         * @return lista in ordine alfabetico
         */
        public static ArrayList<TreStringhe> ordinaTre(ArrayList<TreStringhe> listaIn) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibArray.ordinaTre(listaIn);
        }


        /**
         * Ordina alfabeticamente una lista di stringhe (ordina sulla prima colonna).
         * <p/>
         *
         * @param listaIn da ordinare alfabeticamente
         *
         * @return lista in ordine alfabetico
         */
        public static ArrayList<QuattroStringhe> ordinaQuattro(ArrayList<QuattroStringhe> listaIn) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibArray.ordinaQuattro(listaIn);
        }


        /**
         * Ordina alfabeticamente una lista di stringhe (ordina sulla prima colonna).
         * <p/>
         *
         * @param listaIn da ordinare alfabeticamente
         *
         * @return lista in ordine alfabetico
         */
        public static ArrayList<CinqueStringhe> ordinaCinque(ArrayList<CinqueStringhe> listaIn) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibArray.ordinaCinque(listaIn);
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
        public static ArrayList<QuattroStringhe> ordinaUnicaQuattro(ArrayList<QuattroStringhe> listaIn) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibArray.ordinaUnicaQuattro(listaIn);
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
        public static ArrayList<CinqueStringhe> ordinaUnicaCinque(ArrayList<CinqueStringhe> listaIn) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibArray.ordinaUnicaCinque(listaIn);
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
        public static ArrayList<DieciStringhe> ordinaDieci(ArrayList<DieciStringhe> listaIn) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibArray.ordinaDieci(listaIn);
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
        public static ArrayList<DieciStringhe> ordinaUnicaDieci(ArrayList<DieciStringhe> listaIn) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibArray.ordinaUnicaDieci(listaIn);
        }


        /**
         * Ordina alfabeticamente una mappa (secondo la chiave).
         * <p/>
         *
         * @param mappa da ordinare alfabeticamente
         *
         * @return mappa con chiavi in ordine alfabetico
         */
        public static LinkedHashMap ordina(LinkedHashMap mappa) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibArray.ordina(mappa);
        }


        /**
         * Somma due liste.
         * <p/>
         * Esegue solo se gli array non sono nulli <br>
         *
         * @param prima   lista da sommare
         * @param seconda lista da sommare
         *
         * @return lista somma delle due
         */
        public static ArrayList<String> sommaStr(ArrayList<String> prima,
                                                 ArrayList<String> seconda) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibArray.sommaStr(prima, seconda);
        }


        /**
         * Somma due liste di dieci stringhe.
         * <p/>
         * Esegue solo se gli array non sono nulli <br>
         *
         * @param prima   lista da sommare
         * @param seconda lista da sommare
         *
         * @return lista somma delle due
         */
        public static ArrayList<DieciStringhe> sommaDieci(ArrayList<DieciStringhe> prima,
                                                          ArrayList<DieciStringhe> seconda) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibArray.sommaDieci(prima, seconda);
        }


        /**
         * Somma due liste.
         * <p/>
         * Esegue solo se gli array non sono nulli <br>
         *
         * @param prima   lista da sommare
         * @param seconda lista da sommare
         *
         * @return lista somma delle due
         */
        public static ArrayList<Object> sommaObj(ArrayList<Object> prima,
                                                 ArrayList<Object> seconda) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibArray.sommaObj(prima, seconda);
        }


        /**
         * Somma due liste.
         * <p/>
         * Esegue solo se gli array non sono nulli <br>
         *
         * @param prima   lista da sommare
         * @param seconda lista da sommare
         *
         * @return lista somma delle due
         */
        public static ArrayList<Campo> sommaCampi(ArrayList<Campo> prima,
                                                  ArrayList<Campo> seconda) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibArray.sommaCampi(prima, seconda);
        }


        /**
         * Intersezione di due array (matrici).
         * <p/>
         * Esegue solo se gli array non sono nulli <br>
         *
         * @param primo   array da confrontare
         * @param secondo array da confrontare
         *
         * @return array intersezione dei due (tutte e solo le stringhe che appartengono ad
         *         entrambi)
         */
        public static String[] intersezione(String[] primo, String[] secondo) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibArray.intersezione(primo, secondo);
        }


        /**
         * Differenza di due array (matrici).
         * <p/>
         * Esegue solo se gli array non sono nulli <br>
         *
         * @param primo   array da confrontare
         * @param secondo array da confrontare
         *
         * @return array differenza dei due (solo le stringhe del primo che non appartengono anche
         *         al secondo)
         */
        public static String[] differenza(String[] primo, String[] secondo) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibArray.differenza(primo, secondo);
        }



        /**
         * Converts a list of Integer objects to an array of integer primitives
         *
         * @param integerList the integer list
         * @return an array of integer primitives
         */
        public static int[] toIntArray(List<Integer> integerList) {
            return LibArray.toIntArray(integerList);
        }


        /**
         * Converts a list of Double objects to an array of double primitives
         *
         * @param doubleList the double list
         * @return an array of double primitives
         */
        public static double[] toDoubleArray(List<Double> doubleList) {
            return LibArray.toDoubleArray(doubleList);
        }


    } // fine della classe 'interna'


    /**
     * Classe 'interna' per facilitare il reperimento. </p>
     */
    public static class Gui {

        /**
         * Centra una finestra nello schermo.
         *
         * @param finestra da centrare
         */
        public static void centraFinestra(Finestra finestra) {
            /* invoca il metodo sovrascritto della libreria specifica */
            LibGui.centraFinestra(finestra);
        }


        /**
         * Centra una finestra nello schermo.
         * <p/>
         * La finestra viene impacchettata <br> La finestra viene centrata <br> La finestra viene
         * resa visibile <br>
         *
         * @param finestra da centrare
         */
        public static void centraFinestra(Window finestra) {
            /* invoca il metodo sovrascritto della libreria specifica */
            LibGui.centraFinestra(finestra);
        }


        /**
         * Centra una finestra in un'altra finestra.
         *
         * @param fin        finestra da centrare
         * @param finEsterna finestra contenitore esterno (di defalt lo schermo)
         */
        public static void centraFinestra(Finestra fin, Finestra finEsterna) {
            /* invoca il metodo sovrascritto della libreria specifica */
            LibGui.centraFinestra(fin, finEsterna);
        }


        /**
         * Centra una finestra nello schermo.
         * <p/>
         * La finestra non viene impacchettata <br> La finestra non viene resa visibile <br>
         *
         * @param fin        finestra da centrare
         * @param finEsterna finestra contenitore esterno (di default l'area utilizzabile dello
         *                   schermo)
         */
        public static void centraWindow(Window fin, Window finEsterna) {
            /* invoca il metodo sovrascritto della libreria specifica */
            LibGui.centraWindow(fin, finEsterna);
        }


        /**
         * Centra un dialogo nello schermo.
         *
         * @param dialogo da centrare
         */
        public static void centraFinestra(JDialog dialogo) {
            /* invoca il metodo sovrascritto della libreria specifica */
            LibGui.centraFinestra(dialogo);
        }


        /**
         * Forza una finestra a starci nell'area disponibile corrente.
         * <p/>
         * Se non ci sta ne riduce le dimensioni.
         *
         * @param window finestra da controllare
         */
        public static void fitWindowToScreen(Window window) {
            LibGui.fitWindowToScreen(window);
        }


        /**
         * Forza una finestra alle dimensioni dello schermo intero corrente.
         * <p/>
         * Se non ci sta ne riduce le dimensioni.
         *
         * @param window finestra da controllare
         */
        public static void fitWindowToFullScreen(Window window) {
            LibGui.fitWindowToFullScreen(window);
        }


        /**
         * Controlla se il look è in uso.
         *
         * @param look nome del Look da testare
         *
         * @return vero se il look è in uso
         */
        public static boolean usaLook(String look) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibGui.usaLook(look);
        }
    } // fine della classe 'interna'


    /**
     * Classe 'interna' per facilitare il reperimento. </p>
     */
    public static class Data {

        /**
         * Aggiunge ad un una data i giorni indicati.
         * <p/>
         * Se i giorni sono negativi, li sottrae <br> Esegue solo se la data è valida (non nulla e
         * non vuota) <br>
         *
         * @param data   di riferimento
         * @param giorni da aggiungere (positivo o negativo)
         *
         * @return la data risultante
         */
        public static Date add(Date data, int giorni) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibDate.add(data, giorni);
        }


        /**
         * Differenza in giorni tra le date indicate.
         * <p/>
         * Esegue solo se la data sono valide
         * <p/>
         * <p/>
         * Se le due date sono uguali la differenza è zero
         * Se le due date sono consecutive la differenza è uno, ecc...
         *
         * @param dataUno finale
         * @param dataDue iniziale
         *
         * @return il numero di giorni passati tra le due date (positivo se la prima data e'
         *         precedente la seconda, altrimenti negativo)
         */
        public static int diff(Date dataUno, Date dataDue) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibDate.diff(dataUno, dataDue);
        }


        /**
         * Elimina l'ora da una data.
         * <p/>
         * Imposta le informazioni sull'ora a zero
         *
         * @param data la data dalla quale eliminare l'ora
         *
         * @return la data senza ora
         */
        public static Date dropTime(Date data) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibDate.dropTime(data);
        }


        /**
         * Aggiunge ad un timestamp i secondi indicati.
         * <p/>
         * Se i secondi sono negativi, li sottrae <br>
         *
         * @param time    timestamp di riferiemento
         * @param secondi da aggiungere
         *
         * @return la data risultante
         */
        public static Timestamp add(Timestamp time, int secondi) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibDate.add(time, secondi);
        }


        /**
         * Calcola quanti secondi sono passati tra due timestamp.
         * <p/>
         *
         * @param timeStamp1 il primo timestamp
         * @param timeStamp2 il secondo timestamp
         *
         * @return il numero di secondi passati tra i due timestamp (positivo se il primo e'
         *         precedente il secondo, altrimenti negativo)
         */
        public static int secondi(Timestamp timeStamp1, Timestamp timeStamp2) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibDate.secondi(timeStamp1, timeStamp2);
        }


        /**
         * Controlla se una data è precedente ad una data di riferimento.
         * <p/>
         * Esegue solo se le date sono valide (non nulle e non vuote) <br> Gli estremi non sono
         * compresi <br> Se le date coincidono, la risposta è falsa <br>
         *
         * @param dataRif  di riferimento
         * @param dataTest da controllare
         *
         * @return vero se la seconda data è precedente alla prima
         */
        public static boolean isPrecedente(Date dataRif, Date dataTest) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibDate.isPrecedente(dataRif, dataTest);
        }


        /**
         * Controlla se una data è precedente ad una data di riferimento.
         * <p/>
         * Esegue solo se le date sono valide (non nulle e non vuote) <br> Gli estremi sono compresi
         * <br> Se le date coincidono, la risposta è vera <br>
         *
         * @param dataRif  di riferiemento
         * @param dataTest da controllare
         *
         * @return vero se la seconda data è precedente alla prima
         */
        public static boolean isPrecedenteUguale(Date dataRif, Date dataTest) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibDate.isPrecedenteUguale(dataRif, dataTest);
        }


        /**
         * Controlla se una data è posteriore ad una data di riferimento.
         * <p/>
         * Esegue solo se le date sono valide (non nulle e non vuote) <br>
         * Gli estremi non sono compresi <br>
         * Se le date coincidono, la risposta è falsa <br>
         *
         * @param dataRif  di riferimento
         * @param dataTest da controllare
         *
         * @return vero se la seconda data è posteriore alla prima
         */
        public static boolean isPosteriore(Date dataRif, Date dataTest) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibDate.isPosteriore(dataRif, dataTest);
        }


        /**
         * Controlla se una data è posteriore ad una data di riferimento.
         * <p/>
         * Esegue solo se le date sono valide (non nulle e non vuote) <br> Gli estremi sono compresi
         * <br> Se le date coincidono, la risposta è vera <br>
         *
         * @param dataRif  di riferiemento
         * @param dataTest da controllare
         *
         * @return vero se la seconda data è posteriore alla prima
         */
        public static boolean isPosterioreUguale(Date dataRif, Date dataTest) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibDate.isPosterioreUguale(dataRif, dataTest);
        }


        /**
         * Controlla se una data è compresa tra due date di riferimento.
         * <p/>
         * Esegue solo se le date sono valide (non nulle e non vuote) <br>
         * Gli estremi non sono compresi <br>
         * Se la data coincide con uno degli estremi, la risposta è falsa <br>
         *
         * @param inizio   data di riferimento iniziale dell'intervallo
         * @param fine     data di riferimento finale dell'intervallo
         * @param dataTest data da controllare
         *
         * @return vero se la data è compresa nell'intervallo di date
         */
        public static boolean isCompresa(Date inizio, Date fine, Date dataTest) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibDate.isCompresa(inizio, fine, dataTest);
        }


        /**
         * Controlla se una data è compresa tra due date di riferimento.
         * <p/>
         * Esegue solo se le date sono valide (non nulle e non vuote) <br>
         * Gli estremi sono compresi<br>
         * Se la data coincide con uno degli estremi, la risposta è vera <br>
         *
         * @param inizio   data di riferimento iniziale dell'intervallo
         * @param fine     data di riferimento finale dell'intervallo
         * @param dataTest data da controllare
         *
         * @return vero se la data è compresa nell'intervallo di date
         */
        public static boolean isCompresaUguale(Date inizio, Date fine, Date dataTest) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibDate.isCompresaUguale(inizio, fine, dataTest);
        }


        /**
         * Controlla se un intervallo check è completamente escluso dall'intervallo rif.
         * <p/>
         *
         * @param dataIniCheck inizio del periodo da controllare
         * @param dataEndCheck fine del periodo da controllare
         * @param dataIniRif   inizio del periodo di riferimento
         * @param dataEndRif   fine del periodo di riferimento
         *
         * @return vero se il periodo da controllare è completamente esterno al periodo di
         *         riferimento
         */
        public static boolean isPeriodoEscluso(Date dataIniCheck,
                                               Date dataEndCheck,
                                               Date dataIniRif,
                                               Date dataEndRif) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibDate.isPeriodoEscluso(dataIniCheck, dataEndCheck, dataIniRif, dataEndRif);
        }


        /**
         * Controlla se una data è vuota.
         * <p/>
         * Una data nulla si considera vuota <br>
         *
         * @param data data da controllare
         *
         * @return vero se la data è vuota
         */
        public static boolean isVuota(Date data) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibDate.isVuota(data);
        }


        /**
         * Controlla se una data è valida.
         * <p/>
         * Una data nulla si considera non valida <br>
         *
         * @param data data da controllare
         *
         * @return vero se la data è valida
         */
        public static boolean isValida(Date data) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return !LibDate.isVuota(data);
        }


        /**
         * Controlla se due date sono in sequenza.
         * <p/>
         * Entrambe le date non devono essere vuote o nulle
         * Due date coincidenti sono valide
         * <br>
         *
         * @param prima   data da controllare
         * @param seconda data da controllare
         *
         * @return vero se la seconda data è posteriore alla prima
         */
        public static boolean isSequenza(Date prima, Date seconda) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibDate.isPosterioreUguale(prima, seconda);
        }


        /**
         * Restituisce una data vuota.
         * <p/>
         * La data vuota e' una data particolare convenzionalmente interpretata come vuota
         *
         * @return la data vuota
         */
        public static Date getVuota() {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibDate.getVuota();
        }


        /**
         * Restituisce la data corrente.
         *
         * @return la data del giorno
         */
        public static Date getCorrente() {
            /* valore di ritorno */
            return LibDate.getCorrente();
        }


        /**
         * Restituisce l'ora corrente.
         *
         * @return l'ora corrente
         */
        public static String getOra() {
            return LibDate.getOra();
        }


        /**
         * Restituisce la data minore tra due date.
         *
         * @param data1 data da controllare
         * @param data2 data da controllare
         *
         * @return la data minore
         */
        public static Date getMin(Date data1, Date data2) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibDate.getMin(data1, data2);
        }


        /**
         * Restituisce la data maggiore tra due date.
         *
         * @param data1 data da controllare
         * @param data2 data da controllare
         *
         * @return la data maggiore
         */
        public static Date getMax(Date data1, Date data2) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibDate.getMax(data1, data2);
        }


        /**
         * Restituisce la data maggiore tra una serie di date.
         *
         * @param date serie di date
         *
         * @return la data maggiore
         */
        public static Date getMax(Date... date) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibDate.getMax(date);
        }


        /**
         * Restituisce un Timestamp relativo all'ora corrente.
         *
         * @return un Timestamp
         */
        public static Timestamp getTimestampCorrente() {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibDate.getTimestampCorrente();
        }


        /**
         * Restituisce un Time relativo all'ora corrente.
         *
         * @return un Time
         */
        public static Time getOraCorrente() {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibDate.getOraCorrente();
        }


        /**
         * Restituisce i secondi passati dalla mezzanotte di oggi.
         *
         * @return i secondi passati
         */
        public static int getSecondiCorrenti() {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibDate.getSecondiCorrenti();
        }


        /**
         * Converte una data in stringa.
         * <p/>
         *
         * @param data da convertire
         *
         * @return la stringa corrispondente
         */
        public static String getStringa(Date data) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibDate.getStringa(data);
        }


        /**
         * Converte una stringa in data.
         * <p/>
         *
         * @param stringa da convertire
         *
         * @return la data corrispondente
         */
        public static Date getData(String stringa) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibDate.getData(stringa);
        }


        /**
         * Restituisce la data corrente.
         * <p/>
         *
         * @return data corrente nel formato 2-9-07
         */
        public static String getDataNum() {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibDate.getDataNum();
        }


        /**
         * Restituisce la data corrente.
         * <p/>
         *
         * @return data corrente nel formato 2-9-2007
         */
        public static String getDataNumerica() {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibDate.getDataNumerica();
        }


        /**
         * Restituisce la data corrente.
         * <p/>
         *
         * @return data corrente nel formato 2-nov-2007
         */
        public static String getDataBreve() {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibDate.getDataBreve();
        }


        /**
         * Restituisce la data formattata.
         * <p/>
         *
         * @return data corrente nel formato 2-nov-2007
         */
        public static String getDataBreve(Date data) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibDate.getDataBreve(data);
        }


        /**
         * Restituisce la data formattata.
         * <p/>
         *
         * @return data corrente nel formato 2-nov
         */
        public static String getDataBrevissima(Date data) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibDate.getDataBrevissima(data);
        }
        
    	/**
    	 * Ritorna una data in forma YYYYMMDD
    	 * @param data la data
    	 * @return la stringa YYYYMMDD
    	 */
    	public static String getDateYYYYMMDD(Date data){
            return LibDate.getDateYYYYMMDD(data);
    	}


        /**
         * Restituisce la data formattata estesa.
         * <p/>
         *
         * @param data da convertire
         *
         * @return data nel formato esteso
         */
        public static String getDataEstesa(Date data) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibDate.getDataEstesa(data);
        }


        /**
         * Restituisce la data corrente.
         * <p/>
         *
         * @return data corrente nel formato 2-agosto-2007
         */
        public static String getDataEstesa() {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibDate.getDataEstesa();
        }


        /**
         * Crea una data.
         * <p/>
         *
         * @param giorno il giorno del mese (1 per il primo)
         * @param mese   il mese dell'anno (1 per gennaio)
         * @param anno   l'anno
         *
         * @return la data creata
         */
        public static Date creaData(int giorno, int mese, int anno) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibDate.creaData(giorno, mese, anno);
        }


        /**
         * Restituisce il giorno della settimana.
         * <p/>
         * Giorno scritto per intero <br>
         *
         * @param data da elaborare
         *
         * @return giorno della settimana
         */
        public static String getGiorno(Date data) {
            /* invoca il metodo della libreria specifica */
            return LibDate.getGiorno(data);
        }


        /**
         * Ritorna l'anno di una data fornita.
         * <p/>
         *
         * @param data fornita
         *
         * @return l'anno della data
         */
        public static int getAnno(Date data) {
            /* invoca il metodo della libreria specifica */
            return LibDate.getAnno(data);
        }


        /**
         * Ritorna il numero del mese di una data fornita.
         * <p/>
         *
         * @param data fornita
         *
         * @return il numero del mese (1 per Gennaio)
         */
        public static int getNumeroMese(Date data) {
            /* invoca il metodo della libreria specifica */
            return LibDate.getNumeroMese(data);
        }


        /**
         * Ritorna il nome del mese dato il numero del mese.
         * <p/>
         *
         * @param numMese numero del mese
         *
         * @return il nome del mese
         */
        public static String getNomeMese(int numMese) {
            /* invoca il metodo della libreria specifica */
            return LibDate.getNomeMese(numMese);
        }


        /**
         * Ritorna il numero del giorno della settimana di una data fornita.
         * <p/>
         *
         * @param data fornita
         *
         * @return il numero del giorno della settimana (1=dom, 7=sab)
         */
        public static int getNumeroGiornoSettimana(Date data) {
            /* invoca il metodo della libreria specifica */
            return LibDate.getNumeroGiornoSettimana(data);
        }


        /**
         * Ritorna il numero del giorno del mese di una data fornita.
         * <p/>
         *
         * @param data fornita
         *
         * @return il numero del giorno del mese
         */
        public static int getNumeroGiorno(Date data) {
            /* invoca il metodo della libreria specifica */
            return LibDate.getNumeroGiorno(data);
        }


        /**
         * Ritorna il numero del giorno nell'anno di una data fornita.
         * <p/>
         *
         * @param data fornita
         *
         * @return il numero del giorno nell'anno
         */
        public static int getNumeroGiornoInAnno(Date data) {
            /* invoca il metodo della libreria specifica */
            return LibDate.getNumeroGiornoInAnno(data);
        }


        /**
         * Ritorna il numero del giorno del mese di una data fornita.
         * <p/>
         *
         * @param data fornita
         *
         * @return il numero del giorno del mese
         */
        public static int getNumeroGiornoInMese(Date data) {
            /* invoca il metodo della libreria specifica */
            return LibDate.getNumeroGiornoInMese(data);
        }



        /**
         * Ritorna il numero della settimana nell'anno di una data fornita.
         * <p/>
         *
         * @param data fornita
         *
         * @return il numero della settimana nell'anno
         */
        public static int getNumeroSettimanaInAnno(Date data) {
            /* invoca il metodo della libreria specifica */
            return LibDate.getNumeroSettimanaInAnno(data);
        }


        /**
         * Ritorna la data del primo lunedì di una data settimana.
         * <p/>
         *
         * @param numSett il numero di settimana
         * @param anno    l'anno al quale il numero di settimana è riferito
         *
         * @return il primo giorno (lunedì) della settimana data
         */
        public static Date getPrimoGiornoSettimana(int numSett, int anno) {
            /* invoca il metodo della libreria specifica */
            return LibDate.getPrimoGiornoSettimana(numSett, anno);
        }


        /**
         * Ritorna il numero delle ore di una data fornita.
         * <p/>
         *
         * @param data fornita
         *
         * @return il numero delle ore
         */
        public static int getNumeroOre(Date data) {
            /* invoca il metodo della libreria specifica */
            return LibDate.getNumeroOre(data);
        }


        /**
         * Ritorna il numero dei minuti di una data fornita.
         * <p/>
         *
         * @param data fornita
         *
         * @return il numero dei minuti
         */
        public static int getNumeroMinuti(Date data) {
            /* invoca il metodo della libreria specifica */
            return LibDate.getNumeroMinuti(data);
        }


        /**
         * Ritorna il numero dei secondi di una data fornita.
         * <p/>
         *
         * @param data fornita
         *
         * @return il numero dei secondi
         */
        public static int getNumeroSecondi(Date data) {
            /* invoca il metodo della libreria specifica */
            return LibDate.getNumeroSecondi(data);
        }


        /**
         * Ritorna l'anno corrente.
         * <p/>
         *
         * @return l'anno della data corrente
         */
        public static int getAnnoCorrente() {
            /* invoca il metodo della libreria specifica */
            return LibDate.getAnnoCorrente();
        }


        /**
         * Ritorna il numero del mese corrente.
         * <p/>
         *
         * @return il numero del mese corrente
         */
        public static int getNumeroMeseCorrente() {
            /* invoca il metodo della libreria specifica */
            return LibDate.getNumeroMeseCorrente();
        }


        /**
         * Ritorna il numero del giorno corrente.
         * <p/>
         *
         * @return il numero del giorno corrente
         */
        public static int getNumeroGiornoCorrente() {
            /* invoca il metodo della libreria specifica */
            return LibDate.getNumeroGiornoCorrente();
        }


        /**
         * Ritorna il numero dell'ora corrente.
         * <p/>
         *
         * @return il numero dell'ora corrente
         */
        public static int getNumeroOreCorrente() {
            /* invoca il metodo della libreria specifica */
            return LibDate.getNumeroOreCorrente();
        }


        /**
         * Ritorna il numero di minuti corrente.
         * <p/>
         *
         * @return il numero di minuti corrente
         */
        public static int getNumeroMinutiCorrente() {
            /* invoca il metodo della libreria specifica */
            return LibDate.getNumeroMinutiCorrente();
        }


        /**
         * Ritorna il numero di secondi corrente.
         * <p/>
         *
         * @return il numero di secondi corrente
         */
        public static int getNumeroSecondiCorrente() {
            /* invoca il metodo della libreria specifica */
            return LibDate.getNumeroSecondiCorrente();
        }


        /**
         * Ritorna la data corrispondente all'ultimo giorno del mese relativo a una data fornita.
         * <p/>
         *
         * @param data fornita
         *
         * @return la data rappresentante l'ultimo giorno del mese
         */
        public static Date getFineMese(Date data) {
            /* invoca il metodo della libreria specifica */
            return LibDate.getFineMese(data);
        }


        /**
         * Ritorna una matrice di date comprese nel periodo
         * <p/>
         *
         * @param dataIniPeriodo  inizio del periodo
         * @param dataFinePeriodo fine del periodo da controllare
         *
         * @return matrice di date comprese nel periodo
         */
        public static Date[] getDateComprese(Date dataIniPeriodo, Date dataFinePeriodo) {
            /* invoca il metodo della libreria specifica */
            return LibDate.getDateComprese(dataIniPeriodo, dataFinePeriodo);
        }


        /**
         * Ordina cronologicamente una mappa di date.
         * <p/>
         *
         * @param mappa di date/valore, da ordinare
         *
         * @return mappa con la chiave data, ordinata cronologicamente
         */
        public static LinkedHashMap<Date, Double> ordinaMappa(LinkedHashMap<Date, Double> mappa) {
            /* invoca il metodo della libreria specifica */
            return LibDate.ordinaMappa(mappa);
        }


        /**
         * Costruisce la data per il 1° gennaio dell'anno in corso.
         * <p/>
         *
         * @return primo gennaio dell'anno corrente
         */
        public static Date getPrimoGennaio() {
            /* invoca il metodo della libreria specifica */
            return LibDate.getPrimoGennaio();
        }


        /**
         * Costruisce la data per il 1° gennaio dell'anno indicato.
         * <p/>
         *
         * @param anno di riferimento
         *
         * @return primo gennaio dell'anno
         */
        public static Date getPrimoGennaio(int anno) {
            /* invoca il metodo della libreria specifica */
            return LibDate.getPrimoGennaio(anno);
        }


        /**
         * Costruisce la data per il 31° dicembre dell'anno indicato.
         * <p/>
         *
         * @param anno di riferimento
         *
         * @return ultimo dell'anno
         */
        public static Date getTrentunoDicembre(int anno) {
            /* invoca il metodo della libreria specifica */
            return LibDate.getTrentunoDicembre(anno);
        }


        /**
         * Restituisce un intero che identifica l'indice di un dato giorno dell'anno.
         * <p/>
         * I giorni sono collocati su una matrice di 31 righe e 12 colonne
         * quindi questa funzione può restituire anche il numero progressivo
         * di giorni non esistenti nel calendario.
         *
         * @param giorno numero del giorno (da 1 a 31)
         * @param mese   numero del mese (da 1 a 12)
         *
         * @return il numero progressivo del giorno
         */
        public static int getIndiceGiornoDellAnno(int giorno, int mese) {
            /* invoca il metodo della libreria specifica */
            return LibDate.getIndiceGiornoDellAnno(giorno, mese);
        }


        /**
         * Calcola il periodo intersezione tra due periodi
         * <p/>
         *
         * @param p1 il primo periodo
         * @param p2 il secondo periodo
         *
         * @return il il periodo intersezione, null se non si intersecano
         */
        public static DueDate getIntersezionePeriodi(DueDate p1, DueDate p2) {
            /* invoca il metodo della libreria specifica */
            return LibDate.getIntersezionePeriodi(p1, p2);
        }


//        /**
//         * Calcola il numero di giorni intercorsi tra due date
//         * <p/>
//         *
//         * @param d1 la prima data
//         * @param d2 la seconda data
//         *
//         * @return il numero di giorni intercorsi
//         */
//        public static int getQuantiGiorni(Date d1, Date d2) {
//            /* invoca il metodo della libreria specifica */
//            return LibDate.getQuantiGiorni(d1, d2);
//        }


    } // fine della classe 'interna'


    /**
     * Classe 'interna' per facilitare il reperimento. </p>
     */
    public static class Camp {

        /**
         * Ritorna la chiave completa di un campo.
         *
         * @param nomeCampo il nome interno del campo
         * @param modulo    il modulo di riferimento del campo
         *
         * @return la chiave del campo
         */
        public static String chiaveCampo(String nomeCampo, Modulo modulo) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibCampo.chiaveCampo(nomeCampo, modulo);
        }


        /**
         * Ritorna la chiave completa di un campo.
         *
         * @param nomeCampo il nome interno del campo
         *
         * @return la chiave del campo
         */
        public static String chiaveCampo(String nomeCampo) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibCampo.chiaveCampo(nomeCampo);
        }


        /**
         * Ritorna un campo dal Progetto data la chiave.
         * <p/>
         *
         * @param chiaveCampo la chiave del campo (modulo.campo)
         *
         * @return il campo corrispondente
         */
        public static Campo getCampo(String chiaveCampo) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibCampo.getCampo(chiaveCampo);
        }


        /**
         * Ritorna una collezione contenente solo i campi fisici.
         * <p/>
         * Data una collezione di campi, restituisce un'altra collezione di campi contenente solo i
         * campi fisici <br>
         *
         * @param collezioneCampi completa
         *
         * @return collezione con i soli campi fisici
         */
        public static LinkedHashMap<String, Campo> filtraCampiFisici(LinkedHashMap<String, Campo> collezioneCampi) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibCampo.filtraCampiFisici(collezioneCampi);
        }


        /**
         * Ritorna una collezione contenente solo i campi fisici e non fissi (algos).
         * <p/>
         * Data una collezione di campi, restituisce un'altra collezione di campi contenente solo i
         * campi fisici e non fissi (algos) <br>
         *
         * @param collezioneCampi completa
         *
         * @return collezione con i soli campi fisici
         */
        public static LinkedHashMap<String, Campo> filtraCampiFisiciNoAlgos(LinkedHashMap<String, Campo> collezioneCampi) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibCampo.filtraCampiFisiciNoAlgos(collezioneCampi);
        }


        /**
         * Ritorna una collezione contenente solo i campi visibili.
         * <p/>
         * Data una collezione di campi, restituisce un'altra collezione di campi contenente solo i
         * campi visibili nella lista di default <br>
         *
         * @param collezioneCampi completa
         *
         * @return collezione con i soli campi visibili nella lista di default
         */
        public static LinkedHashMap<String, Campo> filtraCampiVisibili(LinkedHashMap<String, Campo> collezioneCampi) {

            /* invoca il metodo sovrascritto della libreria specifica */
            return LibCampo.filtraCampiVisibili(collezioneCampi);
        }


        /**
         * Recupera un campo-valore da una lista.
         * <p/>
         *
         * @param lista di elementi campo-valore
         * @param campo da recuperare
         *
         * @return il campo-valore richiesto
         */
        public static CampoValore getCampoValore(ArrayList<CampoValore> lista, Campo campo) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibCampo.getCampoValore(lista, campo);
        }


        /**
         * Si assicura che una lista di CampiValore contenga un dato campoValore con un valore
         * valido (non vuoto).
         * <p/>
         * Se manca o il valore è vuoto, lo aggiunge ora con il valore fornito.
         *
         * @param lista  di elementi campo-valore
         * @param campo  da recuperare
         * @param valore da utilizzare nel caso che il CampoValore venga creato ora.
         *
         * @return il campo-valore richiesto o creato
         */
        public static CampoValore chkCampoValoreValido(ArrayList<CampoValore> lista,
                                                       Campo campo,
                                                       Object valore) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibCampo.chkCampoValoreValido(lista, campo, valore);
        }


        /**
         * Forza una lista ad oggetti Campo.
         * <p/>
         * In un elenco di oggetti, che possono essere Campi o Nomi, sostituisce tutti gli oggetti
         * che sono Nomi con i corrispondenti oggetti Campo <br> Al termine della conversione
         * l'elenco originale contiene solo oggetti Campo <br>
         *
         * @param lista    di oggetti contenente Campi o Nomi
         * @param unModulo il modulo dal quale recuperare i campi
         */
        public static ArrayList<Campo> convertiCampi(ArrayList lista, Modulo unModulo) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibCampo.convertiCampi(lista, unModulo);
        }

    } // fine della classe 'interna'


    /**
     * Classe 'interna' per facilitare il reperimento. </p>
     */
    public static class Html {


        /**
         * Costruisce l'inizio di una pagina html.
         *
         * @param utf tipo di codifica del testo
         * @param ref riferimenti esterni (pagine css)
         *
         * @return inizio della pagina
         */
        public static String getIniPagina(LibHtml.UTF utf, String ref) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibHtml.getIniPagina(utf, ref);
        }


        /**
         * Costruisce l'inizio di una pagina html.
         *
         * @param utf tipo di codifica del testo
         *
         * @return inizio della pagina
         */
        public static String getIniPagina(LibHtml.UTF utf) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibHtml.getIniPagina(utf);
        }


        /**
         * Costruisce l'inizio di una pagina html.
         *
         * @param ref riferimenti esterni (pagine css)
         *
         * @return inizio della pagina
         */
        public static String getIniPagina(String ref) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibHtml.getIniPagina(ref);
        }


        /**
         * Costruisce l'inizio di una pagina html.
         *
         * @return inizio della pagina
         */
        public static String getIniPagina() {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibHtml.getIniPagina();
        }


        /**
         * Costruisce l'inizio di una tabella html.
         *
         * @return inizio della tabella
         */
        public static String getIniTabella() {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibHtml.getIniTabella();
        }


        /**
         * Costruisce la referenza al file css.
         *
         * @param nomeFile path locale
         *
         * @return testo della referenza
         */
        public static String getCss(String nomeFile) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibHtml.getCss(nomeFile);
        }


        /**
         * Allinea a destra il testo.
         *
         * @param testo da allineare
         *
         * @return testo allineato
         */
        public static String setDestra(String testo) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibHtml.setDestra(testo);
        }


        /**
         * Costruisce i titoli di una tabella html.
         *
         * @param lista dei titoli in formato stringa di testo
         *
         * @return titoli della tabella in formato html
         */
        public static String getTitoliTabella(ArrayList<String> lista) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibHtml.getTitoliTabella(lista);
        }


        /**
         * Costruisce i titoli di una tabella html.
         *
         * @param lista dei titoli in formato stringa di testo ed intero
         *
         * @return titoli della tabella in formato html
         */
        public static String getTitoliTab(ArrayList<IntStringa> lista) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibHtml.getTitoliTab(lista);
        }


        /**
         * Costruisce i titoli di una tabella html.
         * <p/>
         * I titoli possono essere separati da \t o da virgola <br>
         *
         * @param titoli stringa di testo coi titoli in formato normale
         * @param sep    separatore dei titoli (tab o virgola)
         *
         * @return titoli della tabella in formato html
         */
        public static String getTitoliTabella(String titoli, String sep) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibHtml.getTitoliTabella(titoli, sep);
        }


        /**
         * Costruisce i titoli di una tabella html.
         * <p/>
         * I titoli devono essere separati da virgola <br>
         *
         * @param titoli stringa di testo coi titoli in formato normale
         *
         * @return titoli della tabella in formato html
         */
        public static String getTitoliTabella(String titoli) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibHtml.getTitoliTabella(titoli);
        }


        /**
         * Costruisce la riga di una tabella html.
         * <p/>
         *
         * @param lista dei campi in formato stringa di testo
         *
         * @return campi della tabella in formato html
         */
        public static String getRigaTabella(ArrayList<String> lista) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibHtml.getRigaTabella(lista);
        }


        /**
         * Costruisce la riga di una tabella html.
         * <p/>
         * I campi della riga devono essere separati da tab <br>
         *
         * @param riga stringa di testo coi campi in formato normale
         *
         * @return campi della tabella in formato html
         */
        public static String getRigaTabella(String riga) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibHtml.getRigaTabella(riga);
        }


        /**
         * Costruisce la fine di una tabella html.
         *
         * @return fine della tabella
         */
        public static String getEndTabella() {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibHtml.getEndTabella();
        }


        /**
         * Costruisce la fine di una pagina html.
         *
         * @return fine della pagina
         */
        public static String getEndPagina() {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibHtml.getEndPagina();
        }


        /**
         * Costruisce il riferimento ad un link esterno.
         * <p/>
         *
         * @param link   riferimento attivo
         * @param titolo tool tip testo
         * @param testo  testo visibile
         *
         * @return testo html
         */
        public static String getLink(String link, String titolo, String testo) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibHtml.getLink(link, titolo, testo);
        }


        /**
         * Costruisce il riferimento ad un link della wikipedia.
         * <p/>
         *
         * @param link   riferimento attivo
         * @param titolo tool tip testo
         * @param testo  testo visibile
         *
         * @return testo html
         */
        public static String getLinkWiki(String link, String titolo, String testo) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibHtml.getLinkWiki(link, titolo, testo);
        }
    } // fine della classe 'interna'


    /**
     * Classe 'interna' per facilitare il reperimento. </p>
     */
    public static class Web {

        /**
         * Costruisce un oggetto Url dall'indirizzo.
         * <p/>
         *
         * @param indirizzo host + path completo della pagina
         *
         * @return URL costruito
         */
        public static URL getUrl(String indirizzo) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibWeb.getUrl(indirizzo);
        }


        /**
         * Costruisce la connessione.
         * <p/>
         * Utilizza il protocollo FPT <br>
         *
         * @return connessione
         */
        public static FtpWrap getFtp() {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibWeb.getFtp();
        }


        /**
         * Carica sul server un file.
         * <p/>
         * Utilizza il protocollo FPT <br>
         *
         * @param pathLocale path completo del file locale
         * @param pathServer path completo del file sul server
         *
         * @return risultato dell'operazione
         */
        public static boolean upLoad(String pathLocale, String pathServer) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibWeb.upLoad(pathLocale, pathServer);
        }


        /**
         * Scarica dal server un file.
         * <p/>
         * Utilizza il protocollo FPT <br>
         *
         * @param pathServer path completo del file sul server
         * @param pathLocale path completo del file locale
         *
         * @return risultato dell'operazione
         */
        public static boolean downLoad(String pathServer, String pathLocale) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibWeb.downLoad(pathServer, pathLocale);
        }


        /**
         * Carica sul server (nella apposita cartella) un file.
         * <p/>
         * Utilizza il protocollo FPT <br>
         *
         * @param pathServer path completo del file sul server
         * @param testo      da caricare
         *
         * @return risultato dell'operazione
         */
        public static boolean upLoadTesto(String pathServer, String testo) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibWeb.upLoadTesto(pathServer, testo);
        }


        /**
         * Carica sul server (nella apposita cartella) un file.
         * <p/>
         * Utilizza il protocollo FPT <br>
         *
         * @param pathLocale path completo del file locale
         * @param pathServer path completo del file sul server
         *
         * @return risultato dell'operazione
         */
        public static boolean upLoadData(String pathLocale, String pathServer) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibWeb.upLoadData(pathLocale, pathServer);
        }


        /**
         * Scarica dal server (dalla apposita cartella) un file.
         * <p/>
         * Utilizza il protocollo FPT <br>
         *
         * @param pathServer path completo del file sul server
         * @param pathLocale path completo del file locale
         *
         * @return risultato dell'operazione
         */
        public static boolean downLoadData(String pathServer, String pathLocale) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibWeb.downLoadData(pathServer, pathLocale);
        }


        /**
         * Scarica dal server (dalla apposita cartella) il testo di un file.
         * <p/>
         * Utilizza il protocollo FPT <br>
         *
         * @param pathServer path completo del file sul server
         *
         * @return testo recuperato
         */
        public static String downLoadData(String pathServer) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibWeb.downLoadData(pathServer);
        }


        /**
         * Scarica dal server (dalla apposita cartella) il testo di un file.
         * <p/>
         * Utilizza il protocollo FPT <br>
         *
         * @param pathServer path completo del file sul server
         * @param ftp        connessione
         *
         * @return testo recuperato
         */
        public static String downLoadData(String pathServer, FtpWrap ftp) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibWeb.downLoadData(pathServer, ftp);
        }


        /**
         * Lista dei files contenuti in una cartella.
         * <p/>
         * Utilizza il protocollo FPT <br>
         *
         * @param pathDir path completo della cartella
         *
         * @return lista files
         */
        public static ArrayList<FTPFile> getFiles(String pathDir) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibWeb.getFiles(pathDir);
        }


        /**
         * Lista dei files contenuti in una cartella.
         * <p/>
         * Utilizza il protocollo FPT <br>
         *
         * @param pathDir path completo della cartella
         * @param ftp     connessione
         *
         * @return lista files
         */
        public static ArrayList<FTPFile> getFiles(String pathDir, FtpWrap ftp) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibWeb.getFiles(pathDir, ftp);
        }


        /**
         * Lista dei nomi dei files contenuti in una cartella.
         * <p/>
         * Utilizza il protocollo FPT <br>
         *
         * @param pathDir path completo della cartella
         *
         * @return lista nomi dei files
         */
        public static ArrayList<String> getNomiFiles(String pathDir) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibWeb.getNomiFiles(pathDir);
        }


        /**
         * Lista dei nomi dei files contenuti in una cartella.
         * <p/>
         * Utilizza il protocollo FPT <br>
         *
         * @param pathDir path completo della cartella
         * @param ftp     connessione
         *
         * @return lista nomi dei files
         */
        public static ArrayList<String> getNomiFiles(String pathDir, FtpWrap ftp) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibWeb.getNomiFiles(pathDir, ftp);
        }


        /**
         * Ultima data di modifica del file.
         * <p/>
         *
         * @param path il percorso assoluto del file
         * @param ftp  connessione
         *
         * @return data ultima modifica
         */
        public static Date getData(String path, FtpWrap ftp) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibWeb.getData(path, ftp);
        }


        /**
         * Carica sul server un testo.
         * <p/>
         * Utilizza il protocollo FPT <br>
         *
         * @param host     del sito web
         * @param utente   nickName
         * @param password dell'utente
         * @param server   nome del file sul server (inizia con /)
         * @param post     testo da caricare sulla pagina
         *
         * @return risultato dell'operazione
         */
        public static boolean upLoadTesto(String host,
                                          String utente,
                                          String password,
                                          String server,
                                          String post) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibWeb.upLoadTesto(host, utente, password, server, post);
        }


        /**
         * Carica sul server un file locale.
         * <p/>
         * Utilizza il protocollo FPT <br>
         *
         * @param host     del sito web
         * @param utente   nickName
         * @param password dell'utente
         * @param server   nome del file sul server (inizia con /)
         * @param locale   nome del file locale
         *
         * @return risultato dell'operazione
         */
        public static boolean upLoadFile(String host,
                                         String utente,
                                         String password,
                                         String server,
                                         String locale) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibWeb.upLoadFile(host, utente, password, server, locale);
        }


        /**
         * Chiude la connessione.
         * <p/>
         * Utilizza il protocollo FPT <br>
         *
         * @param ftp connessione
         */
        public static void chiudeFtp(FtpWrap ftp) {
            /* invoca il metodo sovrascritto della libreria specifica */
            LibWeb.chiudeFtp(ftp);
        }


        /**
         * Spedisce una lettera.
         *
         * @param destinatari della lettera
         * @param oggetto     della lettera
         * @param testo       della lettera
         * @param mittente    della lettera
         * @param allegato    alla lettera
         *
         * @return vero se spedita
         */
        public static boolean postMail(String destinatari[],
                                       String oggetto,
                                       String testo,
                                       String mittente,
                                       String allegato) throws MessagingException {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibWeb.postMail(destinatari, oggetto, testo, mittente, allegato);
        }


        /**
         * Spedisce una lettera.
         *
         * @param destinatario della lettera
         * @param oggetto      della lettera
         * @param testo        della lettera
         * @param mittente     della lettera
         * @param allegato     alla lettera
         *
         * @return vero se spedita
         */
        public static boolean postMail(String destinatario,
                                       String oggetto,
                                       String testo,
                                       String mittente,
                                       String allegato) throws MessagingException {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibWeb.postMail(destinatario, oggetto, testo, mittente, allegato);
        }


        /**
         * Spedisce una lettera.
         *
         * @param destinatari della lettera
         * @param oggetto     della lettera
         * @param testo       della lettera
         * @param mittente    della lettera
         *
         * @return vero se spedita
         */
        public static boolean postMail(String destinatari[],
                                       String oggetto,
                                       String testo,
                                       String mittente) throws MessagingException {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibWeb.postMail(destinatari, oggetto, testo, mittente);
        }


        /**
         * Spedisce una lettera.
         *
         * @param destinatario della lettera
         * @param oggetto      della lettera
         * @param testo        della lettera
         * @param mittente     della lettera
         *
         * @return vero se spedita
         */
        public static boolean postMail(String destinatario,
                                       String oggetto,
                                       String testo,
                                       String mittente) throws MessagingException {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibWeb.postMail(destinatario, oggetto, testo, mittente);
        }


        /**
         * Spedisce una lettera.
         *
         * @param oggetto della lettera
         * @param testo   della lettera
         *
         * @return vero se spedita
         */
        public static boolean postMail(String oggetto, String testo) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibWeb.postMail(oggetto, testo);
        }


        /**
         * Recupera una pagina da un sito web.
         * <p/>
         *
         * @param pagina titolo della pagina
         *
         * @return testo html della pagina richiesta
         */
        public static String getPagina(String pagina) throws Exception {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibWeb.getPagina(pagina);
        }


        /**
         * Recupera una lista di tre colonne da un testo formattato csv.
         * <p/>
         * Il testo non deve contenere niente oltre alle informazioni significativa <br> La prima
         * riga (che non viene letta) contiene i nomi dei campi/colonne <br> Eventuali altre
         * informazioni presenti nella pagina, vanno escluse prima di questa chiamata <br> Ogni
         * record ha come delimitatore/separatore il carattere \n <br> Ogni campo ha come
         * delimitatore/separatore il carattere \t <br> Vengono recuperati i primi tre campi di ogni
         * record <br> Eventuali altri cmpi vengono ignorati <br>
         *
         * @param testo da elaborare <br>
         *
         * @return lista di tre stringhe, una per ognuno dei primi tre campi
         */
        public static ArrayList<TreStringhe> getCsvTre(String testo) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibWeb.getCsvTre(testo);
        }


        /**
         * Recupera una lista di quattro colonne da un testo formattato csv.
         * <p/>
         * Il testo non deve contenere niente oltre alle informazioni significativa <br> La prima
         * riga (che non viene letta) contiene i nomi dei campi/colonne <br> Eventuali altre
         * informazioni presenti nella pagina, vanno escluse prima di questa chiamata <br> Ogni
         * record ha come delimitatore/separatore il carattere \n <br> Ogni campo ha come
         * delimitatore/separatore il carattere \t <br> Vengono recuperati i primi quattro campi di
         * ogni record <br> Eventuali altri cmpi vengono ignorati <br>
         *
         * @param testo da elaborare <br>
         *
         * @return lista di quattro stringhe, una per ognuno dei primi quattro campi
         */
        public static ArrayList<QuattroStringhe> getCsvQuattro(String testo) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibWeb.getCsvQuattro(testo);
        }


        /**
         * Recupera una lista di cinque colonne da un testo formattato csv.
         * <p/>
         * Il testo non deve contenere niente oltre alle informazioni significativa <br> La prima
         * riga (che non viene letta) contiene i nomi dei campi/colonne <br> Eventuali altre
         * informazioni presenti nella pagina, vanno escluse prima di questa chiamata <br> Ogni
         * record ha come delimitatore/separatore il carattere \n <br> Ogni campo ha come
         * delimitatore/separatore il carattere \t <br> Vengono recuperati i primi cinque campi di
         * ogni record <br> Eventuali altri cmpi vengono ignorati <br>
         *
         * @param testo da elaborare <br>
         *
         * @return lista di cinque stringhe, una per ognuno dei primi cinque campi
         */
        public static ArrayList<CinqueStringhe> getCsvCinque(String testo) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibWeb.getCsvCinque(testo);
        }


        /**
         * Recupera una lista di sette colonne da un testo formattato csv.
         * <p/>
         * Il testo non deve contenere niente oltre alle informazioni significativa <br> La prima
         * riga (che non viene letta) contiene i nomi dei campi/colonne <br> Eventuali altre
         * informazioni presenti nella pagina, vanno escluse prima di questa chiamata <br> Ogni
         * record ha come delimitatore/separatore il carattere \n <br> Ogni campo ha come
         * delimitatore/separatore il carattere \t <br> Vengono recuperati i primi sette campi di
         * ogni record <br> Eventuali altri cmpi vengono ignorati <br>
         *
         * @param testo da elaborare <br>
         *
         * @return lista di sette stringhe, una per ognuno dei primi sette campi
         */
        public static ArrayList<SetteStringhe> getCsvSette(String testo) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibWeb.getCsvSette(testo);
        }


        /**
         * Recupera una lista di otto colonne da un testo formattato csv.
         * <p/>
         * Il testo non deve contenere niente oltre alle informazioni significativa <br> La prima
         * riga (che non viene letta) contiene i nomi dei campi/colonne <br> Eventuali altre
         * informazioni presenti nella pagina, vanno escluse prima di questa chiamata <br> Ogni
         * record ha come delimitatore/separatore il carattere \n <br> Ogni campo ha come
         * delimitatore/separatore il carattere \t <br> Vengono recuperati i primi otto campi di
         * ogni record <br> Eventuali altri cmpi vengono ignorati <br>
         *
         * @param testo da elaborare <br>
         *
         * @return lista di otto stringhe, una per ognuno dei primi otto campi
         */
        public static ArrayList<OttoStringhe> getCsvOtto(String testo) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibWeb.getCsvOtto(testo);
        }


        /**
         * Recupera una lista di dieci colonne da un testo formattato csv.
         * <p/>
         * Il testo non deve contenere niente oltre alle informazioni significativa <br> La prima
         * riga (che non viene letta) contiene i nomi dei campi/colonne <br> Eventuali altre
         * informazioni presenti nella pagina, vanno escluse prima di questa chiamata <br> Ogni
         * record ha come delimitatore/separatore il carattere \n <br> Ogni campo ha come
         * delimitatore/separatore il carattere \t <br> Vengono recuperati i primi dieci campi di
         * ogni record <br> Eventuali altri cmpi vengono ignorati <br>
         *
         * @param testo da elaborare <br>
         *
         * @return lista di dieci stringhe, una per ognuno dei primi dieci campi
         */
        public static ArrayList<DieciStringhe> getCsvDieci(String testo) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibWeb.getCsvDieci(testo);
        }


        /**
         * Costruisce un testo di due colonne formattate csv.
         * <p/>
         * Riceve una lista di dieci stringhe <br>
         *
         * @param lista  di due stringhe <br>
         * @param titoli delle colonne, separati da virgola <br>
         *
         * @return testo della tabella csv costruita
         */
        public static String setCsvDue(ArrayList<DueStringhe> lista, String titoli) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibWeb.setCsvDue(lista, titoli);
        }


        /**
         * Costruisce un testo di dieci colonne formattate csv.
         * <p/>
         * Riceve una lista di dieci stringhe <br>
         *
         * @param lista  di dieci stringhe <br>
         * @param titoli delle colonne, separati da virgola <br>
         *
         * @return testo della tabella csv costruita
         */
        public static String setCsv(ArrayList<DieciStringhe> lista, String titoli) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibWeb.setCsv(lista, titoli);
        }

    } // fine della classe 'interna'


    /**
     * Classe 'interna' per facilitare il reperimento. </p>
     */
    public static class Wiki {

        /**
         * Recupera una pagina dalla wikipedia italiana.
         * <p/>
         * Recupera il testo in modalità modifica (e non lettura) <br> Estrae il testo significativo
         * <br>
         *
         * @param pagina titolo della pagina
         *
         * @return testo significativo della pagina richiesta
         */
        public static String getPagina(String pagina) throws Exception {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibWiki.getPagina(pagina);
        }


        /**
         * Recupera una lista di una colonna dal testo di una pagina o di una tabella.
         * <p/>
         * La tabella deve contenere almeno una colonna significativa che viene recuperata;
         * eventuali altre colonne vengono ignorate <br> La tabella può contenere o meno una colonna
         * sinistra di numeri d'ordine che vengono ignorati <br>
         *
         * @param testo della pagina o della tabella  <br>
         *
         * @return lista di stringhe,
         */
        public static ArrayList<String> getPrettyUno(String testo) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibWiki.getPrettyUno(testo);
        }


        /**
         * Recupera una lista di una colonna da una tabella di una pagina.
         * <p/>
         * La pagina può contenere più di una tabella; quella selezionata viene indica nel parametro
         * <br> La tabella deve contenere almeno una colonna significativa che viene recuperata;
         * eventuali altre colonne vengono ignorate <br> La tabella può contenere o meno una colonna
         * sinistra di numeri d'ordine che vengono ignorati <br>
         *
         * @param testoPagina testo della pagina dove sono presenti più tabelle <br>
         * @param posTabella  tabella selezionata <br>
         *
         * @return lista di stringhe,
         */
        public static ArrayList<String> getPrettyUno(String testoPagina, int posTabella) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibWiki.getPrettyUno(testoPagina, posTabella);
        }


        /**
         * Recupera una lista di due colonne dal testo di una pagina o di una tabella.
         * <p/>
         * La tabella deve contenere almeno due colonne significative che vengono recuperate;
         * eventuali altre colonne vengono ignorate <br> La tabella può contenere o meno una colonna
         * sinistra di numeri d'ordine che vengono ignorati <br>
         *
         * @param testo della pagina o della tabella  <br>
         *
         * @return lista di due stringhe, una per ognuna delle prime due colonne significative della
         *         tabella
         */
        public static ArrayList<DueStringhe> getPrettyDue(String testo) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibWiki.getPrettyDue(testo);
        }


        /**
         * Recupera una lista di due colonne da una tabella di una pagina.
         * <p/>
         * La pagina può contenere più di una tabella; quella selezionata viene indica nel parametro
         * <br> La tabella deve contenere almeno due colonne significative che vengono recuperate;
         * eventuali altre colonne vengono ignorate <br> La tabella può contenere o meno una colonna
         * sinistra di numeri d'ordine che vengono ignorati <br>
         *
         * @param testoPagina testo della pagina dove sono presenti più tabelle <br>
         * @param posTabella  tabella selezionata <br>
         *
         * @return lista di due stringhe, una per ognuna delle prime due colonne significative della
         *         tabella selezionata
         */
        public static ArrayList<DueStringhe> getPrettyDue(String testoPagina, int posTabella) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibWiki.getPrettyDue(testoPagina, posTabella);
        }


        /**
         * Recupera una lista di tre colonne dal testo di una pagina o di una tabella.
         * <p/>
         * <p/>
         * La tabella deve contenere almeno tre colonne significative che vengono recuperate;
         * eventuali altre colonne vengono ignorate <br> La tabella può contenere o meno una colonna
         * sinistra di numeri d'ordine che vengono ignorati <br>
         *
         * @param testo della pagina o della tabella  <br>
         *
         * @return lista di tre stringhe, una per ognuna delle prime tre colonne significative della
         *         tabella
         */
        public static ArrayList<TreStringhe> getPrettyTre(String testo) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibWiki.getPrettyTre(testo);
        }


        /**
         * Recupera una lista di tre colonne da una tabella di una pagina.
         * <p/>
         * La pagina può contenere più di una tabella; quella selezionata viene indica nel parametro
         * <br> La tabella deve contenere almeno tre colonne significative che vengono recuperate;
         * eventuali altre colonne vengono ignorate <br> La tabella può contenere o meno una colonna
         * sinistra di numeri d'ordine che vengono ignorati <br>
         *
         * @param testoPagina testo della pagina dove sono presenti più tabelle <br>
         * @param posTabella  tabella selezionata <br>
         *
         * @return lista di tre stringhe, una per ognuna delle prime tre colonne significative della
         *         tabella selezionata
         */
        public static ArrayList<TreStringhe> getPrettyTre(String testoPagina, int posTabella) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibWiki.getPrettyTre(testoPagina, posTabella);
        }


        /**
         * Recupera una lista di quattro colonne dal testo di una pagina o di una tabella.
         * <p/>
         * <p/>
         * La tabella deve contenere almeno quattro colonne significative che vengono recuperate;
         * eventuali altre colonne vengono ignorate <br> La tabella può contenere o meno una colonna
         * sinistra di numeri d'ordine che vengono ignorati <br>
         *
         * @param testo della pagina o della tabella  <br>
         *
         * @return lista di quattro stringhe, una per ognuna delle prime quattro colonne
         *         significative della tabella
         */
        public static ArrayList<QuattroStringhe> getPrettyQuattro(String testo) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibWiki.getPrettyQuattro(testo);
        }


        /**
         * Recupera una lista di quattro colonne da una tabella di una pagina.
         * <p/>
         * La pagina può contenere più di una tabella; quella selezionata viene indica nel parametro
         * <br> La tabella deve contenere almeno quattro colonne significative che vengono
         * recuperate; eventuali altre colonne vengono ignorate <br> La tabella può contenere o meno
         * una colonna sinistra di numeri d'ordine che vengono ignorati <br>
         *
         * @param testoPagina testo della pagina dove sono presenti più tabelle <br>
         * @param posTabella  tabella selezionata <br>
         *
         * @return lista di quattro stringhe, una per ognuna delle prime quattro colonne
         *         significative della tabella selezionata
         */
        public static ArrayList<QuattroStringhe> getPrettyQuattro(String testoPagina,
                                                                  int posTabella) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibWiki.getPrettyQuattro(testoPagina, posTabella);
        }


        /**
         * Recupera una lista di cinque colonne dal testo di una pagina o di una tabella.
         * <p/>
         * <p/>
         * La tabella deve contenere almeno cinque colonne significative che vengono recuperate;
         * eventuali altre colonne vengono ignorate <br> La tabella può contenere o meno una colonna
         * sinistra di numeri d'ordine che vengono ignorati <br>
         *
         * @param testo della pagina o della tabella  <br>
         *
         * @return lista di cinque stringhe, una per ognuna delle prime cinque colonne significative
         *         della tabella
         */
        public static ArrayList<CinqueStringhe> getPrettyCinque(String testo) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibWiki.getPrettyCinque(testo);
        }


        /**
         * Recupera una lista di cinque colonne da una tabella di una pagina.
         * <p/>
         * La pagina può contenere più di una tabella; quella selezionata viene indica nel parametro
         * <br> La tabella deve contenere almeno cinque colonne significative che vengono
         * recuperate; eventuali altre colonne vengono ignorate <br> La tabella può contenere o meno
         * una colonna sinistra di numeri d'ordine che vengono ignorati <br>
         *
         * @param testoPagina testo della pagina dove sono presenti più tabelle <br>
         * @param posTabella  tabella selezionata <br>
         *
         * @return lista di cinque stringhe, una per ognuna delle prime cinque colonne significative
         *         della tabella selezionata
         */
        public static ArrayList<CinqueStringhe> getPrettyCinque(String testoPagina,
                                                                int posTabella) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibWiki.getPrettyCinque(testoPagina, posTabella);
        }


        /**
         * Recupera una lista di sei colonne dal testo di una pagina o di una tabella.
         * <p/>
         * <p/>
         * La tabella deve contenere almeno sei colonne significative che vengono recuperate;
         * eventuali altre colonne vengono ignorate <br> La tabella può contenere o meno una colonna
         * sinistra di numeri d'ordine che vengono ignorati <br>
         *
         * @param testo della pagina o della tabella  <br>
         *
         * @return lista di sei stringhe, una per ognuna delle prime sei colonne significative della
         *         tabella
         */
        public static ArrayList<SeiStringhe> getPrettySei(String testo) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibWiki.getPrettySei(testo);
        }


        /**
         * Recupera una lista di sei colonne da una tabella di una pagina.
         * <p/>
         * La pagina può contenere più di una tabella; quella selezionata viene indica nel parametro
         * <br> La tabella deve contenere almeno sei colonne significative che vengono recuperate;
         * eventuali altre colonne vengono ignorate <br> La tabella può contenere o meno una colonna
         * sinistra di numeri d'ordine che vengono ignorati <br>
         *
         * @param testoPagina testo della pagina dove sono presenti più tabelle <br>
         * @param posTabella  tabella selezionata <br>
         *
         * @return lista di sei stringhe, una per ognuna delle prime sei colonne significative della
         *         tabella selezionata
         */
        public static ArrayList<SeiStringhe> getPrettySei(String testoPagina, int posTabella) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibWiki.getPrettySei(testoPagina, posTabella);
        }


        /**
         * Recupera una lista di otto colonne dal testo di una pagina o di una tabella.
         * <p/>
         * <p/>
         * La tabella deve contenere almeno otto colonne significative che vengono recuperate;
         * eventuali altre colonne vengono ignorate <br> La tabella può contenere o meno una colonna
         * sinistra di numeri d'ordine che vengono ignorati <br>
         *
         * @param testo della pagina o della tabella  <br>
         *
         * @return lista di otto stringhe, una per ognuna delle prime otto colonne significative
         *         della tabella
         */
        public static ArrayList<OttoStringhe> getPrettyOtto(String testo) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibWiki.getPrettyOtto(testo);
        }


        /**
         * Recupera una lista di otto colonne da una tabella di una pagina.
         * <p/>
         * La pagina può contenere più di una tabella; quella selezionata viene indica nel parametro
         * <br> La tabella deve contenere almeno otto colonne significative che vengono recuperate;
         * eventuali altre colonne vengono ignorate <br> La tabella può contenere o meno una colonna
         * sinistra di numeri d'ordine che vengono ignorati <br>
         *
         * @param testoPagina testo della pagina dove sono presenti più tabelle <br>
         * @param posTabella  tabella selezionata <br>
         *
         * @return lista di otto stringhe, una per ognuna delle prime otto colonne significative
         *         della tabella selezionata
         */
        public static ArrayList<OttoStringhe> getPrettyOtto(String testoPagina, int posTabella) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibWiki.getPrettyOtto(testoPagina, posTabella);
        }


        /**
         * Recupera una lista di dieci colonne dal testo di una pagina o di una tabella.
         * <p/>
         * <p/>
         * La tabella deve contenere almeno dieci colonne significative che vengono recuperate;
         * eventuali altre colonne vengono ignorate <br> La tabella può contenere o meno una colonna
         * sinistra di numeri d'ordine che vengono ignorati <br>
         *
         * @param testo della pagina o della tabella  <br>
         *
         * @return lista di dieci stringhe, una per ognuna delle prime dieci colonne significative
         *         della tabella
         */
        public static ArrayList<DieciStringhe> getPrettyDieci(String testo) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibWiki.getPrettyDieci(testo);
        }


        /**
         * Recupera una lista di dieci colonne da una tabella di una pagina.
         * <p/>
         * La pagina può contenere più di una tabella; quella selezionata viene indica nel parametro
         * <br> La tabella deve contenere almeno dieci colonne significative che vengono recuperate;
         * eventuali altre colonne vengono ignorate <br> La tabella può contenere o meno una colonna
         * sinistra di numeri d'ordine che vengono ignorati <br>
         *
         * @param testoPagina testo della pagina dove sono presenti più tabelle <br>
         * @param posTabella  tabella selezionata <br>
         *
         * @return lista di dieci stringhe, una per ognuna delle prime dieci colonne significative
         *         della tabella selezionata
         */
        public static ArrayList<DieciStringhe> getPrettyDieci(String testoPagina, int posTabella) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibWiki.getPrettyDieci(testoPagina, posTabella);
        }


        /**
         * Costruisce una tabella di una colonna.
         * <p/>
         * Riceve una lista di stringhe <br> Può costruire o meno una colonna iniziale di numeri
         * progressivi <br>
         *
         * @param lista           di stringhe <br>
         * @param titoli          delle colonne, separati da virgola <br>
         * @param flagOrdinamento per costruire o meno una colonna iniziale di numeri progressivi
         *                        <br>
         *
         * @return testo della tabella costruita
         */
        public static String setPrettyUno(ArrayList<String> lista,
                                          String titoli,
                                          boolean flagOrdinamento) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibWiki.setPrettyUno(lista, titoli, flagOrdinamento);
        }


        /**
         * Costruisce una tabella di una colonna, con una colonna iniziale di numeri progressivi.
         * <p/>
         * Riceve una lista di stringhe <br>
         *
         * @param lista  di stringhe <br>
         * @param titoli delle colonne, separati da virgola <br>
         *
         * @return testo della tabella costruita
         */
        public static String setPrettyUno(ArrayList<String> lista, String titoli) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibWiki.setPrettyUno(lista, titoli);
        }


        /**
         * Costruisce una tabella di due colonne.
         * <p/>
         * Riceve una lista di due stringhe <br> Può costruire o meno una colonna iniziale di numeri
         * progressivi <br>
         *
         * @param lista           di due stringhe <br>
         * @param titoli          delle colonne, separati da virgola <br>
         * @param flagOrdinamento per costruire o meno una colonna iniziale di numeri progressivi
         *                        <br>
         *
         * @return testo della tabella costruita
         */
        public static String setPrettyDue(ArrayList<DueStringhe> lista,
                                          String titoli,
                                          boolean flagOrdinamento) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibWiki.setPrettyDue(lista, titoli, flagOrdinamento);
        }


        /**
         * Costruisce una tabella di due colonne, con una colonna iniziale di numeri progressivi.
         * <p/>
         * Riceve una lista di due stringhe <br>
         *
         * @param lista  di due stringhe <br>
         * @param titoli delle colonne, separati da virgola <br>
         *
         * @return testo della tabella costruita
         */
        public static String setPrettyDue(ArrayList<DueStringhe> lista, String titoli) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibWiki.setPrettyDue(lista, titoli);
        }


        /**
         * Costruisce una tabella di tre colonne.
         * <p/>
         * Riceve una lista di tre stringhe <br> Può costruire o meno una colonna iniziale di numeri
         * progressivi <br>
         *
         * @param lista           di tre stringhe <br>
         * @param titoli          delle colonne, separati da virgola <br>
         * @param flagOrdinamento per costruire o meno una colonna iniziale di numeri progressivi
         *                        <br>
         *
         * @return testo della tabella costruita
         */
        public static String setPrettyTre(ArrayList<TreStringhe> lista,
                                          String titoli,
                                          boolean flagOrdinamento) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibWiki.setPrettyTre(lista, titoli, flagOrdinamento);
        }


        /**
         * Costruisce una tabella di tre colonne, con una colonna iniziale di numeri progressivi.
         * <p/>
         * Riceve una lista di tre stringhe <br>
         *
         * @param lista  di tre stringhe <br>
         * @param titoli delle colonne, separati da virgola <br>
         *
         * @return testo della tabella costruita
         */
        public static String setPrettyTre(ArrayList<TreStringhe> lista, String titoli) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibWiki.setPrettyTre(lista, titoli);
        }


        /**
         * Costruisce una tabella di quattro colonne.
         * <p/>
         * Riceve una lista di quattro stringhe <br> Può costruire o meno una colonna iniziale di
         * numeri progressivi <br>
         *
         * @param lista           di quattro stringhe <br>
         * @param titoli          delle colonne, separati da virgola <br>
         * @param flagOrdinamento per costruire o meno una colonna iniziale di numeri progressivi
         *                        <br>
         *
         * @return testo della tabella costruita
         */
        public static String setPrettyQuattro(ArrayList<QuattroStringhe> lista,
                                              String titoli,
                                              boolean flagOrdinamento) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibWiki.setPrettyQuattro(lista, titoli, flagOrdinamento);
        }


        /**
         * Costruisce una tabella di quattro colonne, con una colonna iniziale di numeri
         * progressivi.
         * <p/>
         * Riceve una lista di quattro stringhe <br>
         *
         * @param lista  di quattro stringhe <br>
         * @param titoli delle colonne, separati da virgola <br>
         *
         * @return testo della tabella costruita
         */
        public static String setPrettyQuattro(ArrayList<QuattroStringhe> lista, String titoli) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibWiki.setPrettyQuattro(lista, titoli);
        }


        /**
         * Costruisce una tabella di cinque colonne.
         * <p/>
         * Riceve una lista di cinque stringhe <br> Può costruire o meno una colonna iniziale di
         * numeri progressivi <br>
         *
         * @param lista           di cinque stringhe <br>
         * @param titoli          delle colonne, separati da virgola <br>
         * @param flagOrdinamento per costruire o meno una colonna iniziale di numeri progressivi
         *                        <br>
         *
         * @return testo della tabella costruita
         */
        public static String setPrettyCinque(ArrayList<CinqueStringhe> lista,
                                             String titoli,
                                             boolean flagOrdinamento) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibWiki.setPrettyCinque(lista, titoli, flagOrdinamento);
        }


        /**
         * Costruisce una tabella di cinque colonne, con una colonna iniziale di numeri
         * progressivi.
         * <p/>
         * Riceve una lista di cinque stringhe <br>
         *
         * @param lista  di cinque stringhe <br>
         * @param titoli delle colonne, separati da virgola <br>
         *
         * @return testo della tabella costruita
         */
        public static String setPrettyCinque(ArrayList<CinqueStringhe> lista, String titoli) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibWiki.setPrettyCinque(lista, titoli);
        }


        /**
         * Costruisce una tabella di sei colonne.
         * <p/>
         * Riceve una lista di sei stringhe <br> Può costruire o meno una colonna iniziale di numeri
         * progressivi <br>
         *
         * @param lista           di sei stringhe <br>
         * @param titoli          delle colonne, separati da virgola <br>
         * @param flagOrdinamento per costruire o meno una colonna iniziale di numeri progressivi
         *                        <br>
         *
         * @return testo della tabella costruita
         */
        public static String setPrettySei(ArrayList<SeiStringhe> lista,
                                          String titoli,
                                          boolean flagOrdinamento) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibWiki.setPrettySei(lista, titoli, flagOrdinamento);
        }


        /**
         * Costruisce una tabella di sei colonne, con una colonna iniziale di numeri progressivi.
         * <p/>
         * Riceve una lista di sei stringhe <br>
         *
         * @param lista  di sei stringhe <br>
         * @param titoli delle colonne, separati da virgola <br>
         *
         * @return testo della tabella costruita
         */
        public static String setPrettySei(ArrayList<SeiStringhe> lista, String titoli) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibWiki.setPrettySei(lista, titoli);
        }


        /**
         * Costruisce una tabella di sette colonne.
         * <p/>
         * Riceve una lista di sette stringhe <br> Può costruire o meno una colonna iniziale di
         * numeri progressivi <br>
         *
         * @param lista           di sette stringhe <br>
         * @param titoli          delle colonne, separati da virgola <br>
         * @param flagOrdinamento per costruire o meno una colonna iniziale di numeri progressivi
         *                        <br>
         *
         * @return testo della tabella costruita
         */
        public static String setPrettySette(ArrayList<SetteStringhe> lista,
                                            String titoli,
                                            boolean flagOrdinamento) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibWiki.setPrettySette(lista, titoli, flagOrdinamento);
        }


        /**
         * Costruisce una tabella di sette colonne, con una colonna iniziale di numeri progressivi.
         * <p/>
         * Riceve una lista di sette stringhe <br>
         *
         * @param lista  di sette stringhe <br>
         * @param titoli delle colonne, separati da virgola <br>
         *
         * @return testo della tabella costruita
         */
        public static String setPrettySette(ArrayList<SetteStringhe> lista, String titoli) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibWiki.setPrettySette(lista, titoli);
        }


        /**
         * Costruisce una tabella di otto colonne.
         * <p/>
         * Riceve una lista di otto stringhe <br> Può costruire o meno una colonna iniziale di
         * numeri progressivi <br>
         *
         * @param lista           di otto stringhe <br>
         * @param titoli          delle colonne, separati da virgola <br>
         * @param flagOrdinamento per costruire o meno una colonna iniziale di numeri progressivi
         *                        <br>
         *
         * @return testo della tabella costruita
         */
        public static String setPrettyOtto(ArrayList<OttoStringhe> lista,
                                           String titoli,
                                           boolean flagOrdinamento) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibWiki.setPrettyOtto(lista, titoli, flagOrdinamento);
        }


        /**
         * Costruisce una tabella di otto colonne, con una colonna iniziale di numeri progressivi.
         * <p/>
         * Riceve una lista di otto stringhe <br>
         *
         * @param lista  di otto stringhe <br>
         * @param titoli delle colonne, separati da virgola <br>
         *
         * @return testo della tabella costruita
         */
        public static String setPrettyOtto(ArrayList<OttoStringhe> lista, String titoli) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibWiki.setPrettyOtto(lista, titoli);
        }


        /**
         * Costruisce una tabella di nove colonne.
         * <p/>
         * Riceve una lista di nove stringhe <br> Può costruire o meno una colonna iniziale di
         * numeri progressivi <br>
         *
         * @param lista           di nove stringhe <br>
         * @param titoli          delle colonne, separati da virgola <br>
         * @param flagOrdinamento per costruire o meno una colonna iniziale di numeri progressivi
         *                        <br>
         *
         * @return testo della tabella costruita
         */
        public static String setPrettyNove(ArrayList<NoveStringhe> lista,
                                           String titoli,
                                           boolean flagOrdinamento) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibWiki.setPrettyNove(lista, titoli, flagOrdinamento);
        }


        /**
         * Costruisce una tabella di nove colonne, con una colonna iniziale di numeri progressivi.
         * <p/>
         * Riceve una lista di nove stringhe <br>
         *
         * @param lista  di nove stringhe <br>
         * @param titoli delle colonne, separati da virgola <br>
         *
         * @return testo della tabella costruita
         */
        public static String setPrettyNove(ArrayList<NoveStringhe> lista, String titoli) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibWiki.setPrettyNove(lista, titoli);
        }


        /**
         * Costruisce una tabella di dieci colonne.
         * <p/>
         * Riceve una lista di dieci stringhe <br> Può costruire o meno una colonna iniziale di
         * numeri progressivi <br>
         *
         * @param lista           di dieci stringhe <br>
         * @param titoli          delle colonne, separati da virgola <br>
         * @param flagOrdinamento per costruire o meno una colonna iniziale di numeri progressivi
         *                        <br>
         *
         * @return testo della tabella costruita
         */
        public static String setPrettyDieci(ArrayList<DieciStringhe> lista,
                                            String titoli,
                                            boolean flagOrdinamento) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibWiki.setPrettyDieci(lista, titoli, flagOrdinamento);
        }


        /**
         * Costruisce una tabella di dieci colonne, con una colonna iniziale di numeri progressivi.
         * <p/>
         * Riceve una lista di dieci stringhe <br>
         *
         * @param lista  di dieci stringhe <br>
         * @param titoli delle colonne, separati da virgola <br>
         *
         * @return testo della tabella costruita
         */
        public static String setPrettyDieci(ArrayList<DieciStringhe> lista, String titoli) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibWiki.setPrettyDieci(lista, titoli);
        }


        /**
         * Ordina una lista di dieci stringhe.
         * <p/>
         * Ordinamento alfaabetico effettuato sulla prima stringa <br>
         *
         * @param listaIn da ordinare <br>
         *
         * @return lista ordinata
         */
        public static ArrayList<DieciStringhe> ordinaDieci(ArrayList<DieciStringhe> listaIn) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibWiki.ordinaDieci(listaIn);
        }

    } // fine della classe 'interna' wiki


    /**
     * Classe 'interna' per facilitare il reperimento.
     */
    public static class File {

        /**
         * Sovrascrive un file su disco locale.
         * <p/>
         * Se il file esiste, lo cancella <br>
         *
         * @param nome      del file
         * @param contenuto del file
         *
         * @return vero se il file è stato scritto
         */
        public static boolean sovrascrive(String nome, String contenuto) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibFile.sovrascrive(nome, contenuto);
        }


        /**
         * Scrive un file su disco locale.
         * <p/>
         * Se il file esiste, chiede conferma prima di sovrascriverlo <br>
         *
         * @param path      completo del file
         * @param contenuto del file
         *
         * @return vero se il file è stato scritto
         */
        public static boolean scrive(String path, String contenuto) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibFile.scrive(path, contenuto);
        }


        /**
         * Legge un file dal disco locale.
         * <p/>
         *
         * @param nomeFile nome completo del path del file
         *
         * @return testo del file
         */
        public static String legge(String nomeFile) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibFile.legge(nomeFile);
        }


        /**
         * Cancella un file dal disco locale.
         * <p/>
         *
         * @param nomeFile nome completo del path del file
         *
         * @return vero se il file è stato cancellato
         */
        public static boolean cancella(String nomeFile) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibFile.cancella(nomeFile);
        }


        /**
         * Cancella il contenuto di una directory.
         * <p/>
         *
         * @param nomeDir completo del path della cartella
         *
         * @return vero se il contenuto è stato cancellato
         */
        public static boolean cancellaContenutoDir(String nomeDir) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibFile.cancellaContenutoDir(nomeDir);
        }


        /**
         * Determina se esiste un dato file.
         * <p/>
         *
         * @param path il percorso assoluto del file da cercare
         *
         * @return true se esiste
         */
        public static boolean isEsisteFile(String path) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibFile.isEsisteFile(path);
        }


        /**
         * Determina se esiste una directory.
         * <p/>
         *
         * @param path la directory da cercare
         *
         * @return true se esiste
         */
        public static boolean isEsisteDir(String path) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibFile.isEsisteDir(path);
        }


        /**
         * Lista dei files contenuti in una cartella.
         * <p/>
         *
         * @param pathDir path completo della cartella
         *
         * @return lista files
         */
        public static ArrayList<java.io.File> getFiles(String pathDir) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibFile.getFiles(pathDir);
        }


        /**
         * Lista nomi dei files contenuti in una cartella.
         * <p/>
         *
         * @param pathDir path completo della cartella
         *
         * @return lista nomi files
         */
        public static ArrayList<String> getNomiFiles(String pathDir) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibFile.getNomiFiles(pathDir);
        }


        /**
         * Ultima data di modifica del file.
         * <p/>
         *
         * @param path il percorso assoluto del file
         *
         * @return data ultima modifica
         */
        public static Date getData(String path) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibFile.getData(path);
        }


        /**
         * Ritorna l'estensione di un nome di file.
         * <p/>
         * (i caratteri dopo l'ultimo punto, punto escluso)
         *
         * @param path il percorso completo del file
         *
         * @return l'estensione del file
         */
        public static String getEstensione(String path) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibFile.getEstensione(path);
        }


        /**
         * Rimuove l'estensione da un nome di file.
         * <p/>
         * (i caratteri dopo l'ultimo punto, punto incluso)
         *
         * @param path il percorso completo del file
         *
         * @return il nome del file con l'estensione rimossa
         */
        public static String stripEstensione(String path) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibFile.stripEstensione(path);
        }


    } // fine della classe 'interna'


    /**
     * Classe 'interna' per facilitare il reperimento.
     */
    public static class Risorse {


        /**
         * Recupera una icona dalle icone di base.
         * <p/>
         * Se arriva un path, usa quello <br> Se arriva un nome, aggiunge il path di base <br>
         *
         * @param nomeIcona nome o path dell'icona da recuperare
         *
         * @return l'icona
         */
        public static ImageIcon getIconaBase(String nomeIcona) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibRisorse.getIconaBase(nomeIcona);
        }


        /**
         * Recupera una icona dato il suo URL.
         * <p/>
         *
         * @param url url dell'icona da recuperare
         *
         * @return l'icona, null se non trovata
         */
        public static ImageIcon getIcona(URL url) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibRisorse.getIcona(url);
        }


        /**
         * Recupera una icona del progetto specifico.
         * <p/>
         * si suppone che esista una cartella icone e che sia allo stesso livello del modulo
         * chiamante <br>
         *
         * @param modulo    di riferimento per il path
         * @param nomeIcona nome dell'icona da recuperare
         *
         * @return l'icona
         */
        public static ImageIcon getIcona(Modulo modulo, String nomeIcona) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibRisorse.getIcona(modulo, nomeIcona);
        }


        /**
         * Recupera una icona relativamente alla posizione di una data classe.
         * <p/>
         * si suppone che esista una cartella icone e che sia allo stesso livello della classe data
         * <p/>
         *
         * @param classe    di riferimento
         * @param nomeIcona nome dell'icona da recuperare (senza estensione)
         *
         * @return l'icona
         */
        public static ImageIcon getIcona(Class classe, String nomeIcona) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibRisorse.getIcona(classe, nomeIcona);
        }


        /**
         * Crea un mini-bottone con l'icona.
         * <p/>
         *
         * @param nomeIcona da caricare
         *
         * @return bottone creato
         */
        public static JButton getBottoneSmall(String nomeIcona) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibRisorse.getBottoneSmall(nomeIcona);
        }


        /**
         * Crea un bottone con l'icona.
         * <p/>
         *
         * @param nomeIcona da caricare
         *
         * @return bottone creato
         */
        public static JButton getBottone(String nomeIcona) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibRisorse.getBottone(nomeIcona);
        }


        /**
         * Crea un'azione specifica e la aggiunge alla toolbar o al menu Archivio.
         * <p/>
         * L'azione viene aggiunta al navigatore corrente <br> L'azione viene aggiunta alla toolbar
         * della lista o al menu aArchivio <br> Metodo invocato dal ciclo inizializza <br>
         * <p/>
         * Crea un'istanza di BottoneAzione <br> Recupera l'icona <br> Recupera la toolbar del
         * portale Lista del navigatore corrente oppure il menu Archivio <br> Aggiunge bottone e
         * icona alla toolbar oppure al menu Archivio <br>
         *
         * @param mod     modulo
         * @param path    completo dell'immagine da usare
         * @param metodo  nome del metodo nella classe modulo specifica
         * @param help    string di help (tooltiptext)
         * @param toolBar flag per aggiungere alla toolbar
         */
        public static void addAzione(Modulo mod,
                                     String path,
                                     String metodo,
                                     String help,
                                     boolean toolBar) {
            /* invoca il metodo sovrascritto della libreria specifica */
            LibRisorse.addAzione(mod, path, metodo, help, toolBar);
        }


        /**
         * Aggiunge un'azione al navigatore specificato.
         * <p/>
         * L'azione può essere aggiunta: <ul> <li> Alla toolbar della lista (in coda) </li> <li>
         * Alla toolbar della scheda (in coda) </li> <li> Ad un menu (posizione specificata
         * nell'azione stessa) </li> <ul/> Metodo invocato dal ciclo inizializza <br>
         *
         * @param azione completa di icona, titolo ed help
         * @param nav    navigatore specifico
         * @param tool   switch di selezione lista/scheda/menu
         */
        public static void addAzione(Azione azione, Navigatore nav, Navigatore.Tool tool) {
            /* invoca il metodo sovrascritto della libreria specifica */
            LibRisorse.addAzione(azione, nav, tool);
        }


        /**
         * Aggiunge un'azione al navigatore specificato.
         * <p/>
         * L'azione può essere aggiunta: <ul>
         * <li> Alla toolbar della lista (in coda) </li>
         * <li> Alla toolbar della scheda (in coda) </li>
         * <li> Ad un menu (posizione specificata nell'azione stessa) </li>
         * <ul/>
         * Metodo invocato dal ciclo inizializza <br>
         *
         * @param azione completa di icona, titolo ed help
         * @param nav    navigatore specifico
         */
        public static void addAzione(Azione azione, Navigatore nav) {
            /* invoca il metodo sovrascritto della libreria specifica */
            LibRisorse.addAzione(azione, nav);
        }


        /**
         * Aggiunge un tasto specifico di un'azione al bottone.
         * <p/>
         *
         * @param bottone da regolare col tasto
         * @param key     tasto da associare al bottone
         * @param azione  java completa di icona, titolo ed help da associare
         */
        public static void addTasto(JButton bottone, int key, Action azione) {
            /* invoca il metodo sovrascritto della libreria specifica */
            LibRisorse.addTasto(bottone, key, azione);
        }


        /**
         * Aggiunge un tasto specifico di un'azione al bottone.
         * <p/>
         *
         * @param bottone      da regolare col tasto
         * @param key          tasto da associare al bottone
         * @param modificatore da classe InputEvent
         * @param azione       java completa di icona, titolo ed help da associare
         */
        public static void addTasto(JButton bottone, int key, int modificatore, Action azione) {
            /* invoca il metodo sovrascritto della libreria specifica */
            LibRisorse.addTasto(bottone, key, modificatore, azione);
        }

    } // fine della classe 'interna'


    /**
     * Classe 'interna' per facilitare il reperimento. </p>
     */
    public static class Fonte {


        /**
         * Restituisce la larghezza in pixel di una stringa. </p>
         *
         * @param testo testo di cui calcolare la larghezza
         * @param font  tipo di font utilizzato
         *
         * @return larghezza valore in pixel
         */
        public static int getLarPixel(String testo, Font font) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibFont.getLarPixel(testo, font);
        }


        /**
         * Restituisce la larghezza in pixel di una label.
         *
         * @param label col testo da calcolare
         *
         * @return valore in pixel
         */
        public static int getLarPixel(JLabel label) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibFont.getLarPixel(label);
        }


        /**
         * Restituisce la larghezza in pixel di un radio bottone.
         *
         * @param radio col testo da calcolare
         *
         * @return valore in pixel
         */
        public static int getLarPixel(JRadioButton radio) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibFont.getLarPixel(radio);
        }


        /**
         * Restituisce l'altezza totale standard di un font misurata come ascent + descent +
         * leading
         *
         * @param font tipo di font utilizzato
         *
         * @return valore in pixel
         */
        public static int altezzaTotaleStandard(Font font) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibFont.altezzaTotaleStandard(font);
        }


        /**
         * Restituisce l'altezza standard di un font misurata come ascent + descent
         *
         * @param font tipo di font utilizzato
         *
         * @return valore in pixel
         */
        public static int altezzaADStandard(Font font) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibFont.altezzaADStandard(font);
        }


        /**
         * Restituisce l'ascendente standard di un font misurata come ascent + descent (senza
         * leading)
         *
         * @param font tipo di font utilizzato
         *
         * @return valore in pixel
         */
        public static int ascendenteStandard(Font font) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibFont.ascendenteStandard(font);
        }


        /**
         * Restituisce la discendente standard di un font.
         *
         * @param font tipo di font utilizzato
         *
         * @return valore in pixel
         */
        public static int discendenteStandard(Font font) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibFont.discendenteStandard(font);
        }


        /**
         * Restituisce il bounding box di una stringa in un dato contesto grafico
         *
         * @param testo           testo da calcolare
         * @param font            il Font del testo
         * @param contestoGrafico il contesto grafico in cui si disegna il testo
         *
         * @return il rettangolo nel quale il testo è inscritto
         */
        public static Rectangle2D boundingBoxStringa(String testo,
                                                     Font font,
                                                     Graphics contestoGrafico) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibFont.boundingBoxStringa(testo, font, contestoGrafico);
        }


        /**
         * Ritorna l'altezza di un font
         * <p/>
         *
         * @param unFont il font
         *
         * @return l'altezza del font
         */
        public static int getAltezzaFont(Font unFont) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibFont.getAltezzaFont(unFont);
        }


        /**
         * Ritorna la massima altezza di un font
         * <p/>
         *
         * @param unFont il font
         *
         * @return l'altezza massima del font
         */
        public static int getMaxAltezzaFont(Font unFont) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibFont.getMaxAltezzaFont(unFont);
        }


        /**
         * Ritorna l'altezza di un componente.
         *
         * @param unComponente componente grafico
         *
         * @return altezza in pixel del componente
         */
        public static int getAltezza(Component unComponente) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibFont.getAltezza(unComponente);
        }


        /**
         * Splitta una stringa su più righe considerando la dimensione
         * del font, l'ambiente grafico e una larghzza max in pixel
         * @param in la stringa da splittare
         * @param graphics il contesto grafico
         * @param font il font
         * @param wmax la larghezza massima sula quale splittare
         * @return l'array delle parti splittate
         */
        public static String[] splitString(String in, Graphics graphics, Font font, int wmax){
        	return LibFont.splitString(in, graphics, font, wmax);
        }


    } // fine della classe 'interna'


    /**
     * Classe 'interna' per facilitare il reperimento. </p>
     */
    public static class Eventi {

        /**
         * Aggiunge un listener alla lista.
         * <p/>
         * Controlla che il listener appartenga/non appartenga all'enumerazione <br>
         *
         * @param valori   enumeration interna alla classe
         * @param lista    dei listener degli eventi del componente
         * @param listener interfaccia
         */
        public static void add(Object[] valori, EventListenerList lista, BaseListener listener) {
            /* invoca il metodo sovrascritto della libreria specifica */
            LibEventi.add(valori, lista, listener);
        }


        /**
         * Rimuove un listener dalla lista.
         * <p/>
         * Controlla che il listener appartenga/non appartenga all'enumerazione <br>
         *
         * @param valori   enumeration interna alla classe
         * @param lista    dei listener degli eventi del componente
         * @param listener interfaccia
         */
        public static void remove(Object[] valori, EventListenerList lista, BaseListener listener) {
            /* invoca il metodo sovrascritto della libreria specifica */
            LibEventi.remove(valori, lista, listener);
        }


        /**
         * Avvisa tutti i listener.
         * <p/>
         * Avvisa tutti i listener che si sono registrati per questo tipo di evento <br> L'evento
         * viene creato al momento <br> È responsabilità della classe invocare questo metodo quando
         * si creano le condizioni per generare l'evento <br>
         *
         * @param lista  dei listener degli eventi del componente
         * @param evento da lanciare
         * @param param  lista variabile di argomenti (classe, valore)
         *
         * @see javax.swing.event.EventListenerList
         */
        public static void fire(EventListenerList lista,
                                it.algos.base.evento.Eventi evento,
                                Parametro... param) {
            /* invoca il metodo sovrascritto della libreria specifica */
            LibEventi.fire(lista, evento, param);
        }


        /**
         * Avvisa tutti i listener.
         * <p/>
         * Avvisa tutti i listener che si sono registrati per questo tipo di evento <br> L'evento
         * viene creato al momento <br> È responsabilità della classe invocare questo metodo quando
         * si creano le condizioni per generare l'evento <br>
         *
         * @param lista  dei listener degli eventi del componente
         * @param evento da lanciare
         * @param classe che richiede il lancio dell'evento
         * @param valore istanza della classe che richiede il lancio dell'evento
         *
         * @see javax.swing.event.EventListenerList
         */
        public static void fire(EventListenerList lista,
                                it.algos.base.evento.Eventi evento,
                                Class classe,
                                Object valore) {
            /* invoca il metodo sovrascritto della libreria specifica */
            LibEventi.fire(lista, evento, new Parametro(classe, valore));
        }


    } // fine della classe 'interna'


    /**
     * Classe 'interna' per facilitare il reperimento. </p>
     */
    public static class Comp {

        /**
         * Blocca la larghezza massima del componente.
         * <p/>
         * Pone la larghezza massima pari a quella preferita <br>
         *
         * @param comp componente su cui operare
         */
        public static void bloccaLarMax(JComponent comp) {
            /* invoca il metodo sovrascritto della libreria specifica */
            LibComponente.bloccaLarMax(comp);
        }


        /**
         * Blocca la larghezza minima del componente.
         * <p/>
         * Pone la larghezza minima pari a quella preferita <br>
         *
         * @param comp componente su cui operare
         */
        public static void bloccaLarMin(JComponent comp) {
            /* invoca il metodo sovrascritto della libreria specifica */
            LibComponente.bloccaLarMin(comp);
        }


        /**
         * Blocca la larghezza del componente.
         * <p/>
         * Pone la larghezza minima e massima pari a quella preferita <br>
         *
         * @param comp componente su cui operare
         */
        public static void bloccaLarghezza(JComponent comp) {
            /* invoca il metodo sovrascritto della libreria specifica */
            LibComponente.bloccaLarghezza(comp);
        }


        /**
         * Blocca l'altezza massima del componente.
         * <p/>
         * Pone l'altezza massima pari a quella preferita <br>
         *
         * @param comp componente su cui operare
         */
        public static void bloccaAltMax(JComponent comp) {
            /* invoca il metodo sovrascritto della libreria specifica */
            LibComponente.bloccaAltMax(comp);
        }


        /**
         * Blocca l'altezza minima del componente.
         * <p/>
         * Pone l'altezza minima pari a quella preferita <br>
         *
         * @param comp componente su cui operare
         */
        public static void bloccaAltMin(JComponent comp) {
            /* invoca il metodo sovrascritto della libreria specifica */
            LibComponente.bloccaAltMin(comp);
        }


        /**
         * Blocca l'altezza del componente.
         * <p/>
         * Pone l'altezza minima e massima pari a quella preferita <br>
         *
         * @param comp componente su cui operare
         */
        public static void bloccaAltezza(JComponent comp) {
            /* invoca il metodo sovrascritto della libreria specifica */
            LibComponente.bloccaAltezza(comp);
        }


        /**
         * Blocca la dimensione massima del componente.
         * <p/>
         * Pone la dimensione massima pari alla preferredSize <br>
         *
         * @param comp componente su cui operare
         */
        public static void bloccaDimMax(JComponent comp) {
            /* invoca il metodo sovrascritto della libreria specifica */
            LibComponente.bloccaDimMax(comp);
        }


        /**
         * Blocca la dimensione minima del componente.
         * <p/>
         * Pone la dimensione minima pari alla preferredSize <br>
         *
         * @param comp componente su cui operare
         */
        public static void bloccaDimMin(JComponent comp) {
            /* invoca il metodo sovrascritto della libreria specifica */
            LibComponente.bloccaDimMin(comp);
        }


        /**
         * Blocca la dimensione del componente.
         * <p/>
         * Pone le dimensioni minima e massima pari alla preferredSize <br>
         *
         * @param comp componente su cui operare
         */
        public static void bloccaDim(JComponent comp) {
            /* invoca il metodo sovrascritto della libreria specifica */
            LibComponente.bloccaDim(comp);
        }


        /**
         * Sblocca la larghezza massima del componente.
         * <p/>
         * Pone la larghezza massima pari a infinito <br>
         *
         * @param comp componente su cui operare
         */
        public static void sbloccaLarMax(JComponent comp) {
            /* invoca il metodo sovrascritto della libreria specifica */
            LibComponente.sbloccaLarMax(comp);
        }


        /**
         * Sblocca la larghezza minima del componente.
         * <p/>
         * Pone la larghezza minima pari a zero <br>
         *
         * @param comp componente su cui operare
         */
        public static void sbloccaLarMin(JComponent comp) {
            /* invoca il metodo sovrascritto della libreria specifica */
            LibComponente.sbloccaLarMin(comp);
        }


        /**
         * Sblocca la larghezza del componente.
         * <p/>
         * Pone la larghezza minima a zero e massima a infinito <br>
         *
         * @param comp componente su cui operare
         */
        public static void sbloccaLarghezza(JComponent comp) {
            /* invoca il metodo sovrascritto della libreria specifica */
            LibComponente.sbloccaLarghezza(comp);
        }


        /**
         * Sblocca l'altezza massima del componente.
         * <p/>
         * Pone la altezza massima pari a infinito <br>
         *
         * @param comp componente su cui operare
         */
        public static void sbloccaAltMax(JComponent comp) {
            /* invoca il metodo sovrascritto della libreria specifica */
            LibComponente.sbloccaAltMax(comp);
        }


        /**
         * Sblocca l'altezza minima del componente.
         * <p/>
         * Pone l'altezza minima pari a zero <br>
         *
         * @param comp componente su cui operare
         */
        public static void sbloccaAltMin(JComponent comp) {
            /* invoca il metodo sovrascritto della libreria specifica */
            LibComponente.sbloccaAltMin(comp);
        }


        /**
         * Sblocca l'altezza del componente.
         * <p/>
         * Pone l'altezza minima a zero e massima a infinito <br>
         *
         * @param comp componente su cui operare
         */
        public static void sbloccaAltezza(JComponent comp) {
            /* invoca il metodo sovrascritto della libreria specifica */
            LibComponente.sbloccaAltezza(comp);
        }


        /**
         * Sblocca dimensione massima in larghezza e in altezza del componente.
         * <p/>
         * Pone la larghezza massima a infinito e l'altezza massima a infinito <br>
         *
         * @param comp componente su cui operare
         */
        public static void sbloccaDimMax(JComponent comp) {
            /* invoca il metodo sovrascritto della libreria specifica */
            LibComponente.sbloccaDimMax(comp);
        }


        /**
         * Sblocca dimensione minima in larghezza e in altezza del componente.
         * <p/>
         * Pone la larghezza minima a zero e l'altezza minima a zero <br>
         *
         * @param comp componente su cui operare
         */
        public static void sbloccaDimMin(JComponent comp) {
            /* invoca il metodo sovrascritto della libreria specifica */
            LibComponente.sbloccaDimMin(comp);
        }


        /**
         * Sblocca dimensione massima in larghezza e in altezza del componente.
         * <p/>
         * Pone la dimensione minima a zero e la massima a infinito <br>
         *
         * @param comp componente su cui operare
         */
        public static void sbloccaDim(JComponent comp) {
            /* invoca il metodo sovrascritto della libreria specifica */
            LibComponente.sbloccaDim(comp);
        }


        /**
         * Regola la larghezza preferita del componente.
         * <p/>
         * Mantiene l'altezza preferita corrente.<br> (se non era specificata, la pone pari a
         * zero)<br>
         *
         * @param comp componente su cui operare
         * @param w    valore da assegnare alla larghezza preferita
         */
        public static void setPreferredWidth(JComponent comp, int w) {
            /* invoca il metodo sovrascritto della libreria specifica */
            LibComponente.setPreferredWidth(comp, w);
        }


        /**
         * Regola l'altezza preferita del componente.
         * <p/>
         * Mantiene la larghezza preferita corrente.<br> (se non era specificata, la pone pari a
         * zero)<br>
         *
         * @param comp componente su cui operare
         * @param h    valore da assegnare all'altezza preferita
         */
        public static void setPreferredHeigth(JComponent comp, int h) {
            /* invoca il metodo sovrascritto della libreria specifica */
            LibComponente.setPreferredHeigth(comp, h);
        }


        /**
         * Regola la larghezza minima del componente.
         * <p/>
         * Mantiene l'altezza minima corrente.<br> (se non era specificata, la pone pari a
         * zero)<br>
         *
         * @param comp componente su cui operare
         * @param w    valore da assegnare alla larghezza minima
         */
        public static void setMinimumWidth(JComponent comp, int w) {
            /* invoca il metodo sovrascritto della libreria specifica */
            LibComponente.setMinimumWidth(comp, w);
        }


        /**
         * Regola l'altezza mimina del componente.
         * <p/>
         * Mantiene la larghezza mimina corrente.<br> (se non era specificata, la pone pari a
         * zero)<br>
         *
         * @param comp componente su cui operare
         * @param h    valore da assegnare all'altezza mimina
         */
        public static void setMinimumHeigth(JComponent comp, int h) {
            /* invoca il metodo sovrascritto della libreria specifica */
            LibComponente.setMinimumHeigth(comp, h);
        }


        /**
         * Regola la larghezza massima del componente.
         * <p/>
         * Mantiene l'altezza massima corrente.<br> (se non era specificata, la pone pari a
         * infinito)<br>
         *
         * @param comp componente su cui operare
         * @param w    valore da assegnare alla larghezza massima
         */
        public static void setMaximumWidth(JComponent comp, int w) {
            /* invoca il metodo sovrascritto della libreria specifica */
            LibComponente.setMaximumWidth(comp, w);
        }


        /**
         * Regola l'altezza massima del componente.
         * <p/>
         * Mantiene la larghezza massima corrente.<br> (se non era specificata, la pone pari a
         * infinito)<br>
         *
         * @param comp componente su cui operare
         * @param h    valore da assegnare all'altezza massima
         */
        public static void setMaximumHeigth(JComponent comp, int h) {
            /* invoca il metodo sovrascritto della libreria specifica */
            LibComponente.setMaximumHeigth(comp, h);
        }


        /**
         * Estrae i campi da una gerarchia di contenimento.
         * <p/>
         * Analizza ricorsivamente la gerarchia dei contenuti ed estrae i soli oggetti Campo <br>
         * L'ordine e' quello della gerarchia di contenimento <br>
         *
         * @param contenitore dal quale estrarre i campi
         *
         * @return la lista dei campi presenti nella gerarchia di contenimento del contenitore
         *         dato.
         */
        public static ArrayList<Campo> estraeCampi(Container contenitore) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibComponente.estraeCampi(contenitore);
        }


        /**
         * Risale una gerarchia di contenimento fino all'oggetto della classe richiesta.
         * <p/>
         * Se non trova il contenitore della classe richiesta ritorna null <br>
         *
         * @param comp   il componente del quale risalire la gerarchia
         * @param classe la classe dell'oggetto da cercare
         *
         * @return il contenitore della classe richiesta contenente il componente
         */
        public static Component risali(Component comp, Class classe) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibComponente.risali(comp, classe);
        }


        /**
         * Regola l'altezza di una JTextArea alla misura ottimale per mostrare tutte le righe.
         * <p/>
         * Regola l'altezza di una JTextArea con word wrap attivo.<br> Usa la larghezza esistente
         * (preferredSize) <br> Regola l'altezza in funzione del contenuto.
         *
         * @param area della quale regolare l'altezza Viene regolata l'altezza preferita.
         */
        public static void setAreaOptimalHeight(JTextArea area) {
            /* invoca il metodo sovrascritto della libreria specifica */
            LibComponente.setAreaOptimalHeight(area);
        }


        /**
         * Crea un filler elastico orizzontale.
         * <p/>
         * E' un componente che si espande orizzontalmente tra gli estremi minimo e massimo. <br>
         *
         * @param min  larghezza minima
         * @param pref larghezza preferita
         * @param max  larghezza massima
         *
         * @return il filler creato
         */
        public static Component createHorizontalFiller(int min, int pref, int max) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibComponente.createHorizontalFiller(min, pref, max);
        }


        /**
         * Crea un filler elastico orizzontale.
         * <p/>
         * E' un componente che si espande orizzontalmente da zero fino al massimo specificato.
         * <br>
         *
         * @param max larghezza massima
         *
         * @return il filler creato
         */
        public static Component createHorizontalFiller(int max) {
            return LibComponente.createHorizontalFiller(0, 0, max);
        }


        /**
         * Crea un filler elastico verticale.
         * <p/>
         * E' un componente che si espande verticalmente tra gli estremi minimo e massimo. <br>
         *
         * @param min  altezza minima
         * @param pref altezza preferita
         * @param max  altezza massima
         *
         * @return il filler creato
         */
        public static Component createVerticalFiller(int min, int pref, int max) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibComponente.createVerticalFiller(min, pref, max);
        }


        /**
         * Crea un filler elastico verticale.
         * <p/>
         * E' un componente che si espande verticalmente da zero fino al massimo specificato. <br>
         *
         * @param max altezza massima
         *
         * @return il filler creato
         */
        public static Component createVerticalFiller(int max) {
            return LibComponente.createVerticalFiller(0, 0, max);
        }


    } // fine della classe 'interna'


    /**
     * Classe 'interna' per facilitare il reperimento. </p>
     */
    public static class Mod {

        /**
         * Restituisce il campo valore richiesto.
         * <p/>
         *
         * @param lista array coppia campo-valore
         * @param campo da ricercare
         *
         * @return campo-valore richiesto
         */
        public static CampoValore getCampoValore(ArrayList<CampoValore> lista, Campo campo) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibModello.getCampoValore(lista, campo);
        }


        /**
         * Cancella dal db gli eventuali campi non contenuti nel Modello.
         * <p/>
         *
         * @param modello il modello di riferimento
         *
         * @return true se ha eliminato correttamente i campi superflui o se non ci sono campi
         *         superflui da eliminare
         */
        public static boolean eliminaCampiSuperflui(Modello modello) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibModello.eliminaCampiSuperflui(modello);
        }


        /**
         * Restituisce i nomi per il db dei campi fisici del modello
         * <p/>
         *
         * @param modello il modello di riferimento
         *
         * @return la lista dei nomi
         */
        public static ArrayList getNomiDbCampiFisiciModello(Modello modello) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibModello.getNomiDbCampiFisiciModello(modello);
        }


        /**
         * Registra i dati di default.
         * <p/>
         * Registra sul database dei dati di default i dati contenuti nella tavola di un modello,
         * prendendoli dal database in uso al Modulo <br> I dati di default vengono completamente
         * sostituiti <br>
         *
         * @param modello il modello di riferimento
         *
         * @return true se eseguito correttamente
         */
        public static boolean registraDatiDefault(Modello modello) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibModello.registraDatiDefault(modello);
        }


        /**
         * Carica i dati di default per questo Modulo/Modello.
         * <p/>
         *
         * @param modello il modello di riferimento
         */
        public static void caricaDatiDefault(Modello modello) {
            /* invoca il metodo sovrascritto della libreria specifica */
            LibModello.caricaDatiDefault(modello);
        }


        /**
         * Controlla se esistono dei dati di default per un Modello.
         * <p/>
         * Cerca nel database di default la tavola del modello e controlla che ci siano dei dati
         * <br>
         *
         * @param modello il modello di riferimento
         *
         * @return true se esistono dei dati di default
         */
        public static boolean isEsistonoDatiStandard(Modello modello) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibModello.isEsistonoDatiStandard(modello);
        }


    } // fine della classe 'interna'


    /**
     * Classe 'interna' per facilitare il reperimento. </p>
     */
    public static class Sist {

        /**
         * Restituisce il percorso assoluto della User Home Directory dell'utente del sistema.
         * <p/>
         * Il percorso restituito non comprende il separatore finale <br>
         *
         * @return il percorso della User Home
         */
        public static String getUserHome() {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibSistema.getUserHome();
        }


        /**
         * Restituisce il percorso assoluto della Directory Algos.
         * <p/>
         * Il percorso restituito non comprende il separatore finale <br>
         *
         * @return il percorso della Directory Algos
         */
        public static String getDirAlgos() {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibSistema.getDirAlgos();
        }


        /**
         * Restituisce il percorso assoluto della cartella moduli.
         * <p/>
         * Il percorso restituito non comprende il separatore finale <br>
         *
         * @return il percorso della cartella moduli
         */
        public static String getDirModuli() {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibSistema.getDirModuli();
        }


        /**
         * Restituisce il percorso assoluto della Working Directory.
         * <p/>
         * La directory e' quella dalla quale è stato eseguito Java <br> Il percorso restituito non
         * comprende il separatore finale <br>
         *
         * @return il percorso della User Directory
         */
        public static String getUserDir() {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibSistema.getUserDir();
        }


        /**
         * Restituisce il percorso assoluto della home directory del programma.
         * <p/>
         * La home del programma e' la directory dove il programma mantiene tutti i propri dati
         * (preferenze, dati, ecc...) <br> Il percorso restituito non comprende il separatore finale
         * <br>
         *
         * @return il percorso della home directory del programma
         */
        public static String getHomeProgramma() {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibSistema.getHomeProgramma();
        }


        /**
         * Restituisce il percorso assoluto della directory Preferenze del programma.
         * <p/>
         * La cartella preferenze contiene i file delle preferenze di base e specifiche dell'utente
         * <br> Il percorso restituito non comprende il separatore finale <br>
         *
         * @return il percorso della directory Preferenze
         */
        public static String getDirPreferenze() {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibSistema.getDirPreferenze();
        }


        /**
         * Restituisce il percorso assoluto della directory Dati del programma.
         * <p/>
         * La directory dati e' la cartella dove il programma immagazzina i dati quando usa un
         * database stand-alone <br> Il percorso restituito non comprende il separatore finale <br>
         *
         * @return il percorso della directory Preferenze
         */
        public static String getDirDati() {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibSistema.getDirDati();
        }


        /**
         * Restituisce il percorso assoluto della directory Dati Standard del programma.
         * <p/>
         * La directory dati standard e' la cartella dove il programma mantiene i dati standard che
         * usa per popolare inizialmente la tavola di un modulo quando e' vuota <br> Il percorso
         * restituito non comprende il separatore finale <br>
         *
         * @return il percorso della directory dati standard
         */
        public static String getDirDatiStandard() {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibSistema.getDirDatiStandard();
        }


        /**
         * Restituisce la cartella che contiene i file di aggiornamento del programma specifico.
         * <p/>
         *
         * @return indirizzo completo
         */
        public static String getDirUpdate() {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibSistema.getDirUpdate();
        }


        /**
         * Restituisce l'indirizzo del database delle preferenze del programma.
         * <p/>
         *
         * @return indirizzo completo
         */
        public static String getIndirizzoPreferenze() {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibSistema.getIndirizzoPreferenze();
        }


        /**
         * Restituisce il file di configurazione del programma.
         * <p/>
         *
         * @return il file
         */
        public static java.io.File getFileConfig() {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibSistema.getFileConfig();
        }


        /**
         * Emette un beep di sistema.
         * <p/>
         */
        public static void beep() {
            /* invoca il metodo sovrascritto della libreria specifica */
            LibSistema.beep();
        }


        /**
         * Ritorna la quantità di memoria occupata in Kb.
         * <p/>
         *
         * @return la quantità di memoria occupata in Kb.
         */
        public static long getBusyMemory() {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibSistema.getBusyMemory();
        }


    } // fine della classe 'interna'


    /**
     * Classe 'interna' per facilitare il reperimento. </p>
     */
    public static class Clas {

        /**
         * Crea una istanza di una qualsiasi classe coi parametri. </p> Crea un array di parametri
         * per il costruttore <br> Recupera dalla classe il costruttore corrispondente <br> Crea un
         * array di valori per il costruttore <br> Invoca il costruttore coi necessari parametri
         * <br>
         *
         * @param path         completo della classe da creare
         * @param classiValori dei parametri
         *
         * @return l'istanza appena creata (occorre il casting)
         */
        public static Object crea(String path, Parametro[] classiValori) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibClasse.crea(path, classiValori);
        }


        /**
         * Crea una istanza di una qualsiasi classe con un parametro. </p> Crea un array di
         * parametri per il costruttore <br> Recupera dalla classe il costruttore corrispondente
         * <br> Crea un array di valori per il costruttore <br> Invoca il costruttore coi necessari
         * parametri <br>
         *
         * @param path         completo della classe da creare
         * @param classeValore del parametro
         *
         * @return l'istanza appena creata (occorre il casting)
         */
        public static Object crea(String path, Parametro classeValore) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibClasse.crea(path, classeValore);
        }


        /**
         * Crea una istanza di una qualsiasi classe con un parametro. </p> Crea un array di
         * parametri per il costruttore <br> Recupera dalla classe il costruttore corrispondente
         * <br> Crea un array di valori per il costruttore <br> Invoca il costruttore coi necessari
         * parametri <br>
         *
         * @param path   completo della classe da creare
         * @param classe del parametro
         * @param valore del parametro
         *
         * @return l'istanza appena creata (occorre il casting)
         */
        public static Object crea(String path, Class classe, Object valore) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibClasse.crea(path, classe, valore);
        }


        /**
         * Crea una istanza di una qualsiasi classe. </p> Invoca il metodo delegato <br>
         *
         * @param path nome completo della classe da creare
         *
         * @return l'istanza appena creata (occorre il casting)
         */
        public static Object crea(String path) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibClasse.crea(path);
        }


        /**
         * Crea una istanza di una qualsiasi classe. </p> Invoca il metodo delegato <br>
         *
         * @param classe con cui creare l'oggetto (istanza)
         *
         * @return l'istanza appena creata (occorre il casting)
         */
        public static Object crea(Class classe) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibClasse.crea(classe);
        }


        /**
         * Crea una istanza di classe Modulo, col parametro AlberoNodo. </p> Crea un array di
         * parametri per il costruttore <br> Recupera dalla classe il costruttore corrispondente
         * <br> Crea un array di valori per il costruttore <br> Invoca il costruttore coi necessari
         * parametri <br>
         *
         * @param unPathCompleto nome completo della classe da creare
         * @param unNodo         parametro del costruttore di Modulo
         *
         * @return l'istanza Modulo appena creata
         */
        public static Modulo modulo(String unPathCompleto, AlberoNodo unNodo) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibClasse.modulo(unPathCompleto, unNodo);
        }


        /**
         * Ritorna il nome di una classe, senza il percorso.
         * <p/>
         *
         * @param classe l'oggetto Classe
         *
         * @return il nome della classe
         */
        public static String getNomeClasse(Class classe) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibClasse.getNomeClasse(classe);
        }


        /**
         * Recupera un argomento di tipo intero.
         * <p/>
         *
         * @param argomenti eventuali parametri in ingresso (quasi mai)
         * @param pos       argomento da esaminare
         * @param standard  valore di default
         *
         * @return argomento
         */
        public static int argInt(String[] argomenti, int pos, Object standard) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibClasse.argInt(argomenti, pos, standard);
        }


        /**
         * Recupera un argomento di tipo booleano.
         * <p/>
         *
         * @param argomenti eventuali parametri in ingresso (quasi mai)
         * @param pos       argomento da esaminare
         * @param standard  valore di default
         *
         * @return argomento
         */
        public static boolean argBool(String[] argomenti, int pos, Object standard) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibClasse.argBool(argomenti, pos, standard);
        }


        /**
         * Recupera un argomento di tipo stringa.
         * <p/>
         *
         * @param argomenti eventuali parametri in ingresso (quasi mai)
         * @param pos       argomento da esaminare
         * @param standard  valore di default
         *
         * @return argomento
         */
        public static String argStr(String[] argomenti, int pos, Object standard) {
            /* invoca il metodo sovrascritto della classe */
            return LibClasse.argStr(argomenti, pos, standard);
        }


        /**
         * Elabora gli argomenti/parametri in ingresso al programma.
         * <p/>
         * <p/>
         * Trasforma l'array di argomenti in una stringa unica <br> Trasforma la stringa in un array
         * di parametri chaive/valore <br> Inserisce i parametri in una mappa globale di
         * chiave/valore <br>
         *
         * @param argomenti eventuali parametri in ingresso (quasi mai)
         */
        public static void setArg(String[] argomenti) {
            /* invoca il metodo sovrascritto della classe */
            LibClasse.setArg(argomenti);
        }


        /**
         * Controlla la validità dei parametri passati.
         * <p/>
         * Ogni parametro viene convalidato in relazione al suo tipo di appartenenza <br>
         * <p/>
         *
         * @param parametri in ingresso al metodo
         *
         * @return vero se tutti i parametri sono validi
         */
        public static boolean isValidi(Object... parametri) {
            /* invoca il metodo sovrascritto della classe */
            return LibClasse.isValidi(parametri);
        }
    } // fine della classe 'interna'


    /**
     * Classe 'interna' per facilitare il reperimento. </p>
     */
    public static class Db {

        /**
         * Raddoppia i caratteri riservati del DB (single quote e backslash) per poterli registrare
         *
         * @param stringaInput la stringa da proteggere
         *
         * @return una stringa con i caratteri riservati protetti
         */
        public static String proteggiCaratteriRiservati(String stringaInput) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibDB.proteggiCaratteriRiservati(stringaInput);
        }

    } // fine della classe 'interna'


    /**
     * Classe 'interna' per facilitare il reperimento. </p>
     */
    public static class Imp {

        /**
         * Importa un file in formato vCard.
         * <p/>
         *
         * @param fileUtenti in formato vCard
         *
         * @return lista dei dati
         */
        public static ArrayList<LinkedHashMap<String, String>> vCard(java.io.File fileUtenti) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibImport.vCard(fileUtenti);
        }


        /**
         * Importa un file in formato vCard.
         * <p/>
         *
         * @param titolo del dialogo di selezione del file
         *
         * @return lista dei dati
         */
        public static ArrayList<LinkedHashMap<String, String>> vCard(String titolo) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibImport.vCard(titolo);
        }


        /**
         * Importa un file in formato vCard.
         * <p/>
         *
         * @return lista dei dati
         */
        public static ArrayList<LinkedHashMap<String, String>> vCard() {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibImport.vCard();
        }


        /**
         * Legge il contenuto di un file in formato CSV codificato UTF-8.
         * </p>
         *
         * @param pathFile nome completo del file da leggere
         *
         * @return lista di liste (righe di colonne)
         */
        public static List<List<String>> importCSV(String pathFile) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibImport.importCSV(pathFile);
        }// fine del metodo


        /**
         * Legge il contenuto di un file in formato CSV.
         * </p>
         *
         * @param pathFile     nome completo del file da leggere
         * @param encodingName nome dell'encoding del file da leggere (v. Charset)
         *
         * @return lista di liste (righe di colonne)
         */
        public static List<List<String>> importCSV(String pathFile, String encodingName) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibImport.importCSV(pathFile, encodingName);
        }// fine del metodo


        /**
         * Legge il contenuto di un file in formato CSV e codificato in UTF-8.
         * <p/>
         *
         * @return lista di liste (righe di colonne)
         */
        public static java.util.List<java.util.List<String>> importCSV() {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibImport.importCSV();
        }// fine del metodo


    } // fine della classe 'interna'


    /**
     * Rimando alla libreria immagini. </p>
     */
    public static class Image {

        /**
         * Importa un file in formato vCard.
         * <p/>
         *
         * @param fileUtenti in formato vCard
         *
         * @return lista dei dati
         */
        public static ArrayList<LinkedHashMap<String, String>> vCard(java.io.File fileUtenti) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibImport.vCard(fileUtenti);
        }


        /**
         * Crea una immagine da un componente.
         * <p/>
         * "Fotografa" il componente e restituisce la sua immagine grafica
         *
         * @param comp   il componente
         * @param hScale il fattore di scala orizzontale
         * @param vScale il fattore di scala verticale
         *
         * @return l'immagine creata dal componente
         */
        public static BufferedImage creaImmagine(Component comp, double hScale, double vScale) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibImage.creaImmagine(comp, hScale, vScale);
        }


        /**
         * Crea una immagine da un componente nelle dimensioni originali del componente.
         * <p/>
         * "Fotografa" il componente e restituisce la sua immagine grafica
         *
         * @param comp il componente
         *
         * @return l'immagine creata dal componente
         */
        public static BufferedImage creaImmagine(Component comp) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibImage.creaImmagine(comp);
        }


        /**
         * Crea un pannello contenente l'immagine di un componente a una data risoluzione.
         * <p/>
         * "Fotografa" il componente e crea un pannello contenente la sua immagine grafica alla
         * risuluzione desiderata
         *
         * @param comp il componente
         * @param dpi  la risoluzione in dpi
         *
         * @return il pannello contenente l'immagine
         */
        public static ImagePanel creaPannello(Component comp, double dpi) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibImage.creaPannello(comp, dpi);
        }


        /**
         * Crea un pannello contenente l'immagine di un componente alla risoluzione di default.
         * <p/>
         * "Fotografa" il componente e crea un pannello contenente la sua immagine grafica alla
         * risoluzione di default La risoluzione di default è quella dello schermo (72 dpi)
         *
         * @param comp il componente
         *
         * @return il pannello contenente l'immagine
         */
        public static ImagePanel creaPannello(Component comp) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibImage.creaPannello(comp);
        }


        /**
         * Crea un pannello contenente l'immagine di un componente alla risoluzione di stampa
         * standard.
         * <p/>
         * "Fotografa" il componente e crea un pannello contenente la sua immagine grafica alla
         * risoluzione di stampa standard La risoluzione di stampa standard è 300 dpi
         *
         * @param comp il componente
         *
         * @return il pannello contenente l'immagine
         */
        public static ImagePanel creaPannelloStampa(Component comp) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibImage.creaPannelloStampa(comp);
        }


        /**
         * Ritorna una BufferedImage con il contenuto di una Image
         * <p/>
         *
         * @param image immagine da convertire
         *
         * @return la BufferedImage con  il contenuto della image
         */
        public static BufferedImage toBufferedImage(java.awt.Image image) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibImage.toBufferedImage(image);
        }


    } // fine della classe 'interna'


    /**
     * Classe 'interna' per facilitare il reperimento. </p>
     */
    public static class Pref {

        /**
         * Recupera solo la parte terminale del nome canonico.
         * <p/>
         * Il nome viene restituito sempre minuscoilo <br>
         *
         * @param nomeCanonico da elaborare/convertire
         *
         * @return nome elaborato
         */
        public static String getNome(String nomeCanonico) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibPref.getNome(nomeCanonico);
        }


        /**
         * Recupera un parametro booleano.
         * <p/>
         *
         * @param parametro nome chiave del parametro
         *
         * @return oggetto booleano
         */
        public static boolean getBool(String parametro) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibPref.getBool(parametro);
        }


        /**
         * Recupera un parametro intero.
         * <p/>
         *
         * @param parametro nome chiave del parametro
         *
         * @return oggetto intero
         */
        public static int getInt(String parametro) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibPref.getInt(parametro);
        }


        /**
         * Recupera un parametro stringa.
         * <p/>
         *
         * @param parametro nome chiave del parametro
         *
         * @return oggetto stringa
         */
        public static String getStr(String parametro) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibPref.getStr(parametro);
        }


        /**
         * Recupera un argomento.
         * <p/>
         *
         * @param argomento nome chiave
         *
         * @return oggetto
         */
        public static Object getArg(String argomento) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibPref.getArg(argomento);
        }


        /**
         * Recupera tutti gli argomenti.
         * <p/>
         *
         * @return testo di tutti gli argomenti nella forma chiave/valore per ogni riga
         */
        public static String getArg() {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibPref.getArg();
        }


        /**
         * Controlla l'esistenza di un qrgomento.
         * <p/>
         *
         * @return vero se esiste mnella mappa
         */
        public static boolean isArg(String argomento) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibPref.isArg(argomento);
        }
    } // fine della classe 'interna'


    /**
     * Classe 'interna' per facilitare il reperimento. </p>
     */
    public static class FourD {

        /**
         * Interroga un metodo di 4D passandogli dei parametri e recupera la risposta.
         * <p/>
         *
         * @param indirizzo del web server di 4d
         * @param porta     del web server di 4d
         * @param user      il nome utente
         * @param password  la password
         * @param metodo    nome del metodo
         * @param parametri da passare al metodo, ogni parametro nella forma "nome=valore"
         *
         * @return la stringa di risposta
         */
        public static String query4D(String indirizzo,
                                     int porta,
                                     String user,
                                     String password,
                                     String metodo,
                                     String... parametri) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return Lib4D.query4D(indirizzo, porta, user, password, metodo, parametri);
        }

    } // fine della classe 'interna'


    /**
     * Classe 'interna' per facilitare il reperimento. </p>
     */
    public static class Dial {

        /**
         * Costruisce ed avvia un dialogo con i bottoni annulla e conferma.
         * <p/>
         *
         * @return valido o annullato
         */
        public static boolean annullaConferma(String titolo) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibDialogo.annullaConferma(titolo);
        }


        /**
         * Costruisce ed avvia un dialogo per l'input di un campo testo.
         * <p/>
         * Il titolo della finestra di dialogo è fisso <br>
         * Se il messaggio è valido, l'etichetta del campo testo è vuota <br>
         * Se il messaggio non è valido, l'etichetta del campo testo viene inserita di default <br>
         * Il dialogo è del tipo annulla/conferma <br>
         *
         * @param messaggio    di testo posizionato sopra il campo, nella parte superiore del dialogo
         * @param testoDefault suggerito
         *
         * @return testo inserito dall'utente
         */
        public static String creaTesto(String messaggio, String testoDefault) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibDialogo.creaTesto(messaggio, testoDefault);
        }


        /**
         * Costruisce ed avvia un dialogo per l'input di un campo testo.
         * <p/>
         * Il titolo della finestra di dialogo è fisso <br>
         * Se il messaggio è valido, l'etichetta del campo testo è vuota <br>
         * Se il messaggio non è valido, l'etichetta del campo testo viene inserita di default <br>
         * Il dialogo è del tipo annulla/conferma <br>
         *
         * @param messaggio di testo posizionato sopra il campo, nella parte superiore del dialogo
         *
         * @return testo inserito dall'utente
         */
        public static String creaTesto(String messaggio) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibDialogo.creaTesto(messaggio);
        }


        /**
         * Costruisce ed avvia un dialogo per l'input di un campo testo.
         * <p/>
         * Il titolo della finestra di dialogo è fisso <br>
         * L'etichetta del campo testo viene inserita di default <br>
         * Il dialogo è del tipo annulla/conferma <br>
         *
         * @return testo inserito dall'utente
         */
        public static String creaTesto() {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibDialogo.creaTesto();
        }


        /**
         * Costruisce ed avvia un dialogo per l'input di un campo numero intero.
         * <p/>
         * Il titolo della finestra di dialogo è fisso <br>
         * Se il messaggio è valido, l'etichetta del campo numero è vuota <br>
         * Se il messaggio non è valido, l'etichetta del campo numero viene inserita di default <br>
         * Il dialogo è del tipo annulla/conferma <br>
         *
         * @param messaggio     di testo posizionato sopra il campo, nella parte superiore del dialogo
         * @param numeroDefault suggerito
         *
         * @return valore numerico intero inserito dall'utente
         */
        public static int creaIntero(String messaggio, int numeroDefault) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibDialogo.creaIntero(messaggio, numeroDefault);
        }


        /**
         * Costruisce ed avvia un dialogo per l'input di un campo numero intero.
         * <p/>
         * Il titolo della finestra di dialogo è fisso <br>
         * Se il messaggio è valido, l'etichetta del campo numero è vuota <br>
         * Se il messaggio non è valido, l'etichetta del campo numero viene inserita di default <br>
         * Il dialogo è del tipo annulla/conferma <br>
         *
         * @param messaggio di testo posizionato sopra il campo, nella parte superiore del dialogo
         *
         * @return valore numerico intero inserito dall'utente
         */
        public static int creaIntero(String messaggio) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibDialogo.creaIntero(messaggio);
        }


        /**
         * Costruisce ed avvia un dialogo per l'input di un campo numero intero.
         * <p/>
         * Il titolo della finestra di dialogo è fisso <br>
         * L'etichetta del campo testo viene inserita di default <br>
         * Il dialogo è del tipo annulla/conferma <br>
         *
         * @return valore numerico intero inserito dall'utente
         */
        public static int creaIntero() {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibDialogo.creaIntero();
        }


        /**
         * Costruisce ed avvia un dialogo per l'input di un campo data.
         * <p/>
         * Il titolo della finestra di dialogo è fisso <br>
         * Se il messaggio è valido, l'etichetta del campo testo è vuota <br>
         * Se il messaggio non è valido, l'etichetta del campo testo viene inserita di default <br>
         * Il dialogo è del tipo annulla/conferma <br>
         *
         * @param messaggio   di testo posizionato sopra il campo, nella parte superiore del dialogo
         * @param dataDefault suggerita
         *
         * @return data inserita dall'utente
         */
        public static Date creaData(String messaggio, Date dataDefault) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibDialogo.creaData(messaggio, dataDefault);
        }


        /**
         * Costruisce ed avvia un dialogo per l'input di un campo data.
         * <p/>
         * Il titolo della finestra di dialogo è fisso <br>
         * Se il messaggio è valido, l'etichetta del campo testo è vuota <br>
         * Se il messaggio non è valido, l'etichetta del campo testo viene inserita di default <br>
         * Il dialogo è del tipo annulla/conferma <br>
         *
         * @param messaggio di testo posizionato sopra il campo, nella parte superiore del dialogo
         *
         * @return data inserita dall'utente
         */
        public static Date creaData(String messaggio) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibDialogo.creaData(messaggio);
        }


        /**
         * Costruisce ed avvia un dialogo per l'input di un campo data.
         * <p/>
         * Il titolo della finestra di dialogo è fisso <br>
         * L'etichetta del campo testo viene inserita di default <br>
         * Il dialogo è del tipo annulla/conferma <br>
         *
         * @return data inserita dall'utente
         */
        public static Date creaData() {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibDialogo.creaData();
        }


        /**
         * Costruisce ed avvia un dialogo per l'input di un campo data.
         * <p/>
         * Viene suggerita la data odierna (modificabile) <br>
         * Il titolo della finestra di dialogo è fisso <br>
         * Se il messaggio è valido, l'etichetta del campo testo è vuota <br>
         * Se il messaggio non è valido, l'etichetta del campo testo viene inserita di default <br>
         * Il dialogo è del tipo annulla/conferma <br>
         *
         * @param messaggio di testo posizionato sopra il campo, nella parte superiore del dialogo
         *
         * @return data inserita dall'utente
         */
        public static Date creaDataOggi(String messaggio) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibDialogo.creaDataOggi(messaggio);
        }


        /**
         * Costruisce ed avvia un dialogo per l'input di un campo data.
         * <p/>
         * Viene suggerita la data odierna (modificabile) <br>
         * Il titolo della finestra di dialogo è fisso <br>
         * L'etichetta del campo testo viene inserita di default <br>
         * Il dialogo è del tipo annulla/conferma <br>
         *
         * @return data inserita dall'utente
         */
        public static Date creaDataOggi() {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibDialogo.creaDataOggi();
        }
    } // fine della classe 'interna'


    /**
     * Classe 'interna' per facilitare il reperimento. </p>
     */
    public static class Stampa {


        /**
         * Crea un Printer vuoto con le impostazioni di default.
         * <p/>
         *
         * @return il printer creato
         */
        public static Printer getDefaultPrinter() {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibStampa.getDefaultPrinter();
        }


        /**
         * Stampa una o più pagine di testo.
         * <p/>
         * Il contenuto viene stampato come tabella <br>
         *
         * @param testo   (iniziale) da stampare
         * @param titolo  della pagina da stampare
         * @param matrice (righe, colonne) del contenuto
         * @param labelDx titolo di destra
         *
         * @return vero se la stampa è confermata
         */
        public static boolean report(String testo,
                                     String titolo,
                                     MatriceBase matrice,
                                     String labelDx) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibStampa.report(testo, titolo, matrice, labelDx);
        }


        /**
         * Stampa una o più pagine di testo.
         * <p/>
         * Il contenuto viene stampato come tabella <br>
         *
         * @param testo   (iniziale) da stampare
         * @param titolo  della pagina da stampare
         * @param matrice (righe, colonne) del contenuto
         *
         * @return vero se la stampa è confermata
         */
        public static boolean report(String testo, String titolo, MatriceBase matrice) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibStampa.report(testo, titolo, matrice);
        }


        /**
         * Stampa una o più pagine di testo.
         * <p/>
         *
         * @param testo   (iniziale) da stampare
         * @param titolo  della pagina da stampare
         * @param righe   di contenuto della pagina da stampare
         * @param labelDx titolo di destra
         *
         * @return vero se la stampa è confermata
         */
        public static boolean report(String testo,
                                     String titolo,
                                     ArrayList<String> righe,
                                     String labelDx) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibStampa.report(testo, titolo, righe, labelDx);
        }


        /**
         * Stampa una o più pagine di testo.
         * <p/>
         *
         * @param testo  (iniziale) da stampare
         * @param titolo della pagina da stampare
         * @param righe  di contenuto della pagina da stampare
         *
         * @return vero se la stampa è confermata
         */
        public static boolean report(String testo, String titolo, ArrayList<String> righe) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibStampa.report(testo, titolo, righe);
        }


        /**
         * Stampa una o più pagine di testo.
         * <p/>
         *
         * @param testo  (iniziale) da stampare
         * @param titolo della pagina da stampare
         *
         * @return vero se la stampa è confermata
         */
        public static boolean report(String testo, String titolo) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibStampa.report(testo, titolo);
        }


        /**
         * Stampa una o più pagine di testo.
         * <p/>
         *
         * @param testo da stampare
         *
         * @return vero se la stampa è confermata
         */
        public static boolean report(String testo) {
            /* invoca il metodo sovrascritto della libreria specifica */
            return LibStampa.report(testo);
        }
        
        


    } // fine della classe 'interna'

}// fine della classe
