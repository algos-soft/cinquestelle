/**
 * Copyright: Copyright (c) 2005
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      19-giu-2005
 */
package it.algos.base.layout;

import it.algos.base.errore.Errore;
import it.algos.base.pannello.Pannello;

import javax.swing.*;
import java.awt.*;

/**
 * Implementazione di un Layout che dispone i componenti
 * in un flusso orizzontale o verticale.<br>
 * <p/>
 * Da usare in associazione a PannelloFlusso, che inserisce
 * gli opportuni componenti invisibili per distanziare i componenti.<br>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 19-giu-2005 ore 11.09.23
 */
public class LayoutFlusso extends LayoutScorrevole {

    /**
     * pannello di riferimento
     */
    private Pannello pannello = null;

    /**
     * orientamento del layout
     */
    private int orientamento = 0;

    /**
     * flag - true per usare un gap fisso (usa sempre il gap preferito)
     * false per usare un gap variabile
     */
    private boolean usaGapFisso = false;

    /**
     * gap preferito
     */
    private int gapPreferito = 0;

    /**
     * gap minimo
     */
    private int gapMinimo = 0;

    /**
     * gap massimo
     */
    private int gapMassimo = 0;

    /**
     * flag - regola il ridimensionamento dei componenti
     * nel verso parallelo al layout.
     * true - mantiene fissa la dimensione dei componenti (usa la dimensione preferita)
     * false - ridimensiona i componenti rispettando il minimo e il massimo.
     */
    private boolean dimParaFisse = false;

    /**
     * flag - regola il ridimensionamento dei componenti
     * nel verso perpendicolare al layout.
     * true - mantiene fissa la dimensione dei componenti (usa la dimensione preferita)
     * false - ridimensiona i componenti rispettando il minimo e il massimo.
     */
    private boolean dimPerpFisse = false;

    /**
     * Tipo di allineamento nel verso perpendicolare al layout.
     */
    private int allineamento = 0;

    /**
     * Flag - indica se regolare le carateristiche del componente
     * quando viene aggiunto al contenitore.
     * Normalmente true, viene disattivato se si vuole aggiungere
     * un componente mantenendo le regolazioni originali.
     */
    private boolean regolaComponenti = true;


    /**
     * Costruttore completo con parametri.
     * <p/>
     * (senza modificatore, così non può essere invocato fuori dal package) <br>
     *
     * @param pan il pannello gestito da questo layout
     * @param orientamento codifica per l'orientamento (in interfaccia Layout)
     */
    public LayoutFlusso(Pannello pan, int orientamento) {
        /* rimanda al costruttore della superclasse */
        super(orientamento);

        try { // prova ad eseguire il codice
            /* regola le variabili di istanza coi parametri */
            this.setPannello(pan);
            this.setOrientamento(orientamento);

            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }/* fine del blocco try-catch */

    }/* fine del metodo costruttore completo */


    /**
     * Regolazioni immediate di riferimenti e variabili.
     * <p/>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */
        LayoutManager2 layout ;
        int orientamento;

        try { // prova ad eseguire il codice
            orientamento = this.getCodOrientamentoBoxLayout(this.getOrientamento());
            layout = new BoxLayout(getContenitore(), orientamento);
            this.setLayoutRef(layout);

            /* regolazioni di default */
            this.setUsaGapFisso(Layout.USA_GAP_FISSO);
            this.setAllineamento(Layout.ALLINEA_SX);
            this.setRidimensionaParallelo(Layout.RIDIMENSIONA_PARALLELO);
            this.setRidimensionaPerpendicolare(Layout.RIDIMENSIONA_PERPENDICOLARE);
            this.setUsaScorrevole(Layout.USA_SCORREVOLE);
            this.setScorrevoleBordato(Layout.USA_BORDO_SCORREVOLE);
            this.setGapMinimo(Layout.GAP_MINIMO);
            this.setGapPreferito(Layout.GAP_PREFERITO);
            this.setGapMassimo(Layout.GAP_MASSIMO);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }/* fine del metodo inizia */


