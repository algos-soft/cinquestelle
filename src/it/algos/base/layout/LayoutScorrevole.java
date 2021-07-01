/**
 * Copyright: Copyright (c) 2005
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      19-giu-2005
 */
package it.algos.base.layout;

import it.algos.base.errore.Errore;
import it.algos.base.libreria.LibMat;
import it.algos.base.pannello.Pannello;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Layout Manager per la gestione dello scorrevole.
 * </p>
 * Quando la dimensione del contenitore e' inferiore a quella minima
 * consentita dal layout, inserisce uno scorrevole, quando la dimensione
 * ritorna ad essere sufficiente, lo rimuove. <br>
 * E' possibile attivare o disattivare l'uso del bordo nello scorrevole. <br>
 * Nota: per il momento funziona solo se esteso da classi che usano BoxLayout. <br>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 19-giu-2005 ore 11.09.23
 */
public abstract class LayoutScorrevole extends LayoutBase {

    /**
     * flag - true per usare lo scorrevole quando il contenuto
     * non ci sta nel contenitore
     */
    private boolean usaScorrevole = false;

    /**
     * scorrevole eventualmente inserito quando il contenuto
     * non ci sta nel contenitore
     */
    private JScrollPane scorrevole = null;

    /**
     * layer intermedio gestito dallo scorrevole
     */
    private JPanel layer = null;

    /**
     * Bordo dello scorrevole
     */
    private Border bordoScorrevole = null;

    /**
     * flag - indica se lo scorrevole e' attualmente attivo
     */
    private boolean scorrevoleAttivo = false;

