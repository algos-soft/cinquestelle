/**
 * Title:     CalcBase
 * Copyright: Copyright (c) 2009
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      6-lug-2009
 */
package it.algos.albergo.arrivipartenze.riepilogo;

import it.algos.albergo.prenotazione.periodo.Periodo;
import it.algos.albergo.prenotazione.periodo.PeriodoModulo;
import it.algos.albergo.presenza.Presenza;
import it.algos.albergo.presenza.PresenzaModulo;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.wrapper.Campi;

import java.util.Date;

/**
 * Calcolatore dei movimenti su un periodo
 * </p>
 * Classe base astratta
 * <p/>
 * Dato un intervallo di tempo,
 * calcola il numero di persone:
 * - A) presenti al giorno precedente
 * - B) arrivate o in arrivo nel periodo
 * - C) partite o in partenza nel periodo
 * - D) rimanenti al termine del periodo (D=A+B-C)
 * <p/>
 * I risultati sono suddivisi in Adulti e Bambini
 * <p/>
 * Il calcolo è differente nel caso il periodo specificato
 * si trovi nel passato, nel giorno di oggi , nel futuro, o a cavallo
 * del giorno di oggi (dove oggi = data del programma).
 * <p/>
 * Tali differenze sono implementate nelle apposite sottoclassi.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 6-lug-2009 ore 19.26.39
 */
abstract class CalcBase implements CalcMovimenti {

    /* data di inizio periodo */
    private Date data1;

