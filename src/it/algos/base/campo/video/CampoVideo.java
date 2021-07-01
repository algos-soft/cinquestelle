/**
 * Title:        CampoVideo.java
 * Package:      it.algos.base.campo.video
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Ceresa, Colombo, Valbonesi
 * @version 1.0  /
 * Creato:       il 13 settembre 2003 alle 15.17
 */
package it.algos.base.campo.video;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.scheda.CampoScheda;
import it.algos.base.campo.video.decorator.CVDecoratore;
import it.algos.base.combo.Combo;
import it.algos.base.costante.CostanteColore;
import it.algos.base.errore.Errore;
import it.algos.base.evento.BaseListener;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloBase;
import it.algos.base.portale.Portale;

import javax.swing.JComponent;
import javax.swing.JToggleButton;
import javax.swing.text.JTextComponent;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.event.FocusEvent;


/**
 * Questa interfaccia e' responsabile di: <br>
 * A - Regola le funzionalita di gestione di una campo a video nella Scheda <br>
 *
 * @author Guido Andrea Ceresa
 * @author Alberto Colombo
 * @author Alessandro Valbonesi
 * @author gac
 * @version 1.0  /  13 settembre 2003 ore 15.17
 */
public interface CampoVideo extends Cloneable {

    /**
     * flag per attivare il debug del campo video
     */
    public static final boolean DEBUG = false;

    /**
     * delta correttivo verticale per il campo JTextField nella GUI
     * da aggiungere a quanto calcolato per il Font utilizzato
     */
    public static final int DELTA_Y_TESTO = 4;

    /**
     * delta correttivo verticale per il campo JTextArea nella GUI
     * da aggiungere a quanto calcolato per il Font utilizzato
     */
    public static final int DELTA_Y_TESTO_AREA = 3;

    /**
     * delta correttivo verticale per il campo JComboBox nella GUI
     * da aggiungere a quanto calcolato per il Font utilizzato
     */
    public static final int DELTA_Y_COMBO = 5;

    /**
     * Colore del testo quando e' disabilitato
     */
    public static final Color COLORE_TESTO_DISABILITATO = CostanteColore.GRIGIO_SCURO;


    /**
     * Regolazioni iniziali <i>una tantum</i>.
     * </p>
     * Metodo chiamato dalla classe che crea questo oggetto <br>
     * Viene eseguito una sola volta <br>
     */
    public abstract void inizializza();


    /**
     * Regolazioni di ri-avvio.
     * <p/>
     * Metodo invocato da altre classi (o anche da questa) ogni volta che questo oggetto
     * deve <i>ripartire</i>, per essere sicuri che sia <i>pulito</i> <br>
     * Viene eseguito tutte le volte che necessita <br>
     * <p/>
     * Sovrascritto nelle sottoclassi <br>
     */
    public abstract void avvia();


    /**
     * Crea i componenti interni al pannelloComponenti.
     * <p/>
     * Metodo invocato dal ciclo inizia o inizializza, a seconda
     * delle esigenze del tipo specifico di campo <br>
     * E' responsabilita' di questo metodo:
     * - creare i componenti GUI interni al PannelloComponenti<br>
     * - eventualmente assegnare un layout al PannelloComponenti<br>
     * - aggiungere i componenti al pannelloComponenti<br>
     * E' pubblico perché invocato anche dal metodo clone()
     */
    public abstract void creaComponentiInterni();


    /**
     * Aggiunge il componente Legenda al pannelloCampo nella posizione prevista.
     * <p/>
     */
    public abstract void addComponente(Component comp, CVDecoratore.Pos pos);


    /**
     * Fissa le dimensioni del pannello Campo pari
     * alla alla attuale dimensione preferita del suo contenuto
     * <p/>
     * Metodo invocato dal ciclo avvia <br>
     */
    public abstract void pack();


    /**
     * Regola le dimensioni dei singoli componenti
     * interni al pannelloComponenti.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Può essere invocato in qualsiasi momento.<br>
     * E' responsabilita' di questo metodo attribuire una preferred size
     * ad ogni componente interno al pannello componenti.
     * I componenti possono essere anche più di uno <br>
     */
    public void regolaDimensioneComponenti();


