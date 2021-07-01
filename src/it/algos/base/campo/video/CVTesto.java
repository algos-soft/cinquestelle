/**
 * Copyright:    Copyright (c) 2005
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 30 luglio 2005 alle 18.39
 */
package it.algos.base.campo.video;

import it.algos.base.azione.Azione;
import it.algos.base.campo.base.Campo;
import it.algos.base.costante.CostanteColore;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.LibFont;
import it.algos.base.pannello.Pannello;
import it.algos.base.portale.Portale;
import it.algos.base.progetto.Progetto;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.text.JTextComponent;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 * Componente video di tipo testo editabile.
 * </p>
 * Superclasse di CVTestoField e CVTestoArea<BR>
 * Questa classe astratta: <ul>
 * <li> Crea il componente GUI specifico di questa classe </li>
 * <li> Aggiunge i Listener al componente specifico </li>
 * <li> Regola i font, la dimensione e la posizione del componente specifico </li>
 * <li> Implementa i metodi astratti della superclasse per regolare le
 * conversioni tra i vari tipi di dati </li>
 * <li> L'attributo <i>video</i> del <code>CampoDati</code> associato,
 * <strong>deve</strong> essere di tipo testo </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  3 luglio 2003 ore 23.53
 */
public abstract class CVTesto extends CVBase {

    /**
     * Colore di sfondo per il campo abilitato
     */
    public static final Color COLORE_SFONDO_ABILITATO = CostanteColore.BIANCO_SPORCO;

    /**
     * Colore di sfondo per il campo disabilitato
     */
    public static final Color COLORE_SFONDO_DISABILITATO = CostanteColore.BIANCO_GRIGIO;

    /* componente di testo direttamente accessibile dall'utente */ JTextComponent componenteTesto =
            null;


