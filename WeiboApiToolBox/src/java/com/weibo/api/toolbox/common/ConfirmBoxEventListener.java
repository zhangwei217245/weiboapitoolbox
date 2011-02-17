/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weibo.api.toolbox.common;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;

/**
 *
 * @author x-spirit
 */
public class ConfirmBoxEventListener implements EventListener {

    private static final String EVT_OK = "onOK";
    private static final String EVT_CANCEL = "onCancel";
    EventListenerAction ok_operation = new NullListenerAction();
    EventListenerAction cancel_operation = new NullListenerAction();


    public void onEvent(Event event) throws Exception {
        if (EVT_OK.equals(event.getName())) {
            ok_operation.execute();
        }else if (EVT_CANCEL.equals(event.getName())){
            cancel_operation.execute();
        }
    }

    public EventListenerAction getCancel_operation() {
        return cancel_operation;
    }

    public ConfirmBoxEventListener setCancel_operation(EventListenerAction cancel_operation) {
        this.cancel_operation = cancel_operation;
        return this;
    }

    public EventListenerAction getOk_operation() {
        return ok_operation;
    }

    public ConfirmBoxEventListener setOk_operation(EventListenerAction ok_operation) {
        this.ok_operation = ok_operation;
        return this;
    }
}
