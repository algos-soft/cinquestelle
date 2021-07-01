/**
 * Copyright: Copyright (c) 2006
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      4-ott-2006
 */
package it.algos.base.combo;

import it.algos.base.campo.video.CVCombo;
import it.algos.base.errore.Errore;
import it.algos.base.portale.Portale;

import javax.swing.*;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.awt.event.*;

/**
 * //@TODO DESCRIZIONE SINTETICA DELLA CLASSE .
 * </p>
 * Questa classe: <ul>
 * <li> </li>
 * <li> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 4-ott-2006 ore 11.24.09
 */
public abstract class ComboBase implements Combo {

    /* larghezza di default del JComboBox */
    protected static int LARGHEZZA_DEFAULT = 150;

    /* campo video di riferimento */
    private CVCombo campoVideo;

    /**
     * flag - se il combo usa la funzione Nuovo Record
     */
    private boolean usaNuovo = false;

    /**
     * flag - se il combo usa la funzione Modifica Record
     */
    private boolean usaModifica = false;


    /**
     * Costruttore completo senza parametri.
     */
    public ComboBase(CVCombo campoVideo) {
        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza coi parametri */
        this.setCampoVideo(campoVideo);

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
    }// fine del metodo inizia


    /**
     * Regolazioni dinamiche dell'oggetto.
     * <p/>
     * Regola le caratteristiche dinamiche in base alla impostazione
     * corrente delle caratteristiche statiche <br>
     * Metodo invocato dalla classe che crea questo oggetto (di norma) <br>
     * Viene eseguito una sola volta <br>
     * Invoca il metodo sovrascritto della superclasse <br>
     */
    public void inizializza() {
    }


    /**
     * Avvio dell'oggetto.
     * <p/>
     */
    public void avvia() {
    }


    /**
     * Aggiunge i Listeners al combo.
     * <p/>
     *
     * @param portale contenente le azioni
     */
    public void aggiungeListener(Portale portale) {
    }


    /**
     * Abilita/disabilita il combo.
     * <p/>
     * Se disabilitato sbiadisce tutti gli elementi
     * e impedisce di modificare il valore
     *
     * @param flag true per abilitare, false per disabilitare
     */
    public void setAbilitato(boolean flag) {
        this.getComponente().setEnabled(flag);
    }


    public void addItemListener(ItemListener unListener) {
    }


    public void addPopupMenuListener(PopupMenuListener unListener) {
    }


    public void addActionListener(ActionListener unListener) {
    }


    public void addMouseListener(MouseListener unListener) {
    }


    public void addKeyListener(KeyListener unListener) {
    }


    public void removeItemListener(ItemListener unListener) {
    }


    public void removePopupMenuListener(PopupMenuListener unListener) {
    }


    public void removeActionListener(ActionListener unListener) {
    }


    public void setValori(Object[] valori) {
    }


    /**
     * Aggiorna la GUI con il valore dal video.
     * <p/>
     */
    public void aggiornaGUI() {
    }


    public void setSelectedItem(Object unOggetto) {
    }


    public void setSelectedIndex(int unIndice) {
    }


    public ComboLista getComboLista() {
        return null;
    }


    public Object getSelectedItem() {
        return null;
    }


    public int getSelectedIndex() {
        return 0;
    }


    public int getItemCount() {
        return 0;
    }


    /**
     * Regola la larghezza del componente selezionabile
     * o editabile del combo
     * <p/>
     * In un ComboLista, assegna la larghezza al JComboBox <br>
     * In un ComboTavola, assegna la larghezza al componente editabile <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @param larghezza da assegnare
     */
    public abstract void setLarghezza(int larghezza);


    /**
     * Regola l'altezza del componente selezionabile
     * o editabile del combo
     * <p/>
     * In un ComboLista, assegna l'altezza al JComboBox <br>
     * In un ComboTavola, assegna l'altezza al componente editabile <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @param altezza da assegnare
     */
    public abstract void setAltezza(int altezza);


    /**
     * Invocato quando il combo ha
     * acquisito il fuoco in maniera permanente.
     * <p/>
     *
     * @param e l'evento fuoco
     */
    public void focusGainedComponente(FocusEvent e) {
        this.getCampoVideo().entrataCampo(e);
    }


    /**
     * Invocato quando il combo ha
     * perso il fuoco in maniera permanente.
     * <p/>
     *
     * @param e l'evento fuoco
     */
    public void focusLost(FocusEvent e) {
    }


    /**
     * Richiede il fuoco sul componente editabile del combo.
     * <p/>
     */
    public void requestFocus() {
        /* variabili e costanti locali di lavoro */
        Component comp;
        JComponent jComp;

        try { // prova ad eseguire il codice
            comp = this.getComponenteSelettore();
            if (comp != null) {
                if (comp instanceof JComponent) {
                    jComp = (JComponent)comp;
                    jComp.requestFocusInWindow();
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    protected CVCombo getCampoVideo() {
        return campoVideo;
    }


    private void setCampoVideo(CVCombo campoVideo) {
        this.campoVideo = campoVideo;
    }


    /**
     * Controlla se il combo usa la funzione Nuovo Record.
     * <p/>
     *
     * @return true se il combo usa la funzione Nuovo Record
     */
    protected boolean isUsaNuovo() {
        return usaNuovo;
    }


    /**
     * Controlla l'uso della funzione Nuovo Record.
     * <p/>
     *
     * @param flag per usare la funzione Nuovo Record
     */
    public void setUsaNuovo(boolean flag) {
        try { // prova ad eseguire il codice
            this.usaNuovo = flag;
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Controlla se il combo usa la funzione Modifica Record.
     * <p/>
     *
     * @return true se il combo usa la funzione Modifica Record
     */
    protected boolean isUsaModifica() {
        return usaModifica;
    }


    /**
     * Controlla l'uso della funzione Modifica Record.
     * <p/>
     *
     * @param flag per usare la funzione Modifica Record
     */
    public void setUsaModifica(boolean flag) {
        this.usaModifica = flag;
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
     */
    public Combo clona() {
        return null;
    }

}// fine della classe
