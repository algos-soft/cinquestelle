/**
 * Title:     RicercaBase
 * Copyright: Copyright (c) 2005
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      16-ago-2005
 */
package it.algos.base.ricerca;

import it.algos.base.albero.Albero;
import it.algos.base.albero.AlberoCampi;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.componente.bottone.BottoneDialogo;
import it.algos.base.dialogo.DialogoAnnullaConferma;
import it.algos.base.errore.Errore;
import it.algos.base.evento.albero.AlberoSelModAz;
import it.algos.base.evento.albero.AlberoSelModEve;
import it.algos.base.layout.Layout;
import it.algos.base.layout.LayoutFlusso;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.modello.Modello;
import it.algos.base.modulo.Modulo;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFlusso;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.wrapper.TestoAlgos;

import javax.swing.JCheckBox;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Gestione di un dialogo di ricerca.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 16-ago-2005 ore 14.36.34
 */
public class RicercaBase extends DialogoAnnullaConferma implements Ricerca {

    /* mappa degli oggetti CampoRicerca attivi */
    private LinkedHashMap<String, CampoRicerca> campiRicerca = null;

    /* mappa degli oggetti CampoRicerca di backup */
    private LinkedHashMap<String, CampoRicerca> campiRicercaBackup = null;

    /* campo opzioni di ricerca */
    private Campo campoOpzioni;

    /* bottone di reset del dialogo */
    private BottoneDialogo bottoneReset;

    /* check box "cerca in tutti i campi"*/
    private JCheckBox chkTuttiCampi;

    /* campo testo per la stringa da cercare in tutti i campi */
    private Campo campoCercaTutti;


