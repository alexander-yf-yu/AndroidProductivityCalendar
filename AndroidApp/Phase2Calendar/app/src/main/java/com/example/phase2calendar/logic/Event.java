package com.example.phase2calendar.logic;

import android.os.Build;
import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.io.Serializable;

public class Event implements Serializable, Listable {
    private String name;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private ArrayList<Attachment> attachments;
    private Memo memo;
    private AlertHandler alertHandler;

    public Event(String name, String description, LocalDateTime startTime, LocalDateTime endTime) {
        this.name = name;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.attachments = new ArrayList<Attachment>();
        this.alertHandler = new AlertHandler();
        this.memo = null;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setName(String name) { this.name = name; }

    public void setDescription(String description) { this.description = description; }

    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }

    public void addAttachment(Attachment att){
        attachments.add(att);
    }

    public void removeAttachment(Attachment att){
        attachments.remove(att);
    }

    public ArrayList<Attachment> getAttachments() {
        return attachments;
    }

    public Memo getMemo() {
        return memo;
    }

    public void setMemo(Memo newMemo){
        memo = newMemo;
    }

    public void addAlert(Triple<String, String, LocalDateTime> t) {
        alertHandler.addAlert(t);
    }

    public void addAlerts(ArrayList<Triple<String, String, LocalDateTime>> alert_info) {
        alertHandler.addAlerts(alert_info);
    }

    public ArrayList<Alert> getAllAlerts() {
        return this.alertHandler.getAllAlerts();
    }

    public void editAlert(Alert alert, String name, String description, LocalDateTime startTime){
        alert = alertHandler.getAlerts().get(alertHandler.getAlerts().indexOf(alert));
        alert.setName(name);
        alert.setDescription(description);
        alert.setStartTime(startTime);
    }

    public void deleteAlert(Alert alert) {
        alertHandler.deleteAlert(alert);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<Alert> raiseAlerts(LocalDateTime now) {
        return alertHandler.raiseAlerts(now);
    }
}
