/**
 * Copyright: Copyright (c) 2007
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      22-nov-2007
 */
package it.algos.base.campo.logica;

import it.algos.base.campo.base.Campo;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;

/**
 * Logica del campo Estratto Interno
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 22-nov-2007 ore 14.45.41
 */
public class CLEstrattoInterno extends CLEstratto {

    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param unCampoParente campo 'contenitore' di questo oggetto
     */
    public CLEstrattoInterno(Campo unCampoParente) {
        /* rimanda al costruttore della superclasse */
        super(unCampoParente);

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
    }


    /**
     * Ritorna il codice del record esterno per il quale recuperare l'estratto.
     * <p/>
     * Per l'estratto interno, il codice esterno è uguale al codice interno
     *
     * @param codRecordInterno il codice interno del record interno mantenuto dal campo
     *
     * @return il codice del record esterno
     */
    protected int getCodRecordEsterno(int codRecordInterno) {
        return codRecordInterno;
    }


    /**
     * Crea un nuovo record sul modulo esterno.
     * <p/>
     *
     * @param modulo esterno sul quale creare il record
     * @param conn la connessione da utilizzare
     *
     * @return il codice del record creato, <=0 se non creato
     */
    protected int creaRecordEsterno(Modulo modulo, Connessione conn) {
        /* variabili e costanti locali di lavoro */
        int codice = 0;

        try { // prova ad eseguire il codice
            codice = modulo.query().nuovoRecord(conn);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codice;
    }


    /**
     * Invocato dopo la creazione di un record esterno.
     * <p/>
     * Registra il codice come riferimento esterno nella memoria del campo
     *
     * @param codice del record esterno creato
     */
    protected void recordEsternoCreato(int codice) {
        this.setRifEsterno(codice);
        super.recordEsternoCreato(codice);
    }


    /**
     * Invocato dopo la eliminazione del record esterno.
     * <p/>
     * Registra 0 come riferimento esterno nella memoria del campo
     */
    protected void recordEsternoEliminato() {
        this.setRifEsterno(0);
        super.recordEsternoEliminato();
    }
}// fine della classe