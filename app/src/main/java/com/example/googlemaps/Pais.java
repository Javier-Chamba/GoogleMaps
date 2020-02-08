package com.example.googlemaps;

public class Pais {
    private String id_pais;
    private String nombres;
    private String codigoISO;

    private String img;

    public Pais(){

    }

    public String getId_pais() {
        return id_pais;
    }

    public void setId_pais(String id_pais) {
        this.id_pais = id_pais;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getCodigoISO() {
        return codigoISO;
    }

    public void setCodigoISO(String codigoISO) {
        this.codigoISO = codigoISO;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img ="http://www.geognos.com/api/en/countries/flag/"+img+".png";
    }
}
