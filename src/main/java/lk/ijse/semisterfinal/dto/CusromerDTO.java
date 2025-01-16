package lk.ijse.semisterfinal.dto;


import lombok.*;

@NoArgsConstructor
@ToString
@Getter
@Setter

public class CusromerDTO {
    private String txtCustId;
    private String txtCustAddress;
    private String txtCustName;
    private String txtCustMobile;
    private String txtCustPayment;

    public CusromerDTO(String txtCustId, String txtCustAddress, String txtCustName, String txtCustMobile, String txtCustPayment) {
        this.txtCustId = txtCustId;
        this.txtCustAddress = txtCustAddress;
        this.txtCustName = txtCustName;
        this.txtCustMobile = txtCustMobile;
        this.txtCustPayment = txtCustPayment;
    }

    public CusromerDTO(String custName) {
        this.txtCustName = custName;
    }
}
