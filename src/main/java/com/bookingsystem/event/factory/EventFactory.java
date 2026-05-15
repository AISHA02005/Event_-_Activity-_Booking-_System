package com.bookingsystem.event.factory;

import com.bookingsystem.event.model.Event;
import com.bookingsystem.shared.enums.EventType;

import java.util.Map;

/**
 * Factory Pattern — Interface
 * كل event بيتعمل عن طريق الـ factory مش عن طريق new مباشرة
 */
public interface EventFactory {

    /**
     * الـ method الأساسية — بتاخد النوع والبيانات وترجع الـ Event المناسب
     * @param type     نوع الـ event
     * @param params   Map فيها كل البيانات المطلوبة للـ event
     * @return         Event object
     */
    Event createEvent(EventType type, Map<String, Object> params);
}