/**
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Ceresa, Colombo, Valbonesi
 * @version 1.0  /
 * Creato:       il 14 agosto 2003 alle 9.58
 */
package it.algos.base.campo.video.decorator;

import it.algos.base.campo.video.CampoVideo;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.LibFont;

import javax.swing.*;
import java.awt.*;

/**
 * Decoratore grafico del campo vido.
 * <p/>
 * Questa classe astratta: <ul>
 * <li> Implementa il <i>design pattern</i> <b>Decorator</b> </li>
 * <li> Disegna un oggetto nel pannello campo del CampoVideo,
 * oltre al pannello componenti </li>
 * <li> La label può essere posizionata solo in quattro posizioni:
 * sopra, a sinistra, sotto ed a destra del pannello componenti </li>
 * <li> Le posizioni sono codificate nelle enum interna a questa classe </li>
 * <li> Le sottoclassi concrete possono disegnare testo o pannelli </li>
 * <li> Le sottoclassi concrete possono mantenere testo statico o dinamico </li>
 * <ul/>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  14 agosto 2003 ore 9.58
 */
public abstract class CVDLabel extends CVDecoratoreBase {

    /**
     * Colore del testo quando abilitato (default)
     */
    private static final Color ABILITATO = Color.BLACK;

    /**
     * Colore del testo quando disabilitato, uguale per tutti.
     */
    private static final Color DISABILITATO = Color.LIGHT_GRAY;

    /**
     * Colore del testo quando abilitato.
     */
    private Color coloreAbilitato;

    /**
     * testo
     */
    private String testo = null;


    /**
     * Costruttore completo con parametri.
     *
     * @param campoVideo da decorare
     */
    public CVDLabel(CampoVideo campoVideo) {
        /* rimanda al costruttore della superclasse */
        super(campoVideo);

        try { // prova ad eseguire il codice

            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        this.setColoreAbilitato(ABILITATO);
    }


    /**
     * Crea gli elementi GUI del pannelloCampo.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Gli elementi possono essere anche più di uno <br>
     * Gli elementi vengono aggiunti al pannelloCampo <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     *
     * @see #regolaElementi()
     */
    protected void creaComponentiGUI() {
        /* variabili e costanti locali di lavoro */
        JLabel label;
        String testo;
        Pos pos;

        try { // prova ad eseguire il codice
            /* invoca il metodo sovrascritto della superclasse */
            super.creaComponentiGUI();

            /* recupera il testo */
            testo = this.getTestoLabel();

            /* recupera l'oggetto label creato nella superclasse */
            label = this.getLabel();

            /* regola il testo */
            label.setText(testo);

            /* recupera la posizione */
            pos = this.getPos();

            /* aggiunge l'etichetta al pannelloCampo */
            this.addComponente(label, pos);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regola le dimensioni degli elementi.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Gli elementi possono essere anche più di uno <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     */
    protected void regolaElementi() {
        /* variabili e costanti locali di lavoro */
        JLabel label;
        int larghezza;
        int altezza;
        int deltaCorrettivoY;

        try { // prova ad eseguire il codice
            /* recupera l'etichetta */
            label = this.getLabel();

            /* recupera l'altezza */
            altezza = LibFont.getAltezza(label);

            /* per allinearla col campo testo principale */
            deltaCorrettivoY = CampoVideo.DELTA_Y_TESTO;
            altezza += deltaCorrettivoY;

            /* Recupera la larghezza */
            if (super.isRidimensionabile()) {
                larghezza = Lib.Fonte.getLarPixel(label);
            } else {
                larghezza = super.getLarghezzaCampo();
            } /* fine del blocco if/else */

            /* Regola la dimensione dell'etichetta */
            label.setSize(larghezza, altezza);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regola le caratteristiche grafiche degli elementi GUI.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Regola font, colori, bordi e sfondi di tutti gli elementi GUI
     * del pannelloCampo <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     *
     * @see it.algos.base.campo.video.CVBase#inizializza()
     */
    protected void regolaFontColori() {
        /* variabili e costanti locali di lavoro */
        Color colTesto;

        try {    // prova ad eseguire il codice

            colTesto = this.getLabel().getForeground();
            this.setColoreAbilitato(colTesto);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    }


    /**
     * Abilita o disabilita il componente.
     * <p/>
     * Sovrascrive il metodo della superclasse JComponent.<br>
     * Evita di disabilitare il componente che cambierebbe colore.<br>
     *
     * @param flag per abilitare o disabilitare
     */
    public void setModificabile(boolean flag) {
        /* variabili e costanti locali di lavoro */
        JLabel label;

        try { // prova ad eseguire il codice

            super.setModificabile(flag);

            label = this.getLabel();
            if (label != null) {
                if (flag) {
                    label.setForeground(this.getColoreAbilitato());
                } else {
                    label.setForeground(DISABILITATO);
                }// fine del blocco if-else
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Ritorna il testo della label
     * <p/>
     *
     * @return il testo della label
     */
    protected String getTestoLabel() {
        return testo;
    }


    protected void setTestoLabel(String testo) {
        this.testo = testo;
    }


    public void regolaTesto(String testo) {
        /* variabili e costanti locali di lavoro */
        JLabel label;

        try { // prova ad eseguire il codice
            this.setTestoLabel(testo);
            label = this.getLabel();
            if (label != null) {
                label.setText(testo);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    private Color getColoreAbilitato() {
        return coloreAbilitato;
    }


    protected void setColoreAbilitato(Color coloreAbilitato) {
        this.coloreAbilitato = coloreAbilitato;
    }


}// fine della classe