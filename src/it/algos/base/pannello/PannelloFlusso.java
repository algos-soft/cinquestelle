/**
 * Title:        PannelloNew.java
 * Package:      it.algos.base.pannello
 * Description:
 * Copyright:    Copyright (c) 2005
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /
 * Creato:       il 20 agosto 2005 alle 13.29
 */

package it.algos.base.pannello;

import it.algos.base.bottone.BottoneAzione;
import it.algos.base.bottone.BottoneFactory;
import it.algos.base.errore.Errore;
import it.algos.base.interfaccia.ContenitoreCampi;
import it.algos.base.layout.Layout;
import it.algos.base.layout.LayoutFlusso;

import javax.swing.*;
import java.awt.*;


/**
 * Implementazione di un Pannello con layout che dispone i componenti
 * in un flusso orizzontale o verticale.
 * <p/>
 * Il pannello viene costruito con un parametro di orientamento che specifica
 * il verso di disposizione dei componenti, che puo' essere orizzontale o
 * verticale.
 * <br>
 * I componenti possono essere separati da un gap fisso o variabile.
 * <br>
 * In caso di gap variabile, e' possibile regolare la misura preferita,
 * minima e massima del gap. Se il gap e' fisso, usa la misura del gap preferito.
 * <br>
 * Il pannello puo' ridimensionare i componenti oppure usare la loro
 * dimensione preferita.
 * <br>
 * Se ridimensiona i componenti, e' possibile specificare se i componenti
 * vanno ridimensionati in verso parallelo al layout, in verso perpendicolare, o in
 * entrambi i versi (se l'orientamento e' orizzontale, il verso parallelo e' quello
 * orizzontale e il verso perpendicolare e' quello verticale, e viceversa).
 * <br>
 * Nel dimensionare i componenti vengono rispettate la minimumSize e la maximumSize.
 * <br>
 * Regolando MinimumSize e MaximumSize di un componente pari alla preferredSize
 * e' possibile evitare il ridimensionamento di un particolare componente anche
 * se il pannello e' impostato per ridimensionare i componenti.
 * <br>
 * Se il pannello non ridimensiona i componenti (in uno o entrambi i versi), ogni
 * componente viene disegnato sempre alla propria preferredSize (per i versi che
 * non ridimensionano).
 * <br>
 * Per il verso perpendicolare a quello del layout e' possibile specificare il tipo
 * di allineamento dei componenti. Puo' essere in alto/a sinistra, in basso/a destra,
 * centrato, o rispettare la caratteristica aligmentX/alignmentY di ogni componente
 * (0=alto/sinistra, 1=basso/destra, 0.5=centro, i valori intermedi in proporzione).
 * <br>
 * Quando i componenti vengono disposti, se questi non ci stanno nel contenitore
 * alla loro dimensione minima, puo' essere inserito automaticamente uno scorrevole.
 * <br>
 * Lo scorrevole puo' essere visualizzato con bordo o senza bordo, se e' senza
 * bordo vengono visualizzate solo le barre di scorrimento.
 * <br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /  20 agosto 2005 ore 13.29
 */
public class PannelloFlusso extends PannelloBase {

    /**
     * orientamento di default
     */
    private static final int ORIENTAMENTO_DEFAULT = Layout.ORIENTAMENTO_VERTICALE;


    /**
     * Costruttore senza parametri.
     * <p/>
     */
    public PannelloFlusso() {
        /* rimanda al costruttore di questa classe */
        this(ORIENTAMENTO_DEFAULT);
    }


    /**
     * Costruttore con orientamento.
     * <p/>
     *
     * @param orientamento codice dell'orientamento del layout
     * Layout.ORIENTAMENTO_ORIZZONTALE o Layout.ORIENTAMENTO_VERTICALE
     */
    public PannelloFlusso(int orientamento) {
        /* rimanda al costruttore di questa classe */
        this(null, orientamento);
    }


    /**
     * Costruttore con contenitore campi.
     * <p/>
     * Usa l'orientamento di default.
     *
     * @param cont il contenitore di campi di riferimento
     *
     * @see javax.swing.BoxLayout
     */
    public PannelloFlusso(ContenitoreCampi cont) {
        /* rimanda al costruttore di questa classe */
        this(cont, ORIENTAMENTO_DEFAULT);
    }


