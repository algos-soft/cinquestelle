/**
 * Copyright: Copyright (c) 2006
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      22-mar-2006
 */
package it.algos.albergo.conto;

import it.algos.albergo.arrivipartenze.ConfermaPartenzaDialogo;
import it.algos.base.azione.Azione;
import it.algos.base.azione.AzioneBase;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.navigatore.NavigatoreL;
import it.algos.base.toolbar.ToolBar;

import javax.swing.Icon;
import java.awt.event.ActionEvent;

/**
 * Navigatore dei conti dentro al dialogo di conferma partenza.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 19-08-2010
 */
public final class ContoNavPartenze extends NavigatoreL {

    ConfermaPartenzaDialogo dialogo;
    private Azione azVaiConto;
    private Azione azChiudiConto;

    /**
     * Costruttore completo con parametri.
     * <p>
     * @param dialogo di riferimento
     */
    public ContoNavPartenze(ConfermaPartenzaDialogo dialogo) {
        /* rimanda al costruttore della superclasse */
        super(ContoModulo.get());

        try { // prova ad eseguire il codice

            /* regolazioni iniziali di riferimenti e variabili */
            this.setDialogo(dialogo);

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

        try { // prova ad eseguire il codice

            this.setNomeChiave(Conto.Nav.partenza.get());
            this.setNomeVista(Conto.Vis.partenza.get());
            this.setRigheLista(6);
            this.setUsaToolBarLista(true);
            this.setUsaStatusBarLista(false);
            this.setUsaToolBarLista(true);
            this.setUsaTotaliLista(false);
            this.getPortaleLista().setPosToolbar(ToolBar.Pos.sud);
            this.getPortaleLista().setUsaPulsantiStandard(false);
            this.getPortaleLista().getToolBar().getToolBar().setOpaque(true);


            azChiudiConto= new AzChiudiConto();
            this.addAzione(azChiudiConto);

            azVaiConto =  new AzVaiConto();
            this.addAzione(azVaiConto);
            

            this.setUsaFiltriPopLista(false);
//            this.getLista().getTavola().setEnabled(false);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizia

    @Override
    public void inizializza() {
        super.inizializza();
    }

    public void avvia() {
        
        super.avvia();

        // seleziona automaticamente la prima riga
        this.getLista().setRigaSelezionata(0);

    }// fine del metodo lancia


    /**
     * Sincronizza lo stato del Navigatore.
     * <p/>
     * Controlla lo stato di tutti i suoi componenti <br>
     * Elabora la sua business logic <br>
     * Regola la GUI di ogni conseguenza (menu, bottoni, ecc) <br>
     * Invoca il metodo sovrascritto della superclasse <br>
     */
    public void sincronizza() {

        super.sincronizza();

        try { // prova ad eseguire il codice
            int quante = this.getLista().getQuanteRigheSelezionate();
            boolean flag = (quante==1);
            azVaiConto.setEnabled(flag);
            azChiudiConto.setEnabled(flag);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }

    @Override
    protected boolean modificaRecord() {
        this.chiudiContoSelezionato();
        return true;
    }




    /**
     * Va al conto selezionato.
     * <p/>
     */
    private void vaiContoSelezionato() {
        /* variabili e costanti locali di lavoro */
        int codConto;

        try {    // prova ad eseguire il codice
            codConto = this.getLista().getChiaveSelezionata();
            this.getDialogo().vaiConto(codConto);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }

    /**
     * Chiude il conto selezionato.
     * <p/>
     */
    private void chiudiContoSelezionato() {
        /* variabili e costanti locali di lavoro */
        int codConto;

        try {    // prova ad eseguire il codice
            codConto = this.getLista().getChiaveSelezionata();
            this.getDialogo().chiudiConto(codConto);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }

    private ConfermaPartenzaDialogo getDialogo() {
        return dialogo;
    }

    private void setDialogo(ConfermaPartenzaDialogo dialogo) {
        this.dialogo = dialogo;
    }

/* Azione Vai al conto selezionato */
    private final class AzVaiConto extends AzioneBase {

        /**
         * Costruttore completo con parametri.
         */
        public AzVaiConto() {

            /* rimanda al costruttore della superclasse */
            super();

            /* regola le variabili*/
            Icon icona = Lib.Risorse.getIconaBase("Edit24");
            super.setIconaMedia(icona);
            super.setTooltip("Modifica il conto selezionato");
            super.setAttiva(true);
            super.setUsoLista(true);

        }// fine del metodo costruttore completo

        /**
         * actionPerformed, da ActionListener.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo delegato, nel gestore specifico associato
         * all' oggetto che genera questo evento <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void actionPerformed(ActionEvent unEvento) {
            vaiContoSelezionato();
        }

    }// fine della classe

    /* Azione Chiudi il conto selezionato */
    private final class AzChiudiConto extends AzioneBase {

        /**
         * Costruttore completo con parametri.
         */
        public AzChiudiConto() {

            /* rimanda al costruttore della superclasse */
            super();

            /* regola le variabili*/
            Class classe = ContoModulo.class;
            Icon icona = Lib.Risorse.getIcona(classe, "ChiusuraConto24");
            super.setIconaMedia(icona);
            super.setTooltip("Chiude il conto selezionato");
            super.setAttiva(true);
            super.setUsoLista(true);

        }// fine del metodo costruttore completo

        /**
         * actionPerformed, da ActionListener.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo delegato, nel gestore specifico associato
         * all' oggetto che genera questo evento <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void actionPerformed(ActionEvent unEvento) {
            chiudiContoSelezionato();
        }

    }// fine della classe




}// fine della classe