    /**
     * Aggiunge i <code>Listener</code>.
     * <p/>
     * Aggiunge ai componenti video di questo campo gli eventuali
     * ascoltatori delle azioni (eventi) <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @param azione azione da aggiungere
     */
    public abstract void aggiungeListener(BaseListener azione);


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
    public abstract void aggiungeListener(Portale portale);


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
     * Metodo invocato da isModificata() e da modificaCampo() <br>
     *
     * @return valore video per il CampoDati
     *
     * @see it.algos.base.navigatore.NavigatoreBase#modificaCampo(it.algos.base.campo.base.Campo)
     * @see it.algos.base.scheda.SchedaBase#isModificata()
     * @see it.algos.base.campo.logica.CLBase#guiMemoria()
     */
    public abstract Object recuperaGUI();

//    /**
//     * Regola la larghezza del pannelloComponenti.
//     * <p/>
//     * Metodo invocato dal Layout quando deve posizionare il campo <br>
//     * Viene utilizzato per il dimensionamento dall'esterno <br>
//     * Metodo sovrascritto nelle sottoclassi <br>
//     *
//     * @param larghezza da assegnare al pannelloComponenti
//     */
//    public abstract void setLarghezzaPannelloComponenti(int larghezza);

//    /**
//     * Regola l'altezza del pannelloComponenti.
//     * <p/>
//     * Metodo invocato dal Layout quando deve posizionare il campo <br>
//     * Viene utilizzato per il dimensionamento dall'esterno <br>
//     * Metodo sovrascritto nelle sottoclassi <br>
//     *
//     * @param altezza da assegnare al pannelloComponenti
//     */
//    public abstract void setAltezzaPannelloComponenti(int altezza);


    /**
     * Metodo eseguito quando il valore memoria di un campo cambia.
     * <p/>
     * Invoca i metodi delegati <br>
     */
    public abstract void eventoMemoriaModificata();


    /**
     * Metodo eseguito quando il valore video di un campo cambia.
     * <p/>
     * Invoca i metodi delegati <br>
     */
    public abstract void eventoVideoModificato();


    public abstract PannelloBase getPannelloBaseComponenti();


    /**
     * Recupera il pannello disegnato nella GUI.
     * <p/>
     * Pannello finale che viene inserito (disegnato) nella scheda <br>
     * Puo' essere composto di diversi oggetti affatto diversi <br>
     *
     * @return il pannello completo che viene inserito nella GUI
     */
    public abstract PannelloBase getPannelloBaseCampo();


    /**
     * riferimento al campo 'contenitore' dei vari oggetti che
     * insieme svolgono le funzioni del campo
     */
    public abstract void setCampoParente(Campo unCampoParente);


    /**
     * Regola il testo descrittivo del componente.
     * <p/>
     * (checkbox o radio bottone) <br>
     * (cosa diversa dall'etichetta) <br>
     *
     * @param testoComponente testo descrittivo
     */
    public abstract void setTestoComponente(String testoComponente);


    /**
     * Ritorna il testo descrittivo del componente.
     * <p/>
     * (checkbox o radio bottone) <br>
     * (cosa diversa dall'etichetta) <br>
     *
     * @return il testo descrittivo del componente
     */
    public abstract String getTestoComponente();


    public abstract Campo getCampoParente();

//    public abstract void setGapMinimo(Dimension gapMinimo);


    public abstract void setOrdinabile(boolean ordinabile);

//    public abstract boolean isInizializzato();

//    public abstract boolean isValido();


    /**
     * Rende visibile/invisibile il PannelloCampo a video.
     */
    public abstract void setVisibile(boolean visibile);


    /**
     * Determina se il Pannello Campo e' visibile.
     */
    public abstract boolean isVisibile();


    /**
     * Restituisce il riferimento al singolo componente del pannelloComponenti.
     * <p/>
     * Se il pannelloComponenti prevede diversi componenti, restituisce null <br>
     *
     * @return componente GUI singolo
     */
    public abstract JComponent getComponente();


    public abstract void setCombo(Combo combo);


    /**
     * Controlla se il combo usa la funzione Nuovo Record.
     * <p/>
     *
     * @return true se il combo usa la funzione Nuovo Record
     */
    public abstract boolean isUsaNuovo();


    /**
     * Controlla l'uso della funzione Nuovo Record.
     * <p/>
     *
     * @param flag per usare la funzione Nuovo Record
     */
    public abstract void setUsaNuovo(boolean flag);


    /**
     * Controlla se il combo usa la funzione Modifica Record.
     * <p/>
     *
     * @return true se il combo usa la funzione Modifica Record
     */
    public abstract boolean isUsaModifica();


    /**
     * Controlla l'uso della funzione Modifica Record.
     * <p/>
     *
     * @param flag per usare la funzione Modifica Record
     */
    public abstract void setUsaModifica(boolean flag);


    /**
     * Controlla l'uso della voce Non Specificato.
     * <p/>
     *
     * @return true se usa la voce Non Specificato
     */
    public abstract boolean isUsaNonSpecificato();


