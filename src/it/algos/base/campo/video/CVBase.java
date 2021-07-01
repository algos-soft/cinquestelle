/**
 * Title:        CVBase.java
 * Package:      it.algos.base.campo.video
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Ceresa, Colombo, Valbonesi
 * @version 1.0  /
 * Creato:       il 13 settembre 2003 alle 15.21
 */

package it.algos.base.campo.video;

import it.algos.base.azione.Azione;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoAstratto;
import it.algos.base.campo.base.CampoBase;
import it.algos.base.campo.dati.CampoDati;
import it.algos.base.campo.logica.CampoLogica;
import it.algos.base.campo.scheda.CampoScheda;
import it.algos.base.campo.video.decorator.CVDAzione;
import it.algos.base.campo.video.decorator.CVDBottone;
import it.algos.base.campo.video.decorator.CVDCalcolato;
import it.algos.base.campo.video.decorator.CVDCheck;
import it.algos.base.campo.video.decorator.CVDCongelato;
import it.algos.base.campo.video.decorator.CVDCopiato;
import it.algos.base.campo.video.decorator.CVDEstratto;
import it.algos.base.campo.video.decorator.CVDEtichetta;
import it.algos.base.campo.video.decorator.CVDLegenda;
import it.algos.base.campo.video.decorator.CVDObbligatorio;
import it.algos.base.campo.video.decorator.CVDPreferenza;
import it.algos.base.campo.video.decorator.CVDecoratore;
import it.algos.base.campo.video.decorator.VideoFactory;
import it.algos.base.combo.Combo;
import it.algos.base.errore.Errore;
import it.algos.base.evento.BaseListener;
import it.algos.base.form.Form;
import it.algos.base.libreria.Lib;
import it.algos.base.modulo.Modulo;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloBase;
import it.algos.base.pannello.PannelloCampo;
import it.algos.base.pannello.PannelloComponenti;
import it.algos.base.portale.Portale;
import it.algos.base.progetto.Progetto;
import it.algos.base.wrapper.Estratti;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import javax.swing.text.JTextComponent;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

/**
 * Componente GUI visibile nella Scheda.
 * <p/>
 * Larghezza del pannelloCampo; viene fissata a 4 livelli: <ul>
 * <li> (ciclo inizia) Inizialmente con un valore di default comune a tutti i campi </li>
 * <li> (ciclo inizia) Successivamente con un valore di default specifico del
 * <i>tipo</i> di campo </li>
 * <li> (ciclo esterno) Pu� essere regolata dall'esterno della classe nel metodo
 * modello.creaCampi() </li>
 * <li> (ciclo inizializza) Pu� essere calcolata nel LayoutCampo in base alle
 * dimensioni ed alla posizione relativa dei varii elementi (dipende da un flag) </li>
 * </ul>
 * Larghezza del pannelloComponenti; viene fissata a 1 livello: <ul>
 * <li> (ciclo inizializza) Viene calcolata a partire dalla larghezza del pannelloCampo </li>
 * </ul>
 * Altezza del pannelloCampo; viene fissata a 4 livelli: <ul>
 * <li> (ciclo inizia) Inizialmente con un valore di default comune a tutti i campi </li>
 * <li> (ciclo inizia) Successivamente con un valore di default specifico del
 * <i>tipo</i> di campo </li>
 * <li> (ciclo esterno) Pu� essere regolata dall'esterno della classe nel metodo
 * modello.creaCampi() </li>
 * <li> (ciclo inizializza) Pu� essere calcolata nel LayoutCampo in base alle
 * dimensioni ed alla posizione relativa dei varii elementi (dipende da un flag) </li>
 * </ul>
 * Altezza del pannelloComponenti; viene fissata a 2 livelli: <ul>
 * <li> (ciclo inizializza) Viene calcolata a partire dall'altezza del pannelloCampo </li>
 * <li> (ciclo inizializza) Viene ricalcolata quasi sempre dal componente GUI
 * specifico del <i>tipo</i> di campo </li>
 * <li> Alcuni campi particolari stabiliscono un'altezza di default, accettano una
 * modifica esterna e poi mentengono fisso il valore, interponendo uno scorrevole se
 * la dimensione non e' sufficente </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa
 * @author Alberto Colombo
 * @author Alessandro Valbonesi
 * @author gac
 * @version 1.0  /  13 settembre 2003 ore 15.21
 */
public abstract class CVBase extends CampoAstratto implements CampoVideo, Cloneable {

    /**
     * flag obbligatorio per inizializzare una sola volta l'istanza
     */
    private boolean inizializzato = false;

    /**
     * altezza di una singola riga (l'altezza della riga dipende dal font)
     */
    private int altezzaRiga = 0;

    /**
     * numero di righe da visualizzare (l'altezza del pannello campo dipende dalle righe)
     */
    private int numeroRighe = 0;

    /**
     * pannello finale che viene inserito (disegnato) nella scheda
     * e' costituito dal unPannelloComponente piu' altri eventuali oggetti
     */
    private PannelloCampo pannelloCampo = null;

    /**
     * componente GUI principale (quando e' uno solo).
     */
    private JComponent componente = null;

    /**
     * riferimento interno per passare i metodi della classe VideoFactory
     */
    private Decora decora;

    /**
     * flag - indica se il fuoco e' stato perso temporaneamente
     * Il fuoco viene perso temporaneamente quando si attiva un'altra finestra.
     * Il fuoco viene perso definitivamente quando e' trasferito a un
     * altro componente della stessa finestra.
     * A noi interessano solo le perdite e le acquisizioni definitive.
     * Questo flag gestisce questa situazione in focusGained() e focusLost()
     * al fine di ignorare le perdite e le acquisizioni temporanee.
     */
    boolean fuocoPersoTemporaneamente;

