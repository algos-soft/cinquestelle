package it.algos.albergo.stampeobbligatorie.notifica;

import it.algos.albergo.clientealbergo.ClienteAlbergoModulo;
import it.algos.albergo.stampeobbligatorie.RendererInfo;
import it.algos.albergo.stampeobbligatorie.testastampe.TestaStampe;
import it.algos.albergo.stampeobbligatorie.testastampe.TestaStampeModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;

import java.util.Date;

class NotificaRendererInfoMultiplo extends RendererInfo {



    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param campo di riferimento
     */
    NotificaRendererInfoMultiplo(Campo campo) {
        /* rimanda al costruttore della superclasse */
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
        boolean valido=true;
        int[] codTutti;
        int codCapo;
        int cod;
        Modulo moduloNotifica;
        int codTesta;
        Modulo moduloTesta;
        Date dataNotifica;

        try { // prova ad eseguire il codice

            moduloNotifica = NotificaModulo.get();
            codTesta = moduloNotifica.query().valoreInt(Notifica.Cam.linkTesta.get(), codRiga);

            moduloTesta = TestaStampeModulo.get();
            dataNotifica= moduloTesta.query().valoreData(TestaStampe.Cam.data.get(), codTesta);
            
            codTutti = NotificaLogica.getCodMembri(codRiga);
            codCapo = NotificaLogica.getCodCapoGruppo(codRiga);

            for (int k = 0; k < codTutti.length; k++) {
                cod = codTutti[k];
                if (cod==codCapo) {
                    if (!ClienteAlbergoModulo.isValidoNotifica(cod, true, dataNotifica)) {
                        valido = false;
                        break;
                    }// fine del blocco if
                } else {
                    if (!ClienteAlbergoModulo.isValidoNotifica(cod, false, dataNotifica)) {
                        valido = false;
                        break;
                    }// fine del blocco if
                }// fine del blocco if-else
            } // fine del ciclo for

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valido;

    }




}
