package lk.ijse.semisterfinal.model;

import lk.ijse.semisterfinal.DB.DbConnetion;
import lk.ijse.semisterfinal.dto.AddEmployeeDTO;
import lk.ijse.semisterfinal.dto.CusromerDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerModel {
    public static boolean AddCustomer(CusromerDTO dto) throws SQLException {
        Connection connection = DbConnetion.getInstance().getConnection();


        String sql = "INSERT INTO customer VALUES(?,?,?,?,?)";
        PreparedStatement ptm = connection.prepareStatement(sql);

        ptm.setString(1, dto.getTxtCustId());
        ptm.setString(2, dto.getTxtCustAddress());
        ptm.setString(3, dto.getTxtCustName());
        ptm.setString(4, dto.getTxtCustMobile());
        ptm.setString(5, dto.getTxtCustPayment());

        return ptm.executeUpdate()>0;
    }

    public static boolean deleteCustomer(String id) throws SQLException {
        Connection connection = DbConnetion.getInstance().getConnection();

        String sql = "DELETE FROM customer WHERE custId = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,id);

        return pstm.executeUpdate()>0;
    }

    public static List<CusromerDTO> getAllCustomer() throws SQLException {
        Connection connection = DbConnetion.getInstance().getConnection();

        String sql = "SELECT * FROM customer";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        ArrayList<CusromerDTO> dtoList = new ArrayList<>();

        while(resultSet.next()) {
            dtoList.add(
                    new CusromerDTO(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5)
                    )
            );

        }
        return dtoList;
    }


    public static String searchCustomerName(String id) throws SQLException {
        Connection connection = DbConnetion.getInstance().getConnection();

        String sql = "SELECT custName FROM customer WHERE custId = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();

        String custName = null;

        if (resultSet.next()) {
            custName = resultSet.getString("custName");
        }
        return custName; // Returns null if no matching customer is found
    }



    public static boolean updateCustomer(CusromerDTO dto) throws SQLException {
        Connection connection = DbConnetion.getInstance().getConnection();

        String sql = "UPDATE customer SET custAddress = ?, custName = ?, custMobile = ? , custPayment = ?  WHERE custId = ?";

        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,dto.getTxtCustAddress());
        pstm.setString(2,dto.getTxtCustName());
        pstm.setString(3,dto.getTxtCustMobile());
        pstm.setString(4, dto.getTxtCustPayment());
        pstm.setString(5, dto.getTxtCustId());

        return pstm.executeUpdate() >0;

    }
}
