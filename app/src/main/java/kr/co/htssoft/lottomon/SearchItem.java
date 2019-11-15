package kr.co.htssoft.lottomon;

public class SearchItem {

    private String drwNo;
    private String drwNoDate;
    private int drwNo1;
    private int drwNo2;
    private int drwNo3;
    private int drwNo4;
    private int drwNo5;
    private int drwNo6;
    private int drwBNo;
    private String automaticDrw;
    private String manualDrw;
    private String semiAutoMaticDrw;
    private String firstWinner; //당첨자 수
    private String firstAmount;   //1명당 당첨 금액
    private String firstTotalAmount; //당첨 총 금액
    private String secondWinner;
    private String secondAmount;
    private String secondTotalAmount;
    private String thirdWinner;
    private String thirdAmount;
    private String thirdTotalAmount;
    private String fourthWinner;
    private String fourthAmount;
    private String fourthTotalAmount;
    private String fifthWinner;
    private String fifthAmount;
    private String fifthTotalAmount;
    private String totalSellAmount;

    public SearchItem(String drwNo, String drwNoDate, int drwNo1, int drwNo2, int drwNo3, int drwNo4, int drwNo5, int drwNo6, int drwBNo, String automaticDrw, String manualDrw, String semiAutoMaticDrw, String firstWinner, String firstAmount, String firstTotalAmount, String secondWinner, String secondAmount, String secondTotalAmount, String thirdWinner, String thirdAmount, String thirdTotalAmount, String fourthWinner, String fourthAmount, String fourthTotalAmount, String fifthWinner, String fifthAmount, String fifthTotalAmount, String totalSellAmount) {
        this.drwNo = drwNo;
        this.drwNoDate = drwNoDate;
        this.drwNo1 = drwNo1;
        this.drwNo2 = drwNo2;
        this.drwNo3 = drwNo3;
        this.drwNo4 = drwNo4;
        this.drwNo5 = drwNo5;
        this.drwNo6 = drwNo6;
        this.drwBNo = drwBNo;
        this.automaticDrw = automaticDrw;
        this.manualDrw = manualDrw;
        this.semiAutoMaticDrw = semiAutoMaticDrw;
        this.firstWinner = firstWinner;
        this.firstAmount = firstAmount;
        this.firstTotalAmount = firstTotalAmount;
        this.secondWinner = secondWinner;
        this.secondAmount = secondAmount;
        this.secondTotalAmount = secondTotalAmount;
        this.thirdWinner = thirdWinner;
        this.thirdAmount = thirdAmount;
        this.thirdTotalAmount = thirdTotalAmount;
        this.fourthWinner = fourthWinner;
        this.fourthAmount = fourthAmount;
        this.fourthTotalAmount = fourthTotalAmount;
        this.fifthWinner = fifthWinner;
        this.fifthAmount = fifthAmount;
        this.fifthTotalAmount = fifthTotalAmount;
        this.totalSellAmount = totalSellAmount;
    }

    public String getDrwNo() {
        return drwNo;
    }

    public void setDrwNo(String drwNo) {
        this.drwNo = drwNo;
    }

    public String getDrwNoDate() {
        return drwNoDate;
    }

    public void setDrwNoDate(String drwNoDate) {
        this.drwNoDate = drwNoDate;
    }

    public int getDrwNo1() {
        return drwNo1;
    }

    public void setDrwNo1(int drwNo1) {
        this.drwNo1 = drwNo1;
    }

    public int getDrwNo2() {
        return drwNo2;
    }

    public void setDrwNo2(int drwNo2) {
        this.drwNo2 = drwNo2;
    }

    public int getDrwNo3() {
        return drwNo3;
    }

    public void setDrwNo3(int drwNo3) {
        this.drwNo3 = drwNo3;
    }

    public int getDrwNo4() {
        return drwNo4;
    }

    public void setDrwNo4(int drwNo4) {
        this.drwNo4 = drwNo4;
    }

    public int getDrwNo5() {
        return drwNo5;
    }