    /**
     * Costruttore completo con parametri.
     *
     * @param unCampoParente campo 'contenitore' di questo oggetto
     */
    public CVTesto(Campo unCampoParente) {
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

        try { // prova ad eseguire il codice

            /* normalmente questo campo ha una sola riga */
            this.setNumeroRighe(1);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


    }


    /**
     * Regolazioni dinamiche dell'oggetto.
     * <p/>
     * Regola le caratteristiche dinamiche in base alle impostazioni
     * correnti delle caratteristiche statiche <br>
     * Metodo invocato dalla classe che crea questo oggetto (di norma) <br>
     * Viene eseguito una sola volta <br>
     * Sovrascritto nelle sottoclassi <br>
     */
    public void inizializza() {
        super.inizializza();
    }


    /**
     * Crea i componenti interni al pannelloComponenti.
     * <p/>
     * Metodo invocato dal ciclo inizia o inizializza o avvia, a seconda
     * delle esigenze del tipo specifico di campo <br>
     * Metodo sovrascritto dalle sottoclassi <br>
     * E' responsabilita' di questo metodo:
     * - creare i componenti GUI interni al PannelloComponenti<br>
     * - aggiungere i listener ai componenti GUI
     * - eventualmente assegnare un layout al PannelloComponenti<br>
     * - aggiungere i componenti al pannelloComponenti<br>
     */
    public void creaComponentiInterni() {
        /* variabili e costanti locali di lavoro */
        JTextComponent compEdit;
        JComponent compMain;
        Pannello pc;

        try {    // prova ad eseguire il codice

            /* recupera il componente direttamente accessibile dall'utente */
            compEdit = this.getComponenteTesto();

            /* recupera il componente principale */
            compMain = this.getComponente();

            /* regola il componente direttamente accessibile dall'utente */
            compEdit.setDisabledTextColor(CampoVideo.COLORE_TESTO_DISABILITATO);
            compEdit.setBackground(CVTesto.COLORE_SFONDO_ABILITATO);

            /* aggiunge il componente principale al pannelloComponenti
             * usando BorderLayout */
            pc = this.getPannelloComponenti();
            pc.add(compMain);

            /* invoca il metodo sovrascritto nella superclasse */
            super.creaComponentiInterni();


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    }


    /**
     * Regola la larghezza dei singoli componenti
     * interni al PannelloComponenti
     * <p/>
     * Metodo sovrascritto nelle sottoclassi <br>
     * E' responsabilita' di questo metodo assegnare la larghezza
     * preferita a tutti i componenti interni al pannello componenti.<br>
     */
    protected void regolaLarghezzaComponenti() {
        /* variabili e costanti locali di lavoro */
        JComponent comp;
        int lar;

        try { // prova ad eseguire il codice

            /* recupera il componente principale */
            comp = this.getComponente();

            /* Assegna la larghezza preferita */
            if (comp != null) {
                lar = this.getLarghezzaComponenti();
                Lib.Comp.setPreferredWidth(comp, lar);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    protected void regolaAltezzaComponenti() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        JComponent compMain;
        JComponent compTesto = null;
        int numRighe = 0;
        int hRiga;
        Font unFont = null;
        int hTeorica;
        double fattoreAria = 0.3d;
        int hReale;

        try { // prova ad eseguire il codice

            /* recupera il componente principale */
            compMain = this.getComponente();
            continua = compMain != null;

            /* recupera il componente di testo */
            if (continua) {
                compTesto = this.getComponenteTesto();
                continua = compTesto != null;
            }// fine del blocco if

            /* recupera il font utilizzato */
            if (continua) {
                unFont = compTesto.getFont();
                continua = unFont != null;
            }// fine del blocco if

            /* assegna l'altezza preferita al componente principale
             * in funzione del numero di righe e del font */
            if (continua) {

                /* recupera il numero di righe */
                numRighe = this.getNumeroRighe();

                /* determina l'altezza della riga in funzione del font utilizzato */
                hRiga = LibFont.getAltezzaFont(unFont);

                /* determina l'altezza teorica del componente
                 * in funzione del numero e dell'altezza delle righe */
                hTeorica = numRighe * hRiga;

                /* determina l'altezza reale aumentando l'altezza
                 * teorica di un po' per lasciare aria.
                 * L'aumento e' proporzionale all'altezza della riga */
                hReale = hTeorica + (int)(hRiga * fattoreAria);

                /* assegna la nuova altezza al componente principale */
                Lib.Comp.setPreferredHeigth(compMain, hReale);

            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Aggiunge i <code>Listener</code>.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Aggiunge ai componenti video di questo campo gli eventuali
     * ascoltatori delle azioni (eventi) <br>
     * Metodo invocato da SchedaBase.inizializza <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @param portale il portale di riferimento del campo
     *
     * @see it.algos.base.scheda.SchedaBase#inizializza()
     */
    public void aggiungeListener(Portale portale) {
        /* variabili e costanti locali di lavoro */
        KeyListener azione;

        super.aggiungeListener(portale);

        try { // prova ad eseguire il codice

            if (portale != null) {
                azione = portale.getAzKey(Azione.CARATTERE);
            } else {
                azione = Progetto.getAzKey(Azione.CARATTERE);
            }// fine del blocco if-else

            this.getComponenteTesto().addKeyListener(azione);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regola il componente GUI per essere modificabile o meno.
     * <p/>
     * Metodo sovrascritto dalle sottoclassi<br>
     *
     * @param flag true se il campo deve essere modificabile
     */
    protected void regolaModificabile(boolean flag) {
        /* variabili e costanti locali di lavoro */
        JTextComponent comp;
        Color sfondo;

        try {    // prova ad eseguire il codice
            comp = this.getComponenteTesto();
            if (comp != null) {
                if (flag) {
                    sfondo = COLORE_SFONDO_ABILITATO;
                } else {
                    sfondo = COLORE_SFONDO_DISABILITATO;
                }// fine del blocco if-else

                comp.setBackground(sfondo);
                comp.setEnabled(flag);
                comp.setEditable(flag);

            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Aggiorna la GUI col valore video.
     * <p/>
     * Metodo invocato dal ciclo avvia di SchedaBase <br>
     * Regola il componente GUI del campoVideo con il valore <br>
     * (questo metodo va implementato qui) <br>
     *
     * @param unValore valore video proveniente dal CampoDati
     *
     * @see it.algos.base.scheda.SchedaBase#caricaValori
     * @see it.algos.base.campo.logica.CLBase#memoriaGui()
     */
    public void aggiornaGUI(Object unValore) {
        /* variabili e costanti locali di lavoro */
        String unTesto;
        JTextComponent unComponenteTesto;

        try {    // prova ad eseguire il codice

            /* recupera il valore stringa */
            unTesto = Lib.Testo.getStringa(unValore);

            /* recupera il componente di testo */
            unComponenteTesto = this.getComponenteTesto();

            /* regolazione effettiva del componente */
            unComponenteTesto.setText(unTesto);

            /* posiziona il cursore all'inizio del testo */
            unComponenteTesto.setCaretPosition(0);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    }


    /**
     * Recupera dalla GUI il valore video.
     * <p/>
     * Metodo invocato da isModificata() e da modificaCampo() <br>
     *
     * @return valore video per il CampoDati
     *
     * @see it.algos.base.navigatore.NavigatoreBase#modificaCampo(it.algos.base.campo.base.Campo)
     * @see it.algos.base.scheda.SchedaBase#isModificata()
     * @see it.algos.base.campo.logica.CLBase#guiMemoria()
     */
    public Object recuperaGUI() {
        /* variabili e costanti locali di lavoro */
        String unTesto = "";
        JTextComponent unComponenteTesto;

        try {    // prova ad eseguire il codice

            /* recupera il componente di testo */
            unComponenteTesto = this.getComponenteTesto();

            /* recupera il valore stringa */
            unTesto = unComponenteTesto.getText();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return unTesto;
    }


    /**
     * Regola il numero delle righe.
     * <p/>
     * Numero di righe da visualizzare <br>
     * (l'altezza del pannelloComponenti dipende dalle righe) <br>
     *
     * @param numeroRighe visibili
     */
    public void setNumeroRighe(int numeroRighe) {

        /* rimanda alla superclasse */
        super.setNumeroRighe(numeroRighe);

        /* regola immediatamente l'altezza del componente */
        this.regolaAltezzaComponenti();

    }


    /**
     * Seleziona tutto il contenuto del campo.
     * <p/>
     */
    public void selectAll() {

        try {    // prova ad eseguire il codice

            /*
             * Seleziona tutto il testo.
             * Workaround per Java bug 4699955
             * Problema: c'e' un formatter differente tra il focused
             * e il non focused state, e la selezione viene persa
             * successivamente da Java prima della fine della catena
             * degli eventi AWT.
             * Soluzione: il comando viene accodato alla catena degli
             * eventi AWT ed eseguito dopo che tutti gli eventi AWT
             * sono stati processati.
             */
            final JTextComponent comp = this.getComponenteTesto();
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    comp.selectAll();
                }
            });

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Restituisce l'elenco dei componenti video del campo.
     * <p/>
     * In questo caso e' un solo componente.<br>
     *
     * @return l'elenco dei componenti video
     */
    protected ArrayList<JComponent> getComponentiVideo() {
        /* variabili e costanti locali di lavoro */
        ArrayList<JComponent> componenti = null;
        JTextComponent comp;

        try { // prova ad eseguire il codice
            componenti = new ArrayList<JComponent>();
            comp = this.getComponenteTesto();
            componenti.add(comp);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return componenti;
    }


    /**
     * Assegna il colore di primo piano.
     * <p/>
     *
     * @param colore da assegnare
     */
    public void setForegroundColor(Color colore) {
        /* variabili e costanti locali di lavoro */
        JTextComponent comp;

        super.setForegroundColor(colore);

        try {    // prova ad eseguire il codice
            comp = this.getComponenteTesto();
            if (comp != null) {
                if (colore != null) {
                    comp.setForeground(colore);
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Assegna il colore di sfondo.
     * <p/>
     *
     * @param colore da assegnare
     */
    public void setBackgroundColor(Color colore) {
        /* variabili e costanti locali di lavoro */
        JTextComponent comp;

        super.setBackgroundColor(colore);

        try {    // prova ad eseguire il codice
            comp = this.getComponenteTesto();
            if (comp != null) {
                if (colore != null) {
                    comp.setBackground(colore);
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Assegna il font al campo.
     * <p/>
     *
     * @param font da assegnare
     */
    public void setFont(Font font) {
        /* variabili e costanti locali di lavoro */
        JTextComponent comp;

        super.setFont(font);

        try {    // prova ad eseguire il codice
            comp = this.getComponenteTesto();
            if (comp != null) {
                if (font != null) {
                    comp.setFont(font);
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Recupera il componente di testo accessibile dall'utente.
     * <p/>
     *
     * @return il componente di testo accessibile
     */
    public JTextComponent getComponenteTesto() {
        return componenteTesto;
    }


    /**
     * Regola il riferimento al componente di testo accessibile dall'utente.
     * <p/>
     *
     * @param comp il componente di testo accessibile
     */
    protected void setComponenteTesto(JTextComponent comp) {
        this.componenteTesto = comp;
    }

//    public CampoVideo clonaCampo(Campo unCampoParente) {
//        /* variabili e costanti locali di lavoro */
//        CVTesto cvClone=null;
//        JTextComponent compTesto;
//
//        try { // prova ad eseguire il codice
//            cvClone = (CVTesto)super.clonaCampo(unCampoParente);
//            compTesto = cvClone.getComponenteTesto();
//            if (cvClone!=null) {
//                compTesto.setInputVerifier(null);
//            }// fine del blocco if
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//
//        /* valore di ritorno */
//        return cvClone;
//    }
}// fine della classe