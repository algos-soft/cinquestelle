/**
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 4 luglio 2003 alle 11.06
 */
package it.algos.base.campo.video.decorator;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoAstratto;
import it.algos.base.campo.scheda.CampoScheda;
import it.algos.base.campo.video.CVBase;
import it.algos.base.campo.video.CampoVideo;
import it.algos.base.combo.Combo;
import it.algos.base.errore.Errore;
import it.algos.base.evento.BaseListener;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloBase;
import it.algos.base.portale.Portale;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.text.JTextComponent;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.FocusEvent;


/**
 * Decoratore astratto della classe CampoVideo.
 * <p/>
 * Questa classe astratta: <ul>
 * <li> Implementa il <i>design pattern</i> <b>Decorator</b> </li>
 * <li> Implementa (vuoti) tutti i metodi dell'interfaccia <code>campoVideo</code> </li>
 * <li> Mantiene il riferimento al CampoVideo decorato da una sottoclasse
 * di questa classe </li>
 * <li> Rinvia al CampoVideo decorato tutti i metodi </li>
 * <li> Intercetta nelle sottoclassi i metodi che richiedono una elaborazione
 * locale prima di essere rinviati al al CampoVideo decorato </li>
 * <ul/>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  4 luglio 2003 ore 11.06
 */
public abstract class CVDecoratore extends CampoAstratto implements CampoVideo {

    /**
     * Oggetto di tipo CampoVideo che viene decorato da una sottoclasse di questa classe
     */
    private CampoVideo campoVideoDecorato = null;


    /**
     * Costruttore completo con parametri.
     *
     * @param unCampoVideoDecorato oggetto da decorare
     */
    public CVDecoratore(CampoVideo unCampoVideoDecorato) {
        /* rimanda al costruttore della superclasse */
        super(unCampoVideoDecorato.getCampoParente());

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
    }


    /**
     * Regolazioni dinamiche dell'oggetto.
     * <p/>
     * Regola le caratteristiche dinamiche in base alla impostazione
     * corrente delle caratteristiche statiche <br>
     * Metodo invocato dalla classe che crea questo oggetto <br>
     * Viene eseguito una sola volta <br>
     * <p/>
     * Sovrascritto nelle sottoclassi <br>
     */
    public void inizializza() {
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
    }


    /**
     * Crea gli elementi GUI del pannelloCampo.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Gli elementi possono essere anche pi� di uno <br>
     * Gli elementi vengono aggiunti al pannelloCampo <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     *
     * @see #regolaElementi()
     */
    protected void creaComponentiGUI() {
    }


    /**
     * Regola le caratteristiche grafiche degli elementi GUI.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Regola font, colori, bordi e sfondi di tutti gli elementi GUI
     * del pannelloCampo <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     *
     * @see it.algos.base.campo.video.CVBase#inizializza()
     */
    protected void regolaFontColori() {
    }


    /**
     * Regola le dimensioni degli elementi.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Gli elementi possono essere anche pi� di uno <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     */
    protected void regolaElementi() {
    }


    /**
     * Aggiunge il componente Legenda al pannelloCampo nella posizione prevista.
     * <p/>
     */
    public void addComponente(Component comp, Pos pos) {
        /* manda avanti il metodo all'oggetto da decorare */
        this.getCampoVideoDecorato().addComponente(comp, pos);
    }


    /**
     * Regola le dimensioni del pannello Campo.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Blocca la dimensione del pannello Campo
     * alla attuale dimensione preferita.
     */
    public void pack() {
        /* manda avanti il metodo all'oggetto da decorare */
        this.getCampoVideoDecorato().pack();
    }


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
    public void regolaDimensioneComponenti() {
        /* manda avanti il metodo all'oggetto da decorare */
        this.getCampoVideoDecorato().regolaDimensioneComponenti();
    }


