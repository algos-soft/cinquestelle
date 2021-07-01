/**
 * Title:     WrapCampo
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      12-ago-2004
 */
package it.algos.base.wrapper;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;

/**
 * //@TODO DESCRIZIONE SINTETICA DELLA CLASSE .
 * </p>
 * Questa classe: <ul>
 * <li> </li>
 * <li> </li>
 * </ul>
 *
 * @author Alberto Colombo, Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 12-ago-2004 ore 15.32.31
 */
public final class WrapCampo extends Object {

    /**
     * flag per il campo usato nella interfaccia (visibile o meno)
     */
    private boolean isUsatoGUI = false;

    /**
     * flag per il campo fisico
     */
    private boolean isFisico = false;

    private Campo campo = null;


    /**
     * Costruttore base senza parametri. <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo <br>
     * Utilizza eventuali valori di default <br>
     */
    public WrapCampo() {
        /* rimanda al costruttore di questa classe */
        this(null);
    }// fine del metodo costruttore base


    /**
     * Costruttore completo con parametri. <br>
     *
     * @param unCampo
     */
    public WrapCampo(Campo unCampo) {
        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza coi parametri */
        this.setCampo(unCampo);

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
        /* regola il flag campo fisico dal campo */
        this.setFisico(this.getCampo().getCampoDB().isCampoFisico());
    }// fine del metodo inizia


    public boolean isFisico() {
        return isFisico;
    }


    public void setFisico(boolean fisico) {
        isFisico = fisico;
    }


    public Campo getCampo() {
        return campo;
    }


    public void setCampo(Campo unCampo) {
        this.campo = unCampo;
    }

}// fine della classe
