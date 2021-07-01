/**
 * Title:     Finestra
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      11-mar-2004
 */
package it.algos.base.finestra;

import it.algos.base.costante.CostanteColore;
import it.algos.base.portale.Portale;
import it.algos.base.statusbar.StatusBar;

import javax.swing.*;
import java.awt.*;

/**
 * Gestisce la finestra di <CODE>Lista o Scheda/Dialogo</CODE>.
 * </p>
 * Questa interfaccia: <ul>
 * <li> Implementa il <i>design pattern</i> <b>Facade</b> </li>
 * <li> Fornisce un interfaccia unificata per tutte le chiamate alle classi di
 * questo package </li>
 * <li> Ogni finestra contiene e visualizza un <CODE>MenuOld</CODE>,
 * un <CODE>PannelloOld</CODE> ed una StatusBar </li>
 * </ul>
 *
 * @author Alberto Colombo, Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 11-mar-2004 ore 12.28.16
 */
public interface Finestra {

    /**
     * larghezza di default di una finestra grande
     */
    public static final int LARGHEZZA_GRANDE = 900;

    /**
     * larghezza di default di una finestra media
     */
    public static final int LARGHEZZA_MEDIA = 580;

    /**
     * larghezza di default di una finestra piccola
     */
    public static final int LARGHEZZA_PICCOLA = 423;

    /**
     * altezza di default di una finestra grande
     */
    public static final int ALTEZZA_GRANDE = 620;

    /**
     * altezza di default di una finestra media
     */
    public static final int ALTEZZA_MEDIA = 530;

    /**
     * altezza di default di una finestra piccola
     */
    public static final int ALTEZZA_PICCOLA = 470;

    /**
     * larghezza di default di una finestra normale
     */
    public static final int LARGHEZZA_NORMALE = LARGHEZZA_MEDIA;

    /**
     * altezza di default di una finestra normale
     */
    public static final int ALTEZZA_NORMALE = ALTEZZA_GRANDE;

    /**
     * larghezza di default di una tabella normale
     */
    public static final int LARGHEZZA_TABELLA = LARGHEZZA_PICCOLA;

    /**
     * altezza di default di una tabella normale
     */
    public static final int ALTEZZA_TABELLA = LARGHEZZA_PICCOLA;

    /**
     * Colore sfondo delle finestre
     */
    public static final Color COLORE_SFONDO = CostanteColore.GRIGIO_SCURO;


    /**
     * flag di default per modificare le dimensioni della finestra
     */
    public static final boolean MODIFICABILE = false;


    /**
     * Regolazioni di ri-avvio. <br>
     * Metodo chiamato da altre classi ogni volta che questo oggetto deve
     * <i>ripartire</i>, per essere sicuri che sia <i>pulito</i> <br>
     * Viene eseguito tutte le volte che necessita <br>
     */
    public abstract void avvia();


    /**
     * Centra questa finestra sull'area disponibile dello schermo.
     * <p/>
     * La finestra non viene impacchettata.
     * La finestra non viene automaticamente resa visibile.
     */
    public abstract void centra();


    /**
     * Regolazioni iniziali <i>una tantum</i>. <br>
     * Metodo chiamato dalla classe che crea questo oggetto dopo che sono
     * stati regolati dalla sottoclasse i parametri indispensabili <br>
     * Viene eseguito una sola volta <br>
     */
    public abstract void inizializza();


    /**
     * Assegna un voce alla finestra.
     * <p/>
     *
     * @param titolo il voce
     */
    public abstract void setTitolo(String titolo);


    /**
     * Assegna una icona alla finestra
     * <p/>
     *
     * @param icona da assegnare
     */
    public abstract void setIcona(ImageIcon icona);


    /**
     * Sostituisce completamente il contenuto della StatusBar della finestra
     * <p/>
     *
     * @param comp componente da inserire nella StatusBar
     */
    public abstract void setStatusBar(JComponent comp);


    public abstract void setPannelloPrincipale(Portale pannelloPrincipale);


    /**
     * Rende la finestra visibile o invisibile
     * <p/>
     *
     * @param flag di visibilità
     */
    public abstract void setVisible(boolean flag);


    /**
     * Impacchetta la finestra
     * <p/>
     */
    public abstract void pack();


    //    public abstract void setAzioni(LinkedHashMap<String, Azione> azioni);
    public abstract FinestraBase getFinestraBase();


    /*
     * Ritorna la StatusBar della finestra
     * <p>
     * @return la StatusBar della finestra
     */
    public abstract StatusBar getStatusBar();

}// fine della interfaccia
