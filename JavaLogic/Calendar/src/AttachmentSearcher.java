package src;

import java.util.ArrayList;

public class AttachmentSearcher implements EventSearcher<String> {
    //Searches a list of event and return those that are associated with the memo.
    @Override
    public ArrayList<Event> search(ArrayList<Event> events, String desc) {
        ArrayList<Event> lst = new ArrayList<Event>();
        for (Event event : events) {
            for(Attachment att:event.getAttachments()){
                if(att.getDescription().equals(desc)){
                    lst.add(event);
                }
            }
        }
        return lst;

    }

    public ArrayList<Event> searchByMemo(ArrayList<Event> events, Memo memo){
        ArrayList<Event> lst = new ArrayList<Event>();
        for(Event event:events){
            if(event.getMemo() == memo){
                lst.add(event);
            }
        }
        return lst;
    }
}
