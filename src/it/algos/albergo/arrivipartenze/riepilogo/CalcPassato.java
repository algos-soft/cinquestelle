/**
 * Title:     CalcBase
 * Copyright: Copyright (c) 2009
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      6-lug-2009
 */
package it.algos.albergo.arrivipartenze.riepilogo;

import it.algos.albergo.presenza.PresenzaModulo;
import it.algos.base.libreria.Lib;
import it.algos.base.query.filtro.Filtro;

import java.util.Date;

/**
 * Calcolatore delle presenze su un periodo situato nel passato
 * rispetto alla data del programma
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 6-lug-2009 ore 19.26.39
 */
class CalcPassato extends CalcBase{



    /**
     * Costruttore completo con parametri.
     * <p>
     *
     * @param data1 data di inizio periodo
     * @param data2 data di fine periodo
     */
    public CalcPassato(Date data1, Date data2) {
        super(data1, data2);
    }// fine del metodo costruttore completo



    /**
     * Ritorna il filtro Precedenti Presenze
     * al giorno precedente il periodo analizzato.
     * <p/>
     *
     * @return il filtro Precedenti Presenze
     */
    public Filtro getFiltroPrecPres(){
        return PresenzaModulo.getFiltroPresenze(this.getGiornoPrecedente());
    }

    /**
     * Ritorna il filtro Arrivi Presenze
     * durante il periodo analizzato.
     * <p/>
     *
     * @return il filtro Arrivi Presenze
     */
    public Filtro getFiltroArriviPres(){
        return PresenzaModulo.getFiltroPresenzeArrivate(this.getData1(), this.getData2());
    }

    /**
     * Ritorna il filtro Partenze Presenze
     * durante il periodo analizzato.
     * <p/>
     *
     * @return il filtro Partenze Presenze
     */
    public Filtro getFiltroPartenzePres(){
        return PresenzaModulo.getFiltroPresenzePartite(this.getData1(), this.getData2());
    }

    @Override
    public String getTestoPrec() {
        return super.getTestoPrec();
    }


    @Override
    public String getTestoArrivi() {
        /* variabili e costanti locali di lavoro */
        String testo="";

        if (this.getData1().equals(this.getData2())) {
            testo = "Arrivati il "+ Lib.Data.getDataBrevissima(this.getData1());
        } else {
            testo = "Arrivati nel periodo";
        }// fine del blocco if-else

        /* valore di ritorno */
        return testo;

    }


    @Override
    public String getTestoPartenze() {
        /* variabili e costanti locali di lavoro */
        String testo="";

        if (this.getData1().equals(this.getData2())) {
            testo = "Partiti il "+ Lib.Data.getDataBrevissima(this.getData1());
        } else {
            testo = "Partiti nel periodo";
        }// fine del blocco if-else

        /* valore di ritorno */
        return testo;

    }


    @Override
    public String getTestoFinale() {
        return super.getTestoFinale();
    }



}// fine della classe