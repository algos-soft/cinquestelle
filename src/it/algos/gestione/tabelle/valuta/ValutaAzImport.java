/**
 * Copyright: Copyright (c) 2006
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      18-apr-2007
 */
package it.algos.gestione.tabelle.valuta;

import it.algos.base.azione.AzSpecifica;
import it.algos.base.errore.Errore;
import it.algos.base.menu.menu.MenuBase;
import it.algos.base.modulo.Modulo;

import java.awt.event.ActionEvent;

/**
 * ValutaAzImport.
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
public final class ValutaAzImport extends AzSpecifica {

    /**
     * Costruttore completo con parametri.
     *
     * @param modulo di riferimento
     */
    public ValutaAzImport(Modulo modulo) {
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
        super.setChiave("ValutaAzImport");
        super.setNome("Import");
        super.setTooltip("Creazione iniziale delle valute");
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
        try { // prova ad eseguire il codice
            new ValutaImport(this.getModulo(), true);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }
}// fine della classe
