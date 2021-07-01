/**
 * Title:     StatusBar
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      21-feb-2005
 */
package it.algos.base.scheda;

import it.algos.base.errore.Errore;
import it.algos.base.layout.Layout;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.LibComponente;
import it.algos.base.lista.Lista;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFlusso;
import it.algos.base.portale.Portale;
import it.algos.base.wrapper.TestoAlgos;

import javax.swing.*;
import java.awt.*;

/**
 * Barra di stato informativa della lista.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 21-feb-2005 ore 10.30.30
 */
public final class SchedaStatusBar extends PannelloFlusso {

    /**
     * Colore di sfondo per la status bar
     */
    private static final Color SFONDO_BAR = Color.LIGHT_GRAY;

    /**
     * Colore per il segnale di modificato acceso
     */
    private static final Color MODIFICATO = Color.RED;

    /* codifica spia rossa */
    public static final int SPIA_ROSSA = 1;

    /* codifica spia verde */
    public static final int SPIA_VERDE = 2;

    /* codifica spia gialla */
    public static final int SPIA_GIALLA = 3;


    /**
     * Scheda di riferimento.
     * <p/>
     */
    private Scheda scheda = null;

    /**
     * Componente per il display
     * dei records visibili e disponibili.
     * <p/>
     */
    private JLabel infoRecords = null;

    /**
     * Componente per il display
     * dello stato navigatore modificato.
     * <p/>
     */
    private JPanel infoModificato = null;

    /**
     * Icona spia rossa
     */
    private ImageIcon spiaRossa = null;

    /**
     * Icona spia verde
     */
    private ImageIcon spiaVerde = null;

    /**
     * Icona spia gialla
     */
    private ImageIcon spiaGialla = null;

    /**
     * JLabel contenente l'icona
     */
    private JLabel labelIcona = null;

    /**
     * pannello placeholder per il componente di riferimento
     * variabile fornito dalla scheda
     */
    private Pannello placeholderRif;


