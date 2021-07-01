/*
 * FiltroIngressoFactory.java
 *
 * Created on 19 dicembre 2003, 16.48
 */

package it.algos.base.filtro;

import it.algos.base.errore.Errore;
import it.algos.base.filtro.privati.FiltroBase;

import javax.swing.*;
import javax.swing.text.DefaultFormatter;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.awt.event.KeyListener;
import java.text.ParseException;

/**
 * Classe factory per generare JTextField dotate di filtri di ingresso.
 * TODO bisogna ancora fare tutti i test.
 *
 * @author albi
 */
public class FiltroIngressoFactory {

    private static DefaultFormatter F_CODICE_FISCALE = null;

    private static DefaultFormatter F_DATA = null;

    private static FiltroIngresso F_INTERO = null;

    private static FiltroIngresso F_EMAIL = null;

    private static FiltroIngresso F_DECIMALI = null;

    private static FiltroIngresso F_TESTO = null;

    private static FiltroIngresso F_TESTO_NUMERI = null;


    static {
        F_INTERO = new FiltroBase();
        F_INTERO.addCaratteriAccettati(FiltroIngresso.NUMERI);
        F_INTERO.inizializza();

        F_EMAIL = new FiltroBase();
        F_EMAIL.addCaratteriAccettati(FiltroIngresso.PAROLA + ".@" + FiltroIngresso.TRATTINO);
        F_EMAIL.inizializza();

        F_DECIMALI = new FiltroBase();
        F_DECIMALI.addCaratteriAccettati(FiltroIngresso.NUMERI + ".,+\\-");
        F_DECIMALI.inizializza();

        F_TESTO = new FiltroBase();
        F_TESTO.addCaratteriAccettati(FiltroIngresso.LETTERE);
        F_TESTO.inizializza();

        F_TESTO_NUMERI = new FiltroBase();
        F_TESTO_NUMERI.addCaratteriAccettati(FiltroIngresso.LETTERE + FiltroIngresso.NUMERI);
        F_TESTO_NUMERI.inizializza();

        try {
            F_CODICE_FISCALE = new MaskFormatter("UUUUUU##U##U###U");
            F_DATA = new MaskFormatter("##-##-####");
            ((MaskFormatter)F_DATA).setPlaceholderCharacter('_');
            //((MaskFormatter)F_DATA).setPlaceholder("ggmmaaaa");
        } catch (ParseException pe) {
            new Errore(pe);
        }
    }


    /**
     * prevents the creation of a new instance of FiltroIngressoFactory
     */
    private FiltroIngressoFactory() {
    }


    /**
     * crea un textfield formattato che accetta in input un codice fiscale.
     * è <bold>necessario</bold> che il metodo restituisca un JFormattedTextField
     * perché i filtri di questo tipo (JFormattedTextField.AbstractFilter) non
     * sono immediati da usare.
     *
     * @return un JFormattedtextField che accetta solo codici fiscali
     */
    public static JFormattedTextField creaCodiceFiscale() {
        JFormattedTextField ftf = new JFormattedTextField();
        ftf.setFormatterFactory(new DefaultFormatterFactory(F_CODICE_FISCALE));
        ftf.setFocusLostBehavior(ftf.COMMIT);
        return ftf;
    }


    /**
     * @return un JFormattedtextField che accetta date nella forma gg-mm-aaaa
     *
     * @see #creaCodiceFiscale
     */
    public static JFormattedTextField creaFiltroData() {
        JFormattedTextField ftf = new JFormattedTextField();
        ftf.setFormatterFactory(new DefaultFormatterFactory(F_DATA));
        ftf.setFocusLostBehavior(ftf.COMMIT);
        return ftf;
    }


    /**
     * crea una filtroOld che accetta solo numeri
     */
    public static FiltroIngresso creaInteri() {
        FiltroIngresso f = F_INTERO.clona();
        f.inizializza();
        return f;
    }


    /**
     * crea una filtroOld che accetta numeri, il punto decimale e i segni + e -
     */
    public static FiltroIngresso creaDecimali() {
        FiltroIngresso f = F_DECIMALI.clona();
        f.inizializza();
        return f;
    }


    /**
     * crea una filtroOld che accetta solo lettere
     */
    public static FiltroIngresso creaSoloTesto() {
        FiltroIngresso f = F_TESTO.clona();
        f.inizializza();
        return f;
    }


    /**
     * crea una filtroOld che accetta lettere e numeri
     */
    public static FiltroIngresso creaTestoENumeri() {
        FiltroIngresso f = F_TESTO_NUMERI.clona();
        f.inizializza();
        return f;
    }


    /**
     * crea una filtroOld che accetta i caratteri ammessi in un indirizzo email
     */
    public static FiltroIngresso creaEMail() {
        FiltroIngresso f = F_EMAIL.clona();
        f.inizializza();
        return f;
    }


    /**
     * istanzia al volo un JTextField con un filtroOld di ingresso che accetta
     * i caratteri passati come parametro.
     *
     * @see FiltroIngresso
     */
    public static JTextField creaFiltro(String accettati) {
        return creaFiltro(accettati, null);
    }


    /**
     * istanzia al volo un JTextField con filtroOld di ingresso. Come parametri,
     * si consiglia di non utilizzare stringhe specificate a call-time; invece,
     * utilizzare le apposite costanti definite in FiltroIngresso.
     *
     * @param accettati elenco dei caratteri che il filtroOld accetta
     * @param rifiutati elenco dei caratteri che il filtroOld rifiuta
     *
     * @see FiltroIngresso
     */
    public static JTextField creaFiltro(String accettati, String rifiutati) {
        FiltroBase fb = new FiltroBase();
        fb.addCaratteriAccettati(accettati);
        if (rifiutati != null) {
            fb.addCaratteriRifiutati(rifiutati);
        }
        fb.inizializza();
        return creaConListener(fb);
    }


    /**
     * crea un JTextField e gli assegna il KeyListener passato come parametro
     */
    private static JTextField creaConListener(KeyListener unListener) {
        JTextField tf = new JTextField();
        tf.addKeyListener(unListener);
        return tf;
    }

}
