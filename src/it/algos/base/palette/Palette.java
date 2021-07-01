package it.algos.base.palette;

import it.algos.base.azione.Azione;
import it.algos.base.azione.AzioneBase;
import it.algos.base.errore.Errore;
import it.algos.base.layout.Layout;
import it.algos.base.libreria.Lib;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

/**
 * Palette flottante contenente dei bottoni con relative azioni.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0 / 25-giu-2009
 */
public final class Palette extends JFrame {

    /**
     * barra del titolo della palette
     */
    private PaletteTitleBar titleBar;

    /**
     * toolbar contenente i bottoni
     */
    private PaletteToolBar toolBar;

    /**
     * posizione corrente sullo schermo
     */
    private Point origin = new Point();

    /**
     * lista delle azioni contenute
     */
    private ArrayList<Azione> azioni;

    /**
     * Orientamento corrente della palette
     * SwingConstants.VERTICAL o SwingConstants.HORIZONTAL
     */
    private int orientamento;

    /**
     * Larghezza dei bottoni
     */
    private int larBottoni;

    /**
     * Altezza dei bottoni
     */
    private int altBottoni;



    /**
     * Costruttore completo senza parametri.
     * <p/>
     */
    public Palette() {
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
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        try { // prova ad eseguire il codice

            /* orientamento di default */
            this.setOrientamento(SwingConstants.VERTICAL);

            /* dimensioni di default dei bottoni */
            this.setAltBottoni(40);
            this.setLarBottoni(40);

            /* regola questo frame */
            this.setUndecorated(true);
            this.setAlwaysOnTop(true);

            /* crea la lista vuota delle azioni */
            this.setAzioni(new ArrayList<Azione>());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizia


    /**
     * Regolazioni di ri-avvio.
     * <p/>
     * Metodo invocato da altre classi (o anche da questa) ogni volta che questo oggetto
     * deve <i>ripartire</i>, per essere sicuri che sia <i>pulito</i> <br>
     * Viene eseguito tutte le volte che necessita <br>
     * <p/>
     * Sovrascritto nelle sottoclassi <br>
     */
    public void avvia() {
        /* variabili e costanti locali di lavoro */
        int orientamentoFrame;
        int orientamentoTitlebar;
        int orientamentoToolbar;
        LayoutManager layout;

        try { // prova ad eseguire il codice

            /**
             * regola le variabili di orientamento dei componenti interni
             * in funzione dell'orientamento corrente della palette
             */
            if (this.getOrientamento() == SwingConstants.VERTICAL) {
                orientamentoFrame = BoxLayout.PAGE_AXIS;
                orientamentoTitlebar = Layout.ORIENTAMENTO_ORIZZONTALE;
                orientamentoToolbar = Layout.ORIENTAMENTO_VERTICALE;
            } else {
                orientamentoFrame = BoxLayout.LINE_AXIS;
                orientamentoTitlebar = Layout.ORIENTAMENTO_VERTICALE;
                orientamentoToolbar = Layout.ORIENTAMENTO_ORIZZONTALE;
            }// fine del blocco if-else

            /* regola il layout del frame */
            layout = new BoxLayout(this.getContentPane(), orientamentoFrame);
            this.getContentPane().setLayout(layout);

            /* crea e registra titlebar e toolbar */
            this.setTitleBar(new PaletteTitleBar(this, orientamentoTitlebar));
            this.setToolBar(new PaletteToolBar(this, orientamentoToolbar));

            /* crea i bottoni dalle azioni e li aggiunge alla toolbar */
            this.creaBottoni();

//            /* regola l'aspetto dei bottoni */
//            this.regolaBottoni();

            /* svuota il frame e aggiunge titlebar e toolbar al frame */
            this.getContentPane().removeAll();
            this.getContentPane().add(this.getTitleBar());
            this.getContentPane().add(this.getToolBar());

            /* impacchetta e rende visibile */
            this.pack();
            this.setVisible(true);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Regola il colore di sfondo
     * <p/>
     * Quando viene invocato rende anche la palette opaca
     *
     * @param c colore di sfondo
     */
    public void setBackground(Color c) {

        super.setBackground(c);

        PaletteToolBar toolbar = this.getToolBar();
        if (toolbar != null) {
            toolbar.setBackground(c);
        }
    }


    /**
     * Regola l'opacità dello sfondo della palette.
     * <p/>
     *
     * @param flag true se opaco
     */
    public void setOpaque(boolean flag) {
        try {    // prova ad eseguire il codice
            PaletteToolBar toolbar = this.getToolBar();
            if (toolbar != null) {
                toolbar.setOpaque(flag);
            }
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Crea i bottoni dalle azioni e li aggiunge alla toolbar.
     * <p/>
     */
    private void creaBottoni() {
        /* variabili e costanti locali di lavoro */
        PaletteBottone bot;

        try {    // prova ad eseguire il codice

            /**
             * Spazzola le azioni, per ognuna crea un bottone
             * e lo aggiunge alla palette
             */
            for (Azione azione : this.getAzioni()) {
                bot = new PaletteBottone(this, azione);
                this.getToolBar().add(bot);
            }

            /* crea e aggiunge il bottone di rotazione palette */
            bot = new PaletteBottone(this, new AzRuota());
            this.getToolBar().add(bot);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Ruota la palette.
     * <p/>
     * Se è verticale la rigenera orizzontale e viceversa
     */
    private void ruota() {
        /* variabili e costanti locali di lavoro */
        int newOrientamento;

        try {    // prova ad eseguire il codice

            /* deretmina il nuovo orientamento */
            if (this.getOrientamento() == SwingConstants.VERTICAL) {
                newOrientamento = SwingConstants.HORIZONTAL;
            } else {
                newOrientamento = SwingConstants.VERTICAL;
            }// fine del blocco if-else

            /* registra il nuovo orientamento */
            this.setOrientamento(newOrientamento);

            /* rigenera la palette */
            this.avvia();


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Aggiunge una azione alla palette.
     * <p/>
     * Crea un bottone contenente l'azione e lo aggiunge alla palette
     *
     * @param azione da aggiungere
     */
    public void addAzione(Azione azione) {
        this.getAzioni().add(azione);
    }


    private PaletteTitleBar getTitleBar() {
        return titleBar;
    }


    private void setTitleBar(PaletteTitleBar titleBar) {
        this.titleBar = titleBar;
    }


    private PaletteToolBar getToolBar() {
        return toolBar;
    }


    private void setToolBar(PaletteToolBar toolBar) {
        this.toolBar = toolBar;
    }


    Point getOrigin() {
        return origin;
    }


    void setOrigin(Point origin) {
        this.origin = origin;
    }


    private ArrayList<Azione> getAzioni() {
        return azioni;
    }


    private void setAzioni(ArrayList<Azione> azioni) {
        this.azioni = azioni;
    }


    private int getOrientamento() {
        return orientamento;
    }


    private void setOrientamento(int orientamento) {
        this.orientamento = orientamento;
    }


    int getLarBottoni() {
        return larBottoni;
    }

    public void setLarBottoni(int larBottoni) {
        this.larBottoni = larBottoni;
    }

    int getAltBottoni() {
        return altBottoni;
    }

    public void setAltBottoni(int altBottoni) {
        this.altBottoni = altBottoni;
    }

    /**
     * Classe 'interna'.
     * </p>
     * Azione associata al bottone ruota palette
     */
    private class AzRuota extends AzioneBase {

        public AzRuota() {
            /* rimanda al costruttore della superclasse */
            super();
            Icon icona = Lib.Risorse.getIconaBase("rotazione24");
            this.setIconaPiccola(icona);
            this.setIconaMedia(icona);
            this.setIconaGrande(icona);
            this.setTooltip("ruota la palette orizzontale / verticale");
        }// fine del metodo costruttore completo


        @Override
        public void actionPerformed(ActionEvent unEvento) {
            ruota();
        }
    }


}// fine della classe