    /**
     * Costruttore base senza parametri.
     * <p/>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo <br>
     * Utilizza eventuali valori di default <br>
     */
    public SchedaStatusBar() {
        /* rimanda al costruttore di questa classe */
        this(null);
    }// fine del metodo costruttore base


    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param scheda la scheda di riferimento
     */
    public SchedaStatusBar(Scheda scheda) {
        /* rimanda al costruttore della superclasse */
        super(Layout.ORIENTAMENTO_ORIZZONTALE);

        /* regola le variabili di istanza coi parametri */
        this.setScheda(scheda);

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
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */
        JLabel label = null;
        JPanel pan = null;
        Pannello placeholder;
        ImageIcon icona = null;

        try { // prova ad eseguire il codice

            /* regolazioni del layout */
            this.setUsaGapFisso(true);
            this.setGapPreferito(0);
            this.setRidimensionaParallelo(true);
            this.setRidimensionaPerpendicolare(true);

            /* regola allineamento verticale */
            this.setAllineamento(Layout.ALLINEA_CENTRO);

            /* carica le icone */
            this.caricaIcone();

            /* crea la JLabel per contenere l'icona di controllo modificato */
            this.setLabelIcona(new JLabel());
            this.getLabelIcona().setText("");
            this.getLabelIcona().setHorizontalAlignment(JLabel.CENTER);
            this.getLabelIcona().setVerticalAlignment(JLabel.CENTER);
            this.getLabelIcona().setOpaque(false);

            this.setBorder(BorderFactory.createEmptyBorder(2, 4, 2, 4));
            this.setBackground(SFONDO_BAR);
            this.setOpaque(true);

            /* crea e regola il componente per il numero di records */
            label = new JLabel();
            label.setBackground(SFONDO_BAR);
            label.setOpaque(true);
            /* regolazione del font */
            TestoAlgos.setLegenda(label);
            /* assegna subito un testo per regolare l'altezza del componente */
            label.setText(" ");
            Lib.Comp.sbloccaLarMax(label);
            this.setInfoRecords(label);

            /* crea il pannello placeholder per il riferimento */
            placeholder = new PannelloFlusso(Layout.ORIENTAMENTO_VERTICALE);
            placeholder.setAllineamento(Layout.ALLINEA_CENTRO);
            placeholder.sbloccaLarMax(); //permette al pannello di espandersi in orizzontale
            this.setPlaceholderRif(placeholder);

            /* crea e regola il componente per l'indicatore modificato */
            pan = new JPanel();
            pan.setOpaque(false);
            this.setColoreSpia(SPIA_VERDE); // regola contenuto iniziale
            pan.add(this.getLabelIcona());
            pan.setMinimumSize(pan.getPreferredSize());
            pan.setMaximumSize(pan.getPreferredSize());
            this.add(pan);

            this.setInfoModificato(pan);

            /* aggiunge i componenti */
            this.add(this.getInfoRecords());
            this.add(this.getPlaceholderRif());
            this.add(this.getInfoModificato());


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizia


    /**
     * Regolazioni di ri-avvio.
     * <p/>
     * Metodo chiamato da altre classi (o anche da questa) ogni volta che questo oggetto
     * deve <i>ripartire</i>, per essere sicuri che sia <i>pulito</i> <br>
     * Viene eseguito tutte le volte che necessita <br>
     * <p/>
     * Sovrascritto nelle sottoclassi <br>
     */
    public void avvia() {
    }


    /**
     * Sincronizzazione della status bar.
     * <p/>
     * Chiamato dalla sincronizzazione della lista.<br>
     * Aggiorna il display dei numeri records.
     */
    public void sincronizza() {
        /* variabili e costanti locali di lavoro */
        boolean continua = true;
        Navigatore nav = null;
        Scheda scheda = null;
        Lista lista = null;
        int quanti = 0;
        int indice = 0;
        String testo = "";
        JComponent comp;
        Pannello placeholder;
        JLabel infoRecords;


        try { // prova ad eseguire il codice

            if (continua) {
                scheda = this.getScheda();
                continua = (scheda != null);
            }// fine del blocco if

            if (continua) {
                nav = this.getNavigatore();
                continua = (nav != null);
            }// fine del blocco if

            if (continua) {
                lista = nav.getLista();
                continua = (lista != null);
            }// fine del blocco if

            if (continua) {

                /* regola il colore della spia "controllo scheda registrabile" */
                if (scheda.isValida()) {
                    if (scheda.isModificata()) {
                        this.setColoreSpia(SPIA_GIALLA); // ho modificato e posso registrare
                    } else {
                        this.setColoreSpia(SPIA_VERDE); // non ho modificato nulla
                    }// fine del blocco if-else
                } else {
                    this.setColoreSpia(SPIA_ROSSA); // la scheda non è valida, non posso registrare
                }// fine del blocco if-else

                /**
                 * richiede alla scheda il componente di riferimento
                 * e lo sostituisce nel pannello placeholder
                 */
                placeholder = this.getPlaceholderRif();
                placeholder.removeAll();
                comp = this.getCompRiferimento();
                if (comp != null) {

                    /* fissa la dimensione del componente */
                    LibComponente.bloccaDim(comp);

                    /* pone la dimensione del placeholder pari a quella del componente */
                    placeholder.getPanFisso().setPreferredSize(comp.getPreferredSize());

                    /* inserisce il componente nel placeholder */
                    placeholder.add(comp);

                    /* blocca la dimensione del placeholder e
                     * la sblocca solo in orizzontale */
                    placeholder.bloccaDim();
                    placeholder.sbloccaLarMax();

                    /* ridisegna il placeholder */
                    placeholder.getPanFisso().repaint();

                }// fine del blocco if

                /* regola l'indicatore della posizione del record nella selezione */
                if (lista != null) {
                    quanti = lista.getNumRecordsVisualizzati();
                    if (quanti > 0) {
                        indice = lista.getPosizione(scheda.getCodice());
                        if (indice != -1) {
                            testo = (indice + 1) + " di " + quanti;
                        }// fine del blocco if-else
                    }// fine del blocco if
                }// fine del blocco if

                /* regola il testo e fissa la larghezza */
                infoRecords = this.getInfoRecords();
                infoRecords.setText(testo);
                Lib.Comp.bloccaLarMax(infoRecords);

                /* ridisegna il contenitore */
                this.validate();

            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Carica le icone  dalle risorse nelle variabili della classe.
     * <p/>
     */
    private void caricaIcone() {
        try {    // prova ad eseguire il codice
            this.spiaRossa = Lib.Risorse.getIconaBase("spiaRossa");
            this.spiaVerde = Lib.Risorse.getIconaBase("spiaVerde");
            this.spiaGialla = Lib.Risorse.getIconaBase("spiaGialla");
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Regola il colore della spia Modificato .
     * <p/>
     *
     * @param colore il colore della spia (costanti in questa classe)
     */
    private void setColoreSpiaModificato(int colore) {
        /* variabili e costanti locali di lavoro */
        ImageIcon icona = null;

        try {    // prova ad eseguire il codice
            /*  */
            switch (colore) {
                case SPIA_ROSSA:
                    icona = this.spiaRossa;
                    break;
                case SPIA_VERDE:
                    icona = this.spiaVerde;
                    break;
                case SPIA_GIALLA:
                    icona = this.spiaGialla;
                    break;
                default: // caso non definito
                    break;
            } // fine del blocco switch

            /* regola l'icona della JLabel*/
            this.getLabelIcona().setIcon(icona);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Recupera il componente di riferimento dalla scheda.
     * <p/>
     *
     * @return il componente di riferimento fornito dalla scheda
     */
    private JComponent getCompRiferimento() {
        /* variabili e costanti locali di lavoro */
        JComponent comp = null;
        SchedaBase scheda;

        try {    // prova ad eseguire il codice
            scheda = (SchedaBase)this.getScheda();
            if (scheda != null) {
                comp = scheda.getCompSB();
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return comp;
    }


    /**
     * Ritorna la lista proprietaria di questa Status Bar.
     * <p/>
     *
     * @return la lista proprietaria
     */
    public Scheda getScheda() {
        return scheda;
    }


    private void setScheda(Scheda scheda) {
        this.scheda = scheda;
    }


    private JLabel getInfoRecords() {
        return infoRecords;
    }


    private void setInfoRecords(JLabel infoRecords) {
        this.infoRecords = infoRecords;
    }


    private JPanel getInfoModificato() {
        return infoModificato;
    }


    private void setInfoModificato(JPanel infoModificato) {
        this.infoModificato = infoModificato;
    }


    private JLabel getLabelIcona() {
        return labelIcona;
    }


    private void setLabelIcona(JLabel labelIcona) {
        this.labelIcona = labelIcona;
    }


    private Pannello getPlaceholderRif() {
        return placeholderRif;
    }


    private void setPlaceholderRif(Pannello placeholderRif) {
        this.placeholderRif = placeholderRif;
    }


    /**
     * Recupera una spia.
     * <p/>
     *
     * @param codice il codice della spia
     *
     * @return l'icona della spia
     */
    private ImageIcon getSpia(int codice) {
        /* variabili e costanti locali di lavoro */
        ImageIcon icona = null;

        try {    // prova ad eseguire il codice
            switch (codice) {
                case SPIA_ROSSA:
                    icona = this.spiaRossa;
                    break;
                case SPIA_VERDE:
                    icona = this.spiaVerde;
                    break;
                case SPIA_GIALLA:
                    icona = this.spiaGialla;
                    break;
                default: // caso non definito
                    break;
            } // fine del blocco switch

            ;
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return icona;
    }


    /**
     * Regola il colore della spia modificato.
     * <p/>
     *
     * @param codice il codice del colore
     */
    public void setColoreSpia(int codice) {
        /* variabili e costanti locali di lavoro */
        ImageIcon icona = null;

        try {    // prova ad eseguire il codice
            icona = this.getSpia(codice);
            this.getLabelIcona().setIcon(icona);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Ritorna il navigatore che gestisce la lista.
     * <p/>
     *
     * @return il navigatore che gestisce la lista
     */
    private Navigatore getNavigatore() {
        /* variabili e costanti locali di lavoro */
        Navigatore nav = null;
        Portale portale = null;
        Scheda scheda = null;

        try {    // prova ad eseguire il codice
            scheda = this.getScheda();
            if (scheda != null) {
                portale = scheda.getPortale();
                if (portale != null) {
                    nav = portale.getNavigatore();
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return nav;
    }


}// fine della classe
