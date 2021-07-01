/**
 * Copyright(c): 2005
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 3-4-05
 */

package it.algos.base.layout;

import it.algos.base.campo.video.decorator.CVDecoratore;
import it.algos.base.errore.Errore;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Superclasse astratta.
 * <p/>
 * Questa classe astratta: <ul>
 * <li> Mantiene le variabili comuni alle sottoclassi <li/>
 * <li> Mantiene un riferimento ad un oggetto di tipo Layout (Java) <li/>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 3-4-05
 */
public abstract class LayoutBase implements Layout {

    /**
     * Riferimento al layout Java interno utilizzato
     */
    private LayoutManager2 layoutRef = null;

    private boolean inizializzato = false;

    /**
     * Flag - se attivo considera anche le dimensioni dei componenti
     * invisibili quando disegna il contenitore
     */
    private boolean consideraInvisibili = false;

    /**
     * Lista dei componenti invisibili ad uso interno.
     * Prima di eseguire il layout viene riempita con i riferimenti
     * ai componenti invisibili.
     */
    private List compInvisibili = null;


    /**
     * Costruttore completo senza parametri.
     */
    public LayoutBase() {
        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        this.setConsideraComponentiInvisibili(false);
        this.setCompInvisibili(new ArrayList());
    }

//    /**
//     * Regolazioni dinamiche dell'oggetto.
//     * <p/>
//     * Regola le caratteristiche dinamiche in base alla impostazione
//     * corrente delle caratteristiche statiche <br>
//     * Metodo invocato dalla classe che crea questo oggetto (di norma) <br>
//     * Viene eseguito una sola volta <br>
//     * Sovrascritto nelle sottoclassi <br>
//     */
//    public void inizializza() {
//
//        try { // prova ad eseguire il codice
//            if (this.isInizializzato() == false) {
//
//                /* inizializzazioni specifiche */
//                if (this.getLayoutRef() == null) {
//                    this.setLayoutRef(new BorderLayout());
//                }// fine del blocco if
//
//                /* regola il flag*/
//                this.setInizializzato(true);
//
//            }// fine del blocco if
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//    }


