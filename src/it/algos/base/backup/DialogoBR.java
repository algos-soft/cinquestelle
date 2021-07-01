/**
 * Title:     DialogoBR
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      26-gen-2007
 */
package it.algos.base.backup;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.campo.video.decorator.CVDBottone;
import it.algos.base.dialogo.DialogoAnnullaConferma;
import it.algos.base.errore.Errore;
import it.algos.base.layout.Layout;
import it.algos.base.layout.LayoutFlusso;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.LibFile;
import it.algos.base.modulo.Modulo;
import it.algos.base.pannello.Pannello;
import it.algos.base.progetto.Progetto;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

/**
 * Dialogo di backup/restore.
 * </p>
 * Classe astratta di base
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 26-gen-2007
 */
public abstract class DialogoBR extends DialogoAnnullaConferma {

    /* oggetto Backup di riferimento */
    private Backup backup;

    /* campo per il percorso di backup/restore*/
    private Campo campoPercorso;

    /* eventuale avviso visualizzato nel dialogo */
    private JComponent compAvviso;

    /* icona del dialogo */
    private Icon icona;


    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param backup oggetto Backup di riferimento
     */
    public DialogoBR(Backup backup) {
        /* rimanda al costruttore della superclasse */
        super();

        this.setBackup(backup);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni immediate di riferimenti e variabili. <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */
        Campo campo;

        try { // prova ad eseguire il codice

            campo = this.creaCampoPercorso();
            this.setCampoPercorso(campo);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    public void inizializza() {
        /* variabili e costanti locali di lavoro */
        JComponent compAvviso;
        Campo campo;
        Pannello panMessaggio;
        JLabel labelIcona;
        JLabel labelTesto;
        Icon icona;

        try { // prova ad eseguire il codice

            /* aggiunge il componente avviso */
            compAvviso = this.getCompAvviso();
            if (compAvviso != null) {
                this.addComponente(compAvviso);
            }// fine del blocco if

            /* aggiunge il campo percorso */
            campo = this.getCampoPercorso();
            if (campo != null) {
                this.addCampo(campo);
            }// fine del blocco if

            /* inizializza nella superclasse */
            super.inizializza();

            /* modifica il pannello Messaggio */
            panMessaggio = this.getPannelloMessaggio();
            panMessaggio.removeAll();
            panMessaggio.getPanFisso().setLayout(new LayoutFlusso(panMessaggio,
                    Layout.ORIENTAMENTO_ORIZZONTALE));
            panMessaggio.setAllineamento(Layout.ALLINEA_CENTRO);
            panMessaggio.getLayoutAlgos().setUsaGapFisso(true);
            panMessaggio.getLayoutAlgos().setGapPreferito(10);
            icona = this.getIcona();
            labelIcona = new JLabel(icona);
            labelTesto = new JLabel(this.getMessaggio());
            panMessaggio.add(labelIcona);
            panMessaggio.add(labelTesto);


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


    } /* fine del metodo */


    /**
     * Crea il campo selettore del percorso.
     * <p/>
     *
     * @return il pannello file
     */
    private Campo creaCampoPercorso() {
        /* variabili e costanti locali di lavoro */
        Campo campo = null;
        String nome;

        try {    // prova ad eseguire il codice

            nome = this.getTitoloCampoPercorso();
            campo = CampoFactory.testo(nome);
            CVDBottone b = campo.decora().bottone();
            b.getBottone().setText("Seleziona...");
            b.getBottone().setEnabled(true);
            b.getBottone().setContentAreaFilled(true);
            b.getBottone().addActionListener(new BottonePremuto());
            campo.setLarScheda(380);

//            campo.aggiungeListener(new DialogoBR.DialogoBackup.BottoneSel());

//                this.addCampo(campo);
//                campo.setAbilitato(false); // fatto dopo l'inizializzazione
//                pan.add(campo);

//                /* crea e aggiunge il bottone */
//                bot = BottoneFactory.crea(this, "selezionaFile", "Seleziona...");
//                pan.add(bot);

//                /* dopo aver aggiunto i componenti, sblocco la dimensione */
//                pan.sbloccaDimMax();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return campo;
    }


    /**
     * Invocato quando si preme il bottone Seleziona
     */
    private class BottonePremuto implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            selezionaDir();
        }
    }


    /**
     * Seleziona la directory di import/export.
     * <p/>
     */
    private void selezionaDir() {
        /* variabili e costanti locali di lavoro */
        File dir = null;
        String path;
        boolean continua = true;

        try {    // prova ad eseguire il codice

            /* seleziona una directory esistente o ne crea una nuova */
            if (continua) {
                dir = LibFile.getDir(this.getTitoloDialogo());
                continua = (dir != null);
            }// fine del blocco if

            /* controlla che la directory selezionata sia valida */
            if (continua) {
                continua = this.isPercorsoValido(dir);
            }// fine del blocco if

            if (continua) {
                path = dir.getPath();
                this.getCampoPercorso().setValore(path);

                // todo - provato da me, il bottone conferma si accende da solo quando
                // todo - selezioni una dir valida.
                // todo - Il controllo è nel metodo standard isConfermabile().
                // todo - Se non ti funziona dobbiamo capire perché.
                // todo alex 23-02-07
                // this.getBottoneConferma().setEnabled(true);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Recupera il voce del dialogo.
     * <p/>
     * Sovrascritto dalle sottoclassi
     *
     * @return il voce del dialogo
     */
    protected String getTitoloDialogo() {
        return ("");
    }


    /**
     * Recupera il voce del campo percorso.
     * <p/>
     * Sovrascritto dalle sottoclassi
     *
     * @return il voce del campo percorso
     */
    protected String getTitoloCampoPercorso() {
        return ("");
    }


    /**
     * Controlla che la directory selezionata per il backup / restore sia valida.
     * <p/>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @return true se valida
     */
    protected boolean isPercorsoValido(File dir) {
        return true;
    }


    public boolean isConfermabile() {
        /* variabili e costanti locali di lavoro */
        boolean confermabile = false;

        try { // prova ad eseguire il codice
            confermabile = super.isConfermabile();
            if (confermabile) {
                confermabile = Lib.Testo.isValida(this.getPercorso());
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return confermabile;
    }


    /**
     * Ritorna il percorso di backup.
     * <p/>
     */
    public String getPercorso() {
        /* variabili e costanti locali di lavoro */
        String percorso = null;

        try {    // prova ad eseguire il codice
            percorso = (String)this.getCampoPercorso().getValore();
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return percorso;
    }


    /**
     * Ritorna l'elenco completo e ordinato dei moduli
     * per i quali effettuare il backup / restore.
     * <p/>
     * L'ordine rispecchia le dipendenze date dalle relazioni
     *
     * @return l'elenco completo e ordinato dei moduli
     */
    private ArrayList<Modulo> caricaModuliBackup() {
        /* variabili e costanti locali di lavoro */
        ArrayList<Modulo> moduli = null;

        try {    // prova ad eseguire il codice

            /* tutti i moduli del Progetto */
            moduli = Progetto.getModuliPreorder();

            /* toglie quelli che eventualmente hanno la stessa tavola
             * (moduli che estendono altri moduli, es Cliente -> Anagrafica) */
            moduli = this.eliminaPariTavola(moduli);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return moduli;
    }


    /**
     * Elimina da una lista di moduli quelli che hanno la stessa tavola.
     * <p/>
     *
     * @param listaIn lista da elaborare
     *
     * @return la lista elaborata
     */
    private ArrayList<Modulo> eliminaPariTavola(ArrayList<Modulo> listaIn) {
        /* variabili e costanti locali di lavoro */
        ArrayList<Modulo> listaOut = new ArrayList<Modulo>();

        try {    // prova ad eseguire il codice
            for (Modulo modulo : listaIn) {
                if (!this.isEsistePariTavola(modulo, listaOut)) {
                    listaOut.add(modulo);
                }// fine del blocco if
            }
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return listaOut;
    }


    /**
     * Controlla se esiste un modulo con la stessa tavola.
     * <p/>
     *
     * @param modulo da controllare
     * @param lista sulla quale effettuare il controllo
     *
     * @return true se esiste già un modulo con la stessa tavola nella lista
     */
    private boolean isEsistePariTavola(Modulo modulo, ArrayList<Modulo> lista) {
        /* variabili e costanti locali di lavoro */
        boolean esiste = false;
        String tavolaChk;
        String tavola;

        try {    // prova ad eseguire il codice
            tavolaChk = modulo.getTavola();
            for (Modulo unModulo : lista) {
                tavola = unModulo.getTavola();
                if (tavola.equals(tavolaChk)) {
                    esiste = true;
                    break;
                }// fine del blocco if
            }
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return esiste;
    }


    /**
     * Ritorna l'elenco completo e ordinato dei moduli
     * per i quali effettuare il backup / restore.
     * <p/>
     * N.B. per adesso li ritorna tutti; in futuro il dialogo
     * di preparazione del backup potrebbe consentire
     * di filtrare l'elenco.
     * L'ordine rispecchia le dipendenze date dalle relazioni
     *
     * @return l'elenco completo e ordinato dei moduli
     */
    public ArrayList<Modulo> getModuliBackup() {
        return this.caricaModuliBackup();
    }


    protected Backup getBackup() {
        return backup;
    }


    private void setBackup(Backup backup) {
        this.backup = backup;
    }


    private Campo getCampoPercorso() {
        return campoPercorso;
    }


    private void setCampoPercorso(Campo campo) {
        this.campoPercorso = campo;
    }


    private Icon getIcona() {
        return icona;
    }


    protected void setIcona(Icon icona) {
        this.icona = icona;
    }


    private JComponent getCompAvviso() {
        return compAvviso;
    }


    protected void setCompAvviso(JComponent compAvviso) {
        this.compAvviso = compAvviso;
    }
}// fine della classe
