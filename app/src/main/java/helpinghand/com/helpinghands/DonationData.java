package helpinghand.com.helpinghands;

/**
 * Created by root on 5/8/17.
 */

public class DonationData {



    private String Name;
    private String Requested_Donation;

    public DonationData(){

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getRequested_Donation() {
        return Requested_Donation;
    }

    public void setRequested_Donation(String Requested_Donation) {
        this.Requested_Donation = Requested_Donation;
    }

    @Override
    public String toString() {
        return "Name:"+this.Name + "\n Donation:" + this.Requested_Donation ;
    }
}
