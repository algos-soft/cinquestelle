package it.algos.base.palette;

import it.algos.base.costante.CostanteColore;
import it.algos.base.errore.Errore;
import it.algos.base.layout.Layout;
import it.algos.base.libreria.Lib;
import it.algos.base.messaggio.MessaggioDialogo;
import it.algos.base.pannello.PannelloFlusso;
import it.algos.base.progetto.Progetto;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

/**
 * Barra del titolo della palette
 * <p/>
 */
class PaletteTitleBar extends PannelloFlusso {

    /* palette di riferimento */
    private Palette palette;

    /* orientamento corrente */
    private int orientamento;


    /**
     * Costruttore
     * <p/>
     *
     * @param palette di riferimento
     * @param orientamento Layout.HORIZONTAL o Layout.VERTICAL
     * Attenzione quando la palette è verticale la
     * titlebar è orizzontale e viceversa!
     */
    PaletteTitleBar(Palette palette, int orientamento) {
        /* rimanda al costruttore di questa classe */
        super(orientamento);

        this.setPalette(palette);
        this.setOrientamento(orientamento);

        try { // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo costruttore base


    /**
     * Regolazioni immediate di riferimenti e variabili. <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */
        JButton button;

        try { // prova ad eseguire il codice

            this.setBackground(CostanteColore.PALETTE_TITLEBAR);
            this.setOpaque(true);
            this.setUsaGapFisso(true);
            this.setGapPreferito(3);
            this.setAllineamento(Layout.ALLINEA_BASSO);
            this.setBorder(BorderFactory.createEmptyBorder(0, 0, 3, 0));

            int w = 40;
            int h = 36;
            if (this.getOrientamento() == Layout.ORIENTAMENTO_ORIZZONTALE) {
                this.setPreferredSize(new Dimension(w, h));
            } else {
                this.setPreferredSize(new Dimension(h, w));
            }// fine del blocco if-else


            /* aggiunge i mouse listeners a questo oggetto */
            this.addMouseListener(new MousePressed());
            this.addMouseMotionListener(new MouseMoved());

            /* aggiunge i componenti alla titlebar */
            this.add(Box.createHorizontalStrut(3));
            this.add(Box.createHorizontalGlue());

            button = this.creaBottone(
                    "minimize16",
                    "minimizeRollover16",
                    "minimizePressed16",
                    "minimizza",
                    new AzMinimize());
            this.add(button);

            button = this.creaBottone(
                    "closebox16",
                    "closeboxRollover16",
                    "closeboxPressed16",
                    "chiude il programma",
                    new AzClose());
            this.add(button);

            this.add(Box.createHorizontalStrut(3));

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


    }


    /**
     * Crea un bottone.
     * <p/>
     *
     * @param nomeIcona nome dell'icona base
     * @param nomeIconaRollover nome dell'icona rollover
     * @param noneIconaPressed nome dell'icona pressed
     * @param tooltip testo per il tooltip
     * @param azione da eseguire
     *
     * @return il bottone creato
     */
    private JButton creaBottone(
            String nomeIcona,
            String nomeIconaRollover,
            String noneIconaPressed,
            String tooltip,
            ActionListener azione) {
        /* variabili e costanti locali di lavoro */
        JButton button = null;
        Icon icona;

        try {    // prova ad eseguire il codice

            /* assegna le icone */
            icona = Lib.Risorse.getIconaBase(nomeIcona);
            button = new JButton(icona);
            icona = Lib.Risorse.getIconaBase(nomeIconaRollover);
            button.setRolloverIcon(icona);
            icona = Lib.Risorse.getIconaBase(noneIconaPressed);
            button.setPressedIcon(icona);

            /* assegna il tooltip */
            button.setToolTipText(tooltip);

            /* regola graficamente il bottone */
            button.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            button.setFocusable(false);

            /* aggiunge il listener */
            button.addActionListener(azione);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return button;
    }


    private Palette getPalette() {
        return palette;
    }


    private void setPalette(Palette palette) {
        this.palette = palette;
    }


    private int getOrientamento() {
        return orientamento;
    }


    private void setOrientamento(int orientamento) {
        this.orientamento = orientamento;
    }


    /**
     * Classe interna
     * <p/>
     * Gestore del clic del mouse
     */
    private final class MousePressed extends MouseAdapter {

        public void mousePressed(MouseEvent event) {

            /* memorizza le coordinate correnti */
            Point origin = getPalette().getOrigin();
            origin.x = event.getX();
            origin.y = event.getY();

        }
    } // fine della classe 'interna'

    /**
     * Classe interna
     * <p/>
     * Gestore del movimento del mouse
     */
    private final class MouseMoved extends MouseMotionAdapter {

        public void mouseDragged(MouseEvent event) {
            /* variabili e costanti locali di lavoro */
            Point p;
            JFrame frame = null;
            int newX;
            int newY;
            int minX;
            int minY;
            GraphicsEnvironment env;
            Rectangle bounds;

            try { // prova ad eseguire il codice

                /* recupera il frame proprietario */
                frame = getPalette();
                p = frame.getLocation();

                /* determina la nuova posizione */
                Point origin = getPalette().getOrigin();
                newX = p.x + event.getX() - origin.x;
                newY = p.y + event.getY() - origin.y;

                /* controlla i limiti */
                env = GraphicsEnvironment.getLocalGraphicsEnvironment();
                bounds = env.getMaximumWindowBounds();
                minX = bounds.getLocation().x;
                minY = bounds.getLocation().y;
                if (newX < minX) {
                    newX = minX;
                }// fine del blocco if
                if (newY < minY) {
                    newY = minY;
                }// fine del blocco if

                /* riposiziona il frame */
                frame.setLocation(newX, newY);

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }

    } // fine della classe 'interna'

    /**
     * Azione bottone close premuto
     * <p/>
     */
    private final class AzClose implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            /* variabili e costanti locali di lavoro */
            MessaggioDialogo dialogo;

            try { // prova ad eseguire il codice
                dialogo = new MessaggioDialogo("Sei sicuro di voler uscire?");
                if (dialogo.getRisposta() == JOptionPane.YES_OPTION) {
                    Progetto.chiudeProgramma();
                }// fine del blocco if
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

        }

    } // fine della classe 'interna'

    /**
     * Azione bottone minimizza premuto
     * <p/>
     */
    private final class AzMinimize implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            getPalette().setExtendedState(Frame.ICONIFIED);
        }
    } // fine della classe 'interna'


} // fine della classe 'interna'