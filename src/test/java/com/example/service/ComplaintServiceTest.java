package com.example.service;

import com.example.entity.Complaint;
import com.example.exception.ComplaintNotFoundException;
import com.example.service.impl.ComplaintServiceImpl;

import static org.junit.jupiter.api.Assertions.*;

class ComplaintServiceTest {

    ComplaintService complaintService = new ComplaintServiceImpl();

    @org.junit.jupiter.api.Test
    void addComplaint()
    {
        complaintService.
                addComplaint(new Complaint
                        ("Test Complaint", "This is a test complaint", "OPEN"));
        assertTrue(true);
    }

    @org.junit.jupiter.api.Test
    void updateComplaint() throws ComplaintNotFoundException
    {
            complaintService.updateComplaint(6, "CLOSED");
            assertTrue(true);

    }

    @org.junit.jupiter.api.Test
    void deleteComplaint() throws ComplaintNotFoundException
    {
        complaintService.deleteComplaint(5);
        assertTrue(true);
    }

    @org.junit.jupiter.api.Test
    void complaintFindById() throws ComplaintNotFoundException
    {
        Complaint c = complaintService.ComplaintFindById(3);
        assertNotNull(c);
    }
}