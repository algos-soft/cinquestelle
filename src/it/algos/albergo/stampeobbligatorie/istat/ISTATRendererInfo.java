package it.algos.albergo.stampeobbligatorie.istat;

import it.algos.albergo.clientealbergo.ClienteAlbergoModulo;
import it.algos.albergo.stampeobbligatorie.RendererInfo;
import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;

import java.util.ArrayList;
import java.util.Date;

class ISTATRendererInfo extends RendererInfo {

    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param campo di riferimento
     */
    ISTATRendererInfo(Campo campo) {
        super(campo);
    }// fine del metodo costruttore completo




    /**
     * Verifica se il codice record di una riga è valido.
     * <p/>
     * @param codRiga il codice record della riga
     * @return true se è valido
     */
    protected boolean isValido(int codRiga) {
        /* variabili e costanti locali di lavoro */
        boolean valido=false;
        int[] codArrivati;
        int[] codPartiti;
        int cod;
        Date data;

        try { // prova ad eseguire il codice

            ArrayList<Integer> listaCod = new ArrayList<Integer>();
            codArrivati = ISTATLogica.getCodArrivati(codRiga);
            for (int k = 0; k < codArrivati.length; k++) {
                cod = codArrivati[k];
                listaCod.add(cod);
            } // fine del ciclo for
            codPartiti = ISTATLogica.getCodPartiti(codRiga);
            for (int k = 0; k < codPartiti.length; k++) {
                cod = codPartiti[k];
                listaCod.add(cod);
            } // fine del ciclo for

            data = ISTATLogica.getDataTesta(codRiga);
            for(int codice:listaCod){
                valido = ClienteAlbergoModulo.isValidoISTAT(codice, data);
                if (!valido) {
                    break;
                }// fine del blocco if
            }


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valido;

    }

}