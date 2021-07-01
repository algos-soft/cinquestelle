package it.algos.albergo.stampeobbligatorie.notifica;

import it.algos.albergo.clientealbergo.ClienteAlbergoModulo;
import it.algos.albergo.stampeobbligatorie.RendererInfo;
import it.algos.albergo.stampeobbligatorie.testastampe.TestaStampe;
import it.algos.albergo.stampeobbligatorie.testastampe.TestaStampeModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;

import java.util.Date;

class NotificaRendererInfoSingolo extends RendererInfo {

    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param campo di riferimento
     */
    NotificaRendererInfoSingolo(Campo campo) {
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
        Modulo moduloNotifica;
        Modulo moduloTesta;
        int codCliente;
        boolean capo;
        Date dataNotifica;
        int codNotifica;
        int codTesta;

        try { // prova ad eseguire il codice

            moduloInterno = this.getCampo().getModulo();
            codCliente = moduloInterno.query().valoreInt(NotificaModuloInterno.Nomi.codcliente.get(), codRiga);
            capo = moduloInterno.query().valoreBool(moduloInterno.getCampoPreferito(), codRiga);
            codNotifica = moduloInterno.query().valoreInt(NotificaModuloInterno.Nomi.linkNotifica.get(), codRiga);

            moduloNotifica = NotificaModulo.get();
            codTesta = moduloNotifica.query().valoreInt(Notifica.Cam.linkTesta.get(), codNotifica);

            moduloTesta = TestaStampeModulo.get();
            dataNotifica= moduloTesta.query().valoreData(TestaStampe.Cam.data.get(), codTesta);

            valido = ClienteAlbergoModulo.isValidoNotifica(codCliente, capo, dataNotifica);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valido;

    }

}