package com.example.service;

import com.example.entity.Complaint;
import com.example.exception.ComplaintNotFoundException;

import java.util.List;

public interface ComplaintService {

    public void addComplaint(Complaint complaint);

    List<Complaint> getAllComplaints();

    void updateComplaint(int id, String status) throws ComplaintNotFoundException;

    void deleteComplaint(int id) throws ComplaintNotFoundException;

    Complaint ComplaintFindById(int id) throws ComplaintNotFoundException;

    void updateStatusUsingProcedure(int id, String status);



}