    /**
     * Costruttore completo con parametri.
     *
     * @param modulo di riferimento
     */
    public RicercaBase(Modulo modulo) {
        /* rimanda al costruttore della superclasse */
        super(modulo);

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
        String titolo;
        String messaggio;
        Campo campoOpzioni;
        BottoneDialogo bottone;

        try { // prova ad eseguire il codice

            /* costanti letterali */
            titolo = "Ricerca " + this.getModulo().getTitoloFinestra();
            messaggio = "Inserisci le condizioni di ricerca";

            this.setTitolo(titolo);
            this.setMessaggio(messaggio);
            this.setGapMinimo(0);
            this.setGapPreferito(10);
            this.setUsaScorrevole(true);

            /* crea l'elenco vuoto degli oggetti CampoRicerca */
            this.setCampiRicerca(new LinkedHashMap<String, CampoRicerca>());

            /* crea l'elenco vuoto degli oggetti CampoRicerca di backup*/
            this.setCampiRicercaBackup(new LinkedHashMap<String, CampoRicerca>());

            /* crea e registra il campo opzioni di ricerca */
            this.creaCampoOpzioni();

            /* crea e registra il campo modo di ricerca */
            this.creaChkCercaTuttiCampi();

            /* crea e registra il campo per la stringa da cercare in tutti i campi */
            this.creaCampoCercaTutti();

            /**
             * aggiunge il campo opzioni all'area comandi
             * e alla collezione del dialogo
             */
            campoOpzioni = this.getCampoOpzioni();
            this.getPannelloComandi().add(campoOpzioni);
            this.addCampoCollezione(campoOpzioni);

            /* aggiunge il bottone di reset */
            bottone = this.addBottone("Reset", false, false);
            bottone.setIcon(Lib.Risorse.getIconaBase("Refresh24"));
            bottone.setFocusable(false);
            this.setBottoneReset(bottone);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


    }


    /**
     * Regolazioni dinamiche dell'oggetto.
     * <p/>
     * Regola le caratteristiche dinamiche in base alla impostazione
     * corrente delle caratteristiche statiche <br>
     * Metodo invocato dalla classe che crea questo oggetto <br>
     * Viene eseguito una sola volta <br>
     * <p/>
     * Sovrascritto nelle sottoclassi <br>
     */
    public void inizializza() {
        /* variabili e costanti locali di lavoro */
        Collection<CampoRicerca> campiRicerca;

        try { // prova ad eseguire il codice

            /* aggiunge il campo per la stringa da cercare in tutti i campi */
            this.addCampo(this.getCampoCercaTutti());

            /* recupera gli oggetti CampoRicerca */
            campiRicerca = this.getCampiRicerca().values();

            /* inizializza tutti i campi ricerca e
             * aggiunge gli oggetti al dialogo */
            for (CampoRicerca cr : campiRicerca) {

                /* inizializza il CampoRicerca
                 * (crea i campi interni) */
                cr.inizializza();

                /* aggiunge il pannello al dialogo
                 * aggiunge i campi alla collezione */
                this.addCampiInterni(cr);
                

            }

            /* aggiunge graficamente gli oggetti al dialogo */
            this.creaPagina();

            /* registra il backup dei campiRicerca iniziali per eventuale reset */
            this.backupCampiRicerca();

            /* rimanda al metodo sovrascritto */
            super.inizializza();

            /* modifica il pannello messaggio (aggiunge il check "cerca in tutti i campi")*/
            this.regolaPanMessaggio();

            /* sincronizzazione in base al check */
            this.chkTuttiCampiModificato();


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Compone la pagina del dialogo.
     * <p/>
     * Aggiunge sequenzialmente gli oggetti CampoRicerca
     * Sovrascritto dalle sottoclassi che non vogliono usare
     * la disposizione automatica dei campi uno sotto l'altro.
     */
    protected void creaPagina() {
        try {    // prova ad eseguire il codice
            Collection<CampoRicerca> campiRicerca = this.getCampiRicerca().values();
            for (CampoRicerca cr : campiRicerca) {
                this.addComponente(cr);
            }
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }





    /**
     * Avvia concretamente il dialogo.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     */
    public void avvia() {
        /* variabili e costanti locali di lavoro */

        try { // prova ad eseguire il codice

//            /* resetta i campi */
//            this.resetCampi();


            /* visualizza il dialogo */
            super.avvia();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Modifica il pannello superiore (messaggio).
     * <p/>
     * Aggiunge il campo modo di ricerca
     */
    private void regolaPanMessaggio() {
        /* variabili e costanti locali di lavoro */
        Pannello pan = null;
        Layout layout;

        try {    // prova ad eseguire il codice
            pan = this.getPannelloMessaggio();
            layout = new LayoutFlusso(pan, Layout.ORIENTAMENTO_ORIZZONTALE);
            layout.setAllineamento(Layout.ALLINEA_CENTRO);
            pan.getPanFisso().setLayout(layout);
            pan.add(this.getChkTuttiCampi());
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    public void rendiVisibile() {

        /* resetta i campi */
        this.resetCampi();

        /* regola la visibilità dei campi in  base al check */
        this.chkTuttiCampiModificato();

        /* rimanda alla superclasse */
        super.rendiVisibile();

    }


    public void sincronizza() {
        /* variabili e costanti locali di lavoro */
        Collection<CampoRicerca> campiRicerca;
        boolean abilitaUnione = false;

        super.sincronizza();

        try { // prova ad eseguire il codice

            /* disabilita tutte le unioni fino al
             * primo campo valorizzato compreso */
            campiRicerca = this.getCampiRicerca().values();
            for (CampoRicerca cr : campiRicerca) {

                cr.setUnioneAbilitata(abilitaUnione);

                /* se valorizzato, da qui in poi tutte le unioni sono abilitate */
                if (cr.isValorizzato()) {
                    abilitaUnione = true;
                }// fine del blocco if
            }
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Aggiunge un campo ricerca.
     * <p/>
     * Aggunge i campi interni contenuti nel campo ricerca
     * alla collezione di campi del form usando una chiave
     * appositamente costruita
     *
     * @param cr campo ricerca da aggiungere
     */
    private void addCampiInterni(CampoRicerca cr) {
        /* variabili e costanti locali di lavoro */
        ArrayList<Campo> campi = null;
        String chiaveBase = null;
        String chiave;
        boolean continua = true;

        try { // prova ad eseguire il codice

            /* recupera la chiave base dalla collezione */
            if (continua) {
                chiaveBase = this.getChiaveCollezione(cr);
                continua = Lib.Testo.isValida(chiaveBase);
            }// fine del blocco if

            /* recupera i campi dal CampoRicerca */
            if (continua) {
                campi = cr.getCampi();
                continua = campi.size() > 0;
            }// fine del blocco if

            /**
             * aggiunge i campi alla collezione del form
             */
            if (continua) {

                for (Campo campo : campi) {
                    chiave = this.getChiaveComposta(chiaveBase, campo);
                    this.addCampoCollezione(campo, chiave);
                } // fine del ciclo for-each

//                this.addComponente(cr);
//                cr.avvia();
//                cr.repaint();
//                this.sincronizza();

            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Crea e registra il campo opzioni di ricerca.
     * <p/>
     *
     * @return il campo creato
     */
    private Campo creaCampoOpzioni() {
        /* variabili e costanti locali di lavoro */
        Campo campo = null;

        try {    // prova ad eseguire il codice
            campo = CampoFactory.radioInterno("opzioniRicerca");
            this.setCampoOpzioni(campo);
            campo.setValoriInterni(Ricerca.Opzioni.getLista());
            campo.setUsaNonSpecificato(false);
            campo.decora().eliminaEtichetta();
            campo.setLarScheda(160);
            campo.inizializza();
            campo.avvia();
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return campo;
    }


    /**
     * Crea e registra il campo per la stringa da cercare dappertutto.
     * <p/>
     *
     * @return il campo creato
     */
    private Campo creaCampoCercaTutti() {
        /* variabili e costanti locali di lavoro */
        Campo campo = null;

        try {    // prova ad eseguire il codice
            campo = CampoFactory.testo("testo da cercare");
            campo.setLarghezza(200);
            this.setCampoCercaTutti(campo);
            campo.inizializza();
            campo.avvia();
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return campo;
    }


    /**
     * Crea e registra il checkBox "cerca in tutti i campi".
     * <p/>
     *
     * @return il checkBox
     */
    private JCheckBox creaChkCercaTuttiCampi() {
        /* variabili e costanti locali di lavoro */
        JCheckBox box = null;

        try {    // prova ad eseguire il codice
            box = new JCheckBox("cerca in tutti i campi");
            TestoAlgos.setCheckBox(box);
            box.addItemListener(new ChkTuttiListener());
            this.setChkTuttiCampi(box);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return box;
    }


    /**
     * Listener del check box "cerca in tutti i campi".
     * <p/>
     */
    private final class ChkTuttiListener implements ItemListener {

        public void itemStateChanged(ItemEvent event) {
            chkTuttiCampiModificato();
        }
    } // fine della classe 'interna'


    /**
     * Invocato quando si modifica l'opzione "cerca in tutti i campi".
     * <p/>
     */
    private void chkTuttiCampiModificato() {
        /* variabili e costanti locali di lavoro */
        boolean stato;

        try {    // prova ad eseguire il codice

            /* recupera lo stato del check box */
            stato = this.isCercaTuttiCampi();

            /* regola la visibilità degli oggetti CampoRicerca */
            LinkedHashMap<String, CampoRicerca> listaCR = this.getCampiRicerca();
            for (CampoRicerca cr : listaCR.values()) {
                cr.setVisible(!stato);
            }

            /* regola la visibilità del campo "testo da cercare" */
            this.getCampoCercaTutti().setVisibile(stato);

            /* pack del dialogo */
            this.getDialogo().pack();

            /* fuoco al primo campo*/
            this.vaiCampoPrimo();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Ritorna lo stato dell'opzione "cerca in tutti i campi".
     * <p/>
     *
     * @return lo stato della opzione
     */
    private boolean isCercaTuttiCampi() {
        /* variabili e costanti locali di lavoro */
        boolean attivo = false;
        JCheckBox box;

        try {    // prova ad eseguire il codice
            box = this.getChkTuttiCampi();
            if (box != null) {
                attivo = box.isSelected();
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return attivo;
    }


    /**
     * Ritorna l'opzione di ricerca correntemente selezionata.
     * <p/>
     *
     * @return l'opzione selezionata
     *
     * @see it.algos.base.ricerca.Ricerca.Opzioni
     */
    public Ricerca.Opzioni getOpzioneRicerca() {
        /* variabili e costanti locali di lavoro */
        Ricerca.Opzioni opzione = null;
        Campo campo;
        Object oggetto;

        try {    // prova ad eseguire il codice
            campo = this.getCampoOpzioni();
            oggetto = campo.getValoreElenco();
            if (oggetto instanceof Ricerca.Opzioni) {
                opzione = (Ricerca.Opzioni)oggetto;
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return opzione;
    }


    /**
     * Rimuove un CampoRicerca.
     * <p/>
     * Il campo viene eliminato solo se non e' l'ultimo
     * Esegue il pack del dialogo
     *
     * @param cr il CampoRicerca da eliminare
     */
    public void removeCampoRicerca(CampoRicerca cr) {
        try {    // prova ad eseguire il codice

            /* non rimuove l'ultimo! */
            if (this.getCampiRicerca().size() > 1) {
                this.deleteCampoRicerca(cr);
                this.getDialogo().pack();
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Rimuove un CampoRicerca.
     * <p/>
     * Non esegue il pack del dialogo
     *
     * @param cr il CampoRicerca da eliminare
     */
    private void deleteCampoRicerca(CampoRicerca cr) {
        /* variabili e costanti locali di lavoro */
        boolean continua = true;
        ArrayList<Campo> campi;
        String chiaveBase = null;
        String chiave;

        try {    // prova ad eseguire il codice

            /* recupera la chiave del CampoRicerca nella collezione */
            if (continua) {
                chiaveBase = this.getChiaveCollezione(cr);
                continua = Lib.Testo.isValida(chiaveBase);
            }// fine del blocco if

            /* rimuove i campi dalla collezione del form */
            if (continua) {
                campi = cr.getCampi();
                for (Campo campo : campi) {
                    chiave = this.getChiaveComposta(chiaveBase, campo);
                    this.removeCampoCollezione(chiave);
                } // fine del ciclo for-each
            }// fine del blocco if

            /* rimuove il componente grafico dalla pagina del form */
            if (continua) {
                this.removeComponente(cr);
            }// fine del blocco if

            /* rimuove il CampoRicerca dalla collezione */
            if (continua) {
                this.getCampiRicerca().remove(chiaveBase);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Ritorna la chiave con la quale un campo di un CampoRicerca
     * va aggiunto alla collezione del form.
     * <p/>
     *
     * @param chiaveBase chiave del campoRicerca nella collezione
     * @param campo interno del campoRicerca aggiunto al form
     *
     * @return la chiave composta
     */
    private String getChiaveComposta(String chiaveBase, Campo campo) {
        /* variabili e costanti locali di lavoro */
        String chiave = null;

        try {    // prova ad eseguire il codice
            chiave = chiaveBase + "_" + campo.getNomeInterno();
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return chiave;
    }


    /**
     * Resetta i valori di tutti i campi.
     * <p/>
     * Resetta i valori memoria e backup di ogni campo <br>
     */
    public void resetCampi() {

        try { // prova ad eseguire il codice

            /* resetta tutti i campi ricerca */
            for (CampoRicerca cr : this.getCampiRicerca().values()) {
                cr.reset();
            }

            /* resetta il campo "testo da cercare dappertutto" */
            this.getCampoCercaTutti().setValore("");

            /* resetta le opzioni di ricerca */
            this.getCampoOpzioni().setValore(1);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } /* fine del metodo */


    /**
     * Aggiunge un campo singolo di questo modulo alla ricerca.
     * <p/>
     * Usa l'operatore di ricerca di default del campo
     *
     * @param nome del campo di questo modulo
     *
     * @return il campo di ricerca aggiunto
     */
    public CampoRicerca addCampoRicerca(String nome) {
        /* variabili e costanti locali di lavoro */
        CampoRicerca campoRicerca = null;

        try {    // prova ad eseguire il codice

            /* rimanda al metodo delegato */
            campoRicerca = this.addCampoRicerca(nome, false);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return campoRicerca;
    }


    /**
     * Aggiunge un campo singolo di questo modulo alla ricerca.
     * <p/>
     *
     * @param nome del campo di questo modulo
     * @param range true se si vuole cercare su range
     *
     * @return il campo di ricerca aggiunto
     */
    public CampoRicerca addCampoRicerca(String nome, boolean range) {
        /* variabili e costanti locali di lavoro */
        CampoRicerca campoRicerca = null;

        try {    // prova ad eseguire il codice

            /* rimanda al metodo delegato */
            campoRicerca = this.addCampoRicerca(nome, null, range);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return campoRicerca;
    }


    /**
     * Aggiunge un campo singolo di questo modulo alla ricerca.
     * <p/>
     *
     * @param nome del campo di questo modulo
     * @param operatore operatore di ricerca
     *
     * @return il campo di ricerca aggiunto
     */
    public CampoRicerca addCampoRicerca(String nome, String operatore) {
        /* variabili e costanti locali di lavoro */
        CampoRicerca campoRicerca = null;

        try {    // prova ad eseguire il codice

            /* rimanda al metodo delegato */
            campoRicerca = this.addCampoRicerca(nome, operatore, false);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return campoRicerca;
    }


    /**
     * Aggiunge un campo di questo modulo alla ricerca.
     * <p/>
     *
     * @param nome del campo di questo modulo
     * @param operatore operatore di ricerca
     * @param range true se si vuole cercare su range
     *
     * @return il campo di ricerca aggiunto
     *
     * @see it.algos.base.database.util.Operatore
     */
    public CampoRicerca addCampoRicerca(String nome, String operatore, boolean range) {
        /* variabili e costanti locali di lavoro */
        CampoRicerca campoRicerca = null;
        Campo campo;

        try {    // prova ad eseguire il codice

            campo = this.getModulo().getCampo(nome);
            if (campo != null) {
                campoRicerca = this.addCampoRicerca(campo, operatore, range);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return campoRicerca;
    }


    /**
     * Aggiunge un campo singolo alla ricerca.
     * <p/>
     * Usa l'operatore di ricerca di default del campo
     *
     * @param campo oggetto campo di questo od altro modulo
     *
     * @return il campo di ricerca aggiunto
     */
    public CampoRicerca addCampoRicerca(Campo campo) {
        /* variabili e costanti locali di lavoro */
        CampoRicerca campoRicerca = null;

        try {    // prova ad eseguire il codice

            /* rimanda al metodo delegato */
            campoRicerca = this.addCampoRicerca(campo, false);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return campoRicerca;
    }


    /**
     * Aggiunge un campo singolo alla ricerca.
     * <p/>
     *
     * @param campo oggetto campo di questo od altro modulo
     * @param range true se si vuole cercare su range
     *
     * @return il campo di ricerca aggiunto
     */
    public CampoRicerca addCampoRicerca(Campo campo, boolean range) {
        /* variabili e costanti locali di lavoro */
        CampoRicerca campoRicerca = null;

        try {    // prova ad eseguire il codice

            /* rimanda al metodo delegato */
            campoRicerca = this.addCampoRicerca(campo, null, range);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return campoRicerca;
    }


    /**
     * Aggiunge un campo singolo con operatore alla ricerca.
     * <p/>
     *
     * @param campo oggetto campo di questo od altro modulo
     * @param operatore operatore di ricerca
     *
     * @return il campo di ricerca aggiunto
     */
    public CampoRicerca addCampoRicerca(Campo campo, String operatore) {
        /* variabili e costanti locali di lavoro */
        CampoRicerca campoRicerca = null;

        try {    // prova ad eseguire il codice

            /* rimanda al metodo delegato */
            campoRicerca = this.addCampoRicerca(campo, operatore, false);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return campoRicerca;
    }


    /**
     * Aggiunge un campo singolo o un range di campi alla ricerca.
     * <p/>
     *
     * @param campo oggetto campo di questo od altro modulo
     * @param operatore operatore di ricerca
     * @param range true se si vuole cercare su range
     *
     * @return il campo di ricerca aggiunto
     *
     * @see it.algos.base.database.util.Operatore
     */
    public CampoRicerca addCampoRicerca(Campo campo, String operatore, boolean range) {
        /* variabili e costanti locali di lavoro */
        CampoRicerca campoRicerca = null;
        String chiave;

        try {    // prova ad eseguire il codice

            /* crea e regola un oggetto CampoRicerca */
            campoRicerca = new CampoRicerca(this, campo, operatore, range);

            /* Aggiunge il CampoRicerca alla collezione interna. */
            this.addCampoRicerca(campoRicerca);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return campoRicerca;
    }


    /**
     * Aggiunge un oggetto CampoRicerca alla collezione interna.
     * <p/>
     *
     * @param cr oggetto CampoRicerca da aggiungere
     */
    public void addCampoRicerca(CampoRicerca cr) {
        String chiave = this.creaChiaveCollezione(cr);
        this.getCampiRicerca().put(chiave, cr);
    }






    /**
     * Aggiunge un campo di ricerca.
     * <p/>
     * Presenta un dialogo all'utente per scegliere il campo
     * da aggiungere e le opzioni di aggiunta.
     */
    public void addCampoRicerca() {
        /* variabili e costanti locali di lavoro */
        DialogoSceltaCampo dialogo;
        CampoRicerca cr;
        ArrayList<Campo> campi;
        boolean range;

        try {    // prova ad eseguire il codice

            /* mostra il dialogo di selezione campi */
            dialogo = new DialogoSceltaCampo(this.getModulo());
            dialogo.avvia();

            if (dialogo.isConfermato()) {
                campi = dialogo.getCampiSelezionati();
                range = dialogo.isUsaRange();
                for (Campo campo : campi) {

                    /* crea il CampoRicerca e lo aggiunge alla collezione */
                    cr = this.addCampoRicerca(campo, range);

                    /* inizializza il CampoRicerca
                     * i campi interni vengono creati e inizializzati */
                    cr.inizializza();


                    /* aggiunge il pannello al dialogo
                     * aggiunge i campi alla collezione
                     * sincronizza il dialogo */
                    this.addCampiInterni(cr);

                    /* aggiunge il pannello al dialogo */
                    this.addComponente(cr);

                }

                this.getDialogo().pack();

            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Determina la chiave da utilizzare per aggiungere
     * un CampoRicerca alla collezione.
     * <p/>
     * Usa la chiave del campo principale del CampoRicerca<br>
     * Se esiste gia' nella collezione la incrementa con un numero
     * progressivo fino a quando la chiave non esiste piu'<br>
     *
     * @param cr il CampoRicerca da aggiungere
     *
     * @return la chiave per aggiungere il CampoRicerca alla collezione
     */
    private String creaChiaveCollezione(CampoRicerca cr) {
        /* variabili e costanti locali di lavoro */
        String chiave = null;
        String chiaveBase;
        HashMap<String, CampoRicerca> collezioneCampi;
        int i = 0;

        try {    // prova ad eseguire il codice

            /* recupera la collezione */
            collezioneCampi = this.getCampiRicerca();

            /** controlla se la chiave base esiste gia' nella
             * collezione dei campi ricerca - se esiste la
             * modifica aggiungendo un numero progressivo
             * fino a quando e' diversa da tutte le altre */
            chiaveBase = cr.getChiaveBase();
            chiave = chiaveBase;
            while (collezioneCampi.containsKey(chiave)) {
                i++;
                chiave = chiaveBase + i;
            }// fine del blocco while
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return chiave;
    }


    /**
     * Restituisce la chiave con la quale un CampoRicerca
     * e' stato aggiunto alla collezione.
     * <p/>
     *
     * @param cr il CampoRicerca da controllare
     *
     * @return la chiave con la quale il CampoRicerca
     *         e' presente nella collezione - null se non presente
     */
    private String getChiaveCollezione(CampoRicerca cr) {
        /* variabili e costanti locali di lavoro */
        String unaChiave = null;
        String chiave = null;
        HashMap<String, CampoRicerca> campiRicerca;
        Set<Map.Entry<String, CampoRicerca>> setCR;
        CampoRicerca campoRicerca;

        try {    // prova ad eseguire il codice

            /* recupera la chiave base dalla collezione */
            campiRicerca = this.getCampiRicerca();
            setCR = campiRicerca.entrySet();
            for (Map.Entry<String, CampoRicerca> entry : setCR) {
                unaChiave = entry.getKey();
                campoRicerca = entry.getValue();
                if (campoRicerca.equals(cr)) {
                    chiave = unaChiave;
                    break;
                }// fine del blocco if
            }

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return chiave;
    }


    /**
     * Recupera il filtro corrispondente a questa Ricerca.
     * <p/>
     *
     * @return il filtro corrispondente alla ricerca
     *         (null se non sono state impostate condizioni di ricerca)
     */
    public Filtro getFiltro() {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = null;

        try {    // prova ad eseguire il codice

            /* allinea le variabili da GUI a memoria */
            this.guiMemoria();

            if (this.isCercaTuttiCampi()) {
                filtro = this.getFiltroTutti();
            } else {
                filtro = this.getFiltroCampi();
            }// fine del blocco if-else

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return filtro;
    }


    /**
     * Recupera il filtro corrispondente a questa Ricerca.
     * <p/>
     * (solo per ricerca nei campi spefifici)
     *
     * @return il filtro corrispondente alla ricerca
     *         (null se non sono state impostate condizioni di ricerca)
     */
    private Filtro getFiltroCampi() {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = null;
        Filtro filtroCampo = null;
        Collection<CampoRicerca> lista = null;
        String unione;
        int i = 0;

        try {    // prova ad eseguire il codice

            filtro = new Filtro();

            lista = this.getCampiRicerca().values();
            for (CampoRicerca cr : lista) {
                filtroCampo = cr.getFiltro();

                if (filtroCampo != null) {

//                    filtroCampo.setCaseSensitive(true);

                    if (i == 0) {
                        filtro.add(filtroCampo);
                    } else {
                        unione = cr.getUnione();
                        filtro.add(unione, filtroCampo);
                    }// fine del blocco if-else
                }// fine del blocco if
                i++;
            }

            /* se non ha aggiunto nulla, annulla il filtro */
            if (filtro.getSize() == 0) {
                filtro = null;
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return filtro;
    }


    /**
     * Recupera il filtro corrispondente a questa Ricerca.
     * <p/>
     * (solo per ricerca in tutti i campi)
     *
     * @return il filtro per cercare il testo specificato in tutti i campi testo del Modello
     */
    private Filtro getFiltroTutti() {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = null;
        boolean continua = true;
        Object valore;
        String stringa;
        Modello modello;
        LinkedHashMap<String, Campo> campiFisici;
        ArrayList<Campo> campiTesto = new ArrayList<Campo>();
        Filtro filtroCampo = null;

        try {    // prova ad eseguire il codice

            /* recupera la stringa da cercare */
            valore = this.getCampoCercaTutti().getValore();
            stringa = Lib.Testo.getStringa(valore);
            continua = Lib.Testo.isValida(stringa);

            /* recupera tutti i campi testo dal modello */
            if (continua) {
                modello = this.getModulo().getModello();
                campiFisici = modello.getCampiFisici();
                for (Campo campo : campiFisici.values()) {
                    if (campo.isTesto()) {
                        campiTesto.add(campo);
                    }// fine del blocco if
                }
                continua = campiTesto.size() > 0;
            }// fine del blocco if

            /* crea un filtro per ogni campo con clausola OR */
            if (continua) {
                filtro = new Filtro();
                for (Campo campo : campiTesto) {
                    filtroCampo = FiltroFactory.crea(campo, Filtro.Op.CONTIENE, stringa);
                    filtro.add(Filtro.Op.OR, filtroCampo);
                }
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return filtro;
    }


    /**
     * Copia i campi di ricerca attivi nella collezione di backup.
     * <p/>
     */
    public void backupCampiRicerca() {
        /* variabili e costanti locali di lavoro */
        LinkedHashMap<String, CampoRicerca> campiBackup = null;
        LinkedHashMap<String, CampoRicerca> campiAttivi;

        try {    // prova ad eseguire il codice
            campiAttivi = this.getCampiRicerca();
            campiBackup = (LinkedHashMap<String, CampoRicerca>)campiAttivi.clone();
            this.setCampiRicercaBackup(campiBackup);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Ripristina i campi di ricerca attivi dalla collezione di backup.
     * <p/>
     */
    private void restoreCampiRicerca() {
        /* variabili e costanti locali di lavoro */
        LinkedHashMap<String, CampoRicerca> campiAttivi;
        LinkedHashMap<String, CampoRicerca> campiBackup;
        ArrayList<CampoRicerca> listaCR;

        try {    // prova ad eseguire il codice

            /* crea una nuova lista dei campi ricerca attivi da eliminare */
            listaCR = new ArrayList<CampoRicerca>();
            campiAttivi = this.getCampiRicerca();
            for (CampoRicerca cr : campiAttivi.values()) {
                listaCR.add(cr);
            }

            /* elimina i campi ricerca attivi */
            for (CampoRicerca cr : listaCR) {
                this.deleteCampoRicerca(cr);
            }

            /* aggiunge i campi ricerca dal backup */
            campiBackup = this.getCampiRicercaBackup();
            campiAttivi = ((LinkedHashMap<String, CampoRicerca>)campiBackup.clone());
            this.setCampiRicerca(campiAttivi);
            for (CampoRicerca cr : this.getCampiRicerca().values()) {
                this.addCampiInterni(cr);
            }

            /* resetta i campi e ridisegna il dialogo */
            this.resetCampi();
            this.getDialogo().pack();
            this.vaiCampoPrimo();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * posiziona il fuoco al primo campo del form.
     * <p/>
     * Sovrascrive il metodo della superclasse
     * per andare al primo campo utile (non i popup)
     */
    public void vaiCampoPrimo() {
        /* variabili e costanti locali di lavoro */
        Campo campo;
        CampoRicerca cr;
        ArrayList<CampoRicerca> listaCR;

        try {    // prova ad eseguire il codice

            if (this.isCercaTuttiCampi()) {
                this.vaiCampo(this.getCampoCercaTutti());
            } else {
                listaCR = Libreria.hashMapToArrayList(this.getCampiRicerca());
                if (listaCR.size() > 0) {
                    cr = listaCR.get(0);
                    campo = cr.getCampo1();
                    this.vaiCampo(campo);
                }// fine del blocco if
            }// fine del blocco if-else

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Ritorna l'istanza della classe base.
     * <p/>
     *
     * @return l'istanza della classe base
     */
    public RicercaBase getRicercaBase() {
        return this;
    }


    protected void bottonePremuto(BottoneDialogo bottone) {
        if (bottone.equals(this.getBottoneReset())) {
            this.restoreCampiRicerca();
        }// fine del blocco if
    }


    /**
     * Ritorna un testo esplicativo delle condizioni di ricerca impostate.
     * <p/>
     *
     * @return il testo esplicativo
     */
    public String getTestoRicerca() {
        /* variabili e costanti locali di lavoro */
        String testo = "";

        try {    // prova ad eseguire il codice

            if (this.isCercaTuttiCampi()) {
                testo = "In tutti i campi: "+this.getCampoCercaTutti().getValore();
            } else {
                LinkedHashMap<String, CampoRicerca> mappa = this.getCampiRicerca();
                for (CampoRicerca cr : mappa.values()) {
                    if (cr.isValorizzato()) {
                        if (Lib.Testo.isValida(testo)) {
                            testo += "\n";
                        }// fine del blocco if
                        testo += "- "+cr.getTestoRicerca();
                    }// fine del blocco if
                }
            }// fine del blocco if-else


        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return testo;

    }


    private LinkedHashMap<String, CampoRicerca> getCampiRicerca() {
        return campiRicerca;
    }


    private void setCampiRicerca(LinkedHashMap<String, CampoRicerca> campiRicerca) {
        this.campiRicerca = campiRicerca;
    }


    private LinkedHashMap<String, CampoRicerca> getCampiRicercaBackup() {
        return campiRicercaBackup;
    }


    private void setCampiRicercaBackup(LinkedHashMap<String, CampoRicerca> campiRicercaBackup) {
        this.campiRicercaBackup = campiRicercaBackup;
    }


    private Campo getCampoOpzioni() {
        return campoOpzioni;
    }


    private void setCampoOpzioni(Campo campoOpzioni) {
        this.campoOpzioni = campoOpzioni;
    }


    private BottoneDialogo getBottoneReset() {
        return bottoneReset;
    }


    private void setBottoneReset(BottoneDialogo bottoneReset) {
        this.bottoneReset = bottoneReset;
    }


    private JCheckBox getChkTuttiCampi() {
        return chkTuttiCampi;
    }


    private void setChkTuttiCampi(JCheckBox box) {
        this.chkTuttiCampi = box;
    }


    private Campo getCampoCercaTutti() {
        return campoCercaTutti;
    }


    private void setCampoCercaTutti(Campo campo) {
        this.campoCercaTutti = campo;
    }


    /**
     * Dialogo di selezione del campo da aggiungere
     * </p>
     * Classe interna
     */
    private final class DialogoSceltaCampo extends DialogoAnnullaConferma {

        /**
         * Albero dei campi disponibili
         */
        private AlberoCampi alberoCampi;

        /**
         * Campo check per attivazione range
         */
        private Campo campoChkRange;

        /**
         * Campo check per nascondere i campi fissi
         */
        private Campo campoChkFissi;


        /**
         * Costruttore completo con parametri. <br>
         */
        public DialogoSceltaCampo(Modulo modulo) {
            /* rimanda al costruttore della superclasse */
            super(modulo);

//            /* regola le variabili di istanza coi parametri */
//            this.set(un);

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
            PannelloFlusso panAlbero;

            try { // prova ad eseguire il codice

                this.setTitolo("Nuova condizione");
                this.setMessaggio("Seleziona i campi da aggiungere alla ricerca");
                this.creaAlberoCampi();
                this.creaCampoCheckRange();
                this.creaCampoCheckFissi();

                panAlbero = new PannelloFlusso(Layout.ORIENTAMENTO_VERTICALE);
                panAlbero.setUsaGapFisso(true);
                panAlbero.setGapPreferito(0);
                panAlbero.add(this.getAlberoCampi());
                panAlbero.add(this.getCampoChkFissi());

                this.addPannello(panAlbero);
                this.addCampo(this.getCampoChkRange());

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

        }


        public void inizializza() {

            super.inizializza();

            /* di default i campi fissi sono nascosti */
            this.getCampoChkFissi().setValore(true);

        } /* fine del metodo */


        /**
         * Crea e registra l'albero dei campi disponibili.
         * <p/>
         */
        private void creaAlberoCampi() {
            /* variabili e costanti locali di lavoro */
            AlberoCampi alberoCampi;

            try {    // prova ad eseguire il codice
                /* crea e registra l'albero */
                alberoCampi = new AlberoCampi();
                this.setAlberoCampi(alberoCampi);

                /* carica i campi del modulo */
                alberoCampi.caricaCampi(this.getModulo());

                /* aggiunge il listener per la selezione */
                alberoCampi.addListener(new AzioneSelezioneAlberoModificata());

            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch
        }


        /**
         * Crea e registra il campo check opzione range.
         * <p/>
         */
        private void creaCampoCheckRange() {
            /* variabili e costanti locali di lavoro */
            Campo campo;

            try {    // prova ad eseguire il codice
                campo = CampoFactory.checkBox("Ricerca su range");
                campo.setLarScheda(200);
                this.setCampoChkRange(campo);
            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch
        }


        /**
         * Crea e registra il campo check opzione nascondi campi fissi.
         * <p/>
         */
        private void creaCampoCheckFissi() {
            /* variabili e costanti locali di lavoro */
            Campo campo;

            try {    // prova ad eseguire il codice
                campo = CampoFactory.checkBox("Nascondi campi fissi");
                campo.setLarScheda(200);
                this.setCampoChkFissi(campo);
            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch
        }


        protected void eventoMemoriaModificata(Campo campo) {
            /* variabili e costanti locali di lavoro */
            Object valore;

            /* campo check nascondi campi fissi */
            if (campo == this.getCampoChkFissi()) {
                valore = campo.getValore();
                boolean selezionato = (Boolean)valore;
                if (selezionato) {
                    getAlberoCampi().rimuoviCampiFissi();
                } else {
                    getAlberoCampi().aggiungiCampiFissi();
                }// fine del blocco if-else
            }// fine del blocco if
        }


        /**
         * Per essere confermabile bisogna aver selezionato almeno un campo
         */
        public boolean isConfermabile() {
            /* variabili e costanti locali di lavoro */
            boolean confermabile = false;
            Albero albero;
            int quantiSelezionati;

            try { // prova ad eseguire il codice
                confermabile = super.isConfermabile();
                if (confermabile) {
                    albero = this.getAlberoCampi();
                    quantiSelezionati = albero.getNodiSelezionati().size();
                    confermabile = quantiSelezionati > 0;
                }// fine del blocco if

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return confermabile;
        }


        /**
         * Ritorna l'elenco dei campi selezionati nell'albero.
         * <p/>
         */
        public ArrayList<Campo> getCampiSelezionati() {
            return this.getAlberoCampi().getCampiSelezionati();
        }


        /**
         * Ritorna lo stato del flag Usa Range.
         * <p/>
         */
        public boolean isUsaRange() {
            /* variabili e costanti locali di lavoro */
            boolean usaRange = false;

            try {    // prova ad eseguire il codice
                usaRange = (Boolean)this.getCampoChkRange().getValore();
            } catch (Exception unErrore) {    // intercetta l'errore
                Errore.crea(unErrore);
            } // fine del blocco try-catch

            /* valore di ritorno */
            return usaRange;
        }


        private AlberoCampi getAlberoCampi() {
            return alberoCampi;
        }


        private void setAlberoCampi(AlberoCampi alberoCampi) {
            this.alberoCampi = alberoCampi;
        }


        private Campo getCampoChkRange() {
            return campoChkRange;
        }


        private void setCampoChkRange(Campo campoChkRange) {
            this.campoChkRange = campoChkRange;
        }


        private Campo getCampoChkFissi() {
            return campoChkFissi;
        }


        private void setCampoChkFissi(Campo campoChkFissi) {
            this.campoChkFissi = campoChkFissi;
        }


        /**
         * Inner class per gestire l'azione.
         */
        private class AzioneSelezioneAlberoModificata extends AlberoSelModAz {

            /**
             * @param unEvento evento che causa l'azione da eseguire <br>
             */
            public void alberoSelModAz(AlberoSelModEve unEvento) {
                try { // prova ad eseguire il codice
                    sincronizza();
                } catch (Exception unErrore) { // intercetta l'errore
                    Errore.crea(unErrore);
                }// fine del blocco try-catch
            }
        } // fine della classe interna

    } // fine della classe 'interna'


}
