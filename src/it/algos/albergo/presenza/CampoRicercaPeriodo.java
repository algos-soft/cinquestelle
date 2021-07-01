/**
 * Title:     PresenzaRicerca
 * Copyright: Copyright (c) 2009
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      20-lug-2009
 */
package it.algos.albergo.presenza;

import it.algos.base.campo.base.Campo;
import it.algos.base.database.util.Operatore;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.ricerca.CampoRicerca;
import it.algos.base.ricerca.RicercaBase;

import java.util.Date;

/**
 * Campo di ricerca specifico per il periodo di soggiorno
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 20-lug-2009 ore 22.11.47
 */
public class CampoRicercaPeriodo extends CampoRicerca {

    public CampoRicercaPeriodo(
            RicercaBase ricerca, Campo campo) {

        super(
                ricerca,
                campo,
                Operatore.UGUALE,
                true);
    }


    @Override
    public Filtro getFiltro() {
        /* variabili e costanti locali di lavoro */
        Filtro filtro=null;

        Campo campo1 = this.getCampo1();
        Campo campo2 = this.getCampo2();
        Date d1 = Libreria.getDate(campo1.getValore());
        Date d2 = Libreria.getDate(campo2.getValore());

        if (Lib.Data.isValida(d1) || Lib.Data.isValida(d2)) {
            filtro=PresenzaModulo.getFiltroPresenze(d1,d2);
        }// fine del blocco if

        /* valore di ritorno */
        return filtro;
    }
}// fine della classe