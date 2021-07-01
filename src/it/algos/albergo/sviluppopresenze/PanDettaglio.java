/**
 * Title:     PanDettaglio
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      9-feb-2009
 */
package it.algos.albergo.sviluppopresenze;

import it.algos.base.errore.Errore;
import it.algos.base.layout.Layout;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFactory;
import it.algos.base.pannello.PannelloFlusso;
import it.algos.base.portale.Portale;
import it.algos.base.progressbar.ProgressBar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Pannello per la presentazione del dettaglio dei risultati della ricerca
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 9-feb-2009 ore 12.38.23
 */
class PanDettaglio extends PanDialogo {

    /* combo box per la selezione del tipo di suddivisione del risultato */
    private JComboBox comboSudd;

    /* Progress bar della operazione */
    private ProgressBar progressBar;



    /**
     * Costruttore completo con parametri. <br>
     *
     * @param dialogo dialogo di riferimento
     */
    public PanDettaglio(SviluppoDialogo dialogo) {
        /* rimanda al costruttore della superclasse */
        super(dialogo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        try { // prova ad eseguire il codice

            this.setUsaGapFisso(true);
            this.setGapPreferito(10);
            this.creaBordo("Presenze sviluppate");

            /* aggiunge un pannello con il combo box di scelta suddivisione */
            this.add(this.creaPanCombo());

            /* aggiunge la JTable di dettaglio */
            this.add(this.creaPanTavola());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


    }// fine del metodo inizia


    /**
     * Crea il pannelllo con il combo box di scelta suddivisione.
     * <p/>
     *
     * @return il pannello creato
     */
    private Pannello creaPanCombo() {
        /* variabili e costanti locali di lavoro */
        Pannello pan = null;

        try { // prova ad eseguire il codice

            pan = PannelloFactory.orizzontale(null);
            pan.setAllineamento(Layout.ALLINEA_CENTRO);
            pan.setUsaGapFisso(true);
            pan.setGapPreferito(5);

            JComboBox combo = new JComboBox(this.creaSuddivisioni());
            this.setComboSudd(combo);
//            combo.addActionListener(new ComboListener());
//            combo.addItemListener(new ComboItemListener());

            combo.setOpaque(false);
            pan.add(new JLabel("Suddivisione"));
            pan.add(combo);

            /* crea il bottone Esegui */
            JButton bot = new JButton("Esegui");
            bot.addActionListener(new AzBottoneEsegui());
            bot.setOpaque(false);
            String tooltip = "Esegue l'analisi in base alle condizioni specificate";
            bot.setToolTipText(tooltip);

            pan.add(bot);

            /* crea la progress bar */
            ProgressBar pb = new ProgressBar();
            this.setProgressBar(pb);
            pan.add(pb);

            pan.bloccaAltMax();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return pan;

    }


    /**
     * Crea il pannelllo con la JTable completa di totali.
     * <p/>
     *
     * @return il pannello creato
     */
    private Pannello creaPanTavola() {
        /* variabili e costanti locali di lavoro */
        Pannello pan = null;

        try {    // prova ad eseguire il codice

            /* crea il pannello */
            pan = new PannelloFlusso(Layout.ORIENTAMENTO_VERTICALE);
            pan.setUsaGapFisso(true);
            pan.setGapPreferito(0);

            /**
             * Aggiunge la lista dei risultati
             */
            ModuloRisultati mod = this.getDialogoMain().getModuloRisultati();
            Navigatore nav = mod.getNavigatoreDefault();
            Portale por = nav.getPortaleLista();
            pan.add(por);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return pan;
    }


    /**
     * Crea un elenco delle possibili suddivisioni.
     * <p/>
     *
     * @return l'elenco creato
     */
    private Suddivisione[] creaSuddivisioni() {
        /* variabili e costanti locali di lavoro */
        ArrayList<Suddivisione> lista = new ArrayList<Suddivisione>();

        lista.add(new Sudd_Canale());
        lista.add(new Sudd_Trattamento());
        lista.add(new Sudd_Camera());
        lista.add(new Sudd_Provincia());
        lista.add(new Sudd_Nazione());
        // per città
        // per provincia
        // per nazione
        // per giorno della settimana (?)

        lista.add(new Sudd_Giornaliera());
        lista.add(new Sudd_Settimanale());
        lista.add(new Sudd_Mensile());
        // mensile

        /* converte in array */
        Suddivisione[] matrice = new Suddivisione[lista.size()];
        for (int k = 0; k < lista.size(); k++) {
            matrice[k] = lista.get(k);
        } // fine del ciclo for

        /* valore di ritorno */
        return matrice;
    }


    /**
     * Ritorna il tipo di suddivisione correntemente selezionato.
     * <p/>
     *
     * @return il tipo di suddivisione selezionato
     */
    public Suddivisione getSuddivisione() {
        /* variabili e costanti locali di lavoro */
        Suddivisione suddivisione = null;
        Object ogg;
        JComboBox combo;

        try {    // prova ad eseguire il codice
            combo = this.getComboSudd();
            ogg = combo.getSelectedItem();
            if ((ogg != null) && (ogg instanceof Suddivisione)) {
                suddivisione = (Suddivisione)ogg;
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return suddivisione;
    }


    private JComboBox getComboSudd() {
        return comboSudd;
    }


    private void setComboSudd(JComboBox combo) {
        this.comboSudd = combo;
    }


    public ProgressBar getProgressBar() {
        return progressBar;
    }


    private void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }


    /**
     * Action listener del bottone Esegui
     */
    private final class AzBottoneEsegui implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            getDialogoMain().eseguiAnalisi();
        }
    } // fine della classe 'interna'





}// fine della classe