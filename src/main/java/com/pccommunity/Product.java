package com.pccommunity;

import java.util.Set;

import javax.persistence.*;

@Entity
public class Product {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long idProduct;
    private String name;
    private String imageSource;
    private String description;
    private int price;
    private String bannerSource1;
    private String bannerSource2;
    private String bannerSource3;
    private int stock;
    private String category;
    private int starsAverage = 0;
    private String highlighted = "";
    @OneToMany(mappedBy = "product")
    private Set<Review> reviews;


    public Product() {
    }
    public Product(Product p){
        this.idProduct = p.idProduct;
        this.name= p.name;
        this.price= p.price;
        this.description=p.description;
        this.category = p.category;
        this.stock = p.stock;
    }

    public Product(long id, String name, String description, int price, String category, int stock) {
        this.idProduct = id;
        this.name=name;
        this.price=price;
        this.description=description;
        this.category = category;
        this.stock = stock;
    }

    public void setCategory(String category){
        this.category = category;
    }
    public void setIdProduct(long idProduct) {
        this.idProduct = idProduct;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
    public void adjustStars(int stars){
        this.starsAverage = stars;
    }
    public void adjustHighlighted(String hi){
        this.highlighted = hi;
    }

    public String getCategoria(){
        return category;
    }

    public long getIdProduct() {
        return idProduct;
    }

    public String getName() {
        return name;
    }

    public String getImageSource() {
        return imageSource;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }


    public int getStock() {
        return stock;
    }
    public int getStarsAverage() {
        return starsAverage;
    }

    public String getBannerSource1() {
        return bannerSource1;
    }

    public void setBannerSource1(String bannerSource1) {
        this.bannerSource1 = bannerSource1;
    }

    public String getBannerSource2() {
        return bannerSource2;
    }

    public void setBannerSource2(String bannerSource2) {
        this.bannerSource2 = bannerSource2;
    }

    public String getBannerSource3() {
        return bannerSource3;
    }

    public void setBannerSource3(String bannerSource3) {
        this.bannerSource3 = bannerSource3;
    }
    public boolean equalsId(Product p){
        return this.idProduct == p.getIdProduct();

    }
    @Override
    public String toString() {
        return "Product{" +
                "idProduct=" + idProduct +
                ", imageSource=" + imageSource + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", categoria='" + category + '\'' +
                ", stock='" + stock + '\'' +
                '}';
    }
    
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        Product producto = (Product) object;
        return idProduct == producto.getIdProduct();
    }
    @Override
    public int hashCode() {
        return java.util.Objects.hash(super.hashCode(), idProduct);
    }
}
