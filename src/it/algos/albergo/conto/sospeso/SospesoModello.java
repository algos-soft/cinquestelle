/**
 * Title:     SospesoModello
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      28-set-2007
 */
package it.algos.albergo.conto.sospeso;

import it.algos.albergo.conto.Conto;
import it.algos.albergo.conto.movimento.MovimentoModello;
import it.algos.base.errore.Errore;

/**
 * Tracciato record della tavola Pagamento.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0 / 28-set-2007
 */
public class SospesoModello extends MovimentoModello implements Sospeso {

    /**
     * Costruttore completo senza parametri.
     */
    public SospesoModello() {
        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        /* regola il nome della tavola dalla costante */
        super.setTavolaArchivio(Sospeso.NOME_TAVOLA);

        /* campo totale sincronizzato */
        this.setCampoContoSync(Conto.Cam.totSospeso);

    }


} // fine della classe
