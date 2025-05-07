package nje.hu.quickshop.api_stuff;

public class PersonInfo {
    private String pname; //person name
    private String surname;
    private String pemail; //person email
    private String ppassword; //person password

    void constructor(String pname, String pemail, String ppassword) {
        this.pname = pname;
        this.pemail = pemail;
        this.ppassword = ppassword;
    }
}
