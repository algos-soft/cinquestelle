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
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.query.filtro.Filtro;

/**
 * Dialogo con una lista di valori esterni (link).
 *
 * @author Guido Andrea Ceresa
 * @author Alessandro Valbonesi
 * @author gac
 * @version 1.0  /  2 novembre 2003 ore 7.53
 */
public class DialogoListaLink extends DialogoLista {

    private Modulo moduloLink;

    private String campoLink;


    /**
     * Costruttore senza parametri.
     * <p/>
     */
    public DialogoListaLink() {
        this(null, null);

    } /* fine del metodo costruttore completo */


    /**
     * Costruttore base con parametri.
     * <p/>
     */
    public DialogoListaLink(Modulo moduloLink, String campoLink) {

        super("");

        /* regola le variabili di istanza coi parametri */
        this.setModuloLink(moduloLink);
        this.setCampoLink(campoLink);

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
        Campo campoLista;
        Modulo modulo;
        String campoLink;
        String nomeMod;

        try { // prova ad eseguire il codice
            modulo = this.getModuloLink();
            nomeMod = modulo.getNomeChiave();

            campoLink = this.getCampoLink();

            campoLista = CampoFactory.listaLink(NOME_CAMPO_LISTA);
            campoLista.getCampoDB().setNomeModuloLinkato(nomeMod);
            campoLista.getCampoDB().setNomeCampoValoriLinkato(campoLink);
            campoLista.decora().obbligatorio();
            campoLista.decora().eliminaEtichetta();

            this.setCampoLista(campoLista);
            this.addCampo(campoLista);

            /* si registra presso il campo */
            campoLista.addListener(new AzioneDoppioClick());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    } /* fine del metodo inizia */


    public void setFiltro(Filtro filtro) {
        /* variabili e costanti locali di lavoro */
        Campo campo;

        try { // prova ad eseguire il codice
            campo = this.getCampoLista();
            if (campo != null) {
                campo.getCampoDB().setFiltroCorrente(filtro);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    private Modulo getModuloLink() {
        return moduloLink;
    }


    private void setModuloLink(Modulo moduloLink) {
        this.moduloLink = moduloLink;
    }


    private String getCampoLink() {
        return campoLink;
    }


    private void setCampoLink(String campoLink) {
        this.campoLink = campoLink;
    }
}// fine della classe
