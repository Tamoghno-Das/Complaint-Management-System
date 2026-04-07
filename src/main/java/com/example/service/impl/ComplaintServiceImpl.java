package com.example.service.impl;

import com.example.config.DBUtil;
import com.example.entity.Complaint;
import com.example.exception.ComplaintNotFoundException;
import com.example.service.ComplaintService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComplaintServiceImpl implements ComplaintService {
    @Override
    public void addComplaint(Complaint complaint) {
      try(Connection con = DBUtil.getConnection())
      {
          String sql = "insert into complaints(NAME,DESCRIPTION,STATUS) values(?,?,?)";
          try (PreparedStatement ps = con.prepareStatement(sql)) {
              ps.setString(1,complaint.getName());
              ps.setString(2,complaint.getDescription());
              ps.setString(3,complaint.getStatus());
              ps.executeUpdate();
          }
          System.out.println("complaint added in DB successfully");

      }
      catch (SQLException e)
      {
          System.out.println("Exception: "+e);
      }
    }

    @Override
    public List<Complaint> getAllComplaints() {
        List<Complaint> complaints = new ArrayList<>();
        try(Connection con = DBUtil.getConnection())
        {
            try (Statement statement = con.createStatement();
                 ResultSet rs = statement.executeQuery("select * from complaints")) {
                while (rs.next())
                {
                    complaints.add(new Complaint
                            (rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getString("status")));
                }
            }

        }
        catch(SQLException e )
        {
            System.out.println("Exception: "+e);
        }
        return complaints;
    }

    @Override
    public void updateComplaint(int id, String status) throws ComplaintNotFoundException
    {
        try(Connection con = DBUtil.getConnection())
        {
            String sql = "update complaints set status = ? where id = ?";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1,status);
                ps.setInt(2,id);
                if (ps.executeUpdate() == 0)
                {
                    throw new ComplaintNotFoundException("Complaint with id "+id+" not found");
                }
            }
            System.out.println("complaint status updated successfully");

        }
        catch (SQLException e)
        {
            System.out.println("Exception: "+e);
        }


    }

    @Override
    public void deleteComplaint(int id) throws ComplaintNotFoundException
    {
        try(Connection con = DBUtil.getConnection())
        {
            String sql = "delete from complaints where id = ?";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1,id);
                if (ps.executeUpdate() == 0)
                {
                    throw new ComplaintNotFoundException("Complaint with id "+id+" not found");
                }
            }
            System.out.println("complaint deleted successfully");

        }
        catch (SQLException e)
        {
            System.out.println("Exception: "+e);
        }

    }

    @Override
    public Complaint ComplaintFindById(int id) throws ComplaintNotFoundException {
      return getAllComplaints().stream()
              .filter(c -> c.getId() == id)
              .findFirst()
              .orElseThrow(() -> new ComplaintNotFoundException("Complaint with id "+id+" not found"));
    }

    @Override
    public void updateStatusUsingProcedure(int id, String status)
    {

        try(Connection con = DBUtil.getConnection())
        {
            try (CallableStatement cs = con.prepareCall("{call update_complaint_status(?,?)}")) {
                cs.setInt(1,id);
                cs.setString(2,status);
                cs.execute();
            }
            System.out.println("complaint status updated successfully using procedure");
        }
        catch (SQLException e)
        {
            System.out.println("Exception: "+e);
        }

    }
}
