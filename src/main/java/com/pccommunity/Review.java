package com.pccommunity;


public class Review {
    private long idReview;
    private int stars; //Las estrellas
    private String message;
    private String clientName;
    private Customer client = new Customer();
    private Product product = new Product();

    public Review() {
    }

    public long getIdReview() {
        return idReview;
    }

    public void setIdReview(long idReview) {
        this.idReview = idReview;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getMessage() {
        return message;
    }

    public String getProductName(){
        return product.getName();
    }
    public long getProductId(){
        return product.getidProduct();
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void assingClient(Customer c1){
        this.client = c1;
        this.clientName = c1.getName();
    }

    public Customer takeClient(){
        return this.client;
    }
    public void assingProduct(Product c1){
        this.product = c1;
    }

    public Product takeProduct(){
        return this.product;
    }

    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        Review review = (Review) object;
        return idReview == review.idReview;
    }

    public int hashCode() {
        return java.util.Objects.hash(super.hashCode(), idReview);
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Review{" +
                "idReview=" + idReview +
                ", stars='" + stars + '\'' +
                ", message='" + message + '\'' +
                ", clientId='" + client + '\'' +
                ", productId='" + product + '\'' +
                '}';
    }
}
