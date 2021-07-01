/**
 * Copyright: Copyright (c) 2005
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      25-gen-2005
 */
package it.algos.base.campo.video;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloCampo;
import it.algos.base.pannello.PannelloComponenti;
import it.algos.base.scheda.Scheda;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Componente video di tipo scheda.
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
public final class CVScheda extends CVBase {


    /**
     * Costruttore completo con parametri.
     * <p/>
     * Costruttore invocato di solito dalla classe CampoFactory <br>
     *
     * @param unCampoParente campo 'contenitore' di questo oggetto
     *
     * @see it.algos.base.campo.base.CampoFactory#navigatore
     */
    public CVScheda(Campo unCampoParente) {
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

        /* sblocca la dimensione massima del pannello campo
         * in modo che il navigatore si possa espandere */
        this.getPannelloBaseCampo().setMaximumSize(null);

    } /* fine del metodo */


    /**
     * Modifica le constraints del componente nel layout in modo che la
     * scheda si espanda assieme al pannello.
     * Invocato dal ciclo Inizializza
     */
    private void regolaPanCampo() {
        /* variabili e costanti locali di lavoro */
        PannelloCampo pCampo;
        PannelloComponenti pComp;
        GridBagConstraints gbc;


        try { // prova ad eseguire il codice

            pCampo = this.getPannelloCampo();
            pComp = this.getPannelloComponenti();

            /* recupera le constraints, le modifica e le riassegna */
            gbc = pCampo.getConstraints(pComp);

            // il componente riempie tutta l'area disponibile
            // nella propria cella
            gbc.fill = GridBagConstraints.BOTH;

            pCampo.setConstraints(pComp, gbc);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

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
        Modulo mod;
        Scheda scheda;
        Pannello pc;
        JComponent comp;

        try { // prova ad eseguire il codice

            /* svuota il pannello componenti */
            this.getPannelloComponenti().removeAll();

            /* recupera la scheda */
            mod = this.getCampoParente().getCampoDB().getModuloLinkato();
            scheda = mod.getSchedaDefault();

            /* regola il riferimento al componente principale */
            comp = scheda.getScheda();
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


    /**
     * Restituisce l'elenco dei componenti video del campo.
     * <p/>
     * In questo caso e' la scheda.<br>
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
