/**
 * Title:        FontColore.java
 * Package:      it.algos.base.wrapper
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 9 giugno 2003 alle 12.44
 */
package it.algos.base.wrapper;

import it.algos.base.costante.CostanteColore;
import it.algos.base.costante.CostanteFont;
import it.algos.base.errore.Errore;

import javax.swing.*;
import java.awt.*;

/**
 * Questa classe concreta e' responsabile di: <br>
 * A - ... <br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  9 giugno 2003 ore 12.44
 */
public final class TestoAlgos implements CostanteColore, CostanteFont {

    private static final int ALLINEAMENTO_SX = SwingConstants.LEFT;

    private static final int ALLINEAMENTO_DX = SwingConstants.RIGHT;

    private static final int ALLINEAMENTO_CENTRO = SwingConstants.CENTER;

    private Font unFont = null;

    private Color unColoreSfondo = null;

    private Color unColoreTesto = null;

    private boolean isTrasparente = false;

    private int unAllineamento = 0;


    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     */
    public TestoAlgos() {
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


    /**
     * Regola un oggetto (etichetta, componente, testo aggiuntivo),
     * in funzione del TestoAlgos che riceve
     */
    private static void setTestoAlgos(JComponent unComponente, TestoAlgos unTestoAlgos) {
        unComponente.setFont(unTestoAlgos.getFont());
        unComponente.setForeground(unTestoAlgos.getColoreTesto());
        unComponente.setBackground(unTestoAlgos.getColoreSfondo());
        unComponente.setOpaque(!unTestoAlgos.isTrasparente());
        unComponente.setAlignmentX(unTestoAlgos.getAllineamento());
    } /* fine del metodo */


    /**
     * Crea il testo per l'etichetta di un campo
     * con i valori di default
     */
    private static TestoAlgos creaTestoEtichettaCampo() {
        /** variabili e costanti locali di lavoro */
        TestoAlgos unTesto = new TestoAlgos();
        unTesto.setColoreSfondo(SFONDO_SCHEDA);
        unTesto.setColoreTesto(TESTO_ETICHETTA);
        unTesto.setFont(FONT_LABEL_SCHEDA);
        unTesto.setAllineamento(ALLINEAMENTO_SX);
        unTesto.isTrasparente(true);
        return unTesto;
    } /* fine del metodo */


    /**
     * Regola il componente come etichetta, con i valori di default
     */
    public static void setEtichetta(JComponent unComponente) {
        setTestoAlgos(unComponente, creaTestoEtichettaCampo());
    } /* fine del metodo */


    /**
     * Crea il testo per il componente di un campo
     * con i valori di default
     */
    private static TestoAlgos creaTestoComponenteCampo() {
        /** variabili e costanti locali di lavoro */
        TestoAlgos unTesto = new TestoAlgos();
        unTesto.setColoreTesto(TESTO_NORMALE);
        unTesto.setColoreSfondo(SFONDO_CAMPO_EDIT);
        unTesto.setFont(FONT_CAMPI_EDIT_SCHEDA);
        unTesto.setAllineamento(ALLINEAMENTO_SX);
        unTesto.isTrasparente(false);
        return unTesto;
    } /* fine del metodo */


    /**
     * Regola il componente come un campo edit, con i valori di default
     */
    public static void setField(JComponent unComponente) {
        setTestoAlgos(unComponente, creaTestoComponenteCampo());
    } /* fine del metodo */


    /**
     * Crea il testo normale.
     */
    private static TestoAlgos creaTesto() {
        /* variabili e costanti locali di lavoro */
        TestoAlgos unTesto = new TestoAlgos();
        unTesto.setColoreTesto(TESTO_ETICHETTA);
        unTesto.setColoreSfondo(SFONDO_CAMPO_EDIT);
        unTesto.setFont(FONT_DIALOGHI);
        unTesto.setAllineamento(ALLINEAMENTO_SX);
        unTesto.isTrasparente(true);
        return unTesto;
    }


    /**
     * Regola il componente come testo normale.
     * <p/>
     * <ul>
     * <li> Colore del testo =  azzurro</li>
     * <li> Colore di sfondo =  non usato</li>
     * <li> Tipo di font =  dialoghi</li>
     * <li> Allineamento =  sinistra</li>
     * <li> Trasparente =  vero</li>
     * </ul>
     *
     * @param unComponente componente da regolare
     */
    public static void setTesto(JComponent unComponente) {
        setTestoAlgos(unComponente, creaTesto());
    }


    /**
     * Crea il testo per il componente testo scorrevole di un campo
     * con i valori di default
     */
    private static TestoAlgos creaTestoAreaCampo() {
        /** variabili e costanti locali di lavoro */
        TestoAlgos unTesto = new TestoAlgos();
        unTesto.setColoreSfondo(SFONDO_CAMPO_TESTO_AREA);
        unTesto.setColoreTesto(TESTO_NORMALE);
        unTesto.setFont(FONT_CAMPI_EDIT_SCHEDA);
        unTesto.setAllineamento(ALLINEAMENTO_SX);
        unTesto.isTrasparente(false);
        return unTesto;
    } /* fine del metodo */


    /**
     * Regola il componente come testo scorrevole di un campo, con i valori di default
     */
    public static void setArea(JComponent unComponente) {
        setTestoAlgos(unComponente, creaTestoAreaCampo());
    } /* fine del metodo */


    /**
     * Crea il testo per il testo aggiuntivo di un campo
     * con i valori di default
     */
    private static TestoAlgos creaTestoAggiuntivoCampo() {
        /** variabili e costanti locali di lavoro */
        TestoAlgos unTesto = new TestoAlgos();
        unTesto.setColoreSfondo(SFONDO_SCHEDA);
        unTesto.setColoreTesto(TESTO_AGGIUNTIVO_CAMPO);
        unTesto.setFont(FONT_LEGENDA_NORMALE);
        unTesto.setAllineamento(ALLINEAMENTO_SX);
        unTesto.isTrasparente(true);
        return unTesto;
    } /* fine del metodo */


    /**
     * Regola il componente come testo aggiuntivo di un campo, con i valori di default
     */
    public static void setFieldExtra(JComponent unComponente) {
        setTestoAlgos(unComponente, creaTestoAggiuntivoCampo());
    } /* fine del metodo */


    /**
     * Crea il testo per un checkbox
     * con i valori di default
     */
    private static TestoAlgos creaTestoCheckBox() {
        /** variabili e costanti locali di lavoro */
        TestoAlgos unTesto = new TestoAlgos();
        unTesto.setColoreSfondo(SFONDO_SCHEDA_CENTRO);
        unTesto.setColoreTesto(TESTO_ETICHETTA);
        unTesto.setFont(FONT_CAMPI_CHECK_SCHEDA);
        unTesto.setAllineamento(ALLINEAMENTO_SX);
        unTesto.isTrasparente(true);
        return unTesto;
    } /* fine del metodo */


    /**
     * Regola il componente come un checkbox, con i valori di default
     */
    public static void setCheckBox(JComponent unComponente) {
        setTestoAlgos(unComponente, creaTestoCheckBox());
    } /* fine del metodo */


    /**
     * Crea il testo per un radio bottone
     * con i valori di default
     */
    private static TestoAlgos creaTestoRadioBottone() {
        /** variabili e costanti locali di lavoro */
        TestoAlgos unTesto = new TestoAlgos();
        unTesto.setColoreSfondo(SFONDO_SCHEDA_CENTRO);
        unTesto.setColoreTesto(TESTO_NORMALE);
        unTesto.setFont(FONT_CAMPI_RADIO_SCHEDA);
        unTesto.setAllineamento(ALLINEAMENTO_SX);
        unTesto.isTrasparente(true);
        return unTesto;
    } /* fine del metodo */


    /**
     * Regola il componente come un radio bottone, con i valori di default
     */
    public static void setRadio(JComponent unComponente) {
        setTestoAlgos(unComponente, creaTestoRadioBottone());
    } /* fine del metodo */


    /**
     * Crea il testo per il popup di un campo
     * con i valori di default
     */
    private static TestoAlgos creaTestoComboBox() {
        /** variabili e costanti locali di lavoro */
        TestoAlgos unTesto = new TestoAlgos();
        unTesto.setColoreSfondo(SFONDO_CAMPO_POPUP);
        unTesto.setColoreTesto(TESTO_NORMALE);
        unTesto.setFont(FONT_CAMPI_POPUP_SCHEDA);
        unTesto.setAllineamento(ALLINEAMENTO_SX);
        unTesto.isTrasparente(true);
        return unTesto;
    } /* fine del metodo */


    /**
     * Regola il componente come un popup, con i valori di default
     */
    public static void setCombo(JComponent unComponente) {
        setTestoAlgos(unComponente, creaTestoComboBox());
    } /* fine del metodo */


    /**
     * Crea il testo per la statusbar delle finestre
     * con i valori di default
     */
    private static TestoAlgos creaStatusBarFinestra() {
        /** variabili e costanti locali di lavoro */
        TestoAlgos unTesto = new TestoAlgos();
        unTesto.setColoreSfondo(SFONDO_FINESTRA_LISTA_STATUSBAR);
        unTesto.setColoreTesto(TESTO_STATUS_BAR);
        unTesto.setFont(FONT_CAMPI_EDIT_SCHEDA);
        unTesto.setAllineamento(ALLINEAMENTO_SX);
        unTesto.isTrasparente(true);
        return unTesto;
    } /* fine del metodo */


    /**
     * Regola il componente, con i valori di default
     */
    public static void setStatusBarFinestra(JComponent unComponente) {
        setTestoAlgos(unComponente, creaStatusBarFinestra());
    } /* fine del metodo */


    /**
     * Crea il testo per la legenda di spiegazione sotto un campo
     * con i valori di default
     */
    private static TestoAlgos creaTestoLegenda() {
        /** variabili e costanti locali di lavoro */
        TestoAlgos unTesto = new TestoAlgos();
        unTesto.setColoreSfondo(SFONDO_CAMPO_POPUP);
        unTesto.setColoreTesto(new Color(120, 50, 78));
        unTesto.setFont(FONT_LEGENDA_PICCOLO);
        unTesto.setAllineamento(ALLINEAMENTO_SX);
        unTesto.isTrasparente(true);
        return unTesto;
    } /* fine del metodo */


    /**
     * Regola il componente, con i valori di default
     */
    public static void setLegenda(JComponent unComponente) {
        setTestoAlgos(unComponente, creaTestoLegenda());
    } /* fine del metodo */


    /**
     * Crea il testo per un voce interno al campo
     * con i valori di default
     */
    private static TestoAlgos creaTestoTitolo() {
        /** variabili e costanti locali di lavoro */
        TestoAlgos unTesto = new TestoAlgos();
        unTesto.setColoreSfondo(SFONDO_SCHEDA);
        unTesto.setColoreTesto(TESTO_NORMALE);
        unTesto.setFont(FONT_LABEL_SCHEDA);
        unTesto.setAllineamento(ALLINEAMENTO_SX);
        unTesto.isTrasparente(true);
        return unTesto;
    } /* fine del metodo */


    /**
     * Regola il componente, con i valori di default
     */
    public static void setTitolo(JComponent unComponente) {
        setTestoAlgos(unComponente, creaTestoTitolo());
    } /* fine del metodo */


    /**
     * Crea il testo per il componente di un campo
     * con i valori di default
     */
    private static TestoAlgos creaTestoComponenteBoldCampo() {
        /** variabili e costanti locali di lavoro */
        TestoAlgos unTesto = new TestoAlgos();
        unTesto.setColoreSfondo(SFONDO_CAMPO_EDIT);
        unTesto.setColoreTesto(TESTO_AGGIUNTIVO_CAMPO);
        unTesto.setFont(FONT_CAMPI_EDIT_BOLD_SCHEDA);
        unTesto.setAllineamento(ALLINEAMENTO_SX);
        unTesto.isTrasparente(false);
        return unTesto;
    } /* fine del metodo */


    /**
     * Regola il componente come un campo edit, con i valori di default
     */
    public static void setFieldBold(JComponent unComponente) {
        setTestoAlgos(unComponente, creaTestoComponenteBoldCampo());
    } /* fine del metodo */


    /**
     * Crea il testo per il componente di un campo
     * con i valori di default
     */
    private static TestoAlgos creaTestoLista() {
        /** variabili e costanti locali di lavoro */
        TestoAlgos unTesto = new TestoAlgos();
        unTesto.setColoreSfondo(SFONDO_SCHEDA);
        unTesto.setColoreTesto(TESTO_NORMALE);
        unTesto.setFont(FONT_CAMPI_EDIT_SCHEDA);
        unTesto.setAllineamento(ALLINEAMENTO_SX);
        unTesto.isTrasparente(true);
        return unTesto;
    } /* fine del metodo */


    /**
     * Regola il testo del componente.
     * <ul>
     * <li>trasparente, senza sfondo</li>
     * <li>colore nero</li>
     * <li>famiglia font base, stile plain, dimensione base</li>
     * <li>allineamento a sinistra</li>
     * </ul>
     */
    public static void setLista(JComponent unComponente) {
        setTestoAlgos(unComponente, creaTestoLista());
    } /* fine del metodo */


    /**
     * Regola il testo del componente.
     * <ul>
     * <li>trasparente, senza sfondo</li>
     * <li>colore nero</li>
     * <li>famiglia font base, stile bold, dimensione base</li>
     * <li>allineamento a sinistra</li>
     * </ul>
     */
    public static void setListaBold(JComponent unComponente) {
        /* variabili e costanti locali di lavoro */
        TestoAlgos stile;
        Font font;

        try { // prova ad eseguire il codice
            stile = creaTestoLista();
            font = stile.getFont();
            font = font.deriveFont(Font.BOLD);
            stile.setFont(font);
            setTestoAlgos(unComponente, stile);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } /* fine del metodo */


    /**
     * Regolazioni iniziali di riferimenti e variabili
     * Metodo chiamato direttamente dal costruttore
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
    }


    /**
     * Setter for property unFont.
     *
     * @param unFont New value of property unFont.
     */
    public void setFont(java.awt.Font unFont) {
        this.unFont = unFont;
    }


    /**
     * Setter for property unColoreSfondo.
     *
     * @param unColoreSfondo New value of property unColoreSfondo.
     */
    public void setColoreSfondo(java.awt.Color unColoreSfondo) {
        this.unColoreSfondo = unColoreSfondo;
    }


    /**
     * Setter for property unColoreTesto.
     *
     * @param unColoreTesto New value of property unColoreTesto.
     */
    public void setColoreTesto(java.awt.Color unColoreTesto) {
        this.unColoreTesto = unColoreTesto;
    }


    /**
     * Setter for property isTrasparente.
     *
     * @param isTrasparente New value of property isTrasparente.
     */
    public void isTrasparente(boolean isTrasparente) {
        this.isTrasparente = isTrasparente;
    }


    /**
     * Setter for property unAllineamento.
     *
     * @param unAllineamento New value of property unAllineamento.
     */
    public void setAllineamento(int unAllineamento) {
        this.unAllineamento = unAllineamento;
    }


    /**
     * Getter for property unFont.
     *
     * @return Value of property unFont.
     */
    public java.awt.Font getFont() {
        return unFont;
    }


    /**
     * Getter for property unColoreSfondo.
     *
     * @return Value of property unColoreSfondo.
     */
    public java.awt.Color getColoreSfondo() {
        return unColoreSfondo;
    }


    /**
     * Getter for property unColoreTesto.
     *
     * @return Value of property unColoreTesto.
     */
    public java.awt.Color getColoreTesto() {
        return unColoreTesto;
    }


    /**
     * Getter for property isTrasparente.
     *
     * @return Value of property isTrasparente.
     */
    public boolean isTrasparente() {
        return isTrasparente;
    }


    /**
     * Getter for property unAllineamento.
     *
     * @return Value of property unAllineamento.
     */
    public int getAllineamento() {
        return unAllineamento;
    }

}// fine della classe