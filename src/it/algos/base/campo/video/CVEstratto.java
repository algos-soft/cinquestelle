/**
 * Title:     CVEstratto
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      28-apr-2004
 */
package it.algos.base.campo.video;

import it.algos.base.azione.Azione;
import it.algos.base.azione.lista.AzEliminaRecord;
import it.algos.base.azione.lista.AzModificaRecord;
import it.algos.base.azione.lista.AzNuovoRecord;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.logica.CLEstratto;
import it.algos.base.campo.logica.CampoLogica;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloBase;
import it.algos.base.pannello.PannelloFactory;
import it.algos.base.toolbar.ToolBar;
import it.algos.base.toolbar.ToolBarBase;
import it.algos.base.wrapper.EstrattoBase;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Campo Video contenente un estratto di un altro modulo
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 28-apr-2004 ore 7.39.10
 */
public class CVEstratto extends CVBase {

    /**
     * Determina se usare un solo bottone (che cambia automaticamente icona)
     * per le funzioni nuovo e modifica. (In tal caso, il bottone usato
     * per entrambe le funzioni è il bottone Modifica)
     */
    private boolean usaNuovoModifica;

    /**
     * Pannello placeholder contenente l'estratto da visualizzare
     */
    private Pannello panEstratto;

    /**
     * Azione per la creazione del record
     */
    private Azione azioneNuovo;

    /**
     * Azione per la modifica (o creazione e modifica) del record
     */
    private Azione azioneModifica;

    /**
     * Azione per la eliminazione del record
     */
    private Azione azioneElimina;

    private ToolBar toolBar;


    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo utilizzando eventuali valori di default
     */
    public CVEstratto() {
        /** rimanda al costruttore di questa classe */
        this(null);
    } /* fine del metodo costruttore base */


