package src;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Calendar {

    private User currentUser = null;
    private LocalDateTime currentTime;

    public void run() throws IOException {
        int choice = 0;
        Scanner sc = new Scanner(System.in);
        while (currentUser == null) {
            System.out.println("Select an option: \n 1. Login \n 2. Register \n 3. Exit");
            boolean matches = false;
            while (!matches) {
                String text = sc.nextLine();
                String options = "[123]";

                Pattern pattern = Pattern.compile(options);
                Matcher matcher = pattern.matcher(text);
                matches = matcher.matches();
                if (matches) {
                    choice = Integer.parseInt(text);
                } else {
                    System.out.println("That is not an option, please enter an available option.");
                }
            }


            if (choice == 1) {
                System.out.println("Enter your username:");
                String username = sc.nextLine().split(" ")[0];
                System.out.println("Enter your password:");
                String password = sc.nextLine();
                LoginValidator val = new LoginValidator();
                currentUser = val.validate(username, password);
                if (currentUser == null) {
                    System.out.println("The specified user does not exists, or your credentials are incorrect.");
                } else {
                    System.out.println("Login successful!");
                }

            } else if (choice == 2) {
                System.out.println("Enter your desired username:");
                String username = sc.nextLine();
                if (username.contains(" ")) {
                    System.out.println("Your username cannot contain spaces.");
                }
                else if(username.equals("")){
                    System.out.println("Your username cannot be blank");
                } else {
                    System.out.println("Enter your desired password:");
                    String password = sc.nextLine();
                    UserManager manager = new UserManager();
                    Object[] res = manager.createUser(username, password);
                    if ((boolean) res[1] == false) {
                        System.out.println("This username is already in use.");
                    } else {
                        currentUser = (User) res[0];
                        System.out.println("Account creation successful!");
                    }
                }
            } else {
                sc.close();
                System.exit(0);
            }
        }
        System.out.println("Welcome, " + currentUser.getUsername() + "!");
        currentTime = LocalDateTime.now();
        UserWriter writer = new UserWriter();
        currentUser.addObserver(writer);
        while (true) {
            currentTime = LocalDateTime.now();

            // Displays new alerts to the user
            ArrayList<Alert> currentAlerts = currentUser.raiseAllAlerts(currentTime);
            System.out.println("You have " + currentAlerts.size() + " new alerts:");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
            for(Alert alert:currentAlerts){
                System.out.println(alert.getName() + " | " + alert.getTime().format(formatter));
                System.out.println(alert.getDescription());
                System.out.println("------------------------------------------");
            }

            showMainMenu(sc);
        }
    }

    private void prevScreen(Scanner sc, int prev, int next) throws IOException {
        switch (prev){
            case 1: showMainMenu(sc); break;
            case 2: showEvents(sc, next); break;
            case 3: showSeries(sc, next); break;
            case 4: showMemos(sc, next); break;
            case 5: showCreateEvent(sc, next); break;
            case 6: showCreateMemo(sc, next); break;
            case 7: showCreateSeries(sc, next); break;
        }
    }

    // Displays main menu, screen ID 1
    private void showMainMenu(Scanner sc) throws IOException {
        System.out.println("Dashboard Options: \n 1. Manage Events \n 2. Manage Series \n 3. Manage Memos \n 4. Search for Events \n 5. Exit \n 6. Delete Account and Exit");
        int choice = Integer.parseInt(sc.nextLine());
        currentTime = LocalDateTime.now();
        switch (choice){
            case 1:
                showEvents(sc, 1);
                break;
            case 2:
                showSeries(sc, 1);
                break;
            case 3:
                showMemos(sc, 1);
                break;
            case 4:
                showSearchMenu(sc, 1);
                break;
            case 5:
                System.exit(0);
            case 6:
                System.out.println("Are you sure you want to delete this account? This cannot be undone. \n 1. Yes \n 2. No");
                int choice2 = Integer.parseInt(sc.nextLine());
                if(choice2 == 1) {
                    UserManager u = new UserManager();
                    u.deleteUser(currentUser.getUsername());
                    System.exit(0);
                }
        }

    }
    //Displays events, screen ID 2
    private void showEvents(Scanner sc, int prev) throws IOException {
        ArrayList<Event> events = currentUser.getEvents();
        int choice = listEvents(events, true, sc);
        Event chosenEvent = null;
        if(choice <= events.size()){
            chosenEvent = events.get(choice-1);
            showEventInfo(sc, 2, chosenEvent);
        }
        else if(choice == events.size() + 1){
            showCreateEvent(sc, 2);
        } else {
            prevScreen(sc, prev, 2);
        }
    }
    //Displays series, screen ID 3
    private void showSeries(Scanner sc, int prev) throws IOException {
        ArrayList<Series> series = currentUser.getSeries();
        int i;
        for(i = 1; i <= series.size(); i++){
            System.out.println(i + ": " + series.get(i-1).getName());
        }
        System.out.println((i) + ": Create new Series");
        System.out.println((i+1) + ": Back");
        int choice = Integer.parseInt(sc.nextLine());
        Series chosenSeries = null;
        if(choice <= series.size()){
            chosenSeries = series.get(choice-1);
            showSeriesInfo(sc, 3, chosenSeries);
        }
        else if(choice == series.size() + 1){
            showCreateSeries(sc, 3);
        } else {
            prevScreen(sc, prev, 3);
        }
    }
    //Shows memos, screen ID 4
    private void showMemos(Scanner sc, int prev) throws IOException {
        ArrayList<Memo> memos = currentUser.getMemos();
        int i;
        for(i = 1; i <= memos.size(); i++){
            System.out.println(i + ": " + memos.get(i-1).getName());
        }
        System.out.println((i) + ": Create new Memo");
        System.out.println((i+1) + ": Back");
        int choice = Integer.parseInt(sc.nextLine());
        Memo chosenMemo = null;
        if(choice <= memos.size()){
            chosenMemo = memos.get(choice-1);
            showMemoInfo(sc, 4, chosenMemo);
        }
        else if(choice == memos.size() + 1){
            showCreateMemo(sc, 4);
        } else {
            prevScreen(sc, prev, 4);
        }
    }
    //Shows create event, screen ID 5
    private void showCreateEvent(Scanner sc, int prev) throws IOException {
        System.out.println("Enter event name:");
        String eventName = sc.nextLine();
        System.out.println("Enter event description:");
        String eventDescription = sc.nextLine();

        StringToDateTimeConverter conv = new StringToDateTimeConverter();

        System.out.println("Enter the desired start date and time in yyyy/MM/dd/hh/mm/ss form");
        String td1 = "";
        boolean bad = true;
        while(bad){
            td1 = sc.nextLine();
            if(td1.matches("[0-9][0-9][0-9][0-9]/[0-9][0-9]/[0-9][0-9]/[0-9][0-9]/[0-9][0-9]/[0-9][0-9]")){
                bad = false;
            }
            else{
                System.out.println("The input is formatted incorrectly");
            }
        }
        String[] tokens = td1.split("/");
        LocalDateTime start = conv.secondLowest(tokens);
        bad = true;
        String td2 = "";
        System.out.println("Enter the desired end date and time in yyyy/MM/dd/hh/mm/ss form");

        while(bad){
            td2 = sc.nextLine();
            if(td2.matches("[0-9][0-9][0-9][0-9]/[0-9][0-9]/[0-9][0-9]/[0-9][0-9]/[0-9][0-9]/[0-9][0-9]")){
                bad = false;
            }
            else{
                System.out.println("The input is formatted incorrectly");
            }
        }
        tokens = td2.split("/");
        LocalDateTime end = conv.secondLowest(tokens);

        currentUser.createEvent(eventName, eventDescription, start, end);
        System.out.println("Event successfully created!");
        prevScreen(sc, prev, 1);
    }
    //shows create memo, screen ID 6
    private void showCreateMemo(Scanner sc, int prev) throws IOException {
        System.out.println("Enter memo name:");
        String memoName = sc.nextLine();
        System.out.println("Enter memo description:");
        String memoDescription = sc.nextLine();
        currentUser.createMemo(memoName, memoDescription);
        System.out.println("Memo successfully created!");
        prevScreen(sc, prev, 1);
    }
    //shows create series, screen ID 7
    private void showCreateSeries(Scanner sc, int prev) throws IOException {
        System.out.println("Select a way to create a series: \n 1. Create an empty series \n 2. Create a series with a specific day gap (i.e. daily, every other day) \n 3. Create a series with specific days of the week (i.e. every monday and friday)");
        int choice = Integer.parseInt(sc.nextLine());
        SeriesFactory fact = new SeriesFactory();
        System.out.println("Enter the desired series name:");
        String seriesName = sc.nextLine();
        System.out.println("Enter the desired series description:");
        String seriesDescription = sc.nextLine();
        if(choice == 1){
            currentUser.addSeries(fact.createEmptySeries(seriesName, seriesDescription));
        } else if (choice != 2 && choice != 3) {
            prevScreen(sc, prev, 1);
        } else {
            StringToDateTimeConverter conv = new StringToDateTimeConverter();
            System.out.println("Enter the desired start date and time in yyyy/MM/dd/hh/mm/ss form:");
            String[] tokens = sc.nextLine().split("/");
            LocalDateTime start = conv.secondLowest(tokens);

            System.out.println("Enter the desired end time for each event in hh/mm/ss form:");
            tokens = sc.nextLine().split("/");
            LocalTime end = conv.secondLowestTime(tokens);

            if (choice == 2){
                System.out.println("Enter the number of days between each event:");
                int dayGap = Integer.parseInt(sc.nextLine());
                System.out.println("Enter the number of events:");
                int numEvents = Integer.parseInt(sc.nextLine());
                currentUser.addSeries(fact.createDayGapSeries(seriesName, seriesDescription, start, end, dayGap, numEvents));
            }
            else if (choice == 3){
                System.out.println("Enter the days of the week separated by spaces, where 1=monday and 7=sunday, (i.e. 1 2 5 is monday, tuesday, friday)");
                tokens = sc.nextLine().split(" ");
                ArrayList<DayOfWeek> days = conv.toDayOfWeek(tokens);
                System.out.println("Enter the number of events:");
                int numEvents = Integer.parseInt(sc.nextLine());
                currentUser.addSeries(fact.createSpecificDaySeries(seriesName, seriesDescription, start, end, days, numEvents));
            }
        }
        prevScreen(sc, prev,1);
    }

    //screen ID 8
    private void showMemoInfo(Scanner sc, int prev, Memo memo) throws IOException {
        System.out.println(memo.getName());
        System.out.println("--------------------------");
        System.out.println(memo.getDescription());
        System.out.println("--------------------------");
        System.out.println("Select an option:");
        System.out.println("1. Associate an event with this memo. \n 2. Find associated events to this memo. \n 3. Delete this memo. \n 4. Back");
        int choice = Integer.parseInt(sc.nextLine());
        switch (choice){
            case 1:
                System.out.println("Pick an event to associate with this memo:");
                int choice2 = listEvents(currentUser.getEvents(), false, sc);
                if(choice2 <= currentUser.getEvents().size()) {
                    currentUser.addMemoToEvent(currentUser.getEvents().get(choice2-1), memo);
                    System.out.println("Successfully associated!");
                }
                showMemoInfo(sc, prev, memo);
                break;
            case 2:
                System.out.println("Here is a list of associated events:");
                AttachmentSearcher searcher2 = new AttachmentSearcher();
                int choice3 = listEvents(searcher2.searchByMemo(currentUser.getEvents(), memo), false, sc);
                if(choice3 <= currentUser.getEvents().size()){
                    Event event = currentUser.getEvents().get(choice3-1);
                    showEventInfo(sc, prev, event);
                } else {
                    showMemoInfo(sc, prev, memo);
                }
                break;
            case 3:
                System.out.println("Are you sure you want to delete this memo? 1 for yes, 2 for no.");
                int choiceDelete = Integer.parseInt(sc.nextLine());
                if(choice == 1){
                    currentUser.removeMemo(memo);
                    AttachmentSearcher searcher = new AttachmentSearcher();
                    for(Event ev : searcher.searchByMemo(currentUser.getEvents(), memo)){
                        currentUser.removeMemoFromEvent(ev);
                    }
                    System.out.println("Successfully deleted!");
                    prevScreen(sc, prev, 1);
                } else {showMemoInfo(sc, prev, memo);}
                break;
            default:
                prevScreen(sc, prev, 1);
        }
    }
    //screen ID 9
    private void showSeriesInfo(Scanner sc, int prev, Series series) throws IOException {
        System.out.println(series.getName());
        System.out.println("--------------------------");
        System.out.println(series.getDescription());
        System.out.println("--------------------------");
        System.out.println("Select an option:");
        System.out.println("1. Add an event to this series. \n 2. Find events in this series. \n 3. Delete this series. \n 4. Back");
        int choice = Integer.parseInt(sc.nextLine());
        switch (choice){
            case 1:
                System.out.println("Pick an event to add to this series:");
                int choice2 = listEvents(currentUser.getEvents(), false, sc);
                if(choice2 <= currentUser.getEvents().size()) {
                    currentUser.addEventToSeries(currentUser.getEvents().get(choice2-1), series);
                    System.out.println("Successfully added!");
                }
                showSeriesInfo(sc, prev, series);
                break;
            case 2:
                System.out.println("Here is a list of events in this series:");
                int choice3 = listEvents(series.getEvents(), false, sc);
                if(choice3 <= series.getEvents().size()){
                    Event event = series.getEvents().get(choice3-1);
                    showEventInfo(sc, prev, event);
                } else {
                    showSeriesInfo(sc, prev, series);
                }
                break;
            case 3:
                System.out.println("Are you sure you want to delete this series? 1 for yes, 2 for no.");
                int choiceDelete = Integer.parseInt(sc.nextLine());
                if(choice == 1){
                    currentUser.removeSeries(series);
                    System.out.println("Successfully deleted!");
                    prevScreen(sc, prev, 1);
                } else {showSeriesInfo(sc, prev, series);}
                break;
            default:
                prevScreen(sc, prev, 1);
        }
    }
    //screen ID 10
    private void showEventInfo(Scanner sc, int prev, Event event) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
        System.out.println(event.getName());
        System.out.println("--------------------------");
        System.out.println(event.getDescription());
        System.out.println("--------------------------");
        System.out.println("Start time: " + event.getStartTime().format(formatter));
        System.out.println("End time: " + event.getEndTime().format(formatter));
        System.out.println("--------------------------");
        System.out.println("Select an option:");
        System.out.println("1. View memo. \n 2. View tags. \n 3. Add tag. \n 4. Add individual alert \n 5. Add many alerts \n 6. Back");
        int choice = Integer.parseInt(sc.nextLine());
        switch (choice){
            case 1:
                try {
                    showMemoInfo(sc, 10, event.getMemo());
                } catch (NullPointerException e) {
                    System.out.println("There are no memos");
                }
                break;
            case 2:
                System.out.println("Tags:");
                for (Attachment att:event.getAttachments()){
                    System.out.println(att.getDescription());
                    System.out.println("--------------------------");
                }
                showEventInfo(sc, prev, event);
                break;
            case 3:
                System.out.println("Enter the tag description:");
                String desc = sc.nextLine();
                currentUser.addTagToEvent(event, new Tag(desc));
                System.out.println("Successfully added new tag!");
                showEventInfo(sc, prev, event);
                break;
            case 4:
                System.out.println("Enter the alert name:");
                String name = sc.nextLine();
                System.out.println("Enter the alert description:");
                String des = sc.nextLine();
                StringToDateTimeConverter conv = new StringToDateTimeConverter();

                System.out.println("Enter the desired alert date and time in yyyy/MM/dd/hh/mm/ss form");
                String td1 = "";
                boolean bad = true;
                while(bad){
                    td1 = sc.nextLine();
                    if(td1.matches("[0-9][0-9][0-9][0-9]/[0-9][0-9]/[0-9][0-9]/[0-9][0-9]/[0-9][0-9]/[0-9][0-9]")){
                        bad = false;
                    }
                    else{
                        System.out.println("The input is formatted incorrectly");
                    }
                }
                String[] tokens = td1.split("/");
                LocalDateTime start = conv.secondLowest(tokens);

                currentUser.addAlertToEvent(event, new Triple<String,String, LocalDateTime>(des, name, start));
                System.out.println("Alert successfully added!");
                showEventInfo(sc, prev, event);
                break;
            case 5:
                System.out.println("Enter the alert name:");
                String name2 = sc.nextLine();
                System.out.println("Enter the alert description:");
                String des2 = sc.nextLine();

                StringToDateTimeConverter conv2 = new StringToDateTimeConverter();

                System.out.println("Enter the desired date and time to start alerts in yyyy/MM/dd/hh/mm/ss form");
                String[] tokens2 = sc.nextLine().split("/");
                LocalDateTime start2 = conv2.secondLowest(tokens2);

                System.out.println("Enter the desired frequency of alerts in dd/hh/mm/ss form");
                String[] tokens3 = sc.nextLine().split("/");
                int plusDays = Integer.parseInt(tokens3[0]);
                int plusHours = Integer.parseInt(tokens3[1]);
                int plusMins = Integer.parseInt(tokens3[2]);
                int plusSecs = Integer.parseInt(tokens3[3]);

                ArrayList<Triple<String, String, LocalDateTime>> lst = new ArrayList<>();
                while(start2.isBefore(event.getStartTime())){
                    lst.add(new Triple(name2, des2, start2));
                    start2 = start2.plusDays(plusDays).plusHours(plusHours).plusMinutes(plusMins).plusSeconds(plusSecs);
                }
                currentUser.addAlertsToEvent(event, lst);
                break;
            case 6: prevScreen(sc, prev, 1); break;
            default:showEventInfo(sc, prev, event); break;
        }
    }

    private void showSearchMenu(Scanner sc, int prev) throws IOException {
        System.out.println("How would you like to search for events?");
        System.out.println("1. By event name \n 2. By series name \n 3. By date \n 4. By tag description \n 5. Back");
        int choice = Integer.parseInt(sc.nextLine());
        int choice3 = 0;
        ArrayList<Event> searchedEvents = new ArrayList<>();
        if(choice == 2){
            System.out.println("Enter the series name:");
            String seriesName = sc.nextLine();
            for(Series s:currentUser.getSeries()){
                if(s.getName().equals(seriesName)){
                    searchedEvents.addAll(s.getEvents());
                }
            }
            choice3 = listEvents(searchedEvents, false, sc);
        } else if (choice == 5) {
            prevScreen(sc, prev, 1);
        } else {
            EventSearcherFactory factory = new EventSearcherFactory();
            EventSearcher searcher = factory.getEventSearcher(choice);
            switch (choice){
                case 1:
                    System.out.println("Enter the event name:");
                    String name = sc.nextLine();
                    searchedEvents = searcher.search(currentUser.getEvents(), name);
                    choice3 = listEvents(searchedEvents, false, sc);
                    break;
                case 4:
                    System.out.println("Enter the tag description:");
                    String desc = sc.nextLine();
                    searchedEvents = searcher.search(currentUser.getEvents(), desc);
                    choice3 = listEvents(searchedEvents, false, sc);
                    break;
                case 3:
                    System.out.println("Search by: ");
                    System.out.println("1. Before a certain date");
                    System.out.println("2. After a certain date");
                    System.out.println("3. On a certain date");
                    int choice2 = Integer.parseInt(sc.nextLine());
                    System.out.println("Enter the desired date to search by in form yyyy/MM/dd:");
                    String[] tokens = sc.nextLine().split("/");

                    StringToDateTimeConverter conv = new StringToDateTimeConverter();
                    LocalDateTime searchDate = conv.dateOnly(tokens);

                    switch (choice2){
                        case 1:
                            searchedEvents = ((DateSearcher) searcher).searchBefore(currentUser.getEvents(), searchDate);
                            choice3 = listEvents(searchedEvents, false, sc); break;
                        case 2:
                            searchedEvents = ((DateSearcher) searcher).searchAfter(currentUser.getEvents(), searchDate);
                            choice3 = listEvents(searchedEvents, false, sc); break;
                        case 3:
                            searchedEvents = ((DateSearcher) searcher).search(currentUser.getEvents(), searchDate);
                            choice3 = listEvents(searchedEvents, false, sc); break;
                    }

                    break;
                }
            }

            if(choice3 <= searchedEvents.size()){
                Event event = searchedEvents.get(choice3-1);
                showEventInfo(sc, prev, event);
            } else {
                showSearchMenu(sc, prev);
            }
        }


    private int listEvents(ArrayList<Event> list, boolean createOption, Scanner sc){
        int i;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for(i = 1; i <= list.size(); i++){
            Event curr = list.get(i-1);
            if(curr.getStartTime().isAfter(currentTime)) {
                System.out.println(i + ": " + curr.getName() + " - " + curr.getStartTime().format(formatter));
            }
        }
        if(createOption) {
            System.out.println((i) + ": Create new Event");
            System.out.println((i + 1) + ": Back");
        } else {
            System.out.println((i) + ": Back");
        }
        int choice = Integer.parseInt(sc.nextLine());
        return choice;
    }
}
