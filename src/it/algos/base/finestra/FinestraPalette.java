/**
 * Title:     FinestraPalette
 * Copyright: Copyright (c) 2007
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      03-ago-2007
 */
package it.algos.base.finestra;

import it.algos.base.azione.adapter.AzAdapterWindow;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.messaggio.MessaggioDialogo;
import it.algos.base.progetto.Progetto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

/**
 * Finestra flottante contenente la Palette.
 * </p>
 * Questa classe: <ul>
 * <li> </li>
 * <li> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 03-ago-2007
 */
public final class FinestraPalette extends FinestraBase {


    /**
     * Costruttore completo.
     * <p/>
     */
    public FinestraPalette() {
        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni immediate di riferimenti e variabili. <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */
        Point p1;
        Point p2;
        int move = 10;


        try { // prova ad eseguire il codice

            /* La finestra Palette è sempre sopra a tutte le altre */
            this.setAlwaysOnTop(true);

            this.getRootPane().setBorder(BorderFactory.createLineBorder(Color.darkGray, 1));

            /* sposta la finestra in basso e a destra di un po' */
            p1 = this.getLocation();
            p2 = new Point(p1.x + move, p1.y + move);
            this.setLocation(p2);

            /* ascoltatore del CloseBox */
            this.addWindowListener(new AzChiude());

            this.setIcona(Lib.Risorse.getIconaBase("aiuto24"));
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


    }// fine del metodo inizia


    /**
     * Sovrascrive il metodo Inizializza.
     * <p/>
     * Non fa nulla
     * Questo oggetto non necessita di inizializzazione
     */
    public void inizializza() {
    }// fine del metodo inizializza


    /**
     * Azione esporta sul server.
     * </p>
     */
    private final class AzChiude extends AzAdapterWindow {

        /**
         * Costruttore senza parametri.
         */
        public AzChiude() {
            /* rimanda al costruttore della superclasse */
            super();

        }// fine del metodo costruttore senza parametri


        /**
         * actionPerformed, da ActionListener.
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void windowClosing(WindowEvent unEvento) {
            /* variabili e costanti locali di lavoro */
            MessaggioDialogo dialogo;

            try { // prova ad eseguire il codice

                dialogo = new MessaggioDialogo("Sei sicuro di voler uscire?");
                /* procede solo dopo ulteriore conferma esplicita */
                if (dialogo.getRisposta() == JOptionPane.YES_OPTION) {
                    Progetto.chiudeProgramma();
                }// fine del blocco if

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }

    } // fine della classe 'interna'

}// fine della classe
