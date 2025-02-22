package lk.ijse.semisterfinal.model;

import lk.ijse.semisterfinal.DB.DbConnetion;
import lk.ijse.semisterfinal.dto.AddEmployeeDTO;
import lk.ijse.semisterfinal.dto.SalaryDTO;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddEmployeeModel {
    public static boolean addEmployee(AddEmployeeDTO dto) throws SQLException {
        Connection connection = DbConnetion.getInstance().getConnection();

        String sql = "INSERT INTO employee VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ptm = connection.prepareStatement(sql);

        ptm.setString(1, dto.getEmployeeId());
        ptm.setString(2, dto.getEmployeeName());
        ptm.setString(3, dto.getEmpAddress());
        ptm.setInt(4, dto.getEmployeePhone());
        ptm.setString(5, dto.getEmpDate());
        ptm.setString(6, dto.getEmpPosition());
        ptm.setString(7, dto.getEmail());
        ptm.setString(8, dto.getGender());
        ptm.setString(9,dto.getEducation());
        ptm.setDouble(10,dto.getBasicSalary());
        ptm.setString(11,dto.getExpiriance());
        ptm.setString(12,dto.getDe());

        return ptm.executeUpdate()>0;

    }

    public static List<AddEmployeeDTO> getAllEmployee() throws SQLException {
        Connection connection = DbConnetion.getInstance().getConnection();

        String sql = "SELECT * FROM employee";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        ArrayList<AddEmployeeDTO> dtoList = new ArrayList<>();

        while (resultSet.next()) {
            dtoList.add(
                    new AddEmployeeDTO(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getInt(4),
                            resultSet.getString(5),
                            resultSet.getString(6),
                            resultSet.getString(7),
                            resultSet.getString(8),
                            resultSet.getString(9),
                            resultSet.getDouble(10),
                            resultSet.getString(11),
                            resultSet.getString(12)
                    )
            );
        }
        return dtoList;
    }

    public static boolean updateEmployee(AddEmployeeDTO dto) throws SQLException {
        Connection connection = DbConnetion.getInstance().getConnection();

        String sql = "UPDATE employee SET employeeName = ?, employee_address = ?, employeePhone = ?, job_start_date = ? , position =?, email =?, gender =?, education =?, basicSalary =?, experiance =?, department =?  WHERE employeeId = ?";

        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,dto.getEmployeeName());
        pstm.setString(2,dto.getEmpAddress());
        pstm.setInt(3,dto.getEmployeePhone());
        pstm.setString(4, dto.getEmpDate());
        pstm.setString(5, dto.getEmpPosition());
        pstm.setString(6, dto.getEmail());
        pstm.setString(7, dto.getGender());
        pstm.setString(8, dto.getEducation());
        pstm.setDouble(9, dto.getBasicSalary());
        pstm.setString(10, dto.getExpiriance());
        pstm.setString(11, dto.getDe());
        pstm.setString(12, dto.getEmployeeId());

        return pstm.executeUpdate() >0;

    }

    public static AddEmployeeDTO searchEmployee(String id) throws SQLException {
        Connection connection = DbConnetion.getInstance().getConnection();

        String sql = "SELECT * FROM employee WHERE employeeId = ? ";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();

        AddEmployeeDTO dto = null;

        if (resultSet.next()){
            String eid = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            int mobile = Integer.parseInt(String.valueOf(resultSet.getInt(4)));
            String date = resultSet.getString(5);
            String email = resultSet.getString(7);
            String position = resultSet.getString(6);
            double bSalary = resultSet.getDouble(10);

            dto = new AddEmployeeDTO(eid,name,address,mobile,date,email,position,bSalary);
        }
        return dto;
    }


    public static boolean deleteEmployee(String id) throws SQLException {
        Connection connection = DbConnetion.getInstance().getConnection();

        String sql = "DELETE FROM employee WHERE employeeId = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, id);

        return pstm.executeUpdate() > 0;
    }

}