    /**
     * Valore video del campo quando prende il fuoco
     */
    private Object valoreIngresso;

    /**
     * flag - stato focusable del campo
     */
    protected boolean focusable = true;

    /**
     * flag - stato corrente di modificabilità del campo
     * se disabilitato sbiadisce tutti i componenti
     * e impedisce di modificare
     */
    protected boolean modificabile = false;

    /**
     * colore di primo piano
     */
    private Color foreground;

    /**
     * colore di sfondo
     */
    private Color background;

    /**
     * font del campo
     */
    private Font font;

    /**
     * codifica per indicare se e come il campo si espande nel proprio contenitore
     */
    private Espandibilita espandibilita;


    /**
     * Costruttore completo con parametri.
     * (indispensabile perch� chiamato dalla sottoclasse concreta) <br>
     * (senza modificatore, cos� non pu� essere invocato fuori dal package) <br>
     *
     * @param unCampoParente campo 'contenitore' di questo oggetto
     */
    CVBase(Campo unCampoParente) {
        /* rimanda al costruttore della superclasse */
        super(unCampoParente);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni iniziali di riferimenti e variabili
     * Metodo chiamato direttamente dal costruttore
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        try { // prova ad eseguire il codice

            /* riferimento interno per passare i metodi della classe VideoFactory */
            this.setDecora(new Decora());

            /* crea i componenti GUI */
            this.creaComponentiGUI();

            /* crea il decoratore etichetta e lo
             * interpone al campo video del campo  */
            new CVDEtichetta(this);

            /* di default e' modificabile */
            this.setModificabile(true);

            /* di default non è espandibile */
            this.setEspandibilita(Espandibilita.nessuna);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regolazioni dinamiche dell'oggetto.
     * <p/>
     * Regola le caratteristiche dinamiche in base alla impostazione
     * corrente delle caratteristiche statiche <br>
     * Metodo invocato dalla classe che crea questo oggetto (di norma) <br>
     * Viene eseguito una sola volta <br>
     * Sovrascritto nelle sottoclassi <br>
     */
    public void inizializza() {
        try {    // prova ad eseguire il codice

            if (!this.isInizializzato()) {

                /* invoca il metodo (quasi) sovrascritto della superclasse */
                super.inizializzaCampoAstratto();

                /* Regola le dimensioni dei componenti interni al pannelloComponenti */
                this.regolaDimensioneComponenti();

                /**
                 * regola la modificabilita' iniziale del campo
                 * dopo che sono stati creati i componenti video
                 */
                this.setModificabile(this.isModificabile());

                /**
                 * regola la focusabilità iniziale del campo
                 * dopo che sono stati creati i componenti video
                 */
                this.setFocusable(this.isFocusable());

                /**
                 * regola il colore di foreground del campo
                 * dopo che sono stati creati i componenti video
                 */
                this.setForegroundColor(this.getForegroundColor());

                /**
                 * regola il colore di background del campo
                 * dopo che sono stati creati i componenti video
                 */
                this.setBackgroundColor(this.getBackgroundColor());

                /**
                 * regola il colore di background del campo
                 * dopo che sono stati creati i componenti video
                 */
                this.setFont(this.getFont());

                /** lascio i pannelli non-trasparenti (default),
                 * ma regolo il colore standard
                 * questo perche se li mettessi trasparenti, poi non potrei
                 * eventualmente modificare il colore
                 * (a meno di non rimetterli opachi, ovviamente) */
                if (this.isDebug()) {
                    this.getPannelloCampo().setOpaque(true);
                    this.getPannelloCampo().setBackground(Color.yellow);
                    this.getPannelloBaseComponenti().setOpaque(true);
                    this.getPannelloBaseComponenti().setBackground(Color.green);
                }// fine del blocco if-else

                /* regola il flag */
                this.setInizializzato(true);


            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


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

        try { // prova ad eseguire il codice

            /* invoca il metodo (quasi) sovrascritto della superclasse */
            super.avviaCampoAstratto();

            /* Fissa le dimensioni del Pannello Campo */
            this.pack();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


    } /* fine del metodo */


    /**
     * Crea i componenti GUI per il campo.
     * <p/>
     */
    private void creaComponentiGUI() {
        /* variabili e costanti locali di lavoro */
        PannelloCampo panCampo;

        try {    // prova ad eseguire il codice

            /* crea e registra il pannello campo vuoto
             * (contiene già il PannelloComponenti) */
            panCampo = new PannelloCampo(this.getCampoParente());
            this.setPannelloCampo(panCampo);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Crea i componenti interni al pannelloComponenti.
     * <p/>
     * Metodo invocato dal ciclo inizia o inizializza o avvia, a seconda
     * delle esigenze del tipo specifico di campo <br>
     * Metodo sovrascritto dalle sottoclassi <br>
     * E' responsabilita' di questo metodo:
     * - creare i componenti GUI interni al PannelloComponenti<br>
     * - aggiungere i listener ai componenti GUI creati
     * - eventualmente assegnare un layout al PannelloComponenti<br>
     * - aggiungere i componenti al pannelloComponenti<br>
     */
    public void creaComponentiInterni() {

        /** aggiunge i listener ai componenti interni del campo
         * che sono stati già creati dalla sottoclasse */
        this.aggiungeListener((Portale)null);

    }


    /**
     * Aggiunge il decoratore al pannelloCampo nella posizione prevista.
     * <p/>
     */
    public void addComponente(Component comp, CVDecoratore.Pos pos) {
        /* variabili e costanti locali di lavoro */
        PannelloCampo pc;

        try {    // prova ad eseguire il codice

            /* aggiunge il componente etichetta */
            pc = this.getPannelloCampo();
            if (pc != null) {
                if (comp != null) {
                    pc.add(comp, pos);
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Fissa le dimensioni del pannello Campo pari
     * alla alla attuale dimensione preferita del suo contenuto
     * <p/>
     * Metodo invocato dal ciclo avvia <br>
     */
    public void pack() {
        /* variabili e costanti locali di lavoro */
        Pannello panCampo;

        try { // prova ad eseguire il codice

            /* recupera il pannello Campo */
            panCampo = this.getPannelloCampo();
            panCampo.bloccaDim();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regola le dimensioni dei singoli componenti
     * interni al pannelloComponenti.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * E' responsabilita' di questo metodo attribuire una preferred size
     * ad ogni componente interno al pannello componenti.
     * I componenti possono essere anche più di uno <br>
     */
    public void regolaDimensioneComponenti() {
        this.regolaLarghezzaComponenti();
        this.regolaAltezzaComponenti();
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
    }


    /**
     * Regola l'altezza dei singoli componenti
     * interni al PannelloComponenti
     * <p/>
     * Metodo sovrascritto nelle sottoclassi <br>
     * E' responsabilita' di questo metodo assegnare l'altezza
     * preferita ai componenti interni al pannello componenti.<br>
     * Nella classe base L'altezza viene regolata in funzione del numero di righe
     * da visualizzare e del font utilizzato
     */
    protected void regolaAltezzaComponenti() {
    }

    /**
     * Ritorna il componente interno di tipo Testo.
     * <p/>
     * (campi di tipo TestoField, TestoArea)
     *
     * @return il componente unico di tipo Testo
     */
    public JTextComponent getComponenteTesto() {
        return null;
    }

    /**
     * Ritorna il componente unico di tipo JToggleButton.
     * <p/>
     * Sovrascritto nelle sottoclassi
     *
     * @return il componente unico di tipo JToggleButton
     */
    public JToggleButton getComponenteToggle() {
        return null;
    }


    /**
     * Campo obbligatorio per registrazione / conferma
     */
    public boolean isObbligatorio() {
        return false;
    }


    /**
     * Recupera lo stato corrente di modificabilita'
     * del componente GUI del campo per la scheda.
     * <p/>
     * Metodo sovrascritto dalle sottoclassi.
     *
     * @return lo stato di abilitazione del componente GUI
     */
    public boolean isModificabile() {
        return this.modificabile;
    }


    /**
     * Rende il campo modificabile o meno.
     * <p/>
     * Se il flag è true, risponde solo se il campo è abilitato.<br>
     *
     * @param flag true se il campo deve essere modificabile
     */
    public void setModificabile(boolean flag) {
        /* variabili e costanti locali di lavoro */
        boolean abilitato;

        try {    // prova ad eseguire il codice

            /* recupera lo stato di abilitazione del campo */
            abilitato = this.getCampoParente().isAbilitato();
            if (flag) { // voglio renderlo modificabile
                /* risponde solo se abilitato */
                if (abilitato) {
                    this.regolaModificabile(true);
                    this.modificabile = true;
                }// fine del blocco if
            } else {   // voglio renderlo non modificabile
                this.regolaModificabile(false);
                this.modificabile = false;
            }// fine del blocco if-else
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
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
        ArrayList<JComponent> componenti;

        try {    // prova ad eseguire il codice
            componenti = this.getComponentiVideo();
            if (componenti != null) {
                for (JComponent comp : componenti) {
                    if (comp != null) {
                        comp.setEnabled(flag);
                    }// fine del blocco if
                }
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Ritorna lo stato corrente dell'attributo Focusable del campo.
     * <p/>
     *
     * @return true se focusable, false se non focusable
     */
    public boolean isFocusable() {
        return focusable;
    }


    /**
     * Rende il campo focusable o non focusable.
     * <p/>
     * Metodo sovrascritto dalle sottoclassi<br>
     *
     * @param flag di controllo dell'attributo Focusable
     */
    public void setFocusable(boolean flag) {
        /* variabili e costanti locali di lavoro */
        ArrayList<JComponent> componenti;

        try {    // prova ad eseguire il codice

            this.focusable = flag;

            componenti = this.getComponentiVideo();
            if (componenti != null) {
                for (JComponent comp : componenti) {
                    if (comp != null) {
                        comp.setFocusable(flag);
                    }// fine del blocco if
                }
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Ritorna il colore di primo piano.
     * <p/>
     *
     * @return il colore di primo piano
     */
    public Color getForegroundColor() {
        return this.foreground;
    }


    /**
     * Assegna il colore di primo piano.
     * <p/>
     *
     * @param colore da assegnare
     */
    public void setForegroundColor(Color colore) {
        this.foreground = colore;
    }


    /**
     * Ritorna il colore di sfondo.
     * <p/>
     *
     * @return il colore di sfondo
     */
    public Color getBackgroundColor() {
        return this.background;
    }


    /**
     * Assegna il colore di sfondo.
     * <p/>
     *
     * @param colore da assegnare
     */
    public void setBackgroundColor(Color colore) {
        this.background = colore;
    }


    /**
     * Ritorna il font del campo.
     * <p/>
     *
     * @return il font del campo
     */
    public Font getFont() {
        return font;
    }


    /**
     * Assegna il font al campo.
     * <p/>
     *
     * @param font da assegnare
     */
    public void setFont(Font font) {
        this.font = font;
    }


    /**
     * Regola l'allineamento del testo.
     * <p/>
     * Metodo sovrascritto dalle sottoclassi<br>
     *
     * @param allineamento codice di allineamento
     *
     * @see javax.swing.SwingConstants
     *      SwingConstants.LEFT
     *      SwingConstants.CENTER
     *      SwingConstants.RIGHT
     *      SwingConstants.LEADING
     *      SwingConstants.TRAILING
     */
    public void setAllineamento(int allineamento) {
    }


    /**
     * Aggiunge i <code>Listener</code>.
     * <p/>
     * Aggiunge ai componenti video di questo campo gli eventuali
     * ascoltatori delle azioni (eventi) <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @param azione azione da aggiungere
     */
    public void aggiungeListener(BaseListener azione) {
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
        ArrayList<JComponent> componentiVideo;
        FocusListener azioneEntrata;
        FocusListener azioneUscita;

        try { // prova ad eseguire il codice

            componentiVideo = this.getComponentiVideo();
            if (componentiVideo != null) {

                for (JComponent comp : componentiVideo) {
                    if (comp != null) {

                        if (portale != null) {

                            azioneEntrata = portale.getAzFocus(Azione.ENTRATA_CAMPO);
                            azioneUscita = portale.getAzFocus(Azione.USCITA_CAMPO);

                        } else {

                            azioneEntrata = Progetto.getAzFocus(Azione.ENTRATA_CAMPO);
                            azioneUscita = Progetto.getAzFocus(Azione.USCITA_CAMPO);

                        }// fine del blocco if-else

                        comp.addFocusListener(azioneEntrata);
                        comp.addFocusListener(azioneUscita);

                    }// fine del blocco if

                }

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }

//    /**
//     * Recupera una azione dal modulo del campo.
//     * <p/>
//     */
//    protected Azione getAzione(String chiave) {
//        /* variabili e costanti locali di lavoro */
//        Azione azione  = null;
//        Modulo modulo;
//
//        try {    // prova ad eseguire il codice
//
//            modulo = this.getCampoParente().getModulo();
//
//            if (modulo!=null) {
//                azione = modulo.getAzione(chiave);
//            }// fine del blocco if
//
//        } catch (Exception unErrore) {    // intercetta l'errore
//            Errore.crea(unErrore);
//        } // fine del blocco try-catch
//
//        /* valore di ritorno */
//        return azione;
//    }


    /**
     * Aggiorna la GUI col valore video.
     * <p/>
     * Metodo invocato dal ciclo avvia di SchedaBase <br>
     * Regola il componente GUI del campoVideo con il valore <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @param unValore valore video proveniente dal CampoDati
     *
     * @see it.algos.base.scheda.SchedaBase#caricaValori
     * @see it.algos.base.campo.logica.CLBase#memoriaGui()
     */
    public abstract void aggiornaGUI(Object unValore);


    /**
     * Recupera dalla GUI il valore video.
     * <p/>
     *
     * @return valore video per il CampoDati
     */
    public abstract Object recuperaGUI();


    /**
     * Metodo eseguito quando il valore memoria di un campo cambia.
     * <p/>
     * Invoca i metodi delegati <br>
     */
    public void eventoMemoriaModificata() {
    }


    /**
     * Metodo eseguito quando il valore video di un campo cambia.
     * <p/>
     * Invoca i metodi delegati <br>
     */
    public void eventoVideoModificato() {
    }


    /**
     * pannello dei componenti base (uno o piu')
     * puo' essere composto di diversi oggetti affatto diversi
     */
    public PannelloBase getPannelloBaseComponenti() {
        return this.getPannelloComponenti().getPanFisso();
    } /* fine del metodo getter */


    /**
     * pannello finale che viene inserito (disegnato) nella scheda
     * e' costituito dal unPannelloComponente piu' altri eventuali oggetti
     */
    public PannelloBase getPannelloBaseCampo() {
        return this.getPannelloCampo().getPanFisso();
    } /* fine del metodo getter */


    public void setOrdinabile(boolean ordinabile) {
    }


    /**
     * Regola il testo descrittivo del componente.
     * <p/>
     * (checkbox o radio bottone) <br>
     * (cosa diversa dall'etichetta) <br>
     *
     * @param testoComponente testo descrittivo
     */
    public void setTestoComponente(String testoComponente) {
    }


    /**
     * Ritorna il testo descrittivo del componente.
     * <p/>
     * (checkbox o radio bottone) <br>
     * (cosa diversa dall'etichetta) <br>
     *
     * @return il testo descrittivo del componente
     */
    public String getTestoComponente() {
        return "";
    }


    /**
     * Regola lo stato di visibilita' del campo.
     * <p/>
     *
     * @param visibile true visibile, false invisibile
     */
    public void setVisibile(boolean visibile) {
        this.getPannelloBaseCampo().setVisible(visibile);
    }


    /**
     * Ritorna lo stato corrente di visibilita' del campo.
     * <p/>
     *
     * @return la visibilita' del campo
     */
    public boolean isVisibile() {
        return this.getPannelloBaseCampo().isVisible();
    }


    public boolean isSelezionato() {
        return false;
    }


    /**
     * Restituisce il CampoLogica associato a questo CampoVideo.
     * <p/>
     *
     * @return il campo logica
     */
    protected CampoLogica getCampoLogica() {
        /* variabili e costanti locali di lavoro */
        CampoLogica campoLogica = null;
        Campo campo;

        try { // prova ad eseguire il codice
            campo = this.getCampoParente();
            if (campo != null) {
                campoLogica = campo.getCampoLogica();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return campoLogica;
    }


    /**
     * Restituisce il CampoScheda associato a questo CampoVideo.
     * <p/>
     *
     * @return il campo scheda
     */
    public CampoScheda getCampoScheda() {
        /* variabili e costanti locali di lavoro */
        CampoScheda campoScheda = null;
        Campo campo;

        try { // prova ad eseguire il codice
            campo = this.getCampoParente();
            if (campo != null) {
                campoScheda = campo.getCampoScheda();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return campoScheda;
    }


    /**
     * Restituisce il CampoDati associato a questo CampoVideo.
     * <p/>
     *
     * @return il campo dati
     */
    protected CampoDati getCampoDati() {
        /* variabili e costanti locali di lavoro */
        CampoDati campoDati = null;
        Campo campo;

        try { // prova ad eseguire il codice
            campo = this.getCampoParente();
            if (campo != null) {
                campoDati = campo.getCampoDati();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return campoDati;
    }


    /**
     * Ritorna il pannello campo
     * <p/>
     *
     * @return il pannello campo
     */
    public PannelloCampo getPannelloCampo() {
        return this.pannelloCampo;
    }


    public void setPannelloCampo(PannelloCampo pan) {
        this.pannelloCampo = pan;
    }


    /**
     * Restituisce il pannello componenti del campo.
     * <p/>
     *
     * @return il pannello componenti
     */
    public PannelloComponenti getPannelloComponenti() {
        return this.getPannelloCampo().getPannelloComponenti();
    }


    protected void setPannelloComponenti(PannelloComponenti pan) {
        this.getPannelloCampo().setPannelloComponenti(pan);
    }


    /**
     * Larghezza complessiva dei componenti.
     * <p/>
     * Nella classe base recupera l'attributo
     * dalla classe CampoScheda <br>
     *
     * @return larghezza complessiva dei componenti
     */
    protected int getLarghezzaComponenti() {
        /* variabili e costanti locali di lavoro */
        int lar = 0;
        CampoScheda cs;

        try { // prova ad eseguire il codice
            cs = this.getCampoScheda();
            if (cs != null) {
                lar = cs.getLarghezzaComponenti();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return lar;
    }


    /**
     * Altezza complessiva dei componenti.
     * <p/>
     * Nella classe base recupera l'attributo
     * dalla classe CampoScheda <br>
     *
     * @return altezza complessiva dei componenti
     */
    protected int getAltezzaComponenti() {
        /* variabili e costanti locali di lavoro */
        int alt = 0;
        CampoScheda cs;

        try { // prova ad eseguire il codice
            cs = this.getCampoScheda();
            if (cs != null) {
                alt = cs.getAltezzaComponenti();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return alt;
    }


    /**
     * Ritorna il testo dell'etichetta
     * <p/>
     *
     * @return il testo dell'etichetta
     *         Se non ha decoratore etichetta ritorna il nome interno
     */
    public String getTestoEtichetta() {
        /* valore di ritorno */
        return this.getCampoParente().getNomeInterno();
    }


    /**
     * Ritorna la larghezza in pixel dell'etichetta
     * <p/>
     *
     * @return la larghezza in pixel dell'etichetta
     */
    public int getLarghezzaEtichetta() {
        return 0;
    }


    /**
     * Regola la larghezza in pixel dell'etichetta
     * <p/>
     *
     * @param larghezza in pixel dell'etichetta
     */
    public void setLarghezzaEtichetta(int larghezza) {
    }


    /**
     * Regola (sinistra o destra) l'allineamento del testo dell'etichetta
     * <p/>
     *
     * @param bandiera tipo di allineamento
     */
    public void setAllineamentoEtichetta(Pannello.Bandiera bandiera) {
    }


    /**
     * Controlla se il campo ha l'etichetta e se questa è a sinistra.
     * <p/>
     *
     * @return vero se esiste l'etichetta ed è a sinistra
     */
    public boolean isEtichettaSinistra() {
        return false;
    }


    protected int getAltezzaRiga() {
        return altezzaRiga;
    }


    protected void setAltezzaRiga(int altezzaRiga) {
        this.altezzaRiga = altezzaRiga;
    }


    public int getNumeroRighe() {
        return numeroRighe;
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
        this.numeroRighe = numeroRighe;
    }


    /**
     * Seleziona tutto il contenuto del campo.
     * <p/>
     */
    public void selectAll() {
    }


    public void setAltezzaRadioBottone(int altezzaRadioBottone) {
    }


    public void setCombo(Combo combo) {
    }


    /**
     * Controlla se il combo usa la funzione Nuovo Record.
     * <p/>
     *
     * @return true se il combo usa la funzione Nuovo Record
     */
    public boolean isUsaNuovo() {
        return false;
    }


    /**
     * Controlla l'uso della funzione Nuovo Record.
     * <p/>
     *
     * @param flag per usare la funzione Nuovo Record
     */
    public void setUsaNuovo(boolean flag) {
    }


    /**
     * Controlla se il combo usa la funzione Modifica Record.
     * <p/>
     *
     * @return true se il combo usa la funzione Modifica Record
     */
    public boolean isUsaModifica() {
        return false;
    }


    /**
     * Controlla l'uso della funzione Modifica Record.
     * <p/>
     *
     * @param flag per usare la funzione Modifica Record
     */
    public void setUsaModifica(boolean flag) {
    }


    /**
     * Controlla l'uso della voce Non Specificato.
     * <p/>
     *
     * @return true se usa la voce Non Specificato
     */
    public boolean isUsaNonSpecificato() {
        return false;
    }


    /**
     * Controlla l'uso della voce Non Specificato.
     * <p/>
     *
     * @param flag per usare la voce Non Specificato
     */
    public void setUsaNonSpecificato(boolean flag) {
    }


    /**
     * Restituisce un campo della scheda.
     * <p/>
     *
     * @param nome del campo
     *
     * @return campo richiesto
     */
    public Campo getCampoForm(String nome) {
        /* variabili e costanti locali di lavoro */
        Campo campo = null;
        Campo campoParente = null;
        boolean continua;
        Form form = null;

        try { // prova ad eseguire il codice
            continua = Lib.Testo.isValida(nome);

            if (continua) {
                campoParente = this.getCampoParente();
                continua = campoParente != null;
            }// fine del blocco if

            if (continua) {
                form = campoParente.getForm();
                continua = form != null;
            }// fine del blocco if

            if (continua) {
                campo = form.getCampo(nome);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return campo;
    }


    public CVBase getCVBase() {
        return this;
    }


    /**
     * Gestione evento gui modificata.
     * <p/>
     * Metodo invocato dal campo logica <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    public void guiModificata() {
        int a = 87;
    }


    /**
     * Invocato quando si entra in un campo (prende il fuoco).
     * <p/>
     */
    public void entrataCampo() {
        try {    // prova ad eseguire il codice
            this.setValoreIngresso(this.getCampoDati().getVideo());
            this.fireFuocoPreso();
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Invocato quando si esce da un campo
     * <p/>
     * (il campo perde il fuoco in maniera definitiva).
     * Trasporta il valore da Memoria a GUI<br>
     * Confronta il valore video in ingresso con quello in uscita,
     * se diversi lancia l'evento uscita campo modificato.
     */
    public void uscitaCampo() {
        /* variabili e costanti locali di lavoro */
        boolean continua = true;
        Object prima = null;
        Object dopo = null;

        try {    // prova ad eseguire il codice

            /* trasporta il valore da memoria a GUI per
             * aggiornare il display */
            this.getCampoParente().getCampoLogica().memoriaGui();

            if (continua) {
                prima = this.getValoreIngresso();
                continua = (prima != null);
            }// fine del blocco if

            if (continua) {
                dopo = this.getCampoDati().getVideo();
                continua = (dopo != null);
            }// fine del blocco if

            if (continua) {
                if (!(prima.equals(dopo))) {
                    this.getCampoParente().fire(CampoBase.Evento.fuocoModificato);
                }// fine del blocco if
            }// fine del blocco if

            this.fireFuocoPerso();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Pone il fuoco sul componente del campo video.
     * <p/>
     * Attenzione! il componente deve essere focusable, visibile e displayable,
     * altrimenti la chiamata non ha effetto. <br>
     * Per essere displayable, deve essere effettivamente visualizzato
     * a video, cioe' contenuto in un contenitore che faccia capo a una
     * finestra gia' visibile o gia' packed. <br>
     */
    public void grabFocus() {
        try { // prova ad eseguire il codice

            /* accodo la richiesta in modo che venga eseguita
             * al termibe della catena eventi AWT */
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    requestFocus();
                }
            });

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Richiede il fuoco sul componente editabile del campo.
     * <p/>
     */
    protected void requestFocus() {
        /* variabili e costanti locali di lavoro */
        Component comp;
        JComponent jComp;

        try {    // prova ad eseguire il codice
            comp = this.getComponente();
            if (comp != null) {
                if (comp instanceof JComponent) {
                    jComp = (JComponent)comp;
                    jComp.requestFocusInWindow();
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Invocato quando un qualsiasi componente video
     * del campo acquisisce il fuoco in maniera temporanea
     * o permanente
     * <p/>
     *
     * @param e l'evento fuoco
     */
    public void entrataCampo(FocusEvent e) {

        if (!this.isFuocoPersoTemporaneamente()) {
            this.focusGainedComponente(e);
        } else {
            /* spegne il flag e riattiva la nomale gestione del fuoco */
            this.setFuocoPersoTemporaneamente(false);
        }// fine del blocco if-else
    }


    /**
     * Invocato quando un componente del campo video ha
     * acquisito il fuoco in maniera permanente.
     * <p/>
     * Sovrascritto dalle sottoclassi.
     *
     * @param e l'evento fuoco
     */
    protected void focusGainedComponente(FocusEvent e) {
        this.entrataCampo();
//        this.fireFuocoPreso();
    }


    /**
     * Lancia un evento di fuoco acquisito per il campo.
     * <p/>
     */
    protected void fireFuocoPreso() {
        if (DEBUG) {
            String nome = this.getCampoParente().getNomeInterno();
            System.out.print("\n" + nome + ": focusGained");
        }// fine del blocco if
        this.getCampoParente().fire(CampoBase.Evento.prendeFuoco);
    }


    /**
     * Invocato quando un qualsiasi componente video
     * del campo perde il fuoco in maniera temporanea
     * o permanente
     * <p/>
     *
     * @param e l'evento fuoco
     */
    public void uscitaCampo(FocusEvent e) {

        if (!e.isTemporary()) {
            this.focusLostComponente(e);
        } else {

            /* accende il flag in modo che quando torna
             * il fuoco non faccia nulla */
            this.setFuocoPersoTemporaneamente(true);

        }// fine del blocco if-else
    }


    /**
     * Invocato quando un componente del campo video ha
     * perso il fuoco in maniera permanente.
     * <p/>
     * Sovrascritto dalle sottoclassi.
     *
     * @param e l'evento fuoco
     */
    protected void focusLostComponente(FocusEvent e) {
        this.uscitaCampo();
    }


    /**
     * Lancia un evento di fuoco perso per il campo.
     * <p/>
     */
    protected void fireFuocoPerso() {
        if (DEBUG) {
            String nome = this.getCampoParente().getNomeInterno();
            System.out.print("\n" + nome + ": focusLost");
        }// fine del blocco if
        this.getCampoParente().fire(CampoBase.Evento.perdeFuoco);
    }


    /**
     * Restituisce il riferimento al singolo componente
     * contenuto nel pannelloComponenti.
     * <p/>
     * Se il pannelloComponenti prevede diversi componenti,
     * restituisce null <br>
     *
     * @return componente GUI singolo
     */
    public JComponent getComponente() {
        return this.componente;
    }


    protected void setComponente(JComponent componente) {
        this.componente = componente;
    }


    /**
     * Restituisce l'elenco dei componenti video del campo.
     * <p/>
     * I componenti possono essere uno o piu' in funzione del tipo
     * di campo.<br>
     * Sovrascritto dalle sottoclassi.
     *
     * @return l'elenco dei componenti video
     */
    protected ArrayList<JComponent> getComponentiVideo() {
        /* valore di ritorno */
        return null;
    }


    /**
     * Regola l'orientamento del layout del Pannello Componenti.
     * <p/>
     * Metodo sovrascritto dalle sottoclassi<br>
     *
     * @param orientamento il codice di orientamento
     * Layout.ORIENTAMENTO_ORIZZONTALE o Layout.ORIENTAMENTO_VERTICALE
     */
    public void setOrientamentoComponenti(int orientamento) {
    }


    /**
     * Regola il gap tra i componenti del gruppo (radio o check).
     *
     * @param gap la distanza tra i componenti
     */
    public void setGapGruppo(int gap) {
    }


    /**
     * Aggiunge una colonna del modulo linkato alla Vista per il combo.
     * <p/>
     *
     * @param nome del campo del modulo linkato
     */
    public void addColonnaCombo(String nome) {
    }


    /**
     * Aggiunge una colonna alla Vista per il combo.
     * <p/>
     *
     * @param nomeModulo nome del modulo
     * @param nomeCampo nome del campo
     */
    public void addColonnaCombo(String nomeModulo, String nomeCampo) {
    }


    protected boolean isInizializzato() {
        return inizializzato;
    }


    protected boolean isDaInizializzare() {
        return !this.isInizializzato();
    }


    public void setInizializzato(boolean inizializzato) {
        this.inizializzato = inizializzato;
    }


    private void setDecora(Decora decora) {
        this.decora = decora;
    }


    private boolean isFuocoPersoTemporaneamente() {
        return fuocoPersoTemporaneamente;
    }


    private void setFuocoPersoTemporaneamente(boolean flag) {
        this.fuocoPersoTemporaneamente = flag;
    }


    /**
     * Flag di controllo per disegnare provvisoriamente sfondi colorati.
     *
     * @return vero se attivo
     */
    protected boolean isDebug() {
        return Campo.DEBUG;
    }


    private Object getValoreIngresso() {
        return valoreIngresso;
    }


    private void setValoreIngresso(Object valoreIngresso) {
        this.valoreIngresso = valoreIngresso;
    }


    protected Espandibilita getEspandibilita() {
        return espandibilita;
    }


    /**
     * Determina se e come il campo si espande nel proprio contenitore
     * <p/>
     *
     * @param espandibilita codifica del tipo di espandibilità
     */
    public void setEspandibilita(Espandibilita espandibilita) {
        this.espandibilita = espandibilita;
    }


    /**
     * Aggiunge un listener di tipo CampoEvento.
     * <p/>
     */
    public void addCampoEvento() {
        /* variabili e costanti locali di lavoro */
        CampoLogica logica;

        try { // prova ad eseguire il codice
            /* recupera il campo logica */
            logica = this.getCampoLogica();

            /* sposta in basso il valore e poi lo riporta in alto */
            /* serve per allineare la memoria e per formattare la GUI */
            logica.guiMemoria();
            logica.memoriaGui();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    public boolean isCongelato() {
        return false;
    }

//    /**
//     * Esegue il comando dell'evento.
//     * <p/>
//     * Metodo invocato all'uscita dal campo <br>
//     * Trasporta il valore video a memoria <br>
//     * Trasporta il valore memoria a video <br>
//     * serve per allineare la memoria e per formattare la GUI <br>
//     * Metodo sovrascritto nelle sottoclassi <br>
//     */
//    protected void azFocusLost() {
//        /* variabili e costanti locali di lavoro */
//        CampoLogica logica;
//
//        try { // prova ad eseguire il codice
//            /* recupera il campo logica */
//            logica = this.getCampoLogica();
//
//            /* sposta in basso il valore e poi lo riporta in alto */
//            /* serve per allineare la memoria e per formattare la GUI */
//            logica.guiMemoria();
//            logica.memoriaGui();
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//    }


    /**
     * Classe interna per passare i metodi della classe VideoFactory
     */
    public Decora decora() {
        decora.setCampo(this.getCampoParente());
        return decora;
    }


    /**
     * Ritorna una copia profonda dell'oggetto (deep copy) col casting
     * Per fare una copia completa di questo oggetto occorre:
     * Prima copiare l'oggetto nel suo insieme, richiamando il metodo
     * sovrascritto che copia e regola tutte le variabili dell'oggetto con
     * gli stessi valori delle variabili originarie
     * Secondo copiare tutte le variabili che sono puntatori ad altri
     * oggetti, per evitare che nella copia ci sia il puntatore all'oggetto
     * originale (in genere tutti gli oggetti che vengono creati nella
     * classe col comando new)
     * Terzo in ogni sottoclasse occorre fare le copie dei puntatori
     * esistenti nelle sottoclassi stesse
     *
     * @param unCampoParente CampoBase che cantiene questo CampoLogica
     */
    public CampoVideo clonaCampo(Campo unCampoParente) {
        /* variabili e costanti locali di lavoro */
        CampoVideo cv;

        try { // prova ad eseguire il codice
            /* invoca il metodo sovrascritto della superclasse Object */
            cv = (CampoVideo)super.clone();

        } catch (CloneNotSupportedException unErrore) { // intercetta l'errore
            throw new InternalError();
        }// fine del blocco try-catch

        try { // prova ad eseguire il codice

            /* modifica il riferimento al campo parente */
            cv.setCampoParente(unCampoParente);

            /* deep cloning - ricrea i componenti GUI */
            cv.getCVBase().creaComponentiGUI();

            /* deep cloning - ricrea i componenti interni al pannelloComponenti */
            cv.creaComponentiInterni();

            JComponent comp = cv.getCVBase().getComponente();
            if (comp != null) {
                comp.setInputVerifier(null);
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return cv;
    }

//    /**
//     * Inner class per gestire l'uscita dal campo.
//     */
//    protected class UscitaCampo extends AzAdapterFocus {
//
//        /**
//         * focusLost, da FocusListener.
//         * <p/>
//         * Esegue l'azione <br>
//         * Rimanda al metodo delegato, nel gestore specifico associato
//         * all' oggetto che genera questo evento <br>
//         * Sovrascritto nelle sottoclassi <br>
//         *
//         * @param unEvento evento che causa l'azione da eseguire <br>
//         */
//        public void focusLost(FocusEvent unEvento) {
//            try { // prova ad eseguire il codice
//                /* invoca il metodo delegato di questa classe */
//                azFocusLost();
//            } catch (Exception unErrore) { // intercetta l'errore
//                Errore.crea(unErrore);
//            }// fine del blocco try-catch
//        }
//    } // fine della classe interna


    /**
     * Classe interna per passare i metodi della classe VideoFactory
     */
    public final class Decora {

        private Campo campo;


        /**
         * Costruttore completo senza parametri.
         */
        public Decora() {
            this.setCampo(getCampoParente());
        }

//        public CVDEtichetta etichetta() {
//            return VideoFactory.etichetta(campo);
//        }


        public CVDEtichetta etichetta(String testo) {
            return VideoFactory.etichetta(campo, testo);
        }


        public CVDEtichetta etichettaSinistra(String testo) {
            return VideoFactory.etichettaSinistra(campo, testo);
        }


        /**
         * Sposta l'etichetta nella posizione a sinistra del campo.
         * <p/>
         * Se il testo dell'etichetta è inesistente, utilizza il nome del campo <br>
         * Elimina l'etichetta (in qualunque posizione si trovi) <br>
         * L'etichetta viene posizionata a sinistra del pannelloComponenti <br>
         *
         * @return campo video etichetta
         */
        public CVDEtichetta etichettaSinistra() {
            return VideoFactory.etichettaSinistra(campo);
        }


        public void eliminaEtichetta(CampoVideo campo) {
            VideoFactory.eliminaEtichetta(campo);
        }


        public void eliminaEtichetta() {
            VideoFactory.eliminaEtichetta(campo.getCampoVideo());
        }


        public void eliminaLegenda() {
            VideoFactory.eliminaLegenda(campo.getCampoVideo());
        }


        public CVDLegenda legenda(String testo) {
            return VideoFactory.legenda(campo, testo);
        }


        public CVDLegenda legendaDestra(String testo) {
            return VideoFactory.legendaDestra(campo, testo);
        }


        public CVDCopiato copiato(String... campi) {
            return VideoFactory.copiato(campo, campi);
        }


        public CVDCalcolato calcolato(String campoValore) {
            return VideoFactory.calcolato(campo, campoValore);
        }


        public CVDCalcolato calcolatoDestra(String campoValore) {
            return VideoFactory.calcolatoDestra(campo, campoValore);
        }


        public CVDCalcolato calcolatoSopra(String campoValore) {
            return VideoFactory.calcolatoSopra(campo, campoValore);
        }


        public CVDEstratto estratto(Estratti nome) {
            return VideoFactory.estratto(campo, nome);
        }


        public CVDEstratto estrattoSotto(Estratti nome) {
            return VideoFactory.estrattoSotto(campo, nome);
        }


        public CVDCheck check() {
            return VideoFactory.check(campo);
        }


        public CVDCheck check(boolean selezionato) {
            return VideoFactory.check(campo, selezionato);
        }


        public CVDBottone bottone() {
            return VideoFactory.bottone(campo);
        }


        public CVDBottone bottone(JButton bottone) {
            return VideoFactory.bottone(campo, bottone);
        }


        public CVDBottone bottone(String nomeIcona) {
            return VideoFactory.bottone(campo, nomeIcona);
        }


        public CVDBottone bottone(String nomeIcona, String tooltip) {
            return VideoFactory.bottone(campo, nomeIcona, tooltip);
        }


        public CVDBottone bottone(Modulo modulo, String nomeIcona) {
            return VideoFactory.bottone(campo, modulo, nomeIcona);
        }


        public CVDBottone bottone(Modulo modulo, String nomeIcona, String tooltip) {
            return VideoFactory.bottone(campo, modulo, nomeIcona, tooltip);
        }


        public CVDBottone bottone2stati(Icon icona0, Icon icona1) {
            return VideoFactory.bottone2stati(campo, icona0, icona1);
        }


        public CVDBottone lucchetto() {
            return VideoFactory.lucchetto(campo);
        }


        public CVDBottone lucchetto(boolean chiuso) {
            return VideoFactory.lucchetto(campo, chiuso);
        }


        public CVDAzione azione(Azione azione) {
            return VideoFactory.azione(campo, azione);
        }


        public CVDObbligatorio obbligatorio() {
            return VideoFactory.obbligatorio(campo);
        }


        public void eliminaObbligatorio() {
            VideoFactory.eliminaObligatorio(campo.getCampoVideo());
        }


        public CVDCongelato congelato(String nomeCampo) {
            return VideoFactory.congelato(campo.getCampoVideo(), nomeCampo);
        }


        public CVDPreferenza preferenza(Object valore) {
            return VideoFactory.preferenza(campo.getCampoVideo(), valore);
        }


        public CVDPreferenza preferenza(Object valore, String notaUte) {
            return VideoFactory.preferenza(campo.getCampoVideo(), valore, notaUte);
        }


        private Campo getCampo() {
            return campo;
        }


        public void setCampo(Campo campo) {
            this.campo = campo;
        }

    } // fine della classe 'interna'

}// fine della classe
