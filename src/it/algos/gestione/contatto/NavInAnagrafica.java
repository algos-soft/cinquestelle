/**
 * Title:     NavInAnagrafica
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      9-ago-2007
 */
package it.algos.gestione.contatto;

import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.NavigatoreLS;
import it.algos.base.toolbar.ToolBar;

/**
 * Navigatore dei contatti dentro a una scheda anagrafica.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 3-feb-2005 ore 12.28.15
 */
public final class NavInAnagrafica extends NavigatoreLS implements Contatto {


    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param modulo il modulo di riferimento
     */
    public NavInAnagrafica(Modulo modulo) {
        /* rimanda al costruttore della superclasse */
        super(modulo);

        /* regola le variabili di istanza coi parametri */

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
        /* variabili e costanti locali di lavoro */

        try { // prova ad eseguire il codice

            this.setUsaFrecceSpostaOrdineLista(true);
            this.setUsaPannelloUnico(true);
            this.getPortaleLista().setUsaStatusBar(false);
            this.setRigheLista(3);
            this.setNomeVista(Contatto.Vis.contattiInAnag.toString());
//            this.addSchedaCorrente(new IndirizzoScheda(this.getModulo()));
            this.setUsaPreferito(false);
            this.setUsaFinestraPop(true);
            this.getPortaleLista().setPosToolbar(ToolBar.Pos.nord);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


    }// fine del metodo inizia


    /**
     * Inizializzazione dell'oggetto.<br>
     * Regola le caratteristiche dinamiche in base alla impostazione
     * corrente delle caratteristiche statiche.<br>
     * Viene chiamato dall'inizializzazione del Modulo.<br>
     * Puo' essere chiamato piu' volte.<br>
     * Se l'inizializzazione ha successo imposta il flag inizializzato a true.<br>
     * Il flag puo' essere successivamente modificato dalle sottoclassi se non
     * riescono a portare a termine la propria inizializzazione specifica.<br>
     */
    public void inizializza() {
        super.inizializza();    //To change body of overridden methods use File | Settings | File Templates.
    }// fine del metodo inizializza


    /**
     * Abilita l'uso del pulsante Preferito nella lista.
     * <p/>
     *
     * @param flag per usare il pulsante
     */
    public void setUsaPreferito(boolean flag) {
        super.setUsaPreferito(flag);    //To change body of overridden methods use File | Settings | File Templates.
    }
}// fine della classe
