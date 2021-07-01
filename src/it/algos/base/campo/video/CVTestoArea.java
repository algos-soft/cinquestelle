/**
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 5 luglio 2003 alle 14.57
 */
package it.algos.base.campo.video;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.wrapper.TestoAlgos;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;

/**
 * Componente video di tipo testo area.
 * </p>
 * Questa classe concreta: <ul>
 * <li> Crea il componente GUI specifico di questa classe </li>
 * <li> Aggiunge i Listener al componente specifico </li>
 * <li> Regola i font, la dimensione e la posizione del componente specifico </li>
 * <li> Regola le dimensioni del pannelloComponenti </li>
 * <li> Implementa i metodi astratti della superclasse per regolare le
 * conversioni tra i vari tipi di dati </li>
 * <li> L'attributo <i>video</i> del <code>CampoDati</code> associato,
 * <strong>deve</strong> essere di tipo testo </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  5 luglio 2003 ore 14.57
 */
public final class CVTestoArea extends CVTesto {

    /**
     * numero di righe di default in scheda
     */
    private static final int NUMERO_RIGHE_DEFAULT = 3;

    /**
     * larghezza di default in scheda
     */
    private static final int LARGHEZZA_DEFAULT = 250;

    /* scroll pane che contiene il campo testo editabile */ JScrollPane scrollPane = null;


    /**
     * Costruttore completo con parametri.
     *
     * @param unCampoParente campo 'contenitore' di questo oggetto
     */
    public CVTestoArea(Campo unCampoParente) {
        /* rimanda al costruttore della superclasse */
        super(unCampoParente);

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

        /* assegna il numero di righe di default */
        super.setNumeroRighe(NUMERO_RIGHE_DEFAULT);

        /* larghezza di default dei componenti interni al pannello componenti */
        this.getCampoParente().setLarScheda(LARGHEZZA_DEFAULT);

        /* creazione dei componenti */
        this.creaComponentiInterni();

    } /* fine del metodo inizia */


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

        super.avvia();

        try { // prova ad eseguire il codice

            /*
             * scrolla al top dell'area
             * accoda il comando dopo la fine della catena eventi AWT
             */
            final JTextComponent comp = this.getComponenteTesto();
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    comp.setCaretPosition(0);
                }
            });

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } /* fine del metodo */


    protected void regolaLarghezzaComponenti() {
        super.regolaLarghezzaComponenti();
    }


    protected void regolaAltezzaComponenti() {
        super.regolaAltezzaComponenti();
    }


    /**
     * Crea i componenti interni al pannelloComponenti.
     * <p/>
     * Metodo invocato dal ciclo inizia o inizializza, a seconda
     * delle esigenze del tipo specifico di campo <br>
     * Sovrascrive il metodo della superclasse <br>
     * E' responsabilita' di questo metodo:
     * - creare i componenti GUI interni al PannelloComponenti<br>
     * - eventualmente assegnare un layout al PannelloComponenti<br>
     * - aggiungere i componenti al pannelloComponenti<br>
     */
    public void creaComponentiInterni() {
        /* variabili e costanti locali di lavoro */
        JTextArea area;
        JScrollPane scrollPane;

        try { // prova ad eseguire il codice

            /* crea e registra il componente attivo (verso l'utente)
             * di tipo JTextArea */
            area = new JTextArea();

            /* Disabilita l'uso del tasto tab all'interno dell'area.
             * Il tab viene usato per trasferisce il fuoco
             * come fanno tutti gli altri componenti.
             * da JavaDoc: "If a value of null is specified for the Set, this Component
             * inherits the Set from its parent"  */
            area.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
            area.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);

            /* abilita il line wrap */
            area.setLineWrap(true);

            /* line wrap alla singola parola */
            area.setWrapStyleWord(true);

            /* numero di spazi del carattere tab nell'area */
            area.setTabSize(4);

            /* regola colore e font del componente testo area */
            TestoAlgos.setArea(area);

            /* registra il componente */
            this.setComponenteTesto(area);

            /* crea il componente principale di tipo scorrevole
             * contenente il componente di testo */
            scrollPane = new JScrollPane(this.getComponenteTesto());
            this.setScrollPane(scrollPane);

            /* registra il riferimento al componente principale */
            this.setComponente(scrollPane);

            /* rimanda al metodo della superclasse */
            super.creaComponentiInterni();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }

//    /**
//     * Aggiunge i <code>Listener</code>.
//     * <p/>
//     * Metodo invocato dal ciclo inizializza <br>
//     * Aggiunge ai componenti video di questo campo gli eventuali
//     * ascoltatori delle azioni (eventi) <br>
//     * Metodo invocato da SchedaBase.inizializza <br>
//     * Metodo sovrascritto nelle sottoclassi <br>
//     *
//     * @param portale il portale di riferimento del campo
//     *
//     * @see it.algos.base.scheda.SchedaBase#inizializza()
//     */
//    public void aggiungeListener(Portale portale) {
//        /* variabili e costanti locali di lavoro */
//        KeyListener unAzione;
//
//        try { // prova ad eseguire il codice
//            unAzione = portale.getAzKey(Azione.CARATTERE_TESTO_AREA);
//            this.getComponenteTesto().addKeyListener(unAzione);
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//    }


    private JScrollPane getScrollPane() {
        return scrollPane;
    }


    private void setScrollPane(JScrollPane scrollPane) {
        this.scrollPane = scrollPane;
    }


}// fine della classe