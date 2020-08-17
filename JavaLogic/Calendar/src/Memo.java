package src;

public class Memo extends Attachment {
    //src.Memo class that stores a memo for a event
    private String name;

    public Memo (String name, String info){
        super(info);
        this.name = name;
    }

    public String getName() {
        return name;
    }


}
