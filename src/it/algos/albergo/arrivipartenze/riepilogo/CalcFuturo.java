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
 * Calcolatore delle presenze su un periodo situato nel futuro
 * rispetto alla data del programma
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 6-lug-2009 ore 19.26.39
 */
class CalcFuturo extends CalcBase{

    /**
     * Costruttore completo con parametri.
     * <p>
     *
     * @param data1 data di inizio periodo
     * @param data2 data di fine periodo
     */
    public CalcFuturo(Date data1, Date data2) {
        super(data1, data2);
    }// fine del metodo costruttore completo




    /**
     * Ritorna il filtro Precedenti Presenze
     * al giorno precedente il periodo analizzato.
     * <p/>
     * + (PRES) presenti al (d0)
     * @return il filtro Precedenti Presenze
     */
    public Filtro getFiltroPrecPres(){
        return PresenzaModulo.getFiltroPresenze(AlbergoLib.getDataProgramma());
    }


    /**
     * Ritorna il filtro Precedenti Periodi Positivo
     * per il giorno precedente il periodo analizzato.
     * <p/>
     * Le persone dei periodi selezionati da questo filtro
     * vengono sottratte dal totale
     *
     * + (PREN) arrivi non conf del d0
     * + (PREN) arrivi da (d0+1) a (d1-1)
     *
     * @return il filtro Precedenti Periodi Positivo
     */
    public Filtro getFiltroPrecPeriPos(){
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

            /* solo se il periodo futuro non inizia il
             * giorno successivo a quello corrente  */
            Date da = Lib.Data.add(d0,1);
            Date db = Lib.Data.add(this.getData1(),-1);
            if (Lib.Data.isPosterioreUguale(da,db)) {

                /* + (PREN) arrivi da (d0+1) a (d1-1) */
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
     * Ritorna il filtro Precedenti Periodi Negativo
     * per il giorno precedente il periodo analizzato.
     * <p/>
     * Le persone dei periodi selezionati da questo filtro
     * vengono sottratte dal totale
     *
     * - (PREN) partenze non conf del d0
     * - (PREN) partenze da (d0+1) a (d1-1)
     *
     * @return il filtro Precedenti Periodi Negativo
     */
    public Filtro getFiltroPrecPeriNeg(){
        /* variabili e costanti locali di lavoro */
        Filtro filtroTot=new Filtro();
        Filtro filtro;

        try { // prova ad eseguire il codice

            Date d0 = AlbergoLib.getDataProgramma();


            /* - (PREN) partenze non conf del d0  */
            filtro = new Filtro();
            filtro.add(PeriodoModulo.getFiltroPartenze(d0, d0));
            filtro.add(FiltroFactory.creaFalso(Periodo.Cam.partito));
            filtroTot.add(filtro);


            /* solo se il periodo futuro non inizia il
             * giorno successivo a quello corrente  */
            Date da = Lib.Data.add(d0,1);
            Date db = Lib.Data.add(this.getData1(),-1);
            if (Lib.Data.isPosterioreUguale(da,db)) {

                /* - (PREN) partenze da (d0+1) a (d1-1) */
                filtro = PeriodoModulo.getFiltroPartenze(da,db);
                filtroTot.add(Filtro.Op.OR, filtro);

            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return filtroTot;
    }



    /**
     * Ritorna il filtro Arrivi Periodi
     * durante il periodo analizzato.
     * <p/>
     *
     * @return il filtro Arrivi Periodi
     */
    public Filtro getFiltroArriviPeri(){
        return PeriodoModulo.getFiltroArrivi(this.getData1(), this.getData2());
    }

    /**
     * Ritorna il filtro Partenze Periodi
     * durante il periodo analizzato.
     * <p/>
     *
     * @return il filtro Partenze Periodi
     */
    public Filtro getFiltroPartenzePeri(){
        return PeriodoModulo.getFiltroPartenze(this.getData1(), this.getData2());
    }


    /**
     * Ritorna il filtro Finale Presenze
     * presenze alla fine del periodo analizzato.
     * <p/>
     *
     * @return il filtro Finale Presenze
     */
    public Filtro getFiltroFinalePres(){
        return null;
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
            testo = "Arrivi del "+ Lib.Data.getDataBrevissima(this.getData1());
        } else {
            testo = super.getTestoArrivi();
        }// fine del blocco if-else

        /* valore di ritorno */
        return testo;
    }


    @Override
    public String getTestoPartenze() {
        /* variabili e costanti locali di lavoro */
        String testo="";

        if (this.getData1().equals(this.getData2())) {
            testo = "Partenze del "+ Lib.Data.getDataBrevissima(this.getData1());
        } else {
            testo = super.getTestoPartenze();
        }// fine del blocco if-else

        /* valore di ritorno */
        return testo;
    }


    @Override
    public String getTestoFinale() {
        return super.getTestoFinale();
    }



}// fine della classe