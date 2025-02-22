package lk.ijse.semisterfinal.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lk.ijse.semisterfinal.DB.DbConnetion;
import lk.ijse.semisterfinal.Tm.CustomerTm;
import lk.ijse.semisterfinal.Tm.EmployeeTm;
import lk.ijse.semisterfinal.Tm.ItemTm;
import lk.ijse.semisterfinal.dto.CusromerDTO;
import lk.ijse.semisterfinal.model.CustomerModel;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import static java.awt.SystemColor.activeCaption;
import static java.awt.SystemColor.text;

public class AddCustomerController implements Initializable {

    public TableView <CustomerTm> CustomerAddTable;
    public TableColumn <?, ?> tbCid;
    public TableColumn <?, ?> tbCname;
    public TableColumn <?, ?> tbCaddress;
    public TableColumn <?, ?> tbCmobile;
    public TableColumn <?, ?> tbCpayment;
    public TableColumn <?, ?> tbCitemId;
    public TextField serachItem;
    public Label lblTotalCustomer;
    @FXML
    public TextField txtCustMobile;
    @FXML
    public TextField txtCustName;
    @FXML
    public TextField txtCustPayment;
    @FXML
    public TextField txtCustAddress;
//    @FXML
//    public TextField txtCustitemId;
    @FXML
    public TextField txtCustId;



    private void clearField() {
        txtCustId.setText("");
        txtCustName.setText("");
        txtCustAddress.setText("");
        txtCustMobile.setText("");
        txtCustPayment.setText("");
//        txtCustitemId.setText("");

    }


    private void tableListener() {
        CustomerAddTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValued, newValue) -> {
            setData(newValue);

        });
    }

    private void setData(CustomerTm row) {
        txtCustId.setText(row.getId());
        txtCustAddress.setText(row.getAddress());
        txtCustName.setText(row.getName());
        txtCustMobile.setText(row.getTel());
        txtCustPayment.setText(row.getPayment());

    }

    public void initialize() {
        setCellValueFactory();
        loadAllCustomer();
        clearField();
    }

    private void setCellValueFactory() {
        tbCid.setCellValueFactory(new PropertyValueFactory<>("id"));
        tbCaddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        tbCname.setCellValueFactory(new PropertyValueFactory<>("name"));
        tbCmobile.setCellValueFactory(new PropertyValueFactory<>("tel"));
        tbCpayment.setCellValueFactory(new PropertyValueFactory<>("payment"));
//        tbCitemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));

    }


    public void CustomerUpdateOnAction(ActionEvent event) throws IOException {
        String id = txtCustId.getText();
        String address = txtCustAddress.getText();
        String name = txtCustName.getText();
        String contact = txtCustMobile.getText();
//        String item_id = txtCustitemId.getText();
        String  Payment = txtCustPayment.getText();

        try{
            /*if (!validateEmployee()){
                return;
            }*/
            var dto = new CusromerDTO(id,address,name,contact,Payment);
            boolean isUpdate = CustomerModel.updateCustomer(dto);

            if (isUpdate){
                new Alert(Alert.AlertType.CONFIRMATION,"Employee is updated").show();
                loadAllCustomer();
                clearField();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
        
    }

    public void CustomerDeleteOnAction(ActionEvent event) {
        String id = txtCustId.getText();

        try{
            boolean isDelete = CustomerModel.deleteCustomer(id);
            if (isDelete){
                new Alert(Alert.AlertType.CONFIRMATION,"Customer deleted").show();
                loadAllCustomer();
                clearField();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public void CustomerAddOnAction(ActionEvent event) {
        String custId = txtCustId.getText();
        String custAddress = txtCustAddress.getText();
        String custName = txtCustName.getText();
        String custMobile = txtCustMobile.getText();
//        String custItemid = txtCustitemId.getText();
        String custPayment = txtCustPayment.getText();

        try {
            if (!validateCustomer()){
                return;
            }
            var dto = new CusromerDTO(custId,custAddress,custName,custMobile,custPayment);
            boolean isadd= CustomerModel.AddCustomer(dto);
            if (isadd){
                new Alert(Alert.AlertType.CONFIRMATION,"Customer is Added").show();
                loadAllCustomer();
                clearField();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    private void loadAllCustomer() {
        var model = new CustomerModel();

        ObservableList <CustomerTm> obList = FXCollections.observableArrayList();

        try {
            List <CusromerDTO> dtoList = model.getAllCustomer();

            for (CusromerDTO dto : dtoList) {
                obList.add(
                        new CustomerTm(
                                dto.getTxtCustId(),
                                dto.getTxtCustName(),
                                dto.getTxtCustAddress(),
                                dto.getTxtCustMobile(),
                                dto.getTxtCustPayment()
                        )
                );
            }

            CustomerAddTable.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean validateCustomer() {
        boolean isValidate = true;
        boolean address = Pattern.matches("[A-Za-z]{3,}",txtCustAddress.getText());
        if (!address){
            showErrorNotification("Invalid Address", "The Address you entered is invalid");
            isValidate = false;
        }
        boolean mobile = Pattern.matches("^([0-9]{9}|[0-9]{10})$",txtCustMobile.getText());
        if (!mobile){
            showErrorNotification("Invalid Mobile", "The Mobile Number you entered is invalid");
            isValidate = false;

        }
        boolean NIC = Pattern.matches("^([0-9]{9}|[0-9]{12})$",txtCustId.getText());
        if (!NIC){
            showErrorNotification("Invalid NIC", "The NIC Number you entered is invalid");
            isValidate = false;

        }

        return isValidate;
    }

    private void showErrorNotification(String title, String txtt) {
        Notifications.create()
                .title(title)
                .text(String.valueOf(text))
                .showError();
    }

    public void totalCustomer() throws SQLException {
        Connection connection = DbConnetion.getInstance().getConnection();

        String sql = "SELECT COUNT(customer_id) FROM customer";

        String totalcust = null;

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                totalcust = resultSet.getString("COUNT(customer_id)");
            }
            lblTotalCustomer.setText(totalcust);

        } catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadAllCustomer();
            setCellValueFactory();
            clearField();
            itemSerachOnAction();
            tableListener();
            totalCustomer();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void itemSerachOnAction() {
            FilteredList<CustomerTm> filteredData = new FilteredList<>(CustomerAddTable.getItems(), b -> true);
            serachItem.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(item -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String serchKey = newValue.toLowerCase();

                    if (item.getTel().toString().contains(serchKey)) {
                        return true;
                    } else if (item.getId().toLowerCase().contains(serchKey)) {
                        return true;
                    } else return false;
                });
            });
            
            SortedList<CustomerTm> sortedList = new SortedList<>(filteredData);
            sortedList.comparatorProperty().bind(CustomerAddTable.comparatorProperty());
            CustomerAddTable.setItems(sortedList);
    }
}


