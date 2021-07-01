package it.algos.gestione.indirizzo.tabelle.regione;
/**
 * Copyright: Copyright (c) 2007
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      13-apr-2007
 */

import it.algos.base.dialogo.DialogoImport;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.modulo.Modulo;
import it.algos.base.wrapper.CampoValore;
import it.algos.gestione.indirizzo.tabelle.nazione.Nazione;
import it.algos.gestione.indirizzo.tabelle.nazione.NazioneModulo;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * //@TODO DESCRIZIONE SINTETICA DELLA CLASSE .
 * </p>
 * Questa classe: <ul>
 * <li> </li>
 * <li> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 13-apr-2007 ore 19.59.27
 */
public final class RegioneImport extends DialogoImport {

    private LinkedHashMap siglaCod;


    /**
     * Costruttore completo .
     *
     * @param modulo di riferimento
     * @param mostraDialogo flag per usare o meno il dialogo
     */
    public RegioneImport(Modulo modulo, boolean mostraDialogo) {
        /* rimanda al costruttore della superclasse */
        super(modulo);

        /* regola le variabili di istanza coi parametri */
        this.setMostraDialogo(mostraDialogo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        this.setSiglaCod(new LinkedHashMap());

        if (this.isMostraDialogo()) {
            this.creaDialogo();
        } else {
            super.creaCondizionato();
        }// fine del blocco if-else
    }// fine del metodo inizia


    /**
     * Creazione dialogo.
     * <p/>
     * Metodo invocato dal bottone specifico nella toolbar della lista <br>
     */
    private void creaDialogo() {
        try { // prova ad eseguire il codice

            super.creaDialogo("Suddivisioni nazionali di primo livello");

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Crea tutti i records.
     * <p/>
     * Metodo invocato dal ciclo inizia <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     * <p/>
     * Recupera i dati dalla pagina Wikipedia:Data/Divisioni nazionali di 1° livello <br>
     */
    @Override
    protected void creaRecords() {
        /* variabili e costanti locali di lavoro */

        try { // prova ad eseguire il codice
            this.creaRegioni();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Crea tutti i records.
     * <p/>
     * Metodo invocato dal ciclo inizia <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     * <p/>
     * Recupera i dati da una pagina di wikipedia <br>
     * I dati bengono passati con un wrapper <br>
     */
    private void creaRegioni() {
        /* variabili e costanti locali di lavoro */
        boolean continua = false;
        ArrayList<Reg> lista;

        try { // prova ad eseguire il codice
            /* recupera la lista delle regioni in ordine alfabetico di codice */
            lista = this.getRegioni();

            /* controllo specifico di congruità */
            if (lista != null) {
                if (lista.size() > 0) {
                    continua = true;
                }// fine del blocco if
            }// fine del blocco if

            if (continua) {
                /* traverso tutta la collezione */
                for (Reg reg : lista) {
                    this.creaRecord(reg);
                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Recupera la lista delle regioni.
     * <p/>
     * Metodo invocato dal ciclo inizia <br>
     * Recupera i dati dalla pagina Wikipedia:Data/Divisioni nazionali di 1° livello <br>
     * Per ogni riga crea un wrapper di dati <br>
     */
    private ArrayList<Reg> getRegioni() {
        /* variabili e costanti locali di lavoro */
        ArrayList<Reg> lista = null;
        boolean continua;
        String contenuto;
        String testo = "";
        String titolo = "Wikipedia:Data/Divisioni nazionali di 1° livello";
        String tagRiga = "\n";
        String aCapo = "\n";
        String[] righe = null;
        Reg reg;
        int pos;

        try { // prova ad eseguire il codice
            contenuto = Lib.Wiki.getPagina(titolo);
            continua = (Lib.Testo.isValida(contenuto));

            if (continua) {
                testo = contenuto;
                pos = testo.indexOf(TAG);
                if (pos != -1) {
                    pos += TAG.length();
                    testo = testo.substring(pos);
                }// fine del blocco if
                continua = (Lib.Testo.isValida(testo));
            }// fine del blocco if

            if (continua) {
                testo = testo.substring(testo.indexOf(aCapo) + aCapo.length());
                righe = testo.split(tagRiga);
                continua = (righe != null && righe.length > 0);
            }// fine del blocco if

            if (continua) {
                lista = new ArrayList<Reg>();
                /* traverso tutta la collezione */
                for (String riga : righe) {
                    reg = this.getRigaReg(riga);
                    if (reg != null && Lib.Testo.isValida(reg.getNome())) {
                        lista.add(reg);
                    }// fine del blocco if
                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }


    /**
     * Elabora la riga.
     * <p/>
     * Per ogni riga crea un wrapper di dati <br>
     */
    private Reg getRigaReg(String rigaIn) {
        /* variabili e costanti locali di lavoro */
        Reg rigaOut = null;
        boolean continua;
        String tagAst = "*";
        String[] parti = null;
        String tagTab = "\t";

        try { // prova ad eseguire il codice
            continua = (Lib.Testo.isValida(rigaIn) && rigaIn.startsWith(tagAst));

            if (continua) {
                if (!rigaIn.startsWith(tagAst + tagAst)) {
                    this.regolaCodNaz(rigaIn);
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if

            if (continua) {
                rigaIn = rigaIn.substring(2);
                parti = rigaIn.split(tagTab);
                continua = (parti != null && parti.length >= 3);
            }// fine del blocco if

            if (continua) {
                rigaOut = new Reg();
                rigaOut.setSiglaNaz(parti[0].trim());
                rigaOut.setSigla(parti[1].trim());
                rigaOut.setNome(parti[2].trim());
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return rigaOut;
    }


    /**
     * Regola il codice della nazione.
     * <p/>
     */
    private void regolaCodNaz(String rigaIn) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        String tagAst = "*";
        int pos = 0;
        String tagIni = "(";
        String tagEnd = ")";
        String nazione = "";
        String siglaNaz;
        String regione = "";
        Modulo mod;
        int cod;

        try { // prova ad eseguire il codice
            continua = (Lib.Testo.isValida(rigaIn) && rigaIn.startsWith(tagAst));

            if (continua) {
                rigaIn = rigaIn.substring(1);
                continua = Lib.Testo.isValida(rigaIn);
            }// fine del blocco if

            if (continua) {
                pos = rigaIn.indexOf(tagIni);
                continua = (pos != -1);
            }// fine del blocco if

            if (continua) {
                nazione = rigaIn.substring(0, pos);
                nazione = nazione.trim();
                regione = Lib.Testo.estrae(rigaIn, tagIni, tagEnd);
                regione = Lib.Testo.primaMaiuscola(regione);
            }// fine del blocco if

            if (continua) {
                mod = NazioneModulo.get();
                cod = mod.query().valoreChiave(Nazione.Cam.nazione.get(), nazione);
                if (cod != 0) {
                    siglaNaz = mod.query().valoreStringa(Nazione.Cam.sigla2.get(), cod);
                    this.getSiglaCod().put(siglaNaz, cod);
                    mod.query().registraRecordValore(cod, Nazione.Cam.divisioniUno.get(), regione);
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Crea un singolo record.
     * <p/>
     * Metodo invocato dal ciclo inizia <br>
     */
    private void creaRecord(Reg reg) {
        /* variabili e costanti locali di lavoro */
        Modulo mod;
        ArrayList<CampoValore> campi;
        CampoValore campoNaz;
        CampoValore campoDesc;
        CampoValore campoSigla;
        LinkedHashMap siglaCod;
        String siglaNaz;
        int cod = 0;
        Object ogg;

        try { // prova ad eseguire il codice
            siglaCod = this.getSiglaCod();
            siglaNaz = reg.getSiglaNaz();
            if (Lib.Testo.isValida(siglaNaz)) {
                ogg = siglaCod.get(siglaNaz);
                if (ogg != null) {
                    cod = (Integer)ogg;
                }// fine del blocco if
            }// fine del blocco if

            mod = this.getModulo();
            campoNaz = new CampoValore(Regione.Cam.linkNazione.get(), cod);
            campoDesc = new CampoValore(Regione.Cam.regione.get(), reg.getNome());
            campoSigla = new CampoValore(Regione.Cam.sigla.get(), reg.getSigla());

            campi = new ArrayList<CampoValore>();
            campi.add(campoDesc);
            campi.add(campoSigla);
            campi.add(campoNaz);

            super.creaRecord(mod, campi, Regione.Cam.regione.get(), reg.getNome());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    private LinkedHashMap getSiglaCod() {
        return siglaCod;
    }


    private void setSiglaCod(LinkedHashMap siglaCod) {
        this.siglaCod = siglaCod;
    }


    /**
     * Classe 'interna'.
     * </p>
     */
    public final class Reg {

        private String nome;

        private String sigla;

        private String siglaNaz;


        private String getNome() {
            return nome;
        }


        private void setNome(String nome) {
            this.nome = nome;
        }


        private String getSigla() {
            return sigla;
        }


        private void setSigla(String sigla) {
            this.sigla = sigla;
        }


        private String getSiglaNaz() {
            return siglaNaz;
        }


        private void setSiglaNaz(String siglaNaz) {
            this.siglaNaz = siglaNaz;
        }


    } // fine della classe 'interna'

}// fine della classe
