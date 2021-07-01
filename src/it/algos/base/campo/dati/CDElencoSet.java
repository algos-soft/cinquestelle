/**
 * Copyright(c): 2006
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 4 ott 2006
 */

package it.algos.base.campo.dati;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.db.CampoDB;
import it.algos.base.campo.tipodati.tipoarchivio.TALink;
import it.algos.base.campo.tipodati.tipoarchivio.TipoArchivio;
import it.algos.base.campo.tipodati.tipomemoria.TMIntero;
import it.algos.base.campo.tipodati.tipomemoria.TipoMemoria;
import it.algos.base.campo.tipodati.tipovideo.TVIntero;
import it.algos.base.campo.tipodati.tipovideo.TipoVideo;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Libreria;
import it.algos.base.modulo.Modulo;

/**
 * </p>
 * Questa classe: <ul>
 * <li> </li>
 * <li> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 4 ott 2006
 */
public class CDElencoSet extends CDBase {

    private static final TipoArchivio TIPO_ARCHIVIO = TALink.getIstanza();

    private static final TipoMemoria TIPO_MEMORIA = TMIntero.getIstanza();

    private static final TipoVideo TIPO_VIDEO = TVIntero.getIstanza();


    /**
     * Costruttore completo
     *
     * @param unCampoParente campo 'contenitore' di questo oggetto
     */
    public CDElencoSet(Campo unCampoParente) {

        /* rimanda al costruttore della superclasse */
        super(unCampoParente, TIPO_ARCHIVIO, TIPO_MEMORIA, TIPO_VIDEO);
//        super(unCampoParente);

        /* regolazioni iniziali di riferimenti e variabili */
        try {                                   // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */


    } /* fine del metodo costruttore completo */


    /**
     * Regolazioni iniziali di riferimenti e variabili <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        try { // prova ad eseguire il codice
//            this.setTipoArchivio(TIPO_ARCHIVIO);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


    } /* fine del metodo inizia */


    /**
     * Recupera il valore di elenco correntemente selezionato.
     * <p/>
     *
     * @return il valore corrispondente
     */
    public Object getValoreElenco() {
        /* variabili e costanti locali di lavoro */
        Object valore = null;
        Campo campo;
        Object mem;
        CampoDB cdb;
        Modulo moduloLink;
        Campo campoLink;
        int codice;

        try { // prova ad eseguire il codice

            campo = this.getCampoParente();
            mem = this.getMemoria();
            codice = Libreria.getInt(mem);
            cdb = campo.getCampoDB();
            moduloLink = cdb.getModuloLinkato();
            if (moduloLink != null) {
                campoLink = cdb.getCampoValoriLinkato();
                if (campoLink != null) {
                    valore = moduloLink.query().valoreCampo(campoLink, codice);
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        return valore;
    } /* fine del metodo getter */


    /**
     * Ritorna true se il campo e' numerico.
     *
     * @return true se è campo numerico
     */
    public boolean isNumero() {
        return true;
    }

} // fine della classe
