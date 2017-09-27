package com.company;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/* WORKORDER JAVABEAN */
public class WorkOrder {
    private static int STATIC_ID;
    private int id;
    private String description;
    private String senderName;
    private Status status;

    public WorkOrder() {
    }

    public WorkOrder(String description, String senderName) {
        // set an id for the current work order.
        this.id = STATIC_ID++;

        // set the STATUS TO INITIAL
        this.status = Status.INITIAL;

        this.description = description;
        this.senderName = senderName;
    }

    public void persist() {
        ObjectMapper mapper = new ObjectMapper();

        try {
            // convert our object to json..
            String objString = mapper.writeValueAsString(this);

            // save object json string to disk.
            File f = new File(this.id + ".json");
            FileWriter fw = new FileWriter(f);

            // write this object, as json,
            // to the file using our file writer.
            fw.append(objString);

            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "WorkOrder{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", senderName='" + senderName + '\'' +
                ", status=" + status +
                '}';
    }
}
