package com.example.jigokushoujo.appdomhogar;

public class ProductItem {

    private int title;
    private int description;
    private int imagen;


    public ProductItem(int title, int description, int imagen) {
        super();
        this.title = title;
        this.description = description;
        this.imagen = imagen;
    }


    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public int getDescription() {
        return description;
    }

    public void setDescription(int description) {
        this.description = description;
    }

    public int getImage() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }


}
