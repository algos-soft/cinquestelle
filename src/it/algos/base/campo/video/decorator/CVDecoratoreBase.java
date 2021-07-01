/**
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 4 luglio 2003 alle 11.06
 */
package it.algos.base.campo.video.decorator;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.scheda.CampoScheda;
import it.algos.base.campo.video.CampoVideo;
import it.algos.base.errore.Errore;
import it.algos.base.pannello.PannelloBase;
import it.algos.base.pannello.PannelloCampo;

import javax.swing.*;
import java.awt.*;

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
public abstract class CVDecoratoreBase extends CVDecoratore {


    /**
     * posizione del componente video (di solito label)
     * rispetto al pannello componenti del pannello campo
     */
    private Pos pos = null;

    /**
     * componente video label di tipo testo
     */
    private JLabel label = null;

    /**
     * flag di controllo
     */
    private boolean flag = false;


    /**
     * Costruttore completo con parametri.
     *
     * @param unCampoVideoDecorato oggetto da decorare
     */
    public CVDecoratoreBase(CampoVideo unCampoVideoDecorato) {
        /* rimanda al costruttore della superclasse */
        super(unCampoVideoDecorato);

        /* regola le variabili di istanza coi parametri */
        this.setCampoVideoDecorato(unCampoVideoDecorato);

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
        /* swap del riferimento al CampoScheda mantenuto nel campo parente */
        super.unCampoParente.setCampoVideo(this);
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
        try { // prova ad eseguire il codice

            /* invoca il metodo (quasi) sovrascritto della superclasse */
            super.inizializzaCampoAstratto();

            /* manda avanti il metodo all'oggetto da decorare */
            this.getCampoVideoDecorato().inizializza();

            /* inizializza il solo campo decoratore */
            this.inizializzaDecoratore();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * inizializza il solo campo decoratore
     * Regolazioni dinamiche del decoratore.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Inizializza solo gli attributi e le componenti proprie del decoratore <br>
     * Il ciclo di inizializzazione del CampoVideo decorato viene eseguito nell'oggetto
     * di classe CampoVideo, <b>prima</b> di questa inizializzazione <br>
     */
    private void inizializzaDecoratore() {

        /* Crea gli elementi GUI del pannelloCampo */
        this.creaComponentiGUI();

        /* Regola le caratteristiche grafiche dei componenti GUI */
        this.regolaFontColori();

        /* Regola le dimensioni degli elementi */
        this.regolaElementi();
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
        /* variabili e costanti locali di lavoro */
        CampoVideo cv;

        try { // prova ad eseguire il codice

            /* manda avanti il metodo all'oggetto da decorare */
            cv = this.getCampoVideoDecorato();
            cv.avvia();

            /* invoca il metodo (quasi)sovrascritto della superclasse */
            super.avviaCampoAstratto();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Elimina eventuali (quasi sempre) componenti grafici.
     * <p/>
     * Elimina dal pannello campo <br>
     * Non tocca il pannello componenti <br>
     * Elimina solo il componente nella posizione
     * mantenuta da questo decoratore <br>
     * Di solito il componente è uno solo <br>
     */
    public void reset(Component comp) {
        PannelloBase pan;

        try { // prova ad eseguire il codice
            pan = this.getPannelloBaseCampo();

            pan.remove(comp);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Crea gli elementi GUI del pannelloCampo.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Gli elementi possono essere anche più di uno <br>
     * Gli elementi vengono aggiunti al pannelloCampo <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     *
     * @see #regolaElementi()
     */
    protected void creaComponentiGUI() {
        /* variabili e costanti locali di lavoro */
        JLabel label;

        try { // prova ad eseguire il codice

            /* crea l'oggetto JLabel */
            label = new JLabel();

            /* regola la variabile */
            this.setLabel(label);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }

//    /**
//     * Aggiunge il componente Legenda al pannelloCampo nella posizione prevista.
//     * <p/>
//     */
//    public void addComponente(Component comp, Pos pos) {
//
//        /* manda avanti il metodo all'oggetto da decorare */
//        this.getCampoVideoDecorato().addComponente(comp, pos);
//    }


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
     * elimina i componenti GUI del pannello
     */
    public void eliminaPannelloComponenti() {
    } /* fine del metodo */


    /**
     * Restituisce il CampoScheda associato a questo CampoVideo.
     * <p/>
     */
    public CampoScheda getCampoScheda() {
        /* valore di ritorno */
        return this.getCampoParente().getCampoScheda();
    }


    protected boolean isRidimensionabile() {
        return true;
    }


    /**
     * Elimina gli effetti di un decoratore.
     * <p/>
     * Possono essere grafici o logici <br>
     */
    public void remove() {
        /* variabili e costanti locali di lavoro */
        PannelloCampo pan;

        try { // prova ad eseguire il codice
            pan = (PannelloCampo)this.getPannelloBaseCampo();
            if (pan != null) {
                pan.remove(this.getPos());
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    protected Pos getPos() {
        return pos;
    }


    protected void setPos(Pos pos) {
        this.pos = pos;
    }


    public JLabel getLabel() {
        return label;
    }


    protected void setLabel(JLabel label) {
        this.label = label;
    }


    protected boolean isFlag() {
        return flag;
    }


    public void setFlag(boolean flag) {
        this.flag = flag;
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
    public CVDecoratoreBase clonaCampo(Campo unCampoParente) {
        /* variabili e costanti locali di lavoro */
        CVDecoratoreBase unCampo;
        CampoVideo campoVideo;

        try { // prova ad eseguire il codice

            /* invoca il metodo sovrascritto della superclasse Object */
            unCampo = (CVDecoratoreBase)super.clone();
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

//                /* deep cloning - ricrea i componenti GUI */
//                unCampo.reset(oldComp);

                unCampo.inizializzaDecoratore();

//                unCampo.creaComponentiGUI();

            } /* fine del blocco if */


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unCampo;
    }


}// fine della classe