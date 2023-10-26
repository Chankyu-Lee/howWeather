package org.howWeather;

public class CourseWeather {
    private String tm;
    private String thema;
    private String courseId;
    private String courseAreaId;
    private String courseAreaName;
    private String courseName;
    private long spotAreaId;
    private String spotAreaName;
    private String spotName;
    private long th3;
    private long wd;
    private long ws;
    private long sky;
    private long rhm;
    private long pop;

    public CourseWeather(String tm, String thema, String courseId, String courseAreaId, String courseAreaName,
                         String courseName, long spotAreaId, String spotAreaName, String spotName, long th3, long wd, long ws, long sky,
                         long rhm, long pop) {
        super();
        this.tm = tm;
        this.thema = thema;
        this.courseId = courseId;
        this.courseAreaId = courseAreaId;
        this.courseAreaName = courseAreaName;
        this.courseName = courseName;
        this.spotAreaId = spotAreaId;
        this.spotAreaName = spotAreaName;
        this.spotName = spotName;
        this.th3 = th3;
        this.wd = wd;
        this.ws = ws;
        this.sky = sky;
        this.rhm = rhm;
        this.pop = pop;
    }

    public String getTm() {
        return tm;
    }
    public void setTm(String tm) {
        this.tm = tm;
    }
    public String getThema() {
        return thema;
    }
    public void setThema(String thema) {
        this.thema = thema;
    }
    public String getCourseId() {
        return courseId;
    }
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
    public String getCourseAreaId() {
        return courseAreaId;
    }
    public void setCourseAreaId(String courseAreaId) {
        this.courseAreaId = courseAreaId;
    }
    public String getCourseAreaName() {
        return courseAreaName;
    }
    public void setCourseAreaName(String courseAreaName) {
        this.courseAreaName = courseAreaName;
    }
    public String getCourseName() {
        return courseName;
    }
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    public long getSpotAreaId() {
        return spotAreaId;
    }
    public void setSpotAreaId(long spotAreaId) {
        this.spotAreaId = spotAreaId;
    }
    public String getSpotAreaName() {
        return spotAreaName;
    }
    public void setSpotAreaName(String spotAreaName) {
        this.spotAreaName = spotAreaName;
    }
    public String getSpotName() {
        return spotName;
    }
    public void setSpotName(String spotName) {
        this.spotName = spotName;
    }
    public long getTh3() {
        return th3;
    }
    public void setTh3(long th3) {
        this.th3 = th3;
    }
    public long getWd() {
        return wd;
    }
    public void setWd(long wd) {
        this.wd = wd;
    }
    public long getWs() {
        return ws;
    }
    public void setWs(long ws) {
        this.ws = ws;
    }
    public long getSky() {
        return sky;
    }
    public void setSky(long sky) {
        this.sky = sky;
    }
    public long getRhm() {
        return rhm;
    }
    public void setRhm(long rhm) {
        this.rhm = rhm;
    }
    public long getPop() {
        return pop;
    }
    public void setPop(long pop) {
        this.pop = pop;
    }

    @Override
    public String toString() {
        return "tm = " + tm + "\nthema = " + thema + "\ncourseId = " + courseId + "\ncourseAreaId = " + courseAreaId
                + "\ncourseAreaName = " + courseAreaName + "\ncourseName = " + courseName + "\nspotAreaId = "
                + spotAreaId + "\nspotAreaName = " + spotAreaName + "\nspotName = " + spotName + "\nth3 = " + th3
                + "\nwd = " + wd + "\nws = " + ws + "\nsky = " + sky + "\nrhm = " + rhm + "\npop = " + pop;
    }

    public String getSkyConditionString() {
        switch ((int)sky) {
            case 1:
                return "맑음 (Clear)";
            case 2:
                return "구름 조금 (Partly Cloudy)";
            case 3:
                return "구름 많음 (Mostly Cloudy)";
            case 4:
                return "흐림 (Cloudy)";
            case 5:
                return "비 (Rain)";
            case 6:
                return "눈 (Snow)";
            case 7:
                return "비/눈 (Rain/Snow)";
            case 8:
                return "눈발 (Snow Flurries)";
            case 9:
                return "우박 (Hail)";
            case 10:
                return "천둥번개 (Thunderstorm)";
            default:
                return "알 수 없음 (Unknown)";
        }
    }

}

