package com.loveuu.vv.base.eventbus;

/**
 * Created by VV on 2016/9/21.
 * EventBus传递对象
 */

public class EventObject {
    private Object mObject;
    private int mEventId;

    public EventObject() {
    }

    public EventObject(Object object, int eventId) {
        mObject = object;
        mEventId = eventId;
    }

    public <T> T getObject() {
        return (T) mObject;
    }

    public void setObject(Object object) {
        mObject = object;
    }

    public int getEventId() {
        return mEventId;
    }

    public void setEventId(int eventId) {
        mEventId = eventId;
    }
}
