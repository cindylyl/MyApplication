package com.example.cindy_liao.myapplication;




/**
 * Created by cindy_liao on 06/03/2017.
 */

public class Movie{
    private String title,link,gernes,year;

    public Movie() {

    }
//    public Movie(String m_title,String m_link, String m_gernes){
//        this.title=m_title;
//        this.gernes=m_gernes;
//        this.link=m_link;
//    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getGernes() {
        return gernes;
    }

    public void setGernes(String gernes) {
        this.gernes = gernes;
    }

    public String getYear(){
        return year;
    }
    public void setYear(String year){
        this.year=year;
    }

}
