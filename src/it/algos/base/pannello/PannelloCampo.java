/**
 * Title:     WrapperPannello
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      29-feb-2004
 */
package it.algos.base.pannello;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.video.decorator.CVDecoratore;
import it.algos.base.errore.Errore;
import it.algos.base.layout.Layout;
import it.algos.base.layout.LayoutCampo;

import java.awt.*;
import java.util.HashMap;

/**
 * Wrapper per inglobare il riferimento al Campo che crea questo pannello.
 * </p>
 * Utilizza il layout LayoutCampo.<br>
 * E' possibile aggiungere un componente in una delle cinque posizioni
 * previste (vedi schema).<br>
 * Le posizioni sono definite dalle costanti CENTRO, SOPRA, SOTTO, SINISTRA, DESTRA
 * nell'interfaccia Layout.
 * <p/>
 * +----------+----------+----------+
 * |(0,0)     |(1,0)     |(2,0)     |
 * |          |          |          |
 * |          | SOPRA    |          |
 * +----------+----------+----------+
 * |(0,1)     |(1,1)     |(2,1)     |
 * |          |          |          |
 * | SINISTRA | CENTRO   | DESTRA   |
 * +----------+----------+----------+
 * |(0,2)     |(1,2)     |(2,2)     |
 * |          |          |          |
 * |          | SOTTO    |          |
 * +----------+----------+----------+
 * <p/>
 * Il Pannello Camponenti viene aggiunto automaticamente al centro.
 * Normalmente l'etichetta viene aggiunta sopra o a sinistra.
 * Normalmente la legenda viene aggiunta sotto o a destra.
 *
 * @author Alberto Colombo, Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 29-feb-2004 ore 15.12.45
 */
public final class PannelloCampo extends PannelloBase implements Cloneable {

    /**
     * disegna il pannello con sfondo, bordi e colore
     */
    protected static final boolean DEBUG = false;

    /**
     * riferimento al Campo a cui appartiene questo pannelloCampo
     */
    private Campo campo = null;


    /**
     * Pannello componenti contenuto in questo PannelloCampo
     */
    private PannelloComponenti pannelloComponenti = null;

    /**
     * Mappa che mantiene i componenti e le rispettive posizioni
     */
    private HashMap<CVDecoratore.Pos, Component> mappa;


