package com.philips.lighting.hue.sdk.wrapper.utilities;

import com.philips.lighting.hue.sdk.wrapper.WrapperObject;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightPoint;
import java.util.List;

public final class LightPointClustering extends WrapperObject {
    /* access modifiers changed from: protected */
    public native List<LightPointCluster> clusterNative(LightPoint[] lightPointArr);

    /* access modifiers changed from: protected */
    public native void create();

    /* access modifiers changed from: protected */
    public native void delete();

    public LightPointClustering() {
        create();
    }

    public final List<LightPointCluster> cluster(List<LightPoint> lightPoints) {
        return clusterNative((LightPoint[]) lightPoints.toArray(new LightPoint[lightPoints.size()]));
    }

    protected LightPointClustering(Scope scope) {
    }
}
