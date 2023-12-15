package com.example.project31;

public class Event {
    private String _id;
    private String eventName;
    private String _eventDescription;
    private int _eventAge;
    private double _distanceRequired;
    private int _levelRequired;

    private String _eventSafety;
    private String _eventConditions;
    private String _eventRules;
    private double _eventLength;
    private double _eventElevation;
    private String _eventStage;
    private String _creator;

    public Event(){

    }
    public Event(String id, String eventType, String description, int eventAge, double distanceRequired, int levelRequired, String creator) {
        _id = id;
        eventName = eventType;
        _eventDescription = description;
        _eventAge = eventAge;
        _distanceRequired = distanceRequired;
        _levelRequired = levelRequired;
        _creator = creator;
    }

    //Time trials event constructor
    public Event (String id, String eventType, String description, int age, double distance, String safety, String conditions, String rules ) {
        _id = id;
        eventName = eventType;
        _eventDescription = description;
        _eventAge = age;
        _distanceRequired = distance;
        _eventSafety = safety;
        _eventConditions = conditions;
        _eventRules = rules;
    }

    //Hill Climb Event constructor
    public Event (String id, String eventType, String description, int eventAge, int levelRequired, String safety, double length, double elevation) {
        _id = id;
        eventName = eventType;
        _eventDescription = description;
        _eventAge = eventAge;
        _levelRequired = levelRequired;
        _eventSafety = safety;
        _eventLength = length;
        _eventElevation = elevation;
    }

    //Road Stage Race Event Constructor
    public Event (String id, String eventType, String description, int age, int level, String safety, String stage, String rules ) {
        _id = id;
        eventName = eventType;
        _eventDescription = description;
        _eventAge = age;
        _levelRequired = level;
        _eventSafety = safety;
        _eventStage = stage;
        _eventRules = rules;
    }

    public void setId(String id) {
        _id = id;
    }
    public String getId() {
        return _id;
    }

    public void setEventName(String eventName){
        this.eventName = eventName;
    }

    public String getEventName(){
        return eventName;
    }

    public String get_creator() {return _creator;}

    public void setEventDescription(String eventDescription){
        _eventDescription = eventDescription;
    }


    public String getEventDescription(){
        return _eventDescription;
    }
    public void setEventAge(int eventAge){
        _eventAge = eventAge;
    }

    public int getEventAge(){
        return _eventAge;
    }

    public void setDistanceRequired(double distanceRequired){
        _distanceRequired = distanceRequired;
    }

    public double getDistanceRequired(){
        return _distanceRequired;
    }

    public void setLevelRequired(int levelRequired){
        _levelRequired = levelRequired;
    }

    public int getLevelRequired() {
        return _levelRequired;
    }


    @Override
    public String toString() {
        return "Event{" +
                "_id='" + _id + '\'' +
                ", _eventName='" + eventName + '\'' +
                ", _eventDescription='" + _eventDescription + '\'' +
                ", _eventAge=" + _eventAge +
                ", _distanceRequired=" + _distanceRequired +
                ", _levelRequired=" + _levelRequired +
                ", _eventSafety='" + _eventSafety + '\'' +
                ", _eventConditions='" + _eventConditions + '\'' +
                ", _eventRules='" + _eventRules + '\'' +
                ", _eventLength=" + _eventLength +
                ", _eventElevation=" + _eventElevation +
                ", _eventStage='" + _eventStage + '\'' +
                ", _creator='" + _creator + '\'' +
                '}';
    }
}