    /**
     * Sovrascrive il metodo addLayoutComponent, da LayoutManager2.
     * <p/>
     * Viene chiamato dal metodo add del Container, quando si aggiungono gli
     * oggetti al contenitore <br>
     *
     * @param nome del componente
     * @param comp componente da aggiungere al contenitore
     */
    public void addLayoutComponent(String nome, Component comp) {

        try { // prova ad eseguire il codice

            /* regola le caratteristiche del componente */
            this.regolaComponente(comp);

            /* rimanda alla superclasse */
            super.addLayoutComponent(nome, comp);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Sovrascrive il metodo addLayoutComponent, da LayoutManager2.
     * <p/>
     * Viene chiamato dal metodo add del Container, quando si aggiungono gli
     * oggetti al contenitore <br>
     *
     * @param comp the component to be added
     * @param constraints where/how the component is added to the layout.
     */
    public void addLayoutComponent(Component comp, Object constraints) {

        try { // prova ad eseguire il codice

            /* regola le caratteristiche del componente */
            this.regolaComponente(comp);

            /* rimanda alla superclasse */
            super.addLayoutComponent(comp, constraints);


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Posiziona i componenti interni al contenitore.
     * <p/>
     * Sovrascrive il metodo layoutContainer, da LayoutManager. <br>
     *
     * @param container pannello contenitore <br>
     */
    public void layoutContainer(Container container) {
        super.layoutContainer(container);
    }


    /**
     * Regola le caratteristiche di un componente in funzione delle
     * impostazioni generali del layout.
     * <p/>
     * Invocato da addLayoutComponent(...) <br>
     *
     * @param comp da regolare
     */
    private void regolaComponente(Component comp) {

        try {    // prova ad eseguire il codice

            /* regola le caratteristiche del componente */
            if (this.isRegolaComponenti()) {

                /* non tocca i fillers */
                if (!(comp instanceof Box.Filler)) {

                    /* regola le dimensioni minime e massime del componente */
                    this.regolaDimensioniComponente(comp);

                    /* regola le caratteristiche di allineamento */
                    this.regolaAllineamentoComponente(comp);

                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Crea un nuovo filler in base alle impostazioni correnti del layout.
     * <p/>
     *
     * @return il filler appena creato
     */
    public Component getNewFiller() {
        /* variabili e costanti locali di lavoro */
        Component filler = null;
        int gMin = 0;
        int gPref = 0;
        int gMax = 0;
        Dimension dMin = null;
        Dimension dPref = null;
        Dimension dMax = null;

        try {    // prova ad eseguire il codice
            gMin = this.getGapMinimo();
            gPref = this.getGapPreferito();
            gMax = this.getGapMassimo();

            if (this.isOrizzontale()) {
                dMin = new Dimension(gMin, 0);
                dPref = new Dimension(gPref, 0);
                dMax = new Dimension(gMax, 0);
            } else {
                dMin = new Dimension(0, gMin);
                dPref = new Dimension(0, gPref);
                dMax = new Dimension(0, gMax);
            }// fine del blocco if-else

            if (this.isUsaGapFisso()) {
                dMin = dPref;
                dMax = dPref;
            }// fine del blocco if

            filler = new Box.Filler(dMin, dPref, dMax);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return filler;
    }


    /**
     * Regola l'allineamento di un singolo componente.
     * <p/>
     * Metodo invocato dal metodo addLayoutComponent.<br>
     * Regola l'allineamento del componente nel verso perpendicolare
     * al layout, in funzione delle impostazioni generali di allineamento del layout. <br>
     *
     * @param comp da regolare
     */
    private void regolaAllineamentoComponente(Component comp) {
        /* variabili e costanti locali di lavoro */
        JComponent JComp = null;

        try {    // prova ad eseguire il codice

            /* Blocca il componente in nessuno, uno o entrambi i versi */
            /* opera solo su JComponent */
            if (comp instanceof JComponent) {
                JComp = (JComponent)comp;

                switch (this.getAllineamento()) {
                    case ALLINEA_SX:  // alto o sinistra
                        if (this.isVerticale()) {
                            JComp.setAlignmentX(0);
                        } else {
                            JComp.setAlignmentY(0);
                        }// fine del blocco if-else
                        break;
                    case ALLINEA_DX:  // basso o destra
                        if (this.isVerticale()) {
                            JComp.setAlignmentX(1);
                        } else {
                            JComp.setAlignmentY(1);
                        }// fine del blocco if-else
                        break;
                    case ALLINEA_CENTRO:  // basso o destra
                        if (this.isVerticale()) {
                            JComp.setAlignmentX(0.5f);
                        } else {
                            JComp.setAlignmentY(0.5f);
                        }// fine del blocco if-else
                        break;
                    case ALLINEA_DA_COMPONENTI:  // mantiene impostazione componente
                        // non fa nulla
                        break;
                    default: // caso non definito
                        throw new Exception("Tipo di allineamento non supportato");
                } // fine del blocco switch

            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Regola le dimensioni minime e massime di un componente.
     * <p/>
     * Metodo invocato dal metodo addLayoutComponent.<br>
     * Regola la dimensione minima, massima e preferita del componente
     * in funzione delle impostazioni generali di dimensionamento del layout. <br>
     *
     * @param comp da regolare
     */
    private void regolaDimensioniComponente(Component comp) {
        /* variabili e costanti locali di lavoro */

        try {    // prova ad eseguire il codice

            if (this.isDimParaFisse()) {
                this.bloccaDimCompPara(comp);
            }// fine del blocco if

            if (this.isDimPerpFisse()) {
                this.bloccaDimCompPerp(comp);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Blocca la dimensione di un componente a quella preferita.
     * <p/>
     * Opera in un dato verso (parallelo o perpendicolare al layout).<br>
     * Pone Minimum e Maximum size pari alla Preferred Size <br>
     *
     * @param comp da bloccare
     * @param verso true=parallelo, false=perpendicolare
     */
    private void bloccaDimComp(Component comp, boolean verso) {
        /* variabili e costanti locali di lavoro */
        Dimension dim = null;
        JComponent jComp = null;
        int wMin = 0;
        int hMin = 0;
        int wPref = 0;
        int hPref = 0;
        int wMax = 0;
        int hMax = 0;
        Dimension dimMin = null;
        Dimension dimMax = null;

        try {    // prova ad eseguire il codice

            /* puo' operare solo su JComponents*/
            if (comp instanceof JComponent) {

                jComp = (JComponent)comp;

                /* recupera larghezza e altezza minima, preferita
                 * e massima correnti */
                dim = jComp.getMinimumSize();
                if (dim != null) {
                    wMin = dim.width;
                    hMin = dim.height;
                }// fine del blocco if
                dim = jComp.getPreferredSize();
                if (dim != null) {
                    wPref = dim.width;
                    hPref = dim.height;
                }// fine del blocco if
                dim = jComp.getMaximumSize();
                if (dim != null) {
                    wMax = dim.width;
                    hMax = dim.height;
                }// fine del blocco if

                if (verso) {    // parallelo
                    /* assegna le nuove lunghezze minima e massima nel verso parallelo
                     * (mantiene quelle originali nel verso perpendicolare) */
                    if (this.isVerticale()) {
                        dimMin = new Dimension(wMin, hPref);
                        dimMax = new Dimension(wMax, hPref);
                    } else {
                        dimMin = new Dimension(wPref, hMin);
                        dimMax = new Dimension(wPref, hMax);
                    }// fine del blocco if-else
                } else {        // perpendicolare
                    /* assegna le nuove lunghezze minima e massima nel verso perpendicolare
                     * (mantiene quelle originali nel verso parallelo) */
                    if (this.isVerticale()) {
                        dimMin = new Dimension(wPref, hMin);
                        dimMax = new Dimension(wPref, hMax);
                    } else {
                        dimMin = new Dimension(wMin, hPref);
                        dimMax = new Dimension(wMax, hPref);
                    }// fine del blocco if-else
                }// fine del blocco if-else

                jComp.setMinimumSize(dimMin);
                jComp.setMaximumSize(dimMax);

            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Blocca la dimensione di un componente a quella preferita.
     * <p/>
     * Opera nel verso parallelo al layout.<br>
     * Pone Minimum e Maximum size pari alla Preferred Size <br>
     *
     * @param comp da bloccare
     */
    private void bloccaDimCompPara(Component comp) {
        this.bloccaDimComp(comp, true);
    }


    /**
     * Blocca la dimensione di un componente a quella preferita.
     * <p/>
     * Opera nel verso perpendicolare al layout.<br>
     * Pone Minimum e Maximum size pari alla Preferred Size <br>
     *
     * @param comp da bloccare
     */
    private void bloccaDimCompPerp(Component comp) {
        this.bloccaDimComp(comp, false);
    }


    /**
     * Ritorna true se l'orientamento e' orizzontale, false se e' verticale.
     * <p/>
     *
     * @return true o false
     */
    private boolean isOrizzontale() {
        /* variabili e costanti locali di lavoro */
        boolean orizzontale = false;
        int orientamento = 0;

        try {    // prova ad eseguire il codice
            orientamento = this.getOrientamento();
            if (orientamento == ORIENTAMENTO_ORIZZONTALE) {
                orizzontale = true;
            }// fine del blocco if-else
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return orizzontale;
    }


    /**
     * Ritorna true se l'orientamento e' verticale, false se orizzontale.
     * <p/>
     *
     * @return true o false
     */
    private boolean isVerticale() {
        return this.isOrizzontale() == false;
    }


    /**
     * Ritorna il pannello gestito da questo layout
     * <p/>
     *
     * @return il pannello
     */
    public Pannello getPannello() {
        return pannello;
    }


    private void setPannello(Pannello pannello) {
        this.pannello = pannello;
    }


    /**
     * ritorna il contenitore gestito da questo layout.
     * <p/>
     *
     * @return il contenitore
     */
    private Container getContenitore() {
        return (Container)this.getPannello();
    }


    /**
     * Attiva o disattiva il ridimensionamento dei componenti
     * in entrambi i versi del layout.
     * <p/>
     * Se il ridimensionamento e' attivo ridimensiona i componenti rispettando
     * la dimensione preferita, minima e massima. <br>
     * Se il ridimensionamento non e' attivo visualizza i componenti alla
     * loro dimensione preferita. <br>
     *
     * @param flag per attivare o disattivare il ridimensionamento
     */
    public void setRidimensionaComponenti(boolean flag) {
        try {    // prova ad eseguire il codice
            this.setRidimensionaParallelo(flag);
            this.setRidimensionaPerpendicolare(flag);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Attiva o disattiva il ridimensionamento dei componenti
     * nel verso parallelo al layout.
     * <p/>
     * Se il ridimensionamento e' attivo ridimensiona i componenti rispettando
     * la dimensione preferita, minima e massima. <br>
     * Se il ridimensionamento non e' attivo visualizza i componenti alla
     * loro dimensione preferita. <br>
     *
     * @param flag per attivare o disattivare il ridimensionamento parallelo
     */
    public void setRidimensionaParallelo(boolean flag) {
        try {    // prova ad eseguire il codice
            this.setDimParaFisse(flag == false);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Attiva o disattiva il ridimensionamento dei componenti
     * nel verso perpendicolare al layout.
     * <p/>
     * Se il ridimensionamento e' attivo ridimensiona i componenti rispettando
     * la dimensione preferita, minima e massima. <br>
     * Se il ridimensionamento non e' attivo visualizza i componenti alla
     * loro dimensione preferita. <br>
     *
     * @param flag per attivare o disattivare il ridimensionamento perpendicolare
     */
    public void setRidimensionaPerpendicolare(boolean flag) {
        try {    // prova ad eseguire il codice
            this.setDimPerpFisse(flag == false);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    private int getOrientamento() {
        return orientamento;
    }


    private void setOrientamento(int orientamento) {
        this.orientamento = orientamento;
    }


    private boolean isUsaGapFisso() {
        return usaGapFisso;
    }


    /**
     * Abilita l'uso del gap fisso.
     * <p/>
     *
     * @param usaGapFisso true per usare il gap fisso, false per il gap variabile
     */
    public void setUsaGapFisso(boolean usaGapFisso) {
        this.usaGapFisso = usaGapFisso;
    }


    private int getGapPreferito() {
        return gapPreferito;
    }


    /**
     * Regola il gap preferito.
     * <p/>
     * Se si usa il gap fisso, questo e' il gap che viene usato. <br>
     *
     * @param gapPreferito il gap preferito (o fisso)
     */
    public void setGapPreferito(int gapPreferito) {
        this.gapPreferito = gapPreferito;

        /* se il gap minimo e' maggiore del preferito lo riduce al preferito */
        if (this.getGapMinimo() > gapPreferito) {
            this.setGapMinimo(gapPreferito);
        }// fine del blocco if

        /* se il gap massimo e' minore del preferito lo aumenta al preferito */
        if (this.getGapMassimo() < gapPreferito) {
            this.setGapMassimo(gapPreferito);
        }// fine del blocco if

    }


    private int getGapMinimo() {
        return gapMinimo;
    }


    /**
     * Regola il gap minimo.
     * <p/>
     * Significativo solo se si usa il gap variabile. <br>
     *
     * @param gapMinimo il gap minimo
     */
    public void setGapMinimo(int gapMinimo) {
        this.gapMinimo = gapMinimo;

        /* se il gap preferito e' minore del minimo lo aumenta al minimo */
        if (this.getGapPreferito() < gapMinimo) {
            this.setGapPreferito(gapMinimo);
        }// fine del blocco if

        /* se il gap massimo e' minore del minimo lo aumenta al minimo */
        if (this.getGapMassimo() < gapMinimo) {
            this.setGapMassimo(gapMinimo);
        }// fine del blocco if

    }


    private int getGapMassimo() {
        return gapMassimo;
    }


    /**
     * Regola il gap massimo.
     * <p/>
     * Significativo solo se si usa il gap variabile. <br>
     *
     * @param gapMassimo il gap massimo
     */
    public void setGapMassimo(int gapMassimo) {
        this.gapMassimo = gapMassimo;

        /* se il gap minimo e' maggiore del massimo lo riduce al massimo */
        if (this.getGapMinimo() > gapMassimo) {
            this.setGapMinimo(gapMassimo);
        }// fine del blocco if

        /* se il gap preferito e' maggiore del massimo lo riduce al massimo */
        if (this.getGapPreferito() > gapMassimo) {
            this.setGapPreferito(gapMassimo);
        }// fine del blocco if

    }


    private boolean isDimParaFisse() {
        return dimParaFisse;
    }


    /**
     * Regola il ridimensionamento dei componenti nel verso parallelo al layout.
     * <p/>
     * Metodo privato - da fuori usare setRidimensionaParallelo
     *
     * @param dimParaFisse la modalita' di ridimensionamento
     * true - mantiene fissa la dimensione dei componenti (usa la dimensione preferita)
     * false - ridimensiona i componenti rispettando il minimo e il massimo.
     */
    private void setDimParaFisse(boolean dimParaFisse) {
        this.dimParaFisse = dimParaFisse;
    }


    private boolean isDimPerpFisse() {
        return dimPerpFisse;
    }


    /**
     * Regola il ridimensionamento dei componenti nel verso perpendicolare al layout.
     * <p/>
     * Metodo privato - da fuori usare setRidimensionaPerpendicolare
     *
     * @param dimPerpFisse la modalita' di ridimensionamento
     * true - mantiene fissa la dimensione dei componenti (usa la dimensione preferita)
     * false - ridimensiona i componenti rispettando il minimo e il massimo.
     */
    private void setDimPerpFisse(boolean dimPerpFisse) {
        this.dimPerpFisse = dimPerpFisse;
    }


    private int getAllineamento() {
        return allineamento;
    }


    /**
     * Regola il tipo di allineamento dei componenti nel verso
     * perpendicolare a quello del layout.
     * <p/>
     *
     * @param allineamento il codice dell'allineamento
     * puo' essere ALLINEA_ALTO, ALLINEA_SX, ALLINEA_BASSO, ALLINEA_DX,
     * ALLINEA_CENTRO, ALLINEA_DA_COMPONENTI
     *
     * @see Layout
     */
    public void setAllineamento(int allineamento) {
        this.allineamento = allineamento;
    }


    /**
     * Ritorna lo stato del flag di regolazione dei componenti.
     * <p/>
     * Normalmente il layout regola automaticamente le dimensioni e l'allineamento
     * del componente quando viene aggiunto. <br>
     * Questa funzione puo' essere disattivata agendo sul flag <br>
     *
     * @return lo stato del flag di regolazione dei componenti in entrata
     */
    public boolean isRegolaComponenti() {
        return regolaComponenti;
    }


    /**
     * Flag - indica se regolare le carateristiche del componente
     * quando viene aggiunto al contenitore.
     * <p/>
     * Normalmente true, viene disattivato solo se si vuole aggiungere
     * un componente mantenendo le regolazioni originali.
     *
     * @param flag true per attivare la regolazione dei
     * componenti, false per disattivarla
     */
    public void setRegolaComponenti(boolean flag) {
        this.regolaComponenti = flag;
    }


}// fine della classe
