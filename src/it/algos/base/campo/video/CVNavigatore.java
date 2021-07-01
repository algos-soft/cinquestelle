/**
 * Copyright: Copyright (c) 2005
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      25-gen-2005
 */
package it.algos.base.campo.video;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloBase;
import it.algos.base.pannello.PannelloCampo;
import it.algos.base.pannello.PannelloComponenti;
import it.algos.base.portale.Portale;
import it.algos.base.portale.PortaleNavigatore;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Componente video di tipo navigatore.
 * </p>
 * Questa classe concreta: <ul>
 * <li> Crea il componente GUI specifico di questa classe </li>
 * <li> Aggiunge i Listener al componente specifico </li>
 * <li> Regola i font, la dimensione e la posizione del componente specifico </li>
 * <li> Regola le dimensioni del pannelloComponenti </li>
 * <li> Implementa i metodi astratti della superclasse per regolare le
 * conversioni tra i vari tipi di dati </li>
 * <li> L'attributo <i>video</i> del <code>CampoDati</code> associato,
 * <strong>deve</strong> essere di tipo ... </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 25-gen-2005 ore 13.24.27
 */
public final class CVNavigatore extends CVBase {


    /**
     * Costruttore completo con parametri.
     * <p/>
     * Costruttore invocato di solito dalla classe CampoFactory <br>
     *
     * @param unCampoParente campo 'contenitore' di questo oggetto
     *
     * @see it.algos.base.campo.base.CampoFactory#navigatore
     */
    public CVNavigatore(Campo unCampoParente) {
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

        /* regola l'espandibilità di default */
        this.setEspandibilita(Espandibilita.entrambe);

    }// fine del metodo inizia


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
        try { // prova ad eseguire il codice

            /* invoca il metodo sovrascritto della superclasse */
            super.inizializza();

            /* regola il pannello campo */
            this.regolaPanCampo();

            /* regola i bordi */
            this.regolaBordi();


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regolazioni di ri-avvio.
     * <p/>
     * Reinserisce il PortaleNavigatore nel PannelloComponenti.
     * Il PortaleNavigatore e' un oggetto grafico che puo' esistere
     * solo in un posto per volta, e potrebbe essere finito da qualche
     * altra parte, per esempio se il campo e' stato clonato.<br>
     * Quindi ad ogni avvio lo reinserisco nel pannelloComponenti
     */
    public void avvia() {

        /* svuota il pannello componenti */
        this.getPannelloComponenti().removeAll();

        /* crea i componenti interni al pannelloComponenti */
        this.creaComponentiInterni();

        /* invoca il metodo sovrascritto della superclasse */
        super.avvia();

        /* regola le maximum size del PannelloCampo in funzione della espandibilità */
        switch (this.getEspandibilita()) {
            case nessuna:
                break;
            case orizzontale:
                Lib.Comp.sbloccaLarMax(this.getPannelloBaseCampo());
                break;
            case verticale:
                Lib.Comp.sbloccaAltMax(this.getPannelloBaseCampo());
                break;
            case entrambe:
                this.getPannelloBaseCampo().setMaximumSize(null);
                break;
            default: // caso non definito
                break;
        } // fine del blocco switch

    } /* fine del metodo */


    /**
     * Regola i bordi degli oggetti.
     * <p/>
     * Di default, portale Lista senza bordo e portale Scheda con bordo
     */
    private void regolaBordi() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Navigatore nav;
        Portale portale = null;
        PortaleNavigatore pn;
        PannelloBase pannello;

        try {    // prova ad eseguire il codice
            nav = this.getCampoLogica().getNavigatore();
            continua = nav != null;

            if (continua) {
                pn = nav.getPortaleNavigatore();
                continua = !(pn.isUsaFinestraPop());
            }// fine del blocco if

            if (continua) {
                portale = nav.getPortaleScheda();
                continua = portale != null;
            }// fine del blocco if

            if (continua) {
                pannello = (PannelloBase)portale;
                pannello.creaBordo(0);
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
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
     * - aggiungere i listener ai componenti GUI
     * - eventualmente assegnare un layout al PannelloComponenti<br>
     * - aggiungere i componenti al pannelloComponenti<br>
     */
    public void creaComponentiInterni() {
        /* variabili e costanti locali di lavoro */
        Navigatore nav;
        Pannello pc;
        JComponent comp;

        try { // prova ad eseguire il codice

            /* svuota il pannello componenti */
            this.getPannelloComponenti().removeAll();

            /* recupera il navigatore */
            nav = this.getCampoLogica().getNavigatore();

            /* regola il riferimento al componente principale */
            comp = nav.getPortaleNavigatore();
            this.setComponente(comp);

            /* inserisce il componente principale nel pannelloComponenti */
            pc = this.getPannelloComponenti();
            pc.add(this.getComponente());

            /* invoca il metodo sovrascritto nella superclasse */
            super.creaComponentiInterni();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }

//    protected void regolaLarghezzaComponenti() {
//        /* variabili e costanti locali di lavoro */
//        JComponent comp;
//        int lar;
//
//        try { // prova ad eseguire il codice
//
//            /* recupera il componente principale */
//            comp = this.getCampoLogica().getNavigatore().getPortaleNavigatore();
//
//            /* Assegna la larghezza preferita */
//            if (comp != null) {
//                lar = this.getLarghezzaComponenti();
//                Lib.Comp.setPreferredWidth(comp, lar);
//            }// fine del blocco if
//
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//    }


    protected void regolaAltezzaComponenti() {
        super.regolaAltezzaComponenti();

    }


    /**
     * Modifica le constraints del componente nel layout in modo che il
     * navigatore si espanda assieme al pannello.
     * Invocato dal ciclo Inizializza
     */
    private void regolaPanCampo() {
        /* variabili e costanti locali di lavoro */
        PannelloCampo pCampo;
        PannelloComponenti pComp;
        GridBagConstraints gbc;
        Espandibilita esp;


        try { // prova ad eseguire il codice

            pCampo = this.getPannelloCampo();
            pComp = this.getPannelloComponenti();

            /* recupera le constraints, le modifica e le riassegna */
            gbc = pCampo.getConstraints(pComp);

            /* regola la constraint di espandibilità nel layout */
            esp = this.getEspandibilita();
            gbc.fill = esp.getConstraint();
            pCampo.setConstraints(pComp, gbc);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Restituisce l'elenco dei componenti video del campo.
     * <p/>
     * In questo caso e' il portale navigatore.<br>
     *
     * @return l'elenco dei componenti video
     */
    protected ArrayList<JComponent> getComponentiVideo() {
        /* variabili e costanti locali di lavoro */
        ArrayList<JComponent> componenti = null;
        JComponent comp;

        try { // prova ad eseguire il codice
            componenti = new ArrayList<JComponent>();
            comp = this.getComponente();
            componenti.add(comp);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return componenti;
    }


    /**
     * Regola il componente GUI per essere modificabile o meno.
     * <p/>
     * In questo caso il componente GUI è il Navigatore<br>
     *
     * @param flag true se il campo deve essere modificabile
     */
    protected void regolaModificabile(boolean flag) {
        /* variabili e costanti locali di lavoro */
        Navigatore nav;

        try {    // prova ad eseguire il codice

            nav = this.getCampoParente().getNavigatore();
            if (nav != null) {
                nav.setModificabile(flag);
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
        return null;
    }


}// fine della classe