    /**
     * Aggiorna la GUI col valore video.
     * <p/>
     * Metodo invocato dal ciclo avvia di SchedaBase <br>
     * Regola il componente GUI del campoVideo con il valore <br>
     * <p/>
     * Intercetta la chiamata al metodo dell'oggetto da decorare <br>
     *
     * @param unValore valore video proveniente dal CampoDati
     *
     * @see it.algos.base.scheda.SchedaBase#caricaValori
     * @see it.algos.base.campo.logica.CLBase#memoriaGui()
     */
    public void aggiornaGUI(Object unValore) {
        /* manda avanti il metodo all'oggetto da decorare */
        this.getCampoVideoDecorato().aggiornaGUI(unValore);
    }


    /**
     * Metodo eseguito quando il valore memoria di un campo cambia.
     * <p/>
     * Invoca i metodi delegati <br>
     */
    public void eventoMemoriaModificata() {
        /* manda avanti il metodo all'oggetto da decorare */
        this.getCampoVideoDecorato().eventoMemoriaModificata();
    }


    /**
     * Metodo eseguito quando il valore video di un campo cambia.
     * <p/>
     * Invoca i metodi delegati <br>
     */
    public void eventoVideoModificato() {
        /* manda avanti il metodo all'oggetto da decorare */
        this.getCampoVideoDecorato().eventoVideoModificato();
    }


    /**
     * Recupera dalla GUI il valore video.
     * <p/>
     * Metodo invocato da isModificata() e da modificaCampo() <br>
     * Intercetta la chiamata al metodo dell'oggetto da decorare <br>
     *
     * @return valore video per il CampoDati
     *
     * @see it.algos.base.navigatore.NavigatoreBase#modificaCampo(it.algos.base.campo.base.Campo)
     * @see it.algos.base.scheda.SchedaBase#isModificata()
     * @see it.algos.base.campo.logica.CLBase#guiMemoria()
     */
    public Object recuperaGUI() {
        /* manda avanti il metodo all'oggetto da decorare */
        return this.getCampoVideoDecorato().recuperaGUI();
    }


    /**
     * elimina i componenti GUI del pannello
     */
    public void eliminaPannelloComponenti() {
    } /* fine del metodo */


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
        this.getCampoVideoDecorato().aggiungeListener(azione);
    }


    /**
     * Aggiunge i <code>Listener</code>.
     * <p/>
     * Aggiunge ai componenti video di questo campo gli eventuali
     * ascoltatori dei comandi (eventi) <br>
     * Metodo invocato da SchedaBase.inizializza <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @param portale il portale di riferimento del campo
     */
    public void aggiungeListener(Portale portale) {
        this.getCampoVideoDecorato().aggiungeListener(portale);
    } /* fine del metodo */


    /**
     * metodo setter per modificare il valore della variabile privata
     */
    public void setCampoVideoDecorato(CampoVideo campoVideoDecorato) {
        this.campoVideoDecorato = campoVideoDecorato;
    }


    public void setOrdinabile(boolean ordinabile) {
        /* manda avanti il metodo all'oggetto da decorare */
        this.getCampoVideoDecorato().setOrdinabile(ordinabile);
    }


    /**
     * Rende visibile/invisibile il PannelloCampo a video.
     */
    public void setVisibile(boolean visibile) {
        /* manda avanti il metodo all'oggetto da decorare */
        this.getCampoVideoDecorato().setVisibile(visibile);
    }


    /**
     * Determina se il Pannello Campo e' visibile.
     */
    public boolean isVisibile() {
        /* manda avanti il metodo all'oggetto da decorare */
        return this.getCampoVideoDecorato().isVisibile();
    }


    /**
     * Regola il testo descrittivo del checkbox.
     * <br>
     * Sovrascritto nella sottoclasse specifica <br>
     *
     * @param unTestoCheckBox testo descrittivo <br>
     */
    public void setTestoComponente(String unTestoCheckBox) {
        /* manda avanti il metodo all'oggetto da decorare */
        this.getCampoVideoDecorato().setTestoComponente(unTestoCheckBox);
    }


    /**
     * metodo getter per ottenere il valore della variabile privata
     */
    public CampoVideo getCampoVideoDecorato() {
        return this.campoVideoDecorato;
    } /* fine del metodo getter */


    /**
     * Recupera il pannello disegnato nella GUI.
     * <p/>
     * Pannello finale che viene inserito (disegnato) nella scheda <br>
     * Puo' essere composto di diversi oggetti affatto diversi <br>
     *
     * @return il pannello completo che viene inserito nella GUI
     */
    public PannelloBase getPannelloBaseCampo() {
        /* manda avanti il metodo all'oggetto da decorare */
        return this.getCampoVideoDecorato().getPannelloBaseCampo();
    } /* fine del metodo */


    /**
     * pannello dei componenti base (uno o piu')
     * puo' essere composto di diversi oggetti affatto diversi
     */
    public PannelloBase getPannelloBaseComponenti() {
        /* manda avanti il metodo all'oggetto da decorare */
        return this.getCampoVideoDecorato().getPannelloBaseComponenti();
    } /* fine del metodo getter */


    /**
     * Restituisce il riferimento al singolo componente del pannelloComponenti.
     * <p/>
     * Se il pannelloComponenti prevede diversi componenti, restituisce null <br>
     *
     * @return componente GUI singolo
     */
    public JComponent getComponente() {
        /* manda avanti il metodo all'oggetto da decorare */
        return this.getCampoVideoDecorato().getComponente();
    }