    /**
     * Costruttore completo.
     * <p/>
     *
     * @param cont il contenitore di campi di riferimento
     * @param orientamento codice dell'orientamento del layout
     * ORIENTAMENTO_ORIZZONTALE o ORIENTAMENTO_VERTICALE
     *
     * @see javax.swing.BoxLayout
     */
    public PannelloFlusso(ContenitoreCampi cont, int orientamento) {
        /* rimanda al costruttore della superclasse */
        super(cont);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia(orientamento);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @param orientamento codice dell'orientamento del layout
     * ORIENTAMENTO_ORIZZONTALE o ORIENTAMENTO_VERTICALE
     *
     * @throws Exception unaEccezione
     */
    private void inizia(int orientamento) throws Exception {
        /* variabili e costanti locali di lavoro */
        Layout layout;

        try { // prova ad eseguire il codice

            /* crea e regola un'istanza di layoutFlusso */
            layout = new LayoutFlusso(this, orientamento);
            this.setLayout(layout);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Aggiunge un componente.
     * <p/>
     * Sovrascrive il metodo, per aggiungere eventualmente
     * un filler prima del componente.
     */
    public Component add(Component comp) {
        this.inserisciFiller(comp);
        return super.add(comp);
    }


    /**
     * Aggiunge un componente.
     * <p/>
     * Sovrascrive il metodo, per aggiungere eventualmente
     * un filler prima del componente.
     */
    public Component add(String name, Component comp) {
        this.inserisciFiller(comp);
        return super.add(name, comp);
    }


    /**
     * Aggiunge un componente a una data posizione.
     * <p/>
     * Non inserisce filler
     */
    public Component add(Component comp, int index) {
        /* variabili e costanti locali di lavoro */
        Component componente;

//        this.inserisciFiller(comp);
//        return super.add(comp, index);

        componente = super.add(comp, index);
        this.inserisciFiller(index + 1);

        /* valore di ritorno */
        return componente;
    }


    /**
     * Aggiunge un componente.
     * <p/>
     * Sovrascrive il metodo, per aggiungere eventualmente
     * un filler prima del componente.
     */
    public void add(Component comp, Object constraints) {
        this.inserisciFiller(comp);
        super.add(comp, constraints);
    }


    /**
     * Aggiunge un componente.
     * <p/>
     * Sovrascrive il metodo, per aggiungere eventualmente
     * un filler prima del componente.
     */
    public void add(Component comp, Object constraints, int index) {

//        this.inserisciFiller(comp);
//        super.add(comp, constraints, index);

        super.add(comp, constraints, index);
        this.inserisciFiller(index + 1);

    }


    /**
     * Aggiunge un componente senza modificarlo.
     * <p/>
     * Non modifica dimensioni e allineamento.<br>
     * Non aggiunge filler.<br>
     *
     * @param comp il componente da aggiungere
     */
    public void addOriginale(Component comp) {
        /* spegne il flag, aggiunge il componente e riaccende il flag */
        this.getLayoutAlgos().setRegolaComponenti(false);
        this.add(comp);
        this.getLayoutAlgos().setRegolaComponenti(true);
    }


    /**
     * Rimuove un componente.
     * <p/>
     * Rimuove anche l'eventuale filler successivo.<br>
     *
     * @param comp il componente da rimuovere
     */
    public void remove(Component comp) {
        Component[] componenti;
        Component c;
        Component f;
        Component filler = null;
        int posComp = -1;

        try { // prova ad eseguire il codice

            /* determina la posizione del componente */
            componenti = this.getComponents();
            for (int k = 0; k < componenti.length; k++) {
                c = componenti[k];
                if (c.equals(comp)) {
                    posComp = k;
                    break;
                }// fine del blocco if
            } // fine del ciclo for

            /* recupera l'eventuale filler successivo */
            if (posComp >= 0) {
                if (this.getComponentCount() > posComp + 1) {
                    f = this.getComponent(posComp + 1);
                    if (f instanceof Box.Filler) {
                        filler = f;
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

            /* rimuove il componente piu' l'eventuale filler */
            super.remove(comp);
            if (filler != null) {
                super.remove(filler);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }

//    /**
//     * Aggiunge un oggetto generico al pannello.
//     * <p/>
//     * Se si tratta di un Component, lo aggiunge come Component,
//     * altrimenti non fa nulla.
//     *
//     * @param oggetto oggetti da disporre in un pannello; puo' essere:
//     *                Component - un singolo componente (Campo od altro)
//     *                String - un nome set, un nome di campo singolo, una lista di nomi
//     *                ArrayList - di nomi, di oggetti Campo, di componenti <br>
//     */
//    public void add(Object oggetto) {
//        Component comp = null;
//        if (oggetto != null) {
//            if (oggetto instanceof Component) {
//                comp = (Component)oggetto;
//                this.add(comp);
//            }// fine del blocco if
//        }// fine del blocco if
//    }


    /**
     * Inserisce un filler prima di un componente.
     * <p/>
     * - Il filler viene inserito solo se esistono gia' dei componenti
     * nel pannello e l'ultimo componente non e' a sua volta un filler. <br>
     * - Il filler viene ottenuto dal layout di questo pannello che lo rende con
     * le impostazioni gia' regolate. <br>
     * - Se il componente che sta per essere inserito e' un filler, non fa nulla.<br>
     * ATTENZIONE! L'inserimento del filler deve essere fatto dal pannello e non
     * dal layout, perche' il pannello contiene gia' il componente e il layout no,
     * e in questa condizione dal layout non si riesce a inserire il filler nella
     * posizione desiderata.<br>
     *
     * @param comp il componente che sta per essere inserito, prima del quale
     * va eventualmente inserito il filler
     */
    private void inserisciFiller(Component comp) {
        /* variabili e costanti locali di lavoro */
        Component ultimoComp = null;
        Component filler;
        int quanti = 0;
        LayoutManager lay = null;
        Layout layout = null;
        boolean continua;

        try {    // prova ad eseguire il codice

            /* non esegue se il componente e' un filler */
            continua = !(comp instanceof Box.Filler);

            /* ci devono essere gia' dei componenti inseriti */
            if (continua) {
                quanti = this.getComponentCount();
                continua = quanti > 0;
            }// fine del blocco if

            /* l'ultimo componente non deve essere nullo */
            if (continua) {
                ultimoComp = this.getComponent(quanti - 1);
                continua = (ultimoComp != null);
            }// fine del blocco if

            /* l'ultimo componente non deve essere un filler */
            if (continua) {
                continua = !(ultimoComp instanceof Box.Filler);
            }// fine del blocco if

            /* il layout deve essere di interfaccia Layout */
            if (continua) {
                lay = this.getLayout();
                continua = (lay instanceof Layout);
            }// fine del blocco if

            /* il flag deve essere attivato */
            if (continua) {
                layout = (Layout)lay;
                continua = layout.isRegolaComponenti();
            }// fine del blocco if

            /* esegue */
            if (continua) {
                filler = layout.getNewFiller();
                this.add(filler);
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Inserisce un filler a una data posizione.
     * <p/>
     * - Il filler viene ottenuto dal layout di questo pannello che lo rende con
     * le impostazioni gia' regolate. <br>
     *
     * @param index la posizione alla quale inserire il filler
     */
    private void inserisciFiller(int index) {
        /* variabili e costanti locali di lavoro */
        Component filler;
        LayoutManager lay = null;
        Layout layout = null;
        boolean continua = true;

        try {    // prova ad eseguire il codice

            /* il layout deve essere di interfaccia Layout */
            if (continua) {
                lay = this.getLayout();
                continua = (lay instanceof Layout);
            }// fine del blocco if

            /* il flag deve essere attivato */
            if (continua) {
                layout = (Layout)lay;
                continua = layout.isRegolaComponenti();
            }// fine del blocco if

            /* esegue nella superclasse per non ricadere
             * nello stesso metodo chiamante di questa classe */
            if (continua) {
                filler = layout.getNewFiller();
                super.add(filler, index);
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Crea un bottone.
     * <p/>
     * Crea il bottone, inizialmente disabilitatao <br>
     * Regola il toolTiptext <br>
     *
     * @param metodo associato all'evento
     * @param icona del bottone
     * @param toolTipText associato al bottone
     *
     * @return bottone appena creato
     */
    protected BottoneAzione getBottone(String metodo, String icona, String toolTipText) {
        /* variabili e costanti locali di lavoro */
        BottoneAzione bot = null;

        try { // prova ad eseguire il codice
            /* crea il bottone */
            bot = BottoneFactory.creaIcona(this, metodo, icona);
            bot.setToolTipText(toolTipText);
            bot.setEnabled(false);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return bot;
    }


}// fine della classe