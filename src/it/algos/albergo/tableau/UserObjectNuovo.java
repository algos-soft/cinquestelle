/**
 * Title:     UserObjectPeriodo
 * Copyright: Copyright (c) 2009
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      25-feb-2009
 */
package it.algos.albergo.tableau;

import java.util.Date;

/**
 * UserObject di una cella di Nuovo Periodo nel grafo
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 25-feb-2009 ore 19.12.04
 */
class UserObjectNuovo {

    private int codCamera;

    private Date dataInizio;

    private Date dataFine;


    public int getCodCamera() {
        return codCamera;
    }


    public void setCodCamera(int cod) {
        this.codCamera = cod;
    }


    public Date getDataInizio() {
        return dataInizio;
    }


    public void setDataInizio(Date dataInizio) {
        this.dataInizio = dataInizio;
    }


    public Date getDataFine() {
        return dataFine;
    }


    public void setDataFine(Date dataFine) {
        this.dataFine = dataFine;
    }


}// fine della classe