//    /**
//     * Regola l'altezza del pannelloComponenti.
//     * <p/>
//     * Metodo invocato dal Layout quando deve posizionare il campo <br>
//     * Viene utilizzato per il dimensionamento dall'esterno <br>
//     * Metodo sovrascritto nelle sottoclassi <br>
//     *
//     * @param altezza da assegnare al pannelloComponenti
//     */
//    public void setAltezzaPannelloComponenti(int altezza) {
//        /* manda avanti il metodo all'oggetto da decorare */
//        this.getCampoVideoDecorato().setAltezzaPannelloComponenti(altezza);
//    }


    /**
     * Pannello campo del CampoVideo decorato.
     *
     * @return pannello campo
     */
    private JPanel getPannelloCampo() {
        /* variabili e costanti locali di lavoro */
        JPanel pannello = null;

        try { // prova ad eseguire il codice
            if (this.getCampoVideoDecorato() != null) {
                pannello = this.getCampoVideoDecorato().getPannelloBaseCampo();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return pannello;
    }


    /**
     * Pannello componenti del CampoVideo decorato.
     *
     * @return pannello campo
     */
    private JPanel getPannelloComponenti() {
        /* variabili e costanti locali di lavoro */
        JPanel pannello = null;

        try { // prova ad eseguire il codice
            if (this.getCampoVideoDecorato() != null) {
                pannello = this.getCampoVideoDecorato().getPannelloBaseComponenti();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return pannello;
    }


    /**
     * Altezza del pannello campo del CampoVideo decorato.
     *
     * @return altezza in pixel
     */
    protected int getAltezzaCampo() {
        /* variabili e costanti locali di lavoro */
        int altezza = 0;
        JPanel pannello;

        try { // prova ad eseguire il codice
            pannello = this.getPannelloCampo();
            if (pannello != null) {
                altezza = pannello.getHeight();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return altezza;
    }


    /**
     * Larghezza del pannello campo del CampoVideo decorato.
     *
     * @return larghezza in pixel
     */
    protected int getLarghezzaCampo() {
        /* variabili e costanti locali di lavoro */
        int larghezza = 0;
        JPanel pannello;

        try { // prova ad eseguire il codice
            pannello = this.getPannelloCampo();
            if (pannello != null) {
                larghezza = pannello.getWidth();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return larghezza;
    }


    /**
     * Altezza del pannello componenti del CampoVideo decorato.
     *
     * @return altezza in pixel
     */
    protected int getAltezzaComponenti() {
        /* variabili e costanti locali di lavoro */
        int altezza = 0;
        JPanel pannello;

        try { // prova ad eseguire il codice
            pannello = this.getPannelloComponenti();
            if (pannello != null) {
                altezza = pannello.getHeight();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return altezza;
    }


    /**
     * Larghezza del pannello componenti del CampoVideo decorato.
     *
     * @return larghezza in pixel
     */
    protected int getLarghezzaComponenti() {
        /* variabili e costanti locali di lavoro */
        int larghezza = 0;
        JPanel pannello;

        try { // prova ad eseguire il codice
            pannello = this.getPannelloComponenti();
            if (pannello != null) {
                larghezza = pannello.getWidth();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return larghezza;
    }


    /**
     * Ascissa del pannello componenti del CampoVideo decorato.
     *
     * @return distanza dal bordo sinistro del pannello campo
     */
    protected int getxComponenti() {
        /* variabili e costanti locali di lavoro */
        int distanza = 0;
        JPanel pannello;

        try { // prova ad eseguire il codice
            pannello = this.getPannelloComponenti();
            if (pannello != null) {
                distanza = pannello.getX();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return distanza;
    }


    /**
     * Ordinata del pannello componenti del CampoVideo decorato.
     *
     * @return distanza dal bordo superiore del pannello campo
     */
    protected int getyComponenti() {
        /* variabili e costanti locali di lavoro */
        int distanza = 0;
        JPanel pannello;

        try { // prova ad eseguire il codice
            pannello = this.getPannelloComponenti();
            if (pannello != null) {
                distanza = pannello.getY();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return distanza;
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
        /* manda avanti il metodo all'oggetto da decorare */
        this.getCampoVideoDecorato().setNumeroRighe(numeroRighe);
    }


    /**
     * Seleziona tutto il contenuto del campo.
     * <p/>
     */
    public void selectAll() {
        /* manda avanti il metodo all'oggetto da decorare */
        this.getCampoVideoDecorato().selectAll();
    }


    public void setAltezzaRadioBottone(int altezzaRadioBottone) {
        /* manda avanti il metodo all'oggetto da decorare */
        this.getCampoVideoDecorato().setAltezzaRadioBottone(altezzaRadioBottone);
    }


    /**
     * Restituisce il CampoScheda associato a questo CampoVideo.
     * <p/>
     */
    public CampoScheda getCampoScheda() {
        /* valore di ritorno */
        return this.getCampoParente().getCampoScheda();
    }


    public void setCombo(Combo combo) {
        /* manda avanti il metodo all'oggetto da decorare */
        this.getCampoVideoDecorato().setCombo(combo);
    }


    /**
     * Controlla se il combo usa la funzione Nuovo Record.
     * <p/>
     *
     * @return true se il combo usa la funzione Nuovo Record
     */
    public boolean isUsaNuovo() {
        return this.getCampoVideoDecorato().isUsaNuovo();
    }


    /**
     * Controlla l'uso della funzione Nuovo Record.
     * <p/>
     *
     * @param flag per usare la funzione Nuovo Record
     */
    public void setUsaNuovo(boolean flag) {
        this.getCampoVideoDecorato().setUsaNuovo(flag);
    }


    /**
     * Controlla se il combo usa la funzione Modifica Record.
     * <p/>
     *
     * @return true se il combo usa la funzione Modifica Record
     */
    public boolean isUsaModifica() {
        return this.getCampoVideoDecorato().isUsaModifica();
    }


    /**
     * Controlla l'uso della funzione Modifica Record.
     * <p/>
     *
     * @param flag per usare la funzione Modifica Record
     */
    public void setUsaModifica(boolean flag) {
        this.getCampoVideoDecorato().setUsaModifica(flag);
    }


    /**
     * Controlla l'uso della voce Non Specificato.
     * <p/>
     *
     * @return true se usa la voce Non Specificato
     */
    public boolean isUsaNonSpecificato() {
        return this.getCampoVideoDecorato().isUsaNonSpecificato();
    }


    /**
     * Controlla l'uso della voce Non Specificato.
     * <p/>
     *
     * @param flag per usare la voce Non Specificato
     */
    public void setUsaNonSpecificato(boolean flag) {
        this.getCampoVideoDecorato().setUsaNonSpecificato(flag);
    }


    /**
     * Prende il fuoco sul componente del campo video.
     * <p/>
     */
    public void grabFocus() {
        /* manda avanti il metodo all'oggetto da decorare */
        this.getCampoVideoDecorato().grabFocus();
    }


    public CVBase getCVBase() {
        /* manda avanti il metodo all'oggetto da decorare */
        return this.getCampoVideoDecorato().getCVBase();
    }


    /**
     * Regola l'orientamento del layout del Pannello Componenti.
     * <p/>
     *
     * @param orientamento il codice di orientamento
     * Layout.ORIENTAMENTO_ORIZZONTALE o Layout.ORIENTAMENTO_VERTICALE
     */
    public void setOrientamentoComponenti(int orientamento) {
        /* manda avanti il metodo all'oggetto da decorare */
        this.getCampoVideoDecorato().setOrientamentoComponenti(orientamento);
    }


    /**
     * Regola il gap tra i componenti del gruppo (radio o check).
     *
     * @param gap la distanza tra i componenti
     */
    public void setGapGruppo(int gap) {
        /* manda avanti il metodo all'oggetto da decorare */
        this.getCampoVideoDecorato().setGapGruppo(gap);
    }


    /**
     * Crea i componenti GUI del pannelloComponenti.
     * <p/>
     * Metodo invocato dal ciclo inizia <br>
     * E' responsabilita' di questo metodo:
     * - creare i componenti GUI interni al PannelloComponenti<br>
     * - eventualmente assegnare un layout al PannelloComponenti<br>
     * - aggiungere i componenti al pannelloComponenti<br>
     */
    public void creaComponentiInterni() {
        /* manda avanti il metodo all'oggetto da decorare */
        this.getCampoVideoDecorato().creaComponentiInterni();
    }


    /**
     * Campo obbligatorio per registrazione / conferma
     */
    public boolean isObbligatorio() {
        /* manda avanti il metodo all'oggetto da decorare */
        return this.getCampoVideoDecorato().isObbligatorio();
    }


    /**
     * Ritorna lo stato corrente dell'attributo Focusable del campo.
     * <p/>
     *
     * @return true se focusable, false se non focusable
     */
    public boolean isFocusable() {
        /* manda avanti il metodo all'oggetto da decorare */
        return this.getCampoVideoDecorato().isFocusable();
    }


    /**
     * Rende il campo focusable o non focusable.
     * <p/>
     * Metodo sovrascritto dalle sottoclassi<br>
     *
     * @param flag di controllo dell'attributo Focusable
     */
    public void setFocusable(boolean flag) {
        /* manda avanti il metodo all'oggetto da decorare */
        this.getCampoVideoDecorato().setFocusable(flag);
    }


    /**
     * Ritorna il colore di primo piano.
     * <p/>
     *
     * @return il colore di primo piano
     */
    public Color getForegroundColor() {
        /* manda avanti il metodo all'oggetto da decorare */
        return this.getCampoVideoDecorato().getForegroundColor();
    }


    /**
     * Assegna il colore di primo piano.
     * <p/>
     *
     * @param colore da assegnare
     */
    public void setForegroundColor(Color colore) {
        /* manda avanti il metodo all'oggetto da decorare */
        this.getCampoVideoDecorato().setForegroundColor(colore);
    }


    /**
     * Ritorna il colore di sfondo.
     * <p/>
     *
     * @return il colore di sfondo
     */
    public Color getBackgroundColor() {
        /* manda avanti il metodo all'oggetto da decorare */
        return this.getCampoVideoDecorato().getBackgroundColor();
    }


    /**
     * Assegna il colore di sfondo.
     * <p/>
     *
     * @param colore da assegnare
     */
    public void setBackgroundColor(Color colore) {
        /* manda avanti il metodo all'oggetto da decorare */
        this.getCampoVideoDecorato().setBackgroundColor(colore);
    }


    /**
     * Ritorna il font del campo.
     * <p/>
     *
     * @return il font del campo
     */
    public Font getFont() {
        /* manda avanti il metodo all'oggetto da decorare */
        return this.getCampoVideoDecorato().getFont();
    }


    /**
     * Assegna il font al campo.
     * <p/>
     *
     * @param font da assegnare
     */
    public void setFont(Font font) {
        /* manda avanti il metodo all'oggetto da decorare */
        this.getCampoVideoDecorato().setFont(font);
    }


    /**
     * Recupera lo stato corrente di modificabilita'
     * del componente GUI del campo per la scheda.
     * <p/>
     *
     * @return lo stato di abilitazione del componente GUI
     */
    public boolean isModificabile() {
        /* manda avanti il metodo all'oggetto da decorare */
        return this.getCampoVideoDecorato().isModificabile();
    }


    /**
     * Regola il componente GUI per essere modificabile o meno.
     * <p/>
     * Metodo sovrascritto dalle sottoclassi<br>
     *
     * @param modificabile true se il campo deve essere modificabile
     */
    public void setModificabile(boolean modificabile) {
        /* manda avanti il metodo all'oggetto da decorare */
        this.getCampoVideoDecorato().setModificabile(modificabile);
    }


    /**
     * Ritorna il testo dell'etichetta
     * <p/>
     *
     * @return il testo dell'etichetta
     */
    public String getTestoEtichetta() {
        /* manda avanti il metodo all'oggetto da decorare */
        return this.getCampoVideoDecorato().getTestoEtichetta();
    }


    /**
     * Ritorna la larghezza in pixel dell'etichetta
     * <p/>
     *
     * @return la larghezza in pixel dell'etichetta
     */
    public int getLarghezzaEtichetta() {
        /* manda avanti il metodo all'oggetto da decorare */
        return this.getCampoVideoDecorato().getLarghezzaEtichetta();
    }


    /**
     * Regola la larghezza in pixel dell'etichetta
     * <p/>
     *
     * @param larghezza in pixel dell'etichetta
     */
    public void setLarghezzaEtichetta(int larghezza) {
        /* manda avanti il metodo all'oggetto da decorare */
        this.getCampoVideoDecorato().setLarghezzaEtichetta(larghezza);
        this.getCampoVideoDecorato().pack();
    }


    /**
     * Regola (sinistra o destra) l'allineamento del testo dell'etichetta
     * <p/>
     *
     * @param bandiera tipo di allineamento
     */
    public void setAllineamentoEtichetta(Pannello.Bandiera bandiera) {
        /* manda avanti il metodo all'oggetto da decorare */
        this.getCampoVideoDecorato().setAllineamentoEtichetta(bandiera);
    }


    /**
     * Controlla se il campo ha l'etichetta e se questa è a sinistra.
     * <p/>
     *
     * @return vero se esiste l'etichetta ed è a sinistra
     */
    public boolean isEtichettaSinistra() {
        /* manda avanti il metodo all'oggetto da decorare */
        return this.getCampoVideoDecorato().isEtichettaSinistra();
    }


    /**
     * Regola l'allineamento del testo nella scheda.
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
        /* manda avanti il metodo all'oggetto da decorare */
        this.getCampoVideoDecorato().setAllineamento(allineamento);
    }


    /**
     * Determina se e come il campo si espande nel proprio contenitore
     * <p/>
     *
     * @param espandibilita codifica del tipo di espandibilità
     */
    public void setEspandibilita(Espandibilita espandibilita) {
        /* manda avanti il metodo all'oggetto da decorare */
        this.getCampoVideoDecorato().setEspandibilita(espandibilita);
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
        /* manda avanti il metodo all'oggetto da decorare */
        return this.getCampoVideoDecorato().getTestoComponente();
    }


    public JTextComponent getComponenteTesto() {
        /* manda avanti il metodo all'oggetto da decorare */
        return this.getCampoVideoDecorato().getComponenteTesto();
    }



    public JToggleButton getComponenteToggle() {
        /* manda avanti il metodo all'oggetto da decorare */
        return this.getCampoVideoDecorato().getComponenteToggle();
    }


    /**
     * Classe interna per passare i metodi della classe VideoFactory
     */
    public CVBase.Decora decora() {
        /* manda avanti il metodo all'oggetto da decorare */
        return this.getCampoVideoDecorato().decora();
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
        /* manda avanti il metodo all'oggetto da decorare */
        return this.getCampoVideoDecorato().getCampoForm(nome);
    }


    public boolean isSelezionato() {
        /* manda avanti il metodo all'oggetto da decorare */
        return this.getCampoVideoDecorato().isSelezionato();
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
        this.getCampoVideoDecorato().entrataCampo(e);
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
        this.getCampoVideoDecorato().uscitaCampo(e);
    }


    /**
     * Invocato quando si entra in un campo
     * <p/>
     * (il campo prende il fuoco in maniera definitiva).
     */
    public void entrataCampo() {
        this.getCampoVideoDecorato().entrataCampo();
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
        this.getCampoVideoDecorato().uscitaCampo();
    }


    /**
     * Gestione evento gui modificata.
     * <p/>
     * Metodo invocato dal campo logica <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    public void guiModificata() {
        this.getCampoVideoDecorato().guiModificata();
    }


    /**
     * Aggiunge una colonna del modulo linkato alla Vista per il combo.
     * <p/>
     *
     * @param nome del campo del modulo linkato
     */
    public void addColonnaCombo(String nome) {
        this.getCampoVideoDecorato().addColonnaCombo(nome);
    }


    /**
     * Aggiunge una colonna alla Vista per il combo.
     * <p/>
     *
     * @param nomeModulo nome del modulo
     * @param nomeCampo nome del campo
     */
    public void addColonnaCombo(String nomeModulo, String nomeCampo) {
        this.getCampoVideoDecorato().addColonnaCombo(nomeModulo, nomeCampo);
    }


    public void setInizializzato(boolean inizializzato) {
        this.getCampoVideoDecorato().setInizializzato(inizializzato);
    }


    /**
     * Flag di controllo per disegnare provvisoriamente sfondi colorati.
     *
     * @return vero se attivo
     */
    protected boolean isDebug() {
        return Campo.DEBUG;
    }


    /**
     * Elimina gli effetti di un decoratore.
     * <p/>
     * Possono essere grafici o logici <br>
     */
    public void remove() {
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
    public CVDecoratore clonaCampo(Campo unCampoParente) {
        /* variabili e costanti locali di lavoro */
        CVDecoratore unCampo;
        CampoVideo campoVideo;

        try { // prova ad eseguire il codice
            /* invoca il metodo sovrascritto della superclasse Object */
            unCampo = (CVDecoratore)super.clone();
        } catch (CloneNotSupportedException unErrore) { // intercetta l'errore
            throw new InternalError();
        }// fine del blocco try-catch

        try { // prova ad eseguire il codice
            /* modifica il riferimento al campo parente */
            unCampo.setCampoParente(unCampoParente);

            /* clona il campo decorato */
            campoVideo = this.getCampoVideoDecorato();
            if (campoVideo != null) {
                campoVideo = campoVideo.clonaCampo(unCampoParente);
                unCampo.setCampoVideoDecorato(campoVideo);
            } /* fine del blocco if */
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unCampo;
    }


    /**
     * Classe interna Enumerazione.
     */
    public enum Pos {

        SOPRA(),
        SINISTRA(),
        SOTTO(),
        DESTRA()

    }// fine della classe


}// fine della classe