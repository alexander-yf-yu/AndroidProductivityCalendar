package com.example.phase2calendar.logic;

import android.os.Build;
import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.util.ArrayList;
import java.time.LocalDateTime;

public class AlertHandler implements Serializable {

    private ArrayList<Alert> alerts;

    public AlertHandler() {
        this.alerts = new ArrayList<Alert>();
    }

    public ArrayList<Alert> getAlerts() {
        return alerts;
    }

    // takes a src.Triple with (description, name, datetime) format
    public void addAlert(Triple<String, String, LocalDateTime> t) {
        alerts.add(new Alert(t.x, t.y, t.z));
    }

    // takes a list of Triples with (description, name, datetime) format
    public void addAlerts(ArrayList<Triple<String, String, LocalDateTime>> alert_info) {
        for (Triple<String, String, LocalDateTime> t : alert_info) {
            alerts.add(new Alert(t.x, t.y, t.z));
        }
    }

    public ArrayList<Alert> getAllAlerts() {
        return alerts;
    }

    public void deleteAlert(Alert alert) { alerts.remove(alert); }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<Alert> raiseAlerts(LocalDateTime now) {
        ArrayList<Alert> out = new ArrayList<>();
        for (Alert a : alerts) {
            if (now.isAfter(a.getStartTime())) {
                out.add(a);
            }
        }
        alerts.removeAll(out);
        return out;
    }


}