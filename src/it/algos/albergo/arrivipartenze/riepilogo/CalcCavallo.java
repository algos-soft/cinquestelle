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
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;

import java.util.Date;

/**
 * Calcolatore delle presenze su un periodo situato a cavallo
 * della data del programma
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 6-lug-2009 ore 19.26.39
 */
class CalcCavallo extends CalcBase{


    /**
     * Costruttore completo con parametri.
     * <p>
     *
     * @param data1 data di inizio periodo
     * @param data2 data di fine periodo
     */
    public CalcCavallo(Date data1, Date data2) {
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
        return PresenzaModulo.getFiltroPresenzeArrivate(this.getData1(), AlbergoLib.getDataProgramma());
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
        Filtro filtroTot=new Filtro();
        Filtro filtro;

        try { // prova ad eseguire il codice

            Date d0 = AlbergoLib.getDataProgramma();


            /* + (PREN) arrivi non conf del d0  */
            filtro = new Filtro();
            filtro.add(PeriodoModulo.getFiltroArrivi(d0, d0));
            filtro.add(FiltroFactory.creaFalso(Periodo.Cam.arrivato));
            filtroTot.add(filtro);

            /* solo se d2 è maggiore di d0 */
            Date da = Lib.Data.add(d0,1);
            Date db = this.getData2();
            if (Lib.Data.isPosterioreUguale(da,db)) {

                /* + (PREN) arrivi da (d0+1) a d2 */
                filtro = PeriodoModulo.getFiltroArrivi(da,db);
                filtroTot.add(Filtro.Op.OR, filtro);

            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return filtroTot;
    }



    /**
     * Ritorna il filtro Partenze Presenze
     * durante il periodo analizzato.
     * <p/>
     *
     * @return il filtro Partenze Presenze
     */
    public Filtro getFiltroPartenzePres(){
        return PresenzaModulo.getFiltroPresenzePartite(this.getData1(), AlbergoLib.getDataProgramma());
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
        Filtro filtroTot=new Filtro();
        Filtro filtro;

        try { // prova ad eseguire il codice

            Date d0 = AlbergoLib.getDataProgramma();


            /* + (PREN) partenze non conf del d0  */
            filtro = new Filtro();
            filtro.add(PeriodoModulo.getFiltroPartenze(d0, d0));
            filtro.add(FiltroFactory.creaFalso(Periodo.Cam.partito));
            filtroTot.add(filtro);

            /* solo se d2 è maggiore di d0 */
            Date da = Lib.Data.add(d0,1);
            Date db = this.getData2();
            if (Lib.Data.isPosterioreUguale(da,db)) {

                /* + (PREN) partenze da (d0+1) a d2 */
                filtro = PeriodoModulo.getFiltroPartenze(da,db);
                filtroTot.add(Filtro.Op.OR, filtro);

            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return filtroTot;
    }

    


    @Override
    public String getTestoPrec() {
        return super.getTestoPrec();
    }


    @Override
    public String getTestoArrivi() {
        return super.getTestoArrivi();
    }


    @Override
    public String getTestoPartenze() {
        return super.getTestoPartenze();
    }


    @Override
    public String getTestoFinale() {
        return super.getTestoFinale();
    }


}// fine della classe