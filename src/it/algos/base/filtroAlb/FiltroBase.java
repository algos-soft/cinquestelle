/**
 * Title:        FiltroBase.java
 * Package:      it.algos.base.filtroAlb
 * Description:
 * Copyright:    __COPY__
 * Company:      __COMPANY__
 * @author __AUTORI__  /  albi
 * @version 1.0  /
 * Creato:       il 5 dicembre 2003 alle 17.06
 */
//-----------------------------------------------------------------------------
// __COPY__  __COMPANY__ All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
//  Package di questa classe e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.filtroAlb;

import it.algos.base.errore.Errore;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//-----------------------------------------------------------------------------
// Classe principale


//-----------------------------------------------------------------------------
/**
 * Questa classe concreta e' responsabile di: <br>
 * gestisce una serie di filtri per la digitazione all'interno di componenti
 * di testo quali textarea e textfield.
 * A - Dispone di un set standard di filtri accessibili tramite metodi
 * statici <code>creaFiltroXYZ()</code><br>
 * B - Implementa l'interfaccia filtroOld, permettendo di creare filtri personalizzati
 * usando i metodi <code>addCaratteriAccettati()</code> e <code>addCaratteriRifiutati</code>
 * <br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  albi
 * @version 1.0  /  5 dicembre 2003 ore 17.06
 *          Non gestisce correttamente il carattere "euro" (€). Viene accettato
 *          solo se non ci sono caratteri rifiutati. Altrimenti (se c'e' almeno
 *          un carattere fifiutato, qualsiasi) viene sempre rifiutato.
 */
public class FiltroBase implements FiltroIngresso {

    //-------------------------------------------------------------------------
    // Costanti statiche della classe    (private,pubbliche)  (class variables)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Variabili statiche della classe         (private)      (class variables)
    //-------------------------------------------------------------------------
    private static FiltroIngresso F_INTERO = null;

    private static FiltroIngresso F_EMAIL = null;

    private static FiltroIngresso F_DECIMALI = null;

    private static FiltroIngresso F_TESTO = null;

    private static FiltroIngresso F_TESTO_NUMERI = null;

    /**
     * Metodo statico.
     * <p/>
     * Invocato la prima volta che la classe statica viene richiamata nel programma <br>
     */
    static {
        F_INTERO = new FiltroBase();
        F_INTERO.addCaratteriAccettati(NUMERI);
        F_INTERO.inizializza();

        F_EMAIL = new FiltroBase();
        F_EMAIL.addCaratteriAccettati(PAROLA + ".@" + TRATTINO);
        F_EMAIL.inizializza();

        F_DECIMALI = new FiltroBase();
        F_DECIMALI.addCaratteriAccettati(NUMERI + ".,+\\-");
        F_DECIMALI.inizializza();

        F_TESTO = new FiltroBase();
        F_TESTO.addCaratteriAccettati(LETTERE);
        F_TESTO.inizializza();

        F_TESTO_NUMERI = new FiltroBase();
        F_TESTO_NUMERI.addCaratteriAccettati(LETTERE + NUMERI);
        F_TESTO_NUMERI.inizializza();
    }


    //-------------------------------------------------------------------------
    // Riferimenti ad altri oggetti    (private,protette)  (instance variables)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Variabili degli oggetti (private,protette,pubbliche)(instance variables)
    //-------------------------------------------------------------------------
    private String accettati = "";

    private String rifiutati = "";

    private Pattern pattern = null;


