/**
 * Package:      it.algos.base.campo.dati
 * Description:
 * Copyright:    Copyright (c) 2005
 * Company:      Algos s.r.l.
 * @author Ceresa, Valbonesi
 * @version 1.0  /
 * Creato:       il 28 geb 2005 alle 19.22
 */

package it.algos.base.campo.dati;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.tipodati.tipomemoria.TipoMemoria;
import it.algos.base.campo.tipodati.tipovideo.TipoVideo;
import it.algos.base.errore.Errore;

/**
 * Campo dati per un campo logico (non fisico)
 * <p/>
 * Un campo logico non ha mai un tipo Archivio
 * Un campo logico puo' avere un tipo Memoria
 * Un campo logico puo' avere un tipo Video
 */
public abstract class CDLogico extends CDBase {

    /**
     * Costruttore con solo campo parente <br>
     * Rimanda al costruttore completo utilizzando eventuali valori di default
     *
     * @param unCampoParente campo 'contenitore' di questo oggetto
     */
    public CDLogico(Campo unCampoParente) {
        /** rimanda al costruttore di questa classe */
        this(unCampoParente, null, null);
    } /* fine del metodo costruttore base */


    /**
     * Costruttore completo
     *
     * @param unCampoParente campo 'contenitore' di questo oggetto
     * @param tipoMemoria il tipo di dati per la variabile Memoria
     * (interfaccia TipoMemoria)
     * @param tipoVideo il tipo di dati per la variabile Video
     * (interfaccia TipoVideo)
     */
    public CDLogico(Campo unCampoParente, TipoMemoria tipoMemoria, TipoVideo tipoVideo) {

        /* rimanda al costruttore della superclasse */
        super(unCampoParente, null, tipoMemoria, tipoVideo);

        try {                                   // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) {           // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore completo */


    /**
     * Regolazioni iniziali di riferimenti e variabili
     * Metodo chiamato direttamente dal costruttore
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
    } /* fine del metodo inizia */

}// fine della classe