    /* data di fine periodo */
    private Date data2;

    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param data1 data di inizio periodo
     * @param data2 data di fine periodo
     */
    public CalcBase(Date data1, Date data2) {
        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza coi parametri */
        this.setData1(data1);
        this.setData2(data2);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */

        try { // prova ad eseguire il codice

            /**
             * controlla che le date fornite siano valide
             * se non lo sono solleva una eccezione
             */
            if (!this.isDateValide()) {
                throw new Exception("Date non valide o non in sequenza!");
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizia


    /**
     * controlla che le date fornite siano valide e in sequenza (anche stesso giorno)
     * se non lo sono ritorna false
     * <p/>
     *
     * @return true se le date sono valide e in sequenza
     */
    private boolean isDateValide() {
        /* variabili e costanti locali di lavoro */
        boolean continua = true;

        try {    // prova ad eseguire il codice

            if (!Lib.Data.isValida(this.getData1())) {
                continua = false;
            }// fine del blocco if

            if (continua) {
                if (!Lib.Data.isValida(this.getData2())) {
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            if (continua) {
                continua = Lib.Data.isSequenza(this.getData1(), this.getData2());
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return continua;
    }



    /**
     * Ritorna il filtro Precedenti Presenze
     * al giorno precedente il periodo analizzato.
     * <p/>
     *
     * @return il filtro Precedenti Presenze
     */
    public Filtro getFiltroPrecPres(){
        return null;
    }

    /**
     * Ritorna il filtro Precedenti Periodi
     * al giorno precedente il periodo analizzato.
     * <p/>
     *
     * @return il filtro Precedenti Periodi
     */
    public Filtro getFiltroPrecPeriPos(){
        return null;
    }

    /**
     * Ritorna il filtro Precedenti Periodi Negativo
     * per il giorno precedente il periodo analizzato.
     * <p/>
     * Le persone dei periodi selezionati da questo filtro
     * vengono sottratte dal totale
     *
     * @return il filtro Precedenti Periodi Negativo
     */
    public Filtro getFiltroPrecPeriNeg(){
        return null;
    }


    /**
     * Ritorna il filtro Arrivi Presenze
     * durante il periodo analizzato.
     * <p/>
     *
     * @return il filtro Arrivi Presenze
     */
    public Filtro getFiltroArriviPres(){
        return null;
    }

    /**
     * Ritorna il filtro Arrivi Periodi
     * durante il periodo analizzato.
     * <p/>
     *
     * @return il filtro Arrivi Periodi
     */
    public Filtro getFiltroArriviPeri(){
        return null;
    }


    /**
     * Ritorna il filtro Partenze Presenze
     * durante il periodo analizzato.
     * <p/>
     *
     * @return il filtro Partenze Presenze
     */
    public Filtro getFiltroPartenzePres(){
        return null;
    }

    /**
     * Ritorna il filtro Partenze Periodi
     * durante il periodo analizzato.
     * <p/>
     *
     * @return il filtro Partenze Periodi
     */
    public Filtro getFiltroPartenzePeri(){
        return null;
    }


    /**
     * Ritorna il filtro Finale Presenze
     * presenze alla fine del periodo analizzato.
     * <p/>
     *
     * @return il filtro Finale Presenze
     */
    public Filtro getFiltroFinalePres(){
        return PresenzaModulo.getFiltroPresenze(this.getData2());
    }


    /**
     * Ritorna il numero di adulti presenti al giorno
     * precedente il periodo analizzato.
     * <p/>
     *
     * @return adulti al giorno precedente
     */
    public int getNumPrecAd(){
        return this.getNumPrec(false);
    }


    /**
     * Ritorna il numero di bambini presenti al giorno
     * precedente il periodo analizzato.
     * <p/>
     *
     * @return bambini al giorno precedente
     */
    public int getNumPrecBa(){
        return this.getNumPrec(true);
    }


    /**
     * Ritorna il numero di adulti arrivati o in arrivo
     * durante il periodo analizzato.
     * <p/>
     *
     * @return adulti arrivati o in arrivo nel periodo
     */
    public int getNumArriviAd() {
        return this.getNumArrivi(false);
    }


    /**
     * Ritorna il numero di bambini arrivati o in arrivo
     * durante il periodo analizzato.
     * <p/>
     *
     * @return bambini arrivati o in arrivo nel periodo
     */
    public int getNumArriviBa() {
        return this.getNumArrivi(true);
    }


    /**
     * Ritorna il numero di adulti partiti o in partenza
     * durante il periodo analizzato.
     * <p/>
     *
     * @return adulti partiti o in partenza nel periodo
     */
    public int getNumPartenzeAd() {
        return this.getNumPartenze(false);
    }


    /**
     * Ritorna il numero di bambini partiti o in partenza
     * durante il periodo analizzato.
     * <p/>
     *
     * @return bambini partiti o in partenza nel periodo
     */
    public int getNumPartenzeBa() {
        return this.getNumPartenze(true);
    }


    /**
     * Ritorna il numero di adulti presenti alla fine
     * del periodo analizzato.
     * <p/>
     *
     * @return adulti al giorno precedente
     */
    public int getNumFinaleAd() {
        return this.getNumPrecAd() + this.getNumArriviAd() - this.getNumPartenzeAd();
    }


    /**
     * Ritorna il numero di bambini presenti alla fine
     * del periodo analizzato.
     * <p/>
     *
     * @return bambini al giorno precedente
     */
    public int getNumFinaleBa() {
        return this.getNumPrecBa() + this.getNumArriviBa() - this.getNumPartenzeBa();
    }


    /**
     * Ritorna il numero di presenti al giorno precedente
     * <p/>
     * @param bambino true per i bambini false per gli adulti
     * @return il numero di presenti al giorno precedente
     */
    private int getNumPrec(boolean bambino){
        /* variabili e costanti locali di lavoro */
        int quanti=0;
        int quantiPeriPos=0;
        int quantiPeriNeg=0;
        int quantiPres=0;
        Filtro filtro;
        Filtro filtro1;

        try { // prova ad eseguire il codice

            /* valore positivo dai Periodi */
            filtro = this.getFiltroPrecPeriPos();
            if (filtro!=null) {
                quantiPeriPos = this.contaPersone(filtro, bambino);
            }// fine del blocco if

            /* valore negativo dai Periodi */
            filtro = this.getFiltroPrecPeriNeg();
            if (filtro!=null) {
                quantiPeriNeg = this.contaPersone(filtro, bambino);
            }// fine del blocco if

            /* valore dalle Presenze */
            filtro = this.getFiltroPrecPres();
            if (filtro!=null) {
                filtro1 = new Filtro();
                filtro1.add(filtro);
                filtro1.add(this.getFiltroPresenzeAB(bambino));
                quantiPres = this.contaPresenze(filtro1);
            }// fine del blocco if

            /* valore totale */
            quanti = quantiPeriPos-quantiPeriNeg+quantiPres;

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return quanti;

    }


    /**
     * Ritorna il numero di persone arrivate o in arrivo
     * durante il periodo analizzato.
     * <p/>
     * @param bambino true per i bambini false per gli adulti
     * @return il numero di persone arrivate o in arrivo
     */
    private int getNumArrivi(boolean bambino){
        /* variabili e costanti locali di lavoro */
        int quanti=0;
        int quantiPeri=0;
        int quantiPres=0;
        Filtro filtro;

        try { // prova ad eseguire il codice

            /* valore dai Periodi */
            filtro = this.getFiltroArriviPeri();
            if (filtro!=null) {
                quantiPeri = this.contaPersone(filtro, bambino);
            }// fine del blocco if

            /* valore dalle Presenze */
            filtro = this.getFiltroArriviPres();
            if (filtro!=null) {
                filtro.add(this.getFiltroPresenzeAB(bambino));
                quantiPres = this.contaPresenze(filtro);
            }// fine del blocco if

            /* valore totale */
            quanti = quantiPeri+quantiPres;

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return quanti;

    }


    /**
     * Ritorna il numero di persone partite o in partenza
     * durante il periodo analizzato.
     * <p/>
     * @param bambino true per i bambini false per gli adulti
     * @return il numero di persone partite o in partenza
     */
    private int getNumPartenze(boolean bambino){
        /* variabili e costanti locali di lavoro */
        int quanti=0;
        int quantiPeri=0;
        int quantiPres=0;
        Filtro filtro;

        try { // prova ad eseguire il codice

            /* valore dai Periodi */
            filtro = this.getFiltroPartenzePeri();
            if (filtro!=null) {
                quantiPeri = this.contaPersone(filtro, bambino);
            }// fine del blocco if

            /* valore dalle Presenze */
            filtro = this.getFiltroPartenzePres();
            if (filtro!=null) {
                filtro.add(this.getFiltroPresenzeAB(bambino));
                quantiPres = this.contaPresenze(filtro);
            }// fine del blocco if

            /* valore totale */
            quanti = quantiPeri+quantiPres;

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return quanti;

    }


    /**
     * Ritorna il filtro adulti/bambini sulle presenze
     * <p/>
     * @param bambino true per i bambini false per gli adulti
     * @return il filtro adulti/bambini
     */
    protected Filtro getFiltroPresenzeAB(boolean bambino){
        return FiltroFactory.crea(Presenza.Cam.bambino.get(), bambino);
    }


    /**
     * Conta le presenze corrispondenti a un dato filtro.
     * <p/>
     *
     * @param filtro il filtro
     *
     * @return il numero di record delle presenze
     */
    protected int contaPresenze(Filtro filtro) {
        return PresenzaModulo.get().query().contaRecords(filtro);
    }


    /**
     * Conta le persone nei periodi selezionati da un dato filtro.
     * <p/>
     *
     * @param filtro il filtro sui periodi
     * @param bambino true per contare i bambini false per contare gli adulti
     *
     * @return il numero di persone nei periodi
     */
    protected int contaPersone(Filtro filtro, boolean bambino) {
        /* variabili e costanti locali di lavoro */
        int quanti;
        Campi campo;
        Number numero;

        if (bambino) {
            campo = Periodo.Cam.bambini;
        } else {
            campo = Periodo.Cam.adulti;
        }// fine del blocco if-else
        numero = PeriodoModulo.get().query().somma(campo.get(), filtro);
        quanti = Libreria.getInt(numero);

        /* valore di ritorno */
        return quanti;
    }



    /**
     * Ritorna il testo descrittivo per i presenti al giorno precedente
     * <p/>
     *
     * @return il testo
     */
    public String getTestoPrec(){
        return "Presenti al "+Lib.Data.getDataBrevissima(this.getGiornoPrecedente());
    }

    /**
     * Ritorna il testo descrittivo per gli arrivi nel periodo
     * <p/>
     *
     * @return il testo
     */
    public String getTestoArrivi(){
        return "Arrivi nel periodo";
    }

    /**
     * Ritorna il testo descrittivo per le partenze nel periodo
     * <p/>
     *
     * @return il testo
     */
    public String getTestoPartenze(){
        return "Partenze nel periodo";
    }

    /**
     * Ritorna il testo descrittivo per i presenti a fine periodo
     * <p/>
     *
     * @return il testo
     */
    public String getTestoFinale(){
        return "Presenti al "+Lib.Data.getDataBrevissima(this.getData2());
    }


    /**
     * Ritorna true se è sensato utilizzare il filtro presenze precedenti
     * per proiettare l'elenco a video.
     * <p/>
     * In genere è sensato se è l'unico filtro che compone il totale Precedenti
     *
     * @return true se ha senso utilizzare il filtro
     */
    public boolean isSensatoPresPrec(){
        /* variabili e costanti locali di lavoro */
        boolean continua = false;

        try { // prova ad eseguire il codice

            /**
             * deve esistere e deve essere l'unico filtro
             * utilizzato per determinare le presenze precedenti
             */

            continua=(this.getFiltroPrecPres()!=null);

            if (continua) {
                continua=(this.getFiltroPrecPeriNeg()==null);
            }// fine del blocco if

            if (continua) {
                continua=(this.getFiltroPrecPeriPos()==null);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


        /* valore di ritorno */
        return continua;
    }

    /**
     * Ritorna true se è sensato utilizzare il filtro presenze Arrivi
     * per proiettare l'elenco a video.
     * <p/>
     * In genere è sensato se è l'unico filtro che compone il totale Arrivi
     *
     * @return true se ha senso utilizzare il filtro
     */
    public boolean isSensatoPresArrivi(){
        /* variabili e costanti locali di lavoro */
        boolean continua = false;

        try { // prova ad eseguire il codice

            /**
             * deve esistere e deve essere l'unico filtro
             * utilizzato per determinare gli arrivi del periodo
             */

            continua=(this.getFiltroArriviPres()!=null);

            if (continua) {
                continua=(this.getFiltroArriviPeri()==null);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


        /* valore di ritorno */
        return continua;
    }

    /**
     * Ritorna true se è sensato utilizzare il filtro presenze Partenze
     * per proiettare l'elenco a video.
     * <p/>
     * In genere è sensato se è l'unico filtro che compone il totale Partenze
     *
     * @return true se ha senso utilizzare il filtro
     */
    public boolean isSensatoPresPartenze(){
        /* variabili e costanti locali di lavoro */
        boolean continua = false;

        try { // prova ad eseguire il codice

            /**
             * deve esistere e deve essere l'unico filtro
             * utilizzato per determinare le partenze del periodo
             */

            continua=(this.getFiltroPartenzePres()!=null);

            if (continua) {
                continua=(this.getFiltroPartenzePeri()==null);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


        /* valore di ritorno */
        return continua;
    }

    /**
     * Ritorna true se è sensato utilizzare il filtro presenze Finali
     * per proiettare l'elenco a video.
     * <p/>
     *
     * @return true se ha senso utilizzare il filtro
     */
    public boolean isSensatoPresFinali(){
        /* variabili e costanti locali di lavoro */
        boolean continua = false;

        try { // prova ad eseguire il codice

            /**
             * deve esistere e devono essere sensati tuti gli altri
             */

            continua=(this.getFiltroFinalePres()!=null);

            if (continua) {
                continua=(this.isSensatoPresPrec());
            }// fine del blocco if

            if (continua) {
                continua=(this.isSensatoPresArrivi());
            }// fine del blocco if

            if (continua) {
                continua=(this.isSensatoPresPartenze());
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


        /* valore di ritorno */
        return continua;
    }



    /**
     * Ritorna il giorno precedente al periodo analizzato.
     * <p/>
     * @return il giorno precedente
     */
    protected Date getGiornoPrecedente () {
        return Lib.Data.add(this.getData1(), -1);
    }


    protected Date getData1() {
        return data1;
    }


    private void setData1(Date data1) {
        this.data1 = data1;
    }


    protected Date getData2() {
        return data2;
    }


    private void setData2(Date data2) {
        this.data2 = data2;
    }


}// fine della classe
