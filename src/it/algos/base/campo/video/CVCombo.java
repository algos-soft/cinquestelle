/**
 * Copyright: Copyright (c) 2005
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      21-feb-2005
 */
package it.algos.base.campo.video;

import it.algos.base.campo.base.Campo;
import it.algos.base.combo.Combo;
import it.algos.base.combo.ComboLista;
import it.algos.base.errore.Errore;
import it.algos.base.pannello.Pannello;
import it.algos.base.portale.Portale;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Componente video di tipo Combo.
 * </p>
 * Questa classe concreta: <ul>
 * <li> Crea il componente GUI specifico di questa classe </li>
 * <li> Aggiunge i Listener al componente specifico </li>
 * <li> Regola i font, la dimensione e la posizione del componente specifico </li>
 * <li> Regola le dimensioni del pannelloComponenti </li>
 * <li> Implementa i metodi astratti della superclasse per regolare le
 * conversioni tra i vari tipi di dati </li>
 * <li> L'attributo <i>video</i> del <code>CampoDati</code> associato,
 * <strong>deve</strong> essere di tipo testo </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 21-feb-2005 ore 15.47.44
 */
public abstract class CVCombo extends CVBase {


    /**
     * oggetto GUI principale
     */
    private Combo combo = null;

    /**
     * flag - se il combo usa la funzione Nuovo Record
     */
    private boolean usaNuovo = false;

    /**
     * flag - se il combo usa la funzione Modifica Record
     */
    private boolean usaModifica = false;


    /**
     * flag - se il combo usa la voce Non Secificato
     */
    private boolean usaNonSpecificato = false;


