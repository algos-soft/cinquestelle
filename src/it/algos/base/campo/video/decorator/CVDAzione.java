package it.algos.base.campo.video.decorator; /**
 * Copyright: Copyright (c) 2007
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      19-apr-2007
 */

import it.algos.base.azione.Azione;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.video.CampoVideo;
import it.algos.base.errore.Errore;
import it.algos.base.gestore.Gestore;
import it.algos.base.modulo.Modulo;

import javax.swing.*;
import java.awt.*;

/**
 * //@TODO DESCRIZIONE SINTETICA DELLA CLASSE .
 * </p>
 * Questa classe: <ul>
 * <li> </li>
 * <li> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 19-apr-2007 ore 19.23.18
 */
public final class CVDAzione extends CVDBottone {

    /**
     * azione associata
     */
    private Azione azione = null;


    /**
     * Costruttore completo con parametri.
     *
     * @param campoVideo da decorare
     * @param azione del bottone
     */
    public CVDAzione(CampoVideo campoVideo, Azione azione) {
        /* rimanda al costruttore della superclasse */
        super(campoVideo);

        /* regola le variabili di istanza coi parametri */
        this.setAzione(azione);

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
     * Crea gli elementi GUI del pannelloCampo.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Gli elementi possono essere anche più di uno <br>
     * Gli elementi vengono aggiunti al pannelloCampo <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     *
     * @see #regolaElementi()
     */
    protected void creaComponentiGUI() {
        /* variabili e costanti locali di lavoro */
        JButton bottone;
        Pos pos;

        try { // prova ad eseguire il codice

            /* recupera la posizione */
            pos = this.getPos();

            /* aggiunge il bottone al pannelloCampo */
            bottone = this.creaBottone();
            this.addComponente(bottone, pos);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Crea il bottone con l'azione.
     * <p/>
     *
     * @return il bottone appena creato, comprensivo di azione
     */
    public JButton creaBottone() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Azione azione;
        JButton unBottone = null;
        ImageIcon unIcona = null;

        try { // prova ad eseguire il codice
            azione = this.getAzione();
            continua = (azione != null);

            /* controllo di congruità */
            if (continua) {

                /* inizializza l'azione */
                azione.inizializza();

                /* costruisce il bottone con l'azione prevista */
                unBottone = new JButton(azione.getAzione());
                unBottone.setDisplayedMnemonicIndex(-1);
                unBottone.setText("");
                unBottone.setMargin(new Insets(0, 0, 0, 0));
                unBottone.setOpaque(false);
                unBottone.setBorderPainted(false);
                unBottone.setRolloverEnabled(true);
                /* regola il bottone come non focusable
                 * in modo che non entri nel ciclo del fuoco */
                unBottone.setFocusable(false);
            }// fine del blocco if

            /* recupera l'icona di questa azione */
            if (continua) {
                unIcona = azione.getIcona(Azione.ICONA_PICCOLA);
                continua = (unIcona != null);
            }// fine del blocco if

            /* controllo di congruità */
            if (continua) {
                /* regola il bottone con l'icona selezionata */
                unBottone.setIcon(unIcona);

                /* aggiunge il gestore delle azioni */
                this.addGestore(azione);
            } /* fine del blocco if */

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unBottone;
    }


    /**
     * Aggiunge il gestore all'azione.
     * <p/>
     *
     * @param azione a cui aggiungere il gestore
     */
    private void addGestore(Azione azione) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Campo campoParente = null;
        Modulo mod = null;
        Gestore gest = null;

        try { // prova ad eseguire il codice
            continua = (azione != null);

            if (continua) {
                campoParente = this.getCampoParente();
                continua = (campoParente != null);
            }// fine del blocco if

            if (continua) {
                mod = campoParente.getModulo();
                continua = (mod != null);
            }// fine del blocco if

            if (continua) {
                gest = mod.getGestore();
                continua = (gest != null);
            }// fine del blocco if

            if (continua) {
                azione.setGestore(gest);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    private Azione getAzione() {
        return azione;
    }


    private void setAzione(Azione azione) {
        this.azione = azione;
    }
}// fine della classe