    /**
     * Controlla l'uso della voce Non Specificato.
     * <p/>
     *
     * @param flag per usare la voce Non Specificato
     */
    public abstract void setUsaNonSpecificato(boolean flag);


    /**
     * Restituisce un campo della scheda.
     * <p/>
     *
     * @param nome del campo
     *
     * @return campo richiesto
     */
    public abstract Campo getCampoForm(String nome);


    public abstract boolean isSelezionato();


    /**
     * Regola l'orientamento del layout del Pannello Componenti.
     * <p/>
     *
     * @param orientamento il codice di orientamento
     * Layout.ORIENTAMENTO_ORIZZONTALE o Layout.ORIENTAMENTO_VERTICALE
     */
    public abstract void setOrientamentoComponenti(int orientamento);


    /**
     * Regola il gap tra i componenti del gruppo (radio o check).
     *
     * @param gap la distanza tra i componenti
     */
    public abstract void setGapGruppo(int gap);


    /**
     * Aggiunge una colonna del modulo linkato alla Vista per il combo.
     * <p/>
     *
     * @param nome del campo del modulo linkato
     */
    public abstract void addColonnaCombo(String nome);


    /**
     * Aggiunge una colonna alla Vista per il combo.
     * <p/>
     *
     * @param nomeModulo nome del modulo
     * @param nomeCampo nome del campo
     */
    public abstract void addColonnaCombo(String nomeModulo, String nomeCampo);


    /**
     * Ritorna una copia profonda dell'oggetto (deep copy) col casting
     */
    public abstract CampoVideo clonaCampo(Campo unCampoParente);


    /**
     * Restituisce il CampoScheda associato a questo CampoVideo.
     * <p/>
     */
    public abstract CampoScheda getCampoScheda();

//    /**
//     * Restituisce il pannello componenti del campo.
//     * <p>
//     * @return il pannello componenti
//     */
//    public abstract Pannello getPannelloComponenti();


    public abstract CVBase getCVBase();


    /**
     * Gestione evento gui modificata.
     * <p/>
     * Metodo invocato dal campo logica <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    public abstract void guiModificata();


    /**
     * Pone il fuoco sul componente del campo video.
     * <p/>
     * Attenzione! il componente deve essere focusable, visibile e displayable,
     * altrimenti la chiamata non ha effetto. <br>
     * Per essere displayable, deve essere effettivamente visualizzato
     * a video, cioe' contenuto in un contenitore che faccia capo a una
     * finestra gia' visibile o gia' packed. <br>
     */
    public abstract void grabFocus();


    /**
     * Regola il numero delle righe.
     * <p/>
     * Numero di righe da visualizzare <br>
     * (l'altezza del pannelloComponenti dipende dalle righe) <br>
     *
     * @param numeroRighe visibili
     */
    public abstract void setNumeroRighe(int numeroRighe);


    /**
     * Seleziona tutto il contenuto del campo.
     * <p/>
     */
    public abstract void selectAll();


    public abstract void setAltezzaRadioBottone(int altezzaRadioBottone);

    /**
     * Ritorna il componente interno di tipo Testo.
     * <p/>
     * (campi di tipo TestoField, TestoArea)
     *
     * @return il componente unico di tipo Testo
     */
    public abstract JTextComponent getComponenteTesto();


    /**
     * Ritorna il componente unico di tipo JToggleButton.
     * <p/>
     * (campi di tipo CVBox)
     *
     * @return il componente unico di tipo JToggleButton
     */
    public abstract JToggleButton getComponenteToggle();


    /**
     * Campo obbligatorio per registrazione / conferma
     */
    public abstract boolean isObbligatorio();


    /**
     * Ritorna lo stato corrente dell'attributo Focusable del campo.
     * <p/>
     *
     * @return true se focusable, false se non focusable
     */
    public abstract boolean isFocusable();


    /**
     * Rende il campo focusable o non focusable.
     * <p/>
     * Metodo sovrascritto dalle sottoclassi<br>
     *
     * @param flag di controllo dell'attributo Focusable
     */
    public abstract void setFocusable(boolean flag);


    /**
     * Recupera lo stato corrente di modificabilita'
     * del componente GUI del campo per la scheda.
     * <p/>
     * Metodo sovrascritto dalle sottoclassi.
     *
     * @return lo stato di abilitazione del componente GUI
     */
    public abstract boolean isModificabile();


