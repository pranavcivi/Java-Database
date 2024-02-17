public class Job {
    private int id;
    private String title;
    private Double wage;

    // Default Constructor
    public Job() { }

    // Parameterized Constructor
    public Job(int id, String title, Double wage) {
        this.id = id;
        this.title = title;
        this.wage = wage;
    }

    // Getter for id
    public int getId() {
        return id;
    }

    // Setter for id
    public void setId(int id) {
        this.id = id;
    }

    // Getter for title
    public String getTitle() {
        return title;
    }

    // Setter for title
    public void setTitle(String title) {
        this.title = title;
    }

    // Getter for wage
    public Double getWage() {
        return wage;
    }

    // Setter for wage
    public void setWage(Double wage) {
        this.wage = wage;
    }
}