    /**
     * addLayoutComponent, da LayoutManager.
     * </p>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @param unNome stringa da associare al componente
     * @param unComponente componente da aggiungere
     *
     * @see #addLayoutComponent
     */
    public void addLayoutComponent(String unNome, Component unComponente) {
        /* variabili e costanti locali di lavoro */
        LayoutManager layout = null;

        try { // prova ad eseguire il codice
            layout = this.getLayoutRef();

            if (layout != null) {
                layout.addLayoutComponent(unNome, unComponente);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * addLayoutComponent, da LayoutManager2.
     * <p/>
     * Adds the specified component to the layout, using the specified
     * constraint object <br>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @param comp the component to be added
     * @param constraints where/how the component is added to the layout.
     */
    public void addLayoutComponent(Component comp, Object constraints) {
        /* variabili e costanti locali di lavoro */
        LayoutManager2 layout = null;

        try { // prova ad eseguire il codice

            layout = this.getLayoutRef();

            if (layout != null) {
                layout.addLayoutComponent(comp, constraints);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Aggiunge un componente al layout.
     * <p/>
     *
     * @param comp il componente da aggiungere
     * @param posizione la posizione alla quale aggiungere il componente
     * Layout.CENTRO, Layout.SOPRA, Layout.SOTTO, Layout.SINISTRA, Layout.DESTRA
     *
     * @see Layout
     */
    public void addLayoutComponent(Component comp, int posizione) {
    }


    /**
     * Aggiunge un componente al layout.
     * <p/>
     *
     * @param comp il componente da aggiungere
     * Layout.CENTRO, Layout.SOPRA, Layout.SOTTO, Layout.SINISTRA, Layout.DESTRA
     *
     * @see Layout
     */
    public void addLayoutComponent(Component comp, CVDecoratore.Pos pos) {
    }


    /**
     * layoutContainer, da LayoutManager.
     * </p>
     * Posiziona i componenti interni al contenitore <br>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @param parent contenitore da organizzare (disegnare)
     */
    public void layoutContainer(Container parent) {
        /* variabili e costanti locali di lavoro */
        LayoutManager layout = null;

        try { // prova ad eseguire il codice
            layout = this.getLayoutRef();

            if (layout != null) {

                /* salva i componenti invisibili e li rende visibili */
                if (this.isConsideraComponentiInvisibili()) {
                    this.salvaInvisibili(parent);
                }// fine del blocco if

                /* effettua il disegno del contenitore */
                layout.layoutContainer(parent);

                /* ripristina i componenti invisibili */
                if (this.isConsideraComponentiInvisibili()) {
                    this.ripristinaInvisibili();
                }// fine del blocco if

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * removeLayoutComponent, da LayoutManager.
     * </p>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @param comp componente da rimuovere
     */
    public void removeLayoutComponent(Component comp) {
        /* variabili e costanti locali di lavoro */
        LayoutManager layout = null;

        try { // prova ad eseguire il codice
            layout = this.getLayoutRef();

            if (layout != null) {
                layout.removeLayoutComponent(comp);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * preferredLayoutSize, da LayoutManager.
     * </p>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @param parent contenitore da organizzare (disegnare)
     *
     * @return dimensione preferita
     *
     * @see #minimumLayoutSize
     */
    public Dimension preferredLayoutSize(Container parent) {
        /* variabili e costanti locali di lavoro */
        Dimension dim = null;
        LayoutManager layout = null;

        try { // prova ad eseguire il codice
            layout = this.getLayoutRef();

            /* salva i componenti invisibili e li rende visibili */
            if (this.isConsideraComponentiInvisibili()) {
                this.salvaInvisibili(parent);
            }// fine del blocco if

            dim = layout.preferredLayoutSize(parent);

            /* ripristina i componenti invisibili */
            if (this.isConsideraComponentiInvisibili()) {
                this.ripristinaInvisibili();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return dim;
    }


    /**
     * minimumLayoutSize, da LayoutManager.
     * </p>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @param parent contenitore di cui calcolare le dimensioni
     *
     * @return dimensione minima
     *
     * @see #preferredLayoutSize
     */
    public Dimension minimumLayoutSize(Container parent) {
        /* variabili e costanti locali di lavoro */
        Dimension dim = null;
        LayoutManager layout = null;

        try { // prova ad eseguire il codice
            layout = this.getLayoutRef();

            /* salva i componenti invisibili e li rende visibili */
            if (this.isConsideraComponentiInvisibili()) {
                this.salvaInvisibili(parent);
            }// fine del blocco if

            dim = layout.minimumLayoutSize(parent);

            /* ripristina i componenti invisibili */
            if (this.isConsideraComponentiInvisibili()) {
                this.ripristinaInvisibili();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return dim;
    }


    /**
     * maximumLayoutSize, da LayoutManager2.
     * <p/>
     * Calculates the maximum size dimensions for the specified container,
     * given the components it contains <br>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @param parent contenitore di cui calcolare le dimensioni
     *
     * @return dimensione massima
     *
     * @see java.awt.Component#getMaximumSize
     * @see LayoutManager
     */
    public Dimension maximumLayoutSize(Container parent) {
        /* variabili e costanti locali di lavoro */
        Dimension dim = null;
        LayoutManager2 layout = null;

        try { // prova ad eseguire il codice
            layout = this.getLayoutRef();

            /* salva i componenti invisibili e li rende visibili */
            if (this.isConsideraComponentiInvisibili()) {
                this.salvaInvisibili(parent);
            }// fine del blocco if

            dim = layout.maximumLayoutSize(parent);

            /* ripristina i componenti invisibili */
            if (this.isConsideraComponentiInvisibili()) {
                this.ripristinaInvisibili();
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return dim;
    }


    /**
     * getLayoutAlignmentX, da LayoutManager2.
     * <p/>
     * Returns the alignment along the x axis.  This specifies how
     * the component would like to be aligned relative to other
     * components.  The value should be a number between 0 and 1
     * where 0 represents alignment along the origin, 1 is aligned
     * the furthest away from the origin, 0.5 is centered, etc <br>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @param target contenitore di cui calcolare l'allineamento
     *
     * @return valore dell'allineamento
     */
    public float getLayoutAlignmentX(Container target) {
        /* variabili e costanti locali di lavoro */
        float num = 0;
        LayoutManager2 layout = null;

        try { // prova ad eseguire il codice
            layout = this.getLayoutRef();

            if (layout != null) {
                num = layout.getLayoutAlignmentX(target);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return num;
    }


    /**
     * getLayoutAlignmentY, da LayoutManager2.
     * <p/>
     * Returns the alignment along the y axis.  This specifies how
     * the component would like to be aligned relative to other
     * components.  The value should be a number between 0 and 1
     * where 0 represents alignment along the origin, 1 is aligned
     * the furthest away from the origin, 0.5 is centered, etc <br>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @param target contenitore di cui calcolare l'allineamento
     *
     * @return valore dell'allineamento
     */
    public float getLayoutAlignmentY(Container target) {
        /* variabili e costanti locali di lavoro */
        float num = 0;
        LayoutManager2 layout = null;

        try { // prova ad eseguire il codice
            layout = this.getLayoutRef();

            if (layout != null) {
                num = layout.getLayoutAlignmentY(target);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return num;
    }


    /**
     * getLayoutAlignmentY, da LayoutManager2.
     * <p/>
     * Invalidates the layout, indicating that if the layout manager
     * has cached information it should be discarded.
     * Sovrascritto nelle sottoclassi <br>
     *
     * @param target contenitore da invalidare
     */
    public void invalidateLayout(Container target) {
        /* variabili e costanti locali di lavoro */
        LayoutManager2 layout = null;

        try { // prova ad eseguire il codice
            layout = this.getLayoutRef();

            if (layout != null) {
                layout.invalidateLayout(target);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Registra i componenti invisibili e li rende visibili.
     * <p/>
     * Svuota la lista di lavoro <br>
     * Spazzola i componenti, e per quelli invisibili:<br>
     * - li aggiunge alla lista <br>
     * - li rende provvisoriamente visibili <br>
     *
     * @param container il contenitore da esaminare
     */
    private void salvaInvisibili(Container container) {
        /* variabili e costanti locali di lavoro */
        Component[] componenti = null;
        Component componente = null;

        try {    // prova ad eseguire il codice

            /* svuota la lista di lavoro */
            this.getCompInvisibili().clear();

            /* recupera i componenti */
            componenti = container.getComponents();

            /* spazzola e memorizza i componenti invisibili
             * nell'array di lavoro.
             * contestualmente, li rende visibili. */
            for (int k = 0; k < componenti.length; k++) {
                componente = componenti[k];
                if (componente.isVisible() == false) {
                    this.getCompInvisibili().add(componente);
                    componente.setVisible(true);
                }// fine del blocco if

            } // fine del ciclo for

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Ripristina i componenti invisibili.
     * <p/>
     * Spazzola la lista di lavoro e rende invisibili i componenti
     * in essa contenuti.
     */
    private void ripristinaInvisibili() {
        /* variabili e costanti locali di lavoro */
        List invisibili = null;
        Component componente = null;

        try {    // prova ad eseguire il codice

            invisibili = this.getCompInvisibili();

            for (int k = 0; k < invisibili.size(); k++) {
                componente = (Component)invisibili.get(k);
                componente.setVisible(false);
            } // fine del ciclo for

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Ritorna il codice di orientamento per un BoxLayout
     * corrispondente a un orientamento di questo layout.
     * <p/>
     *
     * @param orientamento codifica per l'orientamento (in interfaccia Layout)
     *
     * @return il corrispondente codice di orientamento di un BoxLayout.
     */
    protected int getCodOrientamentoBoxLayout(int orientamento) {
        /* variabili e costanti locali di lavoro */
        int codBoxLayout = 0;

        try {    // prova ad eseguire il codice

            /* converte dalle costanti di questo layout alle costanti di BoxLayout */
            switch (orientamento) {
                case Layout.ORIENTAMENTO_ORIZZONTALE:
                    codBoxLayout = BoxLayout.LINE_AXIS;
                    break;
                case Layout.ORIENTAMENTO_VERTICALE:
                    codBoxLayout = BoxLayout.PAGE_AXIS;
                    break;
                default: // caso non definito
                    throw new Exception("Codice orientamento " +
                            orientamento +
                            " non riconosciuto.");
            } // fine del blocco switch

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codBoxLayout;
    }


    /**
     * Ritorna il layout di riferimento usato internamente
     * <p/>
     *
     * @return il layout di riferimento
     */
    public LayoutManager2 getLayoutRef() {
        return layoutRef;
    }


    protected void setLayoutRef(LayoutManager2 layoutRef) {
        this.layoutRef = layoutRef;
    }


    protected boolean isInizializzato() {
        return inizializzato;
    }


    protected void setInizializzato(boolean inizializzato) {
        this.inizializzato = inizializzato;
    }


    private boolean isConsideraComponentiInvisibili() {
        return consideraInvisibili;
    }


    /**
     * Considera anche i componenti invisibili
     * disegnando il contenitore.
     * <p/>
     *
     * @param flag true per considerare anche i componenti invisibili
     */
    public void setConsideraComponentiInvisibili(boolean flag) {
        this.consideraInvisibili = flag;
    }


    private List getCompInvisibili() {
        return compInvisibili;
    }


    private void setCompInvisibili(List compInvisibili) {
        this.compInvisibili = compInvisibili;
    }


    /**
     * @deprecated
     */
    public void setLarghezzaEsterna(boolean larghezzaEsterna) {
    }


    /**
     * @deprecated
     */
    public void setAltezzaEsterna(boolean altezzaEsterna) {
    }


    /**
     * Attiva o disattiva l'uso del bordo nello scorrevole.
     * <p/>
     *
     * @param flag per attivare/disattivare l'uso del bordo.
     */
    public void setScorrevoleBordato(boolean flag) {
    }


    /**
     * Abilita l'uso dello scorrevole.
     * <p/>
     *
     * @param flag true per abilitare l'uso dello scorrevole
     */
    public void setUsaScorrevole(boolean flag) {
    }


    /**
     * Abilita l'uso del gap fisso.
     * <p/>
     *
     * @param usaGapFisso true per usare il gap fisso, false per il gap variabile
     */
    public void setUsaGapFisso(boolean usaGapFisso) {
    }


    /**
     * Regola il gap preferito.
     * <p/>
     * Se si usa il gap fisso, questo e' il gap che viene usato. <br>
     *
     * @param gapPreferito il gap preferito (o fisso)
     */
    public void setGapPreferito(int gapPreferito) {
    }


    /**
     * Regola il gap minimo.
     * <p/>
     * Significativo solo se si usa il gap variabile. <br>
     *
     * @param gapMinimo il gap minimo
     */
    public void setGapMinimo(int gapMinimo) {
    }


    /**
     * Regola il gap massimo.
     * <p/>
     * Significativo solo se si usa il gap variabile. <br>
     *
     * @param gapMassimo il gap massimo
     */
    public void setGapMassimo(int gapMassimo) {
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
    }


    /**
     * Crea un nuovo filler in base alle impostazioni correnti del layout.
     * <p/>
     *
     * @return il filler appena creato
     */
    public Component getNewFiller() {
        return null;
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
        return false;
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
    }


} // fine della classe
