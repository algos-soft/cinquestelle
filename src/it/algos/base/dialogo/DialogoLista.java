/**
 * Title:        DialogoBase.java
 * Package:      it.algos.base.dialogo
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Ceresa, Colombo, Valbonesi
 * @version 1.0  /
 * Creato:       il 2 novembre 2003 alle 7.53
 */

package it.algos.base.dialogo;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.evento.campo.CampoDoppioClickAz;
import it.algos.base.evento.campo.CampoDoppioClickEve;

/**
 * Dialogo con dentro una lista.
 *
 * @author Guido Andrea Ceresa
 * @author Alessandro Valbonesi
 * @author gac
 * @version 1.0  /  2 novembre 2003 ore 7.53
 */
public abstract class DialogoLista extends DialogoAnnullaConferma {


    private Campo campoLista;

    protected static final String NOME_CAMPO_LISTA = "lista";


    /**
     * Costruttore base con parametri.
     * <p/>
     */
    public DialogoLista(String titolo) {

        super(titolo);

        /* regolazioni iniziali di riferimenti e variabili */
        try {
            this.inizia();
        } catch (Exception unErrore) {
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore completo */


    /**
     * Regolazioni iniziali di riferimenti e variabili <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione <br>
     */
    private void inizia() throws Exception {
    } /* fine del metodo inizia */


    public Campo getCampoLista() {
        return campoLista;
    }


    protected void setCampoLista(Campo campoLista) {
        this.campoLista = campoLista;
    }


    /**
     * Listener invocato quando si clicca due volte nel campo.
     */
    protected class AzioneDoppioClick extends CampoDoppioClickAz {

        /**
         * campoDoppioClickAz, da CampoDoppioClickLis.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo sovrascritto, nell'oggetto specifico
         * della classe che genera questo evento <br>
         * Sovrascritto nelle sottoclassi <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void campoDoppioClickAz(CampoDoppioClickEve unEvento) {
            if (isConfermabile()) {
                setConfermato(true);
                chiudiDialogo();
            }// fine del blocco if

        }
    }

}// fine della classe