    /**
     * Costruttore completo
     *
     * @param unCampoParente campo 'contenitore' di questo oggetto
     */
    public CVEstratto(Campo unCampoParente) {
        /** rimanda al costruttore della superclasse */
        super(unCampoParente);

        /** regolazioni iniziali di riferimenti e variabili */
        try {
            this.inizia();
        } catch (Exception unErrore) {
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore completo */


    /**
     * Regolazioni immediate di riferimenti e variabili. <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        try { // prova ad eseguire il codice

            /* di default usa un solo bottone per Nuovo e Modifica */
            this.setUsaNuovoModificaInsieme(true);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizia


    /**
     * Regolazioni dinamiche dell'oggetto.
     * <p/>
     * Regola le caratteristiche dinamiche in base alla impostazione
     * corrente delle caratteristiche statiche <br>
     * Metodo invocato dalla classe che crea questo oggetto (di norma) <br>
     * Viene eseguito una sola volta <br>
     * Sovrascritto nelle sottoclassi <br>
     */
    @Override
    public void inizializza() {
        this.creaComponentiInterni();
        super.inizializza();
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
    @Override
    public void avvia() {
        super.avvia();
    } /* fine del metodo */


    /**
     * Crea i componenti interni al pannelloComponenti.
     * <p/>
     * Metodo invocato dal ciclo inizia o inizializza o avvia, a seconda
     * delle esigenze del tipo specifico di campo <br>
     * Metodo sovrascritto dalle sottoclassi <br>
     * E' responsabilita' di questo metodo:
     * - creare i componenti GUI interni al PannelloComponenti<br>
     * - aggiungere i listener ai componenti GUI creati
     * - eventualmente assegnare un layout al PannelloComponenti<br>
     * - aggiungere i componenti al pannelloComponenti<br>
     */
    @Override
    public void creaComponentiInterni() {
        /* variabili e costanti locali di lavoro */
        ToolBar toolbar;
        Pannello panTot;
        PannelloBase placeholder;
        Border bordoIn;
        Border bordoOut;
        Border bordo;


        try { // prova ad eseguire il codice

            /* crea le azioni */
            this.creaAzioni();

            /* crea la toolbar */
            toolbar = this.setToolBar(new ToolBarCampo());

            /* crea e registra il pannello placeholder che conterrà l'estratto */
            placeholder = new PannelloBase();
            placeholder.setLayout(new BorderLayout());
            bordoIn = BorderFactory.createEmptyBorder(2, 2, 2, 2);
            bordoOut = BorderFactory.createEtchedBorder();
            bordo = BorderFactory.createCompoundBorder(bordoOut, bordoIn);
            placeholder.setBorder(bordo);

            this.setPanEstratto(placeholder);

            /* crea un pannello orizzontale completo di toolbar e placeholder */
            panTot = PannelloFactory.orizzontale(null);
            panTot.setUsaGapFisso(true);
            panTot.setGapPreferito(0);
            panTot.add(toolbar);
            panTot.add(placeholder);

            /* regola il riferimento al componente principale */
            this.setComponente(panTot.getPanFisso());

            /* inserisce il componente principale nel pannelloComponenti */
            this.getPannelloComponenti().add(this.getComponente());

            /* invoca il metodo sovrascritto nella superclasse */
            super.creaComponentiInterni();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Crea e registra le azioni del campo.
     * <p/>
     */
    private void creaAzioni() {
        /* variabili e costanti locali di lavoro */
        Azione azione;

        try {    // prova ad eseguire il codice

            azione = new NuovoRecordAz();
            this.setAzioneNuovo(azione);
            azione = new ModificaRecordAz();
            this.setAzioneModifica(azione);
            azione = new EliminaRecordAz();
            this.setAzioneElimina(azione);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Creazione di un record del campo.
     * <p/>
     */
    private void nuovoRecord() {
        /* variabili e costanti locali di lavoro */
        CLEstratto campoLogica;

        try {    // prova ad eseguire il codice
            campoLogica = this.getCampoLogica();
            campoLogica.nuovoRecord();
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Modifica di un record del campo (o nuovo-modifica).
     * <p/>
     */
    private void modificaRecord() {
        /* variabili e costanti locali di lavoro */
        CLEstratto campoLogica;

        try {    // prova ad eseguire il codice
            campoLogica = this.getCampoLogica();
            if (this.isUsaNuovoModifica()) {
                if (campoLogica.isEsisteRecordEsterno()) {
                    campoLogica.modificaRecord();
                } else {
                    campoLogica.nuovoRecord();
                }// fine del blocco if-else
            } else {
                campoLogica.modificaRecord();
            }// fine del blocco if-else

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Eliminazione di un record del campo.
     * <p/>
     */
    private void eliminaRecord() {
        /* variabili e costanti locali di lavoro */
        CLEstratto campoLogica;

        try {    // prova ad eseguire il codice
            campoLogica = this.getCampoLogica();
            campoLogica.eliminaRecord();
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * aggiorna la GUI in base al valore video
     * <p/>
     *
     * @param unValore valore video proveniente dal CampoDati
     */
    public void aggiornaGUI(Object unValore) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Pannello pan;
        Pannello panEst = null;
        int cod;
        EstrattoBase estrattoBase;
        CLEstratto campoLogica;

        try { // prova ad eseguire il codice

            /* recupera il campo Logica */
            campoLogica = (CLEstratto)this.getCampoLogica();

            /* recupera il codice del record esterno dal video */
            cod = Libreria.getInt(unValore);

            /* recupera l'estratto */
            estrattoBase = campoLogica.getEstratto(cod);
            continua = (estrattoBase != null);

            if (continua) {
                panEst = estrattoBase.getPannello();
                continua = (panEst != null);
            }// fine del blocco if

            if (continua) {

                /* inserisce il pannello dell'estratto nel pannello placeholder */
                pan = this.getPanEstratto();
                pan.removeAll();
                pan.add(panEst);

                /* invalida il componente principale contenuto nel Pannello Componenti */
                JComponent compMain = this.getComponente();
                compMain.invalidate();
                compMain.validate();
                compMain.repaint();

                /* reimpacchetta il campo - fissa le dimensioni del PannelloCampo */
                this.pack();

            }// fine del blocco if

            /* regola l'abilitazione dei bottoni */
            this.abilitaBottoni();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } /* fine del metodo */


    /**
     * Abilita i bottoni in funzione dello stato del campo.
     * <p/>
     */
    private void abilitaBottoni() {
        /* variabili e costanti locali di lavoro */
        Azione azNuovo = this.getAzioneNuovo();
        Azione azModifica = this.getAzioneModifica();
        Azione azElimina = this.getAzioneElimina();
        boolean abilitaNuovo = false;
        boolean abilitaModifica = false;
        boolean abilitaElimina = false;
        boolean continua;
        Campo campo;
        String nomeIcona;
        String tooltip;
        Icon icona;
        String testoEtichetta;
        String testoTip;

        try {    // prova ad eseguire il codice

            campo = this.getCampoParente();

            /* il campo deve essere abilitato */
            continua = campo.isAbilitato();

            /* il campo deve essere modificabile */
            if (continua) {
                continua = campo.isModificabile();
            }// fine del blocco if

            /* invoca i metodi di controllo */
            if (continua) {
                if (this.isUsaNuovoModifica()) {   //bottone unico
                    abilitaNuovo = false;
                    abilitaModifica = (this.isPossibileNuovo() | this.isPossibileModifica());
                } else {     //bottoni separati
                    abilitaNuovo = this.isPossibileNuovo();
                    abilitaModifica = this.isPossibileModifica();
                }// fine del blocco if-else
                abilitaElimina = this.isPossibileElimina();
            }// fine del blocco if

            azNuovo.setEnabled(abilitaNuovo);
            azModifica.setEnabled(abilitaModifica);
            azElimina.setEnabled(abilitaElimina);

            /* se il campo ha una etichetta, la recupera per usarla nel tooltip */
            testoEtichetta = this.getCampoParente().getTestoEtichetta();
            if (Lib.Testo.isValida(testoEtichetta)) {
                testoTip = testoEtichetta;
            } else {
                testoTip = "record";
            }// fine del blocco if-else

            /* regola il tooltip dell'azione nuovo */
            azNuovo.getAzione().putValue(Action.SHORT_DESCRIPTION, "Crea " + testoTip);

            /* regola l'icona e il tooltip dell'azione nuovo (o nuovo-modifica) */
            if (this.isUsaNuovoModifica()) {
                if (!this.getCampoLogica().isEsisteRecordEsterno()) {
                    nomeIcona = "Nuovo16";
                    tooltip = "Crea " + testoTip;
                } else {
                    nomeIcona = "Edit16";
                    tooltip = "Modifica " + testoTip;
                }// fine del blocco if-else
            } else {
                nomeIcona = "Edit16";
                tooltip = "Modifica " + testoTip;
            }// fine del blocco if-else
            icona = Lib.Risorse.getIconaBase(nomeIcona);
            azModifica.getAzione().putValue(Azione.ICONA_PICCOLA, icona);
            azModifica.getAzione().putValue(Action.SHORT_DESCRIPTION, tooltip);

            /* regola il tooltip dell'azione elimina */
            azElimina.getAzione().putValue(Action.SHORT_DESCRIPTION, "Elimina " + testoTip);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Determina se è possibile creare un nuovo record collegato.
     * <p/>
     *
     * @return true se è possibile
     */
    protected boolean isPossibileNuovo() {
        /* variabili e costanti locali di lavoro */
        boolean possibile = false;
        boolean esisteRecordEsterno;

        try {    // prova ad eseguire il codice
            /* si può creare un nuovo record se non ne esiste già uno */
            esisteRecordEsterno = this.getCampoLogica().isEsisteRecordEsterno();
            if (!esisteRecordEsterno) {
                possibile = true;
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return possibile;
    }


    /**
     * Determina se è possibile modificare il record collegato.
     * <p/>
     *
     * @return true se è possibile
     */
    protected boolean isPossibileModifica() {
        /* variabili e costanti locali di lavoro */
        boolean possibile = false;
        boolean esisteRecordEsterno;

        try {    // prova ad eseguire il codice
            /* si può modificare il record esterno solo se esiste */
            esisteRecordEsterno = this.getCampoLogica().isEsisteRecordEsterno();
            if (esisteRecordEsterno) {
                possibile = true;
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return possibile;
    }


    /**
     * Determina se è possibile eliminare il record collegato.
     * <p/>
     *
     * @return true se è possibile
     */
    protected boolean isPossibileElimina() {
        /* variabili e costanti locali di lavoro */
        boolean possibile = false;
        boolean esisteRecordEsterno;

        try {    // prova ad eseguire il codice
            /* si può eliminare il record esterno solo se esiste */
            esisteRecordEsterno = this.getCampoLogica().isEsisteRecordEsterno();
            if (esisteRecordEsterno) {
                possibile = true;
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return possibile;
    }


    /**
     * Regola il componente GUI per essere modificabile o meno.
     * <p/>
     * Metodo sovrascritto dalle sottoclassi<br>
     *
     * @param flag true se il campo deve essere modificabile
     */
    protected void regolaModificabile(boolean flag) {
        /* variabili e costanti locali di lavoro */
        Azione azione;

        try {    // prova ad eseguire il codice
            super.regolaModificabile(flag);

            azione = this.getAzioneModifica();
            if (azione != null) {
                azione.setEnabled(flag);
            }// fine del blocco if

            azione = this.getAzioneElimina();
            if (azione != null) {
                azione.setEnabled(flag);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    protected CLEstratto getCampoLogica() {
        /* variabili e costanti locali di lavoro */
        CLEstratto clEstratto = null;
        CampoLogica campoLogica;

        try { // prova ad eseguire il codice
            campoLogica = super.getCampoLogica();
            if (campoLogica != null) {
                if (campoLogica instanceof CLEstratto) {
                    clEstratto = (CLEstratto)campoLogica;
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return clEstratto;
    }


    /**
     * aggiorna il valore video con lo stato della GUI <br>
     */
    public Object recuperaGUI() {
        /* valore di ritorno, è il valore video stesso, qui la gui non è editabile */
        return this.getCampoDati().getVideo();
    } /* fine del metodo */


    public JButton getBottoneNuovo() {
        return this.getToolBar().getBottone(Azione.NUOVO_RECORD);
    }


    public JButton getBottoneModifica() {
        return this.getToolBar().getBottone(Azione.MODIFICA_RECORD);
    }


    public JButton getBottoneElimina() {
        return this.getToolBar().getBottone(Azione.ELIMINA_RECORD);
    }


    private boolean isUsaNuovoModifica() {
        return usaNuovoModifica;
    }


    /**
     * Determina se usare un bottone unico o due bottoni separati per Nuovo e Modifica
     * <p/>
     *
     * @param flag true per usare un solo bottone con icona variabile,
     * false per usare due bottoni separati
     */
    public void setUsaNuovoModificaInsieme(boolean flag) {
        this.usaNuovoModifica = flag;
    }


    private Pannello getPanEstratto() {
        return panEstratto;
    }


    private void setPanEstratto(Pannello panEstratto) {
        this.panEstratto = panEstratto;
    }


    private Azione getAzioneNuovo() {
        return azioneNuovo;
    }


    private void setAzioneNuovo(Azione azioneNuovo) {
        this.azioneNuovo = azioneNuovo;
    }


    private Azione getAzioneModifica() {
        return azioneModifica;
    }


    private void setAzioneModifica(Azione azioneModifica) {
        this.azioneModifica = azioneModifica;
    }


    private Azione getAzioneElimina() {
        return azioneElimina;
    }


    private void setAzioneElimina(Azione azioneElimina) {
        this.azioneElimina = azioneElimina;
    }


    private ToolBar getToolBar() {
        return toolBar;
    }


    private ToolBar setToolBar(ToolBar toolBar) {
        this.toolBar = toolBar;
        return getToolBar();
    }


    /**
     * Modifica di un record del campo.
     */
    public final class NuovoRecordAz extends AzNuovoRecord {

        /**
         * actionPerformed, da ActionListener.
         * </p>
         * Esegue l'azione <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void actionPerformed(ActionEvent unEvento) {
            nuovoRecord();
        }
    }// fine della classe


    /**
     * Modifica di un record del campo.
     */
    public final class ModificaRecordAz extends AzModificaRecord {

        /**
         * actionPerformed, da ActionListener.
         * </p>
         * Esegue l'azione <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void actionPerformed(ActionEvent unEvento) {
            modificaRecord();
        }
    }// fine della classe


    /**
     * Eliminazione di un record del campo.
     */
    public final class EliminaRecordAz extends AzEliminaRecord {

        /**
         * actionPerformed, da ActionListener.
         * </p>
         * Esegue l'azione <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void actionPerformed(ActionEvent unEvento) {
            eliminaRecord();
        }
    }// fine della classe


    /**
     * Toolbar del campo.
     * </p>
     */
    private final class ToolBarCampo extends ToolBarBase {

        /**
         * Costruttore base senza parametri. <br>
         * Indispensabile anche se non viene utilizzato
         * (anche solo per compilazione in sviluppo) <br>
         * Rimanda al costruttore completo <br>
         * Utilizza eventuali valori di default <br>
         */
        public ToolBarCampo() {
            /* rimanda al costruttore di questa classe */
            super(null);
            try { // prova ad eseguire il codice
                this.inizia();
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }// fine del metodo costruttore base


        /**
         * Regolazioni immediate di riferimenti e variabili. <br>
         * Metodo chiamato direttamente dal costruttore <br>
         *
         * @throws Exception unaEccezione
         */
        private void inizia() throws Exception {
            try { // prova ad eseguire il codice
                this.setTipoIcona(ToolBar.ICONA_PICCOLA);

                /* aggiunge il bottone nuovo solo se usa bottoni separati */
                if (!isUsaNuovoModifica()) {
                    this.addBottone(getAzioneNuovo());
                }// fine del blocco if

                /* aggiunge il bottone modifica */
                this.addBottone(getAzioneModifica());

                /* aggiunge il bottone elimina */
                this.addBottone(getAzioneElimina());

                this.inizializza();
                this.avvia();
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

        }

    } // fine della classe 'interna'


}// fine della classe
