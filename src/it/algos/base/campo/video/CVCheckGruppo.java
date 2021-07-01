/**
 * Copyright: Copyright (c) 2005
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      23-feb-2005
 */
package it.algos.base.campo.video;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.layout.Layout;
import it.algos.base.wrapper.TestoAlgos;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Componente video di tipo gruppo di check boxes.
 * </p>
 * Questa classe concreta: <ul>
 * <li> Crea il componente GUI specifico di questa classe </li>
 * <li> Aggiunge i Listener al componente specifico </li>
 * <li> Regola i font, la dimensione e la posizione del componente specifico </li>
 * <li> Regola le dimensioni del pannelloComponenti </li>
 * <li> Implementare i metodi astratti della superclasse per regolare le
 * conversioni tra i vari tipi di dati </li>
 * <li> L'attributo <i>video</i> del <code>CampoDati</code> associato,
 * <strong>deve>/strong> essere di tipo intero </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 23-feb-2005 ore 10.21.27
 */
public final class CVCheckGruppo extends CVGruppo {


    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param unCampoParente campo 'contenitore' di questo oggetto
     */
    public CVCheckGruppo(Campo unCampoParente) {
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

        /* regola l'orientamento di default del layout del pannello componenti */
        this.setOrientamentoComponenti(Layout.ORIENTAMENTO_VERTICALE);

        /* prepara i componenti */
        this.resetComponenti();

    }// fine del metodo inizia


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
        ArrayList listaValori;
        int numBottoni;
        String stringa;
        Object oggetto;
        JCheckBox bottone;


        try {    // prova ad eseguire il codice

            /* prepara i componenti */
            this.resetComponenti();

            /* recupera la lista valori
             * (se non e' definita ne crea una di emergenza) */
            listaValori = this.getListaValori();
            if (listaValori == null) {
                listaValori = new ArrayList();
                listaValori.add("primo valore");
                listaValori.add("secondo valore");
                listaValori.add("terzo valore");
            }// fine del blocco if

            /* recupera il numero dei bottoni
             * regola il numero di righe del campo */
            numBottoni = listaValori.size();
            this.setNumeroRighe(numBottoni);

            /* crea gli oggetti GUI
            * aggiunge gli oggetti GUI al gruppo radio
            * aggiunge gli oggetti GUI al gruppo logico
            * aggiunge gli oggetti GUI al pannello componenti
             */
            for (int k = 0; k < numBottoni; k++) {

                /* recupera l'oggetto valore e determina se stringa o icona */
                oggetto = listaValori.get(k);
                stringa = (String)oggetto;

                bottone = new JCheckBox(stringa);

                /* il bottone e' trasparente */
                bottone.setContentAreaFilled(false);

                /* regola il testo */
                TestoAlgos.setRadio(bottone);

                /* il bottone non ha margini */
                bottone.setMargin(new Insets(0, 0, 0, 0));

                /* aggiunge il radiobottone alla lista interna */
                this.getComponentiGruppo().add(bottone);

//                /* aggiunge il radiobottone al gruppo logico */
//                this.getGruppoBottoni().add(bottone);

                /* aggiunge il check al pannelloComponenti */
                this.getPannelloComponenti().add(bottone);

            } // fine del ciclo for

            /* invoca il metodo sovrascritto nella superclasse */
            super.creaComponentiInterni();


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Aggiorna la GUI col valore video.
     * <p/>
     * Metodo invocato dal ciclo avvia di SchedaBase <br>
     * Regola il componente GUI del campoVideo con il valore <br>
     * (questo metodo va implementato qui) <br>
     *
     * @param valoreVideo valore video proveniente dal CampoDati
     *
     * @see it.algos.base.scheda.SchedaBase#caricaValori
     * @see it.algos.base.campo.logica.CLBase#memoriaGui()
     */
    public void aggiornaGUI(Object valoreVideo) {
        /* variabili e costanti locali di lavoro */
        ArrayList<Boolean> valori = null;
        boolean valore;

        try {    // prova ad eseguire il codice

            /* spegne tutti i toggle button */
            for (Object oggetto : this.getComponentiGruppo()) {
                ((JToggleButton)oggetto).setSelected(false);
            } // fine del ciclo for-each

            if (valoreVideo != null) {

                /* casting */
                if (valoreVideo instanceof ArrayList) {
                    valori = (ArrayList<Boolean>)valoreVideo;
                }// fine del blocco if

                /* regolazione effettiva dei componenti */
                if (valori != null) {
                    for (int k = 0; k < valori.size(); k++) {
                        valore = valori.get(k);
                        this.getComponente(k).setSelected(valore);
                    } // fine del ciclo for
                }// fine del blocco if

            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    }


    /**
     * Recupera dalla GUI il valore video.
     * <p/>
     * Metodo invocato da isModificata() e da modificaCampo() <br>
     *
     * @return valore video per il CampoDati
     *
     * @see it.algos.base.navigatore.NavigatoreBase#modificaCampo(it.algos.base.campo.base.Campo)
     * @see it.algos.base.scheda.SchedaBase#isModificata()
     * @see it.algos.base.campo.logica.CLBase#guiMemoria()
     */
    public Object recuperaGUI() {
        /* variabili e costanti locali di lavoro */
        ArrayList<Boolean> valoreVideo = null;

        try {    // prova ad eseguire il codice

            /* lista iniziale vuota */
            valoreVideo = new ArrayList<Boolean>();

            /* lista dei JToggleButton nella GUI */
            for (JToggleButton bottone : this.getComponentiGruppo()) {
                valoreVideo.add(bottone.isSelected());
            } // fine del ciclo for-each

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return valoreVideo;
    }


}// fine della classe
