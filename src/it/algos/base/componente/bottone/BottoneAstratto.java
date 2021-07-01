/**
 * Title:        BottoneAstratto.java
 * Package:      it.algos.base.componente.bottone
 * Description:  Componenti da riutilizzare (quasi dei Beans)
 * Copyright:    Copyright (c) 2002,2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 7 gennaio 2003 alle 9.33
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2002, 2003  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
// Package di questa classe e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.componente.bottone;

import it.algos.base.azione.Azione;
import it.algos.base.errore.ErroreInizia;
import it.algos.base.gestore.Gestore;

import javax.swing.*;
import java.awt.*;
//-----------------------------------------------------------------------------
// Classe principale


//-----------------------------------------------------------------------------
/**
 * Questa classe astratta e' responsabile di: <br>
 * A - Essere la superclasse da cui derivano tutti singoli bottoni <br>
 * B - Regola le dimensioni e la posizione del bottone <br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  7 gennaio 2003 ore 9.33
 */
public abstract class BottoneAstratto extends JButton {

    //-------------------------------------------------------------------------
    // Costanti statiche della classe    (private,pubbliche)  (class variables)
    //-------------------------------------------------------------------------
    /**
     * nome di questa classe (per i messaggi di errore)
     */
    private static final String NOME_CLASSE = "BottoneAstratto";

    /**
     * larghezza standard del bottone
     */
    protected static final int LARGHEZZA_BOTTONE = 120;

    /**
     * altezza standard del bottone
     */
    protected static final int ALTEZZA_BOTTONE = 30;

    /**
     * colore standard del testo
     */
    private static final Color COLORE_BOTTONE = new Color(0, 0, 128);

    /**
     * carattere standard del testo
     */
    private static final String FONT_BOTTONE = "Arial";

    /**
     * stile standard del testo
     */
    private static final int STILE_BOTTONE = Font.BOLD;

    /**
     * dimensione standard del testo
     */
    private static final int CORPO_BOTTONE = 12;

    /**
     * distanza 'suggerita' dal bordo sinistro
     */
    private static final int BORDO_SINISTRO = 50;

    /**
     * distanza 'suggerita' dal bordo superiore
     */
    private static final int BORDO_SUPERIORE = 50;

    /**
     * distanza dal bordo destro del contenitore (se esiste)
     */
    private static final int BORDO_DESTRO = 40;

    /**
     * distanza dal bordo inferiore del contenitore (se esiste)
     */
    private static final int BORDO_INFERIORE = 50;

    //-------------------------------------------------------------------------
    // Variabili statiche della classe         (private)      (class variables)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Riferimenti ad altri oggetti    (private,protette)  (instance variables)
    //-------------------------------------------------------------------------
    /**
     * gestore principale della logica e dei riferimenti (puntatori)
     */
    protected Gestore unGestore = null;

    /**
     * azione da applicare al bottone e da cui recuperare il testo
     */
    protected Azione unAzione = null;

    /**
     * eventuale contenitore in cui posizionare il bottone
     */
    private JPanel unPannello = null;

    //-------------------------------------------------------------------------
    // Variabili degli oggetti (private,protette,pubbliche)(instance variables)
    //-------------------------------------------------------------------------
    /**
     * oggetto di tipo font
     */
    private Font unFont;


    //-------------------------------------------------------------------------
    // Costruttori della classe                                   constructors)
    //-------------------------------------------------------------------------
    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo utilizzando eventuali valori di default
     */
    public BottoneAstratto() {
        /** rimanda al costruttore di questa classe */
        this(null, null);
    } /* fine del metodo costruttore base */


    /**
     * Costruttore completo
     *
     * @param unGestore oggetto che gestisce i comandi
     * @param unPannello contenitore in cui posizionarsi
     */
    public BottoneAstratto(Gestore unGestore, JPanel unPannello) {
        /** rimanda al costruttore della superclasse */
        super();

        /** regola le variabili di istanza coi parametri */
        this.unGestore = unGestore;
        this.unPannello = unPannello;

        /** regolazioni iniziali di riferimenti e variabili */
        try {                                   // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            new ErroreInizia(NOME_CLASSE, unErrore);
        } /* fine del blocco try-catch */

        /** Operazioni alla partenza ed eventuale interfaccia utente */
        this.partenza();
    } /* fine del metodo costruttore completo */


    //-------------------------------------------------------------------------
    // Metodi statici della classe                              (class methods)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi privati chiamati dal costruttore prima di tornare alla sottoclasse
    //-------------------------------------------------------------------------
    /**
     * Regolazioni iniziali di riferimenti e variabili
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        unFont = new Font(FONT_BOTTONE, STILE_BOTTONE, CORPO_BOTTONE);
    } /* fine del metodo inizia */


    /**
     * Operazioni alla partenza ed eventuale interfaccia utente
     */
    private void partenza() {
        /** dimensione standard per il bottone */
        this.setSize(LARGHEZZA_BOTTONE, ALTEZZA_BOTTONE);

        /** colore standard del testo */
        this.setForeground(COLORE_BOTTONE);

        /** font standard del testo */
        this.setFont(unFont);

        /** posizionamento del bottone */
        if (unPannello == null) {
            this.posizioneAltoSinistra();
        } else {
            this.posizioneBassoDestra();
        } /* fine del blocco if/else */
    } /* fine del metodo partenza */


    //-------------------------------------------------------------------------
    // Metodi protetti chiamati dalla sottoclasse subito dopo il costruttore
    //-------------------------------------------------------------------------
    /**
     * ...
     */
    protected void avviaBottone() {
        /** regola il nome */
//        this.setText((String)unAzioneOld.getAzione().getValue(AbstractAction.NAME));

        /** regola il tooltip */
//        this.setToolTipText((String)unAzioneOld.getAzione().getValue(AbstractAction.SHORT_DESCRIPTION));
    } /* fine del metodo */


    //-------------------------------------------------------------------------
    // Metodi privati degli oggetti (usati nella classe)     (instance methods)
    //-------------------------------------------------------------------------
    /**
     * regola la posizione del bottone in alto a sinistra
     */
    private void posizioneAltoSinistra() {
        /** posizione 'suggerita' - ogni finestra può spostarlo */
        this.setLocation(BORDO_SINISTRO, BORDO_SUPERIORE);
    } /* fine del metodo */


    /**
     * regola la posizione del bottone in basso a destra
     */
    private void posizioneBassoDestra() {
        /** recupera le dimensioni del contenitore */
        int larghezzaPannello = unPannello.getTopLevelAncestor().getWidth();
        int altezzaPannello = unPannello.getTopLevelAncestor().getHeight();

        /** calcola dove posizionarlo */
        int ascissa = larghezzaPannello - LARGHEZZA_BOTTONE - BORDO_DESTRO;
        int ordinata = altezzaPannello - ALTEZZA_BOTTONE - BORDO_INFERIORE;

        /** regola la posizione e aggiunge il bottone al contenitore */
        this.setLocation(ascissa, ordinata);
        unPannello.add(this);
    } /* fine del metodo */


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
    //-------------------------------------------------------------------------
    // Metodi di regolazione delle variabili locali                    (setter)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi di restituzione delle variabili locali                   (getter)
    //-------------------------------------------------------------------------
    /**
     * metodo getter per ottenere il valore della variabile privata
     */
    public Font getFont() {
        return this.unFont;
    } /* fine del metodo getter */
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
}// fine della classe it.algos.base.componente.bottone.BottoneAstratto.java
//-----------------------------------------------------------------------------