    /**
     * Costruttore completo con parametri.
     *
     * @param unCampoParente campo 'contenitore' di questo oggetto
     */
    public CVCombo(Campo unCampoParente) {
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

            /* svuota il pannello componenti */
            this.getPannelloComponenti().removeAll();

            /* crea il componente */
            this.creaComponentiInterni();

            /* invoca il metodo sovrascritto della superclasse */
            super.inizializza();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    public void avvia() {
        super.avvia();
        try { // prova ad eseguire il codice
            this.getCombo().avvia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    } /* fine del metodo */


    /**
     * Assegna un Combo al campo.
     * <p/>
     * Chiamato da creaComponentiInterni delle sottoclassi
     * Metodo invocato dal ciclo inizializza <br>
     *
     * @param combo da assegnare
     */
    protected void assegnaCombo(Combo combo) {
        /* variabili e costanti locali di lavoro */
        String nomeCampo;
        Pannello pc;
        JComponent comp;

        try { // prova ad eseguire il codice

            /* registra l'oggetto GUI principale */
            this.setCombo(combo);

            /* recupera il componente grafico */
            comp = this.getCombo().getComponente();

            /* regola il nome (per rintracciare chi genera un evento) */
            nomeCampo = this.getCampoParente().getNomeInterno();
            comp.setName(nomeCampo);

            /* registra il componente grafico */
            this.setComponente(comp);

            /* aggiunge il componente principale al pannelloComponenti */
            pc = this.getPannelloComponenti();
            pc.add(comp);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regola la larghezza dei singoli componenti
     * interni al PannelloComponenti
     * <p/>
     * Regola la larghezza del Combo.
     */
    protected void regolaLarghezzaComponenti() {
        /* variabili e costanti locali di lavoro */
        Combo combo;
        int lar;

        try { // prova ad eseguire il codice

            /* recupera il componente */
            combo = this.getCombo();

            /*
             * Il combo ha già una larghezza di default.
             * La larghezza viene modificata solo se è stata
             * specificata una larghezza differente.
             */
            if (combo != null) {
                lar = this.getLarghezzaComponenti();
                if (lar != 0) {
                    combo.setLarghezza(lar);
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regola l'altezza dei singoli componenti
     * interni al PannelloComponenti
     * <p/>
     * Sovrascritto vuoto perché l'altezza è regolata <br>
     * nel componente Combo.
     */
    protected void regolaAltezzaComponenti() {
    }


    /**
     * Aggiunge i <code>Listener</code>.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Aggiunge ai componenti video di questo campo gli eventuali
     * ascoltatori delle azioni (eventi) <br>
     * Metodo invocato da SchedaBase.inizializza <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @param portale il portale di riferimento del campo
     *
     * @see it.algos.base.scheda.SchedaBase#inizializza()
     */
    public void aggiungeListener(Portale portale) {
        /* variabili e costanti locali di lavoro */
        Combo combo;

        try { // prova ad eseguire il codice

            /* delega l'aggiunta dei listeners al combo */
            combo = this.getCombo();
            if (combo != null) {
                combo.aggiungeListener(portale);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Restituisce l'elenco dei componenti video del campo.
     * <p/>
     * In questo caso e' un solo componente.<br>
     *
     * @return l'elenco dei componenti video
     */
    protected ArrayList<JComponent> getComponentiVideo() {
        /* variabili e costanti locali di lavoro */
        ArrayList<JComponent> componenti = null;
        Combo combo;
        JComponent comp;

        try { // prova ad eseguire il codice
            combo = this.getCombo();
            if (combo != null) {
                comp = combo.getComponenteSelettore();
                if (comp != null) {
                    componenti = new ArrayList<JComponent>();
                    componenti.add(comp);
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return componenti;
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
        return null;
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
        /* variabili e costanti locali di lavoro */
        Combo combo;

        try {    // prova ad eseguire il codice
            combo = this.getCombo();
            if (combo != null) {
                combo.aggiornaGUI();
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    }


    /**
     * Recupera dalla GUI il valore video.
     * <p/>
     *
     * @return valore video per il CampoDati
     */
    public Object recuperaGUI() {
        return null;
    }

//    /**
//     * Recupera dalla GUI il valore video.
//     * <p/>
//     *
//     * @return valore video per il CampoDati
//     */
//    public Object recuperaGUI() {
//        /* variabili e costanti locali di lavoro */
//        Integer posizione = null;
//        int valore;
//        Object unValoreVideoVuoto;
//
//        try {    // prova ad eseguire il codice
//
//            /* recupera la posizione selezionata nel combo */
//            valore = this.getCombo().getSelectedIndex();
//
//            /* controlla la selezione nulla */
//            if (valore == SELEZIONE_NULLA) {
//                unValoreVideoVuoto
//                        = unCampoParente.getCampoDati().getValoreVideoVuoto();
//                valore = (Integer)unValoreVideoVuoto;
//            } else {
//                /* la pop-lista parte da zero, mentre posizione inizia da 1 */
//                valore++;
//            } /* fine del blocco if/else */
//
//            /* crea l'oggetto da restituire */
//            posizione = valore;
//
//        } catch (Exception unErrore) {    // intercetta l'errore
//            Errore.crea(unErrore);
//        } /* fine del blocco try-catch */
//
//        /* valore di ritorno */
//        return posizione;
//    }

//    /**
//     * Regola la larghezza del pannelloComponenti.
//     * <p/>
//     * Metodo invocato dal Layout quando deve posizionare il campo <br>
//     * Viene utilizzato per il dimensionamento dall'esterno <br>
//     * Metodo sovrascritto nelle sottoclassi <br>
//     *
//     * @param larghezza da assegnare al pannelloComponenti
//     */
//    public void setLarghezzaPannelloComponenti(int larghezza) {
//        /* variabili e costanti locali di lavoro */
//        Dimension dim;
//
//        try { // prova ad eseguire il codice
//            /* invoca il metodo sovrascritto della superclasse */
//            super.setLarghezzaPannelloComponenti(larghezza);
//
//            /* regola le dimensioni del combo */
//            dim = this.getPannelloBaseComponenti().getSize();
//            this.getComboBox().setSize(dim);
//
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//    }


    /**
     * Abilita/disabilita il componente GUI del campo per la scheda.
     * <p/>
     * Se disabilitato sbiadisce tutti gli elementi
     * e impedisce di modificare il valore
     * Metodo sovrascritto dalle sottoclassi<br>
     *
     * @param flag true per abilitare, false per disabilitare
     */
    @Override public void regolaModificabile(boolean flag) {
        /* variabili e costanti locali di lavoro */
        Combo combo;

        try { // prova ad eseguire il codice
            combo = this.getCombo();
            if (combo != null) {
                combo.setAbilitato(flag);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Gestione evento gui modificata.
     * <p/>
     * Metodo invocato dal campo logica <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    public void guiModificata() {
        this.comboModificato();
    }


    /**
     * Metodo invocato quando si modifica il valore del combo
     * <p/>
     */
    protected void comboModificato() {
    }


    /**
     * Richiede il fuoco sul componente editabile del campo.
     * <p/>
     * Delega al Combo
     */
    protected void requestFocus() {
        /* variabili e costanti locali di lavoro */
        Combo combo;

        try {    // prova ad eseguire il codice
            combo = this.getCombo();
            combo.requestFocus();
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Restituisce l'oggetto Combo.
     * <p/>
     *
     * @return l'oggetto Combo
     */
    public Combo getCombo() {
        return this.combo;
    }


    public void setCombo(Combo combo) {
        this.combo = combo;
    }


    /**
     * Controlla se il combo usa la funzione Nuovo Record.
     * <p/>
     *
     * @return true se il combo usa la funzione Nuovo Record
     */
    public boolean isUsaNuovo() {
        return usaNuovo;
    }


    /**
     * Controlla l'uso della funzione Nuovo Record.
     * <p/>
     *
     * @param flag per usare la funzione Nuovo Record
     */
    public void setUsaNuovo(boolean flag) {
        /* variabili e costanti locali di lavoro */
        Combo combo;

        try { // prova ad eseguire il codice
            combo = this.getCombo();
            if (combo != null) {
                combo.setUsaNuovo(flag);
            }// fine del blocco if
            this.usaNuovo = flag;
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Controlla se il combo usa la funzione Modifica Record.
     * <p/>
     *
     * @return true se il combo usa la funzione Modifica Record
     */
    public boolean isUsaModifica() {
        return usaModifica;
    }


    /**
     * Controlla l'uso della funzione Modifica Record.
     * <p/>
     *
     * @param flag per usare la funzione Modifica Record
     */
    public void setUsaModifica(boolean flag) {
        /* variabili e costanti locali di lavoro */
        Combo combo;

        try { // prova ad eseguire il codice
            combo = this.getCombo();
            if (combo != null) {
                combo.setUsaModifica(flag);
            }// fine del blocco if
            this.usaModifica = flag;
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Controlla l'uso della voce Non Specificato.
     * <p/>
     *
     * @return true se usa la voce Non Specificato
     */
    public boolean isUsaNonSpecificato() {
        return usaNonSpecificato;
    }


    /**
     * Controlla l'uso della voce Non Specificato.
     * <p/>
     *
     * @param flag per usare la voce Non Specificato
     */
    public void setUsaNonSpecificato(boolean flag) {
//        combo = this.getCombo();
//        if (combo!=null) {
//            combo.setUsa..(flag);
//        }// fine del blocco if
        this.usaNonSpecificato = flag;
    }


    /**
     * Restituisce l'oggetto GUI.
     */
    public JComboBox getComboBox() {
        /* variabili e costanti locali di lavoro */
        JComboBox cb = null;
        Combo combo;
        ComboLista cl;

        try { // prova ad eseguire il codice
            combo = this.getCombo();
            if (combo != null) {
                cl = combo.getComboLista();
                if (cl != null) {
                    cb = cl.getComboBox();
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return cb;
    }




}// fine della classe
