/**
 * Title:     CalcBase
 * Copyright: Copyright (c) 2009
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      6-lug-2009
 */
package it.algos.albergo.arrivipartenze.riepilogo;

import it.algos.albergo.AlbergoLib;
import it.algos.albergo.prenotazione.periodo.Periodo;
import it.algos.albergo.prenotazione.periodo.PeriodoModulo;
import it.algos.albergo.presenza.PresenzaModulo;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;

/**
 * Calcolatore delle presenze per il singolo giorno di oggi
 * (corrispondente alla data del programma)
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 6-lug-2009 ore 19.26.39
 */
class CalcOggi extends CalcBase{

    /**
     * Costruttore completo con parametri.
     * <p>
     */
    public CalcOggi() {
        super(AlbergoLib.getDataProgramma(), AlbergoLib.getDataProgramma());
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
     * Ritorna il filtro Arrivi Periodi
     * durante il periodo analizzato.
     * <p/>
     *
     * @return il filtro Arrivi Periodi
     */
    public Filtro getFiltroArriviPeri(){
        /* variabili e costanti locali di lavoro */
        Filtro filtro;

        /* solo gli arrivi ancora da confermare */
        filtro = PeriodoModulo.getFiltroArrivi(this.getData1(), this.getData2());
        filtro.add(FiltroFactory.creaFalso(Periodo.Cam.arrivato));

        /* valore di ritorno */
        return filtro;

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

    /**
     * Ritorna il filtro Partenze Periodi
     * durante il periodo analizzato.
     * <p/>
     *
     * @return il filtro Partenze Periodi
     */
    public Filtro getFiltroPartenzePeri(){
        /* variabili e costanti locali di lavoro */
        Filtro filtro;

        /* solo le partenze ancora da confermare */
        filtro = PeriodoModulo.getFiltroPartenze(this.getData1(), this.getData2());
        filtro.add(FiltroFactory.creaFalso(Periodo.Cam.partito));

        /* valore di ritorno */
        return filtro;
    }


    @Override
    public boolean isSensatoPresArrivi() {
        return true;
    }


    @Override
    public boolean isSensatoPresPartenze() {
        return true;
    }


    @Override
    public String getTestoPrec() {
        return "Presenti ieri";
    }


    @Override
    public String getTestoArrivi() {
        return "Arrivi di oggi";
    }


    @Override
    public String getTestoPartenze() {
        return "Partenze di oggi";
    }


    @Override
    public String getTestoFinale() {
        return "Presenti nella notte";
    }

}// fine della classe