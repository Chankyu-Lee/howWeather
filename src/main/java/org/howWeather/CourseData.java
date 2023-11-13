package org.howWeather;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

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
        return "Theme Category: " + themeCategory + "\nCourse ID: " + courseId + "\nTourism ID: " + tourismId
                + "\nRegion ID: " + regionId + "\nTourism Name: " + tourismName + "\nLongitude: " + longitude
                + "\nLatitude: " + latitude + "\nCourse Order: " + courseOrder + "\nTravel Time: " + travelTime
                + "\nIndoor Type: " + indoorType + "\nTheme Name: " + themeName;
    }

    // XML로 변환하는 메서드
    public Element toXmlElement(Document doc) {
        Element courseDataElement = doc.createElement("CourseData");

        courseDataElement.appendChild(createElement(doc, "ThemeCategory", themeCategory));
        courseDataElement.appendChild(createElement(doc, "CourseId", String.valueOf(courseId)));
        courseDataElement.appendChild(createElement(doc, "TourismId", String.valueOf(tourismId)));
        courseDataElement.appendChild(createElement(doc, "RegionId", String.valueOf(regionId)));
        courseDataElement.appendChild(createElement(doc, "TourismName", tourismName));
        courseDataElement.appendChild(createElement(doc, "Longitude", longitude));
        courseDataElement.appendChild(createElement(doc, "Latitude", latitude));
        courseDataElement.appendChild(createElement(doc, "CourseOrder", String.valueOf(courseOrder)));
        courseDataElement.appendChild(createElement(doc, "TravelTime", String.valueOf(travelTime)));
        courseDataElement.appendChild(createElement(doc, "IndoorType", indoorType));
        courseDataElement.appendChild(createElement(doc, "ThemeName", themeName));

        return courseDataElement;
    }

    private Element createElement(Document doc, String tagName, String value) {
        Element element = doc.createElement(tagName);
        element.appendChild(doc.createTextNode(value));
        return element;
    }
}