    /**
     * Rende il campo modificabile o meno.
     * <p/>
     * Se il flag è true, risponde solo se il campo è abilitato.<br>
     *
     * @param flag true se il campo deve essere modificabile
     */
    public abstract void setModificabile(boolean flag);


    /**
     * Ritorna il testo dell'etichetta
     * <p/>
     *
     * @return il testo dell'etichetta
     *         Se non ha decoratore etichetta ritorna il nome interno
     */
    public abstract String getTestoEtichetta();


    /**
     * Ritorna la larghezza in pixel dell'etichetta
     * <p/>
     *
     * @return la larghezza in pixel dell'etichetta
     */
    public abstract int getLarghezzaEtichetta();


    /**
     * Regola la larghezza in pixel dell'etichetta
     * <p/>
     *
     * @param larghezza in pixel dell'etichetta
     */
    public abstract void setLarghezzaEtichetta(int larghezza);


    /**
     * Regola (sinistra o destra) l'allineamento del testo dell'etichetta
     * <p/>
     *
     * @param bandiera tipo di allineamento
     */
    public abstract void setAllineamentoEtichetta(Pannello.Bandiera bandiera);


    /**
     * Controlla se il campo ha l'etichetta e se questa è a sinistra.
     * <p/>
     *
     * @return vero se esiste l'etichetta ed è a sinistra
     */
    public abstract boolean isEtichettaSinistra();


    /**
     * Ritorna il colore di primo piano.
     * <p/>
     *
     * @return il colore di primo piano
     */
    public abstract Color getForegroundColor();


    /**
     * Assegna il colore di primo piano.
     * <p/>
     *
     * @param colore da assegnare
     */
    public abstract void setForegroundColor(Color colore);


    /**
     * Ritorna il colore di sfondo.
     * <p/>
     *
     * @return il colore di sfondo
     */
    public abstract Color getBackgroundColor();


    /**
     * Assegna il colore di sfondo.
     * <p/>
     *
     * @param colore da assegnare
     */
    public abstract void setBackgroundColor(Color colore);


    /**
     * Ritorna il font del campo.
     * <p/>
     *
     * @return il font del campo
     */
    public abstract Font getFont();


    /**
     * Assegna il font al campo.
     * <p/>
     *
     * @param font da assegnare
     */
    public abstract void setFont(Font font);


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
    public abstract void setAllineamento(int allineamento);


    /**
     * Determina se e come il campo si espande nel proprio contenitore
     * <p/>
     *
     * @param espandibilita codifica del tipo di espandibilità
     */
    public abstract void setEspandibilita(Espandibilita espandibilita);


    /**
     * Invocato quando un qualsiasi componente video
     * del campo acquisisce il fuoco in maniera temporanea
     * o permanente
     * <p/>
     *
     * @param e l'evento fuoco
     */
    public abstract void entrataCampo(FocusEvent e);


    /**
     * Invocato quando un qualsiasi componente video
     * del campo perde il fuoco in maniera temporanea
     * o permanente
     * <p/>
     *
     * @param e l'evento fuoco
     */
    public abstract void uscitaCampo(FocusEvent e);


    /**
     * Invocato quando si entra in un campo
     * <p/>
     * (il campo prende il fuoco in maniera definitiva).
     */
    public abstract void entrataCampo();


    /**
     * Invocato quando si esce da un campo
     * <p/>
     * (il campo perde il fuoco in maniera definitiva).
     * Trasporta il valore da Memoria a GUI<br>
     * Confronta il valore video in ingresso con quello in uscita,
     * se diversi lancia l'evento uscita campo modificato.
     */
    public abstract void uscitaCampo();


    public abstract void setInizializzato(boolean inizializzato);


    /**
     * Classe interna per passare i metodi della classe VideoFactory
     */
    public abstract CVBase.Decora decora();


    /**
     * Tipi di espandibilità del campo video nel proprio contenitore.
     */
    public enum Espandibilita {

        nessuna(GridBagConstraints.NONE),
        orizzontale(GridBagConstraints.HORIZONTAL),
        verticale(GridBagConstraints.VERTICAL),
        entrambe(GridBagConstraints.BOTH),;

        /**
         * codice da GridBagConstraints
         */
        private int constraint;


        /**
         * Costruttore completo con parametri.
         *
         * @param constraint da GridBagConstraints
         */
        Espandibilita(int constraint) {
            try { // prova ad eseguire il codice
                /* regola le variabili di istanza coi parametri */
                this.setConstraint(constraint);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        public int getConstraint() {
            return constraint;
        }


        private void setConstraint(int constraint) {
            this.constraint = constraint;
        }
    }// fine della classe


}// fine della interfaccia