    /**
     * flag - indica il layout e' in fase di spostamento componenti
     * tra il layer e il contenitore principale
     */
    private boolean isSpostamento;


    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param orientamento codifica per l'orientamento (in interfaccia Layout)
     */
    LayoutScorrevole(int orientamento) {
        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia(orientamento);
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
    private void inizia(int asse) throws Exception {

        try { // prova ad eseguire il codice

            /* regolazioni iniziali di default */
            this.setUsaScorrevole(Layout.USA_SCORREVOLE);

            /* crea lo scorrevole */
            this.creaScorrevole(asse);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }/* fine del metodo inizia */


    /**
     * Regolazione iniziale dello scorrevole.
     * <p/>
     * Metodo invocato dal ciclo inizia del costruttore <br>
     *
     * @param orientamento codifica per l'orientamento (in interfaccia Layout)
     */
    private void creaScorrevole(int orientamento) {
        /* variabili e costanti locali di lavoro */
        JPanel layer;
        Layout layout;
        JScrollPane scorrevole;

        try {    // prova ad eseguire il codice

            /* crea layer e scorrevole */
            layer = new JPanel();
            layout = new LayoutInternoScorrevole(layer, orientamento);
            layer.setLayout(layout);
            this.setLayer(layer);
            scorrevole = new JScrollPane();
            scorrevole.setViewportView(layer);
            this.setScorrevole(scorrevole);

            /* regola l'aspetto grafico */
            this.setBordoScorrevole(scorrevole.getBorder());
            this.setScorrevoleBordato(USA_BORDO_SCORREVOLE);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Posiziona i componenti interni al contenitore.
     * <p/>
     * Sovrascrive il metodo layoutContainer, da LayoutManager. <br>
     *
     * @param container pannello contenitore <br>
     */
    public void layoutContainer(Container container) {

        try { // prova ad eseguire il codice

            /* Dimensiona e posiziona i componenti interni al contenitore */
            super.layoutContainer(container);

            /* Attiva o disattiva lo scorrevole */
            this.controllaScorrevole(container);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    public void addLayoutComponent(Component comp, Object constraints) {
        /* variabili e costanti locali di lavoro */
        boolean conScorrevole = false;
        JPanel layer;

        try { // prova ad eseguire il codice

            /* se lo scorrevole e' attivo, aggiunge il componente al layer
             * anziche' al contenitore principale
             * (funzione disattivata in caso di spostamento
             * interno dei componenti) */
            if (this.isUsaScorrevole()) {
                if (this.isScorrevoleAttivo()) {
                    if (!this.isSpostamento()) {
                        conScorrevole = true;
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

            if (conScorrevole) {

                layer = this.getLayer();
                layer.add(comp);
//                layer.validate();
//                layer.doLayout();
//                this.getScorrevole().validate();
//                this.getScorrevole().doLayout();

            } else {
                super.addLayoutComponent(comp, constraints);
            }// fine del blocco if-else


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Rimuove un componente.
     * <p/>
     * Se il componente è lo scorrevole, segnala
     * anche che non è più attivo.
     */
    public void removeLayoutComponent(Component comp) {

        try { // prova ad eseguire il codice

            super.removeLayoutComponent(comp);

            if (comp.equals(this.getScorrevole())) {
                this.setScorrevoleAttivo(false);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Attiva o disattiva lo scorrevole.
     * <p/>
     *
     * @param container pannello contenitore <br>
     */
    private void controllaScorrevole(Container container) {
        /* variabili e costanti locali di lavoro */
        Dimension dimMin;
        Dimension dimDisp;
        Container cont;

        try { // prova ad eseguire il codice

            /* inserisce o rimuove lo scorrevole.
             * opera solo se 'uso dello scorrevole e' abilitato */
            if (this.isUsaScorrevole()) {

                /* dimensione minima del contenuto */
                if (this.isScorrevoleAttivo()) {
                    cont = this.getLayer();
                } else {
                    cont = container;
                }// fine del blocco if-else
                dimMin = this.getDimMinNettaContenuto(cont);

                /* dimensione netta disponibile del contenitore */
                dimDisp = this.getDimNetta(container);

                /* se la dimensione del contenitore non e' sufficiente
                 * inserisce lo scorrevole, altrimenti lo rimuove */
                if (LibMat.isContenuta(dimDisp, dimMin)) {
                    this.rimuoviScorrevole(container);
                } else {
                    this.inserisciScorrevole(container);
                }// fine del blocco if-else

            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Inserisce lo scorrevole.
     * <p/>
     * Sposta il contenuto dal contenitore allo scorrevole <br>
     * Aggiunge lo scorrevole al contenitore <br>
     * Ridisegna il contenitore <br>
     *
     * @param container il contenitore nel quale inserire lo scorrevole
     */
    private void inserisciScorrevole(Container container) {
        /* variabili e costanti locali di lavoro */
        JScrollPane scorrevole;
        JPanel layer;
        Dimension dim;
        Pannello panDest;

        try {    // prova ad eseguire il codice

            if (!this.isScorrevoleAttivo()) {

                /* recupera il layer intermedio
                 * sposta i componenti dal contenitore al layer
                 * assegna al layer la dimensione minima necessaria
                 * per contenere i componenti */
                layer = this.getLayer();
                this.spostaComponenti(container, layer);
                dim = layer.getMinimumSize();
                layer.setPreferredSize(dim);

                /*
                 * assegna inizialmente allo scorrevole la dimensione
                 * netta del contenitore
                 */
                scorrevole = this.getScorrevole();
                dim = this.getDimNetta(container);
                scorrevole.setSize(dim);

                /*
                 * Aggiunge lo scorrevole al contenitore.
                 * Se il contenitore e' un Pannello lo aggiunge
                 * senza effettuare regolazioni sul componente.
                 * (lo scorrevole e' gia' regolato)
                 */
                if (container instanceof Pannello) {
                    panDest = (Pannello)container;
                    panDest.addOriginale(scorrevole);
                } else {
                    container.add(scorrevole);
                }// fine del blocco if-else

                /* assegna al layer lo stesso sfondo e opacita' del contenitore */
                layer.setOpaque(container.isOpaque());
                layer.setBackground(container.getBackground());

                /* regola il flag indicante che lo scorrevole e' attivo.
                 * va fatto prima di ridisegnare il contenitore! */
                this.setScorrevoleAttivo(true);

                /* dopo aver cambiato le carte in tavola forza
                 * il ridisegno del contenitore */
                container.invalidate();
                container.doLayout();

            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Rimuove lo scorrevole.
     * <p/>
     * Rimuove lo scorrevole dal contenitore <br>
     * Sposta il contenuto dallo scorrevole al contenitore <br>
     * Ridisegna il contenitore <br>
     *
     * @param container il contenitore dal quale rimuovere lo scorrevole
     */
    private void rimuoviScorrevole(Container container) {

        try {    // prova ad eseguire il codice

            if (this.isScorrevoleAttivo()) {

                /* rimuove lo scorrevole dal contenitore */
                container.remove(this.getScorrevole());

                /* sposta i componenti dal layer al container */
                this.spostaComponenti(this.getLayer(), container);

                /* regola il flag indicante che lo scorrevole non e' attivo
                 * va fatto prima di ridisegnare il contenitore! */
                this.setScorrevoleAttivo(false);

                /* dopo aver cambiato le carte in tavola forza
                 * il ridisegno del contenitore */
                container.invalidate();
                container.doLayout();

            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Sposta i componenti da un contenitore a un'altro.
     * <p/>
     *
     * @param origine contenitore di origine
     * @param destinazione contenitore di destinazione
     */
    private void spostaComponenti(Container origine, Container destinazione) {
        /* variabili e costanti locali di lavoro */
        Component[] componenti;
        Component comp;
        Pannello pannelloDest;

        try {    // prova ad eseguire il codice

            /* Sposta i componenti da un contenitore all'altro.
             * In teoria non sarebbe necessario rimuovere i componenti dal
             * contenitore di origine perche' viene fatto automaticamente.
             * (I componenti possono stare in un solo contenitore) */
            componenti = origine.getComponents();

            /*
             * Spazzola i componenti, li rimuove dal contenitore
             * di provenienza e li aggiunge al contenitore di destinazione.
             * Se il contenitore destinazione e' un Pannello, li aggiunge senza
             * effettuare alcuna regolazione sui componenti in entrata.
             * I componenti devono entrare cosi' come sono.
             */
            this.setSpostamento(true); // accende l'indicatore di spostamento in corso
            for (int k = 0; k < componenti.length; k++) {
                comp = componenti[k];
                origine.remove(comp);
                if (destinazione instanceof Pannello) {
                    pannelloDest = (Pannello)destinazione;
                    pannelloDest.addOriginale(comp);
                } else {
                    destinazione.add(comp);
                }// fine del blocco if-else
            } // fine del ciclo for
            this.setSpostamento(false); // spegne l'indicatore di spostamento in corso


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Ritorna la dimensione netta disponibile del contenitore.
     * <p/>
     * E' la dimensione del contenitore tolti i margini <br>
     *
     * @param container pannello contenitore <br>
     */
    private Dimension getDimNetta(Container container) {
        /* variabili e costanti locali di lavoro */
        Dimension dim = null;
        Dimension dimCont;
        Dimension dimBordi;

        try { // prova ad eseguire il codice

            dimCont = container.getSize();
            dimBordi = this.getDimMargini(container);
            dim = LibMat.sottraeDimensioni(dimCont, dimBordi);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return dim;
    }


    /**
     * Ritorna la dimensione minima netta del contenuto di un contenitore.
     * <p/>
     * E' la dimensione minima del contenuto tolti i margini <br>
     *
     * @param contenuto interno <br>
     */
    private Dimension getDimMinNettaContenuto(Container contenuto) {
        /* variabili e costanti locali di lavoro */
        Dimension dim = null;
        LayoutManager layout;

        try { // prova ad eseguire il codice
            layout = contenuto.getLayout();
            dim = layout.minimumLayoutSize(contenuto);
            dim = LibMat.sottraeDimensioni(dim, this.getDimMargini(contenuto));
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return dim;
    }


    /**
     * Calcola l'ingombro dei soli margini.
     * <p/>
     *
     * @param container il contenitore <br>
     *
     * @return ingombro totale dei margini
     */
    private Dimension getDimMargini(Container container) {
        /* variabili e costanti locali di lavoro */
        Dimension dim = null;
        int alt = 0; // altezza
        int lar = 0; // larghezza

        try { // prova ad eseguire il codice
            lar += container.getInsets().left;
            lar += container.getInsets().right;

            alt += container.getInsets().top;
            alt += container.getInsets().bottom;

            /* calcola la nuova dimensione */
            dim = new Dimension(lar, alt);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }/* fine del blocco try-catch */

        /* valore di ritorno */
        return dim;
    }/* fine del metodo */


    /**
     * Attiva o disattiva l'uso del bordo nello scorrevole.
     * <p/>
     *
     * @param flag per attivare/disattivare l'uso del bordo.
     */
    public void setScorrevoleBordato(boolean flag) {
        /* variabili e costanti locali di lavoro */
        Border bordo;

        try {    // prova ad eseguire il codice

            if (flag) {
                bordo = this.getBordoScorrevole();
            } else {
                bordo = null;
            }// fine del blocco if-else

            this.getScorrevole().setBorder(bordo);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Considera anche i componenti invisibili
     * disegnando il contenitore.
     * <p/>
     * Sovrascrive il metodo della superclasse.<br>
     * Regola il flag anche sul layout interno dello scorrevole.<br>
     *
     * @param flag true per considerare anche i componenti invisibili
     */
    public void setConsideraComponentiInvisibili(boolean flag) {
        /* variabili e costanti locali di lavoro */
        JPanel layer;
        LayoutManager layoutManager;
        Layout layout;

        try { // prova ad eseguire il codice

            /* regola il flag sul layout interno allo scorrevole */
            layer = this.getLayer();
            if (layer != null) {
                layoutManager = layer.getLayout();
                if (layoutManager != null) {
                    if (layoutManager instanceof Layout) {
                        layout = (Layout)layoutManager;
                        layout.setConsideraComponentiInvisibili(flag);
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

            /* invoca il metodo sovrascritto */
            super.setConsideraComponentiInvisibili(flag);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Determina se il layout puo' usare lo scorrevole automatico
     * <p/>
     *
     * @return true se puo' usare lo scorrevole
     */
    private boolean isUsaScorrevole() {
        return usaScorrevole;
    }


    /**
     * Abilita l'uso dello scorrevole.
     * <p/>
     *
     * @param flag true per abilitare l'uso dello scorrevole
     */
    public void setUsaScorrevole(boolean flag) {
        this.usaScorrevole = flag;
    }


    /**
     * Ritorna lo scorrevole usato dal layout
     * <p/>
     *
     * @return lo scorrevole
     */
    private JScrollPane getScorrevole() {
        return scorrevole;
    }


    private void setScorrevole(JScrollPane scorrevole) {
        this.scorrevole = scorrevole;
    }


    /**
     * Ritorna il layer intermedio contenitore dei componenti quando
     * lo scorrevole e' attivo
     * <p/>
     *
     * @return il layer contenitore
     */
    private JPanel getLayer() {
        return layer;
    }


    private void setLayer(JPanel layer) {
        this.layer = layer;
    }


    private Border getBordoScorrevole() {
        return bordoScorrevole;
    }


    private void setBordoScorrevole(Border bordoScorrevole) {
        this.bordoScorrevole = bordoScorrevole;
    }


    /**
     * Determina se lo scorrevole e' attivo al momento della chiamata
     * <p/>
     *
     * @return true se e' attivo
     */
    private boolean isScorrevoleAttivo() {
        return scorrevoleAttivo;
    }


    private void setScorrevoleAttivo(boolean flag) {
        this.scorrevoleAttivo = flag;
    }


    private boolean isSpostamento() {
        return isSpostamento;
    }


    private void setSpostamento(boolean spostamento) {
        isSpostamento = spostamento;
    }

}// fine della classe
