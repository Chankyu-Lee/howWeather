package org.howWeather;

public class CourseData {
    private String themeCategory;
    private long courseId;
    private long tourismId;
    private long regionId;
    private String tourismName;
    private String longitude;
    private String latitude;
    private long courseOrder;
    private long travelTime;
    private String indoorType;
    private String themeName;

    public CourseData(String themeCategory, long courseId, long tourismId, long regionId, String tourismName,
                      String longitude, String latitude, long courseOrder, long travelTime, String indoorType, String themeName) {
        super();
        this.themeCategory = themeCategory;
        this.courseId = courseId;
        this.tourismId = tourismId;
        this.regionId = regionId;
        this.tourismName = tourismName;
        this.longitude = longitude;
        this.latitude = latitude;
        this.courseOrder = courseOrder;
        this.travelTime = travelTime;
        this.indoorType = indoorType;
        this.themeName = themeName;
    }

    public CourseData() {

    }

    public String getThemeCategory() {
        return themeCategory;
    }

    public void setThemeCategory(String themeCategory) {
        this.themeCategory = themeCategory;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public long getTourismId() {
        return tourismId;
    }

    public void setTourismId(long tourismId) {
        this.tourismId = tourismId;
    }

    public long getRegionId() {
        return regionId;
    }

    public void setRegionId(long regionId) {
        this.regionId = regionId;
    }

    public String getTourismName() {
        return tourismName;
    }

    public void setTourismName(String tourismName) {
        this.tourismName = tourismName;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public long getCourseOrder() {
        return courseOrder;
    }

    public void setCourseOrder(long courseOrder) {
        this.courseOrder = courseOrder;
    }

    public long getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(long travelTime) {
        this.travelTime = travelTime;
    }

    public String getIndoorType() {
        return indoorType;
    }

    public void setIndoorType(String indoorType) {
        this.indoorType = indoorType;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    @Override
    public String toString() {
        return "themeCategory = " + themeCategory + "\ncourseId = " + courseId + "\ntourismId = " + tourismId
                + "\nregionId = " + regionId + "\ntourismName = " + tourismName + "\nlongitude = " + longitude
                + "\nlatitude = " + latitude + "\ncourseOrder = " + courseOrder + "\ntravelTime = " + travelTime
                + "\nindoorType = " + indoorType + "\nthemeName = " + themeName;
    }



}

