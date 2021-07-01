/*
 * FiltroUscitaFactory.java
 *
 * Created on 18 dicembre 2003, 17.23
 */

package it.algos.base.filtro;

import java.text.Format;
import java.text.SimpleDateFormat;

/**
 * @author albi
 */
public class FiltroUscitaFactory {

    /**
     * Creates a new instance of FiltroUscitaFactory
     */
    private FiltroUscitaFactory() {
    }


    /**
     * crea un filtroOld selezionando il formato di lettura/scrittura.
     *
     * @param unFormato il formato di lettura/scrittura
     *
     * @return un FiltroUscita pronto da usare
     */
    public static FiltroUscita crea(Format unFormato) {
        FiltroUscita fu = new FiltroUscita();
        fu.setFormato(unFormato);
        return fu;
    }


    /**
     * crea un filtroOld selezionando i formati di lettura e scrittura.
     *
     * @param unFormato formato di lettura
     * @param unFormatoScrittura formato di scrittura
     *
     * @return il filtroOld
     */
    public static FiltroUscita crea(Format unFormato, Format unFormatoScrittura) {
        FiltroUscita fu = new FiltroUscita();
        fu.setFormato(unFormato);
        fu.setFormatoScrittura(unFormatoScrittura);
        return fu;
    }


    /**
     * crea un bel filtroOld per le date. Il filtroOld accetta come date stringhe
     * nel formato dd-MM-yyyy (tipo "18-12-2003") e le ristampa nel formato
     * EEEE dd MMM yyyy (tipo "gioved&igrave; 18 dic 2003").
     */
    public static FiltroUscita creaData() {
        return crea(new SimpleDateFormat("dd-MM-yyyy"), new SimpleDateFormat("EEEE dd MMM yyyy"));
    }

}
