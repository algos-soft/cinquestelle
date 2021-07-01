/**
 * Copyright: Copyright (c) 2005
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      21-feb-2005
 */
package it.algos.base.campo.video;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.db.CampoDB;
import it.algos.base.combo.Combo;
import it.algos.base.combo.ComboTavola;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.modulo.Modulo;
import it.algos.base.progetto.Progetto;

import javax.swing.JComponent;
import java.util.ArrayList;

/**
 * Campo video di tipo Combo per gestione di valori linkati.
 * </p>
 * Come componente video utilizza
 * un campo testo con lista dinamica associata (ComboTavola).
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 21-feb-2005 ore 15.47.44
 */
public final class CVComboTavola extends CVCombo {


    /**
     * Colonne aggiuntive visibili nel navigatore
     */
    private ArrayList<ColonnaCombo> colonneCombo;


    /**
     * Costruttore completo con parametri.
     *
     * @param unCampoParente campo 'contenitore' di questo oggetto
     */
    public CVComboTavola(Campo unCampoParente) {
        /* rimanda al costruttore della superclasse */
        super(unCampoParente);

        try { // prova ad eseguire il codice
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

        try { // prova ad eseguire il codice

            /* creazione della lista dei nomi di campo aggiuntivi */
            this.setColonneCombo(new ArrayList<ColonnaCombo>(0));

            /* regolazioni di default */
            this.setUsaNuovo(true);
            this.setUsaModifica(false);

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
     * Invoca il metodo sovrascritto della superclasse <br>
     */
    public void inizializza() {
        try { // prova ad eseguire il codice


            /* invoca il metodo sovrascritto della superclasse
             * (crea e registra un nuovo oggetto combo) */
            super.inizializza();


            /* inizializza l'oggetto combo */
            this.getCombo().inizializza();

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
        Combo combo;

        try { // prova ad eseguire il codice

            /* crea il combo tavola */
            combo = new ComboTavola(this);
            super.assegnaCombo(combo);

            /* invoca il metodo sovrascritto nella superclasse */
            super.creaComponentiInterni();


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Aggiunge una colonna del modulo linkato alla Vista per il combo.
     * <p/>
     *
     * @param nome del campo del modulo linkato
     */
    public void addColonnaCombo(String nome) {
        this.addColonnaCombo(null, nome);
    }


    /**
     * Aggiunge una colonna alla Vista per il combo.
     * <p/>
     *
     * @param nomeModulo nome del modulo
     * @param nomeCampo nome del campo
     */
    public void addColonnaCombo(String nomeModulo, String nomeCampo) {
        /* variabili e costanti locali di lavoro */
        ColonnaCombo colonna;

        try { // prova ad eseguire il codice
            colonna = new ColonnaCombo(nomeModulo, nomeCampo);
            this.getColonneCombo().add(colonna);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Ritorna l'elenco dei campi aggiuntivi per la Vista
     * nel selettore.
     * <p/>
     * Da chiamare dopo l'inizializzazione.
     *
     * @return l'elenco dei campi aggiuntivi.
     */
    public ArrayList<Campo> getCampiAggiuntivi() {
        /* variabili e costanti locali di lavoro */
        ArrayList<Campo> campi = null;
        Campo campo;

        try {    // prova ad eseguire il codice
            campi = new ArrayList<Campo>(0);
            for (ColonnaCombo colonna : this.getColonneCombo()) {
                campo = colonna.getCampo();
                campi.add(campo);
            }
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return campi;
    }


    /**
     * Metodo invocato quando si modifica il valore del combo
     * <p/>
     */
    protected void comboModificato() {
        /* variabili e costanti locali di lavoro */

        try { // prova ad eseguire il codice
            int a = 87;
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Recupera dalla GUI il valore video.
     * <p/>
     * In questo caso è uguale al video
     *
     * @return valore video per il CampoDati
     */
    public Object recuperaGUI() {
        return this.getCampoDati().getVideo();
    }


    /**
     * Regola la larghezza dei singoli componenti
     * interni al PannelloComponenti
     * <p/>
     * Metodo sovrascritto nelle sottoclassi <br>
     * E' responsabilita' di questo metodo assegnare la larghezza
     * preferita a tutti i componenti interni al pannello componenti.<br>
     */
    protected void regolaLarghezzaComponenti() {
        /* variabili e costanti locali di lavoro */
        int lar;

        try { // prova ad eseguire il codice

            /* recupera il componente */
            Combo combo = this.getCombo();
            if (combo!=null) {
                lar = this.getLarghezzaComponenti(); // dal campo scheda
                if (lar!=0) {         // definita nel campo scheda
                    combo.setLarghezza(lar);
                } else {              // non definita, la pone pari alla larghezza del campo linkato
                    Campo campoParente = this.getCampoParente();
                    if (campoParente!=null) {
                        CampoDB cdb = campoParente.getCampoDB();
                        if (cdb!=null) {
                            Campo cLink=cdb.getCampoValoriLinkato();
                            if (cLink!=null) {
                                JComponent comp = cLink.getCampoVideo().getComponente();
                                lar = comp.getPreferredSize().width;
                            }// fine del blocco if

                        }// fine del blocco if
                    }// fine del blocco if

                    // controllo finale se non riuscito mette 100
                    if (lar==0) {
                        lar=100;
                    }// fine del blocco if
                    
                    // assegno la larghezza
                    // devo farlo anche sul campo scheda se no non funziona non so perche'...
                    combo.setLarghezza(lar);
                    this.getCampoScheda().setLarghezzaComponenti(lar);

                }// fine del blocco if-else
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch



    }



    private ArrayList<ColonnaCombo> getColonneCombo() {
        return colonneCombo;
    }


    private void setColonneCombo(ArrayList<ColonnaCombo> colonneCombo) {
        this.colonneCombo = colonneCombo;
    }


    /**
     * Classe interna - Riferimento a una colonna aggiuntiva per il combo.
     * </p>
     * Serve per mantenere i riferimenti in fase statica
     * In fase dinamica rende il campo a cui si fa riferimento
     */
    private final class ColonnaCombo {

        /* nome del modulo */
        private String nomeModulo;

        /* nome del campo */
        private String nomeCampo;


        /**
         * Costruttore completo con parametri. <br>
         *
         * @param nomeModulo nome del modulo
         * @param nomeCampo nome del campo
         */
        public ColonnaCombo(String nomeModulo, String nomeCampo) {

            /* regola le variabili di istanza coi parametri */
            this.setNomeModulo(nomeModulo);
            this.setNomeCampo(nomeCampo);

        }// fine del metodo costruttore completo


        /**
         * Ritorna il campo a cui la colonna fa riferimento.
         * <p/>
         * Se è specificato il modulo, usa il campo del modulo specificato.
         * Altrimenti, usa il campo del modulo al quale questo campo è linkato.
         *
         * @return il campo
         */
        public Campo getCampo() {
            /* variabili e costanti locali di lavoro */
            Campo campo = null;
            String nomeModulo;
            String nomeCampo;
            Modulo modulo;
            boolean continua;

            try {    // prova ad eseguire il codice

                /* recupera il modulo */
                nomeModulo = this.getNomeModulo();
                if (Lib.Testo.isValida(nomeModulo)) {
                    modulo = Progetto.getModulo(nomeModulo);
                } else {
                    modulo = getCampoParente().getCampoDB().getModuloLinkato();
                }// fine del blocco if-else
                continua = modulo != null;

                /* recupera il campo */
                if (continua) {
                    nomeCampo = this.getNomeCampo();
                    campo = modulo.getCampo(nomeCampo);
                }// fine del blocco if

            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch

            /* valore di ritorno */
            return campo;
        }


        private String getNomeModulo() {
            return nomeModulo;
        }


        private void setNomeModulo(String nomeModulo) {
            this.nomeModulo = nomeModulo;
        }


        private String getNomeCampo() {
            return nomeCampo;
        }


        private void setNomeCampo(String nomeCampo) {
            this.nomeCampo = nomeCampo;
        }

    } // fine della classe 'interna'


}// fine della classe