    /**
     * Costruttore completo con parametri. <br>
     *
     * @param campo il Campo a cui appartiene questo pannello
     */
    public PannelloCampo(Campo campo) {
        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice
            /* regola le variabili di istanza coi parametri */
            this.setCampo(campo);

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
        /* variabili e costanti locali di lavoro */
        PannelloComponenti pComp;


        try { // prova ad eseguire il codice
            this.setMappa(new HashMap<CVDecoratore.Pos, Component>());
            this.setLayout(new LayoutCampo());
            this.setOpaque(false);

            /* crea e registra un nuovo PannelloComponenti e lo inserisce */
            pComp = new PannelloComponenti(this);
            this.setPannelloComponenti(pComp);
            this.add(pComp, Layout.CENTRO);

            /* se e' attivo il debug usa sfondo, bordi e colori */
            if (DEBUG) {
//            String titolo = this.getClass().getName();
//            List lista = Libreria.estrae(titolo,'.');
//            titolo = (String)lista.get(lista.size()-1);
//            Border bordo = BorderFactory.createTitledBorder(titolo);
                this.setOpaque(true);
//            this.setBorder(bordo);
                this.setBackground(Color.yellow);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


    }


    /**
     * Aggiunge un componente al layout in una data posizione.
     * <p/>
     *
     * @param comp il componente da aggiungere
     * @param posizione la posizione alla quale aggiungere il componente
     * Layout.CENTRO, Layout.SOPRA, Layout.SOTTO,
     * Layout.SINISTRA, Layout.DESTRA
     *
     * @return il componente aggiunto
     *
     * @see Layout
     */
    public Component add(Component comp, int posizione) {
        /* variabili e costanti locali di lavoro */
        GridBagConstraints gbc;
        int margSopra;
        int margSotto;
        int margSx;
        int margDx;

        try {    // prova ad eseguire il codice

            /* margini di rispetto attorno al componente centrale */
            margSopra = 1;
            margSotto = 2;
            margSx = 4;
            margDx = 4;

            /* crea una nuova constraint regolata con i valori di default */
            gbc = new GridBagConstraints();

            /* regola la posizione del componente nella griglia */
            switch (posizione) {
                case Layout.CENTRO:

                    // coordinate della cella
                    gbc.gridx = 1;
                    gbc.gridy = 1;

                    // la cella ha il massimo peso nella
                    // distribuzione orizzontale dello spazio
                    gbc.weightx = 1;

                    // la cella ha il massimo peso nella
                    // distribuzione verticale dello spazio
                    gbc.weighty = 1;
                    break;

                case Layout.SOPRA:

                    // coordinate della cella
                    gbc.gridx = 1;
                    gbc.gridy = 0;

                    // span su tutte le colonne di destra
                    gbc.gridwidth = GridBagConstraints.REMAINDER;

                    // spazio di rispetto sotto
                    gbc.insets = new Insets(0, 0, margSopra, 0);
                    break;

                case Layout.SOTTO:

                    // coordinate della cella
                    gbc.gridx = 1;
                    gbc.gridy = 2;

                    // span su tutte le colonne di destra
                    gbc.gridwidth = GridBagConstraints.REMAINDER;

                    // spazio di rispetto sopra
                    gbc.insets = new Insets(margSotto, 0, 0, 0);
                    break;

                case Layout.SINISTRA:

                    // coordinate della cella
                    gbc.gridx = 0;
                    gbc.gridy = 1;

                    // spazio di rispetto a destra
                    gbc.insets = new Insets(0, 0, 0, margDx);
                    break;

                case Layout.DESTRA:

                    // coordinate della cella
                    gbc.gridx = 2;
                    gbc.gridy = 1;

                    // spazio di rispetto a sinistra
                    gbc.insets = new Insets(0, margSx, 0, 0);
                    break;

                default: // caso non definito
                    break;
            } // fine del blocco switch

            /* allinea il componente a sinistra nella propria cella */
            gbc.anchor = GridBagConstraints.LINE_START;

            /* non ridimensiona il componente in base alla cella */
            gbc.fill = GridBagConstraints.NONE;

            /* aggiunge il componente con la constraint */
            this.add(comp, gbc);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return comp;
    }


    /**
     * Aggiunge un componente al layout in una data posizione.
     * <p/>
     * Usa come costanti i valori della Enum di CVDecoratore
     *
     * @param comp il componente da aggiungere
     * @param pos la posizione alla quale aggiungere il componente
     *
     * @return il componente aggiunto
     *
     * @see CVDecoratore.Pos
     */
    public Component add(Component comp, CVDecoratore.Pos pos) {
        /* variabili e costanti locali di lavoro */
        int LayoutPos;

        try {    // prova ad eseguire il codice

            /* se esiste già un componente alla posizione data lo elimina  */
            if (this.isEsisteComponente(pos)) {
                this.eliminaComponente(pos);
            }// fine del blocco if

            /* valore di default */
            if (pos == null) {
                pos = CVDecoratore.Pos.DESTRA;
            }// fine del blocco if

            /* traduce il valore della enum nella corrispondente costante di Layout */
            switch (pos) {
                case SOPRA:
                    LayoutPos = Layout.SOPRA;
                    break;
                case SOTTO:
                    LayoutPos = Layout.SOTTO;
                    break;
                case SINISTRA:
                    LayoutPos = Layout.SINISTRA;
                    break;
                case DESTRA:
                    LayoutPos = Layout.DESTRA;
                    break;
                default: // caso non definito
                    LayoutPos = Layout.DESTRA;
                    break;
            } // fine del blocco switch

            /* aggiunge il componente al pannello */
            this.add(comp, LayoutPos);

            /* registra il componente nella mappa */
            this.registraComponente(pos, comp);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return comp;
    }


    public void add(Component comp, Object constraints) {

        /* protezione: usa le constraints solo
         * se il layout è di tipo LayoutCampo */
        if (this.getLayout() instanceof LayoutCampo) {
            super.add(comp, constraints);
        } else {
            super.add(comp);
        }// fine del blocco if-else
    }


    /**
     * Ritorna le constraint di layout per un componente del pannello
     * <p/>
     *
     * @param comp il componente
     *
     * @return le constraint per il componente
     */
    public GridBagConstraints getConstraints(Component comp) {
        /* variabili e costanti locali di lavoro */
        GridBagLayout gbl = null;
        GridBagConstraints gbc = null;

        try {    // prova ad eseguire il codice
            gbl = this.getGridBagLayout();
            if (gbl != null) {
                gbc = gbl.getConstraints(comp);
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return gbc;
    }


    /**
     * Assegna le constraint di layout a un componente del pannello
     * <p/>
     *
     * @param comp il componente
     * @param constraints da assegnare
     */
    public void setConstraints(Component comp, GridBagConstraints constraints) {
        /* variabili e costanti locali di lavoro */
        GridBagLayout gbl = null;

        try {    // prova ad eseguire il codice
            gbl = this.getGridBagLayout();
            if (gbl != null) {
                gbl.setConstraints(comp, constraints);
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Ritorna il GridBagLayout di riferimento per questo pannello campo.
     * <p/>
     *
     * @return il GridBagLayout del pannello
     */
    private GridBagLayout getGridBagLayout() {
        /* variabili e costanti locali di lavoro */
        GridBagLayout gbl = null;
        Layout layoutAlgos = null;
        LayoutManager2 layout = null;

        try {    // prova ad eseguire il codice
            layoutAlgos = this.getLayoutAlgos();
            layout = layoutAlgos.getLayoutRef();
            if (layout instanceof GridBagLayout) {
                gbl = (GridBagLayout)layout;
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return gbl;
    }


    /**
     * Verifica se esiste un componente a una data posizione.
     * <p/>
     *
     * @param posizione
     *
     * @return true se esiste un componente
     */
    private boolean isEsisteComponente(CVDecoratore.Pos posizione) {
        /* variabili e costanti locali di lavoro */
        boolean esiste = false;
        HashMap<CVDecoratore.Pos, Component> mappa;

        try {    // prova ad eseguire il codice
            mappa = this.getMappa();
            esiste = mappa.containsKey(posizione);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return esiste;
    }


    /**
     * Elimina un componente a una data posizione.
     * <p/>
     *
     * @param posizione
     */
    private void eliminaComponente(CVDecoratore.Pos posizione) {
        /* variabili e costanti locali di lavoro */
        Component comp;
        HashMap<CVDecoratore.Pos, Component> mappa;

        try {    // prova ad eseguire il codice
            mappa = this.getMappa();

            /* elimina il componente dal pannello */
            comp = mappa.get(posizione);
            if (comp != null) {
                this.remove(comp);
            }// fine del blocco if

            /* elimina la entry dalla mappa */
            mappa.remove(posizione);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Registra un componente nella mappa.
     * <p/>
     *
     * @param posizione
     * @param comp
     */
    private void registraComponente(CVDecoratore.Pos posizione, Component comp) {
        /* variabili e costanti locali di lavoro */
        HashMap<CVDecoratore.Pos, Component> mappa;

        try {    // prova ad eseguire il codice
            mappa = this.getMappa();

            /* elimina la entry dalla mappa */
            mappa.put(posizione, comp);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Elimina un componente.
     * <p/>
     *
     * @param posizione
     */
    public void remove(CVDecoratore.Pos posizione) {
        try { // prova ad eseguire il codice

            /* se esiste già un componente alla posizione data lo elimina  */
            if (this.isEsisteComponente(posizione)) {
                this.eliminaComponente(posizione);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    public Campo getCampo() {
        return campo;
    }


    private void setCampo(Campo campo) {
        this.campo = campo;
    }


    public PannelloComponenti getPannelloComponenti() {
        return pannelloComponenti;
    }


    public void setPannelloComponenti(PannelloComponenti pannelloComponenti) {
        this.pannelloComponenti = pannelloComponenti;
        this.pannelloComponenti.setPannelloCampo(this);
    }


    private HashMap<CVDecoratore.Pos, Component> getMappa() {
        return mappa;
    }


    private void setMappa(HashMap<CVDecoratore.Pos, Component> mappa) {
        this.mappa = mappa;
    }


    /**
     * Abilita o disabilita il componente.
     * <p/>
     *
     * @param flag per abilitare o disabilitare
     */
    public void setEnabled(boolean flag) {
        /* variabili e costanti locali di lavoro */
        Campo campo = null;

        try {    // prova ad eseguire il codice

            campo = this.getCampo();
            campo.setModificabile(flag);

            super.setEnabled(flag);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
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
    public PannelloCampo clona() {
        try {    // prova ad eseguire il codice
            /* valore di ritorno */
            return (PannelloCampo)super.clone();

        } catch (CloneNotSupportedException unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
            throw new InternalError();
        } /* fine del blocco try-catch */
    }


}// fine della classe
