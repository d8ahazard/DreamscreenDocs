package com.philips.lighting.hue.sdk.wrapper.domain.device.light;

import com.philips.lighting.hue.sdk.wrapper.utilities.Color.XY;

public interface CustomStartUpSettings {
    Integer getBri();

    Integer getCt();

    XY getXy();

    void setBri(Integer num);

    void setCt(Integer num);

    void setXy(XY xy);
}
