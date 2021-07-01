/**
 * Title:     ContoDialogoEseguiFissi
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      16-giu-2006
 */
package it.algos.gestione.indirizzo.tabelle.citta;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.dialogo.DialogoImport;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.matrice.MatriceDoppia;
import it.algos.base.modulo.Modulo;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.wrapper.CampoValore;
import it.algos.base.wrapper.DieciStringhe;
import it.algos.base.wrapper.FtpWrap;
import it.algos.gestione.indirizzo.tabelle.nazione.Nazione;
import it.algos.gestione.indirizzo.tabelle.provincia.Provincia;
import it.algos.gestione.indirizzo.tabelle.provincia.ProvinciaModulo;

import javax.swing.JSeparator;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Dialogo di importazione dei comuni italiani e di altre nazioni. </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 22-mar-2006 ore 15.00.06
 */
public final class CittaImport extends DialogoImport {


    private LinkedHashMap<String, Integer> mappaPro = null;

    private ArrayList<String> nazioni;


    /**
     * Costruttore completo .
     *
     * @param modulo di riferimento
     * @param mostraDialogo flag per usare o meno il dialogo
     */
    public CittaImport(Modulo modulo, boolean mostraDialogo) {
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
     * Regolazioni statiche di riferimenti e variabili. </p> Metodo invocato direttamente dal
     * costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
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
            this.creaDialogo("Città e località");
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Creazione dialogo.
     * <p/>
     * Metodo invocato dal bottone specifico: nella toolbar della lista o nel menu Archivio <br>
     * Metodo invocato dal ciclo inizia <br>
     *
     * @param mess specifico
     */
    protected void creaDialogo(String mess) {
        /* variabili e costanti locali di lavoro */
        Campo campoScelta;
        int scelta;
        FtpWrap ftp;

        try { // prova ad eseguire il codice
            this.setGapMassimo(0);

            /* costruisce e memorizza la connessione da usare diverse volte */
            this.setFtp(Lib.Web.getFtp());

            /* crea la lista base delle città/località */
            this.creaNazioni();

            ftp = this.getFtp();
            if (ftp != null) {
                Lib.Web.chiudeFtp(ftp);
            }// fine del blocco if

            /* aggiunge una mappa di checkbox per selezionare le nazioni disponibili */
            this.addCheckNazioni();

            /* Regolazione iniziale del dialogo, senza avviarlo */
            campoScelta = super.regolaDialogo(mess);

            super.avvia();

            /* esegue l'operazione */
            if (super.isConfermato()) {
                scelta = (Integer)campoScelta.getValore();
                super.setScelta(Scelta.getScelta(scelta));

                this.leggeSelezioneNazioni();

                super.crea();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * crea la lista base delle nazionalità disponibili.
     * <p/>
     * Le nazioni sono quelle disponibili nella directory geo/terzo livello <br>
     */
    private void creaNazioni() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        FtpWrap ftp;
        String directory = "/algosData/geo/terzo livello/";
//        String comuni;
//        String nome;
//        String tagIni = "(";
//        String tagEnd = ")";
//        ArrayList<DueStringhe> nazioni;
//        DueStringhe naz;
        ArrayList<String> listaFiles;

        try { // prova ad eseguire il codice
            ftp = this.getFtp();

            /* elenco dei files nella directory */
            listaFiles = Lib.Web.getNomiFiles(directory, ftp);
            continua = (listaFiles != null && listaFiles.size() > 0);

//            /* costruisce la lista col nome della nazione ed il titolo specifico della suddivisione */
//            if (continua) {
//                nazioni = new ArrayList<DueStringhe>();
//                for (String titolo : listaFiles) {
//                    nome = Lib.Testo.prima(titolo, tagIni);
//                    nome = Lib.Testo.primaMaiuscola(nome);
//
//                    comuni = Lib.Testo.dopo(titolo, tagIni);
//                    comuni = Lib.Testo.levaCoda(comuni, tagEnd);
//
//                    naz = new DueStringhe(nome, comuni);
//                    nazioni.add(naz);
//                } // fine del ciclo for-each
//            }// fine del blocco if

            if (continua) {
                this.setNazioni(listaFiles);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Creazione di una serie di checkbox con le nazioni disponibili per l'importazione.
     * <p/>
     */
    private void addCheckNazioni() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        String tagIta = "Italia-comuni";
        String nome;
        Campo campo;
        ArrayList<String> nazioni;
        int lar = 250;

        try { // prova ad eseguire il codice
            nazioni = this.getNazioni();
            continua = (nazioni != null && nazioni.size() > 0);

            if (continua) {

                for (String naz : nazioni) {
                    nome = naz;
                    nome = Lib.Testo.primaMaiuscola(nome);

                    campo = CampoFactory.checkBox(nome);
                    campo.setLarScheda(lar);
                    campo.decora().eliminaEtichetta();

                    if (nome.equals(tagIta)) {
                        campo.setValore(true);
                    } else {
                        campo.setValore(false);
                    }// fine del blocco if-else

                    campo.setTestoComponente(nome);
                    this.addCampo(campo);
                } // fine del ciclo for-each

                super.getPrimaPagina().aggiungeComponenti(new JSeparator());
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Controlla quali nazioni sono state selezionate.
     * <p/>
     */
    private void leggeSelezioneNazioni() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        String nome;
        ArrayList<String> nazioniAll;
        ArrayList<String> nazioniTrue = null;
        boolean acceso;

        try { // prova ad eseguire il codice
            nazioniAll = this.getNazioni();
            continua = (nazioniAll != null && nazioniAll.size() > 0);

            if (continua) {
                nazioniTrue = new ArrayList<String>();
                for (String naz : nazioniAll) {
                    nome = naz;
                    acceso = this.getBool(nome);
                    if (acceso) {
                        nazioniTrue.add(naz);
                    }// fine del blocco if
                } // fine del ciclo for-each
            }// fine del blocco if

            if (continua) {
                this.setNazioni(nazioniTrue);
            }// fine del blocco if

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
     */
    @Override
    protected void creaRecords() {
        try { // prova ad eseguire il codice
            this.creaCitta();
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
     * Recupera i dati da una pagina del server di  servizio <br>
     * I dati vengono passati con un wrapper <br>
     */
    private void creaCitta() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        ArrayList<String> nazioni;
        ArrayList<DieciStringhe> lista;
        String paese;
        String tag = "-";
        int codNaz;

        try { // prova ad eseguire il codice
            nazioni = this.getNazioni();
            continua = (nazioni != null && nazioni.size() > 0);

            /* recupera la lista delle province in ordine alfabetico di codice */
            if (continua) {
                for (String naz : nazioni) {
                    /* recupera il nome del paese dal nome del file
                     * (che può contenere i nomi delle regioni od altro) */
                    paese = naz;
                    if (naz.contains(tag)) {
                        paese = Lib.Testo.primaPrima(paese, tag);
                    }// fine del blocco if

                    /* crea una mappa sigla/record delle province */
                    this.creaMappaProvince(paese);

                    /* recupera il codice nazione,
                     * che è lo stesso per tutte le città di questo ciclo */
                    codNaz = this.getCodNaz(paese);

                    /* lista delle città */
                    lista = getListaCitta(naz);

                    if (lista != null) {
                        for (DieciStringhe citta : lista) {
                            this.creaCitta(citta, codNaz);
                        } // fine del ciclo for-each
                    }// fine del blocco if

                } // fine del ciclo for-each
            }// fine del blocco if

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
     * Recupera i dati da una pagina del server di  servizio <br>
     * I dati vengono passati con un wrapper <br>
     *
     * @param naz nome della nazione
     *
     * @return wrapper completo di info (stringhe) sulla città
     */
    private ArrayList<DieciStringhe> getListaCitta(String naz) {
        /* variabili e costanti locali di lavoro */
        ArrayList<DieciStringhe> lista = null;
        boolean continua;
        String tagTitolo = "geo/terzo livello/";
        String titolo;
        String testo;
        String paese;

        try { // prova ad eseguire il codice

            paese = naz;
            titolo = tagTitolo + paese;
            testo = Lib.Web.downLoadData(titolo);
            continua = (Lib.Testo.isValida(testo));

            if (continua) {
                lista = Lib.Web.getCsvDieci(testo);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }


    /**
     * Crea un singolo record.
     * <p/>
     * Metodo invocato dal ciclo inizia <br>
     * <p/>
     * 1-nome
     * 2-nome provincia (o altra suddivisione)
     * 3-cap (o altra suddivisione postale)
     * 4-prefisso telefonico
     * 5-codifica (istat o similari)
     * 6-altitudine 7-abitanti
     * 8-coordinate geografiche (47°40′N 15°26′E)
     * 9-status (comune frazione, città)
     * 10-note (eventualmente nome della regione)
     *
     * @param dieci wrapper completo di info (stringhe) sulla città
     * @param codNaz codice chiave del record della nazione
     */
    private void creaCitta(DieciStringhe dieci, int codNaz) {
        /* variabili e costanti locali di lavoro */
        Modulo modCitta;
        ArrayList<CampoValore> campi;
        CampoValore campoNome;
        CampoValore campoLinkPro;
        CampoValore campoLinkNaz;
        CampoValore campoCap;
        CampoValore campoPref;
        CampoValore campoIstat;
        CampoValore campoAltitudine;
        CampoValore campoAbitanti;
        CampoValore campoCoord;
        CampoValore campoStatus;
        CampoValore campoNote;
        CampoValore campoWiki;
        String nome;
        String nomePro;
        int codPro;
        String cap;
        String prefisso;
        String istat;
        String altitudine;
        String abitanti;
        String coordinate;
        String status;
        String note;
        Filtro filtro;

        try { // prova ad eseguire il codice
            modCitta = this.getModulo();
            nome = dieci.getPrima();
            nomePro = dieci.getSeconda();

            cap = dieci.getTerza();
            prefisso = dieci.getQuarta();
            altitudine = dieci.getQuinta();
            abitanti = dieci.getSesta();
            istat = dieci.getSettima();
            coordinate = dieci.getOttava();
            status = dieci.getNona();
            note = dieci.getDecima();

            /* filtro per la provincia (tiene conto di province con lo stesso nome in nazioni diverse) */
            codPro = this.getCodProv(nomePro);

            campoNome = new CampoValore(Citta.Cam.citta.get(), nome);
            campoLinkPro = new CampoValore(Citta.Cam.linkProvincia.get(), codPro);
            campoLinkNaz = new CampoValore(Citta.Cam.linkNazione.get(), codNaz);
            campoCap = new CampoValore(Citta.Cam.cap.get(), cap);
            campoPref = new CampoValore(Citta.Cam.prefisso.get(), prefisso);
            campoIstat = new CampoValore(Citta.Cam.codice.get(), istat);
            campoAltitudine = new CampoValore(Citta.Cam.altitudine.get(), altitudine);
            campoAbitanti = new CampoValore(Citta.Cam.abitanti.get(), abitanti);
            campoCoord = new CampoValore(Citta.Cam.coordinate.get(), coordinate);
            campoStatus = new CampoValore(Citta.Cam.status.get(), status);
            campoNote = new CampoValore(Citta.Cam.note.get(), note);
            campoWiki = new CampoValore(Citta.Cam.verificato.get(), true);

            campi = new ArrayList<CampoValore>();
            campi.add(campoNome);
            campi.add(campoLinkPro);
            campi.add(campoLinkNaz);
            campi.add(campoCap);
            campi.add(campoPref);
            campi.add(campoIstat);
            campi.add(campoAltitudine);
            campi.add(campoAbitanti);
            campi.add(campoCoord);
            campi.add(campoStatus);
            campi.add(campoNote);
            campi.add(campoWiki);

            /* filtro per modificare i record esistenti (tiene conto degli omonimi */
            filtro = this.getFiltroCitta(dieci);

            super.creaRecord(modCitta, campi, filtro);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Costruisce un filtro città/provincia.
     * <p/>
     * Sono accettabili due città con lo stesso nome <br>
     * Non accetta due città nella stessa provincia <br>
     * <p/>
     * 1-nome
     * 2-nome provincia (o altra suddivisione)
     * 3-cap (o altra suddivisione postale)
     * 4-prefisso telefonico
     * 5-codifica (istat o similari)
     * 6-altitudine 7-abitanti
     * 8-coordinate geografiche (47°40′N 15°26′E)
     * 9-status (comune frazione, città)
     * 10-note (eventualmente nome della regione)
     *
     * @param citta wrapper completo di info (stringhe) sulla città
     *
     * @return filtro per evitare due città della stessa provincia con lo stesso nome
     */
    private Filtro getFiltroCitta(DieciStringhe citta) {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = null;
        boolean continua;
        String nome = "";
        String provincia = "";
        Filtro filtroCitta;
        Filtro filtroProvincia;
        Modulo modPro = null;
        Campo campoNome = null;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (citta != null);

            if (continua) {
                nome = citta.getPrima();
                provincia = citta.getSeconda();
                continua = (Lib.Testo.isValida(nome) && Lib.Testo.isValida(provincia));
            }// fine del blocco if

            if (continua) {
                modPro = ProvinciaModulo.get();
                continua = (modPro != null);
            }// fine del blocco if

            if (continua) {
                campoNome = modPro.getCampo(Provincia.Cam.nomeCorrente.get());
                continua = (campoNome != null);
            }// fine del blocco if

            /* controlla che non esistano due città con lo stesso nome e la stessa provincia */
            if (continua) {
                filtro = new Filtro();
                filtroCitta = FiltroFactory.crea(Citta.Cam.citta.get(), nome);
                filtroProvincia = FiltroFactory.crea(campoNome, provincia);
                filtro.add(filtroCitta);
                filtro.add(filtroProvincia);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return filtro;
    }


    /**
     * Recupera il codice del record dal nome della provincia. </p>
     *
     * @param nome della provincia <br>
     *
     * @return codice chiave della provincia richiesta
     */
    private int getCodProv(String nome) {
        /* variabili e costanti locali di lavoro */
        int codice = 0;
        boolean continua;
        LinkedHashMap<String, Integer> mappaPro = null;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(nome));

            if (continua) {
                mappaPro = this.getMappaPro();
                continua = (mappaPro != null);
            }// fine del blocco if

            if (continua) {
                if (mappaPro.containsKey(nome)) {
                    codice = mappaPro.get(nome);
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codice;
    }


    /**
     * Recupera il codice del record dal nome della nazione. </p>
     *
     * @param paese nome della nazione <br>
     *
     * @return codice chiave della nazione richiesta
     */
    private int getCodNaz(String paese) {
        /* variabili e costanti locali di lavoro */
        int codice = 0;
        boolean continua;
        Modulo modNaz = null;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(paese));

            if (continua) {
                modNaz = Progetto.getModulo(Nazione.NOME_MODULO);
                continua = (modNaz != null);
            }// fine del blocco if

            if (continua) {
                codice = modNaz.query().valoreChiave(Nazione.Cam.nazione.get(), paese);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codice;
    }


    /**
     * Costruisce una mappa nome - codice della provincia.
     * </p>
     * tiene conto di province con lo stesso nome in nazioni diverse <br>
     *
     * @param paese nome della nazione
     */
    private void creaMappaProvince(String paese) {
        /* variabili e costanti locali di lavoro */
        LinkedHashMap<String, Integer> mappaPro = null;
        boolean continua;
        String sigla = "";
        Modulo modNaz = null;
        Modulo modPro = null;
        MatriceDoppia province = null;
        int codice;
        Filtro filtro;
        int codNaz;
        Object ogg;

        try { // prova ad eseguire il codice

            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(paese));

            if (continua) {
                mappaPro = new LinkedHashMap<String, Integer>();
                modNaz = Progetto.getModulo(Nazione.NOME_MODULO);
                modPro = Progetto.getModulo(Provincia.NOME_MODULO);
                continua = (modPro != null && modNaz != null);
            }// fine del blocco if

            if (continua) {
                codNaz = modNaz.query().valoreChiave(Nazione.Cam.nazione.get(), paese);
                filtro = FiltroFactory.crea(Provincia.Cam.linkNazione.get(), codNaz);
                province = modPro.query().valoriDoppi(Provincia.Cam.nomeCorrente.get(), filtro);
                continua = (province != null && province.size() > 0);
            }// fine del blocco if

            if (continua) {
                for (int k = 1; k <= province.size(); k++) {
                    ogg = province.getValoreAt(k);
                    if (ogg instanceof String) {
                        sigla = (String)ogg;
                    }// fine del blocco if

                    codice = province.getCodiceAt(k);
                    mappaPro.put(sigla, codice);
                } // fine del ciclo for
            }// fine del blocco if


            this.setMappaPro(mappaPro);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    private LinkedHashMap<String, Integer> getMappaPro() {
        return mappaPro;
    }


    private void setMappaPro(LinkedHashMap<String, Integer> mappaPro) {
        this.mappaPro = mappaPro;
    }


    private ArrayList<String> getNazioni() {
        return nazioni;
    }


    private void setNazioni(ArrayList<String> nazioni) {
        this.nazioni = nazioni;
    }


}// fine della classe
