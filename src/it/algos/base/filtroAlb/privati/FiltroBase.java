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
package it.algos.base.filtroAlb.privati;

import it.algos.base.errore.Errore;
import it.algos.base.filtroAlb.FiltroIngresso;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//-----------------------------------------------------------------------------
// Classe principale


//-----------------------------------------------------------------------------
/**
 * Questa classe concreta e' responsabile di: <br>
 * gestisce una serie di filtri per la digitazione all'interno di componenti
 * di testo quali textarea e textfield.
 * A - Implementa l'interfaccia filtroOld, permettendo di creare filtri personalizzati
 * usando i metodi <code>addCaratteriAccettati()</code> e <code>addCaratteriRifiutati</code>
 * <br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  albi
 * @version 1.0  /  5 dicembre 2003 ore 17.06
 * @todo Non gestisce correttamente il carattere "euro" (€). Viene accettato
 * solo se non ci sono caratteri rifiutati. Altrimenti (se c'e' almeno
 * un carattere rifiutato, qualsiasi) viene sempre rifiutato.
 */
public class FiltroBase extends KeyAdapter implements FiltroIngresso {

    //-------------------------------------------------------------------------
    // Costanti statiche della classe    (private,pubbliche)  (class variables)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Variabili statiche della classe         (private)      (class variables)
    //-------------------------------------------------------------------------
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
     * inizializza il filtroOld. Una volta inizializzato, un filtroOld non può più
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


    /**
     * permette di usare questo filtroOld come KeyListener. Filtra tutti i
     * tasti che vengono premuti all'interno del componente che ascolta
     */
    public void keyTyped(KeyEvent e) {
        filtra(e);
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
