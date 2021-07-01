/**
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Ceresa, Colombo, Valbonesi
 * @version 1.0  /
 * Creato:       il 15 agosto 2003 alle 21.04
 */
package it.algos.base.campo.video.decorator;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.video.CampoVideo;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.pannello.Pannello;

import javax.swing.*;
import java.awt.*;

/**
 * Decoratore congelato della classe CampoVideo.
 * <p/>
 * Questa classe concreta: <ul>
 * <li> Implementa il <i>design pattern</i> <b>Decorator</b> </li>
 * <li> Disegna una legenda (descrizione) nel pannello campo del CampoVideo,
 * dopo il pannello componenti </li>
 * <li> Può essere applicato solo ad un campo combobox </li>
 * <li> Mantiene un secondo campo testo associato </li>
 * <li> Mantiene un flag per una condizione switch </li>
 * <li> Se il flag non è attivo mostra il combobox </li>
 * <li> Se il flag è attivo mostra il campo testo associato </li>
 * <ul/>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  15 agosto 2003 ore 21.04
 */
public class CVDCongelato extends CVDecoratoreBase {


    /**
     * riferimento al campo associato
     */
    private String nomeCampoAssociato = "";

    /**
     * riferimento al campo associato
     */
    private Campo campoAssociato = null;

    /**
     * pannello componenti del campo combo
     */
    private JComponent compCombo = null;

    /**
     * pannello componenti del campo associato
     */
    private JComponent compTesto = null;


    /**
     * Costruttore completo con parametri.
     *
     * @param campoVideoDecorato oggetto da decorare
     */
    public CVDCongelato(CampoVideo campoVideoDecorato) {
        /* rimanda al costruttore della superclasse */
        super(campoVideoDecorato);

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
    } /* fine del metodo inizia */


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
        /* variabili e costanti locali di lavoro */
        Modulo mod;
        Campo campo;
        JComponent comp;

        try { // prova ad eseguire il codice
            /* invoca il metodo sovrascritto della superclasse */
            super.inizializza();

            mod = this.getCampoParente().getModulo();
            campo = mod.getCampo(this.getNomeCampoAssociato());
            this.setCampoAssociato(campo);
            this.getCampoAssociato().setAbilitato(false);

            comp = this.getCampoParente().getComponenteVideo();
            this.setCompCombo(comp);
            comp = this.getCampoAssociato().getComponenteVideo();
            this.setCompTesto(comp);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regolazioni di ri-avvio.
     * <p/>
     * Metodo invocato da altre classi (o anche da questa)
     * ogni volta che questo oggetto deve <i>ripartire</i>,
     * per essere sicuri che sia <i>pulito</i> <br>
     * Viene eseguito tutte le volte che necessita <br>
     * Sovrascritto nelle sottoclassi <br>
     */
    public void avvia() {
        /* invoca il metodo sovrascritto della superclasse */
        super.avvia();
        this.regolaComponentiGUI();
    }


    /**
     * Regola gli elementi GUI del pannelloCampo.
     * <p/>
     * Metodo invocato dal ciclo avvia <br>
     */
    private void regolaComponentiGUI() {
        /* variabili e costanti locali di lavoro */
        boolean flag;
        Component comp;
        Pannello pan;

        try { // prova ad eseguire il codice

            flag = this.isFlag();
            if (flag) {
                comp = this.getCompTesto();
            } else {
                comp = this.getCompCombo();
            }// fine del blocco if-else

            pan = this.getPannelloBaseComponenti();
            pan.removeAll();
            pan.add(comp);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    private String getNomeCampoAssociato() {
        return nomeCampoAssociato;
    }


    public void setNomeCampoAssociato(String nomeCampoAssociato) {
        this.nomeCampoAssociato = nomeCampoAssociato;
    }


    public Campo getCampoAssociato() {
        return campoAssociato;
    }


    private void setCampoAssociato(Campo campoAssociato) {
        this.campoAssociato = campoAssociato;
    }


    private JComponent getCompCombo() {
        return compCombo;
    }


    private void setCompCombo(JComponent compCombo) {
        this.compCombo = compCombo;
    }


    private JComponent getCompTesto() {
        return compTesto;
    }


    private void setCompTesto(JComponent compTesto) {
        this.compTesto = compTesto;
    }


}// fine della classe