package it.algos.albergo.stampeobbligatorie.istat;

import it.algos.albergo.clientealbergo.ClienteAlbergoModulo;
import it.algos.albergo.stampeobbligatorie.RendererInfo;
import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;

import java.util.Date;

class ISTATRendererCheck extends RendererInfo {

    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param campo di riferimento
     */
    ISTATRendererCheck(Campo campo) {
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
        Modulo moduloInterno;
        int codCliente;
        int codRigaIstat;
        Date data;

        try { // prova ad eseguire il codice

            moduloInterno = this.getCampo().getModulo();
            codCliente = moduloInterno.query().valoreInt(ISTATModuloInterno.Nomi.codcliente.get(), codRiga);
            codRigaIstat = moduloInterno.query().valoreInt(ISTATModuloInterno.Nomi.linkRigaISTAT.get(), codRiga);
            data = ISTATLogica.getDataTesta(codRigaIstat);
            valido = ClienteAlbergoModulo.isValidoISTAT(codCliente, data);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valido;

    }

}