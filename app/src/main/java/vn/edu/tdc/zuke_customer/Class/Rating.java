package vn.edu.tdc.zuke_customer.Class;

public class Rating {
    private int numStar;
    private String userId, productId, commentDescription, idRating;

    public Rating() {
    }

    public Rating(int numStar, String userId, String productId, String commentDescription, String idRating) {
        this.numStar = numStar;
        this.userId = userId;
        this.productId = productId;
        this.commentDescription = commentDescription;
        this.idRating = idRating;
    }

    public int getNumStar() {
        return numStar;
    }

    public String getUserId() {
        return userId;
    }

    public String getProductId() {
        return productId;
    }

    public String getCommentDescription() {
        return commentDescription;
    }

    public String getIdRating() {
        return idRating;
    }
}
