package com.example.musicmanpop.aphasiatalkhelper;

/**
 * Created by porpla on 28/1/2559.
 */
public class Provider {
    private int USER_ID;
    private String Pfirstname, Plastname, Pnickname, Page, Cfirstname, Clastname, Ctel, Hname, Hphysician, HN;

    private int KEY_ID_SHOW_SENTENCE;
    private String Showsentence,ShowImg;
    private byte[] ImageByteArray;

    public Provider(String Pfirstname,String  Plastname,String Pnickname,String Page, String Cfirstname,
                    String Clastname,String Ctel,String Hname,String Hphysician,String HN){
        this.KEY_ID_SHOW_SENTENCE = KEY_ID_SHOW_SENTENCE;
        this.Pfirstname = Pfirstname;
        this.Plastname = Plastname;
        this.Pnickname = Pnickname;
        this.Page = Page;
        this.Cfirstname = Cfirstname;
        this.Clastname = Clastname;
        this.Ctel = Ctel;
        this.Hname = Hname;
        this.Hphysician = Hphysician;
        this.HN = HN;
    }

    public Provider( String Showsentece){

        this.Showsentence = Showsentece;
    }

    public int getKEY_ID_SHOW_SENTENCE(){
        return KEY_ID_SHOW_SENTENCE;
    }

    public void setKEY_ID_SHOW_SENTENCE(int KEY_ID_SHOW_SENTENCE){
        this.KEY_ID_SHOW_SENTENCE = KEY_ID_SHOW_SENTENCE;
    }

    public String getShowsentence(){
        return Showsentence;
    }

    public void setShowImg(String showImg){
        this.ShowImg = showImg;
    }

    public byte[] getImageByteArray(){
        return getImageByteArray();
    }

    public void setImageByteArray(byte[] imageByteArray){
        this.ImageByteArray = ImageByteArray;
    }


    public int getUSER_ID(){
        return USER_ID;
    }

    public void setUSER_ID(int USER_ID){
        this.USER_ID = USER_ID;
    }



    public String getPfirstname(){
        return Pfirstname;
    }

    public void setPfirstname(String Pfirstname){
        this.Pfirstname = Pfirstname;
    }

    public String getPlastname(){
        return Plastname;
    }

    public void setPlastname(String Plastname){
        this.Plastname = Plastname;
    }

    public String getPnickname(){
        return Pnickname;
    }

    public void setPnickname(String Pnickname){
        this.Pnickname = Pnickname;
    }

    public String getPage(){
        return Page;
    }

    public void setPage(String Page){
        this.Page = Page;
    }

    public String getCfirstname(){
        return Cfirstname;
    }

    public void SetCfirstname(String Cfirstname){
        this.Cfirstname = Cfirstname;
    }

    public String getClastname(){
        return Clastname;
    }

    public void setClastname(String Clastname){
        this.Clastname = Clastname;
    }

    public String getCtel(){
        return Ctel;
    }

    public void setCtel(String Ctel){
        this.Ctel = Ctel;
    }

    public String getHname(){
        return Hname;
    }

    public void setHname(String Hname){
        this.Hname = Hname;
    }

    public String getHphysician(){
        return Hphysician;
    }

    public void setHphysician(String Hphysician){
        this.Hphysician = Hphysician;
    }

    public String getHN(){
        return HN;
    }

    public void setHNe(String HN){
        this.HN = HN;
    }
}
