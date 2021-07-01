/**
 * Copyright: Copyright (c) 2006
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      18-apr-2007
 */
package it.algos.gestione.tabelle.valuta;

import it.algos.base.azione.AzSpecifica;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoBase;
import it.algos.base.errore.Errore;
import it.algos.base.form.Form;
import it.algos.base.menu.menu.MenuBase;
import it.algos.base.modulo.Modulo;
import it.algos.base.pannello.PannelloCampo;
import it.algos.base.scheda.Scheda;

import javax.swing.JButton;
import java.awt.Container;
import java.awt.event.ActionEvent;

/**
 * ValutaAzCambio.
 * <p/>
 * Questa classe azione concreta: <ul>
 * <li> @TODO DESCRIZIONE SINTETICA DELL'AZIONE </li>
 * <li> Implementa il metodo <code>actionPerformed</code> della interfaccia
 * <code>ActionListener</code> </li>
 * <li> Viene usata nei menu, nelle toolbar e nei bottoni di comando </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 18-apr-2007 ore 20.48.13
 */
public final class ValutaAzCambio extends AzSpecifica {

    /**
     * Costruttore completo con parametri.
     *
     * @param modulo di riferimento
     */
    public ValutaAzCambio(Modulo modulo) {
        /* rimanda al costruttore della superclasse */
        super(modulo, POS);

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
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* regola le variabili*/
        super.setChiave("ValutaAzCambio");
        super.setNome("Cambi");
        super.setTooltip("Principali cambi correnti");
        super.setHelp("");
        super.setIconaPiccola("Import16");
        super.setIconaMedia("Import24");
        super.setIconaGrande("");
        super.setCarattereAcceleratore(' ');
        super.setCarattereMnemonico(0);
        super.setCarattereComando(null);
        super.setAttiva(true);
        super.setAbilitataPartenza(true);
        super.setColonnaMenu(MenuBase.MenuTipo.HELP);
        super.setUsoLista(true);
    }// fine del metodo inizia


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
        boolean continua;
        Object ogg;
        JButton bot;
        Container cont = null;
        PannelloCampo pan;
        Campo campo = null;
        CampoBase campoBase;
        Form form = null;
        Scheda scheda;
        int cod = 0;

        try { // prova ad eseguire il codice
            ogg = unEvento.getSource();
            continua = (ogg != null);

            if (continua) {
                continua = (ogg instanceof JButton);
            }// fine del blocco if

            if (continua) {
                bot = (JButton)ogg;
                cont = bot.getParent();
                continua = (cont != null);
            }// fine del blocco if

            if (continua) {
                continua = (cont instanceof PannelloCampo);
            }// fine del blocco if

            if (continua) {
                pan = (PannelloCampo)cont;
                campo = pan.getCampo();
                continua = (campo != null);
            }// fine del blocco if

            if (continua) {
                continua = (campo instanceof CampoBase);
            }// fine del blocco if

            if (continua) {
                campoBase = (CampoBase)campo;
                form = campoBase.getForm();
                continua = (form != null);
            }// fine del blocco if

            if (continua) {
                continua = (form instanceof Scheda);
            }// fine del blocco if

            if (continua) {
                scheda = (Scheda)form;
                cod = scheda.getCodice();
            }// fine del blocco if

            new ValutaCambio(this.getModulo(), cod);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


}// fine della classe