    //-------------------------------------------------------------------------
    // Costruttori della classe                                   constructors)
    //-------------------------------------------------------------------------
    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     */
    public FiltroBase() {
        /** rimanda al costruttore della superclasse */
        super();

        /** regolazioni iniziali di riferimenti e variabili */
        try {                                   // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore completo */


    //-------------------------------------------------------------------------
    // Metodi statici della classe                              (class methods)
    //-------------------------------------------------------------------------
    /**
     * crea una filtroOld che accetta solo numeri
     */
    public static FiltroIngresso creaFiltroInteri() {
        return F_INTERO;
    }


    /**
     * crea una filtroOld che accetta numeri, il punto decimale e i segni + e -
     */
    public static FiltroIngresso creaFiltroDecimali() {
        return F_DECIMALI;
    }


    /**
     * crea una filtroOld che accetta solo lettere
     */
    public static FiltroIngresso creaFiltroSoloTesto() {
        return F_TESTO;
    }


    /**
     * crea una filtroOld che accetta lettere e numeri
     */
    public static FiltroIngresso creaFiltroTestoENumeri() {
        return F_TESTO_NUMERI;
    }


    /**
     * crea una filtroOld che accetta i caratteri ammessi in un indirizzo email
     */
    public static FiltroIngresso creaFiltroEMail() {
        return F_EMAIL;
    }


    //-------------------------------------------------------------------------
    // Metodi privati chiamati dal costruttore prima di tornare alla sottoclasse
    //-------------------------------------------------------------------------
    /**
     * Regolazioni iniziali di riferimenti e variabili
     * Metodo chiamato direttamente dal costruttore
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
    }


    /* fine del metodo */
    //-------------------------------------------------------------------------
    // Metodi protetti chiamati dalla sottoclasse subito dopo il costruttore
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi privati degli oggetti (usati nella classe)     (instance methods)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi protetti degli oggetti (usati nelle sottoclassi)    (instance m.)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi che verranno sovrascritti nelle sottoclassi          (overriding)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi che sovrascrivono metodi della superclasse           (overriding)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi pubblici degli oggetti (usati in altre classi)      (instance m.)
    //-------------------------------------------------------------------------
    public void addCaratteriAccettati(String s) {
        if (this.pattern != null) {
            throw new IllegalStateException("Impossibile modificare un filtroOld inizializzato");
        }
        this.accettati += s;
    }


    public void addCaratteriRifiutati(String s) {
        if (this.pattern != null) {
            throw new IllegalStateException("Impossibile modificare un filtroOld inizializzato");
        }
        this.rifiutati += s;
    }


    public void filtra(java.awt.event.KeyEvent e) {
        if (this.pattern == null) {
            throw new IllegalStateException("Filtro non inizializzato");
        }
        char c = e.getKeyChar();
        if (Character.isISOControl(c)) {
            //System.out.print("^"+e.getKeyText(e.getKeyCode()));
            return;
        }
        Matcher m = this.pattern.matcher(Character.toString(c));
        if (!m.matches()) {
            e.consume();
        }
    }


    /**
     * inizializza il filtroOld. Una volta inizializzato, un filtroOld non può piò
     * essere modificato tramite i metodi <code>addCaratteriAccettati</code>
     * o <code>addCaratteriRifiutati</code>
     */
    public void inizializza() {
        String connector = "&&",
                closer = "",
                accettati = this.accettati,
                rifiutati = this.rifiutati;

        if (!accettati.equals("")) {
            accettati = "[" + accettati;
            closer = "]";
        } else {
            connector = "";
            closer = "";
        }
        if (!rifiutati.equals("")) {
            rifiutati = "[^" + rifiutati + "]";
        } else {
            connector = "";
        }
        String regexp;
        regexp = accettati + connector + rifiutati + closer;
        this.pattern = Pattern.compile(regexp);
        if (this.pattern == null) {
            throw new IllegalStateException("Filtro non inizializzato! \n" + "(" + regexp + ")");
        }
    }


    public String toString() {
        return this.pattern.pattern();
    }


    /**
     * crea un nuovo filtroOld identico a questo ma non inizializzato. Può essere
     * usato per clonare uno dei filtri base e modificarli.
     */
    public FiltroIngresso clona() {
        FiltroBase f = new FiltroBase();
        f.accettati = this.accettati;
        f.rifiutati = this.rifiutati;
        return f;
    }


    public void keyPressed(java.awt.event.KeyEvent e) {
    }


    public void keyReleased(java.awt.event.KeyEvent e) {
    }


    public void keyTyped(java.awt.event.KeyEvent e) {
    }

    //-------------------------------------------------------------------------
    // Metodi di regolazione delle variabili locali                    (setter)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi di restituzione delle variabili locali                   (getter)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi di gestione interni, invocati dai metodi azione     (instance m.)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi azione                                         (instance methods)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Classi azione interne degli eventi                         (inner class)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
}// fine della classe it.algos.base.filtroAlb.FiltroBase.java
//-----------------------------------------------------------------------------
