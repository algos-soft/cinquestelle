package it.algos.base.wrapper;

import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;

import java.util.Date;

/**
 * Wrapper di due date.
 * </p>
 *
 * @author Guido Andrea Ceresa ed Alessandro Valbonesi
 * @author alex
 * @version 1.0 / 12-feb-2009 ore 10:04
 */
public final class DueDate {

    /**
     * prima data
     */
    private Date data1;

    /**
     * seconda data
     */
    private Date data2;


    /**
     * Costruttore senza parametri.
     */
    public DueDate() {
        /* rimanda al costruttore della superclasse */
        super();
    }// fine del metodo costruttore


    /**
     * Costruttore con parametri.
     *
     * @param data1 - prima data
     * @param data2 - seconda data
     */
    public DueDate(Date data1, Date data2) {
        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza coi parametri */
        this.setData1(data1);
        this.setData2(data2);

    }// fine del metodo costruttore completo


    /**
     * Verifica se le due date sono in sequenza.
     * <p/>
     * Le due date devono esistere ed essere in sequenza (la seconda >= alla prima).
     *
     * @return true se sono in sequenza
     */
    public boolean isSequenza() {
        /* variabili e costanti locali di lavoro */
        boolean sequenza = false;
        Date d1, d2;

        try { // prova ad eseguire il codice
            d1 = this.getData1();
            d2 = this.getData2();
            if (Lib.Data.isValida(d1) && (Lib.Data.isValida(d2))) {
                sequenza = Lib.Data.isPosterioreUguale(d1, d2);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return sequenza;
    }


    /**
     * Verifica se le due date sono entrambe piene.
     * <p/>
     *
     * @return true se sono entrambe piene
     */
    public boolean isPieno() {
        return (Lib.Data.isValida(this.getData1()) && Lib.Data.isValida(this.getData2()));
    }


    /**
     * Verifica se le due date sono entrambe vuote.
     * <p/>
     *
     * @return true se sono entrambe vuote
     */
    public boolean isVuoto() {
        return (Lib.Data.isVuota(this.getData1()) && Lib.Data.isVuota(this.getData2()));
    }


    /**
     * Inverte le due date.
     * <p/>
     */
    public void inverti() {
        Date d1 = this.getData1();
        Date d2 = this.getData2();
        this.setData2(d1);
        this.setData1(d2);
    }


    /**
     * Ritorna il numero di giorni intercorrenti tra le 2 date.
     * <p/>
     * @return il numero di giorni in valore assoluto
     * (indipendente dall'ordine delle date)
     */
    public int getQuantiGiorni() {
        Date d1 = this.getData1();
        Date d2 = this.getData2();
        int diff = Lib.Data.diff(d2, d1);
        diff = Math.abs(diff);
        return diff;
    }


    public Date getData1() {
        return data1;
    }


    public void setData1(Date data1) {
        this.data1 = data1;
    }


    public Date getData2() {
        return data2;
    }


    public void setData2(Date data2) {
        this.data2 = data2;
    }


    @Override
    public String toString() {
        return Lib.Data.getStringa(this.getData1()) + " - " + Lib.Data.getStringa(this.getData2());
    }
}// fine della classe