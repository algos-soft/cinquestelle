/**
 * Title:     FamigliaFont
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      19-gen-2004
 */
package it.algos.base.font;

import it.algos.base.errore.Errore;
import it.algos.base.messaggio.MessaggioAvviso;

import java.util.ArrayList;

/**
 * </p>
 * Questa classe definisce una Famiglia di font<br>
 * Una FamigliaFont e' costituita da<ul>
 * <li> un Nome di famiglia
 * <li> una collezione di oggetti Font in diversi stili
 * </ul>
 *
 * @author Alberto Colombo, Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 19-gen-2004 ore 9.36.32
 */
public final class FamigliaFont extends Object {

    /* nome della famiglia */
    private String nomeFamiglia = null;

    /* collezione di oggetti FontSingolo */
    private ArrayList collezioneFont = null;


    /**
     * Costruttore completo.<br>
     *
     * @param unNomeFamiglia nome della famiglia di font
     */
    public FamigliaFont(String unNomeFamiglia) {
        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia(unNomeFamiglia);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni immediate di riferimenti e variabili. <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @param unNomeFamiglia nome della famiglia di font
     *
     * @throws Exception unaEccezione
     */
    private void inizia(String unNomeFamiglia) throws Exception {
        this.setNomeFamiglia(unNomeFamiglia);
        collezioneFont = new ArrayList();
    }// fine del metodo inizia


    /**
     * Aggiunge un Font alla collezione font della famiglia. <br>
     *
     * @param unFont il font da aggiungere alla collezione
     */
    public void addFont(FontSingolo unFont) {
        /* variabili e costanti locali di lavoro */
        String messaggio = null;

        /* controllo di congruita' */
        if (unFont instanceof FontSingolo) {
            collezioneFont.add(unFont);
        } else {
            messaggio = "Si possono aggiungere solo oggetti di classe ";
            messaggio += "FontSingolo a una FamigliaFont.";
            new MessaggioAvviso(messaggio);
        }// fine del blocco if-else

    } // fine del metodo


    /**
     * Recupera il nome della famiglia. <br>
     *
     * @return il nome della famiglia
     */
    public String getNomeFamiglia() {
        return nomeFamiglia;
    }


    /**
     * Regola il nome della famiglia .<br>
     *
     * @param nomeFamiglia il nuovo nome famiglia
     */
    public void setNomeFamiglia(String nomeFamiglia) {
        this.nomeFamiglia = nomeFamiglia;
    }


    /**
     * Ritorna la collezione di font della famiglia.<br>
     * (oggetti FontSingolo)
     *
     * @return la collezione di oggetti FontSingolo
     */
    public ArrayList getElencoFont() {
        return collezioneFont;
    }

}// fine della classe
