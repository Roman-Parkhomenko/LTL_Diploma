package info.Parkhomenko.personaldiary.data.model;

public class Section {

    private final String name;
    public boolean isExpanded;
    private String text;
    public String getText(){
        return text;
    }
    public void  setText(String text){
        this.text=text;

    }
    public Section(String name) {
        this.name = name;
        isExpanded = false;
    }

    public String getName() {
        return name;
    }
}