    public void setDrwNo5(int drwNo5) {
        this.drwNo5 = drwNo5;
    }

    public int getDrwNo6() {
        return drwNo6;
    }

    public void setDrwNo6(int drwNo6) {
        this.drwNo6 = drwNo6;
    }

    public int getDrwBNo() {
        return drwBNo;
    }

    public void setDrwBNo(int drwBNo) {
        this.drwBNo = drwBNo;
    }

    public String getAutomaticDrw() {
        return automaticDrw;
    }

    public void setAutomaticDrw(String automaticDrw) {
        this.automaticDrw = automaticDrw;
    }

    public String getManualDrw() {
        return manualDrw;
    }

    public void setManualDrw(String manualDrw) {
        this.manualDrw = manualDrw;
    }

    public String getSemiAutoMaticDrw() {
        return semiAutoMaticDrw;
    }

    public void setSemiAutoMaticDrw(String semiAutoMaticDrw) {
        this.semiAutoMaticDrw = semiAutoMaticDrw;
    }

    public String getFirstWinner() {
        return firstWinner;
    }

    public void setFirstWinner(String firstWinner) {
        this.firstWinner = firstWinner;
    }

    public String getFirstAmount() {
        return firstAmount;
    }

    public void setFirstAmount(String firstAmount) {
        this.firstAmount = firstAmount;
    }

    public String getFirstTotalAmount() {
        return firstTotalAmount;
    }

    public void setFirstTotalAmount(String firstTotalAmount) {
        this.firstTotalAmount = firstTotalAmount;
    }

    public String getSecondWinner() {
        return secondWinner;
    }

    public void setSecondWinner(String secondWinner) {
        this.secondWinner = secondWinner;
    }

    public String getSecondAmount() {
        return secondAmount;
    }

    public void setSecondAmount(String secondAmount) {
        this.secondAmount = secondAmount;
    }

    public String getSecondTotalAmount() {
        return secondTotalAmount;
    }

    public void setSecondTotalAmount(String secondTotalAmount) {
        this.secondTotalAmount = secondTotalAmount;
    }

    public String getThirdWinner() {
        return thirdWinner;
    }

    public void setThirdWinner(String thirdWinner) {
        this.thirdWinner = thirdWinner;
    }

    public String getThirdAmount() {
        return thirdAmount;
    }

    public void setThirdAmount(String thirdAmount) {
        this.thirdAmount = thirdAmount;
    }

    public String getThirdTotalAmount() {
        return thirdTotalAmount;
    }

    public void setThirdTotalAmount(String thirdTotalAmount) {
        this.thirdTotalAmount = thirdTotalAmount;
    }

    public String getFourthWinner() {
        return fourthWinner;
    }

    public void setFourthWinner(String fourthWinner) {
        this.fourthWinner = fourthWinner;
    }

    public String getFourthAmount() {
        return fourthAmount;
    }

    public void setFourthAmount(String fourthAmount) {
        this.fourthAmount = fourthAmount;
    }

    public String getFourthTotalAmount() {
        return fourthTotalAmount;
    }

    public void setFourthTotalAmount(String fourthTotalAmount) {
        this.fourthTotalAmount = fourthTotalAmount;
    }

    public String getFifthWinner() {
        return fifthWinner;
    }

    public void setFifthWinner(String fifthWinner) {
        this.fifthWinner = fifthWinner;
    }

    public String getFifthAmount() {
        return fifthAmount;
    }

    public void setFifthAmount(String fifthAmount) {
        this.fifthAmount = fifthAmount;
    }

    public String getFifthTotalAmount() {
        return fifthTotalAmount;
    }

    public void setFifthTotalAmount(String fifthTotalAmount) {
        this.fifthTotalAmount = fifthTotalAmount;
    }

    public String getTotalSellAmount() {
        return totalSellAmount;
    }

    public void setTotalSellAmount(String totalSellAmount) {
        this.totalSellAmount = totalSellAmount;
    }
}


