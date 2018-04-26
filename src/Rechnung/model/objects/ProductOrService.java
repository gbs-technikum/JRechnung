package Rechnung.model.objects;

public class ProductOrService {

    private String id;
    private String title;
    private double price;
    private String description;

    public ProductOrService(String id, String title, double price, String description) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("");
        sb.append(title).append('\'');
        sb.append(" [").append(String.format("%.2f",price));
        sb.append("] ");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductOrService productOrService = (ProductOrService) o;
        return this.id.equals(productOrService.getId());
    }
}
