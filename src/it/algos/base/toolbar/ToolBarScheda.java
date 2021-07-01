/**
 * Title:        ToolBarScheda.java
 * Package:      it.algos.base.toolbar
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Ceresa, Colombo, Valbonesi
 * @version 1.0  /
 * Creato:       il 7 settembre 2003 alle 17.21
 */

package it.algos.base.toolbar;

import it.algos.base.azione.Azione;
import it.algos.base.errore.Errore;
import it.algos.base.portale.Portale;
import it.algos.base.pref.Pref;

import javax.swing.*;
import java.awt.event.KeyListener;

/**
 * ToolBar del </code>PortaleScheda</code>.
 * <p/>
 * Bottoni <code>Azione</code> previsti: <ul>
 * <li> Annulla modifiche (sempre visibile) </li>
 * <li> Registra record (sempre visibile) </li>
 * <li> Vai al primo record (opzionale) </li>
 * <li> vai al record precedente (opzionale) </li>
 * <li> vai al record successivo (opzionale) </li>
 * <li> Vai all'ultimo record (opzionale) </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa
 * @author Alessandro Valbonesi
 * @author gac
 * @version 1.0  /  7 settembre 2003 ore 17.21
 * @see it.algos.base.portale.PortaleScheda
 */
public final class ToolBarScheda extends ToolBarBase {

    /**
     * default per l'orientamento
     */
    private static final boolean USA_VERTICALE = false;

    /**
     * default per l'uso del bottone annulla modifiche
     */
    private static final boolean USA_ANNULLA = true;

    /**
     * default per l'uso del bottone registra record
     */
    private static final boolean USA_REGISTRA = true;

    /**
     * default per l'uso dei bottoni delle frecce di spostamento record
     */
    private static final boolean USA_FRECCE = true;

    /**
     * flag - usa il bottone annulla modifiche
     */
    private boolean isUsaAnnulla = false;

    /**
     * flag - usa il bottone registra record
     */
    private boolean isUsaRegistra = false;

    /**
     * flag - usa i bottoni delle frecce di spostamento record
     */
    private boolean isUsaFrecce = false;


    /**
     * Costruttore completo con parametri.
     *
     * @param unPortale portale proprietario di questa toolbar
     */
    public ToolBarScheda(Portale unPortale) {
        /* rimanda al costruttore della superclasse */
        super(unPortale);

        /* regolazioni iniziali di riferimenti e variabili */
        try { // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } /* fine del metodo costruttore completo */


    /**
     * Regolazioni immediate di riferimenti e variabili.
     * </p>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* modifica l'impostazione di default della superclasse */
        this.setVerticale(USA_VERTICALE);

        /* regolazioni di default */
        this.setUsaAnnulla(USA_ANNULLA);
        this.setUsaRegistra(USA_REGISTRA);
        this.setUsaFrecce(USA_FRECCE);
    } /* fine del metodo inizia */

    @Override
    public void inizializza() {
        super.inizializza();
    }

    @Override
    protected void addBottoniStandard() {
        /* variabili e costanti locali di lavoro */
        JButton botEscape;
        JButton botEnter;

        /* chiusura della scheda (sempre presente) */
        botEscape = super.addBottone(Azione.CHIUDE_SCHEDA);
        super.setBotEscape(botEscape);

        /* registrazione un record (di solito presente) */
        if (this.isUsaRegistra()) {
            botEnter = super.addBottone(Azione.REGISTRA_SCHEDA);
            super.setBotEnter(botEnter);
        }// fine del blocco if

        /* annullamento delle modifiche (di solito presente) */
        if (this.isUsaAnnulla()) {
            super.addBottone(Azione.ANNULLA_MODIFICHE);
        }// fine del blocco if

        /* bottoni delle frecce di spostamento record */
        if (Pref.GUI.spostamento.is()) {
            if (this.isUsaFrecce()) {
                super.addBottone(Azione.PRIMO_RECORD);
                super.addBottone(Azione.RECORD_PRECEDENTE);
                super.addBottone(Azione.RECORD_SUCCESSIVO);
                super.addBottone(Azione.ULTIMO_RECORD);
            } /* fine del blocco if */
        } /* fine del blocco if */
    }


    private boolean isUsaAnnulla() {
        return isUsaAnnulla;
    }


    public void setUsaAnnulla(boolean usaAnnulla) {
        isUsaAnnulla = usaAnnulla;
    }


    private boolean isUsaRegistra() {
        return isUsaRegistra;
    }


    public void setUsaRegistra(boolean usaRegistra) {
        isUsaRegistra = usaRegistra;
    }


    /**
     * Determina se usa i bottoni di spostamento di record avanti e indietro
     */
    public boolean isUsaFrecce() {
        return isUsaFrecce;
    }


    public void setUsaFrecce(boolean usaFrecce) {
        isUsaFrecce = usaFrecce;
    }

}// fine della classe